package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbAmazonMarketplace;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FinancialSiteParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialSiteResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFinancialSiteService;
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
 * 财务站点信息 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
@RestController
@ApiResource(name = "财务站点信息", path = "/financialSite")
@Api(tags = "财务站点信息")
public class FinancialSiteController {

  @Autowired
  private IFinancialSiteService service;

  @PostResource(name = "财务站点信息管理", path = "/queryListPage", menuFlag = true)
  @ApiOperation(value = "财务站点信息管理", response = FinancialSiteResult.class)
  @BusinessLog(title = "财务站点信息-财务站点信息查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody FinancialSiteParam param) {
    PageResult<FinancialSiteResult> pageBySpec = service.findPageBySpec(param);
    return ResponseData.success(pageBySpec);
  }

  @GetResource(name = "导出财务站点信息列表", path = "/exportFinancialSiteList", requiredPermission = false,requiredLogin = false)
  @ApiOperation(value = "导出财务站点信息列表")
  @BusinessLog(title = "财务站点信息-导出财务站点信息列表",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void exportFinancialSiteList(FinancialSiteParam param, HttpServletResponse response)
      throws IOException {
    List<FinancialSiteResult> pageBySpec = service.exportFinancialSiteList(param);
    response.addHeader("Content-Disposition",
        "attachment;filename=" + new String("导出财务站点信息列表.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), FinancialSiteResult.class).sheet("导出财务站点信息列表").doWrite(pageBySpec);
  }

  @GetResource(name = "财务站点自动分析", path = "/autoAnalysis", requiredPermission = false)
  @ApiOperation(value = "财务站点自动分析", response = FinancialSiteResult.class)
  @BusinessLog(title = "财务站点信息-财务站点自动分析",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData autoAnalysis() {
    List<TbAmazonMarketplace> list = service.findSiteEbms();
    service.autoAnalysis(list);
    return ResponseData.success();
  }

  @GetResource(name = "财务站点平台", path = "/getPlatform", requiredPermission = false)
  @ApiOperation(value = "财务站点平台", response = FinancialSiteResult.class)
  public ResponseData getPlatform() {
    List<FinancialSiteResult> list = service.getPlatform();
    return ResponseData.success(list);
  }

  @GetResource(name = "财务站点", path = "/getSite", requiredPermission = false)
  @ApiOperation(value = "财务站点", response = FinancialSiteResult.class)
  public ResponseData getSite() {
    List<FinancialSiteResult> list = service.getSite();
    return ResponseData.success(list);
  }

  @GetResource(name = "财务站点信息修改", path = "/updateSite", requiredPermission = false)
  @ApiOperation(value = "财务站点信息修改", response = FinancialSiteResult.class)
  @BusinessLog(title = "财务站点信息-财务站点信息修改",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData updateSite(FinancialSiteParam param) {
    service.updateSite(param);
    return ResponseData.success();
  }
}
