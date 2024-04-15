package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCountryInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 国家地区信息 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgCountryInfoService extends IService<TgCountryInfo> {

    /**
     * 国家下拉
     * @return
     */
    ResponseData countrySelect();

}
