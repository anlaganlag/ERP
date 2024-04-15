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
* 产品提案
* @author AmteMa
* @date 2022/4/15
*/
@RestController
@ApiResource(name = "开模合同管理", path = "/moldOpenContract")
@Api(tags = "开模合同管理")
public class MoldOpenContractController {

    @PostResource(name = "开模合同管理-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "开模合同管理-列表查询")
    @BusinessLog(title = "开模合同管理-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage() {
        return ResponseData.success();
    }


}
