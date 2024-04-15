package com.tadpole.cloud.platformSettlement.modular.vat.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseExchangeRate;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseExchangeRateParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseExchangeRateResult;
import java.util.List;

/**
 * <p>
 * 基础信息-汇率表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
public interface IBaseExchangeRateService extends IService<BaseExchangeRate> {

    List<BaseExchangeRateResult> exportExcel(BaseExchangeRateParam param);

    Page<BaseExchangeRateResult> queryListPage(BaseExchangeRateParam param);

    ResponseData update(BaseExchangeRateParam param, String controllerName);

    ResponseData add(BaseExchangeRateParam param,String controllerName);

    ResponseData addBatch(List<BaseExchangeRateParam> params,String controllerName);

    ResponseData queryOriginalCurrency();

    ResponseData queryTargetCurrency();
}

