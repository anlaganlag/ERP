package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IShopCurrencyService;
import com.tadpole.cloud.platformSettlement.modular.vat.enums.ShopCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatShopCheckParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatCheckResult;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatShopCheckResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatShopCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 账号检查表 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@RestController
@ApiResource(name = "账号检查列表", path = "/shopCheck")
@Api(tags = "账号检查列表")
public class VatShopCheckController {

    @Autowired
    private IVatShopCheckService service;
    @Autowired
    private IShopCurrencyService shopService;

    @PostResource(name = "账号检查列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "账号检查列表",response = VatShopCheckResult.class)
    @BusinessLog(title = "账号检查列表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody VatShopCheckParam param) {
        return service.queryListPage(param);
    }

    @GetResource(name = "账号检查", path = "/shopCheck")
    @ApiOperation(value = "账号检查" ,response = VatCheckResult.class)
    public ResponseData shopCheck(VatShopCheckParam param) {
        TbComShop shop = new TbComShop();
        List<TbComShop> list = shopService.findShopEbms(shop);
        return service.shopCheck(list);
    }

    @GetResource(name = "修改", path = "/update")
    @ApiOperation(value = "修改" ,response = VatCheckResult.class)
    @BusinessLog(title = "账号检查列表-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData update(VatShopCheckParam param) {
        return service.updateRmark(param);
    }

    @GetResource(name = "异常类型下拉", path = "/errorType",requiredPermission = false)
    @ApiOperation(value = "异常类型下拉", response = VatCheckResult.class)
    public ResponseData errorType() throws Exception {
        return ResponseData.success(ShopCheckStatus.getEnumList());
    }

    @GetResource(name = "欧盟店铺下拉", path = "/euShop",requiredPermission = false)
    @ApiOperation(value = "欧盟店铺下拉", response = VatCheckResult.class)
    public ResponseData euShop() {
        return service.euShop();
    }
}
