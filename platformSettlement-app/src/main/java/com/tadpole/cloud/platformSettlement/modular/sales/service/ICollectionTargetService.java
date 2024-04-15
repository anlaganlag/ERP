package com.tadpole.cloud.platformSettlement.modular.sales.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.CollectionTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.CollectionTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.CollectionTargetResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 回款目标 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-04
 */
public interface ICollectionTargetService extends IService<CollectionTarget> {

    /**
     * 回款目标列表接口
     */
    List<CollectionTargetResult> findPageBySpec(CollectionTargetParam param);

    /**
     * 回款目标列表合计接口
     */
    CollectionTargetResult getQuantity(CollectionTargetParam param);

    /**
     * 回款目标导出接口
     */
    List<CollectionTargetResult> export(CollectionTargetParam param);

    /**
     * 回款目标确认接口
     */
    ResponseData confirm(CollectionTargetParam param);

    /**
     * 回款目标修改接口
     */
    ResponseData edit(CollectionTarget param);

    /**
     * 回款目标下拉接口
     */
    List<Map<String, Object>> getPlatformSelect();

    List<Map<String, Object>> getProductTypeSelect();

    List<Map<String, Object>> getDepartmentSelect();

    List<Map<String, Object>> getTeamSelect();

    List<Map<String, Object>> getCompanyBrandSelect();

    List<Map<String, Object>> getYearSelect();

    List<Map<String, Object>> getVersionSelect(CollectionTargetParam param);

    /**
     * 回款目标导入接口
     */
    ResponseData importExcel(String year, String version, String currency, MultipartFile file
            ,List<String> platformList,List<String> departmentTeamList,List<String> productTypeList,List<String> brandList,List<String> jpShops);
}
