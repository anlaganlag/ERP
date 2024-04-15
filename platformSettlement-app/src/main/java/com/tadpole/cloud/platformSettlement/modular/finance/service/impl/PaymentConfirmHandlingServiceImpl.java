package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.PaymentConfirmHandlingParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmHandlingResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.VoucherDetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.*;
import com.tadpole.cloud.platformSettlement.modular.finance.service.*;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.SyncToErpConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* <p>
* 回款确认办理 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Slf4j
@Service
public class PaymentConfirmHandlingServiceImpl extends ServiceImpl<PaymentConfirmHandlingMapper, PaymentConfirmHandling> implements IPaymentConfirmHandlingService {

    @Autowired
    IPaymentConfirmVoucherService paymentConfirmVoucherService;

    @Autowired
    IReceivableDetailService receivableDetailService;

    @Autowired
    IReceivableDetailDetailService receivableDetailDetailService;

    @Autowired
    IFixedExchangeRateService rateService;

    @Autowired
    IPaymentVoucherDetailService voucherDetailService;

    @Autowired
    SyncToErpConsumer syncToErpConsumer;

    @Resource
    InitialBalanceMapper initialBalanceMapper;

    @Resource
    ReceivableDetailMapper receivableDetailMapper;

    @Resource
    ReceivableDetailDetailMapper receivableDetailDetailMapper;

    @Resource
    IPaymentConfirmHandlingService handlingService;

    @Resource
    PaymentConfirmVoucherMapper paymentConfirmVoucherMapper;

    @DataSource(name = "finance")
    @Override
    public PageResult<PaymentConfirmHandlingResult> findPageBySpec(PaymentConfirmHandlingParam param) {
        Page pageContext = param.getPageContext();

        IPage<PaymentConfirmHandlingResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<PaymentConfirmHandlingResult> exportPaymentConfirmHandlingList(
        PaymentConfirmHandlingParam param) {
        List<PaymentConfirmHandlingResult> page = this.baseMapper.exportPaymentConfirmHandlingList( param);
        return page;
    }

    @DataSource(name = "finance")
    @Override
    public void confirm(PaymentConfirmHandlingParam param) throws ParseException {

        UpdateWrapper<PaymentConfirmHandling> updateWrapper = new UpdateWrapper<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date collectionDate = sdf.parse(param.getReceiveDate());

        //去前后空格
        String collectionAccount =param.getCollectionAccount();
        collectionAccount = collectionAccount.trim();
        while (collectionAccount.startsWith("　")) {//这里判断是不是全角空格
            collectionAccount = collectionAccount.substring(1, collectionAccount.length()).trim();
        }
        while (collectionAccount.endsWith("　")) {
            collectionAccount = collectionAccount.substring(0, collectionAccount.length() - 1).trim();
        }

        updateWrapper.eq("ID",param.getId())
                .set("COLLECTION_BANK",param.getCollectionBank())
                .set("COLLECTION_ACCOUNT",collectionAccount)
                .set("RECEIVE_AMOUNT",param.getReceiveAmount())
                .set("PROCEEDS_CURRENCY",param.getProceedsCurrency())
                .set("RECEIVE_DATE",collectionDate)
                .set("FISCAL_PERIOD",param.getReceiveDate().substring(0,7))
                .set("IS_RECEIVE_AMOUNT","是")
                .set("PAYMENT_TYPE",param.getPaymentType())
                .set("VERIFY_STATUS",1);
        if(param.getSubjectCode()!=null){
            updateWrapper.set("SUBJECT_CODE",param.getSubjectCode())
            .set("SUBJECT_NAME",param.getSubjectName());
            }
        this.baseMapper.update(null,updateWrapper);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmBatch(List<PaymentConfirmHandlingParam> params) throws ParseException {
        for (PaymentConfirmHandlingParam param : params) {
            this.confirm(param);
        }
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verify(PaymentConfirmHandlingParam param) throws Exception {

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();


        PaymentConfirmHandling paymentConfirm = this.baseMapper.selectById(param.getId());

        //判断汇款日期是否是月末
        Date voucherDate;
        String strvoucherDate;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(paymentConfirm.getReceiveDate());
        int getDay=calendar.get(Calendar.DAY_OF_MONTH);
        if(getDay==calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            //当月倒数第二天
            calendar.add(Calendar.DATE,-1);
            voucherDate=calendar.getTime();

        }else{
            voucherDate=paymentConfirm.getReceiveDate();
        }

        //凭证日期
        strvoucherDate=df.format(voucherDate);

        QueryWrapper<PaymentConfirmVoucher> voucherQueryWrapper=new QueryWrapper<>();
        voucherQueryWrapper.eq("CONFIRM_ID",paymentConfirm.getId())
                .eq("FISCAL_PERIOD",paymentConfirm.getFiscalPeriod());
        PaymentConfirmVoucher voucherdata = paymentConfirmVoucherMapper.selectOne(voucherQueryWrapper
                .orderByDesc("CONFIRM_ID").apply("rownum={0}",1));

        //日期转换：上个会计期间
        DateFormat format2=new SimpleDateFormat("yyyy-MM");
        Date date=new Date();
        try{
            date=format2.parse(paymentConfirm.getFiscalPeriod());
        }catch(Exception e){
            e.printStackTrace();
        }
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        c.add(Calendar.MONTH,-1);
        String premonth=format.format(c.getTime());

        //查询上个会计期间应收明细
        QueryWrapper<ReceivableDetail> prequeryWrapper = new QueryWrapper<>();
        prequeryWrapper.eq("PLATFORM_NAME",paymentConfirm.getPlatformName()).eq("SHOP_NAME",paymentConfirm.getShopName())
                .eq("SITE",paymentConfirm.getSite())
                .eq("FISCAL_PERIOD",premonth)
                .eq("STATUS",0);
        ReceivableDetail  prereceivableDetail = receivableDetailService.getBaseMapper().selectOne(prequeryWrapper);
        //效验
        if(prereceivableDetail!=null)
        {
            throw new Exception("上个会计期间应收明细未审核！");
        }

        String exchangeRateType = "固定汇率";
        String dest = paymentConfirm.getPlatformName() +
                " " + paymentConfirm.getShopName() + " " + paymentConfirm.getSite() + " " + paymentConfirm.getFiscalPeriod();
        String accountingDimensions = paymentConfirm.getPlatformName() + "_" + paymentConfirm.getShopName() + "_" + paymentConfirm.getSite();

        String currency = paymentConfirm.getOriginalCurrency();//原币币别

        FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
        rateParam.setOriginalCurrency(currency);
        rateParam.setEffectDate(df.parse(strvoucherDate));

        BigDecimal  exchangeRate= new BigDecimal(1);
        BigDecimal  inRate= new BigDecimal(1);

        if(!currency.equals("CNY")){
            FixedExchangeRate rate = rateService.getRateByDateCurrency(rateParam);
            exchangeRate = rate.getDirectRate();// 查询原币汇率
        }

        String proceedsCurrency = paymentConfirm.getProceedsCurrency();//收款币币别
        FixedExchangeRateParam rateParam2 = new FixedExchangeRateParam();
        rateParam2.setOriginalCurrency(proceedsCurrency);
        rateParam2.setEffectDate(df.parse(strvoucherDate));
        if(!proceedsCurrency.equals("CNY")){
            FixedExchangeRate incomeRate = rateService.getRateByDateCurrency(rateParam2);
            inRate= incomeRate.getDirectRate();// 查询收款币汇率
        }

        //回款方式
        String paymentType = paymentConfirm.getPaymentType();
        // 生成凭证
        if(voucherdata==null && paymentConfirm.getReceiveAmount().compareTo(BigDecimal.ZERO) != 0) {
            //海外银行销售中心凭证
            if(paymentType.equals("海外银行")){

                PaymentConfirmVoucher voucher = new PaymentConfirmVoucher();
                voucher.setConfirmId(paymentConfirm.getId());
                voucher.setAccountBook("销售中心账簿");
                voucher.setAccountingOrganization("销售中心");
                voucher.setFiscalPeriod(paymentConfirm.getFiscalPeriod());
                voucher.setProofWords("记");
                voucher.setVoucherDate(strvoucherDate);
                voucher.setVoucherType("销售中心");
                paymentConfirmVoucherService.save(voucher);

                BigDecimal voucherId = voucher.getVoucherId();

                // 生成凭证明细
                BigDecimal DebitAmount = BigDecimal.ZERO;
                BigDecimal CreditAmount = BigDecimal.ZERO;
                List<PaymentVoucherDetail> detailList = new ArrayList<>();
                //回款
                if (paymentConfirm.getReceiveAmount().compareTo(BigDecimal.ZERO) != 0) {

                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();

                    detail1.setVoucherId(voucherId);
                    detail1.setCurrency(proceedsCurrency);
                    detail1.setAccountingDimensions(accountingDimensions);
                    detail1.setExchangeRate(inRate);
                    detail1.setExchangeRateType(exchangeRateType);

                    detail1.setDigest(dest+" 销售回款");
                    detail1.setSubjectCode(paymentConfirm.getSubjectCode());
                    detail1.setSubjectName(paymentConfirm.getSubjectName());
                    detail1.setOriginalAmount(paymentConfirm.getReceiveAmount());
                    DebitAmount = paymentConfirm.getReceiveAmount().multiply(inRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                    detail1.setDebitAmount(DebitAmount);
                    detailList.add(detail1);
                }

                //应收账款
                if (paymentConfirm.getReceiveOriginalAmount().compareTo(BigDecimal.ZERO) != 0) {

                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();

                    detail1.setVoucherId(voucherId);
                    detail1.setCurrency(currency);
                    detail1.setAccountingDimensions(accountingDimensions);
                    detail1.setExchangeRate(exchangeRate);
                    detail1.setExchangeRateType(exchangeRateType);

                    detail1.setDigest(dest+" 销售回款");
                    detail1.setSubjectCode("1122.001");
                    detail1.setSubjectName("应收账款_平台货款");
                    detail1.setOriginalAmount(paymentConfirm.getReceiveOriginalAmount());
                    CreditAmount = paymentConfirm.getReceiveOriginalAmount().multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                    detail1.setCreditAmount(CreditAmount);
                    detailList.add(detail1);
                }

                //差额
                if (CreditAmount.subtract(DebitAmount).compareTo(BigDecimal.ZERO) != 0) {

                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();

                    detail1.setVoucherId(voucherId);
                    detail1.setCurrency("CNY");
                    detail1.setAccountingDimensions("BM1");
                    detail1.setDigest(dest+" 销售回款汇兑损益");
                    detail1.setSubjectCode("6603.001.07");
                    detail1.setSubjectName("财务费用_银行类费用_汇兑损益");
                    detail1.setDebitAmount(CreditAmount.subtract(DebitAmount));
                    detailList.add(detail1);
                }
                voucherDetailService.saveBatch(detailList);
            }
            //虎爪销售中心凭证
            if(paymentType.equals("虎爪")){

                PaymentConfirmVoucher voucher = new PaymentConfirmVoucher();
                voucher.setConfirmId(paymentConfirm.getId());
                voucher.setAccountBook("销售中心账簿");
                voucher.setAccountingOrganization("销售中心");
                voucher.setFiscalPeriod(paymentConfirm.getFiscalPeriod());
                voucher.setProofWords("记");
                voucher.setVoucherDate(strvoucherDate);
                voucher.setVoucherType("销售中心");
                paymentConfirmVoucherService.save(voucher);

                BigDecimal voucherId = voucher.getVoucherId();

                // 生成凭证明细
                BigDecimal DebitAmount = BigDecimal.ZERO;
                BigDecimal CreditAmount = BigDecimal.ZERO;
                List<PaymentVoucherDetail> detailList = new ArrayList<>();
                //回款
                if (paymentConfirm.getReceiveAmount().compareTo(BigDecimal.ZERO) != 0) {

                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();

                    detail1.setVoucherId(voucherId);
                    detail1.setCurrency(proceedsCurrency);
                    detail1.setAccountingDimensions(accountingDimensions);
                    detail1.setExchangeRate(inRate);
                    detail1.setExchangeRateType(exchangeRateType);

                    detail1.setDigest(dest+" 销售回款");
                    detail1.setSubjectCode("1122.004");
                    detail1.setSubjectName("应收账款_虎爪");
                    detail1.setOriginalAmount(paymentConfirm.getReceiveAmount());
                    DebitAmount = paymentConfirm.getReceiveAmount().multiply(inRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                    detail1.setDebitAmount(DebitAmount);
                    detailList.add(detail1);
                }

                //应收账款
                if (paymentConfirm.getReceiveOriginalAmount().compareTo(BigDecimal.ZERO) != 0) {

                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();

                    detail1.setVoucherId(voucherId);
                    detail1.setCurrency(currency);
                    detail1.setAccountingDimensions(accountingDimensions);
                    detail1.setExchangeRate(exchangeRate);
                    detail1.setExchangeRateType(exchangeRateType);

                    detail1.setDigest(dest+" 销售回款");
                    detail1.setSubjectCode("1122.001");
                    detail1.setSubjectName("应收账款_平台账款");
                    detail1.setOriginalAmount(paymentConfirm.getReceiveOriginalAmount());
                    CreditAmount = paymentConfirm.getReceiveOriginalAmount().multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                    detail1.setCreditAmount(CreditAmount);
                    detailList.add(detail1);
                }

                //差额
                if (CreditAmount.subtract(DebitAmount).compareTo(BigDecimal.ZERO) != 0) {

                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();

                    detail1.setVoucherId(voucherId);
                    detail1.setCurrency("CNY");
                    detail1.setAccountingDimensions("BM1");
                    detail1.setDigest(dest+" 销售回款汇兑损益");
                    detail1.setSubjectCode("6603.001.07");
                    detail1.setSubjectName("财务费用_银行类费用_汇兑损益");
                    detail1.setDebitAmount(CreditAmount.subtract(DebitAmount));
                    detailList.add(detail1);
                }
                voucherDetailService.saveBatch(detailList);
            }
//            //国内银行销售中心凭证
//            if(paymentType.equals("国内银行")){
//
//                PaymentConfirmVoucher voucher = new PaymentConfirmVoucher();
//                voucher.setConfirmId(paymentConfirm.getId());
//                voucher.setAccountBook("销售中心账簿");
//                voucher.setAccountingOrganization("销售中心");
//                voucher.setFiscalPeriod(paymentConfirm.getFiscalPeriod());
//                voucher.setProofWords("记");
//                voucher.setVoucherDate(strvoucherDate);
//                voucher.setVoucherType("销售中心");
//                paymentConfirmVoucherService.save(voucher);
//
//                BigDecimal voucherId = voucher.getVoucherId();
//
//                // 生成凭证明细
//                BigDecimal DebitAmount = BigDecimal.ZERO;
//                BigDecimal CreditAmount = BigDecimal.ZERO;
//                List<PaymentVoucherDetail> detailList = new ArrayList<>();
//                //回款
//                if (paymentConfirm.getReceiveAmount().compareTo(BigDecimal.ZERO) != 0) {
//
//                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();
//
//                    detail1.setVoucherId(voucherId);
//                    detail1.setCurrency(proceedsCurrency);
//                    detail1.setAccountingDimensions("采购中心");
//                    detail1.setExchangeRate(inRate);
//                    detail1.setExchangeRateType(exchangeRateType);
//
//                    detail1.setDigest(dest+" 销售回款");
//                    detail1.setSubjectCode("1122.002");
//                    detail1.setSubjectName("应收账款_内部往来");
//                    detail1.setOriginalAmount(paymentConfirm.getReceiveAmount());
//                    DebitAmount = paymentConfirm.getReceiveAmount().multiply(inRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//                    detail1.setDebitAmount(DebitAmount);
//                    detailList.add(detail1);
//                }
//
//                //应收账款
//                if (paymentConfirm.getReceiveOriginalAmount().compareTo(BigDecimal.ZERO) != 0) {
//
//                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();
//
//                    detail1.setVoucherId(voucherId);
//                    detail1.setCurrency(currency);
//                    detail1.setAccountingDimensions(accountingDimensions);
//                    detail1.setExchangeRate(exchangeRate);
//                    detail1.setExchangeRateType(exchangeRateType);
//
//                    detail1.setDigest(dest+" 销售回款");
//                    detail1.setSubjectCode("1122.001");
//                    detail1.setSubjectName("应收账款_平台货款");
//                    detail1.setOriginalAmount(paymentConfirm.getReceiveOriginalAmount());
//                    CreditAmount = paymentConfirm.getReceiveOriginalAmount().multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//                    detail1.setCreditAmount(CreditAmount);
//                    detailList.add(detail1);
//                }
//
//                //差额
//                if (CreditAmount.subtract(DebitAmount).compareTo(BigDecimal.ZERO) != 0) {
//
//                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();
//
//                    detail1.setVoucherId(voucherId);
//                    detail1.setCurrency("CNY");
//                    detail1.setAccountingDimensions("BM1");
//                    detail1.setDigest(dest+" 销售回款汇兑损益");
//                    detail1.setSubjectCode("6603.001.07");
//                    detail1.setSubjectName("财务费用_银行类费用_汇兑损益");
//                    detail1.setDebitAmount(CreditAmount.subtract(DebitAmount));
//                    detailList.add(detail1);
//                }
//                voucherDetailService.saveBatch(detailList);
//            }
//            //国内银行采购中心凭证
//            if(paymentType.equals("国内银行")){
//
//                PaymentConfirmVoucher voucher = new PaymentConfirmVoucher();
//                voucher.setConfirmId(paymentConfirm.getId());
//                voucher.setAccountBook("采购中心账簿");
//                voucher.setAccountingOrganization("采购中心");
//                voucher.setFiscalPeriod(paymentConfirm.getFiscalPeriod());
//                voucher.setProofWords("记");
//                voucher.setVoucherDate(strvoucherDate);
//                voucher.setVoucherType("采购中心");
//                paymentConfirmVoucherService.save(voucher);
//
//                BigDecimal voucherId = voucher.getVoucherId();
//
//                // 生成凭证明细
//                BigDecimal DebitAmount = BigDecimal.ZERO;
//                BigDecimal CreditAmount = BigDecimal.ZERO;
//                List<PaymentVoucherDetail> detailList = new ArrayList<>();
//                //回款
//                if (paymentConfirm.getReceiveAmount().compareTo(BigDecimal.ZERO) != 0) {
//
//                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();
//
//                    detail1.setVoucherId(voucherId);
//                    detail1.setCurrency(proceedsCurrency);
//                    detail1.setAccountingDimensions(accountingDimensions);
//                    detail1.setExchangeRate(inRate);
//                    detail1.setExchangeRateType(exchangeRateType);
//
//                    detail1.setDigest(dest+" 销售回款");
//                    detail1.setSubjectCode(paymentConfirm.getSubjectCode());
//                    detail1.setSubjectName(paymentConfirm.getSubjectName());
//                    detail1.setOriginalAmount(paymentConfirm.getReceiveAmount());
//                    DebitAmount = paymentConfirm.getReceiveAmount().multiply(inRate).setScale(2, BigDecimal.ROUND_HALF_UP);
//                    detail1.setDebitAmount(DebitAmount);
//                    detailList.add(detail1);
//                }
//
//                //应收账款
//                if (paymentConfirm.getReceiveOriginalAmount().compareTo(BigDecimal.ZERO) != 0) {
//
//                    PaymentVoucherDetail detail1 = new PaymentVoucherDetail();
//
//                    detail1.setVoucherId(voucherId);
//                    detail1.setCurrency(proceedsCurrency);
//                    detail1.setAccountingDimensions("销售中心");
//                    detail1.setExchangeRate(inRate);
//                    detail1.setExchangeRateType(exchangeRateType);
//
//                    detail1.setDigest(dest+" 销售回款");
//                    detail1.setSubjectCode("2202.002");
//                    detail1.setSubjectName("应付账款_内部往来");
//                    detail1.setOriginalAmount(paymentConfirm.getReceiveAmount());
//                    detail1.setCreditAmount(DebitAmount);
//                    detailList.add(detail1);
//                }
//
//                voucherDetailService.saveBatch(detailList);
//            }

        //生成应收明细
        ReceivableDetail detailParam = new ReceivableDetail();
        detailParam.setPlatformName(paymentConfirm.getPlatformName());
        detailParam.setShopName(paymentConfirm.getShopName());
        detailParam.setSite(paymentConfirm.getSite());
        detailParam.setFiscalPeriod(paymentConfirm.getFiscalPeriod());
        receivableDetailService.generateReceivable(detailParam);

        QueryWrapper<ReceivableDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName()).eq("SHOP_NAME",detailParam.getShopName())
                .eq("SITE",detailParam.getSite()).eq("FISCAL_PERIOD",detailParam.getFiscalPeriod());

        //应收明细会计期间
        ReceivableDetail  receivableDetail = receivableDetailService.getBaseMapper().selectOne(queryWrapper);

        ReceivableDetailDetail detailDetail = new ReceivableDetailDetail();
        detailDetail.setReceivableId(receivableDetail.getId());
        detailDetail.setSettlementId(paymentConfirm.getSettlementId());

        detailDetail.setIncomeType("收款");
        detailDetail.setOriginalCurrency(paymentConfirm.getOriginalCurrency());
        detailDetail.setProceedsCurrency(paymentConfirm.getProceedsCurrency());
        //收款金额为0
        if(paymentConfirm.getReceiveAmount().compareTo(BigDecimal.ZERO)==0){
            detailDetail.setReceiveAmount(BigDecimal.ZERO);
        }else{
            detailDetail.setReceiveAmount(paymentConfirm.getReceiveOriginalAmount());
        }
        detailDetail.setCreateAt(new Date());
        detailDetail.setCreateBy(currentUser.getName());
        receivableDetailDetailService.save(detailDetail);

        //刷取应收明细金额
        receivableDetailService.refresh(detailParam);
        }
        paymentConfirm.setVerifyBy(currentUser.getName());
        paymentConfirm.setIsReceiveAmount("是");
        paymentConfirm.setVerifyStatus(2);
        paymentConfirm.setVerifyBy(currentUser.getName());

        this.updateById(paymentConfirm);

    }



    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncToErp(PaymentConfirmHandlingParam param) throws IOException {

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        JSONArray Jarr = new JSONArray();
        //销售中心
        PaymentConfirmVoucher vParam = new PaymentConfirmVoucher();
        vParam.setConfirmId(param.getId());
        vParam.setVoucherType("销售中心");

        PaymentConfirmVoucher voucher = paymentConfirmVoucherService.queryVoucher(vParam);
        List<VoucherDetailResult> detail = paymentConfirmVoucherService.getSyncDetail(vParam);

        //采购中心
        PaymentConfirmVoucher vParamPurchase = new PaymentConfirmVoucher();
        vParamPurchase.setConfirmId(param.getId());
        vParamPurchase.setVoucherType("采购中心");

        PaymentConfirmVoucher voucherPurchase = paymentConfirmVoucherService.queryVoucher(vParamPurchase);
        List<VoucherDetailResult> detailPurchase = paymentConfirmVoucherService.getSyncDetail(vParamPurchase);

        String period = voucher.getFiscalPeriod();
        String[] eriodArr = period.split("-");

        if(voucher!=null){
            JSONObject object = new JSONObject();
            object.put("FBillNo", "HKQR-"+ period+"-"+voucher.getVoucherId().toString());//凭证编号 不传 返回
            object.put("FAccountBookID", "003");//销售中心
            object.put("FDate", voucher.getVoucherDate());
            object.put("FCreatorId",currentUser.getAccount());
            object.put("FVOUCHERGROUPID", "PRE001");//凭证字 记
            object.put("FATTACHMENTS", 0); //附件数
            object.put("FYEAR", eriodArr[0]);  //会计年度
            object.put("FACCBOOKORGID", "003");//销售中心
            object.put("FISADJUSTVOUCHER",false);
            object.put("FPERIOD", eriodArr[1]);//期间
            object.put("FSourceBillKey", "78050206-2fa6-40e3-b7c8-bd608146fa38"); //业务类型 手工录入

            for (VoucherDetailResult res:detail) {
                List<Map> mList = new ArrayList<>();
                Map map = new HashMap();
                if(res.getFACCOUNTID()!=null && res.getFACCOUNTID().equals("6603.001.07")){
                    map.put("HSType","部门");
                    map.put("HSNumber","BM1");
                }else if(res.getFACCOUNTID()!=null && res.getFACCOUNTID().equals("1122.002")){
                    map.put("HSType","组织机构");
                    map.put("HSNumber","002");
                }else if(res.getFACCOUNTID()!=null && res.getFACCOUNTID().equals("2202.002")){
                    map.put("HSType","组织机构");
                    map.put("HSNumber","003");
                }else{
                    map.put("HSType","组织机构");
                    map.put("HSNumber",res.getSHOPCODE());
                }
                mList.add(map);
                res.setFHSData(mList);
            }

            object.put("FEntity", detail);


            Jarr.add(object);
        }

        if(voucherPurchase!=null){
            JSONObject object = new JSONObject();
            object.put("FBillNo", "HKQR-"+ period+"-"+voucherPurchase.getVoucherId().toString());//凭证编号 不传 返回
            object.put("FAccountBookID", "002");//采购中心
            object.put("FDate", voucherPurchase.getVoucherDate());
            object.put("FCreatorId",currentUser.getAccount());
            object.put("FVOUCHERGROUPID", "PRE001");//凭证字 记
            object.put("FATTACHMENTS", 0); //附件数
            object.put("FYEAR", eriodArr[0]);  //会计年度
            object.put("FACCBOOKORGID", "002");//销售中心
            object.put("FISADJUSTVOUCHER",false);
            object.put("FPERIOD", eriodArr[1]);//期间
            object.put("FSourceBillKey", "78050206-2fa6-40e3-b7c8-bd608146fa38"); //业务类型 手工录入

            for (VoucherDetailResult res:detailPurchase) {
                List<Map> mList = new ArrayList<>();
                Map map = new HashMap();
                if(res.getFACCOUNTID()!=null && res.getFACCOUNTID().equals("6603.001.07")){
                    map.put("HSType","部门");
                    map.put("HSNumber","BM1");
                }else if(res.getFACCOUNTID()!=null && res.getFACCOUNTID().equals("1122.002")){
                    map.put("HSType","组织机构");
                    map.put("HSNumber","002");
                }else if(res.getFACCOUNTID()!=null && res.getFACCOUNTID().equals("2202.002")){
                    map.put("HSType","组织机构");
                    map.put("HSNumber","003");
                }else{
                    map.put("HSType","组织机构");
                    map.put("HSNumber",res.getSHOPCODE());
                }
                mList.add(map);
                res.setFHSData(mList);
            }

            object.put("FEntity", detailPurchase);


            Jarr.add(object);
        }

        log.error("回款确认办理凭证同步ERP入参[{}]", Jarr.toString());
        JSONObject obj  = syncToErpConsumer.voucher(Jarr);
        Boolean allPass = true;
        if (obj.getString("Code")==null ||!obj.getString("Code").equals("0")) {
            return  ResponseData.error("同步erp失败！"+obj.getString("Message"));
        }else {
            for (int j = 0; j < obj.getJSONArray("Response").size(); j++) {
                if (obj.getJSONArray("Response").getJSONObject(j).getString("SubCode").equals("0")) {
                    String voucherNo = obj.getJSONArray("Response").getJSONObject(0).getString("FVOUCHERGROUPNO");
                    voucher.setVoucherNo(voucherNo);
                    voucher.setSyncStatus((int) 1);
                    paymentConfirmVoucherService.updateById(voucher);

                    String voucherNo2 = "";
                    if (voucherPurchase != null) {
                        voucherNo2 = obj.getJSONArray("Response").getJSONObject(1).getString("FVOUCHERGROUPNO");
                        voucherPurchase.setVoucherNo(voucherNo2);
                        voucherPurchase.setSyncStatus((int) 1);
                        paymentConfirmVoucherService.updateById(voucherPurchase);
                    }
                    PaymentConfirmHandling handling = new PaymentConfirmHandling();
                    handling.setId(param.getId());
                    handling.setSyncStatus(1);
                    voucherNo = voucherNo2.equals("") ? voucherNo : voucherNo + "," + voucherNo;
                    handling.setVoucherNo(voucherNo);

                    handlingService.updateVoucherNo(handling);
                }else{
                    allPass=false;
                }
            }
        }
            if(allPass){
                return  ResponseData.success("同步erp成功！");
            }else{
                return  ResponseData.success("部分同步erp成功，部分失败!");
            }

    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyBatch(List<PaymentConfirmHandlingParam> params) throws Exception {
        for (PaymentConfirmHandlingParam param : params) {
            this.verify(param);
        }
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncToErpBatch(List<PaymentConfirmHandlingParam> params) throws IOException {

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        JSONArray Jarr = new JSONArray();

        for (PaymentConfirmHandlingParam param : params) {


            PaymentConfirmVoucher vParam = new PaymentConfirmVoucher();
            vParam.setConfirmId(param.getId());
            vParam.setVoucherType(param.getVoucherType());

            PaymentConfirmVoucher voucher = paymentConfirmVoucherService.queryVoucher(vParam);
            List<VoucherDetailResult> detail = paymentConfirmVoucherService.getSyncDetail(vParam);


            String period = voucher.getFiscalPeriod();
            String[] eriodArr = period.split("-");

            if(voucher!=null && param.getVoucherType().equals("销售中心")) {
                JSONObject object = new JSONObject();
                object.put("FBillNo", "HKQR-" + period + "-" + voucher.getVoucherId().toString());//凭证编号 不传 返回
                object.put("FAccountBookID", "003");//销售中心
                object.put("FDate", voucher.getVoucherDate());
                object.put("FCreatorId", currentUser.getAccount());
                object.put("FVOUCHERGROUPID", "PRE001");//凭证字 记
                object.put("FATTACHMENTS", 0); //附件数
                object.put("FYEAR", eriodArr[0]);  //会计年度
                object.put("FACCBOOKORGID", "003");//销售中心
                object.put("FISADJUSTVOUCHER", false);
                object.put("FPERIOD", eriodArr[1]);//期间
                object.put("FSourceBillKey", "78050206-2fa6-40e3-b7c8-bd608146fa38"); //业务类型 手工录入

                for (VoucherDetailResult res : detail) {
                    List<Map> mList = new ArrayList<>();
                    Map map = new HashMap();
                    if (res.getFACCOUNTID() != null && res.getFACCOUNTID().equals("6603.001.07")) {
                        map.put("HSType", "部门");
                        map.put("HSNumber", "BM1");
                    } else {
                        map.put("HSType", "组织机构");
                        map.put("HSNumber", res.getSHOPCODE());
                    }
                    mList.add(map);
                    res.setFHSData(mList);
                }

                object.put("FEntity", detail);
                Jarr.add(object);
            }
            if(voucher!=null && param.getVoucherType().equals("采购中心")){
                JSONObject object = new JSONObject();
                object.put("FBillNo", "HKQR-"+ period+"-"+voucher.getVoucherId().toString());//凭证编号 不传 返回
                object.put("FAccountBookID", "002");//采购中心
                object.put("FDate", voucher.getVoucherDate());
                object.put("FCreatorId",currentUser.getAccount());
                object.put("FVOUCHERGROUPID", "PRE001");//凭证字 记
                object.put("FATTACHMENTS", 0); //附件数
                object.put("FYEAR", eriodArr[0]);  //会计年度
                object.put("FACCBOOKORGID", "002");//销售中心
                object.put("FISADJUSTVOUCHER",false);
                object.put("FPERIOD", eriodArr[1]);//期间
                object.put("FSourceBillKey", "78050206-2fa6-40e3-b7c8-bd608146fa38"); //业务类型 手工录入

                for (VoucherDetailResult res:detail) {
                    List<Map> mList = new ArrayList<>();
                    Map map = new HashMap();
                    if(res.getFACCOUNTID()!=null && res.getFACCOUNTID().equals("6603.001.07")){
                        map.put("HSType","部门");
                        map.put("HSNumber","BM1");
                    }else{
                        map.put("HSType","组织机构");
                        map.put("HSNumber",res.getSHOPCODE());
                    }
                    mList.add(map);
                    res.setFHSData(mList);
                }

                object.put("FEntity", detail);


                Jarr.add(object);
            }

        }
        JSONObject obj  = syncToErpConsumer.voucher(Jarr);

        if (obj.getString("Code")==null ||!obj.getString("Code").equals("0")) {
            return  ResponseData.error("同步erp失败！"+obj.getJSONArray("Response").getJSONObject(0).getString("SubMessage"));
        }else{
            Boolean allPass = true;
            for(int j=0;j<obj.getJSONArray("Response").size();j++){

               if(obj.getJSONArray("Response").getJSONObject(j).getString("SubCode").equals("0")){
                PaymentConfirmVoucher vParam = new PaymentConfirmVoucher();
                vParam.setConfirmId(params.get(j).getId());
                vParam.setVoucherType(params.get(j).getVoucherType());
                PaymentConfirmVoucher voucher = paymentConfirmVoucherService.queryVoucher(vParam);

                String voucherNo = obj.getJSONArray("Response").getJSONObject(j).getString("FVOUCHERGROUPNO");

                voucher.setVoucherNo(voucherNo);
                voucher.setSyncStatus((int)1);
                paymentConfirmVoucherService.updateById(voucher);

                PaymentConfirmHandling handling = new PaymentConfirmHandling();
                handling.setId(params.get(j).getId());
                handling.setSyncStatus(1);
                voucherNo = handling.getVoucherNo()==null||handling.getVoucherNo().equals("")?voucherNo:handling.getVoucherNo()+","+voucherNo;
                handling.setVoucherNo(voucherNo);

                handlingService.updateVoucherNo(handling);
               }else{
                   allPass=false;
               }
            }

            if(allPass){
                return  ResponseData.success("同步erp成功！");
            }else{
                return  ResponseData.success("部分同步erp成功，部分失败!");
            }
        }
    }

    @DataSource(name = "EBMS")
    @Override
    public List<PaymentConfirmHandlingResult> getBank(PaymentConfirmHandlingParam param) {
        return this.baseMapper.getBank(param);
    }

    @DataSource(name = "finance")
    @Override
    public void reject(PaymentConfirmHandlingParam param) {

        UpdateWrapper<PaymentConfirmHandling> updateWrapper = new UpdateWrapper<>();

        updateWrapper.eq("ID",param.getId())
                .set("VERIFY_STATUS",3);

        this.baseMapper.update(null,updateWrapper);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectBatch(List<PaymentConfirmHandlingParam> params) {
        for (PaymentConfirmHandlingParam param : params) {
            this.reject(param);
        }
    }

    @DataSource(name = "erpcloud")
    @Override
    public BankSubjectCodeMcms getSubjectByBank(PaymentConfirmHandlingParam param) {
        return this.baseMapper.getSubjectByBank(param);
    }

    @DataSource(name = "finance")
    @Override
    public void updateVoucherNo(PaymentConfirmHandling handing) {
        this.baseMapper.updateVoucherNo(handing);
    }

    @DataSource(name = "erpcloud")
    @Override
    public List<String> getErpVoucherNo(PaymentConfirmHandlingParam param) {
        return this.baseMapper.erpVoucherNo(param);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData returnVerify(PaymentConfirmHandlingParam param) throws Exception {

        LoginContext current= SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        PaymentConfirmHandling handling = this.baseMapper.selectById(param.getId());

        if(handling.getVerifyStatus()==2 && handling.getSyncStatus()!=1){

            //生成应收明细
            ReceivableDetail detailParam = new ReceivableDetail();
            detailParam.setPlatformName(handling.getPlatformName());
            detailParam.setShopName(handling.getShopName());
            detailParam.setSite(handling.getSite());
            detailParam.setFiscalPeriod(handling.getFiscalPeriod());
            receivableDetailService.generateReceivable(detailParam);

            QueryWrapper<ReceivableDetail> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName()).eq("SHOP_NAME",detailParam.getShopName())
                    .eq("SITE",detailParam.getSite()).eq("FISCAL_PERIOD",detailParam.getFiscalPeriod());

            //应收明细会计期间
            ReceivableDetail  receivableDetail = receivableDetailService.getBaseMapper().selectOne(queryWrapper);

            if(receivableDetail.getStatus().compareTo(new BigDecimal(1))==0){
                return ResponseData.error("应收明细已审核，数据不能被驳回！");
            }

            ReceivableDetailDetail detailDetail = new ReceivableDetailDetail();
            detailDetail.setReceivableId(receivableDetail.getId());
            detailDetail.setSettlementId(handling.getSettlementId());

            detailDetail.setIncomeType("收款");
            detailDetail.setOriginalCurrency(handling.getOriginalCurrency());
            detailDetail.setProceedsCurrency(handling.getProceedsCurrency());
            //收款金额为0
            if(handling.getReceiveAmount().compareTo(BigDecimal.ZERO)==0){
                detailDetail.setReceiveAmount(BigDecimal.ZERO);
            }else{
                detailDetail.setReceiveAmount(handling.getReceiveOriginalAmount().negate());
            }
            detailDetail.setCreateAt(new Date());
            detailDetail.setCreateBy(currentUser.getName());
            detailDetail.setIsReject(1);
            receivableDetailDetailService.save(detailDetail);

            UpdateWrapper<ReceivableDetailDetail> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SETTLEMENT_ID",handling.getSettlementId()).eq("RECEIVABLE_ID",receivableDetail.getId())
                         .eq("INCOME_TYPE","收款").set("IS_REJECT",1);

            receivableDetailDetailService.update(null,updateWrapper);
            //刷取应收明细金额
            receivableDetailService.refresh(detailParam);
        }else {
            return ResponseData.error("数据不能被驳回！");
        }

        handling.setVerifyBy(currentUser.getName());
        handling.setVerifyStatus(3);//未通过
        handling.setVerifyBy(currentUser.getName());

        this.updateById(handling);

        return ResponseData.success();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData toReceivableDetail(PaymentConfirmHandlingParam param) throws Exception {

        PaymentConfirmHandling paymentConfirm = this.baseMapper.selectById(param.getId());

        ReceivableDetail detailParam = new ReceivableDetail();
        detailParam.setPlatformName(paymentConfirm.getPlatformName());
        detailParam.setShopName(paymentConfirm.getShopName());
        detailParam.setSite(paymentConfirm.getSite());
        detailParam.setFiscalPeriod(paymentConfirm.getFiscalPeriod());
        receivableDetailService.generateReceivable(detailParam);

        QueryWrapper<ReceivableDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PLATFORM_NAME",detailParam.getPlatformName()).eq("SHOP_NAME",detailParam.getShopName())
                .eq("SITE",detailParam.getSite()).eq("FISCAL_PERIOD",detailParam.getFiscalPeriod());

        //应收明细会计期间
        ReceivableDetail  receivableDetail = receivableDetailService.getBaseMapper().selectOne(queryWrapper);

        ReceivableDetailDetail detailDetail = new ReceivableDetailDetail();
        detailDetail.setReceivableId(receivableDetail.getId());
        detailDetail.setSettlementId(paymentConfirm.getSettlementId());

        detailDetail.setIncomeType("收款");
        detailDetail.setOriginalCurrency(paymentConfirm.getOriginalCurrency());
        detailDetail.setProceedsCurrency(paymentConfirm.getProceedsCurrency());
        //收款金额为0
        if(paymentConfirm.getReceiveAmount().compareTo(BigDecimal.ZERO)==0){
            detailDetail.setReceiveAmount(BigDecimal.ZERO);
        }else{
            detailDetail.setReceiveAmount(paymentConfirm.getReceiveOriginalAmount());
        }
        detailDetail.setCreateAt(new Date());
        receivableDetailDetailService.save(detailDetail);
        //刷取应收明细金额
        receivableDetailService.refresh(detailParam);
        return ResponseData.success();
    }
}
