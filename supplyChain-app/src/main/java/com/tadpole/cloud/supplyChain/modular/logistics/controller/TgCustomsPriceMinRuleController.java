package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsPriceMinRuleParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsPriceMinRuleResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsPriceMinRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 最低清关价格 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@RestController
@Api(tags = "最低清关价格")
@ApiResource(name = "最低清关价格", path = "/tgCustomsPriceMinRule")
public class TgCustomsPriceMinRuleController {

    @Autowired
    private ITgCustomsPriceMinRuleService service;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "最低清关价格", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgCustomsPriceMinRuleResult.class)
    @BusinessLog(title = "最低清关价格-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgCustomsPriceMinRuleParam param) {
        return service.queryPage(param);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "最低清关价格-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TgCustomsPriceMinRuleParam param) {
        return service.add(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "最低清关价格-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgCustomsPriceMinRuleParam param) {
        return service.delete(param);
    }

    /**
     * 编辑
     * @param param
     * @return
     */
    @PostResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "最低清关价格-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody TgCustomsPriceMinRuleParam param) {
        return service.edit(param);
    }

    /**
     * 币别下拉
     * @return
     */
    @GetResource(name = "币别下拉", path = "/currencySelect")
    @ApiOperation(value = "币别下拉")
    public ResponseData currencySelect() {
        return ResponseData.success(fixedExchangeRateConsumer.originalCurrencyList());
    }
}
