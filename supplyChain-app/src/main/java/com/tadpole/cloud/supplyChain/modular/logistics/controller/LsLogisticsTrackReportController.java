package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsTrackReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsTrackReportResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbLogisticsNoService;
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
 *  物流跟踪报表前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-12-13
 */
@RestController
@Api(tags = "物流跟踪报表")
@ApiResource(name = "物流跟踪报表", path = "/lsLogisticsTrackReport")
public class LsLogisticsTrackReportController {

    @Autowired
    private ILsBtbLogisticsNoService service;

    /**
     * BTB物流跟踪报表分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "物流跟踪报表", path = "/queryBtbPage", menuFlag = true)
    @ApiOperation(value = "BTB分页查询列表", response = LsLogisticsTrackReportResult.class)
    @BusinessLog(title = "物流跟踪报表-BTB分页查询列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryBtbPage(@RequestBody LsLogisticsTrackReportParam param) {
        return service.queryBtbPage(param);
    }

    /**
     * EBMS物流跟踪报表分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "EBMS物流跟踪报表分页查询列表", path = "/queryEbmsPage")
    @ApiOperation(value = "EBMS分页查询列表", response = LsLogisticsTrackReportResult.class)
    @BusinessLog(title = "物流跟踪报表-EBMS分页查询列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryEbmsPage(@RequestBody LsLogisticsTrackReportParam param) {
        return service.queryEbmsPage(param);
    }

    /**
     * 物流跟踪报表导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "物流跟踪报表导出", path = "/exportLogisticsTrack", requiredPermission = false)
    @ApiOperation(value = "物流跟踪报表导出")
    @BusinessLog(title = "物流跟踪报表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportLogisticsTrack(@RequestBody LsLogisticsTrackReportParam param, HttpServletResponse response) throws IOException {
        List<LsLogisticsTrackReportResult> resultList = service.exportLogisticsTrack(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流跟踪报表导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LsLogisticsTrackReportResult.class).sheet("物流跟踪报表导出").doWrite(resultList);
    }

    /**
     * 发货仓下拉
     * @return
     */
    @GetResource(name = "发货仓下拉", path = "/shipmentWarehouseSelect")
    @ApiOperation(value = "发货仓下拉", response = BaseSelectResult.class)
    public ResponseData shipmentWarehouseSelect() {
        return service.shipmentWarehouseSelect();
    }
}
