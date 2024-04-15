package com.tadpole.cloud.product.modular.productplan.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.product.api.productplan.entity.RdPreProposal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalParam;
import com.tadpole.cloud.product.api.productplan.model.result.RdPreProposalExtentResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 预提案 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
public interface RdPreProposalMapper extends BaseMapper<RdPreProposal> {
    IPage<RdPreProposalExtentResult> listPage(@Param("page") Page page, @Param("paramCondition") RdPreProposalParam param);

    RdPreProposalExtentResult detail(@Param("paramCondition") RdPreProposalParam param);

    List<RdPreProposalExtentResult> listPageFebk(@Param("paramCondition") RdPreProposalParam param);
}
