package com.tadpole.cloud.externalSystem.modular.oa.service;

import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.externalSystem.modular.oa.entity.Hrmresourcetoebms;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-12
 */
public interface IHrmresourcetoebmsService extends IService<Hrmresourcetoebms> {

    List<HrmresourcetoebmsResult> getHrmResource();
}
