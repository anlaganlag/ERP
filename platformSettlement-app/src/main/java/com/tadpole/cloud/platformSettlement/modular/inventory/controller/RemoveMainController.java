package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcelFactory;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemoveMain;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalOrderDetailParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemoveMainParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemoveMainResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemovalOrderDetailService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemoveMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Amazon销毁移除主表 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@RestController
@ApiResource(name = "Amazon销毁移除主表", path = "/removeMain")
@Api(tags = "Amazon销毁移除主表")
public class RemoveMainController {

  @Autowired
  private IRemoveMainService service;
  @Autowired
  private IErpWarehouseCodeService wareService;
  @Autowired
  private IRemovalOrderDetailService removalOrderDetailService;

  @PostResource(name = "Amazon销毁移除主表", path = "/list", requiredPermission = false)
  @ApiOperation(value = "Amazon销毁移除主表", response = RemoveMain.class)
  @BusinessLog(title = "Amazon销毁移除主表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody RemoveMainParam param) {
    PageResult<RemoveMainResult> pageBySpec = service.findPageBySpec(param);
    return ResponseData.success(pageBySpec);
  }

  @PostResource(name = "获取数量", path = "/getQuantity", requiredPermission = false)
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "Amazon销毁移除主表-获取数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public String getQuantity(@RequestBody RemoveMainParam param) {
    return service.getQuantity(param);
  }

  @GetResource(name = "刷新数据", path = "/refreshData", requiredPermission = false)
  @ApiOperation(value = "刷新数据")
  @BusinessLog(title = "Amazon销毁移除主表-刷新数据",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData refreshData() throws IOException {
    RemovalOrderDetailParam param = new RemovalOrderDetailParam();
    removalOrderDetailService.RemovalOrderDetailSplit(param);
    removalOrderDetailService.updateDeptDetailList();
    removalOrderDetailService.updateFileDeptDetailList();
    return ResponseData.success();
  }

  @PostResource(name = "Amazon销毁移除主表导出", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "Amazon销毁移除主表导出")
  @BusinessLog(title = "Amazon销毁移除主表-Amazon销毁移除主表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody RemoveMainParam param, HttpServletResponse response) throws IOException {
    List<RemoveMainResult> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition","attachment;filename=" + new String("Amazon销毁移除主表导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcelFactory.write(response.getOutputStream(), RemoveMainResult.class).sheet("Amazon销毁移除主表导出").doWrite(resultList);
  }

  @GetResource(name = "审核", path = "/verify", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "审核")
  @BusinessLog(title = "Amazon销毁移除主表-审核",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData verify(RemoveMainParam param) {
    String invOrg = "Amazon" + "_" + param.getSysShopsName() + "_" + param.getSysSite();

    String invOrgCode = wareService.getOrganizationCode(invOrg);
    String wareCode = wareService.getOrganizationCode(invOrg + "_仓库");

    param.setInventoryOrganization(invOrg);
    param.setInventoryOrganizationCode(invOrgCode);
    param.setWarehouseName(invOrg + "_仓库");
    param.setWarehouseCode(wareCode);
    //获取及设置物料编码以便ERP分配物料
    String material = service.getMaterial(param);
    param.setMat(material);

    if (param.getInventoryOrganizationCode() == null){
      return ResponseData.error("无法获取库存编码");

    }

    //通过审核
    service.verify(param);
    //分配物料即插入物料及组织

    //分配物料即插入物料及组织
    service.assignMaterial(param);

    return ResponseData.success();
  }

  @GetResource(name = "作废", path = "/reject", requiredPermission = false)
  @ApiOperation(value = "作废")
  @BusinessLog(title = "Amazon销毁移除主表-作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData reject(RemoveMainParam param) {
    service.reject(param);
    return ResponseData.success();
  }

  @PostResource(name = "批量作废", path = "/rejectBatch", requiredPermission = false)
  @ApiOperation(value = "批量作废")
  @BusinessLog(title = "Amazon销毁移除主表-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData rejectBatch(@RequestBody List<RemoveMainParam> params) {
    service.rejectBatch(params);
    return ResponseData.success();
  }

  @PostResource(name = "添加销售组织", path = "/addOrg", requiredPermission = false)
  @ApiOperation(value = "添加销售组织")
  @BusinessLog(title = "Amazon销毁移除主表-添加销售组织",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData addOrg() {
    List<RemoveMainParam> list = service.orgList();
    int batchSize = 1000;
    List<List<RemoveMainParam>> lists = ListUtil.split(list, batchSize);
    //正常取时分配
    for (List<RemoveMainParam> lst: lists) {
      lst.forEach(i -> {
        String org = "Amazon" + "_" + i.getShopName() + "_" + i.getSite();
        String orgCode = wareService.getOrganizationCode(org);
        String wareCode = wareService.getOrganizationCode(org + "_仓库");
        i.setInventoryOrganization(org);
        i.setInventoryOrganizationCode(orgCode);
        i.setWarehouseName(org + "_仓库");
        i.setWarehouseCode(wareCode);
      });
      List<RemoveMainParam> orgList = lst.stream().filter(
          i -> i.getInventoryOrganizationCode() != null && i.getWarehouseCode() != null)
          .collect(Collectors.toList());
      if (CollUtil.isNotEmpty(orgList)){
        //批量更新
        service.orgBatch(orgList);
      }
      //重新取值
    }
    return ResponseData.success();
  }

  @PostResource(name = "批量审核", path = "/verifyBatchBySpec", requiredPermission = false)
  @ApiOperation(value = "批量审核")
  @BusinessLog(title = "Amazon销毁移除主表-批量审核",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData verifyBatchBySpec(@RequestBody RemoveMainParam param) {
    param.setVerifyBy(LoginContext.me().getLoginUser().getAccount());
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    service.verifyUpdateBatch(param);
    return ResponseData.success();
  }

  @GetResource(name = "生成销毁移除详情列表", path = "/toRemovalList", requiredPermission = false)
  @ApiOperation(value = "生成销毁移除详情列表")
  @BusinessLog(title = "Amazon销毁移除主表-生成销毁移除详情列表",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData toDisposeRemoveList(RemoveMainParam param) {
    return service.toDisposeRemoveList(param);
  }
}

