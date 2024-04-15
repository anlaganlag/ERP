package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.entity.RdMoldOpenPfa;
import com.tadpole.cloud.product.api.productproposal.model.result.RdFeePayResult;

import java.util.List;

/**
 * <p>
 * 提案-开模费付款申请 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdFeePaymentMapper extends BaseMapper<RdMoldOpenPfa> {
    List<RdFeePayResult> listRdFeePay();
}
