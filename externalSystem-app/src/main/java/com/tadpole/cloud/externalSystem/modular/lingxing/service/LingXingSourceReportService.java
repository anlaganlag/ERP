package com.tadpole.cloud.externalSystem.modular.lingxing.service;

import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.*;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;

/**
 * @author: ty
 * @description: 亚马逊源表数据Service接口类
 * @date: 2022/4/24
 */
public interface LingXingSourceReportService {

    /**
     * 查询亚马逊源报表-所有订单
     * @return
     */
    LingXingBaseRespData allOrders(AllOrdersReq req) throws Exception;

    /**
     * 查询查询亚马逊源表-Settlement文件列表
     * @return
     */
    LingXingBaseRespData settlementFileList(SettlementFileListReq req) throws Exception;

    /**
     * 查询亚马逊源表——Settlement源文件下载
     * @return
     */
    LingXingBaseRespData getSettlementFile(SettlementFileReq req) throws Exception;

    /**
     * 查询亚马逊源报表-退货订单
     * @return
     */
    LingXingBaseRespData refundOrders(RefundOrdersReq req) throws Exception;

    /**
     * 查询亚马逊源报表-交易明细
     * @return
     */
    LingXingBaseRespData transaction(TransactionReq req) throws Exception;
}
