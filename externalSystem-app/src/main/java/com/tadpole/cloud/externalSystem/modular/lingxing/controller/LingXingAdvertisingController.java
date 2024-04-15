package com.tadpole.cloud.externalSystem.modular.lingxing.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.advertising.*;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingAdvertisingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;

/**
 * @author: ty
 * @description: 财务Controller类
 * @date: 2022/4/25
 */
@RestController
@ApiResource(name = "广告管理类Controller类", path = "/lingXingAdvertising")
@Api(tags = "广告管理类Controller类")
public class LingXingAdvertisingController {

    @Autowired
    private LingXingAdvertisingService lingXingAdvertisingService;

    @PostResource(name = "查询广告管理-广告组", path = "/advertisingGroup", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询广告管理 - 广告组")
    public ResponseData advertisingGroup(@RequestBody AdvertisingGroupReq req) throws Exception {
        return   lingXingAdvertisingService.advertisingGroup(req);
    }

    @PostResource(name = "查询广告管理-用户搜索词", path = "/userSearchWords", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询广告管理 - 用户搜索词")
    public ResponseData userSearchWords(@RequestBody UserSearchWordsReq req) throws Exception {
        return  lingXingAdvertisingService.userSearchWords(req);
    }

    @PostResource(name = "查询广告管理-商品定位", path = "/commodityPosition", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询广告管理 - 商品定位")
    public ResponseData commodityPosition(@RequestBody CommodityPositionReq req) throws Exception {
        return lingXingAdvertisingService.commodityPosition(req);
    }

    @PostResource(name = "查询广告管理-广告活动", path = "/advertisingActivity", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询广告管理 - 广告活动")
    public ResponseData advertisingActivity(@RequestBody AdvertisingActivityReq req) throws Exception {
        return lingXingAdvertisingService.advertisingActivity(req);
    }

    @PostResource(name = "查询广告管理-操作日志", path = "/operationLog", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询广告管理 - 操作日志")
    public ResponseData operationLog(@RequestBody OperationLogReq req) throws Exception {
        return lingXingAdvertisingService.operationLog(req);
    }

    @PostResource(name = "查询广告管理-广告", path = "/advertising", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询广告管理 - 广告")
    public ResponseData advertising(@RequestBody AdvertisingReq req) throws Exception {
        return lingXingAdvertisingService.advertising(req);
    }

    @PostResource(name = "查询广告管理-关键词", path = "/keywords", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询广告管理 - 关键词")
    public ResponseData keywords(@RequestBody KeywordsReq req) throws Exception {
        return lingXingAdvertisingService.keywords(req);
    }
}
