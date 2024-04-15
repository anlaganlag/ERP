package com.tadpole.cloud.platformSettlement.api.inventory;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author: ty
 * @description: ERP仓库组织编码API
 * @date: 2023/4/14
 */
@RequestMapping("/erpWarehouseCodeApi")
public interface ErpWarehouseCodeApi {

    /**
     * 根据组织名称获取组织编码
     * @param orgName
     * @return
     */
    @RequestMapping(value = "/getOrganizationCode", method = RequestMethod.GET)
    @ApiOperation(value = "根据组织名称获取组织编码")
    String getOrganizationCode(@RequestParam String orgName);

    /**
     * 根据仓库名称获取组织编码
     * @param warehouseName
     * @return
     */
    @RequestMapping(value = "/getOrganizationCodeByWarehouse", method = RequestMethod.GET)
    @ApiOperation(value = "根据仓库名称获取组织编码")
    ResponseData getOrganizationCodeByWarehouse(@RequestParam String warehouseName);

    /**
     * 获取组织编码
     * @param
     * @return
     */
    @RequestMapping(value = "/getOrgMap", method = RequestMethod.GET)
    @ApiOperation(value = "获取组织编码")
    Map<String,String> getOrgMap();
}
