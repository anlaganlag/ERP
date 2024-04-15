package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.SpotExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SpotExchangeRateResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* ERP即期汇率 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface SpotExchangeRateMapper extends BaseMapper<SpotExchangeRate> {

    Page<SpotExchangeRateResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") SpotExchangeRateParam paramCondition);

    List<SpotExchangeRate> queryErp();

    List<SpotExchangeRate> originalCurrencyList();

    void deleteAll();

    SpotExchangeRate getRateByDateCurrency(@Param("paramCondition") SpotExchangeRateParam param);

}
