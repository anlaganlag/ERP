package com.tadpole.cloud.platformSettlement.modular.inventory.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;

import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ShipmentReplacements;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ShipmentReplacementsParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IShipmentReplacementsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* <p>
    *  前端控制器
    * </p>
*
* @author S20190161
* @since 2023-06-08
*/
@RestController
@Api(tags = "索赔情况追踪表")
@ApiResource(name = "索赔情况追踪表", path = "/shipmentReplacements")
public class ShipmentReplacementsController {

    @Autowired
    private IShipmentReplacementsService service;


    @PostResource(name = "索赔情况追踪表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "索赔情况追踪表")
    @BusinessLog(title = "索赔情况追踪表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody ShipmentReplacementsParam param) {
        return ResponseData.success(service.findPageBySpec(param));
    }
    @BusinessLog(title = "索赔情况追踪表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    @PostResource(name = "导出", path = "/export")
    @ApiOperation(value = "导出")
    public ResponseData export(@RequestBody ShipmentReplacementsParam param, HttpServletResponse response) throws IOException {
        var list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("替换货报告.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ShipmentReplacements.class).sheet("替换货报告").doWrite(list);
        return ResponseData.success();
    }
    }
