package com.tadpole.cloud.product.modular.productproposal.Controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.model.params.RdCustProductParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.productproposal.service.IRdCustProductService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 提案-定品 前端控制器
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
@RestController
@Api(tags = "提案-定品")
@ApiResource(name = "提案-定品", path = "/rdCustProduct")
public class RdCustProductController {

    @Autowired
    private IRdCustProductService custProductService;

    @PostResource(name = "定品审批-列表查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "定品审批-列表查询")
    @BusinessLog(title = "定品审批-列表查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdCustProductParam param) {
        return ResponseData.success(this.custProductService.listPage(param));
    }

    @PostResource(name = "提案管理-定品申请校验", path = "/checkIsCanCreate")
    @ApiOperation(value = "提案管理-定品申请校验")
    @BusinessLog(title = "提案管理-定品申请校验", opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData checkIsCanCreate(@RequestBody RdCustProductParam param) {
        return custProductService.checkIsCanCreate(param);
    }

    @PostResource(name = "提案管理-定品待申/详情", path = "/detail")
    @ApiOperation(value = "提案管理-定品待申/详情")
    @BusinessLog(title = "提案管理-定品待申/详情", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestBody RdCustProductParam param) {
        return ResponseData.success(custProductService.detail(param));
    }

    @PostResource(name = "提案管理-定品一级审批", path = "/custProductAppr")
    @ApiOperation(value = "提案管理-定品一级审批")
    @BusinessLog(title = "提案管理-定品一级审批", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData custProductAppr(@RequestBody RdCustProductParam param) {
        return custProductService.custProductAppr(param);
    }

    @PostResource(name = "提案管理-定品二级审批", path = "/custProductAppr2")
    @ApiOperation(value = "提案管理-定品二级审批")
    @BusinessLog(title = "提案管理-定品二级审批", opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData custProductAppr2(@RequestBody RdCustProductParam param) {
        return custProductService.custProductAppr2(param);
    }

    @PostResource(name = "提案管理-定品申请", path = "/addOrEdit")
    @ApiOperation(value = "提案管理-定品申请")
    @BusinessLog(title = "提案管理-定品申请", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addOrEdit(@RequestBody RdCustProductParam param) {
        return custProductService.addOrEdit(param);
    }


    @PostResource(name = "定品二级审批-列表查询", path = "/listPage2", menuFlag = true)
    @ApiOperation(value = "定品二级审批-列表查询")
    @BusinessLog(title = "定品二级审批-列表查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage2(@RequestBody RdCustProductParam param) {
        return ResponseData.success(this.custProductService.listPage(param));
    }

}
