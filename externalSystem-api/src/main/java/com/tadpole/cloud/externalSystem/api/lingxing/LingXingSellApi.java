package com.tadpole.cloud.externalSystem.api.lingxing;

import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.ListingReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: ty
 * @description: 领星销售API
 * @date: 2023/6/5
 */
@RequestMapping("/lingXingSellApi")
public interface LingXingSellApi {

    /**
     * 查询亚马逊Listing
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listing", method = RequestMethod.POST)
    LingXingBaseRespData listing(@RequestBody ListingReq req) throws Exception;
}
