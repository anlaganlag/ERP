package com.tadpole.cloud.operationManagement.modular.shipment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentTracking;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.TrackParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 发货追踪汇总表 Mapper 接口
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
public interface ShipmentTrackingMapper extends BaseMapper<ShipmentTracking> {


    List<ErpTransferResult> getErpTransfer(List<String> billNoSet);

    List<TrackingShippingResult> getErpShipping(List<String> pickCodeSet);

    List<TrackingLogisticsResult> getEbmsLogistics(List<String> shippingListCodeSet);

    List<EbmsCompensateResult> getCompensateDataFromEbms(List<String> shipmentIdSet);

    List<WareHouseReceiveResult> getReceiveQtyFromWareHouse(List<String> shipmentIdSet);
    Page<TrackingFlatVO> trackFlatList(@Param("page") Page page, @Param("paramCondition") TrackParam param);

    Page<ShipmentTrackingResult> trackBatchList(@Param("page") Page page, @Param("paramCondition") TrackParam param);
    List<TrackingTransferResult> trackTransferList(String applyBatchNo);
    List<TrackingShipLogResult> trackLogisticsList(String billNo);


    /**
     * 计算批次的汇总数量， 实际发货数量合计  ，实际到货数量合计
     * @param batchNoList
     */
    void calcSumQty(List<String> batchNoList);

    List<String> getSkuStatus(List<String> mergeFields);
}
