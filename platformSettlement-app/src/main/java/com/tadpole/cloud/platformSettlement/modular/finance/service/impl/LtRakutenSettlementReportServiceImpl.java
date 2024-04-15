package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LtRakutenSettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AllocStructureMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LtRakutenSettlementReportMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.BoxDimensionParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.DepartTeamProductTypeParam;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.LtRakutenSettlementReportParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.LtRakutenSettlementReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Get;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ;(LT_RAKUTEN_SETTLEMENT_REPORT)表服务实现类
 * @author : LSY
 * @date : 2023-12-22
 */
@Service
@Transactional
@Slf4j
public class LtRakutenSettlementReportServiceImpl  extends ServiceImpl<LtRakutenSettlementReportMapper, LtRakutenSettlementReport> implements LtRakutenSettlementReportService {
    @Resource
    private LtRakutenSettlementReportMapper ltRakutenSettlementReportMapper;

    @Resource
    AllocStructureService allocStructureService;

    @Resource
    LtRakutenSettlementReportService ltRakutenSettlementReportService;


    @Resource
    private LtRakutenSettlementReportService rakutenSettlementReportService;

    @Resource
    ISpotExchangeRateService spotExchangeRateService;

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
    public LtRakutenSettlementReport queryById(String undefinedId) {
        return ltRakutenSettlementReportMapper.selectById(undefinedId);
    }

    /**
     * 分页查询
     *
     * @param param   筛选条件
     * @param current 当前页码
     * @param size    每页大小
     * @return
     */
    @DataSource(name = "finance")
    @Override
    public Page<LtRakutenSettlementReportResult> paginQuery(LtRakutenSettlementReportParam param, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtRakutenSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getPeriod()), LtRakutenSettlementReport::getPeriod, param.getPeriod());

        //2. 执行分页查询
        Page<LtRakutenSettlementReportResult> pagin = new Page<>(current, size, true);
        IPage<LtRakutenSettlementReportResult> selectResult = ltRakutenSettlementReportMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    @DataSource(name = "finance")
    @Override
    public List<LtRakutenSettlementReport> paginExport(LtRakutenSettlementReportParam param) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtRakutenSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getId()), LtRakutenSettlementReport::getId, param.getId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCategory()), LtRakutenSettlementReport::getCategory, param.getCategory());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductType()), LtRakutenSettlementReport::getProductType, param.getProductType());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getProductName()), LtRakutenSettlementReport::getProductName, param.getProductName());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getStyle()), LtRakutenSettlementReport::getStyle, param.getStyle());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getMainMaterial()), LtRakutenSettlementReport::getMainMaterial, param.getMainMaterial());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getDesign()), LtRakutenSettlementReport::getDesign, param.getDesign());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getCompanyBrand()), LtRakutenSettlementReport::getCompanyBrand, param.getCompanyBrand());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getFitBrand()), LtRakutenSettlementReport::getFitBrand, param.getFitBrand());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getModel()), LtRakutenSettlementReport::getModel, param.getModel());
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getColor()), LtRakutenSettlementReport::getColor, param.getColor());
        //2. 执行分页查询
        List<LtRakutenSettlementReport> selectResult = ltRakutenSettlementReportMapper.exportByPage(queryWrapper);
        //3. 返回结果
        return selectResult;
    }

    @DataSource(name = "finance")
    @Override
    public List<LtRakutenSettlementReportExport> downloadExport(LtRakutenSettlementReportParam param) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<LtRakutenSettlementReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LtRakutenSettlementReport::getId, "-1");

        //2. 执行分页查询
        List<LtRakutenSettlementReportExport> selectResult = ltRakutenSettlementReportMapper.downloadExport(queryWrapper);
        //3. 返回结果
        return selectResult;
    }


    /**
     * 新增数据
     *
     * @param ltRakutenSettlementReport 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtRakutenSettlementReport insert(LtRakutenSettlementReport ltRakutenSettlementReport) {
        ltRakutenSettlementReportMapper.insert(ltRakutenSettlementReport);
        return ltRakutenSettlementReport;
    }

    /**
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "finance")
    @Override
    public LtRakutenSettlementReport update(LtRakutenSettlementReportParam param) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<LtRakutenSettlementReport> wrapper = new LambdaUpdateChainWrapper<LtRakutenSettlementReport>(ltRakutenSettlementReportMapper);
        //2. 设置主键，并更新
        wrapper.eq(LtRakutenSettlementReport::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(LtRakutenSettlementReport::getUpdateTime, new Date());
        wrapper.set(LtRakutenSettlementReport::getUpdateBy, loginUser.getName());
        wrapper.set(LtRakutenSettlementReport::getProductType, param.getProductType());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(param.getId());
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param
     * @return 是否成功
     */
    @DataSource(name = "finance")
    @Override
    public ResponseData mergeRakutenSettleNallocStruct(LtRakutenSettlementReportParam param) {
        List<LtRakutenSettlementReportResult> ltRakutenSettlementReportResults = ltRakutenSettlementReportMapper.mergeRakutenSettleNallocStruct(param);
        return ResponseData.success(ltRakutenSettlementReportResults);
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
        int delCount = ltRakutenSettlementReportMapper.deleteBatchIds(undefinedIdList);
        if (undefinedIdList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData importSettlementReport(MultipartFile file, List<String> departmentList, List<String> teamList, List<String> shopList, List<String> siteList) {
        log.info("小平台结算报告乐天平台-导入:Excel处理开始");
        BufferedInputStream buffer = null;

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<LtRakutenSettlementReport>();
            EasyExcel.read(buffer, LtRakutenSettlementReport.class, listener).sheet()
                    .doRead();

            List<LtRakutenSettlementReport> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("小平台结算报告乐天平台-导入为空！");
            }

            //异常数据集合
            List<LtRakutenSettlementReport> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);
            String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "系统生成";
            if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getShopName()) || !shopList.contains(i.getShopName()))) {
                log.error(StrUtil.format("存在异常账号:可选账号[{}]", String.join("、", shopList)));
                return ResponseData.error(StrUtil.format("存在异常账号:可选账号[{}]", String.join("、", shopList)));
            }


            if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getDepartment()) || !departmentList.contains(i.getDepartment()))) {
                log.error(StrUtil.format("存在异常事业部:可选事业部[{}]", String.join("、", departmentList)));
                return ResponseData.error(StrUtil.format("存在异常事业部:可选事业部[{}]", String.join("、", departmentList)));
            }

            if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getTeam()) || !teamList.contains(i.getTeam()))) {
                log.error(StrUtil.format("存在异常Team:可选Team[{}]", String.join("、", teamList)));
                return ResponseData.error(StrUtil.format("存在异常Team:可选Team[{}]", String.join("、", teamList)));
            }


            DateTime date = DateUtil.date();
            String pattern = "\\d{4}-\\d{2}";
            if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getPeriod()) || !i.getPeriod().matches(pattern))) {
                log.error("导入失败:期间正确格式yyyy-MM");
                return ResponseData.error(StrUtil.format("导入失败:期间正确格式yyyy-MM"));
            }


            if (dataList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getMatCode()))) {
                return ResponseData.error(StrUtil.format("导入失败:存在物料编码为空"));
            }
            for (LtRakutenSettlementReport i : dataList) {

                if (ObjectUtil.isNotEmpty(i.getCreateBy())) {
                    i.setUpdateBy(operator);
                    i.setUpdateTime(date);
                } else {
                    i.setCreateBy(operator);
                    i.setCreateTime(date);
                }
                ;
                i.setPlatform("Rakuten");
                try {
                    if (ObjectUtil.isNotEmpty(i.getFirstOrderDate())) {
                        DateTime parse = DateUtil.parse(i.getFirstOrderDate(), "yyyy-MM-dd");
                        i.setFirstOrderDate(DateUtil.format(parse, "yyyy-MM-dd hh:mm:ss"));
                    }
                } catch (Exception e) {
                    log.error(StrUtil.format("导入失败:最早下单日期转换异常[{}]", i.getFirstOrderDate()));
                    return ResponseData.error(StrUtil.format("导入失败:最早下单日期:[{}]转换异常,正确格式yyyy-MM-dd hh:mm:ss", i.getFirstOrderDate()));
                }
                if ((ObjectUtil.isNotEmpty(i.getPacking()) && i.getPacking().contains("#")) ||
                        (ObjectUtil.isNotEmpty(i.getVersion()) && i.getVersion().contains("#")) ||
                        (ObjectUtil.isNotEmpty(i.getType()) && i.getType().contains("#")) ||
                        (ObjectUtil.isNotEmpty(i.getStyleSecondLabel()) && i.getStyleSecondLabel().contains("#"))) {
                    log.error("存在#乱码数据#");
                    return ResponseData.error("存在#乱码数据#");
                }
            }


            //批量保存更新表数据
            if (CollectionUtil.isNotEmpty(dataList)) {
                String period = dataList.get(0).getPeriod();
                new LambdaUpdateChainWrapper<>(ltRakutenSettlementReportMapper)
                        .eq(ObjectUtil.isNotEmpty(period), LtRakutenSettlementReport::getPeriod, period)
                        .ne(LtRakutenSettlementReport::getConfirmStatus, 1)
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
        } catch (Exception e) {
            log.error("导入Excel处理异常，导入失败！", e);
            return ResponseData.error("导入Excel处理异常，导入失败！" + e.getMessage());
        }
    }


    @DataSource(name = "finance")
    @Override
    public boolean deleteById(String undefinedId) {
        int total = ltRakutenSettlementReportMapper.deleteById(undefinedId);
        return total > 0;
    }


    @DataSource(name = "finance")
    @Override
    public int updatePeopleCostZero(LtRakutenSettlementReportParam param) {
        return ltRakutenSettlementReportMapper.updatePeopleCostZero(param);
    }





    @DataSource(name = "finance")
    @Override
    public ResponseData autoCalPeopleCost(LtRakutenSettlementReportParam param) {
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

        LambdaQueryWrapper<LtRakutenSettlementReport> shopifyQw = new LambdaQueryWrapper<>();
        shopifyQw.eq(LtRakutenSettlementReport::getPeriod,param.getPeriod());
        if (ltRakutenSettlementReportMapper.selectCount(shopifyQw) == 0) {
            return ResponseData.error(StrUtil.format("当前会计期间[{}]无数据", param.getPeriod()));
        }


        String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "系统生成";


        //最终结算补充架构数据(占比,人数*占比,人数*占比*费用)
        List<LtRakutenSettlementReportResult> mergedRakutenSettle = ltRakutenSettlementReportMapper.mergeRakutenSettleNallocStruct(param);

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

        //结算和结构未匹配上数据(有架构无结算无明细或有结算明细无架构)  销量占比固定为1,人数取架构分摊亚马逊人数,成本 = 7000 *  direct * 人数
        List<LtRakutenSettlementReport> unMatchSettlement = mergedRakutenSettle.stream().filter(i -> ObjectUtil.isEmpty(i.getId()) || ObjectUtil.isEmpty(i.getStructId())).map(i -> {
            LtRakutenSettlementReport ltRakutenSettlementReport = new LtRakutenSettlementReport();
            BeanUtil.copyProperties(i, ltRakutenSettlementReport);
            ltRakutenSettlementReport.setRevenueRation(BigDecimal.ONE);
            BigDecimal amazonAllocPeopleNum = ObjectUtil.isEmpty(i.getAmazonAlloc()) ? BigDecimal.ZERO : i.getAmazonAlloc();
            ltRakutenSettlementReport.setPeopleNum(amazonAllocPeopleNum.setScale(12, RoundingMode.HALF_UP));
            ltRakutenSettlementReport.setPeopleCost(amazonAllocPeopleNum.multiply(directRate).multiply(new BigDecimal(-7000)));
            ltRakutenSettlementReport.setId(IdWorker.getIdStr());
            return ltRakutenSettlementReport;
        }).collect(Collectors.toList());
        List<LtRakutenSettlementReport> updateRakutenList = new ArrayList<>(unMatchSettlement);

        //有明细 占比(明细/Team销量) 占比*人数 占比*人数*成本 人力成本固定7000
        List<LtRakutenSettlementReport> matchedSettlement = mergedRakutenSettle.stream().filter(i -> ObjectUtil.isNotEmpty(i.getId()) && ObjectUtil.isNotEmpty(i.getStructId())).map(i -> {
            LtRakutenSettlementReport ltRakutenSettlementReport = new LtRakutenSettlementReport();
            BeanUtil.copyProperties(i, ltRakutenSettlementReport);
            BigDecimal skuTeamRatio;
            //明细为0,占比为0
            if (ObjectUtil.isEmpty(i.getDetailsalesvol())) {
                skuTeamRatio = BigDecimal.ZERO;
                //无对应Team数据占比为1
            } else if (ObjectUtil.isEmpty(i.getTeamsalesvol()) || i.getTeamsalesvol().compareTo(BigDecimal.ZERO) == 0) {
                skuTeamRatio = BigDecimal.ONE;
            } else {
                //明细销量比Team销量
                skuTeamRatio = i.getDetailsalesvol().divide(i.getTeamsalesvol(), 12, BigDecimal.ROUND_HALF_UP);
            }
            ltRakutenSettlementReport.setRevenueRation(skuTeamRatio);
            BigDecimal amazonAllocPeopleNum = ObjectUtil.isEmpty(i.getAmazonAlloc()) ? BigDecimal.ZERO : i.getAmazonAlloc();

            ltRakutenSettlementReport.setPeopleNum(amazonAllocPeopleNum.multiply(skuTeamRatio).setScale(12, RoundingMode.HALF_UP));
            ltRakutenSettlementReport.setPeopleCost(amazonAllocPeopleNum.multiply(skuTeamRatio).multiply(directRate).multiply(new BigDecimal(-7000)));
            ltRakutenSettlementReport.setUpdateTime(date);
            ltRakutenSettlementReport.setUpdateBy(operator);
            return ltRakutenSettlementReport;
        }).collect(Collectors.toList());
        updateRakutenList.addAll(matchedSettlement);
//
        //未确认重新生成
        new LambdaUpdateChainWrapper<>(ltRakutenSettlementReportMapper)
                .eq(ObjectUtil.isEmpty(param.getPeriod()), LtRakutenSettlementReport::getPeriod, DateUtil.format(date, "YYYY-MM"))
                .eq(ObjectUtil.isNotEmpty(param.getPeriod()), LtRakutenSettlementReport::getPeriod, param.getPeriod())
                .eq(LtRakutenSettlementReport::getConfirmStatus, 0)
                .remove();


        ltRakutenSettlementReportService.saveOrUpdateBatch(updateRakutenList);
        ltRakutenSettlementReportService.updatePeopleCostZero(param);


        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData haveReport(LtRakutenSettlementReportParam param) {
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

        LambdaQueryWrapper<LtRakutenSettlementReport> shopifyQw = new LambdaQueryWrapper<>();
        shopifyQw.eq(LtRakutenSettlementReport::getPeriod, param.getPeriod());
        if (ltRakutenSettlementReportMapper.selectCount(shopifyQw) == 0) {
            log.error("报告当前会计期间[{}]无数据", param.getPeriod());
            return ResponseData.error("数据为空");
        }
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData insertStructure(LtRakutenSettlementReportParam param) {
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
        ltRakutenSettlementReportMapper.insertStructure(param);

        return ResponseData.success();

    }

    @DataSource(name = "stocking")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> getProductType(DepartTeamProductTypeParam param) {
        List<DepartTeamProductTypeParam> productTypeList = ltRakutenSettlementReportMapper.getProductType(param);
        String productTypeStr = "";
        for (DepartTeamProductTypeParam pt : productTypeList) {
            if (pt.getDepartment().equals(param.getDepartment()) && pt.getTeam().equals(param.getTeam())) {
                productTypeStr = pt.getProductType();
            }
        }
        if (ObjectUtil.isEmpty(productTypeStr)) {
            productTypeStr = productTypeList.stream().filter(pt -> "0".equals(pt.getDepartment()) && "0".equals(pt.getTeam())).map(DepartTeamProductTypeParam::getProductType).collect(Collectors.toList()).get(0);
        }
        if (productTypeStr != null) {
            return Arrays.stream(productTypeStr.split(",")).map(String::trim).collect(Collectors.toCollection(ArrayList::new));
        }else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        String fileName = "p1.csv";
//        String fileName = "/新版pack list.xlsx/";

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("CSV Data");


        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int rowNum = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Assuming the CSV is comma-separated
                Row row = sheet.createRow(rowNum++);

                for (int i = 0; i < values.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(values[i].replaceAll("^\"|\"$", ""));
                }
            }
        }


        double totalBox = 0;
        int colDimension = 5;
        int skipCol = 1;
        List<List<Double>> listOfDimensions = new ArrayList<>();
        List<String> listOfBoxNames = new ArrayList<>();



        for (Row row : sheet) {
            for (Cell cell : row) {
                String trim = cell.getStringCellValue().trim();
                if (trim.contains("箱子数量")) {
                    int columnIndex = cell.getColumnIndex()+1;
                    Cell cellTotal = row.getCell(columnIndex);
                    if (cellTotal != null) {
                        String totalBoxString = cellTotal.getStringCellValue();
                        try {
                            totalBox = Double.parseDouble(totalBoxString);
                        } catch (NumberFormatException e) {
                            System.out.println("获取总箱数失败");
                        }
                            break;
                        }
                }
                if (trim.contains("箱子名称")) {
                    int row0 = cell.getRowIndex() ;
                    int col0 = cell.getColumnIndex() ;
                    for(int r = 0; r < colDimension; r++) {
                        int curRow = row0 + r;
                        Row theRow = sheet.getRow(curRow);
                        List<Double> curDimensions = new ArrayList<>();
                        if (theRow != null) {
                            for (int c = 0; c < totalBox; c++) {
                                int curCol = col0 + skipCol + c;
                                Cell cellWeight = theRow.getCell(curCol);
                                if (cellWeight != null) {
                                    if (r == 0) {
                                        listOfBoxNames.add(cellWeight.getStringCellValue());
                                    } else {
                                        curDimensions.add(Double.parseDouble(cellWeight.getStringCellValue()));
                                    }
                                }
                            }

                        }
                        if (ObjectUtil.isNotEmpty(curDimensions)) {
                            listOfDimensions.add(curDimensions);
                        }

                    }

                }
                }
            }
        List<BoxDimensionParam> boxList = new ArrayList<>();
        Double poundToKgRatio = 0.45359237;
        Double inchToCmRatio = 2.54;
        if (ObjectUtil.isNotEmpty(listOfDimensions)) {
            List<Double> weightList = listOfDimensions.get(0);
            List<Double> widthList = listOfDimensions.get(1);
            List<Double> lengthList = listOfDimensions.get(2);
            List<Double> highList = listOfDimensions.get(3);
            for (int i = 0; i < totalBox; i++) {
                BoxDimensionParam box = BoxDimensionParam.builder()
                        .id( i+"1" )
                        .weight(weightList.get(i)*poundToKgRatio)
                        .width(widthList.get(i)*inchToCmRatio)
                        .length(lengthList.get(i)*inchToCmRatio)
                        .high(highList.get(i)*inchToCmRatio)
                        .boxNum(listOfBoxNames.get(i))
                        .boxCount(totalBox)
                        .build();
                boxList.add(box);
            }
        }

        System.out.println("\n>>>>>>>>>>>>>>总箱数:"+totalBox);        // Close the workbook and input stream
        System.out.println("\n>>>>>>>>>>>>>>箱子列表:"+boxList);        // Close the workbook and input stream
        workbook.close();
    }


}