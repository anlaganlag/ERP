package com.tadpole.cloud.platformSettlement.modular.sales.provider;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.sales.InventoryDemandServiceApi;
import com.tadpole.cloud.platformSettlement.api.sales.entity.StockMonitor;
import com.tadpole.cloud.platformSettlement.modular.sales.service.IInventoryDemandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 存货需求 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-03-03
 */
@Service
@Slf4j
@RestController
public class InventoryDemandServiceProvider  implements InventoryDemandServiceApi {

  @Autowired
  private IInventoryDemandService iInventoryDemandService;


  @DataSource(name = "sales")
  @Override
  public List<StockMonitor> stockMonitorHead(String department) {
    return iInventoryDemandService.stockMonitorHead(department);
  }
  @DataSource(name = "sales")
  @Override
  public StockMonitor stockMonitorHeadDept(String department) {
    return iInventoryDemandService.stockMonitorHeadDept(department);
  }
}
