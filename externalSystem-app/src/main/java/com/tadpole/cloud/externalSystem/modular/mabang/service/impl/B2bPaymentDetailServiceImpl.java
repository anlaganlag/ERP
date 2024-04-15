package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.entity.SysDict;
import cn.stylefeng.guns.cloud.system.api.base.model.result.DictInfo;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.k3.service.K3DatabaseService;
import com.tadpole.cloud.externalSystem.modular.k3.utils.SyncToErpUtil;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.consumer.DictServiceConsumer;
import com.tadpole.cloud.externalSystem.modular.mabang.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.externalSystem.modular.mabang.consumer.PaymentConfirmVoucherConsumer;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.B2bPaymentDetail;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.B2bPaymentDetailMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3BankAccountInfo;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.B2bPaymentDetailParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bMabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.B2bPaymentDetailResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bMabangOrdersService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bPaymentDetailService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.B2bPaymentService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmVoucherResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * B2B客户付款明细;(B2B_PAYMENT_DETAIL)--服务实现类
 *
 * @author : LSY
 * @create : 2023-9-14
 */
@Slf4j
@Service
public class B2bPaymentDetailServiceImpl extends ServiceImpl<B2bPaymentDetailMapper, B2bPaymentDetail> implements B2bPaymentDetailService {
    @Resource
    private B2bPaymentDetailMapper b2bPaymentDetailMapper;

    @Resource
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;

    @Resource
    private B2bMabangOrdersService b2bMabangOrdersService;

    @Resource
    private SyncToErpUtil syncToErpUtil;

    @Resource
    private PaymentConfirmVoucherConsumer paymentConfirmVoucherConsumer;

    @Resource
    private B2bPaymentService b2bPaymentService;

    @Resource
    private K3DatabaseService k3DatabaseService;

    @Resource
    private DictServiceConsumer dictServiceConsumer;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bPaymentDetail queryById(BigDecimal id) {
        return b2bPaymentDetailMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param queryParam 筛选条件
     * @param current    当前页码
     * @param size       每页大小
     * @return Page 分页查询结果
     */
    @DataSource(name = "external")
    @Override
    public Page<B2bPaymentDetailResult> paginQuery(B2bPaymentDetailParam queryParam, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<B2bPaymentDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPlatformOrderId()), B2bPaymentDetail::getPlatformOrderId, queryParam.getPlatformOrderId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCurrency()), B2bPaymentDetail::getCurrency, queryParam.getCurrency());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPayType()), B2bPaymentDetail::getPayType, queryParam.getPayType());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getPayRatioTitle()), B2bPaymentDetail::getPayRatioTitle, queryParam.getPayRatioTitle());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getAccountNumber()), B2bPaymentDetail::getAccountNumber, queryParam.getAccountNumber());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getInvNumber()), B2bPaymentDetail::getInvNumber, queryParam.getInvNumber());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getOperateSubmit()), B2bPaymentDetail::getOperateSubmit, queryParam.getOperateSubmit());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getConfirmStatus()), B2bPaymentDetail::getConfirmStatus, queryParam.getConfirmStatus());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getConfirmPerson()), B2bPaymentDetail::getConfirmPerson, queryParam.getConfirmPerson());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getConfirmPersonNo()), B2bPaymentDetail::getConfirmPersonNo, queryParam.getConfirmPersonNo());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getVoucherNo()), B2bPaymentDetail::getVoucherNo, queryParam.getVoucherNo());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSyncType()), B2bPaymentDetail::getSyncType, queryParam.getSyncType());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSyncRequstPar()), B2bPaymentDetail::getSyncRequstPar, queryParam.getSyncRequstPar());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getSyncResultMsg()), B2bPaymentDetail::getSyncResultMsg, queryParam.getSyncResultMsg());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getCreatedBy()), B2bPaymentDetail::getCreatedBy, queryParam.getCreatedBy());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getUpdatedBy()), B2bPaymentDetail::getUpdatedBy, queryParam.getUpdatedBy());
        queryWrapper.eq(ObjectUtil.isNotEmpty(queryParam.getRemark()), B2bPaymentDetail::getRemark, queryParam.getRemark());

        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getId()), B2bPaymentDetail::getId, queryParam.getId());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCurrencyRate()), B2bPaymentDetail::getCurrencyRate, queryParam.getCurrencyRate());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getPayRatio()), B2bPaymentDetail::getPayRatio, queryParam.getPayRatio());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getAmountUnconfirmed()), B2bPaymentDetail::getAmountUnconfirmed, queryParam.getAmountUnconfirmed());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getAmountConfirmed()), B2bPaymentDetail::getAmountConfirmed, queryParam.getAmountConfirmed());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getBankArrivalTime()), B2bPaymentDetail::getBankArrivalTime, queryParam.getBankArrivalTime());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getConfirmTime()), B2bPaymentDetail::getConfirmTime, queryParam.getConfirmTime());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSyncStatus()), B2bPaymentDetail::getSyncStatus, queryParam.getSyncStatus());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getSyncTime()), B2bPaymentDetail::getSyncTime, queryParam.getSyncTime());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getCreatedTime()), B2bPaymentDetail::getCreatedTime, queryParam.getCreatedTime());
        queryWrapper.eq(ObjectUtil.isNotNull(queryParam.getUpdatedTime()), B2bPaymentDetail::getUpdatedTime, queryParam.getUpdatedTime());
        //2. 执行分页查询
        Page<B2bPaymentDetailResult> pagin = new Page<>(current, size, true);
        IPage<B2bPaymentDetailResult> selectResult = b2bPaymentDetailMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param b2bPaymentDetail 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    public B2bPaymentDetail insert(B2bPaymentDetail b2bPaymentDetail) {
        b2bPaymentDetail.setId(BigDecimal.valueOf(IdWorker.getId()));
        b2bPaymentDetail.setCreatedTime(new Date());
        b2bPaymentDetailMapper.insert(b2bPaymentDetail);
        return b2bPaymentDetail;
    }

    /**
     * 更新数据
     *
     * @param entityParam 实例对象
     * @return 实例对象
     */
    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public B2bPaymentDetail update(B2bPaymentDetail entityParam) {
        LoginUser loginUserInfo = LoginContext.me().getLoginUser();

        entityParam.setUpdatedBy(loginUserInfo.getName());
        entityParam.setUpdatedTime(new Date());
        boolean yet = this.updateById(entityParam);
        //3. 更新成功了，查询最最对象返回
        if (yet) {
            return queryById(entityParam.getId());
        } else {
            return entityParam;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "external")
    @Override
    public boolean deleteById(BigDecimal id) {
        int total = b2bPaymentDetailMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "external")
    @Override
    public boolean deleteBatchIds(List<BigDecimal> idList) {
        int delCount = b2bPaymentDetailMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @DataSource(name = "external")
    @Override
    public List<B2bPaymentDetail> queryByPlatformOrderId(String platformOrderId) {
        LambdaQueryWrapper<B2bPaymentDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(B2bPaymentDetail::getPlatformOrderId, platformOrderId);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    @DataSource(name = "external")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData financeConfirm(B2bPaymentDetail param) {


        B2bPaymentDetail paymentDetail = this.getById(param.getId());
        if (ObjectUtil.isNull(paymentDetail)) {
            return ResponseData.error("提交数据id不正确！");
        }
        if (!MabangConstant.OPERATE_PAYMENT_DETAIL_STATUS_SUBMIT.equals(paymentDetail.getOperateSubmit())) {
            return ResponseData.error("运营未提交本数据，财务不能确认收款！");
        }
        if (MabangConstant.FINANCE_PAYMENT_DETAIL_STATUS_CONFIRMED.equals(paymentDetail.getConfirmStatus())) {
            return ResponseData.error("本数据已经处理，请勿重复操作！");
        }
        //汇率查询
        if (!("CNY".equals(paymentDetail.getCurrency()) || "RMB".equals(paymentDetail.getCurrency()))) {//不是人民币的情况，需要取汇率 y
            FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
            rateParam.setOriginalCurrency(paymentDetail.getCurrency());
            rateParam.setEffectDate(param.getConfirmTime());
            FixedExchangeRate rate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
            if (ObjectUtil.isNull(rate)) {
                return ResponseData.error("财务填写的收款时间[" + param.getConfirmTime().toString() + "]，获取货币[" + paymentDetail.getCurrency() + "]转人民币的汇率失败！");
            }
        }

        //更新付款明细
        updatePaymentDetail(param, paymentDetail);
        //更新汇总表的确认金额
        b2bPaymentService.updateAmount(paymentDetail, MabangConstant.BIZ_TYPE_FINANCE_CONFIRM);
        //产生K3付款凭证---生成凭证信息
        VoucherInfo voucherInfo = this.saveVoucherInfo(paymentDetail);
        //调用K3接口
        return requstK3Voucher(voucherInfo, paymentDetail);
    }

    private ResponseData requstK3Voucher(VoucherInfo voucherInfo, B2bPaymentDetail paymentDetail) {
        JSONArray jar = getRequestK3Parm(voucherInfo);
        paymentDetail.setSyncRequstPar(JSONUtil.toJsonStr(jar));
        log.error("B2B回款确认办理凭证同步ERP入参[{}]", jar.toString());
        JSONObject obj = null;
        try {
            obj = syncToErpUtil.voucher(jar);
        } catch (Exception e) {
            log.error("B2B回款确认办理凭证同步ERP异常[{}]", JSON.toJSONString(e));
            ResponseData.error("B2B回款确认办理凭证同步ERP异常[" + e.toString() + "]");
        }
        paymentDetail.setSyncResultMsg(JSONUtil.toJsonStr(obj));
        if (obj.getString("Code") == null || !obj.getString("Code").equals("0")) {
            paymentDetail.setSyncStatus(BigDecimal.ZERO);
            this.saveOrUpdate(paymentDetail);
            return ResponseData.error("同步erp失败！" + obj.getString("Message"));
        } else {
            for (int j = 0; j < obj.getJSONArray("Response").size(); j++) {
                if (obj.getJSONArray("Response").getJSONObject(j).getString("SubCode").equals("0")) {
                    String voucherNo = obj.getJSONArray("Response").getJSONObject(0).getString("FVOUCHERGROUPNO");
                    voucherInfo.confirmVoucher.setVoucherNo(voucherNo);
                    voucherInfo.confirmVoucher.setSyncStatus((int) 1);
                    paymentDetail.setSyncStatus(BigDecimal.ONE);
                    paymentDetail.setVoucherNo(voucherNo);
                    this.saveOrUpdate(paymentDetail);
                    paymentConfirmVoucherConsumer.saveOrUpdate(voucherInfo.confirmVoucher);
                }
            }
            return ResponseData.success();
        }
    }

    private void updatePaymentDetail(B2bPaymentDetail parm, B2bPaymentDetail paymentDetail) {
        paymentDetail.setAmountConfirmed(parm.getAmountConfirmed());
        paymentDetail.setAmountCommission(parm.getAmountCommission());
//        paymentDetail.setAmountUnconfirmed(paymentDetail.getAmountUnconfirmed().subtract(parm.getAmountConfirmed()));
        LoginUser loginUserInfo = LoginContext.me().getLoginUser();

        paymentDetail.setConfirmStatus(MabangConstant.FINANCE_PAYMENT_DETAIL_STATUS_CONFIRMED);
        paymentDetail.setConfirmPerson(loginUserInfo.getName());
        paymentDetail.setConfirmPersonNo(loginUserInfo.getAccount());
        paymentDetail.setConfirmTime(parm.getConfirmTime());
        paymentDetail.setUpdatedTime(new Date());
        paymentDetail.setUpdatedBy(loginUserInfo.getName());

        this.saveOrUpdate(paymentDetail);
    }

    private VoucherInfo saveVoucherInfo(B2bPaymentDetail paymentDetail) {
        //凭证单头
        PaymentConfirmVoucher confirmVoucher = getPaymentConfirmVoucher(paymentDetail);
        B2bMabangOrdersResult mabangOrdersResult = b2bMabangOrdersService.queryByPlatformOrderId(paymentDetail.getPlatformOrderId());
        //凭证明细（应收账款_平台贷款（贷方金额） =  银行存款（借方金额） + 财务费用_银行类费用_银行手续费（借方金额） ）
        List<PaymentVoucherDetail> detailList = getPaymentVoucherDetail(paymentDetail, confirmVoucher.getVoucherId(), mabangOrdersResult);
        paymentConfirmVoucherConsumer.saveVoucherDetail(detailList);
        VoucherInfo result = new VoucherInfo(confirmVoucher, mabangOrdersResult, detailList);
        return result;
    }

    private PaymentConfirmVoucher getPaymentConfirmVoucher(B2bPaymentDetail paymentDetail) {
        LocalDate confirmDate = paymentDetail.getConfirmTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String year = confirmDate.getYear() + "";
        String yearAndMonth = confirmDate.getYear() + "-" + confirmDate.getMonthValue();
        String yearAndMonthDay = confirmDate.getYear() + "/" + confirmDate.getMonthValue() + "/" + confirmDate.getDayOfMonth();

        PaymentConfirmVoucher confirmVoucher = new PaymentConfirmVoucher();
        confirmVoucher.setConfirmId(paymentDetail.getId());
        confirmVoucher.setVoucherId(paymentDetail.getId());
        confirmVoucher.setAccountBook("销售中心账簿");
        confirmVoucher.setAccountingOrganization("销售中心");
        confirmVoucher.setFiscalPeriod(yearAndMonth);
        confirmVoucher.setProofWords("记");
        confirmVoucher.setVoucherDate(yearAndMonthDay);
        confirmVoucher.setVoucherType("销售中心");
        confirmVoucher = paymentConfirmVoucherConsumer.saveOrUpdate(confirmVoucher);
        return confirmVoucher;
    }

    /**
     * 凭证明细（应收账款_平台贷款（贷方金额） =  银行存款（借方金额） + 财务费用_银行类费用_银行手续费（借方金额） ）
     *
     * @param paymentDetail
     * @param voucherId
     * @param mabangOrdersResult
     * @return
     */
    private List<PaymentVoucherDetail> getPaymentVoucherDetail(B2bPaymentDetail paymentDetail, BigDecimal voucherId, B2bMabangOrdersResult mabangOrdersResult) {

        BigDecimal inRate = BigDecimal.ONE;
        if (!("CNY".equals(paymentDetail.getCurrency()) || "RMB".equals(paymentDetail.getCurrency()))) {//不是人民币的情况，需要取汇率 y
            FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
            rateParam.setOriginalCurrency(paymentDetail.getCurrency());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            rateParam.setEffectDate(paymentDetail.getConfirmTime());
            FixedExchangeRate rate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
            inRate = rate.getDirectRate();//
        }

        List<PaymentVoucherDetail> detailList = new ArrayList<>();
        //贷方金额=实收+手续费
        PaymentVoucherDetail voucherDetailCredit = getVoucherDetailBaseInfo(paymentDetail, voucherId, mabangOrdersResult, inRate);
        BigDecimal originalCreditAmount = paymentDetail.getAmountConfirmed().add(paymentDetail.getAmountCommission());
        BigDecimal creditAmount = originalCreditAmount.multiply(inRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        voucherDetailCredit.setOriginalAmount(originalCreditAmount);
        voucherDetailCredit.setCreditAmount(creditAmount);
        voucherDetailCredit.setSubjectCode("1122.001");
        voucherDetailCredit.setSubjectName("应收账款_平台货款");
        detailList.add(voucherDetailCredit);

        if (paymentDetail.getAmountCommission().compareTo(BigDecimal.ZERO) > 0) {
            PaymentVoucherDetail voucherDetailCommission = getVoucherDetailBaseInfo(paymentDetail, voucherId, mabangOrdersResult, inRate);
            BigDecimal commissionAmount = paymentDetail.getAmountCommission().multiply(inRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            //汇率可能会存在相差1分钱的情况  偏差的1分钱 在手续费里加减
            BigDecimal confirmedAumount = paymentDetail.getAmountConfirmed().multiply(inRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = commissionAmount.add(creditAmount.subtract(commissionAmount).subtract(confirmedAumount));

            voucherDetailCommission.setOriginalAmount(paymentDetail.getAmountCommission());
            voucherDetailCommission.setDebitAmount(commissionAmount);
            voucherDetailCommission.setSubjectCode("6603.001.01");
            voucherDetailCommission.setSubjectName("财务费用_银行类费用_银行手续费");
            detailList.add(voucherDetailCommission);
        }

        PaymentVoucherDetail voucherDetailConfirmed = getVoucherDetailBaseInfo(paymentDetail, voucherId, mabangOrdersResult, inRate);
        //获取填写账号的科目信息
        //如果银行账号为：平台店铺账号收款
        K3BankAccountInfo k3BankAccountInfo = null;
        k3BankAccountInfo = k3DatabaseService.queryK3BankAccountInfo(paymentDetail.getAccountNumber(), mabangOrdersResult.getFinanceCode());
        if (ObjectUtil.isNotEmpty(paymentDetail.getRemark())
                && paymentDetail.getRemark().contains(MabangConstant.REMARK_PLAT_SHOP_ACCOUNT)) {
            //获取店铺对应的 平台店铺账号收款
            k3BankAccountInfo = new K3BankAccountInfo();
            k3BankAccountInfo.setSubjectName(paymentDetail.getAccountName());
            k3BankAccountInfo.setSubjectCode(paymentDetail.getAccountNumber());
        }

        if (ObjectUtil.isNull(k3BankAccountInfo)) {
            log.error("更具银行账号【{}】获取k3银行信息科目信息失败", paymentDetail.getAccountNumber());
        } else {
            voucherDetailConfirmed.setSubjectCode(k3BankAccountInfo.getSubjectCode());
            voucherDetailConfirmed.setSubjectName(k3BankAccountInfo.getSubjectName());
            //财务人员要求调整 科目
            //            1002.015.01  银行存款_刘爱珍_招商银行深圳龙城支行
            if ("1002.015.01".equals(k3BankAccountInfo.getSubjectCode())) {
                voucherDetailConfirmed.setSubjectCode("2202.002");
                voucherDetailConfirmed.setSubjectName("应付账款_内部往来");
            }
            //1221.002.03  其他应收款_支付宝_深圳市百山川科技有限公司
            if ("1221.002.03".equals(k3BankAccountInfo.getSubjectCode())) {
                voucherDetailConfirmed.setSubjectCode("2202.002");
                voucherDetailConfirmed.setSubjectName("应付账款_内部往来");
            }
        }
        BigDecimal confirmedAumount = paymentDetail.getAmountConfirmed().multiply(inRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        voucherDetailConfirmed.setOriginalAmount(paymentDetail.getAmountConfirmed());
        voucherDetailConfirmed.setDebitAmount(confirmedAumount);
        detailList.add(voucherDetailConfirmed);

        return detailList;
    }


    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData operSubmit(B2bPaymentDetail b2bPaymentDetail) {

        LoginUser loginUserInfo = LoginContext.me().getLoginUser();
        if (ObjectUtil.isNull(b2bPaymentDetail.getId())) {
            //新增的数据
            b2bPaymentDetail.setId(BigDecimal.valueOf(IdWorker.getId()));
            b2bPaymentDetail.setCreatedTime(new Date());
        } else {

            B2bPaymentDetail paymentDetail = this.getById(b2bPaymentDetail.getId());
            if (ObjectUtil.isNull(paymentDetail)) {
                return ResponseData.error("提交数据未找到明细记录");
            }

            if (MabangConstant.OPERATE_PAYMENT_DETAIL_STATUS_SUBMIT.equals(paymentDetail.getOperateSubmit())) {
                return ResponseData.error("数据已经提交，请勿重复操作！");
            }
        }

        B2bMabangOrdersResult mabangOrdersResult = b2bMabangOrdersService.queryByPlatformOrderId(b2bPaymentDetail.getPlatformOrderId());
        K3BankAccountInfo k3BankAccountInfo = k3DatabaseService.queryK3BankAccountInfo(b2bPaymentDetail.getAccountNumber(), mabangOrdersResult.getFinanceCode());
        if (ObjectUtil.isNull(k3BankAccountInfo)
                || ObjectUtil.isEmpty(k3BankAccountInfo.getSubjectCode())
                || ObjectUtil.isEmpty(k3BankAccountInfo.getSubjectName())) {

            //K3未找到对应的账户信息--查找选择的是不是平台账户收款
            ResponseData checkResult = checkAccount(b2bPaymentDetail, mabangOrdersResult);
            if (!checkResult.getSuccess()) {
                return checkResult;
            }
        }

        b2bPaymentDetail.setOperateSubmit(MabangConstant.OPERATE_PAYMENT_DETAIL_STATUS_SUBMIT);
        b2bPaymentDetail.setCreatedBy(loginUserInfo.getAccount());
        b2bPaymentDetail.setUpdatedTime(new Date());
        b2bPaymentDetail.setUpdatedBy(loginUserInfo.getName());

        this.saveOrUpdate(b2bPaymentDetail);

        b2bPaymentService.updateAmount(b2bPaymentDetail, MabangConstant.BIZ_TYPE_OPERATE_SUBMIT);
        return ResponseData.success();
    }


    /**
     * @param b2bPaymentDetail
     * @param mabangOrdersResult
     * @return true:运营采用平台收款  false :未采用平台收款
     */
    private ResponseData checkAccount(B2bPaymentDetail b2bPaymentDetail, B2bMabangOrdersResult mabangOrdersResult) {
        List<K3BankAccountInfo> platShopAccount = this.getPlatShopAccount(mabangOrdersResult.getShopName());
        if (ObjectUtil.isEmpty(platShopAccount)) {
            return ResponseData.error("收款明细选择的收款方式：【" + b2bPaymentDetail.getAccountName() + "】数据字典未配置店铺的平台收款信息");
        }
        K3BankAccountInfo accountInfo = platShopAccount.get(0);
        if (accountInfo.getBankName().equals(b2bPaymentDetail.getAccountName()) && accountInfo.getBankNo().equals(b2bPaymentDetail.getAccountNumber())) {
            String remark = ObjectUtil.isEmpty(b2bPaymentDetail.getRemark()) ? MabangConstant.REMARK_PLAT_SHOP_ACCOUNT : b2bPaymentDetail.getRemark() + "|" + MabangConstant.REMARK_PLAT_SHOP_ACCOUNT;
            b2bPaymentDetail.setRemark(remark);
            return ResponseData.success();
        }

        return ResponseData.error("收款明细选择的收款方式：【" + b2bPaymentDetail.getAccountName() + "】和数据字典配置的店铺平台收款信息不一致");
    }

    @DataSource(name = "external")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncPaymentDetail(BigDecimal id) {
        B2bPaymentDetail paymentDetail = this.getById(id);
        if (ObjectUtil.isNull(paymentDetail)) {
            return ResponseData.error("id错误");
        }
        PaymentConfirmVoucher param = new PaymentConfirmVoucher();
        param.setConfirmId(id);
        PaymentConfirmVoucherResult voucherResult = paymentConfirmVoucherConsumer.queryVoucherInfo(param);

        if (ObjectUtil.isNull(voucherResult)) {
            return ResponseData.error("未找到之前生产的凭证信息");
        }
        List<PaymentVoucherDetail> voucherDetailList = voucherResult.getPaymentVoucherDetailList();
        PaymentConfirmVoucher confirmVoucher = BeanUtil.copyProperties(voucherResult, PaymentConfirmVoucher.class);
        B2bMabangOrdersResult mabangOrdersResult = b2bMabangOrdersService.queryByPlatformOrderId(paymentDetail.getPlatformOrderId());
        VoucherInfo voucherInfo = new VoucherInfo(confirmVoucher, mabangOrdersResult, voucherDetailList);
        return this.requstK3Voucher(voucherInfo, paymentDetail);
    }

    @DataSource(name = "k3cloud")
    @Override
    public List<K3BankAccountInfo> queryK3BankInfoByOrgCode(String orgCode, String shopName) {
        List<K3BankAccountInfo> accountInfoList = new ArrayList<>();
        accountInfoList.addAll(baseMapper.queryK3BankInfoByOrgCode(orgCode));
        //获取店铺的平台收款信息
        accountInfoList.addAll(getPlatShopAccount(shopName));
        return accountInfoList;
    }

    private List<K3BankAccountInfo> getPlatShopAccount(String shopName) {
        List<K3BankAccountInfo> accountInfoList = new ArrayList<>();


        List<SysDict> dictInfoList = dictServiceConsumer.getDictListByTypeCode("PlatformDevelopERP");
        Long parentId = null;
        try {
            parentId = dictInfoList.stream().filter(dict -> dict.getDictCode().equals("ShopAccSubjectCode")).collect(Collectors.toList()).get(0).getDictId();
        } catch (Exception e) {
            throw new RuntimeException("请先配置平台发展ERP>>店铺账号收款-财务科目映射(ShopAccSubjectCode)");
        }

        Long finalParentId = parentId;
        List<SysDict> dictList = dictInfoList.stream()
                .filter(dict -> dict.getParentId().equals(finalParentId) && dict.getStatus() == 1 && shopName.equals(dict.getDictCode())).
                collect(Collectors.toList());

        if (ObjectUtil.isEmpty(dictList)) {
            return accountInfoList;
        }
        for (SysDict dic : dictList) {
            //银行信息 等于 财务科目信息
            K3BankAccountInfo accountInfo = new K3BankAccountInfo();
            accountInfo.setBankNo(dic.getDictShortCode());
            accountInfo.setBankName(dic.getDictName());
            accountInfo.setSubjectCode(dic.getDictShortCode());
            accountInfo.setSubjectName(dic.getDictName());
            accountInfoList.add(accountInfo);
        }
        return accountInfoList;
    }

    private static PaymentVoucherDetail getVoucherDetailBaseInfo(B2bPaymentDetail paymentDetail, BigDecimal voucherId, B2bMabangOrdersResult mabangOrdersResult, BigDecimal inRate) {
        PaymentVoucherDetail paymentVoucherDetail = new PaymentVoucherDetail();
        paymentVoucherDetail.setVoucherId(voucherId);
        paymentVoucherDetail.setCurrency(paymentDetail.getCurrency());
        String accountingDimensions;//1501/B2B_MK_ALL todo: 先传店铺名称 平台_账号_站点
        paymentVoucherDetail.setAccountingDimensions(mabangOrdersResult.getShopName());
        paymentVoucherDetail.setExchangeRate(inRate);
        paymentVoucherDetail.setExchangeRateType("固定汇率");
        String digest = "";//店铺+发票号码/阿里订单号
        digest = ObjectUtil.isNotEmpty(paymentDetail.getInvNumber()) ? paymentDetail.getInvNumber() : paymentDetail.getPlatformOrderId();
        digest = mabangOrdersResult.getShopName() + "/" + digest + "/" + paymentDetail.getAccountName();
        paymentVoucherDetail.setDigest(digest);
        return paymentVoucherDetail;
    }

    private static class VoucherInfo {
        public final PaymentConfirmVoucher confirmVoucher;
        public final B2bMabangOrdersResult mabangOrdersResult;
        public final List<PaymentVoucherDetail> paymentVoucherDetailList;

        public VoucherInfo(PaymentConfirmVoucher confirmVoucher, B2bMabangOrdersResult mabangOrdersResult, List<PaymentVoucherDetail> paymentVoucherDetailList) {
            this.confirmVoucher = confirmVoucher;
            this.mabangOrdersResult = mabangOrdersResult;
            this.paymentVoucherDetailList = paymentVoucherDetailList;
        }
    }

    /**
     * 组装生成付款凭证的k3请求参数
     *
     * @param voucherInfo
     * @return
     */
    private static JSONArray getRequestK3Parm(VoucherInfo voucherInfo) {

        PaymentConfirmVoucher voucher = voucherInfo.confirmVoucher;
        List<PaymentVoucherDetail> voucherDetailList = voucherInfo.paymentVoucherDetailList;
        B2bMabangOrdersResult mabangOrdersResult = voucherInfo.mabangOrdersResult;

        String period = voucher.getFiscalPeriod();
        String[] eriodArr = period.split("-");

        LoginContext current = SpringContext.getBean(LoginContext.class);
        LoginUser currentUser = current.getLoginUser();

        JSONArray Jarr = new JSONArray();//Data:[]

        JSONObject object = new JSONObject();
        object.put("FBillNo", "PTFZ-B2B-" + voucher.getFiscalPeriod() + "-" + voucher.getVoucherId().toString());//凭证编号 不传 返回
        object.put("FAccountBookID", "003");//销售中心
        object.put("FDate", voucher.getVoucherDate());
        object.put("FCreatorId", currentUser.getAccount());
        object.put("FVOUCHERGROUPID", "PRE001");//凭证字 记
        object.put("FATTACHMENTS", 0); //附件数
        object.put("FYEAR", eriodArr[0]);  //会计年度
        object.put("FACCBOOKORGID", "003");//销售中心
        object.put("FISADJUSTVOUCHER", false);
        object.put("FPERIOD", eriodArr[1]);//期间
//        object.put("FSourceBillKey", "78050206-2fa6-40e3-b7c8-bd608146fa38"); //业务类型 手工录入

        //FEntity:[]
        List<Map> detailList = new ArrayList<>();

        for (PaymentVoucherDetail detail : voucherDetailList) {
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("FEXPLANATION", detail.getDigest());
            objectMap.put("FACCOUNTID", detail.getSubjectCode());
            objectMap.put("FCURRENCYID", detail.getCurrency());
            objectMap.put("FEXCHANGERATETYPE", "HLTX01_SYS");
            objectMap.put("FEXCHANGERATE", detail.getExchangeRate());
            objectMap.put("FAMOUNTFOR", detail.getOriginalAmount());
            objectMap.put("FDEBIT", detail.getDebitAmount());
            objectMap.put("FCREDIT", detail.getCreditAmount());

            //FHSData:[]
            List<Map> mList = new ArrayList<>();
            Map map = new HashMap();

            if (detail.getSubjectCode() != null && "6603.001.01".equals(detail.getSubjectCode())) {
                map.put("HSType", "部门");
                map.put("HSNumber", "BM35");
            } else if (detail.getSubjectCode() != null && "1122.001".equals(detail.getSubjectCode())) {
                map.put("HSType", "组织机构");
                map.put("HSNumber", mabangOrdersResult.getFinanceCode());
            } else if (detail.getSubjectCode() != null && "2202.002".equals(detail.getSubjectCode())) {
                map.put("HSType", "组织机构");
                map.put("HSNumber", "002");
            }

            mList.add(map);
            objectMap.put("FHSData", mList);
            detailList.add(objectMap);
        }
        object.put("FEntity", detailList);
        Jarr.add(object);
        return Jarr;
    }

}