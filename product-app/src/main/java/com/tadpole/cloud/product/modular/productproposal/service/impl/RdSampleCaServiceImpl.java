package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountFlowParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import com.tadpole.cloud.product.api.productproposal.entity.*;
import com.tadpole.cloud.product.api.productproposal.model.params.RdMoldOpenPfaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdRefRegistParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCfbParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdRefRegistResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCaResult;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.consumer.ProposalServiceConsumer;
import com.tadpole.cloud.product.modular.consumer.RdPreProposalServiceConsumer;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPssManageResult;
import com.tadpole.cloud.product.modular.product.service.IRdPssManageService;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.*;
import com.tadpole.cloud.product.modular.productproposal.service.IRdMoldOpenPfaService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdRefRegistService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleCaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 提案-定制申请 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Service
public class RdSampleCaServiceImpl extends ServiceImpl<RdSampleCaMapper, RdSampleCa> implements IRdSampleCaService {

    @Resource
    private RdSampleCaMapper mapper;

    @Resource
    private RdPreProposalServiceConsumer oaServiceConsumer;

    @Resource
    private IRdRefRegistService rdRefRegistService;

    @Resource
    private IRdMoldOpenPfaService moldOpenPfaService;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private RdSampleCfbMapper sampleCfbMapper;

    @Resource
    private RdProposalMapper proposalMapper;

    @Resource
    private ProposalServiceConsumer proposalServiceConsumer;

    @Resource
    private IRdPssManageService pssManageService;

    @Resource
    private RdRefRegistMapper refRegistMapper;

    @Resource
    private RdSampleTaskMapper rdSampleTaskMapper;

    @Resource
    private RdSampleCfbMapper rdSampleCfbMapper;

    @Resource
    private RdMoldOpenPfaMapper rdMoldOpenPfaMapper;

    @Resource
    private RdSamplePaMapper rdSamplePaMapper;

    @Resource
    private RdProposalMapper rdProposalMapper;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData add(RdSampleCaParam param) {
        RdSampleCa rdSampleCa = BeanUtil.copyProperties(param, RdSampleCa.class);
        if (this.mapper.insert(rdSampleCa) > 0) {
            return ResponseData.success();
        } else {
            return ResponseData.error("");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData checkIsCanCreateFeedback(RdSampleCaParam param) {
        QueryWrapper<RdSampleCa> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());

        if (this.mapper.selectCount(queryWrapper) > 0) {
            return ResponseData.error("");
        } else {
            return ResponseData.success();
        }


    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData uploadContract(RdSampleCaParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            UpdateWrapper<RdSampleCa> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
            updateWrapper.set("SYS_SA_CONTRACT_UD", new Date()).set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_CPR.getName())
                    .set("SYS_SA_CONTRACT_AMOUNT", param.getSysSaContractAmount()).set("SYS_SA_CONTRACT_DOC", param.getSysSaContractDoc())
                    .set("SYS_SA_CONTRACT_REMARKS", ObjectUtil.isNotNull(param.getSysSaContractRemarks()) ? param.getSysSaContractRemarks() : "").set("SYS_SA_CONTRACT_UPC", loginUser.getAccount())
                    .set("SYS_SA_CONTRACT_UPN", loginUser.getName()).set("SYS_SA_AUDIT_RESULT", "").set("SYS_SA_AUDIT_EXPLAIN", "").set("SYS_SA_AUDIT_PER_NAME", "")
                    .set("SYS_SA_AUDIT_PER_CODE", "").set("SYS_SA_APP_RESULT", "").set("SYS_SA_APP_PER_CODE", "").set("SYS_SA_APP_PER_NAME", "").set("SYS_SA_APP_REMARKS", "");

            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("定制合同上传成功.");
            } else {
                return ResponseData.error("定制合同上传失败.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public PageResult<RdSampleCaResult> listPage(RdSampleCaParam param) {
        Page pageContext = param.getPageContext();
        IPage<RdSampleCaResult> page = this.mapper.listPage(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleCaResult> listSampleCa(RdSampleCaParam param) {
        return this.mapper.listSampleCa(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData reviewContract(RdSampleCaParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();

            UpdateWrapper<RdSampleCa> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());

            updateWrapper.set("SYS_SA_AUDIT_DATE", new Date()).set("SYS_SA_AUDIT_RESULT", param.getSysSaAuditResult()).set("SYS_SA_AUDIT_IS_USE_TEMP", param.getSysSaAuditIsUseTemp())
                    .set("SYS_SA_AUDIT_PER_CODE", loginUser.getAccount()).set("SYS_SA_AUDIT_PER_NAME", loginUser.getName());

            if (param.getSysSaAuditResult().equals(RdProposalEnum.SA_AUDIT_RESULT_YES.getName())) {
                updateWrapper.set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_PA.getName());
            } else {
                updateWrapper.set("SYS_SA_AUDIT_EXPLAIN", ObjectUtil.isNotNull(param.getSysSaAuditExplain()) ? param.getSysSaAuditExplain() : "").set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_CBU.getName());
            }

            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("合同审核成功.");
            } else {
                return ResponseData.error("合同审核失败.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdSampleCaResult detail(RdSampleCaParam param) {
        return this.mapper.detail(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData approveSampleCa(RdSampleCaParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();

            QueryWrapper<RdSampleCfb> rdSampleCfbQueryWrapper = new QueryWrapper<>();
            rdSampleCfbQueryWrapper.eq("SYS_CUST_FEBK_CODE", param.getSysCustFebkCode());
            RdSampleCfb rdSampleCfb = this.sampleCfbMapper.selectOne(rdSampleCfbQueryWrapper);

            QueryWrapper<RdProposal> rdProposalQueryWrapper = new QueryWrapper<>();
            rdProposalQueryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
            RdProposal rdProposal = this.proposalMapper.selectOne(rdProposalQueryWrapper);

            // 是否开模
            if (rdSampleCfb.getSysCfIsMoldOpen().equals(RdProposalEnum.CF_IS_MOLD_OPEN_YES.getName())) {

                if (param.getSysSaAppResult().equals(RdProposalEnum.SA_APP_RESULT_UPDATE_CONTRACT.getName())) {
                    UpdateWrapper<RdSampleCa> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
                    updateWrapper.set("SYS_SA_APP_DATE", oprDate).set("SYS_SA_APP_RESULT", param.getSysSaAppResult()).set("SYS_SA_APP_REMARKS", ObjectUtil.isNotNull(param.getSysSaAppRemarks()) ? param.getSysSaAppRemarks() : "")
                            .set("SYS_SA_APP_PER_CODE", loginUser.getAccount()).set("SYS_SA_APP_PER_NAME", loginUser.getName()).set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_CBU.getName());
                    if (this.mapper.update(null, updateWrapper) > 0) {
                        return ResponseData.success("定制申请审批成功.");
                    } else {
                        return ResponseData.error("定制申请审批失败.");
                    }
                } else if (param.getSysSaAppResult().equals(RdProposalEnum.SA_APP_RESULT_NO.getName())) {
                    UpdateWrapper<RdSampleCa> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
                    updateWrapper.set("SYS_SA_APP_DATE", oprDate).set("SYS_SA_APP_RESULT", param.getSysSaAppResult())
                            .set("SYS_SA_APP_PER_CODE", loginUser.getAccount()).set("SYS_SA_APP_PER_NAME", loginUser.getName());
                    updateWrapper.set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_COMPLETED.getName()).set("SYS_SA_APP_REMARKS", ObjectUtil.isNotNull(param.getSysSaAppRemarks()) ? param.getSysSaAppRemarks() : "");

                    if (this.mapper.update(null, updateWrapper) > 0) {
                        return ResponseData.success("定制申请审批成功.");
                    } else {
                        return ResponseData.error("定制申请审批失败.");
                    }
                } else {

                    RdPlManageParam rdPlManageParam = new RdPlManageParam();
                    rdPlManageParam.setSysPlCode(param.getSysPlCode());
                    rdPlManageParam.setSysSpu(param.getSysSpu());
                    List<RdPssManageResult> rdPssManageResults = this.pssManageService.listPage(rdPlManageParam);

                    RdPssManageResult rdPssManageResult = rdPssManageResults.stream().filter(l -> l.getSysSpu().equals(param.getSysSpu())).findFirst().get();

                    //开模费付款申请
                    RdMoldOpenPfaParam rdMoldOpenPfaParam = new RdMoldOpenPfaParam();
                    rdMoldOpenPfaParam.setSysCDate(oprDate);
                    rdMoldOpenPfaParam.setSysLDate(oprDate);
                    rdMoldOpenPfaParam.setSysDeptCode(hrm.getIdall());
                    rdMoldOpenPfaParam.setSysDeptName(hrm.getDepartmentname());
                    rdMoldOpenPfaParam.setSysPerCode(loginUser.getAccount());
                    rdMoldOpenPfaParam.setSysPerName(loginUser.getName());
                    rdMoldOpenPfaParam.setSysMofStatus(RdProposalEnum.MOF_STATUS_WAIT_SUBMIT.getName());
                    rdMoldOpenPfaParam.setSysPlCode(param.getSysPlCode());
                    rdMoldOpenPfaParam.setSysSpu(param.getSysSpu());
                    rdMoldOpenPfaParam.setSysTaCode(param.getSysTaCode());
                    rdMoldOpenPfaParam.setSysTsTaskCode(param.getSysTsTaskCode());
                    rdMoldOpenPfaParam.setSysCustFebkCode(param.getSysCustFebkCode());
                    rdMoldOpenPfaParam.setSysFeeAppCode(param.getSysFeeAppCode());
                    rdMoldOpenPfaParam.setSysPmPerCode(rdProposal.getSysPmPerCode());
                    rdMoldOpenPfaParam.setSysPmPerName(rdProposal.getSysPmPerName());
                    rdMoldOpenPfaParam.setSysCfSupplierNum(rdSampleCfb.getSysCfSupplierNum());
                    rdMoldOpenPfaParam.setSysCfSupplierName(rdSampleCfb.getSysCfSupplierName());
                    rdMoldOpenPfaParam.setSysCfPurPerCode(rdSampleCfb.getSysCfPurPerCode());
                    rdMoldOpenPfaParam.setSysCfPurPerName(rdSampleCfb.getSysCfPurPerName());
                    rdMoldOpenPfaParam.setSysCfTicketType(rdSampleCfb.getSysCfTicketType());
                    rdMoldOpenPfaParam.setSysCfAccountType(rdSampleCfb.getSysCfAccountType());
                    rdMoldOpenPfaParam.setSysCfPayMethod(rdSampleCfb.getSysCfPayMethod());
                    rdMoldOpenPfaParam.setSysCfAccountName(rdSampleCfb.getSysCfAccountName());
                    rdMoldOpenPfaParam.setSysCfBankAccount(rdSampleCfb.getSysCfBankAccount());
                    rdMoldOpenPfaParam.setSysCfBankName(rdSampleCfb.getSysCfBankName());
                    rdMoldOpenPfaParam.setSysSaContractAmount(rdSampleCfb.getSysCfFeeTotal());
                    rdMoldOpenPfaParam.setSysMofTitle("CW28-付款单-" + rdSampleCfb.getSysCfPurPerName() + "-" + String.format("%tY", oprDate) + "-" + String.format("%tm", oprDate) + "-" + String.format("%td", oprDate));
                    rdMoldOpenPfaParam.setSysMofSummary(param.getSysTaCode() + ">" + rdPssManageResult.getSysProName() + " 开模费用-" + rdSampleCfb.getSysCfFeeTotal());
                    rdMoldOpenPfaParam.setSysFuncOpr(RdProposalEnum.MOF_FUNC_SAVE.getName());
                    rdMoldOpenPfaParam.setSysPageOpr(RdProposalEnum.MOF_PAGE_NEW.getName());
                    rdMoldOpenPfaParam.setSysMofCode(generatorNoUtil.getMofBillNoExtents("000", "SA-KMF", 3));

                    //是否可退款
                    if (rdSampleCfb.getSysCfIsRefund().equals(RdProposalEnum.CF_IS_REFUND_YES.getName())) {
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
                        rdRefRegistParam.setSysFeeAppSource(RdProposalEnum.FEE_APP_SOURCE_DZSQ.getName());
                        rdRefRegistParam.setSysRefFeeType(RdProposalEnum.REF_FEE_TYPE_KMF.getName());
                        rdRefRegistParam.setSysRefFees(rdSampleCfb.getSysCfFeeTotal());
                        rdRefRegistParam.setSysRefSupplierCode(rdSampleCfb.getSysCfSupplierNum());
                        rdRefRegistParam.setSysRefSupplierName(rdSampleCfb.getSysCfSupplierName());
                        rdRefRegistParam.setSysRefType(rdSampleCfb.getSysCfRefundType());
                        rdRefRegistParam.setSysRefCondition(rdSampleCfb.getSysCfRefundCondition());
                        rdRefRegistParam.setSysRefPurPerCode(rdSampleCfb.getSysCfPurPerCode());
                        rdRefRegistParam.setSysRefPurPerName(rdSampleCfb.getSysCfPurPerName());

                        boolean rdRefRegistResult = false;
                        boolean moldOpenPfaResult = false;
                        boolean rdSampleCaResult = false;
                        boolean rdSampleTaskResult = false;
                        RdRefRegist rdRefRegist = BeanUtil.copyProperties(rdRefRegistParam, RdRefRegist.class);
                        rdRefRegistResult = this.refRegistMapper.insert(rdRefRegist) > 0;

                        RdMoldOpenPfa moldOpenPfa = BeanUtil.copyProperties(rdMoldOpenPfaParam, RdMoldOpenPfa.class);
                        moldOpenPfaResult = this.rdMoldOpenPfaMapper.insert(moldOpenPfa) > 0;

                        UpdateWrapper<RdSampleCa> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
                        updateWrapper.set("SYS_SA_APP_RESULT", param.getSysSaAppResult()).set("SYS_SA_APP_DATE", oprDate)
                                .set("SYS_SA_APP_PER_CODE", loginUser.getAccount()).set("SYS_SA_APP_PER_NAME", loginUser.getName());
                        updateWrapper.set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_TBP.getName());
                        rdSampleCaResult = this.mapper.update(null, updateWrapper) > 0;
                        //更新任务进度
                        UpdateWrapper<RdSampleTask> rdSampleTaskUpdateWrapper = new UpdateWrapper<>();
                        rdSampleTaskUpdateWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());
                        rdSampleTaskUpdateWrapper.set("SYS_TS_TASK_PROGRESS", RdProposalEnum.SAMPLE_PROCESS_WAIT_SAMP.getName());
                        rdSampleTaskResult = this.rdSampleTaskMapper.update(null, rdSampleTaskUpdateWrapper) > 0;

                        if (rdRefRegistResult && moldOpenPfaResult && rdSampleCaResult && rdSampleTaskResult) {
                            return ResponseData.success("定制申请审批成功.");
                        } else {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResponseData.error("定制申请审批失败.");
                        }
                    } else {

                        boolean moldOpenPfaResult = false;
                        boolean rdSampleCaResult = false;
                        boolean rdSampleTaskResult = false;

                        RdMoldOpenPfa moldOpenPfa = BeanUtil.copyProperties(rdMoldOpenPfaParam, RdMoldOpenPfa.class);
                        moldOpenPfaResult = this.rdMoldOpenPfaMapper.insert(moldOpenPfa) > 0;

                        UpdateWrapper<RdSampleCa> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
                        updateWrapper.set("SYS_SA_APP_RESULT", param.getSysSaAppResult()).set("SYS_SA_APP_DATE", oprDate)
                                .set("SYS_SA_APP_PER_CODE", loginUser.getAccount()).set("SYS_SA_APP_PER_NAME", loginUser.getName());
                        updateWrapper.set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_TBP.getName());
                        rdSampleCaResult = this.mapper.update(null, updateWrapper) > 0;

                        //更新任务进度
                        UpdateWrapper<RdSampleTask> rdSampleTaskUpdateWrapper = new UpdateWrapper<>();
                        rdSampleTaskUpdateWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());
                        rdSampleTaskUpdateWrapper.set("SYS_TS_TASK_PROGRESS", RdProposalEnum.SAMPLE_PROCESS_WAIT_SAMP.getName());
                        rdSampleTaskResult = this.rdSampleTaskMapper.update(null, rdSampleTaskUpdateWrapper) > 0;

                        if (moldOpenPfaResult && rdSampleCaResult && rdSampleTaskResult) {
                            return ResponseData.success("定制申请审批成功.");
                        } else {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResponseData.error("定制申请审批失败.");
                        }
                    }
                }
            } else {

                //是否可退款
                if (rdSampleCfb.getSysCfIsRefund().equals(RdProposalEnum.CF_IS_REFUND_YES.getName())) {
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
                    rdRefRegistParam.setSysFeeAppSource(RdProposalEnum.FEE_APP_SOURCE_DZSQ.getName());
                    rdRefRegistParam.setSysRefFeeType(RdProposalEnum.REF_FEE_TYPE_DYF.getName());
                    rdRefRegistParam.setSysRefFees(rdSampleCfb.getSysCfFeeTotal());
                    rdRefRegistParam.setSysRefSupplierCode(rdSampleCfb.getSysCfSupplierNum());
                    rdRefRegistParam.setSysRefSupplierName(rdSampleCfb.getSysCfSupplierName());
                    rdRefRegistParam.setSysRefType(rdSampleCfb.getSysCfRefundType());
                    rdRefRegistParam.setSysRefCondition(rdSampleCfb.getSysCfRefundCondition());
                    rdRefRegistParam.setSysRefPurPerCode(rdSampleCfb.getSysCfPurPerCode());
                    rdRefRegistParam.setSysRefPurPerName(rdSampleCfb.getSysCfPurPerName());

                    boolean rdRefRegistResult = false;
                    boolean rdSampleCaResult = false;
                    boolean rdSampleTaskResult = false;

                    RdRefRegist rdRefRegist = BeanUtil.copyProperties(rdRefRegistParam, RdRefRegist.class);
                    rdRefRegistResult = this.refRegistMapper.insert(rdRefRegist) > 0;

                    UpdateWrapper<RdSampleCa> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
                    updateWrapper.set("SYS_SA_APP_RESULT", param.getSysSaAppResult()).set("SYS_SA_APP_DATE", oprDate)
                            .set("SYS_SA_APP_PER_CODE", loginUser.getAccount()).set("SYS_SA_APP_PER_NAME", loginUser.getName());
                    if (param.getSysSaAppResult().equals(RdProposalEnum.SA_APP_RESULT_YES.getName())) {
                        updateWrapper.set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_TBP.getName());
                    } else {
                        updateWrapper.set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_COMPLETED.getName()).set("SYS_SA_APP_REMARKS", param.getSysSaAppRemarks());
                    }
                    rdSampleCaResult = this.mapper.update(null, updateWrapper) > 0;

                    //更新任务进度
                    UpdateWrapper<RdSampleTask> rdSampleTaskUpdateWrapper = new UpdateWrapper<>();
                    rdSampleTaskUpdateWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());
                    rdSampleTaskUpdateWrapper.set("SYS_TS_TASK_PROGRESS", RdProposalEnum.SAMPLE_PROCESS_WAIT_SAMP.getName());
                    rdSampleTaskResult = this.rdSampleTaskMapper.update(null, rdSampleTaskUpdateWrapper) > 0;

                    if (rdRefRegistResult && rdSampleCaResult && rdSampleTaskResult) {
                        return ResponseData.success("定制申请审批成功.");
                    } else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("定制申请审批失败.");
                    }

                } else {

                    boolean rdSampleCaResult = false;
                    boolean rdSampleTaskResult = false;

                    UpdateWrapper<RdSampleCa> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
                    updateWrapper.set("SYS_SA_APP_RESULT", param.getSysSaAppResult()).set("SYS_SA_APP_DATE", oprDate)
                            .set("SYS_SA_APP_PER_CODE", loginUser.getAccount()).set("SYS_SA_APP_PER_NAME", loginUser.getName());
                    if (param.getSysSaAppResult().equals(RdProposalEnum.SA_APP_RESULT_YES.getName())) {
                        updateWrapper.set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_TBP.getName());
                    } else {
                        updateWrapper.set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_COMPLETED.getName()).set("SYS_SA_APP_REMARKS", param.getSysSaAppRemarks());
                    }
                    rdSampleCaResult = this.mapper.update(null, updateWrapper) > 0;

                    //更新任务进度
                    UpdateWrapper<RdSampleTask> rdSampleTaskUpdateWrapper = new UpdateWrapper<>();
                    rdSampleTaskUpdateWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());
                    rdSampleTaskUpdateWrapper.set("SYS_TS_TASK_PROGRESS", RdProposalEnum.SAMPLE_PROCESS_WAIT_SAMP.getName());
                    rdSampleTaskResult = this.rdSampleTaskMapper.update(null, rdSampleTaskUpdateWrapper) > 0;

                    if (rdSampleCaResult && rdSampleTaskResult) {
                        return ResponseData.success("定制申请审批成功.");
                    } else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("定制申请审批失败.");
                    }
                }
            }
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData payRdSampleCa(RdSampleCaParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();

            List<AccountMgtPersonal> accountMgtPersonals = this.proposalServiceConsumer.listAccountMgtPersonal(new AccountMgtPersonalParam());
            AccountMgtPersonal accountMgtPersonal = accountMgtPersonals.stream().filter(l -> l.getAccNo().equals(param.getSysSaAlipayAccount())).findFirst().get();

            AccountFlowParam accountFlowParam = new AccountFlowParam();
            accountFlowParam.setAccountId(accountMgtPersonal.getId());
            accountFlowParam.setAccountNo(accountMgtPersonal.getAccNo());
            accountFlowParam.setPaymentAccount("1");
            accountFlowParam.setPaymentTime(param.getSysSaPayDate());
            accountFlowParam.setAmount(param.getSysSaPayAmount());
            accountFlowParam.setInOrOut(-1);
            accountFlowParam.setCurrency("CNY");
            accountFlowParam.setRemark("定制申请->打样申请->支付");
            accountFlowParam.setBizDataSources("product");

            UpdateWrapper<RdSampleCa> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode());
            updateWrapper.set("SYS_SA_PAY_DATE", param.getSysSaPayDate()).set("SYS_SA_PAY_AMOUNT", param.getSysSaPayAmount())
                    .set("SYS_SA_PAY_VD", ObjectUtil.isNotNull(param.getSysSaPayVd()) ? param.getSysSaPayVd() : "").set("SYS_SA_PAY_RD", new Date())
                    .set("SYS_SA_ALIPAY_ACCOUNT", param.getSysSaAlipayAccount()).set("SYS_SA_ALIPAY_AN", param.getSysSaAlipayAn())
                    .set("SYS_SA_PAY_RPC", loginUser.getAccount()).set("SYS_SA_PAY_RPN", loginUser.getName())
                    .set("SYS_SA_AP_STATUS", RdProposalEnum.SA_AP_STATU_COMPLETED.getName());

            RdProposal rdProposal = this.rdProposalMapper.selectById(param.getSysTaCode());
            UpdateWrapper<RdProposal> updateWrapperPro = new UpdateWrapper<>();
            updateWrapperPro.eq("SYS_TA_CODE", param.getSysTaCode());
            updateWrapperPro.set("SYS_TA_ACCU_RD_FEE", rdProposal.getSysTaAccuRdFee().add(param.getSysSaPayAmount()));

            if (this.mapper.update(null, updateWrapper) > 0 && this.rdProposalMapper.update(null, updateWrapperPro) > 0) {
                ResponseData responseData = this.proposalServiceConsumer.addOutFlow(accountFlowParam);
                if (responseData.getSuccess() && responseData.getCode().equals(200)) {
                    return ResponseData.success("打样申请支付成功.");
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("打样申请支付失败.");
                }
            } else {
                return ResponseData.error("打样申请支付失败.");
            }
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData refRdSampleCaDetail(RdSampleCaParam param) {

        try {
            QueryWrapper<RdRefRegist> queryWrapperRef = new QueryWrapper<>();
            queryWrapperRef.eq("SYS_FEE_APP_CODE", param.getSysFeeAppCode()).eq("SYS_FEE_APP_SOURCE", "定制申请").eq("SYS_REF_FEE_TYPE", "打样费");
            RdRefRegist refRegist = refRegistMapper.selectOne(queryWrapperRef);

            RdRefRegistResult refRegistResult = BeanUtil.copyProperties(refRegist, RdRefRegistResult.class);

            RdSampleTask rdSampleTask = rdSampleTaskMapper.selectById(refRegistResult.getSysTsTaskCode());
            refRegistResult.setSysTsTaskName(rdSampleTask.getSysTsTaskName());
            QueryWrapper<RdSampleManage> manageQueryWrapper = new QueryWrapper<>();
            if (refRegistResult.getSysRefFeeType().equals(RdProposalEnum.REF_FEE_TYPE_DYF.getName())) {

                RdSampleCa rdSampleCa = mapper.selectById(refRegistResult.getSysFeeAppCode());
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

                if (refRegistResult.getSysRefType().equals("首单退款")) {
                    refRegistResult.setSysRefConditionView("首单退款");
                } else if (refRegistResult.getSysRefType().equals("订单量退款")) {
                    refRegistResult.setSysRefConditionView(refRegistResult.getSysRefType() + "-" + refRegistResult.getSysRefCondition() + " PCS");
                } else {
                    refRegistResult.setSysRefConditionView(refRegistResult.getSysRefType() + "-" + refRegistResult.getSysRefCondition() + " RMB");
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
                RdSampleCa rdSampleCa = mapper.selectById(refRegistResult.getSysFeeAppCode());
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

                if (refRegistResult.getSysRefType().equals("首单退款")) {
                    refRegistResult.setSysRefConditionView("首单退款");
                } else if (refRegistResult.getSysRefType().equals("订单量退款")) {
                    refRegistResult.setSysRefConditionView(refRegistResult.getSysRefType() + "-" + refRegistResult.getSysRefCondition() + " PCS");
                } else {
                    refRegistResult.setSysRefConditionView(refRegistResult.getSysRefType() + "-" + refRegistResult.getSysRefCondition() + " RMB");
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

            return ResponseData.success(refRegistResult);
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData refRdSampleCa(RdRefRegistParam param) {

        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();

            RdRefRegist refRegist = refRegistMapper.selectById(param.getSysRefAppCode());
            if (refRegist.getSysRefFeeType().equals("购样费")) {
                RdSamplePa rdSamplePa = rdSamplePaMapper.selectById(refRegist.getSysFeeAppCode());

                List<AccountMgtPersonal> accountMgtPersonals = this.proposalServiceConsumer.listAccountMgtPersonal(new AccountMgtPersonalParam());
                AccountMgtPersonal accountMgtPersonal = accountMgtPersonals.stream().filter(l -> l.getAccNo().equals(rdSamplePa.getSysFeeAppAlipayAccount())).findFirst().get();

                AccountFlowParam accountFlowParam = new AccountFlowParam();
                accountFlowParam.setAccountId(accountMgtPersonal.getId());
                accountFlowParam.setAccountNo(accountMgtPersonal.getAccNo());
                accountFlowParam.setPaymentAccount("-1");
                accountFlowParam.setPaymentTime(refRegist.getSysRefPayDate());
                accountFlowParam.setAmount(refRegist.getSysRefAppAmount());
                accountFlowParam.setInOrOut(1);
                accountFlowParam.setCurrency("CNY");
                accountFlowParam.setRemark("购样申请->退款");
                accountFlowParam.setBizDataSources("product");

                UpdateWrapper<RdRefRegist> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("SYS_REF_APP_CODE", refRegist.getSysRefAppCode());
                updateWrapper.set("SYS_REF_APP_ED", new Date())
                        .set("SYS_REF_APP_EPC", loginUser.getAccount()).set("SYS_REF_APP_EPN", loginUser.getName())
                        .set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_RC.getName());

                if (this.refRegistMapper.update(null, updateWrapper) > 0) {
                    ResponseData responseData = this.proposalServiceConsumer.addOutFlow(accountFlowParam);

                    if (responseData.getSuccess() && responseData.getCode().equals(200)) {
                        return ResponseData.success("购样申请退款确认成功.");
                    } else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("购样申请退款确认失败.");
                    }
                } else {
                    return ResponseData.error("购样申请退款确认失败.");
                }
            } else if (refRegist.getSysRefFeeType().equals("打样费")) {

                RdSampleCa rdSampleCa = this.mapper.selectById(refRegist.getSysFeeAppCode());

                List<AccountMgtPersonal> accountMgtPersonals = this.proposalServiceConsumer.listAccountMgtPersonal(new AccountMgtPersonalParam());
                AccountMgtPersonal accountMgtPersonal = accountMgtPersonals.stream().filter(l -> l.getAccNo().equals(rdSampleCa.getSysSaAlipayAccount())).findFirst().get();

                AccountFlowParam accountFlowParam = new AccountFlowParam();
                accountFlowParam.setAccountId(accountMgtPersonal.getId());
                accountFlowParam.setAccountNo(accountMgtPersonal.getAccNo());
                accountFlowParam.setPaymentAccount("-1");
                accountFlowParam.setPaymentTime(param.getSysRefPayDate());
                accountFlowParam.setAmount(param.getSysRefAppAmount());
                accountFlowParam.setInOrOut(1);
                accountFlowParam.setCurrency("CNY");
                accountFlowParam.setRemark("定制申请->打样申请->退款");
                accountFlowParam.setBizDataSources("product");

                UpdateWrapper<RdRefRegist> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("SYS_REF_APP_CODE", refRegist.getSysRefAppCode());
                updateWrapper.set("SYS_REF_APP_ED", new Date())
                        .set("SYS_REF_APP_EPC", loginUser.getAccount()).set("SYS_REF_APP_EPN", loginUser.getName())
                        .set("SYS_REF_APP_STATUS", RdProposalEnum.REF_APP_STATUS_RC.getName());


                if (this.refRegistMapper.update(null, updateWrapper) > 0) {
                    ResponseData responseData = this.proposalServiceConsumer.addOutFlow(accountFlowParam);
                    if (responseData.getSuccess() && responseData.getCode().equals(200)) {
                        return ResponseData.success("定制申请退款确认成功.");
                    } else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("定制申请退款确认失败.");
                    }
                } else {
                    return ResponseData.error("定制申请退款确认失败.");
                }
            }
            return ResponseData.success();
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

}
