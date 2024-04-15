package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleManageParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdProposalExtentResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleManageResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-开发样管理 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdSampleManageMapper extends BaseMapper<RdSampleManage> {

    IPage<RdSampleManageResult> listPage(@Param("page") Page page, @Param("paramCondition") RdSampleManageParam param);

    List<RdSampleManageResult> listTestSample(@Param("paramCondition") RdSampleManageParam param);

    List<RdSampleManageResult> listSample(@Param("paramCondition") RdSampleManageParam param);

    RdSampleManageResult detail(@Param("paramCondition") RdSampleManageParam param);

    List<RdSampleManageResult> listSampleManage(@Param("paramCondition") RdSampleManageParam param);

    @Update("")
    int invSample();
}
