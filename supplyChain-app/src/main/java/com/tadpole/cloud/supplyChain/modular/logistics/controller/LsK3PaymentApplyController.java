package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsK3PaymentApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbPackOrderResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsK3PaymentApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物流费K3付款申请单 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-12-05
 */
@RestController
@Api(tags = "物流费K3付款申请单")
@ApiResource(name = "物流费K3付款申请单", path = "/lsK3PaymentApply")
public class LsK3PaymentApplyController {

    @Autowired
    private ILsK3PaymentApplyService service;

    /**
     * 查询
     * @param param
     * @return
     */
    @PostResource(name = "物流费K3付款申请单", path = "/queryPaymentApply", menuFlag = true)
    @ApiOperation(value = "查询", response = LsBtbPackOrderResult.class)
    @BusinessLog(title = "物流费K3付款申请单-查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPaymentApply(@RequestBody LsK3PaymentApplyParam param) {
        return service.queryPaymentApply(param);
    }

    /**
     * 是否可以编辑K3付款申请单
     * @param param
     * @return
     */
    @PostResource(name = "是否可以编辑K3付款申请单", path = "/canEditPaymentApply", menuFlag = true)
    @ApiOperation(value = "是否可以编辑K3付款申请单", response = LsBtbPackOrderResult.class)
    @BusinessLog(title = "物流费K3付款申请单-是否可以编辑K3付款申请单",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData canEditPaymentApply(@RequestBody LsK3PaymentApplyParam param) {
        return service.canEditPaymentApply(param);
    }

}
