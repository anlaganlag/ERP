package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalDestroyFeeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalDestroyFeeResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalDestroyFeeTotal;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.StationManualAllocationMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.TotalDestroyFeeMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.*;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
* <p>
* 销毁移除费用统计 服务实现类
* </p>
*
* @author S20190161
* @since 2022-10-18
*/
@Service
@Slf4j
public class TotalDestroyFeeServiceImpl extends ServiceImpl<TotalDestroyFeeMapper, TotalDestroyFee> implements ITotalDestroyFeeService {

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
    private TotalDestroyFeeTotal total =new TotalDestroyFeeTotal();

    @Resource
    IFixedExchangeRateService rateService;

    @Resource
    ITotalDestroyFeeService totalDestroyFeeService;


    @Resource
    ITotalStorageFeeService totalStorageFeeService;


    @Resource
    TotalDestroyFeeMapper totalDestroyFeeMapper;

    @Resource
    ISpotExchangeRateService spotExchangeRateService;


    @Resource
    StationManualAllocationMapper stationManualAllocationMapper;

    @Resource
    IStationManualAllocationService stationManualAllocationService;

    @DataSource(name = "finance")
    @Override
    public PageTotalResult<TotalDestroyFeeResult,TotalDestroyFeeTotal> findPageBySpec(TotalDestroyFeeParam param) {
        QueryWrapper<TotalDestroyFee> queryWrapper=queryWrapper(param);
        //查询分页
        IPage<TotalDestroyFeeResult> page =  this.baseMapper.selectPage(getPageContext(),queryWrapper);

        //第一页的时候才查询汇总
        if(getPageContext().getCurrent()==1){
            //查询汇总统计
            queryWrapper.select("sum( DISPOSAL_FEE) disposalFee , sum( RETURN_FEE ) returnFee ").eq("type",2);
            TotalDestroyFee Fees = this.baseMapper.selectOne(queryWrapper);
            if (Fees != null)
                BeanUtils.copyProperties(Fees,total);
        }
        return new PageTotalResult<>(page,total);
    }

    @DataSource(name = "finance")
    @Override
    public List<TotalDestroyFee> export(TotalDestroyFeeParam param) {
        return this.baseMapper.selectList(queryWrapper(param));
    }

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
    public void confirm(TotalDestroyFeeParam param){
        UpdateWrapper<TotalDestroyFee> wrapper=new UpdateWrapper<>();
        wrapper.eq("SETTLEMENT_ID",param.getSettlementId());
        TotalDestroyFee fee=new TotalDestroyFee();
        fee.setStatus(2);
        fee.setUpdateTime(DateUtil.date());
        fee.setUpdateUser(LoginContext.me().getLoginUser().getName());
        fee.setRemark("手动确认");
        this.baseMapper.update(fee,wrapper);

        QueryWrapper<TotalDestroyFee> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("max(id) id")
                .eq("SETTLEMENT_ID",param.getSettlementId())
                .groupBy("ORDER_ID")
                .having("count(*)=1");
        List<TotalDestroyFee> datarangeList=this.baseMapper.selectList(queryWrapper);
        datarangeList.stream().forEach(i->i.setStatus(0));
        this.updateBatchById(datarangeList);

    }
    @Transactional
    @DataSource(name = "finance")
    @Override
    public int add(TotalDestroyFeeParam param){
        BigDecimal DisposalFee = null;
        BigDecimal ReturnFee = null;
        QueryWrapper<TotalDestroyFee> wrapper=new QueryWrapper<>();
        wrapper
                .select("TYPE,sum(DISPOSAL_FEE) DISPOSALFEE,sum(RETURN_FEE) RETURNFEE")
                .eq("SETTLEMENT_ID",param.getSettlementId())
                .eq("ORDER_ID",param.getOrderId())
                .groupBy("TYPE","ORDER_ID");
        var list= this.baseMapper.selectList(wrapper);
        //获取OrderId第一条汇总行数据
        var t1=list.stream().filter(a->a.getType().equals(1)).findFirst().get();
        //有些订单只有汇总行没有明细数据的
        if (list.size()==2){
            var t2=list.stream().filter(a->a.getType().equals(2)).findFirst().get();
            DisposalFee=t2.getDisposalFee().add(param.getDisposalFee());
            ReturnFee=t2.getReturnFee().add(param.getReturnFee());

        }else{
            DisposalFee=param.getDisposalFee();
            ReturnFee=param.getReturnFee();
        }
        //判断分摊费用 汇总行 销毁费(负数) 小于明细行汇总+手工分摊费用
        if (t1.getDisposalFee().add(DisposalFee).compareTo(BigDecimal.valueOf(0))==1)
        {
            return -1;
        }
        //汇总行 销毁费 小于明细行汇总+手工分摊费用
        if (t1.getReturnFee().add(ReturnFee).compareTo(BigDecimal.valueOf(0)) == 1)
        {
            return -2;
        }

        param.setStatus(1);
        param.setType(2);
        param.setSource(2);
        TotalDestroyFee fee=new TotalDestroyFee();
        BeanUtils.copyProperties(param,fee);
        fee.setCreateUser(LoginContext.me().getLoginUser().getName());
        this.baseMapper.insert(fee);
        if (t1.getDisposalFee().add(DisposalFee).compareTo(BigDecimal.valueOf(0)) ==0
        && t1.getReturnFee().add(ReturnFee).compareTo(BigDecimal.valueOf(0))==0
        ){
            UpdateWrapper<TotalDestroyFee> uwrapper=new UpdateWrapper<>();
            uwrapper.eq("SETTLEMENT_ID",param.getSettlementId());
            uwrapper.eq("order_id",param.getOrderId());
            TotalDestroyFee ufee=new TotalDestroyFee();
            ufee.setStatus(2);
            ufee.setUpdateTime(DateUtil.date());
            ufee.setUpdateUser(LoginContext.me().getLoginUser().getName());
            ufee.setRemark("手动确认");
            this.baseMapper.update(ufee,uwrapper);
        }
        return 1;
    }

    @DataSource(name = "finance")
    @Override
    public TotalDestroyFeeResult getFifferenceFee(TotalDestroyFeeParam param) {
        return this.baseMapper.getFifferenceFee(param.getSettlementId(),param.getOrderId());
    }
    @DataSource(name = "finance")
    @Override
    public TotalDestroyFeeResult getProductBySku(TotalDestroyFeeParam param) {
        return this.baseMapper.getProductBySku(param);
    }

    @DataSource(name = "finance")
    @Override
    public void freshDisposeReturnFee(TotalDestroyFeeParam param) {
         this.baseMapper.freshDisposeReturnFee(param);
    }


    @DataSource(name = "finance")
    @Override
    public List<TotalDestroyFeeResult> emailList() {
        List<TotalDestroyFeeResult> list = this.baseMapper.emailList();
        return list;
    }

    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLatestDate(TotalDestroyFeeParam param) throws ParseException {

        List<TotalDestroyFeeResult> resultList = emailList();
        //刷listing最晚时间保存
        for(int i=0;i<resultList.size();i++) {
            UpdateWrapper<TotalDestroyFee> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("id",resultList.get(i).getId())
                    .set("UPDATE_TIME",DateUtil.parse(param.getUpdateTime(),"yyyy-MM-dd"))
                    .set("CREATE_TIME",new Date());

            this.update(updateWrapper);
        }
    }



    @DataSource(name = "finance")
    @Override
    public void deleteById(TotalDestroyFeeParam param){
        this.baseMapper.deleteById(param.getId());
    }
    @Transactional
    @DataSource(name = "finance")
    @Override
    public void updateById(TotalDestroyFeeParam param){
        deleteById(param);
        add(param);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData pushDestroyManualAllocSql(TotalDestroyFeeParam param) throws ParseException {
        try {
            //选取推送的数据
            if (ObjectUtil.isEmpty(param.getStartDur()) || ObjectUtil.isEmpty(param.getEndDur())) {
                return ResponseData.error("销毁移除生成异常: [起始和结束区间不能为空]");
            }
            //计算区间不一定是数据区间,可能下月结算
            String settlePeriod = ObjectUtil.isNotEmpty(param.getSettlePeriod())?param.getSettlePeriod():param.getStartDur();

            param.setDuration(DateUtil.parse(settlePeriod +"-01"));
            LambdaQueryWrapper<TotalDestroyFee> queryWrapper = new LambdaQueryWrapper<>();
            //状态为4已下推(1.未确认2.手动确认3.自动确认4.已下推)
            queryWrapper
                    .ge(ObjectUtil.isNotEmpty(param.getStartDur()),TotalDestroyFee::getDuration, DateUtil.parse(param.getStartDur() + "-01"))
                    .lt(ObjectUtil.isNotEmpty(param.getEndDur()),TotalDestroyFee::getDuration, DateUtil.offsetMonth(DateUtil.parse(param.getEndDur() + "-01"), 1))
                    .in(ObjectUtil.isNotEmpty(param.getSysShopsNames()),TotalDestroyFee::getSysShopsName, param.getSysShopsNames())
                    .eq(ObjectUtil.isNotEmpty(param.getSysShopsName()),TotalDestroyFee::getSysShopsName, param.getSysShopsName())
                    .eq(ObjectUtil.isNotEmpty(param.getOrderId()),TotalDestroyFee::getOrderId,param.getOrderId())
                    .in(ObjectUtil.isNotEmpty(param.getSysSites()),TotalDestroyFee::getSysSite, param.getSysSites())
                    .eq(ObjectUtil.isNotEmpty(param.getStatus()),TotalDestroyFee::getStatus, param.getStatus())
                    .eq(TotalDestroyFee::getType,2)
                    .in(TotalDestroyFee::getStatus, 2,3);

            if (ObjectUtil.isNotEmpty(param.getNormal() )) {
                if (param.getNormal().equals(0)) {
                    queryWrapper.isNull(TotalDestroyFee::getDepartment);
                } else if (param.getNormal().equals(1)) {
                    queryWrapper.isNotNull(TotalDestroyFee::getDepartment);
                }
            }

            List<TotalDestroyFee> destroyreturnList = this.baseMapper.selectList(queryWrapper);
            if (ObjectUtil.isEmpty(destroyreturnList)) {
                return ResponseData.success("无可下推数据");
            }

            //校验全部确认
            if (destroyreturnList.stream().anyMatch(i -> i.getStatus() == 1)) {
                return ResponseData.error("销毁移除生成异常: [部分下推订单状态未确认]");
            }


            //事业部不能为空
            if (destroyreturnList.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getDepartment()))) {
                return ResponseData.error("销毁移除存在事业部为空,请先刷listing");
            }
            StationManualAllocationParam stationManualAllocationParam = StationManualAllocationParam.builder().fiscalPeriod(settlePeriod).shopName(param.getSysShopsName()).shopNames(param.getSysShopsNames()).sites(param.getSysSites()).build();


            //刷销毁移除listings
            stationManualAllocationService.fnskuFillDestroyListing(stationManualAllocationParam);
            //更新指定分摊行
            List<StationManualAllocation> toAlloclist = new LambdaQueryChainWrapper<>(stationManualAllocationMapper)
                    .eq(ObjectUtil.isNotEmpty(settlePeriod), StationManualAllocation::getFiscalPeriod, settlePeriod)
                    .eq(ObjectUtil.isNotEmpty(param.getSysShopsName()), StationManualAllocation::getShopName, param.getSysShopsName())
                    .in(ObjectUtil.isNotEmpty(param.getSysShopsNames()), StationManualAllocation::getShopName, param.getSysShopsNames())
                    .in(ObjectUtil.isNotEmpty(param.getSysSite()), StationManualAllocation::getSite, param.getSysSite())
                    .in(ObjectUtil.isNotEmpty(param.getSysSites()), StationManualAllocation::getSite, param.getSysSites())
                    .isNull(StationManualAllocation::getAllocId)
                    .ne(StationManualAllocation::getConfirmStatus, "1")
                    .list();
            if (ObjectUtil.isEmpty(toAlloclist)) {
                log.info("仓储费下推站内手动分摊异常:无对应分摊行或分摊行已确定");
                return ResponseData.success("仓储费下推站内手动分摊异常:无对应分摊行或分摊行已确定");
            }
            //存在状态0即未指定分摊行,需要指定分摊主行和非分摊主行
            if (toAlloclist.stream().anyMatch(i -> i.getConfirmStatus().compareTo(BigDecimal.ZERO) == 0)) {
                totalStorageFeeService.assignAllocLineStatus(stationManualAllocationParam);
            }

            destroyreturnList.forEach(i->i.setDuration(DateUtil.parse(settlePeriod+"-01","yyyy-MM-dd")));
            Map<String, List<TotalDestroyFee>> detailSiteDimension =  destroyreturnList.stream().collect(Collectors.groupingBy(TotalDestroyFee::getSiteDimension));


            //获取汇率
            List<String> distinctCurrencies = destroyreturnList.stream()
                    .map(TotalDestroyFee::getCurrency)
                    .distinct().filter(i->!"USD".equals(i))
                    .collect(Collectors.toList());
            //汇率字典
            Map<String, BigDecimal> currencyDirectRateMap = new HashMap<>();
            currencyDirectRateMap.put("USD",BigDecimal.ONE);
            for (String distinctCurrency : distinctCurrencies) {
                SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
                rateParam.setOriginalCurrency(distinctCurrency);
                rateParam.setEffectDate(DateUtil.parse(param.getStartDur(),"yyyy-MM"));
                SpotExchangeRate rateByDateCurrency = spotExchangeRateService.getRateByDateCurrency(rateParam);
                if (ObjectUtil.isNotEmpty(rateByDateCurrency) && ObjectUtil.isNotEmpty(rateByDateCurrency.getDirectRate())) {
                    currencyDirectRateMap.put(distinctCurrency,rateByDateCurrency.getDirectRate());
                }
            }
            String name;
            if (ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())) {
                name = LoginContext.me().getLoginUser().getName();
            } else {
                name = "系统生成";
            }

            // 待分摊主行
            List<StationManualAllocation> allocMainList = new LambdaQueryChainWrapper<>(stationManualAllocationMapper)
                    .eq(ObjectUtil.isNotEmpty(settlePeriod), StationManualAllocation::getFiscalPeriod, settlePeriod)
                    .eq(ObjectUtil.isNotEmpty(param.getSysShopsName()), StationManualAllocation::getShopName, param.getSysShopsName())
                    .in(ObjectUtil.isNotEmpty(param.getSysShopsNames()), StationManualAllocation::getShopName, param.getSysShopsNames())
                    .in(ObjectUtil.isNotEmpty(param.getSysSites()), StationManualAllocation::getSite, param.getSysSites())
                    .isNull(StationManualAllocation::getAllocId)
                    .eq(StationManualAllocation::getConfirmStatus, "2")
                    .list();
            //插入明细
            List<StationManualAllocation> skuDetail = new ArrayList<>();
            List<StationManualAllocation> allocList = new ArrayList<>();
            for (StationManualAllocation mainAlloc : allocMainList) {
                String dimension = mainAlloc.getFiscalPeriod() + mainAlloc.getShopName() + mainAlloc.getSite();
                List<TotalDestroyFee> details = detailSiteDimension.get(dimension);
                if (ObjectUtil.isEmpty(details)) {
                    continue;
                }
                TotalDestroyFee detailEnt = details.get(0);
                BigDecimal directRate = ObjectUtil.isNotEmpty(currencyDirectRateMap.get(detailEnt.getCurrency()))?currencyDirectRateMap.get(detailEnt.getCurrency()):BigDecimal.ONE;

                //更新主分摊行的销毁移除费用
                BigDecimal disposalFee = details.stream().map(TotalDestroyFee::getDisposalFee).filter(ObjectUtil::isNotEmpty).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).negate();
                BigDecimal returnFee = details.stream().map(TotalDestroyFee::getReturnFee).filter(ObjectUtil::isNotEmpty).reduce( BigDecimal::add).orElse(BigDecimal.ZERO).negate();

                BigDecimal disposalFeeTotalUsd = disposalFee.multiply(directRate);
                BigDecimal returnFeeTotalUsd = returnFee.multiply(directRate);
                BigDecimal preMainAllocDisposeFee =  ObjectUtil.isEmpty(mainAlloc.getDisposeFee())?BigDecimal.ZERO:mainAlloc.getDisposeFee();
                BigDecimal preMainAllocRemovalDeal =  ObjectUtil.isEmpty(mainAlloc.getRemovalDeal())?BigDecimal.ZERO:mainAlloc.getRemovalDeal();
                mainAlloc.setDisposeFee(disposalFeeTotalUsd.add(preMainAllocDisposeFee));
                mainAlloc.setRemovalDeal(returnFeeTotalUsd.add(preMainAllocRemovalDeal));
                allocList.add(mainAlloc);
                BigDecimal mainAllocId = mainAlloc.getId();
                String reportType = mainAlloc.getReportType();
                String incomeType = mainAlloc.getIncomeType();
                DateTime date = DateUtil.date();

                details.stream().forEach(i -> {
                    BigDecimal disposalDetailFee = ObjectUtil.isNotEmpty(i.getDisposalFee())?i.getDisposalFee().negate():BigDecimal.ZERO;
                    BigDecimal returnDetailFee = ObjectUtil.isNotEmpty(i.getReturnFee())?i.getReturnFee().negate():BigDecimal.ZERO;
                    StationManualAllocation usd = StationManualAllocation.builder()
                             .allocId(mainAllocId)
                             .fiscalPeriod(DateUtil.format(param.getDuration(), "YYYY-MM"))
                             .shopName(i.getSysShopsName())
                             .site(i.getSysSite())
                             .reportType(reportType)
                             .incomeType(incomeType)
                             .accountingCurrency("USD")
                             .department(i.getDepartment())
                             .team(i.getTeam())
                             .sku(i.getSku())
                             .materialCode(i.getMaterialCode())
                             .productType(i.getProductType())
                             .disposeFee(disposalDetailFee.multiply(directRate))
                             .removalDeal(returnDetailFee.multiply(directRate))
                             .createAt(date)
                             .createBy(name)
                             .confirmStatus(BigDecimal.ZERO).build();
                    skuDetail.add(usd);});
            }
            stationManualAllocationService.saveBatch(skuDetail);
            stationManualAllocationService.updateBatchById(allocList);
            //已下推修改状态4  null 1 (未确认)  2 3(确认)   4(已下推,已生成)
            LambdaUpdateWrapper<TotalDestroyFee> uw = new LambdaUpdateWrapper<>();
            uw
                    .ge(ObjectUtil.isNotEmpty(param.getStartDur()),TotalDestroyFee::getDuration, DateUtil.parse(param.getStartDur() + "-01"))
                    .lt(ObjectUtil.isNotEmpty(param.getEndDur()),TotalDestroyFee::getDuration, DateUtil.offsetMonth(DateUtil.parse(param.getEndDur() + "-01"), 1))
                    .in(ObjectUtil.isNotEmpty(param.getSysShopsNames()),TotalDestroyFee::getSysShopsName, param.getSysShopsNames())
                    .eq(ObjectUtil.isNotEmpty(param.getSysShopsName()),TotalDestroyFee::getSysShopsName,param.getSysShopsName())
                    .eq(ObjectUtil.isNotEmpty(param.getOrderId()),TotalDestroyFee::getOrderId,param.getOrderId())
                    .in(ObjectUtil.isNotEmpty(param.getSysSites()),TotalDestroyFee::getSysSite, param.getSysSites())
                    .eq(ObjectUtil.isNotEmpty(param.getStatus()),TotalDestroyFee::getStatus, param.getStatus())
                    .in(TotalDestroyFee::getStatus, 2,3);
            ;
//                    .eq(TotalDestroyFee::getType,2);

            if (ObjectUtil.isNotEmpty(param.getNormal())) {
                if (param.getNormal().equals(0)) {
                    uw.isNull(TotalDestroyFee::getDepartment);
                } else if (param.getNormal().equals(1)) {
                    uw.isNotNull(TotalDestroyFee::getDepartment);
                }
            };
            uw.set(TotalDestroyFee::getStatus, "4")
              .set(TotalDestroyFee::getSettlePeriod,settlePeriod)
              .set(TotalDestroyFee::getUpdateTime, DateUtil.date());
            totalDestroyFeeMapper.update(null, uw);
            stationManualAllocationService.fillSalesBrand(stationManualAllocationParam);
            return ResponseData.success();
        } catch (Exception e){
            log.error(StrUtil.format("推销毁费用： 系统异常原因：[{}] ",e.getMessage()));
            return ResponseData.error("推销毁费用异常:"+e.getMessage());
        }
    }

    @Transactional
    @DataSource(name = "finance")
    @Override
    public ResponseData pushDestroyManualAlloc(TotalDestroyFeeParam param) throws ParseException {
        try {
            //选取推送的数据
            if (ObjectUtil.isEmpty(param.getStartDur()) || ObjectUtil.isEmpty(param.getStartDur())) {
                return ResponseData.error("销毁移除生成异常: [起始结束区间不能为空]");
            }
            if (ObjectUtil.isEmpty(param.getDuration())) {
                param.setDuration(DateUtil.parse(param.getStartDur()+"-01"));
            }
            LambdaQueryWrapper<TotalDestroyFee> queryWrapper = new LambdaQueryWrapper<>();
            //状态为4已下推(1.未确认2.手动确认3.自动确认4.已下推)
            queryWrapper
                    .ge(ObjectUtil.isNotEmpty(param.getStartDur()),TotalDestroyFee::getDuration, DateUtil.parse(param.getStartDur() + "-01"))
                    .lt(ObjectUtil.isNotEmpty(param.getStartDur()),TotalDestroyFee::getDuration, DateUtil.offsetMonth(DateUtil.parse(param.getEndDur() + "-01"), 1))
                    .in(ObjectUtil.isNotEmpty(param.getSysShopsNames()),TotalDestroyFee::getSysShopsName, param.getSysShopsNames())
                    .eq(ObjectUtil.isNotEmpty(param.getSysShopsName()),TotalDestroyFee::getSysShopsName, param.getSysShopsName())
                    .in(ObjectUtil.isNotEmpty(param.getSysSites()),TotalDestroyFee::getSysSite, param.getSysSites())
                    .ne(TotalDestroyFee::getStatus, 4);
            List<TotalDestroyFee> destroyreturnList = this.baseMapper.selectList(queryWrapper);
            //校验全部确认
            if (destroyreturnList.stream().anyMatch(i -> i.getStatus() == 1)) {
                return ResponseData.error("销毁移除生成异常: [部分下推订单状态未确认]");
            }

            if (ObjectUtil.isEmpty(destroyreturnList)) {
                return ResponseData.success("无可下推数据");
            }

            //更新指定分摊行
            stationManualAllocationService.updateAllocLineStatus();

            Map<String, List<TotalDestroyFee>> detailSiteDimension = destroyreturnList.stream().collect(Collectors.groupingBy(TotalDestroyFee::getSiteDimension));

            for (Map.Entry<String, List<TotalDestroyFee>> siteDimensionDetail : detailSiteDimension.entrySet()) {
                //获取当前维度下主分摊行
                QueryWrapper<StationManualAllocation> qw = new QueryWrapper<>();
                qw.eq("FISCAL_PERIOD||SHOP_NAME||SITE", siteDimensionDetail.getKey())
                        .in("CONFIRM_STATUS", "2")
                        .isNull("ALLOC_ID");
                List<StationManualAllocation> mainlist = stationManualAllocationService.list(qw);
                if (ObjectUtil.isEmpty(mainlist)) {
                    log.error("销毁移除生成异常:无主分摊行");
                    break;
                }
                if (mainlist.size() > 1) {
                    log.error("销毁移除生成异常:存在多条主分摊行");
                    break;
                }


                StationManualAllocation mainAllocLine;
                UpdateWrapper<StationManualAllocation> updateWrapper = new UpdateWrapper<>();
                mainAllocLine = mainlist.get(0);
                List<TotalDestroyFee> details = siteDimensionDetail.getValue();
                TotalDestroyFee detailEnt = details.get(0);

                //当前站点维度下的直接汇率
                SpotExchangeRateParam rateParam = new SpotExchangeRateParam();
                rateParam.setOriginalCurrency(detailEnt.getCurrency());
                rateParam.setEffectDate(param.getDuration());
                SpotExchangeRate rateByDateCurrency = spotExchangeRateService.getRateByDateCurrency(rateParam);
                if (rateByDateCurrency == null) {
                    log.error(StrUtil.format("无当期汇率{} {}", detailEnt.getCurrency(), param.getDuration().toString()));
                    continue;
                }
                BigDecimal directRate = rateByDateCurrency.getDirectRate();

                //更新主分摊行的销毁移除费用
                BigDecimal disposalFee = details.stream().map(TotalDestroyFee::getDisposalFee).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal returnFee = details.stream().map(TotalDestroyFee::getReturnFee).reduce(BigDecimal.ZERO, BigDecimal::add);
                mainAllocLine.setDisposeFee(disposalFee.multiply(directRate));
                mainAllocLine.setRemovalDeal(returnFee.multiply(directRate));
                updateWrapper.eq("FISCAL_PERIOD||SHOP_NAME||SITE", siteDimensionDetail.getKey())
                        .isNull("ALLOC_ID");
                stationManualAllocationService.update(mainAllocLine, updateWrapper);
                updateWrapper.clear();
                BigDecimal mainAllocId = mainAllocLine.getId();
                String reportType = mainAllocLine.getReportType();
                String incomeType = mainAllocLine.getIncomeType();

                List<StationManualAllocation> skuDetail = new ArrayList<>();


                for (TotalDestroyFee i : details) {

                    StationManualAllocation usd = StationManualAllocation.builder().allocId(mainAllocId).fiscalPeriod(DateUtil.format(param.getDuration(), "YYYY-MM"))
                            .reportType(reportType).incomeType(incomeType).shopName(i.getSysShopsName()).site(i.getSysSite()).accountingCurrency("USD").sku(i.getSku()).department(i.getDepartment())
                            .team(i.getTeam()).department(i.getDepartment()).productType(i.getProductType()).materialCode(i.getMaterialCode())
                            .disposeFee(i.getDisposalFee().multiply(directRate)).removalDeal(i.getReturnFee().multiply(directRate)).build();
                    skuDetail.add(usd);
                }
                stationManualAllocationService.saveBatch(skuDetail);


                //已下推修改状态4  null 1 (未确认)  2 3(确认)   4(已下推,已生成)
                LambdaUpdateWrapper<TotalDestroyFee> uw = new LambdaUpdateWrapper<>();
                uw
                        .ge(ObjectUtil.isNotEmpty(param.getStartDur()),TotalDestroyFee::getDuration, DateUtil.parse(param.getStartDur() + "-01"))
                        .lt(ObjectUtil.isNotEmpty(param.getEndDur()),TotalDestroyFee::getDuration, DateUtil.offsetMonth(DateUtil.parse(param.getEndDur() + "-01"), 1))
                        .in(ObjectUtil.isNotEmpty(param.getSysShopsNames()),TotalDestroyFee::getSysShopsName, param.getSysShopsNames())
                        .in(ObjectUtil.isNotEmpty(param.getSysSites()),TotalDestroyFee::getSysSite, param.getSysSites())
                        .ne(TotalDestroyFee::getStatus, "4")
                        .set(TotalDestroyFee::getStatus, "4");
                totalDestroyFeeMapper.update(null, uw);

            }




            //下推数据 sku维度


            return ResponseData.success();
        } catch (Exception e){
                log.error(StrUtil.format("推销毁费用： 系统异常原因：[{}] ",e.getMessage()));
                return ResponseData.error("推销毁费用异常:"+e.getMessage());
            }
        }



    private QueryWrapper<TotalDestroyFee> queryWrapper(TotalDestroyFeeParam param) {
        QueryWrapper<TotalDestroyFee> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("row_number() over (partition by SETTLEMENT_ID,ORDER_ID order by TYPE) lev",
                "case when TYPE=2 and  DEPARTMENT is null then '异常' else '正常' end normal",
                "decode(STATUS,1,'未确认',2,'手动确认',3,'自动确认') state",
                "decode(TYPE,1,'汇总行',2,'分摊行') dataType",
                "id, settlement_id, sys_shops_name, sys_site, order_id,fnsku, sku, currency, disposal_fee, return_fee, department, team, material_code, category, product_type, duration, status, source, type, create_time,settle_period"
        );
        if (param.getSysShopsNames() != null && param.getSysShopsNames().size() > 0) {
            queryWrapper.in("sys_shops_name", param.getSysShopsNames());
        }
        if (param.getSysSites() != null && param.getSysSites().size() > 0) {
            queryWrapper.in("sys_site", param.getSysSites());
        }
        if (StringUtils.isNotEmpty(param.getOrderId())) {
            queryWrapper.eq("ORDER_ID", param.getOrderId());
        }
        if (param.getStatus() != null) {
            queryWrapper.eq("status", param.getStatus());
        }

        if (ObjectUtil.isNotEmpty(param.getSettlePeriod())) {
            if ("空".equals(param.getSettlePeriod())) {
                queryWrapper.isNull(param.getSettlePeriod());
            } else {
                queryWrapper.eq("settle_period", param.getSettlePeriod());
            }
        }
        if (param.getNormal()!=null ){
            if (param.getNormal().equals(0))
            {
                queryWrapper.isNull("department");
                //只获取明细的异常
                queryWrapper.eq("type",2);
            }
            //SKU正常的 把汇总行显示出来
            if (param.getNormal().equals(1))
                queryWrapper.and(a->a.isNotNull("department").or().eq("type",1));

        }

        if (StringUtils.isNotEmpty(param.getStartDur()) && StringUtils.isNotEmpty(param.getEndDur())){
            Date startDate = DateUtil.parse(param.getStartDur(), "yyyy-MM");
            Date endDate= DateUtil.offsetMonth(DateUtil.parse(param.getEndDur(), "yyyy-MM"),1);
            queryWrapper.lambda().ge(TotalDestroyFee::getDuration,startDate)
                    .lt(TotalDestroyFee::getDuration,endDate);
        }
        return queryWrapper;
    }
}
