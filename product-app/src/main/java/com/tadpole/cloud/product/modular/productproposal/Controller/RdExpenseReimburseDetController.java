package com.tadpole.cloud.product.modular.productproposal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.productproposal.service.IRdExpenseReimburseDetService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 提案-研发费报销明细 前端控制器
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
@RestController
@Api(tags = "提案-研发费报销明细")
@ApiResource(name = "提案-研发费报销明细", path = "/rdExpenseReimburseDet")
public class RdExpenseReimburseDetController {

    @Autowired
    private IRdExpenseReimburseDetService service;

}
