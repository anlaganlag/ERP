package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveTrackDTO;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.DisposeRemoveTrackHistoryParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveTrackHistoryResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IDisposeRemoveTrackHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 销毁移除跟踪历史表 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-02-28
 */
@RestController
@ApiResource(name = "销毁移除跟踪历史表", path = "/disposeRemoveTrackHistory")
@Api(tags = "销毁移除跟踪历史表")
public class DisposeRemoveTrackHistoryController {

    @Autowired
    private IDisposeRemoveTrackHistoryService service;

    @PostResource(name = "销毁移除跟踪历史表", path = "/trackHistoryList", menuFlag = true)
    @ApiOperation(value = "销毁移除跟踪历史表", response = DisposeRemoveTrackDTO.class)
    @BusinessLog(title = "销毁移除跟踪历史表-销毁移除跟踪历史表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData trackHistoryList(@RequestBody DisposeRemoveTrackHistoryParam param) {
        return ResponseData.success(service.trackHistoryList(param));
    }

    @PostResource(name = "销毁移除跟踪历史表导出", path = "/trackHistoryExport", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "销毁移除跟踪历史表导出")
    @BusinessLog(title = "销毁移除跟踪历史表-销毁移除跟踪历史表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData trackHistoryExport(@RequestBody DisposeRemoveTrackHistoryParam param, HttpServletResponse response) throws IOException {
        List<DisposeRemoveTrackHistoryResult> list = service.trackHistoryExport(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("销毁移除跟踪列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), DisposeRemoveTrackHistoryResult.class).sheet("销毁移除跟踪列表").doWrite(list);
        return ResponseData.success();
    }

    /**
     * 销毁移除跟踪历史表汇总数量
     * @param param
     * @return
     */
    @PostResource(name = "销毁移除跟踪历史表汇总数量", path = "/getTrackHistoryQuantity", requiredPermission = false)
    @ApiOperation(value = "销毁移除跟踪历史表汇总数量")
    @BusinessLog(title = "销毁移除跟踪历史表-销毁移除跟踪历史表汇总数量",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getTrackQuantity(@RequestBody DisposeRemoveTrackHistoryParam param) {
        return service.getTrackHistoryQuantity(param);
    }

    @GetResource(name = "生成销毁移除跟踪历史表数据", path = "/generateHistoryTrack", requiredPermission = false)
    @ApiOperation(value = "生成销毁移除跟踪历史表数据")
    @BusinessLog(title = "销毁移除跟踪历史表-生成销毁移除跟踪历史表数据",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateHistoryTrack() {
        return service.generateHistoryTrack();
    }
}

