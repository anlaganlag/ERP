package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.AmazonVatTransactionsReport;
import com.tadpole.cloud.platformSettlement.modular.vat.enums.ReportCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.AmazonVatTransactionsReportMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.AmazonVatTransactionsReportParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.AmazonVatTransactionsReportResult;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseExchangeRateResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IAmazonVatTransactionsReportService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 源报告表 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Service
public class AmazonVatTransactionsReportServiceImpl extends ServiceImpl<AmazonVatTransactionsReportMapper, AmazonVatTransactionsReport> implements IAmazonVatTransactionsReportService {

    @DataSource(name = "finance")
    @Override
    public ResponseData queryListPage(AmazonVatTransactionsReportParam param) {
        List <String> exportOutsideEus = param.getExportOutsideEus();
        if (CollUtil.isNotEmpty(exportOutsideEus) && exportOutsideEus.contains("空")){
            param.setExportOutsideEu("空");
        }

        List <String> priceAbnormals = param.getPriceAbnormals();
        if (CollUtil.isNotEmpty(priceAbnormals) && priceAbnormals.contains("空")){
            param.setPriceAbnormal("空");
        }
        List <String> transactionCurrencyCodes = param.getTransactionCurrencyCodes();
        if (CollUtil.isNotEmpty(transactionCurrencyCodes) && transactionCurrencyCodes.contains("空")){
            param.setTransactionCurrencyCode("空");
        }

        List <String> taxCollectionResponsibilitys = param.getTaxCollectionResponsibilitys();
        if (CollUtil.isNotEmpty(taxCollectionResponsibilitys) && taxCollectionResponsibilitys.contains("空")){
            param.setTaxCollectionResponsibility("空");
        }

        Page pageContext = param.getPageContext();
        return ResponseData.success(this.baseMapper.queryListPage(pageContext,param));
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData querySum(AmazonVatTransactionsReportParam param) {
        List <String> exportOutsideEus = param.getExportOutsideEus();
        if (CollUtil.isNotEmpty(exportOutsideEus) && exportOutsideEus.contains("空")){
            param.setExportOutsideEu("空");
        }

        List <String> priceAbnormals = param.getPriceAbnormals();
        if (CollUtil.isNotEmpty(priceAbnormals) && priceAbnormals.contains("空")){
            param.setPriceAbnormal("空");
        }
        List <String> transactionCurrencyCodes = param.getTransactionCurrencyCodes();
        if (CollUtil.isNotEmpty(transactionCurrencyCodes) && transactionCurrencyCodes.contains("空")){
            param.setTransactionCurrencyCode("空");
        }

        List <String> taxCollectionResponsibilitys = param.getTaxCollectionResponsibilitys();
        if (CollUtil.isNotEmpty(taxCollectionResponsibilitys) && taxCollectionResponsibilitys.contains("空")){
            param.setTaxCollectionResponsibility("空");
        }
        return ResponseData.success(this.baseMapper.querySum(param));
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData verifyBatch(AmazonVatTransactionsReportParam param) {
        UpdateWrapper<AmazonVatTransactionsReport> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ACTIVITY_PERIOD",param.getActivityPeriod())
                .in(CollectionUtils.isNotEmpty(param.getSysShopsNames()),"SYS_SHOPS_NAME",param.getSysShopsNames())
                .eq("VERIFY_STATUS", ReportCheckStatus.getEnumValue("未审核").getCode())
                .set("VERIFY_STATUS", ReportCheckStatus.getEnumValue("已手动审核").getCode());

        this.baseMapper.update(null,updateWrapper);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData autoVerify(AmazonVatTransactionsReportParam param) {

        //上个月会计期间
        String lastMonth = getLastMonth();
        param.setActivityPeriod(lastMonth);
        //刷新sales total
        this.baseMapper.refreshSalesTotalFromDataRange(param);
        //比较差异
        this.baseMapper.refreshDiffrence(param);
        //数据异常的自动作废
        this.baseMapper.autoFei(param);
        //状态为未审核，售价异常为否的自动审核
        this.baseMapper.autoVerify(param);

        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData getEditData(AmazonVatTransactionsReportParam param) {
        return ResponseData.success(this.baseMapper.getEditData(param));
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(AmazonVatTransactionsReportParam param) {
        AmazonVatTransactionsReportResult res =this.baseMapper.getEditData(param);
        QueryWrapper<AmazonVatTransactionsReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ACTIVITY_PERIOD",param.getActivityPeriod())
                .eq("SYS_SHOPS_NAME",param.getSysShopsName())
                .eq("transaction_event_id",param.getTransactionEventId())
                .eq("SELLER_SKU",param.getSellerSku())
                .eq("VERIFY_STATUS",1)
                .isNotNull("TOTAL_ACTIVITY_VALUE_AMT_VAT_INCL")
                .orderByAsc("QTY");

        List<AmazonVatTransactionsReport> lists = this.baseMapper.selectList(queryWrapper);
        // 单售价
        BigDecimal priceOne = param.getTotalActivityValueAmtVatInclNew().divide(res.getQty());
        //调整后是否相等
        if(lists.size()>0 && param.getTotalActivityValueAmtVatInclNew().compareTo(res.getSalesTotal())==0){

          if(lists.size()==1){
              lists.get(0).setTotalActivityValueAmtVatInclNew(param.getTotalActivityValueAmtVatInclNew());
              if(param.getTotalActivityValueAmtVatInclNew().compareTo(res.getSalesTotal())==0){
                  lists.get(0).setPriceAbnormal("否");
                  lists.get(0).setPriceDifference(BigDecimal.ZERO);
              }else{
                  lists.get(0).setPriceAbnormal("是");
                  lists.get(0).setPriceDifference(res.getSalesTotal().subtract(param.getTotalActivityValueAmtVatInclNew()));
              }
          }else{
              BigDecimal sum = BigDecimal.ZERO;
              String normal = "";
              if(param.getTotalActivityValueAmtVatInclNew().compareTo(res.getSalesTotal())==0){
                  normal="否";
              }else{
                  normal="是";
              }
              for (int i = 0; i < lists.size()-1; i++) {
                  sum = sum.add(lists.get(i).getQty().multiply(priceOne).setScale(2,2));
                  lists.get(i).setTotalActivityValueAmtVatInclNew(lists.get(i).getQty().multiply(priceOne).setScale(2,2));
                  lists.get(i).setPriceAbnormal(normal);
                  lists.get(i).setPriceDifference(res.getSalesTotal().subtract(param.getTotalActivityValueAmtVatInclNew()));
              }
              lists.get(lists.size()-1).setTotalActivityValueAmtVatInclNew(param.getTotalActivityValueAmtVatInclNew().subtract(sum));
              lists.get(lists.size()-1).setPriceAbnormal(normal);
              lists.get(lists.size()-1).setPriceDifference(res.getSalesTotal().subtract(param.getTotalActivityValueAmtVatInclNew()));

          }
            this.updateBatchById(lists);
        }
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateSalesDetail(AmazonVatTransactionsReportParam param) {
        //汇率是否维护检查
        List<BaseExchangeRateResult> list = this.baseMapper.notExchangeRate(param);
        if(list.size()>0){
            return ResponseData.error(500,"汇率表未维护",list);
        }
        //刷新转换汇率
        this.baseMapper.refreshRateOne(param);//同币汇率为1的
        this.baseMapper.refreshRateOneMdg(param);//同币汇率为1的
        this.baseMapper.refreshExchangeRate(param);
        this.baseMapper.refreshExchangeRateMdg(param);

//        //按会计期间生成主维度数据
//        this.baseMapper.generateMainData(param);
//        //按会计期间生成主维度数据目的国
//        this.baseMapper.generateMainDataMdg(param);

        //刷新seller-发货国数据  去除有税号的
        /*
         * 1."TAX_COLLECTION_RESPONSIBILITY"=Seller
         * 2."PRICE_OF_ITEMS_VAT_RATE_PERCENT"≠0或≠空白
         * 4."SALE_ARRIVAL_COUNTRY"在《欧盟国这参数表》【缩写】范围内，再根据《税号列表》剔除有税号的国家。
        * */
        this.baseMapper.refreshSellerFhg(param);

        //刷新seller-目的国数据
        /*
         * 1."TAX_COLLECTION_RESPONSIBILITY"=Seller
         * 2."PRICE_OF_ITEMS_VAT_RATE_PERCENT"≠0或≠空白
         * */
        this.baseMapper.refreshSellerMdg(param);

        //刷新Marketplace-发货国数据  去除有税号的
        /*
         * 1."TAX_COLLECTION_RESPONSIBILITY"=MARKETPLACE
         * 2."SALE_ARRIVAL_COUNTRY"在《欧盟国这参数表》【缩写】范围内。
         * */
        this.baseMapper.refreshMarket(param);

        //B2B业务 刷新seller-发货国数据
        /*
         * 1."TAX_COLLECTION_RESPONSIBILITY"=Seller
         * 2."PRICE_OF_ITEMS_VAT_RATE_PERCENT"=0或=空白
         * 4."BUYER_VAT_NUMBER_COUNTRY"剔除GB以外的国家
         * 5."BUYER_VAT_NUMBER"≠空白
         * */
        this.baseMapper.refreshBtbSellerFhg(param);

        //修改状态为已审核
        this.baseMapper.updateStatus(param);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public ResponseData cancelBatch(AmazonVatTransactionsReportParam param) {
        UpdateWrapper<AmazonVatTransactionsReport> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ACTIVITY_PERIOD",param.getActivityPeriod())
                .eq("SYS_SHOPS_NAME",param.getSysShopsName())
                .eq("VERIFY_STATUS", ReportCheckStatus.getEnumValue("未审核").getCode())
                .set("VERIFY_STATUS", ReportCheckStatus.getEnumValue("已作废").getCode());
        this.baseMapper.update(null,updateWrapper);
        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    public List<AmazonVatTransactionsReportResult> export(AmazonVatTransactionsReportParam param) {
        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<AmazonVatTransactionsReportResult> page = this.baseMapper.queryListPage(pageContext, param);
        return page.getRecords();
    }

    public static final String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        // 设置为当前时间
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        // 设置为上一个月
        //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        date = calendar.getTime();
        return format.format(date);
    }
}
