package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLpDepositPrepaymentRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLpDepositPrepaymentRecordResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLpDepositPrepaymentRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物流商押金&预付操作记录 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-12-21
 */
@RestController
@Api(tags = "物流商押金&预付操作记录")
@ApiResource(name = "物流商押金&预付操作记录", path = "/lsLpDepositPrepaymentRecord")
public class LsLpDepositPrepaymentRecordController {

    @Autowired
    private ILsLpDepositPrepaymentRecordService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "物流商押金&预付操作记录", path = "/queryPage")
    @ApiOperation(value = "分页查询列表", response = LsLpDepositPrepaymentRecordResult.class)
    @BusinessLog(title = "物流商押金&预付操作记录-分页查询列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody LsLpDepositPrepaymentRecordParam param) {
        return service.queryPage(param);
    }

}
