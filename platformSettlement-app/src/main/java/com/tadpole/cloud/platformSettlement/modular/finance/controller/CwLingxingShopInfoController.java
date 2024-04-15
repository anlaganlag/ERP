package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ICwLingxingShopInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  领星亚马逊店铺信息前端控制器
 * </p>
 *
 * @author ty
 * @since 2022-04-29
 */
@RestController
@ApiResource(name = "领星亚马逊店铺信息前端控制器", path = "/lingxingShopInfo")
@Api(tags = "领星亚马逊店铺信息前端控制器")
public class CwLingxingShopInfoController {

    @Autowired
    private ICwLingxingShopInfoService cwLingxingShopInfoService;

    @GetResource(name = "同步领星亚马逊店铺信息", path = "/syncSellerLists", requiredPermission = false, requiredLogin = false)
    @ApiOperation("同步领星亚马逊店铺信息")
    @BusinessLog(title = "领星亚马逊店铺信息-同步领星亚马逊店铺信息",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData syncSellerLists() throws Exception {
        cwLingxingShopInfoService.syncSellerLists();
        return ResponseData.success();
    }
}

