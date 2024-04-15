package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsApplyDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsApplyDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 报关单明细 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@RestController
@Api(tags = "报关单明细")
@ApiResource(name = "报关单明细", path = "/tgCustomsApplyDetail")
public class TgCustomsApplyDetailController {

    @Autowired
    private ITgCustomsApplyDetailService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "分页查询列表", path = "/queryPage")
    @ApiOperation(value = "分页查询列表", response = TgCustomsApplyDetailResult.class)
    @BusinessLog(title = "报关单明细-分页列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgCustomsApplyDetailParam param) {
        return service.queryPage(param);
    }

    /**
     * 查询列表
     * @param param
     * @return
     */
    @PostResource(name = "查询列表", path = "/queryList")
    @ApiOperation(value = "查询列表", response = TgCustomsApplyDetailResult.class)
    @BusinessLog(title = "报关单明细-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryList(@RequestBody TgCustomsApplyDetailParam param) {
        return service.queryList(param);
    }

}
