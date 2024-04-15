package com.tadpole.cloud.operationManagement.modular.shipment.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.DataScope;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.InvProductGallery;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.InvProductGalleryParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ListingSelectParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.InvProductGalleryResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ListingSelectResult;
import com.tadpole.cloud.operationManagement.modular.shipment.service.InvProductGalleryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * AmazSKU前端控制器
 * </p>
 *
 * @author lsy
 * @since 2023-02-03
 */
@RestController
@Api(tags = "亚马逊sku")
@ApiResource(name = "亚马逊sku", path = "/invProductGallery")
public class InvProductGalleryController {

    @Autowired
    private InvProductGalleryService service;

    private final String controllerName = "每日备货推荐New";


    /**
     * SKU推荐查询
     *
     * @param param
     * @return
     */
    @PostResource(name = "SKU推荐查询", path = "/querySku", requiredPermission = false )
    @ApiOperation(value = "SKU推荐查询", response = InvProductGalleryResult.class)
    @BusinessLog(title = controllerName + "_" +"SKU推荐查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData querySku(@RequestBody InvProductGalleryParam param) {
        List<InvProductGallery> results = service.querySkuDatalimit(param);
        if (ObjectUtil.isNotEmpty(results)) {
            return ResponseData.success(results);
        }
        return ResponseData.error("未找到相关推荐SKU组合信息");
    }

    /**
     * SKU表通用下拉接口 账号，站点，物料，asin，sku
     *
     * @param param
     * @return
     */
    @PostResource(name = "listing下拉选择", path = "/listingSelect", requiredPermission = false ,
            materialPermission = true, areaPermission = true)
    @ApiOperation(value = "listing下拉选择", response = ListingSelectResult.class)
    @DataScope(platformAreaAlias="a",platformField = "PLATFORM",areaField = "AREA")
    public ResponseData listingSelect(@RequestBody ListingSelectParam param) {
        ListingSelectResult result = service.listingSelect(param);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("未找listing下拉接口数据");

    }

    /**
     * 获取当前登录人信息
     *
     * @return
     */
    @GetResource(name = "获取当前登录人信息", path = "/getLoginUserInfo", requiredPermission = false)
    @ApiOperation(value = "获取当前登录人信息", response = LoginUser.class)
    public ResponseData getLoginUserInfo() {
        LoginUser userInfo = service.getLoginUserInfo();
        if (ObjectUtil.isNotNull(userInfo)) {
            return ResponseData.success(userInfo);
        }
        return ResponseData.error("未找登录信息");

    }


}
