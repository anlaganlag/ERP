package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.SaleOutOrderK3QueryResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderK3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 根据K3仓库可用数量自动产生-销售出库单 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2023-04-07
 */
@RestController
@Api(tags = "根据K3仓库可用数量自动产生-销售出库单")
@ApiResource(name = "根据K3仓库可用数量自动产生-销售出库单", path = "/saleOutOrderK3")
public class SaleOutOrderK3Controller {

    @Autowired
    private ISaleOutOrderK3Service service;

    @PostResource(name = "根据K3仓库可用数量自动产生-销售出库单列表", path = "/list", requiredPermission = false,requiredLogin = false,menuFlag = true)
    @ApiOperation(value = "根据K3仓库可用数量自动产生-销售出库单列表", response = ResponseData.class)
    public ResponseData findPageBySpec(@RequestBody SaleOutOrderK3QueryResult param) {
        PageResult<SaleOutOrderK3QueryResult> pageResult = service.findPageBySpec(param);
        return ResponseData.success(pageResult);
    }

}
