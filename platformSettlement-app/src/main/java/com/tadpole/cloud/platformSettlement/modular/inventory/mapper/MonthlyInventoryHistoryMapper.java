package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.EndingInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EndingInventoryDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.MonthlyInventoryHistory;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.MonthlyInventoryHistoryParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Monthly inventory history报告明细 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface MonthlyInventoryHistoryMapper extends BaseMapper<MonthlyInventoryHistory> {

  /**
   * Monthly inventory history分页查询列表
   * @param page
   * @param paramCondition
   * @return
   */
  Page<MonthlyInventoryHistory> customPageList(@Param("page") Page page, @Param("paramCondition") MonthlyInventoryHistoryParam paramCondition);

  /**
   * Monthly inventory history获取数量
   * @param paramCondition
   * @return
   */
  MonthlyInventoryHistory getQuantity(@Param("paramCondition") MonthlyInventoryHistoryParam paramCondition);

  String getMaterial(
      @Param("paramCondition") MonthlyInventoryHistoryParam paramCondition);

  /**
   * 获获取指定月份Monthly Inventory_History汇总数据
   * @param paramCondition
   * @return
   */
  List<EndingInventory> getListHeader(@Param("paramCondition") MonthlyInventoryHistoryParam paramCondition);

  Boolean isInvReady(@Param("paramCondition") MonthlyInventoryHistoryParam paramCondition);

  /**
   * 获取指定月份Monthly Inventory_History明细数据
   * @param paramCondition
   * @return
   */
  List<EndingInventoryDetail> getDetailList(@Param("paramCondition") EndingInventory paramCondition);

  /**
   * 更新Monthly Inventory History源报告生成状态和单据头
   * @param paramCondition
   */
  void updateSrcList(@Param("paramCondition") EndingInventory paramCondition);

  /**
   * +海外仓库存更新在库数和在途数
   * @param dateMonthly
   */
  void updateSeaInventory(@Param("dateMonthly") String dateMonthly);

  /**
   * 更新海外仓库存使用状态
   * @param dateMonthly
   */
  void updateSeaUse(@Param("dateMonthly") String dateMonthly);

  /**
   * +在途库存更新物流代发数和在途数
   * @param dateMonthly
   */
  void updateComingInventory(@Param("dateMonthly") String dateMonthly);

  /**
   * 更新在途库存使用状态
   * @param dateMonthly
   */
  void updateComingUse(@Param("dateMonthly") String dateMonthly);

  /**
   * 获取未使用的在途库存和海外仓库单据头部数据
   * @param dateMonthly
   * @param userAccount
   */
  List<EndingInventory> getHeaderByComingAndSea(@Param("dateMonthly") String dateMonthly, @Param("userAccount") String userAccount);

  /**
   * 获取未使用的在途库存和海外仓库明细数据
   * @param endingInventory
   */
  List<EndingInventoryDetail> getDetailByComingAndSea(@Param("endingInventory") EndingInventory endingInventory);

  /**
   * 期末库存列表明细刷listings
   */
  void updateDetailList();

  /**
   * 期末库存列表明细刷listings（存档）
   */
  void updateFileDetailList();

  List<MonthlyInventoryHistoryParam> orgList();

  Boolean orgBatch(@Param("verifyList") List<MonthlyInventoryHistoryParam> verifyList);


  Boolean verifyUpdateBatch(@Param("paramCondition") MonthlyInventoryHistoryParam paramCondition);


  void rejectBatch(@Param("paramCondition") MonthlyInventoryHistoryParam paramCondition);

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
   * 分配的物料和库存组织编码
   * @param detailIdList
   * @return
   */
  List<ZZDistributeMcms> assignMaterialList(@Param("detailIdList") List<String> detailIdList);

  /**
   * 查询是否有未审核或者未生成期末库存列表的数据
   * @param paramCondition
   * @return
   */
  int getNotVerifyCounts(@Param("paramCondition") MonthlyInventoryHistoryParam paramCondition);

  /**
   * 获MonthlyInventoryHistory数据任务
   * @param dataDate
   */
  void generateMonthlyInventoryHistory(@Param("dataDate") String dataDate);
}
