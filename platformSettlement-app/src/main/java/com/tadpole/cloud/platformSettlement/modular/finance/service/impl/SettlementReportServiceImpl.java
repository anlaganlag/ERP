package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.NoAllocationAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.entity.NoStationAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationAutoAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationManualAllocation;

import com.tadpole.cloud.platformSettlement.api.finance.model.params.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.*;
import com.tadpole.cloud.platformSettlement.modular.finance.service.*;
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

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class SettlementReportServiceImpl extends ServiceImpl<SettlementRepoertMapper, SettlementReport> implements ISettlementReportService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    SettlementRepoertMapper settlementRepoertMapper;

    @Resource
    SettlementDetailUsdMapper settlementDetailUsdMapper;

    @Resource
    StationManualAllocationMapper stationManualAllocationMapper;
    @Resource
    ISettlementDetailUsdService settlementDetailUsdService;


    @Resource
    IStationAutoAllocationService stationAutoAllocationService;

    @Resource
    StationAutoAllocationMapper stationAutoAllocationMapper;

    @Resource
    IStationManualAllocationService stationManualAllocationService;

    @Resource
    IManualAllocationAdjustService manualAllocationAdjustService;

    @Resource
    INoStationAllocationService noStationAllocationService;


    @Resource
    NoStationAllocationMapper noStationAllocationMapper;
    @Resource
    INoAllocationAdjustService noAllocationAdjustService;

    @Resource
    ISubsidySummaryService subsidySummaryService;

    @Resource
    SubsidySummaryMapper subsidySummaryMapper;

    @Resource
    IOutStationAllocationService outStationAllocationService;


    @Resource
    NoAllocationAdjustMapper noAllocationAdjustMapper;

    @Resource
    IStationManualAllocationService IStationManualAllocationService;




    /**
     * 批量确认结算报告标识
     */
    @Value("${rediskey.confirmSettlement}")
    public String confirmSettlement;

    @DataSource(name = "finance")
    @Override
    public PageResult<SettlementReportResult> findPageBySpec(SettlementReportParam param) {
        Page pageContext = param.getPageContext();

        IPage<SettlementReportResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public void confirm(SettlementReportParam param) {

        QueryWrapper<SettlementReport> qs=new QueryWrapper<>();
        qs.eq("id",param.getId());
        SettlementReport check=this.baseMapper.selectOne(qs);
        if(check.getConfirmStatus().equals(BigDecimal.ZERO)){
            SettlementReport ss=new SettlementReport();

            ss.setId(param.getId());
            ss.setConfirmStatus(new BigDecimal(1));
            ss.setConfirmBy(LoginContext.me().getLoginUser().getName());
            ss.setConfirmDate(new Date());

            this.baseMapper.updateById(ss);

        }

    }

    @DataSource(name = "finance")
    @Override
    public ResponseData confirmBatch(SettlementReportParam param) {

        //redis操作需绑定key
        BoundValueOperations toList = redisTemplate.boundValueOps(this.confirmSettlement);

        try{
            //从非空则为正在批量确认中
            if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
                return ResponseData.error("正在批量确认中!");
            }
            //设定正在批量确认
            toList.set("正在批量确认中!");

            QueryWrapper<SettlementReport> queryWrapper=new QueryWrapper<>();
            queryWrapper
                    .eq("SHOP_NAME", param.getShopName())
                    .eq("FISCAL_PERIOD", param.getFiscalPeriod())
                    .eq("CONFIRM_STATUS",0)
                    .in(CollectionUtils.isNotEmpty(param.getSites()),"SITE",param.getSites());
            List<SettlementReport> ss=this.list(queryWrapper);
            queryWrapper.clear();

            if (CollUtil.isEmpty(ss)) {
                return ResponseData.success("无可确认的数据!");
            }

            //批量保存
            for(SettlementReport pa:ss){
                param.setId(pa.getId());
                this.confirm(param);
            }

            return ResponseData.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error("生成失败!:"+e);
        } finally{
            toList.set("");
        }
        //for (SettlementReportParam param : params) {
        //    this.confirm(param);
        //}
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData refreshAmount(SettlementReportParam param) {
        //kindle广告费
        this.baseMapper.updateKindleFee(param);
        //海外税费
        this.baseMapper.updateOverSeasFee(param);
        //回款
        this.baseMapper.updateCollecTionFee(param);
        //Profit
        this.baseMapper.updateProfitFee(param);
        //刷退货数量
        this.baseMapper.updateReturnAmount();
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData refreshReturnAmount() {
        this.baseMapper.updateReturnAmount();
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementReportResult> export(SettlementReportParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<SettlementReportResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "finance")
    @Override
    public SettlementReportResult getQuantity(SettlementReportParam param) {
        return this.baseMapper.getQuantity(param);
    }


    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public ResponseData shopSiteToReport(SettlementReportParam param) throws Exception {
        try {
            //检查参数
            String fiscalPeriod = param.getFiscalPeriod();
            String shopName = param.getShopName();
            List<String> shopNames = ObjectUtil.isEmpty(param.getShopNames())? new ArrayList<>():param.getShopNames();
            if (ObjectUtil.isNotEmpty(param.getShopName())) {
                shopNames.add(shopName);
            }
            if (ObjectUtil.isEmpty(fiscalPeriod)  && ObjectUtil.isEmpty(shopNames)) {
                return ResponseData.error("会计区间和店铺不能为空");
            }
            List sites = param.getSites();
            String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser())?LoginContext.me().getLoginUser().getName():"重新生成报告";
            DateTime date = DateUtil.date();

            List<SettlementReport> reportList = new LambdaQueryChainWrapper<>(settlementRepoertMapper)
                    .eq(ObjectUtil.isNotEmpty(fiscalPeriod), SettlementReport::getFiscalPeriod, fiscalPeriod)
                    .in(ObjectUtil.isNotEmpty(shopNames), SettlementReport::getShopName, shopNames)
                    .in(ObjectUtil.isNotEmpty(sites), SettlementReport::getSite, sites).list();
            //存在1即已确认,移除未确认的报告
            if (ObjectUtil.isNotEmpty(reportList)) {
                if (reportList.stream().anyMatch(i -> ObjectUtil.isNotEmpty(i.getConfirmStatus()) && BigDecimal.ONE.compareTo(i.getConfirmStatus()) == 0)) {
                    log.error(operator + " 存在已确认的报表,请先确认后重新生成");
                    return ResponseData.error("结算报告已确认");
                } else { new LambdaUpdateChainWrapper<>(settlementRepoertMapper)
                            .eq(ObjectUtil.isNotEmpty(fiscalPeriod), SettlementReport::getFiscalPeriod, fiscalPeriod)
                            .in(ObjectUtil.isNotEmpty(shopNames), SettlementReport::getShopName, shopNames)
                            .in(ObjectUtil.isNotEmpty(sites), SettlementReport::getSite, sites)
                            .eq(SettlementReport::getConfirmStatus, 0)
                            .remove();
                        }
            }
            //分摊表恢复未确认状态
            new LambdaUpdateChainWrapper<>(stationAutoAllocationMapper)
                    .eq(ObjectUtil.isNotEmpty(fiscalPeriod), StationAutoAllocation::getFiscalPeriod, fiscalPeriod)
                    .in(ObjectUtil.isNotEmpty(shopNames), StationAutoAllocation::getShopName, shopNames)
                    .in(ObjectUtil.isNotEmpty(sites), StationAutoAllocation::getSite, sites)
                    .set(StationAutoAllocation::getISManual,0)
                    .set(StationAutoAllocation::getConfirmBy,operator)
                    .set(StationAutoAllocation::getConfirmDate,date)
                    .set(StationAutoAllocation::getConfirmStatus, 0).update();

            new LambdaUpdateChainWrapper<>(stationManualAllocationMapper)
                    .eq(ObjectUtil.isNotEmpty(fiscalPeriod), StationManualAllocation::getFiscalPeriod, fiscalPeriod)
                    .in(ObjectUtil.isNotEmpty(shopNames), StationManualAllocation::getShopName, shopNames)
                    .in(ObjectUtil.isNotEmpty(sites), StationManualAllocation::getSite, sites)
                    .set(StationManualAllocation::getConfirmStatus, 0)
                    .set(StationManualAllocation::getConfirmBy,operator)
                    .set(StationManualAllocation::getConfirmDate,date).update();


            new LambdaUpdateChainWrapper<>(noStationAllocationMapper)
                    .eq(ObjectUtil.isNotEmpty(fiscalPeriod), NoStationAllocation::getFiscalPeriod, fiscalPeriod)
                    .in(ObjectUtil.isNotEmpty(shopNames), NoStationAllocation::getShopName, shopNames)
                    .in(ObjectUtil.isNotEmpty(sites), NoStationAllocation::getSite, sites)
                    .set(NoStationAllocation::getConfirmStatus, 0)
                    .set(NoStationAllocation::getConfirmBy,operator)
                    .set(NoStationAllocation::getConfirmDate,date).update();

            new LambdaUpdateChainWrapper<>(noAllocationAdjustMapper)
                    .eq(ObjectUtil.isNotEmpty(fiscalPeriod), NoAllocationAdjust::getFiscalPeriod, fiscalPeriod)
                    .in(ObjectUtil.isNotEmpty(shopNames), NoAllocationAdjust::getShopName, shopNames)
                    .in(ObjectUtil.isNotEmpty(sites), NoAllocationAdjust::getSite, sites)
                    .set(NoAllocationAdjust::getConfirmStatus, 0)
                    .set(NoAllocationAdjust::getConfirmBy,operator)
                    .set(NoAllocationAdjust::getConfirmDate,date).update();



            //重新生成结算报告
            log.info("会计区间[{}]、账号{}、站点{}:开始重新生成结算报告", fiscalPeriod,shopNames, sites);

            //1 站内费用自动分摊重新确认
            for (String shop : shopNames) {
                StationAutoAllocationParam stationAutoAllocationParam = StationAutoAllocationParam.builder().fiscalPeriod(fiscalPeriod)
                        .shopName(shop).sites(sites).confirmBy(operator).build();
                ResponseData stationAutoResponse = stationAutoAllocationService.confirmBatch(stationAutoAllocationParam);
                if (stationAutoResponse.getCode() == 500) {
                    log.error("站内费用自动分摊确认异常:" + stationAutoResponse.getData());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("站内费用自动分摊确认异常:" + stationAutoResponse.getData());
                } else {
                    log.info("-------------------------11111------------------------------站内费用自动分摊确认成功 : 会计区间[{}]、账号[{}]、站点{} \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites,stationAutoResponse.getMessage()+stationAutoResponse.getData());
                }
            }

            //2 重新拉未确认仓储费-站内费用手动分摊表 及 站内费用手工分摊表确认
            for (String shop : shopNames) {
                StationManualAllocationParam stationManualAllocationParam = StationManualAllocationParam.builder().fiscalPeriod(fiscalPeriod)
                        .shopName(shop).sites(sites).build();
                ResponseData pullStorageDisposeFeeResponse = stationManualAllocationService.pullStorageDisposeFee(stationManualAllocationParam);
                if (pullStorageDisposeFeeResponse.getCode() == 500) {
                    log.error("拉取仓储销毁移除费异常:" + pullStorageDisposeFeeResponse.getData());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("拉取仓储销毁移除费异常:" + pullStorageDisposeFeeResponse.getData());
                } else {
                    log.info("-------------------------22222------------------------------拉取仓储销毁移除费成功 : 会计区间[{}]、账号[{}]、站点{} \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites,pullStorageDisposeFeeResponse.getMessage() + pullStorageDisposeFeeResponse.getData());
                }
                ResponseData stationManResponse = stationManualAllocationService.confirmBatch(stationManualAllocationParam);
                if (stationManResponse.getCode() == 500) {
                    log.error("站内费用手动分摊异常:" + stationManResponse.getData());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("站内费用手动分摊异常:" + stationManResponse.getData());
                } else{
                    log.info("-------------------------3333333------------------------------站内费用手动分摊成功 : 会计区间[{}]、账号[{}]、站点{} \n 接口返回信息:[{}]:[{}]", fiscalPeriod,shop, sites,stationManResponse.getData());
                }
            }

            //4 站内无需分摊:删除+批量刷成本+站内无需分摊费用确认
            for (String shop : shopNames) {
                noStationAllocationService.refreshCost();
                NoStationAllocationParam noStationAllocationParam = NoStationAllocationParam.builder().fiscalPeriod(fiscalPeriod)
                        .shopName(shop).sites(sites).build();
                ResponseData noStationResponse = noStationAllocationService.confirmBatch(noStationAllocationParam);
                if (noStationResponse.getCode() == 500) {
                    log.error("站内无需分摊异常:" + noStationResponse.getMessage());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("站内无需分摊异常:" + noStationResponse.getMessage());
                } else {
                    log.info("-------------------------4444444444------------------------------站内无需分摊生成成功 : 会计区间[{}]、账号[{}]、站点{} \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites,noStationResponse.getData() , noStationResponse.getMessage());
                }
            }

            //5 无需分摊调整 : 删除 + 批量刷成本 + 无需分摊调整表确认
            noAllocationAdjustService.refreshCost();
            for (String shop : shopNames) {
                NoAllocationAdjustParam noAllocationAdjustParam = NoAllocationAdjustParam.builder().fiscalPeriod(fiscalPeriod)
                        .shopName(shop).sites(sites).build();
                ResponseData noAdjustResponseData = noAllocationAdjustService.confirmBatch(noAllocationAdjustParam);

                if (noAdjustResponseData.getCode() == 500) {
                    log.error("无需分摊调整表异常:" + noAdjustResponseData.getMessage());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("无需分摊调整表异常:" + noAdjustResponseData.getMessage());
                } else {
                    log.info("-------------------------555555------------------------------无需分摊调整表生成成功 : 会计区间[{}]、账号[{}]、站点{}  \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites ,noAdjustResponseData.getData() , noAdjustResponseData.getMessage());
                }
            }
            return ResponseData.success();
        } catch (Exception e) {
            log.error("店铺站点维度重新生成结算报告异常: "+e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("店铺站点维度重新生成结算报告异常: "+e.getMessage());
        }

    }



    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public ResponseData shopSiteToReport1(SettlementReportParam param) throws Exception {
        String fiscalPeriod = param.getFiscalPeriod();
        String shopName = param.getShopName();
        List<String> shopNames = ObjectUtil.isEmpty(param.getShopNames())? new ArrayList<>():param.getShopNames();
        if (ObjectUtil.isNotEmpty(param.getShopName())) {
            shopNames.add(shopName);
        }
        if (ObjectUtil.isEmpty(fiscalPeriod)  && ObjectUtil.isEmpty(shopNames)) {
            return ResponseData.error("会计区间和店铺不能为空");
        }
        List sites = param.getSites();


        List<SettlementReport> reportList = new LambdaQueryChainWrapper<>(settlementRepoertMapper)
                .eq(ObjectUtil.isNotEmpty(fiscalPeriod), SettlementReport::getFiscalPeriod, fiscalPeriod)
                .in(ObjectUtil.isNotEmpty(shopNames), SettlementReport::getShopName, shopNames)
                .in(ObjectUtil.isNotEmpty(sites), SettlementReport::getSite, sites).list();
        //存在1即已确认,移除未确认的报告
        if (ObjectUtil.isNotEmpty(reportList)) {
            if (reportList.stream().anyMatch(i -> ObjectUtil.isNotEmpty(i.getConfirmStatus()) && BigDecimal.ONE.compareTo(i.getConfirmStatus()) == 0)) {
                return ResponseData.error("结算报告已确认");
            } else { new LambdaUpdateChainWrapper<>(settlementRepoertMapper)
                    .eq(ObjectUtil.isNotEmpty(fiscalPeriod), SettlementReport::getFiscalPeriod, fiscalPeriod)
                    .in(ObjectUtil.isNotEmpty(shopNames), SettlementReport::getShopName, shopNames)
                    .in(ObjectUtil.isNotEmpty(sites), SettlementReport::getSite, sites)
                    .eq(SettlementReport::getConfirmStatus, 0)
                    .remove();
            }
        }


        //重新生成结算报告
        log.info("会计区间[{}]、账号{}、站点{}:开始生成结算报告", fiscalPeriod,shopNames, sites);

        //先删除美金确认的下游分摊表
        //站内自动分摊
        new LambdaUpdateChainWrapper<>(stationAutoAllocationMapper)
                .eq(ObjectUtil.isNotEmpty(fiscalPeriod), StationAutoAllocation::getFiscalPeriod, fiscalPeriod)
                .in(ObjectUtil.isNotEmpty(shopNames), StationAutoAllocation::getShopName, shopNames)
                .in(ObjectUtil.isNotEmpty(sites), StationAutoAllocation::getSite, sites)
                .isNotNull(StationAutoAllocation::getAllocId)
                .eq(StationAutoAllocation::getConfirmStatus, 0)
                .remove();
        //站内手动分摊
        new LambdaUpdateChainWrapper<>(stationManualAllocationMapper)
                .eq(ObjectUtil.isNotEmpty(fiscalPeriod), StationManualAllocation::getFiscalPeriod, fiscalPeriod)
                .in(ObjectUtil.isNotEmpty(shopNames), StationManualAllocation::getShopName, shopNames)
                .in(ObjectUtil.isNotEmpty(sites), StationManualAllocation::getSite, sites)
                //明细数据
                .isNotNull(StationManualAllocation::getAllocId)
                //删除广告为0非广告导入数据:销毁移除和仓储费
                .and(i->i.eq(StationManualAllocation::getAdvertising,0).or()
                        .isNull(StationManualAllocation::getAdvertising))
                .eq(StationManualAllocation::getConfirmStatus, 0)
                .remove();
        //无需分摊站内
        new LambdaUpdateChainWrapper<>(noStationAllocationMapper)
                .eq(ObjectUtil.isNotEmpty(fiscalPeriod), NoStationAllocation::getFiscalPeriod, fiscalPeriod)
                .in(ObjectUtil.isNotEmpty(shopNames), NoStationAllocation::getShopName, shopNames)
                .in(ObjectUtil.isNotEmpty(sites), NoStationAllocation::getSite, sites)
                .eq(NoStationAllocation::getConfirmStatus, 0)
                .remove();
        //无需分摊调整
        new LambdaUpdateChainWrapper<>(noAllocationAdjustMapper)
                .eq(ObjectUtil.isNotEmpty(fiscalPeriod), NoAllocationAdjust::getFiscalPeriod, fiscalPeriod)
                .in(ObjectUtil.isNotEmpty(shopNames), NoAllocationAdjust::getShopName, shopNames)
                .in(ObjectUtil.isNotEmpty(sites), NoAllocationAdjust::getSite, sites)
                .eq(NoAllocationAdjust::getConfirmStatus, 0)
                .remove();

        //1.确认美金明细生成分摊
        settlementDetailUsdService.refreshExchangeRate();
        for (String shop : shopNames) {
            SettlementDetailUsdParam settlementDetailUsdParam = SettlementDetailUsdParam.builder().fiscalPeriod(fiscalPeriod)
                    .shopName(shop).sites(sites).build();
            ResponseData usdResponse = settlementDetailUsdService.confirmBatch(settlementDetailUsdParam);
            if (usdResponse.getCode() == 500) {
                return ResponseData.error("确认美金明细异常:" + usdResponse.getMessage());
            } else{
                log.info("确认美金成功 : 会计区间[{}]、账号[{}]、站点{}  \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites,usdResponse.getMessage()+usdResponse.getData());
            }
        }


        //1 站内费用自动分摊重新确认
        for (String shop : shopNames) {
            StationAutoAllocationParam stationAutoAllocationParam = StationAutoAllocationParam.builder().fiscalPeriod(fiscalPeriod)
                    .shopName(shop).sites(sites).build();
            ResponseData stationAutoResponse = stationAutoAllocationService.confirmBatch(stationAutoAllocationParam);
            if (stationAutoResponse.getCode() == 500) {
                log.error("站内费用自动分摊异常:" + stationAutoResponse.getData());
                return ResponseData.error("站内费用自动分摊异常:" + stationAutoResponse.getData());
            } else {
                log.info("站内费用自动分摊成功 : 会计区间[{}]、账号[{}]、站点{} \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites,stationAutoResponse.getMessage()+stationAutoResponse.getData());
            }
        }

        //2 重新拉未确认仓储费-站内费用手动分摊表 及 站内费用手工分摊表确认
        for (String shop : shopNames) {
            StationManualAllocationParam stationManualAllocationParam = StationManualAllocationParam.builder().fiscalPeriod(fiscalPeriod)
                    .shopName(shop).sites(sites).build();
            ResponseData stationManResponse = stationManualAllocationService.confirmBatch(stationManualAllocationParam);
            if (stationManResponse.getCode() == 500) {
                return ResponseData.error("站内费用手动分摊异常:" + stationManResponse.getData());
            } else{
                log.info("站内费用手动分摊成功 : 会计区间[{}]、账号[{}]、站点{} \n 接口返回信息:[{}]:[{}]", fiscalPeriod,shop, sites,stationManResponse.getData());
            }
        }

        //3.手动分摊调整批量确认,导入数据无需删除
        for (String shop : shopNames) {
            ManualAllocationAdjustParam manualAllocationAdjustParam = ManualAllocationAdjustParam.builder().fiscalPeriod(fiscalPeriod)
                    .shopName(shop).sites(sites).build();
            ResponseData manAdjustResponse = manualAllocationAdjustService.confirmBatch(manualAllocationAdjustParam);
            if (manAdjustResponse.getCode() == 500) {
                return ResponseData.error("手动分摊调整分摊异常:" + manAdjustResponse.getData());
            } else {
                log.info("手动分摊调整分摊生成成功 : 会计区间[{}]、账号[{}]、站点{} \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites, manAdjustResponse.getData() , manAdjustResponse.getMessage());
            }
        }



        //4 站内无需分摊:删除+批量刷成本+站内无需分摊费用确认
        for (String shop : shopNames) {
            noStationAllocationService.refreshCost();
            NoStationAllocationParam noStationAllocationParam = NoStationAllocationParam.builder().fiscalPeriod(fiscalPeriod)
                    .shopName(shop).sites(sites).build();
            ResponseData noStationResponse = noStationAllocationService.confirmBatch(noStationAllocationParam);
            if (noStationResponse.getCode() == 500) {
                return ResponseData.error("站内无需分摊异常:" + noStationResponse.getMessage());
            } else {
                log.info("站内无需分摊生成成功 : 会计区间[{}]、账号[{}]、站点{} \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites,noStationResponse.getData() , noStationResponse.getMessage());
            }
        }

        //5 无需分摊调整 : 删除 + 批量刷成本 + 无需分摊调整表确认

        noAllocationAdjustService.refreshCost();
        for (String shop : shopNames) {
            NoAllocationAdjustParam noAllocationAdjustParam = NoAllocationAdjustParam.builder().fiscalPeriod(fiscalPeriod)
                    .shopName(shop).sites(sites).build();
            ResponseData noAdjustResponseData = noAllocationAdjustService.confirmBatch(noAllocationAdjustParam);

            if (noAdjustResponseData.getCode() == 500) {
                return ResponseData.error("无需分摊调整表异常:" + noAdjustResponseData.getMessage());
            } else {
                log.info("无需分摊调整表生成成功 : 会计区间[{}]、账号[{}]、站点{}  \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites ,noAdjustResponseData.getData() , noAdjustResponseData.getMessage());
            }
        }
        // 6 确认汇总补贴总表
        for (String shop : shopNames) {
            SubsidySummaryParam subsidySummaryParam = SubsidySummaryParam.builder().fiscalPeriod(fiscalPeriod)
                    .shopName(shop).sites(sites).build();
            ResponseData subsidySummaryResponseData = subsidySummaryService.confirmBatch(subsidySummaryParam);
            if (subsidySummaryResponseData.getCode() == 500) {
                return ResponseData.error("补贴汇总表确认异常:" + subsidySummaryResponseData.getData() + subsidySummaryResponseData.getMessage());
            } else {
                log.info("补贴汇总表确认成功 : 会计区间[{}]、账号[{}]、站点{}  \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites ,subsidySummaryResponseData.getData() , subsidySummaryResponseData.getMessage());
            }
        }

        // 7 站外费用分摊汇总表
        for (String shop : shopNames) {
            OutStationAllocationParam outStationAllocationParam = OutStationAllocationParam.builder().fiscalPeriod(fiscalPeriod)
                    .shopName(shop).sites(sites).build();
            ResponseData outStationAllocationResponseData = outStationAllocationService.confirmBatch(outStationAllocationParam);
            if (outStationAllocationResponseData.getCode() == 500) {
                return ResponseData.error("站外费用分摊汇总表确认异常:" + outStationAllocationResponseData.getData() + outStationAllocationResponseData.getMessage());
            } else {
                log.info("站外费用分摊汇总表确认成功 : 会计区间[{}]、账号[{}]、站点{}  \n 接口返回信息:[{}]:[{}]", fiscalPeriod, shop, sites ,outStationAllocationResponseData.getData() , outStationAllocationResponseData.getMessage());
            }
        }





        return ResponseData.success();
    }



}
