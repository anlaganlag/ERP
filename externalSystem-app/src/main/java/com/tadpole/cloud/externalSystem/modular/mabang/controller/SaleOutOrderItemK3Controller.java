package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderItemK3Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 根据K3仓库可用数量自动产生-销售出库单明细项 前端控制器
    * </p>
*
* @author lsy
* @since 2023-04-07
*/
@RestController
@Api(tags = "根据K3仓库可用数量自动产生-销售出库单明细项")
@ApiResource(name = "根据K3仓库可用数量自动产生-销售出库单明细项", path = "/saleOutOrderItemK3")
public class SaleOutOrderItemK3Controller {

    @Autowired
    private ISaleOutOrderItemK3Service service;

    }
