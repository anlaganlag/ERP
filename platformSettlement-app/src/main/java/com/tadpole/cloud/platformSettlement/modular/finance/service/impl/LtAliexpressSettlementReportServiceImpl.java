package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

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
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.AllocStructure;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAliexpressSettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LtAliexpressSettlementReportExport;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LtAliexpressSettlementReportParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtAliexpressSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AllocStructureMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LtAliexpressSettlementReportMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.LtAliexpressSettlementReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ;(LT_ALIEXPRESS_SETTLEMENT_REPORT)表服务实现类
 * @author : LSY
 * @date : 2023-12-22
 */
@Service
@Transactional
@Slf4j
public class LtAliexpressSettlementReportServiceImpl  extends ServiceImpl<LtAliexpressSettlementReportMapper, LtAliexpressSettlementReport> implements LtAliexpressSettlementReportService{
    @Resource
    private LtAliexpressSettlementReportMapper ltAliexpressSettlementReportMapper;

    @Autowired
    ISpotExchangeRateService spotExchangeRateService;

    @Resource
    AllocStructureService allocStructureService;

    @Resource
    private AllocStructureMapper allocStructureMapper;



    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtAliexpressSettlementReport queryById(String undefinedId){
        return ltAliexpressSettlementReportMapper.selectById(undefinedId);
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
    public Page<LtAliexpressSettlementReportResult> paginQuery(LtAliexpressSettlementReportParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtAliexpressSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getId()),LtAliexpressSettlementReport::getId, param.getId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPeriod()),LtAliexpressSettlementReport::getPeriod, param.getPeriod());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogisticsTrackNo()),LtAliexpressSettlementReport::getLogisticsTrackNo, param.getLogisticsTrackNo());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getNewMatCode()),LtAliexpressSettlementReport::getNewMatCode, param.getNewMatCode());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCategory()),LtAliexpressSettlementReport::getCategory, param.getCategory());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductType()),LtAliexpressSettlementReport::getProductType, param.getProductType());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductName()),LtAliexpressSettlementReport::getProductName, param.getProductName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStyle()),LtAliexpressSettlementReport::getStyle, param.getStyle());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMainMaterial()),LtAliexpressSettlementReport::getMainMaterial, param.getMainMaterial());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDesign()),LtAliexpressSettlementReport::getDesign, param.getDesign());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCompanyBrand()),LtAliexpressSettlementReport::getCompanyBrand, param.getCompanyBrand());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFitBrand()),LtAliexpressSettlementReport::getFitBrand, param.getFitBrand());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getModel()),LtAliexpressSettlementReport::getModel, param.getModel());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getColor()),LtAliexpressSettlementReport::getColor, param.getColor());
            queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOrderAomount()),LtAliexpressSettlementReport::getOrderAomount, param.getOrderAomount());
        //2. 执行分页查询
        Page<LtAliexpressSettlementReportResult> pagin = new Page<>(current , size , true);
        IPage<LtAliexpressSettlementReportResult> selectResult = ltAliexpressSettlementReportMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    @DataSource(name = "finance")
    @Override
    public List<LtAliexpressSettlementReportExport> downloadExport(LtAliexpressSettlementReportParam param){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtAliexpressSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LtAliexpressSettlementReport::getId, "-1");

        //2. 执行分页查询
        List<LtAliexpressSettlementReportExport> selectResult = ltAliexpressSettlementReportMapper.downloadExport( queryWrapper);
        //3. 返回结果
        return selectResult;
    }


     @DataSource(name = "finance")
     @Override
     public List<LtAliexpressSettlementReport> paginExport(LtAliexpressSettlementReportParam param){
         //1. 构建动态查询条件
         LambdaQueryWrapper<LtAliexpressSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getId()),LtAliexpressSettlementReport::getId, param.getId());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getLogisticsTrackNo()),LtAliexpressSettlementReport::getLogisticsTrackNo, param.getLogisticsTrackNo());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getNewMatCode()),LtAliexpressSettlementReport::getNewMatCode, param.getNewMatCode());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCategory()),LtAliexpressSettlementReport::getCategory, param.getCategory());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductType()),LtAliexpressSettlementReport::getProductType, param.getProductType());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductName()),LtAliexpressSettlementReport::getProductName, param.getProductName());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStyle()),LtAliexpressSettlementReport::getStyle, param.getStyle());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMainMaterial()),LtAliexpressSettlementReport::getMainMaterial, param.getMainMaterial());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDesign()),LtAliexpressSettlementReport::getDesign, param.getDesign());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCompanyBrand()),LtAliexpressSettlementReport::getCompanyBrand, param.getCompanyBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFitBrand()),LtAliexpressSettlementReport::getFitBrand, param.getFitBrand());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getModel()),LtAliexpressSettlementReport::getModel, param.getModel());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getColor()),LtAliexpressSettlementReport::getColor, param.getColor());
         queryWrapper.eq(ObjectUtil.isNotEmpty(param.getOrderAomount()),LtAliexpressSettlementReport::getOrderAomount, param.getOrderAomount());
         //2. 执行分页查询
         List<LtAliexpressSettlementReport> selectResult = ltAliexpressSettlementReportMapper.exportByPage( queryWrapper);
         //3. 返回结果
         return selectResult;
     }
    
    /** 
     * 新增数据
     *
     * @param ltAliexpressSettlementReport 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtAliexpressSettlementReport insert(LtAliexpressSettlementReport ltAliexpressSettlementReport){
        ltAliexpressSettlementReportMapper.insert(ltAliexpressSettlementReport);
        return ltAliexpressSettlementReport;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtAliexpressSettlementReport update(LtAliexpressSettlementReportParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<LtAliexpressSettlementReport> wrapper = new LambdaUpdateChainWrapper<LtAliexpressSettlementReport>(ltAliexpressSettlementReportMapper);
         wrapper.set(ObjectUtil.isNotEmpty(param.getId()),LtAliexpressSettlementReport::getId, param.getId());
         wrapper.set(ObjectUtil.isNotEmpty(param.getLogisticsTrackNo()),LtAliexpressSettlementReport::getLogisticsTrackNo, param.getLogisticsTrackNo());
         wrapper.set(ObjectUtil.isNotEmpty(param.getNewMatCode()),LtAliexpressSettlementReport::getNewMatCode, param.getNewMatCode());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCategory()),LtAliexpressSettlementReport::getCategory, param.getCategory());
         wrapper.set(ObjectUtil.isNotEmpty(param.getProductType()),LtAliexpressSettlementReport::getProductType, param.getProductType());
         wrapper.set(ObjectUtil.isNotEmpty(param.getProductName()),LtAliexpressSettlementReport::getProductName, param.getProductName());
         wrapper.set(ObjectUtil.isNotEmpty(param.getStyle()),LtAliexpressSettlementReport::getStyle, param.getStyle());
         wrapper.set(ObjectUtil.isNotEmpty(param.getMainMaterial()),LtAliexpressSettlementReport::getMainMaterial, param.getMainMaterial());
         wrapper.set(ObjectUtil.isNotEmpty(param.getDesign()),LtAliexpressSettlementReport::getDesign, param.getDesign());
         wrapper.set(ObjectUtil.isNotEmpty(param.getCompanyBrand()),LtAliexpressSettlementReport::getCompanyBrand, param.getCompanyBrand());
         wrapper.set(ObjectUtil.isNotEmpty(param.getFitBrand()),LtAliexpressSettlementReport::getFitBrand, param.getFitBrand());
         wrapper.set(ObjectUtil.isNotEmpty(param.getModel()),LtAliexpressSettlementReport::getModel, param.getModel());
         wrapper.set(ObjectUtil.isNotEmpty(param.getColor()),LtAliexpressSettlementReport::getColor, param.getColor());
         wrapper.set(ObjectUtil.isNotEmpty(param.getOrderAomount()),LtAliexpressSettlementReport::getOrderAomount, param.getOrderAomount());

        //2. 设置主键，并更新
        wrapper.eq(LtAliexpressSettlementReport::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(LtAliexpressSettlementReport::getUpdateTime, new Date());
        wrapper.set(LtAliexpressSettlementReport::getUpdateBy, loginUser.getName());
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
     * @param undefinedId 主键
     * @return 是否成功
     */
    @DataSource(name = "finance")
    @Override
    public boolean deleteById(String undefinedId){
        int total = ltAliexpressSettlementReportMapper.deleteById(undefinedId);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param undefinedIdList 主键List
     * @return 是否成功
     */
    @DataSource(name = "finance")
    @Override
    public boolean deleteBatchIds(List<String> undefinedIdList) {
        int delCount = ltAliexpressSettlementReportMapper.deleteBatchIds(undefinedIdList);
        if (undefinedIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

     @DataSource(name = "finance")
     @Override
     public ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList) {
         log.info("小平台结算报告速卖通-导入:Excel处理开始");
         BufferedInputStream buffer = null;

         try {
             buffer = new BufferedInputStream(file.getInputStream());
             BaseExcelListener listener = new BaseExcelListener<LtAliexpressSettlementReport>();
             EasyExcel.read(buffer, LtAliexpressSettlementReport.class, listener).sheet()
                     .doRead();

             List<LtAliexpressSettlementReport> dataList = listener.getDataList();
             if (CollectionUtil.isEmpty(dataList)) {
                 return ResponseData.error("小平台结算报告速卖通-导入为空！");
             }

             //异常数据集合
             List<LtAliexpressSettlementReport> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);
             String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";

             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getShopName())||!shopList.contains(i.getShopName()))) {
                 log.error(StrUtil.format("存在异常账号:可选账号[{}]",String.join("、", shopList)));
                 return ResponseData.error(StrUtil.format("存在异常账号:可选账号[{}]",String.join("、", shopList)));
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



             if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getNewMatCode()))) {
                 return ResponseData.error(StrUtil.format("导入失败:存在物料编码为空"));
             }

             for (LtAliexpressSettlementReport i : dataList) {
                 if (ObjectUtil.isNotEmpty(i.getCreateBy())) {
                     i.setUpdateBy(operator);
                     i.setUpdateTime(date);
                 } else {
                     i.setCreateBy(operator);
                     i.setCreateTime(date);
                 }
                 ;
                 i.setPlatform("Aliexpress");
                 try {
                     if (ObjectUtil.isNotEmpty(i.getOutTime())) {
                     DateTime parse = DateUtil.parse(i.getOutTime(), "yyyy-MM-dd");
                     i.setOutTime(DateUtil.format(parse,"yyyy-MM-dd hh:mm:ss"));}
                 } catch (Exception e){
                     log.error(StrUtil.format("导入失败:出库时间日期转换异常[{}]",i.getOutTime()));
                     return ResponseData.error(StrUtil.format("导入失败:出库时间:[{}]转换异常,正确格式yyyy-MM-dd hh:mm:ss",i.getOutTime()));
                 }
             }
             //批量保存更新表数据
             if (CollectionUtil.isNotEmpty(dataList)) {
                 String period = dataList.get(0).getPeriod();
                 new LambdaUpdateChainWrapper<>(ltAliexpressSettlementReportMapper)
                         .eq(ObjectUtil.isNotEmpty(period), LtAliexpressSettlementReport::getPeriod, period)
                         .ne(LtAliexpressSettlementReport::getConfirmStatus,1)
                         .remove();

                 this.saveOrUpdateBatch(dataList);
                 if (CollectionUtil.isNotEmpty(errorList)) {
                     String fileName = "异常数据";
                     //部分导入成功
                     return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "部分导入成功，存在异常数据数据", fileName);
                 }
                 this.baseMapper.mergePeople(period);
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

//    @DataSource(name = "finance")
//    @Override
//    public ResponseData getPeopleCost(LtAliexpressSettlementReportParam param) {
//
//
//        //0.当前会计架构是否确认
//
//
//
//        //1.补充完整结算报告(占比,人数*占比,人数*占比*费用)
//        List<LtAliexpressSettlementReportResult> supplement = ltAliexpressSettlementReportMapper.supplement(param);
//        List<LtAliexpressSettlementReport> updateFinalList = new ArrayList<>();
//
//        //获取CNY->USD费率
//        SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
//        rateParam.setOriginalCurrency("CNY");
//        rateParam.setEffectDate(ObjectUtil.isEmpty(param.getPeriod())? DateUtil.date():DateUtil.parse(param.getPeriod()));
//        SpotExchangeRate rateByDateCurrency = spotExchangeRateService.getRateByDateCurrency(rateParam);
//        BigDecimal directRate = rateByDateCurrency.getDirectRate();
//
//        //有架构无结算无明细  销量占比固定为1,人数取架构分摊亚马逊人数,成本 = 7000 *  direct * 人数
//        List<LtAliexpressSettlementReport> collect = supplement.stream().filter(i -> ObjectUtil.isEmpty(i.getId())).map(i -> {
//            LtAliexpressSettlementReport ltAliexpressSettlementReportResult = new LtAliexpressSettlementReport();
//            BeanUtil.copyProperties(i,ltAliexpressSettlementReportResult);
//            ltAliexpressSettlementReportResult.setRevenueRatio(BigDecimal.ONE);
//            ltAliexpressSettlementReportResult.setPeopleNum(i.getAmazonAlloc());
//            ltAliexpressSettlementReportResult.setPeopleCost(i.getAmazonAlloc().multiply(directRate).multiply(new BigDecimal(7000)));
//            ltAliexpressSettlementReportResult.setId(IdWorker.getIdStr());
//            return ltAliexpressSettlementReportResult;
//        }).collect(Collectors.toList());
//        //有明细 占比(明细/Team销量) 占比*人数 占比*人数*成本 人力成本固定7000
//        updateFinalList.addAll(collect);
//
//        List<LtAliexpressSettlementReport> detailCollect = supplement.stream().filter(i -> ObjectUtil.isNotEmpty(i.getId())).map(i -> {
//            LtAliexpressSettlementReport ltAliexpressSettlementReportResult = new LtAliexpressSettlementReport();
//            BeanUtil.copyProperties(i,ltAliexpressSettlementReportResult);
//            BigDecimal divide;
//            //明细为0,占比为0
//            if (ObjectUtil.isEmpty(i.getDETAILSALESVOL())) {
//                divide = BigDecimal.ZERO;
//                //无对应Team数据占比为1
//            } else  if (ObjectUtil.isEmpty(i.getTEAMSALESVOL()) || i.getTEAMSALESVOL().compareTo(BigDecimal.ZERO) ==0){
//                divide = BigDecimal.ONE;
//            } else {
//                //明细销量比Team销量
//                divide = i.getDETAILSALESVOL().divide(i.getTEAMSALESVOL(), 12, BigDecimal.ROUND_HALF_UP);
//            }
//            ltAliexpressSettlementReportResult.setRevenueRatio(divide);
//            ltAliexpressSettlementReportResult.setPeopleNum(ObjectUtil.isEmpty(i.getAmazonAlloc())?BigDecimal.ZERO:i.getAmazonAlloc().multiply(divide));
//            ltAliexpressSettlementReportResult.setPeopleCost(ObjectUtil.isEmpty(i.getAmazonAlloc())?BigDecimal.ZERO:i.getAmazonAlloc().multiply(divide).multiply(directRate).multiply(new BigDecimal(7000)));
//            return ltAliexpressSettlementReportResult;
//        }).collect(Collectors.toList());
//        updateFinalList.addAll(detailCollect);
//
//        //未确认重新生成
//        new LambdaUpdateChainWrapper<>(settlementReportFinalMapper)
//                .eq(ObjectUtil.isEmpty(param.getFiscalPeriod()),LtAliexpressSettlementReport::getFiscalPeriod, DateUtil.format(DateUtil.date(),"YYYY-MM"))
//                .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()),LtAliexpressSettlementReport::getFiscalPeriod, param.getFiscalPeriod())
//                .eq(LtAliexpressSettlementReport::getConfirmStatus,0)
//                .remove();
//
//
//        settlementReportFinalService.saveBatch(updateFinalList);
//
//
//
////        List<List<LtAliexpressSettlementReport>> splitList = CollUtil.split(updateFinalList, 500);
////        for (List<LtAliexpressSettlementReport> split : splitList) {
////
////            settlementRepoertAdjustMapper.updateFinalBatch(split);
////        }
//
//
//
//
//
//        //2.根据分摊结构填充字段(销量占比,人数*,人工成本)
////        settlementRepoertAdjustMapper.fill(param);
//
//
//        return ResponseData.success();
//    }

    @DataSource(name = "finance")
    @Override
    public ResponseData autoCalPeopleCost(LtAliexpressSettlementReportParam param) {
        try {
            if (ObjectUtil.isEmpty(param.getPeriod())) {
                return ResponseData.error("导入失败:期间不能为空");
            }
            this.baseMapper.mergePeople(param.getPeriod());
            return ResponseData.success();
        }catch (Exception e){
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！" + e.getMessage());
        }
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData haveReport(LtAliexpressSettlementReportParam param) {
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

        LambdaQueryWrapper<LtAliexpressSettlementReport> shopifyQw = new LambdaQueryWrapper<>();
        shopifyQw.eq(LtAliexpressSettlementReport::getPeriod, param.getPeriod());
        if (ltAliexpressSettlementReportMapper.selectCount(shopifyQw) == 0) {
            log.error("报告当前会计期间[{}]无数据", param.getPeriod());
            return ResponseData.error("数据为空");
        }
        return ResponseData.success();
    }
    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData insertStructure(LtAliexpressSettlementReportParam param) {
        if (ObjectUtil.isEmpty(param.getPeriod())) {
            return  ResponseData.error("未指定会计区间");
        }

        String createBy = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "系统生成架构";
        param.setCreateBy(createBy);
        ltAliexpressSettlementReportMapper.insertStructure(param);

        return ResponseData.success();

    }




    public static void main(String[] args) {



    }


 }