package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.product.api.productproposal.entity.*;
import com.tadpole.cloud.product.api.productproposal.model.params.*;
import com.tadpole.cloud.product.api.productproposal.model.result.*;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 提案-开发样任务 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Service
public class RdSampleTaskServiceImpl extends ServiceImpl<RdSampleTaskMapper, RdSampleTask> implements IRdSampleTaskService {

    @Resource
    private RdSampleTaskMapper mapper;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private RdPreProposalServiceConsumer oaServiceConsumer;

    @Resource
    private IRdSdSettingService sdSettingService;

    @Resource
    private IRdSampleManageService sampleManageService;

    @Resource
    private IRdProposalService proposalService;

    @Resource
    private IRdSampleCfbService sampleCfbService;

    @Resource
    private IRdSamplePaService samplePaService;

    @Resource
    private RdSampleManageMapper rdSampleManageMapper;

    @Resource
    private RdCustProductMapper rdCustProductMapper;

    @Resource
    private RdSamplePaMapper rdSamplePaMapper;

    @Resource
    private RdSampleCfbMapper rdSampleCfbMapper;

    @Resource
    private RdSampleCaMapper rdSampleCaMapper;

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData addOrEdit(RdSampleTaskParam param) {

        // 提交校验
        if (param.getSysFuncOpr().equals(RdProposalEnum.SAMPLE_FUNC_PUBLIC.getName())) {
            if (ObjectUtil.isNull(param.getSysTsSampleMethod())) {
                return ResponseData.error("参数【拿样方式】为必填项,请选择。");
            }

            if (ObjectUtil.isNull(param.getSysTsBotLinePurPrice())) {
                return ResponseData.error("参数【底线采购价】为必填项,请输入。");
            }

            if (param.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_SPOT_SAMPLE.getName())) {
                /*if (ObjectUtil.isNull(param.getSysTsStyleReq())) {
                    return ResponseData.error("参数【款式要求】为必填项,请输入。");
                }
                if (ObjectUtil.isNull(param.getSysTsBrandReq())) {
                    return ResponseData.error("参数【适用品牌或对象要求】为必填项,请输入。");
                }
                if (ObjectUtil.isNull(param.getSysTsMaterialReq())) {
                    return ResponseData.error("参数【材质要求】为必填项,请输入。");
                }*/
            }
        }

        LoginUser loginUser = LoginContext.me().getLoginUser();
        Date oprDate = new Date();
        HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();

        QueryWrapper<RdSampleTask> queryWrapper = new QueryWrapper<>();

        if (param.getSysFuncOpr().equals(RdProposalEnum.SAMPLE_FUNC_DELETE.getName())) {

            this.mapper.deleteById(param.getSysTsTaskCode());

            return ResponseData.success("拿样任务删除成功");
        }

        RdSampleTask rdSampleTask = BeanUtil.copyProperties(param, RdSampleTask.class);

        if (param.getSysPageOpr().equals(RdProposalEnum.TA_PAGE_NEW.getName())) {
            rdSampleTask.setSysTsTaskCode(generatorNoUtil.getTsBillNoExtents("00", "Task-NY", 2));

            queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
            Integer rdSampleTaskCount = this.mapper.selectCount(queryWrapper);
            rdSampleTask.setSysTsTaskName("第【" + (rdSampleTaskCount + 1) + "】轮【" + param.getSysTsSampleMethod() + "】");
        } else {
            String[] strArr = param.getSysTsTaskName().split("轮【");
            rdSampleTask.setSysTsTaskName(strArr[0] + "轮【" + param.getSysTsSampleMethod() + "】");
        }

        rdSampleTask.setSysCDate(oprDate);
        rdSampleTask.setSysLDate(oprDate);
        rdSampleTask.setSysPerCode(loginUser.getAccount());
        rdSampleTask.setSysPerName(loginUser.getName());
        rdSampleTask.setSysDeptCode(hrm.getIdall());
        rdSampleTask.setSysDeptName(hrm.getDepartmentname());

        if (param.getSysFuncOpr().equals(RdProposalEnum.SAMPLE_FUNC_PUBLIC.getName())) {

            List<RdSdSetting> sdSettings = sdSettingService.listPage();
            RdSdSetting sdSetting = sdSettings.stream().filter(l -> l.getSysSampleType().equals(rdSampleTask.getSysTsSampleMethod().replace("拿样", ""))).findFirst().get();

            rdSampleTask.setSysTsTaskStatus(RdProposalEnum.SAMPLE_STATE_SAMPLE.getName());
            long oneDayInMilliseconds = 24 * 60 * 60 * 1000;
            if (rdSampleTask.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_SPOT_SAMPLE.getName())) {
                rdSampleTask.setSysTsTaskProgress(RdProposalEnum.SAMPLE_PROCESS_WAIT_SAMP.getName());
            } else {
                rdSampleTask.setSysTsTaskProgress(RdProposalEnum.SAMPLE_PROCESS_WAIT_FEBK.getName());
                rdSampleTask.setSysTsFebkDeadline(new Date(oprDate.getTime() + (sdSetting.getSysFebkDuration().toBigInteger().longValue() * oneDayInMilliseconds)));
            }
            rdSampleTask.setSysTsDeadline(new Date(oprDate.getTime() + (sdSetting.getSysSamplingDuration().toBigInteger().longValue() * oneDayInMilliseconds)));

            rdSampleTask.setSysTsRelieaseDate(oprDate);
            rdSampleTask.setSysTsSubPerCode(loginUser.getAccount());
            rdSampleTask.setSysTsSubPerName(loginUser.getName());

            if (param.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName())) {
                rdSampleTask.setSysTsStyleReq("");
                rdSampleTask.setSysTsBrandReq("");
                rdSampleTask.setSysTsMaterialReq("");
                rdSampleTask.setSysTsPatternReq("");
                rdSampleTask.setSysTsColorReq("");
                rdSampleTask.setSysTsSizeReq("");
                rdSampleTask.setSysTsWeightReq("");
                rdSampleTask.setSysTsPackQtyReq("");
                rdSampleTask.setSysTsFunctionReq("");
                rdSampleTask.setSysTsPartsReq("");
                rdSampleTask.setSysTsPackReq("");
                rdSampleTask.setSysTsComplianceReq("");
                rdSampleTask.setSysTsCertificationReq("");

                rdSampleTask.setSysTsTaskStatus(RdProposalEnum.SAMPLE_STATE_SAMPLE.getName());
                rdSampleTask.setSysTsTaskProgress(RdProposalEnum.SAMPLE_PROCESS_WAIT_FEBK.getName());
            } else {
                rdSampleTask.setSysTsDesignDoc("");

                rdSampleTask.setSysTsTaskStatus(RdProposalEnum.SAMPLE_STATE_SAMPLE.getName());
                rdSampleTask.setSysTsTaskProgress(RdProposalEnum.SAMPLE_PROCESS_WAIT_SAMP.getName());
            }

        } else {

            if (ObjectUtil.isNotNull(param.getSysTsSampleMethod())) {
                if (param.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName())) {
                    rdSampleTask.setSysTsStyleReq("");
                    rdSampleTask.setSysTsBrandReq("");
                    rdSampleTask.setSysTsMaterialReq("");
                    rdSampleTask.setSysTsPatternReq("");
                    rdSampleTask.setSysTsColorReq("");
                    rdSampleTask.setSysTsSizeReq("");
                    rdSampleTask.setSysTsWeightReq("");
                    rdSampleTask.setSysTsPackQtyReq("");
                    rdSampleTask.setSysTsFunctionReq("");
                    rdSampleTask.setSysTsPartsReq("");
                    rdSampleTask.setSysTsPackReq("");
                    rdSampleTask.setSysTsComplianceReq("");
                    rdSampleTask.setSysTsCertificationReq("");
                } else {
                    rdSampleTask.setSysTsDesignDoc("");
                }
            }

            rdSampleTask.setSysTsTaskStatus(RdProposalEnum.SAMPLE_STATE_WAIT_PUBLIC.getName());
            rdSampleTask.setSysTsTaskProgress(RdProposalEnum.SAMPLE_PROCESS_WAIT_PUBLIC.getName());
        }

        if (param.getSysFuncOpr().equals(RdProposalEnum.SAMPLE_FUNC_PUBLIC.getName())) {
            if (this.saveOrUpdate(rdSampleTask)) {
                return ResponseData.success(200, "拿样任务发布成功", rdSampleTask.getSysTsTaskName());
            } else {
                return ResponseData.error("拿样任务发布失败");
            }
        } else {
            if (this.saveOrUpdate(rdSampleTask)) {
                return ResponseData.success(200, "拿样任务创建/编辑成功", rdSampleTask.getSysTsTaskName());
            } else {
                return ResponseData.error("拿样任务创建/编辑失败");
            }
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RdSampleTaskResult waitPublic(RdSampleTaskParam param) {
        QueryWrapper<RdSampleTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
        queryWrapper.eq("SYS_TS_TASK_STATUS", RdProposalEnum.SAMPLE_STATE_WAIT_PUBLIC.getName());

        RdSampleTask rdSampleTask = this.mapper.selectOne(queryWrapper);

        RdSampleTaskResult rdSampleTaskResult = BeanUtil.copyProperties(rdSampleTask, RdSampleTaskResult.class);

        return rdSampleTaskResult;
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData checkIsCanCreate(RdSampleTaskParam param) {

        //校验任务
        QueryWrapper<RdSampleTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());

        List<RdSampleTask> rdSampleTasks = this.mapper.selectList(queryWrapper);

        List<RdSampleTask> rdSampleTasksWaitPub = rdSampleTasks.stream().filter(l -> l.getSysTsTaskStatus().equals(RdProposalEnum.SAMPLE_STATE_WAIT_PUBLIC.getName())).collect(Collectors.toList());
        List<RdSampleTask> rdSampleTasksIng = rdSampleTasks.stream().filter(l -> l.getSysTsTaskStatus().equals(RdProposalEnum.SAMPLE_STATE_SAMPLE.getName())).collect(Collectors.toList());
        if (rdSampleTasksWaitPub.size() > 0) {
            return ResponseData.error("系统已存在待发布的任务,任务编号{" + rdSampleTasksWaitPub.get(0).getSysTsTaskCode() + "},不能再发任务。");
        }

        if (rdSampleTasksIng.size() > 0) {
            return ResponseData.error("系统存在进行中的任务,任务编号{" + rdSampleTasksIng.get(0).getSysTsTaskCode() + "},不能再发任务。");
        }

        //校验定品申请
        QueryWrapper<RdCustProduct> rdCustProductQueryWrapper = new QueryWrapper<>();
        rdCustProductQueryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());

        List<RdCustProduct> rdCustProducts = this.rdCustProductMapper.selectList(rdCustProductQueryWrapper);
        List<RdCustProduct> rdCustProductWaitPub = rdCustProducts.stream().filter(l -> l.getSysCpStatus().equals(RdProposalEnum.CP_STATUS_WAIT_SUBMIT.getName())).collect(Collectors.toList());
        List<RdCustProduct> rdCustProductIng = rdCustProducts.stream().filter(l -> l.getSysCpStatus().equals(RdProposalEnum.CP_STATUS_WAIT_APPR1.getName())).collect(Collectors.toList());
        if (rdCustProductWaitPub.size() > 0) {
            return ResponseData.error("系统已存在待提交的申请,申请编号{" + rdCustProductWaitPub.get(0).getSysCpCode() + "},不能再发任务。");
        }

        if (rdCustProductIng.size() > 0) {
            return ResponseData.error("系统存在进行中的申请,申请编号{" + rdCustProductIng.get(0).getSysCpCode() + "},不能再发任务。");
        }

        return ResponseData.success();
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData close(RdSampleTaskParam param) {

        //校验任务下的定制申请/购样申请是否完结
        RdSampleTask rdSampleTask = this.mapper.selectById(param.getSysTsTaskCode());
        if (rdSampleTask.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_SPOT_SAMPLE.getName())){ //购样申请
            QueryWrapper<RdSamplePa> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_TS_TASK_CODE",rdSampleTask.getSysTsTaskCode());
            List<RdSamplePa> rdSamplePas = this.rdSamplePaMapper.selectList(queryWrapper);
            List<RdSamplePa> waitSubRdSamplePas = rdSamplePas.stream().filter(l->l.getSysFeeAppStatus().equals(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_SUBMIT.getName())).collect(Collectors.toList());
            List<RdSamplePa> waitExamRdSamplePas = rdSamplePas.stream().filter(l->l.getSysFeeAppStatus().equals(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_APP.getName())).collect(Collectors.toList());
            List<RdSamplePa> waitApprRdSamplePas = rdSamplePas.stream().filter(l->l.getSysFeeAppStatus().equals(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_APPR.getName())).collect(Collectors.toList());
            List<RdSamplePa> waitPayRdSamplePas = rdSamplePas.stream().filter(l->l.getSysFeeAppStatus().equals(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_PAY.getName())).collect(Collectors.toList());
            List<RdSamplePa> waitSupRdSamplePas = rdSamplePas.stream().filter(l->l.getSysFeeAppStatus().equals(RdProposalEnum.PA_FEE_APP_STATUS_WAIT_SUPPLY.getName())).collect(Collectors.toList());

            if (waitSubRdSamplePas.size() > 0){
                String sysFeeAppCodes = String.join(";",waitSubRdSamplePas.stream().map(RdSamplePa::getSysFeeAppCode).collect(Collectors.toList()));
                return ResponseData.error("当前任务不能关闭,原因存在待提交的购样申请,申请编号【"+sysFeeAppCodes+"】,请联系相关负责人完成后续业务.");
            }
            if (waitExamRdSamplePas.size() > 0){
                String sysFeeAppCodes = String.join(";",waitExamRdSamplePas.stream().map(RdSamplePa::getSysFeeAppCode).collect(Collectors.toList()));
                return ResponseData.error("当前任务不能关闭,原因存在待审核的购样申请,申请编号【"+sysFeeAppCodes+"】,请联系相关负责人完成后续业务.");
            }
            if (waitApprRdSamplePas.size() > 0){
                String sysFeeAppCodes = String.join(";",waitApprRdSamplePas.stream().map(RdSamplePa::getSysFeeAppCode).collect(Collectors.toList()));
                return ResponseData.error("当前任务不能关闭,原因存在待审批的购样申请,申请编号【"+sysFeeAppCodes+"】,请联系相关负责人完成后续业务.");
            }
            if (waitSupRdSamplePas.size() > 0){
                String sysFeeAppCodes = String.join(";",waitSupRdSamplePas.stream().map(RdSamplePa::getSysFeeAppCode).collect(Collectors.toList()));
                return ResponseData.error("当前任务不能关闭,原因存在待补充订单信息的购样申请,申请编号【"+sysFeeAppCodes+"】,请联系相关负责人完成后续业务.");
            }
            if (waitPayRdSamplePas.size() > 0){
                String sysFeeAppCodes = String.join(";",waitPayRdSamplePas.stream().map(RdSamplePa::getSysFeeAppCode).collect(Collectors.toList()));
                return ResponseData.error("当前任务不能关闭,原因存在待支付的购样申请,申请编号【"+sysFeeAppCodes+"】,请联系相关负责人完成后续业务.");
            }
            List<RdSamplePa> payRdSamplePas = rdSamplePas.stream().filter(l->l.getSysFeeAppStatus().equals(RdProposalEnum.PA_FEE_APP_STATUS_ARCH.getName()) && l.getSysFeeAppAppResult().equals(RdProposalEnum.PA_FEE_APP_APP_RESULT_YES.getName())).collect(Collectors.toList());
            if (payRdSamplePas.size() > 0){
                QueryWrapper<RdSampleManage> manageQueryWrapper = new QueryWrapper<>();
                manageQueryWrapper.eq("SYS_TS_TASK_CODE",rdSampleTask.getSysTsTaskCode());
                List<RdSampleManage> rdSampleManages = this.rdSampleManageMapper.selectList(manageQueryWrapper).stream().filter(l->ObjectUtil.isNotNull(l.getSysKfySubDate())).collect(Collectors.toList());

                StringBuilder check = new StringBuilder();
                for (RdSamplePa pa:payRdSamplePas) {
                    List<RdSampleManage> paList = rdSampleManages.stream().filter(l->l.getSysKfyFeeCode().equals(pa.getSysFeeAppCode()) && l.getSysKfyFeeSource().equals(RdProposalEnum.KFY_FEE_SOURCE_SPA.getName())).collect(Collectors.toList());
                    if (pa.getSysFeeAppPurQty().compareTo(BigDecimal.valueOf(paList.size())) > 0){
                        check.append("申请编号【"+pa.getSysFeeAppCode()+"】登记的样品数量小于样品采购数量; ");
                    }
                }
                return ResponseData.error("当前任务不能关闭,原因{"+check.toString()+"},请联系相关负责人完成后续业务.");
            }
        }else { // 定制申请

            QueryWrapper<RdSampleCfb> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_TS_TASK_CODE",rdSampleTask.getSysTsTaskCode());
            List<RdSampleCfb> rdSampleCfbs = this.rdSampleCfbMapper.selectList(queryWrapper);
            List<RdSampleCfb> waitSubRdSampleCfbs = rdSampleCfbs.stream().filter(l->l.getSysCfStatus().equals(RdProposalEnum.CF_STATUS_WAIT_SUBMIT.getName())).collect(Collectors.toList());
            List<RdSampleCfb> waitFebkRdSampleCfbs = rdSampleCfbs.stream().filter(l->l.getSysCfStatus().equals(RdProposalEnum.CF_STATUS_WAIT_DEV_FB.getName())).collect(Collectors.toList());
            if (waitSubRdSampleCfbs.size() > 0){
                String sysFeeAppCodes = String.join(";",waitSubRdSampleCfbs.stream().map(RdSampleCfb::getSysCustFebkCode).collect(Collectors.toList()));
                return ResponseData.error("当前任务不能关闭,原因存在待提交的定制反馈,反馈编号【"+sysFeeAppCodes+"】,请联系相关负责人完成后续业务.");
            }
            if (waitFebkRdSampleCfbs.size() > 0){
                String sysFeeAppCodes = String.join(";",waitFebkRdSampleCfbs.stream().map(RdSampleCfb::getSysCustFebkCode).collect(Collectors.toList()));
                return ResponseData.error("当前任务不能关闭,原因存在待开发反馈的定制反馈,反馈编号【"+sysFeeAppCodes+"】,请联系相关负责人完成后续业务.");
            }
            RdSampleCfb rdSampleCfb;
            Optional<RdSampleCfb> optional = rdSampleCfbs.stream().filter(l->l.getSysCfStatus().equals(RdProposalEnum.CF_STATUS_DEV_FB.getName()) && l.getSysCfDevResult().equals(RdProposalEnum.CF_DEV_RESULT_YES.getName())).findFirst();
            if (optional.isPresent()){
                rdSampleCfb = optional.get();
            }else {
                rdSampleCfb = new RdSampleCfb();
            }
            if (ObjectUtil.isNotNull(rdSampleCfb.getSysCustFebkCode())){
                QueryWrapper<RdSampleCa> sampleCaQueryWrapper = new QueryWrapper<>();
                sampleCaQueryWrapper.eq("SYS_CUST_FEBK_CODE",rdSampleCfb.getSysCustFebkCode());
                RdSampleCa rdSampleCa = this.rdSampleCaMapper.selectOne(sampleCaQueryWrapper);
                if (rdSampleCfb.getSysCfIsMoldOpen().equals(RdProposalEnum.CF_IS_MOLD_OPEN_NO.getName())){
                    if (rdSampleCa.getSysSaApStatus().equals(RdProposalEnum.SA_AP_STATU_PA.getName())){
                        return ResponseData.error("当前任务不能关闭,原因存在待审批的定制申请,申请编号【"+rdSampleCa.getSysFeeAppCode()+"】,请联系相关负责人完成后续业务.");
                    }
                    if (rdSampleCa.getSysSaApStatus().equals(RdProposalEnum.SA_AP_STATU_TBP.getName())){
                        return ResponseData.error("当前任务不能关闭,原因存在待支付的定制申请,申请编号【"+rdSampleCa.getSysFeeAppCode()+"】,请联系相关负责人完成后续业务.");
                    }
                }else {
                    if (rdSampleCa.getSysSaApStatus().equals(RdProposalEnum.SA_AP_STATU_CTBU.getName())){
                        return ResponseData.error("当前任务不能关闭,原因存在待上传合同的定制申请,申请编号【"+rdSampleCa.getSysFeeAppCode()+"】,请联系相关负责人完成后续业务.");
                    }
                    if (rdSampleCa.getSysSaApStatus().equals(RdProposalEnum.SA_AP_STATU_CPR.getName())){
                        return ResponseData.error("当前任务不能关闭,原因存在合同待审核的定制申请,申请编号【"+rdSampleCa.getSysFeeAppCode()+"】,请联系相关负责人完成后续业务.");
                    }
                    if (rdSampleCa.getSysSaApStatus().equals(RdProposalEnum.SA_AP_STATU_CBU.getName())){
                        return ResponseData.error("当前任务不能关闭,原因存在待更新合同的定制申请,申请编号【"+rdSampleCa.getSysFeeAppCode()+"】,请联系相关负责人完成后续业务.");
                    }
                    if (rdSampleCa.getSysSaApStatus().equals(RdProposalEnum.SA_AP_STATU_PA.getName())){
                        return ResponseData.error("当前任务不能关闭,原因存在待审批的定制申请,申请编号【"+rdSampleCa.getSysFeeAppCode()+"】,请联系相关负责人完成后续业务.");
                    }
                    if (rdSampleCa.getSysSaApStatus().equals(RdProposalEnum.SA_AP_STATU_TBP.getName())){
                        return ResponseData.error("当前任务不能关闭,原因存在待支付的定制申请,申请编号【"+rdSampleCa.getSysFeeAppCode()+"】,请联系相关负责人完成后续业务.");
                    }
                }

                QueryWrapper<RdSampleManage> manageQueryWrapper = new QueryWrapper<>();
                manageQueryWrapper.eq("SYS_KFY_FEE_CODE",rdSampleCa.getSysFeeAppCode()).eq("SYS_KFY_FEE_SOURCE",RdProposalEnum.KFY_FEE_SOURCE_CA.getName());
                List<RdSampleManage> rdSampleManages = this.rdSampleManageMapper.selectList(manageQueryWrapper).stream().filter(l->ObjectUtil.isNotNull(l.getSysKfySubDate())).collect(Collectors.toList());
                if (rdSampleManages.size() == 0){
                    return ResponseData.error("当前任务不能关闭,原因存在定制申请申请编号【"+rdSampleCa.getSysFeeAppCode()+"】,未登记过样品,请联系相关负责人完成后续业务.");
                }

                if (rdSampleCfb.getSysCfSampleQty().compareTo(BigDecimal.valueOf(rdSampleManages.size())) > 0){
                    return ResponseData.error("当前任务不能关闭,原因存在定制申请申请编号【"+rdSampleCa.getSysFeeAppCode()+"】,登记的样品数量小于样品打样数量,请联系相关负责人完成后续业务.");
                }

            }
        }

        UpdateWrapper<RdSampleTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());
        updateWrapper.set("SYS_TS_CLOSE_TYPE", param.getSysTsCloseType()).set("SYS_TS_CLOSE_REASON", param.getSysTsCloseReason())
                .set("SYS_TS_TASK_STATUS", param.getSysTsTaskStatus()).set("SYS_TS_TASK_PROGRESS", param.getSysTsTaskProgress())
                .set("SYS_TS_CLOSE_PER_CODE", param.getSysTsClosePerCode()).set("SYS_TS_CLOSE_PER_NAME", param.getSysTsClosePerName())
                .set("SYS_TS_CLOSE_DATE", param.getSysTsCloseDate());

        if (this.update(null, updateWrapper)) {
            return ResponseData.success("拿样任务关闭成功");
        } else {
            return ResponseData.error("拿样任务关闭失败");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdSampleTaskExtentResult detail(RdSampleTaskParam param) {

        if (ObjectUtil.isNotNull(param.getSysTsTaskCode()) && !param.getSysTsTaskCode().equals("")){
            Date oprDate = new Date();
            RdSampleTaskExtentResult taskExtentResult = this.mapper.detail(param);

            RdSampleTaskResult rdSampleTaskResult = this.mapper.queryTaskTime(param);
            if (ObjectUtil.isNotNull(rdSampleTaskResult.getSysKfySubDate())){
                long differenceMillisecondDur = rdSampleTaskResult.getSysKfySubDate().getTime() - rdSampleTaskResult.getSysTsRelieaseDate().getTime();
                taskExtentResult.setSysTsSampleDuration(BigDecimal.valueOf(differenceMillisecondDur / (24 * 60 * 60 * 1000)));

                if (rdSampleTaskResult.getSysTsDeadline().after(rdSampleTaskResult.getSysKfySubDate())){
                    taskExtentResult.setSysTsSampleTimeout(BigDecimal.ZERO);
                }else {
                    long differenceMillisecondTod = rdSampleTaskResult.getSysKfySubDate().getTime() - rdSampleTaskResult.getSysTsDeadline().getTime();
                    taskExtentResult.setSysTsSampleTimeout(BigDecimal.valueOf(differenceMillisecondTod / (24 * 60 * 60 * 1000)));
                }
            }else {
                long differenceMilliseconds = oprDate.getTime() - rdSampleTaskResult.getSysTsRelieaseDate().getTime();
                taskExtentResult.setSysTsSampleDuration(BigDecimal.valueOf(differenceMilliseconds / (24 * 60 * 60 * 1000)));
                taskExtentResult.setSysTsSampleTimeout(BigDecimal.ZERO);
            }

            List<RdSampleTaskTimeAndCostResult> costResults = this.mapper.queryTaskCost(param);
            if (costResults.size() > 0){
                taskExtentResult.setSysFeeTotalFee(costResults.stream().map(l->l.getSysTsTotalFee()).reduce(BigDecimal.ZERO,BigDecimal::add));
            }

            RdSampleManageParam rdSampleManageParam = new RdSampleManageParam();
            rdSampleManageParam.setSysTsTaskCode(param.getSysTsTaskCode());
            taskExtentResult.setRdSampleManageResults(this.sampleManageService.listTestSample(rdSampleManageParam));

            if (taskExtentResult.getSysTsSampleMethod().equals(RdProposalEnum.SAMPLE_METHOD_CUST_SAMPLE.getName())){
                RdSampleCfbParam rdSampleCfbParam = new RdSampleCfbParam();
                rdSampleCfbParam.setSysTsTaskCode(param.getSysTsTaskCode());
                taskExtentResult.setRdSampleCfbResults(this.sampleCfbService.listSampleCfb(rdSampleCfbParam));
            }
            return taskExtentResult;
        }else {
            param.setSysTsTaskStatus(RdProposalEnum.SAMPLE_STATE_WAIT_PUBLIC.getName());
            RdSampleTaskExtentResult rdSampleTaskResult = this.mapper.detail(param);
            return rdSampleTaskResult;
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleTaskExtentResult> listPage(RdSampleTaskParam param) {
        return this.mapper.listPage(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData closeBySysAuto(RdSampleTaskParam param) {
        try {
            QueryWrapper<RdSampleTask> queryWrapper = new QueryWrapper<>();
            Date oprDate = new Date();

            if (ObjectUtil.isNull(param.getSysTsTaskCode())){

                queryWrapper.eq("SYS_TS_TASK_STATUS", RdProposalEnum.SAMPLE_STATE_SAMPLE.getName());
                List<RdSampleTask> rdSampleTasks = this.mapper.selectList(queryWrapper);

                List<String> tsTaskCodes = new ArrayList<>();
                List<String> taCodes = new ArrayList<>();

                for (RdSampleTask task : rdSampleTasks) {
                    if (oprDate.after(task.getSysTsDeadline())) {
                        tsTaskCodes.add(task.getSysTsTaskCode());
                        taCodes.add(task.getSysTaCode());
                    }
                }

                if (tsTaskCodes.size() > 0 && taCodes.size() > 0) {

                    RdProposalParam proposalParam = new RdProposalParam();
                    proposalParam.setSysTaCodeList(taCodes);

                    ResponseData responseData = this.proposalService.changeProposalProcess(proposalParam);
                    if (responseData.getSuccess() && responseData.getCode() == 200) {
                        UpdateWrapper<RdSampleTask> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.in("SYS_TS_TASK_CODE", tsTaskCodes);
                        updateWrapper.set("SYS_TS_CLOSE_TYPE", RdProposalEnum.SAMPLE_CLOSE_AUTO.getName()).set("SYS_TS_CLOSE_REASON", "")
                                .set("SYS_TS_TASK_STATUS", RdProposalEnum.SAMPLE_STATE_CLOSE.getName()).set("SYS_TS_TASK_PROGRESS", RdProposalEnum.SAMPLE_PROCESS_WAIT_CLOSE.getName())
                                .set("SYS_TS_CLOSE_PER_CODE", RdProposalEnum.TA_SYS_DEFAULT_CODE.getName()).set("SYS_TS_CLOSE_PER_NAME", RdProposalEnum.TA_SYS_DEFAULT_NAME.getName())
                                .set("SYS_TS_CLOSE_DATE", oprDate);

                        if (this.update(null, updateWrapper)) {
                            return ResponseData.success("拿样任务自动关闭成功");
                        } else {
                            return ResponseData.error("拿样任务自动关闭失败");
                        }
                    }
                }

                return ResponseData.success("系统处理成功");
            }else {
                queryWrapper.eq("SYS_TS_TASK_CODE",param.getSysTsTaskCode());
                RdSampleTask rdSampleTask = this.mapper.selectOne(queryWrapper);

                RdProposalParam proposalParam = new RdProposalParam();
                List<String> sysTaCodes = new ArrayList<>();
                sysTaCodes.add(rdSampleTask.getSysTaCode());
                proposalParam.setSysTaCodeList(sysTaCodes);

                ResponseData responseData = this.proposalService.changeProposalProcess(proposalParam);
                if (responseData.getSuccess() && responseData.getCode() == 200) {
                    UpdateWrapper<RdSampleTask> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("SYS_TS_TASK_CODE", rdSampleTask.getSysTsTaskCode());
                    updateWrapper.set("SYS_TS_CLOSE_TYPE", RdProposalEnum.SAMPLE_CLOSE_AUTO.getName()).set("SYS_TS_CLOSE_REASON", param.getSysTsCloseReason())
                            .set("SYS_TS_TASK_STATUS", RdProposalEnum.SAMPLE_STATE_CLOSE.getName()).set("SYS_TS_TASK_PROGRESS", RdProposalEnum.SAMPLE_PROCESS_WAIT_CLOSE.getName())
                            .set("SYS_TS_CLOSE_PER_CODE", RdProposalEnum.TA_SYS_DEFAULT_CODE.getName()).set("SYS_TS_CLOSE_PER_NAME", RdProposalEnum.TA_SYS_DEFAULT_NAME.getName())
                            .set("SYS_TS_CLOSE_DATE", oprDate);

                    if (this.update(null, updateWrapper)) {
                        return ResponseData.success();
                    } else {
                        return ResponseData.error("");
                    }
                }else {
                    return ResponseData.error("");
                }
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData addSample(List<RdSampleManageParam> params) {

        try {

            if (params.size() == 0){
                return ResponseData.error("传入参数异常");
            }else {
                ResponseData responseData = this.sampleManageService.add(params);
                return responseData;
            }

        }catch (Exception exception){
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData changeStatus(RdSampleTaskParam param){
        UpdateWrapper<RdSampleTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SYS_TS_TASK_CODE",param.getSysTsTaskCode());
        updateWrapper.set("SYS_TS_TASK_PROGRESS",param.getSysTsTaskProgress());

        if (ObjectUtil.isNotNull(param.getSysTsTaskStatus())){
            updateWrapper.set("SYS_TS_TASK_STATUS",param.getSysTsTaskStatus());
        }

        if (this.mapper.update(null,updateWrapper) > 0){
            return ResponseData.success();
        }else {
            return ResponseData.error("");
        }
    }

    @DataSource(name = "product")
    @Override
    public ResponseData getIsTsRead(String sysTsTaskCode) {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        List<RdSampleTask> list = this.baseMapper.getIsTsRead(loginUser.getAccount(),sysTsTaskCode);

        if(list.size()>0){
            return ResponseData.success(true);
        }

        return ResponseData.success(false);
    }
}
