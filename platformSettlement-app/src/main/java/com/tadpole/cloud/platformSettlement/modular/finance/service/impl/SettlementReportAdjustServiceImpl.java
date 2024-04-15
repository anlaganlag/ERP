package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportExportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SettlementRepoertAdjustMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SettlementRepoertMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SettlementReportFinalMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.model.params.AllocStructureParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.AllocStructureService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementReportAdjustService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementReportFinalService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
* <p>
* 结算报告 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
@Slf4j
public class SettlementReportAdjustServiceImpl extends ServiceImpl<SettlementRepoertAdjustMapper, SettlementReportAdjust> implements ISettlementReportAdjustService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Autowired
    SettlementRepoertAdjustMapper settlementRepoertAdjustMapper;

    @Autowired
    SettlementReportFinalMapper settlementReportFinalMapper;

    @Resource
    SettlementRepoertMapper settlementRepoertMapper;


    @Autowired
    ISettlementReportFinalService settlementReportFinalService;


    @Autowired
    AllocStructureService allocStructureService;

    @Value("${rediskey.confirmSettlement}")
    public String confirmSettlement;

    @Autowired
    ISpotExchangeRateService spotExchangeRateService;

    @DataSource(name = "finance")
    @Override
    public PageResult<SettlementReportAdjustResult> findPageBySpec(SettlementReportAdjustParam param) {
        Page pageContext = param.getPageContext();

        IPage<SettlementReportAdjustResult> page = settlementRepoertAdjustMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData confirm(SettlementReportAdjustParam param) {
        List<Map<String,String>> integers = this.adjustRepeatDimension(param);
        StringBuffer repeatDimension = new StringBuffer();
        if (ObjectUtil.isNotEmpty(integers)) {
            for (Map<String, String> integer : integers) {
                String dimension = integer.get("DIMENSION");
                dimension = "[" + dimension + "] " ;
                repeatDimension.append(dimension);
            }
        }
        if (repeatDimension.length()>0) {
            return ResponseData.error(StrUtil.format("重复维度调整报告: {}",repeatDimension));
        }

        QueryWrapper<SettlementReportAdjust> qs=new QueryWrapper<>();
        qs.eq("id",param.getId());
        SettlementReportAdjust check=settlementRepoertAdjustMapper.selectOne(qs);
        if(check.getConfirmStatus().equals(BigDecimal.ZERO)){
            SettlementReportAdjust ss=new SettlementReportAdjust();

            ss.setId(param.getId());
            ss.setConfirmStatus(new BigDecimal(1));
            ss.setConfirmBy(LoginContext.me().getLoginUser().getName());
            ss.setConfirmDate(new Date());

            settlementRepoertAdjustMapper.updateById(ss);

        }
        return ResponseData.success();

    }

    @DataSource(name = "finance")
    @Override
    public ResponseData confirmBatch(SettlementReportAdjustParam param) {

        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmSettlement);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");

            QueryWrapper<SettlementReportAdjust> queryWrapper=new QueryWrapper<>();
            queryWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites());
            List<SettlementReportAdjust> ss=this.list(queryWrapper);
            queryWrapper.clear();

            if (CollUtil.isEmpty(ss)) {
                return ResponseData.success("无可确认的数据!");
            }

            //批量保存
            for(SettlementReportAdjust pa:ss) {
                param.setId(pa.getId());
                ResponseData confirm = this.confirm(param);
                if (confirm.getCode() == 500) {
                    return ResponseData.error(confirm.getMessage());
                }
            }

            return ResponseData.success(ss);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:"+e);
        } finally{
            toList.set("");
        }
        //for (SettlementReportAdjustParam param : params) {
        //    this.confirm(param);
        //}
    }

//    @DataSource(name = "finance")
//    @Override
//    public ResponseData refreshAmount(SettlementReportAdjustParam param) {
//        //kindle广告费
//        settlementRepoertAdjustMapper.updateKindleFee(param);
//        //海外税费
//        settlementRepoertAdjustMapper.updateOverSeasFee(param);
//        //回款
//        settlementRepoertAdjustMapper.updateCollecTionFee(param);
//        //Profit
//        settlementRepoertAdjustMapper.updateProfitFee(param);
//        //刷退货数量
//        settlementRepoertAdjustMapper.updateReturnAmount();
//        return ResponseData.success();
//    }

//    @DataSource(name = "finance")
//    @Override
//    public ResponseData refreshReturnAmount() {
//        settlementRepoertAdjustMapper.updateReturnAmount();
//        return ResponseData.success();
//    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementReportExportResult> export(SettlementReportAdjustParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<SettlementReportExportResult> page = settlementRepoertAdjustMapper.export(pageContext, param);
        return page.getRecords();
    }



    @DataSource(name = "finance")
    @Override
    public ResponseData importSettlementReportAdjust(MultipartFile file) {
        log.info("结算报告调整-导入:Excel处理开始");
        BufferedInputStream buffer = null;
        String operater = "系统生成";
        if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
            operater = LoginContext.me().getLoginUser().getName();
        }
        DateTime date = DateUtil.date();

        String column10 = "fiscal_period || shop_name || site || NVL(sku,'0') || income_type || NVL(report_type,'0') || NVL(department,'0') || NVL(team,'0') || NVL(material_code,'0') || NVL(product_type,'0')";

        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<SettlementReportAdjust>();
            EasyExcel.read(buffer, SettlementReportAdjust.class, listener).sheet()
                    .doRead();

            List<SettlementReportAdjust> dataList = listener.getDataList();
            //仓储费用业务不手动导入,误导入需清空该字段
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("结算报告调整-导入为空！");
            }

            //异常数据集合
            List<SettlementReportAdjust> errorList = new ArrayList<>();
//            this.validation(dataList, errorList, account, departmentList, teamList);
            if (dataList.stream().filter(i-> ObjectUtil.isEmpty(i.getFiscalPeriod())).count()>0) {
                log.error(StrUtil.format("导入失败:存在会计区间为空"));
                return ResponseData.error(StrUtil.format("导入失败:存在会计区间为空"));
            }
            if (dataList.stream().filter(i-> ObjectUtil.isEmpty(i.getShopName())).count()>0) {
                log.error(StrUtil.format("导入失败:存在店铺为空"));
                return ResponseData.error(StrUtil.format("导入失败:存在店铺为空"));
            }
            if (dataList.stream().filter(i-> ObjectUtil.isEmpty(i.getSite())).count()>0) {
                log.error(StrUtil.format("导入失败:存在站点为空"));
                return ResponseData.error(StrUtil.format("导入失败:存在站点为空"));
            }
            if (dataList.stream().filter(i-> ObjectUtil.isEmpty(i.getIncomeType())).count()>0) {
                log.error(StrUtil.format("导入失败:存在收入确认类型为空"));
                return ResponseData.error(StrUtil.format("导入失败:存在收入确认类型为空"));
            }

            HashSet<String> dimensions = new HashSet<>();
            HashSet<String> repeatedDimension = new HashSet<>();
            for (SettlementReportAdjust i : dataList) {
                try {
                    DateUtil.parse(i.getFiscalPeriod(),"YYYY-MM");
                } catch (Exception e){
                    log.error(StrUtil.format("导入失败:日期转换转换异常[{}]",i.getFiscalPeriod()));
                    return ResponseData.error(StrUtil.format("导入失败:日期转换转换异常[{}]",i.getFiscalPeriod()));
                }
                i.setCreateTime(date);
                i.setCreateBy(operater);
                String reportType = ObjectUtil.isEmpty(i.getReportType())?"0":i.getReportType();
                String sku = ObjectUtil.isEmpty(i.getSku())?"0":i.getSku();
                String department = ObjectUtil.isEmpty(i.getDepartment())?"0":i.getDepartment();
                String team = ObjectUtil.isEmpty(i.getTeam())?"0":i.getTeam();
                String materialCode = ObjectUtil.isEmpty(i.getMaterialCode())?"0":i.getMaterialCode();
                String productType = ObjectUtil.isEmpty(i.getProductType())?"0":i.getProductType();
                i.setReportType(reportType);
                i.setSku(sku);
                i.setDepartment(department);
                i.setTeam(team);
                i.setMaterialCode(materialCode);
                i.setProductType(productType);
                String d10 = i.getFiscalPeriod() + i.getShopName() + i.getSite() + sku + i.getIncomeType() + reportType + department + team + materialCode + productType;
                if (dimensions.contains(d10)) {
                    repeatedDimension.add(d10);

                }
                dimensions.add(d10);
            }
            if (ObjectUtil.isNotEmpty(repeatedDimension)) {
                log.error(StrUtil.format("检测到重复维度: {}",repeatedDimension));
                return ResponseData.error(StrUtil.format("检测到重复维度: {}",repeatedDimension));
            }

            if (ObjectUtil.isNotEmpty(dimensions)) {
                List<List<String>> dimensionsSplit = CollUtil.split(dimensions, 500);
                for (List<String> ds : dimensionsSplit) {
                    //是否有已确认维度的数据
                    QueryWrapper<SettlementReportAdjust> confirmQueryWrapper = new QueryWrapper<>();
                    confirmQueryWrapper
                            .eq("confirm_status",1)
                            .in(column10,ds);
                    List<SettlementReportAdjust> confirmList=this.baseMapper.selectList(confirmQueryWrapper);
                    List<String> confirmDimensionList = confirmList.stream().map(i->i.getFiscalPeriod()+i.getShopName()+i.getSite()+(ObjectUtil.isNotEmpty(i.getSku())?i.getSku():"0")+i.getIncomeType()+(ObjectUtil.isNotEmpty(i.getReportType())?i.getReportType():"0")+"\n").collect(Collectors.toList());
                    if (ObjectUtil.isNotEmpty(confirmDimensionList)) {
                        log.error(StrUtil.format("检测到已确认维度: {}",confirmDimensionList));
                        return ResponseData.error(StrUtil.format("检测到已确认维度: {}",confirmDimensionList));
                    }

                    //删除非确认重复维度
                    QueryWrapper<SettlementReportAdjust> queryWrapper = new QueryWrapper<>();
                    queryWrapper
                            .select("id")
                            .ne("confirm_status",1)
                            .in(column10,ds);
                    List<SettlementReportAdjust> datarangeList=this.baseMapper.selectList(queryWrapper);
                    List<String> idList = datarangeList.stream().map(SettlementReportAdjust::getId).collect(Collectors.toList());
                    this.removeByIds(idList);
                }
            }






            //批量保存更新表数据
            if (CollectionUtil.isNotEmpty(dataList)) {
                this.saveBatch(dataList);
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
        } catch (Exception e) {log.error("导入Excel处理异常，导入失败！", e.getMessage());
            return ResponseData.error("导入Excel处理异常，导入失败！"+e.getMessage());
        }
    }

    @DataSource(name = "finance")
    @Override
    public SettlementReportAdjustResult getQuantity(SettlementReportAdjustParam param) {
        return settlementRepoertAdjustMapper.getQuantity(param);
    }


    @DataSource(name = "finance")
    @Override
    public ResponseData adjustReportByMerge(SettlementReportAdjustParam param) {
         settlementRepoertAdjustMapper.adjustReportByMerge(param);
         return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public List<Map<String,String>> adjustRepeatDimension(SettlementReportAdjustParam param) {
        return settlementRepoertAdjustMapper.adjustRepeatDimension(param);
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData fillPeopleCost(SettlementReportAdjustParam param) {
        settlementRepoertAdjustMapper.fillPeopleCost(param);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData fillPeopleCostNew(SettlementReportAdjustParam param) {
        settlementRepoertAdjustMapper.fillPeopleCostNew(param);
        return ResponseData.success();
    }



    @DataSource(name = "finance")
    @Override
    public ResponseData adjustReport(SettlementReportAdjustParam param) {
        List<SettlementReportFinal> finalList = settlementRepoertAdjustMapper.adjustReport(param);
        settlementReportFinalService.saveOrUpdateBatch(finalList);
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData getFinalReportSql(SettlementReportAdjustParam param){
        try {
            if (ObjectUtil.isEmpty(param.getFiscalPeriod())) {
                return ResponseData.error("生成最终结算报告:未指定会计区间");
            }

            //0.当前会计架构是否确认
            AllocStructureParam allocStructureParam = AllocStructureParam.builder().period(param.getFiscalPeriod()).build();
            if (allocStructureService.isNoComfirm(allocStructureParam)) {
                return ResponseData.error(StrUtil.format("会计区间[{}]分摊架构未确认", param.getFiscalPeriod()));
            }

            if (new LambdaQueryChainWrapper<>(settlementReportFinalMapper)
                    .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), SettlementReportFinal::getFiscalPeriod, param.getFiscalPeriod())
                    .in(ObjectUtil.isNotEmpty(param.getShopNames()), SettlementReportFinal::getShopName, param.getShopName())
                    .in(ObjectUtil.isNotEmpty(param.getSites()), SettlementReportFinal::getSite, param.getSites())
                    .eq(SettlementReportFinal::getConfirmStatus, BigDecimal.ONE)
                    .count()>0) {
                return ResponseData.error("存在已确认最终结算报告");
            }


            //无对应已确认结算报告
          ;
            if ( new LambdaQueryChainWrapper<>(settlementRepoertMapper)
                    .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), SettlementReport::getFiscalPeriod, param.getFiscalPeriod())
                    .in(ObjectUtil.isNotEmpty(param.getShopNames()), SettlementReport::getShopName, param.getShopName())
                    .in(ObjectUtil.isNotEmpty(param.getSites()), SettlementReport::getSite, param.getSites())
                    .eq(SettlementReport::getConfirmStatus, BigDecimal.ONE)
                    .count() == 0) {
                return ResponseData.error("无对应已确认结算报告");
            }

            param.setCreateBy(ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "系统生成");
            param.setCreateTime(DateUtil.date());
            //1.报告+调整报告 = 初步终结算报告
            settlementRepoertAdjustMapper.adjustReportByMerge(param);

            //获取CNY->USD费率
            SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
            rateParam.setOriginalCurrency("CNY");
            rateParam.setEffectDate(ObjectUtil.isEmpty(param.getFiscalPeriod()) ? DateUtil.date() : DateUtil.parse(param.getFiscalPeriod(), "yyyy-MM"));
            SpotExchangeRate rateByDateCurrency = spotExchangeRateService.getRateByDateCurrency(rateParam);
            BigDecimal directRate = rateByDateCurrency.getDirectRate();
            if (ObjectUtil.isEmpty(directRate)) {
                log.error(StrUtil.format("无法获取当期[{}]CNY直接汇率", rateParam.getEffectDate()));
                return ResponseData.error(StrUtil.format("无法获取当期[{}]CNY直接汇率", rateParam.getEffectDate()));
            }


/**
 *  sql实现 刷销量占比,人数及人力成本字段
 */
            param.setDirectRate(directRate);
//            settlementRepoertAdjustMapper.fillPeopleCost(param);
            settlementRepoertAdjustMapper.fillPeopleCostNew(param);
            settlementRepoertAdjustMapper.updatePeopleCostZero(param);





            return ResponseData.success();
        }catch (Exception e){
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error(e.getMessage());
        }

/**
    下面代码实现会慢
 */

//        //最终结算补充架构数据(占比,人数*占比,人数*占比*费用)
//        List<SettlementReportFinalResult> mergedFinalSettle = settlementRepoertAdjustMapper.mergeFinalSettleNallocStruct(param);
//        List<SettlementReportFinal> updateFinalList = new ArrayList<>();
//
//        //结算和结构未匹配上数据(有架构无结算无明细或有结算明细无架构)  销量占比固定为1,人数取架构分摊亚马逊人数,成本 = 7000 *  direct * 人数
//        List<SettlementReportFinal> unMatchSettlement = mergedFinalSettle.stream().filter(i -> ObjectUtil.isEmpty(i.getId()) || ObjectUtil.isEmpty(i.getStructId())).map(i -> {
//            SettlementReportFinal settlementReportFinal = new SettlementReportFinal();
//            BeanUtil.copyProperties(i,settlementReportFinal);
//            settlementReportFinal.setRevenueRatio(BigDecimal.ONE);
//            BigDecimal amazonAllocPeopleNum = ObjectUtil.isEmpty(i.getAmazonAlloc()) ? BigDecimal.ZERO : i.getAmazonAlloc();
//            settlementReportFinal.setPeopleNum(amazonAllocPeopleNum);
//            settlementReportFinal.setPeopleCost(amazonAllocPeopleNum.multiply(directRate).multiply(new BigDecimal(-7000)));
//            settlementReportFinal.setId(IdWorker.getIdStr());
//            return settlementReportFinal;
//        }).collect(Collectors.toList());
//        updateFinalList.addAll(unMatchSettlement);
//
//        //有明细 占比(明细/Team销量) 占比*人数 占比*人数*成本 人力成本固定7000
//        List<SettlementReportFinal> matchedSettlement = mergedFinalSettle.stream().filter(i -> ObjectUtil.isNotEmpty(i.getId())).map(i -> {
//            SettlementReportFinal settlementReportFinal = new SettlementReportFinal();
//            BeanUtil.copyProperties(i,settlementReportFinal);
//            BigDecimal skuTeamRatio;
//            //明细为0,占比为0
//            if (ObjectUtil.isEmpty(i.getDetailsalesvol())) {
//                skuTeamRatio = BigDecimal.ZERO;
//                //无对应Team数据占比为1
//            } else  if (ObjectUtil.isEmpty(i.getTeamsalesvol()) || i.getTeamsalesvol().compareTo(BigDecimal.ZERO) ==0){
//                skuTeamRatio = BigDecimal.ONE;
//            } else {
//                //明细销量比Team销量
//                skuTeamRatio = i.getDetailsalesvol().divide(i.getTeamsalesvol(), 12, BigDecimal.ROUND_HALF_UP);
//            }
//            settlementReportFinal.setRevenueRatio(skuTeamRatio);
//            BigDecimal amazonAllocPeopleNum = ObjectUtil.isEmpty(i.getAmazonAlloc()) ? BigDecimal.ZERO : i.getAmazonAlloc();
//
//            settlementReportFinal.setPeopleNum(amazonAllocPeopleNum.multiply(skuTeamRatio));
//            settlementReportFinal.setPeopleCost(amazonAllocPeopleNum.multiply(skuTeamRatio).multiply(directRate).multiply(new BigDecimal(-7000)));
//            return settlementReportFinal;
//        }).collect(Collectors.toList());
//        updateFinalList.addAll(matchedSettlement);
//
//        //未确认重新生成
//        new LambdaUpdateChainWrapper<>(settlementReportFinalMapper)
//                .eq(ObjectUtil.isEmpty(param.getFiscalPeriod()),SettlementReportFinal::getFiscalPeriod, DateUtil.format(DateUtil.date(),"YYYY-MM"))
//                .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()),SettlementReportFinal::getFiscalPeriod, param.getFiscalPeriod())
//                .eq(SettlementReportFinal::getConfirmStatus,0)
//                .remove();
//
//
//        settlementReportFinalService.saveBatch(updateFinalList);






    }

    @DataSource(name = "finance")
    @Override
    public int updatePeopleCostZero(SettlementReportAdjustParam param) {
        return settlementRepoertAdjustMapper.updatePeopleCostZero(param);
    }


}
