package com.tadpole.cloud.supplyChain.modular.logistics.provider;

import com.tadpole.cloud.supplyChain.api.logistics.OverseasWarehouseManageApi;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManage;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ty
 * @description: 海外仓库存列表服务提供者
 * @date: 2023/4/17
 */
@RestController
public class OverseasWarehouseManageProvider implements OverseasWarehouseManageApi {

    @Autowired
    private IOverseasWarehouseManageService overseasWarehouseManageService;

    /**
     * Monthly Inventory History生成期末库存列表获取海外仓数据
     */
    public List<OverseasWarehouseManage> getOverseasWarehouseManageList(){
        return overseasWarehouseManageService.getOverseasWarehouseManageList();
    }
}
