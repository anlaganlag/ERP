package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlement;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementExportResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementPageTotalResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementResult;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 物流实际结算
 * @date: 2022/11/14
 */
public interface ILogisticsSettlementService extends IService<LogisticsSettlement> {

    /**
     * 物流实际结算查询列表
     */
    PageTotalResult<LogisticsSettlementResult, LogisticsSettlementPageTotalResult> queryListPage(LogisticsSettlementParam param);

    /**
     * 物流实际结算查询列表导出
     */
    List<LogisticsSettlementExportResult> export(LogisticsSettlementParam param);

    /**
     * 物流实际结算备注导入-预览
     * @param file
     * @return
     */
    ResponseData uploadRemark(MultipartFile file);

    /**
     * 物流实际结算备注导入-保存
     * @param params
     * @return
     */
    ResponseData uploadRemarkSave(List<LogisticsSettlementImportParam> params);


    /**
     * 同步对账数据(手动/定时)
     * @param fileDate
     * @return
     */
    ResponseData readSmbExcel(String fileDate);

    /**
     * 对账
     * @param param
     * @return
     */
    ResponseData logisticsSettlement(LogisticsSettlement param);

    /**
     * 批量对账
     * @param params
     * @return
     */
    ResponseData batchLogisticsSettlement(List<LogisticsSettlement> params);

    /**
     * 合约号下拉
     * @return
     */
    ResponseData contractNoSelect();

    /**
     * 货运方式1下拉
     * @return
     */
    ResponseData freightCompanySelect();

    /**
     * 运输方式下拉
     * @return
     */
    ResponseData transportTypeSelect();

    /**
     * 物流渠道下拉
     * @return
     */
    ResponseData logisticsChannelSelect();

    /**
     * 物流单类型下拉
     * @return
     */
    ResponseData orderTypeSelect();

    /**
     * 接收EBMS物流单数据
     * @param params
     * @return
     */
    ResponseData receiveLogisticsSettlement(List<EbmsLogisticsSettlementParam> params);

    /**
     * 初始处理Excel数据
     * @param dataMonths
     * @param dataList
     * @param billStatusMap
     */
    void dealDetailImportParam(String dataMonths, List<LogisticsSettlementDetailImportParam> dataList, Map<String, String> billStatusMap);

    /**
     * 处理明细数据
     * @param dataMonths
     * @param dataList
     * @param billStatusMap
     * @throws ParseException
     */
    void dealDetail(String dataMonths, List<LogisticsSettlementDetailImportParam> dataList, Map<String, String> billStatusMap) throws ParseException;

    /**
     * EBMS删除物流单
     * @param params
     * @return
     */
    ResponseData deleteLogisticsSettlement(List<LogisticsSettlementDetailParam> params);


    /**
     * 定时刷物流跟踪表的签收日期
     * @return
     */
    ResponseData refreshSignDate();

    /**
     * 获取EBMS物流跟踪表的签收日期
     * @param param
     * @return
     */
    List<LogisticsSettlement> getEbmsSignDate(List<LogisticsSettlement> param);
}
