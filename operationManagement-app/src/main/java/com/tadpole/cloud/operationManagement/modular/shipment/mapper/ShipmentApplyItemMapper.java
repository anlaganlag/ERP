package com.tadpole.cloud.operationManagement.modular.shipment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentApplyItem;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentTrackingParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ExportVerifyListResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* <p>
    * 发货申请明细项 Mapper 接口
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
public interface ShipmentApplyItemMapper extends BaseMapper<ShipmentApplyItem> {



    List<Map<String,String>> warehouse(@Param("platform") String platform,@Param("site") String site);

    /**
     * 查询team下占用的物料数量
     * @param queryMergeFiels
     * @return
     */
    List<Map<String, BigDecimal>> batchQueryQty(Set<String> queryMergeFiels);


    List<ShipmentApplyItem>  selectVerifyList(String applyStatus,String deptMgr);
    List<ShipmentApplyItem>  selectVerifyListByDept(String applyStatus, String department);
    List<ExportVerifyListResult>  exportVerifyList(@Param("paramCondition") ShipmentTrackingParam trackingParam);


    List<ShipmentApplyItem> selectVerifyListDatalimit(@Param("paramCondition") ShipmentTrackingParam trackingParam);

}
