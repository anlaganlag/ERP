package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.constants.AuditorCodeTwoEnum;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcVerifInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrders2Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PlanPurchaseOrders2Result;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PurchaseOrdersResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPmcVerifInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApprovaltimeParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@ApiResource(name = "备货2.0-计划部审核", path = "/planVerif")
@Api(tags = "备货2.0-计划部审核")
public class PlanVerifController {

    @Autowired
    private IPurchaseOrdersService service;

    @Autowired
    private IStockApprovaltimeParameterService parameterService;

    @Autowired
    private IPmcVerifInfoService pmcVerifInfoService;


    private final String controllerName = "备货2.0-计划部审核";


    @PostResource(name = "备货2.0计划部审批列表", path = "/planVerifList", menuFlag = false)
    @ApiOperation(value = "备货2.0计划部审批列表", response = PurchaseOrdersResult.class)
    @BusinessLog(title = controllerName + "_" +"备货2.0计划部审批列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData planVerifList(@RequestBody PurchaseOrders2Param param) {
//        param.setOrderStatus(2);
        return ResponseData.success(service.planVerifList2(param));
    }

    @PostResource(name = "备货2.0计划部审批列表", path = "/planVerifList2", menuFlag = true)
    @ApiOperation(value = "备货2.0计划部审批列表", response = PlanPurchaseOrders2Result.class)
    @BusinessLog(title = controllerName + "_" +"备货2.0计划部审批列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData planVerifList2(@RequestBody PurchaseOrders2Param param) {
        return ResponseData.success(service.planVerifList2(param));
    }


    @PostResource(name = "计划部审批", path = "/verif")
    @ApiOperation(value = "计划部审批", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"计划部审批",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  verif(@RequestBody PurchaseOrdersParam param) {
        return service.verif(param);
    }


    @PostResource(name = "计划部批量审批", path = "/batchVerif")
    @ApiOperation(value = "计划部批量审批", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"计划部批量审批",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  batchVerif(@RequestBody List<PurchaseOrdersParam>  paramList) {
        return service.batchVerif(paramList);
    }

    @PostResource(name = "详情-明细", path = "/itemDetail")
    @ApiOperation(value = "详情-明细", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"详情-明细",opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData  itemDetail (@RequestBody PurchaseOrdersParam param) {
        return service.detail(param);
    }



    @PostResource(name = "Asin备货明细", path = "/asinStockReason")
    @ApiOperation(value = "Asin备货明细", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"Asin备货明细",opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData  getAsinStockReason (@RequestBody PurchaseOrdersParam param) {
        return service.getAsinStockReason(param);
    }



    @PostResource(name = "计划部当日已审批订单(部门+team+物料编码)", path = "/planApprovedOrder" )
    @ApiOperation(value = "计划部当日已审批订单(部门+team+物料编码)", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"计划部当日已审批订单(部门+team+物料编码)",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData  planApprovedOrder (@RequestBody PmcVerifInfoParam param) {
        if (ObjectUtil.isNull(param.getCreateTimeStart())) {
            param.setCreateTimeStart(DateUtil.beginOfDay(new Date()));
        }
        if (ObjectUtil.isNull(param.getCreateTimeEnd())) {
            param.setCreateTimeEnd(DateUtil.endOfDay(new Date()));
        }
        return pmcVerifInfoService.planApprovedOrder(param);
    }


    /**
     * 计划部超时自动审批
     * @return
     */
    @ParamValidator
    @PostResource(name = "计划部超时自动审批", path = "/overTimeAction", requiredPermission = false)
    @ApiOperation(value = "计划部超时自动审批")
    public ResponseData overTimeAction() {
        StockApprovaltimeParameterResult jhb = parameterService.findByAuditorCode(AuditorCodeTwoEnum.JHB.getCode());
        if (ObjectUtil.isNull(jhb)) {
            return ResponseData.error("计划部超时自动审批参数未设置");
        }
        return service.planOverTimeAction(jhb);
    }

}
