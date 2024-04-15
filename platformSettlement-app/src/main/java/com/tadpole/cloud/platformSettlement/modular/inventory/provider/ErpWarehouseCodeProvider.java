package com.tadpole.cloud.platformSettlement.modular.inventory.provider;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.ErpWarehouseCodeApi;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: ty
 * @description: ERP仓库组织编码服务提供者
 * @date: 2023/4/14
 */
@RestController
public class ErpWarehouseCodeProvider implements ErpWarehouseCodeApi {

    @Autowired
    private IErpWarehouseCodeService erpWarehouseCodeService;

    @Override
    public String getOrganizationCode(String orgName) {
        return erpWarehouseCodeService.getOrganizationCode(orgName);
    }

    @Override
    public ResponseData getOrganizationCodeByWarehouse(String warehouseName) {
        return erpWarehouseCodeService.getOrganizationCodeByWarehouse(warehouseName);
    }

    @Override
    public Map<String, String> getOrgMap() {
        return erpWarehouseCodeService.getOrgMap();
    }
}
