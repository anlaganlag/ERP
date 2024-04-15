package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.entity.RdSamplePa;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSamplePaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleApproveResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSamplePaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 提案-购样申请 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdFeeReimburseReviewMapper extends BaseMapper<RdSampleApproveResult> {
    List<RdSampleApproveResult> listPageApprove();

    List<RdSampleCaResult> listCaApprove(@Param("paramCondition") RdProposalParam param);

    List<RdSampleCaResult> listCaMoApprove(@Param("paramCondition") RdProposalParam param);

    List<RdSamplePaResult> listPaApprove(@Param("paramCondition") RdProposalParam param);

}
