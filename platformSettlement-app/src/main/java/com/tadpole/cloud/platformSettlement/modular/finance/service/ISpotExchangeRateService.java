package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SpotExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SpotExchangeRateResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * ERP即期汇率 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface ISpotExchangeRateService extends IService<SpotExchangeRate> {

    PageResult<SpotExchangeRateResult> findPageBySpec(SpotExchangeRateParam param);

    List<SpotExchangeRate> queryErp();

    void insertErpRate(List<SpotExchangeRate> list);

    List<SpotExchangeRate> originalCurrencyList();

    List<SpotExchangeRateResult> exportShopCurrencyList(SpotExchangeRateParam param);

    SpotExchangeRate getRateByDateCurrency(SpotExchangeRateParam param);

}
