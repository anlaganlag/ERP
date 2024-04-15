package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentVoucherDetail;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.PaymentVoucherDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IPaymentVoucherDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* 回款确认凭证明细 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class PaymentVoucherDetailServiceImpl extends ServiceImpl<PaymentVoucherDetailMapper, PaymentVoucherDetail> implements IPaymentVoucherDetailService {

    @Autowired
    private PaymentVoucherDetailMapper mapper;

}
