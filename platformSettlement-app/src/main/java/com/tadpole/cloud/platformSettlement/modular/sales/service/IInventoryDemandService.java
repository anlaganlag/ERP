package com.tadpole.cloud.platformSettlement.modular.sales.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.sales.entity.StockMonitor;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.InventoryDemand;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryDemandParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.InventoryDemandResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 存货需求 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-03
 */
public interface IInventoryDemandService extends IService<InventoryDemand> {

  /**
   * 存货需求列表接口
   */
  List<InventoryDemandResult> list(InventoryDemandParam param);

  List<StockMonitor> stockMonitorHead(String department);

  /**
   * 存货需求列表合计接口
   */
  InventoryDemandResult listSum(InventoryDemandParam param);

  /**
   * 存货需求确认接口
   */
  ResponseData confirm(InventoryDemandParam param);

  /**
   * 存货需求修改接口
   */
  ResponseData edit(InventoryDemandParam param);

  /**
   * 广告预算下拉接口-平台
   */
  List<Map<String, Object>> getPlatformSelect();

  /**
   * 广告预算下拉接口-事业部
   */
  List<Map<String, Object>> getDepartmentSelect();

  /**
   * 广告预算下拉接口-Team
   */
  List<Map<String, Object>> getTeamSelect();

  /**
   * 广告预算下拉接口-运营大类
   */
  List<Map<String, Object>> getProductTypeSelect();

  /**
   * 广告预算下拉接口-销售品牌
   */
  List<Map<String, Object>> getCompanyBrandSelect();

  /**
   * 广告预算下拉接口-年份
   */
  List<Map<String, Object>> getYearSelect();

  /**
   * 广告预算下拉接口-版本
   * @Param String year
   */
  List<Map<String, Object>> getVersionSelect(String year);

  /**
   * 存货需求导入
   */
  ResponseData upload(InventoryDemandParam param, MultipartFile file,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList,List<String> brandList);

  /**
   * 按部门统计
   * @param department
   * @return
   */
  StockMonitor stockMonitorHeadDept(String department);
}
