package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.tadpole.cloud.externalSystem.api.k3.entity.ViewSupplier;
import com.tadpole.cloud.externalSystem.api.k3.model.params.K3PaymentApplyItemMap;
import com.tadpole.cloud.externalSystem.api.k3.model.params.K3PaymentApplyMap;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsK3PaymentApply;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsK3PaymentApplyDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsK3PaymentApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsPaymentApplyParam;
import com.tadpole.cloud.supplyChain.modular.consumer.ExternalConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsK3PaymentApplyMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsK3PaymentApplyDetailService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsK3PaymentApplyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 物流费K3付款申请单 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-12-05
 */
@Service
@Slf4j
public class LsK3PaymentApplyServiceImpl extends ServiceImpl<LsK3PaymentApplyMapper, LsK3PaymentApply> implements ILsK3PaymentApplyService {

    @Resource
    private LsK3PaymentApplyMapper mapper;
    @Autowired
    private ILsK3PaymentApplyDetailService paymentApplyDetailService;
    @Resource
    private ExternalConsumer externalConsumer;

    @Value("${k3webapi.acctid}")
    private String k3Acctid;
    @Value("${k3webapi.username}")
    private String k3Username;
    @Value("${k3webapi.appid}")
    private String k3Appid;
    @Value("${k3webapi.appsec}")
    private String k3Appsec;
    @Value("${k3webapi.serverurl}")
    private String k3Serverurl;
    private static String k3FormId="CN_PAYAPPLY";

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPaymentApply(LsK3PaymentApplyParam param) {
        if(StringUtils.isBlank(param.getPaymentApplyNo())){
            return ResponseData.error("单据编号为空");
        }
        LambdaQueryWrapper<LsK3PaymentApply> paQw = new LambdaQueryWrapper<>();
        paQw.eq(LsK3PaymentApply :: getPaymentApplyNo, param.getPaymentApplyNo());
        LsK3PaymentApply apply = mapper.selectOne(paQw);
        if(apply == null){
            return ResponseData.error("未查询到单据数据");
        }
        LsPaymentApplyParam result = new LsPaymentApplyParam();
        result.setApply(apply);
        LambdaQueryWrapper<LsK3PaymentApplyDetail> padQw = new LambdaQueryWrapper<>();
        padQw.eq(LsK3PaymentApplyDetail :: getPaymentApplyNo, param.getPaymentApplyNo());
        List<LsK3PaymentApplyDetail> applyDetailList = paymentApplyDetailService.list(padQw);
        result.setApplyDetailList(applyDetailList);
        return ResponseData.success(result);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData canEditPaymentApply(LsK3PaymentApplyParam param) {
        if(StringUtils.isBlank(param.getPaymentApplyNo())){
            return ResponseData.error("单据编号为空");
        }
        LambdaQueryWrapper<LsK3PaymentApply> paQw = new LambdaQueryWrapper<>();
        paQw.eq(LsK3PaymentApply :: getPaymentApplyNo, param.getPaymentApplyNo());
        LsK3PaymentApply apply = mapper.selectOne(paQw);
        if(apply == null){
            return ResponseData.error("未查询到单据数据");
        }
        if(StringUtils.isBlank(apply.getFBillNo()) || StringUtils.isBlank(apply.getFId())){
            return ResponseData.success(Boolean.TRUE);
//            return ResponseData.error("金蝶付款申请单ID或单据编号为空");
        }

        //封装查询接口数据
        String jsonData = "{\"Number\":[\""+apply.getFBillNo()+"\"],\"Id\":\""+apply.getFId()+"\"}";
        try{
            //1、查看
            K3CloudApi k3CloudApi = new K3CloudApi(getConfigInfo());
            log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(k3CloudApi));
            if (k3CloudApi.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info( "调用k3物流费付款申请单查看接口，请求参数[{}]", jsonData);
                String result = k3CloudApi.view(k3FormId, jsonData);
                log.info( "调用k3物流费付款申请单查看接口，响应参数[{}]", result);
                JSONObject resultJson = JSON.parseObject(result);
                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject resultValue = JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                //订单状态：A:创建，B:审核中，C:已审核，D:重新审核，Z:暂存
                                JSONObject dataResult = JSON.parseObject(resultValue.getString("Result"));
                                String dataStatus = dataResult.getString("FDOCUMENTSTATUS");
                                if("A".equals(dataStatus) || "D".equals(dataStatus)){
                                    return ResponseData.success(Boolean.TRUE);
                                } else {
                                    return ResponseData.success(Boolean.FALSE);
                                }
                            } else {
                                String errors = resultResponse.getString("Errors");
                                return ResponseData.error("调用k3物流费付款申请单查看接口失败，失败详情：" + errors);
                            }
                        }
                    }
                }
            }else{
                //失败返回信息
                return ResponseData.error("调用k3物流费付款申请单查看鉴权接口失败!");
            }
        }catch(Exception e){
            log.error("调用k3物流费付款申请单查看接口异常，异常信息[{}]",e.getMessage());
            return ResponseData.error("调用k3物流费付款申请单查看接口异常");
        }
        return ResponseData.error("调用k3物流费付款申请单查看接口失败！");
    }

    @DataSource(name = "logistics")
    @Override
    public ResponseData k3Save(LsK3PaymentApply k3Apply, List<LsK3PaymentApplyDetail> applyDetailList) {
        //封装保存接口数据
        JSONObject objectAll = new JSONObject();
        //物流费付款申请单单据头信息
        K3PaymentApplyMap k3PaymentApply = new K3PaymentApplyMap();
        if(StringUtils.isNotBlank(k3Apply.getFId())){
            k3PaymentApply.setFID(new BigDecimal(k3Apply.getFId()));
        }
        k3PaymentApply.setFBillNo(k3Apply.getPaymentApplyNo());
        k3PaymentApply.setFDOCUMENTSTATUS("");
        k3PaymentApply.setFSETTLEORGID(k3Apply.getSettlementCode());
        k3PaymentApply.setFCURRENCYID(k3Apply.getCurrencyCode());//币别(必填项)"PRE001"
        k3PaymentApply.setFDATE(DateUtil.format(k3Apply.getErpApplyDate(), DatePattern.NORM_DATE_PATTERN));
        k3PaymentApply.setFBILLTYPEID("FKSQ007");//物流付款申请
        k3PaymentApply.setFCANCELSTATUS("A");//作废状态(必填项)
        k3PaymentApply.setFCONTACTUNITTYPE("BD_Supplier");//供应商
        k3PaymentApply.setFCONTACTUNIT(k3Apply.getCompanyCode());//往来单位编码"S1001"
        k3PaymentApply.setFRECTUNITTYPE("BD_Supplier");//供应商
        k3PaymentApply.setFRECTUNIT(k3Apply.getReceiveCompanyCode());//收款单位编码"S1001"
        k3PaymentApply.setFPAYORGID(k3Apply.getPaymentOrgCode());
        k3PaymentApply.setFEXCHANGERATE(k3Apply.getCurrencyRate().toString());
        k3PaymentApply.setFAPPLYORGID(k3Apply.getApplyOrgCode());
        k3PaymentApply.setFSETTLECUR(k3Apply.getSettlementCurrencyCode());//结算币别(必填项)"PRE001"
        k3PaymentApply.setFSETTLERATE(k3Apply.getSettlementCurrencyRate().toString());
        k3PaymentApply.setF_BSC_Supply(k3Apply.getSupplierCode());//"011"
        k3PaymentApply.setF_BSC_Abstract(k3Apply.getRemark());
        k3PaymentApply.setF_BSC_Assistant(k3Apply.getPayTypeCode());//"FKLX_01"
//        k3PaymentApply.setF_BSC_Text2(k3Apply.getIsPrePay());
        k3PaymentApply.setF_BSC_Combo(k3Apply.getHasBill());
        k3PaymentApply.setF_Apply_person(k3Apply.getApplyAccount());//FSTAFFNUMBER
        k3PaymentApply.setF_BSC_Combo1(k3Apply.getIsSupplementBill());
        if(k3Apply.getApplyAccount() != null){
            //根据工号获取K3用户id
            ViewSupplier viewSupplier =  externalConsumer.getDeptUserId(k3Apply.getApplyAccount());
            k3PaymentApply.setFCREATORID(viewSupplier.getFname());
        }
        if(k3Apply.getUpdateUserAccount() != null){
            //根据工号获取K3用户id
            ViewSupplier viewSupplier =  externalConsumer.getDeptUserId(k3Apply.getUpdateUserAccount());
            k3PaymentApply.setFMODIFIERID(viewSupplier.getFname());
        }

        //物流费付款申请单明细信息
        ArrayList<K3PaymentApplyItemMap> itemList = new ArrayList<>();
        for (LsK3PaymentApplyDetail applyDetail : applyDetailList) {
            K3PaymentApplyItemMap k3ApplyDetailItem = new K3PaymentApplyItemMap();
            k3ApplyDetailItem.setFSETTLETYPEID(applyDetail.getSettlementTypeCode());
            k3ApplyDetailItem.setFEACHBANKACCOUNT(applyDetail.getBankAccNo());
            k3ApplyDetailItem.setFAPPLYAMOUNTFOR(applyDetail.getApplyPaymentAmt().toString());
            k3ApplyDetailItem.setFPAYPURPOSEID(applyDetail.getPaymentUseCode());
            k3ApplyDetailItem.setFENDDATE(DateUtil.format(applyDetail.getExpireDate(), DatePattern.NORM_DATE_PATTERN));
            k3ApplyDetailItem.setFEXPECTPAYDATE(DateUtil.format(applyDetail.getWishPaymentDate(), DatePattern.NORM_DATE_PATTERN));
            k3ApplyDetailItem.setFEACHCCOUNTNAME(applyDetail.getBankAccName());
            k3ApplyDetailItem.setFEACHBANKNAME(applyDetail.getBankName());
            k3ApplyDetailItem.setFDescription(applyDetail.getRemark());
            k3ApplyDetailItem.setFUnpaidAmount(applyDetail.getNotPayAmt().toString());
            k3ApplyDetailItem.setF_BSC_Text(applyDetail.getPaymentRemark());
            k3ApplyDetailItem.setF_BSC_Decimal(applyDetail.getPaymentPercent());
            k3ApplyDetailItem.setF_BSC_CODE(applyDetail.getBillCode());
            k3ApplyDetailItem.setF_BSC_Invoice(applyDetail.getBillNo());
            k3ApplyDetailItem.setF_BSC_Amount(applyDetail.getSettlementAmt().toString());
            k3ApplyDetailItem.setF_BSC_Date(DateUtil.format(applyDetail.getBillDate(), DatePattern.NORM_DATE_PATTERN));
            itemList.add(k3ApplyDetailItem);
        }
        k3PaymentApply.setFPAYAPPLYENTRY(itemList);
        objectAll.put("Model", k3PaymentApply);
        String jsonData = JSON.toJSONString(objectAll);
        try{
            //1、保存
            K3CloudApi k3CloudApi = new K3CloudApi(getConfigInfo());
            log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(k3CloudApi));
            if (k3CloudApi.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info( "调用k3物流费付款申请单保存接口，请求参数[{}]", jsonData);
                String result = k3CloudApi.save(k3FormId, jsonData);
                log.info( "调用k3物流费付款申请单保存接口，响应参数[{}]", result);
                JSONObject resultJson = JSON.parseObject(result);
                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject resultValue = JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            //K3保存成功，更新入库
                            LsK3PaymentApply apply = new LsK3PaymentApply();
                            apply.setId(k3Apply.getId());
                            String syncResultMsg = resultResponse.toJSONString();
                            apply.setSyncResultMsg(syncResultMsg.length() > 1000 ? syncResultMsg.substring(0, 1000) : syncResultMsg);
                            apply.setSyncK3Date(DateUtil.date());
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                String fId = resultValue.getString("Id");
                                String fNumber = resultValue.getString("Number");
                                apply.setFId(fId);
                                apply.setFBillNo(fNumber);
                                apply.setSyncK3Status("true");
                                this.updateById(apply);
                                return ResponseData.success();
                            } else {
                                apply.setSyncK3Status("false");
                                this.updateById(apply);
                                String errors = resultResponse.getString("Errors");
                                return ResponseData.error("调用k3物流费付款申请单保存接口失败，失败详情：" + errors);
                            }
                        }
                    }
                }
            }else{
                //失败，更新状态
                LsK3PaymentApply apply = new LsK3PaymentApply();
                apply.setId(k3Apply.getId());
                apply.setSyncResultMsg("调用k3物流费付款申请单鉴权接口失败!");
                apply.setSyncK3Status("false");
                apply.setSyncK3Date(DateUtil.date());
                this.updateById(apply);
                return ResponseData.error("调用k3物流费付款申请单鉴权接口失败!");
            }
        }catch(Exception e){
            LsK3PaymentApply apply = new LsK3PaymentApply();
            apply.setId(k3Apply.getId());
            apply.setSyncResultMsg("调用k3物流费付款申请单保存接口异常!");
            apply.setSyncK3Status("false");
            apply.setSyncK3Date(DateUtil.date());
            this.updateById(apply);
            log.error("调用k3物流费付款申请单保存接口异常，异常信息[{}]",e.getMessage());
            return ResponseData.error("调用k3物流费付款申请单保存接口异常");
        }
        return ResponseData.error("调用k3物流费付款申请单保存接口失败！");
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData delPaymentApply(LsK3PaymentApplyParam param) {
        if(StringUtils.isBlank(param.getPaymentApplyNo())){
            return ResponseData.error("付款申请单编号为空");
        }
        LambdaQueryWrapper<LsK3PaymentApply> paQw = new LambdaQueryWrapper<>();
        paQw.eq(LsK3PaymentApply :: getPaymentApplyNo, param.getPaymentApplyNo());
        LsK3PaymentApply apply = mapper.selectOne(paQw);
        if(apply == null){
            return ResponseData.error("未查询到付款申请单数据");
        }
        //封装删除接口数据
        String jsonData = "{\"Numbers\":[\""+apply.getFBillNo()+"\"],\"Ids\":\""+apply.getFId()+"\"}";
        try{
            //1、删除
            K3CloudApi k3CloudApi = new K3CloudApi(getConfigInfo());
            log.info("调用k3鉴权接口响应信息[{}]", JSONObject.toJSON(k3CloudApi));
            if (k3CloudApi.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info( "调用k3物流费付款申请删除接口，请求参数[{}]", jsonData);
                String result = k3CloudApi.delete(k3FormId, jsonData);
                log.info( "调用k3物流费付款申请单删除接口，响应参数[{}]", result);
                JSONObject resultJson = JSON.parseObject(result);
                if (ObjectUtil.isNotNull(resultJson)) {
                    JSONObject resultValue = JSON.parseObject(resultJson.getString("Result"));
                    if(ObjectUtil.isNotNull(resultValue)){
                        JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                        if(ObjectUtil.isNotNull(resultResponse)){
                            if("true".equals(resultResponse.getString("IsSuccess"))){
                                return ResponseData.success();
                            } else {
                                String errors = resultResponse.getString("Errors");
                                return ResponseData.error("调用k3物流费付款申请单删除接口失败，失败详情：" + errors);
                            }
                        }
                    }
                }
            }else{
                //失败返回信息
                return ResponseData.error("调用k3物流费付款申请单删除鉴权接口失败!");
            }
        }catch(Exception e){
            log.error("调用k3物流费付款申请单删除接口异常，异常信息[{}]",e.getMessage());
            return ResponseData.error("调用k3物流费付款申请单删除接口异常");
        }
        return ResponseData.error("调用k3物流费付款申请单删除接口失败！");
    }

    public IdentifyInfo getConfigInfo() {

        IdentifyInfo identifyInfoAdb=new IdentifyInfo(){};
        //应用id
        identifyInfoAdb.setAppId(k3Appid);

        identifyInfoAdb.setAppSecret(k3Appsec);
        //账套id
        identifyInfoAdb.setdCID(k3Acctid);

        identifyInfoAdb.setUserName(k3Username);

        identifyInfoAdb.setServerUrl(k3Serverurl);
        return identifyInfoAdb;
    }

}
