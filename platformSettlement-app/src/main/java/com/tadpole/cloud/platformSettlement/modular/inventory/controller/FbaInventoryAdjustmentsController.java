package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.FbaInventoryAdjustments;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaInventoryAdjustmentsParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IFbaInventoryAdjustmentsService;
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
 * 库存调整报告 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@RestController
@ApiResource(name = "库存调整报告", path = "/fbaInventoryAdjustments")
@Api(tags = "库存调整报告")
public class FbaInventoryAdjustmentsController {

  @Autowired
  private IFbaInventoryAdjustmentsService service;
  @Autowired
  private IErpWarehouseCodeService wareService;

  @PostResource(name = "库存调整报告", path = "/list", requiredPermission = false)
  @ApiOperation(value = "库存调整报告", response = FbaInventoryAdjustments.class)
  @BusinessLog(title = "库存调整报告-库存调整报告列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody FbaInventoryAdjustmentsParam param) {
    PageResult<FbaInventoryAdjustments> pageBySpec = service.findPageBySpec(param);
    return ResponseData.success(pageBySpec);
  }

  @PostResource(name = "获取数量", path = "/getQuantity", requiredPermission = false)
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "库存调整报告-获取数量汇总查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public Integer getQuantity(@RequestBody FbaInventoryAdjustmentsParam param) {
    return service.getQuantity(param);
  }

  @PostResource(name = "库存调整报告导出", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "库存调整报告导出")
  @BusinessLog(title = "库存调整报告-库存调整报告导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody FbaInventoryAdjustmentsParam param, HttpServletResponse response) throws IOException {
    List<FbaInventoryAdjustments> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition",
        "attachment;filename=" + new String("库存调整报告导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), FbaInventoryAdjustments.class).sheet("库存调整报告导出")
        .doWrite(resultList);
  }

  @PostResource(name = "审核", path = "/verify", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "审核")
  @BusinessLog(title = "库存调整报告-审核",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData verify(@RequestBody FbaInventoryAdjustmentsParam param) {
    if (param.getSysSite() == null || "".equals(param.getSysSite()) ) {
      return ResponseData.error("站点为空!");
    }

    String invOrg = param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
    String invOrgCode = wareService.getOrganizationCode(invOrg);
    String wareCode = wareService.getOrganizationCode(invOrg + "_仓库");


    if (StrUtil.isEmpty(invOrgCode) || StrUtil.isEmpty(wareCode) ) {
      return ResponseData.error("无法获取到组织编码!");
    }
    param.setInventoryOrganization(invOrg);
    param.setInventoryOrganizationCode(invOrgCode);
    param.setWarehouseName(invOrg + "_仓库");
    param.setWarehouseCode(wareCode);
    service.verify(param);
    //获取及设置物料编码以便ERP分配物料
    String material = service.getMaterial(param);
    if (StrUtil.isNotEmpty(material) ) {
      param.setMat(material);
      service.assignMaterial(param);
    }
    return ResponseData.success();
  }


  @PostResource(name = "批量审核", path = "/verifyBatch", requiredPermission = false)
  @ApiOperation(value = "审核")
  @BusinessLog(title = "库存调整报告-批量审核",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData verifyBatch(@RequestBody List<FbaInventoryAdjustmentsParam> params) {
    for (FbaInventoryAdjustmentsParam param : params) {
      if (param.getSysSite() == null || "".equals(param.getSysSite())) {
        continue;
      }
      String invOrg =
          param.getPlatform() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
      String invOrgCode = wareService.getOrganizationCode(invOrg);
      String wareCode = wareService.getOrganizationCode(invOrg + "_仓库");

      param.setInventoryOrganization(invOrg);
      param.setInventoryOrganizationCode(invOrgCode);
      param.setWarehouseName(invOrg + "_仓库");
      param.setWarehouseCode(wareCode);
      //获取及设置物料编码以便ERP分配物料



      service.verify(param);
      //获取及设置物料编码以便ERP分配物料
      String material = service.getMaterial(param);
      if (StrUtil.isNotEmpty(material) ) {
        param.setMat(material);
        service.assignMaterial(param);
      }

    }
    return ResponseData.success();
  }

  @PostResource(name = "作废", path = "/reject", requiredPermission = false)
  @ApiOperation(value = "作废")
  @BusinessLog(title = "库存调整报告-作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData reject(@RequestBody FbaInventoryAdjustmentsParam param) {
    service.reject(param);
    return ResponseData.success();
  }

  @PostResource(name = "批量作废", path = "/rejectBatch", requiredPermission = false)
  @ApiOperation(value = "批量作废")
  @BusinessLog(title = "库存调整报告-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData rejectBatch(@RequestBody List<FbaInventoryAdjustmentsParam> params) {
    service.rejectBatch(params);
    return ResponseData.success();
  }

  @PostResource(name = "生成库存调整列表", path = "/toInventoryAdjustList", requiredPermission = false)
  @ApiOperation(value = "生成库存调整列表")
  @BusinessLog(title = "库存调整报告-生成库存调整列表",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData toInventoryAdjustList(@RequestBody FbaInventoryAdjustmentsParam param) {
    return service.toInventoryAdjustList(param);
  }

  @PostResource(name = "批量审核", path = "/verifyBatchBySpec", requiredPermission = false)
  @ApiOperation(value = "批量审核")
  @BusinessLog(title = "库存调整报告-批量审核",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData verifyBatchBySpec(@RequestBody FbaInventoryAdjustmentsParam param) {
    param.setVerifyBy(LoginContext.me().getLoginUser().getAccount());
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    service.verifyUpdateBatch(param);
    return ResponseData.success();
  }

  @PostResource(name = "修改账号", path = "/editShop", requiredPermission = false)
  @ApiOperation(value = "修改账号")
  @BusinessLog(title = "库存调整报告-修改账号",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editShop(@RequestBody List<FbaInventoryAdjustmentsParam> params) {
    return service.editShop(params);
  }

  @PostResource(name = "修改站点", path = "/editSites", requiredPermission = false)
  @ApiOperation(value = "修改站点")
  @BusinessLog(title = "库存调整报告-修改站点",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editSites(@RequestBody List<FbaInventoryAdjustmentsParam> params) {
    return service.editSites(params);
  }

  @PostResource(name = "新添加组织", path = "/addOrg", requiredPermission = false)
  @ApiOperation(value = "新添加组织")
  @BusinessLog(title = "库存调整报告-新添加组织",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData addOrg() {
    return ResponseData.success(service.addNewOrg());
  }

  /**
   * 获InventoryAdjustments数据任务（定时）
   * @return
   */
  @PostResource(name = "获InventoryAdjustments数据任务", path = "/generateInventoryAdjustments", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "获InventoryAdjustments数据任务")
  @BusinessLog(title = "库存调整报告-获InventoryAdjustments数据任务",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData generateInventoryAdjustments() {
    return service.generateInventoryAdjustments();
  }
}
