package com.tadpole.cloud.product.modular.productproposal.Controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.model.params.*;
import com.tadpole.cloud.product.modular.productproposal.enums.RdProposalEnum;
import com.tadpole.cloud.product.modular.productproposal.service.IRdProposalService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSamplePaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 产品提案
 *
 * @author AmteMa
 * @date 2022/4/15
 */
@RestController
@ApiResource(name = "购样申请", path = "/rdSamplePa")
@Api(tags = "购样申请")
public class RdSamplePaController {

    @Autowired
    private IRdSamplePaService samplePaService;

    @PostResource(name = "购样申请-列表查询", path = "/listPage")
    @ApiOperation(value = "购样申请-列表查询")
    @BusinessLog(title = "购样申请-列表查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdSampleTaskParam param) {
        return ResponseData.success();
    }

    @PostResource(name = "购样申请-购样申请校验", path = "/checkIsCanCreateSamplePa")
    @ApiOperation(value = "购样申请-购样申请校验")
    @BusinessLog(title = "购样申请-购样申请校验", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData checkIsCanCreateSamplePa(@RequestBody RdSamplePaParam param) {
        return this.samplePaService.checkIsCanCreateSamplePa(param);
    }

    @PostResource(name = "购样申请-购样申请", path = "/purchaseSampleApp")
    @ApiOperation(value = "购样申请-购样申请")
    @BusinessLog(title = "购样申请-购样申请", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData purchaseSampleApp(@RequestBody RdSamplePaParam param) {
        return this.samplePaService.addOrEdit(param);
    }

    @PostResource(name = "购样申请-购样申请列表", path = "/listSamplePa")
    @ApiOperation(value = "购样申请-购样申请列表")
    @BusinessLog(title = "购样申请-购样申请列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSamplePa(@RequestBody RdSamplePaParam param) {
        return ResponseData.success(this.samplePaService.listSamplePa(param));
    }

    @PostResource(name = "购样申请-撤销购样申请", path = "/revokeSamplePurchaseApp")
    @ApiOperation(value = "购样申请-撤销购样申请")
    @BusinessLog(title = "购样申请-撤销购样申请", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData revokeSamplePurchaseApp(@RequestBody RdSamplePaParam param) {
        return this.samplePaService.revokeSamplePurchaseApp(param);
    }

    @PostResource(name = "购样申请-支付补充", path = "/paySupply")
    @ApiOperation(value = "购样申请-支付补充")
    @BusinessLog(title = "购样申请-支付补充", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData paySupply(@RequestBody RdSamplePaParam param) {
        return this.samplePaService.paySupply(param);
    }

    @PostResource(name = "购样申请-购样审核", path = "/reviewSamplePurchaseApp")
    @ApiOperation(value = "购样申请-购样审核")
    @BusinessLog(title = "购样申请-购样审核", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData reviewSamplePurchaseApp(@RequestBody List<RdSamplePaParam> params){
        return this.samplePaService.reviewSamplePurchaseApp(params);
    }

    @PostResource(name = "购样申请-购样审批", path = "/approveSamplePurchaseApp")
    @ApiOperation(value = "购样申请-购样审批")
    @BusinessLog(title = "购样申请-购样审批", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData approveSamplePurchaseApp(@RequestBody List<RdSamplePaParam> params){
        return this.samplePaService.approveSamplePurchaseApp(params);
    }

    @PostResource(name = "购样申请-购样申请详情", path = "/detail")
    @ApiOperation(value = "购样申请-购样申请详情")
    @BusinessLog(title = "购样申请-购样申请详情", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestBody RdSamplePaParam param) {
        return ResponseData.success(this.samplePaService.detail(param));
    }

    @PostResource(name = "购样申请-购样申请支付", path = "/paySamplePurchaseApp")
    @ApiOperation(value = "购样申请-购样申请支付")
    @BusinessLog(title = "购样申请-购样申请支付", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData paySamplePurchaseApp(@RequestBody RdSamplePaParam param){
        return this.samplePaService.paySamplePurchaseApp(param);
    }

    @PostResource(name = "购样申请-购样申请退款详情", path = "/refSamplePurchaseAppDetail")
    @ApiOperation(value = "购样申请-购样申请退款详情")
    @BusinessLog(title = "购样申请-购样申请退款详情", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData refSamplePurchaseAppDetail(@RequestBody RdSamplePaParam param){
        return this.samplePaService.refSamplePurchaseAppDetail(param);
    }

    @PostResource(name = "购样申请-购样申请退款", path = "/refSamplePurchaseApp")
    @ApiOperation(value = "购样申请-购样申请退款")
    @BusinessLog(title = "购样申请-购样申请退款", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData refSamplePurchaseApp(@RequestBody RdSampleRprParam param){
        return this.samplePaService.refSamplePurchaseApp(param);
    }

    @PostResource(name = "购样申请-购样申请直接退货", path = "/returnSamplePurchaseApp")
    @ApiOperation(value = "购样申请-购样申请直接退货")
    @BusinessLog(title = "购样申请-购样申请直接退货", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData returnSamplePurchaseApp(@RequestBody RdSamplePaParam param) {
        return this.samplePaService.returnSamplePurchaseApp(param);
    }

    @PostResource(name = "购样申请-购样申请直接退货校验", path = "/checkReturnSamplePurchaseApp")
    @ApiOperation(value = "购样申请-购样申请直接退货校验")
    @BusinessLog(title = "购样申请-购样申请直接退货校验", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData checkReturnSamplePurchaseApp(@RequestBody RdSamplePaParam param) {
        return this.samplePaService.checkReturnSamplePurchaseApp(param);
    }
}
