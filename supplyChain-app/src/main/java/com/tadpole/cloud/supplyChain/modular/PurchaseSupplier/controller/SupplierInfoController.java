package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.objectLog.client.service.impl.LogClient;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params.SupplierInfoParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierInfoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 供应商-供应商信息 前端控制器
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@RestController
@Api(tags = "采购供应商")
@ApiResource(name = "采购供应商", path = "/supplierInfo")
public class SupplierInfoController {

    @Autowired
    private ISupplierInfoService service;

    @PostResource(name = "供应商档案-列表查询", path = "/profileList", menuFlag = true)
    @ApiOperation(value = "供应商档案-列表查询")
    @BusinessLog(title = "供应商档案-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData profileList(@RequestBody SupplierInfoParam param) {
        return ResponseData.success(service.profileList(param));
    }

    @PostResource(name = "供应商档案-创建", path = "/profileAdd")
    @ApiOperation(value = "供应商档案-创建")
    @BusinessLog(title = "供应商档案-创建",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData profileAdd(@RequestBody SupplierInfoParam param) {

        return service.profileAdd(param);
    }

    @PostResource(name = "供应商档案-提交", path = "/profileSubmit")
    @ApiOperation(value = "供应商档案-提交")
    @BusinessLog(title = "供应商档案-提交",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData profileSubmit(@RequestBody SupplierInfoParam param) throws IllegalAccessException {
        return service.profileSubmit(param);
    }

    @PostResource(name = "供应商档案-变更", path = "/profileUpdate")
    @ApiOperation(value = "供应商档案-变更")
    @BusinessLog(title = "供应商档案-变更",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData profileUpdate(@RequestBody SupplierInfoParam param) throws IllegalAccessException {

        return service.profileUpdate(param);
    }

    @PostResource(name = "供应商部门审核-列表查询", path = "/departmentChecklist", menuFlag = true)
    @ApiOperation(value = "供应商部门审核-列表查询")
    @BusinessLog(title = "供应商部门审核-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData departmentChecklist(@RequestBody SupplierInfoParam param) {
        param.setSysAuditStatus("待管理审核");
        return ResponseData.success(service.supplierList(param));
    }

    @PostResource(name = "供应商部门审核-审核", path = "/departmentCheck")
    @ApiOperation(value = "供应商部门审核-审核")
    @BusinessLog(title = "供应商部门审核-审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData departmentCheck(@RequestBody SupplierInfoParam param) {
        service.departmentCheck(param);
        return ResponseData.success();
    }

    @PostResource(name = "供应商资质审核-列表查询", path = "/qualificationList", menuFlag = true)
    @ApiOperation(value = "供应商资质审核-列表查询")
    @BusinessLog(title = "供应商资质审核-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData qualificationList(@RequestBody SupplierInfoParam param) {
        param.setSysAuditStatus("待资质审核");
        return ResponseData.success(service.supplierList(param));
    }

    @PostResource(name = "供应商资质审核-审核", path = "/qualificationCheck")
    @ApiOperation(value = "供应商资质审核-审核")
    @BusinessLog(title = "供应商资质审核-审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData qualificationCheck(@RequestBody SupplierInfoParam param) {
        service.qualificationCheck(param);
        return ResponseData.success();
    }

    @PostResource(name = "供应商审批-列表查询", path = "/reviewList", menuFlag = true)
    @ApiOperation(value = "供应商审批-列表查询")
    @BusinessLog(title = "供应商审批-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData reviewList(@RequestBody SupplierInfoParam param) {
        param.setSysAuditStatus("待审批");
        return ResponseData.success(service.supplierList(param));
    }


    @PostResource(name = "供应商审批-审核", path = "/review")
    @ApiOperation(value = "供应商审批-审核")
    @BusinessLog(title = "供应商审批-审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData review(@RequestBody SupplierInfoParam param) throws Exception {
        return service.review(param);
    }

    @GetResource(name = "供应商-下拉", path = "/select")
    @ApiOperation(value = "供应商-下拉")
    @BusinessLog(title = "供应商-下拉",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData select() {
        return ResponseData.success(service.supSelect());
    }

    @PostResource(name = "同步erp", path = "/syncSupplierToErp")
    @ApiOperation(value = "同步erp")
    @BusinessLog(title = "同步erp",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData syncSupplierToErp(@RequestBody SupplierInfoParam param) throws Exception {
        return service.syncSupplierToErp();
    }
}
