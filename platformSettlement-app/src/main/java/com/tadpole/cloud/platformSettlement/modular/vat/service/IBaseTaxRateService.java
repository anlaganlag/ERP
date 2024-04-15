package com.tadpole.cloud.platformSettlement.modular.vat.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseTaxRate;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseTaxRateParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseTaxRateResult;

/**
 * <p>
 * 基础信息-税率表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
public interface IBaseTaxRateService extends IService<BaseTaxRate> {
    Page<BaseTaxRateResult> queryListPage(BaseTaxRateParam param);

    ResponseData add(BaseTaxRateParam param,String controllerName);

    ResponseData update(BaseTaxRateParam param, String controllerName);
}
