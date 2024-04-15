package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalStorageFeeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalStorageFeeResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalStorageFeeTotal;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.*;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStationManualAllocationService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ITotalStorageFeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
* <p>
* 仓储费合计 数据来源 定期生成 服务实现类
* </p>
*
* @author S20190161
* @since 2022-10-14
*/
@Service
@Slf4j
public class TotalStorageFeeServiceImpl extends ServiceImpl<TotalStorageFeeMapper, TotalStorageFee> implements ITotalStorageFeeService {
    private TotalStorageFeeTotal total=new TotalStorageFeeTotal();

    @Resource
    ITotalStorageFeeService totalStorageFeeService;


    @Resource
    MonthlyStorageFeesMapper monthlyStorageFeesMapper;

    @Resource
    LongTermStorageFeeChargesMapper longTermStorageFeeChargesMapper;



    @Resource
    IStationManualAllocationService stationManualAllocationService;

    @Resource
    StationManualAllocationMapper stationManualAllocationMapper;

    @Resource
    ISpotExchangeRateService spotExchangeRateService;

    @DataSource(name = "finance")
    @Override
    public PageTotalResult<TotalStorageFeeResult, TotalStorageFeeTotal> findPageBySpec(TotalStorageFeeParam param) {
        Page pageContext = param.getPageContext();
        QueryWrapper<TotalStorageFee> queryWrapper=queryWrapper(param);

        IPage<TotalStorageFeeResult> page =  this.baseMapper.selectPage(pageContext,queryWrapper);
        //第一页的时候才查询汇总
        if(pageContext.getCurrent()==1){
            //查询汇总统计
            queryWrapper.select("sum(B_STORAGE_Fee) billStorageFee , sum( B_STORAGE_LONG_Fee ) billStorageLongFee , sum( B_STORAGE_OVERAGE_Fee ) billStorageOverageFee , sum( B_TOTALFee ) billTotalFee ,sum( STORAGE_OVERAGE_Fee ) storageOverageFee , round(sum( STORAGE_Fee ),2) storageFee , sum( STORAGE_LONG_Fee ) storageLongFee , round(sum( TOTAL_Fee ),2) totalFee , round(sum( D_STORAGE_Fee ),2) diffStorageFee , sum( D_STORAGELONG_Fee ) diffStoragelongFee , sum( D_STORAGE_OVERAGE_Fee ) diffStorageOverageFee , round(sum( D_TOTAL_Fee ),2) diffTotalFee ");
            TotalStorageFee Fees = this.baseMapper.selectOne(queryWrapper);
            if (Fees != null)
                BeanUtils.copyProperties(Fees,total);
        }
        return new PageTotalResult<>(page,total);
    }


    /*
    * durDate: yyyy-MM
    * @author AmteMa
    * @date 2024/1/30
    */
    @DataSource(name = "finance")
    @Override
    public void afreshCount(String durDate) {
        String date;
        //从前端手动调用刷新刷一次指定期间数据
        if (StringUtils.isNotEmpty(durDate)){
            Date dateTime = DateUtil.parse(durDate, "yyyy-MM");
            dateTime=DateUtil.offsetMonth(dateTime,1);
            date=DateUtil.format(dateTime,"yyyy-MM-dd");
            this.baseMapper.afreshCount(date);
        }else{
            //定时调度每次刷前两期的数据
            date= LocalDate.now().toString();
            this.baseMapper.afreshCount(date);
            date= LocalDate.now().plusMonths(-1).toString();
            this.baseMapper.afreshCount(date);
        }


    }
    @DataSource(name = "finance")
    @Override
    public List<TotalStorageFee> export(TotalStorageFeeParam param) {
        return this.baseMapper.selectList(queryWrapper(param));
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData pushStorageToManualAllocSQL(TotalStorageFeeParam param) {
        try {
            String startDur = param.getStartDur();
            String endDur = param.getEndDur();
            String shopsName = param.getSysShopsName();
            List<String> shopsNames = param.getSysShopsNames();
            List<String> sites = param.getSysSites();
            LambdaQueryChainWrapper<StationManualAllocation> wp = new LambdaQueryChainWrapper<>(stationManualAllocationMapper)
                    .ge(ObjectUtil.isNotEmpty(startDur), StationManualAllocation::getFiscalPeriod, startDur)
                    .le(ObjectUtil.isNotEmpty(endDur), StationManualAllocation::getFiscalPeriod, endDur)
                    .eq(ObjectUtil.isNotEmpty(shopsName), StationManualAllocation::getShopName, shopsName)
                    .in(ObjectUtil.isNotEmpty(shopsNames), StationManualAllocation::getShopName, shopsNames)
                    .in(ObjectUtil.isNotEmpty(sites), StationManualAllocation::getSite, sites)
                    .isNull(StationManualAllocation::getAllocId);

            List<StationManualAllocation> allocLines = wp.list();
            if (allocLines.stream().allMatch(i->i.getConfirmStatus().compareTo(BigDecimal.ONE) ==0 )) {
                return ResponseData.success("全部数据已确认");
            }
            Map<String, List<StationManualAllocation>> allocLineMap = allocLines.stream().collect(Collectors.groupingBy(s -> s.getFiscalPeriod() + s.getShopName() + s.getSite()));
            //一条
            List<StationManualAllocation> newAllocLines = new ArrayList<>();

            for (Map.Entry<String, List<StationManualAllocation>> dimension : allocLineMap.entrySet()) {
                List<StationManualAllocation> allocNums = dimension.getValue();
                if (allocNums.size() == 1){
                    allocNums.forEach(i -> i.setConfirmStatus(new BigDecimal(2)));
                } else {
                    BigDecimal storageFeeSum = allocNums.stream().map(StationManualAllocation::getStorageFee).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    BigDecimal advertisingFeeSum = allocNums.stream().map(StationManualAllocation::getAdvertising).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    for (StationManualAllocation allocNum : allocNums) {
                        if ("Settlement".equals(allocNum.getReportType())) {
                            allocNum.setConfirmStatus(new BigDecimal(2));
                            allocNum.setStorageFee(storageFeeSum);
                            allocNum.setAdvertising(advertisingFeeSum);
                        }
                    }

                }

            }


            return  ResponseData.success();
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseData.error(e.getMessage());
        }


    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData pushStorageToManualAlloc0(StationManualAllocationParam param) {

        List<StationManualAllocation> list = new LambdaQueryChainWrapper<>(stationManualAllocationMapper)
                .eq(StationManualAllocation::getFiscalPeriod, param.getFiscalPeriod())
                .eq(ObjectUtil.isNotEmpty(param.getShopName()),StationManualAllocation::getShopName, param.getShopName())
                .in(ObjectUtil.isNotEmpty(param.getSites()), StationManualAllocation::getSite, param.getSites())
                .isNull(StationManualAllocation::getAllocId)
                .eq(StationManualAllocation::getConfirmStatus, "0")
                .list();
        Map<String, List<StationManualAllocation>> mapMat = list.stream().collect(Collectors.groupingBy(e->e.getFiscalPeriod()+e.getShopName()+e.getSite()));

        List<StationManualAllocation> itemList = new ArrayList<>();


        for (Map.Entry<String, List<StationManualAllocation>> stringListEntry : mapMat.entrySet()) {
            if (stringListEntry.getValue().size() ==1) {
                stringListEntry.getValue().get(0).setConfirmStatus(new BigDecimal(2));
                itemList.add(stringListEntry.getValue().get(0));
            }else {
                stringListEntry.getValue().forEach(i->{
                    if ("Settlement".equals(i.getReportType())) {
                        i.setConfirmStatus(new BigDecimal(2));
                    }else {
                        i.setConfirmStatus(new BigDecimal(3));
                    }
                });
                itemList.addAll(stringListEntry.getValue());
            }
        }
     new LambdaUpdateChainWrapper<>(stationManualAllocationMapper)
                .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()),StationManualAllocation::getFiscalPeriod, param.getFiscalPeriod())
                .eq(ObjectUtil.isNotEmpty(param.getShopName()),StationManualAllocation::getShopName, param.getShopName())
                .in(ObjectUtil.isNotEmpty(param.getSites()), StationManualAllocation::getSite, param.getSites())
                .isNull(StationManualAllocation::getAllocId)
                .eq(StationManualAllocation::getConfirmStatus, "0")
                .remove();
        stationManualAllocationService.saveBatch(itemList);
        return ResponseData.success();

    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData pushStorageToManualAlloc1(StationManualAllocationParam param) {
        this.baseMapper.updateAllocFee(param);
        return ResponseData.success();


    }
    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData pushStorageToManualAlloc2(StationManualAllocationParam param) {
        this.baseMapper.stationSum2(param);
        return ResponseData.success();


    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData assignAllocLineStatus(StationManualAllocationParam param) {
        this.baseMapper.assignAllocLineStatus(param);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData fillAllocLineVales(StationManualAllocationParam param) {
        this.baseMapper.fillAllocLineVales(param);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData getSkuStorageDetail(StationManualAllocationParam param) {
        List<StationManualAllocation> skuStorageDetail = this.baseMapper.getSkuStorageDetail(param);
        return ResponseData.success(skuStorageDetail);
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData insertOverStorage(StationManualAllocationParam param) {
      this.baseMapper.insertOverStorage(param,new BigDecimal(1.5));
        return ResponseData.success();
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Double toInsertdetailSum(StationManualAllocationParam param) {
        return this.baseMapper.toInsertdetailSum(param);
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer noTicked(StationManualAllocationParam param) {
        return this.baseMapper.noTicked(param);
    }






    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData pushStorageToManualAllocSql(StationManualAllocationParam param) {
        try {
            if (ObjectUtil.isEmpty(param.getFiscalPeriod())) {
                return ResponseData.success("仓储费下推站内手动分摊异常:会计区间为空");
            }
            //状态为0待分摊行,2分摊主行,3被分摊行,1已确定
            List<StationManualAllocation> toAlloclist = new LambdaQueryChainWrapper<>(stationManualAllocationMapper)
                    .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), StationManualAllocation::getFiscalPeriod, param.getFiscalPeriod())
                    .eq(ObjectUtil.isNotEmpty(param.getShopName()), StationManualAllocation::getShopName, param.getShopName())
                    .in(ObjectUtil.isNotEmpty(param.getSites()), StationManualAllocation::getSite, param.getSites())
                    .isNull(StationManualAllocation::getAllocId)
                    .ne(StationManualAllocation::getConfirmStatus, "1")
                    .list();
            if (ObjectUtil.isEmpty(toAlloclist)) {
                return ResponseData.success("仓储费下推站内手动分摊异常:无待分摊行或已确定");
            }
            //指定分摊行状态,主行状态指定为2,被分摊行指定为3
            if (toAlloclist.stream().anyMatch(i-> ObjectUtil.isEmpty(i.getConfirmStatus())||i.getConfirmStatus().compareTo(BigDecimal.ZERO)==0)) {
                this.assignAllocLineStatus(param);
            }

            //源报告(月仓储+长期仓储)是否有可下推值
            if (this.toInsertdetailSum(param) == 0) {
                return ResponseData.success("仓储费下推站内手动分摊异常:月仓储/长期仓储无可下推源报告");
            }

            //源报告(月仓储+长期仓储)是否有未确认
            Integer i1 = this.noTicked(param);
            if (i1 > 0) {
                log.info(StrUtil.format("仓储费下推站内手动分摊异常:{}月仓储/长期仓储存在{}条未确认数据",param.getFiscalPeriod()+param.getShopName()+param.getSites(),i1));
                return ResponseData.error(StrUtil.format("仓储费下推站内手动分摊异常:{}月仓储/长期仓储存在{}条未确认数据",param.getFiscalPeriod()+param.getShopName()+param.getSites(),i1));
            }

            //填充主分摊行状态2:仓储费合计,广告费合计,仓储费原值,分摊比率
            //被分摊行状态3:归零仓储费合计,广告费合计
            this.fillAllocLineVales(param);

            // 待分摊主行
            List<StationManualAllocation> allocMainList = new LambdaQueryChainWrapper<>(stationManualAllocationMapper)
                    .eq(ObjectUtil.isNotEmpty(param.getFiscalPeriod()), StationManualAllocation::getFiscalPeriod, param.getFiscalPeriod())
                    .eq(ObjectUtil.isNotEmpty(param.getShopName()), StationManualAllocation::getShopName, param.getShopName())
                    .in(ObjectUtil.isNotEmpty(param.getSites()), StationManualAllocation::getSite, param.getSites())
                    .isNull(StationManualAllocation::getAllocId)
                    .eq(StationManualAllocation::getConfirmStatus, "2")
                    .list();

            //分摊明细
            List<StationManualAllocation> skuStorageDetail = this.baseMapper.getSkuStorageDetail(param);

            Map<String, List<StationManualAllocation>> siteSkuStorageMap = skuStorageDetail.stream().collect(Collectors.groupingBy(StationManualAllocation::getSiteDimension));

            //获取汇率
            List<String> distinctCurrencies = skuStorageDetail.stream()
                    .map(StationManualAllocation::getAccountingCurrency)
                    .distinct().filter(i->!"USD".equals(i))
                    .collect(Collectors.toList());

            //汇率字典
            Map<String, BigDecimal> currencyDirectRateMap = new HashMap<>();
            currencyDirectRateMap.put("USD",BigDecimal.ONE);
            for (String distinctCurrency : distinctCurrencies) {
                SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
                rateParam.setOriginalCurrency(distinctCurrency);
                //获取当月汇率对应月仓储费汇率
                rateParam.setEffectDate(DateUtil.parse(param.getFiscalPeriod(),"yyyy-MM"));
                SpotExchangeRate rateByDateCurrency = spotExchangeRateService.getRateByDateCurrency(rateParam);
                if (ObjectUtil.isNotEmpty(rateByDateCurrency) && ObjectUtil.isNotEmpty(rateByDateCurrency.getDirectRate())) {
                    currencyDirectRateMap.put(distinctCurrency,rateByDateCurrency.getDirectRate());
                }
                //获取次月对应长期仓储费汇率
                rateParam.setEffectDate(DateUtil.offsetMonth(DateUtil.parse(param.getFiscalPeriod(),"yyyy-MM"),1));
                SpotExchangeRate rateByDateNextMonCurrency = spotExchangeRateService.getRateByDateCurrency(rateParam);
                if (ObjectUtil.isNotEmpty(rateByDateNextMonCurrency) && ObjectUtil.isNotEmpty(rateByDateCurrency.getDirectRate())) {
                    currencyDirectRateMap.put(distinctCurrency+"next",rateByDateNextMonCurrency.getDirectRate());
                }
            }


            String name;
            if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
                name = LoginContext.me().getLoginUser().getName();
            } else {
                name = "系统生成";
            }

            //插入明细
            List<StationManualAllocation> skuList = new ArrayList<>();
            List<StationManualAllocation> allocList = new ArrayList<>();


            for (StationManualAllocation mainAlloc : allocMainList) {
                String dimension = mainAlloc.getFiscalPeriod()+mainAlloc.getShopName()+mainAlloc.getSite();
                BigDecimal allocId= mainAlloc.getId();
                String reportType = mainAlloc.getReportType();
                String incomeType = mainAlloc.getIncomeType();
//                BigDecimal preAllocRate = ObjectUtil.isNotEmpty(mainAlloc.getStorageFeeAllocRate())?mainAlloc.getStorageFeeAllocRate():BigDecimal.ZERO;
//                BigDecimal storageFeeOri = mainAlloc.getStorageFeeOri();
                DateTime creatAt = DateUtil.date();

                List<StationManualAllocation> details = siteSkuStorageMap.get(dimension);
                if (ObjectUtil.isNotEmpty(details)) {
                    String accountingCurrency = details.get(0).getAccountingCurrency();
                    BigDecimal directRate = ObjectUtil.isNotEmpty(currencyDirectRateMap.get(accountingCurrency))?currencyDirectRateMap.get(accountingCurrency):BigDecimal.ONE;
                    BigDecimal nextDirectRate = ObjectUtil.isNotEmpty(currencyDirectRateMap.get(accountingCurrency+"next"))?currencyDirectRateMap.get(accountingCurrency+"next"):BigDecimal.ONE;
                    log.info("直接汇率" + directRate);
                    log.info("下月直接汇率" + nextDirectRate);
                    BigDecimal monFee = details.stream().filter(i -> ObjectUtil.isNotEmpty(i.getMonFee())).map(StationManualAllocation::getMonFee).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    BigDecimal longFee = details.stream().filter(i -> ObjectUtil.isNotEmpty(i.getLongFee())).map(StationManualAllocation::getLongFee).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    BigDecimal monFeeUsd = monFee.multiply(directRate);
                    BigDecimal longFeeUsd = longFee.multiply(nextDirectRate);
                    mainAlloc.setStorageFeeOri(monFeeUsd.add(longFeeUsd).abs().negate());//设置美金原值
                    BigDecimal allocStorageFee = ObjectUtil.isNotEmpty(mainAlloc.getStorageFee())?mainAlloc.getStorageFee().abs().negate():BigDecimal.ZERO;
                    BigDecimal allocRate = allocStorageFee.divide(mainAlloc.getStorageFeeOri(), 12, RoundingMode.HALF_UP);
                    mainAlloc.setStorageFeeAllocRate(allocRate);
                    //一个分摊行只对应一条超库容费
                    if (new LambdaQueryChainWrapper<>(stationManualAllocationMapper)
                            .eq(StationManualAllocation::getAllocId,allocId)
                            .eq(StationManualAllocation::getSku, "超库容费")
                            .count() == 0) {
                        this.baseMapper.insertOverStorage(param,directRate);
                        StationManualAllocation overStorageEnt = new LambdaQueryChainWrapper<>(stationManualAllocationMapper)
                                .eq(StationManualAllocation::getAllocId, allocId)
                                .eq(StationManualAllocation::getSku, "超库容费")
                                .one();
                        mainAlloc.setStorageFeeOri(mainAlloc.getStorageFeeOri().add(overStorageEnt.getStorageFeeOri()));
                    }
                    allocList.add(mainAlloc);


                    details.forEach(i -> {
                        i.setAllocId(allocId);
                        i.setReportType(reportType);
                        i.setIncomeType(incomeType);
                        i.setAccountingCurrency("USD");
                        BigDecimal monFeeOri = ObjectUtil.isNotEmpty(i.getMonFee())?i.getMonFee().negate():BigDecimal.ZERO;
                        BigDecimal longFeeOri = ObjectUtil.isNotEmpty(i.getLongFee())?i.getLongFee().negate():BigDecimal.ZERO;
                        BigDecimal add = monFeeOri.multiply(directRate).add(longFeeOri.multiply(nextDirectRate));
                        i.setStorageFee(add.multiply(allocRate));
                        i.setStorageFeeAllocRate(allocRate);
                        i.setStorageFeeOri(add);
                        i.setCreateAt(creatAt);
                        i.setCreateBy(name);
                    });
                    BigDecimal detailSum = details.stream().map(StationManualAllocation::getStorageFee).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).abs().negate();
                    log.info("--------------------START--------------------------");
                    log.info(detailSum.toString());
                    log.info("------------------------SPLIT----------------------");
                    log.info(allocStorageFee.toString());
                    log.info("-----------------------END-----------------------");
                    if (detailSum.compareTo(allocStorageFee)>0){
                        details.get(0).setStorageFee(details.get(0).getStorageFee().add(detailSum.subtract(allocStorageFee)));
                    } else if(detailSum.compareTo(allocStorageFee)<0){
                        details.get(0).setStorageFee(details.get(0).getStorageFee().add(allocStorageFee.subtract(detailSum)));
                    }

                    skuList.addAll(details);
                }
            }
            stationManualAllocationService.saveBatch(skuList);
            //费用合并到sku/物料/运营大类维度
            List<BigDecimal> allocIdList= allocMainList.stream().filter(i->ObjectUtil.isNotEmpty(i.getAllocId())).map(StationManualAllocation::getAllocId).distinct().collect(Collectors.toList());

            stationManualAllocationService.updateBatchById(allocList);
            stationManualAllocationService.fillListing(param);
            stationManualAllocationService.fillSalesBrand(param);

            //更新源报告已下推
            this.updateStorageSrc(param);
            return ResponseData.success();
        } catch (Exception e) {
            log.error("推仓储到站内手动分摊异常:" + e.getMessage());
            throw new RuntimeException("推仓储到站内手动分摊异常:" + e.getMessage());
        }
    }




    @DataSource(name = "finance")
    @Override
    public void updateStorageSrc(StationManualAllocationParam param) {
         this.baseMapper.updateStorageSrc(param);
    }


    private QueryWrapper<TotalStorageFee> queryWrapper(TotalStorageFeeParam param) {
        QueryWrapper<TotalStorageFee> queryWrapper=new QueryWrapper<>();
        if (param.getSysShopsNames() !=null && param.getSysShopsNames().size()>0){
            queryWrapper.in("sys_shops_name",param.getSysShopsNames());
        }
        if (param.getSysSites()!=null && param.getSysSites().size()>0){
            queryWrapper.in("sys_site",param.getSysSites());
        }
        if (param.getDifference()!=null && param.getDifference().equals(1)){
            queryWrapper.notIn("D_TOTAL_Fee",0);
        }
        if (param.getDifference()!=null && param.getDifference().equals(0)){
            queryWrapper.eq("D_TOTAL_Fee",0);
        }
        if (StringUtils.isNotEmpty(param.getStartDur()) && StringUtils.isNotEmpty(param.getEndDur())){
            Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
            Date endDate= DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"),1);
            queryWrapper.lambda().ge(TotalStorageFee::getDuration,startDate)
                    .lt(TotalStorageFee::getDuration,endDate);
        }
        return queryWrapper;
    }
}
