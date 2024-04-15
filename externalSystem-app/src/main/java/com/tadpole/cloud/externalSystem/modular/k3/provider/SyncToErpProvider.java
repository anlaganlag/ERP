package com.tadpole.cloud.externalSystem.modular.k3.provider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.externalSystem.api.k3.SyncToErpApi;
import com.tadpole.cloud.externalSystem.modular.k3.utils.SyncToErpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: ty
 * @description: 同步K3服务提供者
 * @date: 2023/4/19
 */
@RestController
public class SyncToErpProvider implements SyncToErpApi {

    @Autowired
    private SyncToErpUtil syncToErpUtil;

    @Override
    public String syncPurschase(JSONArray jarr) {
        return syncToErpUtil.syncPurschase(jarr);
    }

    @Override
    public JSONObject syncPurschaseObj(JSONArray jarr) {
        return syncToErpUtil.syncPurschaseObj(jarr);
    }

    @Override
    public JSONObject saleOutStock(JSONArray jarr) {
        return syncToErpUtil.saleOutStock(jarr);
    }

    @Override
    public JSONObject saleReturnStock(JSONArray jarr) {
        return syncToErpUtil.saleReturnStock(jarr);
    }

    @Override
    public JSONObject outStock(JSONArray jarr) {
        return syncToErpUtil.outStock(jarr);
    }

    @Override
    public JSONObject inStock(JSONArray jarr) {
        return syncToErpUtil.inStock(jarr);
    }

    @Override
    public JSONObject endingInventory(JSONArray jarr) {
        return syncToErpUtil.endingInventory(jarr);
    }

    @Override
    public String voucherString(JSONArray jarr) {
        return syncToErpUtil.voucherString(jarr);
    }

    @Override
    public JSONObject voucher(JSONArray jarr) {
        return syncToErpUtil.voucher(jarr);
    }

    @Override
    public String createTransferMap(Map<String, Object> mapParm) throws Exception {
        return syncToErpUtil.createTransferMap(mapParm);
    }

    @Override
    public cn.hutool.json.JSONObject createTransferMapJson(Map<String, Object> mapParm) throws Exception {
        return syncToErpUtil.createTransferMapJson(mapParm);
    }


    @Override
    public JSONObject createOrg(JSONArray jarr) {
        return syncToErpUtil.createOrg(jarr);
    }
}
