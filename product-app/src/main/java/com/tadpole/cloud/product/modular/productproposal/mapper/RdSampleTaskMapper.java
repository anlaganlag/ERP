package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.tadpole.cloud.product.api.productproposal.entity.RdSampleTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskTimeAndCostResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-开发样任务 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdSampleTaskMapper extends BaseMapper<RdSampleTask> {

    List<RdSampleTaskExtentResult> listPage(@Param("paramCondition") RdSampleTaskParam param);

    RdSampleTaskExtentResult detail(@Param("paramCondition") RdSampleTaskParam param);

    List<RdSampleTaskTimeAndCostResult> queryTaskCost(@Param("paramCondition") RdSampleTaskParam param);

    RdSampleTaskResult queryTaskTime(@Param("paramCondition") RdSampleTaskParam param);

    void updateTsRead(@Param("account") String account, @Param("sysTsTaskCode")  String sysTsTaskCode);

    List<RdSampleTask> getIsTsRead(@Param("account") String account, @Param("sysTsTaskCode")  String sysTsTaskCode);
}
