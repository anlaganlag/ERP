package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.InventoryDemandServiceConsumer;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockMonitorParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockMonitorResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockMonitorService;
import com.tadpole.cloud.platformSettlement.api.sales.entity.StockMonitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* <p>
    * 备货监控 前端控制器
    * </p>
*
* @author cyt
* @since 2022-07-21
*/
@RestController
@ApiResource(name = "备货监控", path = "/stockMonitor")
@Api(tags = "备货监控")
@Slf4j
public class StockMonitorController {

    @Resource
    private IStockMonitorService service;

    @Resource
    private InventoryDemandServiceConsumer inventoryDemandServiceConsumer;


    private final String controllerName = "备货监控";


    @GetResource(name = "备货监控", path = "/list", menuFlag = true)
    @ApiOperation(value = "备货监控", response = StockMonitor.class)
    @BusinessLog(title = controllerName + "_" +"备货监控",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(StockMonitorParam param) {
        List<StockMonitorResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "生成数据", path = "/CreateData")
    @ApiOperation(value = "生成数据")
    @BusinessLog(title = controllerName + "_" +"生成数据",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData CreateData(String department) {
            service.deleteByDept(department);

            //获取erp系统相关数据
            List<StockMonitor> erpData = service.getErpData(department);
            //获取备货，新品 这两个项目中的 本次采买数量
            List<StockMonitor> curPurQtyData = service.getCurPurQty(department);

        if ("事业五部".equals(department)) {
            StockMonitor stockMonitorDept =  inventoryDemandServiceConsumer.stockMonitorHeadDept(department);
            return   service.megerCalcDept(stockMonitorDept,erpData,curPurQtyData);

        } else {
            List<StockMonitor> stockMonitorHeadList =  inventoryDemandServiceConsumer.stockMonitorHead(department);
            //汇总数据,并计算值
            return   service.megerCalc2(stockMonitorHeadList,erpData,curPurQtyData);
        }

//        return ResponseData.success();

    }

}
