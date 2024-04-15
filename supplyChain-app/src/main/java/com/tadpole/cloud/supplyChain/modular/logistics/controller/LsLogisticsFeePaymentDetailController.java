package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsFeePaymentDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsFeePaymentDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物流费付款明细 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
@RestController
@Api(tags = "物流费付款明细")
@ApiResource(name = "物流费付款明细", path = "/lsLogisticsFeePaymentDetail")
public class LsLogisticsFeePaymentDetailController {

    @Autowired
    private ILsLogisticsFeePaymentDetailService service;

    /**
     * 列表查询
     * @param param
     * @return
     */
    @PostResource(name = "列表查询", path = "/queryList")
    @ApiOperation(value = "列表查询", response = LsLogisticsFeePaymentDetailResult.class)
    @BusinessLog(title = "物流费付款明细-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryList(@RequestBody LsLogisticsFeePaymentDetailParam param) {
        return ResponseData.success(service.queryList(param));
    }

    /**
     * 列表合计
     * @param param
     * @return
     */
    @PostResource(name = "列表合计", path = "/queryPageTotal", menuFlag = true)
    @ApiOperation(value = "列表合计", response = LsLogisticsFeePaymentDetailResult.class)
    @BusinessLog(title = "物流费付款明细-列表合计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListTotal(@RequestBody LsLogisticsFeePaymentDetailParam param) {
        return service.queryListTotal(param);
    }

}
