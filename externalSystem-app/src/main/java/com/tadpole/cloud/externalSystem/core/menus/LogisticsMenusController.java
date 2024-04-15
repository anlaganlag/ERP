package com.tadpole.cloud.externalSystem.core.menus;

import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import org.springframework.web.bind.annotation.RestController;

/*
 * Demo
 * @author AmteMa
 * @date 2022/4/14
 * @param  * @param null
 * @return
 */
@RestController
@ApiResource(name = "物流管理", path = "/manageMenus")
public class LogisticsMenusController {

    @ApiResource(name = "物流管理(一级)", path = "/oneMenu", menuFlag = true)
    public ResponseData workflow() {
        return ResponseData.success();
    }

    @ApiResource(name = "API物流对接", path = "/apiButt", menuFlag = true)
    public ResponseData apiButt() {
        return ResponseData.success();
    }
    @ApiResource(name = "头程物流", path = "/headWay", menuFlag = true)
    public ResponseData headWay() {
        return ResponseData.success();
    }
    @ApiResource(name = "平台物流", path = "/platform", menuFlag = true)
    public ResponseData platform() {
        return ResponseData.success();
    }
    @ApiResource(name = "第三方仓物流", path = "/otferWarehouse", menuFlag = true)
    public ResponseData otferWarehouse() {
        return ResponseData.success();
    }



}
