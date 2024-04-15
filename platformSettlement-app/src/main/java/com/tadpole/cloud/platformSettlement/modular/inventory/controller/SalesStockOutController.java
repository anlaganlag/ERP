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
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.SalesStockOutParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.SalesStockOutResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.ISalesStockOutService;
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
 * 销售出库列表 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
@RestController
@ApiResource(name = "销售出库列表", path = "/salesStockOut")
@Api(tags = "销售出库列表")
public class SalesStockOutController {

  @Autowired
  private ISalesStockOutService service;
  @Autowired
  private ApacheEmailConsumer apacheEmailConsumer;

  @PostResource(name = "销售出库列表", path = "/list", menuFlag = true)
  @ApiOperation(value = "销售出库列表", response = SalesStockOutResult.class)
  @BusinessLog(title = "销售出库列表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody SalesStockOutParam param) {
    return ResponseData.success(service.findPageBySpec(param));
  }

  @PostResource(name = "获取数量", path = "/getQuantity")
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "销售出库列表-获取数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public String getQuantity(@RequestBody SalesStockOutParam param) {
    return service.getQuantity(param);
  }

  @PostResource(name = "销售出库列表导出", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "销售出库列表导出")
  @BusinessLog(title = "销售出库列表-销售出库列表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody SalesStockOutParam param, HttpServletResponse response) throws IOException {
    List<SalesStockOutResult> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition", "attachment;filename=" + new String("销售出库列表导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), SalesStockOutResult.class).sheet("销售出库列表导出").doWrite(resultList);
  }

  @GetResource(name = "作废", path = "/reject", requiredPermission = false)
  @ApiOperation(value = "作废")
  @BusinessLog(title = "销售出库列表-作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData reject(SalesStockOutParam param) {
    return service.reject(param);
  }

  @PostResource(name = "批量作废", path = "/rejectBatch", requiredPermission = false)
  @ApiOperation(value = "批量作废")
  @BusinessLog(title = "销售出库列表-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData rejectBatch(@RequestBody List<SalesStockOutParam> params) {
    return service.rejectBatch(params);
  }

  @GetResource(name = "同步erp", path = "/syncErp", requiredPermission = false)
  @ApiOperation(value = "同步erp")
  @BusinessLog(title = "销售出库列表-同步erp",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData syncErp(SalesStockOutParam param) throws IOException {
    service.syncErp(param);
    return ResponseData.success();
  }

  @GetResource(name = "出库单明细", path = "/outDetail", requiredPermission = false)
  @ApiOperation(value = "出库单明细")
  @BusinessLog(title = "销售出库列表-出库单明细",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData outDetail(SalesStockOutParam param) throws IOException {
    return ResponseData.success(service.getSyncList(param));
  }

  @GetResource(name = "刷listing", path = "/refreshListing", requiredPermission = false)
  @ApiOperation(value = "刷listing")
  @BusinessLog(title = "销售出库列表-刷listing",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData refreshListing() {
    //销售出库列表明细刷listing
    service.updateDetailList();
    //销售出库列表明细刷存档listing
    service.updateFileDetailList();
    return ResponseData.success();
  }

  @GetResource(name = "定时同步", path = "/timerSync", requiredPermission = false)
  @ApiOperation(value = "定时同步")
  @BusinessLog(title = "销售出库列表-定时同步",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData timerSync() throws IOException {
    //销售出库列表明细刷listing
    service.updateDetailList();
    //销售出库列表明细刷存档listing
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
  @BusinessLog(title = "销售出库列表-修改物料",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editMat(@RequestBody SalesStockOutParam param) {
    return service.editMat(param);
  }

  @PostResource(name = "发邮件", path = "/sendEmail", requiredPermission = false)
  @ApiOperation(value = "发邮件")
  @BusinessLog(title = "销售出库列表-发邮件",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData sendEmail(@RequestBody SalesStockOutParam param) {
    try {
      List<SalesStockOutResult> resultList = service.emailList();
      if (CollUtil.isEmpty(resultList)) {
        return ResponseData.error("无缺少sku");
      }
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
      EasyExcel.write(outputStream, SalesStockOutResult.class).sheet("销售出库列表缺失listings导出").doWrite(resultList);
      String[] sendToEmailArr = param.getEmailList().toArray(new String[param.getEmailList().size()]);
      List<ApacheEmailAttachments> attachmentsList = new ArrayList<>();
      ApacheEmailAttachments attachments = new ApacheEmailAttachments();
      String subject = "销售出库列表缺失listings";
      String msg = "rt";
      attachments.setBytes(outputStream.toByteArray());
      attachments.setFileName("销售出库列表缺失listings导出.xlsx");
      attachmentsList.add(attachments);
      apacheEmailConsumer.sendEmailsWithOnlineAttachments(subject, msg, sendToEmailArr, attachmentsList);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ResponseData.success();
  }

  @PostResource(name = "无需处理", path = "/disable", requiredPermission = false)
  @ApiOperation(value = "无需处理")
  @BusinessLog(title = "销售出库列表-无需处理",opType = LogAnnotionOpTypeEnum.OTHER)
  public ResponseData disable(@RequestBody SalesStockOutParam param) {
    return service.disable(param);
  }
}
