package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentTracking;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.TrackingTransfer;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.ShipmentTrackingMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.TrackingTransferMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ErpTeamAvailableQytResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.OccupyQytResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentBoardArrivalResult;
import com.tadpole.cloud.operationManagement.modular.shipment.service.ITrackingTransferService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
    * 追踪明细项-调拨单 服务实现类
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Service
@Slf4j
public class TrackingTransferServiceImpl extends ServiceImpl<TrackingTransferMapper, TrackingTransfer> implements ITrackingTransferService {

    @Resource
    private TrackingTransferMapper mapper;

    @Resource
    private ShipmentTrackingMapper shipmentTrackingMapper;


    @Resource
    private RedisTemplate redisTemplate;




    @Override
    @DataSource(name = "stocking")
    public OccupyQytResult queryOccupyQty(String applyBatchNo,String team, String  materialCode,String deliverypointNo)  {
        Set<String> queryMergeFiels = new TreeSet<>();
        queryMergeFiels.add(team + materialCode + deliverypointNo);

        List<String> applyBatchNoList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(applyBatchNo)) {
            applyBatchNoList =Arrays.asList(applyBatchNo);
        }

        List<OccupyQytResult> qytResultList = mapper.batchQueryApplyOccupyQty(queryMergeFiels,applyBatchNoList);

        if (ObjectUtil.isNotEmpty(qytResultList)) {
            return qytResultList.get(0);
        }
        return null;
    }

    @Override
    @DataSource(name = "stocking")
    public List<OccupyQytResult> batchQueryOccupyQty(Set<String> queryMergeFiels,List<String> applyBatchNoList) {
//        return mapper.batchQueryOccupyQty(queryMergeFiels);
        return mapper.batchQueryApplyOccupyQty(queryMergeFiels,applyBatchNoList);
    }


    /**
     * 合并字段拼接查询erp系统team下的可用物料数量
     *
     * @param mergeFieldList team+物料合并成字符串的list
     * @return
     */
    @Override
    @DataSource(name = "erpcloud")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public List<ErpTeamAvailableQytResult> erpTeamAvailableQty(Set<String> mergeFieldList) {
        return this.mapper.erpTeamAvailableQty(mergeFieldList);
    }


    /**
     * erp系统team下的可用物料数量和即时库存取最小值
     *
     * @param mergeFieldList team+物料+发货点 合并成字符串的list
     * @return
     */
    @Override
    @DataSource(name = "erpcloud")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public List<ErpTeamAvailableQytResult> erpAvailableQty(Set<String> mergeFieldList) {
        Set<String> teamSet = new HashSet<>();
        Set<String> matSet = new HashSet<>();
        Set<String> deliverypointNoSet = new HashSet<>();

        for (String mergeField : mergeFieldList) {
            String[] splitStr = StringUtils.split(mergeField, '@');
            if (splitStr.length>2) {
                teamSet.add(splitStr[0]);
                matSet.add(splitStr[1]);
                deliverypointNoSet.add(splitStr[2]);
            }
        }
        return this.mapper.erpAvailableQty(teamSet,matSet,deliverypointNoSet,mergeFieldList);
    }

    /**
     * erp系统team下的可用物料数量和即时库存取最小值
     *
     * @param mergeFieldList team+物料+发货点 合并成字符串的list
     * @return
     */
    @Override
    @DataSource(name = "erpcloud")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public List<ErpTeamAvailableQytResult> erpAvailableQty(Set<String> teamSet,Set<String> matSet,Set<String> deliverypointNoSet,Set<String> mergeFieldList) {
        return this.mapper.erpAvailableQty2(teamSet,matSet,deliverypointNoSet,mergeFieldList);
    }
    @Override
    @DataSource(name = "erpcloud")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public List<ErpTeamAvailableQytResult> erpAvailableAllQty(List<String> team,List<String> matCode){
        return this.mapper.erpAvailableAllQty(team,matCode);
    }

    @Override
    @DataSource(name = "erpcloud")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public BigDecimal toCheckQty(List<String> matCodeList){
        return this.mapper.toCheckQty(matCodeList);
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public BigDecimal toSendQty(List<String> team,List<String> matCode,String deliverypointNo){
        return this.mapper.toSendQty(team,matCode,deliverypointNo);
    }


    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public Map<String, String> transit(List<String> teamList, List<String> matCodeList,  List<String> areaList, List<String> asinList,Boolean isTransit){
        return this.mapper.transit(teamList,matCodeList,areaList,asinList,isTransit);
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public BigDecimal toShelveQty(List<String> teamList,List<String> matCodeList, List<String> areaList, List<String> asinList){
        return this.mapper.toShelveQty(teamList,matCodeList,areaList,asinList);
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public List<ShipmentBoardArrivalResult> arrivalList(List<String> team, List<String> matCode , List<String> area, List<String> asin){
        //area非空字符串过滤
        if (ObjectUtil.isNotEmpty(area)) {
            area = area.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        }
        //非空字符串过滤
        if (ObjectUtil.isNotEmpty(asin)) {
            asin = asin.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        }
        return this.mapper.arrivalList(team,matCode,area,asin);
    }


    @Override
    @DataSource(name = "stocking")
    public void updateTransferStatus(List<String> billNoList) {
         mapper.updateTransferStatus(billNoList);
         //todo:更新已有物流单号的调拨单 不需要继续追踪了
    }

    @Override
    @DataSource(name = "stocking")
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW )
    public ResponseData transferRevoke(String applyBatchNo, String billNo, LoginUser currentUser, String revokeReason) {
        try {
            //同步改状态 1.撤销9 2.无需追踪1
            LambdaUpdateWrapper<TrackingTransfer> trackingTransferUpdateWrapper = new LambdaUpdateWrapper<>();
            trackingTransferUpdateWrapper.eq(TrackingTransfer::getBillNo, billNo);
            trackingTransferUpdateWrapper.set(TrackingTransfer::getApplyTrackStatus, 9);
            trackingTransferUpdateWrapper.set(TrackingTransfer::getNeedTrack, 1);
            trackingTransferUpdateWrapper.set(TrackingTransfer::getRevokeReason, revokeReason);
            trackingTransferUpdateWrapper.set(TrackingTransfer::getRevokePerson, currentUser.getName());
            trackingTransferUpdateWrapper.set(TrackingTransfer::getRevokePersonNo, currentUser.getAccount());
            trackingTransferUpdateWrapper.set(TrackingTransfer::getUpdatedTime, new Date());
            trackingTransferUpdateWrapper.set(TrackingTransfer::getRevokeDate, new Date());

            int count =this.baseMapper.update(null,trackingTransferUpdateWrapper);
            if (count>0) {
                log.info("调拨单【{}】撤销成功【{}】,时间【{}】",billNo,"已经更改调拨单状态为9",new Date());

                if(new LambdaQueryChainWrapper<>(mapper)
                        .eq(TrackingTransfer::getApplyBatchNo, applyBatchNo)
                        .list().stream()
                        .allMatch(i -> i.getApplyTrackStatus() == 9 || i.getApplyTrackStatus() == 8)) {

                    new LambdaUpdateChainWrapper<>(shipmentTrackingMapper).eq(ShipmentTracking::getApplyBatchNo, applyBatchNo)
                            .set(ShipmentTracking::getTrackingStatus, "已完结")
                            .set(ShipmentTracking::getUpdatedBy, currentUser.getName())
                            .set(ShipmentTracking::getUpdatedTime, new Date())
                            .set(ShipmentTracking::getNeedTrack, 1)
                            .update();
                    log.info("调拨单【{}】撤销成功,整个批次【{}】状态已完结，时间【{}】",billNo,applyBatchNo,new Date());
                }
                return ResponseData.success();
            }
            return ResponseData.success();
        } catch (Exception e) {
            log.error("调拨单【{}】撤销异常,时间【{}】,异常信息【{}】",billNo,new Date(), JSON.toJSONString(e));
            return ResponseData.error("调拨单撤销异常:"+e.getMessage());
        }
    }
}
