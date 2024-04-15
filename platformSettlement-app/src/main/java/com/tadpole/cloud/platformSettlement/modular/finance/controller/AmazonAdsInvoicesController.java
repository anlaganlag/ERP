package com.tadpole.cloud.platformSettlement.modular.finance.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LongTermStorageFeeCharges;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.AmazonAdsInvoicesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LongTermStorageFeeChargesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.AmazonAdsInvoicesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.VerifyExceptionEnum;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IAmazonAdsInvoicesService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
* <p>
    * Amazon广告费用账单 前端控制器
    * </p>
*
* @author S20190161
* @since 2023-07-13
*/
@RestController
@Api(tags = "AZ广告扣费明细")
@ApiResource(name = "AZ广告扣费明细", path = "/amazonAdsInvoices")
public class AmazonAdsInvoicesController {

    @Autowired
    private IAmazonAdsInvoicesService service;
    @BusinessLog(title = "AZ广告扣费明细",opType = LogAnnotionOpTypeEnum.QUERY)
    @PostResource(name = "AZ广告扣费明细", path = "/queryListPage")
    @ApiOperation(value = "AZ广告扣费明细", response = AmazonAdsInvoicesResult.class)
    public ResponseData queryListPage(@RequestBody AmazonAdsInvoicesParam param) {
        var pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }
    @BusinessLog(title = "AZ广告扣费明细-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    @PostResource(name = "导出", path = "/export")
    @ApiOperation(value = "导出")
    public ResponseData export(@RequestBody AmazonAdsInvoicesParam param, HttpServletResponse response) throws IOException {
        List<AmazonAdsInvoicesResult> list = service.export(param);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("广告费用账单.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), AmazonAdsInvoicesResult.class).sheet("广告费用账单").doWrite(list);

        return ResponseData.success();
    }

}
