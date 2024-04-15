package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBoxInfoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgBoxInfoResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgBoxInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  报关外箱前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-07-07
 */
@RestController
@Api(tags = "报关外箱")
@ApiResource(name = "报关外箱", path = "/tgBoxInfo")
public class TgBoxInfoController {

    @Autowired
    private ITgBoxInfoService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "报关外箱", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgBoxInfoResult.class)
    @BusinessLog(title = "报关外箱-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgBoxInfoParam param) {
        return service.queryPage(param);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "报关外箱-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TgBoxInfoParam param) {
        return service.add(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "报关外箱-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgBoxInfoParam param) {
        return service.delete(param);
    }

    /**
     * 编辑
     * @param param
     * @return
     */
    @PostResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "报关外箱-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody TgBoxInfoParam param) {
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
