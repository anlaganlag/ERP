package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmVoucherResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IPaymentConfirmVoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* <p>
* 回款确认办理凭证 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "回款确认凭证", path = "/paymentConfirmVoucher")
@Api(tags = "回款确认凭证")
public class PaymentConfirmVoucherController {

    @Autowired
    private IPaymentConfirmVoucherService service;

    @GetResource(name = "回款确认凭证-销售中心", path = "/queryVoucher", requiredPermission = false)
    @ApiOperation(value = "回款确认凭证-销售中心", response = PaymentConfirmVoucher.class)
    @BusinessLog(title = "回款确认凭证-回款确认凭证-销售中心",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryVoucher(PaymentConfirmVoucher param) {
        param.setVoucherType("销售中心");
        PaymentConfirmVoucher voucher = service.queryVoucher(param);
        return ResponseData.success(voucher);
    }

    @GetResource(name = "回款确认凭证明细-销售中心", path = "/queryVoucherDetail",requiredPermission = false)
    @ApiOperation(value = "回款确认凭证明细-销售中心", response = PaymentVoucherDetail.class)
    @BusinessLog(title = "回款确认凭证-回款确认凭证明细-销售中心",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryVoucherDetail(PaymentConfirmVoucher param) {
        param.setVoucherType("销售中心");
        List<PaymentVoucherDetail> voucherDetailList= service.queryVoucherDetail(param);
        return ResponseData.success(voucherDetailList);
    }

    @GetResource(name = "凭证明细合计-销售中心", path = "/voucherDetailTotal",requiredPermission = false)
    @ApiOperation(value = "凭证明细合计-销售中心", response = PaymentVoucherDetail.class)
    @BusinessLog(title = "回款确认凭证-凭证明细合计-销售中心",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData voucherDetailTotal(PaymentConfirmVoucher param) {
        param.setVoucherType("销售中心");
        List<PaymentVoucherDetail> voucherDetailList= service.voucherDetailTotal(param);
        return ResponseData.success(voucherDetailList);
    }

    @GetResource(name = "回款确认凭证-采购中心", path = "/queryVoucherPurchase", requiredPermission = false)
    @ApiOperation(value = "回款确认凭证-采购中心", response = PaymentConfirmVoucher.class)
    @BusinessLog(title = "回款确认凭证-回款确认凭证-采购中心",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryVoucherPurchase(PaymentConfirmVoucher param) {
        param.setVoucherType("采购中心");
        PaymentConfirmVoucher voucher = service.queryVoucher(param);
        return ResponseData.success(voucher);
    }

    @GetResource(name = "回款确认凭证明细-采购中心", path = "/queryVoucherDetailPurchase",requiredPermission = false)
    @ApiOperation(value = "回款确认凭证明细-采购中心", response = PaymentVoucherDetail.class)
    @BusinessLog(title = "回款确认凭证-回款确认凭证明细-采购中心",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryVoucherDetailPurchase(PaymentConfirmVoucher param) {
        param.setVoucherType("采购中心");
        List<PaymentVoucherDetail> voucherDetailList= service.queryVoucherDetail(param);
        return ResponseData.success(voucherDetailList);
    }

    @GetResource(name = "凭证明细合计-采购中心", path = "/voucherDetailTotalPurchase",requiredPermission = false)
    @ApiOperation(value = "凭证明细合计-采购中心", response = PaymentVoucherDetail.class)
    @BusinessLog(title = "回款确认凭证-凭证明细合计-采购中心",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData voucherDetailTotalPurchase(PaymentConfirmVoucher param) {
        param.setVoucherType("采购中心");
        List<PaymentVoucherDetail> voucherDetailList= service.voucherDetailTotal(param);
        return ResponseData.success(voucherDetailList);
    }

    @PostResource(name = "回款确认凭证信息查询", path = "/queryVoucherInfo",requiredPermission = false)
    @ApiOperation(value = "回款确认凭证信息查询", response = PaymentVoucherDetail.class)
    @BusinessLog(title = "回款确认凭证信息查询-销售中心",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryVoucherInfo(@RequestBody PaymentConfirmVoucher param) {
        param.setVoucherType("销售中心");
        PaymentConfirmVoucherResult voucherInfo= service.queryVoucherInfo(param);
        return ResponseData.success(voucherInfo);
    }
}
