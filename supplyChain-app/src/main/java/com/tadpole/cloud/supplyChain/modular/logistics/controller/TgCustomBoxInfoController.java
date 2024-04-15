package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomBoxInfoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomBoxInfoResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomBoxInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 报关自定义外箱 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-08-18
 */
@RestController
@Api(tags = "报关自定义外箱")
@ApiResource(name = "报关自定义外箱", path = "/tgCustomBoxInfo")
public class TgCustomBoxInfoController {

    @Autowired
    private ITgCustomBoxInfoService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "报关自定义外箱", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgCustomBoxInfoResult.class)
    @BusinessLog(title = "报关自定义外箱-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgCustomBoxInfoParam param) {
        return service.queryPage(param);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "报关自定义外箱-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TgCustomBoxInfoParam param) {
        return service.add(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "报关自定义外箱-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgCustomBoxInfoParam param) {
        return service.delete(param);
    }

    /**
     * 编辑
     * @param param
     * @return
     */
    @PostResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "报关自定义外箱-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody TgCustomBoxInfoParam param) {
        return service.edit(param);
    }

    /**
     * 箱型下拉
     * @return
     */
    @GetResource(name = "箱型下拉", path = "/boxTypeSelect")
    @ApiOperation(value = "箱型下拉")
    public ResponseData boxTypeSelect() {
        return ResponseData.success(service.boxTypeSelect());
    }

}
