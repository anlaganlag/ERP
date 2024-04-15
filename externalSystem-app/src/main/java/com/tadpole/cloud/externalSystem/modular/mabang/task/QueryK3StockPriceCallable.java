package com.tadpole.cloud.externalSystem.modular.mabang.task;

import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.tadpole.cloud.externalSystem.config.K3CloudWebapiConfig;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.concurrent.Callable;
@Slf4j
public class QueryK3StockPriceCallable implements Callable<String> {

   private LocalDate needUpdateDate;
   private int startRow;
   private int pageSize;


    private static final K3CloudWebapiConfig k3CloudWebapiConfig = SpringContext.getBean(K3CloudWebapiConfig.class);

    public QueryK3StockPriceCallable(LocalDate needUpdateDate, int startRow, int pageSize) {
        this.needUpdateDate = needUpdateDate;
        this.startRow = startRow;
        this.pageSize = pageSize;
    }
    @Override
    public String call() throws Exception {
        return this.requestK3(needUpdateDate,startRow,pageSize);
    }




    /**
     * 统一请求K3获取指定期限的物料库存价格
     *
     * @param needUpdateDate
     * @param startRow
     * @param pageSize
     * @return
     */
    private String requestK3(LocalDate needUpdateDate, int startRow, int pageSize) {

        String jsonData = getJsonParmStr(needUpdateDate, startRow, pageSize);
        try {
            K3CloudApi api = new K3CloudApi(getConfigInfo(),60*60*2,60*60*2,60*60*2);
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info("使用WEB-API查询K3库存成本价--多线程[{}]--请求参数-startRow【{}】，pageSize【{}】请求参数【{}】",Thread.currentThread().getName(),startRow,pageSize, jsonData);
                String result = api.getSysReportData("HS_NoDimInOutStockDetailRpt", jsonData);
                log.info("使用WEB-API查询K3库存成本价--多线程[{}]--返回结果[{}]", Thread.currentThread().getName(),result);
                return result;
            }
            return null;
        } catch (Exception e) {
            log.info("使用WEB-API查询K3库存成本价--异常-startRow【{}】，pageSize【{}】-异常[{}]",startRow,pageSize, e.getMessage());
        }
        return null;
    }

    public static String getJsonParmStr(LocalDate needUpdateDate, Integer startRow, Integer limit) {
        int year = needUpdateDate.getYear();
        int month = needUpdateDate.getMonthValue();
        String jsonStrParm = "{\"FieldKeys\": \"FPERIOD,FMATERIALID,FMATERIALNAME,FENDPrice,FBUSINESSTYPE,FMATERTYPENAME\",\"StartRow\": " + startRow + ",\"Limit\": " + limit + ",\"IsVerifyBaseDataField\": \"false\",\"Model\":{\"FACCTGSYSTEMID\": {\"FNumber\": \"KJHSTX01_SYS\"},\"FACCTGORGID\": {\"FNumber\": \"002\"},\"FACCTPOLICYID\": {\"FNumber\": \"KJZC01_SYS\"},\"FYear\": \"" + year + "\",\"FENDYEAR\": \"" + year + "\",\"FPeriod\": \"" + month + "\",\"FEndPeriod\": \"" + month + "\"}}";
        return jsonStrParm;
    }

    public IdentifyInfo getConfigInfo() {

        IdentifyInfo identifyInfoAdb = new IdentifyInfo() {
        };
        //应用id
        identifyInfoAdb.setAppId(k3CloudWebapiConfig.getAppid());

        identifyInfoAdb.setAppSecret(k3CloudWebapiConfig.getAppsec());
        //账套id
        identifyInfoAdb.setdCID(k3CloudWebapiConfig.getAcctid());

        identifyInfoAdb.setUserName(k3CloudWebapiConfig.getUsername());

        identifyInfoAdb.setServerUrl(k3CloudWebapiConfig.getServerurl());
        return identifyInfoAdb;
    }
}
