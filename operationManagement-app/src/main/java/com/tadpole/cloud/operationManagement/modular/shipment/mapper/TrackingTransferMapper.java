package com.tadpole.cloud.operationManagement.modular.shipment.mapper;

import com.tadpole.cloud.operationManagement.modular.shipment.entity.TrackingTransfer;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ErpTeamAvailableQytResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.OccupyQytResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentBoardArrivalResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* <p>
    * 追踪明细项-调拨单 Mapper 接口
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
public interface TrackingTransferMapper extends BaseMapper<TrackingTransfer> {


    @Select("SELECT COUNT(t.APPLY_SEND_QTY)  " +
            "FROM  TRACKING_TRANSFER t JOIN  SHIPMENT_TRACKING s ON t.APPLY_BATCH_NO =s.APPLY_BATCH_NO " +
            "WHERE t.MATERIAL_CODE =#{materialCode} AND t.APPLY_TRACK_STATUS IN (1,3,,31,4) AND s.TEAM =#{team} ")
    BigDecimal queryOccupyQty(@Param("team") String team, @Param("materialCode")  String materialCode);


    List<OccupyQytResult> batchQueryOccupyQty(@Param("queryMergeFiels") Set<String> queryMergeFiels);

    List<OccupyQytResult> batchQueryApplyOccupyQty(@Param("queryMergeFiels") Set<String> queryMergeFiels,@Param("applyBatchNo") List<String> applyBatchNo);

    List<ErpTeamAvailableQytResult> erpTeamAvailableQty(Set<String> mergeFiledList);
    List<ErpTeamAvailableQytResult> erpAvailableQty(Set<String> teamSet,Set<String> matSet,Set<String> deliverypointNoSet,Set<String> mergeFieldList);
    List<ErpTeamAvailableQytResult> erpAvailableQty2(Set<String> teamSet,Set<String> matSet,Set<String> deliverypointNoSet,Set<String> mergeFieldList);

    List<ErpTeamAvailableQytResult> erpAvailableAllQty(List<String> teamList,List<String> matCodeList);

    BigDecimal toCheckQty(List<String> matCodeList);
    BigDecimal toSendQty(List<String> teamList,List<String> matCodeList,String deliverypointNo);

    Map<String,String> transit(List<String> teamList, List<String> matCodeList, List<String> areaList, List<String> asinList, Boolean isTransit);


    BigDecimal toShelveQty(List<String> teamList,List<String> matCodeList, List<String> areaList, List<String> asinList);


    List<ShipmentBoardArrivalResult> arrivalList(List<String> teamList, List<String> matCodeList , List<String> areaList, List<String> asinList);


    void updateTransferStatus (List<String> billNoList);


    /**
     * 1个调拨单对应一个shipmentId的时候（绝大多数都是这种情况)
     * @param multipleShipmentId 查找调拨单对应多个shipmentId true:只包含多个shipmentId的调拨单，false:调拨单只包含一个shipmentId
     * @return
     */
    List<TrackingTransfer> getNeedCheckTransferData(boolean multipleShipmentId);



}
