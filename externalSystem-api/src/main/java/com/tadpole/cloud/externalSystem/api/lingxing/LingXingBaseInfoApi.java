package com.tadpole.cloud.externalSystem.api.lingxing;

import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: ty
 * @description: 领星基础数据API
 * @date: 2023/4/17
 */
@RequestMapping("/lingXingBaseInfoApi")
public interface LingXingBaseInfoApi {

    /**
     * 查询亚马逊店铺信息
     * @return
     */
    @RequestMapping(value = "/getSellerLists", method = RequestMethod.POST)
    LingXingBaseRespData getSellerLists() throws Exception;
}
