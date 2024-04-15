package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.VoucherDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 回款确认办理凭证 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface PaymentConfirmVoucherMapper extends MPJBaseMapper<PaymentConfirmVoucher> {


    List<PaymentVoucherDetail> queryVoucherDetail(@Param("paramCondition") PaymentConfirmVoucher param);

    List<PaymentVoucherDetail> voucherDetailTotal(@Param("paramCondition") PaymentConfirmVoucher param);

    List<VoucherDetailResult> getSyncDetail(@Param("paramCondition") PaymentConfirmVoucher param);
}
