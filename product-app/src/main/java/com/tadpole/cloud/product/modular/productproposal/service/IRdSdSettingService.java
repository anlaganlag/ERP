package com.tadpole.cloud.product.modular.productproposal.service;

import com.tadpole.cloud.product.api.productproposal.entity.RdSdSetting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSdSettingParam;

import java.util.List;

/**
 * <p>
 * 提案-设置-拿样任务时长设置 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
public interface IRdSdSettingService extends IService<RdSdSetting> {
    List<RdSdSetting> listPage();
    void save(List<RdSdSettingParam> params);
}
