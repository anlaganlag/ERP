package com.tadpole.cloud.externalSystem.modular.k3.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.k3.entity.ViewSupplier;
import com.tadpole.cloud.externalSystem.modular.k3.provider.ViewBusinessProvider;
import com.tadpole.cloud.externalSystem.modular.k3.service.TranferApplyService;
import com.tadpole.cloud.externalSystem.modular.k3.service.ViewBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: Amte Ma
 * @version: 1.0
 * @date: 2023/10/31 <br>
 */
@RestController()
@ApiResource(name = "k3业务数据读取", path = "/k3business")
@Api(tags = "k3业务数据读取")
public class K3BusinessController {
    @Autowired
    ViewBusinessService viewBusinessProvider;

    @ApiOperation("获取代理机构")
    @GetResource(name = "获取代理机构", path = "/getAgencys")
    public ResponseData getAgencys(){
        return ResponseData.success(viewBusinessProvider.getAgencys());
    }

    @ApiOperation("获取供应商分组")
    @GetResource(name = "获取供应商分组", path = "/getSupplierGroup")
    public ResponseData getSupplierGroup(){
        return ResponseData.success(viewBusinessProvider.getSupplierGroup());
    }

    @ApiOperation("获取省份")
    @GetResource(name = "获取省份", path = "/getProvince")
    public ResponseData getProvince(){
        return ResponseData.success(viewBusinessProvider.getProvince());
    }

    @ApiOperation("获取城市")
    @GetResource(name = "获取城市", path = "/getCity")
    public ResponseData getCity(String province) {
        return ResponseData.success(viewBusinessProvider.getCity(province));
    }

    @ApiOperation("获取部门")
    @GetResource(name = "获取部门", path = "/getDepartment")
    public ResponseData getDepartment() {
        return ResponseData.success(viewBusinessProvider.getDepartment());
    }

    @ApiOperation("获取付款条件")
    @GetResource(name = "获取付款条件", path = "/getPaymentCondition")
    public ResponseData getPaymentCondition() {
        return ResponseData.success(viewBusinessProvider.getPaymentCondition());
    }

    @ApiOperation("获取供应商分类")
    @GetResource(name = "获取供应商分类", path = "/getSupplierClassify")
    public ResponseData getSupplierClassify() {
        return ResponseData.success(viewBusinessProvider.getSupplierClassify());
    }

    @ApiOperation("获取结算币别")
    @GetResource(name = "获取结算币别", path = "/getCurrency")
    public ResponseData getCurrency() {
        return ResponseData.success(viewBusinessProvider.getCurrency());
    }

    @ApiOperation("获取默认税率")
    @GetResource(name = "获取默认税率", path = "/getTaxRate")
    public ResponseData getTaxRate() {
        return ResponseData.success(viewBusinessProvider.getTaxRate());
    }

    @ApiOperation("获取结算方式")
    @GetResource(name = "获取结算方式", path = "/getSettleType")
    public ResponseData getSettleType() {
        return ResponseData.success(viewBusinessProvider.getSettleType());
    }

    @ApiOperation("获取税分类")
    @GetResource(name = "获取税分类", path = "/getTaxType")
    public ResponseData getTaxType() {
        return ResponseData.success(viewBusinessProvider.getTaxType());
    }

    @ApiOperation("获取银行-国家")
    @GetResource(name = "获取银行-国家", path = "/getBankCountry")
    public ResponseData getBankCountry() {
        return ResponseData.success(viewBusinessProvider.getBankCountry());
    }

    @ApiOperation("获取银行-网点")
    @GetResource(name = "获取银行-网点", path = "/getBankDetail")
    public ResponseData getBankDetail(String bankDetail) {
        return ResponseData.success(viewBusinessProvider.getBankDetail(bankDetail));
    }

    @ApiOperation("获取内部供应商")
    @GetResource(name = "获取内部供应商", path = "/getInnerSupplier")
    public ResponseData getInnerSupplier() {
        return ResponseData.success(viewBusinessProvider.getInnerSupplier());
    }

    @ApiOperation("获取人员id部门")
    @GetResource(name = "获取人员id部门", path = "/getDeptUserId")
    public ResponseData getDeptUserId(String account) {
        return ResponseData.success(viewBusinessProvider.getDeptUserId(account));
    }

    @ApiOperation("获取行业")
    @GetResource(name = "获取行业", path = "/getTrade")
    public ResponseData getTrade() {
        return ResponseData.success(viewBusinessProvider.getTrade());
    }

    @ApiOperation("获取供应商下单数量金额")
    @GetResource(name = "获取供应商下单数量金额", path = "/getSupplierQtyAmount")
    public ResponseData getSupplierQtyAmount(String supplierCode) {
        return ResponseData.success(viewBusinessProvider.getSupplierQtyAmount(supplierCode));
    }

    @ApiOperation("获取供应商未付款金额")
    @GetResource(name = "获取供应商未付款金额", path = "/getSupplierNotAmount")
    public ResponseData getSupplierNotAmount(String supplierCode) {
        return ResponseData.success(viewBusinessProvider.getSupplierNotAmount(supplierCode));
    }
}
