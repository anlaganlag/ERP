package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
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
import com.tadpole.cloud.product.api.productproposal.entity.RdExpenseReimburse;
import com.tadpole.cloud.product.api.productproposal.entity.RdExpenseReimburseDet;
import com.tadpole.cloud.product.api.productproposal.entity.RdMoldOpenPfa;
import com.tadpole.cloud.product.api.productproposal.model.params.RdExpenseReimburseParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdMoldOpenPfaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdExpenseReimburseDetResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdExpenseReimburseResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdProposalExtentResult;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.consumer.RdPreProposalServiceConsumer;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdExpenseReimburseDetMapper;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdExpenseReimburseMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdExpenseReimburseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 提案-研发费报销 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
@Service
public class RdExpenseReimburseServiceImpl extends ServiceImpl<RdExpenseReimburseMapper, RdExpenseReimburse> implements IRdExpenseReimburseService {

    @Resource
    private RdExpenseReimburseMapper mapper;

    @Resource
    private RdExpenseReimburseDetMapper rdExpenseReimburseDetMapper;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private RdPreProposalServiceConsumer oaServiceConsumer;

    @DataSource(name = "product")
    @Override
    @Transactional
    public PageResult<RdExpenseReimburseResult> listPage(RdExpenseReimburseParam param) {
        Page pageContext = param.getPageContext();

        IPage<RdExpenseReimburseResult> page = this.mapper.listPage(pageContext, param);

        return new PageResult<>(page);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdExpenseReimburseResult> listExpenseReimburse(RdExpenseReimburseParam param) {
        return this.mapper.listExpenseReimburse(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData statisticsExpenseReimburse(RdExpenseReimburseParam param) {
        List<RdExpenseReimburse> rdExpenseReimburseList = this.mapper.selectList(null);

        Map result = new HashMap();
        result.put("waitSubmitCount", rdExpenseReimburseList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysSaeaStatus()) && l.getSysSaeaStatus().equals(RdProposalEnum.SAEA_STATUS_WAIT_SUBMIT.getName())).count());
        result.put("waitReviewCount", rdExpenseReimburseList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysSaeaStatus()) && l.getSysSaeaStatus().equals(RdProposalEnum.SAEA_STATUS_WAIT_REVIEW.getName())).count());
        result.put("waitPrintCount", rdExpenseReimburseList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysSaeaStatus()) && (l.getSysSaeaStatus().equals(RdProposalEnum.SAEA_STATUS_WAIT_PRINT.getName()) || l.getSysSaeaStatus().equals(RdProposalEnum.SAEA_STATUS_WAIT_PAY.getName()))).count());
        result.put("waitPayCount", rdExpenseReimburseList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysSaeaStatus()) && l.getSysSaeaStatus().equals(RdProposalEnum.SAEA_STATUS_WAIT_PAY.getName())).count());

        return ResponseData.success(result);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdExpenseReimburseResult detail(RdExpenseReimburseParam param) {

        RdExpenseReimburseResult rdExpenseReimburseResult = this.mapper.detail(param);

        QueryWrapper<RdExpenseReimburseDet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_SAEA_CODE", rdExpenseReimburseResult.getSysSaeaCode());

        List<RdExpenseReimburseDetResult> rdExpenseReimburseDetResults = new ArrayList<>();

        List<RdExpenseReimburseDet> rdExpenseReimburseDets = this.rdExpenseReimburseDetMapper.selectList(queryWrapper);
        rdExpenseReimburseDets.forEach(l -> {
            rdExpenseReimburseDetResults.add(BeanUtil.copyProperties(l, RdExpenseReimburseDetResult.class));
        });
        rdExpenseReimburseResult.setRdExpenseReimburseDetResultList(rdExpenseReimburseDetResults);

        return rdExpenseReimburseResult;
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdExpenseReimburseResult useLastRecipientAccount(RdExpenseReimburseParam param) {
        return this.mapper.useLastRecipientAccount(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData refreshExpenseReimburseDet(RdExpenseReimburseParam param) {

        if (ObjectUtil.isNull(param.getSysSaeaAccount())) {
            return ResponseData.error("请选择报销账户.");
        }
        if (ObjectUtil.isNotNull(param.getSysSaeaDateList()) && param.getSysSaeaDateList().size() == 0) {
            return ResponseData.error("请选择报销范围.");
        }

        if (ObjectUtil.isNotNull(param.getSysSaeaCode())) {
            //删除旧有明细
            QueryWrapper<RdExpenseReimburseDet> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_SAEA_CODE", param.getSysSaeaCode());
            this.rdExpenseReimburseDetMapper.delete(queryWrapper);

            UpdateWrapper<RdExpenseReimburse> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_SAEA_CODE", param.getSysSaeaCode());
            updateWrapper.set("SYS_SAEA_TITLE", "").set("SYS_SAEA_SUMMARY", "").set("SYS_SAEA_BUY_AMOUNT", "").set("SYS_SAEA_SAMPLE_AMOUNT", "")
                    .set("SYS_SAEA_SAMPLE_REF_QTY", "").set("SYS_SAEA_REF_AMOUNT", "").set("SYS_SAEA_AMOUNT", "");
            this.mapper.update(null, updateWrapper);
        }

        List<RdExpenseReimburseDetResult> rdExpenseReimburseDetResults = this.rdExpenseReimburseDetMapper.listExpenseReimburseDet(param);
        List<RdExpenseReimburseDetResult> expenditures = rdExpenseReimburseDetResults.stream().filter(l -> l.getSysSaeaType().equals("支出")).collect(Collectors.toList());
        List<RdExpenseReimburseDetResult> refunds = rdExpenseReimburseDetResults.stream().filter(l -> l.getSysSaeaType().equals("退款")).collect(Collectors.toList());

        BigDecimal expenditureTotal = expenditures.stream().map(l -> l.getSysSaeaRealAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal refundTotal = refunds.stream().map(l -> l.getSysSaeaRealAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (expenditureTotal.compareTo(refundTotal) <= 0) {
            return ResponseData.error("报销金额小于或等于0，不允许申请！");
        } else {
            return ResponseData.success(rdExpenseReimburseDetResults);
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData autoAnalysis(RdExpenseReimburseParam param) {
        if (ObjectUtil.isNull(param.getSysSaeaAccount())) {
            return ResponseData.error("请选择报销账户.");
        }

        if (ObjectUtil.isNotNull(param.getSysSaeaCode())) {
            //删除旧有明细
            QueryWrapper<RdExpenseReimburseDet> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_SAEA_CODE", param.getSysSaeaCode());
            this.rdExpenseReimburseDetMapper.delete(queryWrapper);

            UpdateWrapper<RdExpenseReimburse> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_SAEA_CODE", param.getSysSaeaCode());
            updateWrapper.set("SYS_SAEA_TITLE", "").set("SYS_SAEA_SUMMARY", "").set("SYS_SAEA_BUY_AMOUNT", "").set("SYS_SAEA_SAMPLE_AMOUNT", "")
                    .set("SYS_SAEA_SAMPLE_REF_QTY", "").set("SYS_SAEA_REF_AMOUNT", "").set("SYS_SAEA_AMOUNT", "");
            this.mapper.update(null, updateWrapper);
        }

        List<RdExpenseReimburseDetResult> rdExpenseReimburseDetResults = this.rdExpenseReimburseDetMapper.listExpenseReimburseDet(param);
        List<RdExpenseReimburseDetResult> expenditures = rdExpenseReimburseDetResults.stream().filter(l -> l.getSysSaeaType().equals("支出")).collect(Collectors.toList());
        List<RdExpenseReimburseDetResult> refunds = rdExpenseReimburseDetResults.stream().filter(l -> l.getSysSaeaType().equals("退款")).collect(Collectors.toList());

        BigDecimal expenditureTotal = expenditures.stream().map(l -> l.getSysSaeaRealAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal refundTotal = refunds.stream().map(l -> l.getSysSaeaRealAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (expenditureTotal.compareTo(refundTotal) <= 0) {
            return ResponseData.error("报销金额小于或等于0，不允许申请！");
        } else {
            return ResponseData.success(rdExpenseReimburseDetResults);
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData addOrEdit(RdExpenseReimburseParam param) {
        try {

            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();

            if (ObjectUtil.isNull(hrm)) {
                ResponseData.error("当前登陆账户,在OA系统里面未查询到,请确认!");
            }

            RdExpenseReimburse rdExpenseReimburse = BeanUtil.copyProperties(param, RdExpenseReimburse.class);
            rdExpenseReimburse.setSysCDate(oprDate);
            rdExpenseReimburse.setSysLDate(oprDate);
            rdExpenseReimburse.setSysDeptCode(hrm.getIdall());
            rdExpenseReimburse.setSysDeptName(hrm.getDepartmentname());
            rdExpenseReimburse.setSysPerCode(loginUser.getAccount());
            rdExpenseReimburse.setSysPerName(loginUser.getName());

            if (ObjectUtil.isNull(param.getSysSaeaCode())) {
                rdExpenseReimburse.setSysSaeaCode(generatorNoUtil.getSaeaBillNoExtents("00", "SA-YFF", 2));
            }
            rdExpenseReimburse.setSysSaeaStatus(RdProposalEnum.SAEA_STATUS_WAIT_REVIEW.getName());
            rdExpenseReimburse.setSysSaeaPerCode(loginUser.getAccount());
            rdExpenseReimburse.setSysSaeaPerName(loginUser.getName());
            rdExpenseReimburse.setSysSaeaAppDate(oprDate);
            rdExpenseReimburse.setSysSaeaTitle("CW28-报销单-" + loginUser.getName() + "-" + new SimpleDateFormat("yyyy-MM-dd").format(oprDate));
            rdExpenseReimburse.setSysSaeaSummary(new SimpleDateFormat("yyyy-MM-dd").format(rdExpenseReimburse.getSysSaeaStartDate()) + "~" + new SimpleDateFormat("yyyy-MM-dd").format(rdExpenseReimburse.getSysSaeaEndDate()) + " 开发样采购费用");
            rdExpenseReimburse.setSysSaeaBuyAmount(param.getRdExpenseReimburseDetParams().stream().filter(l -> l.getSysSaeaType().equals("支出") && l.getSysSaeaSource().equals("购样申请")).map(n -> n.getSysSaeaRealAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
            rdExpenseReimburse.setSysSaeaSampleAmount(param.getRdExpenseReimburseDetParams().stream().filter(l -> l.getSysSaeaType().equals("支出") && l.getSysSaeaSource().equals("定制申请")).map(n -> n.getSysSaeaRealAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
            rdExpenseReimburse.setSysSaeaSampleRefQty(param.getRdExpenseReimburseDetParams().stream().filter(l -> l.getSysSaeaType().equals("退款") && l.getSysSaeaSource().equals("购样申请")).map(n -> n.getSysSaeaSampleQty()).reduce(BigDecimal.ZERO, BigDecimal::add));
            rdExpenseReimburse.setSysSaeaRefAmount(param.getRdExpenseReimburseDetParams().stream().filter(l -> l.getSysSaeaType().equals("退款")).map(n -> n.getSysSaeaRealAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
            rdExpenseReimburse.setSysSaeaAuditResult("");
            rdExpenseReimburse.setSysSaeaAuditExplain("");
            rdExpenseReimburse.setSysSaeaAuditPc("");
            rdExpenseReimburse.setSysSaeaAuditPn("");

            List<RdExpenseReimburseDet> rdExpenseReimburseDets = new ArrayList<>();
            param.getRdExpenseReimburseDetParams().forEach(l -> {
                l.setSysSaeaCode(rdExpenseReimburse.getSysSaeaCode());
                l.setId(UUID.randomUUID().toString().replace("-", ""));
                rdExpenseReimburseDets.add(BeanUtil.copyProperties(l, RdExpenseReimburseDet.class));
            });

            //删除旧有明细
            QueryWrapper<RdExpenseReimburseDet> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_SAEA_CODE", rdExpenseReimburse.getSysSaeaCode());
            this.rdExpenseReimburseDetMapper.delete(queryWrapper);

            if (this.saveOrUpdate(rdExpenseReimburse)) {

                for (RdExpenseReimburseDet det : rdExpenseReimburseDets) {
                    this.rdExpenseReimburseDetMapper.insert(det);
                }

                return ResponseData.success("研发费报销申请成功.");
            } else {
                return ResponseData.error("研发费报销申请失败.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }


    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData reviewExpenseReimburse(RdExpenseReimburseParam param) {
        try {

            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();

            UpdateWrapper<RdExpenseReimburse> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_SAEA_CODE", param.getSysSaeaCode());

            updateWrapper.set("SYS_SAEA_AUDIT_DATE", oprDate).set("SYS_SAEA_AUDIT_RESULT", param.getSysSaeaAuditResult())
                    .set("SYS_SAEA_AUDIT_PC", loginUser.getAccount()).set("SYS_SAEA_AUDIT_PN", loginUser.getName());

            if (param.getSysSaeaAuditResult().equals(RdProposalEnum.SAEA_AUDIT_RESULT_YES.getName())) {
                updateWrapper.set("SYS_SAEA_STATUS", RdProposalEnum.SAEA_STATUS_WAIT_PRINT.getName());
            } else {
                updateWrapper.set("SYS_SAEA_STATUS", RdProposalEnum.SAEA_STATUS_WAIT_SUBMIT.getName()).set("SYS_SAEA_AUDIT_EXPLAIN", param.getSysSaeaAuditExplain());
            }

            if (this.mapper.update(null, updateWrapper) > 0) {

                return ResponseData.success("研发费报销审核成功.");
            } else {
                return ResponseData.error("研发费报销审核成功.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }


    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData printExpenseReimburse(RdExpenseReimburseParam param) {
        try {

            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            UpdateWrapper<RdExpenseReimburse> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_SAEA_CODE", param.getSysSaeaCode());

            RdExpenseReimburse rdExpenseReimburse = this.mapper.selectById(param.getSysSaeaCode());
            if (ObjectUtil.isNull(rdExpenseReimburse.getSysSaeaPrintDate())) {
                updateWrapper.set("SYS_SAEA_PRINT_DATE", oprDate).set("SYS_SAEA_PRINT_QTY", 1)
                        .set("SYS_SAEA_PRINT_PC", loginUser.getAccount()).set("SYS_SAEA_PRINT_PN", loginUser.getName());
            } else {
                updateWrapper.set("SYS_SAEA_PRINT_QTY", rdExpenseReimburse.getSysSaeaPrintQty().add(new BigDecimal("1")));
            }

            updateWrapper.set("SYS_SAEA_STATUS", RdProposalEnum.SAEA_STATUS_WAIT_PAY.getName());

            if (this.mapper.update(null, updateWrapper) > 0) {

                return ResponseData.success("研发费报销打印成功.");
            } else {
                return ResponseData.error("研发费报销打印成功.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }


    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData payExpenseReimburse(RdExpenseReimburseParam param) {
        try {

            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            UpdateWrapper<RdExpenseReimburse> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("SYS_SAEA_CODE", param.getSysSaeaCode());

            updateWrapper.set("SYS_SAEA_PAY_DATE", param.getSysSaeaPayDate()).set("SYS_SAEA_PAY_AMOUNT", param.getSysSaeaAmount())
                    .set("SYS_SAEA_PAY_COMP", param.getSysSaeaPayComp()).set("SYS_SAEA_PAY_AP", "银行卡")
                    .set("SYS_SAEA_PAY_AO", "公司账户").set("SYS_SAEA_PAY_AT", param.getSysSaeaPayAt()).set("SYS_SAEA_PAY_AN", param.getSysSaeaPayAn())
                    .set("SYS_SAEA_PAY_ACCOUNT", param.getSysSaeaPayAccount()).set("SYS_SAEA_PAY_AOB", param.getSysSaeaPayAob())
                    .set("SYS_SAEA_PAY_RD", oprDate).set("SYS_SAEA_PAY_RPC", loginUser.getAccount()).set("SYS_SAEA_PAY_RPN", loginUser.getName());

            updateWrapper.set("SYS_SAEA_STATUS", RdProposalEnum.SAEA_STATUS_ARCH.getName()).set("SYS_SAEA_ARCHIVE_DATE", oprDate);

            if (this.mapper.update(null, updateWrapper) > 0) {

                return ResponseData.success("研发费报销付款成功.");
            } else {
                return ResponseData.error("研发费报销付款成功.");
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }


    }

}
