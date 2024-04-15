package com.tadpole.cloud.product.modular.productproposal.Controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleInvDetParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleInvDetService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 提案-开发样盘点明细 前端控制器
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
@RestController
@Api(tags = "提案-开发样盘点明细")
@ApiResource(name = "提案-开发样盘点明细", path = "/rdSampleInvDet")
public class RdSampleInvDetController {

    @Autowired
    private IRdSampleInvDetService sampleInvDetService;

    @PostResource(name = "开发样盘点-盘点明细", path = "/listSampleInvDet")
    @ApiOperation(value = "开发样盘点-盘点明细")
    @BusinessLog(title = "开发样盘点-盘点明细",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSampleInvDet(@RequestBody RdSampleInvDetParam param) {
        return ResponseData.success(this.sampleInvDetService.listSampleInvDet(param));
    }
}
