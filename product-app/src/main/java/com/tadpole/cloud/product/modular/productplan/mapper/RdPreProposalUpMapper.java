package com.tadpole.cloud.product.modular.productplan.mapper;

import com.tadpole.cloud.product.api.productplan.entity.RdPreProposalUp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.product.api.productplan.model.params.RdPreProposalUpParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 预提案-改良 Mapper接口
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
public interface RdPreProposalUpMapper extends BaseMapper<RdPreProposalUp> {
    int savaBatch(List<RdPreProposalUpParam> paramList);
}
