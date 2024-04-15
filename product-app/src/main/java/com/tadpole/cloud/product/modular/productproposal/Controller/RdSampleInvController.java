package com.tadpole.cloud.product.modular.productproposal.Controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleInvParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleManageParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleInvService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 提案-开发样盘点 前端控制器
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
@RestController
@Api(tags = "提案-开发样盘点")
@ApiResource(name = "提案-开发样盘点", path = "/rdSampleInv")
public class RdSampleInvController {

    @Autowired
    private IRdSampleInvService sampleInvService;

    @PostResource(name = "开发样盘点-盘点查询", path = "/listSampleInv")
    @ApiOperation(value = "开发样盘点-盘点查询")
    @BusinessLog(title = "开发样盘点-盘点查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSampleInv() {
        return ResponseData.success(this.sampleInvService.listSampleInv());
    }

}
