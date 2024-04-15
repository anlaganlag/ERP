package com.tadpole.cloud.platformSettlement.modular.sales.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.CollectionTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.CollectionTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.CollectionTargetResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 回款目标 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2022-03-04
 */
public interface CollectionTargetMapper extends BaseMapper<CollectionTarget> {

    List<CollectionTargetResult> findPageBySpec(@Param("paramCondition") CollectionTargetParam paramCondition);

    CollectionTargetResult getQuantity(@Param("paramCondition") CollectionTargetParam paramCondition);

}
