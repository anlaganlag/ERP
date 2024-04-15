package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FixedExchangeRateResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* ERP固定汇率 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface FixedExchangeRateMapper extends BaseMapper<FixedExchangeRate> {

    Page<FixedExchangeRateResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") FixedExchangeRateParam paramCondition);

    List<FixedExchangeRate> queryErp();

    void deleteAll();

    List<FixedExchangeRate> originalCurrencyList();

    FixedExchangeRate getRateByDateCurrency(@Param("paramCondition") FixedExchangeRateParam param);

    /**
     * 查询ERP固定利率
     * @param param
     * @return
     */
    FixedExchangeRate getRateByDateCurrencyErp(@Param("paramCondition") FixedExchangeRateParam param);

    /**
     * 查询固定利率
     * @param param
     * @return
     */
    List<FixedExchangeRate> getFixedExchangeRateList(@Param("paramCondition") FixedExchangeRateParam param);
}
