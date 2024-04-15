package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtShopifySettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AllocStructureMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LtShopifySettlementReportMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtShopifySettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.LtShopifySettlementReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Shopify小平台结算报告;(LT_SHOPIFY_SETTLEMENT_REPORT)表服务实现类
 * @author : LSY
 * @date : 2023-12-23
 */
@Service
@Transactional
@Slf4j
public class LtShopifySettlementReportServiceImpl  extends ServiceImpl<LtShopifySettlementReportMapper, LtShopifySettlementReport> implements LtShopifySettlementReportService{
    @Resource
    private LtShopifySettlementReportMapper ltShopifySettlementReportMapper;

     @Resource
     AllocStructureService allocStructureService;

     @Resource
     ISpotExchangeRateService spotExchangeRateService;

    @Resource
    private AllocStructureMapper allocStructureMapper;

    @Resource
    LtShopifySettlementReportService ltShopifySettlementReportService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtShopifySettlementReport queryById(String id){
        return ltShopifySettlementReportMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "finance")
    @Override
    public Page<LtShopifySettlementReportResult> paginQuery(LtShopifySettlementReportParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtShopifySettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPeriod()), LtShopifySettlementReport::getPeriod, param.getPeriod());
        //2. 执行分页查询
        Page<LtShopifySettlementReportResult> pagin = new Page<>(current , size , true);
        IPage<LtShopifySettlementReportResult> selectResult = ltShopifySettlementReportMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

     @DataSource(name = "finance")
     @Override
     public List<LtShopifySettlementReport> paginExport(LtShopifySettlementReportParam param){
         //1. 构建动态查询条件
         LambdaQueryWrapper<LtShopifySettlementReport> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getId()),LtShopifySettlementReport::getId, param.getId());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCategory()),LtShopifySettlementReport::getCategory, param.getCategory());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductType()),LtShopifySettlementReport::getProductType, param.getProductType());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductName()),LtShopifySettlementReport::getProductName, param.getProductName());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStyle()),LtShopifySettlementReport::getStyle, param.getStyle());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMainMaterial()),LtShopifySettlementReport::getMainMaterial, param.getMainMaterial());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDesign()),LtShopifySettlementReport::getDesign, param.getDesign());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCompanyBrand()),LtShopifySettlementReport::getCompanyBrand, param.getCompanyBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFitBrand()),LtShopifySettlementReport::getFitBrand, param.getFitBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getModel()),LtShopifySettlementReport::getModel, param.getModel());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getColor()),LtShopifySettlementReport::getColor, param.getColor());
         //2. 执行分页查询
         List<LtShopifySettlementReport> selectResult = ltShopifySettlementReportMapper.exportByPage( queryWrapper);
         //3. 返回结果
         return selectResult;
     }


    @DataSource(name = "finance")
    @Override
    public List<LtShopifySettlementReportExport> downloadExport(LtShopifySettlementReportParam param){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtShopifySettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LtShopifySettlementReport::getId, "-1");

        //2. 执行分页查询
        List<LtShopifySettlementReportExport> selectResult = ltShopifySettlementReportMapper.downloadExport( queryWrapper);
        //3. 返回结果
        return selectResult;
    }
    
    /** 
     * 新增数据
     *
     * @param ltShopifySettlementReport 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtShopifySettlementReport insert(LtShopifySettlementReport ltShopifySettlementReport){
        ltShopifySettlementReportMapper.insert(ltShopifySettlementReport);
        return ltShopifySettlementReport;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtShopifySettlementReport update(LtShopifySettlementReportParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<LtShopifySettlementReport> wrapper = new LambdaUpdateChainWrapper<LtShopifySettlementReport>(ltShopifySettlementReportMapper);
        //2. 设置主键，并更新
        wrapper.eq(LtShopifySettlementReport::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(LtShopifySettlementReport::getUpdateTime, new Date());
        wrapper.set(LtShopifySettlementReport::getUpdateBy, loginUser.getName());
        wrapper.set(LtShopifySettlementReport::getProductType, param.getProductType());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "finance")
    @Override
    public boolean deleteById(String id){
        int total = ltShopifySettlementReportMapper.deleteById(id);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "finance")
    @Override
    public boolean deleteBatchIds(List<String> idList) {
        int delCount = ltShopifySettlementReportMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     @DataSource(name = "finance")
     @Override
     public ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList) {
         log.info("小平台结算报告Shopify平台-导入:Excel处理开始");
         BufferedInputStream buffer = null;

         try {
             buffer = new BufferedInputStream(file.getInputStream());
             BaseExcelListener listener = new BaseExcelListener<LtShopifySettlementReport>();
             EasyExcel.read(buffer, LtShopifySettlementReport.class, listener).sheet()
                     .doRead();

             List<LtShopifySettlementReport> dataList = listener.getDataList();
             if (CollectionUtil.isEmpty(dataList)) {
                 return ResponseData.error("小平台结算报告Shopify平台-导入为空！");
             }

             //异常数据集合
             List<LtShopifySettlementReport> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);
             String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";
             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getShopName())||!shopList.contains(i.getShopName()))) {
                 log.error(StrUtil.format("存在异常账号:可选账号[{}]",String.join("、", shopList)));
                 return ResponseData.error(StrUtil.format("存在异常账号:可选账号[{}]",String.join("、", shopList)));
             }

             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getSite())||!siteList.contains(i.getSite()))) {
                 log.error(StrUtil.format("存在异常站点:可选站点[{}]",String.join("、", siteList)));
                 return ResponseData.error(StrUtil.format("存在异常站点:可选站点[{}]",String.join("、", siteList)));
             }

             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getDepartment())||!departmentList.contains(i.getDepartment()))) {
                 log.error(StrUtil.format("存在异常事业部:可选事业部[{}]",String.join("、", departmentList)));
                 return ResponseData.error(StrUtil.format("存在异常事业部:可选事业部[{}]",String.join("、", departmentList)));
             }

             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getTeam())||!teamList.contains(i.getTeam()))) {
                 log.error(StrUtil.format("存在异常Team:可选Team[{}]",String.join("、", teamList)));
                 return ResponseData.error(StrUtil.format("存在异常Team:可选Team[{}]",String.join("、", teamList)));
             }


             DateTime date = DateUtil.date();
             String pattern = "\\d{4}-\\d{2}";
             if (dataList.stream().anyMatch(i-> ObjectUtil.isEmpty(i.getPeriod()) ||!i.getPeriod().matches(pattern))) {
                 return ResponseData.error(StrUtil.format("导入失败:期间正确格式yyyy-MM"));
             }

             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getMatCode()))) {
                 return ResponseData.error(StrUtil.format("导入失败:存在物料编码为空"));
             }
             for (LtShopifySettlementReport i : dataList) {
                 if (ObjectUtil.isNotEmpty(i.getCreateBy())) {
                     i.setUpdateBy(operator);
                     i.setUpdateTime(date);
                 } else {
                     i.setCreateBy(operator);
                     i.setCreateTime(date);
                 }
                 ;
                 i.setPlatform("Shopify");

                 if ((ObjectUtil.isNotEmpty(i.getPacking()) && i.getPacking().contains("#"))||
                         (ObjectUtil.isNotEmpty(i.getVersion()) &&i.getVersion().contains("#"))||
                         (ObjectUtil.isNotEmpty(i.getType()) &&i.getType().contains("#"))||
                         (ObjectUtil.isNotEmpty(i.getStyleSecondLabel()) &&i.getStyleSecondLabel().contains("#"))) {
                     log.error("存在#乱码数据#");
                     return ResponseData.error("存在#乱码数据#");
                 }
             }

             //批量保存更新表数据
             if (CollectionUtil.isNotEmpty(dataList)) {
                 String period = dataList.get(0).getPeriod();
                 new LambdaUpdateChainWrapper<>(ltShopifySettlementReportMapper)
                         .eq(ObjectUtil.isNotEmpty(period),LtShopifySettlementReport::getPeriod, period)
                         .ne(LtShopifySettlementReport::getConfirmStatus,1)
                         .remove();
                 this.saveOrUpdateBatch(dataList);
                 if (CollectionUtil.isNotEmpty(errorList)) {
                     String fileName = "异常数据";
                     //部分导入成功
                     return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
                 }
                 return ResponseData.success("导入成功！");
             }
             if (CollectionUtil.isNotEmpty(errorList)) {
                 String fileName = "异常数据";
                 //导入失败
                 return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入失败，存在异常数据数据", fileName);
             }
             return ResponseData.error("导入失败！导入数据为空！");
         } catch (Exception e) {log.error("导入Excel处理异常，导入失败！", e);
             return ResponseData.error("导入Excel处理异常，导入失败！"+ e.getMessage());
         }
     }


    @DataSource(name = "finance")
    @Override
    public ResponseData autoCalPeopleCost(LtShopifySettlementReportParam param) {
        if (ObjectUtil.isEmpty(param.getPeriod())) {
            return ResponseData.error("未指定会计区间");
        }

        LambdaQueryWrapper<AllocStructure> qw = new LambdaQueryWrapper<>();

        qw.eq(ObjectUtil.isNotEmpty(param.getPeriod()),AllocStructure :: getPeriod,param.getPeriod());

        if (allocStructureMapper.selectCount(qw) == 0) {
            return ResponseData.error(StrUtil.format("无分摊分摊架构", param.getPeriod()));
        }
        //0.当前会计架构是否确认
        AllocStructureParam allocStructureParam = AllocStructureParam.builder().period(param.getPeriod()).build();
        if (allocStructureService.isNoComfirm(allocStructureParam)) {
            return ResponseData.error(StrUtil.format("当前会计区间{}存在未未确认分摊架构", param.getPeriod()));
        }

        LambdaQueryWrapper<LtShopifySettlementReport> shopifyQw = new LambdaQueryWrapper<>();
        shopifyQw.eq(LtShopifySettlementReport::getPeriod,param.getPeriod());
        if (ltShopifySettlementReportMapper.selectCount(shopifyQw) == 0) {
            return ResponseData.error(StrUtil.format("当前会计期间[{}]无数据", param.getPeriod()));
        }

        String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";
        DateTime date = DateUtil.date();

        //最终结算补充架构数据(占比,人数*占比,人数*占比*费用)
        List<LtShopifySettlementReportResult> mergedShopifySettle = ltShopifySettlementReportMapper.mergeShopifySettleNallocStruct(param);
        List<LtShopifySettlementReport> updateShopifyList = new ArrayList<>();

        //获取CNY->USD费率
        SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
        rateParam.setOriginalCurrency("CNY");
        rateParam.setEffectDate(ObjectUtil.isEmpty(param.getPeriod())? DateUtil.date():DateUtil.parse(param.getPeriod(),"yyyy-MM"));
        SpotExchangeRate rateByDateCurrency = spotExchangeRateService.getRateByDateCurrency(rateParam);
        BigDecimal directRate;
        if (ObjectUtil.isEmpty(rateByDateCurrency)) {
            return  ResponseData.error(StrUtil.format("无法获取当期{}直接汇率",rateParam.getEffectDate()));
        } else {
            directRate = rateByDateCurrency.getDirectRate();
        }


        //结算和结构未匹配上数据(有架构无结算无明细或有结算明细无架构)  销量占比固定为1,人数取架构分摊亚马逊人数,成本 = 7000 *  direct * 人数
        List<LtShopifySettlementReport> unMatchSettlement = mergedShopifySettle.stream().filter(i -> ObjectUtil.isEmpty(i.getId()) || ObjectUtil.isEmpty(i.getStructId())).map(i -> {
            LtShopifySettlementReport ltShopifySettlementReport = new LtShopifySettlementReport();
            BeanUtil.copyProperties(i,ltShopifySettlementReport);
            ltShopifySettlementReport.setRevenueRation(BigDecimal.ONE);
            BigDecimal amazonAllocPeopleNum = ObjectUtil.isEmpty(i.getAmazonAlloc()) ? BigDecimal.ZERO : i.getAmazonAlloc();
            ltShopifySettlementReport.setPeopleNum(amazonAllocPeopleNum.setScale(12, RoundingMode.HALF_UP));
            ltShopifySettlementReport.setPeopleCost(amazonAllocPeopleNum.multiply(directRate).multiply(new BigDecimal(-7000)));
            ltShopifySettlementReport.setId(IdWorker.getIdStr());
            ltShopifySettlementReport.setUpdateTime(date);
            ltShopifySettlementReport.setUpdateBy(operator);
            return ltShopifySettlementReport;
        }).collect(Collectors.toList());
        updateShopifyList.addAll(unMatchSettlement);

        //有明细 占比(明细/Team销量) 占比*人数 占比*人数*成本 人力成本固定7000
        List<LtShopifySettlementReport> matchedSettlement = mergedShopifySettle.stream().filter(i -> ObjectUtil.isNotEmpty(i.getId()) && ObjectUtil.isNotEmpty(i.getStructId())).map(i -> {
            LtShopifySettlementReport ltShopifySettlementReport = new LtShopifySettlementReport();
            BeanUtil.copyProperties(i,ltShopifySettlementReport);
            BigDecimal skuTeamRatio;
            //明细为0,占比为0
            if (ObjectUtil.isEmpty(i.getDetailsalesvol())) {
                skuTeamRatio = BigDecimal.ZERO;
                //无对应Team数据占比为1
            } else  if (ObjectUtil.isEmpty(i.getTeamsalesvol()) || i.getTeamsalesvol().compareTo(BigDecimal.ZERO) ==0){
                skuTeamRatio = BigDecimal.ONE;
            } else {
                //明细销量比Team销量
                skuTeamRatio = i.getDetailsalesvol().divide(i.getTeamsalesvol(), 12, BigDecimal.ROUND_HALF_UP);
            }
            ltShopifySettlementReport.setRevenueRation(skuTeamRatio);
            BigDecimal amazonAllocPeopleNum = ObjectUtil.isEmpty(i.getAmazonAlloc()) ? BigDecimal.ZERO : i.getAmazonAlloc();

            ltShopifySettlementReport.setPeopleNum(amazonAllocPeopleNum.multiply(skuTeamRatio).setScale(12, RoundingMode.HALF_UP));
            ltShopifySettlementReport.setPeopleCost(amazonAllocPeopleNum.multiply(skuTeamRatio).multiply(directRate).multiply(new BigDecimal(-7000)));
            return ltShopifySettlementReport;
        }).collect(Collectors.toList());
        updateShopifyList.addAll(matchedSettlement);
//
        //未确认重新生成
        new LambdaUpdateChainWrapper<>(ltShopifySettlementReportMapper)
                .eq(ObjectUtil.isEmpty(param.getPeriod()),LtShopifySettlementReport::getPeriod, DateUtil.format(DateUtil.date(),"YYYY-MM"))
                .eq(ObjectUtil.isNotEmpty(param.getPeriod()),LtShopifySettlementReport::getPeriod, param.getPeriod())
                .eq(LtShopifySettlementReport::getConfirmStatus,0)
                .remove();


        ltShopifySettlementReportService.saveOrUpdateBatch(updateShopifyList);
        ltShopifySettlementReportService.updatePeopleCostZero(param);




        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData haveReport(LtShopifySettlementReportParam param) {
        if (ObjectUtil.isEmpty(param.getPeriod())) {
            return ResponseData.error("未指定会计区间");
        }

        LambdaQueryWrapper<AllocStructure> qw = new LambdaQueryWrapper<>();
        qw.eq(ObjectUtil.isNotEmpty(param.getPeriod()), AllocStructure::getPeriod, param.getPeriod());

        if (allocStructureMapper.selectCount(qw) == 0) {
            return ResponseData.error(StrUtil.format("当前会计区间{}无分摊分摊架构", param.getPeriod()));
        }

        //0.当前会计架构是否确认
        AllocStructureParam allocStructureParam = AllocStructureParam.builder().period(param.getPeriod()).build();
        if (allocStructureService.isNoComfirm(allocStructureParam)) {
            return ResponseData.error(StrUtil.format("当前会计区间{}存在未未确认分摊架构", param.getPeriod()));
        }

        LambdaQueryWrapper<LtShopifySettlementReport> shopifyQw = new LambdaQueryWrapper<>();
        shopifyQw.eq(LtShopifySettlementReport::getPeriod, param.getPeriod());
        if (ltShopifySettlementReportMapper.selectCount(shopifyQw) == 0) {
            log.error("报告当前会计期间[{}]无数据", param.getPeriod());
            return ResponseData.error("数据为空");
        }
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData insertStructure(LtShopifySettlementReportParam param) {
        if (ObjectUtil.isEmpty(param.getPeriod())) {
            return  ResponseData.error("未指定会计区间");
        }
        //获取CNY->USD费率
        SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
        rateParam.setOriginalCurrency("CNY");
        DateTime date = DateUtil.date();
        rateParam.setEffectDate(ObjectUtil.isEmpty(param.getPeriod()) ? date : DateUtil.parse(param.getPeriod(), "yyyy-MM"));
        SpotExchangeRate rateByDateCurrency = spotExchangeRateService.getRateByDateCurrency(rateParam);
        BigDecimal directRate;
        if (ObjectUtil.isEmpty(rateByDateCurrency)) {
            return ResponseData.error(StrUtil.format("无法获取当期{}直接汇率", rateParam.getEffectDate()));
        } else {
            directRate = rateByDateCurrency.getDirectRate();
        }

        String createBy = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "系统生成架构";
        param.setCreateBy(createBy);
        param.setDirectRate(directRate);
        ltShopifySettlementReportMapper.insertStructure(param);

        return ResponseData.success();

    }

    @DataSource(name = "finance")
    @Override
    public int updatePeopleCostZero(LtShopifySettlementReportParam param) {
        return ltShopifySettlementReportMapper.updatePeopleCostZero(param);
    }


}