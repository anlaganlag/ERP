package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SpotExchangeRate;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import com.tadpole.cloud.platformSettlement.modular.inventory.enums.RejectStatusEnum;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ErpBankAccountParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyBaseApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ErpBankAccountResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalDestroyBaseApplyResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemovalDestroyBaseApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
* <p>
*  前端控制器
* </p>
*
* @author cyt
* @since 2022-05-25
*/
@RestController
@ApiResource(name = "库存移除基础信息", path = "/removalDestroyBaseApply")
@Api(tags = "库存移除基础信息")
public class RemovalDestroyBaseApplyController {

    @Autowired
    private ISpotExchangeRateService currencyService;
    @Autowired
    private IRemovalDestroyBaseApplyService service;

    @GetResource(name = "Erp银行账号", path = "/bankAccountList",requiredPermission = false)
    @ApiOperation(value = "Erp银行账号", response = ErpBankAccountResult.class)
    public ResponseData bankAccountList(ErpBankAccountParam param) throws IOException {
        PageResult<ErpBankAccountResult> pageBySpec = service.bankAccountList(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "驳回节点下拉", path = "/types",requiredPermission = false)
    @ApiOperation(value = "驳回节点下拉", response = RemovalDestroyBaseApplyResult.class)
    public ResponseData types() throws Exception {
        return ResponseData.success(RejectStatusEnum.getEnumList());
    }

    @GetResource(name = "币别下拉", path = "/currencyList",requiredPermission = false)
    @ApiOperation(value = "币别下拉", response = RemovalDestroyBaseApplyResult.class)
    public ResponseData currencyList() throws Exception {
        List<SpotExchangeRate> list = currencyService.originalCurrencyList();
        return ResponseData.success(list);
    }

    @PostResource(name = "流程审批校验", path = "/processCheck",requiredPermission = false)
    @ApiOperation(value = "流程审批校验", response = RemovalDestroyBaseApplyResult.class)
    public ResponseData processCheck(@RequestBody RemovalDestroyBaseApplyParam param) throws Exception {
        return service.processCheck(param);
    }
}
