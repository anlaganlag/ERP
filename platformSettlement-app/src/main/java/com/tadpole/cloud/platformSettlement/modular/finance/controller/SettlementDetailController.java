package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* settlement明细数据 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "settlement明细数据", path = "/settlementDetail")
@Api(tags = "settlement明细数据")
public class SettlementDetailController {

    @Autowired
    private ISettlementDetailService settlementDetailService;

    /**
     * 刷新settlement财务分类明细
     *
     * @author gal
     * @Date 2021-6-02
     */
    @GetResource(name = "settlement刷新财务明细", path = "/refreshSettlementDetail",requiredPermission = false)
    @ApiOperation(value = "settlement刷新财务明细", response = SettlementDetailResult.class)
    @BusinessLog(title = "settlement明细数据-settlement刷新财务明细",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshSettlementDetail(SettlementDetailParam param) {
        settlementDetailService.refreshFinancialClass(param);
        return ResponseData.success();
    }
}
