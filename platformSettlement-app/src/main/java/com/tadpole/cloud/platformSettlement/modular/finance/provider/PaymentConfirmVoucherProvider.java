package com.tadpole.cloud.platformSettlement.modular.finance.provider;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.PaymentConfirmVoucherApi;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmVoucherResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IPaymentConfirmVoucherService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IPaymentVoucherDetailService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PaymentConfirmVoucherProvider implements PaymentConfirmVoucherApi {
    @Resource
    private IPaymentConfirmVoucherService paymentConfirmVoucherService;

    @Resource
    private IPaymentVoucherDetailService paymentVoucherDetailService;




    @DataSource(name = "finance")
    @Override
    public PaymentConfirmVoucher saveOrUpdate(PaymentConfirmVoucher param) {
        paymentConfirmVoucherService.saveOrUpdate(param);
        return param;
    }

    @DataSource(name = "finance")
    @Override
    public List<PaymentVoucherDetail> saveVoucherDetail(List<PaymentVoucherDetail> paramDetailList) {
        paymentVoucherDetailService.saveOrUpdateBatch(paramDetailList);
        return paramDetailList;
    }

    @DataSource(name = "finance")
    @Override
    public PaymentConfirmVoucherResult queryVoucherInfo(@RequestBody PaymentConfirmVoucher param) {
        param.setVoucherType("销售中心");
        return paymentConfirmVoucherService.queryVoucherInfo(param);
    }
}
