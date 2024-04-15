package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoRecordResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsNoRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物流单费用操作记录 前端控制器
 * </p>
 *
 * @author ty
 * @since 2024-03-19
 */
@RestController
@Api(tags = "物流单费用操作记录")
@ApiResource(name = "物流单费用操作记录", path = "/lsLogisticsNoRecord")
public class LsLogisticsNoRecordController {

    @Autowired
    private ILsLogisticsNoRecordService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "物流单费用操作记录", path = "/queryPage")
    @ApiOperation(value = "分页查询列表", response = LsLogisticsNoRecordResult.class)
    @BusinessLog(title = "物流单费用操作记录-分页查询列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody LsLogisticsNoRecordParam param) {
        return service.queryPage(param);
    }

}
