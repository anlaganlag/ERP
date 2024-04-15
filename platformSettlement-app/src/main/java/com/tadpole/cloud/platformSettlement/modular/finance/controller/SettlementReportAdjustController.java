package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportExportResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.SettlementReportStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementReportAdjustService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* <p>
* 结算报告调整 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "结算报告调整", path = "/SettlementReportAdjust")
@Api(tags = "结算报告调整")
public class SettlementReportAdjustController {

    @Autowired
    private ISettlementReportAdjustService service;

    @PostResource(name = "结算报告调整列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "结算报告调整列表", response = SettlementReportAdjustResult.class)
    @BusinessLog(title = "结算报告调整-结算报告调整列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody SettlementReportAdjustParam param) {
        PageResult<SettlementReportAdjustResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }




    
    @PostResource(name = "导出结算报告调整", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出结算报告调整")
    @BusinessLog(title = "结算报告调整-导出结算报告调整",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody SettlementReportAdjustParam param, HttpServletResponse response) throws IOException {
        List<SettlementReportExportResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("结算报告调整1.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SettlementReportExportResult.class).sheet("结算报告调整").doWrite(pageBySpec);
    }

    @ParamValidator
    @PostResource(name = "结算报告调整-导入", path = "/importSettlementReportAdjust")
    @ApiOperation(value = "结算报告调整-导入")
    @BusinessLog(title = "结算报告调整-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importSettlementReportAdjust(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importSettlementReportAdjust(file);
    }

//    @PostResource(name = "结算报告调整刷金额", path = "/refreshAmount", requiredPermission = false)
//    @ApiOperation(value = "结算报告调整刷金额", response = SettlementReportAdjustResult.class)
//    @BusinessLog(title = "结算报告调整-结算报告调整刷金额",opType = LogAnnotionOpTypeEnum.EDIT)
//    public ResponseData refreshAmount(@RequestBody SettlementReportAdjustParam param) {
//        return service.refreshAmount(param);
//    }



    @GetResource(name = "确认状态下拉", path = "/status",requiredPermission = false)
    @ApiOperation(value = "确认状态下拉", response = SettlementReportAdjustResult.class)
    public ResponseData status() throws Exception {
        return ResponseData.success(SettlementReportStatus.getEnumList());
    }


    @PostResource(name = "调整报告", path = "/adjustReport",requiredPermission = false)
    @ApiOperation(value = "调整报告", response = SettlementReportAdjustResult.class)
    public ResponseData adjustReport(SettlementReportAdjustParam param) throws Exception {
        return  service.adjustReport(param);
    }


    @PostResource(name = "生成最终结算报告", path = "/getFinalReport",requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "生成最终结算报告", response = SettlementReportAdjustResult.class)
    public ResponseData getFinalReport(@RequestBody  SettlementReportAdjustParam param) throws Exception {
        return  service.getFinalReportSql(param);
    }




    @PostResource(name = "数量", path = "/getQuantity",requiredPermission = false)
    @ApiOperation(value = "数量", response = SettlementReportAdjustResult.class)
    @BusinessLog(title = "结算报告调整-数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody SettlementReportAdjustParam param) throws Exception {
        SettlementReportAdjustResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "合并结算报告调整", path = "/mergeAdjustReport",requiredPermission = false)
    @ApiOperation(value = "合并结算报告调整合并", response = SettlementReportAdjustResult.class)
    @BusinessLog(title = "合并结算报告调整合并",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData adjustReportByMerge(@RequestBody SettlementReportAdjustParam param) throws Exception {
        return service.adjustReportByMerge(param);
    }

    @PostResource(name = "调整报告维度重复", path = "/adjustRepeatDimension",requiredPermission = false)
    @ApiOperation(value = "调整报告维度重复", response = SettlementReportAdjustResult.class)
    @BusinessLog(title = "调整报告维度重复",opType = LogAnnotionOpTypeEnum.QUERY)
    public List<Map<String,String>> adjustRepeatDimension(@RequestBody SettlementReportAdjustParam param) throws Exception {
        return service.adjustRepeatDimension(param);
    }

    @PostResource(name = "填充最报告人员成本", path = "/fillPeopleCost",requiredPermission = false)
    @ApiOperation(value = "填充最报告人员成本", response = SettlementReportAdjustResult.class)
    @BusinessLog(title = "填充最报告人员成本",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData fillPeopleCost(@RequestBody SettlementReportAdjustParam param) throws Exception {
        param.setDirectRate(new BigDecimal(1));
        return service.fillPeopleCost(param);
    }

    @PostResource(name = "填充最报告人员成本NEW", path = "/fillPeopleCostNew",requiredPermission = false)
    @ApiOperation(value = "填充最报告人员成本NEW", response = SettlementReportAdjustResult.class)
    @BusinessLog(title = "填充最报告人员成本NEW",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData fillPeopleCostNew(@RequestBody SettlementReportAdjustParam param) throws Exception {
        param.setDirectRate(new BigDecimal(1));
        param.setCreateBy("test");
        param.setCreateTime(new Date());
        return service.fillPeopleCostNew(param);
    }



    @GetResource(name = "结算报告调整确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "结算报告调整确认", response = SettlementReportResult.class)
    @BusinessLog(title = "结算报告调整-结算报告调整确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(SettlementReportAdjustParam param) throws Exception {
        return service.confirm(param);
    }

    @PostResource(name = "结算报告调整批量确认", path = "/confirmBatch", requiredPermission = false)
    @ApiOperation(value = "结算报告调整批量确认", response = SettlementReportAdjustResult.class)
    @BusinessLog(title = "结算报告调整-结算报告批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody SettlementReportAdjustParam param) throws Exception {
        ResponseData rd =service.confirmBatch(param);
        return rd;
    }



}
