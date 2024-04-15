package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.ApacheEmailAttachments;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustReduce;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InventoryAdjustReduceParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.InventoryAdjustReduceResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.FbaInventoryAdjustmentsMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IInventoryAdjustReduceService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.ApacheEmailConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * AZ库存调整列表(减少) 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */

@RestController
@ApiResource(name = "库存调整减少列表", path = "/inventoryAdjustReduce")
@Api(tags = "库存调整减少列表")
public class InventoryAdjustReduceController {

  @Autowired
  private IInventoryAdjustReduceService service;
  @Resource
  private FbaInventoryAdjustmentsMapper adjustmentsMapper;
  @Autowired
  private ApacheEmailConsumer apacheEmailConsumer;

  @PostResource(name = "库存调整减少列表", path = "/list", menuFlag = true)
  @ApiOperation(value = "库存调整减少列表", response = InventoryAdjustReduce.class)
  @BusinessLog(title = "库存调整减少列表-库存调整减少列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody InventoryAdjustReduceParam param) {
    PageResult<InventoryAdjustReduceResult> pageBySpec = service.findPageBySpec(param);
    return ResponseData.success(pageBySpec);
  }

  @PostResource(name = "获取数量", path = "/getQuantity")
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "库存调整减少列表-获取数量汇总查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public String getQuantity(@RequestBody InventoryAdjustReduceParam param) {
    return service.getQuantity(param);
  }

  @PostResource(name = "库存调整减少列表导出", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "库存调整减少列表导出")
  @BusinessLog(title = "库存调整减少列表-库存调整减少列表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody InventoryAdjustReduceParam param, HttpServletResponse response) throws IOException {
    List<InventoryAdjustReduceResult> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition", "attachment;filename=" + new String("库存调整减少列表导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), InventoryAdjustReduceResult.class).sheet("库存调整减少列表导出").doWrite(resultList);
  }

  @GetResource(name = "作废", path = "/reject", requiredPermission = false)
  @ApiOperation(value = "作废")
  @BusinessLog(title = "库存调整减少列表-作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData reject(InventoryAdjustReduceParam param) {
    service.reject(param);
    return ResponseData.success();
  }

  @PostResource(name = "批量作废", path = "/rejectBatch", requiredPermission = false)
  @ApiOperation(value = "批量作废")
  @BusinessLog(title = "库存调整减少列表-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData rejectBatch(@RequestBody List<InventoryAdjustReduceParam> params) {
    service.rejectBatch(params);
    return ResponseData.success();
  }

  @GetResource(name = "刷listing", path = "/refreshListing", requiredPermission = false)
  @ApiOperation(value = "刷listing")
  @BusinessLog(title = "库存调整减少列表-刷listing",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData refreshListing() throws IOException {
    try {
      service.updateReduceDetailList();
      service.updateFileReduceDetailList();
    } catch (Exception e){
      e.printStackTrace();
    }
    return ResponseData.success();
  }

  @GetResource(name = "定时同步", path = "/timerSync", requiredPermission = false)
  @ApiOperation(value = "定时同步")
  @BusinessLog(title = "库存调整减少列表-定时同步",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData timerSync() throws IOException {
    service.updateReduceDetailList();
    service.updateFileReduceDetailList();
    service.updateCanSyncNormal();
    service.updateSyncStatus();
    service.timerSync();
    return ResponseData.success();
  }

  @PostResource(name = "发邮件", path = "/sendEmail", requiredPermission = false)
  @ApiOperation(value = "发邮件")
  @BusinessLog(title = "库存调整减少列表-发邮件",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData sendEmail(@RequestBody InventoryAdjustReduceParam param)  {
    try {
      List<InventoryAdjustReduceResult> resultList = service.emailList();
      if (CollUtil.isEmpty(resultList)) {
        return ResponseData.error("无缺少sku");
      }
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
      EasyExcel.write(outputStream, InventoryAdjustReduceResult.class).sheet("库存调整减少列表缺失listings导出")
          .doWrite(resultList);
      String[] sendToEmailArr =  param.getEmailList().toArray(new String[param.getEmailList().size()]);
      List<ApacheEmailAttachments> attachmentsList = new ArrayList<>();
      ApacheEmailAttachments attachments = new ApacheEmailAttachments();
      String subject = "库存调整减少列表缺失listings";
      String msg = "rt";
      attachments.setBytes(outputStream.toByteArray());
      attachments.setFileName("库存调整减少列表缺失listings导出.xlsx");
      attachmentsList.add(attachments);
      apacheEmailConsumer.sendEmailsWithOnlineAttachments(subject, msg, sendToEmailArr, attachmentsList);
    } catch (Exception e){
      e.printStackTrace();
    }
    return ResponseData.success();
  }

  @GetResource(name = "同步erp", path = "/syncErp", requiredPermission = false)
  @ApiOperation(value = "同步erp")
  @BusinessLog(title = "库存调整减少列表-同步erp",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData syncErp(InventoryAdjustReduceParam param) throws IOException {
    service.syncErp(param);
    return ResponseData.success();
  }

  @GetResource(name = "调整明细", path = "/outDetail", requiredPermission = false)
  @ApiOperation(value = "调整明细")
  @BusinessLog(title = "库存调整减少列表-调整明细",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData addDetail(InventoryAdjustReduceParam param) throws IOException {
    return ResponseData.success(service.getSyncList(param));
  }

  @PostResource(name = "修改物料", path = "/editMat", requiredPermission = false)
  @ApiOperation(value = "修改物料")
  @BusinessLog(title = "库存调整减少列表-修改物料",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editMat(@RequestBody InventoryAdjustReduceParam param) {
    return service.editMat(param);
  }
}