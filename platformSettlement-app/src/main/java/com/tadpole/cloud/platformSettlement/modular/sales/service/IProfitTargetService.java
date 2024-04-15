package com.tadpole.cloud.platformSettlement.modular.sales.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.ProfitTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.ProfitTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.ProfitTargetResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 利润目标 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-07
 */
public interface IProfitTargetService extends IService<ProfitTarget> {

    /**
     * 利润目标列表接口
     */
    List<ProfitTargetResult> findPageBySpec(ProfitTargetParam param);

    /**
     * 利润目标列表合计接口
     */
    ProfitTargetResult getQuantity(ProfitTargetParam param);

    /**
     * 利润目标导出接口
     */
    List<ProfitTargetResult> export(ProfitTargetParam param);
    /**
     * 利润目标确认接口
     */
    ResponseData confirm(ProfitTargetParam param);

    /**
     * 利润目标修改接口
     */
    ResponseData edit(ProfitTarget param);

    /**
     * 利润目标下拉接口
     */
    List<Map<String, Object>> getPlatformSelect();

    List<Map<String, Object>> getProductTypeSelect();

    List<Map<String, Object>> getDepartmentSelect();

    List<Map<String, Object>> getTeamSelect();

    List<Map<String, Object>> getCompanyBrandSelect();

    List<Map<String, Object>> getYearSelect();

    List<Map<String, Object>> getVersionSelect(ProfitTargetParam param);

    /**
     * 利润目标导入接口
     */
    ResponseData importExcel(String year, String version, String currency, MultipartFile file
            ,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList,List<String> brandList,List<String> jpShops);

}
