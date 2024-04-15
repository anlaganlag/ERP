package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.RecomSeasonRatioResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IRecomSeasonRatioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 季节系数规则表-最细维度匹配 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2022-06-23
 */


@Slf4j
@RestController
@ApiResource(name = "备货2.0季节系数", path = "/recomSeasonRatio")
@Api(tags = "备货2.0季节系数")
public class RecomSeasonRatioController {

    @Autowired
    private IRecomSeasonRatioService service;


    private final String controllerName = "备货2.0季节系数";



    @PostResource(name = "根据采购订单id获取季节系数", path = "/findByPurchaseOrderId")
    @ApiOperation(value = "根据采购订单id获取季节系数", response = RecomSeasonRatioResult.class)
    @BusinessLog(title = controllerName + "_" +"根据采购订单id获取季节系数",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData findByPurchaseOrderId(@RequestBody PurchaseOrdersParam param) {
        return service.findByPurchaseOrderId(param);
    }

}
