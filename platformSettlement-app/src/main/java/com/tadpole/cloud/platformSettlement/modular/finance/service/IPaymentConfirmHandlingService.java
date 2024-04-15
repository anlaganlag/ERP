package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.BankSubjectCodeMcms;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmHandling;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.PaymentConfirmHandlingParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmHandlingResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 回款确认办理 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IPaymentConfirmHandlingService extends IService<PaymentConfirmHandling> {

    PageResult<PaymentConfirmHandlingResult> findPageBySpec(PaymentConfirmHandlingParam param);

    List<PaymentConfirmHandlingResult> exportPaymentConfirmHandlingList(PaymentConfirmHandlingParam param);

    void confirm(PaymentConfirmHandlingParam param) throws Exception;

    void verify(PaymentConfirmHandlingParam param) throws Exception;

    ResponseData syncToErp(PaymentConfirmHandlingParam param) throws IOException;

    void confirmBatch(List<PaymentConfirmHandlingParam> params) throws Exception;

    void verifyBatch(List<PaymentConfirmHandlingParam> params) throws Exception;

    ResponseData syncToErpBatch(List<PaymentConfirmHandlingParam> params) throws IOException;

    List<PaymentConfirmHandlingResult> getBank(PaymentConfirmHandlingParam param);

    void reject(PaymentConfirmHandlingParam param);

    void rejectBatch(List<PaymentConfirmHandlingParam> params) throws IOException;

    BankSubjectCodeMcms getSubjectByBank(PaymentConfirmHandlingParam param);

    void updateVoucherNo(PaymentConfirmHandling handing);

    List<String> getErpVoucherNo(PaymentConfirmHandlingParam param);

    ResponseData returnVerify(PaymentConfirmHandlingParam param) throws Exception;

    ResponseData toReceivableDetail(PaymentConfirmHandlingParam param) throws Exception;
}
