package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockParameterParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockParameterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
* <p>
    * 备货通用参数 前端控制器
    * </p>
*
* @author cyt
* @since 2022-07-19
*/
@RestController
@ApiResource(name = "系统参数_备货天数设置", path = "/stockParameterDays",menuFlag = true)
@Api(tags = "系统参数_备货天数设置")
public class StockParameterController {

    @Autowired
    private IStockParameterService service;

    private final String controllerName = "系统参数_备货天数设置";

    /**
     * 分页查询列表
     *
     * @author gal @Date 2021-6-02
     */
    @GetResource(name = "备货天数设置2.0", path = "/list", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "备货天数设置2.0", response = StockParameterResult.class)
    @BusinessLog(title = controllerName + "_" +"备货天数设置2.0",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(StockParameterParam param) {
        List<StockParameterResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    /**
     * 分页查询列表
     *
     * @author gal
     * @Date 2021-6-02
     */
    @PostResource(name = "备货和预警天数", path = "/update",requiredPermission = false)
    @ApiOperation(value = "备货和预警天数", response = StockParameterResult.class)
    @BusinessLog(title = controllerName + "_" +"备货和预警天数",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody List<StockParameterParam> params) {
        return service.update(params);
    }


}
