package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.BankSubjectCodeMcms;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmHandling;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.PaymentConfirmHandlingParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmHandlingResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 回款确认办理 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface PaymentConfirmHandlingMapper extends BaseMapper<PaymentConfirmHandling> {

    Page<PaymentConfirmHandlingResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") PaymentConfirmHandlingParam param);

    List<PaymentConfirmHandlingResult> getBank(@Param("paramCondition") PaymentConfirmHandlingParam param);

    BankSubjectCodeMcms getSubjectByBank(@Param("paramCondition") PaymentConfirmHandlingParam param);

    List<PaymentConfirmHandlingResult> exportPaymentConfirmHandlingList( @Param("paramCondition") PaymentConfirmHandlingParam paramCondition);


    void updateVoucherNo( @Param("paramCondition")  PaymentConfirmHandling handing);

    List<String> erpVoucherNo( @Param("paramCondition")  PaymentConfirmHandlingParam handing);
}
