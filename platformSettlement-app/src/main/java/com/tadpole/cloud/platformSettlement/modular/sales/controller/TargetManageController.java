package com.tadpole.cloud.platformSettlement.modular.sales.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.SalesTargetResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 销量目标 前端控制器
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
@RestController
@ApiResource(name = "目标管理", path = "/targetManageMenu")
@Api(tags = "目标管理")
public class TargetManageController {
    @PostResource(name = "目标管理", path = "/TargetManageMenu", menuFlag = true)
    @ApiOperation(value = "目标管理", response = SalesTargetResult.class)
    @BusinessLog(title = "目标管理", opType = LogAnnotionOpTypeEnum.QUERY)
    public void list() {
    }
}
