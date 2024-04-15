package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportFinalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportFinalResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.SettlementReportStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementReportFinalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
* 结算报告最终 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "结算报告最终", path = "/SettlementReportFinal")
@Api(tags = "结算报告最终")
public class SettlementReportFinalController {

    @Autowired
    private ISettlementReportFinalService service;

    @PostResource(name = "结算报告最终列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "结算报告最终列表", response = SettlementReportFinalResult.class)
    @BusinessLog(title = "结算报告最终-结算报告最终列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody SettlementReportFinalParam param) {
        PageResult<SettlementReportFinalResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }




    
    @PostResource(name = "导出结算报告最终", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出结算报告最终")
    @BusinessLog(title = "结算报告最终-导出结算报告最终",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody SettlementReportFinalParam param, HttpServletResponse response) throws IOException {
        List<SettlementReportFinalResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("结算报告最终.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SettlementReportFinalResult.class).sheet("结算报告最终").doWrite(pageBySpec);
    }

//    @PostResource(name = "结算报告最终刷金额", path = "/refreshAmount", requiredPermission = false)
//    @ApiOperation(value = "结算报告最终刷金额", response = SettlementReportFinalResult.class)
//    @BusinessLog(title = "结算报告最终-结算报告最终刷金额",opType = LogAnnotionOpTypeEnum.EDIT)
//    public ResponseData refreshAmount(@RequestBody SettlementReportFinalParam param) {
//        return service.refreshAmount(param);
//    }

    @GetResource(name = "刷退货数量", path = "/refreshReturnAmount", requiredPermission = false)
    @ApiOperation(value = "刷退货数量")
    @BusinessLog(title = "结算报告最终-刷退货数量",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshReturnAmount() {
        try {
            return service.refreshReturnAmount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.success();
    }

    @GetResource(name = "确认状态下拉", path = "/status",requiredPermission = false)
    @ApiOperation(value = "确认状态下拉", response = SettlementReportFinalResult.class)
    public ResponseData status() throws Exception {
        return ResponseData.success(SettlementReportStatus.getEnumList());
    }

    @PostResource(name = "数量", path = "/getQuantity",requiredPermission = false)
    @ApiOperation(value = "数量", response = SettlementReportFinalResult.class)
    @BusinessLog(title = "结算报告最终-数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody SettlementReportFinalParam param) throws Exception {
        SettlementReportFinalResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "结算报告最终确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "结算报告最终确认", response = SettlementReportResult.class)
    @BusinessLog(title = "结算报告最终-结算报告最终确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(SettlementReportFinalParam param) throws Exception {
        service.confirm(param);
        return ResponseData.success();
    }

    @PostResource(name = "结算报告最终批量确认", path = "/confirmBatch", requiredPermission = false)
    @ApiOperation(value = "结算报告最终批量确认", response = SettlementReportFinalResult.class)
    @BusinessLog(title = "结算报告最终-结算报告批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody SettlementReportFinalParam param) throws Exception {
        ResponseData rd =service.confirmBatch(param);
        return rd;
    }


    @PostResource(name = "getFinalReport", path = "/getFinalReport", requiredPermission = false)
    @ApiOperation(value = "getFinalReport", response = SettlementReportFinalResult.class)
    @BusinessLog(title = "getFinalReport",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData getFinalReport(@RequestBody SettlementReportFinalParam param)  {
        return service.getFinalReport(param);
    }



}
