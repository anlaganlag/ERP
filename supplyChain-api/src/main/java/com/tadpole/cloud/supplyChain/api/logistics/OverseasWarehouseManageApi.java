package com.tadpole.cloud.supplyChain.api.logistics;

import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManage;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author: ty
 * @description: 海外仓库存列表API
 * @date: 2023/4/17
 */
@RequestMapping("/overseasWarehouseManageApi")
public interface OverseasWarehouseManageApi {

    /**
     * Monthly Inventory History生成期末库存列表获取海外仓数据
     * @return
     */
    @RequestMapping(value = "/getOverseasWarehouseManageList", method = RequestMethod.GET)
    @ApiOperation(value = "Monthly Inventory History生成期末库存列表获取海外仓数据")
    List<OverseasWarehouseManage> getOverseasWarehouseManageList();
}
