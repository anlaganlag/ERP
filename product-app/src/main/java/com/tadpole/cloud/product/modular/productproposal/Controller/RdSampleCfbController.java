package com.tadpole.cloud.product.modular.productproposal.Controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCfbParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSamplePaParam;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleCaService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleCfbService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSamplePaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
* 产品提案
* @author AmteMa
* @date 2022/4/15
*/
@RestController
@ApiResource(name = "定制反馈", path = "/rdSampleCfb")
@Api(tags = "定制反馈")
public class RdSampleCfbController {

    @Autowired
    private IRdSampleCfbService sampleCfbService;

    @PostResource(name = "定制反馈-定制反馈校验", path = "/checkIsCanCreateFeedback")
    @ApiOperation(value = "定制反馈-定制反馈校验")
    @BusinessLog(title = "定制反馈-定制反馈校验",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData checkIsCanCreateFeedback(@RequestBody RdSampleCfbParam param) {
        return this.sampleCfbService.checkIsCanCreateFeedback(param);
    }

    @PostResource(name = "定制反馈-定制反馈", path = "/custFeedback")
    @ApiOperation(value = "定制反馈-定制反馈")
    @BusinessLog(title = "定制反馈-定制反馈",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData custFeedback(@RequestBody RdSampleCfbParam param) {
        return this.sampleCfbService.custFeedback(param);
    }

    @PostResource(name = "定制反馈-定制反馈列表", path = "/listSampleCfb")
    @ApiOperation(value = "定制反馈-定制反馈列表")
    @BusinessLog(title = "定制反馈-定制反馈列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSampleCfb(@RequestBody RdSampleCfbParam param) {
        return ResponseData.success(this.sampleCfbService.listSampleCfb(param));
    }

    @PostResource(name = "提案管理-定制申请反馈保存", path = "/custApplicationFeedback")
    @ApiOperation(value = "提案管理-定制申请反馈保存")
    @BusinessLog(title = "提案管理-定制申请反馈保存",opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData custApplicationFeedback(@RequestBody List<RdSampleCfbParam> params) {
        return sampleCfbService.custApplicationFeedback(params);
    }

    @PostResource(name = "定制反馈-定制反馈详情", path = "/detail")
    @ApiOperation(value = "定制反馈-定制反馈详情")
    @BusinessLog(title = "定制反馈-定制反馈详情",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestBody RdSampleCfbParam param) {
        return ResponseData.success(this.sampleCfbService.detail(param));
    }

    @PostResource(name = "定制申请-研发费统计", path = "/statisticsRdSampleCa")
    @ApiOperation(value = "定制申请-研发费统计")
    @BusinessLog(title = "定制申请-研发费统计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData statisticsRdSampleCa(@RequestBody RdSampleCfbParam param) {
        return this.sampleCfbService.statisticsRdSampleCa(param);
    }
}
