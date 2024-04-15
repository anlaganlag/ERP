package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsPriceMinRule;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsPriceMinRuleParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsPriceMinRuleResult;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 最低清关价格 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgCustomsPriceMinRuleMapper extends BaseMapper<TgCustomsPriceMinRule> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomsPriceMinRuleResult> queryPage(@Param("page") Page page, @Param("param") TgCustomsPriceMinRuleParam param);

}
