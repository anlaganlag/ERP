package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesStockOut;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesStockOutDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.SalesStockOutParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.SalesStockOutResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 销售出库列表 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface SalesStockOutMapper extends BaseMapper<SalesStockOut> {

  /**
   * 销售出库列表、导出
   * @param page
   * @param paramCondition
   * @return
   */
  Page<SalesStockOutResult> customPageList(@Param("page") Page page, @Param("paramCondition") SalesStockOutParam paramCondition);

  /**
   * 销售出库发邮件数据
   * @return
   */
  List<SalesStockOutResult> emailList();

  /**
   * 销售出库数量合计数量
   * @param param
   * @return
   */
  String getQuantity(@Param("paramCondition") SalesStockOutParam param);

  /**
   * 批量作废销售出库列表数据
   * @param paramCondition
   */
  void rejectBatch(@Param("paramCondition") SalesStockOutParam paramCondition);

  /**
   * 作废销售出库Customer Shipment Sales源报告数据
   * @param paramCondition
   */
  void syncReportReject(@Param("paramCondition") SalesStockOutParam paramCondition);

  /**
   * 批量作废销售出库Customer Shipment Sales源报告数据
   * @param paramCondition
   */
  void syncReportBatchReject(@Param("paramCondition") List<SalesStockOutParam> paramCondition);

  /**
   * 出库单明细
   * @param param
   * @return
   */
  List<SalesStockOutDetail> getSyncList(@Param("paramCondition") SalesStockOutParam param);

  /**
   * 更新单据主表是否可以同步状态：刷新不可以同步：0
   */
  void updateSyncStatus();

  /**
   * 定时获取同步推送ERP数据
   * @return
   */
  List<SalesStockOutParam> normalList();

  /**
   * 更新单据主表是否可以同步状态：刷新可以同步：1
   */
  void updateCanSyncNormal();
}
