package com.tadpole.cloud.externalSystem.modular.ebms.provider;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.EbmsOverseaApi;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsOverseasOutWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.ebms.service.IEbmsOverseaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 海外仓调用EBMS服务提供者
 * @date: 2023/4/4
 */
@RestController
public class EbmsOverseaServiceProvider implements EbmsOverseaApi {

    @Autowired
    private IEbmsOverseaService ebmsOverseaService;

    @Override
    public ResponseData getAllBoxInfo() {
        return ebmsOverseaService.getAllBoxInfo();
    }

    @Override
    public ResponseData createShipmentList(EbmsOverseasOutWarehouseParam outParam) {
        return ebmsOverseaService.createShipmentList(outParam);
    }
}
