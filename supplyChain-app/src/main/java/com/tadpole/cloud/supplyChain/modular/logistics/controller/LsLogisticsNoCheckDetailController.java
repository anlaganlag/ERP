package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoCheckDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsNoCheckDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物流单对账明细 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
@RestController
@Api(tags = "物流单对账明细")
@ApiResource(name = "物流单对账明细", path = "/lsLogisticsNoCheckDetail")
public class LsLogisticsNoCheckDetailController {

    @Autowired
    private ILsLogisticsNoCheckDetailService service;

    /**
     * 列表查询
     * @param param
     * @return
     */
    @PostResource(name = "列表查询", path = "/queryList")
    @ApiOperation(value = "列表查询", response = LsLogisticsNoCheckDetailResult.class)
    @BusinessLog(title = "物流单对账明细-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryList(@RequestBody LsLogisticsNoCheckDetailParam param) {
        return ResponseData.success(service.queryList(param));
    }

}
