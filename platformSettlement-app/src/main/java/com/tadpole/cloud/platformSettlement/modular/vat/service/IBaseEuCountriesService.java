package com.tadpole.cloud.platformSettlement.modular.vat.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseEuCountries;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseEuCountriesParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseEuCountriesResult;

/**
 * <p>
 * 基础信息-欧盟国家参数列表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
public interface IBaseEuCountriesService extends IService<BaseEuCountries> {

    Page<BaseEuCountriesResult> queryListPage(BaseEuCountriesParam param);

    ResponseData edit(BaseEuCountriesParam param);

    ResponseData save(BaseEuCountriesParam param);

    ResponseData euCountry();

    ResponseData cnCountry();

    ResponseData enCountry();
}
