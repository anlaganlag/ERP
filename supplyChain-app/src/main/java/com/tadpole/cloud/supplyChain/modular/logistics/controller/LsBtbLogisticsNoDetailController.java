package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbLogisticsNoDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbLogisticsNoDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbLogisticsNoDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  BTB物流单明细前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
@RestController
@Api(tags = "BTB物流单明细")
@ApiResource(name = "BTB物流单明细", path = "/lsBtbLogisticsNoDetail")
public class LsBtbLogisticsNoDetailController {

    @Autowired
    private ILsBtbLogisticsNoDetailService service;

    /**
     * 列表查询
     * @param param
     * @return
     */
    @PostResource(name = "列表查询", path = "/queryList")
    @ApiOperation(value = "列表查询", response = LsBtbLogisticsNoDetailResult.class)
    @BusinessLog(title = "BTB物流单明细-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryList(@RequestBody LsBtbLogisticsNoDetailParam param) {
        return ResponseData.success(service.queryList(param));
    }

}
