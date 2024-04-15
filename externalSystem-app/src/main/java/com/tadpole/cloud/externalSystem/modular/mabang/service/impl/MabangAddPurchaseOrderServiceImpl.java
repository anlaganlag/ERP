package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageQuery;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.*;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangAddPurchaseOrderMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.SaleOutOrderItemK3Mapper;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.SaleOutOrderK3Mapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangAddPurchaseOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.*;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3TransferMabangItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAddPurchaseOrder2Result;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAddPurchaseOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3TransferMabangItemService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangAddPurchaseOrderService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 马帮新增采购订单 服务实现类
 * </p>
 *
 * @author lsy
 * @since 2022-06-15
 */
@Slf4j
@Service
public class MabangAddPurchaseOrderServiceImpl extends ServiceImpl<MabangAddPurchaseOrderMapper, MabangAddPurchaseOrder> implements IMabangAddPurchaseOrderService {

    @Resource
    private MabangAddPurchaseOrderMapper mapper;

    @Resource
    IMabangRequstService mabangRequstService;

    @Resource
    IK3TransferMabangItemService transferMabangItemService;

    @Resource
    SaleOutOrderK3Mapper saleOutOrderK3Mapper;

    @Resource
    SaleOutOrderItemK3Mapper outOrderItemK3Mapper;

    @DataSource(name = "external")
    @Override
    public PageResult<MabangAddPurchaseOrderResult> list(MabangAddPurchaseOrderParam param) {
        Page pageContext = getPageContext();
        IPage<MabangAddPurchaseOrderResult> page = mapper.list(pageContext, param);
        return new PageResult<>(page);
    }


    @DataSource(name = "external")
    @Override
    public PageResult<K3TransferMabangItemResult> display(String billNo, String groupId) {
        Page pageContext = getPageContext();
        IPage<K3TransferMabangItemResult> page = mapper.display(pageContext, billNo, groupId);
        return new PageResult<>(page);
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(String summaryId, List<K3TransferItem> transferItemList) {


        boolean haveException = false;//是否异常
        String exceptionMsg = "";//是否异常


        //马帮新增采购订单记录集合
        List<MabangAddPurchaseOrder> purchaseOrderList = new ArrayList<>();


        //拆单，新增采购订单 可能会有多个入仓，按照不同入仓分别新增采购订单
        Map<String, List<K3TransferItem>> listMap = transferItemList.stream().collect(Collectors.groupingBy(K3TransferItem::getDeststockCode));
        int splitTotal = listMap.size();
        int splitNum = 1;

        for (Map.Entry<String, List<K3TransferItem>> entry : listMap.entrySet()) {

            List<K3TransferMabangItem> transferMabangItemList = new ArrayList<>();

            String deststockCode = entry.getKey();
            List<K3TransferItem> itemList = entry.getValue();

            PurchaseAddParm purchaseOrder = this.createPurchaseOrder(itemList);
            MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-do-add-purchase", purchaseOrder);

            MabangAddPurchaseOrder addPurchaseOrder = BeanUtil.copyProperties(purchaseOrder, MabangAddPurchaseOrder.class);
            addPurchaseOrder.setOrderBillNo(purchaseOrder.getOrderBillNO());//字段大小写不一样

            BeanUtil.copyProperties(itemList.get(0), addPurchaseOrder);
            Date date = new Date();

            addPurchaseOrder.setCreateTime(date);
            addPurchaseOrder.setSyncTime(date);
            addPurchaseOrder.setSyncStatus(MabangConstant.SYNC_FAIL);
            addPurchaseOrder.setId(addPurchaseOrder.getOrderBillNo());//数据id和传给马帮的自定义编号保持一直
            addPurchaseOrder.setSplitNum(String.valueOf(splitNum));
            addPurchaseOrder.setSplitTotal(String.valueOf(splitTotal));
            addPurchaseOrder.setGroupId("待同步成功后回写");
            addPurchaseOrder.setSyncResultMsg("MCMS数据已经生成等待同步");
            addPurchaseOrder.setSummaryId(summaryId);

            purchaseOrderList.add(addPurchaseOrder);

            //生成K3调拨单明细项已同步记录

            for (K3TransferItem item : itemList) {
                K3TransferMabangItem k3TransferMabangItem = BeanUtil.copyProperties(item, K3TransferMabangItem.class);
                k3TransferMabangItem.setSplitNum(String.valueOf(splitNum));
                k3TransferMabangItem.setTransferDirection(MabangConstant.TRANSFER_DIRECTION_IN);
                k3TransferMabangItem.setGroupId("待同步成功后回写");
                k3TransferMabangItem.setPurchaseOrderId(addPurchaseOrder.getId());
                k3TransferMabangItem.setSyncTime(date);
                k3TransferMabangItem.setSyncStatus(MabangConstant.SYNC_FAIL);
                k3TransferMabangItem.setCreateTime(date);
                k3TransferMabangItem.setSummaryId(summaryId);
                k3TransferMabangItem.setId(IdWorker.getIdStr());
                k3TransferMabangItem.setSyncResultMsg("MCMS数据已经生成等待同步");
                transferMabangItemList.add(k3TransferMabangItem);
            }

            //先保存数据，在调用接口
            this.save(addPurchaseOrder);
            transferMabangItemService.saveBatch(transferMabangItemList);

            Date updateTime = new Date();


            LambdaUpdateWrapper<K3TransferMabangItem> itemUpdateWrapper = new LambdaUpdateWrapper<>();
            itemUpdateWrapper.eq(K3TransferMabangItem::getPurchaseOrderId, addPurchaseOrder.getId());
            itemUpdateWrapper.set(K3TransferMabangItem::getUpdateTime, updateTime);
            itemUpdateWrapper.set(K3TransferMabangItem::getSyncTime, updateTime);

            try {
                MabangResult result = mabangRequstService.purchaseOrderAdd(mabangHeadParm);

                addPurchaseOrder.setSyncResultMsg(JSONUtil.toJsonStr(result));
                addPurchaseOrder.setUpdateTime(updateTime);
                addPurchaseOrder.setSyncTime(updateTime);


                itemUpdateWrapper.set(K3TransferMabangItem::getSyncResultMsg, JSONUtil.toJsonStr(result));

                //新增采购订单成功
                if (MabangConstant.RESULT_SUCCESS_CODE.equals(String.valueOf(result.getCode()))) {
                    //更新采购订单
                    String groupId = String.valueOf(((Map) result.getData()).get("groupId"));
                    addPurchaseOrder.setGroupId(groupId);
                    addPurchaseOrder.setSyncStatus(MabangConstant.SYNC_SUCCESS);
                    addPurchaseOrder.setSyncSuccessTimes(BigDecimal.ONE);
                    //更新采购订单項
                    itemUpdateWrapper.set(K3TransferMabangItem::getSyncStatus, MabangConstant.SYNC_SUCCESS)
                            .set(K3TransferMabangItem::getSyncSuccessTimes, BigDecimal.ONE)
                            .set(K3TransferMabangItem::getGroupId, groupId);

                } else {//新增采购单失败

                    haveException = true;
                    exceptionMsg = exceptionMsg + "新增采购单调用马帮接口失败返回信息:" + JSONUtil.toJsonStr(result) + "---------";

                    addPurchaseOrder.setSyncFailTimes(BigDecimal.ONE);

                    itemUpdateWrapper.set(K3TransferMabangItem::getSyncFailTimes, BigDecimal.ONE);
                    log.error("新增采购单失败>>summaryId:{}", summaryId);
                }


            } catch (Exception e) {

                addPurchaseOrder.setSyncFailTimes(BigDecimal.ONE);
                addPurchaseOrder.setSyncResultMsg(JSONUtil.toJsonStr(e));

                itemUpdateWrapper.set(K3TransferMabangItem::getSyncFailTimes, BigDecimal.ONE);
                itemUpdateWrapper.set(K3TransferMabangItem::getSyncResultMsg, JSONUtil.toJsonStr(e));

                haveException = true;
                exceptionMsg = exceptionMsg + "新增采购单出现异常:" + JSONUtil.toJsonStr(e) + "---------";
                log.error(JSONUtil.toJsonStr(e));
            }

            transferMabangItemService.getBaseMapper().update(null, itemUpdateWrapper);

            this.updateById(addPurchaseOrder);

            splitNum += 1;
        }
        if (haveException) {
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, exceptionMsg, purchaseOrderList.size());
        }

        return ResponseData.success(purchaseOrderList.size());
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add2(String summaryId, List<K3TransferItem> transferItemList) {

        //马帮新增采购订单记录集合
        List<MabangAddPurchaseOrder> purchaseOrderList = new ArrayList<>();
        List<K3TransferMabangItem> transferMabangItemList = new ArrayList<>();


        //拆单，新增采购订单 可能会有多个入仓，按照不同入仓分别新增采购订单
        Map<String, List<K3TransferItem>> listMap = transferItemList.stream().collect(Collectors.groupingBy(K3TransferItem::getDeststockCode));
        int splitTotal = listMap.size();
        int splitNum = 1;

        for (Map.Entry<String, List<K3TransferItem>> entry : listMap.entrySet()) {
            String deststockCode = entry.getKey();
            List<K3TransferItem> itemList = entry.getValue();

            PurchaseAddParm purchaseOrder = this.createPurchaseOrder(itemList);
            MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-do-add-purchase", purchaseOrder);

            try {
                MabangResult result = mabangRequstService.purchaseOrderAdd(mabangHeadParm);

                //新增采购订单成功
                if (MabangConstant.RESULT_SUCCESS_CODE.equals(String.valueOf(result.getCode()))) {
                    //生成新增采购订单同步记录数
                    String groupId = String.valueOf(((Map) result.getData()).get("groupId"));

                    MabangAddPurchaseOrder addPurchaseOrder = BeanUtil.copyProperties(purchaseOrder, MabangAddPurchaseOrder.class);
                    addPurchaseOrder.setOrderBillNo(purchaseOrder.getOrderBillNO());//字段大小写不一样

                    BeanUtil.copyProperties(itemList.get(0), addPurchaseOrder);
                    Date date = new Date();

                    addPurchaseOrder.setCreateTime(date);
                    addPurchaseOrder.setSyncTime(date);
                    addPurchaseOrder.setSyncStatus(MabangConstant.SYNC_SUCCESS);
                    addPurchaseOrder.setId(addPurchaseOrder.getOrderBillNo());//数据id和传给马帮的自定义编号保持一直
                    addPurchaseOrder.setSplitNum(String.valueOf(splitNum));
                    addPurchaseOrder.setSplitTotal(String.valueOf(splitTotal));
                    addPurchaseOrder.setGroupId(groupId);
                    addPurchaseOrder.setSummaryId(summaryId);

                    purchaseOrderList.add(addPurchaseOrder);

                    //生成K3调拨单明细项已同步记录

                    for (K3TransferItem item : itemList) {

                        K3TransferMabangItem k3TransferMabangItem = BeanUtil.copyProperties(item, K3TransferMabangItem.class);
                        k3TransferMabangItem.setSummaryId("");
                        k3TransferMabangItem.setSplitNum(String.valueOf(splitNum));
                        k3TransferMabangItem.setTransferDirection(MabangConstant.TRANSFER_DIRECTION_IN);
                        k3TransferMabangItem.setGroupId(groupId);
                        k3TransferMabangItem.setPurchaseOrderId(addPurchaseOrder.getOrderBillNo());
                        k3TransferMabangItem.setSyncTime(date);
                        k3TransferMabangItem.setSyncStatus(MabangConstant.SYNC_SUCCESS);
                        k3TransferMabangItem.setCreateTime(date);
                        k3TransferMabangItem.setSummaryId(summaryId);
                        k3TransferMabangItem.setId(IdWorker.getIdStr());
                        k3TransferMabangItem.setSyncResultMsg("success");

                        transferMabangItemList.add(k3TransferMabangItem);
                    }
                } else {//新增采购单失败

                    log.error("新增采购单失败>>summaryId:{}", summaryId);

                }
            } catch (Exception e) {
                log.error(JSONUtil.toJsonStr(e));

            }
            splitNum += 1;
        }

        if (ObjectUtil.isNotEmpty(purchaseOrderList)) {
            this.saveBatch(purchaseOrderList);
        }

        if (ObjectUtil.isNotEmpty(transferMabangItemList)) {
            // todo 是否数据源生效
            transferMabangItemService.saveBatch(transferMabangItemList);
        }

        return purchaseOrderList.size();
    }


    @Override
    public PurchaseAddParm createPurchaseOrder(List<K3TransferItem> itemList) {
        K3TransferItem item = itemList.get(0);

        String idStr = IdWorker.getIdStr();
        ArrayList<PurchaseAddItem> addItemList = new ArrayList<PurchaseAddItem>();

        // 同一个调拨单 ，子项的出仓，入仓，物料编码 都相同  唯独数量不同时  需要合并
        Map<String, List<K3TransferItem>> listMap = itemList.stream().collect(Collectors.groupingBy(K3TransferItem::getMaterialCode));
        for (Map.Entry<String, List<K3TransferItem>> entry : listMap.entrySet()) {

            List<K3TransferItem> items = entry.getValue();
            K3TransferItem t = items.get(0);
            BigDecimal sumQty = items.stream().map(K3TransferItem::getQty).reduce(BigDecimal::add).get();
//            t.setQty(sumQty);

            // 生成采购单子项信息
            PurchaseAddItem purchaseAddItem = PurchaseAddItem.builder()
                    .stockSku(t.getMaterialCode())
                    .purchaseNum(String.valueOf(sumQty.intValue()))
                    .price("1")
                    .remark(String.valueOf(t.getId()))
                    .build();
            addItemList.add(purchaseAddItem);
        }

        PurchaseAddParm purchaseAddParm = PurchaseAddParm.builder()
                .warehouseName(item.getDeststockName())
                .providerName("虚拟供应商")
                .employeeName("虚拟采购员")
                .orderBillNO(idStr)
                .expressMoney("0")
                .estimatedTime("0")//todo:参数读取配置文件
                .expressType("")
                .expressId("")
                .content(item.getBillNo())
                .notCalculate("1")//是否计算采购在途，1不计算 2计算
                .isAutoSubmitCheck("1")//生成的采购单将自动提交采购，1自动 2不自动
                .stockList(JSONUtil.toJsonStr(addItemList))
                .build();
        return purchaseAddParm;
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData anitAudit(K3TransferMabangSummary summary) {

        LambdaQueryWrapper<MabangAddPurchaseOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MabangAddPurchaseOrder::getBillNo, summary.getBillNo());
        wrapper.eq(MabangAddPurchaseOrder::getSummaryId, summary.getId());
        wrapper.eq(MabangAddPurchaseOrder::getSyncStatus, MabangConstant.SYNC_SUCCESS);
        wrapper.eq(MabangAddPurchaseOrder::getAntiAudit, MabangConstant.ANTI_AUDIT_UN);

        List<MabangAddPurchaseOrder> orderList = this.baseMapper.selectList(wrapper);


        if (ObjectUtil.isNotEmpty(orderList)) {

            //查询当前同步到马帮的 新增采购单的状态
            for (MabangAddPurchaseOrder purchaseOrder : orderList) {

                PurchaseGetOrderParm orderParm = PurchaseGetOrderParm.builder().groupId(purchaseOrder.getGroupId()).build();
                MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-get-purchase-list", orderParm);
                MabangResult mabangResult = mabangRequstService.getPurchaseOrderList(mabangHeadParm);
                if (MabangConstant.RESULT_SUCCESS_CODE.equals(mabangResult.getCode())) {
//                    JSONUtil.parse(JSONUtil.parseArray(((Map) mabangResult.getData()).get("data")).get(0)).getByPath("flag")

                    Object data = mabangResult.getData();
                    if (ObjectUtil.isNotNull(data)) {
                        JSONArray jsonArray = JSONUtil.parseArray(((Map) data).get("data"));

                        if (jsonArray.size() > 0) {
//                            "flag":"状态：1、新采购 2、等待一审 3、等待二审 4、采购中 5、签收 6、验货 7、上架 8、部分到货 9、全部到货 10、已完成 11、异常 12、删除",
                            String flag = (String) JSONUtil.parse(jsonArray.get(0)).getByPath("flag");
                            if (!checkFlag(flag)) {// 只要有一个采购订单不允许反审核  整个调拨单都不允许反审核
                                return ResponseData.success(200, "由K3直接调拨单生成的马帮采购订单处于以下其中一个状态：5、签收 6、验货 7、上架 8、部分到货 9、全部到货 10、已完成 11、异常", false);
                            }
                        }
                    }
                }
            }


            //所有采购订单都允许反审核--->取消所有采购订单，作废处理
            List<String> orderIds = new ArrayList<>();

            for (MabangAddPurchaseOrder purchaseOrder : orderList) {

                PurchaseModifParm purchaseModifParm = PurchaseModifParm.builder()
                        .groupId(purchaseOrder.getGroupId())
                        .employeeName("虚拟采购员")
                        .actionType("2")
                        .scrapOrder("1")
                        .build();
                MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-do-change-purchase", purchaseModifParm);
                MabangResult mabangResult = mabangRequstService.purchaseOrderModify(mabangHeadParm);

                if (!mabangResult.getCode().equals(MabangConstant.RESULT_SUCCESS_CODE)) {
                    return ResponseData.success("反审核触发，作废采购订单失败");
                }
                //
                purchaseOrder.setIsDelete(MabangConstant.IS_DELETE_YES);
                purchaseOrder.setAntiAudit(MabangConstant.ANTI_AUDIT_DO);
                purchaseOrder.setUpdateTime(new Date());
                orderIds.add(purchaseOrder.getId());
            }

            //更新之前的同步记录信息
            this.updateBatchById(orderList);

            //更新之前的 k3同步项 信息
            LambdaUpdateWrapper<K3TransferMabangItem> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .in(K3TransferMabangItem::getPurchaseOrderId, orderIds)
                    .set(K3TransferMabangItem::getAntiAudit, MabangConstant.ANTI_AUDIT_DO)
                    .set(K3TransferMabangItem::getUpdateTime, new Date())
                    .set(K3TransferMabangItem::getAntiAuditAction, MabangConstant.ANTI_AUDIT_ACTION_PURCHASE_ORDER_DEL);

            transferMabangItemService.getBaseMapper().update(null, updateWrapper);

            return ResponseData.success(true);
        }

        return ResponseData.success(true);

    }

    private boolean checkFlag(String flag) {
//        "flag":"状态：1、新采购 2、等待一审 3、等待二审 4、采购中 5、签收 6、验货 7、上架 8、部分到货 9、全部到货 10、已完成 11、异常 12、删除",

//        "判断采购单状态的重要字段：
//        a.当采购单状态为1,2,3,4时，可以作废该采购单；
//        b.当采购单状态为5,6,7,8,9,10，不可以作废该采购单；
//        c.当采购单状态为11,12时，表明该采购单已经作废/删除过，可以忽略该采购单接下来的作废操作；
//        d.作废后的采购单状态为12"

        Integer f = Integer.valueOf(flag);

        if (f == 1 || f == 2 || f == 3 || f == 4 || f == 12) {
            return true;
        }
        if (f == 5 || f == 6 || f == 7 || f == 8 || f == 9 || f == 10 || f == 11) {
            return false;
        }
        return false;

    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    @DataSource(name = "external")
    @Override
    public PageResult<MabangAddPurchaseOrder2Result> mergeList(MabangAddPurchaseOrderParam param) {
        Page pageContext = getPageContext();
        IPage<MabangAddPurchaseOrder2Result> page = mapper.mergeList(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "external")
    @Override
    public List<MabangAddPurchaseOrder2Result> exportExcel(MabangAddPurchaseOrderParam param) {
        //设置导出数据为100万；
        PageQuery page = new PageQuery();
        page.setPageSize(Integer.MAX_VALUE);
        page.setPageNo(1);

        Page pageContext = PageFactory.createPage(page);
        Page<MabangAddPurchaseOrder2Result> pageResult = this.baseMapper.mergeList(pageContext, param);
        //返回查询结果
        return pageResult.getRecords();
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void createMabangPurchaseOrder() {

        LambdaQueryWrapper<SaleOutOrderK3> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SaleOutOrderK3::getSyncStatus, "1");
        queryWrapper.in(SaleOutOrderK3::getCreatePurchaseOrder, -1, 0);
        List<SaleOutOrderK3> outOrderK3List = saleOutOrderK3Mapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(outOrderK3List)) {
            return;
        }

        for (SaleOutOrderK3 outOrderK3 : outOrderK3List) {
            LambdaQueryWrapper<SaleOutOrderItemK3> itemQuery = new LambdaQueryWrapper<>();
            itemQuery.eq(SaleOutOrderItemK3::getFBillNo, outOrderK3.getFBillNo());
            itemQuery.isNull(SaleOutOrderItemK3::getGroupId);
            List<SaleOutOrderItemK3> orderItemK3List = outOrderItemK3Mapper.selectList(itemQuery);
            this.createMabangPurchaseOrder(outOrderK3, orderItemK3List);
        }
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData createMabangPurchaseOrder(SaleOutOrderK3 saleOutOrderK3, List<SaleOutOrderItemK3> itemList) {


        //是否之前同步时出现异常+马帮已经存在，
        if (!this.checkSyncException(saleOutOrderK3.getFBillNo())) {
            return ResponseData.error("");
        }

        boolean haveException = false;//是否异常
        String exceptionMsg = "";//是否异常


        int splitTotal = 1;
        int splitNum = 1;
        String summaryId = IdWorker.getIdStr();

        List<K3TransferMabangItem> transferMabangItemList = new ArrayList<>();

        PurchaseAddParm purchaseOrder = this.autoCreatePurchaseOrder(saleOutOrderK3, itemList);
        MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-do-add-purchase", purchaseOrder);

        MabangAddPurchaseOrder addPurchaseOrder = BeanUtil.copyProperties(purchaseOrder, MabangAddPurchaseOrder.class);
        addPurchaseOrder.setOrderBillNo(purchaseOrder.getOrderBillNO());//字段大小写不一样

        BeanUtil.copyProperties(itemList.get(0), addPurchaseOrder);
        Date date = new Date();

        addPurchaseOrder.setCreateTime(date);
        addPurchaseOrder.setSyncTime(date);
        addPurchaseOrder.setSyncStatus(MabangConstant.SYNC_FAIL);
        addPurchaseOrder.setId(addPurchaseOrder.getOrderBillNo());//数据id和传给马帮的自定义编号保持一直
        addPurchaseOrder.setSplitNum(String.valueOf(splitNum));
        addPurchaseOrder.setSplitTotal(String.valueOf(splitTotal));
        addPurchaseOrder.setGroupId("待同步成功后回写");
        addPurchaseOrder.setSyncResultMsg("MCMS数据已经生成等待同步");
        addPurchaseOrder.setSummaryId(summaryId);//todo:
        addPurchaseOrder.setId(summaryId);//todo:
        addPurchaseOrder.setBillNo(saleOutOrderK3.getFBillNo());
        addPurchaseOrder.setApproveDate(saleOutOrderK3.getSyncTime());



        //生成K3调拨单明细项已同步记录

        for (SaleOutOrderItemK3 item : itemList) {
            K3TransferMabangItem k3TransferMabangItem = BeanUtil.copyProperties(item, K3TransferMabangItem.class);
            k3TransferMabangItem.setSplitNum(String.valueOf(splitNum));
            k3TransferMabangItem.setTransferDirection(MabangConstant.TRANSFER_DIRECTION_IN);
            k3TransferMabangItem.setGroupId("待同步成功后回写");
            k3TransferMabangItem.setPurchaseOrderId(addPurchaseOrder.getId());
            k3TransferMabangItem.setSyncTime(date);
            k3TransferMabangItem.setSyncStatus(MabangConstant.SYNC_FAIL);
            k3TransferMabangItem.setCreateTime(date);
            k3TransferMabangItem.setSummaryId(summaryId);
            k3TransferMabangItem.setId(IdWorker.getIdStr());
            k3TransferMabangItem.setEntryId(item.getId());
            k3TransferMabangItem.setSyncResultMsg("MCMS数据已经生成等待同步");
            k3TransferMabangItem.setMaterialCode(item.getFMaterialId());
            k3TransferMabangItem.setMaterialName(item.getFMaterialName());
            k3TransferMabangItem.setQty(BigDecimal.valueOf(Integer.valueOf(item.getFRealQty())));
            k3TransferMabangItem.setDocumentStatus("已审核");
            k3TransferMabangItem.setSrcstockCode(item.getFStockId());
            k3TransferMabangItem.setSrcstockName(item.getWarehouseName());
            k3TransferMabangItem.setDeststockCode("平台发展");
            k3TransferMabangItem.setDeststockName("平台发展");
            k3TransferMabangItem.setBillNo(item.getFBillNo());
            transferMabangItemList.add(k3TransferMabangItem);
        }

        //先保存数据，在调用接口
        this.save(addPurchaseOrder);
        transferMabangItemService.saveBatch(transferMabangItemList);

        Date updateTime = new Date();

        LambdaUpdateWrapper<K3TransferMabangItem> itemUpdateWrapper = new LambdaUpdateWrapper<>();
        itemUpdateWrapper.eq(K3TransferMabangItem::getPurchaseOrderId, addPurchaseOrder.getId());
        itemUpdateWrapper.set(K3TransferMabangItem::getUpdateTime, updateTime);
        itemUpdateWrapper.set(K3TransferMabangItem::getSyncTime, updateTime);

        try {
            MabangResult result = mabangRequstService.purchaseOrderAdd(mabangHeadParm);

            addPurchaseOrder.setSyncResultMsg(JSONUtil.toJsonStr(result));
            addPurchaseOrder.setUpdateTime(updateTime);
            addPurchaseOrder.setSyncTime(updateTime);
            itemUpdateWrapper.set(K3TransferMabangItem::getSyncResultMsg, JSONUtil.toJsonStr(result));

            //新增采购订单成功
            if (MabangConstant.RESULT_SUCCESS_CODE.equals(String.valueOf(result.getCode()))) {
                //更新采购订单
                String groupId = String.valueOf(((Map) result.getData()).get("groupId"));
                addPurchaseOrder.setGroupId(groupId);
                addPurchaseOrder.setSyncStatus(MabangConstant.SYNC_SUCCESS);
                addPurchaseOrder.setSyncSuccessTimes(BigDecimal.ONE);
                //更新采购订单項
                itemUpdateWrapper.set(K3TransferMabangItem::getSyncStatus, MabangConstant.SYNC_SUCCESS)
                        .set(K3TransferMabangItem::getSyncSuccessTimes, BigDecimal.ONE)
                        .set(K3TransferMabangItem::getGroupId, groupId);

                //更新自动创建的K3销售出库单  在马帮erp同步新增采购单成功
                List<BigDecimal> itemIdList = itemList.stream().map(i -> i.getId()).collect(Collectors.toList());
                this.updateAutoCreateK3OutOrderInfo(saleOutOrderK3.getFBillNo(),"1",groupId,itemIdList);

            } else {//新增采购单失败
                haveException = true;
                exceptionMsg = exceptionMsg + "新增采购单调用马帮接口失败返回信息:" + JSONUtil.toJsonStr(result) + "---------";

                addPurchaseOrder.setSyncFailTimes(BigDecimal.ONE);

                itemUpdateWrapper.set(K3TransferMabangItem::getSyncFailTimes, BigDecimal.ONE);
                log.error("新增采购单失败>>summaryId:{}", summaryId);

                //更新自动创建的K3销售出库单  在马帮erp同步新增采购单成功
                saleOutOrderK3.setCreatePurchaseOrder(BigDecimal.ZERO);
                saleOutOrderK3Mapper.updateById(saleOutOrderK3);
            }


        } catch (Exception e) {
            haveException = true;
            exceptionMsg = exceptionMsg + "新增采购单出现异常:" + JSONUtil.toJsonStr(e) + "---------";
            log.error(JSONUtil.toJsonStr(e));

            addPurchaseOrder.setSyncFailTimes(BigDecimal.ONE);
            addPurchaseOrder.setSyncResultMsg(JSONUtil.toJsonStr(e));

            itemUpdateWrapper.set(K3TransferMabangItem::getSyncFailTimes, BigDecimal.ONE);
            itemUpdateWrapper.set(K3TransferMabangItem::getSyncResultMsg, JSONUtil.toJsonStr(e));

            saleOutOrderK3.setCreatePurchaseOrder(BigDecimal.ZERO);
            saleOutOrderK3Mapper.updateById(saleOutOrderK3);
        }

        transferMabangItemService.getBaseMapper().update(null, itemUpdateWrapper);

        this.updateById(addPurchaseOrder);

        if (haveException) {
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, exceptionMsg, 1);
        }

        return ResponseData.success();
    }

    /**
     * 更新自动生成的k3销售出库信息
     * @param billNo
     * @param syncStatu
     * @param groupId
     * @param itemIdList
     */
    private void updateAutoCreateK3OutOrderInfo(String billNo,String syncStatu, String groupId,List<BigDecimal> itemIdList) {

        LambdaUpdateWrapper<SaleOutOrderK3> orderUpdateWrapper = new LambdaUpdateWrapper<>();
        orderUpdateWrapper.set(SaleOutOrderK3::getCreatePurchaseOrder,syncStatu);
        orderUpdateWrapper.eq(SaleOutOrderK3::getFBillNo,billNo);
        saleOutOrderK3Mapper.update(null, orderUpdateWrapper);

        LambdaUpdateWrapper<SaleOutOrderItemK3> itemK3UpdateWrapper = new LambdaUpdateWrapper<>();
        itemK3UpdateWrapper.set(SaleOutOrderItemK3::getGroupId, groupId);
        itemK3UpdateWrapper.in(SaleOutOrderItemK3::getId, itemIdList);
        outOrderItemK3Mapper.update(null, itemK3UpdateWrapper);
    }

    @Override
    @DataSource(name = "external")
    public PurchaseAddParm autoCreatePurchaseOrder(SaleOutOrderK3 saleOutOrderK3, List<SaleOutOrderItemK3> itemList) {
        SaleOutOrderItemK3 item = itemList.get(0);

        String idStr = IdWorker.getIdStr();
        ArrayList<PurchaseAddItem> addItemList = new ArrayList<PurchaseAddItem>();
        for (SaleOutOrderItemK3 itemK3 : itemList) {

            String priceStr = itemK3.getSalePrice().toPlainString();

            // 生成采购单子项信息
            PurchaseAddItem purchaseAddItem = PurchaseAddItem.builder()
                    .stockSku(itemK3.getFMaterialId())
                    .purchaseNum(itemK3.getFRealQty())
                    .price(priceStr)
                    .remark(String.valueOf(itemK3.getId()))
                    .build();
            addItemList.add(purchaseAddItem);
        }

        PurchaseAddParm purchaseAddParm = PurchaseAddParm.builder()
                .warehouseName(item.getWarehouseName())
                .providerName("采购中心")
                .employeeName("虚拟采购员")
                .orderBillNO(idStr)
                .expressMoney("0")
                .estimatedTime("0")//todo:参数读取配置文件
                .expressType("")
                .expressId("")
                .content(saleOutOrderK3.getFBillNo())
                .notCalculate("1")//是否计算采购在途，1不计算 2计算
                .isAutoSubmitCheck("1")//生成的采购单将自动提交采购，1自动 2不自动
                .stockList(JSONUtil.toJsonStr(addItemList))
                .build();
        return purchaseAddParm;
    }

    @Override
    @DataSource(name = "external")
    public boolean checkSyncException(String fBillNo) {
        LambdaQueryWrapper<MabangAddPurchaseOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MabangAddPurchaseOrder::getBillNo, fBillNo);
        queryWrapper.eq(MabangAddPurchaseOrder::getSyncStatus, "0");
        List<MabangAddPurchaseOrder> orderList = this.list(queryWrapper);
        if (ObjectUtil.isEmpty(orderList)) {//没有同步失败的记录,继续执行
            return true;
        }
        MabangAddPurchaseOrder purchaseOrder = orderList.get(0);
        String id = purchaseOrder.getId();

        PurchaseGetOrderParm purchaseGetOrderParm = new PurchaseGetOrderParm();
        purchaseGetOrderParm.setOrderBillNO(id);

        MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-get-purchase-list", purchaseGetOrderParm);
        MabangResult mabangResult = mabangRequstService.getPurchaseOrderList(mabangHeadParm);

        if (mabangResult.getCode().equals(MabangConstant.RESULT_SUCCESS_CODE)) {
            JSONObject jsonObject = JSONUtil.parseObj(mabangResult.getData());
            Integer pageCount = jsonObject.get("pageCount", Integer.class);
            if (pageCount > 0) {
                //马帮已经成功了，MCMS不需要在新增采购单了,直接更新之前的 新增采购单记录
                purchaseOrder.setSyncStatus("1");
                purchaseOrder.setSyncResultMsg("第一次调用马帮新增采购单接口异常，然后根据MCMS的推送记录ID 查询马帮erp已经新增采购单成功，更新同步状态");
                purchaseOrder.setSyncTime(new Date());
                purchaseOrder.setUpdateTime(new Date());
                this.updateById(purchaseOrder);
                // k3自动创建的销售出库单 更新

                LambdaUpdateWrapper<SaleOutOrderK3> orderUpdateWrapper = new LambdaUpdateWrapper<>();
                orderUpdateWrapper.set(SaleOutOrderK3::getCreatePurchaseOrder,1);
                orderUpdateWrapper.eq(SaleOutOrderK3::getFBillNo,fBillNo);
                saleOutOrderK3Mapper.update(null, orderUpdateWrapper);

                return false;
            } else {
                // 马帮接收失败，删除之前的推送记录，,继续执行

                //查询失败没有找到或者异常
                log.info("调用马帮erp接口出现异常的数据，后期查询仍然失败-查询--失败[{}]", fBillNo);
                Date syncTime = ObjectUtil.isNull(purchaseOrder.getSyncTime()) ? purchaseOrder.getCreateTime() : purchaseOrder.getSyncTime();
                LocalDate syncDate = syncTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (syncDate.plusDays(3).isBefore(LocalDate.now())) {
                    //异常的数据超过3天自动清楚
                    LambdaQueryWrapper<K3TransferMabangItem> delWrapper = new LambdaQueryWrapper<>();
                    delWrapper.eq(K3TransferMabangItem::getBillNo, fBillNo);

                    transferMabangItemService.remove(delWrapper);
                    this.removeById(purchaseOrder);
                    return true;
                }

            }
        }

        return false;
    }
}
