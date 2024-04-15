package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleTask;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCfbParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSamplePaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCfbResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSamplePaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 提案-开模费付款申请 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface SampleTakeOverTimeDealMapper extends BaseMapper<RdSampleTask> {
    List<RdSampleTaskExtentResult> listTimeoutTask(@Param("paramCondition") RdSampleTaskParam param);

    List<RdSamplePaResult> listSamplePa(@Param("paramCondition") RdSamplePaParam param);

    List<RdSampleCfbResult> listSampleCfb(@Param("paramCondition") RdSampleCfbParam param);

}
