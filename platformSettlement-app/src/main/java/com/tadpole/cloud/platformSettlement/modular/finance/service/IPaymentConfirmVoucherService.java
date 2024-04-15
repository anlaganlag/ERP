package com.tadpole.cloud.platformSettlement.modular.finance.service;

import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmVoucherResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.VoucherDetailResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 回款确认办理凭证 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IPaymentConfirmVoucherService extends IService<PaymentConfirmVoucher> {

    PaymentConfirmVoucher queryVoucher(PaymentConfirmVoucher param);

    List<PaymentVoucherDetail> queryVoucherDetail(PaymentConfirmVoucher param);

    List<PaymentVoucherDetail> voucherDetailTotal(PaymentConfirmVoucher param);

    List<VoucherDetailResult> getSyncDetail(PaymentConfirmVoucher param);

    boolean updateById(PaymentConfirmVoucher param);

    /**
     * 查询凭证信息，包含凭证明细list
     * @param param
     * @return
     */
    PaymentConfirmVoucherResult queryVoucherInfo(PaymentConfirmVoucher param);
}
