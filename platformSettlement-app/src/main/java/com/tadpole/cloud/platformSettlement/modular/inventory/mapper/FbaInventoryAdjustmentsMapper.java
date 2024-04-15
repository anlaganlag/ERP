package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.FbaInventoryAdjustments;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustAddDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustReduceDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaInventoryAdjustmentsParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.FbaInventoryAdjustmentsResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 库存调整报告 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface FbaInventoryAdjustmentsMapper extends BaseMapper<FbaInventoryAdjustments> {

  /**
   * Inventory Adjustments库存调整报告分页查询列表
   * @param page
   * @param paramCondition
   * @return
   */
  Page<FbaInventoryAdjustments> customPageList(@Param("page") Page page, @Param("paramCondition") FbaInventoryAdjustmentsParam paramCondition);

  /**
   * Inventory Adjustments库存调整报告获取数量
   * @param paramCondition
   * @return
   */
  Integer getQuantity(@Param("paramCondition") FbaInventoryAdjustmentsParam paramCondition);

  String getMaterial(
      @Param("paramCondition") FbaInventoryAdjustmentsParam paramCondition);

  List<FbaInventoryAdjustmentsResult> getAddListHeader(
      @Param("paramCondition") FbaInventoryAdjustmentsParam paramCondition);

  List<FbaInventoryAdjustmentsResult> getReduceListHeader(
      @Param("paramCondition") FbaInventoryAdjustmentsParam paramCondition);


  List<InventoryAdjustAddDetail> getAddDetailList(
      @Param("paramCondition") FbaInventoryAdjustmentsResult paramCondition);

  List<InventoryAdjustReduceDetail> getReduceDetailList(
      @Param("paramCondition") FbaInventoryAdjustmentsResult paramCondition);


  void updateAddDetailList();

  void updateReduceDetailList();


  void updateFileAddDetailList();

  void updateFileReduceDetailList();


  void updateAddSrcList(
      @Param("paramCondition") FbaInventoryAdjustmentsResult paramCondition);

  void updateReduceSrcList(
      @Param("paramCondition") FbaInventoryAdjustmentsResult paramCondition);

  List<FbaInventoryAdjustmentsParam> orgList();

  Boolean orgBatch(@Param("verifyList") List<FbaInventoryAdjustmentsParam> verifyList);

  Boolean verifyUpdateBatch(@Param("paramCondition") FbaInventoryAdjustmentsParam paramCondition);


  void rejectBatch(@Param("paramCondition") FbaInventoryAdjustmentsParam paramCondition);

  /**
   * 刷组织名称（Amazon_账号_站点）和仓库组织名称（Amazon_账号_站点_仓库）
   */
  void addOrgName();

  /**
   * 根据组织刷库存组织编码
   */
  void refreshOrgCode();

  /**
   * 根据仓库组织名称获取仓库组织编码
   */
  void refreshWareOrgCode();

  /**
   * 获InventoryAdjustments数据任务
   * @param dataDate
   */
  void generateInventoryAdjustments(@Param("dataDate") Date dataDate);
}


