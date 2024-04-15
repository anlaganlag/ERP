package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesReturn;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesReturnDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.SalesReturnParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.SalesReturnResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 销售退货列表 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface SalesReturnMapper extends BaseMapper<SalesReturn> {

  /**
   * 销售退货列表、导出
   * @param page
   * @param paramCondition
   * @return
   */
  Page<SalesReturnResult> customPageList(@Param("page") Page page, @Param("paramCondition") SalesReturnParam paramCondition);

  /**
   * 销售退货发邮件数据
   * @return
   */
  List<SalesReturnResult> emailList();

  /**
   * 销售退货数量合计
   * @param param
   * @return
   */
  String getQuantity(@Param("paramCondition") SalesReturnParam param);

  /**
   * 批量作废销售退货列表数据
   * @param paramCondition
   */
  void rejectBatch(@Param("paramCondition") SalesReturnParam paramCondition);

  /**
   * 作废销售退货FBA_CUSTOMER_RETURNS源报告数据
   * @param paramCondition
   */
  void syncReportReject(@Param("paramCondition") SalesReturnParam paramCondition);

  /**
   * 批量作废销售退货FBA_CUSTOMER_RETURNS源报告数据
   * @param paramCondition
   */
  void syncReportBatchReject(@Param("paramCondition") List<SalesReturnParam> paramCondition);

  /**
   * 退货单明细
   * @param param
   * @return
   */
  List<SalesReturnDetail> getSyncList(@Param("paramCondition") SalesReturnParam param);

  /**
   * 更新单据主表是否可以同步状态：刷新不可以同步：0
   */
  void updateSyncStatus();

  /**
   * 执行定时同步推送ERP数据
   * @return
   */
  List<SalesReturnParam> normalList();

  /**
   * 更新单据主表是否可以同步状态：刷新可以同步：1
   */
  void updateCanSyncNormal();
}
