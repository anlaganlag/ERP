package com.tadpole.cloud.platformSettlement.modular.inventory.provider;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.RemovalShipmentDetailApi;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemovalShipmentDetailService;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.OverseasInWarehouseFBAResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 移除货件详情报告服务提供者
 * @date: 2023/4/13
 */
@RestController
public class RemovalShipmentDetailProvider implements RemovalShipmentDetailApi {

    @Autowired
    private IRemovalShipmentDetailService removalShipmentDetailService;

    public ResponseData refreshSite(){
        return removalShipmentDetailService.refreshSite();
    }

    public List<OverseasInWarehouseFBAResult> generateInWarehouseByFBA(){
        return removalShipmentDetailService.generateInWarehouseByFBA();
    }

    public void updateGenerateHwc(List<BigDecimal> params){
        removalShipmentDetailService.updateGenerateHwc(params);
    }
}
