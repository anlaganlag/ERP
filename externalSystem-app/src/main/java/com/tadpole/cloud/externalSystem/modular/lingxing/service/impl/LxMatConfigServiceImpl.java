package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.lingxing.entity.LxMatConfig;
import com.tadpole.cloud.externalSystem.modular.lingxing.mapper.LxMatConfigMapper;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.LxMatConfigParm;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.MatSyncConfigPlan;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.SwitchPlanParm;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LxMatConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 领星物料配置表;(LX_MAT_CONFIG)表服务实现类
 * @author : LSY
 * @date : 2022-12-5
 */
@Service
public class LxMatConfigServiceImpl extends ServiceImpl<LxMatConfigMapper, LxMatConfig> implements LxMatConfigService{
    @Autowired
    private LxMatConfigMapper lxMatConfigMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public LxMatConfig queryById(String id){
        return lxMatConfigMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param lxMatConfig 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "external")
    @Override
    public PageResult<LxMatConfig> paginQuery(LxMatConfigParm lxMatConfig, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LxMatConfig> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(lxMatConfig.getCategory())){
            queryWrapper.eq(LxMatConfig::getCategory, lxMatConfig.getCategory());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getProductType())){
            queryWrapper.eq(LxMatConfig::getProductType, lxMatConfig.getProductType());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxInterfacePlan())){
            queryWrapper.eq(LxMatConfig::getLxInterfacePlan, lxMatConfig.getLxInterfacePlan());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxProductNameTitle())){
            queryWrapper.eq(LxMatConfig::getLxProductNameTitle, lxMatConfig.getLxProductNameTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxProductNameValue())){
            queryWrapper.eq(LxMatConfig::getLxProductNameValue, lxMatConfig.getLxProductNameValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryOneTitle())){
            queryWrapper.eq(LxMatConfig::getLxCategoryOneTitle, lxMatConfig.getLxCategoryOneTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryOneValue())){
            queryWrapper.eq(LxMatConfig::getLxCategoryOneValue, lxMatConfig.getLxCategoryOneValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryTwoTitle())){
            queryWrapper.eq(LxMatConfig::getLxCategoryTwoTitle, lxMatConfig.getLxCategoryTwoTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryTwoValue())){
            queryWrapper.eq(LxMatConfig::getLxCategoryTwoValue, lxMatConfig.getLxCategoryTwoValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryThreeTitle())){
            queryWrapper.eq(LxMatConfig::getLxCategoryThreeTitle, lxMatConfig.getLxCategoryThreeTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryThreeValue())){
            queryWrapper.eq(LxMatConfig::getLxCategoryThreeValue, lxMatConfig.getLxCategoryThreeValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxModelTitle())){
            queryWrapper.eq(LxMatConfig::getLxModelTitle, lxMatConfig.getLxModelTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxModelValue())){
            queryWrapper.eq(LxMatConfig::getLxModelValue, lxMatConfig.getLxModelValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getIsDelete())){
            queryWrapper.eq(LxMatConfig::getIsDelete, lxMatConfig.getIsDelete());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getCreateBy())){
            queryWrapper.eq(LxMatConfig::getCreateBy, lxMatConfig.getCreateBy());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getUpdatedBy())){
            queryWrapper.eq(LxMatConfig::getUpdatedBy, lxMatConfig.getUpdatedBy());
        }
        //2. 执行分页查询
        Page<LxMatConfig> pagin = new Page<>(current , size , true);
        IPage<LxMatConfig> selectResult = lxMatConfigMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return new PageResult<LxMatConfig>(selectResult);
    }

    /**
     * 新增数据
     *
     * @param lxMatConfig 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public LxMatConfig insert(LxMatConfig lxMatConfig){
        lxMatConfigMapper.insert(lxMatConfig);
        return lxMatConfig;
    }

    /**
     * 更新数据
     *
     * @param lxMatConfig 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public LxMatConfig update(LxMatConfigParm lxMatConfig){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<LxMatConfig> chainWrapper = new LambdaUpdateChainWrapper<LxMatConfig>(lxMatConfigMapper);
        if(StrUtil.isNotBlank(lxMatConfig.getCategory())){
            chainWrapper.eq(LxMatConfig::getCategory, lxMatConfig.getCategory());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getProductType())){
            chainWrapper.eq(LxMatConfig::getProductType, lxMatConfig.getProductType());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxInterfacePlan())){
            chainWrapper.eq(LxMatConfig::getLxInterfacePlan, lxMatConfig.getLxInterfacePlan());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxProductNameTitle())){
            chainWrapper.eq(LxMatConfig::getLxProductNameTitle, lxMatConfig.getLxProductNameTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxProductNameValue())){
            chainWrapper.eq(LxMatConfig::getLxProductNameValue, lxMatConfig.getLxProductNameValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryOneTitle())){
            chainWrapper.eq(LxMatConfig::getLxCategoryOneTitle, lxMatConfig.getLxCategoryOneTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryOneValue())){
            chainWrapper.eq(LxMatConfig::getLxCategoryOneValue, lxMatConfig.getLxCategoryOneValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryTwoTitle())){
            chainWrapper.eq(LxMatConfig::getLxCategoryTwoTitle, lxMatConfig.getLxCategoryTwoTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryTwoValue())){
            chainWrapper.eq(LxMatConfig::getLxCategoryTwoValue, lxMatConfig.getLxCategoryTwoValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryThreeTitle())){
            chainWrapper.eq(LxMatConfig::getLxCategoryThreeTitle, lxMatConfig.getLxCategoryThreeTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxCategoryThreeValue())){
            chainWrapper.eq(LxMatConfig::getLxCategoryThreeValue, lxMatConfig.getLxCategoryThreeValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxModelTitle())){
            chainWrapper.eq(LxMatConfig::getLxModelTitle, lxMatConfig.getLxModelTitle());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getLxModelValue())){
            chainWrapper.eq(LxMatConfig::getLxModelValue, lxMatConfig.getLxModelValue());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getIsDelete())){
            chainWrapper.eq(LxMatConfig::getIsDelete, lxMatConfig.getIsDelete());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getCreateBy())){
            chainWrapper.eq(LxMatConfig::getCreateBy, lxMatConfig.getCreateBy());
        }
        if(StrUtil.isNotBlank(lxMatConfig.getUpdatedBy())){
            chainWrapper.eq(LxMatConfig::getUpdatedBy, lxMatConfig.getUpdatedBy());
        }
        //2. 设置主键，并更新
        chainWrapper.set(LxMatConfig::getId, lxMatConfig.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(lxMatConfig.getId());
        }else{
            return null ;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "external")
    @Override
    public boolean deleteById(String id){
        int total = lxMatConfigMapper.deleteById(id);
        return total > 0;
    }


     @DataSource(name = "external")
     @Override
     public List<MatSyncConfigPlan> allPlan(String planName) {
         List<MatSyncConfigPlan> listConfigPlan = new ArrayList<>();
         LambdaQueryWrapper<LxMatConfig> wrapper = new LambdaQueryWrapper<>();
         wrapper.eq(LxMatConfig::getIsDelete, "0");
         List<LxMatConfig> configList = this.baseMapper.selectList(wrapper);
         if (ObjectUtil.isNotEmpty(configList)) {
             Map<String, List<LxMatConfig>> confingListMap = configList.stream().collect(Collectors.groupingBy(LxMatConfig::getLxInterfacePlan));
             for (Map.Entry<String, List<LxMatConfig>> entry : confingListMap.entrySet()) {
                 LxMatConfig config = entry.getValue().get(0);

                 MatSyncConfigPlan configPlan = new MatSyncConfigPlan();
                 BeanUtil.copyProperties(config,configPlan);
                 configPlan.setValue(entry.getKey());
                 listConfigPlan.add(configPlan);
             }
         }

         return listConfigPlan;
     }

    @DataSource(name = "external")
    @Override
    public ResponseData matAllProperty() {
        HashMap<String, String> map = new HashMap<>();
        map.put("物料编码","matCode");
        map.put("物料名称","matName");
        map.put("类目","matCate");
        map.put("运营大类","matOperateCate");
        map.put("公司品牌","matComBrand");
        map.put("产品名称","matProName");
        map.put("适用品牌或对象","matBrand");
        map.put("型号","matModel");
        map.put("款式","matStyle");
        map.put("颜色","matColor");
        map.put("规格单位","matNormUnit");
        map.put("最后更新日期","matLastUpdateDate");
        map.put("套装属性","matSetAttributes");
        map.put("上新模式","matNewestModel");
        map.put("体积","matVolume");
        map.put("包装体积","matBoxVolume");
        map.put("海关编码","matCustomsCode");
        map.put("主材料","matMainMaterial");
        map.put("图案","matPattern");
        map.put("尺码","matSize");
        map.put("包装数量","matPackQty");
        map.put("版本","matVerson");
        map.put("版本描述","matVersonDesc");
        map.put("适用机型","matCompatibleModel");
        map.put("类目编码","matCateCode");
        map.put("运营大类编码","matOperateCateCode");
        map.put("产品名称编码","matProNameCode");
        map.put("款式编码","matStyleCode");
        map.put("主材料编码","matMainMaterialCode");
        map.put("图案编码","matPatternCode");
        map.put("节日标签","matFestLabel");
        map.put("季节标签","matSeasonLabel");
        map.put("色号","matColourNumber");
        map.put("入库标签短描述","matInShortLabelDesc");
        map.put("二级类目","matStyleSecondLabel");
        map.put("物料属性","matPropertity");


//        map.put("长","matNetLength");
//        map.put("宽","matNetWidth");
//        map.put("高","matNetHeight");
//        map.put("包装长","matBoxLong");
//        map.put("包装宽","matBoxWidth");
//        map.put("包装高","matBoxHeight");
//        map.put("直径","matDiameter");
//        map.put("重量单位","matWeightUnit");
//        map.put("净重量","matNetWeight");
//        map.put("包装重量","matBoxWeight");
//        map.put("运输重量","matTranportWeight");
//        map.put("容量单位","matCapacityUnit");
//        map.put("容量","matCapacity");

        return ResponseData.success(map);
    }

    @DataSource(name = "external")
    @Override
    public ResponseData add(LxMatConfig lxMatConfig) {
        ResponseData checkResult = this.checkConfig(lxMatConfig);
        if (checkResult.getSuccess()) {

            LambdaQueryWrapper<LxMatConfig> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(LxMatConfig::getCategory, lxMatConfig.getCategory());
            wrapper.eq(LxMatConfig::getProductType, lxMatConfig.getProductType());
            List<LxMatConfig> configList = this.baseMapper.selectList(wrapper);
            if (ObjectUtil.isNotEmpty(configList)) {
                return ResponseData.error("类目:[" + lxMatConfig.getCategory() + "]" + "--运营大类[" + lxMatConfig.getProductType() + "]--已经配置了，不能重复配置!");
            }

            this.save(lxMatConfig);
            return ResponseData.success();
        }

        return checkResult;

    }


    @DataSource(name = "external")
    @Override
    public ResponseData edit(LxMatConfig updateConfig) {

        ResponseData checkResult = this.checkConfig(updateConfig);
        if (checkResult.getSuccess()) {
            this.saveOrUpdate(updateConfig);
            return ResponseData.success();
        }
        return checkResult;
    }


    @DataSource(name = "external")
    @Override
    public ResponseData switchPlan(SwitchPlanParm switchPlanParm) {
        List<MatSyncConfigPlan> planList = this.allPlan(null);
        MatSyncConfigPlan syncConfig = planList.stream().filter(p -> p.getLxInterfacePlan().equals(switchPlanParm.getPlanName())).findAny().orElse(null);
        if (ObjectUtil.isNull(syncConfig)) {
            return ResponseData.error("配置方案[" + switchPlanParm.getPlanName() + "]未找到相应配置");
        }

        List<String> idList = switchPlanParm.getIdList();
        LambdaQueryWrapper<LxMatConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(LxMatConfig::getId, idList);
        List<LxMatConfig> configList = this.baseMapper.selectList(queryWrapper);
        for (LxMatConfig matConfig : configList) {
            BeanUtils.copyProperties(syncConfig, matConfig);
            matConfig.setUpdatedTime(new Date());
            matConfig.setVersion(matConfig.getVersion()+1);
//            matConfig.setLxInterfacePlan(syncConfig.getLxInterfacePlan());
//            matConfig.setLxProductNameTitle(syncConfig.getLxProductNameTitle());
//            matConfig.setLxProductNameValue(syncConfig.getLxProductNameValue());
//            matConfig.setLxCategoryOneTitle(syncConfig.getLxCategoryOneTitle());
//            matConfig.setLxCategoryOneValue(syncConfig.getLxCategoryOneValue());
//            matConfig.setLxCategoryTwoTitle(syncConfig.getLxCategoryTwoTitle());
//            matConfig.setLxCategoryTwoValue(syncConfig.getLxCategoryTwoValue());
//            matConfig.setLxCategoryThreeTitle(syncConfig.getLxCategoryThreeTitle());
//            matConfig.setLxCategoryThreeValue(syncConfig.getLxCategoryThreeValue());
//            matConfig.setLxModelTitle(syncConfig.getLxModelTitle());
//            matConfig.setLxModelValue(syncConfig.getLxModelValue());
        }
        this.saveOrUpdateBatch(configList);
        return ResponseData.success();
    }


    @DataSource(name = "external")
    private ResponseData checkConfig(LxMatConfig lxMatConfig) {
        List<MatSyncConfigPlan> planList = this.allPlan(null);
        if (ObjectUtil.isEmpty(planList)) {
            return ResponseData.success();
        }
        String planName = lxMatConfig.getLxInterfacePlan();
        List<MatSyncConfigPlan> plans = planList.stream().filter(p -> planName.equals(p.getLxInterfacePlan())).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(plans)) {//有相同的方案名称，则所有配置选项都要一样
            MatSyncConfigPlan configPlan = plans.get(0);
            if (!(configPlan.getLxInterfacePlan().equals(lxMatConfig.getLxInterfacePlan())
                    && configPlan.getLxProductNameValue().equals(lxMatConfig.getLxProductNameValue())
                    && configPlan.getLxCategoryOneValue().equals(lxMatConfig.getLxCategoryOneValue())
                    && configPlan.getLxCategoryTwoValue().equals(lxMatConfig.getLxCategoryTwoValue())
                    && configPlan.getLxCategoryThreeValue().equals(lxMatConfig.getLxCategoryThreeValue())
                    && configPlan.getLxModelValue().equals(lxMatConfig.getLxModelValue()))) {
                return ResponseData.error("新增的配置与已有的方案配置不一样，方案配置【" + planName + "】");
            }
        } else {//不存在相同的方案名称，判断所有配置是否相同
            for (MatSyncConfigPlan configPlan : planList) {
                if (       configPlan.getLxInterfacePlan().equals(lxMatConfig.getLxInterfacePlan())
                        && configPlan.getLxProductNameValue().equals(lxMatConfig.getLxProductNameValue())
                        && configPlan.getLxCategoryOneValue().equals(lxMatConfig.getLxCategoryOneValue())
                        && configPlan.getLxCategoryTwoValue().equals(lxMatConfig.getLxCategoryTwoValue())
                        && configPlan.getLxCategoryThreeValue().equals(lxMatConfig.getLxCategoryThreeValue())
                        && configPlan.getLxModelValue().equals(lxMatConfig.getLxModelValue())) {//有相同的配置，但是名字不一样
                    return ResponseData.error("本次新增方案对应的具体组合字段配置，系统已经存在，方案名称【" + configPlan.getLxInterfacePlan() + "】");
                }
            }
        }
        return ResponseData.success();
    }



}
