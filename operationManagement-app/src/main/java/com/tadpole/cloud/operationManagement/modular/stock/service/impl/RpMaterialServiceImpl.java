package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.WarehouseSixCodeParam;
import cn.stylefeng.guns.cloud.system.api.base.model.result.WarehouseSixCodeResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.MaterialMapper;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.RpMaterialMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SysMaterialParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.RpMaterialService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物料属性基础数据 服务实现类
 *
 * @author cyt
 * @since 2022-06-21
 */
@Service
public class RpMaterialServiceImpl extends ServiceImpl<MaterialMapper, Material>
        implements RpMaterialService {

    @Autowired
    private RpMaterialService materialService;

    @Resource
    private RpMaterialMapper mapper;

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getCategorySelect() {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("category").groupBy("category");
        return this.baseMapper.selectMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getProductTypeSelect(String category) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper =
                materialWrapper.select("product_type")
                        .eq(category != null && category.length() > 0, "category", category)
                        .groupBy("product_type");
        return this.baseMapper.selectMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getProductSelect(String productType) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper =
                materialWrapper
                        .select("product_name")
                        .eq(productType != null && productType.length() > 0, "product_type", productType)
                        .groupBy("product_name");
        return this.baseMapper.selectMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getStyleSelect(String product) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("style").eq(product != null && product.length() > 0, "product_name", product).groupBy("style");
        return this.baseMapper.selectMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getMainMaterialSelect(String style) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper =
                materialWrapper.select("main_material").eq(style != null && style.length() > 0, "style", style).groupBy("main_material");
        return materialService.listMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getPatternSelect(String material) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper =
                materialWrapper.select("design").eq(material != null && material.length() > 0, "main_material", material).groupBy("design");
        return materialService.listMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getBrandSelect() {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("company_brand").groupBy("company_brand");
        return materialService.listMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getFitBrandSelect() {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("fit_brand").groupBy("fit_brand");
        return materialService.listMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getModelSelect(String productName) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("model").
                eq(productName != null && productName.length() > 0, "product_name", productName).or()
                .eq(productName != null && productName.length() > 0, "product_type", productName)
                .groupBy("model");
        return materialService.listMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getColorSelect(String model) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();

        materialWrapper = materialWrapper.select("color").eq(model != null && model.length() > 0, "model", model).groupBy("color");
        return materialService.listMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getSizeSelect() {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("sizes").groupBy("sizes");
        return materialService.listMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getPackSelect() {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("packing").groupBy("packing");
        return materialService.listMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getTypeSelect(String productName) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper =
                materialWrapper
                        .select("type")
                        .eq(productName != null && productName.length() > 0, "product_name", productName)
                        .groupBy("type");
        return this.baseMapper.selectMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getVersionSelect() {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("version").groupBy("version");
        return materialService.listMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> StyleSecondLabel() {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("style_second_label").groupBy("style_second_label");
        return this.baseMapper.selectMaps(materialWrapper);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getLogisticsModeSelect() {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("logistics_mode").groupBy("logistics_mode");
        return this.baseMapper.selectMaps(materialWrapper);
    }

    @Override
    @DataSource(name = "basicdata")
    public PageResult<WarehouseSixCodeResult> sixCodeListPage(WarehouseSixCodeParam param) {
        Page pageContext = param.getPageContext();
        IPage<WarehouseSixCodeResult> page = mapper.sixCodeListPage(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "basicdata")
    @Override
    public List<WarehouseSixCodeResult> sixCodeExport(WarehouseSixCodeParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<WarehouseSixCodeResult> page = mapper.sixCodeListPage(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getFestivalLabel(String model) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("festival_label").groupBy("festival_label");
        List<Map<String, Object>> maps = materialService.listMaps(materialWrapper);
        maps.remove(null);
        return maps;
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getSeasonLabel(String model) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("season_label").groupBy("season_label");
        List<Map<String, Object>> maps = materialService.listMaps(materialWrapper);
        maps.remove(null);
        return maps;
    }

    @DataSource(name = "basicdata")
    @Override
    public List<Map<String, Object>> getProductName(String productName) {
        QueryWrapper<Material> materialWrapper = new QueryWrapper<>();
        materialWrapper = materialWrapper.select("product_name").groupBy("product_name");
        List<Map<String, Object>> maps = materialService.listMaps(materialWrapper);
        maps.remove(null);
        return maps;
    }

    @DataSource(name = "basicdata")
    @Override
    public List<String> getMaterialCode() {
        return mapper.selectCode();
    }


    @DataSource(name = "basicdata")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Material getMaterialInfo(String materialCode) {
        LambdaQueryWrapper<Material> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Material::getMaterialCode, materialCode);
        List<Material> materialList = this.baseMapper.selectList(wrapper);

        if (ObjectUtil.isNotEmpty(materialList)) {
            return materialList.get(0);
        }

        return null;
    }

    @DataSource(name = "basicdata")
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public Material queryMaterialInfo(String materialCode) {
        LambdaQueryWrapper<Material> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Material::getMaterialCode, materialCode);
        List<Material> materialList = this.baseMapper.selectList(wrapper);

        if (ObjectUtil.isNotEmpty(materialList)) {
            return materialList.get(0);
        }

        return null;
    }


    @DataSource(name = "basicdata")
    @Override
    public List<Material> getMaterials(List<String> materialCodes) {
        LambdaQueryWrapper<Material> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Material::getMaterialCode, materialCodes);
        List<Material> materialList = this.baseMapper.selectList(wrapper);

        if (materialList.size() > 0) {
            return materialList;
        }
        return null;
    }

    @Override
    @DataSource(name = "basicdata")
    public ResponseData isValidateMater(List<String> matCodes, String controllerName) {

        List<Material> materialList = this.getMaterials(matCodes);

        if (CollectionUtil.isEmpty(materialList)) {
            return ResponseData.error(controllerName + "_" +"导入失败，原因：未匹配到物料，请确认物料编码。");
        }

        //验证 导入的物料 在 数据库中是否存在
        if (matCodes.size() != materialList.size()) {
            matCodes.removeAll(materialList.stream().map(material -> material.getMaterialCode()).collect(Collectors.toList()));
            return ResponseData.error(controllerName + "_" +"导入失败，原因：存在未匹配到的物料" + JSON.toJSONString(matCodes));
        }

        //验证 导入的物料 是否 可销售
        List<Material> matAvailList = materialList.stream().filter(mat -> "否".equals(mat.getMatIsAvailableSale())).collect(Collectors.toList());
        if (matAvailList.size() > 0) {
            List<String> mats = matAvailList.stream().map(mat -> mat.getMaterialCode()).collect(Collectors.toList());
            return ResponseData.error(controllerName + "_" +"导入失败，原因：存在不可销售的物料" + JSON.toJSONString(mats));
        }
        //验证 导入的物料 是否 禁用
        List<Material> disableList = materialList.stream().filter(mat -> "禁用".equals(mat.getStatus())).collect(Collectors.toList());
        if (disableList.size() > 0) {
            List<String> mats = disableList.stream().map(mat -> mat.getMaterialCode()).collect(Collectors.toList());
            return ResponseData.error(controllerName + "_" +"导入失败，原因：存在禁用的物料" + JSON.toJSONString(mats));
        }
        return ResponseData.success(materialList);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectCodeByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectCodeByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectProductTypeByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectProductTypeByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectProductNameByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectProductNameByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectStyleByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectStyleByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectMaterialByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectMaterialByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectPatternByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectPatternByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectCompanyBrandByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectCompanyBrandByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectBrandByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectBrandByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectModelByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectModelByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectColorByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectColorByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectSizeByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectSizeByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectPackingByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectPackingByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectVersionByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectVersionByPerson(paramCondition);
    }

    @DataSource(name = "stocking")
    @Override
    public List<String> selectStyleSecondLabelByPerson(SysMaterialParam paramCondition) {
        paramCondition.setOperator(this.getUserAccount());
        return mapper.selectStyleSecondLabelByPerson(paramCondition);
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private String getUserAccount() {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return loginUser.getAccount();
    }

}
