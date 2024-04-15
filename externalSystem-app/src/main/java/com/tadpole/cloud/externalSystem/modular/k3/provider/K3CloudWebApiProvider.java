package com.tadpole.cloud.externalSystem.modular.k3.provider;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.tadpole.cloud.externalSystem.api.k3.K3CloudWebApi;
import com.tadpole.cloud.externalSystem.api.k3.model.params.K3TransferApplyParam;
import com.tadpole.cloud.externalSystem.api.k3.model.params.K3TransferApplyParam1;
import com.tadpole.cloud.externalSystem.api.k3.model.params.ShipmentLabelApplyK3Param;
import com.tadpole.cloud.externalSystem.config.K3CloudWebapiConfig;
import com.tadpole.cloud.externalSystem.modular.k3.service.TranferApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class K3CloudWebApiProvider implements K3CloudWebApi {
    @Resource
    K3CloudWebapiConfig k3CloudWebapiConfig;

    @Resource
    TranferApplyService tranferApplyService;


    public static HashMap<String, String> transportationTypeMap = new HashMap<>();
    public static HashMap<String, String> unwTypeMap = new HashMap<>();
    static {
        transportationTypeMap.put("海运-快船", "B");
        transportationTypeMap.put("铁运", "G");
        transportationTypeMap.put("卡航", "H");
        transportationTypeMap.put("快递", "J");
        transportationTypeMap.put("空运", "K");
        transportationTypeMap.put("海运-普船", "L");


        unwTypeMap.put("亚马逊出货", "A");
        unwTypeMap.put("滞销销毁出货", "E");
        unwTypeMap.put("小袋出货", "B");
        unwTypeMap.put("B2B出货", "C");
        unwTypeMap.put("批量出货", "D");

    }


    @Override
    public boolean delTranferByBillNo(String billNo) {
        K3CloudApi api = new K3CloudApi(getConfigInfo());

        try {

            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {

                String jsonData = "{\"Numbers\":[\""+billNo+"\"]}";

                String result = api.unAudit("STK_TRANSFERAPPLY", jsonData);
                log.info("【发货项目】调用直接调拨单【反审核】接口核执行完成，调拨单[{}]，返回结果:[{}]", billNo, result);

                if (this.checkResult(result)) {
                    String result2 = api.delete("STK_TRANSFERAPPLY", jsonData);
                    log.info("【发货项目】调用直接调拨单【删除】接口核执行完成，调拨单[{}]，返回结果:[{}]", billNo, result2);
                    if (this.checkResult(result2)) {
                        log.error("【发货项目】删除调拨单成功，调拨单[{}]", billNo);
                        return true;
                    }
                }
            }

        } catch (Exception e) {
            log.error("【发货项目】删除调拨单异常，调拨单[{}]，异常信息:[{}]", billNo, JSON.toJSONString(e));
        }
        return Boolean.FALSE;
    }

    @Override
    public ResponseData tranferApplySave(  ShipmentLabelApplyK3Param item) {

        //webapi sku fnsku 传到 k3会随机获取，所以先获取FID
        String orgName="Amazon_"+item.getSysShopsName()+"_"+item.getSysSite();
        String bscId = tranferApplyService.getBscId(orgName, item.getSku());
        if (ObjectUtil.isEmpty(bscId)) {
            log.error("【发货标签申请】保存调拨申请单时未获取到FID，调拨申请单orgName[{}]，sku:[{}]", orgName, item.getSku());
            return ResponseData.error("orgName["+orgName+"]---sku["+item.getSku()+"]未获取到FID");
        }

        K3TransferApplyParam1 applyParam1 = new K3TransferApplyParam1();
        K3TransferApplyParam model = new K3TransferApplyParam();
        model.setFDate(DateUtil.format(new Date(), "yyyy-MM-dd") + " 00:00:00");
        model.setFapporgid(item.getOrgCode());//申请组织
        model.setBillNo(item.getBillNo());
        model.setUnwCombo(unwTypeMap.get(item.getUnwType()));
        List<K3TransferApplyParam.K3TransferApplyItemParam> entityList = new ArrayList<>();

        K3TransferApplyParam.K3TransferApplyItemParam entity = new K3TransferApplyParam.K3TransferApplyItemParam();
        entity.setFBscBase(bscId); //传FID
        entity.setFmaterialid(item.getMaterialCode());
        entity.setFRequireteam(item.getTeam());
        entity.setFRequireper(item.getApllyPersonNo());
        entity.setFQty(item.getPrintQty());
        entity.setFStockOrgInId(item.getOrgCode());
        entity.setFStockInId(item.getOrgCode());
        entity.setFOwnerInId(item.getOrgCode());
        entity.setRemark1(item.getRemark1());
        entity.setRemark2(item.getRemark2());
        entity.setRemark3(item.getRemark3());
        entity.setShippingMethod(transportationTypeMap.get(item.getTransportationType()));


        entityList.add(entity);

        model.setFEntity(entityList);
        applyParam1.setModel(model);

        JSONObject objectAll = new JSONObject();
        objectAll.put("Model", model);
        String jsonDataStr = JSON.toJSONString(objectAll);
        K3CloudApi api = new K3CloudApi(getConfigInfo());
        try {
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info("【发货标签申请】调用调拨申请单【保存】接口核执行请求参数，调拨单[{}]，请求参数:[{}]", jsonDataStr, jsonDataStr);
                String result = api.save("STK_TRANSFERAPPLY", jsonDataStr);
                log.info("【发货标签申请】调用调拨申请单【保存】接口核执行完成，调拨单[{}]，返回结果:[{}]", jsonDataStr, result);
                if (this.checkResult(result)) {
                    log.info("【发货标签申请】调用调拨申请单 保存成功，调拨单[{}]", jsonDataStr);
                    return ResponseData.success(result);
                }
                return ResponseData.error("【发货标签申请】同步失败:"+result);

            }
        } catch (Exception e) {
            log.error("【发货标签申请】保存调拨申请单异常，调拨申请单[{}]，异常信息:[{}]", jsonDataStr, JSON.toJSONString(e));
            return ResponseData.error("【发货标签申请】同步异常："+e.getMessage());
        }
        return ResponseData.error("【发货标签申请】同步失败");

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

    private boolean checkResult(String result) {
        JSONObject resultJson = JSON.parseObject(result);
        if (ObjectUtil.isNotNull(resultJson)) {
            JSONObject resultValue = JSON.parseObject(resultJson.getString("Result"));
            if (ObjectUtil.isNotNull(resultValue)) {
                JSONObject resultResponse = JSON.parseObject(resultValue.getString("ResponseStatus"));
                if (ObjectUtil.isNotNull(resultResponse)) {
                    //2-1.审核同步返回状态处理
                    if ("true".equals(resultResponse.getString("IsSuccess"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
