package com.tadpole.cloud.operationManagement.modular.brand.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.*;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbTrademarkCertificateService;
import org.springframework.web.bind.annotation.RestController;



/**
* <p>
* 商标证书管理表 前端控制器
* </p>
*
* @author S20190161
* @since 2023-10-27
*/
@RestController
@Api(tags = "商标证书管理表")
@ApiResource(name = "商标证书管理表", path = "/brand/trademarkCertificate")
public class TbTrademarkCertificateController {

    @Autowired
    private TbTrademarkCertificateService service;

    @ApiOperation(value = "分页查询", response = TbTrademarkCertificateResult.class)
    @PostResource(name = "商标证书管理-分页查询", path = "/getPage", menuFlag = true)
    @BusinessLog(title = "分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getPage(@RequestBody TbTrademarkCertificatePageParam param) {

        return ResponseData.success(service.getPage(param));
    }


    @ApiOperation(value = "提醒信息")
    @GetResource(name="提醒信息",path = "/detail")
    @BusinessLog(title = "提醒信息", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestParam Long id) {
        return ResponseData.success(service.queryById(id));

    }
    @ApiOperation(value = "补贴信息")
    @GetResource(name="补贴信息",path = "/detail2")
    @BusinessLog(title = "补贴信息", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail2(@RequestParam Long id) {
        return ResponseData.success(service.queryById(id));

    }
    @ApiOperation(value = "延期信息")
    @GetResource(name="延期信息",path = "/detail3")
    @BusinessLog(title = "延期信息", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail3(@RequestParam Long id) {
        return ResponseData.success(service.queryById(id));

    }
    @ApiOperation(value = "交接")
    @GetResource(name="交接",path = "/detail4")
    @BusinessLog(title = "交接", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail4(@RequestParam Long id) {
        return ResponseData.success(service.queryById(id));

    }
    @ApiOperation(value = "确认")
    @GetResource(name="确认",path = "/detail5")
    @BusinessLog(title = "确认", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail5(@RequestParam Long id) {
        return ResponseData.success(service.queryById(id));

    }

    @ApiOperation(value = "获取商标证书信息By SalesBrand")
    @GetResource(path = "/queryByBrand")
    @BusinessLog(title = "获取商标证书信息By SalesBrand", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryByBrand(@RequestParam String salesBrand) {
        return ResponseData.success(service.queryByBrand(salesBrand));

    }

    @ApiOperation(value = "保存提醒信息")
    @PostResource(name = "保存提醒信息", path = "/save")
    @BusinessLog(title = "保存提醒信息", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody @Valid TbTrademarkCertificateParam param) {
        service.save(param);
        return ResponseData.success();

    }

    @ApiOperation(value = "保存补贴信息")
    @PostResource(name = "保存补贴信息", path = "/save2")
    @BusinessLog(title = "保存补贴信息", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save2(@RequestBody @Valid TbTrademarkCertificateParam param) {
        service.save(param);
        return ResponseData.success();

    }
    @ApiOperation(value = "保存延期信息")
    @PostResource(name = "保存延期信息", path = "/save3")
    @BusinessLog(title = "保存延期信息", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save3(@RequestBody @Valid TbTrademarkCertificateParam param) {
        service.save(param);
        return ResponseData.success();

    }
}
