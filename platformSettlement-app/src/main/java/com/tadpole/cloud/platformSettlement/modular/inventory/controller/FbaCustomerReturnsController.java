package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.FbaCustomerReturns;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaCustomerReturnsParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.enums.CommonTypeEnum;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IFbaCustomerReturnsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 亚马逊物流买家退货 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@RestController
@ApiResource(name = "FBA customer return", path = "/fbaCustomerReturns")
@Api(tags = "FBA customer return")
public class FbaCustomerReturnsController {

  @Autowired
  private IFbaCustomerReturnsService service;
  @Autowired
  private IErpWarehouseCodeService erpWarehouseCodeService;

  @PostResource(name = "FBA customer return", path = "/list", menuFlag = true, requiredPermission = false)
  @ApiOperation(value = "FBA customer return", response = FbaCustomerReturns.class)
  @BusinessLog(title = "FBA customer return-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody FbaCustomerReturnsParam param) {
    return ResponseData.success(service.findPageBySpec(param));
  }

  @PostResource(name = "获取数量", path = "/getQuantity", requiredPermission = false)
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "FBA customer return-数量汇总查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public Integer getQuantity(@RequestBody FbaCustomerReturnsParam param) {
    return service.getQuantity(param);
  }

  @PostResource(name = "导出", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "导出")
  @BusinessLog(title = "FBA customer return-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody FbaCustomerReturnsParam param, HttpServletResponse response) throws IOException {
    List<FbaCustomerReturns> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition", "attachment;filename=" + new String("FBAcustomerReturn.xlsx".getBytes(
            StandardCharsets.UTF_8), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), FbaCustomerReturns.class).sheet("FBAcustomerReturn").doWrite(resultList);
  }

  @GetResource(name = "导入修改销售组织模板", path = "/download", requiredPermission = false, requiredLogin = false)
  @ApiOperation("导入修改销售组织模板")
  public void download(HttpServletResponse response) {
    new ExcelUtils().downloadExcel(response, "/template/导入修改销售组织模板.xlsx");
  }

  @ParamValidator
  @PostResource(name = "导入订单修改销售组织", path = "/upload", requiredPermission = false)
  @ApiOperation(value = "导入订单修改销售组织")
  @BusinessLog(title = "FBA customer return-导入订单修改销售组织",opType = LogAnnotionOpTypeEnum.IMPORT)
  public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
    return service.upload(file);
  }

  @PostResource(name = "审核", path = "/verify", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "审核")
  @DataSource(name = "warehouse")
  @BusinessLog(title = "FBA customer return-审核",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData verify(@RequestBody FbaCustomerReturnsParam param) {
    try {
      FbaCustomerReturns customerReturns = service.getById(param.getId());
      if (StringUtils.isAnyBlank(customerReturns.getSalesOrganization(), customerReturns.getSalesOrganizationCode())) {
        return ResponseData.error("销售组织名称/编码为空，审核失败！");
      }
      param.setSalesOrganization(customerReturns.getSalesOrganization());
      param.setSalesOrganizationCode(customerReturns.getSalesOrganizationCode());

      //组织名称ORG：Amazon_账号_站点
      String org = CommonTypeEnum.Amazon.getName() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
      param.setInventoryOrganization(org);
      //仓库名称：Amazon_账号_站点_仓库
      String warehouseName = CommonTypeEnum.Amazon.getName() + "_" + param.getSysShopsName() + "_" + param.getSysSite() + "_仓库";
      param.setWarehouseName(warehouseName);

      //获取AmazonERP组织名称对应的组织编码
      Map<String, String> orgCodeMap = erpWarehouseCodeService.getOrgCodeMap();
      //库存组织编码INVENTORY_ORGANIZATION_CODE
      if(StringUtils.isBlank(orgCodeMap.get(org))){
        return ResponseData.error("库存组织编码为空，审核失败！");
      }
      param.setInventoryOrganizationCode(orgCodeMap.get(org));

      //仓库编码
      if(StringUtils.isBlank(orgCodeMap.get(warehouseName))){
        return ResponseData.error("仓库编码为空，审核失败！");
      }
      param.setWarehouseCode(orgCodeMap.get(warehouseName));
      service.verify(param);
      //获取及设置物料编码以便ERP分配物料
      String material = service.getMaterial(param);
      if (StrUtil.isNotEmpty(material) ) {
        param.setMat(material);
        //根据组织编码和物料编码分配物料
        service.assignMaterial(param);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseData.error("审核失败");
    }
    return ResponseData.success();
  }

  @PostResource(name = "修改平台", path = "/editPlatform", requiredPermission = false)
  @ApiOperation(value = "修改平台")
  @BusinessLog(title = "FBA customer return-修改平台",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editPlatform(@RequestBody List<FbaCustomerReturnsParam> params) {
    service.editPlatform(params);
    return ResponseData.success();
  }

  @PostResource(name = "修改账号", path = "/editShop", requiredPermission = false)
  @ApiOperation(value = "修改账号")
  @BusinessLog(title = "FBA customer return-修改账号",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editShop(@RequestBody List<FbaCustomerReturnsParam> params) {
    return service.editShop(params);
  }

  @PostResource(name = "修改站点", path = "/editSites", requiredPermission = false)
  @ApiOperation(value = "修改站点")
  @BusinessLog(title = "FBA customer return-修改站点",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editSites(@RequestBody List<FbaCustomerReturnsParam> params) {
    return service.editSites(params);
  }

  @PostResource(name = "作废", path = "/reject", requiredPermission = false)
  @ApiOperation(value = "作废")
  @BusinessLog(title = "FBA customer return-作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData reject(@RequestBody FbaCustomerReturnsParam param) {
    service.reject(param);
    return ResponseData.success();
  }

  @PostResource(name = "批量作废", path = "/rejectBatch", requiredPermission = false)
  @ApiOperation(value = "批量作废")
  @BusinessLog(title = "FBA customer return-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData rejectBatch(@RequestBody List<FbaCustomerReturnsParam> params) {
    service.rejectBatch(params);
    return ResponseData.success();
  }

  @PostResource(name = "生成销售退货列表", path = "/toSalesReturnList", requiredPermission = false)
  @ApiOperation(value = "生成销售退货列表")
  @BusinessLog(title = "FBA customer return-生成销售退货列表",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData toSalesReturnList(@RequestBody FbaCustomerReturnsParam param) throws IOException {
    ResponseData rd =  service.toSalesReturnList(param);
    if (rd.getSuccess()){
      //根据组织编码和物料编码批量分配物料
      return service.assignBatchMaterial((List<ZZDistributeMcms>) rd.getData());
    }
    return rd;
  }

  @PostResource(name = "添加库存仓库组织", path = "/addInvWareOrg", requiredPermission = false)
  @ApiOperation(value = "添加库存仓库组织")
  @BusinessLog(title = "FBA customer return-添加库存仓库组织",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData addInvWareOrg() {
    return ResponseData.success(service.addInvWareOrg());
  }

  @PostResource(name = "新添加组织", path = "/addOrg", requiredPermission = false)
  @ApiOperation(value = "新添加组织")
  @BusinessLog(title = "FBA customer return-新添加组织",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData addOrg() {
    return service.addNewOrg();
  }

  @PostResource(name = "批量审核", path = "/verifyBatchBySpec", requiredPermission = false)
  @ApiOperation(value = "批量审核")
  @BusinessLog(title = "FBA customer return-批量审核",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData verifyBatchBySpec(@RequestBody FbaCustomerReturnsParam param) {
    param.setVerifyBy(LoginContext.me().getLoginUser().getAccount());
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    service.verifyUpdateBatch(param);
    return ResponseData.success();
  }

  @PostResource(name = "修改销售组织", path = "/editSalesOrg", requiredPermission = false)
  @ApiOperation(value = "修改销售组织")
  @BusinessLog(title = "FBA customer return-修改销售组织",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData editSalesOrg(@RequestBody List<FbaCustomerReturnsParam> params) {
    return service.editSalesOrg(params);
  }
}

