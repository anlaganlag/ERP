package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesStockOut;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesStockOutDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.SalesStockOutParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.SalesStockOutResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 销售出库列表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface ISalesStockOutService extends IService<SalesStockOut> {

  /**
   * 销售出库列表
   * @param param
   * @return
   */
  PageResult<SalesStockOutResult> findPageBySpec(SalesStockOutParam param);

  /**
   * 销售出库列表导出
   * @param param
   * @return
   */
  List<SalesStockOutResult> export(SalesStockOutParam param);

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
  String getQuantity(SalesStockOutParam param);

  /**
   * 作废
   * @param param
   */
  ResponseData reject(SalesStockOutParam param);

  /**
   * 批量作废
   * @param params
   */
  ResponseData rejectBatch(List<SalesStockOutParam> params);

  /**
   * 同步erp
   * @param param
   * @throws IOException
   */
  void syncErp(SalesStockOutParam param) throws IOException;

  /**
   * 出库单明细
   * @param param
   * @return
   */
  List<SalesStockOutDetail> getSyncList(SalesStockOutParam param);

  /**
   * 销售出库列表明细刷listing
   */
  void updateDetailList();

  /**
   * 销售出库列表明细刷存档listing
   */
  void updateFileDetailList();

  /**
   * 更新单据主表是否可以同步状态：刷新不可以同步：0
   */
  void updateSyncStatus();

  /**
   * 执行定时同步推送ERP数据
   * @throws IOException
   */
  void timerSync() throws IOException;

  /**
   * 修改物料
   * @param param
   * @return
   */
  ResponseData editMat(SalesStockOutParam param);

  /**
   * 更新单据主表是否可以同步状态：刷新可以同步：1
   */
  void updateCanSyncNormal();

  /**
   * 无需处理
   * @param param
   */
  ResponseData disable(SalesStockOutParam param);
}
