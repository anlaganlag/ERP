package com.tadpole.cloud.operationManagement.modular.stock.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProdPurchaseRequire;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Callable;

import com.tadpole.cloud.operationManagement.modular.stock.consumer.SyncToErpConsumer;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PurchasePushK3Task implements Callable<ProdPurchaseRequire> {

    private JSONArray jsonArray;
    private ProdPurchaseRequire purchase;
    private SyncToErpConsumer syncToErpConsumer;





    public PurchasePushK3Task(JSONArray jsonArray, ProdPurchaseRequire purchase,SyncToErpConsumer syncToErpConsumer) {
        this.jsonArray = jsonArray;
        this.purchase = purchase;
        this.syncToErpConsumer = syncToErpConsumer;
    }

    @Override
    public ProdPurchaseRequire call() {
        long startTime = System.currentTimeMillis();
        try {
            JSONObject resultJsonObject;
            log.info(DateUtil.now() + "同步K3采购订单开始:" + Thread.currentThread().getName());
            resultJsonObject = syncToErpConsumer.syncPurschaseObj(jsonArray);
            if (ObjectUtil.isNotNull(resultJsonObject)) {
                purchase.setSyncResultMsg(JSON.toJSONString(resultJsonObject));
                String code = resultJsonObject.getString("Code");
                if (ObjectUtil.isNotEmpty(code) && code.equals("0")) {
                    //同步成功修改状态为1
                    purchase.setSyncStatus(StockConstant.SYNC_SUCESS);
                } else {
                    purchase.setSyncStatus(StockConstant.SYNC_FAIL);
                }
            }
        } catch (Exception e) {
            log.error("同步K3采购订异常:{}", e.getMessage());
            purchase.setSyncStatus(StockConstant.SYNC_FAIL);
            purchase.setSyncResultMsg(JSON.toJSONString(e.getMessage()));
        }
        purchase.setSyncTime(new Date());
        log.info(StrUtil.format("执行[{}]时间:{} 线程:[{}] 同步K3采购订单[执行时长：{} 毫秒.]","1".equals(purchase.getSyncStatus()) ? "成功":"失败", DateUtil.now(), Thread.currentThread().getName(),  System.currentTimeMillis() - startTime));
        return purchase;


    }
}
