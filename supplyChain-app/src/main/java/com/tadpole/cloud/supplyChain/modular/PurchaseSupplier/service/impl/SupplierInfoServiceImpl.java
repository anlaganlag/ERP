package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.objectLog.model.AttributeModel;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.BaseSystemApi;
import cn.stylefeng.guns.cloud.system.api.model.BaseUserInfo;
import cn.stylefeng.guns.cloud.system.api.objectLog.client.service.impl.LogClient;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.tadpole.cloud.externalSystem.api.k3.entity.ViewSupplier;
import com.tadpole.cloud.supplyChain.config.K3CloudWebapiConfig;
import com.tadpole.cloud.supplyChain.core.util.GeneratorSupplierNoUtil;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierAccountInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierContactInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierLog;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.enums.AuditStatusEnum;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.mapper.SupplierInfoMapper;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.k3.*;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierInfoParam;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result.SupplierInfoResult;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierAccountInfoService;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierContactInfoService;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierLogService;
import com.tadpole.cloud.supplyChain.modular.consumer.ExternalConsumer;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * 供应商-供应商信息 服务实现类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@Service
@Slf4j
public class SupplierInfoServiceImpl extends ServiceImpl<SupplierInfoMapper, SupplierInfo> implements ISupplierInfoService {

    @Resource
    private LogClient logClient;

    @Autowired
    K3CloudWebapiConfig k3CloudWebapiConfig;

    @Resource
    private SupplierInfoMapper mapper;

    @Resource
    private ISupplierAccountInfoService accountMapper;

    @Resource
    private ISupplierContactInfoService contactMapper;

    @Resource
    private ISupplierLogService logService;

    @Resource
    private GeneratorSupplierNoUtil generatorSupplierNoUtil;

    @Resource
    private BaseSystemApi baseSystemApi;

    @Resource
    private ExternalConsumer externalConsumer;

    private static String k3FormId="BD_Supplier";

    private static String EXAM_RERSULT_YES ="同意";
    private static String EXAM_RERSULT_NO ="不同意";
    private static String VAILD_STATUS_OK ="有效";
    private static String VAILD_STATUS_NO ="未生效";

    @DataSource(name ="product")
    @Override
    public PageResult<SupplierInfoResult> profileList(SupplierInfoParam param) {
        Page pageContext = param.getPageContext();
        IPage<SupplierInfoResult> page = this.baseMapper.profileList(pageContext, param);

        for (SupplierInfoResult res:page.getRecords()
             ) {
            QueryWrapper<SupplierAccountInfo> accountInfoQueryWrapper = new QueryWrapper<>();
            accountInfoQueryWrapper.eq("SYS_SUP_CODE",res.getSysSupCode());
            res.setSupplierAccountInfos(accountMapper.list(accountInfoQueryWrapper));

            QueryWrapper<SupplierContactInfo> contactInfoQueryWrapper = new QueryWrapper<>();
            contactInfoQueryWrapper.eq("SYS_SUP_CODE",res.getSysSupCode());
            res.setSupplierContactInfos(contactMapper.list(contactInfoQueryWrapper));
        }

        return new PageResult<>(page);
    }

    @DataSource(name ="product")
    @Override
    public ResponseData profileAdd(SupplierInfoParam param) {

        try {

        if(param.getSysCreditCode()!=null){
            QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_CREDIT_CODE",param.getSysCreditCode());

            if(this.list(queryWrapper).size()>0){
                return ResponseData.error("供应商信用代码已存在！");
            }
        }
        if(param.getSysSupName()!=null){
            QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_SUP_NAME",param.getSysSupName());

            if(this.list(queryWrapper).size()>0){
                return ResponseData.error("供应商名称已存在！");
            }
        }
        if(param.getSysSupAbb()!=null){
            QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_SUP_ABB",param.getSysSupAbb());

            if(this.list(queryWrapper).size()>0){
                return ResponseData.error("供应商简称已存在！");
            }
        }


            LoginUser loginUser = LoginContext.me().getLoginUser();

        SupplierInfo info = BeanUtil.copyProperties(param,SupplierInfo.class);
        info.setSysSupCode(generatorSupplierNoUtil.getBillNo());
        List<SupplierAccountInfo> accountInfos = param.getSupplierAccountInfos();
        List<SupplierContactInfo> contactInfos = param.getSupplierContactInfos();
        info.setSysAuditStatus(info.getSysAuditStatus()==null?AuditStatusEnum.WAIT_SUBMIT.getName():info.getSysAuditStatus());
        info.setSysValidStatus(VAILD_STATUS_NO);
        info.setSysCDate(new Date());
        info.setSysLDate(new Date());
        info.setSysPerCode(loginUser.getAccount());
        info.setSysPerName(loginUser.getName());
        info.setSysDeptCode(loginUser.getDeptId()==null?"":loginUser.getDeptId().toString());
        info.setSysDeptName(loginUser.getDeptId()==null?"":loginUser.getDepartment());
        if(param.getSysPurChargePerCode()!=null){
            BaseUserInfo userInfo = baseSystemApi.getUserInfoByAccount(param.getSysPurChargePerCode());
            info.setSysPurChargeDeptCode(userInfo.getDeptId().toString());
            info.setSysPurChargeDeptName(userInfo.getTeam());
        }
        this.save(info);

        if(accountInfos!=null && accountInfos.size()>0){
            for (SupplierAccountInfo account :accountInfos
                 ) {
                account.setSysSupCode(info.getSysSupCode());
                account.setSysCDate(new Date());
                account.setSysLDate(new Date());
            }
            accountMapper.saveBatch(accountInfos);
        }
        if(contactInfos!=null && contactInfos.size()>0){
            for (SupplierContactInfo contactInfo :contactInfos
            ) {
                contactInfo.setSysSupCode(info.getSysSupCode());
                contactInfo.setSysCDate(new Date());
                contactInfo.setSysLDate(new Date());
                contactInfo.setSysPerCode(loginUser.getAccount());
                contactInfo.setSysPerName(loginUser.getName());
                contactInfo.setSysDeptCode(loginUser.getDepartment());
                contactInfo.setSysDeptName(loginUser.getTeam());
            }
            contactMapper.saveBatch(contactInfos);
        }

        SupplierLog supplierLog = new SupplierLog();
        supplierLog.setSysSupCode(info.getSysSupCode());
        supplierLog.setSysUpdateDate(new Date());
        supplierLog.setSysUpdateType(info.getSysSupName()==null?info.getSysSupCode():info.getSysSupName());
        supplierLog.setSysUpdatePerCode(loginUser.getAccount());
        supplierLog.setSysUpdatePerName(loginUser.getName());
        logService.save(supplierLog);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.error(e.getMessage());
        }

        return ResponseData.success();
    }

    @DataSource(name ="product")
    @Override
    public ResponseData profileUpdate(SupplierInfoParam param) throws IllegalAccessException {

        if(param.getSysCreditCode()!=null){
            QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_CREDIT_CODE",param.getSysCreditCode()).ne("ID",param.getId());

            if(this.list(queryWrapper).size()>0){
                return ResponseData.error("供应商信用代码已存在！");
            }
        }
        if(param.getSysSupName()!=null){
            QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_SUP_NAME",param.getSysSupName()).ne("ID",param.getId());

            if(this.list(queryWrapper).size()>0){
                return ResponseData.error("供应商名称已存在！");
            }
        }
        if(param.getSysSupAbb()!=null){
            QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_SUP_ABB",param.getSysSupAbb()).ne("ID",param.getId());

            if(this.list(queryWrapper).size()>0){
                return ResponseData.error("供应商简称已存在！");
            }
        }

        try {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        SupplierInfo infoOld = this.getById(param.getId());

        SupplierInfo info = BeanUtil.copyProperties(param,SupplierInfo.class);

        List<AttributeModel> difList = logClient.getDifAttributeModel(infoOld,info);
        List<AttributeModel> difList2 = new ArrayList<>();
        Map codeName = this.getCodeName();
        for (AttributeModel attributeModel:difList) {
            if(codeName.get(attributeModel.getAttributeAlias())!=null && codeName.get(attributeModel.getAttributeAlias()).toString().contains("-")){
                attributeModel.setAttributeType(codeName.get(attributeModel.getAttributeAlias()).toString().split("-")[0]);
                attributeModel.setAttributeName(codeName.get(attributeModel.getAttributeAlias()).toString().split("-")[1]);
                difList2.add(attributeModel);
            }
        }
        info.setSysLDate(new Date());

        if(param.getSysPurChargePerCode()!=null){
            BaseUserInfo userInfo = baseSystemApi.getUserInfoByAccount(param.getSysPurChargePerCode());
            info.setSysPurChargeDeptCode(userInfo.getDeptId().toString());
            info.setSysPurChargeDeptName(userInfo.getTeam());
        }
        this.updateById(info);

        if(!param.getSysAuditStatus().equals(AuditStatusEnum.WAIT_DEPARTMENT_REVIEW.getName())
                && !param.getSysAuditStatus().equals(AuditStatusEnum.PASS.getName())){

            List<SupplierAccountInfo> accountInfos = param.getSupplierAccountInfos();
            List<SupplierContactInfo> contactInfos = param.getSupplierContactInfos();

            QueryWrapper<SupplierAccountInfo> accountInfoQueryWrapper = new QueryWrapper<>();
            accountInfoQueryWrapper.eq("SYS_SUP_CODE",info.getSysSupCode());
            accountMapper.getBaseMapper().delete(accountInfoQueryWrapper);


            QueryWrapper<SupplierContactInfo> contactInfoQueryWrapper = new QueryWrapper<>();
            contactInfoQueryWrapper.eq("SYS_SUP_CODE",info.getSysSupCode());
            contactMapper.getBaseMapper().delete(contactInfoQueryWrapper);

            if(accountInfos!=null && accountInfos.size()>0){
                for (SupplierAccountInfo account :accountInfos
                ) {
                    account.setSysSupCode(info.getSysSupCode());
                    account.setSysPerName(loginUser.getName());
                    account.setSysCDate(new Date());
                    account.setSysLDate(new Date());
                }
                accountMapper.saveBatch(accountInfos);
            }
            if(contactInfos!=null && contactInfos.size()>0){
                for (SupplierContactInfo contactInfo :contactInfos
                ) {
                    contactInfo.setSysSupCode(info.getSysSupCode());
                    contactInfo.setSysCDate(new Date());
                    contactInfo.setSysLDate(new Date());
                    contactInfo.setSysPerCode(loginUser.getAccount());
                    contactInfo.setSysPerName(loginUser.getName());
                    contactInfo.setSysDeptCode(loginUser.getDepartment());
                    contactInfo.setSysDeptName(loginUser.getTeam());
            }
                contactMapper.saveBatch(contactInfos);
            }
        }

        SupplierLog supplierLog = new SupplierLog();
        supplierLog.setSysSupCode(info.getSysSupCode());
        supplierLog.setSysUpdateDate(new Date());
        if(param.getSysAuditStatus().equals(AuditStatusEnum.PASS.getName())||param.getSysAuditStatus().equals(AuditStatusEnum.WAIT_DEPARTMENT_REVIEW.getName())){
            supplierLog.setSysUpdateType("提交");
        }else {
            supplierLog.setSysUpdateType("修改");
        }
        supplierLog.setSysUpdateContent(JSON.toJSONString(difList2));
        supplierLog.setSysUpdatePerCode(loginUser.getAccount());
        supplierLog.setSysUpdatePerName(loginUser.getName());
        logService.save(supplierLog);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.error(e.getMessage());
        }

        return ResponseData.success();
    }

    @DataSource(name ="product")
    @Override
    public ResponseData profileSubmit(SupplierInfoParam param) throws IllegalAccessException {
        if(param.getSysAdmitType().equals("购样")){
            param.setSysAuditStatus(AuditStatusEnum.PASS.getName());
            param.setSysValidStatus(VAILD_STATUS_OK);
        }else{
            param.setSysAuditStatus(AuditStatusEnum.WAIT_DEPARTMENT_REVIEW.getName());
        }
        //修改提交
        if(param.getId()!=0){
          return   this.profileUpdate(param);
        }else{//新增提交
          return  this.profileAdd(param);
        }
    }

    @DataSource(name ="product")
    @Override
    public void departmentCheck(SupplierInfoParam param) {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        UpdateWrapper<SupplierInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",param.getId())
        .set("SYS_DEPT_EXAM_RESULT",param.getSysDeptExamResult())
        .set(StringUtils.isNotEmpty(param.getSysDeptExamInstructe()),"SYS_DEPT_EXAM_INSTRUCTE",param.getSysDeptExamInstructe())
                .set("SYS_DEPT_EXAM_DATE",new Date())
                .set("SYS_DEPT_EXAM_PER_NAME",loginUser.getName())
                .set("SYS_DEPT_EXAM_PER_CODE",loginUser.getAccount());

        if(param.getSysDeptExamResult().equals(EXAM_RERSULT_YES)) {
            updateWrapper.set("SYS_AUDIT_STATUS", AuditStatusEnum.WAIT_QUALIFICATION_REVIEW.getName());
        }else if(param.getSysDeptExamResult().equals(EXAM_RERSULT_NO)){
            updateWrapper.set("SYS_AUDIT_STATUS",AuditStatusEnum.OVER.getName());
        }else{
            updateWrapper.set("SYS_AUDIT_STATUS",AuditStatusEnum.COMPLETE_INFORMATION.getName());
        }

        this.update(updateWrapper);

        UpdateWrapper<SupplierLog> updateWrapperLog = new UpdateWrapper<>();
        updateWrapperLog.eq("SYS_SUP_CODE",param.getSysSupCode())
                .isNull("SYS_DEPT_EXAM_PER_CODE")
                .set("SYS_DEPT_EXAM_DATE",new Date())
                .set("SYS_DEPT_EXAM_PER_NAME",loginUser.getName())
                .set("SYS_DEPT_EXAM_PER_CODE",loginUser.getAccount());
        logService.update(updateWrapperLog);

        SupplierLog supplierLog = new SupplierLog();
        supplierLog.setSysSupCode(param.getSysSupCode());
        supplierLog.setSysUpdateDate(new Date());
        supplierLog.setSysUpdateType("审核");

        List<AttributeModel> mList = new ArrayList<>();
        AttributeModel model = new AttributeModel();
        model.setAttributeName(param.getSysDeptExamResult()+","+param.getSysDeptExamInstructe());
        mList.add(model);
        supplierLog.setSysUpdateContent(JSON.toJSONString(mList));

        supplierLog.setSysUpdatePerCode(loginUser.getAccount());
        supplierLog.setSysUpdatePerName(loginUser.getName());
        logService.save(supplierLog);

    }

    @DataSource(name ="product")
    @Override
    public void qualificationCheck(SupplierInfoParam param) {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        UpdateWrapper<SupplierInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",param.getId())
                .set("SYS_QUAL_EXAM_RESULT",param.getSysQualExamResult())
                .set(StringUtils.isNotEmpty(param.getSysQualExamInstructe()),"SYS_QUAL_EXAM_INSTRUCTE",param.getSysQualExamInstructe())
                .set("SYS_QUAL_EXAM_DATE",new Date())
                .set("SYS_QUAL_EXAM_PER_NAME",loginUser.getName())
                .set("SYS_QUAL_EXAM_PER_CODE",loginUser.getAccount());

        if(param.getSysQualExamResult().equals(EXAM_RERSULT_YES)){

            if(param.getSysApprResult()!=null && param.getSysApprResult().equals(EXAM_RERSULT_YES)){
                updateWrapper.set("SYS_AUDIT_STATUS",AuditStatusEnum.PASS.getName());
            }else {
                updateWrapper.set("SYS_AUDIT_STATUS",AuditStatusEnum.WAIT_REVIEW.getName());
            }
        }else{
            updateWrapper.set("SYS_AUDIT_STATUS",AuditStatusEnum.COMPLETE_INFORMATION.getName());
        }
        this.update(updateWrapper);

        UpdateWrapper<SupplierLog> updateWrapperLog = new UpdateWrapper<>();
        updateWrapperLog.eq("SYS_SUP_CODE",param.getSysSupCode())
                .isNull("SYS_QUAL_EXAM_PER_CODE")
                .set("SYS_QUAL_EXAM_DATE",new Date())
                .set("SYS_QUAL_EXAM_PER_NAME",loginUser.getName())
                .set("SYS_QUAL_EXAM_PER_CODE",loginUser.getAccount());
        logService.update(updateWrapperLog);

        SupplierLog supplierLog = new SupplierLog();
        supplierLog.setSysSupCode(param.getSysSupCode());
        supplierLog.setSysUpdateDate(new Date());
        supplierLog.setSysUpdateType("资质审核");

        List<AttributeModel> mList = new ArrayList<>();
        AttributeModel model = new AttributeModel();
        model.setAttributeName(param.getSysQualExamResult()+","+param.getSysQualExamInstructe());
        mList.add(model);
        supplierLog.setSysUpdateContent(JSON.toJSONString(mList));

        supplierLog.setSysUpdatePerCode(loginUser.getAccount());
        supplierLog.setSysUpdatePerName(loginUser.getName());
        logService.save(supplierLog);
    }

    @DataSource(name ="product")
    @Override
    public ResponseData review(SupplierInfoParam param) throws Exception {

        LoginUser loginUser = LoginContext.me().getLoginUser();

        UpdateWrapper<SupplierInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",param.getId())
                .set("SYS_APPR_RESULT",param.getSysApprResult())
                .set(StringUtils.isNotEmpty(param.getSysApprRemarks()),"SYS_APPR_REMARKS",param.getSysApprRemarks())
                .set("SYS_APPR_DATE",new Date())
                .set("SYS_APPR_PER_NAME",loginUser.getName())
                .set("SYS_APPR_PER_CODE",loginUser.getAccount());

        if(param.getSysApprResult().equals(EXAM_RERSULT_YES)){
            updateWrapper.set("SYS_AUDIT_STATUS",AuditStatusEnum.PASS.getName())
                    .set("SYS_VALID_STATUS",VAILD_STATUS_OK);
        }else{
            updateWrapper.set("SYS_AUDIT_STATUS",AuditStatusEnum.OVER.getName()).set("SYS_VALID_STATUS",VAILD_STATUS_NO);
        }

        this.update(updateWrapper);


        UpdateWrapper<SupplierLog> updateWrapperLog = new UpdateWrapper<>();
        updateWrapperLog.eq("SYS_SUP_CODE",param.getSysSupCode())
                .isNull("SYS_APPR_PER_CODE")
                .set("SYS_APPR_DATE",new Date())
                .set("SYS_APPR_PER_NAME",loginUser.getName())
                .set("SYS_APPR_PER_CODE",loginUser.getAccount());
        logService.update(updateWrapperLog);

        if(param.getSysApprResult().equals(EXAM_RERSULT_YES)){
        return this.syncSupplierToErp();
        }

        SupplierLog supplierLog = new SupplierLog();
        supplierLog.setSysSupCode(param.getSysSupCode());
        supplierLog.setSysUpdateDate(new Date());
        supplierLog.setSysUpdateType("审批");

        List<AttributeModel> mList = new ArrayList<>();
        AttributeModel model = new AttributeModel();
        model.setAttributeName(param.getSysApprResult()+","+param.getSysApprRemarks());
        mList.add(model);
        supplierLog.setSysUpdateContent(JSON.toJSONString(mList));

        supplierLog.setSysUpdatePerCode(loginUser.getAccount());
        supplierLog.setSysUpdatePerName(loginUser.getName());
        logService.save(supplierLog);

        return  ResponseData.success();
    }

    @DataSource(name ="product")
    @Override
    public List<SupplierInfoResult> supplierList(SupplierInfoParam param) {

        List<SupplierInfoResult> list = this.baseMapper.supplierList(param);

        for (SupplierInfoResult res:list) {
            QueryWrapper<SupplierAccountInfo> accountInfoQueryWrapper = new QueryWrapper<>();
            accountInfoQueryWrapper.eq("SYS_SUP_CODE",res.getSysSupCode());
            res.setSupplierAccountInfos(accountMapper.list(accountInfoQueryWrapper));

            QueryWrapper<SupplierContactInfo> contactInfoQueryWrapper = new QueryWrapper<>();
            contactInfoQueryWrapper.eq("SYS_SUP_CODE",res.getSysSupCode());
            res.setSupplierContactInfos(contactMapper.list(contactInfoQueryWrapper));
        }
        return list;
    }

    @DataSource(name = "product")
    @Override
    public List<Map<String, Object>>  supSelect() {
        List supList = new ArrayList();
        QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("SYS_SUP_NAME sysSupName,SYS_SUP_CODE sysSupCode,SYS_SUP_LEVEL sysSupLevel,SYS_ADMIT_TYPE sysAdmitType")
                .orderByAsc("SYS_SUP_CODE")
                .eq("SYS_VALID_STATUS",VAILD_STATUS_OK);
        supList = this.listMaps(queryWrapper);
        return supList;
    }

    @DataSource(name = "product")
    @Override
    public ResponseData syncSupplierToErp() throws Exception{
                SupplierInfoParam param = new SupplierInfoParam();
                param.setSysAuditStatus(AuditStatusEnum.PASS.getName());
                param.setSysAdmitType("同步erp");
                List<SupplierInfoResult> results = this.supplierList(param);

                for (SupplierInfoResult result:results) {

                    try {
                        JSONObject objectAll = new JSONObject();

                        //供应商信息
                        K3SupplierParamMap k3Supplier = new K3SupplierParamMap();
                        k3Supplier.setFSupplierId(result.getSupplierId());
                        k3Supplier.setFName(result.getSysSupName());
                        k3Supplier.setFNumber(result.getSysSupCode());
                        k3Supplier.setFShortName(result.getSysSupAbb());
                        k3Supplier.setFGroup("CPGYS");
                        k3Supplier.setF_PAEZ_Remark2(result.getSysMainProduct());
                        k3Supplier.setF_PAEZ_Combo(result.getSysIsSugBycompStaf() == "是" ? "1" : "2");
                        k3Supplier.setF_BSC_Assistant(StringUtils.isNotEmpty(result.getSysProvince())?result.getSysProvince().split("-")[1]:null);
                        k3Supplier.setF_BSC_Assistant1(StringUtils.isNotEmpty(result.getSysUrbanArea())?result.getSysUrbanArea().split("-")[1]:null);
                        k3Supplier.setF_BSC_Combo(result.getSysIsSugBycompStaf() == "是" ? "1" : "2");
                        k3Supplier.setFPayCondition(StringUtils.isNotEmpty(result.getSysPaymentTerms())?result.getSysPaymentTerms().split("-")[1]:null);
                        k3Supplier.setFCreateOrgId("001");
                        k3Supplier.setFUseOrgId("001");
                        k3Supplier.setFTaxType(StringUtils.isNotEmpty(result.getSysTaxClassify())?result.getSysTaxClassify().split("-")[1]:null);
                        k3Supplier.setF_BSC_Combo2(result.getSysInvoCompType());
                        k3Supplier.setF_BSC_Base(result.getSysMcsPerCode());
                        k3Supplier.setF_BSC_Base1(StringUtils.isNotEmpty(result.getSysSupChainComp())?result.getSysSupChainComp().split("-")[1]:null);
                        k3Supplier.setF_PAEZ_Remark3(result.getSysRelateToSug());
                        k3Supplier.setF_BSC_TAXRATECONEW(result.getTaxRateConew());

                        //供应商基础信息
                        FBaseInfo baseInfo = new FBaseInfo();
                        baseInfo.setFSupplyClassify("CG");
                        baseInfo.setFAddress(result.getSysMailAddress());
                        baseInfo.setFSOCIALCRECODE(result.getSysCreditCode());
                        baseInfo.setFRegisterAddress(result.getSysRegistAddress());
                        baseInfo.setFStaffId(result.getSysPurChargePerCode());
                        baseInfo.setFPayCondition(StringUtils.isNotEmpty(result.getSysPaymentTerms())?result.getSysPaymentTerms().split("-")[1]:null);
                        baseInfo.setF_Obsolete_suppliers("setF_Obsolete_suppliers");
                        baseInfo.setFTrade(StringUtils.isNotEmpty(result.getSysIndustry())?result.getSysIndustry().split("-")[1]:null);
                        baseInfo.setF_PAEZ_Remark(result.getSysRemarks());

                        k3Supplier.setFBaseInfo(baseInfo);


                        //供应商财务信息
                        FFinanceInfo finance = new FFinanceInfo();
                        finance.setFPayCurrencyId(StringUtils.isNotEmpty(result.getSysSettlementCurrency())?result.getSysSettlementCurrency().split("-")[1]:null);
                        finance.setFTaxType(StringUtils.isNotEmpty(result.getSysTaxClassify())?result.getSysTaxClassify().split("-")[1]:null);
                        finance.setFInvoiceType("1");
                        finance.setFTaxRateId(StringUtils.isNotEmpty(result.getSysDefaultTaxRate())?result.getSysDefaultTaxRate().split("-")[1]:null);
                        finance.setFPayCondition(StringUtils.isNotEmpty(result.getSysPaymentTerms())?result.getSysPaymentTerms().split("-")[1]:null);
                        k3Supplier.setFFinanceInfo(finance);

                        //供应商财务信息
                        FBusinessInfo businessInfo = new FBusinessInfo();
                        businessInfo.setFSettleTypeId(StringUtils.isNotEmpty(result.getSysPaymentTerms())?result.getSysSettlementMethod().split("-")[1]:null);
                        businessInfo.setFVmiBusiness("false");
                        businessInfo.setFEnableSL("false");
                        k3Supplier.setFBusinessInfo(businessInfo);

                        //供应商银行信息
                        List<FBankInfo> bankInfos = new ArrayList<>();
                        for (SupplierAccountInfo accountInfo : result.getSupplierAccountInfos()) {
                            FBankInfo bankInfo = new FBankInfo();
                            bankInfo.setFBankCode(accountInfo.getSysBankAccountNum());
                            bankInfo.setFBankCountry("China");
                            bankInfo.setFBankHolder(accountInfo.getSysAccountName());
                            bankInfo.setFOpenBankName(accountInfo.getSysOpenBank());
                            bankInfo.setFBankDetail(StringUtils.isNotEmpty(accountInfo.getSysBankOutlet())?accountInfo.getSysBankOutlet().split("-")[1]:null);
                            bankInfo.setFBankCurrencyId(StringUtils.isNotEmpty(accountInfo.getSysCurrency())?accountInfo.getSysCurrency().split("-")[1]:null);
                            bankInfo.setFCNAPS(accountInfo.getSysInterbankNum());
                            bankInfo.setFBankIsDefault(accountInfo.getIsDefault());
                            bankInfos.add(bankInfo);
                        }
                        k3Supplier.setFBankInfo(bankInfos);

                        //供应商联系人信息
                        List<FSupplierContact> contacts = new ArrayList<>();
                        for (SupplierContactInfo contactInfo : result.getSupplierContactInfos()) {
                            FSupplierContact supplierContact = new FSupplierContact();
                            supplierContact.setFContactNumber(Long.toString(contactInfo.getId()));
                            supplierContact.setFContact(contactInfo.getSysName());
                            if(StringUtils.isNotEmpty(contactInfo.getSysGender())){
//                               supplierContact.setFGender(contactInfo.getSysGender()=="男"?"SEX01_SYS":"SEX02_SYS");
                            }
                            supplierContact.setFPost(contactInfo.getSysJobTitle());
                            supplierContact.setFMobile(contactInfo.getSysMobile());
                            supplierContact.setFEMail(contactInfo.getSysEmail());
                            supplierContact.setFContactDescription(contactInfo.getSysRemarks());
                            supplierContact.setFContactIsDefault(contactInfo.getSysDefaultContact()=="是"?"true":"false");
                            contacts.add(supplierContact);
                        }
                        k3Supplier.setFSupplierContact(contacts);

                        //供应商位置信息
//                    List<FLocationInfo> locationInfos = new ArrayList<>();
//                    FLocationInfo locationInfo = new FLocationInfo();
//                    locationInfo.setFLocName(result.getSysDeptName());
//                    locationInfo.setFLocNewContact("CXR103347");
//                    locationInfo.setFLocAddress("setFLocAddress");
//                    locationInfo.setFLocMobile("setFLocMobile");
//                    locationInfos.add(locationInfo);
//
//                    k3Supplier.setFLocationInfo(locationInfos);

                      ViewSupplier viewSupplier =  externalConsumer.getDeptUserId(result.getSysPurChargePerCode());
                      k3Supplier.setFDeptId(viewSupplier.getFnumber());

                        if(result.getSupplierId()!=null){
                            k3Supplier.setFMODIFIERID(viewSupplier.getFname());
                            //需要反审核
                            unAudit(result.getSupplierId(),result.getSysSupCode());
                        }else{
                            k3Supplier.setFCREATORID(viewSupplier.getFname());
                        }

                        objectAll.put("Model", k3Supplier);

                        String jsonData = JSON.toJSONString(objectAll);


                        //保存供应商
                   String res = saveSupplier(k3Supplier.getFNumber(),jsonData);

                    if(!res.equals("")){
                        return ResponseData.error(res);
                    }

                    } catch (Exception e) {
                        log.error(e.getMessage());
                        return ResponseData.error("同步金蝶erp失败！");
                    }
                }

        return ResponseData.success();
    }

    public String saveSupplier(String fNumber,String jsonStr) throws Exception{
        try{
            String saveRes ="";
            K3CloudApi api=new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                String result = api.save(k3FormId, jsonStr);
                //System.out.println(result);
                log.info( "供应商接口保存执行完成，" + "返回结果:" + result);

                JSONObject  resultJson = JSON.parseObject(result);

                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            //2-1.保存同步返回成功状态处理
                            if("true".equals(resultResponse.getString("IsSuccess"))){

                                String fId=resultValue.getString("Id");

                                //2-3.供应商
                                saveRes =commitSupplier(fId,fNumber);
                                if(saveRes.equals("")){
                                    //2-4.供应商
                                    saveRes = auditTransfer(fId,fNumber);
                                }
                                if(saveRes.equals("")){
                                    return "";
                                }else {
                                    return saveRes;
                                }
                            }
                            else{
                                //保存同步信息
                                LambdaUpdateWrapper<SupplierInfo> updateItemWrapper=new LambdaUpdateWrapper<>();
                                updateItemWrapper.eq(SupplierInfo::getSysSupCode,fNumber)
                                        .set(SupplierInfo::getSyncTime,new Date())
                                        .set(SupplierInfo::getSyncResultMsg,result);
                                mapper.update(null,updateItemWrapper);
                               String error = JSON.parseObject(String.valueOf(resultResponse.getJSONArray("Errors").get(0))).getString("Message");
                                log.error( "供应商接口保存执行完成，" + "返回结果:" + error);
                                return error;
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "";
    }

    public String commitSupplier(String fID,String fNumber) throws Exception {

        try{
            K3CloudApi api=new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {

                String jsonData = "{\"Numbers\":[\""+fNumber+"\"],\"Ids\":\""+fID+"\"}";

                String result = api.submit(k3FormId, jsonData);
                //System.out.println(result);
                log.info( "供应商接口提交执行完成，供应商[{}]，返回结果:[{}]",fNumber,result);

                JSONObject  resultJson = JSON.parseObject(result);

                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            //2-1.保存同步返回成功状态处理
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                //2-2.返回成功
                                return "";
                            }
                            else{
                                //2-3.k3跨组织提交返回结果保存
                                String sql = "SYNC_RESULT_MSG=SYNC_RESULT_MSG||'";
                                sql=sql+"to_char('||')||"+result + "'";

                                //保存同步信息
                                LambdaUpdateWrapper<SupplierInfo> updateItemWrapper=new LambdaUpdateWrapper<>();
                                updateItemWrapper.eq(SupplierInfo::getSysSupCode,fNumber)
                                        .set(SupplierInfo::getSyncTime,DateUtil.date())
                                        .set(SupplierInfo::getSyncResultMsg,result);
                                mapper.update(null,updateItemWrapper);
                                String error = JSON.parseObject(String.valueOf(resultResponse.getJSONArray("Errors").get(0))).getString("Message");
                                log.error( "供应商接口提交执行完成，" + "返回结果:" + error);
                                return error;
                            }
                        }
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("供应商:[{}] 提交异常，异常信息:[{}]",fNumber,e.getMessage());
        }
        return "";
    }

    public String auditTransfer(String fID,String fNumber) throws Exception {
        try{
            K3CloudApi api=new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {

                String jsonData = "{\"Numbers\":[\""+fNumber+"\"],\"Ids\":\""+fID+"\"}";

                String result = api.audit(k3FormId, jsonData);

                log.info( "供应商接口审核执行完成[{}]，返回结果:[{}]",fNumber,result);

                JSONObject  resultJson = JSON.parseObject(result);

                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            //2-1.审核同步返回状态处理
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                //保存同步信息
                                LambdaUpdateWrapper<SupplierInfo> updateItemWrapper=new LambdaUpdateWrapper<>();
                                updateItemWrapper.eq(SupplierInfo::getSysSupCode,fNumber)
                                        .set(SupplierInfo::getSupplierId,fID)
                                        .set(SupplierInfo::getSysAuditStatus,AuditStatusEnum.SYNC_ERP.getName())
                                        .set(SupplierInfo::getSyncTime,DateUtil.date())
                                        .set(SupplierInfo::getSyncResultMsg,result);
                                mapper.update(null,updateItemWrapper);
                                return "";
                            }
                            else{
                                //2-2.k3跨组织审核异常返回结果保存
                                String sql = "SYNC_RESULT_MSG=SYNC_RESULT_MSG||'";
                                sql=sql+"to_char('||')||"+result + "'";

                                //保存同步信息
                                LambdaUpdateWrapper<SupplierInfo> updateItemWrapper=new LambdaUpdateWrapper<>();
                                updateItemWrapper.eq(SupplierInfo::getSysSupCode,fNumber)
                                        .set(SupplierInfo::getSupplierId,fID)
                                        .set(SupplierInfo::getSyncTime,DateUtil.date())
                                        .set(SupplierInfo::getSyncResultMsg,result);
                                mapper.update(null,updateItemWrapper);
                                String error = JSON.parseObject(String.valueOf(resultResponse.getJSONArray("Errors").get(0))).getString("Message");
                                log.error( "供应商接口审核执行完成，" + "返回结果:" + error);
                                return error;
                            }
                        }
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("供应商:[{}] 审核异常，异常信息:[{}]",fNumber,e.getMessage());
        }
        return "";
    }

    public String unAudit(String fID,String fNumber) throws Exception {
        try{
            K3CloudApi api=new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {

                String jsonData = "{\"Numbers\":[\""+fNumber+"\"],\"Ids\":\""+fID+"\"}";

                String result = api.unAudit(k3FormId, jsonData);

                log.info( "供应商接口反审核执行完成[{}]，返回结果:[{}]",fNumber,result);

                JSONObject  resultJson = JSON.parseObject(result);

                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject  resultValue= JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject  resultResponse= JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            //2-1.反审核同步返回状态处理
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                return "";
                            }else{
                                return "金蝶erp，反审核失败！";
                            }
                        }
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("供应商:[{}] 反审核异常，异常信息:[{}]",fNumber,e.getMessage());
        }
        return "";
    }
    public IdentifyInfo getConfigInfo() {

        IdentifyInfo identifyInfoAdb=new IdentifyInfo(){};
        //应用id
        identifyInfoAdb.setAppId(k3CloudWebapiConfig.getAppid());

        identifyInfoAdb.setAppSecret(k3CloudWebapiConfig.getAppsec());
        //账套id
        identifyInfoAdb.setdCID(k3CloudWebapiConfig.getAcctid());

        identifyInfoAdb.setUserName(k3CloudWebapiConfig.getUsername());

        identifyInfoAdb.setServerUrl(k3CloudWebapiConfig.getServerurl());
        return identifyInfoAdb;
    }

    public Map getCodeName(){
        //反射获取类对象
        Class<SupplierInfo> invoiceImgExcelClass = SupplierInfo.class;
        //获得所有字段
        Field[] declaredFields = invoiceImgExcelClass.getDeclaredFields();
        List<ApiModelProperty> excelHeaderAnnotations = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (Field field : declaredFields) {
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if (annotation != null) {
                map.put(field.getName(), annotation.value());
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("name====>" + entry.getKey()+"value=============>"+entry.getValue() );
        }
        return map;
    }

}
