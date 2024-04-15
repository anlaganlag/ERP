package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.VoucherDetailResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 收入记录表凭证 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface StatementVoucherMapper extends BaseMapper<StatementVoucher> {

    List<StatementVoucherDetail> queryVoucherDetail(@Param("paramCondition") StatementVoucher param);

    List<StatementVoucherDetail> voucherDetailTotal(@Param("paramCondition") StatementVoucher param);

    List<VoucherDetailResult> getSyncDetail(@Param("paramCondition") StatementVoucher param);
}
