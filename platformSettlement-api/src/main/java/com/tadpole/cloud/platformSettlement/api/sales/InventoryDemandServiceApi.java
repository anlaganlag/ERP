package com.tadpole.cloud.platformSettlement.api.sales;


import com.tadpole.cloud.platformSettlement.api.sales.entity.StockMonitor;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 存货需求 服务类
 * </p>
 *
 * @author gal
 * @since 2022-03-03
 */
@RequestMapping("/IInventoryDemandServiceApi")
public interface InventoryDemandServiceApi {

  @RequestMapping(value = "/stockMonitorHead")
  @ApiOperation(value = "备货监控-请求头汇总")
  List<StockMonitor> stockMonitorHead(@RequestParam String department);
  /**
   * 按部门统计
   * @param department
   * @return
   */
  @RequestMapping(value = "/stockMonitorHeadDept")
  @ApiOperation(value = "备货监控-按部门统计")
  StockMonitor stockMonitorHeadDept(@RequestParam  String department);
}
