package com.tadpole.cloud.externalSystem.modular.lingxing.service;

import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.ListingReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.OrderDetailReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.OrderReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;

/**
 * @author: ty
 * @description: 领星销售Service接口类
 * @date: 2022/8/12
 */
public interface LingXingSellService {

    /**
     * 亚马逊订单列表
     * @return
     */
    LingXingBaseRespData order(OrderReq req) throws Exception;

    /**
     * 亚马逊订单详情
     * @return
     */
    LingXingBaseRespData orderDetail(OrderDetailReq req) throws Exception;

    /**
     * 查询亚马逊Listing
     * @return
     */
    LingXingBaseRespData listing(ListingReq req) throws Exception;
}
