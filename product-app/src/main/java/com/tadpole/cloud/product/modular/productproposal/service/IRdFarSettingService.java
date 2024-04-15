package com.tadpole.cloud.product.modular.productproposal.service;

import com.tadpole.cloud.product.api.productproposal.entity.RdFarSetting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdFarSettingParam;

import java.util.List;

/**
 * <p>
 * 提案-设置-研发费用自动过审设置 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
public interface IRdFarSettingService extends IService<RdFarSetting> {
    List<RdFarSetting> listPage();

    void save(List<RdFarSettingParam> params);
}
