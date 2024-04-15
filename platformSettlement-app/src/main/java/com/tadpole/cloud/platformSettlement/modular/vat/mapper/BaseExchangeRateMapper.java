package com.tadpole.cloud.platformSettlement.modular.vat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseExchangeRate;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseExchangeRateParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseExchangeRateResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基础信息-汇率表 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
public interface BaseExchangeRateMapper extends BaseMapper<BaseExchangeRate> {

    Page<BaseExchangeRateResult> queryListPage(@Param("page") Page page, @Param("paramCondition") BaseExchangeRateParam paramCondition);

    List<String> queryOriginalCurrency();

    List<String> queryTargetCurrency();

}
