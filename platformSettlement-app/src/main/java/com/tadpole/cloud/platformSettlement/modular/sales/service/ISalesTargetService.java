package com.tadpole.cloud.platformSettlement.modular.sales.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.SalesTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.SalesTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.SalesTargetResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 销量目标 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
public interface ISalesTargetService extends IService<SalesTarget> {

    /**
     * 销量目标列表接口
     */
    List<SalesTargetResult> findPageBySpec(SalesTargetParam param);

    /**
     * 销量目标列表合计接口
     */
    SalesTargetResult getQuantity(SalesTargetParam param);

    /**
     * 销量目标导出接口
     */
    List<SalesTargetResult> export(SalesTargetParam param);
    /**
     * 销量目标确认接口
     */
    ResponseData confirm(SalesTargetParam param);

    /**
     * 销量目标修改接口
     */
    ResponseData edit(SalesTarget param);

    /**
     * 销量目标下拉接口
     */
    List<Map<String, Object>> getPlatformSelect();

    List<Map<String, Object>> getProductTypeSelect();

    List<Map<String, Object>> getDepartmentSelect();

    List<Map<String, Object>> getTeamSelect();

    List<Map<String, Object>> getCompanyBrandSelect();

    List<Map<String, Object>> getYearSelect();

    List<Map<String, Object>> getVersionSelect(SalesTargetParam param);

    /**
     * 销量目标导入接口
     */
    ResponseData importExcel(String year,String version,String currency,MultipartFile file
            ,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList,List<String> brandList,List<String> jpShops) throws Exception;

    ResponseData uploadAll(String year,String version,String currency,MultipartFile file
            ,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList,List<String> brandList,List<String> jpShops);

    List<String> selectJpShopNameByEbms();

    String getMaxVersion(String year);

    String isVersionConfirmed (String year ,String version);

    Map<String,String> isVersionUnConfirmed (String table, String year);

    String isDiffDimension(String year,String version,String currency,MultipartFile file) throws Exception;

    void deleCurrent(String year,String version);

}
