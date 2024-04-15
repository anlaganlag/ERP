package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComEntityResult;
import com.tadpole.cloud.product.api.productproposal.entity.RdMoldOpenPfa;
import com.tadpole.cloud.product.api.productproposal.entity.RdProposal;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleCa;
import com.tadpole.cloud.product.api.productproposal.model.params.RdMoldOpenPfaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCaParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdMoldOpenPfaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCaResult;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.consumer.ProposalServiceConsumer;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdMoldOpenPfaMapper;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdProposalMapper;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSampleCaMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdMoldOpenPfaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import net.logstash.logback.encoder.org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 提案-开模费付款申请 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Service
public class RdMoldOpenPfaServiceImpl extends ServiceImpl<RdMoldOpenPfaMapper, RdMoldOpenPfa> implements IRdMoldOpenPfaService {

    @Resource
    private RdMoldOpenPfaMapper mapper;

    @Resource
    private ProposalServiceConsumer proposalServiceConsumer;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private RdSampleCaMapper rdSampleCaMapper;

    @Resource
    private RdProposalMapper rdProposalMapper;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData addOrEdit(RdMoldOpenPfaParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Date oprDate = new Date();

        if (param.getSysPageOpr().equals(RdProposalEnum.MOF_PAGE_EDIT.getName()) && param.getSysFuncOpr().equals(RdProposalEnum.MOF_FUNC_SUBMIT.getName())) {

            if (ObjectUtil.isNull(param.getSysSaStaContractDoc())) {
                return ResponseData.error("开模费付款申请提交必须上传开模合同.");
            }
            if (ObjectUtil.isNull(param.getSysMofPayAmount())) {
                return ResponseData.error("开模费付款申请提交必须填写付款金额.");
            }
            if (ObjectUtil.isNull(param.getSysMofPayMethod())) {
                return ResponseData.error("开模费付款申请提交必须选择付款方式.");
            }
        }

        RdMoldOpenPfa moldOpenPfa = BeanUtil.copyProperties(param, RdMoldOpenPfa.class);
        if (param.getSysPageOpr().equals(RdProposalEnum.MOF_PAGE_EDIT.getName())) {
            if (param.getSysFuncOpr().equals(RdProposalEnum.MOF_FUNC_SAVE.getName())) {
                if (ObjectUtil.isNotNull(param.getSysMofInvoiceNum()) && ObjectUtil.isNotNull(param.getSysMofTicketFile())) {
                    moldOpenPfa.setSysMofTicketUd(oprDate);
                    moldOpenPfa.setSysMofTicketUpc(loginUser.getAccount());
                    moldOpenPfa.setSysMofTicketUpn(loginUser.getName());
                }
                if (this.saveOrUpdate(moldOpenPfa)) {
                    return ResponseData.success("开模费付款申请保存成功.");
                } else {
                    return ResponseData.error("开模费付款申请保存失败.");
                }
            } else {
                moldOpenPfa.setSysMofStatus(RdProposalEnum.MOF_STATUS_WAIT_REVIEW.getName());
                moldOpenPfa.setSysMofSubDate(oprDate);
                moldOpenPfa.setSysMofSubPc(loginUser.getAccount());
                moldOpenPfa.setSysMofSubPn(loginUser.getName());
                if (ObjectUtil.isNotNull(param.getSysMofInvoiceNum()) && ObjectUtil.isNotNull(param.getSysMofTicketFile())) {
                    moldOpenPfa.setSysMofTicketUd(oprDate);
                    moldOpenPfa.setSysMofTicketUpc(loginUser.getAccount());
                    moldOpenPfa.setSysMofTicketUpn(loginUser.getName());
                }

                if (!param.getSysMofPayMethod().equals("尾款")) {
                    String sysMofSummary = param.getSysMofSummary().replace("全款", "").replace("首款", "");
                    moldOpenPfa.setSysMofSummary(sysMofSummary + " " + param.getSysMofPayMethod());
                }

                if (this.saveOrUpdate(moldOpenPfa)) {
                    return ResponseData.success("开模费付款申请提交成功.");
                } else {
                    return ResponseData.error("开模费付款申请提交失败.");
                }
            }
        } else {
            if (this.saveOrUpdate(moldOpenPfa)) {
                return ResponseData.success();
            } else {
                return ResponseData.error("");
            }
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public PageResult<RdMoldOpenPfaResult> listPage(RdMoldOpenPfaParam param) {
        Page pageContext = param.getPageContext();
        IPage<RdMoldOpenPfaResult> page = this.mapper.listPage(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdMoldOpenPfaResult> listMoldOpenPfa(RdMoldOpenPfaParam param) {
        return this.mapper.listMoldOpenPfa(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdMoldOpenPfaResult> listMoldOpenPfaArrears(RdMoldOpenPfaParam param) {
        return this.mapper.listMoldOpenPfaArrears(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData statisticsMoldOpenPfa(RdMoldOpenPfaParam param) {
        List<RdMoldOpenPfa> rdMoldOpenPfaList = this.mapper.selectList(null);

        Map result = new HashMap();
        result.put("waitSubmitCount", rdMoldOpenPfaList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysMofStatus()) && l.getSysMofStatus().equals(RdProposalEnum.MOF_STATUS_WAIT_SUBMIT.getName())).count());
        result.put("waitReviewCount", rdMoldOpenPfaList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysMofStatus()) && l.getSysMofStatus().equals(RdProposalEnum.MOF_STATUS_WAIT_REVIEW.getName())).count());
        result.put("waitPrintCount", rdMoldOpenPfaList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysMofStatus()) && (l.getSysMofStatus().equals(RdProposalEnum.MOF_STATUS_WAIT_PRINT.getName()) || l.getSysMofStatus().equals(RdProposalEnum.MOF_STATUS_WAIT_PAY.getName()))).count());
        result.put("waitPayCount", rdMoldOpenPfaList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysMofStatus()) && l.getSysMofStatus().equals(RdProposalEnum.MOF_STATUS_WAIT_PAY.getName())).count());
        result.put("waitUploadCount", rdMoldOpenPfaList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysMofStatus()) && l.getSysMofStatus().equals(RdProposalEnum.MOF_STATUS_WAIT_UPLOAD.getName())).count());
        result.put("waitApprCount", rdMoldOpenPfaList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysMofStatus()) && l.getSysMofStatus().equals(RdProposalEnum.MOF_STATUS_WAIT_APPR.getName())).count());
        result.put("waitAppCount", rdMoldOpenPfaList.stream().filter(l -> ObjectUtil.isNull(l.getSysMofStatus())).count());

        return ResponseData.success(result);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData createAndAppMoldOpenPfa(List<RdMoldOpenPfaParam> params) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            RdMoldOpenPfaParam param = params.get(0);
            if (param.getSysAppOpr().equals(RdProposalEnum.MOF_APP_DIRECT.getName())) {
                for (RdMoldOpenPfaParam rd : params) {
                    UpdateWrapper<RdMoldOpenPfa> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_MOF_CODE", rd.getSysMofCode());
                    updateWrapper.set("SYS_MOF_STATUS", RdProposalEnum.MOF_STATUS_WAIT_REVIEW.getName()).set("SYS_MOF_SUB_DATE", oprDate)
                            .set("SYS_MOF_SUB_PC", loginUser.getAccount()).set("SYS_MOF_SUB_PN", loginUser.getName());

                    this.mapper.update(null, updateWrapper);
                }
                return ResponseData.success("开模付款申请直接申请成功.");
            } else {
                for (RdMoldOpenPfaParam rd : params) {
                    UpdateWrapper<RdMoldOpenPfa> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_MOF_CODE", rd.getSysMofCode());
                    updateWrapper.set("SYS_MOF_STATUS", RdProposalEnum.MOF_STATUS_WAIT_SUBMIT.getName());

                    this.mapper.update(null, updateWrapper);
                }
                return ResponseData.success("开模付款申请创建申请成功.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData reviewMoldOpenPfa(RdMoldOpenPfaParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            UpdateWrapper<RdMoldOpenPfa> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_MOF_CODE", param.getSysMofCode());
            updateWrapper.set("SYS_MOF_AUDIT_DATE", oprDate).set("SYS_MOF_AUDIT_PER_CODE", loginUser.getAccount()).set("SYS_MOF_AUDIT_PER_NAME", loginUser.getName())
                    .set("SYS_MOF_AUDIT_RESULT", param.getSysMofAuditResult());
            if (param.getSysMofAuditResult().equals(RdProposalEnum.MOF_AUDIT_NO.getName())) {
                updateWrapper.set("SYS_MOF_AUDIT_EXPLAIN", param.getSysMofAuditExplain()).set("SYS_MOF_STATUS", RdProposalEnum.MOF_STATUS_WAIT_SUBMIT.getName());
            } else {
                updateWrapper.set("SYS_MOF_STATUS", RdProposalEnum.MOF_STATUS_WAIT_PRINT.getName());
            }
            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("开模付款申请审核成功.");
            } else {
                return ResponseData.error("开模付款申请审核失败.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData printMoldOpenPfa(RdMoldOpenPfaParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            RdMoldOpenPfa rdMoldOpenPfa = this.mapper.selectById(param.getSysMofCode());
            if (ObjectUtil.isNull(rdMoldOpenPfa.getSysMofPrintDate())) {
                rdMoldOpenPfa.setSysMofPrintDate(oprDate);
                rdMoldOpenPfa.setSysMofPrintPerCode(loginUser.getAccount());
                rdMoldOpenPfa.setSysMofPrintPerName(loginUser.getName());
                rdMoldOpenPfa.setSysMofPrintCount(BigDecimal.valueOf(1));
            } else {
                rdMoldOpenPfa.setSysMofPrintCount(rdMoldOpenPfa.getSysMofPrintCount().add(BigDecimal.valueOf(1)));
            }
            rdMoldOpenPfa.setSysMofStatus(RdProposalEnum.MOF_STATUS_WAIT_PAY.getName());

            if (this.saveOrUpdate(rdMoldOpenPfa)) {
                return ResponseData.success("开模付款申请打印成功.");
            } else {
                return ResponseData.error("开模付款申请打印失败.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData listAccountMgtPersonal() throws Exception {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        AccountMgtPersonalParam param = new AccountMgtPersonalParam();
        List<AccountMgtPersonal> accountMgtPersonals = this.proposalServiceConsumer.listAccountMgtPersonal(param);
        String account = loginUser.getAccount();
        List<AccountMgtPersonal> result = accountMgtPersonals.stream().filter(l -> l.getState().equals("enable") && l.getAccType().equals("银行卡")).collect(Collectors.toList());
        return ResponseData.success(result);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData listCompanyAccount() throws Exception {
        List<TbComEntityResult> companyAccounts = this.proposalServiceConsumer.listCompanyAccount();
        return ResponseData.success(companyAccounts);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData payMoldOpenPfa(RdMoldOpenPfaParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();

            UpdateWrapper<RdMoldOpenPfa> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_MOF_CODE", param.getSysMofCode());
            updateWrapper.set("SYS_MOF_PAY_AP", param.getSysMofPayAp()).set("SYS_MOF_PAY_RD", oprDate)
                    .set("SYS_MOF_PAY_R_PER_CODE", loginUser.getAccount()).set("SYS_MOF_PAY_R_PER_NAME", loginUser.getName())
                    .set("SYS_MOF_PAY_DATE", param.getSysMofPayDate()).set("SYS_MOF_PAY_ACCOUNT", param.getSysMofPayAccount())
                    .set("SYS_MOF_PAY_AN", param.getSysMofPayAn()).set("SYS_MOF_PAY_AOB", param.getSysMofPayAob());

            RdMoldOpenPfa rdMoldOpenPfaNew = null;

            if (ObjectUtil.isNotNull(param.getSysMofInvoiceNum()) && ObjectUtil.isNotNull(param.getSysMofTicketFile())) {
                updateWrapper.set("SYS_MOF_REVIEW_RESULT", RdProposalEnum.MOF_REVIEW_YES.getName()).set("SYS_MOF_REVIEW_DATE", param.getSysMofAuditDate())
                        .set("SYS_MOF_REVIEW_PER_CODE", param.getSysMofAuditPerCode()).set("SYS_MOF_REVIEW_PER_NAME", param.getSysMofAuditPerName())
                        .set("SYS_MOF_ARCHIVE_DATE", oprDate).set("SYS_MOF_STATUS", RdProposalEnum.MOF_STATUS_ARCH.getName());

                if (param.getSysMofPayMethod().equals("首款")){

                    rdMoldOpenPfaNew = new RdMoldOpenPfa();
                    rdMoldOpenPfaNew.setSysMofStatus("");
                    rdMoldOpenPfaNew.setSysMofCode(generatorNoUtil.getMofBillNoExtents("000","SA-KMF",3));
                    rdMoldOpenPfaNew.setSysFeeAppCodeFirst(param.getSysMofCode());
                    rdMoldOpenPfaNew.setSysCDate(oprDate);
                    rdMoldOpenPfaNew.setSysLDate(oprDate);
                    rdMoldOpenPfaNew.setSysMofPayMethod("尾款");
                    rdMoldOpenPfaNew.setSysDeptCode(param.getSysDeptCode());
                    rdMoldOpenPfaNew.setSysDeptName(param.getSysDeptName());
                    rdMoldOpenPfaNew.setSysPerCode(loginUser.getAccount());
                    rdMoldOpenPfaNew.setSysPerName(loginUser.getName());
                    rdMoldOpenPfaNew.setSysPlCode(param.getSysPlCode());
                    rdMoldOpenPfaNew.setSysSpu(param.getSysSpu());
                    rdMoldOpenPfaNew.setSysTaCode(param.getSysTaCode());
                    rdMoldOpenPfaNew.setSysTsTaskCode(param.getSysTsTaskCode());
                    rdMoldOpenPfaNew.setSysCustFebkCode(param.getSysCustFebkCode());
                    rdMoldOpenPfaNew.setSysFeeAppCode(param.getSysFeeAppCode());
                    rdMoldOpenPfaNew.setSysPmPerCode(param.getSysPmPerCode());
                    rdMoldOpenPfaNew.setSysPmPerName(param.getSysPmPerName());
                    rdMoldOpenPfaNew.setSysCfSupplierNum(param.getSysCfSupplierNum());
                    rdMoldOpenPfaNew.setSysCfSupplierName(param.getSysCfSupplierName());
                    rdMoldOpenPfaNew.setSysCfPurPerCode(param.getSysCfPurPerCode());
                    rdMoldOpenPfaNew.setSysCfPurPerName(param.getSysCfPurPerName());
                    rdMoldOpenPfaNew.setSysCfTicketType(param.getSysCfTicketType());
                    rdMoldOpenPfaNew.setSysCfAccountType(param.getSysCfAccountType());
                    rdMoldOpenPfaNew.setSysCfPayMethod(param.getSysCfPayMethod());
                    rdMoldOpenPfaNew.setSysCfAccountName(param.getSysCfAccountName());
                    rdMoldOpenPfaNew.setSysCfBankAccount(param.getSysCfBankAccount());
                    rdMoldOpenPfaNew.setSysCfBankName(param.getSysCfBankName());
                    rdMoldOpenPfaNew.setSysSaContractAmount(param.getSysSaContractAmount());
                    rdMoldOpenPfaNew.setSysSaStaContractDoc(param.getSysSaStaContractDoc());
                    rdMoldOpenPfaNew.setSysMofTitle(param.getSysMofTitle());

                    String sysMofSummary = param.getSysMofSummary().replace("全款", "").replace("首款", "");
                    rdMoldOpenPfaNew.setSysMofSummary(sysMofSummary + " " + rdMoldOpenPfaNew.getSysMofPayMethod());
                    rdMoldOpenPfaNew.setSysMofInvoiceNum(param.getSysMofInvoiceNum());
                    rdMoldOpenPfaNew.setSysMofTicketFile(param.getSysMofTicketFile());
                    rdMoldOpenPfaNew.setSysMofTicketUd(param.getSysMofTicketUd());
                    rdMoldOpenPfaNew.setSysMofTicketUpc(param.getSysMofTicketUpc());
                    rdMoldOpenPfaNew.setSysMofTicketUpn(param.getSysMofTicketUpn());
                    rdMoldOpenPfaNew.setSysMofPayAmount(param.getSysSaContractAmount().subtract(param.getSysMofPayAmount()));
                    rdMoldOpenPfaNew.setSysMofPayComp(param.getSysMofPayComp());
                }
            } else {
                updateWrapper.set("SYS_MOF_STATUS", RdProposalEnum.MOF_STATUS_WAIT_UPLOAD.getName());
            }

            RdProposal rdProposal = this.rdProposalMapper.selectById(param.getSysTaCode());
            UpdateWrapper<RdProposal> updateWrapperPro = new UpdateWrapper<>();
            updateWrapperPro.eq("SYS_TA_CODE",param.getSysTaCode());
            updateWrapperPro.set("SYS_TA_ACCU_RD_FEE",rdProposal.getSysTaAccuRdFee().add(param.getSysMofPayAmount()));

            if (ObjectUtil.isNotNull(rdMoldOpenPfaNew)){
                if (this.mapper.insert(rdMoldOpenPfaNew) > 0){
                    if (this.mapper.update(null, updateWrapper) > 0 && this.rdProposalMapper.update(null,updateWrapperPro) > 0) {
                        UpdateWrapper<RdSampleCa> rdSampleCaUpdateWrapper = new UpdateWrapper<>();
                        rdSampleCaUpdateWrapper.eq("SYS_FEE_APP_CODE",param.getSysFeeAppCode());
                        rdSampleCaUpdateWrapper.set("SYS_SA_AP_STATUS",RdProposalEnum.SA_AP_STATU_COMPLETED.getName());
                        this.rdSampleCaMapper.update(null,rdSampleCaUpdateWrapper);

                        return ResponseData.success("开模付款申请付款成功.");
                    } else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("开模付款申请付款失败.");
                    }
                }else {
                    return ResponseData.error("开模付款申请付款失败.");
                }
            }else {
                if (this.mapper.update(null, updateWrapper) > 0 && this.rdProposalMapper.update(null,updateWrapperPro) > 0) {
                    UpdateWrapper<RdSampleCa> rdSampleCaUpdateWrapper = new UpdateWrapper<>();
                    rdSampleCaUpdateWrapper.eq("SYS_FEE_APP_CODE",param.getSysFeeAppCode());
                    rdSampleCaUpdateWrapper.set("SYS_SA_AP_STATUS",RdProposalEnum.SA_AP_STATU_COMPLETED.getName());
                    this.rdSampleCaMapper.update(null,rdSampleCaUpdateWrapper);

                    return ResponseData.success("开模付款申请付款成功.");
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("开模付款申请付款失败.");
                }
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData uploadMoldOpenPfa(RdMoldOpenPfaParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();

            UpdateWrapper<RdMoldOpenPfa> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_MOF_CODE", param.getSysMofCode());

            updateWrapper.set("SYS_MOF_INVOICE_NUM", param.getSysMofInvoiceNum()).set("SYS_MOF_TICKET_FILE", param.getSysMofTicketFile())
                    .set("SYS_MOF_TICKET_UPC", loginUser.getAccount()).set("SYS_MOF_TICKET_UPN", loginUser.getName())
                    .set("SYS_MOF_TICKET_UD", oprDate).set("SYS_MOF_STATUS", RdProposalEnum.MOF_STATUS_WAIT_APPR.getName());

            if (this.mapper.update(null, updateWrapper) > 0) {
                return ResponseData.success("开模付款申请票据上传成功.");
            } else {
                return ResponseData.error("开模付款申请票据上传失败.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData apprMoldOpenPfa(RdMoldOpenPfaParam param) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();

            UpdateWrapper<RdMoldOpenPfa> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_MOF_CODE", param.getSysMofCode());

            RdMoldOpenPfa rdMoldOpenPfaNew = null;
            if (param.getSysMofReviewResult().equals(RdProposalEnum.MOF_REVIEW_YES.getName())) {
                updateWrapper.set("SYS_MOF_REVIEW_RESULT", param.getSysMofReviewResult()).set("SYS_MOF_REVIEW_DATE", oprDate)
                        .set("SYS_MOF_REVIEW_PER_CODE", loginUser.getAccount()).set("SYS_MOF_REVIEW_PER_NAME", loginUser.getName())
                        .set("SYS_MOF_ARCHIVE_DATE", oprDate).set("SYS_MOF_STATUS", RdProposalEnum.MOF_STATUS_ARCH.getName());

                if (param.getSysMofPayMethod().equals("首款")){
                    rdMoldOpenPfaNew = new RdMoldOpenPfa();
                    rdMoldOpenPfaNew.setSysMofStatus("");
                    rdMoldOpenPfaNew.setSysMofCode(generatorNoUtil.getMofBillNoExtents("000","SA-KMF",3));
                    rdMoldOpenPfaNew.setSysFeeAppCodeFirst(param.getSysMofCode());
                    rdMoldOpenPfaNew.setSysCDate(oprDate);
                    rdMoldOpenPfaNew.setSysLDate(oprDate);
                    rdMoldOpenPfaNew.setSysMofPayMethod("尾款");
                    rdMoldOpenPfaNew.setSysDeptCode(param.getSysDeptCode());
                    rdMoldOpenPfaNew.setSysDeptName(param.getSysDeptName());
                    rdMoldOpenPfaNew.setSysPerCode(loginUser.getAccount());
                    rdMoldOpenPfaNew.setSysPerName(loginUser.getName());
                    rdMoldOpenPfaNew.setSysPlCode(param.getSysPlCode());
                    rdMoldOpenPfaNew.setSysSpu(param.getSysSpu());
                    rdMoldOpenPfaNew.setSysTaCode(param.getSysTaCode());
                    rdMoldOpenPfaNew.setSysTsTaskCode(param.getSysTsTaskCode());
                    rdMoldOpenPfaNew.setSysCustFebkCode(param.getSysCustFebkCode());
                    rdMoldOpenPfaNew.setSysFeeAppCode(param.getSysFeeAppCode());
                    rdMoldOpenPfaNew.setSysPmPerCode(param.getSysPmPerCode());
                    rdMoldOpenPfaNew.setSysPmPerName(param.getSysPmPerName());
                    rdMoldOpenPfaNew.setSysCfSupplierNum(param.getSysCfSupplierNum());
                    rdMoldOpenPfaNew.setSysCfSupplierName(param.getSysCfSupplierName());
                    rdMoldOpenPfaNew.setSysCfPurPerCode(param.getSysCfPurPerCode());
                    rdMoldOpenPfaNew.setSysCfPurPerName(param.getSysCfPurPerName());
                    rdMoldOpenPfaNew.setSysCfTicketType(param.getSysCfTicketType());
                    rdMoldOpenPfaNew.setSysCfAccountType(param.getSysCfAccountType());
                    rdMoldOpenPfaNew.setSysCfPayMethod(param.getSysCfPayMethod());
                    rdMoldOpenPfaNew.setSysCfAccountName(param.getSysCfAccountName());
                    rdMoldOpenPfaNew.setSysCfBankAccount(param.getSysCfBankAccount());
                    rdMoldOpenPfaNew.setSysCfBankName(param.getSysCfBankName());
                    rdMoldOpenPfaNew.setSysSaContractAmount(param.getSysSaContractAmount());
                    rdMoldOpenPfaNew.setSysSaStaContractDoc(param.getSysSaStaContractDoc());
                    rdMoldOpenPfaNew.setSysMofTitle(param.getSysMofTitle());

                    String sysMofSummary = param.getSysMofSummary().replace("全款", "").replace("首款", "");
                    rdMoldOpenPfaNew.setSysMofSummary(sysMofSummary + " " + rdMoldOpenPfaNew.getSysMofPayMethod());
                    rdMoldOpenPfaNew.setSysMofInvoiceNum(param.getSysMofInvoiceNum());
                    rdMoldOpenPfaNew.setSysMofTicketFile(param.getSysMofTicketFile());
                    rdMoldOpenPfaNew.setSysMofTicketUd(param.getSysMofTicketUd());
                    rdMoldOpenPfaNew.setSysMofTicketUpc(param.getSysMofTicketUpc());
                    rdMoldOpenPfaNew.setSysMofTicketUpn(param.getSysMofTicketUpn());
                    rdMoldOpenPfaNew.setSysMofPayAmount(param.getSysSaContractAmount().subtract(param.getSysMofPayAmount()));
                    rdMoldOpenPfaNew.setSysMofPayComp(param.getSysMofPayComp());
                }
            } else {
                updateWrapper.set("SYS_MOF_REVIEW_RESULT", param.getSysMofReviewResult()).set("SYS_MOF_REVIEW_DATE", oprDate)
                        .set("SYS_MOF_REVIEW_PER_CODE", loginUser.getAccount()).set("SYS_MOF_REVIEW_PER_NAME", loginUser.getName())
                        .set("SYS_MOF_STATUS", RdProposalEnum.MOF_STATUS_WAIT_UPLOAD.getName());
            }

            if (ObjectUtil.isNotNull(rdMoldOpenPfaNew)){

                if (this.mapper.insert(rdMoldOpenPfaNew) > 0){
                    if (this.mapper.update(null, updateWrapper) > 0) {
                        return ResponseData.success("开模付款申请复核成功.");
                    } else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("开模付款申请复核失败.");
                    }
                }else {
                    return ResponseData.error("开模付款申请复核失败.");
                }
            }else {
                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("开模付款申请复核成功.");
                } else {
                    return ResponseData.error("开模付款申请复核失败.");
                }
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdMoldOpenPfaResult detail(RdMoldOpenPfaParam param) {
        return this.mapper.detail(param);
    }

}
