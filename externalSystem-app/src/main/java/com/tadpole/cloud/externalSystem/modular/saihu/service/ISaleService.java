package com.tadpole.cloud.externalSystem.modular.saihu.service;

import com.tadpole.cloud.externalSystem.api.saihu.model.params.SaiHuProductParam;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;

/**
 * @author: ty
 * @description: 赛狐销售服务接口类
 * @date: 2024/2/19
 */
public interface ISaleService {

    /**
     * 赛狐在线产品
     * @param param
     * @return
     * @throws Exception
     */
    SaiHuBaseResult productList(SaiHuProductParam param) throws Exception;
}
