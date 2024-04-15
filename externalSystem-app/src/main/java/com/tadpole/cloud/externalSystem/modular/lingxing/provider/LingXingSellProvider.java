package com.tadpole.cloud.externalSystem.modular.lingxing.provider;

import com.tadpole.cloud.externalSystem.api.lingxing.LingXingSellApi;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.ListingReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingSellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 领星销售服务提供者
 * @date: 2023/6/5
 */
@RestController
public class LingXingSellProvider implements LingXingSellApi {

    @Autowired
    private LingXingSellService lingXingSellService;

    /**
     * 查询亚马逊Listing
     * @return
     */
    public LingXingBaseRespData listing(ListingReq req) throws Exception {
        return lingXingSellService.listing(req);
    }
}
