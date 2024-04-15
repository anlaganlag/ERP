package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustReduce;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustReduceDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InventoryAdjustReduceParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.InventoryAdjustReduceResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * AZ库存调整列表(减少) Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface InventoryAdjustReduceMapper extends BaseMapper<InventoryAdjustReduce> {


  Page<InventoryAdjustReduceResult> customPageList(
      @Param("page") Page page, @Param("paramCondition") InventoryAdjustReduceParam paramCondition);


  List<InventoryAdjustReduceResult> emailList();


  String getQuantity(@Param("paramCondition") InventoryAdjustReduceParam param);

  void rejectBatch(@Param("paramCondition") InventoryAdjustReduceParam paramCondition);


  void syncReportReject(@Param("paramCondition") InventoryAdjustReduceParam paramCondition);


  void syncReportBatchReject(@Param("paramCondition") List<InventoryAdjustReduceParam> paramCondition);


  List<InventoryAdjustReduceDetail> getSyncList(
      @Param("paramCondition") InventoryAdjustReduceParam param);

  void updateSyncStatus();

  List<InventoryAdjustReduceParam> normalList();

    void updateCanSyncNormal();
}
