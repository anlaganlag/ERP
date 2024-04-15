package com.tadpole.cloud.operationManagement.modular.shipment.service;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.SupplementaryTransferParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.VerifParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 其他模块服务
 */
public interface IOtherModularService {
    /**
     * 获取仓库编码
     * @param warehouseSet
     * @return
     */
     Map<String, String> getWarehouseCode(Set<String> warehouseSet);

    /**
     * 创建调拨单
     * @param successBatchNo
     * @param verifParam
     * @param currentUser
     * @return
     */
    List<String> createTransfer(List<String> successBatchNo, VerifParam verifParam, LoginUser currentUser);

    /**
     * 重erp获取调拨单信息
     * @param billNoSet
     * @return
     */
    List<ErpTransferResult> getErpTransfer(Set<String> billNoSet);

    /**
     * 获取erp出货清单信息
     * @param pickCodeSet
     * @return
     */

    List<TrackingShippingResult> getErpShipping(Set<String> pickCodeSet);

    /**
     * 获取erp物流信息
     * @param shippingListCodeSet
     * @return
     */
    List<TrackingLogisticsResult> getEbmsLogistics(Set<String> shippingListCodeSet);

    /**
     * EBMS获取赔偿数据
     * @param shipmentIdSet
     * @return
     */
    List<EbmsCompensateResult> getCompensateDataFromEbms(Set<String> shipmentIdSet);

    /**
     * 获取来FBA货报告接受数据
     * @param shipmentIdSet
     * @return
     */
    List<WareHouseReceiveResult> getReceiveQtyFromWareHouse(Set<String> shipmentIdSet);

    /**
     * 根据 shipmentId,SKU,SYS_SITE 维度查找EBMS sku发货的接收状态  （已完成的）
     * @param mergeFieldList
     * @return
     */
    List<String> getSkuStatus(List<String> mergeFieldList);


    List<String> supplementaryTransfer( SupplementaryTransferParam supplementaryTransferParam,LoginUser currentUser);
}
