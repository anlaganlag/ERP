package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdSampleTaskProgressDet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdExpenseReimburseParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskProgressDetParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdExpenseReimburseResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskProgressDetResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-开发样任务进度明细 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2024-02-29
 */
public interface RdSampleTaskProgressDetMapper extends BaseMapper<RdSampleTaskProgressDet> {
    List<RdSampleTaskProgressDetResult> listTaskProgressDet(@Param("paramCondition") RdSampleTaskProgressDetParam param);
}
