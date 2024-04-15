package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesReturn;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesReturnDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.SalesReturnParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.SalesReturnResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 销售退货列表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface ISalesReturnService extends IService<SalesReturn> {

  /**
   * 销售退货列表
   * @param param
   * @return
   */
  PageResult<SalesReturnResult> findPageBySpec(SalesReturnParam param);

  /**
   * 销售退货数量合计
   * @param param
   * @return
   */
  String getQuantity(SalesReturnParam param);

  /**
   * 销售退货列表导出
   * @param param
   * @return
   */
  List<SalesReturnResult> export(SalesReturnParam param);

  /**
   * 作废
   * @param param
   * @return
   */
  ResponseData reject(SalesReturnParam param);

  /**
   * 退货单明细
   * @param param
   * @return
   */
  List<SalesReturnDetail> getSyncList(SalesReturnParam param);

  /**
   * 批量作废
   * @param params
   * @return
   */
  ResponseData rejectBatch(List<SalesReturnParam> params);

  /**
   * 同步erp
   * @param param
   * @throws IOException
   */
  void syncErp(SalesReturnParam param) throws IOException;

  /**
   * 销售退货列表明细刷listing
   * @throws IOException
   */
  void updateDetailList() throws IOException;

  /**
   * 销售退货列表明细刷存档listing
   * @throws IOException
   */
  void updateFileDetailList() throws IOException;

  /**
   * 销售退货发邮件数据
   * @return
   */
  List<SalesReturnResult> emailList();

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
  ResponseData editMat(SalesReturnParam param);

  /**
   * 更新单据主表是否可以同步状态：刷新可以同步：1
   */
  void updateCanSyncNormal();

  /**
   * 无需处理
   * @param param
   * @return
   */
  ResponseData disable(SalesReturnParam param);
}
