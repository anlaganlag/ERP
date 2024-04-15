package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import com.tadpole.cloud.externalSystem.modular.lingxing.constants.LingXingUrlConstants;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingBaseInfoService;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingReqInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ty
 * @description: 领星基础数据service实现类
 * @date: 2022/4/22
 */
@Service
public class LingXingBaseInfoServiceImpl implements LingXingBaseInfoService {

    @Autowired
    private LingXingReqInfoUtil lingXingReqInfoUtil;

    @Override
    public LingXingBaseRespData getSellerLists() throws Exception {
        return lingXingReqInfoUtil.doGetReq(LingXingUrlConstants.SELLER_LISTS);
    }
}
