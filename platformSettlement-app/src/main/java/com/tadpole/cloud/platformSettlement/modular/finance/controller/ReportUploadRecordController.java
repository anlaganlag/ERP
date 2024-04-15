package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.model.web.response.SuccessResponseData;
import cn.stylefeng.guns.cloud.system.api.model.result.FileInfoResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ReportUploadRecordParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ReportUploadRecordResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IReportUploadRecordService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.FileConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
* <p>
* AZ报告上传记录 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "AZ报告记录上传", path = "/reportUploadRecord")
@Api(tags = "AZ报告记录上传")
public class ReportUploadRecordController {

    @Value("${ftpRemotePath}")
    public String ftpRemotePath;
    @Autowired
    private FileConsumer fileConsumer;
    @Value("${file.path}")
    public String filePath;
    @Autowired
    private IReportUploadRecordService reportUploadRecordService;

    @PostResource(name = "AZ报告记录上传", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "AZ报告记录上传", response = ReportUploadRecordResult.class)
    @BusinessLog(title = "AZ报告记录上传-AZ报告记录上传列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody ReportUploadRecordParam param) {
        PageResult<ReportUploadRecordResult> pageBySpec = reportUploadRecordService.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "上传Settlement", path = "/settlementUpload",requiredPermission = false)
    @ApiOperation(value = "上传Settlement", response = ReportUploadRecordResult.class)
    @BusinessLog(title = "AZ报告记录上传-上传Settlement",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData uploadSettlement(@RequestParam("file") MultipartFile file, ReportUploadRecordParam param) {
        String shopName = param.getShopName();
        String site = param.getSite();
        String pt = filePath + "/SETTLEMENT/" + shopName + "/" + site + "/";
        String appCode = "amazon-report";

        FileInfoResult fileInfoResult = fileConsumer.upload(appCode, pt, file);
        //新增结算单上传记录
        param.setFilePath(fileInfoResult.getPath().replaceAll("/" + appCode, ""));
        reportUploadRecordService.insertSettlementRecord(param);
        return new SuccessResponseData();
    }

    @PostResource(name = "上传DataRange", path = "/dataRangeUpload",requiredPermission = false)
    @ApiOperation(value = "上传DataRange", response = ReportUploadRecordResult.class)
    @BusinessLog(title = "AZ报告记录上传-上传DataRange",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData uploadDataRange(@RequestParam("file") MultipartFile file, ReportUploadRecordParam param) {
        String shopName = param.getShopName();
        String site = param.getSite();
        String pt = filePath + "/Data Range/" + shopName + "/" + site + "/";
        String appCode = "amazon-report";
        FileInfoResult fileInfoResult = fileConsumer.upload(appCode, pt, file);
        //新增结算单上传记录
        param.setFilePath(fileInfoResult.getPath().replaceAll("/" + appCode, ""));
        reportUploadRecordService.insertDataRangeRecord(param);
        return new SuccessResponseData();
    }
}
