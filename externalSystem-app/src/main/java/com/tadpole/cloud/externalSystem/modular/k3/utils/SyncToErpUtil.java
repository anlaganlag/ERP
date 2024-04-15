package com.tadpole.cloud.externalSystem.modular.k3.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.externalSystem.config.K3CloudConfig;
import com.tadpole.cloud.externalSystem.config.K3CloudWebapiConfig;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: S20190109
 * @Date: 2020/7/26
 * @Version: 1.0
 **/
@Slf4j
@Component
public class SyncToErpUtil {

    private static final String K3CLOUD_TOKEN = "K3CLOUD_TOKEN";
    private static final String K3CLOUD_TOKEN_ADMIN = "K3CLOUD_TOKEN_ADMIN";

    private static final String K3CLOUD_TOKEN_TM = "K3CLOUD_TOKEN_TM";//条码

    private String token;

    private String LoginAddr = "ValidateSystem";   //身份认证地址

    private String PurchaseApply = "CreatePurchaseRequisition";   //采购申请单

    private String Voucher = "CreateVoucher";   //结算单凭证

    private String SaleOutStock = "CreateSaleOutStock";   //销售出库

    private String SaleReturnStock = "CreateSalReturnStock";   //销售退货

    private String OutStock = "CreateOtherOutStock";   //其它出库单

    private String InStock = "CreateOtherInStock";   //其它入库单

    private String EndingInventory = "CreateInventoryCheck";   //库存盘点
    private String createOrg = "CreateOrg";   //组织机构创建

    @Autowired
    private K3CloudConfig k3CloudConfig;

    @Autowired
    private K3CloudWebapiConfig k3CloudWebapiConfig;

    @Autowired
    private RedisTemplate redisTemplate;


    //采购申请单同步
    public String syncPurschase(JSONArray jarr){

        return this.syncToErp(this.PurchaseApply,jarr);
    }

    //采购申请单同步 返回JSONObject
    public JSONObject syncPurschaseObj(JSONArray jarr){

        return this.syncToErpObj(this.PurchaseApply,jarr);
    }


    //销售出库
    public JSONObject saleOutStock(JSONArray jarr){

        return this.syncToErpObj(this.SaleOutStock,jarr);
    }

    //销售退货
    public JSONObject saleReturnStock(JSONArray jarr){

        return this.syncToErpObj(this.SaleReturnStock,jarr);
    }

    //其它出库
    public JSONObject outStock(JSONArray jarr){

        return this.syncToErpObj(this.OutStock,jarr);
    }

    //其它入库
    public JSONObject inStock(JSONArray jarr){

        return this.syncToErpObj(this.InStock,jarr);
    }

    //期末盘点
    public JSONObject endingInventory(JSONArray jarr){

        return this.syncToErpObj(this.EndingInventory,jarr);
    }


    public String voucherString(JSONArray jarr){

        return this.syncToErp(this.Voucher,jarr);
    }

    //结算单凭证
    public JSONObject voucher(JSONArray jarr){

        return this.syncToErpObj(this.Voucher,jarr);
    }

    //同步erp  返回string
    public String syncToErp(String url, JSONArray jarr){

        JSONObject obj =  this.sync(url,jarr);

        return obj.getString("Code");
    }

    //同步erp  返回 JSONObject
    public JSONObject syncToErpObj(String url, JSONArray jarr){

        JSONObject obj =  this.sync(url,jarr);

        return obj;
    }


    public JSONObject adminSyncToErpObj(String url, JSONArray jarr){

        JSONObject obj =  this.adminSync(url,jarr);

        return obj;
    }

    //同步erp  返回 JSONObject
    public JSONObject sync(String url, JSONArray jarr){

        if(redisTemplate.boundValueOps(K3CLOUD_TOKEN).size()>0){

            token = redisTemplate.boundValueOps(K3CLOUD_TOKEN).get().toString();

        }else{
            token = this.login();
        }

        JSONObject param =new JSONObject();


        param.put("AccessToken",token);
        param.put("Data",jarr);
        long start = System.currentTimeMillis();
        log.info("-----------调用K3接口入参"+ String.valueOf(param));

        String result= HttpRequest.post(k3CloudConfig.getIpaddress()+url).header("Content-Type", "application/json").body(String.valueOf(param))
                .execute().body();


        log.info("-----11111111111------调用K3接口响应参数[{}]，耗时[{}]", result, (System.currentTimeMillis() - start) + "ms");


        JSONObject obj = JSON.parseObject(result);

        //如果身份错误再请求一次
        if (obj.getString("Code") !=null  && obj.getString("Code").equals("Invalid")){

            token = this.login();
            param =new JSONObject();
            param.put("AccessToken",token);
            param.put("Data",jarr);
            log.info("-----------（身份错误再请求一次）调用K3接口入参"+ String.valueOf(param));
            result= HttpRequest.post(k3CloudConfig.getIpaddress()+url).header("Content-Type", "application/json").body(String.valueOf(param))
                    .execute().body();
            log.error("-----1111112222222-----"+ result);
            obj = JSON.parseObject(result);
            log.error("-----AccessToken------"+ token);
            log.error("-----222222222------"+ result);

        }

        log.error("-----33333333333------"+ result);

        if(obj.getString("Code") ==null  || !obj.getString("Code").equals("0")){
            log.error("-----44444444444444------"+ result);
        }

        return obj;
    }

    public JSONObject adminSync(String url, JSONArray jarr){

        if(redisTemplate.boundValueOps(K3CLOUD_TOKEN_ADMIN).size()>0){

            token = redisTemplate.boundValueOps(K3CLOUD_TOKEN_ADMIN).get().toString();

        }else{
            token = this.adminLogin();
        }

        JSONObject param =new JSONObject();


        param.put("AccessToken",token);
        param.put("Data",jarr);
        long start = System.currentTimeMillis();
        log.info("-----------调用K3接口入参"+ String.valueOf(param));

        String result= HttpRequest.post(k3CloudConfig.getIpaddress()+url).header("Content-Type", "application/json").body(String.valueOf(param))
                .execute().body();


        log.info("-----11111111111------调用K3接口响应参数[{}]，耗时[{}]", result, (System.currentTimeMillis() - start) + "ms");


        JSONObject obj = JSON.parseObject(result);

        //如果身份错误再请求一次
        if (obj.getString("Code") !=null  && obj.getString("Code").equals("Invalid")){

            token = this.adminLogin();
            param =new JSONObject();
            param.put("AccessToken",token);
            param.put("Data",jarr);
            result= HttpRequest.post(k3CloudConfig.getIpaddress()+url).header("Content-Type", "application/json").body(String.valueOf(param))
                    .execute().body();
            log.error("-----1111112222222-----"+ result);
            obj = JSON.parseObject(result);
            log.error("-----AccessToken------"+ token);
            log.error("-----222222222------"+ result);

        }

        log.error("-----33333333333------"+ result);

        if(obj.getString("Code") ==null  || !obj.getString("Code").equals("0")){
            log.error("-----44444444444444------"+ result);
        }

        return obj;
    }



    //接口身份认证
    public String login(){

        JSONObject param =new JSONObject();
        param.put("UserName",k3CloudConfig.getUsername());
        param.put("PassWord",k3CloudConfig.getPassword());
        String result= HttpRequest.post(k3CloudConfig.getIpaddress()+this.LoginAddr).header("Content-Type", "application/json").body(String.valueOf(param))
                .execute().body();
        JSONObject obj = JSON.parseObject(result);

        log.error("-----login------"+ result);

        if(obj.getString("Code").equals("0")){

            redisTemplate.boundValueOps(K3CLOUD_TOKEN).set(obj.getString("AccessToken"));

            token = obj.getString("AccessToken");
        }


        return obj.getString("AccessToken");
    }

    public String adminLogin(){

        JSONObject param =new JSONObject();
        param.put("UserName",k3CloudConfig.getAdminUsername());
        param.put("PassWord",k3CloudConfig.getAdminPassword());
        String result= HttpRequest.post(k3CloudConfig.getIpaddress()+this.LoginAddr).header("Content-Type", "application/json").body(String.valueOf(param))
                .execute().body();
        JSONObject obj = JSON.parseObject(result);

        log.error("-----login------"+ result);

        if(obj.getString("Code").equals("0")){

            redisTemplate.boundValueOps(K3CLOUD_TOKEN_ADMIN).set(obj.getString("AccessToken"));

            token = obj.getString("AccessToken");
        }


        return obj.getString("AccessToken");
    }


    //条码接口身份认证
    public String tmLogin() throws DocumentException {

        String url = k3CloudWebapiConfig.getServerurl() + "Services/EBMS.asmx/Login";

        Map<String,Object> paramMap = new HashMap<>();

        JSONObject param =new JSONObject();

        param.put("Username",k3CloudConfig.getUsername());
        param.put("Password",k3CloudConfig.getPassword());
        param.put( "AccountID",k3CloudWebapiConfig.getAcctid());

        paramMap.put("json",String.valueOf(param));

        HttpResponse httpResponse   = HttpRequest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded").form(paramMap)
                .execute();
        String result = httpResponse.body();
        Document document = DocumentHelper.parseText(result);
        JSONObject resultObj = JSONObject.parseObject(document.getRootElement().getText());
        log.error("-----222222222------"+ resultObj.getString("_code"));

        if(resultObj.getString("_code").equals("1")){

            redisTemplate.boundValueOps(K3CLOUD_TOKEN_TM).set(resultObj.getString("_ctx"));

            token = resultObj.getString("_ctx");
        }

        return resultObj.getString("_ctx");
    }


    //同步erp
    public String diaobo() throws DocumentException {

        if(redisTemplate.boundValueOps(K3CLOUD_TOKEN_TM).size()>0){

            token = redisTemplate.boundValueOps(K3CLOUD_TOKEN_TM).get().toString();

        }else{
            token = this.tmLogin();
        }

        JSONArray aa = new JSONArray();

        JSONObject param1 =new JSONObject();

        param1.put("SHIPPINGMETHOD","海运");
        param1.put("SKU","NVW-BotlCarrBag-220506F-bluSpman-fA");
        param1.put("FBASEQTY",5);
        param1.put("REQUIRETEAM","Team6");
        param1.put("REQUIREPER","S20180229");
        param1.put("REMARK1","明细一备注1");
        param1.put("REMARK2","明细一备注1");
        param1.put("REMARK3","明细一备注1");

        aa.add(param1);

        JSONObject param =new JSONObject();

        param.put("Context",token);
        param.put("F_BILLNO","MC-FH-202302080004");//BillNo
        param.put("DATE","2023-02-13");
        param.put("APPORGNUMBER","301");
        param.put("DELIVERYPOINTNUMBER","FHD06");
        param.put("BUSINESSTYPE","亚马逊出货");
//        param.put("BUSINESSTYPE","C");
        param.put("SALESNUMBER","");
        param.put("FDESCRIPTION","FBA号备注");
//        param.put("FSTOCKINNUMBER","Amazon_CC_US_仓库");
        param.put("FSTOCKINNUMBER","301");
        param.put("STKTRANSFERAPPENTRYS",aa);

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("json",String.valueOf(param));

        log.error("-----11111111111------"+ JSONUtil.toJsonStr(param));

        HttpResponse httpResponse   = HttpRequest.post("http://192.168.0.93/K3Cloud/Services/EBMS.asmx/AddEStktranSferApp")
                .header("Content-Type", "application/x-www-form-urlencoded").form(paramMap)
//                .header("Content-Type", "text/plain").form(paramMap)
                .execute();
        String result = httpResponse.body();


        log.error("-----11111111111------"+ result);



        return result;
    }


    //同步erp
    public String diaoboTest() throws DocumentException {

        if(redisTemplate.boundValueOps(K3CLOUD_TOKEN_TM).size()>0){

            token = redisTemplate.boundValueOps(K3CLOUD_TOKEN_TM).get().toString();

        }else{
            token = this.tmLogin();
        }

        JSONArray aa = new JSONArray();

        JSONObject param1 =new JSONObject();

        param1.put("SHIPPINGMETHOD","海运");
        param1.put("SKU","NVW-BotlCarrBag-220506F-bluSpman-fA");
        param1.put("FBASEQTY",5);
        param1.put("REQUIRETEAM","Team6");
        param1.put("REQUIREPER","S20180229");
        param1.put("REMARK1","明细一备注1");
        param1.put("REMARK2","明细一备注1");
        param1.put("REMARK3","明细一备注1");

        aa.add(param1);

        JSONObject param =new JSONObject();

        param.put("Context",token);
        param.put("F_BILLNO","MC-FH-202302080004");//BillNo
        param.put("DATE","2023-02-13");
        param.put("APPORGNUMBER","301");
        param.put("DELIVERYPOINTNUMBER","FHD06");
        param.put("BUSINESSTYPE","亚马逊出货");
//        param.put("BUSINESSTYPE","C");
        param.put("SALESNUMBER","");
        param.put("FDESCRIPTION","FBA号备注");
//        param.put("FSTOCKINNUMBER","Amazon_CC_US_仓库");
        param.put("FSTOCKINNUMBER","301");
        param.put("STKTRANSFERAPPENTRYS",aa);

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("json",String.valueOf(param));

        log.error("-----11111111111------"+ JSONUtil.toJsonStr(param));

        HttpResponse httpResponse   = HttpRequest.post("http://192.168.0.93/K3Cloud/Services/EBMS.asmx/AddEStktranSferApp")
                .header("Content-Type", "application/x-www-form-urlencoded").form(paramMap)
//                .header("Content-Type", "text/plain").form(paramMap)
                .execute();
        String result = httpResponse.body();


        log.error("-----11111111111------"+ result);



        return result;
    }


    public String createTransferMap(Map<String, Object> mapParm) throws Exception {

        String token;

        if(redisTemplate.boundValueOps(K3CLOUD_TOKEN_TM).size()>0){

            token = redisTemplate.boundValueOps(K3CLOUD_TOKEN_TM).get().toString();

        }else{
            token = this.tmLogin();
        }

        JSONArray aa = new JSONArray();

        //请求明细
        JSONObject param1 =new JSONObject();
        param1.put("FSTOCKINNUMBER",mapParm.get("receiveWarehouseCode"));//输入仓编号  注意：海外仓name和code一致
        param1.put("SHIPPINGMETHOD",mapParm.get("transportationType"));//运输方式
        param1.put("SKU",mapParm.get("sku"));
        param1.put("FBASEQTY",mapParm.get("sendQty"));
        param1.put("REQUIRETEAM",mapParm.get("team"));
        param1.put("REQUIREPER",mapParm.get("apllyPersonNo"));
        param1.put("REMARK1",mapParm.get("remark1"));
        param1.put("REMARK2",mapParm.get("remark2"));
        param1.put("REMARK3",mapParm.get("remark3"));

        aa.add(param1);

        //请求头
        JSONObject param =new JSONObject();
        param.put("Context",token);
        param.put("BILLNO",mapParm.get("billNo"));
        param.put("DATE", DateUtil.format(new Date(),"yyyy-MM-dd"));
        param.put("APPORGNUMBER",mapParm.get("orgCode"));//组织编码 Amazon_账号_站点_仓库
        param.put("DELIVERYPOINTNUMBER",mapParm.get("deliverypointNo"));
        param.put("BUSINESSTYPE",mapParm.get("unwType"));//亚马逊出货
        param.put("SALESNUMBER","");
        param.put("FDESCRIPTION",mapParm.get("fbaNo"));
        param.put("STKTRANSFERAPPENTRYS",aa);

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("json",String.valueOf(param));
        String requestParam = JSONUtil.toJsonStr(param);
        log.error("-----发货创建调拨单请求参数------"+ requestParam);

        mapParm.put("syncRequestMsg", requestParam);

        //url=http://192.168.0.93/K3Cloud/Services/EBMS.asmx/AddEStktranSferApp
        String url = k3CloudWebapiConfig.getServerurl() + "Services/EBMS.asmx/AddEStktranSferApp";

        HttpResponse httpResponse   = HttpRequest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded").form(paramMap)
                .execute();
        String result = httpResponse.body();
        log.error("-----发货创建调拨单请求--结果------"+ result);

        return result;
    }


    public cn.hutool.json.JSONObject createTransferMapJson (Map<String, Object> mapParm) throws Exception {

        String token;

        if(redisTemplate.boundValueOps(K3CLOUD_TOKEN_TM).size()>0){

            token = redisTemplate.boundValueOps(K3CLOUD_TOKEN_TM).get().toString();

        }else{
            token = this.tmLogin();
        }

        JSONArray aa = new JSONArray();

        //请求明细
        JSONObject param1 =new JSONObject();
        param1.put("FSTOCKINNUMBER",mapParm.get("receiveWarehouseCode"));//输入仓编号  注意：海外仓name和code一致
        param1.put("SHIPPINGMETHOD",mapParm.get("transportationType"));//运输方式
        param1.put("SKU",mapParm.get("sku"));
        param1.put("FBASEQTY",mapParm.get("sendQty"));
        param1.put("REQUIRETEAM",mapParm.get("team"));
        param1.put("REQUIREPER",mapParm.get("apllyPersonNo"));
        param1.put("REMARK1",mapParm.get("remark1"));
        param1.put("REMARK2",mapParm.get("remark2"));
        param1.put("REMARK3",mapParm.get("remark3"));

        aa.add(param1);

        //请求头
        JSONObject param =new JSONObject();
        param.put("Context",token);
        param.put("BILLNO",mapParm.get("billNo"));
        param.put("DATE", DateUtil.format(new Date(),"yyyy-MM-dd"));
        param.put("APPORGNUMBER",mapParm.get("orgCode"));//组织编码 Amazon_账号_站点_仓库
        param.put("DELIVERYPOINTNUMBER",mapParm.get("deliverypointNo"));
        param.put("BUSINESSTYPE",mapParm.get("unwType"));//亚马逊出货
        param.put("SALESNUMBER","");
        param.put("FDESCRIPTION",mapParm.get("fbaNo"));
        param.put("STKTRANSFERAPPENTRYS",aa);

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("json",String.valueOf(param));
        String requestParam = JSONUtil.toJsonStr(param);
        log.error("-----发货创建调拨单请求参数------"+ requestParam);

        mapParm.put("syncRequestMsg", requestParam);

        //url=http://192.168.0.93/K3Cloud/Services/EBMS.asmx/AddEStktranSferApp
        String url = k3CloudWebapiConfig.getServerurl() + "Services/EBMS.asmx/AddEStktranSferApp";

        HttpResponse httpResponse   = HttpRequest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded").form(paramMap)
                .execute();
        String result = httpResponse.body();
        log.error("-----发货创建调拨单请求--结果------"+ result);
        cn.hutool.json.JSONObject entries = JSONUtil.parseObj(result);
        return entries;
    }

    /**
     * 组织机构创建
     * @param jarr
     * @return
     */
    public JSONObject createOrg(JSONArray jarr){
        return this.adminSyncToErpObj(this.createOrg,jarr);
    }
}
