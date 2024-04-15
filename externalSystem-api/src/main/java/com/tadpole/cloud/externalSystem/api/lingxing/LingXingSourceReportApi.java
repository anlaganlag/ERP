package com.tadpole.cloud.externalSystem.api.lingxing;

import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.RefundOrdersReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.SettlementFileListReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.SettlementFileReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.TransactionReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: ty
 * @description: 领星亚马逊源表数据API
 * @date: 2023/4/17
 */
@RequestMapping("/lingXingSourceReportApi")
public interface LingXingSourceReportApi {

    /**
     * 查询查询亚马逊源表-Settlement文件列表
     * @return
     */
    @RequestMapping(value = "/settlementFileList", method = RequestMethod.POST)
    LingXingBaseRespData settlementFileList(@RequestBody SettlementFileListReq req) throws Exception;

    /**
     * 查询亚马逊源表——Settlement源文件下载
     * @return
     */
    @RequestMapping(value = "/getSettlementFile", method = RequestMethod.POST)
    LingXingBaseRespData getSettlementFile(@RequestBody SettlementFileReq req) throws Exception;

    /**
     * 查询亚马逊源报表-交易明细
     * @return
     */
    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    LingXingBaseRespData transaction(@RequestBody TransactionReq req) throws Exception;

    /**
     * 查询亚马逊源报表-退货订单
     * @return
     */
    @RequestMapping(value = "/refundOrders", method = RequestMethod.POST)
    LingXingBaseRespData refundOrders(@RequestBody RefundOrdersReq req) throws Exception;
}
