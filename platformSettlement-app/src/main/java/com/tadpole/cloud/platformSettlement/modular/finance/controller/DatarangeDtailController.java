package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.DatarangeDtailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DatarangeDtailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IDatarangeDtailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* datarange源数据 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "datarange明细数据", path = "/datarangeDetail")
@Api(tags = "datarange明细数据")
public class DatarangeDtailController {

    @Autowired
    private IDatarangeDtailService datarangeDtailService;

    /**
     * 刷新settlement财务分类明细
     *
     * @author gal
     * @Date 2021-6-02
     */
    @GetResource(name = "datarange刷新财务明细", path = "/refreshSettlementDetail")
    @ApiOperation(value = "datarange刷新财务明细", response = DatarangeDtailResult.class)
    @BusinessLog(title = "datarange明细数据-datarange刷新财务明细",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData refreshDataRangeDetail(DatarangeDtailParam param) {
        datarangeDtailService.RefreshFinancialClass(param);
        return ResponseData.success();
    }
}
