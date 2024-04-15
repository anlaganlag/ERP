package com.tadpole.cloud.product.modular.productproposal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.productproposal.service.IRdCustProductDetService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 提案-定品明细 前端控制器
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
@RestController
@Api(tags = "提案-定品明细")
@ApiResource(name = "提案-定品明细", path = "/rdCustProductDet")
public class RdCustProductDetController {

    @Autowired
    private IRdCustProductDetService service;

}
