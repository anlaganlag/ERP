package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockApprovaltimeParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApprovaltimeParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* <p>
    * 日常备货流程审核节点参数设置 前端控制器
    * </p>
*
* @author cyt
* @since 2022-07-19
*/
@RestController
@ApiResource(name = "日常备货流程时间点设置", path = "/approvalTime",menuFlag = true)
@Api(tags = "日常备货流程时间点设置")
public class StockApprovaltimeParameterController {

    @Autowired
    private IStockApprovaltimeParameterService service;

    private final String controllerName = "日常备货流程时间点设置";



    /**
     * 分页查询列表
     *
     * @author gal @Date 2021-6-02
     */
    @GetResource(name = "日常备货流程时间点设置列表", path = "/list", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "日常备货流程时间点设置列表", response = StockApprovaltimeParameterResult.class)
    public ResponseData queryListPage(StockApprovaltimeParameterParam param) {
        List<StockApprovaltimeParameterResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    /**
     * 分页查询列表
     *
     * @author gal
     * @Date 2021-6-02
     */
    @PostResource(name = "更新", path = "/update",requiredPermission = false)
    @ApiOperation(value = "更新", response = StockApprovaltimeParameterResult.class)
    @BusinessLog(title = controllerName + "_" +"更新",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody List<StockApprovaltimeParameterParam> params) {
        return service.update(params);
    }





}
