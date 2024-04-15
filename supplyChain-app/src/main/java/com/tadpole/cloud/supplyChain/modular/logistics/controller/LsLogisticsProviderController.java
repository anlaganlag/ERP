package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物流商管理 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
@RestController
@Api(tags = "物流商管理")
@ApiResource(name = "物流商管理", path = "/lsLogisticsProvider")
public class LsLogisticsProviderController {

    @Autowired
    private ILsLogisticsProviderService service;

    /**
     * 物流商信息下拉
     * @return
     */
    @GetResource(name = "物流商信息下拉", path = "/logisticsProviderSelect")
    @ApiOperation(value = "物流商信息下拉", response = LsLogisticsProvider.class)
    public ResponseData logisticsProviderSelect() {
        return ResponseData.success(service.logisticsProviderSelect());
    }

    /**
     * 物流商编码下拉
     * @return
     */
    @GetResource(name = "物流商编码下拉", path = "/lpCodeSelect")
    @ApiOperation(value = "物流商编码下拉", response = LsLogisticsProvider.class)
    public ResponseData lpCodeSelect() {
        return ResponseData.success(service.lpCodeSelect());
    }

    /**
     * 物流商名称下拉
     * @return
     */
    @GetResource(name = "物流商名称下拉", path = "/lpNameSelect")
    @ApiOperation(value = "物流商名称下拉", response = LsLogisticsProvider.class)
    public ResponseData lpNameSelect() {
        return ResponseData.success(service.lpNameSelect());
    }

    /**
     * 物流商简称下拉
     * @return
     */
    @GetResource(name = "物流商简称下拉", path = "/lpSimpleNameSelect")
    @ApiOperation(value = "物流商简称下拉", response = LsLogisticsProvider.class)
    public ResponseData lpSimpleNameSelect() {
        return ResponseData.success(service.lpSimpleNameSelect());
    }

    /**
     * 统一社会信用代码下拉
     * @return
     */
    @GetResource(name = "统一社会信用代码下拉", path = "/lpUnisoccreCodeSelect")
    @ApiOperation(value = "统一社会信用代码下拉", response = LsLogisticsProvider.class)
    public ResponseData lpUniSocCreCodeSelect() {
        return ResponseData.success(service.lpUniSocCreCodeSelect());
    }

}
