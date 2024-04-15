package com.tadpole.cloud.externalSystem.modular.lingxing.provider;

import com.tadpole.cloud.externalSystem.api.lingxing.LingXingSourceReportApi;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.RefundOrdersReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.SettlementFileListReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.SettlementFileReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.TransactionReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingSourceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 领星亚马逊源表数据服务提供者
 * @date: 2023/4/17
 */
@RestController
public class LingXingSourceReportProvider implements LingXingSourceReportApi {

    @Autowired
    private LingXingSourceReportService lingXingSourceReportService;

    @Override
    public LingXingBaseRespData settlementFileList(SettlementFileListReq req) throws Exception {
        return lingXingSourceReportService.settlementFileList(req);
    }

    @Override
    public LingXingBaseRespData getSettlementFile(SettlementFileReq req) throws Exception {
        return lingXingSourceReportService.getSettlementFile(req);
    }

    @Override
    public LingXingBaseRespData transaction(TransactionReq req) throws Exception {
        return lingXingSourceReportService.transaction(req);
    }

    @Override
    public LingXingBaseRespData refundOrders(RefundOrdersReq req) throws Exception {
        return lingXingSourceReportService.refundOrders(req);
    }
}
