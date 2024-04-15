package com.tadpole.cloud.platformSettlement.modular.finance.provider;

import com.tadpole.cloud.platformSettlement.api.finance.FixedExchangeRateApi;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFixedExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ty
 * @description: ERP固定汇率服务提供者
 * @date: 2023/4/13
 */
@RestController
public class FixedExchangeRateProvider implements FixedExchangeRateApi {

    @Autowired
    private IFixedExchangeRateService fixedExchangeRateService;

    /**
     * 查询ERP固定利率
     * @param param
     * @return
     */
    public FixedExchangeRate getRateByDateCurrency(FixedExchangeRateParam param){
        return fixedExchangeRateService.getRateByDateCurrency(param);
    }

    /**
     * 查询固定利率
     * @param param
     * @return
     */
    public List<FixedExchangeRate> getFixedExchangeRateList(FixedExchangeRateParam param){
        return fixedExchangeRateService.getFixedExchangeRateList(param);
    }

    /**
     * 固定汇率币别
     * @return
     */
    public List<FixedExchangeRate> originalCurrencyList(){
        return fixedExchangeRateService.originalCurrencyList();
    }
}
