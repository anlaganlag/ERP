package com.tadpole.cloud.externalSystem.modular.ebms.service;

import com.tadpole.cloud.externalSystem.api.ebms.model.param.TbcomshopParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.result.TbcomshopResult;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.Tbcomshop;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-15
 */
public interface ITbcomshopService extends IService<Tbcomshop> {

    List<TbcomshopResult> getShopList(TbcomshopParam param);

    List<String> getPlatformList();
    List<Object> getAllShopNameList();
}
