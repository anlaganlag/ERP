package com.tadpole.cloud.platformSettlement.modular.sales.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.SalesVolumeTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.SalesVolumeTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.SalesVolumeTargetResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 销售额目标 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-04
 */
public interface ISalesVolumeTargetService extends IService<SalesVolumeTarget> {

    /**
     * 销售额目标列表接口
     */
    List<SalesVolumeTargetResult> findPageBySpec(SalesVolumeTargetParam param);

    /**
     * 销量额目标列表合计接口
     */
    SalesVolumeTargetResult getQuantity(SalesVolumeTargetParam param);

    /**
     * 销量额目标导出接口
     */
    List<SalesVolumeTargetResult> export(SalesVolumeTargetParam param);

    /**
     * 销量额目标确认接口
     */
    ResponseData confirm(SalesVolumeTargetParam param);

    /**
     * 销量额目标修改接口
     */
    ResponseData edit(SalesVolumeTarget param);

    /**
     * 销量额目标下拉接口
     */
    List<Map<String, Object>> getPlatformSelect();

    List<Map<String, Object>> getProductTypeSelect();

    List<Map<String, Object>> getDepartmentSelect();

    List<Map<String, Object>> getTeamSelect();

    List<Map<String, Object>> getCompanyBrandSelect();

    List<Map<String, Object>> getYearSelect();

    List<Map<String, Object>> getVersionSelect(SalesVolumeTargetParam param);

    /**
     * 销量额目标导入接口
     */
    ResponseData importExcel(String year, String version, String currency, MultipartFile file
            ,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList,List<String> brandList,List<String> jpShops);

}
