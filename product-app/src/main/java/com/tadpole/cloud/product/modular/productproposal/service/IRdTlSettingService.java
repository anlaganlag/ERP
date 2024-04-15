package com.tadpole.cloud.product.modular.productproposal.service;

import com.tadpole.cloud.product.api.productproposal.entity.RdTlSetting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdTlSettingParam;

import java.util.List;

/**
 * <p>
 * 提案-设置-提案等级审批设置 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
public interface IRdTlSettingService extends IService<RdTlSetting> {
    List<RdTlSetting> listPage();

    void save(List<RdTlSettingParam> params);
}
