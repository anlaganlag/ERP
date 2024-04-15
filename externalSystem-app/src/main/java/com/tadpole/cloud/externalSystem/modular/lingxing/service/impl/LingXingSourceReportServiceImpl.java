package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import com.tadpole.cloud.externalSystem.modular.lingxing.constants.LingXingUrlConstants;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.*;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingSourceReportService;
import com.tadpole.cloud.externalSystem.modular.lingxing.utils.LingXingReqInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ty
 * @description: 亚马逊源表数据Service实现类
 * @date: 2022/4/24
 */
@Service
public class LingXingSourceReportServiceImpl implements LingXingSourceReportService {

    @Autowired
    private LingXingReqInfoUtil lingXingReqInfoUtil;

    @Override
    public LingXingBaseRespData allOrders(AllOrdersReq req) throws Exception {
        return lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.ALL_ORDERS);
    }

    @Override
    public LingXingBaseRespData settlementFileList(SettlementFileListReq req) throws Exception {
        return lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.SETTLEMENT_FILE_LIST);
    }

    @Override
    public LingXingBaseRespData getSettlementFile(SettlementFileReq req) throws Exception {
        return lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.GET_SETTLEMENT_FILE);
    }

    @Override
    public LingXingBaseRespData refundOrders(RefundOrdersReq req) throws Exception {
        return lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.REFUND_ORDERS);
    }

    @Override
    public LingXingBaseRespData transaction(TransactionReq req) throws Exception {
        return lingXingReqInfoUtil.doPostReq(req, LingXingUrlConstants.TRANSACTION);
    }
}
