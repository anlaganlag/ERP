package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsAgainCoeffRuleParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsAgainCoeffRuleResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsAgainCoeffRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 清关二次折算 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@RestController
@Api(tags = "清关二次折算")
@ApiResource(name = "清关二次折算", path = "/tgCustomsAgainCoeffRule")
public class TgCustomsAgainCoeffRuleController {

    @Autowired
    private ITgCustomsAgainCoeffRuleService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "清关二次折算", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgCustomsAgainCoeffRuleResult.class)
    @BusinessLog(title = "清关二次折算-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgCustomsAgainCoeffRuleParam param) {
        return service.queryPage(param);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "清关二次折算-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TgCustomsAgainCoeffRuleParam param) {
        return service.add(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "清关二次折算-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgCustomsAgainCoeffRuleParam param) {
        return service.delete(param);
    }

    /**
     * 编辑
     * @param param
     * @return
     */
    @PostResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "清关二次折算-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody TgCustomsAgainCoeffRuleParam param) {
        return service.edit(param);
    }
}
