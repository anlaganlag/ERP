package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsTaxRate;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsTaxRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsTaxRateResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 清关税率 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface TgCustomsTaxRateMapper extends BaseMapper<TgCustomsTaxRate> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    Page<TgCustomsTaxRateResult> queryPage(@Param("page") Page page, @Param("param") TgCustomsTaxRateParam param);

    /**
     * 目的国下拉
     * @return
     */
    List<BaseSelectResult> targetCountrySelect();

    /**
     * 根据国家级联HSCode下拉
     * @return
     */
    List<BaseSelectResult> hsCodeSelect(@Param("countryCode") String countryCode);
}
