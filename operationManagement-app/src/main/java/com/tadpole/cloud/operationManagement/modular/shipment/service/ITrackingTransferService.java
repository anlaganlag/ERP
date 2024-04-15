package com.tadpole.cloud.operationManagement.modular.shipment.service;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.TrackingTransfer;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ErpTeamAvailableQytResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.OccupyQytResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentBoardArrivalResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 追踪明细项-调拨单 服务类
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
public interface ITrackingTransferService extends IService<TrackingTransfer> {


    /**
     * 获取team下占用库存数量
     * @param applyBatchNo
     * @param team
     * @param materialCode
     * @param deliverypointNo
     * @return
     */
    public OccupyQytResult queryOccupyQty(String applyBatchNo, String team, String  materialCode, String deliverypointNo);

    /**
     * 批量获取team下占用库存数量
     * @param queryMergeFiels
     * @return
     */
    List<OccupyQytResult> batchQueryOccupyQty(Set<String> queryMergeFiels,List<String> applyBatchNoList);




    /**
     * 合并字段拼接查询erp系统team下的可用物料数量
     *
     * @param mergeFieldList team+物料合并成字符串的list
     * @return
     */

    public List<ErpTeamAvailableQytResult> erpTeamAvailableQty(Set<String> mergeFieldList);

    /**
     * erp系统team下的可用物料数量和即时库存取最小值
     *
     * @param mergeFieldList team+物料+发货点 合并成字符串的list
     * @return
     */
    List<ErpTeamAvailableQytResult> erpAvailableQty(Set<String> mergeFieldList);


    List<ErpTeamAvailableQytResult> erpAvailableQty(Set<String> teamSet,Set<String> matSet,Set<String> deliverypointNoSet,Set<String> mergeFieldList);
    List<ErpTeamAvailableQytResult> erpAvailableAllQty(List<String> team,List<String> matCode);
    BigDecimal toCheckQty(List<String> matCodeList);

    BigDecimal toSendQty(List<String> team,List<String> matCode,String deliverypointNo);

    Map transit(List<String> teamList, List<String> matCodeList,  List<String> areaList, List<String> asinList, Boolean isTransit);


    BigDecimal toShelveQty(List<String> teamList, List<String> matCodeList,  List<String> areaList, List<String> asinList);


    List<ShipmentBoardArrivalResult> arrivalList(List<String> team, List<String> matCode , List<String> area, List<String> asin);

        /**
         * 更新调拨单追踪状态
         * @param billNoList
         */
    void updateTransferStatus(List<String> billNoList);

    /**
     * 调拨单撤销
     * @param applyBatchNo 批次号
     * @param billNo 调拨单号
     * @param currentUser 操作人
     * @param revokeReason 撤销原因
     * @return
     */
    ResponseData transferRevoke(String applyBatchNo, String billNo, LoginUser currentUser, String revokeReason);
}
