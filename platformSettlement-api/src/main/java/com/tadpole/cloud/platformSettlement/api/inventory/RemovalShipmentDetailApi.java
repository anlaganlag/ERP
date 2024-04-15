package com.tadpole.cloud.platformSettlement.api.inventory;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.OverseasInWarehouseFBAResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 移除货件详情报告API
 * @date: 2023/4/13
 */
@RequestMapping("/removalShipmentDetailApi")
public interface RemovalShipmentDetailApi {

    /**
     * 刷新RemovalShipmentDetail站点
     * @return
     */
    @RequestMapping(value = "/refreshSite", method = RequestMethod.GET)
    @ApiOperation(value = "刷新RemovalShipmentDetail站点")
    ResponseData refreshSite();

    /**
     * 获取FBA退海外仓数据
     * @return
     */
    @RequestMapping(value = "/generateInWarehouseByFBA", method = RequestMethod.GET)
    @ApiOperation(value = "获取FBA退海外仓数据")
    List<OverseasInWarehouseFBAResult> generateInWarehouseByFBA();

    /**
     * 更新入库海外仓状态
     * @return
     */
    @RequestMapping(value = "/updateGenerateHwc", method = RequestMethod.GET)
    @ApiOperation(value = "更新入库海外仓状态")
    void updateGenerateHwc(@RequestBody List<BigDecimal> params);
}
