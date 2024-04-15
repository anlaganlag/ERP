package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tadpole.cloud.externalSystem.api.k3.model.params.results.SupplierQtyAmount;
import com.tadpole.cloud.product.api.productproposal.entity.*;
import com.tadpole.cloud.product.api.productproposal.model.params.RdRefRegistParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdRefRegistResult;
import com.tadpole.cloud.product.modular.consumer.ExternalConsumer;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.*;
import com.tadpole.cloud.product.modular.productproposal.service.IRdRefRegistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提案-退款登记 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Service
@Slf4j
public class RdRefRegistServiceImpl extends ServiceImpl<RdRefRegistMapper, RdRefRegist> implements IRdRefRegistService {

    @Resource
    private RdRefRegistMapper mapper;

    @Resource
    private RdSampleCfbMapper rdSampleCfbMapper;

    @Resource
    private RdSamplePaMapper rdSamplePaMapper;

    @Resource
    private RdSampleManageMapper rdSampleManageMapper;

    @Resource
    private RdSampleCaMapper rdSampleCaMapper;

    @Resource
    private RdMoldOpenPfaMapper rdMoldOpenPfaMapper;

    @Resource
    private RdSampleTaskMapper rdSampleTaskMapper;

    @Resource
    private ExternalConsumer externalConsumer;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData add(RdRefRegistParam param) {
        log.info("退款记录保存/提交--------开始----->传入参数【" + JSONUtil.toJsonStr(param) + "】");
        RdRefRegist rdRefRegist = BeanUtil.copyProperties(param, RdRefRegist.class);
        if (this.mapper.insert(rdRefRegist) > 0) {
            log.info("退款记录保存/提交--------结束----->【成功】");
            return ResponseData.success();
        } else {
            log.info("退款记录保存/提交--------结束----->【失败】");
            return ResponseData.error("");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdRefRegistResult> listRefRegist(RdRefRegistParam param) {
        return this.mapper.listRefRegist(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdRefRegistResult> listPage(RdRefRegistParam param) {
        return this.mapper.listPage(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData statisticsRefRegist(RdRefRegistParam param) {

        //List<RdRefRegistResult> rdRefRegistResults = this.mapper.listPageStatisticsRefRegist();

        List<RdRefRegist> rdRefRegistResults = this.mapper.selectList(null);

        Map result = new HashMap();
        result.put("waitCmCount", rdRefRegistResults.stream().filter(l -> ObjectUtil.isNotNull(l.getSysRefAppStatus()) && l.getSysRefAppStatus().equals(RdProposalEnum.REF_APP_STATUS_CM.getName())).count());
        result.put("waitTbsaCount", rdRefRegistResults.stream().filter(l -> ObjectUtil.isNotNull(l.getSysRefAppStatus()) && (l.getSysRefAppStatus().equals(RdProposalEnum.REF_APP_STATUS_TBSA.getName()))).count());
        result.put("waitTuvCount", rdRefRegistResults.stream().filter(l -> ObjectUtil.isNotNull(l.getSysRefAppStatus()) && l.getSysRefAppStatus().equals(RdProposalEnum.REF_APP_STATUS_TUV.getName())).count());
        result.put("waitTbpCount", rdRefRegistResults.stream().filter(l -> ObjectUtil.isNotNull(l.getSysRefAppStatus()) && (l.getSysRefAppStatus().equals(RdProposalEnum.REF_APP_STATUS_TBP.getName()) || l.getSysRefAppStatus().equals(RdProposalEnum.REF_APP_STATUS_PCV.getName()))).count());
        result.put("waitPcvCount", rdRefRegistResults.stream().filter(l -> ObjectUtil.isNotNull(l.getSysRefAppStatus()) && l.getSysRefAppStatus().equals(RdProposalEnum.REF_APP_STATUS_PCV.getName())).count());
        result.put("CnmCount", rdRefRegistResults.stream().filter(l -> ObjectUtil.isNotNull(l.getSysRefAppStatus()) && l.getSysRefAppStatus().equals(RdProposalEnum.REF_APP_STATUS_CNM.getName())).count());

        return ResponseData.success(result);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdRefRegistResult> listPageDetail(RdRefRegistParam param) {

        List<RdRefRegistResult> rdRefRegistResults = this.mapper.listPageDetail(param);

        return rdRefRegistResults;
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdRefRegistResult detail(RdRefRegistParam param) {

        RdRefRegist refRegist = this.mapper.selectById(param.getSysRefAppCode());
        RdRefRegistResult refRegistResult = BeanUtil.copyProperties(refRegist, RdRefRegistResult.class);

        RdSampleTask rdSampleTask = rdSampleTaskMapper.selectById(refRegistResult.getSysTsTaskCode());
        refRegistResult.setSysTsTaskName(rdSampleTask.getSysTsTaskName());
        QueryWrapper<RdSampleManage> manageQueryWrapper = new QueryWrapper<>();
        if (refRegistResult.getSysRefFeeType().equals(RdProposalEnum.REF_FEE_TYPE_GYF.getName())) {
            RdSamplePa rdSamplePa = rdSamplePaMapper.selectById(refRegistResult.getSysFeeAppCode());

            manageQueryWrapper.eq("SYS_KFY_FEE_CODE", rdSamplePa.getSysFeeAppCode()).eq("SYS_KFY_FEE_SOURCE", "购样申请");

            refRegistResult.setSysColAccountType(rdSamplePa.getSysFeeAppSupplierAt());
            refRegistResult.setSysColPayMethod(rdSamplePa.getSysFeeAppSupplierPm());

            if (rdSamplePa.getSysFeeAppSupplierPm().equals("支付宝账号")) {
                refRegistResult.setSysColAccount(rdSamplePa.getSysFeeAppSupplierAa());
                refRegistResult.setSysColAccountName("-");
                refRegistResult.setSysColBankName("-");
            } else {
                refRegistResult.setSysColAccount(rdSamplePa.getSysFeeAppSupplierBau());
                refRegistResult.setSysColAccountName(rdSamplePa.getSysFeeAppSupplierAn());
                refRegistResult.setSysColBankName(rdSamplePa.getSysFeeAppSupplierOb());
            }

            refRegistResult.setSysPayDate(rdSamplePa.getSysFeeAppPayDate());
            refRegistResult.setSysPayType("支付宝账号");
            refRegistResult.setSysPayAccount(rdSamplePa.getSysFeeAppAlipayAccount());
            refRegistResult.setSysPayAn(rdSamplePa.getSysFeeAppAlipayAn());
            refRegistResult.setSysPayAob("-");

            refRegistResult.setSysAppDate(rdSamplePa.getSysFeeAppSubDate());
        } else if (refRegistResult.getSysRefFeeType().equals(RdProposalEnum.REF_FEE_TYPE_DYF.getName())) {

            RdSampleCa rdSampleCa = rdSampleCaMapper.selectById(refRegistResult.getSysFeeAppCode());
            RdSampleCfb rdSampleCfb = rdSampleCfbMapper.selectById(rdSampleCa.getSysCustFebkCode());
            manageQueryWrapper.eq("SYS_KFY_FEE_CODE", rdSampleCa.getSysFeeAppCode()).eq("SYS_KFY_FEE_SOURCE", "定制申请");

            refRegistResult.setSysColAccountType(rdSampleCfb.getSysCfAccountType());
            refRegistResult.setSysColPayMethod(rdSampleCfb.getSysCfPayMethod());
            if (rdSampleCfb.getSysCfPayMethod().equals("支付宝账号")) {
                refRegistResult.setSysColAccount(rdSampleCfb.getSysCfAlipayAccount());
                refRegistResult.setSysColAccountName("-");
                refRegistResult.setSysColBankName("-");
            } else {
                refRegistResult.setSysColAccount(rdSampleCfb.getSysCfBankAccount());
                refRegistResult.setSysColAccountName(rdSampleCfb.getSysCfAccountName());
                refRegistResult.setSysColBankName(rdSampleCfb.getSysCfBankName());
            }

            refRegistResult.setSysPayDate(rdSampleCa.getSysSaPayDate());
            refRegistResult.setSysPayType("支付宝账号");
            refRegistResult.setSysPayAccount(rdSampleCa.getSysSaAlipayAccount());
            refRegistResult.setSysPayAn(rdSampleCa.getSysSaAlipayAn());
            refRegistResult.setSysPayAob("-");

            refRegistResult.setSysAppDate(rdSampleCa.getSysSaApDate());
            refRegistResult.setSysCfMoldOpenFee(rdSampleCfb.getSysCfMoldOpenFee());
            refRegistResult.setSysCfSampleFee(rdSampleCfb.getSysCfSampleFee());
        } else {
            RdSampleCa rdSampleCa = rdSampleCaMapper.selectById(refRegistResult.getSysFeeAppCode());
            RdSampleCfb rdSampleCfb = rdSampleCfbMapper.selectById(rdSampleCa.getSysCustFebkCode());
            manageQueryWrapper.eq("SYS_KFY_FEE_CODE", rdSampleCa.getSysFeeAppCode()).eq("SYS_KFY_FEE_SOURCE", "定制申请");

            QueryWrapper<RdMoldOpenPfa> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_FEE_APP_CODE", rdSampleCa.getSysFeeAppCode());
            RdMoldOpenPfa rdMoldOpenPfa = rdMoldOpenPfaMapper.selectOne(queryWrapper);

            refRegistResult.setSysColAccountType(rdSampleCfb.getSysCfAccountType());
            refRegistResult.setSysColPayMethod(rdSampleCfb.getSysCfPayMethod());
            if (rdSampleCfb.getSysCfPayMethod().equals("支付宝账号")) {
                refRegistResult.setSysColAccount(rdSampleCfb.getSysCfAlipayAccount());
                refRegistResult.setSysColAccountName("-");
                refRegistResult.setSysColBankName("-");
            } else {
                refRegistResult.setSysColAccount(rdSampleCfb.getSysCfBankAccount());
                refRegistResult.setSysColAccountName(rdSampleCfb.getSysCfAccountName());
                refRegistResult.setSysColBankName(rdSampleCfb.getSysCfBankName());
            }

            refRegistResult.setSysPayDate(rdMoldOpenPfa.getSysMofPayDate());
            refRegistResult.setSysPayType("银行卡");
            refRegistResult.setSysPayAccount(rdMoldOpenPfa.getSysMofPayAccount());
            refRegistResult.setSysPayAn(rdMoldOpenPfa.getSysMofPayAn());
            refRegistResult.setSysPayAob(rdMoldOpenPfa.getSysMofPayAob());

            refRegistResult.setSysAppDate(rdSampleCa.getSysSaApDate());
            refRegistResult.setSysCfMoldOpenFee(rdSampleCfb.getSysCfMoldOpenFee());
            refRegistResult.setSysCfSampleFee(rdSampleCfb.getSysCfSampleFee());
        }

        List<RdSampleManage> rdSampleManageList = rdSampleManageMapper.selectList(manageQueryWrapper);
        refRegistResult.setRdSampleManageList(rdSampleManageList);

        return refRegistResult;
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData submit(RdRefRegistParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            UpdateWrapper<RdRefRegist> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_REF_APP_CODE", param.getSysRefAppCode());

            if (param.getSysRefActualRefResult().equals(RdProposalEnum.REF_ACTUAL_REF_RESULT_DR.getName())) {

                if (param.getSysRefFeeType().equals(RdProposalEnum.REF_FEE_TYPE_KMF.getName())) {
                    updateWrapper.set("SYS_REF_ACTUAL_REF_RESULT", param.getSysRefActualRefResult()).set("SYS_REF_FEB_DATE", oprDate)
                            .set("SYS_REF_ACCOUNT_TYPE", param.getSysRefAccountType()).set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_TBSA.getName());
                } else {
                    updateWrapper.set("SYS_REF_ACTUAL_REF_RESULT", param.getSysRefActualRefResult()).set("SYS_REF_FEB_DATE", oprDate)
                            .set("SYS_REF_APP_AMOUNT", param.getSysRefAppAmount()).set("SYS_REF_APP_EXPLAIN", ObjectUtil.isNotNull(param.getSysRefAppExplain()) ? param.getSysRefAppExplain() : "")
                            .set("SYS_REF_PAYER", param.getSysRefPayer()).set("SYS_REF_PAY_DATE", param.getSysRefPayDate())
                            .set("SYS_REF_VOUCHER", param.getSysRefVoucher()).set("SYS_REF_UP_VOUCHER_DATE", oprDate)
                            .set("SYS_REF_UP_VOUCHER_PC", loginUser.getAccount()).set("SYS_REF_UP_VOUCHER_PN", loginUser.getName())
                            .set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_PRC.getName());
                }

            } else if (param.getSysRefActualRefResult().equals(RdProposalEnum.REF_ACTUAL_REF_RESULT_OD.getName())) {
                updateWrapper.set("SYS_REF_ACTUAL_REF_RESULT", param.getSysRefActualRefResult()).set("SYS_REF_FEB_DATE", oprDate)
                        .set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_TUV.getName());
            } else {
                updateWrapper.set("SYS_REF_ACTUAL_REF_RESULT", param.getSysRefActualRefResult()).set("SYS_REF_INVALID_EXPLAIN", param.getSysRefInvalidExplain()).set("SYS_REF_FEB_DATE", oprDate)
                        .set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_RI.getName());
            }

            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("退款反馈成功");
            } else {
                return ResponseData.error("退款反馈失败");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }


    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData designatedAccount(RdRefRegistParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            UpdateWrapper<RdRefRegist> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_REF_APP_CODE", param.getSysRefAppCode());

            if (param.getSysRefAccountType().equals(RdProposalEnum.REF_ACCOUNT_TYPE_PA.getName())) {
                updateWrapper.set("SYS_REF_APP_ACCOUNT", param.getSysRefAppAccount()).set("SYS_REF_APP_AN", param.getSysRefAppAn())
                        .set("SYS_REF_APP_AOB", param.getSysRefAppAob());
            } else {
                updateWrapper.set("SYS_REF_APP_ACCOUNT", param.getSysRefAppAccount()).set("SYS_REF_APP_AN", param.getSysRefAppAn())
                        .set("SYS_REF_APP_AOB", param.getSysRefAppAob()).set("SYS_REF_APP_COMP", param.getSysRefAppComp()).set("SYS_REF_APP_AP", "银行卡");
            }
            updateWrapper.set("SYS_REF_APP_R_PC", loginUser.getAccount()).set("SYS_REF_APP_R_PN", loginUser.getName())
                    .set("SYS_REF_APP_RD", oprDate).set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_TUV.getName());

            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("指定账户成功");
            } else {
                return ResponseData.error("指定账户失败");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData uploadCredentials(RdRefRegistParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            UpdateWrapper<RdRefRegist> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_REF_APP_CODE", param.getSysRefAppCode());

            updateWrapper.set("SYS_REF_APP_AMOUNT", param.getSysRefAppAmount()).set("SYS_REF_APP_EXPLAIN", ObjectUtil.isNotNull(param.getSysRefAppExplain()) ? param.getSysRefAppExplain() : "")
                    .set("SYS_REF_PAYER", param.getSysRefPayer()).set("SYS_REF_PAY_DATE", param.getSysRefPayDate())
                    .set("SYS_REF_VOUCHER", param.getSysRefVoucher()).set("SYS_REF_UP_VOUCHER_DATE", oprDate)
                    .set("SYS_REF_UP_VOUCHER_PC", loginUser.getAccount()).set("SYS_REF_UP_VOUCHER_PN", loginUser.getName())
                    .set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_TBP.getName());

            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("上传凭证成功");
            } else {
                return ResponseData.error("上传凭证失败");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData print(RdRefRegistParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            UpdateWrapper<RdRefRegist> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_REF_APP_CODE", param.getSysRefAppCode());

            updateWrapper.set("SYS_REF_APP_FIRST_PD", oprDate)
                    .set("SYS_REF_APP_FIRST_PPC", loginUser.getAccount()).set("SYS_REF_APP_PPN", loginUser.getName())
                    .set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_PCV.getName());

            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("打印成功");
            } else {
                return ResponseData.error("打印失败");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData capitalVerification(RdRefRegistParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            UpdateWrapper<RdRefRegist> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_REF_APP_CODE", param.getSysRefAppCode());

            updateWrapper.set("SYS_REF_APP_ED", oprDate)
                    .set("SYS_REF_APP_EPC", loginUser.getAccount()).set("SYS_REF_APP_EPN", loginUser.getName())
                    .set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_RC.getName());

            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("验资成功");
            } else {
                return ResponseData.error("验资失败");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

    @DataSource(name = "product")
    @Override
    public void determineIfRefundIsPossible() {

        QueryWrapper<RdRefRegist> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_CNM.getName());

        List<RdRefRegist> rdRefRegists = this.mapper.selectList(queryWrapper);

        for (RdRefRegist regist : rdRefRegists) {

            SupplierQtyAmount supplierQtyAmount = externalConsumer.getSupplierQtyAmount(regist.getSysRefSupplierCode());

            if (supplierQtyAmount != null) {
                //首单退款   ||  订单量退款  ||   订单金额退款
                if ((RdProposalEnum.REF_TYPE_FIRST_ORDER.getName().equals(regist.getSysRefType())
                        && supplierQtyAmount.getFamount().compareTo(BigDecimal.ZERO) > 0)
                     ||   (RdProposalEnum.REF_TYPE_ORDER_QTY.getName().equals(regist.getSysRefType())
                        && supplierQtyAmount.getFqty().compareTo(regist.getSysRefCondition()) >= 0)
                     ||   (RdProposalEnum.REF_TYPE_ORDER_AMOUNT.getName().equals(regist.getSysRefType())
                        && supplierQtyAmount.getFamount().compareTo(regist.getSysRefCondition()) >= 0)
                ) {
                    UpdateWrapper<RdRefRegist> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_REF_SUPPLIER_CODE", regist.getSysRefSupplierCode());
                    updateWrapper.set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_CM.getName())
                            .set("SYS_ORDER_QTY", supplierQtyAmount.getFqty())
                            .set("SYS_ORDER_AMOUNT", supplierQtyAmount.getFamount());
                    this.mapper.update(null, updateWrapper);
                }
            }
        }
    }
}
