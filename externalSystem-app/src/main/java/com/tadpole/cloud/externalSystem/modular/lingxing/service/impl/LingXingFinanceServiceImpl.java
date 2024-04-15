package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import com.tadpole.cloud.externalSystem.modular.lingxing.constants.LingXingUrlConstants;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.finance.ProfitMskuReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingFinanceService;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingReqInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ty
 * @description: 财务Service实现类
 * @date: 2022/4/25
 */
@Service
public class LingXingFinanceServiceImpl implements LingXingFinanceService {

    @Autowired
    private LingXingReqInfoUtil lingXingReqInfoUtil;

    @Override
    public LingXingBaseRespData profitMsku(ProfitMskuReq req) throws Exception {
        return lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.PROFIT_MSKU);
    }
}
