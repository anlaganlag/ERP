package com.tadpole.cloud.product.modular.productproposal.Controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.model.params.RdExpenseReimburseParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskProgressDetParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleTaskProgressDetService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 提案-开发样任务进度明细 前端控制器
 * </p>
 *
 * @author S20190096
 * @since 2024-02-29
 */
@RestController
@Api(tags = "提案-开发样任务进度明细")
@ApiResource(name = "提案-开发样任务进度明细", path = "/rdSampleTaskProgressDet")
public class RdSampleTaskProgressDetController {

    @Autowired
    private IRdSampleTaskProgressDetService progressDetService;

    @PostResource(name = "拿样任务-开发样任务进度明细", path = "/listTaskProgressDet")
    @ApiOperation(value = "拿样任务-开发样任务进度明细")
    @BusinessLog(title = "拿样任务-开发样任务进度明细",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listTaskProgressDet(@RequestBody RdSampleTaskProgressDetParam param) {

        return ResponseData.success(this.progressDetService.listTaskProgressDet(param));
    }

    @PostResource(name = "拿样任务-开发样任务进度添加", path = "/add")
    @ApiOperation(value = "拿样任务-开发样任务进度添加")
    @BusinessLog(title = "拿样任务-开发样任务进度添加",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody RdSampleTaskProgressDetParam param) {

        return ResponseData.success(this.progressDetService.add(param));
    }
}
