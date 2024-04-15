package com.tadpole.cloud.platformSettlement.modular.sales.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.AdvertisingBudget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.AdvertisingBudgetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.AdvertisingBudgetResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 广告预算 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
public interface IAdvertisingBudgetService extends IService<AdvertisingBudget> {
  /**
   * 广告预算列表接口
   */
  List<AdvertisingBudgetResult> list(AdvertisingBudgetParam param);

  /**
   * 广告预算列表合计接口
   */
  AdvertisingBudgetResult listSum(AdvertisingBudgetParam param);

  /**
   * 广告预算确认接口
   */
  ResponseData confirm(AdvertisingBudgetParam param);

  /**
   * 广告预算修改接口
   */
  ResponseData edit(AdvertisingBudgetParam param);

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
   * 广告预算导入
   */
  ResponseData upload(AdvertisingBudgetParam param, MultipartFile file,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList,List<String> brandList,List<String> jpShops);
}
