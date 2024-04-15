package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlement;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementExportResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementRecordResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.LogisticsOperationBillStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementDetailService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementRecordService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * @author: ty
 * @description: 物流实际结算
 * @date: 2022/11/14
 */
@Slf4j
@RestController
@ApiResource(name = "物流实际结算", path = "/logisticsSettlement")
@Api(tags = "物流实际结算")
@Validated
public class LogisticsSettlementController {

    @Autowired
    private ILogisticsSettlementService logisticsSettlementService;
    @Autowired
    private ILogisticsSettlementDetailService logisticsSettlementDetailService;
    @Autowired
    private ILogisticsSettlementRecordService logisticsSettlementRecordService;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 读取网盘物流实际结算Excel文件
     */
    private static final String LOGISTICS_SETTLEMENT = "LOGISTICS_SETTLEMENT";

    /**
     * 物流实际结算备注Excel文件导入
     */
    private static final String LOGISTICS_SETTLEMENT_UPLOAD = "LOGISTICS_SETTLEMENT_UPLOAD";

    /**
     * 物流实际结算查询列表
     * @param param
     * @return
     */
    @ParamValidator
    @PostResource(name = "物流实际结算", path = "/queryListPage",  menuFlag = true,requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "物流实际结算查询列表", response = LogisticsSettlementResult.class)
    @BusinessLog(title = "物流实际结算-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@Validated @RequestBody LogisticsSettlementParam param) {
        return ResponseData.success(logisticsSettlementService.queryListPage(param));
    }

    /**
     * 物流实际结算查询列表导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "物流实际结算查询列表导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "物流实际结算查询列表导出")
    @BusinessLog(title = "物流实际结算-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody LogisticsSettlementParam param, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流实际结算查询列表导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LogisticsSettlementExportResult.class).sheet("物流实际结算查询列表导出").doWrite(logisticsSettlementService.export(param));
    }

    /**
     * 查询物流实际结算明细
     * @param param
     * @return
     */
    @PostResource(name = "查询物流实际结算明细", path = "/queryDetailListPage")
    @ApiOperation(value = "查询物流实际结算明细", response = LogisticsSettlementDetailResult.class)
    @BusinessLog(title = "物流实际结算-明细查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryDetailListPage(@RequestBody LogisticsSettlementDetailParam param) {
        return ResponseData.success(logisticsSettlementDetailService.queryDetailListPage(param));
    }

    /**
     * 查询物流实际结算记录
     * @param param
     * @return
     */
    @PostResource(name = "查询物流实际结算记录", path = "/queryRecordListPage")
    @ApiOperation(value = "查询物流实际结算记录", response = LogisticsSettlementRecordResult.class)
    @BusinessLog(title = "物流实际结算-记录查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryRecordListPage(@RequestBody LogisticsSettlementRecordParam param) {
        return ResponseData.success(logisticsSettlementRecordService.queryRecordListPage(param));
    }

    /**
     * 物流实际结算备注导入模板下载
     * @param response
     */
    @GetResource(name = "物流实际结算备注导入模板下载", path = "/downloadRemarkExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("物流实际结算备注导入模板下载")
    public void downloadRemarkExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/物流实际结算备注导入模板.xlsx");
    }

    /**
     * 物流实际结算备注导入-预览
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "物流实际结算备注导入-预览", path = "/uploadRemark", requiredPermission = false)
    @ApiOperation(value = "物流实际结算备注导入-预览", response = LogisticsSettlementImportParam.class)
    @BusinessLog(title = "物流实际结算-导入-预览",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadRemark(@RequestParam(value = "file", required = true) MultipartFile file) {
        return logisticsSettlementService.uploadRemark(file);
    }

    /**
     * 物流实际结算备注导入-保存
     * @param params
     * @return
     */
    @ParamValidator
    @PostResource(name = "物流实际结算备注导入-保存", path = "/uploadRemarkSave", requiredPermission = false)
    @ApiOperation(value = "物流实际结算备注导入-保存")
    @BusinessLog(title = "物流实际结算-导入-保存",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadRemarkSave(@Validated @RequestBody List<LogisticsSettlementImportParam> params) {
        if(CollectionUtils.isEmpty(params)){
            return ResponseData.error("入参数据为空！");
        }
        if(redisTemplate.hasKey(LOGISTICS_SETTLEMENT_UPLOAD)){
            return ResponseData.error("物流实际结算备注导入保存中，请稍后再试!");
        }
        try {
            redisTemplate.boundValueOps(LOGISTICS_SETTLEMENT_UPLOAD).set("物流实际结算备注导入保存中", Duration.ofSeconds(600));
            return logisticsSettlementService.uploadRemarkSave(params);
        }catch (Exception e) {
            log.info("物流实际结算备注导入保存异常，异常信息[{}]", e);
            return ResponseData.error("物流实际结算备注导入保存异常!");
        } finally {
            redisTemplate.delete(LOGISTICS_SETTLEMENT_UPLOAD);
        }
    }

    /**
     * 同步对账数据(手动/定时)
     * @return
     */
    @GetResource(name = "同步对账数据(手动/定时)", path = "/readSmbExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "同步对账数据(手动/定时)")
    @BusinessLog(title = "物流实际结算-同步对账数据",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData readSmbExcel(@RequestParam("fileDate") String fileDate) {
        if(redisTemplate.hasKey(LOGISTICS_SETTLEMENT)){
            return ResponseData.error("同步对账数据处理中，请稍后再试!");
        }
        try {
            redisTemplate.boundValueOps(LOGISTICS_SETTLEMENT).set("同步对账数据处理中", Duration.ofSeconds(600));
            return logisticsSettlementService.readSmbExcel(fileDate);
        }catch (Exception e) {
            log.info("同步对账数据异常，异常信息[{}]", e);
            return ResponseData.error("同步对账数据处理异常!");
        } finally {
            redisTemplate.delete(LOGISTICS_SETTLEMENT);
        }
    }

    /**
     * 对账
     * @param param
     * @return
     */
    @PostResource(name = "对账", path = "/logisticsSettlement", requiredPermission = false)
    @ApiOperation(value = "对账")
    @BusinessLog(title = "物流实际结算-对账",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData logisticsSettlement(@RequestBody LogisticsSettlement param) {
        return logisticsSettlementService.logisticsSettlement(param);
    }

    /**
     * 批量对账
     * @param params
     * @return
     */
    @PostResource(name = "批量对账", path = "/batchLogisticsSettlement", requiredPermission = false)
    @ApiOperation(value = "批量对账")
    @BusinessLog(title = "物流实际结算-批量对账",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchLogisticsSettlement(@RequestBody List<LogisticsSettlement> params) {
        return logisticsSettlementService.batchLogisticsSettlement(params);
    }

    /**
     * 对账状态下拉
     * @return
     */
    @GetResource(name = "对账状态下拉", path = "/billStatusSelect")
    @ApiOperation(value = "对账状态下拉")
    public ResponseData billStatusSelect() {
        return ResponseData.success(LogisticsOperationBillStatusEnum.getBillStatus());
    }

    /**
     * 合约号下拉
     * @return
     */
    @GetResource(name = "合约号下拉", path = "/contractNoSelect")
    @ApiOperation(value = "合约号下拉")
    public ResponseData contractNoSelect() {
        return logisticsSettlementService.contractNoSelect();
    }

    /**
     * 货运方式1下拉
     * @return
     */
    @GetResource(name = "货运方式1下拉", path = "/freightCompanySelect")
    @ApiOperation(value = "货运方式1下拉")
    public ResponseData freightCompanySelect() {
        return logisticsSettlementService.freightCompanySelect();
    }

    /**
     * 运输方式下拉
     * @return
     */
    @GetResource(name = "运输方式下拉", path = "/transportTypeSelect")
    @ApiOperation(value = "运输方式下拉")
    public ResponseData transportTypeSelect() {
        return logisticsSettlementService.transportTypeSelect();
    }

    /**
     * 物流渠道下拉
     * @return
     */
    @GetResource(name = "物流渠道下拉", path = "/logisticsChannelSelect")
    @ApiOperation(value = "物流渠道下拉")
    public ResponseData logisticsChannelSelect() {
        return logisticsSettlementService.logisticsChannelSelect();
    }

    /**
     * 物流单类型下拉
     * @return
     */
    @GetResource(name = "物流单类型下拉", path = "/orderTypeSelect")
    @ApiOperation(value = "物流单类型下拉")
    public ResponseData orderTypeSelect() {
        return logisticsSettlementService.orderTypeSelect();
    }

    /**
     * 接收EBMS物流单数据
     * @param params
     * @return
     */
    @PostResource(name = "接收EBMS物流单数据", path = "/receiveLogisticsSettlement", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "接收EBMS物流单数据")
    @BusinessLog(title = "物流实际结算-接收EBMS物流单数据",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData receiveLogisticsSettlement(@Valid @RequestBody List<EbmsLogisticsSettlementParam> params) {
        log.info("接收EBMS物流单数据入参[{}]", JSONObject.toJSONString(params));
        for (EbmsLogisticsSettlementParam param : params) {
            if(!"CNY".equals(param.getCurrency())){
                FixedExchangeRateParam rateParam  = new FixedExchangeRateParam();
                rateParam.setOriginalCurrency(param.getCurrency());
                rateParam.setEffectDate(DateUtil.beginOfDay(param.getShipmentDate()));
                FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
                if(fixedExchangeRate == null){
                    return ResponseData.error("ERP固定汇率不存在！币别：" + param.getCurrency() + "，发货日期：" + param.getShipmentDate());
                }
                param.setDirectRate(fixedExchangeRate.getDirectRate());
            }
        }
        return logisticsSettlementService.receiveLogisticsSettlement(params);
    }

    /**
     * EBMS删除物流单
     * @param params
     * @return
     */
    @PostResource(name = "EBMS删除物流单", path = "/deleteLogisticsSettlement", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "EBMS删除物流单")
    @BusinessLog(title = "物流实际结算-EBMS删除物流单",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteLogisticsSettlement(@RequestBody List<LogisticsSettlementDetailParam> params) {
        log.info("接收EBMS删除物流单入参[{}]", JSONObject.toJSONString(params));
        return logisticsSettlementService.deleteLogisticsSettlement(params);
    }

    /**
     * 定时刷物流跟踪表的签收日期
     * @return
     */
    @GetResource(name = "定时刷物流跟踪表的签收日期", path = "/refreshSignDate", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "定时刷物流跟踪表的签收日期")
    @BusinessLog(title = "物流实际结算-更新签收日期",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshSignDate() {
        log.info("定时刷物流跟踪表的签收日期");
        return logisticsSettlementService.refreshSignDate();
    }
}
