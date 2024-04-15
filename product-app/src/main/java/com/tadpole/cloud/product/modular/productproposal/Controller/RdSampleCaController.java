package com.tadpole.cloud.product.modular.productproposal.Controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.model.params.RdRefRegistParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCfbParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleManageParam;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleCaService;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleCfbService;
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
@ApiResource(name = "定制申请", path = "/rdSampleCa")
@Api(tags = "定制申请")
public class RdSampleCaController {

    @Autowired
    private IRdSampleCaService sampleCaService;

    @PostResource(name = "开模合同管理-列表查询", path = "/listPage")
    @ApiOperation(value = "开模合同管理-列表查询")
    @BusinessLog(title = "开模合同管理-列表查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdSampleCaParam param) {
        return ResponseData.success(this.sampleCaService.listPage(param));
    }

    @PostResource(name = "开模合同管理-待审列表查询/研发费审批-待审批记录", path = "/listSampleCa")
    @ApiOperation(value = "开模合同管理-待审列表查询/研发费审批-待审批记录")
    @BusinessLog(title = "开模合同管理-待审列表查询/研发费审批-待审批记录", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSampleCa(@RequestBody RdSampleCaParam param) {
        return ResponseData.success(this.sampleCaService.listSampleCa(param));
    }

    @PostResource(name = "提案管理-上传合同", path = "/uploadContract")
    @ApiOperation(value = "提案管理-上传合同")
    @BusinessLog(title = "提案管理-上传合同", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData uploadContract(@RequestBody RdSampleCaParam param) {
        return sampleCaService.uploadContract(param);
    }

    @PostResource(name = "开模合同管理-合同审核", path = "/reviewContract")
    @ApiOperation(value = "开模合同管理-合同审核")
    @BusinessLog(title = "开模合同管理-合同审核", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData reviewContract(@RequestBody RdSampleCaParam param) {
        return sampleCaService.reviewContract(param);
    }

    @PostResource(name = "定制申请-定制申请详情", path = "/detail")
    @ApiOperation(value = "定制申请-定制申请详情")
    @BusinessLog(title = "定制申请-定制申请详情", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestBody RdSampleCaParam param) {
        return ResponseData.success(this.sampleCaService.detail(param));
    }

    @PostResource(name = "定制申请-定制申请审批", path = "/approveSampleCa")
    @ApiOperation(value = "定制申请-定制申请审批")
    @BusinessLog(title = "定制申请-定制申请审批", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData approveSampleCa(@RequestBody RdSampleCaParam param) {
        return sampleCaService.approveSampleCa(param);
    }

    @PostResource(name = "定制申请-定制申请支付", path = "/payRdSampleCa")
    @ApiOperation(value = "定制申请-定制申请支付")
    @BusinessLog(title = "定制申请-定制申请支付", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData payRdSampleCa(@RequestBody RdSampleCaParam param) {
        return sampleCaService.payRdSampleCa(param);
    }

    @PostResource(name = "定制申请-定制申请退款详情", path = "/refRdSampleCaDetail")
    @ApiOperation(value = "定制申请-定制申请退款详情")
    @BusinessLog(title = "定制申请-定制申请退款详情", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData refRdSampleCaDetail(@RequestBody RdSampleCaParam param) {
        return sampleCaService.refRdSampleCaDetail(param);
    }

    @PostResource(name = "定制申请-定制申请退款", path = "/refRdSampleCa")
    @ApiOperation(value = "定制申请-定制申请退款")
    @BusinessLog(title = "定制申请-定制申请退款", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData refRdSampleCa(@RequestBody RdRefRegistParam param) {
        return sampleCaService.refRdSampleCa(param);
    }
}
