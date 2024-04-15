package com.tadpole.cloud.platformSettlement.core.menus;

import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作流相关菜单
 *
 * @author fengshuonan
 * @Date 2019年12月22日22:33:29
 */
@RestController
@ApiResource(name = "物流商资料", path = "/infomationMenus")
public class InfomationMenusController {

    @ApiResource(name = "物流商资料(一级)", path = "/oneMenu", menuFlag = true)
    public ResponseData workflow() {
        return ResponseData.success();
    }

    @ApiResource(name = "资料", path = "/data", menuFlag = true)
    public ResponseData data() {
        return ResponseData.success();
    }



}
