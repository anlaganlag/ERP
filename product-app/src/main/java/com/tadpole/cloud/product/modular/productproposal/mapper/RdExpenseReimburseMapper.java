package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.product.api.productproposal.entity.RdExpenseReimburse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdExpenseReimburseParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdExpenseReimburseResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdProposalExtentResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-研发费报销 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
public interface RdExpenseReimburseMapper extends BaseMapper<RdExpenseReimburse> {
    IPage<RdExpenseReimburseResult> listPage(@Param("page") Page page, @Param("paramCondition") RdExpenseReimburseParam param);

    List<RdExpenseReimburseResult> listExpenseReimburse(@Param("paramCondition") RdExpenseReimburseParam param);

    RdExpenseReimburseResult detail(@Param("paramCondition") RdExpenseReimburseParam param);

    RdExpenseReimburseResult useLastRecipientAccount(@Param("paramCondition") RdExpenseReimburseParam param);
}
