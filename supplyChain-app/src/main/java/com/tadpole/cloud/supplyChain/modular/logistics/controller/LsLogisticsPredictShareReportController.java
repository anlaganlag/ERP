package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsPredictShareReport;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsPredictShareReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsPredictShareReportResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsPredictShareReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  物流投入预估分摊报表 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-12-14
 */
@RestController
@Api(tags = "物流投入预估分摊报表")
@ApiResource(name = "物流投入预估分摊报表", path = "/lsLogisticsPredictShareReport")
public class LsLogisticsPredictShareReportController {

    @Autowired
    private ILsLogisticsPredictShareReportService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "分页查询列表", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = LsLogisticsPredictShareReportResult.class)
    @BusinessLog(title = "物流投入预估分摊报表-分页查询列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody LsLogisticsPredictShareReportParam param) {
        return service.queryPage(param);
    }

    /**
     * 导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "物流投入预估分摊报表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody LsLogisticsPredictShareReportParam param, HttpServletResponse response) throws IOException {
        List<LsLogisticsPredictShareReportResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流投入预估分摊报表导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LsLogisticsPredictShareReportResult.class).sheet("物流投入预估分摊报表导出").doWrite(resultList);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "导入模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("导入模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/物流投入预估分摊报表导入模板.xlsx");
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入", path = "/import", requiredPermission = false)
    @ApiOperation(value = "导入")
    @BusinessLog(title = "物流投入预估分摊报表-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importExcel(file);
    }

    /**
     * 定时生成BTB物流投入预估分摊报表
     * @return
     */
    @PostResource(name = "定时生成BTB物流投入预估分摊报表", path = "/generateBtpReport", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "定时生成BTB物流投入预估分摊报表", response = LsLogisticsPredictShareReportResult.class)
    @BusinessLog(title = "物流投入预估分摊报表-定时生成BTB物流投入预估分摊报表",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateBtpReport() {
        return service.generateBtpReport();
    }

    /**
     * 接收EBMS物流投入预估分摊报表
     * @return
     */
    @PostResource(name = "接收EBMS物流投入预估分摊报表", path = "/generateEBMSReport", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "接收EBMS物流投入预估分摊报表", response = LsLogisticsPredictShareReportResult.class)
    public ResponseData generateEBMSReport(@RequestBody List<LsLogisticsPredictShareReport> param) {
        return service.generateEBMSReport(param);
    }

}
