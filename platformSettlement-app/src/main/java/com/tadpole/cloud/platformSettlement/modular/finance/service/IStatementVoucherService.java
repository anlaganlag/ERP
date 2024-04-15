package com.tadpole.cloud.platformSettlement.modular.finance.service;

import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.VoucherDetailResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 收入记录表凭证 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IStatementVoucherService extends IService<StatementVoucher> {

    StatementVoucher queryVoucher(StatementVoucher param);

    List<StatementVoucherDetail> queryVoucherDetail(StatementVoucher param);

    List<StatementVoucherDetail> voucherDetailTotal(StatementVoucher param);

    List<VoucherDetailResult> getSyncDetail(StatementVoucher param);

    boolean updateById(StatementVoucher voucher);
}
