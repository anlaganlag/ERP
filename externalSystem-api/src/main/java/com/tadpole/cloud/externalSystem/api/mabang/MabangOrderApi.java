package com.tadpole.cloud.externalSystem.api.mabang;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: ty
 * @description: 马帮订单信息API
 * @date: 2023/11/21
 */
@RequestMapping("/mabangOrderApi")
public interface MabangOrderApi {

    /**
     * 根据马帮订单号获取订单信息
     * @param orderId 马帮订单号
     * @return
     */
    @RequestMapping(value = "/getOrderListByOrderId", method = RequestMethod.GET)
    ResponseData getOrderListByOrderId(@RequestParam String orderId);
}
