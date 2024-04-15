package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustAdd;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustAddDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InventoryAdjustAddParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.InventoryAdjustAddResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * AZ库存调整列表(增加) Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface InventoryAdjustAddMapper extends BaseMapper<InventoryAdjustAdd> {

  Page<InventoryAdjustAddResult> customPageList(
      @Param("page") Page page, @Param("paramCondition") InventoryAdjustAddParam paramCondition);


  List<InventoryAdjustAddResult> emailList();



  String getQuantity(@Param("paramCondition") InventoryAdjustAddParam param);

  void rejectBatch(@Param("paramCondition") InventoryAdjustAddParam paramCondition);

  void syncReportReject(@Param("paramCondition") InventoryAdjustAddParam paramCondition);

  void syncReportBatchReject(@Param("paramCondition") List<InventoryAdjustAddParam> paramCondition);


  List<InventoryAdjustAddDetail> getSyncList(
      @Param("paramCondition") InventoryAdjustAddParam param);

  void updateSyncStatus();

  List<InventoryAdjustAddParam> normalList();

    void updateCanSyncNormal();
}
