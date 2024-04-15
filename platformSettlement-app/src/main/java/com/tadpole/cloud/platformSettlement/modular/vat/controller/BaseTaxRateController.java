package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseTaxRateParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseTaxRateResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseTaxRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 基础信息-税率表 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@RestController
@Api(tags = "BaseTaxRate VAT基础信息-税率")
@ApiResource(name = "BaseTaxRate VAT基础信息-税率", path = "/baseTaxRate")
public class BaseTaxRateController {

    private final String ControllerName = "VAT基础信息-税率";

    @Autowired
    private IBaseTaxRateService service;

    @PostResource(name = ControllerName + "列表查询", path = "/list", menuFlag = true)
    @ApiOperation(value = ControllerName + "列表查询", response = BaseTaxRateResult.class)
    @BusinessLog(title = "BaseTaxRate VAT基础信息-税率-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody BaseTaxRateParam param) {
        return ResponseData.success(service.queryListPage(param));
    }

    @PostResource(name = ControllerName + "新增", path = "/add", requiredPermission = false)
    @ApiOperation(value = ControllerName + "新增", response = ResponseData.class)
    @BusinessLog(title = "BaseTaxRate VAT基础信息-税率-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody BaseTaxRateParam param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.success(ControllerName + "新增失败，原因：获取数据异常，请确认传入数据。");
        }
        try{
            return service.add(param,ControllerName);
        } catch (Exception ex) {
            return ResponseData.error(ControllerName + "新增异常，原因：" + ex.getMessage());
        }
    }

    @PostResource(name = ControllerName + "修改", path = "/update", requiredPermission = false)
    @ApiOperation(value = ControllerName + "修改", response = ResponseData.class)
    @BusinessLog(title = "BaseTaxRate VAT基础信息-税率-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData update(@RequestBody BaseTaxRateParam param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.success(ControllerName + "修改失败，原因：获取数据异常，请确认传入数据。");
        }
        try{
            return service.update(param,ControllerName);
        } catch (Exception ex) {
            return ResponseData.error(ControllerName + "修改异常，原因：" + ex.getMessage());
        }
    }
}
