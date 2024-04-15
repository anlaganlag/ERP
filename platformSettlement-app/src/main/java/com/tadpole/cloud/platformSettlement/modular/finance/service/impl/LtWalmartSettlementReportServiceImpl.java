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
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtWalmartSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AllocStructureMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LtWalmartSettlementReportMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtWalmartSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.LtWalmartSettlementReportService;
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
 * ;(LT_WALMART_SETTLEMENT_REPORT)表服务实现类
 * @author : LSY
 * @date : 2023-12-22
 */
@Service
@Transactional
@Slf4j
public class LtWalmartSettlementReportServiceImpl  extends ServiceImpl<LtWalmartSettlementReportMapper, LtWalmartSettlementReport> implements LtWalmartSettlementReportService{
    @Resource
    private LtWalmartSettlementReportMapper ltWalmartSettlementReportMapper;

    @Resource
    private AllocStructureMapper allocStructureMapper;

    @Resource
    private LtWalmartSettlementReportService ltWalmartSettlementReportService;
    @Resource
    AllocStructureService allocStructureService;

    @Resource
    ISpotExchangeRateService spotExchangeRateService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtWalmartSettlementReport queryById(String undefinedId){
        return ltWalmartSettlementReportMapper.selectById(undefinedId);
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
    public Page<LtWalmartSettlementReportResult> paginQuery(LtWalmartSettlementReportParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtWalmartSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), LtWalmartSettlementReport::getFiscalPeriod, param.getFiscalPeriod());
        //2. 执行分页查询
        Page<LtWalmartSettlementReportResult> pagin = new Page<>(current , size , true);
        IPage<LtWalmartSettlementReportResult> selectResult = ltWalmartSettlementReportMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    @DataSource(name = "finance")
    @Override
    public List<LtWalmartSettlementReport> paginExport(LtWalmartSettlementReportParam param){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtWalmartSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getId()),LtWalmartSettlementReport::getId, param.getId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCategory()),LtWalmartSettlementReport::getCategory, param.getCategory());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductType()),LtWalmartSettlementReport::getProductType, param.getProductType());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductName()),LtWalmartSettlementReport::getProductName, param.getProductName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStyle()),LtWalmartSettlementReport::getStyle, param.getStyle());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMainMaterial()),LtWalmartSettlementReport::getMainMaterial, param.getMainMaterial());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDesign()),LtWalmartSettlementReport::getDesign, param.getDesign());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCompanyBrand()),LtWalmartSettlementReport::getCompanyBrand, param.getCompanyBrand());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFitBrand()),LtWalmartSettlementReport::getFitBrand, param.getFitBrand());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getModel()),LtWalmartSettlementReport::getModel, param.getModel());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getColor()),LtWalmartSettlementReport::getColor, param.getColor());
        //2. 执行分页查询
        List<LtWalmartSettlementReport> selectResult = ltWalmartSettlementReportMapper.exportByPage( queryWrapper);
        //3. 返回结果
        return selectResult;
    }


    @DataSource(name = "finance")
    @Override
    public List<LtWalmartSettlementReportExport> downloadExport(LtWalmartSettlementReportParam param){
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtWalmartSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LtWalmartSettlementReport::getId, "-1");

        //2. 执行分页查询
        List<LtWalmartSettlementReportExport> selectResult = ltWalmartSettlementReportMapper.downloadExport( queryWrapper);
        //3. 返回结果
        return selectResult;
    }



    /** 
     * 新增数据
     *
     * @param ltWalmartSettlementReport 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtWalmartSettlementReport insert(LtWalmartSettlementReport ltWalmartSettlementReport){
        ltWalmartSettlementReportMapper.insert(ltWalmartSettlementReport);
        return ltWalmartSettlementReport;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtWalmartSettlementReport update(LtWalmartSettlementReportParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<LtWalmartSettlementReport> wrapper = new LambdaUpdateChainWrapper<LtWalmartSettlementReport>(ltWalmartSettlementReportMapper);
        //2. 设置主键，并更新
        wrapper.eq(LtWalmartSettlementReport::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(LtWalmartSettlementReport::getUpdateTime, new Date());
        wrapper.set(LtWalmartSettlementReport::getUpdateBy, loginUser.getName());
        wrapper.set(LtWalmartSettlementReport::getProductType, param.getProductType());

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
        int total = ltWalmartSettlementReportMapper.deleteById(undefinedId);
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
        int delCount = ltWalmartSettlementReportMapper.deleteBatchIds(undefinedIdList);
        if (undefinedIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList,List<String> walShopList) {
        log.info("小平台结算报告Walmart平台-导入:Excel处理开始");
        BufferedInputStream buffer = null;

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<LtWalmartSettlementReport>();
            EasyExcel.read(buffer, LtWalmartSettlementReport.class, listener).sheet()
                    .doRead();

            List<LtWalmartSettlementReport> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("小平台结算报告Walmart平台-导入为空！");
            }

            //异常数据集合
            List<LtWalmartSettlementReport> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);
            String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";
            DateTime date = DateUtil.date();


            if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getShop())||!walShopList.contains(i.getShop()))) {
                log.error(StrUtil.format("存在异常店铺:可选店铺[{}]",String.join("、", walShopList)));
                return ResponseData.error(StrUtil.format("存在异常店铺:可选店铺[{}]",String.join("、", walShopList)));
            }

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

            String pattern = "\\d{4}-\\d{2}";
            if (dataList.stream().anyMatch(i-> ObjectUtil.isEmpty(i.getFiscalPeriod()) ||!i.getFiscalPeriod().matches(pattern))) {
                log.error("导入失败:期间正确格式yyyy-MM");
                return ResponseData.error(StrUtil.format("导入失败:期间正确格式yyyy-MM"));
            }
            String pattern2 = "\\w+_\\w+_\\w+";
            if (dataList.stream().anyMatch(i-> ObjectUtil.isEmpty(i.getShop()) || !i.getShop().matches(pattern2))) {
                log.error("导入失败:店铺非xx_xx_xx的形式或为空");
                return ResponseData.error(StrUtil.format("导入失败:店铺非xx_xx_xx的形式"));
            }
            if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getMatCode()))) {
                log.error("导入失败:存在物料编码为空");
                return ResponseData.error(StrUtil.format("导入失败:存在物料编码为空"));
            }
            dataList.forEach(
                    i -> {
                        if (ObjectUtil.isNotEmpty(i.getCreateBy())) {
                            i.setUpdateBy(operator);
                            i.setUpdateTime(date);
                        }else{
                            i.setCreateBy(operator);
                            i.setCreateTime(date);
                        };
                        i.setPlatform("Walmart");
                    }

            );
            //批量保存更新表数据
            if (CollectionUtil.isNotEmpty(dataList)) {
                String period = dataList.get(0).getFiscalPeriod();
                new LambdaUpdateChainWrapper<>(ltWalmartSettlementReportMapper)
                        .eq(ObjectUtil.isNotEmpty(period),LtWalmartSettlementReport::getFiscalPeriod, period)
                        .ne(LtWalmartSettlementReport::getConfirmStatus,1)
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
    public ResponseData autoCalPeopleCost(LtWalmartSettlementReportParam param) {
        if (ObjectUtil.isEmpty(param.getFiscalPeriod())) {
            return ResponseData.error("未指定会计区间");
        }

        //0.当前会计架构是否确认
        AllocStructureParam allocStructureParam = AllocStructureParam.builder().period(param.getFiscalPeriod()).build();

        LambdaQueryWrapper<AllocStructure> qw = new LambdaQueryWrapper<>();

        qw.eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()),AllocStructure :: getPeriod,param.getFiscalPeriod());
        if (allocStructureMapper.selectCount(qw) == 0) {
            return ResponseData.error(StrUtil.format("无分摊分摊架构", param.getFiscalPeriod()));
        }
        if (allocStructureService.isNoComfirm(allocStructureParam)) {
            return ResponseData.error(StrUtil.format("当前会计区间{}存在未未确认分摊架构", param.getFiscalPeriod()));
        }


        //最终结算补充架构数据(占比,人数*占比,人数*占比*费用)
        List<LtWalmartSettlementReportResult> mergedWalmartSettle = ltWalmartSettlementReportMapper.mergeWalmartSettleNallocStruct(param);List<LtWalmartSettlementReport> updateWalmartList = new ArrayList<>();

        String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"系统生成";
        DateTime date = DateUtil.date();


        //获取CNY->USD费率
        SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
        rateParam.setOriginalCurrency("CNY");
        rateParam.setEffectDate(ObjectUtil.isEmpty(param.getFiscalPeriod())? DateUtil.date():DateUtil.parse(param.getFiscalPeriod(),"yyyy-MM"));
        SpotExchangeRate rateByDateCurrency = spotExchangeRateService.getRateByDateCurrency(rateParam);
        BigDecimal directRate;
        if (ObjectUtil.isEmpty(rateByDateCurrency)) {
            return  ResponseData.error(StrUtil.format("无法获取当期{}直接汇率",rateParam.getEffectDate()));
        } else {
            directRate = rateByDateCurrency.getDirectRate();
        }

        //结算和结构未匹配上数据(有架构无结算无明细或有结算明细无架构)  销量占比固定为1,人数取架构分摊亚马逊人数,成本 = 7000 *  direct * 人数
        List<LtWalmartSettlementReport> unMatchSettlement = mergedWalmartSettle.stream().filter(i -> ObjectUtil.isEmpty(i.getId()) || ObjectUtil.isEmpty(i.getStructId())).map(i -> {
            LtWalmartSettlementReport ltWalmartSettlementReport = new LtWalmartSettlementReport();
            BeanUtil.copyProperties(i,ltWalmartSettlementReport);
            ltWalmartSettlementReport.setRevenueRation(BigDecimal.ONE);
            BigDecimal amazonAllocPeopleNum = ObjectUtil.isEmpty(i.getAmazonAlloc()) ? BigDecimal.ZERO : i.getAmazonAlloc();
            ltWalmartSettlementReport.setPeopleNum(amazonAllocPeopleNum.setScale(12, RoundingMode.HALF_UP));
            ltWalmartSettlementReport.setPeopleCost(amazonAllocPeopleNum.multiply(directRate).multiply(new BigDecimal(-7000)));
            ltWalmartSettlementReport.setId(IdWorker.getIdStr());
            ltWalmartSettlementReport.setUpdateTime(date);
            ltWalmartSettlementReport.setUpdateBy(operator);
            return ltWalmartSettlementReport;
        }).collect(Collectors.toList());
        updateWalmartList.addAll(unMatchSettlement);

        //有明细 占比(明细/Team销量) 占比*人数 占比*人数*成本 人力成本固定7000
        List<LtWalmartSettlementReport> matchedSettlement = mergedWalmartSettle.stream().filter(i -> ObjectUtil.isNotEmpty(i.getId()) && ObjectUtil.isNotEmpty(i.getStructId())).map(i -> {
            LtWalmartSettlementReport ltWalmartSettlementReport = new LtWalmartSettlementReport();
            BeanUtil.copyProperties(i,ltWalmartSettlementReport);
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
            ltWalmartSettlementReport.setRevenueRation(skuTeamRatio);
            BigDecimal amazonAllocPeopleNum = ObjectUtil.isEmpty(i.getAmazonAlloc()) ? BigDecimal.ZERO : i.getAmazonAlloc();

            ltWalmartSettlementReport.setPeopleNum(amazonAllocPeopleNum.multiply(skuTeamRatio).setScale(12, RoundingMode.HALF_UP));
            ltWalmartSettlementReport.setPeopleCost(amazonAllocPeopleNum.multiply(skuTeamRatio).multiply(directRate).multiply(new BigDecimal(-7000)));
            return ltWalmartSettlementReport;
        }).collect(Collectors.toList());
        updateWalmartList.addAll(matchedSettlement);
//
        //未确认重新生成
        new LambdaUpdateChainWrapper<>(ltWalmartSettlementReportMapper)
                .eq(ObjectUtil.isEmpty(param.getFiscalPeriod()),LtWalmartSettlementReport::getFiscalPeriod, DateUtil.format(DateUtil.date(),"YYYY-MM"))
                .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()),LtWalmartSettlementReport::getFiscalPeriod, param.getFiscalPeriod())
                .eq(LtWalmartSettlementReport::getConfirmStatus,0)
                .remove();


        ltWalmartSettlementReportService.saveOrUpdateBatch(updateWalmartList);
        ltWalmartSettlementReportService.updatePeopleCostZero(param);



        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData haveReport(LtWalmartSettlementReportParam param) {
        if (ObjectUtil.isEmpty(param.getFiscalPeriod())) {
            return ResponseData.error("未指定会计区间");
        }

        LambdaQueryWrapper<AllocStructure> qw = new LambdaQueryWrapper<>();
        qw.eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), AllocStructure::getPeriod, param.getFiscalPeriod());

        if (allocStructureMapper.selectCount(qw) == 0) {
            return ResponseData.error(StrUtil.format("当前会计区间{}无分摊分摊架构", param.getFiscalPeriod()));
        }

        //0.当前会计架构是否确认
        AllocStructureParam allocStructureParam = AllocStructureParam.builder().period(param.getFiscalPeriod()).build();
        if (allocStructureService.isNoComfirm(allocStructureParam)) {
            return ResponseData.error(StrUtil.format("当前会计区间{}存在未未确认分摊架构", param.getFiscalPeriod()));
        }

        LambdaQueryWrapper<LtWalmartSettlementReport> shopifyQw = new LambdaQueryWrapper<>();
        shopifyQw.eq(LtWalmartSettlementReport::getFiscalPeriod, param.getFiscalPeriod());
        if (ltWalmartSettlementReportMapper.selectCount(shopifyQw) == 0) {
            log.error("报告当前会计期间[{}]无数据", param.getFiscalPeriod());
            return ResponseData.error("数据为空");
        }
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData insertStructure(LtWalmartSettlementReportParam param) {
        if (ObjectUtil.isEmpty(param.getFiscalPeriod())) {
            return  ResponseData.error("未指定会计区间");
        }
        //获取CNY->USD费率
        SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
        rateParam.setOriginalCurrency("CNY");
        DateTime date = DateUtil.date();
        rateParam.setEffectDate(ObjectUtil.isEmpty(param.getFiscalPeriod()) ? date : DateUtil.parse(param.getFiscalPeriod(), "yyyy-MM"));
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
        ltWalmartSettlementReportMapper.insertStructure(param);

        return ResponseData.success();

    }

    @DataSource(name = "finance")
    @Override
    public int updatePeopleCostZero(LtWalmartSettlementReportParam param) {
        return ltWalmartSettlementReportMapper.updatePeopleCostZero(param);
    }

}