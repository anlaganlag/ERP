package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.libs.util.ReflectUtils;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.TbComMateriel;
import com.tadpole.cloud.externalSystem.modular.lingxing.entity.LxMatConfig;
import com.tadpole.cloud.externalSystem.modular.lingxing.entity.LxMatSync;
import com.tadpole.cloud.externalSystem.modular.lingxing.mapper.LxMatConfigMapper;
import com.tadpole.cloud.externalSystem.modular.lingxing.mapper.LxMatSyncMapper;
import com.tadpole.cloud.externalSystem.api.lingxing.model.parm.LxMatSyncParm;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.product.ProductReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.BrandResp;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.CategoryResp;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.LinXingCategoryAddResp;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingProductService;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LxMatConfigService;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LxMatSyncService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 领星物料同步;(LX_MAT_SYNC)表服务实现类
 * @author : LSY
 * @date : 2022-12-5
 */
@Slf4j
@Service
public class LxMatSyncServiceImpl extends ServiceImpl<LxMatSyncMapper, LxMatSync> implements LxMatSyncService{
    @Autowired
    private LxMatSyncMapper lxMatSyncMapper;

    @Autowired
    private LxMatConfigMapper lxMatConfigMapper;

    @Autowired
    private LxMatConfigService lxMatConfigService;

    @Autowired
    LingXingProductService lingXingProductService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public LxMatSync queryById(String id){
        return lxMatSyncMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param lxMatSync 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "external")
    @Override
    public PageResult<LxMatSync> paginQuery(LxMatSyncParm lxMatSync, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LxMatSync> queryWrapper = new LambdaQueryWrapper<>();
//        if(StrUtil.isNotBlank(lxMatSync.getProductName())){
//            queryWrapper.eq(LxMatSync::getProductName, lxMatSync.getProductName());
//        }
        if (ObjectUtil.isNotNull(lxMatSync.getStatus()) && lxMatSync.getStatus() >= 0) {
            queryWrapper.eq(LxMatSync::getStatus, lxMatSync.getStatus());
        }

        if(StrUtil.isNotBlank(lxMatSync.getMatCode())){
            queryWrapper.eq(LxMatSync::getMatCode, lxMatSync.getMatCode());
        }
        if(StrUtil.isNotBlank(lxMatSync.getSku())){
            queryWrapper.eq(LxMatSync::getSku, lxMatSync.getSku());
        }

        if(StrUtil.isNotBlank(lxMatSync.getBizType())){
            queryWrapper.eq(LxMatSync::getBizType, lxMatSync.getBizType());
        }
//        if(StrUtil.isNotBlank(lxMatSync.getCategoryOne())){
//            queryWrapper.eq(LxMatSync::getCategoryOne, lxMatSync.getCategoryOne());
//        }

//        if(StrUtil.isNotBlank(lxMatSync.getCategoryTwo())){
//            queryWrapper.eq(LxMatSync::getCategoryTwo, lxMatSync.getCategoryTwo());
//        }

//        if(StrUtil.isNotBlank(lxMatSync.getCategoryThree())){
//            queryWrapper.eq(LxMatSync::getCategoryThree, lxMatSync.getCategoryThree());
//        }

//        if(StrUtil.isNotBlank(lxMatSync.getModel())){
//            queryWrapper.eq(LxMatSync::getModel, lxMatSync.getModel());
//        }
//        if(StrUtil.isNotBlank(lxMatSync.getBrand())){
//            queryWrapper.eq(LxMatSync::getBrand, lxMatSync.getBrand());
//        }

//        if(StrUtil.isNotBlank(lxMatSync.getRemark())){
//            queryWrapper.eq(LxMatSync::getRemark, lxMatSync.getRemark());
//        }
//        if(StrUtil.isNotBlank(lxMatSync.getIsDelete())){
//            queryWrapper.eq(LxMatSync::getIsDelete, lxMatSync.getIsDelete());
//        }
        if(StrUtil.isNotBlank(lxMatSync.getSyncType())){
            queryWrapper.eq(LxMatSync::getSyncType, lxMatSync.getSyncType());
        }
        if(StrUtil.isNotBlank(lxMatSync.getSyncStatus())){
            queryWrapper.eq(LxMatSync::getSyncStatus, lxMatSync.getSyncStatus());
        }
        if(StrUtil.isNotBlank(lxMatSync.getSyncPlan())){
            queryWrapper.eq(LxMatSync::getSyncPlan, lxMatSync.getSyncPlan());
        }

        //同步时间
        if(ObjectUtil.isNotNull(lxMatSync.getSyncStartTime())){
            queryWrapper.ge(LxMatSync::getSyncTime, lxMatSync.getSyncStartTime());
        }
        if(ObjectUtil.isNotNull(lxMatSync.getSyncEndTime())){
            queryWrapper.lt(LxMatSync::getSyncTime, DateUtil.offsetDay(lxMatSync.getSyncEndTime(), 1));
        }

        //物料创建时间
        if(ObjectUtil.isNotNull(lxMatSync.getMatCreateStartTime())){
            queryWrapper.ge(LxMatSync::getMatCreateTime, lxMatSync.getMatCreateStartTime());
        }
        if(ObjectUtil.isNotNull(lxMatSync.getMatCreateEndTime())){
            queryWrapper.lt(LxMatSync::getMatCreateTime, DateUtil.offsetDay(lxMatSync.getMatCreateEndTime(), 1));
        }

        //物料最后更新时间
        if(ObjectUtil.isNotNull(lxMatSync.getMatUpdateStartTime())){
            queryWrapper.ge(LxMatSync::getMatUpdateTime, lxMatSync.getMatUpdateStartTime());
        }
        if(ObjectUtil.isNotNull(lxMatSync.getMatUpdateEndTime())){
            queryWrapper.lt(LxMatSync::getMatUpdateTime, DateUtil.offsetDay(lxMatSync.getMatUpdateEndTime(), 1));
        }

        queryWrapper.orderByDesc(LxMatSync::getMatCreateTime);

        //2. 执行分页查询
        Page<LxMatSync> pagin = new Page<>(current , size , true);
        IPage<LxMatSync> selectResult = lxMatSyncMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        //return pagin;
        return new PageResult<LxMatSync>(selectResult);
    }

    /**
     * 新增数据
     *
     * @param lxMatSync 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public LxMatSync insert(LxMatSync lxMatSync){
        lxMatSyncMapper.insert(lxMatSync);
        return lxMatSync;
    }

    /**
     * 更新数据
     *
     * @param lxMatSync 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public LxMatSync update(LxMatSyncParm lxMatSync){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<LxMatSync> chainWrapper = new LambdaUpdateChainWrapper<LxMatSync>(lxMatSyncMapper);
        if(StrUtil.isNotBlank(lxMatSync.getProductName())){
            chainWrapper.eq(LxMatSync::getProductName, lxMatSync.getProductName());
        }
        if(StrUtil.isNotBlank(lxMatSync.getMatCode())){
            chainWrapper.eq(LxMatSync::getMatCode, lxMatSync.getMatCode());
        }
        if(StrUtil.isNotBlank(lxMatSync.getSku())){
            chainWrapper.eq(LxMatSync::getSku, lxMatSync.getSku());
        }

        if(StrUtil.isNotBlank(lxMatSync.getBizType())){
            chainWrapper.eq(LxMatSync::getBizType, lxMatSync.getBizType());
        }
        if(StrUtil.isNotBlank(lxMatSync.getCategoryOne())){
            chainWrapper.eq(LxMatSync::getCategoryOne, lxMatSync.getCategoryOne());
        }

        if(StrUtil.isNotBlank(lxMatSync.getCategoryTwo())){
            chainWrapper.eq(LxMatSync::getCategoryTwo, lxMatSync.getCategoryTwo());
        }

        if(StrUtil.isNotBlank(lxMatSync.getCategoryThree())){
            chainWrapper.eq(LxMatSync::getCategoryThree, lxMatSync.getCategoryThree());
        }

        if(StrUtil.isNotBlank(lxMatSync.getModel())){
            chainWrapper.eq(LxMatSync::getModel, lxMatSync.getModel());
        }
        if(StrUtil.isNotBlank(lxMatSync.getBrand())){
            chainWrapper.eq(LxMatSync::getBrand, lxMatSync.getBrand());
        }

        if(StrUtil.isNotBlank(lxMatSync.getRemark())){
            chainWrapper.eq(LxMatSync::getRemark, lxMatSync.getRemark());
        }
        if(StrUtil.isNotBlank(lxMatSync.getIsDelete())){
            chainWrapper.eq(LxMatSync::getIsDelete, lxMatSync.getIsDelete());
        }
        if(StrUtil.isNotBlank(lxMatSync.getSyncType())){
            chainWrapper.eq(LxMatSync::getSyncType, lxMatSync.getSyncType());
        }
        if(StrUtil.isNotBlank(lxMatSync.getSyncStatus())){
            chainWrapper.eq(LxMatSync::getSyncStatus, lxMatSync.getSyncStatus());
        }
        //2. 设置主键，并更新
        chainWrapper.set(LxMatSync::getId, lxMatSync.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(lxMatSync.getId());
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
        int total = lxMatSyncMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 获取同步记录list
     * @param matCodeList
     * @return
     */
    @DataSource(name = "external")
    @Override
    public List<LxMatSync> getSyncList(List<String> matCodeList) {
        List<LxMatSync> list = new ArrayList<>();

        List<List<String>> splitCodes = CollectionUtil.split(matCodeList, 999);
        for (List<String> splitCode : splitCodes) {
            LambdaQueryWrapper<LxMatSync> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(LxMatSync::getMatCode, splitCode);
            wrapper.eq(LxMatSync::getSyncStatus, "1");
            list.addAll(this.lxMatSyncMapper.selectList(wrapper));
        }
        return list;
    }

    /**
     * 同步物料到领星ERP
     * @param filterResultData
     * @return
     */


    @DataSource(name = "external")
//    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseData initLxErpData(List<TbComMateriel> filterResultData) {
        //获取所有分类数据
        List<CategoryResp> allCategory = lingXingProductService.getAllCategory();
        if (ObjectUtil.isEmpty(allCategory)) {
            return ResponseData.error("获取分类数据失败");
        }

        //获取所有品牌数据
        List<BrandResp> allBrand = lingXingProductService.getAllBrand();
        if (ObjectUtil.isEmpty(allBrand)) {
            return ResponseData.error("获取所有品牌数据失败");
        }

        //1--获取同步到领星接口物料配置规则
        List<String> operateCateList = filterResultData.stream().map(m -> m.getMatOperateCate())
                .collect(Collectors.toSet()).stream().collect(Collectors.toList());

        LambdaQueryWrapper<LxMatConfig> configWrapper = new LambdaQueryWrapper<>();
        configWrapper.in(LxMatConfig::getProductType, operateCateList);
        configWrapper.eq(LxMatConfig::getIsDelete,'0' );
//        configWrapper.in(LxMatConfig::getCategory, operateCateList);
//        List<MatSyncConfigPlan> planList = lxMatConfigService.allPlan(null);
        List<LxMatConfig> matConfigList = lxMatConfigMapper.selectList(configWrapper);
        if (ObjectUtil.isEmpty(matConfigList)) {
            return ResponseData.error("未找到物料同步到领星ERP的配置");
        }

        Map<String, List<LxMatConfig>> categoryCnfigMap = matConfigList.stream().collect(Collectors.groupingBy(LxMatConfig::getProductType));
        Map<String, List<BrandResp>> brandMap = allBrand.stream().collect(Collectors.groupingBy(BrandResp::getTitle));
//        Map<String, List<CategoryResp>> categoryMap = allCategory.stream().collect(Collectors.groupingBy(CategoryResp::getTitle));

        //2--根据配置规则，生成一级目录，二级目录，三级目录，产品名称，型号，品牌数据 ReflectUtils.getValue
        List<String> noConfigMatCodeList = new ArrayList<>();
        List<LxMatSync > matSyncList = new ArrayList<>();
        for (TbComMateriel tbComMateriel : filterResultData) {
            List<LxMatConfig> configs = categoryCnfigMap.get(tbComMateriel.getMatOperateCate());
            if (ObjectUtil.isEmpty(configs)) {
                noConfigMatCodeList.add(tbComMateriel.getMatCode());
                continue;
            }
            LxMatConfig lxMatConfig = configs.get(0);
            List<BrandResp> brandRespList = brandMap.get(tbComMateriel.getMatComBrand());
            BrandResp brand=null;
            if (ObjectUtil.isNotEmpty(brandRespList)) {
                brand = brandRespList.get(0);
            }

            LxMatSync lxMatSync= this.generateSyncInfo(tbComMateriel, lxMatConfig,brand,allCategory);
            matSyncList.add(lxMatSync);

        }

        this.saveBatch(matSyncList);


        List<String > failMatCodeList = new ArrayList<>();

        for (LxMatSync lxMatSync : matSyncList) {
            ResponseData syncResult = this.syncToLxErp(lxMatSync,allCategory);
            if (! syncResult.getSuccess()) {
                log.error("同步物料[{}]到领星erp失败:{}",lxMatSync.getMatCode(),JSON.toJSONString(syncResult));
                failMatCodeList.add(lxMatSync.getMatCode());
            }
            log.info("同步物料[{}]到领星erp成功:{}",lxMatSync.getMatCode(),JSON.toJSONString(syncResult));
        }


        HashMap<String, List<String>> resutlData = new HashMap<>();

        boolean resultFlag=true;

        if (ObjectUtil.isNotEmpty(failMatCodeList)) {
            resutlData.put("failMatCode", failMatCodeList);
            resultFlag=false;
        }

        if (ObjectUtil.isNotEmpty(noConfigMatCodeList)) {
            resutlData.put("noConfigMatCode", noConfigMatCodeList);
            resultFlag=false;
        }

        if (!resultFlag) {
             return ResponseData.error(500,"同步物料到领星erp有部分数据失败",resutlData);
        }
        return ResponseData.success();
    }


    @DataSource(name = "external")
    @Override
    public LxMatSync generateSyncInfo(TbComMateriel tbComMateriel, LxMatConfig matConfig,BrandResp brand,List<CategoryResp> allCategory) {

        LxMatSync lxMatSync = new LxMatSync();
        lxMatSync.setId(IdWorker.getIdStr());
        lxMatSync.setMatCode(tbComMateriel.getMatCode());
        lxMatSync.setSku(tbComMateriel.getMatCode());
        lxMatSync.setMatCreateTime(tbComMateriel.getMatCreatDate());//
        lxMatSync.setMatUpdateTime(tbComMateriel.getMatLastUpdateDate());//
        lxMatSync.setBizType("新增");
        lxMatSync.setRemark(""+matConfig.getVersion());
        lxMatSync.setSyncPlan(matConfig.getLxInterfacePlan());
        lxMatSync.setSyncPlanInfo(JSON.toJSONString(matConfig));


        //型号
        String modelValue= this.getMergeValue(matConfig.getLxModelValue(), tbComMateriel);
        lxMatSync.setModel(modelValue);

        //品牌
        lxMatSync.setBrand(tbComMateriel.getMatComBrand());
        if (ObjectUtil.isNotNull(brand)) {
            lxMatSync.setBrandId(brand.getBid());
        }

        //状态
        if ("启用".equals(tbComMateriel.getMatStatus()) || "淘汰".equals(tbComMateriel.getMatStatus())) {
            lxMatSync.setStatus(1);
        } else if ("禁用".equals(tbComMateriel.getMatStatus())) {
            lxMatSync.setStatus(0);
        }



        //产品名称：
       String productName= this.getMergeValue(matConfig.getLxProductNameValue(), tbComMateriel);
        lxMatSync.setProductName(productName);

        //目录名称
        String categoryOne= this.getMergeValue(matConfig.getLxCategoryOneValue(), tbComMateriel);
        lxMatSync.setCategoryOne(categoryOne);

        String categoryTwo= this.getMergeValue(matConfig.getLxCategoryTwoValue(), tbComMateriel);
        lxMatSync.setCategoryTwo(categoryTwo);

        String categoryThree= this.getMergeValue(matConfig.getLxCategoryThreeValue(), tbComMateriel);
        lxMatSync.setCategoryThree(categoryThree);

        CategoryResp oneFind = allCategory.stream().filter(c -> c.getTitle().equals(categoryOne)
                                                            && c.getParent_cid()==0 ).findAny().orElse(null);
        //一级目录ID
        if (ObjectUtil.isNotNull(oneFind)) {
            lxMatSync.setCategoryOneId(oneFind.getCid());
        } else {
            return lxMatSync;
        }

        //二级目录ID
        if (ObjectUtil.isNotNull(lxMatSync.getCategoryOneId())) { //一级目录有找到
            CategoryResp twoFind = allCategory.stream().filter(c ->c.getParent_cid().equals(lxMatSync.getCategoryOneId())
                    && c.getTitle().equals(categoryTwo)).findAny().orElse(null);
            if (ObjectUtil.isNotNull(twoFind)) {
                lxMatSync.setCategoryTwoId(twoFind.getCid());
            }else {
                return lxMatSync;
            }
        }

        //三级目录ID
        if (ObjectUtil.isNotNull(lxMatSync.getCategoryTwoId())) { //二级目录有找到
            CategoryResp threeFind = allCategory.stream().filter(c ->c.getParent_cid().equals(lxMatSync.getCategoryTwoId())
                    && c.getTitle().equals(categoryThree)).findAny().orElse(null);
            if (ObjectUtil.isNotNull(threeFind)) {
                lxMatSync.setCategoryThreeId(threeFind.getCid());
            }else {
                return lxMatSync;
            }
        }

        return lxMatSync;
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncToLxErp(LxMatSync lxMatSync , List<CategoryResp> allCategory) {
        if ("1".equals(lxMatSync.getSyncStatus())) {
            return ResponseData.success("已经同步成功了，无需再次同步");
        }

        //必传字段校验 分类名称必填
        if (StrUtil.isEmpty(lxMatSync.getCategoryOne()) || StrUtil.isEmpty(lxMatSync.getCategoryTwo()) || StrUtil.isEmpty(lxMatSync.getCategoryThree())) {
            lxMatSync.setSyncStatus("0");
            lxMatSync.setSyncResultMsg("按照接口配置方案生成1,2,3分类信息,物料缺少分类信息【1,2,3分类其中一个或多个分类信息】");
            lxMatSync.setUpdatedTime(new Date());
            lxMatSync.setSyncTime(new Date());
            this.updateById(lxMatSync);
            return ResponseData.error("按照接口配置方案生成1,2,3分类信息,物料缺少分类信息【1,2,3分类其中一个或多个分类信息】");
        }




        if (ObjectUtil.isNull(lxMatSync.getCategoryOneId())) {//
            CategoryResp category = this.getLocalCacheCategory(allCategory, 0, lxMatSync.getCategoryOne());
            if (ObjectUtil.isNotNull(category)) {
                lxMatSync.setCategoryOneId(category.getCid());
            } else {
                LinXingCategoryAddResp addResult = lingXingProductService.addCategory(0, lxMatSync.getCategoryOne());

                if (ObjectUtil.isNotNull(addResult) && addResult.isSuccess() && StrUtil.isNotEmpty(addResult.getId())) {
                    lxMatSync.setCategoryOneId(Integer.valueOf(addResult.getId()));
                    CategoryResp newCategory = new CategoryResp();
                    newCategory.setCid(Integer.valueOf(addResult.getId()));
                    newCategory.setTitle(lxMatSync.getCategoryOne());
                    newCategory.setParent_cid(0);
                    allCategory.add(newCategory);
                } else {
                    lxMatSync.setSyncResultMsg("物料在领星ERP缺少一级目录，调用模拟也页面人工操作新增类目接口失败:"+ JSON.toJSONString(addResult));
                    return ResponseData.error("物料在领星ERP缺少一级目录，调用模拟也页面人工操作新增类目接口失败"+ JSON.toJSONString(addResult));
                }
            }
        }

        if (ObjectUtil.isNull(lxMatSync.getCategoryTwoId())) {//
            CategoryResp category = this.getLocalCacheCategory(allCategory, lxMatSync.getCategoryOneId(), lxMatSync.getCategoryTwo());
            if (ObjectUtil.isNotNull(category)) {
                lxMatSync.setCategoryTwoId(category.getCid());
            } else {
                LinXingCategoryAddResp addResult = lingXingProductService.addCategory(lxMatSync.getCategoryOneId(), lxMatSync.getCategoryTwo());
                if (ObjectUtil.isNotNull(addResult) && addResult.isSuccess() && StrUtil.isNotEmpty(addResult.getId())) {
                    lxMatSync.setCategoryTwoId(Integer.valueOf(addResult.getId()));
                    CategoryResp newCategory = new CategoryResp();
                    newCategory.setCid(Integer.valueOf(addResult.getId()));
                    newCategory.setTitle(lxMatSync.getCategoryTwo());
                    newCategory.setParent_cid(lxMatSync.getCategoryOneId());
                    allCategory.add(newCategory);
                } else {
                    lxMatSync.setSyncResultMsg("物料在领星ERP缺少二级级目录，调用模拟也页面人工操作新增类目接口失败:"+ JSON.toJSONString(addResult));
                    return ResponseData.error("物料在领星ERP缺少二级目录，调用模拟也页面人工操作新增类目接口失败"+ JSON.toJSONString(addResult));
                }
            }

        }

        if (ObjectUtil.isNull(lxMatSync.getCategoryThreeId())) {//
            CategoryResp category = this.getLocalCacheCategory(allCategory, lxMatSync.getCategoryTwoId(), lxMatSync.getCategoryThree());
            if (ObjectUtil.isNotNull(category)) {
                lxMatSync.setCategoryThreeId(category.getCid());
            } else {
                LinXingCategoryAddResp addResult = lingXingProductService.addCategory(lxMatSync.getCategoryTwoId(), lxMatSync.getCategoryThree());

                if (ObjectUtil.isNotNull(addResult) && addResult.isSuccess() && StrUtil.isNotEmpty(addResult.getId())) {
                    lxMatSync.setCategoryThreeId(Integer.valueOf(addResult.getId()));
                    CategoryResp newCategory = new CategoryResp();
                    newCategory.setCid(Integer.valueOf(addResult.getId()));
                    newCategory.setTitle(lxMatSync.getCategoryThree());
                    newCategory.setParent_cid(lxMatSync.getCategoryTwoId());
                    allCategory.add(newCategory);
                } else {
                    lxMatSync.setSyncResultMsg("物料在领星ERP缺少三级级目录，调用模拟也页面人工操作新增类目接口失败:"+ JSON.toJSONString(addResult));
                    return ResponseData.error("物料在领星ERP缺少三级目录，调用模拟也页面人工操作新增类目接口失败"+ JSON.toJSONString(addResult));
                }
            }

        }

        //TODO:品牌非必传，暂不考虑 ，后期看数据情况




        //请求接口
        ProductReq addProductReq = new ProductReq();
        addProductReq.setSku(lxMatSync.getSku());
        addProductReq.setProduct_name(lxMatSync.getProductName());
        addProductReq.setCategory(lxMatSync.getCategoryThree());
        addProductReq.setCategory_id(lxMatSync.getCategoryThreeId());
        addProductReq.setModel(lxMatSync.getModel());
        addProductReq.setBrand(lxMatSync.getBrand());
        addProductReq.setBrand_id(lxMatSync.getBrandId());
        addProductReq.setStatus(lxMatSync.getStatus());

        lxMatSync.setSyncRequstPar(JSON.toJSONString(addProductReq));
        LingXingBaseRespData baseRespData = lingXingProductService.addProduct(addProductReq);
        lxMatSync.setSyncTime(new Date());
        lxMatSync.setSyncStatus("0");
        lxMatSync.setSyncResultMsg(JSON.toJSONString(baseRespData));

        boolean resutl=false;

        if (baseRespData.getCode() == 0 && "success".equals(baseRespData.getMessage())) {
            lxMatSync.setSyncStatus("1");
            resutl=true;
        } else {
            lxMatSync.setSyncStatus("0");
        }

        this.updateById(lxMatSync);

        if (resutl) {
            return ResponseData.success(lxMatSync);
        }
        return ResponseData.error(500,"请求领星接新增商品接口失败",lxMatSync);

    }

    /**
     * 本地缓存运营大类
     * @param allCategory
     * @param Pid 父级ID
     * @param title  当前类目名称
     * @return
     */
    private CategoryResp getLocalCacheCategory(List<CategoryResp> allCategory, Integer Pid, String title) {

        //二级目录ID
        if (ObjectUtil.isNotNull(allCategory)) {
            CategoryResp categoryResp = allCategory.stream().filter(c ->c.getParent_cid().equals(Pid)
                    && c.getTitle().equals(title)).findAny().orElse(null);
            if (ObjectUtil.isNotNull(categoryResp)) {
                return categoryResp;
            }else {
                return null;
            }
        }
        return null;
    }


    private String getMergeValue(String lxProductNameValue, TbComMateriel tbComMateriel) {

        //‘-’标识传固定值，不做拼接
        if ("-".equals(lxProductNameValue)) {
            return "-";
        }

        String[] fieldArry = lxProductNameValue.split("@");
        if (ObjectUtil.isNotNull(fieldArry)) {
            try {
                return   ReflectUtils.getValueStrMerge(tbComMateriel, fieldArry,"-");
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
    *获取分页查询参数
    */
     private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
