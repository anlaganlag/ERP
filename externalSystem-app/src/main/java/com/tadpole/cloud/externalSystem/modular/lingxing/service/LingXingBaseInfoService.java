package com.tadpole.cloud.externalSystem.modular.lingxing.service;

import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;

/**
 * @author: ty
 * @description: 领星基础数据Service接口类
 * @date: 2022/4/22
 */
public interface LingXingBaseInfoService {

    /**
     * 查询亚马逊店铺信息
     * @return
     */
    LingXingBaseRespData getSellerLists() throws Exception;
}
