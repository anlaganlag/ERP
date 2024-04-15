package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.model.web.response.SuccessResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InvReportUploadRecord;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InvReportUploadRecordParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IInvReportUploadRecordService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.FileFtpConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 上传记录表 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-26
 */

@RestController
@ApiResource(name = "库存报告上传记录", path = "/invReportUploadRecord")
@Api(tags = "库存报告上传记录")
public class InvReportUploadRecordController {

  @Autowired
  private IInvReportUploadRecordService service;
  @Autowired
  private FileFtpConsumer fileFtpConsumer;
  @Value("${ftpRemotePath}")
  public String ftpRemotePath;

  @GetResource(name = "库存报告上传记录", path = "/list", menuFlag = true)
  @ApiOperation(value = "库存报告上传记录", response = InvReportUploadRecord.class)
  @BusinessLog(title = "库存报告上传记录-库存报告上传记录列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(InvReportUploadRecordParam param) {
    PageResult<InvReportUploadRecord> pageBySpec = service.findPageBySpec(param);
    return ResponseData.success(pageBySpec);
  }

  @PostResource(name = "上传fbaShipmentSales", path = "/FbaShipmentSalesUpload", requiredPermission = false)
  @ApiOperation(value = "上传fbaShipmentSales", response = InvReportUploadRecord.class)
  @BusinessLog(title = "库存报告上传记录-上传fbaShipmentSales",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData uploadfbaShipmentSales(@RequestParam("file") MultipartFile file,
      InvReportUploadRecordParam param) {
    String pt = this.ftpRemotePath  +"INVENTORY\\REPORT\\FbaShipmentSales";
    param.setFilePath(pt + "\\" + file.getOriginalFilename());
    try {
      fileFtpConsumer.uploadFile(file.getInputStream(), pt, file.getOriginalFilename());
      service.insertFbaShipmentSalesRecord(param);
      //删除本地临时文件
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new SuccessResponseData();
  }


  @PostResource(name = "上传FbaCustomerReturns", path = "/FbaCustomerReturnsUpload", requiredPermission = false)
  @ApiOperation(value = "上传FbaCustomerReturns", response = InvReportUploadRecord.class)
  @BusinessLog(title = "库存报告上传记录-上传FbaCustomerReturns",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData uploadFbaCustomerReturns(@RequestParam("file") MultipartFile file,
      InvReportUploadRecordParam param) {
    String pt = this.ftpRemotePath  +"INVENTORY\\REPORT\\FbaCustomerReturns";
    param.setFilePath(pt + "\\" + file.getOriginalFilename());
    try {
      fileFtpConsumer.uploadFile(file.getInputStream(), pt, file.getOriginalFilename());
      service.insertFbaCustomerReturnsRecord(param);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new SuccessResponseData();
  }

  @PostResource(name = "上传RemovalOrderDetail", path = "/RemovalOrderDetailUpload", requiredPermission = false)
  @ApiOperation(value = "上传RemovalOrderDetail", response = InvReportUploadRecord.class)
  @BusinessLog(title = "库存报告上传记录-上传RemovalOrderDetail",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData uploadRemovalOrderDetail(@RequestParam("file") MultipartFile file,
      InvReportUploadRecordParam param) {
    String pt = this.ftpRemotePath  +"INVENTORY\\REPORT\\RemovalOrderDetail";
    param.setFilePath( pt + "\\" + file.getOriginalFilename());
    try {
      fileFtpConsumer.uploadFile(file.getInputStream(), pt, file.getOriginalFilename());
      service.insertRemovalOrderDetailRecord(param);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new SuccessResponseData();
  }

  @PostResource(name = "上传RemoveMain", path = "/RemoveMainUpload", requiredPermission = false)
  @ApiOperation(value = "上传RemoveMain", response = InvReportUploadRecord.class)
  @BusinessLog(title = "库存报告上传记录-上传RemoveMain",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData uploadRemoveMain(@RequestParam("file") MultipartFile file,
      InvReportUploadRecordParam param) {
    String pt = this.ftpRemotePath  +"INVENTORY\\REPORT\\uploadRemoveMain";
    param.setFilePath(pt + "\\" + file.getOriginalFilename());
    try {
      fileFtpConsumer.uploadFile(file.getInputStream(), pt, file.getOriginalFilename());
      service.insertRemoveMainRecord(param);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new SuccessResponseData();
  }

  @PostResource(name = "上传FbaInventoryAdjustments", path = "/FbaInventoryAdjustmentsUpload", requiredPermission = false)
  @ApiOperation(value = "上传FbaInventoryAdjustments", response = InvReportUploadRecord.class)
  @BusinessLog(title = "库存报告上传记录-上传FbaInventoryAdjustments",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData uploadFbaInventoryAdjustments(@RequestParam("file") MultipartFile file,
      InvReportUploadRecordParam param) {
    String pt = this.ftpRemotePath  +"INVENTORY\\REPORT\\FbaInventoryAdjustments";
    param.setFilePath(pt + "\\" + file.getOriginalFilename());
    try {
      fileFtpConsumer.uploadFile(file.getInputStream(), pt, file.getOriginalFilename());
      service.insertFbaInventoryAdjustmentsRecord(param);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new SuccessResponseData();
  }

  @PostResource(name = "上传MonthlyInventoryHistory", path = "/MonthlyInventoryHistoryUpload", requiredPermission = false)
  @ApiOperation(value = "上传MonthlyInventoryHistory", response = InvReportUploadRecord.class)
  @BusinessLog(title = "库存报告上传记录-上传MonthlyInventoryHistory",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData uploadMonthlyInventoryHistory(@RequestParam("file") MultipartFile file,
      InvReportUploadRecordParam param) {
    String pt = this.ftpRemotePath  +"INVENTORY\\REPORT\\MonthlyInventoryHistory";
    param.setFilePath(pt + "\\" + file.getOriginalFilename());
    try {
      fileFtpConsumer.uploadFile(file.getInputStream(), pt, file.getOriginalFilename());
      service.insertMonthlyInventoryHistoryRecord(param);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new SuccessResponseData();
  }

  @PostResource(name = "上传RemovalShipmentDetail", path = "/RemovalShipmentDetailUpload", requiredPermission = false)
  @ApiOperation(value = "上传RemovalShipmentDetail", response = InvReportUploadRecord.class)
  @BusinessLog(title = "库存报告上传记录-上传RemovalShipmentDetail",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData uploadRemovalShipmentDetail(@RequestParam("file") MultipartFile file,
      InvReportUploadRecordParam param) {
    String pt = this.ftpRemotePath  +"INVENTORY\\REPORT\\RemovalShipmentDetail";
    param.setFilePath(pt + "\\" + file.getOriginalFilename());

    //效验文件名重复
    boolean fileValidate=service.checkFileName(file.getOriginalFilename(),param);
    if (fileValidate){
      return ResponseData.error("文件名已存在");
    }
    try {
      fileFtpConsumer.uploadFile(file.getInputStream(), pt, file.getOriginalFilename());
      service.insertRemovalShipmentDetailRecord(param);
      //删除本地临时文件
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new SuccessResponseData();
  }
}