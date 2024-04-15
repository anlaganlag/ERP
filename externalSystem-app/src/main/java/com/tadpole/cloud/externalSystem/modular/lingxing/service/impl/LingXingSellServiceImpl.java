package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.ListingReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.OrderDetailReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.OrderReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.constants.LingXingUrlConstants;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingSellService;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingReqInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ty
 * @description: 领星销售Service接口类
 * @date: 2022/8/12
 */
@Service
public class LingXingSellServiceImpl implements LingXingSellService {

    @Autowired
    private LingXingReqInfoUtil lingXingReqInfoUtil;

    @Override
    public LingXingBaseRespData order(OrderReq req) throws Exception {
        return lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.MWS_ORDER);
    }

    @Override
    public LingXingBaseRespData orderDetail(OrderDetailReq req) throws Exception {
        return lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.MWS_ORDER_DETAIL);
    }

    @Override
    public LingXingBaseRespData listing(ListingReq req) throws Exception {
        return lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.MWS_LISTING);
    }
}
