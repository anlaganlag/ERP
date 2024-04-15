package com.tadpole.cloud.platformSettlement.api.finance;

import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: ty
 * @description: ERP固定汇率API
 * @date: 2023/4/13
 */
@RequestMapping("/fixedExchangeRateApi")
public interface FixedExchangeRateApi {

    /**
     * 查询ERP固定利率
     * @return
     */
    @RequestMapping(value = "/getRateByDateCurrency", method = RequestMethod.POST)
    @ApiOperation(value = "查询ERP固定利率")
    FixedExchangeRate getRateByDateCurrency(@RequestBody FixedExchangeRateParam param);

    /**
     * 查询固定利率
     * @return
     */
    @PostMapping(value = "/getFixedExchangeRateList")
    @ApiOperation(value = "查询固定利率")
    List<FixedExchangeRate> getFixedExchangeRateList(@RequestBody FixedExchangeRateParam param);

    /**
     * 固定汇率币别
     * @return
     */
    @GetMapping(value = "/originalCurrencyList")
    @ApiOperation(value = "固定汇率币别")
    List<FixedExchangeRate> originalCurrencyList();


}
