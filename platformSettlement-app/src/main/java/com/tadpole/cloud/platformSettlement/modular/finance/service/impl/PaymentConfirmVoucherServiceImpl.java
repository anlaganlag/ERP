package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmVoucherResult;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.PaymentConfirmVoucherMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.VoucherDetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IPaymentConfirmVoucherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* <p>
* 回款确认办理凭证 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class PaymentConfirmVoucherServiceImpl extends ServiceImpl<PaymentConfirmVoucherMapper, PaymentConfirmVoucher> implements IPaymentConfirmVoucherService {

    @Resource
    private PaymentConfirmVoucherMapper paymentConfirmVoucherMapper;

    @DataSource(name = "finance")
    @Override
    public PaymentConfirmVoucher queryVoucher(PaymentConfirmVoucher param) {
        QueryWrapper<PaymentConfirmVoucher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CONFIRM_ID",param.getConfirmId())
                .eq("VOUCHER_TYPE",param.getVoucherType());
        return this.baseMapper.selectOne(queryWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public List<PaymentVoucherDetail> queryVoucherDetail(PaymentConfirmVoucher param) {
        return this.baseMapper.queryVoucherDetail(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<PaymentVoucherDetail> voucherDetailTotal(PaymentConfirmVoucher param) {
        return this.baseMapper.voucherDetailTotal(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<VoucherDetailResult> getSyncDetail(PaymentConfirmVoucher param) {
        return this.baseMapper.getSyncDetail(param);
    }


    @DataSource(name = "finance")
    @Override
    public boolean updateById(PaymentConfirmVoucher param) {

     return super.updateById(param);

    }

    @DataSource(name = "finance")
    @Override
    public PaymentConfirmVoucherResult queryVoucherInfo(PaymentConfirmVoucher param) {
        MPJLambdaWrapper<PaymentConfirmVoucher> wrapper = new MPJLambdaWrapper<PaymentConfirmVoucher>();
        wrapper.selectAll(PaymentConfirmVoucher.class)
                .leftJoin(PaymentVoucherDetail.class,PaymentVoucherDetail::getVoucherId,PaymentConfirmVoucher::getVoucherId)
                .selectCollection(PaymentVoucherDetail.class, PaymentConfirmVoucherResult::getPaymentVoucherDetailList)
                .eq(ObjectUtil.isNotEmpty(param.getVoucherType()), PaymentConfirmVoucher::getVoucherType,param.getVoucherType())
                .eq(ObjectUtil.isNotEmpty(param.getVoucherNo()), PaymentConfirmVoucher::getVoucherNo,param.getVoucherNo())
                .eq(ObjectUtil.isNotNull(param.getVoucherId()), PaymentConfirmVoucher::getVoucherId,param.getVoucherId())
                .eq(ObjectUtil.isNotNull(param.getConfirmId()), PaymentConfirmVoucher::getConfirmId,param.getConfirmId());
        List<PaymentConfirmVoucherResult> confirmVoucherList = paymentConfirmVoucherMapper.selectJoinList(PaymentConfirmVoucherResult.class, wrapper);
        if (ObjectUtil.isNotEmpty(confirmVoucherList)) {
            return confirmVoucherList.get(0);
        }
        return null;
    }
}
