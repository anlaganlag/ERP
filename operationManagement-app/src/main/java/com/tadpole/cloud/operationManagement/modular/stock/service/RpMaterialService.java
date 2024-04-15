package com.tadpole.cloud.operationManagement.modular.stock.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.WarehouseSixCodeParam;
import cn.stylefeng.guns.cloud.system.api.base.model.result.WarehouseSixCodeResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SysMaterialParam;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;

import java.util.List;
import java.util.Map;

/**
 * 物料属性基础数据 服务类
 *
 * @author cyt
 * @since 2022-06-21
 */
public interface RpMaterialService extends IService<Material> {

    /**
     * 获取类目下拉列表
     */
    List<Map<String, Object>> getCategorySelect();

    /**
     * 获取运营大类下拉列表
     */
    List<Map<String, Object>> getProductTypeSelect(String category);

    /**
     * 获取物料产品名称下拉列表
     */
    List<Map<String, Object>> getProductSelect(String productType);

    /**
     * 获取物料款式下拉列表
     */
    List<Map<String, Object>> getStyleSelect(String product);

    /**
     * 获取主材料名称下拉列表
     *
     */
    List<Map<String, Object>> getMainMaterialSelect(String style);

    /**
     * 获取主材料名称下拉列表
     *
     */
    List<Map<String, Object>> getPatternSelect(String material);

    /**
     * 获取公司品牌下拉列表
     *
     */
    List<Map<String, Object>> getBrandSelect();

    /**
     * 获取适用品牌下拉列表
     *
     */
    List<Map<String, Object>> getFitBrandSelect();

    /**
     * 获取机型下拉列表
     *
     */
    List<Map<String, Object>> getModelSelect(String productName);

    /**
     * 获取颜色下拉列表
     *
     */
    List<Map<String, Object>> getColorSelect(String model);

    /**
     * 获取尺码下拉列表
     *
     */
    List<Map<String, Object>> getSizeSelect();

    /**
     * 获取包装数量下拉列表
     *
     */
    List<Map<String, Object>> getPackSelect();

    /**
     * 获取类型下拉列表
     *
     */
    List<Map<String, Object>> getTypeSelect(String productName);

    /**
     * 获取版本下拉列表
     *
     */
    List<Map<String, Object>> getVersionSelect();

    List<Map<String, Object>> StyleSecondLabel();

    List<Map<String, Object>> getLogisticsModeSelect();


    /**
     * 产品线权限下拉
     *
     */
    List<String> selectCodeByPerson(SysMaterialParam paramCondition);

    List<String> selectProductTypeByPerson(SysMaterialParam paramCondition);

    List<String> selectProductNameByPerson(SysMaterialParam paramCondition);

    List<String> selectStyleByPerson(SysMaterialParam paramCondition);

    List<String> selectMaterialByPerson(SysMaterialParam paramCondition);

    List<String> selectPatternByPerson(SysMaterialParam paramCondition);

    List<String> selectCompanyBrandByPerson(SysMaterialParam paramCondition);

    List<String> selectBrandByPerson(SysMaterialParam paramCondition);

    List<String> selectModelByPerson(SysMaterialParam paramCondition);

    List<String> selectColorByPerson(SysMaterialParam paramCondition);

    List<String> selectSizeByPerson(SysMaterialParam paramCondition);

    List<String> selectPackingByPerson(SysMaterialParam paramCondition);

    List<String> selectVersionByPerson(SysMaterialParam paramCondition);

    List<String>  selectStyleSecondLabelByPerson(SysMaterialParam param);

    /**
     * 海外仓六位码查询
     */
    PageResult<WarehouseSixCodeResult> sixCodeListPage(WarehouseSixCodeParam param);

    /**
     * 海外仓六位码导出
     * @param param
     * @return
     */
    List<WarehouseSixCodeResult> sixCodeExport(WarehouseSixCodeParam param);

     List<Map<String, Object>>  getFestivalLabel(String model);

     List<Map<String, Object>>  getSeasonLabel(String model);

    List<Map<String, Object>> getProductName(String productName);

    List<String> getMaterialCode();

    Material getMaterialInfo(String materialCode);

    Material queryMaterialInfo(String materialCode);

    List<Material> getMaterials(List<String> materialCode);

    ResponseData isValidateMater(List<String> matCodes,String controllerName);
}

