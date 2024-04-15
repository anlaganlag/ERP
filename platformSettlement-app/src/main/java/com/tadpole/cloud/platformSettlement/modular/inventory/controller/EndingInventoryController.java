package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.ApacheEmailAttachments;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EndingInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EndingInventoryParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.EndingInventoryResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IEndingInventoryService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.ApacheEmailConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 期末库存列表 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
@RestController
@ApiResource(name = "期末库存列表", path = "/endingInventory")
@Api(tags = "期末库存列表")
public class EndingInventoryController {

  @Autowired
  private IEndingInventoryService service;
  @Autowired
  private ApacheEmailConsumer apacheEmailConsumer;

  @PostResource(name = "期末库存列表", path = "/list", menuFlag = true)
  @ApiOperation(value = "期末库存列表", response = EndingInventory.class)
  @BusinessLog(title = "期末库存列表-期末库存列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody EndingInventoryParam param) { return ResponseData.success(service.findPageBySpec(param));
  }

  @PostResource(name = "获取数量", path = "/getQuantity")
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "期末库存列表-获取数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public String getQuantity(@RequestBody EndingInventoryParam param) {
    return service.getQuantity(param);
  }

  @PostResource(name = "期末库存列表导出", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "期末库存列表导出")
  @BusinessLog(title = "期末库存列表-期末库存列表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody EndingInventoryParam param, HttpServletResponse response)
      throws IOException {
    List<EndingInventoryResult> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition",
        "attachment;filename=" + new String("期末库存列表导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), EndingInventoryResult.class).sheet("期末库存列表导出").doWrite(resultList);
  }

  @GetResource(name = "作废", path = "/reject", requiredPermission = false)
  @ApiOperation(value = "作废")
  @BusinessLog(title = "期末库存列表-作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData reject(EndingInventoryParam param) {
    service.reject(param);
    return ResponseData.success();
  }

  @PostResource(name = "批量作废", path = "/rejectBatch", requiredPermission = false)
  @ApiOperation(value = "批量作废")
  @BusinessLog(title = "期末库存列表-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData rejectBatch(@RequestBody List<EndingInventoryParam> params) {
    service.rejectBatch(params);
    return ResponseData.success();
  }

  @GetResource(name = "同步erp", path = "/syncErp", requiredPermission = false)
  @ApiOperation(value = "同步erp")
  @BusinessLog(title = "期末库存列表-同步erp",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData syncErp(EndingInventoryParam param){
    return ResponseData.success(service.syncErp(param));
  }

  @GetResource(name = "刷listing", path = "/refreshListing", requiredPermission = false)
  @ApiOperation(value = "刷listing")
  @BusinessLog(title = "期末库存列表-刷listing",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData refreshListing(){
    try {
      service.updateDetailList();
      service.updateFileDetailList();
    } catch (Exception e){
      e.printStackTrace();
    }
    return ResponseData.success();
  }

//  @GetResource(name = "定时同步", path = "/timerSync", requiredPermission = false)
//  @ApiOperation(value = "定时同步")
//  public ResponseData timerSync() throws  IOException, ParseException {
//    service.updateDetailList();
//    service.updateFileDetailList();
//    service.updateSyncStatus();
//    service.timerSync();
//    return ResponseData.success();
//  }

  @PostResource(name = "发邮件", path = "/sendEmail", requiredPermission = false)
  @ApiOperation(value = "发邮件")
  @BusinessLog(title = "期末库存列表-发邮件",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData sendEmail(@RequestBody EndingInventoryParam param)  {
    try {
      List<EndingInventoryResult> resultList = service.emailList();
      if (CollUtil.isEmpty(resultList)) {
        return ResponseData.error("无缺少sku");
      }
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
      EasyExcel.write(outputStream, EndingInventoryResult.class).sheet("期末库存列表缺失listings导出")
          .doWrite(resultList);
      String[] sendToEmailArr =  param.getEmailList().toArray(new String[param.getEmailList().size()]);
      List<ApacheEmailAttachments> attachmentsList = new ArrayList<>();
      ApacheEmailAttachments attachments = new ApacheEmailAttachments();
      String subject = "期末库存列表缺失listings";
      String msg = "rt";
      attachments.setBytes(outputStream.toByteArray());
      attachments.setFileName("期末库存列表缺失listings导出.xlsx");
      attachmentsList.add(attachments);
      apacheEmailConsumer.sendEmailsWithOnlineAttachments(subject, msg, sendToEmailArr, attachmentsList);
    } catch (Exception e){
      e.printStackTrace();
    }
    return ResponseData.success();
  }

  @PostResource(name = "修改物料", path = "/editMat", requiredPermission = false)
  @ApiOperation(value = "修改物料")
  @BusinessLog(title = "期末库存列表-修改物料",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editMat(@RequestBody EndingInventoryParam param) {
    return service.editMat(param);
  }
}
