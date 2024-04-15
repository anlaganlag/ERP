package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalOrderDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalOrderDetailParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemovalOrderDetailService;
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
 * 移除订单详情报告 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@RestController
@ApiResource(name = "移除订单详情报告", path = "/removalOrderDetail")
@Api(tags = "移除订单详情报告")
public class RemovalOrderDetailController {

  @Autowired
  private IRemovalOrderDetailService service;
  @Autowired
  private IErpWarehouseCodeService wareService;

  @PostResource(name = "移除订单详情报告", path = "/list", requiredPermission = false)
  @ApiOperation(value = "移除订单详情报告", response = RemovalOrderDetail.class)
  @BusinessLog(title = "移除订单详情报告-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody RemovalOrderDetailParam param) throws IOException {
    PageResult<RemovalOrderDetail> pageBySpec = service.findPageBySpec(param);
    //移除销毁拆分，写入主表和明细表,更新明细
//    service.RemovalOrderDetailSplit(param);
//    service.updateDeptDetailList();
//    service.updateFileDeptDetailList();
    return ResponseData.success(pageBySpec);
  }

  @PostResource(name = "获取数量", path = "/getQuantity", requiredPermission = false)
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "移除订单详情报告-获取汇总数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public String getQuantity(@RequestBody RemovalOrderDetailParam param) {
    String quantities = service.getQuantity(param);
    return quantities;
  }

  @PostResource(name = "批量修改站点", path = "/editSites", requiredPermission = false)
  @ApiOperation(value = "批量修改站点")
  @BusinessLog(title = "移除订单详情报告-批量修改站点",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editSites(@RequestBody RemovalOrderDetailParam param) {
    service.editSites(param);
    return ResponseData.success();
  }

  @PostResource(name = "修改站点", path = "/editSite", requiredPermission = false)
  @ApiOperation(value = "修改站点")
  @BusinessLog(title = "移除订单详情报告-修改站点",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editSite(@RequestBody RemovalOrderDetailParam param) {
    service.editSite(param);
    return ResponseData.success();
  }

  @PostResource(name = "移除订单详情报告导出", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "移除订单详情报告导出")
  @BusinessLog(title = "移除订单详情报告-移除订单详情报告导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody RemovalOrderDetailParam param, HttpServletResponse response)
      throws IOException {

    List<RemovalOrderDetail> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition",
        "attachment;filename=" + new String("移除订单详情报告导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), RemovalOrderDetail.class).sheet("移除订单详情报告导出").doWrite(resultList);
  }
}
