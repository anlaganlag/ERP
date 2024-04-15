package com.tadpole.cloud.product.modular.productproposal.Controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;


/**
* 推样预案
* @author AmteMa
* @date 2022/4/15
*/
@RestController
@ApiResource(name = "购样审核", path = "/sampleReview")
@Api(tags = "购样审核")
public class SampleReviewController {

    @PostResource(name = "购样审核-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "购样审核-列表查询")
    @BusinessLog(title = "购样审核-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage() {
        return ResponseData.success();
    }


}
