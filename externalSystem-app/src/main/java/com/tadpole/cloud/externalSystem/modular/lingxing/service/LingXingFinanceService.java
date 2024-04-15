package com.tadpole.cloud.externalSystem.modular.lingxing.service;

import com.tadpole.cloud.externalSystem.api.lingxing.model.req.finance.ProfitMskuReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;

/**
 * @author: ty
 * @description: 财务Service类
 * @date: 2022/4/25
 */
public interface LingXingFinanceService {
    /**
     * 查询利润报表 - MSKU
     * @return
     */
    LingXingBaseRespData profitMsku(ProfitMskuReq req) throws Exception;
}
