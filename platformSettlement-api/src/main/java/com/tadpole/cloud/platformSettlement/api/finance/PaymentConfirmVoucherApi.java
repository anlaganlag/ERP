package com.tadpole.cloud.platformSettlement.api.finance;

import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmVoucherResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/paymentConfirmVoucherApi")
public interface PaymentConfirmVoucherApi {

    /**
     * 保存回款确认办理凭证
     * @return
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存回款确认办理凭证")
    PaymentConfirmVoucher saveOrUpdate(@RequestBody PaymentConfirmVoucher param);
    /**
     * 保存回款确认办理凭证明细
     * @return
     */
    @PostMapping(value = "/saveDetail")
    @ApiOperation(value = "保存回款确认办理凭证明细")
    List<PaymentVoucherDetail>  saveVoucherDetail(@RequestBody List<PaymentVoucherDetail> paramDetailList);


    @PostMapping(value = "/queryVoucherInfo")
    @ApiOperation(value = "查询付款凭证信息")
    PaymentConfirmVoucherResult queryVoucherInfo(@RequestBody PaymentConfirmVoucher param);
}
