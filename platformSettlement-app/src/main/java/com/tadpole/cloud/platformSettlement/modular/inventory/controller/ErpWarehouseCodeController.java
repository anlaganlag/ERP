package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * ERP仓库组织编码 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-12-07
 */
@RestController
@ApiResource(name = "组织编码", path = "/organizationCode")
@Api(tags = "组织编码")
public class ErpWarehouseCodeController {

  @Autowired
  private IErpWarehouseCodeService service;

  @GetResource(name = "获取销售组织编码", path = "/getOrganizationCode")
  @ApiOperation(value = "获取销售组织编码")
  public String getOrganizationCode(String name) {
    return service.getOrganizationCode(name);
  }

  @GetResource(name = "获取销售组织", path = "/getSalesOrganization")
  @ApiOperation(value = "获取销售组织")
  public String getSalesOrganization(String orderId) {
    return service.getSalesOrganization(orderId);
  }
}
