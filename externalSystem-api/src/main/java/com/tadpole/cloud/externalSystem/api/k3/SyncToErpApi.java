package com.tadpole.cloud.externalSystem.api.k3;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author: ty
 * @description: 同步K3API
 * @date: 2023/4/19
 */
@RequestMapping("/syncToErpApi")
public interface SyncToErpApi {

    /**
     * 采购申请单同步
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/syncPurschase", method = RequestMethod.POST)
    String syncPurschase(@RequestBody JSONArray jarr);

    /**
     * 采购申请单同步 返回JSONObject
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/syncPurschaseObj", method = RequestMethod.POST)
    JSONObject syncPurschaseObj(@RequestBody JSONArray jarr);

    /**
     * 销售出库
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/saleOutStock", method = RequestMethod.POST)
    JSONObject saleOutStock(@RequestBody JSONArray jarr);

    /**
     * 销售退货
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/saleReturnStock", method = RequestMethod.POST)
    JSONObject saleReturnStock(@RequestBody JSONArray jarr);

    /**
     * 其它出库
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/outStock", method = RequestMethod.POST)
    JSONObject outStock(@RequestBody JSONArray jarr);

    /**
     * 其它入库
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/inStock", method = RequestMethod.POST)
    JSONObject inStock(@RequestBody JSONArray jarr);

    /**
     * 期末盘点
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/endingInventory", method = RequestMethod.POST)
    JSONObject endingInventory(@RequestBody JSONArray jarr);

    /**
     *
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/voucherString", method = RequestMethod.POST)
    String voucherString(@RequestBody JSONArray jarr);

    /**
     * 结算单凭证
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/voucher", method = RequestMethod.POST)
    JSONObject voucher(@RequestBody JSONArray jarr);


    /**
     * 创建调拨单
     * @param mapParm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createTransferMap", method = RequestMethod.POST)
    String createTransferMap(@RequestBody Map<String,Object> mapParm) throws Exception;

    @RequestMapping(value = "/createTransferMapJson", method = RequestMethod.POST)
    cn.hutool.json.JSONObject createTransferMapJson(@RequestBody Map<String, Object> mapParm) throws Exception;

    /**
     * 组织机构创建
     * @param jarr
     * @return
     */
    @RequestMapping(value = "/createOrg", method = RequestMethod.POST)
    JSONObject createOrg(@RequestBody JSONArray jarr);
}
