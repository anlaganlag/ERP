package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.EndingInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EndingInventoryDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EndingInventoryParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.EndingInventoryResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 期末库存列表 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface EndingInventoryMapper extends BaseMapper<EndingInventory> {

  /**
   * 期末库存列表、导出
   * @param page
   * @param paramCondition
   * @return
   */
  Page<EndingInventoryResult> customPageList(@Param("page") Page page, @Param("paramCondition") EndingInventoryParam paramCondition);

  /**
   * 发邮件
   * @return
   */
  List<EndingInventoryResult> emailList();

  /**
   * 获取数量
   * @param param
   * @return
   */
  String getQuantity(@Param("paramCondition") EndingInventoryParam param);

  /**
   * 批量作废
   * @param paramCondition
   */
  void rejectBatch(@Param("paramCondition") EndingInventoryParam paramCondition);

  /**
   * 作废源报告monthly_inventory_history
   * @param paramCondition
   */
  void syncReportReject(@Param("paramCondition") EndingInventoryParam paramCondition);

  /**
   * 批量作废源报告：monthly_inventory_history
   * @param paramCondition
   */
  void syncReportBatchReject(@Param("paramCondition") List<EndingInventoryParam> paramCondition);

  /**
   * 获取单据头数据
   * @param param
   * @return
   */
  List<EndingInventory> queryEnding( @Param("paramCondition") EndingInventoryParam param);

  /**
   * 获取同步ERP的明细数据
   * @param ending
   * @return
   */
  List<EndingInventoryDetail> getSyncDetail( @Param("paramCondition")  EndingInventory ending);

  void updateSyncStatus();

  List<EndingInventoryParam> normalList();
}
