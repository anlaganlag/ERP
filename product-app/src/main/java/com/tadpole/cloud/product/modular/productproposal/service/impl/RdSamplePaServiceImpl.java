package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountFlowParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import com.tadpole.cloud.product.api.productproposal.entity.*;
import com.tadpole.cloud.product.api.productproposal.model.params.*;
import com.tadpole.cloud.product.api.productproposal.model.result.RdRefRegistResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSamplePaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleRprResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.consumer.ProposalServiceConsumer;
import com.tadpole.cloud.product.modular.consumer.RdPreProposalServiceConsumer;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.*;
import com.tadpole.cloud.product.modular.productproposal.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 提案-购样申请 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Service
@Slf4j
public class RdSamplePaServiceImpl extends ServiceImpl<RdSamplePaMapper, RdSamplePa> implements IRdSamplePaService {

    @Resource
    private RdSamplePaMapper mapper;
    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private IRdFarSettingService farSettingService;

    @Resource
    private RdPreProposalServiceConsumer oaServiceConsumer;

    @Resource
    private IRdSampleTaskService sampleTaskService;

    @Resource
    private IRdRefRegistService rdRefRegistService;

    @Resource
    private IRdSampleRprService sampleRprService;

    @Resource
    private ProposalServiceConsumer proposalServiceConsumer;

    @Resource
    private RdRefRegistMapper refRegistMapper;

    @Resource
    private RdSampleTaskMapper rdSampleTaskMapper;

    @Resource
    private RdSampleRprMapper rdSampleRprMapper;

    @Resource
    private RdSampleManageMapper rdSampleManageMapper;

    @Resource
    private RdProposalMapper rdProposalMapper;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData addOrEdit(RdSamplePaParam param) {
        try {

            log.info("购样申请保存/提交--------开始-----> 传入参数【" + JSONUtil.toJsonStr(param) + "】");

            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            boolean isAutoApprove = false;

            HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();

            RdSamplePa rdSamplePa = BeanUtil.copyProperties(param, RdSamplePa.class);
            rdSamplePa.setSysCDate(oprDate);
            rdSamplePa.setSysLDate(oprDate);
            rdSamplePa.setSysDeptCode(hrm.getIdall());
            rdSamplePa.setSysDeptName(hrm.getDepartmentname());
            rdSamplePa.setSysPerCode(loginUser.getAccount());
            rdSamplePa.setSysPerName(loginUser.getName());

            if (param.getSysPageOpr().equals(RdProposalEnum.PA_PAGE_NEW.getName())) {
                rdSamplePa.setSysFeeAppCode(generatorNoUtil.getSpaBillNoExtents("0000", "FeeApp", 4));
            }

            if (param.getSysFuncOpr().equals(RdProposalEnum.PA_FUNC_APP.getName())) {
                rdSamplePa.setSysFeeAppSubDate(oprDate);
                rdSamplePa.setSysFeeAppPurPerCode(loginUser.getAccount());
                rdSamplePa.setSysFeeAppPurPerName(loginUser.getName());
                rdSamplePa.setSysFeeAppStatus(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_APP.getName());

                //自动过审设置
                List<RdFarSetting> rdFarSettings = farSettingService.listPage();
                RdFarSetting farSettingReview = rdFarSettings.stream().filter(l -> l.getSysSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_SPOT_SAMPLE.getName()) && l.getSysAuditProcess().equals("审核")).findFirst().get();
                RdFarSetting farSettingAppr = rdFarSettings.stream().filter(l -> l.getSysSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_SPOT_SAMPLE.getName()) && l.getSysAuditProcess().equals("审批")).findFirst().get();

                //当前提案发布的任务（现货拿样）
                RdSampleTaskParam sampleTaskParam = new RdSampleTaskParam();
                sampleTaskParam.setSysTaCode(param.getSysTaCode());
                sampleTaskParam.setSysTsSampleMethod(RdProposalEnum.SAMPLE_METHOD_SPOT_SAMPLE.getName());
                List<RdSampleTaskExtentResult> sampleTasks = sampleTaskService.listPage(sampleTaskParam);

                //当前提案发布的所有现货拿样费用合计
                QueryWrapper<RdSamplePa> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
                queryWrapper.in("SYS_TS_TASK_CODE", sampleTasks.stream().map(RdSampleTaskExtentResult::getSysTsTaskCode).collect(Collectors.toList()));
                List<RdSamplePa> rdSamplePas = this.mapper.selectList(queryWrapper);

                BigDecimal totalFee = rdSamplePas.stream().map(RdSamplePa::getSysFeeAppTotalFee).reduce(BigDecimal.ZERO, BigDecimal::add);

                //审核
                if (param.getSysFeeAppTotalFee().compareTo(farSettingReview.getSysSingleAppAmount()) <= 0 &&
                        totalFee.compareTo(farSettingReview.getSysAppTotalAmount()) <= 0 &&
                        BigDecimal.valueOf(sampleTasks.size()).compareTo(farSettingReview.getSysTaskQty()) <= 0) {
                    rdSamplePa.setSysFeeAppAuditResult(RdProposalEnum.PA_FEE_APP_AUDIT_RESULT_YES.getName());
                    rdSamplePa.setSysFeeAppAuditDate(oprDate);
                    rdSamplePa.setSysFeeAppAuditPerCode(RdProposalEnum.TA_SYS_DEFAULT_CODE.getName());
                    rdSamplePa.setSysFeeAppAuditPerName(RdProposalEnum.TA_SYS_DEFAULT_NAME.getName());
                    rdSamplePa.setSysFeeAppStatus(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_APPR.getName());
                }

                //审批
                if (param.getSysFeeAppTotalFee().compareTo(farSettingAppr.getSysSingleAppAmount()) <= 0 &&
                        totalFee.compareTo(farSettingAppr.getSysAppTotalAmount()) <= 0 &&
                        BigDecimal.valueOf(sampleTasks.size()).compareTo(farSettingAppr.getSysTaskQty()) <= 0) {
                    rdSamplePa.setSysFeeAppAppResult(RdProposalEnum.PA_FEE_APP_APP_RESULT_YES.getName());
                    rdSamplePa.setSysFeeAppAppDate(oprDate);
                    rdSamplePa.setSysFeeAppAppPerCode(RdProposalEnum.TA_SYS_DEFAULT_CODE.getName());
                    rdSamplePa.setSysFeeAppAppPerName(RdProposalEnum.TA_SYS_DEFAULT_NAME.getName());
                    rdSamplePa.setSysFeeAppStatus(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_SUPPLY.getName());

                    if (ObjectUtil.isNotNull(param.getSysFeeAppOrderNum()) && ObjectUtil.isNotNull(param.getSysFeeAppOrderPic())) {
                        rdSamplePa.setSysFeeAppStatus(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_PAY.getName());
                    }

                    if (param.getSysFeeAppSc().equals(RdProposalEnum.KFY_SC_SUPPLIER.getName()) &&
                            ObjectUtil.isNotNull(param.getSysFeeAppSupplierIr()) && param.getSysFeeAppSupplierIr().equals(RdProposalEnum.PA_FEE_APP_SUPPLIER_IR_YES.getName())) {
                        isAutoApprove = true;
                    }
                }

            } else {
                rdSamplePa.setSysFeeAppStatus(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_SUBMIT.getName());
            }

            if (isAutoApprove) {
                RdRefRegistParam rdRefRegistParam = new RdRefRegistParam();
                rdRefRegistParam.setSysCDate(oprDate);
                rdRefRegistParam.setSysLDate(oprDate);
                rdRefRegistParam.setSysDeptCode(hrm.getIdall());
                rdRefRegistParam.setSysDeptName(hrm.getDepartmentname());
                rdRefRegistParam.setSysPerCode(loginUser.getAccount());
                rdRefRegistParam.setSysPerName(loginUser.getName());
                rdRefRegistParam.setSysRefAppCode(generatorNoUtil.getRefBillNoExtents("0000", "Ref-Sq", 4));
                rdRefRegistParam.setSysRefAppStatus(RdProposalEnum.REF_APP_STATUS_CNM.getName());
                rdRefRegistParam.setSysPlCode(param.getSysPlCode());
                rdRefRegistParam.setSysSpu(param.getSysSpu());
                rdRefRegistParam.setSysTaCode(param.getSysTaCode());
                rdRefRegistParam.setSysTsTaskCode(param.getSysTsTaskCode());
                rdRefRegistParam.setSysFeeAppCode(rdSamplePa.getSysFeeAppCode());
                rdRefRegistParam.setSysFeeAppSource(RdProposalEnum.FEE_APP_SOURCE_GYSQ.getName());
                rdRefRegistParam.setSysRefFeeType(RdProposalEnum.REF_FEE_TYPE_GYF.getName());
                rdRefRegistParam.setSysRefFees(param.getSysFeeAppTotalFee());
                rdRefRegistParam.setSysRefSupplierCode(param.getSysFeeAppSupplierNum());
                rdRefRegistParam.setSysRefSupplierName(param.getSysFeeAppSupplierName());
                rdRefRegistParam.setSysRefType(param.getSysFeeAppSupplierRm());
                rdRefRegistParam.setSysRefCondition(param.getSysFeeAppSupplierRc());
                rdRefRegistParam.setSysRefPurPerCode(rdSamplePa.getSysFeeAppPurPerCode());
                rdRefRegistParam.setSysRefPurPerName(rdSamplePa.getSysFeeAppPurPerName());

                ResponseData responseData = this.rdRefRegistService.add(rdRefRegistParam);
                if (responseData.getSuccess() && responseData.getCode().equals(200)) {
                    if (this.saveOrUpdate(rdSamplePa)) {
                        log.info("购样申请保存/提交--------结束----->【成功】");
                        return ResponseData.success("购样申请保存/提交成功");
                    } else {
                        log.info("购样申请保存/提交--------结束----->【失败】");
                        return ResponseData.error("购样申请保存/提交失败");
                    }
                } else {
                    log.info("购样申请保存/提交--------结束----->【失败】");
                    return ResponseData.error("购样申请保存/提交失败");
                }
            } else {
                if (this.saveOrUpdate(rdSamplePa)) {
                    log.info("购样申请保存/提交--------结束----->【成功】");
                    return ResponseData.success("购样申请保存/提交成功");
                } else {
                    log.info("购样申请保存/提交--------结束----->【失败】");
                    return ResponseData.error("购样申请保存/提交失败");
                }
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdSamplePaResult> listSamplePa(RdSamplePaParam param) {
        return this.mapper.listSamplePa(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData checkIsCanCreateSamplePa(RdSamplePaParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();

        QueryWrapper<RdSamplePa> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());
        queryWrapper.eq("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_WAIT_SUBMIT.getName());
        queryWrapper.eq("SYS_PER_CODE", loginUser.getAccount());

        if (this.mapper.selectCount(queryWrapper) >= 1) {
            return ResponseData.error("当前操作人在系统里已存在一条待提交的购样申请,不能再进行购样申请登记.");
        } else {
            return ResponseData.success();
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData revokeSamplePurchaseApp(RdSamplePaParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Date oprDate = new Date();
        UpdateWrapper<RdSamplePa> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
        updateWrapper.set("SYS_FEE_APP_REVOKE_DATE", oprDate).set("SYS_FEE_APP_REVOKE_REASON", param.getSysFeeAppRevokeReason())
                .set("SYS_FEE_APP_REVOKE_PER_CODE", loginUser.getAccount()).set("SYS_FEE_APP_REVOKE_PER_NAME", loginUser.getName())
                .set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_CANCEL.getName());

        if (this.mapper.update(null, updateWrapper) > 0) {
            return ResponseData.success("购样申请撤销成功.");
        } else {
            return ResponseData.success("购样申请撤销失败.");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData paySupply(RdSamplePaParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Date oprDate = new Date();
        UpdateWrapper<RdSamplePa> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
        updateWrapper.set("SYS_FEE_APP_ORDER_UD", oprDate).set("SYS_FEE_APP_ORDER_NUM", param.getSysFeeAppOrderNum())
                .set("SYS_FEE_APP_ORDER_UPC", loginUser.getAccount()).set("SYS_FEE_APP_ORDER_UPN", loginUser.getName())
                .set("SYS_FEE_APP_ORDER_PIC", param.getSysFeeAppOrderPic());

        if (param.getSysFuncOpr().equals(RdProposalEnum.PA_FUNC_SUBMIT.getName())) {
            updateWrapper.set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_WAIT_PAY.getName());

            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("购样申请支付补充提交成功.");
            } else {
                return ResponseData.success("购样申请支付补充提交失败.");
            }
        } else {
            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("购样申请支付补充保存成功.");
            } else {
                return ResponseData.success("购样申请支付补充保存失败.");
            }
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleTaskExtentResult> listPageReview(RdSampleTaskParam param) {
        return this.mapper.listPageReview(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData reviewSamplePurchaseApp(List<RdSamplePaParam> params) {
        try {
            List<RdSamplePaParam> waitParams = params.stream().filter(l -> l.getSysFeeAppStatus().equals(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_APP.getName())).collect(Collectors.toList());
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            for (RdSamplePaParam param : waitParams) {
                UpdateWrapper<RdSamplePa> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
                updateWrapper.set("SYS_FEE_APP_AUDIT_RESULT", param.getSysFeeAppAuditResult()).set("SYS_FEE_APP_AUDIT_DATE", oprDate)
                        .set("SYS_FEE_APP_AUDIT_PER_CODE", loginUser.getAccount()).set("SYS_FEE_APP_AUDIT_PER_NAME", loginUser.getName());

                if (param.getSysFeeAppAuditResult().equals(RdProposalEnum.PA_FEE_APP_AUDIT_RESULT_YES.getName())) {
                    updateWrapper.set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_WAIT_APPR.getName());
                } else if (param.getSysFeeAppAuditResult().equals(RdProposalEnum.PA_FEE_APP_AUDIT_RESULT_NO.getName())) {
                    updateWrapper.set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_ARCH.getName()).set("SYS_FEE_APP_AUDIT_REMARKS", ObjectUtil.isNotNull(param.getSysFeeAppAuditRemarks()) ? param.getSysFeeAppAuditRemarks() : "");
                } else {
                    updateWrapper.set("SYS_FEE_APP_AUDIT_REMARKS", ObjectUtil.isNotNull(param.getSysFeeAppAuditRemarks()) ? param.getSysFeeAppAuditRemarks() : "");
                }
                this.mapper.update(null, updateWrapper);
            }

            return ResponseData.success("购样申请审核完成.");
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdSampleTaskExtentResult> listPageApprove(RdSampleTaskParam param) {
        return this.mapper.listPageApprove(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData approveSamplePurchaseApp(List<RdSamplePaParam> params) {
        try {
            List<RdSamplePaParam> waitParams = params.stream().filter(l -> l.getSysFeeAppStatus().equals(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_APPR.getName())).collect(Collectors.toList());
            LoginUser loginUser = LoginContext.me().getLoginUser();
            HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();

            Date oprDate = new Date();
            for (RdSamplePaParam param : waitParams) {
                UpdateWrapper<RdSamplePa> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
                updateWrapper.set("SYS_FEE_APP_APP_RESULT", param.getSysFeeAppAppResult()).set("SYS_FEE_APP_APP_DATE", oprDate)
                        .set("SYS_FEE_APP_APP_PER_CODE", loginUser.getAccount()).set("SYS_FEE_APP_APP_PER_NAME", loginUser.getName());
                if (param.getSysFeeAppAppResult().equals(RdProposalEnum.PA_FEE_APP_APP_RESULT_YES.getName())) {

                    if (param.getSysFeeAppSc().equals(RdProposalEnum.KFY_SC_SUPPLIER.getName())) {
                        if (param.getSysFeeAppSupplierIr().equals(RdProposalEnum.PA_FEE_APP_SUPPLIER_IR_YES.getName())) {
                            RdRefRegistParam rdRefRegistParam = new RdRefRegistParam();
                            rdRefRegistParam.setSysCDate(oprDate);
                            rdRefRegistParam.setSysLDate(oprDate);
                            rdRefRegistParam.setSysDeptCode(hrm.getIdall());
                            rdRefRegistParam.setSysDeptName(hrm.getDepartmentname());
                            rdRefRegistParam.setSysPerCode(loginUser.getAccount());
                            rdRefRegistParam.setSysPerName(loginUser.getName());
                            rdRefRegistParam.setSysRefAppCode(generatorNoUtil.getRefBillNoExtents("0000", "Ref-Sq", 4));
                            rdRefRegistParam.setSysRefAppStatus(RdProposalEnum.REF_APP_STATUS_CNM.getName());
                            rdRefRegistParam.setSysPlCode(param.getSysPlCode());
                            rdRefRegistParam.setSysSpu(param.getSysSpu());
                            rdRefRegistParam.setSysTaCode(param.getSysTaCode());
                            rdRefRegistParam.setSysTsTaskCode(param.getSysTsTaskCode());
                            rdRefRegistParam.setSysFeeAppCode(param.getSysFeeAppCode());
                            rdRefRegistParam.setSysFeeAppSource(RdProposalEnum.FEE_APP_SOURCE_GYSQ.getName());
                            rdRefRegistParam.setSysRefFeeType(RdProposalEnum.REF_FEE_TYPE_GYF.getName());
                            rdRefRegistParam.setSysRefFees(param.getSysFeeAppTotalFee());
                            rdRefRegistParam.setSysRefSupplierCode(param.getSysFeeAppSupplierNum());
                            rdRefRegistParam.setSysRefSupplierName(param.getSysFeeAppSupplierName());
                            rdRefRegistParam.setSysRefType(param.getSysFeeAppSupplierRm());
                            rdRefRegistParam.setSysRefCondition(param.getSysFeeAppSupplierRc());
                            rdRefRegistParam.setSysRefPurPerCode(param.getSysFeeAppPurPerCode());
                            rdRefRegistParam.setSysRefPurPerName(param.getSysFeeAppPurPerName());

                            this.rdRefRegistService.add(rdRefRegistParam);
                        }
                        updateWrapper.set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_WAIT_PAY.getName());
                    } else {
                        /*if (ObjectUtil.isNotNull(param.getSysFeeAppOrderNum()) && ObjectUtil.isNotNull(param.getSysFeeAppOrderPic())) {
                            updateWrapper.set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_WAIT_PAY.getName());
                        }else {
                            updateWrapper.set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_WAIT_SUPPLY.getName());
                        }*/

                        updateWrapper.set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_WAIT_SUPPLY.getName());
                    }
                } else if (param.getSysFeeAppAppResult().equals(RdProposalEnum.PA_FEE_APP_APP_RESULT_NO.getName())) {
                    updateWrapper.set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_ARCH.getName()).set("SYS_FEE_APP_APP_REMARKS", ObjectUtil.isNotNull(param.getSysFeeAppAppRemarks()) ? param.getSysFeeAppAppRemarks() : "");
                } else {
                    updateWrapper.set("SYS_FEE_APP_APP_REMARKS", ObjectUtil.isNotNull(param.getSysFeeAppAppRemarks()) ? param.getSysFeeAppAppRemarks() : "");
                }
                this.mapper.update(null, updateWrapper);
            }

            return ResponseData.success("购样申请审批完成.");
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdSamplePaResult detail(RdSamplePaParam param) {
        RdSamplePaResult rdSamplePaResult = this.mapper.detail(param);
        if (!rdSamplePaResult.getSysFeeAppSc().equals(RdProposalEnum.KFY_SC_SUPPLIER.getName())) {
            RdSampleRprParam rprParam = new RdSampleRprParam();
            rprParam.setSysFeeAppCode(rdSamplePaResult.getSysFeeAppCode());
            rprParam.setSysFeeAppSource(RdProposalEnum.FEE_APP_SOURCE_GYSQ.getName());

            List<RdSampleRprResult> rdSampleRprResults = this.sampleRprService.listSampleRpr(rprParam);
            if (rdSampleRprResults.size() > 0) {
                RdSampleRprResult rdSampleRprResult = rdSampleRprResults.stream().findFirst().get();
                if (ObjectUtil.isNotNull(rdSampleRprResult)) {
                    rdSamplePaResult.setSysRefDate(rdSampleRprResult.getSysRefDate());
                    rdSamplePaResult.setSysRefPerCode(rdSampleRprResult.getSysRefRgPerCode());
                    rdSamplePaResult.setSysRefPerName(rdSampleRprResult.getSysRefRgPerName());
                    rdSamplePaResult.setSysRefRealAmount(rdSampleRprResult.getSysRefRealAmount());
                }
            }
        } else {
            RdRefRegistParam refRegistParam = new RdRefRegistParam();
            refRegistParam.setSysFeeAppCode(param.getSysFeeAppCode());
            refRegistParam.setSysFeeAppSource(RdProposalEnum.FEE_APP_SOURCE_GYSQ.getName());

            List<RdRefRegistResult> rdRefRegistResults = this.rdRefRegistService.listRefRegist(refRegistParam);
            if (rdRefRegistResults.size() > 0) {
                RdRefRegistResult refRegistResult = rdRefRegistResults.stream().findFirst().get();
                if (ObjectUtil.isNotNull(refRegistResult)) {
                    rdSamplePaResult.setSysRefDate(refRegistResult.getSysRefPayDate());
                    rdSamplePaResult.setSysRefPerCode(refRegistResult.getSysRefUpVoucherPc());
                    rdSamplePaResult.setSysRefPerName(refRegistResult.getSysRefUpVoucherPn());
                    rdSamplePaResult.setSysRefRealAmount(refRegistResult.getSysRefAppAmount());
                }
            }
        }
        return rdSamplePaResult;
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData paySamplePurchaseApp(RdSamplePaParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();

            List<AccountMgtPersonal> accountMgtPersonals = this.proposalServiceConsumer.listAccountMgtPersonal(new AccountMgtPersonalParam());
            AccountMgtPersonal accountMgtPersonal = accountMgtPersonals.stream().filter(l -> ObjectUtil.isNotNull(l.getAccNo()) && l.getAccNo().equals(param.getSysFeeAppAlipayAccount())).findFirst().get();

            AccountFlowParam accountFlowParam = new AccountFlowParam();
            accountFlowParam.setAccountId(accountMgtPersonal.getId());
            accountFlowParam.setAccountNo(accountMgtPersonal.getAccNo());
            accountFlowParam.setPaymentAccount("1");
            accountFlowParam.setPaymentTime(param.getSysFeeAppPayDate());
            accountFlowParam.setAmount(param.getSysFeeAppPayAmount());
            accountFlowParam.setInOrOut(-1);
            accountFlowParam.setCurrency("CNY");
            accountFlowParam.setRemark("购样申请->支付");
            accountFlowParam.setBizDataSources("product");

            UpdateWrapper<RdSamplePa> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
            updateWrapper.set("SYS_FEE_APP_PAY_DATE", param.getSysFeeAppPayDate()).set("SYS_FEE_APP_PAY_AMOUNT", param.getSysFeeAppPayAmount())
                    .set("SYS_FEE_APP_ALIPAY_ACCOUNT", param.getSysFeeAppAlipayAccount()).set("SYS_FEE_APP_ALIPAY_AN", param.getSysFeeAppAlipayAn())
                    .set("SYS_FEE_APP_PAY_VD", ObjectUtil.isNotNull(param.getSysFeeAppPayVd()) ? param.getSysFeeAppPayVd() : "").set("SYS_FEE_APP_PAY_RD", oprDate)
                    .set("SYS_FEE_APP_PAY_RPC", loginUser.getAccount()).set("SYS_FEE_APP_PAY_RPN", loginUser.getName())
                    .set("SYS_FEE_APP_STATUS", RdProposalEnum.PA_FEE_APP_STATUS_ARCH.getName());

            RdProposal rdProposal = this.rdProposalMapper.selectById(param.getSysTaCode());
            UpdateWrapper<RdProposal> updateWrapperPro = new UpdateWrapper<>();
            updateWrapperPro.eq("SYS_TA_CODE", param.getSysTaCode());
            updateWrapperPro.set("SYS_TA_ACCU_RD_FEE", rdProposal.getSysTaAccuRdFee().add(param.getSysFeeAppPayAmount()));

            if (this.mapper.update(null, updateWrapper) > 0 && this.rdProposalMapper.update(null, updateWrapperPro) > 0) {
                ResponseData responseData = this.proposalServiceConsumer.addOutFlow(accountFlowParam);
                if (responseData.getSuccess() && responseData.getCode().equals(200)) {
                    return ResponseData.success("购样申请支付成功.");
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("购样申请支付失败.");
                }

            } else {
                return ResponseData.error("购样申请支付失败.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData refSamplePurchaseAppDetail(RdSamplePaParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();

            if (param.getSysFeeAppSc().equals("供应商")) {
                QueryWrapper<RdRefRegist> queryWrapperRef = new QueryWrapper<>();
                queryWrapperRef.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode()).eq("SYS_FEE_APP_SOURCE", "购样申请").eq("SYS_REF_FEE_TYPE", "购样费");
                RdRefRegist refRegist = refRegistMapper.selectOne(queryWrapperRef);

                RdRefRegistResult refRegistResult = BeanUtil.copyProperties(refRegist, RdRefRegistResult.class);
                RdSampleTask rdSampleTask = rdSampleTaskMapper.selectById(refRegistResult.getSysTsTaskCode());
                refRegistResult.setSysTsTaskName(rdSampleTask.getSysTsTaskName());
                RdSamplePa rdSamplePa = mapper.selectById(param.getSysFeeAppCode());

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

                if (refRegistResult.getSysRefType().equals("首单退款")) {
                    refRegistResult.setSysRefConditionView("首单退款");
                } else if (refRegistResult.getSysRefType().equals("订单量退款")) {
                    refRegistResult.setSysRefConditionView(refRegistResult.getSysRefType() + "-" + refRegistResult.getSysRefCondition() + " PCS");
                } else {
                    refRegistResult.setSysRefConditionView(refRegistResult.getSysRefType() + "-" + refRegistResult.getSysRefCondition() + " RMB");
                }

                refRegistResult.setSysAppDate(rdSamplePa.getSysFeeAppSubDate());
                return ResponseData.success(refRegistResult);
            } else {
                QueryWrapper<RdSampleRpr> queryWrapperRpr = new QueryWrapper<>();
                queryWrapperRpr.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
                RdSampleRpr rdSampleRpr = rdSampleRprMapper.selectOne(queryWrapperRpr);
                RdSampleRprResult rdSampleRprResult = BeanUtil.copyProperties(rdSampleRpr, RdSampleRprResult.class);
                RdSamplePa rdSamplePa = mapper.selectById(param.getSysFeeAppCode());

                rdSampleRprResult.setSysFeeAppSc(rdSamplePa.getSysFeeAppSc());
                rdSampleRprResult.setSysFeeAppShopName(rdSamplePa.getSysFeeAppShopName());
                rdSampleRprResult.setSysFeeAppProPurPage(rdSamplePa.getSysFeeAppProPurPage());
                rdSampleRprResult.setSysFeeAppOrderNum(rdSamplePa.getSysFeeAppOrderNum());
                rdSampleRprResult.setSysFeeAppOrderPic(rdSamplePa.getSysFeeAppOrderPic());
                rdSampleRprResult.setSysFeeAppAlipayAccount(rdSamplePa.getSysFeeAppAlipayAccount());
                rdSampleRprResult.setSysFeeAppAlipayAn(rdSamplePa.getSysFeeAppAlipayAn());
                rdSampleRprResult.setSysRefAlipayA(rdSampleRprResult.getSysFeeAppAlipayAccount());
                rdSampleRprResult.setSysRefAlipayAn(rdSamplePa.getSysFeeAppAlipayAn());
                rdSampleRprResult.setSysFeeAppPurPerName(rdSamplePa.getSysFeeAppPurPerName());

                QueryWrapper<RdSampleManage> manageQueryWrapper = new QueryWrapper<>();
                manageQueryWrapper.eq("SYS_KFY_FEE_CODE", param.getSysFeeAppCode()).eq("SYS_KFY_FEE_SOURCE", "购样申请")
                        .eq("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_RETURNED.getName());
                List<RdSampleManage> rdSampleManageList = rdSampleManageMapper.selectList(manageQueryWrapper);
                rdSampleRprResult.setSampleManageList(rdSampleManageList);

                return ResponseData.success(rdSampleRprResult);
            }

        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData refSamplePurchaseApp(RdSampleRprParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();

            List<AccountMgtPersonal> accountMgtPersonals = this.proposalServiceConsumer.listAccountMgtPersonal(new AccountMgtPersonalParam());
            AccountMgtPersonal accountMgtPersonal = accountMgtPersonals.stream().filter(l -> ObjectUtil.isNotNull(l.getAccNo()) && l.getAccNo().equals(param.getSysRefAlipayA())).findFirst().get();

            AccountFlowParam accountFlowParam = new AccountFlowParam();
            accountFlowParam.setAccountId(accountMgtPersonal.getId());
            accountFlowParam.setAccountNo(accountMgtPersonal.getAccNo());
            accountFlowParam.setPaymentAccount("-1");
            accountFlowParam.setPaymentTime(param.getSysRefDate());
            accountFlowParam.setAmount(param.getSysRefRealAmount());
            accountFlowParam.setInOrOut(1);
            accountFlowParam.setCurrency("CNY");
            accountFlowParam.setRemark("购样申请->退款");
            accountFlowParam.setBizDataSources("product");

            UpdateWrapper<RdSampleRpr> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_REF_CODE", param.getSysRefCode());
            updateWrapper.set("SYS_REF_REAL_AMOUNT", param.getSysRefRealAmount())
                    .set("SYS_REF_ALIPAY_A", param.getSysRefAlipayA()).set("SYS_REF_ALIPAY_AN", param.getSysRefAlipayAn())
                    .set("SYS_REF_DATE", param.getSysRefDate()).set("SYS_REF_RG_DATE", oprDate)
                    .set("SYS_REF_RG_PER_CODE", loginUser.getAccount()).set("SYS_REF_RG_PER_NAME", loginUser.getName());

            if (this.rdSampleRprMapper.update(null, updateWrapper) > 0) {
                ResponseData responseData = this.proposalServiceConsumer.addInFlow(accountFlowParam);
                if (responseData.getSuccess() && responseData.getCode().equals(200)) {
                    return ResponseData.success("购样申请退款确认成功.");
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("购样申请退款确认失败.");
                }
            } else {
                return ResponseData.error("购样申请退款确认失败.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData returnSamplePurchaseApp(RdSamplePaParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();

            QueryWrapper<RdSampleRpr> rprQueryWrapper = new QueryWrapper<>();
            rprQueryWrapper.eq("SYS_FEE_APP_CODE",param.getSysFeeAppCode()).eq("SYS_FEE_APP_SOURCE",RdProposalEnum.KFY_FEE_SOURCE_SPA.getName());
            RdSampleRpr rdSampleRprModel = this.rdSampleRprMapper.selectOne(rprQueryWrapper);
            if (ObjectUtil.isNotNull(rdSampleRprModel) && ObjectUtil.isNotNull(rdSampleRprModel.getSysRefCode())){
                return ResponseData.error("当前购样申请,已经操作过直接退货,不能重复进行.");
            }

            QueryWrapper<RdSampleManage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_KFY_FEE_CODE",param.getSysFeeAppCode());
            queryWrapper.eq("SYS_KFY_FEE_SOURCE",RdProposalEnum.KFY_FEE_SOURCE_SPA.getName());
            Integer count = this.rdSampleManageMapper.selectCount(queryWrapper);

            RdSampleRpr rdSampleRpr = new RdSampleRpr();
            rdSampleRpr.setSysPlCode(param.getSysPlCode());
            rdSampleRpr.setSysSpu(param.getSysSpu());
            rdSampleRpr.setSysTaCode(param.getSysTaCode());
            rdSampleRpr.setSysTsTaskCode(param.getSysTsTaskCode());
            rdSampleRpr.setSysFeeAppCode(param.getSysFeeAppCode());
            rdSampleRpr.setSysFeeAppSource(RdProposalEnum.KFY_FEE_SOURCE_SPA.getName());
            rdSampleRpr.setSysRefOpDate(oprDate);
            rdSampleRpr.setSysRefQty(BigDecimal.valueOf(count));
            rdSampleRpr.setSysRefPreAmount(param.getSysFeeAppPayAmount());
            rdSampleRpr.setSysRefCode(generatorNoUtil.getRprBillNoExtents("0000","Ref-TH",4));
            rdSampleRpr.setSysCDate(oprDate);
            rdSampleRpr.setSysLDate(oprDate);
            rdSampleRpr.setSysPerCode(loginUser.getAccount());
            rdSampleRpr.setSysPerName(loginUser.getName());
            rdSampleRpr.setSysDeptCode(hrm.getIdall());
            rdSampleRpr.setSysDeptName(hrm.getDepartmentname());

            UpdateWrapper<RdSampleManage> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_KFY_FEE_CODE",param.getSysFeeAppCode());
            updateWrapper.eq("SYS_KFY_FEE_SOURCE",RdProposalEnum.KFY_FEE_SOURCE_SPA.getName());
            updateWrapper.set("SYS_KFY_STATUS",RdProposalEnum.KFY_STATE_RETURNED.getName())
                    .set("SYS_KFY_RG_DATE",oprDate).set("SYS_KFY_RG_OP_DATE",oprDate);

            boolean rprResult = this.rdSampleRprMapper.insert(rdSampleRpr) > 0;
            boolean updResult = this.rdSampleManageMapper.update(null,updateWrapper) > 0;

            if (rprResult && updResult){
                return ResponseData.error("购样申请直接退货成功.");
            }else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("购样申请直接退货失败.");
            }

        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData checkReturnSamplePurchaseApp(RdSamplePaParam param) {
        try {
            QueryWrapper<RdSampleRpr> rprQueryWrapper = new QueryWrapper<>();
            rprQueryWrapper.eq("SYS_FEE_APP_CODE",param.getSysFeeAppCode())
                    .eq("SYS_FEE_APP_SOURCE",RdProposalEnum.KFY_FEE_SOURCE_SPA.getName());

            RdSampleRpr rdSampleRprModel = this.rdSampleRprMapper.selectOne(rprQueryWrapper);
            if (ObjectUtil.isNotNull(rdSampleRprModel) && ObjectUtil.isNotNull(rdSampleRprModel.getSysRefCode())){
                return ResponseData.error("当前购样申请,已经操作过直接退货,不能再进行退货或登记样品操作.");
            }else {
                return ResponseData.success();
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

}
