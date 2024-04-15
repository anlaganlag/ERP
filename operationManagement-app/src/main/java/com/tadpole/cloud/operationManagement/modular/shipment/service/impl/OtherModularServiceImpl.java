package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tadpole.cloud.operationManagement.modular.shipment.consumer.ErpWarehouseCodeConsumer;
import com.tadpole.cloud.operationManagement.modular.shipment.consumer.K3CloudWebApiConsumer;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentApplyItem;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.TrackingTransfer;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.ShipmentTrackingMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.SupplementaryTransferParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.VerifParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.*;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IOtherModularService;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentApplyItemService;
import com.tadpole.cloud.operationManagement.modular.shipment.service.ITrackingTransferService;
import com.tadpole.cloud.operationManagement.modular.shipment.utils.GeneratorShipmentNoUtil;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.SyncToErpConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OtherModularServiceImpl implements IOtherModularService {
    @Resource
    ErpWarehouseCodeConsumer erpWarehouseCodeConsumer;

    @Resource
    SyncToErpConsumer syncToErpUtil;

    @Resource
    IShipmentApplyItemService shipmentApplyItemService;

    @Resource
    K3CloudWebApiConsumer k3CloudWebApiConsumer;

    @Resource
    ITrackingTransferService trackingTransferService;

    @Resource
    ShipmentTrackingMapper shipmentTrackingMapper;

    @Resource
    GeneratorShipmentNoUtil generatorShipmentNoUtil;

    private static Integer BATCH_SIZE=500;


    /**
     * 获取仓库编码
     *
     * @param warehouseSet
     * @return
     */
    @DataSource(name = "warehouse")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Map<String, String> getWarehouseCode(Set<String> warehouseSet) {
        HashMap<String, String> shopWarehouseCodeMap = new HashMap<>();
        for (String warehouseName : warehouseSet) {
            ResponseData organizationCodeResult = erpWarehouseCodeConsumer.getOrganizationCodeByWarehouse(warehouseName);
            if (ObjectUtil.isNotNull(organizationCodeResult) && organizationCodeResult.getSuccess()) {
                shopWarehouseCodeMap.put(warehouseName, String.valueOf(organizationCodeResult.getData()));
            }
        }
        return shopWarehouseCodeMap;
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> createTransfer(List<String> batchNoList,VerifParam verifParam, LoginUser currentUser) {
        LambdaQueryWrapper<ShipmentApplyItem> wrapper = new LambdaQueryWrapper<ShipmentApplyItem>();
        wrapper.in(ShipmentApplyItem::getApplyBatchNo, batchNoList);
        wrapper.eq(ShipmentApplyItem::getApplyStatus, 1);
        wrapper.eq(ShipmentApplyItem::getCheckStatus, 0);
        wrapper.ne(ShipmentApplyItem::getSyncStatus, 1);
        List<ShipmentApplyItem> applyItemList = shipmentApplyItemService.list(wrapper);
        if (ObjectUtil.isEmpty(applyItemList)) {
            log.info("根据批次号【{}】查找未能找到需要创建调拨单的申请项",JSONUtil.toJsonStr(batchNoList));
            return null;
        }
        Map<String, List<ShipmentApplyItem>> itemGroupByBatchNoMap
                = applyItemList.stream().collect(Collectors.groupingBy(ShipmentApplyItem::getApplyBatchNo));

        //需要删除的调拨单号
        List<String> syncDelBillNo = new ArrayList<>();
        //同步失败的批次号
        List<String> failBatchNo = new ArrayList<>();
        //同步成功的批次号
        List<String> successBatchNo = new ArrayList<>();

        //创建追踪调拨单明细项
        List<ShipmentApplyItem> needCreateTranferList = new ArrayList<>();

        List<ShipmentApplyItem> updateItemList = new ArrayList<>();

        for (Map.Entry<String, List<ShipmentApplyItem>> entry : itemGroupByBatchNoMap.entrySet()) {
            String batchNo = entry.getKey();
            List<ShipmentApplyItem> itemList = entry.getValue();
            log.info("正在处理发货申请批次号【{}】",batchNo);

            //同批次
            List<String> syncSuccessBillNo = new ArrayList<>();
            for (ShipmentApplyItem item : itemList) {

                item.setCheckReason(verifParam.getReson());
                item.setCheckPerson(currentUser.getName());
                item.setCheckPersonNo(currentUser.getAccount());

                try {
                    Map<String, Object> mapParm = BeanUtil.beanToMap(item);
//                    String syncResult = syncToErpUtil.createTransferMap(mapParm);
                    JSONObject syncResultJson = syncToErpUtil.createTransferMapJson(mapParm);
                    String syncResult = syncResultJson.toString();
                    item.setSyncTime(new Date());
                    item.setSyncRequestMsg(String.valueOf(mapParm.get("syncRequestMsg")));
                    item.setSyncResultMsg(syncResult);


                    //<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<string xmlns=\"http://tempuri.org/\">{\"result\":true,\"msg\":\"保存成功\",\"BillNo\":\"MC-FH-202302130007\"}</string>
                    if (ObjectUtil.isNotEmpty(syncResult) && syncResult.contains("保存成功")) {
                        item.setCheckStatus(1);
                        item.setSyncStatus(1);
                        syncSuccessBillNo.add(item.getBillNo());
                        updateItemList.add(item);
                    } else {
                        //同批次有失败的调拨单，则需要删除之前保存成功的
                        syncDelBillNo.addAll(syncSuccessBillNo);
                        item.setSyncStatus(2);
                        failBatchNo.add(item.getApplyBatchNo());
                        updateItemList.add(item);
                        break;
                    }

                    //返回结果处理
                } catch (Exception e) {
                    //同批次有失败的调拨单，则需要删除之前保存成功的
                    syncDelBillNo.addAll(syncSuccessBillNo);
                    item.setSyncStatus(2);
                    item.setSyncResultMsg(JSONUtil.toJsonStr(e));
                    failBatchNo.add(item.getBatchNo());
                    updateItemList.add(item);
                    break;
                }
            }

            if (itemList.size() == syncSuccessBillNo.size()) {
                successBatchNo.add(batchNo);
                needCreateTranferList.addAll(itemList);
            }
        }


        if (ObjectUtil.isNotEmpty(needCreateTranferList)) {

            List<TrackingTransfer> transferList = new ArrayList<>();

            for (ShipmentApplyItem applyItem : needCreateTranferList) {
                TrackingTransfer transfer = new TrackingTransfer();
                BeanUtil.copyProperties(applyItem, transfer);
                transfer.setShipmentId(applyItem.getFbaNo());
                transfer.setApplySendQty(applyItem.getSendQty());
                transfer.setApplyTrackStatus(3);
                transfer.setCheckDate(new Date());
                transfer.setCheckPerson(currentUser.getName());
                transfer.setCheckPersonNo(currentUser.getAccount());
                transfer.setCheckReason(verifParam.getReson());
                transfer.setCreatedTime(new Date());
                transfer.setSyncTime(new Date());
                transfer.setUpdatedTime(new Date());

                transferList.add(transfer);
            }

            trackingTransferService.saveBatch(transferList);
        }

        shipmentApplyItemService.updateBatchById(updateItemList);


        if (ObjectUtil.isNotNull(failBatchNo) && ObjectUtil.isNotEmpty(syncDelBillNo)) {
//            同批次有失败的调拨单，则需要删除之前保存成功的
            for (String billNo : syncDelBillNo) {
                try {
                    k3CloudWebApiConsumer.delTranferByBillNo(billNo);
                } catch (Exception e) {
                    log.info("同批次有失败的调拨单，则需要删除之前保存成功的调拨单{}异常：{}",billNo,JSONUtil.toJsonStr(e));

                }
                log.info("同批次有失败的调拨单，则需要删除之前保存成功的调拨单：{}",billNo);
            }
            //删除了调拨单号，需要重新设置新的调拨单号，下次同步才不会冲突

            List<ShipmentApplyItem> needUpdateBillNoItem = new ArrayList<>();
            for (String batchNo : failBatchNo) {

                List<ShipmentApplyItem> itemList = updateItemList.stream().filter(i->i.getApplyBatchNo().equals(batchNo)).collect(Collectors.toList());
                if (ObjectUtil.isNotEmpty(itemList)) {
                    for (ShipmentApplyItem item : itemList) {
//                        String billNo = generatorShipmentNoUtil.getBillNo(item.getUnwType(), item.getOrgCode());
                        if (item.getSyncStatus() ==1) {
                            item.setSyncResultMsg("该申请项同步k3创建调拨单【成功】，但是同一批次的调拨单有失败的，因此调用了接口删除同步成功调拨单"+item.getBillNo());
                        } else if (item.getSyncStatus() ==2) {
                           // item.setSyncResultMsg("改申请项同步k3创建调拨单【失败】，但是同一批次的调拨单有失败的，因此调用了接口删除同步成功调拨"+item.getBillNo()+"新的调拨单号"+billNo);
                        }
                        item.setSyncStatus(0);
                        item.setCheckStatus(0);
//                        item.setBillNo(billNo);
                    }
                    needUpdateBillNoItem.addAll(itemList);
                }

            }
            if (ObjectUtil.isNotEmpty(needUpdateBillNoItem)) {
                shipmentApplyItemService.updateBatchById(needUpdateBillNoItem);
            }

        }


        return successBatchNo;
    }

    @Override
    @DataSource(name = "erpcloud")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<ErpTransferResult> getErpTransfer(Set<String> billNoSet) {
        List<ErpTransferResult> resultList = new ArrayList<>();
        for (List<String> billNoList : CollectionUtil.split(billNoSet, BATCH_SIZE)) {
            resultList.addAll(shipmentTrackingMapper.getErpTransfer(billNoList))   ;
        }
       return resultList;
    }

    @Override
    @DataSource(name = "erpcloud")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<TrackingShippingResult> getErpShipping(Set<String> pickCodeSet) {
        List<TrackingShippingResult> resultList = new ArrayList<>();

        for (List<String> pickList : CollectionUtil.split(pickCodeSet, BATCH_SIZE)) {
            resultList.addAll(shipmentTrackingMapper.getErpShipping(pickList))   ;
        }
        return resultList;

    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<TrackingLogisticsResult> getEbmsLogistics(Set<String> shippingListCodeSet) {

        List<TrackingLogisticsResult> resultList = new ArrayList<>();

        for (List<String> shippingListCode : CollectionUtil.split(shippingListCodeSet, BATCH_SIZE)) {
            resultList.addAll(shipmentTrackingMapper.getEbmsLogistics(shippingListCode))   ;
        }
        return resultList;
    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<EbmsCompensateResult> getCompensateDataFromEbms(Set<String> shipmentIdSet) {

        List<EbmsCompensateResult> resultList = new ArrayList<>();

        for (List<String> shipmentIdList : CollectionUtil.split(shipmentIdSet, BATCH_SIZE)) {
            resultList.addAll(shipmentTrackingMapper.getCompensateDataFromEbms(shipmentIdList))   ;
        }
        return resultList;
    }

    @Override
    @DataSource(name = "warehouse")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<WareHouseReceiveResult> getReceiveQtyFromWareHouse(Set<String> shipmentIdSet) {

        List<WareHouseReceiveResult> resultList = new ArrayList<>();

        for (List<String> shipmentIdList : CollectionUtil.split(shipmentIdSet, BATCH_SIZE)) {
            resultList.addAll(shipmentTrackingMapper.getReceiveQtyFromWareHouse(shipmentIdList))   ;
        }
        return resultList;

    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<String> getSkuStatus(List<String> mergeFieldList) {

        List<String> resultList = new ArrayList<>();

        for (List<String> mergeFields : CollectionUtil.split(mergeFieldList, BATCH_SIZE)) {
            resultList.addAll(shipmentTrackingMapper.getSkuStatus(mergeFields))   ;
        }
        return resultList;
    }


    @DataSource(name = "stocking")
    @Override
    public List<String> supplementaryTransfer(SupplementaryTransferParam transferParam, LoginUser currentUser) {
        LambdaQueryWrapper<ShipmentApplyItem> wrapper = new LambdaQueryWrapper<ShipmentApplyItem>();
        wrapper.in(ShipmentApplyItem::getApplyBatchNo, transferParam.getApplyBatchNoList());
        wrapper.eq(ShipmentApplyItem::getApplyStatus, 1);
        wrapper.eq(ShipmentApplyItem::getCheckStatus, 0);
        List<ShipmentApplyItem> applyItemList = shipmentApplyItemService.list(wrapper);
        if (ObjectUtil.isEmpty(applyItemList)) {
            log.info("根据批次号【{}】查找未能找到需要创建调拨单的申请项",JSONUtil.toJsonStr(transferParam.getApplyBatchNoList()));
            return null;
        }
        Map<String, List<ShipmentApplyItem>> itemGroupByBatchNoMap
                = applyItemList.stream().collect(Collectors.groupingBy(ShipmentApplyItem::getApplyBatchNo));

        //同步成功的批次号
        List<String> successBatchNo = new ArrayList<>();

        //创建追踪调拨单明细项
        List<ShipmentApplyItem> needCreateTranferList = new ArrayList<>();

        List<ShipmentApplyItem> updateItemList = new ArrayList<>();

        for (Map.Entry<String, List<ShipmentApplyItem>> entry : itemGroupByBatchNoMap.entrySet()) {
            String batchNo = entry.getKey();
            List<ShipmentApplyItem> itemList = entry.getValue();
            log.info("正在处理发货申请批次号【{}】",batchNo);

            //同批次
            List<String> syncSuccessBillNo = new ArrayList<>();
            for (ShipmentApplyItem item : itemList) {

                item.setCheckReason(transferParam.getReson());
                item.setCheckPerson(currentUser.getName());
                item.setCheckPersonNo(currentUser.getAccount());

                Map<String, Object> mapParm = BeanUtil.beanToMap(item);
                //补充调拨单数据，不需要请求K3
                //JSONObject syncResultJson = syncToErpUtil.createTransferMapJson(mapParm);
                item.setSyncTime(new Date());
                item.setSyncRequestMsg(String.valueOf(mapParm.get("syncRequestMsg")));
                item.setSyncResultMsg("之前数据已经在K3系统生成了调拨单，在JCErp补充调拨单数据");
                item.setCheckStatus(1);
                item.setSyncStatus(1);
                syncSuccessBillNo.add(item.getBillNo());
                updateItemList.add(item);
            }

            if (itemList.size() == syncSuccessBillNo.size()) {
                successBatchNo.add(batchNo);
                needCreateTranferList.addAll(itemList);
            }
        }


        if (ObjectUtil.isNotEmpty(needCreateTranferList)) {

            List<TrackingTransfer> transferList = new ArrayList<>();

            for (ShipmentApplyItem applyItem : needCreateTranferList) {
                TrackingTransfer transfer = new TrackingTransfer();
                BeanUtil.copyProperties(applyItem, transfer);
                transfer.setShipmentId(applyItem.getFbaNo());
                transfer.setApplySendQty(applyItem.getSendQty());
                transfer.setApplyTrackStatus(3);
                transfer.setCheckDate(new Date());
                transfer.setCheckPerson(currentUser.getName());
                transfer.setCheckPersonNo(currentUser.getAccount());
                transfer.setCheckReason(transferParam.getReson());
                transfer.setCreatedTime(new Date());
                transfer.setSyncTime(new Date());
                transfer.setUpdatedTime(new Date());

                transferList.add(transfer);
            }

            trackingTransferService.saveBatch(transferList);
        }

        shipmentApplyItemService.updateBatchById(updateItemList);
        return successBatchNo;
    }
}
