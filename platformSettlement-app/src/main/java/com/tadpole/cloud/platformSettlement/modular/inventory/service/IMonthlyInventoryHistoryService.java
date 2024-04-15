package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.MonthlyInventoryHistory;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.MonthlyInventoryHistoryParam;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * Monthly inventory history报告明细 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface IMonthlyInventoryHistoryService extends IService<MonthlyInventoryHistory> {

  /**
   * Monthly inventory history分页查询列表
   * @param param
   * @return
   */
  PageResult<MonthlyInventoryHistory> findPageBySpec(MonthlyInventoryHistoryParam param);

  /**
   * Monthly inventory history报告明细导出
   * @param param
   * @return
   */
  List<MonthlyInventoryHistory> export(MonthlyInventoryHistoryParam param);

  /**
   * Monthly inventory history获取数量
   * @param param
   * @return
   */
  ResponseData getQuantity(MonthlyInventoryHistoryParam param);

  void verify(MonthlyInventoryHistoryParam param);

  String getMaterial(MonthlyInventoryHistoryParam param);

  void assignMaterial(MonthlyInventoryHistoryParam param);

  void reject(MonthlyInventoryHistoryParam param);

  void rejectBatch(List<MonthlyInventoryHistoryParam> params);

  /**
   * 获取月份Monthly Inventory_History数据入库期末库存汇总和明细表
   * @param param
   * @return
   */
  ResponseData toEndingInventoryList(MonthlyInventoryHistoryParam param);

  /**
   * 修改站点
   * @param params
   * @return
   */
  ResponseData editSites(List<MonthlyInventoryHistoryParam> params);

  /**
   * 修改账号
   * @param params
   * @return
   */
  ResponseData editShop(List<MonthlyInventoryHistoryParam> params);

  List<MonthlyInventoryHistoryParam> orgList();

  Boolean orgBatch(List<MonthlyInventoryHistoryParam> params);

  Boolean verifyUpdateBatch(MonthlyInventoryHistoryParam param);

  /**
   * +海外仓库存更新在库数和在途数
   * @param param
   */
//  void updateBatchSea(MonthlyInventoryHistoryParam param);

  /**
   * +在途库存更新物流代发数和在途数
   * @param param
   */
//  void updateBatchComing(MonthlyInventoryHistoryParam param);

  /**
   * 刷组织
   * @return
   */
  ResponseData addNewOrg();

  /**
   * 分配的物料和库存组织编码
   * @param detailIdList
   * @return
   */
  List<ZZDistributeMcms> assignMaterialList(List<String> detailIdList);

  /**
   * 将物料编码和库存组织编码同步至ERP
   * @param assignList
   * @return
   */
  ResponseData assignBatchMaterial(List<ZZDistributeMcms> assignList) ;

  /**
   * 获MonthlyInventoryHistory数据任务
   * @return
   */
  ResponseData generateMonthlyInventoryHistory();
}
