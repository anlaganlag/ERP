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
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesReturn;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.SalesReturnParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.SalesReturnResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.ISalesReturnService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.ApacheEmailConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * 销售退货列表 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
@Slf4j
@RestController
@ApiResource(name = "销售退货列表", path = "/salesReturn")
@Api(tags = "销售退货列表")
public class SalesReturnController {

  @Autowired
  private ISalesReturnService service;
  @Autowired
  private ApacheEmailConsumer apacheEmailConsumer;

  @PostResource(name = "销售退货列表", path = "/list", menuFlag = true)
  @ApiOperation(value = "销售退货列表", response = SalesReturn.class)
  @BusinessLog(title = "销售退货列表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody SalesReturnParam param) {
    return ResponseData.success(service.findPageBySpec(param));
  }

  @PostResource(name = "获取数量", path = "/getQuantity")
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "销售退货列表-获取数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public String getQuantity(@RequestBody SalesReturnParam param) {
    return service.getQuantity(param);
  }

  @PostResource(name = "销售退货列表导出", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "销售退货列表导出")
  @BusinessLog(title = "销售退货列表-销售退货列表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody SalesReturnParam param, HttpServletResponse response) throws IOException {
    List<SalesReturnResult> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition","attachment;filename=" + new String("销售退货列表导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), SalesReturnResult.class).sheet("销售退货列表导出").doWrite(resultList);
  }

  @GetResource(name = "作废", path = "/reject", requiredPermission = false)
  @ApiOperation(value = "作废")
  @BusinessLog(title = "销售退货列表-作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData reject(SalesReturnParam param) {
    return service.reject(param);
  }

  @PostResource(name = "批量作废", path = "/rejectBatch", requiredPermission = false)
  @ApiOperation(value = "批量作废")
  @BusinessLog(title = "销售退货列表-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData rejectBatch(@RequestBody List<SalesReturnParam> params) {
    return service.rejectBatch(params);
  }

  @GetResource(name = "退货单明细", path = "/outDetail", requiredPermission = false)
  @ApiOperation(value = "退货单明细")
  @BusinessLog(title = "销售退货列表-退货单明细查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData outDetail(SalesReturnParam param) throws IOException {
    return ResponseData.success(service.getSyncList(param));
  }

  @PostResource(name = "同步erp", path = "/syncErp", requiredPermission = false)
  @ApiOperation(value = "同步erp")
  @BusinessLog(title = "销售退货列表-同步erp",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData syncErp(@RequestBody SalesReturnParam param) throws IOException {
    service.syncErp(param);
    return ResponseData.success();
  }

  @GetResource(name = "刷listing", path = "/refreshListing", requiredPermission = false)
  @ApiOperation(value = "刷listing")
  @BusinessLog(title = "销售退货列表-刷listing",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData refreshListing() throws IOException {
    try {
      //销售退货列表明细刷listing
      service.updateDetailList();
      //销售退货列表明细刷存档listing
      service.updateFileDetailList();
    } catch (Exception e){
      e.printStackTrace();
    }
    return ResponseData.success();
  }

  @GetResource(name = "定时同步", path = "/timerSync", requiredPermission = false)
  @ApiOperation(value = "定时同步")
  @BusinessLog(title = "销售退货列表-定时同步",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData timerSync() throws IOException {
    //销售退货列表明细刷listing
    service.updateDetailList();
    //销售退货列表明细刷存档listing
    service.updateFileDetailList();
    //更新单据主表是否可以同步状态：刷新不可以同步：0（不可调换顺序）
    service.updateSyncStatus();
    //更新单据主表是否可以同步状态：刷新可以同步：1（不可调换顺序）
    service.updateCanSyncNormal();
    //执行定时同步推送ERP数据
    service.timerSync();
    return ResponseData.success();
  }

  @PostResource(name = "修改物料", path = "/editMat", requiredPermission = false)
  @ApiOperation(value = "修改物料")
  @BusinessLog(title = "销售退货列表-修改物料",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editMat(@RequestBody SalesReturnParam param) {
    return service.editMat(param);
  }

  @PostResource(name = "发邮件", path = "/sendEmail", requiredPermission = false)
  @ApiOperation(value = "发邮件")
  @BusinessLog(title = "销售退货列表-发邮件",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData sendEmail(@RequestBody  SalesReturnParam param)  {
    try {
      List<SalesReturnResult> resultList = service.emailList();
      if (CollUtil.isEmpty(resultList)) {
        return ResponseData.error("无缺少sku");
      }
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
      EasyExcel.write(outputStream, SalesReturnResult.class).sheet("销售退货列表缺失listings导出").doWrite(resultList);
      String[] sendToEmailArr =  param.getEmailList().toArray(new String[param.getEmailList().size()]);
      List<ApacheEmailAttachments> attachmentsList = new ArrayList<>();
      ApacheEmailAttachments attachments = new ApacheEmailAttachments();
      String subject = "销售退货列表缺失listings";
      String msg = "rt";
      attachments.setBytes(outputStream.toByteArray());
      attachments.setFileName("销售退货列表缺失listings导出.xlsx");
      attachmentsList.add(attachments);
      apacheEmailConsumer.sendEmailsWithOnlineAttachments(subject, msg, sendToEmailArr, attachmentsList);
    } catch (Exception e){
      e.printStackTrace();
    }
    return ResponseData.success();
  }

  @PostResource(name = "无需处理", path = "/disable", requiredPermission = false)
  @ApiOperation(value = "无需处理")
  @BusinessLog(title = "销售退货列表-无需处理",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData disable(@RequestBody SalesReturnParam param) {
    return service.disable(param);
  }
}
