package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsAgainCoeffRule;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsAgainCoeffRuleParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsAgainCoeffRuleResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 清关二次折算 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgCustomsAgainCoeffRuleMapper extends BaseMapper<TgCustomsAgainCoeffRule> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomsAgainCoeffRuleResult> queryPage(@Param("page") Page page, @Param("param") TgCustomsAgainCoeffRuleParam param);

    /**
     * 根据目的国、币种和清关单价范围查询清关二次折算
     * @param param
     * @return
     */
    List<TgCustomsAgainCoeffRule> selectContainVal(@Param("param") TgCustomsAgainCoeffRuleParam param);

    /**
     * 根据条件查询清关价格二次折算
     * @param param
     * @return
     */
    TgCustomsAgainCoeffRule queryAgainCoeff(@Param("param") TgCustomsAgainCoeffRuleParam param);

}
