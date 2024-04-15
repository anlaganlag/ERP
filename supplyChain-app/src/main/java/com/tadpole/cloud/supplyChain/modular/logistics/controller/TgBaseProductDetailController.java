package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBaseProductDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgBaseProductDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgBaseProductDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 通关产品详细信息 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@RestController
@Api(tags = "通关产品详细信息")
@ApiResource(name = "通关产品详细信息", path = "/tgBaseProductDetail")
public class TgBaseProductDetailController {

    @Autowired
    private ITgBaseProductDetailService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "通关产品详细信息", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgBaseProductDetailResult.class)
    @BusinessLog(title = "通关产品详细信息-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgBaseProductDetailParam param) {
        return service.queryPage(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "通关产品详细信息-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgBaseProductDetailParam param) {
        return service.delete(param);
    }
}
