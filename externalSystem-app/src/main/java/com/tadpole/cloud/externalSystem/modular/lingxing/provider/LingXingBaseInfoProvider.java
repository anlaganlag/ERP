package com.tadpole.cloud.externalSystem.modular.lingxing.provider;

import com.tadpole.cloud.externalSystem.api.lingxing.LingXingBaseInfoApi;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 领星基础数据服务提供者
 * @date: 2023/4/17
 */
@RestController
public class LingXingBaseInfoProvider implements LingXingBaseInfoApi {

    @Autowired
    private LingXingBaseInfoService lingXingBaseInfoService;

    /**
     * 查询亚马逊店铺信息
     * @return
     */
    public LingXingBaseRespData getSellerLists() throws Exception {
        return lingXingBaseInfoService.getSellerLists();
    }
}
