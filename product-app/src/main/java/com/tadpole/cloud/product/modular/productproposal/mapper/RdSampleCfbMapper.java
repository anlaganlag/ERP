package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdSampleCfb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCfbParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCfbResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-定制反馈 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdSampleCfbMapper extends BaseMapper<RdSampleCfb> {
    List<RdSampleCfbResult> listSampleCfb(@Param("paramCondition") RdSampleCfbParam param);

    List<RdSampleTaskExtentResult> listPageApprove(@Param("paramCondition") RdSampleTaskParam param);

    RdSampleCfbResult detail(@Param("paramCondition") RdSampleCfbParam param);
}
