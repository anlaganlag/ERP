package com.tadpole.cloud.platformSettlement.modular.sales.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.NewProductBudget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.NewProductBudgetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.NewProductBudgetResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 新品预算 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-07
 */
public interface INewProductBudgetService extends IService<NewProductBudget> {

  /**
   * 新品预算列表接口
   */
  List<NewProductBudgetResult> list(NewProductBudgetParam param);

  /**
   * 新品预算列表合计接口
   */
  NewProductBudgetResult listSum(NewProductBudgetParam param);

  /**
   * 新品预算确认接口
   */
  ResponseData confirm(NewProductBudgetParam param);

  /**
   * 新品预算修改接口
   */
  ResponseData edit(NewProductBudgetParam param);

  /**
   * 新品预算下拉接口
   */
  List<Map<String, Object>> getDepartmentSelect();

  List<Map<String, Object>> getSecondLabelSelect();

  List<Map<String, Object>> getProductTypeSelect();

  List<Map<String, Object>> getYearSelect();

  List<Map<String, Object>> getVersionSelect(String year);

  /**
   * 新品预算导入
   */
  ResponseData upload(NewProductBudgetParam param, MultipartFile file,List<String> productTypeList,List<String> departmentList);

}
