package com.tadpole.cloud.product.modular.productinterpolate.Controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;


/**
* 产品内推
* @author AmteMa
* @date 2022/4/15
*/
@RestController
@ApiResource(name = "产品下单提醒", path = "/productOrderReminder")
@Api(tags = "产品下单提醒")
public class ProductOrderReminderController {

    @PostResource(name = "产品下单提醒-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "产品下单提醒-列表查询")
    @BusinessLog(title = "产品下单提醒-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage() {
        return ResponseData.success();
    }


}
