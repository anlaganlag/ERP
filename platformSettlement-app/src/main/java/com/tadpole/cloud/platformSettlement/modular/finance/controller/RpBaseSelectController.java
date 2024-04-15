package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.FinanceReportTypes;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialSiteResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ShopCurrencyResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFinancialSiteService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFixedExchangeRateService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IShopCurrencyService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.RpMaterialConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * <p>
 * 亚马逊物流买家货件销售 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-23
 */
@RestController
@ApiResource(name = "结算报告下拉", path = "/RpBaseSelect")
@Api(tags = "结算报告下拉")
public class RpBaseSelectController {

  @Autowired
  private IFinancialSiteService siteService;
  @Autowired
  private IShopCurrencyService shopService;
  @Autowired
  private BaseSelectConsumer baseSelectConsumer;
  @Autowired
  private RpMaterialConsumer rpMaterialConsumer;
  @Autowired
  private IFixedExchangeRateService currencyService;

  @GetResource(name = "币种下拉", path = "/getCurrency", requiredPermission = false)
  @ApiOperation(value = "币种下拉", response = ShopCurrencyResult.class)
  public ResponseData getCurrency() {
    List<FixedExchangeRate> list = currencyService.originalCurrencyList();
    return ResponseData.success(list);
  }

  @GetResource(name = "账号下拉", path = "/getShop", requiredPermission = false)
  @ApiOperation(value = "账号下拉", response = ShopCurrencyResult.class)
  public ResponseData getShop() {
    List<ShopCurrencyResult> list = shopService.getShop();
    return ResponseData.success(list);
  }

  @GetResource(name = "站点下拉", path = "/getSite", requiredPermission = false)
  @ApiOperation(value = "站点下拉", response = FinancialSiteResult.class)
  public ResponseData getSite() {
    List<FinancialSiteResult> list = siteService.getSite();
    return ResponseData.success(list);
  }

  @GetResource(name = "获取物料", path = "/getMaterial", requiredPermission = false)
  @ApiOperation(value = "获取物料")
  public ResponseData detail(@RequestParam String materialCode) {
    return ResponseData.success(rpMaterialConsumer.getMaterialInfo(materialCode));
  }

  @GetResource(name = "平台选择", path = "/getPlatformSelect", requiredPermission = false)
  @ApiOperation(value = "平台选择")
  public ResponseData getPlatformSelect() {
    return ResponseData.success(baseSelectConsumer.getPlatform());
  }

  @GetResource(name = "事业部选择", path = "/getDepartmentSelect", requiredPermission = false)
  @ApiOperation(value = "事业部选择")
  public ResponseData getDepartmentSelect() {
    return ResponseData.success(baseSelectConsumer.getDepartmentSelect());
  }

  @GetResource(name = "Team选择", path = "/getTeamSelect", requiredPermission = false)
  @ApiOperation(value = "Team选择")
  public ResponseData getTeamSelect() {
    return ResponseData.success(baseSelectConsumer.getTeamSelect());
  }

  @GetResource(name = "报告类型", path = "/getReportTypeSelect", requiredPermission = false)
  @ApiOperation(value = "报告类型")
  public ResponseData getReportTypeSelect() {
    return ResponseData.success(FinanceReportTypes.getEnumList());
  }
}
