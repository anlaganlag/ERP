package com.tadpole.cloud.externalSystem.api.ebms;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsOverseasOutWarehouseParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: ty
 * @description: 海外仓调用EBMS系统API
 * @date: 2023/4/3
 */
@RequestMapping("/ebmsOverseaApi")
public interface EbmsOverseaApi {

    /**
     * 海外仓箱子信息
     */
    @RequestMapping(value = "/getAllBoxInfoService", method = RequestMethod.POST)
    ResponseData getAllBoxInfo();

    /**
     * 创建出货清单
     * @param outParam
     * @return
     */
    @RequestMapping(value = "/createShipmentList", method = RequestMethod.POST)
    ResponseData createShipmentList(@RequestBody EbmsOverseasOutWarehouseParam outParam);
}
