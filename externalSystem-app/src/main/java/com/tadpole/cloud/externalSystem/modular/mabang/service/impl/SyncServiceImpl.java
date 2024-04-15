package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSON;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.tadpole.cloud.externalSystem.config.K3CloudWebapiConfig;
import com.tadpole.cloud.externalSystem.modular.mabang.constants.MabangConstant;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferItem;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferMabangSummary;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3TransferItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3PurchaseOrderInStockParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3AvailableResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SyncServiceImpl implements ISyncService {


    @Autowired
    IK3TransferItemService k3TransferItemService;

    @Autowired
    ISaleOutOrderK3Service saleOutOrderK3Service;

    @Autowired
    IMabangAddPurchaseOrderService mabangAddPurchaseOrderService;


    @Autowired
    IK3TransferMabangSummaryService summaryService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private K3TransferItemMapper k3TransferItemMapper;

    @Resource
    IMaterialPriceInfoService materialPriceInfoService;

    @Autowired
    K3CloudWebapiConfig k3CloudWebapiConfig;





    @DataSource(name = "external")
    @Override
//    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncToManbang() {

        // K3同步到MCMS 时有可能冲突 需要加锁来排除冲突


        Object k3SyncWork = redisTemplate.opsForValue().get(MabangConstant.K3_SYNC_MCMS_TRANSFER_WORK);

        if (ObjectUtil.isNotNull(k3SyncWork)) {
            log.info("K3调拨单正在同步到MCMS系统中，本次同步调拨单到马帮ERP系统取消,同步触发时间:{}",new Date());
            return ResponseData.success("K3调拨单正在同步到MCMS系统中，本次同步调拨单到马帮ERP系统取消");
        }


        // 同步K3调拨单到马帮ERP系统,加上redis锁
        redisTemplate.boundValueOps(MabangConstant.MCMS_SYNC_MABANG_TRANSFER_WORK)
                .set(MabangConstant.MCMS_SYNC_MABANG_TRANSFER_WORK, Duration.ofMinutes(3));


        //查出所有待同步的调拨单项
        List<K3TransferItem> transferItemList = k3TransferItemService.list();
        Map<String, String> mabangWarehouse = k3TransferItemService.k3MabangWarehouse();

        Map<String, List<K3TransferItem>> billNoGroupItemMap = transferItemList.stream().collect(Collectors.groupingBy(K3TransferItem::getBillNo));

        //查出k3系统马帮专属仓库信息
        // 1.1 直接调拨单 按照调拨方向（小平台入库，小平台出库）分组调拨单明细
        for (Map.Entry<String, List<K3TransferItem>> entry : billNoGroupItemMap.entrySet()) {

            try {
                K3TransferMabangSummary summary = summaryService.doSync(mabangWarehouse, entry.getValue());
            } catch (Exception e) {
                log.error("同步K3直接调拨单{}出现异常:{}", entry.getKey(),JSONUtil.toJsonStr(e));
            }

        }

        // 同步K3调拨单到马帮ERP系统,释放redis锁
        redisTemplate.delete(MabangConstant.MCMS_SYNC_MABANG_TRANSFER_WORK);
        return ResponseData.success();
    }


    @DataSource(name = "external")
    @Override
    public ResponseData anitAudit(String billNo) {

        ResponseData responseData=  summaryService.anitAudit(billNo);
        return responseData;
    }


    /**
     * k3指定仓库所有物料可用数量，销售出库到马帮erp
     * @param warehouseList 待同步的仓库名称集合
     * @return
     */
    @DataSource(name = "external")
    @Override
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData syncAllMatAvailableQty(List<String> warehouseList) {

        Object k3SyncWork = redisTemplate.opsForValue().get(MabangConstant.K3_SYNC_MCMS_WAREHOUSE_AVAILABLE_QYT);

        if (ObjectUtil.isNotNull(k3SyncWork)) {
            log.info("k3指定仓库所有物料可用数量，销售出库到马帮erp的定时任务作业正在执行，本次请求取消,同步触发时间:{}",new Date());
            return ResponseData.success("k3指定仓库所有物料可用数量，销售出库到马帮erp的定时任务作业正在执行，本次请求取消！");
        }

        // 同步K3调拨单到马帮ERP系统,加上redis锁
        redisTemplate.boundValueOps(MabangConstant.K3_SYNC_MCMS_WAREHOUSE_AVAILABLE_QYT)
                .set(MabangConstant.K3_SYNC_MCMS_WAREHOUSE_AVAILABLE_QYT, Duration.ofMinutes(10L));

        //1：查出k3系统指定仓库下的所有物料可以用数量>0的

       List<K3AvailableResult> availableResultList= saleOutOrderK3Service.queryK3AvailableQty(warehouseList);

        if (ObjectUtil.isEmpty(availableResultList)) {
            log.info("仓库【{}】未找到物料可用数量>0的物料，查询时间:{}",warehouseList,new Date());
//            return ResponseData.success();
        }else{
            Map<String, List<K3AvailableResult>> stockNameAvailableQtyMap = availableResultList.stream().collect(Collectors.groupingBy(K3AvailableResult::getStockName));
            for (Map.Entry<String, List<K3AvailableResult>> entry : stockNameAvailableQtyMap.entrySet()) {
                List<K3AvailableResult> availableList = entry.getValue();
                //2: 生成k3的销售出库单，调用k3销售出库全部扣减掉，扣减成功后保存在MCMS
                saleOutOrderK3Service.createK3SaleOutOrder(availableList);
            }
        }

        //3：统一异常查询处理（调用k3时异常，需要查询后确认状态了再更新）
        saleOutOrderK3Service.saleOutOrderExceptionHandle(null);

        //4: 根据k3的销售出库单，生成马帮的ERP采购新增单，保存到MCMS +调用马帮的 新增采购入库单接口
        mabangAddPurchaseOrderService.createMabangPurchaseOrder();

        redisTemplate.delete(MabangConstant.K3_SYNC_MCMS_WAREHOUSE_AVAILABLE_QYT);
        return ResponseData.success("执行完毕!");
    }


    @DataSource(name = "k3cloud")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<K3AvailableResult> queryK3AvailableQty(List<String> warehouseList) {
        List<K3AvailableResult> resultList = k3TransferItemMapper.queryK3AvailableQty(warehouseList);
        return resultList;
    }

    @DataSource(name = "external")
    @Override
    public ResponseData updateMatStockPrice(Integer fallbackMonths) {
        LocalDate beginDate = LocalDate.now().minusMonths(fallbackMonths);
        for (Integer i = 0; i < fallbackMonths; i++) {
            LocalDate tempDate = beginDate.plusMonths(i);
            ResponseData responseData= materialPriceInfoService.updateMatStockPrice(tempDate);
        }

        return null;
    }

    @Override
    public ResponseData purchaseOrderInStock(K3PurchaseOrderInStockParam param) {
        return saveInStockOrder(JSON.toJSONString(param));
    }

    private ResponseData analysisResult(String result) {
        //                {"Result":{"ResponseStatus":{"IsSuccess":true,"Errors":[],"SuccessEntitys":[{"Id":140670,"Number":"MBCGRK-2023051500004","DIndex":0}],"SuccessMessages":[],"MsgCode":0},"Id":140670,"Number":"MBCGRK-2023051500004","NeedReturnData":[{}]}}
        if (ObjectUtil.isEmpty(result)) {
            return ResponseData.error("返回结果为空");
        }
        JSONObject k3ResultJsonObject = JSONUtil.parseObj(result);
        JSONObject resultObject = k3ResultJsonObject.getJSONObject("Result");
        if (ObjectUtil.isNotNull(resultObject)) {

            JSONObject responseStatus = resultObject.getJSONObject("ResponseStatus");
            if (ObjectUtil.isNotNull(responseStatus)) {
                Boolean isSuccess = responseStatus.getBool("IsSuccess");

                if (ObjectUtil.isNotNull(isSuccess) && Boolean.TRUE.equals(isSuccess)) {
                    JSONArray successEntityArray = responseStatus.getJSONArray("SuccessEntitys");
                    if (ObjectUtil.isNotNull(successEntityArray) && successEntityArray.size()>0) {
                        JSONObject successEntity = successEntityArray.getJSONObject(0);
                        Integer id = successEntity.getInt("Id");
                        String number = successEntity.getStr("Number");
                        log.info("K3采购入库单调用k3-webapi标准接口成功返回Id【{}】,返回Number【{}】，调用时间【{}】",id,number,new Date());
                       return ResponseData.success(id);
                    }
                }
            }
        }
        return ResponseData.error(result);
    }

    @Override
    public ResponseData purchaseOrderInStock2(String jsonData) {
        return saveInStockOrder(jsonData);
    }


    /**
     * 统一请求K3获取指定期限的物料库存价格
     * @param jsonData
     * @return
     */
    @Override
    public ResponseData saveInStockOrder(String jsonData) {

        try {
            K3CloudApi api = new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info("使用WEB-API调用K3采购入库单接口请求参数【{}】", jsonData);
                String result = api.save("STK_InStock", jsonData);
                log.info("使用WEB-API调用K3采购入库单接口-返回结果[{}]", result);
                ResponseData responseData = this.analysisResult(result);
                return responseData;
            }
            return ResponseData.error("初始化k3采购入库单接口失败");
        } catch (Exception e) {
            log.info("使用WEB-API调用K3采购入库单接口-异常[{}]", e.getMessage());
            return ResponseData.error("调用k3采购入库单异常"+e.getMessage());
        }
    }


    @Override
    public ResponseData inStockCommit(String number, Integer fid) {
        return inStockCommitRequstK3(number,fid);
    }

    @Override
    public Date queryInStockVerifyDate(String billNo) {
        String filterString = "[{\"Left\":\"(\",\"FieldName\":\"FBILLNO\",\"Compare\":\"=\",\"Value\":\"" + billNo + "\",\"Right\":\")\",\"Logic\":\"\"}]";
        String jsonData = "{\"FormId\":\"STK_InStock\",\"FieldKeys\":\"FAPPROVEDATE\",\"FilterString\":"+filterString+",\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":2000,\"SubSystemId\":\"\"}";

        try {
            K3CloudApi api = new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info("使用WEB-API调用K3采购入库单-查询-接口请求参数【{}】", jsonData);
                List<List<Object>> result = api.executeBillQuery(jsonData);
                log.info("使用WEB-API调用K3采购入库单-查询-接口-返回结果[{}]", result);

                if (ObjectUtil.isNotEmpty(result)
                        && ObjectUtil.isNotEmpty(result.get(0))
                        && ObjectUtil.isNotNull(String.valueOf(result.get(0).get(0)))) {
                    Date verifyDate = Date.from(LocalDateTime.parse(String.valueOf(result.get(0).get(0))).atZone(ZoneId.systemDefault()).toInstant());
                    return verifyDate;
                }
            }
            return null;
        } catch (Exception e) {
            log.info("使用WEB-API调用K3采购入库单-查询-接口-异常[{}]", e.getMessage());
            return null;
        }

    }


    /**
     * 统一请求K3获取指定期限的物料库存价格
     * @param number
     * @param fid
     * @return
     */
    private ResponseData inStockCommitRequstK3(String number,Integer fid) {

//        String jsonData = "{\"CreateOrgId\":0,\"Numbers\":["+number+"],\"Ids\":\"\",\"SelectedPostId\":0,\"NetworkCtrl\":\"\",\"IgnoreInterationFlag\":\"\"}";
        String jsonData = "{\"Numbers\":[\""+number+"\"],\"Ids\":\""+fid+"\"}";

        try {
            K3CloudApi api = new K3CloudApi(getConfigInfo());
            if (api.CheckAuthInfo().getResponseStatus().isIsSuccess()) {
                log.info("使用WEB-API调用K3采购入库单-提交-接口请求参数【{}】", jsonData);
                String result = api.submit("STK_InStock", jsonData);
                log.info("使用WEB-API调用K3采购入库单-提交-接口-返回结果[{}]", result);
                ResponseData responseData = this.analysisResult(result);
                return responseData;
            }
            return ResponseData.error("使用WEB-API调用K3采购入库单-提交-接口调用失败");
        } catch (Exception e) {
            log.info("使用WEB-API调用K3采购入库单-提交-接口-异常[{}]", e.getMessage());
            return ResponseData.error("使用WEB-API调用K3采购入库单-提交-接口异常"+e.getMessage());
        }
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
