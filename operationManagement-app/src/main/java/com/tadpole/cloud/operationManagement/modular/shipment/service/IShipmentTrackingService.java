package com.tadpole.cloud.operationManagement.modular.shipment.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentTracking;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentBoardParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.TrackParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.TrackingTransferParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentTrackingResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.TrackingFlatVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 发货追踪汇总表 服务类
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
public interface IShipmentTrackingService extends IService<ShipmentTracking> {

    /**
     * 发货追踪
     * @param batchNo 批次编号
     * @return
     */
    ResponseData track(String batchNo);
    ResponseData transferRevoke(String billNo,String revokeReason);
    ResponseData transferListRevoke(List<TrackingTransferParam> transferList);

    PageResult<TrackingFlatVO> trackFlatList(TrackParam param);
    PageResult<ShipmentTrackingResult>  trackBatchList(TrackParam param);
    ResponseData trackTransferList(String applyBatchNo);
    ResponseData trackLogisticsList(String billNo);


    /**
     * 删除调拨单
     * @param billNo
     * @return
     */
    ResponseData delTranferByBillNo(String billNo);

    /**
     * 更新调拨单状态
     * @param billNoSet
     */
    void updateTransferStatus(Set<String> billNoSet);

    /**
     * 计算批次的汇总数量， 实际发货数量合计  ，实际到货数量合计
     * @param batchNoSet
     */
    void calcSumQty(Set<String> batchNoSet);

    /**
     * 更新删除的数据（k3系统已经删除了拣货单，出货清单）
     */
    public void updateK3DelData() ;


    ResponseData queryShipmentBoard(ShipmentBoardParam param);
}
