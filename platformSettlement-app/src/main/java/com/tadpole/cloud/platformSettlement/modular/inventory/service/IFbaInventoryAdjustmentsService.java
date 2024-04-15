package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.FbaInventoryAdjustments;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaInventoryAdjustmentsParam;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 库存调整报告 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface IFbaInventoryAdjustmentsService extends IService<FbaInventoryAdjustments> {

  /**
   * Inventory Adjustments库存调整报告分页查询列表
   * @param param
   * @return
   */
  PageResult<FbaInventoryAdjustments> findPageBySpec(FbaInventoryAdjustmentsParam param);

  /**
   * Inventory Adjustments库存调整报告获取数量
   * @param param
   * @return
   */
  Integer getQuantity(FbaInventoryAdjustmentsParam param);

  /**
   * Inventory Adjustments库存调整报告导出
   * @param param
   * @return
   */
  List<FbaInventoryAdjustments> export(FbaInventoryAdjustmentsParam param);

  void verify(FbaInventoryAdjustmentsParam param);


  void reject(FbaInventoryAdjustmentsParam param);

  void rejectBatch(List<FbaInventoryAdjustmentsParam> params);

  String getMaterial(FbaInventoryAdjustmentsParam param);

  void assignMaterial(FbaInventoryAdjustmentsParam param);

  void assignBatchMaterial(List<FbaInventoryAdjustmentsParam> param);

  ResponseData toInventoryAdjustList(FbaInventoryAdjustmentsParam param);

  /**
   * 修改站点
   * @param params
   * @return
   */
  ResponseData editSites(List<FbaInventoryAdjustmentsParam> params);

  /**
   * 修改账号
   * @param params
   * @return
   */
  ResponseData editShop(List<FbaInventoryAdjustmentsParam> params);

  List<FbaInventoryAdjustmentsParam> orgList();

  Boolean orgBatch(List<FbaInventoryAdjustmentsParam> params);

  Boolean verifyUpdateBatch(FbaInventoryAdjustmentsParam param);

  ResponseData addNewOrg();

  /**
   * 获InventoryAdjustments数据任务
   * @return
   */
  ResponseData generateInventoryAdjustments();
}
