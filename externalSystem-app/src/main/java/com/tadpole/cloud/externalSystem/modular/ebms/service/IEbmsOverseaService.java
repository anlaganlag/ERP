package com.tadpole.cloud.externalSystem.modular.ebms.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsOverseasOutWarehouseParam;

/**
 * @author: ty
 * @description: 海外仓调用EBMS接口
 * @date: 2023/4/3
 */
public interface IEbmsOverseaService {

    /**
     * 海外仓箱子信息
     * @return
     */
    ResponseData getAllBoxInfo();

    /**
     * 创建出货清单
     * @param outParam
     * @return
     */
    ResponseData createShipmentList(EbmsOverseasOutWarehouseParam outParam);
}
