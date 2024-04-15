package com.tadpole.cloud.platformSettlement.modular.basic.Controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;


/**
* 财务
* @author AmteMa
* @date 2022/4/15
*/
@RestController
@ApiResource(name = "支付账号管理", path = "/paymentAccount")
@Api(tags = "支付账号管理")
public class PaymentAccountController {

    @PostResource(name = "支付账号管理-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "支付账号管理-列表查询")
    @BusinessLog(title = "支付账号管理-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage() {
        return ResponseData.success();
    }


}
