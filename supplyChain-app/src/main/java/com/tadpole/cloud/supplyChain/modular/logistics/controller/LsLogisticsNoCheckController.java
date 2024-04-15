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
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckExport0Result;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckExport1Result;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsNoCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 物流单对账 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
@Slf4j
@RestController
@Api(tags = "物流单对账")
@ApiResource(name = "物流单对账", path = "/lsLogisticsNoCheck")
public class LsLogisticsNoCheckController {

    @Autowired
    private ILsLogisticsNoCheckService service;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "物流单对账", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = LsLogisticsNoCheckResult.class)
    @BusinessLog(title = "物流单对账-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody LsLogisticsNoCheckParam param) {
        return service.queryPage(param);
    }

    /**
     * 分页查询列表合计
     * @param param
     * @return
     */
    @PostResource(name = "分页查询列表合计", path = "/queryPageTotal", menuFlag = true)
    @ApiOperation(value = "分页查询列表合计", response = LsLogisticsNoCheckResult.class)
    @BusinessLog(title = "物流单对账-分页查询列表合计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPageTotal(@RequestBody LsLogisticsNoCheckParam param) {
        return service.queryPageTotal(param);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "导入模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("导入模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/物流单对账信息导入模板.xlsx");
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入", path = "/import", requiredPermission = false)
    @ApiOperation(value = "导入")
    @BusinessLog(title = "物流单对账-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importExcel(file);
    }

    /**
     * 批量锁定/解锁
     * @param param
     * @return
     */
    @PostResource(name = "批量锁定/解锁", path = "/batchLock")
    @ApiOperation(value = "批量锁定/解锁")
    @BusinessLog(title = "物流单对账-批量锁定/解锁",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchLock(@RequestBody LsLogisticsNoCheckLockParam param) {
        return service.batchLock(param);
    }

    /**
     * 批量对账完成
     * @param param
     * @return
     */
    @PostResource(name = "批量对账完成", path = "/batchCheck")
    @ApiOperation(value = "批量对账完成")
    @BusinessLog(title = "物流单对账-批量对账完成",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchCheck(@RequestBody LsLogisticsNoCheckLockParam param) {
        return service.batchCheck(param);
    }

    /**
     * 导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "物流单对账-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody LsLogisticsNoCheckParam param, HttpServletResponse response) throws IOException {
        ExcelWriter excelWriter = null;
        try {
            List<LsLogisticsNoCheckExport0Result> result = service.export(param);
            List<LsLogisticsNoCheckExport1Result> detailResult = service.exportDetail(param);
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流单对账导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheet0 = EasyExcel.writerSheet(0, "物流单预估费用").head(LsLogisticsNoCheckExport0Result.class).build();
            excelWriter.write(result, writeSheet0);

            WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "物流单实际费用").head(LsLogisticsNoCheckExport1Result.class).build();
            excelWriter.write(detailResult, writeSheet1);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(excelWriter != null){
                excelWriter.finish();
            }
        }
    }

    /**
     * 编辑保存
     * @param param
     * @return
     */
    @PostResource(name = "编辑保存", path = "/edit")
    @ApiOperation(value = "编辑保存")
    @BusinessLog(title = "物流单对账-编辑保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody LsLogisticsNoCheckDetailParam param) {
        return service.edit(param);
    }

    /**
     * 生成物流费
     * @param param
     * @return
     */
    @PostResource(name = "生成物流费", path = "/generateLogisticsFee")
    @ApiOperation(value = "生成物流费")
    @BusinessLog(title = "物流单对账-生成物流费",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData generateLogisticsFee(@RequestBody List<LsLogisticsNoCheckDetailParam> param) {
        return service.generateLogisticsFee(param);
    }

    /**
     * 接收EBMS物流单数据
     * @param params
     * @return
     */
    @PostResource(name = "接收EBMS物流单数据", path = "/receiveLogisticsCheck", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "接收EBMS物流单数据")
    @BusinessLog(title = "物流实际结算-接收EBMS物流单数据",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData receiveLogisticsCheck(@Valid @RequestBody List<EbmsLogisticsCheckParam> params) {
        log.info("接收EBMS物流单数据入参[{}]", JSONObject.toJSONString(params));
        for (EbmsLogisticsCheckParam param : params) {
            if(!"CNY".equals(param.getLogisticsCurrency())){
                FixedExchangeRateParam rateParam  = new FixedExchangeRateParam();
                rateParam.setOriginalCurrency(param.getLogisticsCurrency());
                rateParam.setEffectDate(DateUtil.beginOfDay(param.getShipmentDate()));
                FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);
                if(fixedExchangeRate == null){
                    return ResponseData.error("ERP固定汇率不存在！币别：" + param.getLogisticsCurrency() + "，发货日期：" + param.getShipmentDate());
                }
                param.setDirectRate(fixedExchangeRate.getDirectRate());
            }
        }
        return service.receiveLogisticsCheck(params);
    }

    /**
     * EBMS删除物流单
     * @param params
     * @return
     */
    @PostResource(name = "EBMS删除物流单", path = "/deleteEbmsCheck", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "EBMS删除物流单")
    @BusinessLog(title = "物流实际结算-EBMS删除物流单",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteEbmsCheck(@RequestBody List<LogisticsSettlementDetailParam> params) {
        return service.deleteEbmsCheck(params);
    }

    /**
     * 定时获取EBMS物流跟踪表更新物流对账签收日期
     * @return
     */
    @GetResource(name = "定时获取EBMS物流跟踪表更新物流对账签收日期", path = "/refreshSignDate", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "定时获取EBMS物流跟踪表更新物流对账签收日期")
    @BusinessLog(title = "物流单对账-定时获取EBMS物流跟踪表更新物流对账签收日期",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshSignDate() {
        return service.refreshSignDate();
    }

    /**
     * 物流对账单号校验
     * @param param
     * @return
     */
    @PostResource(name = "物流对账单号校验", path = "/validateCheckOrder")
    @ApiOperation(value = "物流对账单号校验")
    @BusinessLog(title = "物流单对账-物流对账单号校验",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData validateCheckOrder(@RequestBody LsLogisticsNoCheckDetailParam param) {
        return service.validateCheckOrder(param);
    }

}
