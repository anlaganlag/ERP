package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.AccountMgtPersonal;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.AccountMgtPersonalParam;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleTask;
import com.tadpole.cloud.product.api.productproposal.model.params.*;
import com.tadpole.cloud.product.api.productproposal.model.result.RdFeePayResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCfbResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSamplePaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import com.tadpole.cloud.product.modular.consumer.ProposalServiceConsumer;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdFeePaymentMapper;
import com.tadpole.cloud.product.modular.productproposal.mapper.SampleTakeOverTimeDealMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdFeePaymentService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdProposalService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleManageService;
import com.tadpole.cloud.product.modular.productproposal.service.ISampleTakeOverTimeDealService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class SampleTakeOverTimeDealServiceImpl implements ISampleTakeOverTimeDealService {

    @Resource
    private SampleTakeOverTimeDealMapper mapper;

    @Resource
    private ProposalServiceConsumer proposalServiceConsumer;

    @Resource
    private IRdSampleManageService sampleManageService;

    @Resource
    private IRdProposalService proposalService;

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleTaskExtentResult> listTimeoutTask(RdSampleTaskParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        param.setSysPerCode(loginUser.getAccount());

        if (ObjectUtil.isNotNull(param.getSysTaCode()) && !param.getSysTaCode().equals("")){
            param.setSysIsReturnResult("是");
        }
        if (ObjectUtil.isNotNull(param.getSysTaCodeList()) && param.getSysTaCodeList().size() > 0){
            param.setSysIsReturnResult("是");
        }
        if (ObjectUtil.isNotNull(param.getSysPmPerName()) && !param.getSysPmPerName().equals("")){
            param.setSysIsReturnResult("是");
        }
        if (ObjectUtil.isNotNull(param.getSysPmPerNameList()) && param.getSysPmPerNameList().size() > 0){
            param.setSysIsReturnResult("是");
        }
        return this.mapper.listTimeoutTask(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdSamplePaResult> listSamplePa(RdSamplePaParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        param.setSysFeeAppPurPerCode(loginUser.getAccount());
        return this.mapper.listSamplePa(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RdSampleCfbResult> listSampleCfb(RdSampleCfbParam param) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        param.setSysCfPurPerCode(loginUser.getAccount());

        return this.mapper.listSampleCfb(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData addSample(List<RdSampleManageParam> params) {

        try {

            Date oprDate = new Date();
            int sysKfyTakeToDay = 0; //本次登记超时天数
            String sysKfyIsTakeTo = ""; //本次登记是否超时
            if (params.size() == 0) {
                return ResponseData.error("传入参数异常");
            } else {
                RdSampleManageParam rdSampleManageParam = params.get(0);
                RdSampleTask rdSampleTask = this.mapper.selectById(rdSampleManageParam.getSysTsTaskCode());
                sysKfyIsTakeTo = RdProposalEnum.KFY_IS_TAKE_TO_YES.getName();

                Calendar startCal = Calendar.getInstance();
                startCal.setTime(rdSampleTask.getSysTsDeadline());
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);
                startCal.set(Calendar.MILLISECOND, 0);
                Calendar endCal = Calendar.getInstance();
                endCal.setTime(oprDate);
                endCal.set(Calendar.HOUR_OF_DAY, 0);
                endCal.set(Calendar.MINUTE, 0);
                endCal.set(Calendar.SECOND, 0);
                endCal.set(Calendar.MILLISECOND, 0);
                sysKfyTakeToDay = (int) (endCal.getTimeInMillis() - startCal.getTimeInMillis()) / (24 * 60 * 60 * 1000);

                for (RdSampleManageParam param : params) {
                    param.setSysFuncOpr(RdProposalEnum.KFY_FUNC_SUBMIT.getName());
                    param.setSysKfyIsTakeTo(sysKfyIsTakeTo);
                    param.setSysKfyTakeToDay(BigDecimal.valueOf(sysKfyTakeToDay));
                }

                ResponseData responseData = sampleManageService.add(params);

                if (ObjectUtil.isNull(rdSampleTask.getSysTsSampTod()) || (ObjectUtil.isNotNull(rdSampleTask.getSysTsSampTod()) && BigDecimal.valueOf(sysKfyTakeToDay).compareTo(rdSampleTask.getSysTsSampTod()) > 0)) {
                    if (responseData.getSuccess() && responseData.getCode().equals(200)) {

                        //更新提案【累积拿样超时（天）】
                        RdProposalParam proposalParam = new RdProposalParam();
                        proposalParam.setSysTaCode(rdSampleTask.getSysTaCode());
                        proposalParam.setSysTaAccuSampTod(BigDecimal.valueOf(sysKfyTakeToDay));
                        responseData = proposalService.editTaAccuSampTod(proposalParam);

                        //更新开发样任务 【拿样超时时长】
                        if (responseData.getSuccess() && responseData.getCode().equals(200)) {

                            UpdateWrapper<RdSampleTask> updateWrapper = new UpdateWrapper<>();
                            updateWrapper.eq("SYS_TS_TASK_CODE", rdSampleTask.getSysTsTaskCode());
                            updateWrapper.set("SYS_TS_SAMP_TOD", BigDecimal.valueOf(sysKfyTakeToDay));

                            if (this.mapper.update(null, updateWrapper) > 0) {
                                return ResponseData.success("登记样品提交,更新开发任务【拿样超时时长】成功.");
                            } else {
                                return ResponseData.success("登记样品提交,更新开发任务【拿样超时时长】失败.");
                            }
                        } else {
                            return responseData;
                        }
                    } else {
                        return responseData;
                    }
                } else {
                    return responseData;
                }
            }

        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

}
