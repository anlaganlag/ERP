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

import com.tadpole.cloud.externalSystem.api.ebms.model.param.PrintKfyLabelParam;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleInv;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleInvDet;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleManage;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleManageParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleRprParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleManageResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleRprResult;
import com.tadpole.cloud.product.core.util.GeneratorNoUtil;
import com.tadpole.cloud.product.modular.consumer.EbmsPrintServiceConsumer;
import com.tadpole.cloud.product.modular.consumer.RdPreProposalServiceConsumer;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSampleInvDetMapper;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSampleInvMapper;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSampleManageMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleManageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleRprService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 提案-开发样管理 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Service
@Slf4j
public class RdSampleManageServiceImpl extends ServiceImpl<RdSampleManageMapper, RdSampleManage> implements IRdSampleManageService {

    @Resource
    private RdSampleManageMapper mapper;

    @Resource
    private GeneratorNoUtil generatorNoUtil;

    @Resource
    private RdPreProposalServiceConsumer oaServiceConsumer;

    @Resource
    private EbmsPrintServiceConsumer ebmsPrintServiceConsumer;

    @Resource
    private IRdSampleRprService sampleRprService;

    @Resource
    private RdSampleInvMapper rdSampleInvMapper;

    @Resource
    private RdSampleInvDetMapper rdSampleInvDetMapper;

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleManageResult> listSample(RdSampleManageParam param) {
        return this.mapper.listSample(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleManageResult> listTestSample(RdSampleManageParam param) {
        return this.mapper.listTestSample(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData add(List<RdSampleManageParam> params) {

        LoginUser loginUser = LoginContext.me().getLoginUser();
        Date oprDate = new Date();

        HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();

        //提交校验必填项
        if (params.get(0).getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_SUBMIT.getName())) {

            StringBuilder stringBuilder = new StringBuilder();
            for (RdSampleManageParam param : params) {
                String str = "";
                if (ObjectUtil.isNotNull(param.getSysKfySc())) {
                    if (param.getSysKfySc().equals(RdProposalEnum.KFY_SC_SUPPLIER.getName())) {

                        if (ObjectUtil.isNull(param.getSysKfySupplierName())) {
                            str = str + " 【供应商名称】必填";
                        }
                    } else {
                        if (ObjectUtil.isNull(param.getSysKfyShopName())) {
                            str = str + " 【店铺名称】必填";
                        }

                        if (ObjectUtil.isNull(param.getSysKfyProPurPage())) {
                            str = str + " 【购买页面】必填";
                        }
                    }
                } else {
                    str = str + " 【拿样渠道】必填";
                }

                if (ObjectUtil.isNull(param.getSysKfySampleName())) {
                    str = str + " 【样品名称】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyGoodsSource())) {
                    str = str + " 【货源地】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyPackType())) {
                    str = str + " 【包装方式】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyStyle())) {
                    str = str + " 【款式】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyBrand())) {
                    str = str + " 【适用品牌或对象】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyMainMaterial())) {
                    str = str + " 【材质】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyPattern())) {
                    str = str + " 【图案】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyColor())) {
                    str = str + " 【颜色】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyPackQty())) {
                    str = str + " 【包装数量】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfySampleSize())) {
                    str = str + " 【样品尺寸】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfySamplePackSize())) {
                    str = str + " 【样品包装尺寸】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyNetWeight())) {
                    str = str + " 【净重】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyGrossWeight())) {
                    str = str + " 【毛重】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfySampleFee())) {
                    str = str + " 【样品费用】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfySamplePic())) {
                    str = str + " 【样品图片】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyQuotePrice())) {
                    str = str + " 【初次报价】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyTaxRate())) {
                    str = str + " 【税率】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyMinOrderQty())) {
                    str = str + " 【起订量】必填";
                }
                if (ObjectUtil.isNull(param.getSysKfyProductCycle())) {
                    str = str + " 【生产周期】必填";
                }

                if (!str.equals("")) {
                    str = "样品编号【" + param.getSysKfyCode() + "】" + str;
                    stringBuilder.append(str);
                }

            }

            if (ObjectUtil.isNotNull(stringBuilder.toString()) && !stringBuilder.toString().equals("")) {
                return ResponseData.error(stringBuilder.toString());
            }
        }

        List<RdSampleManage> rdSampleManages = new ArrayList<>();
        for (RdSampleManageParam param : params) {

            RdSampleManage rdSampleManage = BeanUtil.copyProperties(param, RdSampleManage.class);
            rdSampleManage.setSysCDate(oprDate);
            rdSampleManage.setSysLDate(oprDate);
            rdSampleManage.setSysDeptCode(hrm.getIdall());
            rdSampleManage.setSysDeptName(hrm.getDepartmentname());
            rdSampleManage.setSysPerCode(loginUser.getAccount());
            rdSampleManage.setSysPerName(loginUser.getName());
            rdSampleManage.setSysKfyPurPc(loginUser.getAccount());
            rdSampleManage.setSysKfyPurPn(loginUser.getName());
            rdSampleManage.setSysKfySampleUseLevel(RdProposalEnum.KFY_SAMPLE_USE_LEVEL_WKF.getName());
            rdSampleManage.setSysKfyInvStatus(RdProposalEnum.KFY_INV_STATUS_EMPTY.getName());

            if (ObjectUtil.isNull(param.getSysKfyCode())) {
                rdSampleManage.setSysKfyCode(generatorNoUtil.getSampleBillNoExtents("0000", "KFY", 4));
            }

            if (param.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_SUBMIT.getName())) {
                rdSampleManage.setSysKfyStatus(RdProposalEnum.KFY_STATE_WAIT_DEV_USE.getName());
                rdSampleManage.setSysKfySubDate(oprDate);
            } else {
                rdSampleManage.setSysKfyStatus(RdProposalEnum.KFY_STATE_WAIT_SUBMIT.getName());
            }
            rdSampleManage.setSysKfyLableStatus(RdProposalEnum.KFY_LABEL_PRINT_NO.getName());

            rdSampleManages.add(rdSampleManage);
        }

        if (params.get(0).getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_SUBMIT.getName())) {
            if (this.saveOrUpdateBatch(rdSampleManages)) {
                return ResponseData.success("样品登记提交成功.");
            } else {
                return ResponseData.error("样品登记提交失败.");
            }
        } else {
            if (this.saveOrUpdateBatch(rdSampleManages)) {
                return ResponseData.success("样品登记保存成功.");
            } else {
                return ResponseData.error("样品登记保存失败.");
            }
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData sampleToDisposeOfReturnCheck(List<RdSampleManageParam> params) {

        RdSampleRprParam rprParam = new RdSampleRprParam();
        rprParam.setSysFeeAppCodeList(params.stream().map(RdSampleManageParam::getSysKfyFeeCode).collect(Collectors.toList()));

        List<RdSampleRprResult> rdSampleRprResults = sampleRprService.listSampleRpr(rprParam);

        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = new ArrayList<>();
        if (rdSampleRprResults.size() > 0) {
            for (RdSampleRprResult r : rdSampleRprResults) {
                List<RdSampleManageParam> checks = params.stream().filter(l -> l.getSysKfyFeeCode().equals(r.getSysFeeAppCode())).collect(Collectors.toList());
                checks.stream().map(RdSampleManageParam::getSysKfyCode).collect(Collectors.toList()).stream().forEach(l -> {
                    list.add(l);
                });
            }
            return ResponseData.error("已勾选样品编号【" + String.join(",", list) + "】,对应的购样申请已进行过退货款申请,该选择样品不能进行退货操作.");
        } else {
            return ResponseData.success();
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData sampleToDisposeOf(List<RdSampleManageParam> params) {
        try {
            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            RdSampleManageParam rdSampleManageParam = params.get(0);
            List<String> sysKfyCodes = params.stream().map(RdSampleManageParam::getSysKfyCode).collect(Collectors.toList());

            if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_DELETE.getName())) { //删除
                QueryWrapper<RdSampleManage> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("SYS_KFY_CODE", sysKfyCodes);

                if (this.mapper.delete(queryWrapper) > 0) {
                    return ResponseData.success("样品删除成功.");
                } else {
                    return ResponseData.success("样品删除失败.");
                }

            }

            UpdateWrapper<RdSampleManage> updateWrapper = new UpdateWrapper<>();
            updateWrapper.in("SYS_KFY_CODE", sysKfyCodes);

            if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_PRINT.getName())) { //打印

                List<PrintKfyLabelParam> listPrint = new ArrayList<>();
                params.forEach(l -> {
                    PrintKfyLabelParam printKfyLabelResult = new PrintKfyLabelParam();
                    printKfyLabelResult.setKfyLabelNumber(l.getSysKfyCode());
                    printKfyLabelResult.setMatOperateCate("");
                    printKfyLabelResult.setMatProName("");
                    listPrint.add(printKfyLabelResult);
                });

                ResponseData responseData = ebmsPrintServiceConsumer.printKfyLabel(listPrint);
                if (responseData.getSuccess() && responseData.getCode().equals(200)) {
                    updateWrapper.set("SYS_KFY_LABLE_STATUS", RdProposalEnum.KFY_LABEL_PRINT_YES.getName());
                    if (this.mapper.update(null, updateWrapper) > 0) {
                        return ResponseData.success("样品条码打印成功.");
                    } else {
                        return ResponseData.error("样品条码打印失败.");
                    }
                } else {
                    return ResponseData.error("样品条码打印失败.");
                }
            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_PUR_LOSS.getName())) { //采购报失
                updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_LOST.getName()).set("SYS_KFY_LOST_OP_DATE", new Date());
                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("样品报失成功.");
                } else {
                    return ResponseData.error("样品报失失败.");
                }
            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_LOSS.getName())) { //报失
                List<RdSampleManageParam> manageParamsInv = params.stream().filter(l->l.getSysKfyInvStatus().equals(RdProposalEnum.KFY_INV_STATUS_NO.getName())).collect(Collectors.toList());
                List<RdSampleManageParam> manageParams = params.stream().filter(l-> !l.getSysKfyInvStatus().equals(RdProposalEnum.KFY_INV_STATUS_NO.getName())).collect(Collectors.toList());
                boolean updateWrapperResult = true;
                if (manageParams.size() > 0){
                    updateWrapper = new UpdateWrapper<>();
                    updateWrapper.in("SYS_KFY_CODE", manageParams.stream().map(RdSampleManageParam::getSysKfyCode).collect(Collectors.toList()));
                    updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_LOST.getName()).set("SYS_KFY_LOST_DATE", new Date());

                    updateWrapperResult = this.mapper.update(null,updateWrapper) > 0;
                }

                boolean manageUpdateResult = true;
                boolean sampleInvDetResult = true;
                if (manageParamsInv.size() > 0){
                    UpdateWrapper<RdSampleManage> manageUpdateWrapper = new UpdateWrapper<>();
                    manageUpdateWrapper.in("SYS_KFY_CODE", manageParamsInv.stream().map(RdSampleManageParam::getSysKfyCode).collect(Collectors.toList()));
                    updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_LOST.getName()).set("SYS_KFY_LOST_DATE", new Date())
                            .set("SYS_KFY_INV_STATUS", RdProposalEnum.KFY_INV_STATUS_YES.getName());

                    QueryWrapper<RdSampleInvDet> sampleInvDetQueryWrapper = new QueryWrapper<>();
                    sampleInvDetQueryWrapper.eq("SYS_INV_CODE", manageParamsInv.get(0).getSysKfyInvCode()).eq("SYS_INV_DISPOSAL_RESULT", RdProposalEnum.INV_DISPOSAL_RESULT_SAVE.getName());
                    Optional<RdSampleInvDet> optRdSampleInvDet = this.rdSampleInvDetMapper.selectList(sampleInvDetQueryWrapper).stream().findFirst();
                    RdSampleInvDet rdSampleInvDet = null;
                    if (optRdSampleInvDet.isPresent()){
                        rdSampleInvDet = optRdSampleInvDet.get();
                    }

                    rdSampleInvDet.setSysInvSampleQty(rdSampleInvDet.getSysInvSampleQty().add(BigDecimal.valueOf(manageParamsInv.size())));
                    rdSampleInvDet.setSysInvAccuFee(rdSampleInvDet.getSysInvAccuFee().add(manageParamsInv.stream().filter(l->ObjectUtil.isNotNull(l.getSysKfySampleFee())).map(n->n.getSysKfySampleFee()).reduce(BigDecimal.ZERO,BigDecimal::add)));

                    manageUpdateResult = this.mapper.update(null,manageUpdateWrapper)> 0;
                    sampleInvDetResult = this.rdSampleInvDetMapper.updateById(rdSampleInvDet) > 0;

                    if (updateWrapperResult && manageUpdateResult && sampleInvDetResult){
                        QueryWrapper<RdSampleManage> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("SYS_KFY_INV_CODE", manageParamsInv.get(0).getSysKfyInvCode()).eq("SYS_KFY_INV_STATUS", RdProposalEnum.KFY_INV_STATUS_NO.getName());
                        if (this.mapper.selectCount(queryWrapper) == 0) {
                            UpdateWrapper<RdSampleInv> invUpdateWrapper = new UpdateWrapper<>();
                            invUpdateWrapper.eq("SYS_INV_CODE", manageParamsInv.get(0).getSysKfyInvCode()).set("SYS_INV_END_DATE", oprDate).set("SYS_INV_STATUS", RdProposalEnum.INV_STATUS_END.getName());

                            if (this.rdSampleInvMapper.update(null, invUpdateWrapper) > 0) {
                                return ResponseData.success("样品报失成功.");
                            } else {
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return ResponseData.error("样品报失失败.");
                            }
                        } else {
                            return ResponseData.success("样品报失成功.");
                        }
                    }else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("样品报失失败.");
                    }
                }else {
                    if (updateWrapperResult){
                        return ResponseData.success("样品报失成功.");
                    }else {
                        return ResponseData.error("样品报失失败.");
                    }
                }

            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_PUR_DESTROY.getName())) { //采购销毁
                updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_DESTROYED.getName()).set("SYS_KFY_PUR_DEST_DATE", new Date())
                        .set("SYS_KFY_PUR_DEST_UPC", rdSampleManageParam.getSysKfyPurDestUpc()).set("SYS_KFY_PUR_DEST_UPN", rdSampleManageParam.getSysKfyPurDestUpn());
                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("样品销毁成功.");
                } else {
                    return ResponseData.error("样品销毁失败.");
                }
            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_DESTROY.getName())) { //销毁
                List<RdSampleManageParam> manageParamsInv = params.stream().filter(l->l.getSysKfyInvStatus().equals(RdProposalEnum.KFY_INV_STATUS_NO.getName())).collect(Collectors.toList());
                List<RdSampleManageParam> manageParams = params.stream().filter(l-> !l.getSysKfyInvStatus().equals(RdProposalEnum.KFY_INV_STATUS_NO.getName())).collect(Collectors.toList());

                boolean updateWrapperResult = true;
                if (manageParams.size() > 0){
                    updateWrapper = new UpdateWrapper<>();
                    updateWrapper.in("SYS_KFY_CODE", manageParams.stream().map(RdSampleManageParam::getSysKfyCode).collect(Collectors.toList()));
                    updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_DESTROYED.getName()).set("SYS_KFY_DEST_DATE", new Date())
                            .set("SYS_KFY_DEST_UPC", rdSampleManageParam.getSysKfyDestUpc()).set("SYS_KFY_DEST_UPN", rdSampleManageParam.getSysKfyDestUpn());

                    updateWrapperResult = this.mapper.update(null,updateWrapper) > 0;
                }

                boolean manageUpdateResult = true;
                boolean sampleInvDetResult = true;
                if (manageParamsInv.size() > 0){
                    UpdateWrapper<RdSampleManage> manageUpdateWrapper = new UpdateWrapper<>();
                    manageUpdateWrapper.in("SYS_KFY_CODE", manageParamsInv.stream().map(RdSampleManageParam::getSysKfyCode).collect(Collectors.toList()));
                    manageUpdateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_DESTROYED.getName()).set("SYS_KFY_DEST_DATE", new Date())
                            .set("SYS_KFY_DEST_UPC", rdSampleManageParam.getSysKfyDestUpc()).set("SYS_KFY_DEST_UPN", rdSampleManageParam.getSysKfyDestUpn())
                            .set("SYS_KFY_INV_STATUS", RdProposalEnum.KFY_INV_STATUS_YES.getName());

                    QueryWrapper<RdSampleInvDet> sampleInvDetQueryWrapper = new QueryWrapper<>();
                    sampleInvDetQueryWrapper.eq("SYS_INV_CODE", manageParamsInv.get(0).getSysKfyInvCode()).eq("SYS_INV_DISPOSAL_RESULT", RdProposalEnum.INV_DISPOSAL_RESULT_SAVE.getName());
                    Optional<RdSampleInvDet> optRdSampleInvDet = this.rdSampleInvDetMapper.selectList(sampleInvDetQueryWrapper).stream().findFirst();
                    RdSampleInvDet rdSampleInvDet = null;
                    if (optRdSampleInvDet.isPresent()){
                        rdSampleInvDet = optRdSampleInvDet.get();
                    }

                    rdSampleInvDet.setSysInvSampleQty(rdSampleInvDet.getSysInvSampleQty().add(BigDecimal.valueOf(manageParamsInv.size())));
                    rdSampleInvDet.setSysInvAccuFee(rdSampleInvDet.getSysInvAccuFee().add(manageParamsInv.stream().filter(l->ObjectUtil.isNotNull(l.getSysKfySampleFee())).map(n->n.getSysKfySampleFee()).reduce(BigDecimal.ZERO,BigDecimal::add)));

                    manageUpdateResult = this.mapper.update(null,manageUpdateWrapper)> 0;
                    sampleInvDetResult = this.rdSampleInvDetMapper.updateById(rdSampleInvDet) > 0;

                    if (updateWrapperResult && manageUpdateResult && sampleInvDetResult){
                        QueryWrapper<RdSampleManage> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("SYS_KFY_INV_CODE", manageParamsInv.get(0).getSysKfyInvCode()).eq("SYS_KFY_INV_STATUS", RdProposalEnum.KFY_INV_STATUS_NO.getName());
                        if (this.mapper.selectCount(queryWrapper) == 0) {
                            UpdateWrapper<RdSampleInv> invUpdateWrapper = new UpdateWrapper<>();
                            invUpdateWrapper.eq("SYS_INV_CODE", manageParamsInv.get(0).getSysKfyInvCode()).set("SYS_INV_END_DATE", oprDate).set("SYS_INV_STATUS", RdProposalEnum.INV_STATUS_END.getName());

                            if (this.rdSampleInvMapper.update(null, invUpdateWrapper) > 0) {
                                return ResponseData.success("样品销毁成功.");
                            } else {
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return ResponseData.error("样品销毁失败.");
                            }
                        } else {
                            return ResponseData.success("样品销毁成功.");
                        }
                    }else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ResponseData.error("样品销毁失败.");
                    }
                }else {
                    if (updateWrapperResult){
                        return ResponseData.success("样品销毁成功.");
                    }else {
                        return ResponseData.error("样品销毁失败.");
                    }
                }
            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_SIGN.getName())) { //签收
                updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_IN_EVALUATE.getName()).set("SYS_KFY_USE_DATE", new Date())
                        .set("SYS_KFY_SAMPLE_USE_LEVEL", RdProposalEnum.KFY_SAMPLE_USE_LEVEL_YKF.getName());
                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("样品销毁成功.");
                } else {
                    return ResponseData.error("样品销毁失败.");
                }

            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_FEEDBACK.getName())) { //反馈
                for (RdSampleManageParam param : params) {
                    UpdateWrapper<RdSampleManage> updateWrapperF = new UpdateWrapper<>();
                    updateWrapperF.eq("SYS_KFY_CODE", param.getSysKfyCode());
                    updateWrapperF.set("SYS_KFY_FEBK_DATE", new Date()).set("SYS_KFY_FEBK_INFO", param.getSysKfyFebkInfo())
                            .set("SYS_KFY_FEBK_IS_VALID", param.getSysKfyFebkIsValid());

                    if (param.getSysKfyRefMethod().equals(RdProposalEnum.KFY_REF_METHOD_REPORT_LOSS.getName())) {
                        updateWrapperF.set("SYS_KFY_REF_METHOD", param.getSysKfyRefMethod()).set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_LOST.getName());
                    } else if (param.getSysKfyRefMethod().equals(RdProposalEnum.KFY_REF_METHOD_REPORTING_LOSS.getName())) {
                        updateWrapperF.set("SYS_KFY_REF_METHOD", param.getSysKfyRefMethod()).set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_TO_RETURN.getName()).set("SYS_KFY_SAMPLE_USE_LEVEL", RdProposalEnum.KFY_SAMPLE_USE_LEVEL_YSH.getName());
                    } else {
                        updateWrapperF.set("SYS_KFY_REF_METHOD", param.getSysKfyRefMethod()).set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_TO_RETURN.getName());
                    }
                    this.mapper.update(null, updateWrapperF);
                }
                return ResponseData.success("样品反馈成功.");
            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_COLLECT.getName())) { //收回
                updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_TO_BE_DISPOSED.getName()).set("SYS_KFY_REF_DATE", new Date());
                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("样品收回成功");
                } else {
                    return ResponseData.error("样品收回失败");
                }
            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_RETURN.getName())) { //退货-----待补充

                Map<String, List<RdSampleManageParam>> groupListMap = params.stream().collect(Collectors.groupingBy(l -> l.getSysPlCode() + "|" + l.getSysSpu() + "|" + l.getSysTaCode() + "|" + l.getSysTsTaskCode() + "|" + l.getSysKfyFeeSource() + "|" + l.getSysKfyFeeCode()));
                List<RdSampleRprParam> rdSampleRprParams = new ArrayList<>();
                groupListMap.forEach((key, value) -> {

                    RdSampleRprParam rprParam = new RdSampleRprParam();
                    rprParam.setSysPlCode(value.get(0).getSysPlCode());
                    rprParam.setSysSpu(value.get(0).getSysSpu());
                    rprParam.setSysTaCode(value.get(0).getSysTaCode());
                    rprParam.setSysTsTaskCode(value.get(0).getSysTsTaskCode());
                    rprParam.setSysFeeAppCode(value.get(0).getSysKfyFeeCode());
                    rprParam.setSysFeeAppSource(value.get(0).getSysKfyFeeSource());
                    rprParam.setSysRefOpDate(value.get(0).getSysKfyRgDate());
                    rprParam.setSysRefQty(BigDecimal.valueOf(value.size()));
                    rprParam.setSysRefPreAmount(value.stream().map(RdSampleManageParam::getSysKfySampleFee).reduce(BigDecimal.ZERO, BigDecimal::add));

                    rdSampleRprParams.add(rprParam);

                });

                ResponseData responseData = this.sampleRprService.add(rdSampleRprParams);
                if (responseData.getSuccess() && responseData.getCode().equals(200)) {
                    updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_RETURNED.getName()).set("SYS_KFY_RG_DATE", new Date()).set("SYS_KFY_RG_OP_DATE", new Date());
                    if (this.mapper.update(null, updateWrapper) > 0) {

                        return ResponseData.success("样品退货成功");
                    } else {
                        return ResponseData.error("样品退货失败");
                    }
                } else {
                    return ResponseData.error("样品退货失败");
                }
            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_RECEIVE.getName())) { //接收

                /*if (rdSampleManageParam.getSysKfyRecSm().equals(RdProposalEnum.KFY_REF_METHOD_REPORT_LOSS.getName())) {
                    updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_LOST.getName()).set("SYS_KFY_REC_DATE", new Date())
                            .set("SYS_KFY_REC_SM", rdSampleManageParam.getSysKfyRecSm()).set("SYS_KFY_REC_PC", loginUser.getAccount())
                            .set("SYS_KFY_REC_PN", loginUser.getName()).set("SYS_KFY_LOST_DATE", new Date());
                } else {*/
                    updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_RECEIVE.getName()).set("SYS_KFY_REC_DATE", new Date())
                            .set("SYS_KFY_REC_SM", RdProposalEnum.KFY_REF_METHOD_NORMAL.getName()).set("SYS_KFY_REC_PC", loginUser.getAccount())
                            .set("SYS_KFY_REC_PN", loginUser.getName());
                //}
                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("样品接收成功");
                } else {
                    return ResponseData.error("样品接收失败");
                }
            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_USE.getName())) { //领用
                updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_USE.getName()).set("SYS_KFY_COLLECT_DATE", new Date())
                        .set("SYS_KFY_COLLECT_PC", loginUser.getAccount()).set("SYS_KFY_COLLECT_PN", loginUser.getName());
                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("样品领用成功");
                } else {
                    return ResponseData.error("样品领用失败");
                }
            } else if (rdSampleManageParam.getSysFuncOpr().equals(RdProposalEnum.KFY_FUNC_DEPOSIT.getName())) { //存放
                updateWrapper.set("SYS_KFY_SEAL_DATE", new Date()).set("SYS_KFY_SHELF_NAME", rdSampleManageParam.getSysKfyShelfName())
                        .set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_SEALED.getName());
                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("样品存放成功");
                } else {
                    return ResponseData.error("样品存放失败");
                }
            } else { //提交

                //提交校验必填项
                StringBuilder stringBuilder = new StringBuilder();
                for (RdSampleManageParam param : params) {
                    String str = "";
                    if (ObjectUtil.isNotNull(param.getSysKfySc())) {
                        if (param.getSysKfySc().equals(RdProposalEnum.KFY_SC_SUPPLIER.getName())) {

                            if (ObjectUtil.isNull(param.getSysKfySupplierName())) {
                                str = str + " 【供应商名称】必填";
                            }
                        } else {
                            if (ObjectUtil.isNull(param.getSysKfyShopName())) {
                                str = str + " 【店铺名称】必填";
                            }

                            if (ObjectUtil.isNull(param.getSysKfyProPurPage())) {
                                str = str + " 【购买页面】必填";
                            }
                        }
                    } else {
                        str = str + " 【拿样渠道】必填";
                    }

                    if (ObjectUtil.isNull(param.getSysKfySampleName())) {
                        str = str + " 【样品名称】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyGoodsSource())) {
                        str = str + " 【货源地】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyPackType())) {
                        str = str + " 【包装方式】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyStyle())) {
                        str = str + " 【款式】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyBrand())) {
                        str = str + " 【适用品牌或对象】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyMainMaterial())) {
                        str = str + " 【材质】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyPattern())) {
                        str = str + " 【图案】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyColor())) {
                        str = str + " 【颜色】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyPackQty())) {
                        str = str + " 【包装数量】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfySampleSize())) {
                        str = str + " 【样品尺寸】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfySamplePackSize())) {
                        str = str + " 【样品包装尺寸】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyNetWeight())) {
                        str = str + " 【净重】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyGrossWeight())) {
                        str = str + " 【毛重】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfySampleFee())) {
                        str = str + " 【样品费用】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfySamplePic())) {
                        str = str + " 【样品图片】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyQuotePrice())) {
                        str = str + " 【初次报价】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyTaxRate())) {
                        str = str + " 【税率】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyMinOrderQty())) {
                        str = str + " 【起订量】必填";
                    }
                    if (ObjectUtil.isNull(param.getSysKfyProductCycle())) {
                        str = str + " 【生产周期】必填";
                    }

                    if (!str.equals("")) {
                        str = "样品编号【" + param.getSysKfyCode() + "】" + str;
                        stringBuilder.append(str);
                    }

                }

                if (ObjectUtil.isNotNull(stringBuilder.toString()) && !stringBuilder.toString().equals("")) {
                    return ResponseData.error(stringBuilder.toString());
                }

                updateWrapper.set("SYS_KFY_STATUS", RdProposalEnum.KFY_STATE_WAIT_DEV_USE.getName()).set("SYS_KFY_SUB_DATE", new Date())
                        .set("SYS_KFY_PUR_PC", loginUser.getAccount()).set("SYS_KFY_PUR_PN", loginUser.getName());
                if (this.mapper.update(null, updateWrapper) > 0) {
                    return ResponseData.success("样品提交成功");
                } else {
                    return ResponseData.error("样品提交失败");
                }
            }
        }catch (Exception exception){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public PageResult<RdSampleManageResult> listPage(RdSampleManageParam param) {
        Page pageContext = param.getPageContext();

        IPage<RdSampleManageResult> page = this.mapper.listPage(pageContext, param);

        return new PageResult<>(page);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public RdSampleManageResult detail(RdSampleManageParam param) {
        return this.mapper.detail(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleManageResult> listSampleManage(RdSampleManageParam param) {
        return this.mapper.listSampleManage(param);
    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData statisticsSample(RdSampleManageParam param) {

        try {
            QueryWrapper<RdSampleManage> queryWrapper = new QueryWrapper<>();
            if (ObjectUtil.isNotNull(param.getSysTsTaskCode())) {
                queryWrapper.eq("SYS_TS_TASK_CODE", param.getSysTsTaskCode());
            }
            if (ObjectUtil.isNotNull(param.getSysTaCode())) {
                queryWrapper.eq("SYS_TA_CODE", param.getSysTaCode());
            }

            List<RdSampleManage> rdSampleManageList = this.mapper.selectList(queryWrapper);

            Map result = new HashMap();
            result.put("totalCount", rdSampleManageList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysKfyFebkIsValid())).count());
            result.put("validCount", rdSampleManageList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysKfyFebkIsValid()) && l.getSysKfyFebkIsValid().equals(RdProposalEnum.KFY_FEBK_IS_VALID_YES.getName())).count());
            result.put("invalidCount", rdSampleManageList.stream().filter(l -> ObjectUtil.isNotNull(l.getSysKfyFebkIsValid()) && (l.getSysKfyFebkIsValid().equals(RdProposalEnum.KFY_FEBK_IS_VALID_NO.getName()))).count());

            return ResponseData.success(result);
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData invSample() {

        try {

            LoginUser loginUser = LoginContext.me().getLoginUser();
            Date oprDate = new Date();
            HrmresourcetoebmsResult hrm = oaServiceConsumer.getHrmResource().stream().filter(d -> d.getWorkcode().equals(loginUser.getAccount())).findFirst().get();
            String sysInvCode = "PD-" + new SimpleDateFormat("yyyy-MM-dd").format(oprDate);
            String[] sysInvDisposalResults = {RdProposalEnum.INV_DISPOSAL_RESULT_SAVE.getName(), RdProposalEnum.INV_DISPOSAL_RESULT_LOSS.getName(), RdProposalEnum.INV_DISPOSAL_RESULT_DES.getName()};
            String[] sysKfyStatus = {RdProposalEnum.KFY_STATE_SEALED.getName(), RdProposalEnum.KFY_STATE_USE.getName()};

            RdSampleInv rdSampleInv = new RdSampleInv();
            rdSampleInv.setSysInvCode(sysInvCode);
            rdSampleInv.setSysCDate(oprDate);
            rdSampleInv.setSysLDate(oprDate);
            rdSampleInv.setSysInvPerCode(loginUser.getAccount());
            rdSampleInv.setSysInvPerName(loginUser.getName());
            rdSampleInv.setSysDeptCode(hrm.getIdall());
            rdSampleInv.setSysDeptName(hrm.getDepartmentname());
            rdSampleInv.setSysInvStartDate(oprDate);
            rdSampleInv.setSysInvStatus(RdProposalEnum.INV_STATUS_ING.getName());

            List<RdSampleInvDet> rdSampleInvDets = new ArrayList<>();
            for (String str : sysInvDisposalResults) {
                RdSampleInvDet rdSampleInvDet = new RdSampleInvDet();
                rdSampleInvDet.setSysInvCode(sysInvCode);
                rdSampleInvDet.setSysInvDisposalResult(str);
                rdSampleInvDet.setSysInvSampleQty(BigDecimal.ZERO);
                rdSampleInvDet.setSysInvAccuFee(BigDecimal.ZERO);
                rdSampleInvDet.setId(UUID.randomUUID().toString().replace("-", ""));
                rdSampleInvDets.add(rdSampleInvDet);
            }

            UpdateWrapper<RdSampleManage> updateWrapper = new UpdateWrapper<>();
            updateWrapper.isNotNull("SYS_KFY_REC_DATE").in("SYS_KFY_STATUS", sysKfyStatus);
            updateWrapper.set("SYS_KFY_INV_CODE", sysInvCode).set("SYS_KFY_INV_STATUS",RdProposalEnum.KFY_INV_STATUS_NO.getName());

            boolean rdSampleInvResult = true;
            boolean rdSampleManageResult = true;
            boolean rdSampleInvDetResult = true;

            rdSampleInvResult = this.rdSampleInvMapper.insert(rdSampleInv) > 0;

            for (RdSampleInvDet r : rdSampleInvDets) {
                rdSampleManageResult = this.rdSampleInvDetMapper.insert(r) > 0;

                if (!rdSampleManageResult){
                    rdSampleInvDetResult = false;
                }
            }

            rdSampleManageResult = this.mapper.update(null, updateWrapper) > 0;
            if (rdSampleInvResult && rdSampleManageResult && rdSampleInvDetResult) {
                QueryWrapper<RdSampleManage> manageQueryWrapper = new QueryWrapper<>();
                manageQueryWrapper.eq("SYS_KFY_INV_CODE", sysInvCode);
                List<RdSampleManage> rdSampleManages = this.mapper.selectList(manageQueryWrapper);

                Map<String, Object> result = new HashMap<>();
                result.put("sysInvStartDate", rdSampleInv.getSysInvStartDate());
                result.put("sysInvCode", rdSampleInv.getSysInvCode());
                result.put("sysInvTotalQty", rdSampleManages.size());
                result.put("sysInvCheckedQty", rdSampleManages.stream().filter(l -> l.getSysKfyInvStatus().equals(RdProposalEnum.KFY_INV_STATUS_YES.getName())).count());
                result.put("sysInvNoCheckedQty", rdSampleManages.stream().filter(l -> l.getSysKfyInvStatus().equals(RdProposalEnum.KFY_INV_STATUS_NO.getName())).count());
                return ResponseData.success(result);
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResponseData.error("开发样盘点开启失败.");
            }
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional
    public ResponseData checkInvSample() {
        try {
            QueryWrapper<RdSampleInv> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_INV_STATUS", RdProposalEnum.INV_STATUS_ING.getName());
            List<RdSampleInv> rdSampleInvs = this.rdSampleInvMapper.selectList(queryWrapper);
            if (rdSampleInvs.size() == 0){
                String[] sysKfyStatus = {RdProposalEnum.KFY_STATE_SEALED.getName(), RdProposalEnum.KFY_STATE_USE.getName()};
                QueryWrapper<RdSampleManage> manageQueryWrapper = new QueryWrapper<>();
                manageQueryWrapper.isNotNull("SYS_KFY_REC_DATE").in("SYS_KFY_STATUS", sysKfyStatus);
                if (this.mapper.selectCount(manageQueryWrapper) == 0){
                    return ResponseData.error("系统里没有可盘点的开发样.");
                }else {
                    return ResponseData.success();
                }
            } else {
                RdSampleInv rdSampleInv = rdSampleInvs.get(0);
                QueryWrapper<RdSampleManage> manageQueryWrapper = new QueryWrapper<>();
                manageQueryWrapper.eq("SYS_KFY_INV_CODE", rdSampleInv.getSysInvCode());
                List<RdSampleManage> rdSampleManages = this.mapper.selectList(manageQueryWrapper);

                Map<String, Object> result = new HashMap<>();
                result.put("sysInvStartDate", rdSampleInv.getSysInvStartDate());
                result.put("sysInvCode", rdSampleInv.getSysInvCode());
                result.put("sysInvTotalQty", rdSampleManages.size());
                result.put("sysInvCheckedQty", rdSampleManages.stream().filter(l -> l.getSysKfyInvStatus().equals(RdProposalEnum.KFY_INV_STATUS_YES.getName())).count());
                result.put("sysInvNoCheckedQty", rdSampleManages.stream().filter(l -> l.getSysKfyInvStatus().equals(RdProposalEnum.KFY_INV_STATUS_NO.getName())).count());
                return ResponseData.success(result);
            }
        } catch (Exception exception) {
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }

    @DataSource(name = "product")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData startInv(RdSampleManageParam param) {
        try {
            Date oprDate = new Date();
            QueryWrapper<RdSampleManage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SYS_KFY_INV_STATUS", RdProposalEnum.KFY_INV_STATUS_NO.getName()).eq("SYS_KFY_CODE", param.getSysKfyCode());
            RdSampleManage rdSampleManage = this.mapper.selectOne(queryWrapper);
            if (ObjectUtil.isNotNull(rdSampleManage)) {

                QueryWrapper<RdSampleInvDet> sampleInvDetQueryWrapper = new QueryWrapper<>();
                sampleInvDetQueryWrapper.eq("SYS_INV_CODE", rdSampleManage.getSysKfyInvCode()).eq("SYS_INV_DISPOSAL_RESULT", RdProposalEnum.INV_DISPOSAL_RESULT_SAVE.getName());
                RdSampleInvDet rdSampleInvDet = this.rdSampleInvDetMapper.selectOne(sampleInvDetQueryWrapper);

                rdSampleInvDet.setSysInvSampleQty(rdSampleInvDet.getSysInvSampleQty().add(BigDecimal.valueOf(1)));
                rdSampleInvDet.setSysInvAccuFee(rdSampleInvDet.getSysInvAccuFee().add(rdSampleManage.getSysKfySampleFee()));

                UpdateWrapper<RdSampleManage> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("SYS_KFY_CODE", param.getSysKfyCode()).eq("SYS_KFY_INV_CODE", rdSampleManage.getSysKfyInvCode())
                        .set("SYS_KFY_INV_STATUS", RdProposalEnum.KFY_INV_STATUS_YES.getName());

                if (this.rdSampleInvDetMapper.updateById(rdSampleInvDet) > 0 && this.mapper.update(null, updateWrapper) > 0) {
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("SYS_KFY_INV_CODE", rdSampleManage.getSysKfyInvCode()).eq("SYS_KFY_INV_STATUS", RdProposalEnum.KFY_INV_STATUS_NO.getName());

                    if (this.mapper.selectCount(queryWrapper) == 0) {
                        UpdateWrapper<RdSampleInv> invUpdateWrapper = new UpdateWrapper<>();
                        invUpdateWrapper.eq("SYS_INV_CODE", rdSampleManage.getSysKfyInvCode()).set("SYS_INV_END_DATE", oprDate).set("SYS_INV_STATUS", RdProposalEnum.INV_STATUS_END.getName());

                        if (this.rdSampleInvMapper.update(null, invUpdateWrapper) > 0) {
                            return ResponseData.success("开发样盘点成功.");
                        } else {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResponseData.error("开发样盘点失败.");
                        }
                    } else {
                        return ResponseData.success("开发样盘点成功.");
                    }
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResponseData.error("开发样盘点失败.");
                }
            } else {
                return ResponseData.error("当前样品货架中不存在,或者该样品已盘点完成.");
            }
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseData.error("系统处理异常: 【" + exception.getCause() + "】");
        }

    }


}
