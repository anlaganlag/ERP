package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseEuCountriesParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseEuCountriesResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseEuCountriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 基础信息-欧盟国家参数列表 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@RestController
@Api(tags = "BaseEuCountries VAT基础信息-欧盟国家参数")
@ApiResource(name = "BaseEuCountries VAT基础信息-欧盟国家参数", path = "/baseEuCountries")
public class BaseEuCountriesController {
    private final String ControllerName = "VAT基础信息-欧盟国家参数";

    @Autowired
    private IBaseEuCountriesService service;

    @PostResource(name = ControllerName + "列表查询", path = "/list", menuFlag = true)
    @ApiOperation(value = ControllerName + "列表查询", response = BaseEuCountriesResult.class)
    @BusinessLog(title = "BaseEuCountries VAT基础信息-欧盟国家参数-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody BaseEuCountriesParam param) {
        return ResponseData.success(service.queryListPage(param));
    }

    @PostResource(name = ControllerName + "修改", path = "/edit", requiredPermission = false)
    @ApiOperation(value = ControllerName + "修改", response = ResponseData.class)
    @BusinessLog(title = "BaseEuCountries VAT基础信息-欧盟国家参数-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody BaseEuCountriesParam param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.error(ControllerName + "修改失败，原因：获取数据异常，请确认传入数据。");
        }
        try {
            return service.edit(param);
        } catch (Exception ex) {
            return ResponseData.error(ControllerName + "修改异常，原因：" + ex.getMessage());
        }
    }

    @PostResource(name = ControllerName + "新增", path = "/add", requiredPermission = false)
    @ApiOperation(value = ControllerName + "新增", response = ResponseData.class)
    @BusinessLog(title = "BaseEuCountries VAT基础信息-欧盟国家参数-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody BaseEuCountriesParam param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.error(ControllerName + "新增失败，原因：获取数据异常，请确认传入数据。");
        }
        try {
            return service.save(param);
        } catch (Exception ex) {
            return ResponseData.error(ControllerName + "新增异常，原因：" + ex.getMessage());
        }
    }

    @GetResource(name = ControllerName+"英文简称下拉", path = "/euCountry",requiredPermission = false)
    @ApiOperation(value = ControllerName+"英文简称下拉", response = BaseEuCountriesResult.class)
    public ResponseData euCountry() {
        return service.euCountry();
    }

    @GetResource(name = ControllerName+"中文简称下拉", path = "/cnCountry",requiredPermission = false)
    @ApiOperation(value = ControllerName+"中文简称下拉", response = BaseEuCountriesResult.class)
    public ResponseData cnCountry(){
        return service.cnCountry();
    }

    @GetResource(name = ControllerName+"英文全称下拉", path = "/enCountry",requiredPermission = false)
    @ApiOperation(value = ControllerName+"英文全称下拉", response = BaseEuCountriesResult.class)
    public ResponseData enCountry(){
        return service.cnCountry();
    }
}
