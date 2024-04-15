package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EbmsSeaInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EbmsSeaInventoryParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.consumer.OverseasWarehouseManageConsumer;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IEbmsSeaInventoryService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 海外库存 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
@RestController
@ApiResource(name = "海外仓库", path = "/ebmsSeaInventory")
@Api(tags = "海外仓库")
public class EbmsSeaInventoryController {

  @Autowired
  private IEbmsSeaInventoryService service;
  @Autowired
  private OverseasWarehouseManageConsumer overseasWarehouseManageConsumer;
  @Autowired
  private IErpWarehouseCodeService erpWarehouseCodeService;

  @PostResource(name = "海外仓库存列表", path = "/list",  requiredPermission = false)
  @ApiOperation(value = "海外仓库存列表", response = EbmsSeaInventory.class)
  @BusinessLog(title = "海外仓库-海外仓库存列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody EbmsSeaInventoryParam param) {
    return ResponseData.success(service.findPageBySpec(param));
  }

  @PostResource(name = "获取数量", path = "/getQuantity", requiredPermission = false)
  @ApiOperation(value = "获取数量")
  @BusinessLog(title = "海外仓库-获取数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
  public String getQuantity(@RequestBody EbmsSeaInventoryParam param) {
    return service.getQty(param);
  }

  @PostResource(name = "海外仓库存列表导出", path = "/export", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "海外仓库存列表导出")
  @BusinessLog(title = "海外仓库-海外仓库存列表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void export(@RequestBody EbmsSeaInventoryParam param, HttpServletResponse response) throws IOException {
    List<EbmsSeaInventory> resultList = service.export(param);
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition",
        "attachment;filename=" + new String("海外仓库存列表导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), EbmsSeaInventory.class).sheet("海外仓库存列表导出").doWrite(resultList);
  }

  @PostResource(name = "生成月末海外仓库存", path = "/generateOverseasWarehouse", requiredPermission = false, requiredLogin = false)
  @ApiOperation(value = "生成月末海外仓库存")
  @BusinessLog(title = "海外仓库-生成月末海外仓库存",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData generateOverseasWarehouse(){
    //获取AmazonERP组织名称对应的组织编码
    Map<String, String> orgCodeMap = erpWarehouseCodeService.getOrgCodeMap();
    return service.generateOverseasWarehouse(overseasWarehouseManageConsumer.getOverseasWarehouseManageList(), orgCodeMap);
  }

  @PostResource(name = "刷组织编码", path = "/refreshOrgCode", requiredPermission = false)
  @ApiOperation(value = "刷组织编码")
  @BusinessLog(title = "海外仓库-刷组织编码",opType = LogAnnotionOpTypeEnum.EDIT)
  public ResponseData refreshOrgCode() {
    return ResponseData.success(service.refreshOrgCode());
  }
}
