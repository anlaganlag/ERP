package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRouteDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.LogisticsStorageTimedTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 仓储物流 定时任务 接口
 */
@Api(tags = "仓储物流定时任务接口")
@RestController
@ApiResource(name = "仓储物流定时任务接口", path="/logisticsStorageTimedTask")
public class LogisticsStorageTimedTaskController {


    public final String baseName = "仓储物流定时任务接口";
    public final String analysisAmazonInGoodsQtyNewV4 = baseName+"--Amazon在途库存报表";
    public final String syncTbReceivedInvenrotyAnalysisV2 = baseName+"--同步来货报告";
    public final String updateBscOverseasWayShipmentStatusNew = baseName+"--更新货物实际状态(按照货件明细SKU)";
    public final String updateBscOverseasWayStatus = baseName+"--更新发货清单Amazon接收状态";
    @Resource
    LogisticsStorageTimedTaskService logisticsStorageTimedTaskService;


    /**
     *Amazon在途库存报表(新增规则：判断货件实际状态为已完成、当前时间往前推6个月、替换接收报告数据)
     * @return
     */
    @ApiOperation(value =analysisAmazonInGoodsQtyNewV4,response= TbLogisticsListToHeadRouteDet.class)
    @GetResource(name = analysisAmazonInGoodsQtyNewV4, path = "/analysisAmazonInGoodsQtyNewV4" )
    public ResponseData analysisAmazonInGoodsQtyNewV4(){
        return logisticsStorageTimedTaskService.analysisAmazonInGoodsQtyNewV4();
    }

    /**
     * TbReceivedInvenrotyAnalysisV2 同步来货报告
     */
    @ApiOperation(value =syncTbReceivedInvenrotyAnalysisV2,response= TbLogisticsListToHeadRouteDet.class)
    @GetResource(name = syncTbReceivedInvenrotyAnalysisV2, path = "/syncTbReceivedInvenrotyAnalysisV2" )
    public ResponseData syncTbReceivedInvenrotyAnalysisV2(){
        return logisticsStorageTimedTaskService.syncTbReceivedInvenrotyAnalysisV2();
    }

    /**
     * 更新货物实际状态(按照货件明细SKU)
     */
    @ApiOperation(value =updateBscOverseasWayShipmentStatusNew,response= ResponseData.class)
    @GetResource(name = updateBscOverseasWayShipmentStatusNew, path = "/updateBscOverseasWayShipmentStatusNew" )
    public ResponseData updateBscOverseasWayShipmentStatusNew(){
        return logisticsStorageTimedTaskService.updateBscOverseasWayShipmentStatusNew();
    }

    /**
     * 更新发货清单Amazon接收状态
     */
    @ApiOperation(value =updateBscOverseasWayStatus,response= ResponseData.class)
    @GetResource(name = updateBscOverseasWayStatus, path = "/updateBscOverseasWayStatus" )
    public ResponseData updateBscOverseasWayStatus(){
        return logisticsStorageTimedTaskService.updateBscOverseasWayStatus();
    }
}
