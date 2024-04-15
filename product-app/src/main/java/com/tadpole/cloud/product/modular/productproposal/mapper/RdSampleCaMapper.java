package com.tadpole.cloud.product.modular.productproposal.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleCa;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCaParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCaResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 提案-定制申请 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface RdSampleCaMapper extends BaseMapper<RdSampleCa> {

    List<RdSampleCaResult> listSampleCa(@Param("paramCondition")RdSampleCaParam param);

    IPage<RdSampleCaResult> listPage(@Param("page") Page page, @Param("paramCondition")RdSampleCaParam param);

    RdSampleCaResult detail(@Param("paramCondition")RdSampleCaParam param);

}
