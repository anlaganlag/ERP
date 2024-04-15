package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.shipment.consumer.K3CloudWebApiConsumer;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.*;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.*;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentBoardParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.TrackParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.TrackingTransferParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.*;
import com.tadpole.cloud.operationManagement.modular.shipment.service.*;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsDayParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.ILogisticsDayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 发货追踪汇总表 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
@Service
@Slf4j
public class ShipmentTrackingServiceImpl extends ServiceImpl<ShipmentTrackingMapper, ShipmentTracking> implements IShipmentTrackingService {

    @Resource
    private ShipmentTrackingMapper mapper;

    @Resource
    private ShipmentApplyItemMapper ApplyItemMapper;


    @Resource
    private TrackingTransferMapper transferMapper;

    @Resource
    private TrackingShippingMapper shippingMapper;

    @Resource
    private TrackingLogisticsMapper logisticsMapper;


    @Resource
    private IOtherModularService otherModularService;
    @Resource
    private K3CloudWebApiConsumer k3CloudWebApiConsumer;

    @Resource
    private ITrackingTransferService trackingTransferService;

    @Resource
    private ITrackingShippingService shippingService;


    @Resource
    private ITrackingLogisticsService logisticsService;

    @Resource
    private ILogisticsDayService logDayService;




    @Autowired
    private RedisTemplate redisTemplate;

    private static String SHIPMENT_TRACKING_KEY="SHIPMENT_TRACKING_KEY";

    private static Integer BATCH_SIZE=800;


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData transferListRevoke(List<TrackingTransferParam> transferList) {
        StringBuffer errorInfo = new StringBuffer();
        for (TrackingTransferParam trackingTransferParam : transferList) {
            String billNo = trackingTransferParam.getBillNo();
            String revokeReason = trackingTransferParam.getRevokeReason();
            try {
                ResponseData rd = this.transferRevoke(billNo, revokeReason);
                if (rd.getCode() == 500) {
                    errorInfo.append(StrUtil.format("{}: {} \n", billNo, rd.getMessage()));
                }
            } catch (Exception e) {
                log.error("批量撤销时订单【{}】出现异常:{}", billNo, e);
            }
        }
        if (errorInfo.length() > 0) {
            return ResponseData.error(errorInfo.toString());
        }
        return ResponseData.success("调拨单批量撤销成功");
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public ResponseData transferRevoke(String billNo, String revokeReason) {
        //撤销原因校验
        if (StrUtil.isEmpty(revokeReason)) {
            log.info("transferCancel>>调拨单撤销>>撤销原因为空");
            return ResponseData.error("撤销原因为空");
        }

        if (StrUtil.isEmpty(billNo)) {
            log.info("transferCancel>>调拨单撤销>>单据编号为空");
            return ResponseData.error("单据编号为空");
        }


        //Team校验
        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();
        if (ObjectUtil.isNull(currentUser)) {
            log.info("transferCancel>>调拨单撤销>>请登录后再撤销");
            return ResponseData.error("登录后再撤销");
        }
        if (ObjectUtil.isEmpty(currentUser.getTeam())) {
            log.info("transferCancel>>调拨单撤销>>无法获取当前登陆人Team");
            return ResponseData.error("无法获取当前登陆人Team");
        }
        Page pageContext = getPageContext();
        TrackParam trackParam = TrackParam.builder().billNo(billNo).team(currentUser.getTeam()).build();

        List<TrackingFlatVO> trackingFlatVOS = this.baseMapper.trackFlatList(pageContext, trackParam).getRecords();
        if (ObjectUtil.isEmpty(trackingFlatVOS) || trackingFlatVOS.size() == 0) {
            log.info("找不对应申请批次号[{}]", billNo);
            return ResponseData.error(StrUtil.format("找不对应申请批次号[{}]", billNo));
        }
        TrackingFlatVO tackInfo = trackingFlatVOS.get(0);
        String team = tackInfo.getTeam();
        if (team != null && !currentUser.getTeam().equals(team)) {
            log.info("不能撤销[{}]单据编号[{}]", team, billNo);
            return ResponseData.error(StrUtil.format("不能撤销非本Team[{}]单据编号:[{}]", team, billNo));
        }
        // when 0 then '待审核'  when 1 then '已通过' when 2 then '未通过'
        if ("待审核".equals(tackInfo.getCheckStatusName())) {
            return ResponseData.error(StrUtil.format("申请批次号[{}]还未审核操作,请审核处理！", billNo));
        }
        if ("未通过".equals(tackInfo.getCheckStatusName())) {
            return ResponseData.error(StrUtil.format("申请批次号[{}]已经审核未通过，不用撤销处理！", billNo));
        }
        /*List<TrackingFlatVO> billDownStreamList = trackingFlatVOS.stream().filter(i -> i.getBillNo().equals(billNo)).collect(Collectors.toList());

        // 情况一:有拣货单/有出货单/有物流单    失败
        List<TrackingFlatVO> LogisticsNumberList = billDownStreamList.stream().filter(i -> StrUtil.isNotEmpty(i.getLogisticsNumber())).collect(Collectors.toList());
        if (LogisticsNumberList.size() > 0) {
            log.info("调拨单【{}】已存在物流单,无法撤销", billNo);
            return ResponseData.error(StrUtil.format("调拨单号[{}]在K3系统已下推形成了出货单，并在EBMS生成了物流信息,无法撤销", billNo));
        }*/

        String resultMsg = "";
        if (! k3CloudWebApiConsumer.delTranferByBillNo(billNo)) {
            log.info("调用ERP删除调拨单接口-撤销失败[{}],下游存在关联单据！" ,billNo);
            resultMsg = "撤销失败！！调拨单【"+billNo+"】在K3系统下游存在关联单据,请和线下仓库同事沟通处理后再来撤销！！！!";
            return ResponseData.error(resultMsg);
        }

        resultMsg = "调拨单【"+billNo+"】撤销成功";
        log.info("调用ERP删除调拨单接口-撤销成功[{}],无下游关联单据！" ,billNo);
        //同步改状态 1.撤销9    2.无需追踪1
        ResponseData responseData = trackingTransferService.transferRevoke(tackInfo.getApplyBatchNo(), billNo, currentUser, revokeReason);
        if (responseData.getSuccess()) {
            responseData.setData(resultMsg);
        }
        return responseData;
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData track(String batchNo) {

        try {

            // 追踪数据有可能冲突 需要加锁来排除冲突
            Object shipmentTrackingKey = redisTemplate.opsForValue().get(SHIPMENT_TRACKING_KEY);
            if (ObjectUtil.isNotNull(shipmentTrackingKey)) {
                return ResponseData.success("发货追踪有任务还未执行完毕,本次请求结束");
            }

            redisTemplate.boundValueOps(SHIPMENT_TRACKING_KEY)
                    .set(SHIPMENT_TRACKING_KEY, Duration.ofMinutes(30));
            log.info("发货追踪批量执行开始：【{}】",new Date());


            LambdaQueryWrapper<ShipmentTracking> trackWrapper = new LambdaQueryWrapper<>();
            trackWrapper.eq(ShipmentTracking::getNeedTrack, 0);
            trackWrapper.eq(ShipmentTracking::getTrackingStatus, "执行中");
            trackWrapper.eq(ObjectUtil.isNotEmpty(batchNo), ShipmentTracking::getApplyBatchNo, batchNo);
            List<ShipmentTracking> trackingList = mapper.selectList(trackWrapper);
            if (ObjectUtil.isEmpty(trackingList)) {
                log.info("没有找到需要跟踪的发货批次数据" + LocalDateTime.now().toString());
                return ResponseData.success();
            }

            Set<String> batchNoSet = trackingList.stream().map(ShipmentTracking::getApplyBatchNo).collect(Collectors.toSet());
            //调拨单信息更新
            List<TrackingTransfer> transferList = new ArrayList<>();
            //分批查询 超过1000个 oracle的 in会报错
            for (List<String> batchNoList : CollUtil.split(batchNoSet, BATCH_SIZE)) {
                LambdaQueryWrapper<TrackingTransfer> transferWrapper = new LambdaQueryWrapper<>();
                transferWrapper.in(TrackingTransfer::getApplyBatchNo, batchNoList);//
                transferWrapper.ge(TrackingTransfer::getApplyTrackStatus, 2);//同步成功以后的状态都需要追踪，
                // transferWrapper.eq(TrackingTransfer::getNeedTrack, 0);//当数据完结时不需要追踪
                transferWrapper.eq(ObjectUtil.isNotEmpty(batchNo), TrackingTransfer::getApplyBatchNo, batchNo);
                transferList.addAll(transferMapper.selectList(transferWrapper));
            }


            Map<Integer, List<TrackingTransfer>> transferMap = transferList.stream().collect(Collectors.groupingBy(TrackingTransfer::getNeedTrack));


            //1--调拨单更新处理
            //部分调拨单 已经不需要更新了,但是可能需要跟踪发货单信息
            List<TrackingTransfer> transferList1 = transferMap.get(1);
            if (ObjectUtil.isEmpty(transferList1)) {
                transferList1 = new ArrayList<>();
            }


            //要更新的调拨单
            List<TrackingTransfer> transferList0 = transferMap.get(0);
            if (ObjectUtil.isNotEmpty(transferList0)) {
                transferList0 = this.updateTransferFromErp(transferList0);
                transferList1.addAll(transferList0);
            }
            //2--出货清单处理


            //获取所有拣货单号
            Map<String, List<TrackingTransfer>> pickListCodeMap =
                    transferList1.stream().filter(t -> ObjectUtil.isNotEmpty(t.getPickListCode())).collect(Collectors.groupingBy(TrackingTransfer::getPickListCode));

            List<TrackingShipping> mergeShippingList = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(pickListCodeMap)) {
                mergeShippingList = this.createOrUpdateShipping(pickListCodeMap);
            }

            //3--物流信息处理
            List<TrackingLogistics> trackingLogisticsList = new ArrayList<>();
            if (ObjectUtil.isNotEmpty(mergeShippingList)) {
                Map<String, List<TrackingShipping>> shippingListCodeMap = mergeShippingList.stream().collect(Collectors.groupingBy(TrackingShipping::getShippingListCode));
                trackingLogisticsList = this.createOrUpdateLogistics(shippingListCodeMap);
            }


            //4--获取所有shipmentId，更新物流赔偿实际签收数据
            if (ObjectUtil.isNotEmpty(trackingLogisticsList)) {
                trackingLogisticsList = this.updateSignQty(trackingLogisticsList);
            }


            //5--来货报告更新 接收数量
            if (ObjectUtil.isNotEmpty(mergeShippingList)) {
                mergeShippingList = this.updateReceiveQty(mergeShippingList);
            }

            //6.更新调拨单状态,
            Set<String> billNoSet = transferList.stream().map(t -> t.getBillNo()).collect(Collectors.toSet());
            if (ObjectUtil.isNotEmpty(billNoSet)) {
                this.updateTransferStatus(billNoSet);
            }

            //7.异常数据处理（在K3系统里已抓取的（拣货单，出货清单被删除了）2023-3-28
            this.updateK3DelData();

            //8.更新汇总数量，和汇总状态
            if (ObjectUtil.isNotEmpty(batchNoSet)) {
                this.calcSumQty(batchNoSet);
            }

            redisTemplate.delete(SHIPMENT_TRACKING_KEY);
            log.info("发货追踪批量执行结束：【{}】",new Date());

            return ResponseData.success();
        } catch (Exception e) {
            redisTemplate.delete(SHIPMENT_TRACKING_KEY);
            log.error("追踪异常："+ JSONUtil.toJsonStr(e));
            return ResponseData.error(JSONUtil.toJsonStr(e));
        }
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public void updateK3DelData() {
        //    1.调拨单状态小于6（物流中）的所有调拨单都需要检查  获取拣货单号
        LambdaQueryWrapper<TrackingTransfer> transferWrapper = new LambdaQueryWrapper<>();
        transferWrapper.lt(TrackingTransfer::getApplyTrackStatus,6);//小于6  物流中的状态   有物流信息后，业务上不允许再删除
        transferWrapper.isNotNull(TrackingTransfer::getPickListCode);//要有拣货单
        List<TrackingTransfer> transferList = transferMapper.selectList(transferWrapper);
        if (ObjectUtil.isEmpty(transferList)) {
            return;
        }
        Map<String, List<TrackingTransfer>> transferMap = transferList.stream().collect(Collectors.groupingBy(TrackingTransfer::getBillNo));
        Map<String, List<TrackingTransfer>> pickListCodeMap = transferList.stream().collect(Collectors.groupingBy(TrackingTransfer::getPickListCode));

        Set<String> billNoSet = transferMap.keySet().stream().collect(Collectors.toSet());//调拨单号
        List<ErpTransferResult> erpTransferList = otherModularService.getErpTransfer(billNoSet);

        //移除能查找到的拣货单，则剩下的就是未能找到的拣货单 被删除了
        Set<String> delPickListCodeSet = pickListCodeMap.keySet().stream().collect(Collectors.toSet());

        if (ObjectUtil.isNotEmpty(erpTransferList)) {
            for (ErpTransferResult erpTransferResult : erpTransferList) {
                if (ObjectUtil.isNotEmpty(erpTransferResult.getPickListCode())) {
                    delPickListCodeSet.remove(erpTransferResult.getPickListCode());
                }
            }
        }

        ArrayList<TrackingTransfer> needUpdateTransferList = new ArrayList<>();

        if (ObjectUtil.isNotEmpty(delPickListCodeSet)) {
            //有拣货单被删除了,更新调拨单为初始状态
            for (String pickListCode : delPickListCodeSet) {
                TrackingTransfer transfer = pickListCodeMap.get(pickListCode).get(0);
                transfer.setNeedTrack(0);
                transfer.setApplyTrackStatus(3);//待从新关联新的拣货单
                if (! pickListCode.contains("已删除")) {
                    transfer.setPickListCode("拣货单["+pickListCode+"]在K3已删除");//
                }
                transfer.setPickQty(BigDecimal.ZERO);
                transfer.setActualSendQty(BigDecimal.ZERO);
//                transfer.setPickFinishDate(DateUtil.parseDate("1997-01-01"));
                transfer.setReturnQty(BigDecimal.ZERO);

                needUpdateTransferList.add(transfer);
            }
            if (ObjectUtil.isNotEmpty(needUpdateTransferList)) {
                trackingTransferService.updateBatchById(needUpdateTransferList);
            }
        }

        //
        Set<String> pickListCodeSet2 = pickListCodeMap.keySet();
        List<TrackingShipping> shippingList = new ArrayList<>();
        for (List<String> pickListCode : CollUtil.split(pickListCodeSet2,BATCH_SIZE)) {
            LambdaQueryWrapper<TrackingShipping> shippingWrapper = new LambdaQueryWrapper<>();
            shippingWrapper.in (TrackingShipping::getPickListCode,pickListCode);//根据捡货单号 查找出货清单
            shippingList.addAll(shippingService.list(shippingWrapper));
        }

        if (ObjectUtil.isEmpty(shippingList)) {//没有出货清单
            return;
        }

        Map<String, List<TrackingShipping>> shippingListCodeMap = shippingList.stream().collect(Collectors.groupingBy(TrackingShipping::getShippingListCode));
        Set<String> shippingCodeSet = shippingListCodeMap.keySet();

        List<TrackingShipping> delShippingDataList = new ArrayList<>();

        if (ObjectUtil.isNotEmpty(delPickListCodeSet)) {
            //拣货单被删除了，对应的出货清单也一定删除了
            List<TrackingShipping> shippingDelByPickListCode = shippingList.stream().filter(s -> delPickListCodeSet.contains(s.getPickListCode())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(shippingDelByPickListCode)) {
                delShippingDataList.addAll(shippingDelByPickListCode);
            }
        }

        List<TrackingShippingResult> erpShippingList = otherModularService.getErpShipping(pickListCodeSet2);

        if (ObjectUtil.isNotEmpty(erpShippingList)) {
            for (TrackingShippingResult shippingResult : erpShippingList) {
                if (ObjectUtil.isNotEmpty(shippingResult.getShippingListCode())) {
                    shippingCodeSet.remove(shippingResult.getShippingListCode());
                }
            }
        }

        if (ObjectUtil.isNotEmpty(shippingCodeSet)) {//有删除了出货清单的数据
            for (String shippingCode : shippingCodeSet) {
                delShippingDataList.addAll(shippingListCodeMap.get(shippingCode));
            }
        }

        if (ObjectUtil.isNotEmpty(delShippingDataList)) {
            List<BigDecimal> delIdList = delShippingDataList.stream().map(s -> s.getId()).collect(Collectors.toList());
            shippingService.getBaseMapper().deleteBatchIds(delIdList);
        }

        //接收数量不等于发货数量，EBMS手动关闭，修改调拨单状态
        List<TrackingTransfer> needUpdateTransfer =new ArrayList<>();

        //  1---  1个调拨单对应一个shipmentId的时候（绝大多数都是这种情况）
        List<TrackingTransfer> transfers= transferMapper.getNeedCheckTransferData(false);

        if (ObjectUtil.isNotEmpty(transfers)) {
            for (List<TrackingTransfer> trackingTransferList : CollectionUtil.split(transfers, BATCH_SIZE)) {
                needUpdateTransfer.addAll(  checkTransferByEbmsData(trackingTransferList));
            }
        }

        // 2--- 1个调拨单对应多个shipmentId的时候
        List<TrackingTransfer> multipleShipmentIdList= transferMapper.getNeedCheckTransferData(true);
        if (ObjectUtil.isNotEmpty(multipleShipmentIdList)) {
            Map<String, List<TrackingTransfer>> transferMultipleShipmentIdMap = multipleShipmentIdList.stream().collect(Collectors.groupingBy(t -> t.getBillNo()));
            for (Map.Entry<String, List<TrackingTransfer>> entry : transferMultipleShipmentIdMap.entrySet()) {
                List<TrackingTransfer> values = entry.getValue();
                List<TrackingTransfer> checkList = checkTransferByEbmsData(values);
                //1个调拨单对应多个shipmentId的时候 所有shipmentId对应的sku+站点的状态都是已完成了才更新调拨单
                if (ObjectUtil.isNotEmpty(checkList) && checkList.size()==values.size()) {
                    needUpdateTransfer.addAll(checkList);
                }
            }
        }

        if (ObjectUtil.isNotEmpty(needUpdateTransfer)) {
            trackingTransferService.updateBatchById(needUpdateTransfer);
        }

        return;
    }

    private List<TrackingTransfer>  checkTransferByEbmsData(List<TrackingTransfer> trackingTransferList) {
        Map<String, List<TrackingTransfer>> listMap = trackingTransferList.stream().collect(Collectors.groupingBy(t -> t.getShipmentId() + t.getSku()));
        Set<String> mergeFieldList = listMap.keySet();
        List<String> successMmergeFieldList = otherModularService.getSkuStatus(new ArrayList<>(mergeFieldList));

        Date date = new Date();

        List<TrackingTransfer> needUpdateTransfer = new ArrayList<>();

        if (ObjectUtil.isNotEmpty(successMmergeFieldList)) {
            for (String mf : successMmergeFieldList) {
                List<TrackingTransfer> needUpdatelist = listMap.get(mf);
                if (ObjectUtil.isNotEmpty(needUpdatelist)) {
                    for (TrackingTransfer nt : needUpdatelist) {
                        nt.setApplyTrackStatus(8);
                        nt.setUpdatedTime(date);
                    }
                    needUpdateTransfer.addAll(needUpdatelist)   ;
                }
            }
        }
        return needUpdateTransfer;
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public PageResult<TrackingFlatVO> trackFlatList(TrackParam param) {

        Page pageContext = param.getPageContext();
        IPage<TrackingFlatVO> page = mapper.trackFlatList(pageContext, param);

        return new PageResult<>(page);

    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public PageResult<ShipmentTrackingResult> trackBatchList(TrackParam param) {
        Page pageContext = param.getPageContext();
        IPage<ShipmentTrackingResult> page = mapper.trackBatchList(pageContext, param);
        return new PageResult<>(page);
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData trackTransferList(String applyBatchNo) {
        if (StrUtil.isEmpty(applyBatchNo)) {
            return ResponseData.error("批次号空");
        }
        return ResponseData.success(mapper.trackTransferList(applyBatchNo));

    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData trackLogisticsList(String billNo) {
        if (StrUtil.isEmpty(billNo)) {
            return ResponseData.error("调拨单号空");
        }
        return ResponseData.success(mapper.trackLogisticsList(billNo));
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData delTranferByBillNo(String billNo) {
        boolean b = k3CloudWebApiConsumer.delTranferByBillNo(billNo);
        return ResponseData.success(b);
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public void updateTransferStatus(Set<String> billNoSet) {
        for (List<String> billNoList : CollectionUtil.split(billNoSet, BATCH_SIZE)) {
            trackingTransferService.updateTransferStatus(billNoList);
        }
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public void calcSumQty(Set<String> batchNoSet) {
        for (List<String> batchNoList : CollectionUtil.split(batchNoSet, BATCH_SIZE)) {
            mapper.calcSumQty(batchNoList);
        }
    }

    /**
     * 跟新出货清单的接受数量
     *
     * @param mergeShippingList
     * @return
     */
    private List<TrackingShipping> updateReceiveQty(List<TrackingShipping> mergeShippingList) {
        Set<String> shipmentIdSet = mergeShippingList.stream().map(TrackingShipping::getShipmentId).filter(ObjectUtil::isNotEmpty).collect(Collectors.toSet());

        if (ObjectUtil.isEmpty(shipmentIdSet)) {
            return mergeShippingList;
        }
        List<WareHouseReceiveResult> receiveResultList = otherModularService.getReceiveQtyFromWareHouse(shipmentIdSet);

        if (ObjectUtil.isEmpty(receiveResultList)) {
            return mergeShippingList;
        }

        Map<String, List<TrackingShipping>> shippingMap =
                mergeShippingList.stream().collect(Collectors.groupingBy(s -> s.getShipmentId() + s.getSku() + s.getSysSite()));

        List<TrackingShipping> upShippingList = new ArrayList<>();

        for (WareHouseReceiveResult receiveResult : receiveResultList) {
            List<TrackingShipping> shippingList = shippingMap.get(receiveResult.getMergeField());
            if (ObjectUtil.isNotEmpty(shippingList)) {
                for (TrackingShipping shipping : shippingList) {
                    shipping.setReceiveQty(receiveResult.getReceiveQty());
                    shipping.setReceiveEndDate(receiveResult.getBusinessDate());
                    shipping.setUpdatedTime(new Date());
                    upShippingList.add(shipping);
                    //todo:什么时候不更新接受数量？
                }
            }
        }
        if (ObjectUtil.isNotEmpty(upShippingList)) {
            shippingService.updateBatchById(upShippingList);
        }


        return null;
    }

    /**
     * 更新签收数量
     *
     * @param trackingLogisticsList
     * @return
     */
    private List<TrackingLogistics> updateSignQty(List<TrackingLogistics> trackingLogisticsList) {
        /**
         * 物流签收差异数量
         * 1：差异数量是根据ShipmentID维度 给出的一个总的差异数量
         * 2：当有差异数量时，更新该shipmentId对应的出物流信息的签收数量
         * （当一个shipmentId对应多个物流信息时，按照每个物流信息中包含的sku数量的从大到小依次扣减 差异的数量，直到差异数量扣减为0）
         */


        Set<String> shipmentIdSet = trackingLogisticsList.stream().map(l -> l.getShipmentId()).collect(Collectors.toSet());

        //根据shipmentId查询出需要赔偿管理的
        List<EbmsCompensateResult> compensateList = otherModularService.getCompensateDataFromEbms(shipmentIdSet);

        //shipmentId + logisticsNumber + sku //更新接收数量
        Map<String, List<TrackingLogistics>> logisticsMap =
                trackingLogisticsList.stream().collect(Collectors.groupingBy(l -> l.getShipmentId() + l.getLogisticsNumber() + l.getSku()));
        List<TrackingLogistics> updateLogisticsList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(compensateList)) {

//            值源：EBMS物流索赔管理-接收数量 关联查找出本次物流签收的sku的数量

            for (EbmsCompensateResult ebmsCompensateResult : compensateList) {
                List<TrackingLogistics> logisticsList = logisticsMap.get(ebmsCompensateResult.getMergeField());
                if (ObjectUtil.isNotEmpty(logisticsList)) {
                    for (TrackingLogistics logistics : logisticsList) {
                        if (ObjectUtil.isNotNull(ebmsCompensateResult.getReceiveQty()) && ebmsCompensateResult.getReceiveQty().compareTo(BigDecimal.ZERO)>0) {
                            logistics.setSignQty(ebmsCompensateResult.getReceiveQty());
                            logistics.setUpdatedTime(new Date());
                            updateLogisticsList.add(logistics);
                        }
                    }
                }
            }
            if (ObjectUtil.isNotEmpty(updateLogisticsList)) {
                logisticsService.updateBatchById(updateLogisticsList);
            }
        }
        return updateLogisticsList;
    }

    /**
     * 物流信息新增+更新
     *
     * @param shippingListCodeMap
     * @return
     */
    private List<TrackingLogistics> createOrUpdateLogistics(Map<String, List<TrackingShipping>> shippingListCodeMap) {
        Set<String> shippingListCodeSet = shippingListCodeMap.keySet();

        //查询数据库已有的 物流信息
        List<TrackingLogistics> alreadyLogisticsList = new ArrayList<>();
        for (List<String> shippingList : CollUtil.split(shippingListCodeSet, BATCH_SIZE)) {
            LambdaQueryWrapper<TrackingLogistics> logisticsWrapper = new LambdaQueryWrapper<>();
            logisticsWrapper.in(TrackingLogistics::getShippingListCode, shippingList);
            alreadyLogisticsList.addAll(logisticsMapper.selectList(logisticsWrapper));
        }

        List<TrackingLogistics> upAlreadyLogisticsList = new ArrayList<>();
        List<TrackingLogistics> delAlreadyLogisticsList = new ArrayList<>();
        List<TrackingLogistics> newLogisticsList = new ArrayList<>();
        boolean logisticsHave = ObjectUtil.isNotEmpty(alreadyLogisticsList);

        Map<String, List<TrackingLogistics>> alreadyLogisticsMap = new HashMap<>();
        if (logisticsHave) {
            alreadyLogisticsMap = alreadyLogisticsList.stream().collect(Collectors.groupingBy(TrackingLogistics::getShippingListCode));
        }

        //根据出货清单号查询 erp物流信息
        List<TrackingLogisticsResult> erpLogisticsList = otherModularService.getEbmsLogistics(shippingListCodeSet);

        boolean erpLogisticsListHave = ObjectUtil.isNotEmpty(erpLogisticsList);
        Map<String, List<TrackingLogisticsResult>> erpLogisticsMap = new HashMap<>();
        if (erpLogisticsListHave) {
            erpLogisticsMap = erpLogisticsList.stream().collect(Collectors.groupingBy(TrackingLogisticsResult::getShippingListCode));
        }

        if (erpLogisticsListHave) {
            //erp查询回来有数据
            for (String shippingCode : shippingListCodeSet) {
                // 出货清单是否产生了物流单
                List<TrackingLogisticsResult> erpResultList = erpLogisticsMap.get(shippingCode);
                List<TrackingLogisticsResult> addList = new ArrayList<>();

                if (ObjectUtil.isNotEmpty(erpResultList)) {
                    // 该出货清单对应多个物流单
                    if (logisticsHave) {//本地已有物流单，找出之前的物流单数据，可能本次会有新增的

                        List<TrackingLogistics> mcLogisticsList = alreadyLogisticsMap.get(shippingCode);
                        if (ObjectUtil.isNotEmpty(mcLogisticsList)) {
                            //之前有保存,则过滤出来，更新数据
                            List<String> mcLogisticsCode = mcLogisticsList.stream().map(m -> m.getLogisticsNumber()).collect(Collectors.toList());
                            for (TrackingLogistics mcLogistics : mcLogisticsList) {
                                List<TrackingLogisticsResult> filterResult = erpResultList.stream()
                                        .filter(er -> mcLogistics.getLogisticsNumber().equals(er.getLogisticsNumber())
                                                && mcLogistics.getSendBatchNo().equals(er.getSendBatchNo())
                                                && mcLogistics.getPickListCode().equals(er.getPickListCode()))
                                        .collect(Collectors.toList());
                                if (ObjectUtil.isNotEmpty(filterResult)) {
                                    mcLogistics.setLogisticsState(filterResult.get(0).getLogisticsState());
                                    mcLogistics.setSignQty(filterResult.get(0).getSignQty());
                                    mcLogistics.setSignDate(filterResult.get(0).getSignDate());
                                    mcLogistics.setPreArriveDate(filterResult.get(0).getPreArriveDate());
                                    mcLogistics.setRemark(filterResult.get(0).getRemark());
                                    mcLogistics.setLogisticsSendQty(filterResult.get(0).getLogisticsSendQty());
                                    mcLogistics.setShipmentId(filterResult.get(0).getShipmentId());
                                    mcLogistics.setLogisticsSendQty(filterResult.get(0).getLogisticsSendQty());
                                    if (ObjectUtil.isNotNull(filterResult.get(0).getSignDate())) {
                                        //默认 签收数量 等于发货数量，如果索赔管理有相关数据则更新该值
                                        mcLogistics.setSignQty(mcLogistics.getLogisticsSendQty());
                                    }
                                    upAlreadyLogisticsList.add(mcLogistics);
                                }else { // EBMS物料信息有变动，比如删除了上次填写的发货批次或者物流单号，都会导致MC里面有匹配不上的物流信息
                                    delAlreadyLogisticsList.add(mcLogistics);
                                }
                            }

                            addList = erpResultList.stream().filter(ep -> !mcLogisticsCode.contains(ep.getLogisticsNumber())).collect(Collectors.toList());
                        } else {
                            addList = erpResultList;
                        }
                    } else {
                        addList = erpResultList;
                    }

                    //需要新增的出货信息
                    for (TrackingLogisticsResult erpResult : addList) {
                        //过滤属于本出货清单的
                        if (!shippingCode.equals(erpResult.getShippingListCode())) {
                            continue;
                        }

                        List<TrackingShipping> shippings = shippingListCodeMap.get(shippingCode);

                        if (ObjectUtil.isNotEmpty(shippings)) {

                            Map<String, List<TrackingShipping>> pickShippingMap = shippings.stream().collect(Collectors.groupingBy(TrackingShipping::getPickListCode));
                            if (!pickShippingMap.containsKey(erpResult.getPickListCode())) {
                                continue;
                            }
                            //todo: 拣货单+出货清单+sku 确定生成一个物流信息
                            List<TrackingShipping> shippingList = pickShippingMap.get(erpResult.getPickListCode());

                            TrackingShipping trackingShipping = shippingList.get(0);

                            TrackingLogistics logistics = new TrackingLogistics();
                            BeanUtil.copyProperties(trackingShipping, logistics);
                            logistics.setSku(trackingShipping.getSku());
                            logistics.setSendBatchNo(erpResult.getSendBatchNo());
                            logistics.setLogisticsSendDate(erpResult.getLogisticsSendDate());
                            logistics.setLogisticsMode(erpResult.getLogisticsMode());
                            logistics.setLogisticsNumber(erpResult.getLogisticsNumber());
                            logistics.setPickListCode(erpResult.getPickListCode());
                            logistics.setShippingListCode(erpResult.getShippingListCode());
                            logistics.setShipmentId(erpResult.getShipmentId());
                            logistics.setLogisticsSendQty(erpResult.getLogisticsSendQty());
                            logistics.setLogisticsState(erpResult.getLogisticsState());
                            logistics.setSignDate(erpResult.getSignDate());
                            logistics.setPreArriveDate(erpResult.getPreArriveDate());
                            logistics.setRemark(erpResult.getRemark());
                            if (ObjectUtil.isNotNull(erpResult.getSignDate())) {
                                //默认 签收数量 等于发货数量，如果索赔管理有相关数据则更新该值
                                logistics.setSignQty(erpResult.getLogisticsSendQty());
                            }
                            logistics.setSyncTime(new Date());
                            logistics.setCreatedTime(new Date());
                            newLogisticsList.add(logistics);
                        }
                    }

                } else {
                    //出货清单还没有对应的物流单
                }
            }

            if (ObjectUtil.isNotEmpty(upAlreadyLogisticsList)) {
                logisticsService.updateBatchById(upAlreadyLogisticsList);
            }

            if (ObjectUtil.isNotEmpty(delAlreadyLogisticsList)) {
                List<BigDecimal> delIdList = delAlreadyLogisticsList.stream().map(l -> l.getId()).collect(Collectors.toList());
                for (List<BigDecimal> ids : CollUtil.split(delIdList, BATCH_SIZE)) {
                    logisticsService.removeByIds(ids);
                }
            }

            if (ObjectUtil.isNotEmpty(newLogisticsList)) {
                logisticsService.saveBatch(newLogisticsList);
                alreadyLogisticsList.addAll(newLogisticsList);
            }

        }

        return alreadyLogisticsList;
    }

    /**
     * 出货清单 新增+更新
     *
     * @param pickListCodeMap
     * @return
     */
    private List<TrackingShipping> createOrUpdateShipping(Map<String, List<TrackingTransfer>> pickListCodeMap) {

        Set<String> pickCodeSet = pickListCodeMap.keySet();

        //查询数据库已有的 出货清单
        List<TrackingShipping> alreadyShippingList = new ArrayList<>();
        for (List<String> pickList : CollUtil.split(pickCodeSet,BATCH_SIZE)) {
            LambdaQueryWrapper<TrackingShipping> shippingWrapper = new LambdaQueryWrapper<>();
            shippingWrapper.in(TrackingShipping::getPickListCode, pickList);
            alreadyShippingList.addAll(shippingMapper.selectList(shippingWrapper))  ;
        }

        List<TrackingShipping> newShippingList = new ArrayList<>();
        List<TrackingShipping> upAlreadyShippingList = new ArrayList<>();
        boolean havaShippingList = ObjectUtil.isNotEmpty(alreadyShippingList);
        Map<String, List<TrackingShipping>> alreadyShippingListMap = new HashMap<>();
        if (havaShippingList) {
            alreadyShippingListMap = alreadyShippingList.stream().collect(Collectors.groupingBy(TrackingShipping::getPickListCode));
        }

        //根据捡货单号查询 erp出货清单
        List<TrackingShippingResult> erpShippList = otherModularService.getErpShipping(pickCodeSet);
        boolean erpHaveShippingList = ObjectUtil.isNotEmpty(erpShippList);
        Map<String, List<TrackingShippingResult>> erpShippingMap = new HashMap<>();
        if (erpHaveShippingList) {
            erpShippingMap = erpShippList.stream().collect(Collectors.groupingBy(TrackingShippingResult::getPickListCode));
        }


        if (erpHaveShippingList) {
            //erp查询回来有数据
            for (String pickCode : pickCodeSet) {
                // 该拣货单是否产生了出货清单
                List<TrackingShippingResult> erpResultList = erpShippingMap.get(pickCode);

                if (ObjectUtil.isNotEmpty(erpResultList)) {
                    // 该拣货单可能对应多个出货清单
                    if (havaShippingList) {//本地已有出货清单，找出之前的出货清单数据，可能本次会有新增的

                        List<TrackingShipping> mcList = alreadyShippingListMap.get(pickCode);
                        if (ObjectUtil.isNotEmpty(mcList)) {
                            //之前有保存,则过滤出来更新字段，不需要新增保存，以免重复
                            List<String> mcShippinglistCode = mcList.stream().map(m -> m.getShippingListCode()).collect(Collectors.toList());
                            for (TrackingShipping shipping : mcList) {

                                List<TrackingShippingResult> filterResultList = erpResultList.stream().filter(er -> shipping.getShippingListCode().equals(er.getShippingListCode())
                                        && shipping.getPickListCode().equals(er.getPickListCode())).collect(Collectors.toList());

                                if (ObjectUtil.isNotEmpty(filterResultList)) {
                                    shipping.setShipmentId(filterResultList.get(0).getShipmentId());
//                                    shipping.setReceiveQty(filterResultList.get(0).getReceiveQty());
//                                    shipping.setReceiveEndDate(filterResultList.get(0).getReceiveEndDate());
                                    shipping.setUpdatedTime(new Date());
                                    upAlreadyShippingList.add(shipping);
                                }

                            }
                            //过滤出已有的，剩下需要新增加的
                            erpResultList = erpResultList.stream().filter(ep -> !mcShippinglistCode.contains(ep.getShippingListCode())).collect(Collectors.toList());
                        }
                    }

                    //过滤后需要新增的出货信息
                    for (TrackingShippingResult erpResult : erpResultList) {
                        List<TrackingTransfer> transfers = pickListCodeMap.get(pickCode);
                        if (ObjectUtil.isNotEmpty(transfers)) {
                            TrackingTransfer trackingTransfer = transfers.get(0);

                            TrackingShipping shipping = new TrackingShipping();
                            BeanUtil.copyProperties(trackingTransfer, shipping);
                            shipping.setSysSite(trackingTransfer.getSysSite());
                            shipping.setSku(trackingTransfer.getSku());
                            shipping.setShipmentId(erpResult.getShipmentId());
                            shipping.setPickListCode(erpResult.getPickListCode());
                            shipping.setShippingListCode(erpResult.getShippingListCode());
                            shipping.setPackingFinishDate(erpResult.getPackingFinishDate());
                            shipping.setShipingQty(erpResult.getShipingQty());
                            shipping.setBillNo(erpResult.getBillNo());
                            shipping.setCreatedTime(new Date());

                            //出货清单数据状态 A创建，B审核中，C已审核，D 重新审核，Z 暂存
                            if ("已审核".equals(erpResult.getFDocumentStatus()) || "重新审核".equals(erpResult.getFDocumentStatus())) {
                                //当出货清单对应的数据状态为 已审核，重新审核，则该出货清单数据不用再更新
                                shipping.setNeedTrack(1);
                            }

                            newShippingList.add(shipping);
                        }
                    }

                } else {
                    //拣货单还没有对应的出货清单
                }
            }

            if (ObjectUtil.isNotEmpty(upAlreadyShippingList)) {
                shippingService.updateBatchById(upAlreadyShippingList);
            }

            if (ObjectUtil.isNotEmpty(newShippingList)) {
                shippingService.saveBatch(newShippingList);
                alreadyShippingList.addAll(newShippingList);
            }

        }
        return alreadyShippingList;
    }


    /**
     * 调拨单更新
     *
     * @param needUpdateTransferList
     * @return
     */
    private List<TrackingTransfer> updateTransferFromErp(List<TrackingTransfer> needUpdateTransferList) {

        Set<String> billNoSet = needUpdateTransferList.stream().map(t -> t.getBillNo()).collect(Collectors.toSet());
        List<ErpTransferResult> erpTransferList = otherModularService.getErpTransfer(billNoSet);

        Map<String, List<ErpTransferResult>> ErpTransferMap = erpTransferList.stream().collect(Collectors.groupingBy(ErpTransferResult::getBillNo));
        for (TrackingTransfer transfer : needUpdateTransferList) {
            String billNo = transfer.getBillNo();
            List<ErpTransferResult> erpList = ErpTransferMap.get(billNo);

            if (ObjectUtil.isNotEmpty(erpList)) {
                //erp获取到了拣货单信息
                ErpTransferResult erpResult = erpList.get(0);
                if ("待拣货".equals(erpResult.getFUnwPickingmethod())) {
                    /** 申请追踪状态;值域{"0待审核"/"1已通过"/"2未通过"/"3待拣货"/"4拣货中"/"5装箱中"/"6物流中"/"7接收中"/"8已完结"/"9已撤销"} */
                    transfer.setApplyTrackStatus(4);
                } else if ("已拣货".equals(erpResult.getFUnwPickingmethod())) {
                    transfer.setApplyTrackStatus(5);
                    transfer.setPickQty(erpResult.getPickQty());
                    transfer.setPickListCode(erpResult.getPickListCode());
                    transfer.setActualSendQty(erpResult.getActualSendQty());
                    transfer.setPickFinishDate(erpResult.getPickFinishDate());
                    transfer.setReturnQty(erpResult.getReturnQty());
                    //1.1新增 返仓数量等于申请数量 调拨单状态改为已撤销
                    if (ObjectUtil.isNotNull(erpResult.getReturnQty())
                            && erpResult.getReturnQty().compareTo(transfer.getApplySendQty()) == 0 ) {
                        transfer.setApplyTrackStatus(9);//9已撤销
                        transfer.setNeedTrack(1);//全部返仓了无需追踪
                        transfer.setRevokeDate(new Date());
                        transfer.setRevokePerson("System");
                        transfer.setRevokePersonNo("System");
                        transfer.setRevokeReason("返仓数量=申请数量-->系统自动更改调拨单状态为[已撤销]");
                    }

                    //拣货单数据状态 A创建，B审核中，C已审核，D 重新审核，Z 暂存
                    if ("已审核".equals(erpResult.getFDocumentStatus()) || "重新审核".equals(erpResult.getFDocumentStatus())) {
                        //当调拨单对应的拣货单数据状态为 已审核，重新审核，则该调拨单数据不用再更新
//                        transfer.setNeedTrack(1);//  当调拨单对应产生了物流单号后就形成了固定值
                    }

                }

            } else {//erp未能获取到拣货单信息
                transfer.setApplyTrackStatus(3); //同步成功但是，还没有下推形成拣货单
            }
            transfer.setUpdatedTime(new Date());

        }
        trackingTransferService.updateBatchById(needUpdateTransferList);

        return needUpdateTransferList;
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData queryShipmentBoard(ShipmentBoardParam param) {
        //兼顾前端传入的参数单条和List
        List<String> teamList =ObjectUtil.isEmpty(param.getTeamList()) ? new ArrayList<>() : param.getTeamList();
        if (ObjectUtil.isEmpty(teamList) && ObjectUtil.isNotEmpty(param.getTeam())) {
            teamList.add(param.getTeam());
        }

        List<String> matCodeList =ObjectUtil.isEmpty(param.getMatCodeList()) ? new ArrayList<>() : param.getMatCodeList();
        if (ObjectUtil.isEmpty(matCodeList) && ObjectUtil.isNotEmpty(param.getMaterialCode())) {
            matCodeList.add(param.getMaterialCode());
        }

        List<String> areaList =ObjectUtil.isEmpty(param.getAreaList()) ? new ArrayList<>() : param.getAreaList();
        if (ObjectUtil.isEmpty(areaList) && ObjectUtil.isNotEmpty(param.getArea())) {
            areaList.add(param.getArea());
        }

        List<String> asinList =ObjectUtil.isEmpty(param.getAsinList()) ? new ArrayList<>() : param.getAsinList();
        if (ObjectUtil.isEmpty(asinList) && ObjectUtil.isNotEmpty(param.getAsin())) {
            asinList.add(param.getAsin());
        }

        if (ObjectUtil.isEmpty(teamList)||ObjectUtil.isEmpty(matCodeList)) {
            return ResponseData.error("Team和物料编码不能为空");
        }

        //area非空字符串过滤
        if (ObjectUtil.isNotEmpty(areaList)) {
            areaList = areaList.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        }
        //asin非空字符串过滤
        if (ObjectUtil.isNotEmpty(asinList)) {
            asinList = asinList.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        }

        //默认联泰仓库编号FHD06
        String ytDeliverypointNo = "FHD06";
        String suppilerDeliverypointNo = "FHD03";

        ShipmentBoardResult boardResult = new ShipmentBoardResult();
        //[库存情况]
        // 1- 待质检数据
        BigDecimal toCheckQty = trackingTransferService.toCheckQty(matCodeList);
        boardResult.setToCheckQty(toCheckQty);
        // 2- 在库
        //2.1获取总erp库存,不传发货点,返回所有发货后汇总库存
        Set<String> teamSet = new TreeSet<>(teamList);
        Set<String> matCodeSet = new TreeSet<>(matCodeList);
        Set<String> deliverypointNoSet = new TreeSet<>(Arrays.asList(ytDeliverypointNo,suppilerDeliverypointNo));
        List<ErpTeamAvailableQytResult> availableQytList = trackingTransferService.erpAvailableQty(teamSet,matCodeSet,deliverypointNoSet, new TreeSet<>());
        BigDecimal allErpCanTransferQty = availableQytList.stream().map(ErpTeamAvailableQytResult::getQty).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ytErpCanTransferQty = availableQytList.stream().filter(i->ytDeliverypointNo.equals(i.getDeliverypointNo())).map(ErpTeamAvailableQytResult::getQty).reduce(BigDecimal.ZERO, BigDecimal::add);

        boardResult.setAllErpCanTransferQty(allErpCanTransferQty);
        boardResult.setYtErpCanTransferQty(ytErpCanTransferQty);
        boardResult.setSupplierErpCanTransferQty(allErpCanTransferQty.subtract(ytErpCanTransferQty));

        //2.2已申请未审核数量

        // 全部发货点
        List<ShipmentApplyItem> allOccupyList  =new LambdaQueryChainWrapper<>(ApplyItemMapper)
                .in(ShipmentApplyItem::getTeam, teamSet)
                .in(ShipmentApplyItem::getMaterialCode, matCodeSet)
                .in(ShipmentApplyItem::getDeliverypointNo, deliverypointNoSet)
                .eq(ShipmentApplyItem::getApplyStatus,1)
                .eq(ShipmentApplyItem::getCheckStatus,0)
                .list();
        BigDecimal allOccupyQty = allOccupyList.stream().map(ShipmentApplyItem::getSendQty).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ytOccupyQty = allOccupyList.stream().filter(i->ytDeliverypointNo.equals(i.getDeliverypointNo())).map(ShipmentApplyItem::getSendQty).reduce(BigDecimal.ZERO, BigDecimal::add);

        boardResult.setAllOccupyQty(allOccupyQty);
        boardResult.setYtOccupyQty(ytOccupyQty);
        boardResult.setSupplierOccupyQty(allOccupyQty.subtract(ytOccupyQty));

        //2.3实际可调拨数量
        boardResult.setAllCanTransferQty(allErpCanTransferQty.subtract(allOccupyQty));
        boardResult.setYtCanTransferQty(ytErpCanTransferQty.subtract(ytOccupyQty));
        boardResult.setSupplierCanTransferQty(boardResult.getSupplierErpCanTransferQty().subtract(boardResult.getSupplierOccupyQty()));

        // 3- 国内待发 调拨单状态小于6
        BigDecimal toSendSuppilerQty = trackingTransferService.toSendQty(teamList, matCodeList,suppilerDeliverypointNo);
        BigDecimal toSendYtQty = trackingTransferService.toSendQty(teamList, matCodeList,ytDeliverypointNo);

        toSendSuppilerQty = ObjectUtil.isEmpty(toSendSuppilerQty) ? BigDecimal.ZERO : toSendSuppilerQty;
        toSendYtQty = ObjectUtil.isEmpty(toSendYtQty) ? BigDecimal.ZERO : toSendYtQty;
        boardResult.setAllToSendQty(toSendYtQty.add(toSendSuppilerQty));
        boardResult.setYtToSendQty(toSendYtQty);
        boardResult.setSupplierToSendQty(toSendSuppilerQty);
        // 4- 来货
        Map<String,Object> allMap = trackingTransferService.transit(teamList, matCodeList,areaList,asinList,Boolean.FALSE);
        Map<String,Object> transitMap = trackingTransferService.transit(teamList, matCodeList,areaList,asinList,Boolean.TRUE);

        BigDecimal transitQty = ObjectUtil.isNotEmpty(transitMap) && transitMap.get("QTY")!= null ? (BigDecimal) transitMap.get("QTY") : BigDecimal.ZERO;
        String preArriveDate = ObjectUtil.isNotEmpty(transitMap) && transitMap.get("PREARRIVEDATE")!= null ? (String) transitMap.get("PREARRIVEDATE") : "";
        BigDecimal allQty = ObjectUtil.isNotEmpty(allMap) &&   allMap.get("QTY")!= null ? (BigDecimal) allMap.get("QTY") : BigDecimal.ZERO;
        BigDecimal receiveQty =  ObjectUtil.isNotEmpty(allMap) && allMap.get("RECEIVE_QTY")!= null ? (BigDecimal) allMap.get("RECEIVE_QTY") : BigDecimal.ZERO;

        BigDecimal toShelveQty = trackingTransferService.toShelveQty(teamList, matCodeList,areaList,asinList);
        toShelveQty = allQty.subtract(transitQty).subtract(receiveQty);
        //存在比多发的业务场景,即上架数据大于来货数据
        toShelveQty =toShelveQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : toShelveQty;
        boardResult.setTransitQty(transitQty);
        boardResult.setToShelveQty(toShelveQty);
        boardResult.setArrivalQty(transitQty.add(toShelveQty));
        boardResult.setPreArriveDate(preArriveDate);

        //[物流参考]
        LogisticsDayParam logDayParam = new LogisticsDayParam();
        if (ObjectUtil.isNotEmpty(param.getAreaList())) {
            logDayParam.setAreaList(param.getAreaList());
        }
        boardResult.setLogisticsInfoList(logDayService.findPageBySpec(logDayParam).getRows());


        //[来货情况]

        boardResult.setArrivalList(trackingTransferService.arrivalList(teamList, matCodeList, param.getAreaList(), param.getAsinList()));

        return ResponseData.success(boardResult);
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

}
