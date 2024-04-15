package com.tadpole.cloud.externalSystem.modular.mabang.provider;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.mabang.MabangOrderApi;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 马帮订单信息服务提供者
 * @date: 2023/11/21
 */
@RestController
public class MabangOrderProvider implements MabangOrderApi {

    @Autowired
    private IMabangOrdersService mabangOrdersService;

    @Override
    public ResponseData getOrderListByOrderId(String orderId) {
        return mabangOrdersService.getOrderListByOrderId(orderId);
    }
}
