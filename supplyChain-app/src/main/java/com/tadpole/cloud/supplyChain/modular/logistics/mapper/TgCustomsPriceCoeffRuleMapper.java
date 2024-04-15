package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsPriceCoeffRule;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsPriceCoeffRuleParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsPriceCoeffRuleResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 清关价格折算 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgCustomsPriceCoeffRuleMapper extends BaseMapper<TgCustomsPriceCoeffRule> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomsPriceCoeffRuleResult> queryPage(@Param("page") Page page, @Param("param") TgCustomsPriceCoeffRuleParam param);

    /**
     * 根据目的国、hsCode和关税率范围查询清关价格折算
     * @param param
     * @return
     */
    List<TgCustomsPriceCoeffRule> selectContainVal(@Param("param") TgCustomsPriceCoeffRuleParam param);

    /**
     * 根据条件查询清关价格折算
     * @param param
     * @return
     */
    TgCustomsPriceCoeffRule queryPriceCoeff(@Param("param") TgCustomsPriceCoeffRuleParam param);
}
