package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdSamplePa;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSamplePaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSamplePaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-购样申请 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdSamplePaMapper extends BaseMapper<RdSamplePa> {
    List<RdSamplePaResult> listSamplePa(@Param("paramCondition") RdSamplePaParam param);

    List<RdSampleTaskExtentResult> listPageReview(@Param("paramCondition") RdSampleTaskParam param);

    List<RdSampleTaskExtentResult> listPageApprove(@Param("paramCondition") RdSampleTaskParam param);

    List<RdSamplePaResult> listPaApprove(@Param("paramCondition") RdSampleTaskParam param);

    RdSamplePaResult detail(@Param("paramCondition") RdSamplePaParam param);
}
