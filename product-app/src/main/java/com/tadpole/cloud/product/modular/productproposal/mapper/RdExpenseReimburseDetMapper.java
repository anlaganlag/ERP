package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdExpenseReimburseDet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdExpenseReimburseParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdExpenseReimburseDetResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-研发费报销明细 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
public interface RdExpenseReimburseDetMapper extends BaseMapper<RdExpenseReimburseDet> {
    List<RdExpenseReimburseDetResult> listExpenseReimburseDet(@Param("paramCondition") RdExpenseReimburseParam param);

}
