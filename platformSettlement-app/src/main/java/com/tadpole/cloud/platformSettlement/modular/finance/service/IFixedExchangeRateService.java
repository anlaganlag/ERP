package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FixedExchangeRateResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * ERP固定汇率 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IFixedExchangeRateService extends IService<FixedExchangeRate> {

    PageResult<FixedExchangeRateResult> findPageBySpec(FixedExchangeRateParam param);

    List<FixedExchangeRate> queryErp();

    void insertErpRate(List<FixedExchangeRate> list);

    List<FixedExchangeRate> originalCurrencyList();

    /**
     * 获取固定汇率
     * @param param
     * @return
     */
    FixedExchangeRate getRateByDateCurrency(FixedExchangeRateParam param);

    /**
     * 获取固定汇率
     * @param param
     * @return
     */
    List<FixedExchangeRate> getFixedExchangeRateList(FixedExchangeRateParam param);

    /**
     * 查询ERP固定利率
     * @param param
     * @return
     */
    FixedExchangeRate getRateByDateCurrencyErp(FixedExchangeRateParam param);
}
