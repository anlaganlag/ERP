package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 税金测算VAT店铺维度 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@RestController
@ApiResource(name = "税金测算VAT店铺维度", path = "/vatShop")
@Api(tags = "税金测算VAT店铺维度")
public class VatShopController {

    @Autowired
    private IVatShopService service;

    @PostResource(name = "税金测算VAT店铺维度", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "税金测算VAT店铺维度")
    public ResponseData queryListPage() {
        return ResponseData.success();
    }
}
