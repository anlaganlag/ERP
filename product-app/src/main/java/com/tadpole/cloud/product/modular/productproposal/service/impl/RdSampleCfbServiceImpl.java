package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.product.api.productproposal.entity.*;
import com.tadpole.cloud.product.api.productproposal.model.params.RdRefRegistParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCfbParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCfbResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.consumer.RdPreProposalServiceConsumer;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.*;
import com.tadpole.cloud.product.modular.productproposal.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

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
 * 提案-定制反馈 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Service
public class RdSampleCfbServiceImpl extends ServiceImpl<RdSampleCfbMapper, RdSampleCfb> implements IRdSampleCfbService {

    @Resource
    private RdSampleCfbMapper mapper;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private RdPreProposalServiceConsumer oaServiceConsumer;

    @Resource
    private IRdSampleTaskService sampleTaskService;

    @Resource
    private IRdSampleCaService sampleCaService;

    @Resource
    private IRdFarSettingService farSettingService;

    @Resource
    private IRdRefRegistService rdRefRegistService;

    @Resource
    private RdSampleCaMapper rdSampleCaMapper;

    @Resource
    private RdMoldOpenPfaMapper rdMoldOpenPfaMapper;

    @Resource
    private RdSampleTaskMapper rdSampleTaskMapper;

    @Resource
    private RdRefRegistMapper refRegistMapper;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData addOrEdit(RdSampleCfbParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        Date oprDate = new Date();
        HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();

        RdSampleCfb rdSampleCfb = BeanUtil.copyProperties(param, RdSampleCfb.class);
        rdSampleCfb.setSysCDate(oprDate);
        rdSampleCfb.setSysLDate(oprDate);
        rdSampleCfb.setSysDeptCode(hrm.getIdall());
        rdSampleCfb.setSysDeptName(hrm.getDepartmentname());
        rdSampleCfb.setSysPerCode(loginUser.getAccount());
        rdSampleCfb.setSysPerName(loginUser.getName());
        rdSampleCfb.setSysCfPurPerCode(loginUser.getAccount());
        rdSampleCfb.setSysCfPurPerName(loginUser.getName());

        if (param.getSysPageOpr().equals(RdProposalEnum.CF_PAGE_NEW.getName())) {
            rdSampleCfb.setSysCustFebkCode(generatorNoUtil.getCustFeedbackBillNoExtents("000", "Task-FB", 3));
        }

        if (param.getSysFuncOpr().equals(RdProposalEnum.CF_FUNC_SUBMIT.getName())) {
            rdSampleCfb.setSysCfStatus(RdProposalEnum.CF_STATUS_WAIT_DEV_FB.getName());
            rdSampleCfb.setSysCfSubDate(oprDate);
            rdSampleCfb.setSysCfSubPerCode(loginUser.getAccount());
            rdSampleCfb.setSysCfSubPerName(loginUser.getName());
        } else {
            rdSampleCfb.setSysCfStatus(RdProposalEnum.CF_STATUS_WAIT_SUBMIT.getName());
        }

        if (this.saveOrUpdate(rdSampleCfb)) {
            return ResponseData.success("定制反馈保存/提交成功");
        } else {
            return ResponseData.success("定制反馈保存/提交失败");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdSampleCfbResult> listSampleCfb(RdSampleCfbParam param) {
        //param.setSysIsRdPageOpr("");

        return this.mapper.listSampleCfb(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData checkIsCanCreateFeedback(RdSampleCfbParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        QueryWrapper<RdSampleCfb> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());
        queryWrapper.eq("SYS_CF_STATUS", RdProposalEnum.CF_STATUS_WAIT_SUBMIT.getName());
        queryWrapper.eq("SYS_PER_CODE", loginUser.getAccount());

        if (this.mapper.selectCount(queryWrapper) >= 1) {
            return ResponseData.error("当前操作人在系统里已存在一条待提交的定制反馈,不能再进行定制反馈登记.");
        } else {
            RdSampleCaParam caParam = new RdSampleCaParam();
            caParam.setSysTsTaskCode(param.getSysTsTaskCode());
            ResponseData responseData = this.sampleCaService.checkIsCanCreateFeedback(caParam);
            if (responseData.getSuccess() && responseData.getCode().equals(500)) {
                return ResponseData.error("当前定制任务在系统里已生成定制申请,不能再进行定制反馈登记.");
            } else {
                return ResponseData.success();
            }
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ResponseData custApplicationFeedback(List<RdSampleCfbParam> params) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();

            Date oprDate = new Date();
            boolean isAutoApprove = false;
            ResponseData responseData = null;
            if (params.stream().filter(l -> l.getSysCfDevResult().equals(RdProposalEnum.CF_DEV_RESULT_YES.getName())).count() > 0) {
                RdSampleCfbParam param = params.stream().filter(l -> l.getSysCfDevResult().equals(RdProposalEnum.CF_DEV_RESULT_YES.getName())).findFirst().get();

                HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();
                RdSampleCaParam rdSampleCaParam = new RdSampleCaParam();
                rdSampleCaParam.setSysFeeAppCode(generatorNoUtil.getCaBillNoExtents("0000", "FeeApp", 4));
                rdSampleCaParam.setSysCDate(oprDate);
                rdSampleCaParam.setSysLDate(oprDate);
                rdSampleCaParam.setSysDeptCode(hrm.getIdall());
                rdSampleCaParam.setSysDeptName(hrm.getDepartmentname());
                rdSampleCaParam.setSysPerCode(loginUser.getAccount());
                rdSampleCaParam.setSysPerName(loginUser.getName());

                if (param.getSysCfIsMoldOpen().equals(RdProposalEnum.CF_IS_MOLD_OPEN_YES.getName())) {
                    rdSampleCaParam.setSysSaApStatus(RdProposalEnum.SA_AP_STATU_CTBU.getName());
                } else {
                    rdSampleCaParam.setSysSaApStatus(RdProposalEnum.SA_AP_STATU_PA.getName());

                    //自动过审设置
                    List<RdFarSetting> rdFarSettings = farSettingService.listPage();
                    RdFarSetting farSettingReview = rdFarSettings.stream().filter(l -> l.getSysSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName()) && l.getSysAuditProcess().equals("审核")).findFirst().get();
                    RdFarSetting farSettingAppr = rdFarSettings.stream().filter(l -> l.getSysSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName()) && l.getSysAuditProcess().equals("审批")).findFirst().get();

                    //当前提案发布的任务（现货拿样）
                    RdSampleTaskParam sampleTaskParam = new RdSampleTaskParam();
                    sampleTaskParam.setSysTaCode(param.getSysTaCode());
                    sampleTaskParam.setSysTsSampleMethod(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName());
                    List<RdSampleTaskExtentResult> sampleTasks = sampleTaskService.listPage(sampleTaskParam);

                    //当前提案发布的所有现货拿样费用合计
                    QueryWrapper<RdSampleCfb> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
                    queryWrapper.in("SYS_TS_TASK_CODE", sampleTasks.stream().map(RdSampleTaskExtentResult::getSysTsTaskCode).collect(Collectors.toList()));
                    List<RdSampleCfb> rdSampleCfbs = this.mapper.selectList(queryWrapper);

                    BigDecimal totalFee = rdSampleCfbs.stream().filter(l -> l.getSysCfStatus().equals(RdProposalEnum.CF_STATUS_DEV_FB.getName()) && l.getSysCfDevResult().equals(RdProposalEnum.CF_DEV_RESULT_YES.getName()))
                            .map(RdSampleCfb::getSysCfFeeTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

                    //审批
                    if (param.getSysCfFeeTotal().compareTo(farSettingAppr.getSysSingleAppAmount()) <= 0 &&
                            totalFee.compareTo(farSettingAppr.getSysAppTotalAmount()) <= 0 &&
                            BigDecimal.valueOf(sampleTasks.size()).compareTo(farSettingAppr.getSysTaskQty()) <= 0) {
                        rdSampleCaParam.setSysSaAppResult(RdProposalEnum.SA_APP_RESULT_YES.getName());
                        rdSampleCaParam.setSysSaAppDate(oprDate);
                        rdSampleCaParam.setSysSaAppPerCode(RdProposalEnum.TA_SYS_DEFAULT_CODE.getName());
                        rdSampleCaParam.setSysSaAppPerName(RdProposalEnum.TA_SYS_DEFAULT_NAME.getName());
                        rdSampleCaParam.setSysSaApStatus(RdProposalEnum.SA_AP_STATU_TBP.getName());

                        if (ObjectUtil.isNotNull(param.getSysCfIsRefund()) && param.getSysCfIsRefund().equals(RdProposalEnum.CF_IS_REFUND_YES.getName())) {
                            isAutoApprove = true;
                        }
                    }

                }

                rdSampleCaParam.setSysPlCode(param.getSysPlCode());
                rdSampleCaParam.setSysSpu(param.getSysSpu());
                rdSampleCaParam.setSysTaCode(param.getSysTaCode());
                rdSampleCaParam.setSysTsTaskCode(param.getSysTsTaskCode());
                rdSampleCaParam.setSysCustFebkCode(param.getSysCustFebkCode());
                rdSampleCaParam.setSysSaApExplain(param.getSysCfDevExplain());
                rdSampleCaParam.setSysSaApDate(oprDate);
                rdSampleCaParam.setSysSaApPerCode(loginUser.getAccount());
                rdSampleCaParam.setSysSaApPerName(loginUser.getName());

                RdSampleCa rdSampleCa = BeanUtil.copyProperties(rdSampleCaParam, RdSampleCa.class);


                boolean rdSampleCaResult = false;
                boolean rdRefRegistResult = false;
                boolean rdSampleTaskResult =false;
                rdSampleCaResult = this.rdSampleCaMapper.insert(rdSampleCa) > 0;

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
                    rdRefRegistParam.setSysFeeAppCode(rdSampleCaParam.getSysFeeAppCode());
                    rdRefRegistParam.setSysFeeAppSource(RdProposalEnum.FEE_APP_SOURCE_DZSQ.getName());
                    rdRefRegistParam.setSysRefFeeType(RdProposalEnum.REF_FEE_TYPE_DYF.getName());
                    rdRefRegistParam.setSysRefFees(param.getSysCfFeeTotal());
                    rdRefRegistParam.setSysRefSupplierCode(param.getSysCfSupplierNum());
                    rdRefRegistParam.setSysRefSupplierName(param.getSysCfSupplierName());
                    rdRefRegistParam.setSysRefType(param.getSysCfRefundType());
                    rdRefRegistParam.setSysRefCondition(param.getSysCfRefundCondition());
                    rdRefRegistParam.setSysRefPurPerCode(param.getSysCfPurPerCode());
                    rdRefRegistParam.setSysRefPurPerName(param.getSysCfPurPerName());
                    RdRefRegist rdRefRegist = BeanUtil.copyProperties(rdRefRegistParam, RdRefRegist.class);
                    rdRefRegistResult = this.refRegistMapper.insert(rdRefRegist) > 0;

                    for (RdSampleCfbParam cfbParam : params) {
                        UpdateWrapper<RdSampleCfb> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("SYS_CUST_FEBK_CODE", cfbParam.getSysCustFebkCode());
                        updateWrapper.set("SYS_CF_STATUS", RdProposalEnum.CF_STATUS_DEV_FB.getName()).set("SYS_CF_DEV_RESULT", cfbParam.getSysCfDevResult())
                                .set("SYS_CF_DEV_EXPLAIN", ObjectUtil.isNotNull(cfbParam.getSysCfDevExplain())?cfbParam.getSysCfDevExplain():"").set("SYS_CF_DEV_DATE", oprDate)
                                .set("SYS_CF_DEV_PER_CODE", loginUser.getAccount()).set("SYS_CF_DEV_PER_NAME", loginUser.getName());

                        this.mapper.update(null, updateWrapper);
                    }

                    //更新任务进度
                    UpdateWrapper<RdSampleTask> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_TS_TASK_CODE",param.getSysTsTaskCode());
                    if (param.getSysCfIsMoldOpen().equals(RdProposalEnum.CF_IS_MOLD_OPEN_YES.getName())) {
                        updateWrapper.set("SYS_TS_TASK_PROGRESS",RdProposalEnum.SAMPLE_PROCESS_WAIT_CONT.getName());
                    } else {
                        updateWrapper.set("SYS_TS_TASK_PROGRESS",RdProposalEnum.SAMPLE_PROCESS_WAIT_APPR.getName());
                    }
                    rdSampleTaskResult = this.rdSampleTaskMapper.update(null,updateWrapper) > 0;

                    if (rdSampleCaResult && rdRefRegistResult && rdSampleTaskResult){
                        return ResponseData.success("定制申请反馈成功.");
                    }else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("定制申请反馈失败.");
                    }

                } else {
                    for (RdSampleCfbParam cfbParam : params) {
                        UpdateWrapper<RdSampleCfb> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("SYS_CUST_FEBK_CODE", cfbParam.getSysCustFebkCode());
                        updateWrapper.set("SYS_CF_STATUS", RdProposalEnum.CF_STATUS_DEV_FB.getName()).set("SYS_CF_DEV_RESULT", cfbParam.getSysCfDevResult())
                                .set("SYS_CF_DEV_EXPLAIN", ObjectUtil.isNotNull(cfbParam.getSysCfDevExplain())?cfbParam.getSysCfDevExplain():"").set("SYS_CF_DEV_DATE", oprDate)
                                .set("SYS_CF_DEV_PER_CODE", loginUser.getAccount()).set("SYS_CF_DEV_PER_NAME", loginUser.getName());

                        this.mapper.update(null, updateWrapper);
                    }

                    //更新任务进度
                    UpdateWrapper<RdSampleTask> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_TS_TASK_CODE",param.getSysTsTaskCode());
                    if (param.getSysCfIsMoldOpen().equals(RdProposalEnum.CF_IS_MOLD_OPEN_YES.getName())) {
                        updateWrapper.set("SYS_TS_TASK_PROGRESS",RdProposalEnum.SAMPLE_PROCESS_WAIT_CONT.getName());
                    } else {
                        updateWrapper.set("SYS_TS_TASK_PROGRESS",RdProposalEnum.SAMPLE_PROCESS_WAIT_APPR.getName());
                    }
                    rdSampleTaskResult = this.rdSampleTaskMapper.update(null,updateWrapper) > 0;

                    if (rdSampleCaResult && rdSampleTaskResult){
                        return ResponseData.success("定制申请反馈成功.");
                    }else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("定制申请反馈失败.");
                    }

                }

            } else {
                for (RdSampleCfbParam cfbParam : params) {
                    UpdateWrapper<RdSampleCfb> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_CUST_FEBK_CODE", cfbParam.getSysCustFebkCode());
                    updateWrapper.set("SYS_CF_STATUS", RdProposalEnum.CF_STATUS_DEV_FB.getName()).set("SYS_CF_DEV_RESULT", cfbParam.getSysCfDevResult())
                            .set("SYS_CF_DEV_EXPLAIN", cfbParam.getSysCfDevExplain()).set("SYS_CF_DEV_DATE", oprDate)
                            .set("SYS_CF_DEV_PER_CODE", loginUser.getAccount()).set("SYS_CF_DEV_PER_NAME", loginUser.getName());

                    this.mapper.update(null, updateWrapper);
                }

                //更新任务进度以及提案进度
                RdSampleTaskParam taskParam = new RdSampleTaskParam();
                taskParam.setSysTsTaskCode(params.get(0).getSysTsTaskCode());
                taskParam.setSysTsCloseReason(params.get(0).getSysCfDevExplain());
                this.sampleTaskService.closeBySysAuto(taskParam);

                return ResponseData.success("定制申请反馈成功.");
            }
        }catch (Exception exception){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleTaskExtentResult> listPageApprove(RdSampleTaskParam param) {
        return this.mapper.listPageApprove(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData custFeedback(RdSampleCfbParam param) {

        try {

            ResponseData responseData = null;
            if (param.getSysFuncOpr().equals(RdProposalEnum.CF_FUNC_SUBMIT.getName())) {

                //校验是不是反馈超时
                QueryWrapper<RdSampleTask> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());
                RdSampleTask rdSampleTask = this.sampleTaskService.list(queryWrapper).stream().findFirst().get();

                Date oprDate = new Date();
                if (oprDate.after(rdSampleTask.getSysTsFebkDeadline())) {
                    return ResponseData.error("定制反馈已超过反馈截止时间,不能进行反馈.");
                }

                //校验是否有未提交的反馈以及是否已有已生成定制申请的反馈
                responseData = this.checkIsCanCreateFeedback(param);
                if (responseData.getSuccess() && responseData.getCode().equals(500)) {
                    return responseData;
                }
            }

            responseData = this.addOrEdit(param);
            if (responseData.getSuccess() && responseData.getCode().equals(200) && param.getSysFuncOpr().equals(RdProposalEnum.CF_FUNC_SUBMIT.getName())) {

                //更新拿样任务进度
                RdSampleTaskParam rdSampleTaskParam = new RdSampleTaskParam();
                rdSampleTaskParam.setSysTsTaskCode(param.getSysTsTaskCode());
                rdSampleTaskParam.setSysTsTaskProgress(RdProposalEnum.SAMPLE_PROCESS_WAIT_APP.getName());
                this.sampleTaskService.changeStatus(rdSampleTaskParam);
            }

            return responseData;
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdSampleCfbResult detail(RdSampleCfbParam param) {
        return this.mapper.detail(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData statisticsRdSampleCa(RdSampleCfbParam param) {

        QueryWrapper<RdSampleCfb> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode()).eq("SYS_CF_DEV_RESULT", RdProposalEnum.CF_DEV_RESULT_YES.getName());
        RdSampleCfb rdSampleCfb = mapper.selectOne(queryWrapper);
        Map result = new HashMap();
        if (ObjectUtil.isNull(rdSampleCfb)) {
            result.put("sysTsAccuRdFee", BigDecimal.ZERO);
        } else {
            QueryWrapper<RdSampleCa> queryWrapperCa = new QueryWrapper<>();
            queryWrapperCa.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode()).eq("SYS_CUST_FEBK_CODE", rdSampleCfb.getSysCustFebkCode());
            RdSampleCa rdSampleCa = rdSampleCaMapper.selectOne(queryWrapperCa);
            if (rdSampleCfb.getSysCfIsMoldOpen().equals(RdProposalEnum.CF_IS_MOLD_OPEN_NO.getName())) {
                if (ObjectUtil.isNull(rdSampleCa)) {
                    result.put("sysTsAccuRdFee", BigDecimal.ZERO);
                } else {
                    result.put("sysTsAccuRdFee", ObjectUtil.isNotNull(rdSampleCa.getSysSaPayAmount()) ? rdSampleCa.getSysSaPayAmount() : BigDecimal.ZERO);
                }
            } else {
                QueryWrapper<RdMoldOpenPfa> queryWrapperOpen = new QueryWrapper<>();
                queryWrapperOpen.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode()).eq("SYS_CUST_FEBK_CODE", rdSampleCfb.getSysCustFebkCode())
                        .eq("SYS_FEE_APP_CODE", rdSampleCa.getSysFeeAppCode());
                List<RdMoldOpenPfa> rdMoldOpenPfas = rdMoldOpenPfaMapper.selectList(queryWrapperOpen);

                if (ObjectUtil.isNotNull(rdMoldOpenPfas) && rdMoldOpenPfas.size() > 0) {
                    result.put("sysTsAccuRdFee", rdMoldOpenPfas.stream().filter(l -> ObjectUtil.isNotNull(l.getSysMofPayAmount())).map(l -> l.getSysMofPayAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
                } else {
                    result.put("sysTsAccuRdFee", BigDecimal.ZERO);
                }
            }
        }
        return ResponseData.success(result);
    }
}
