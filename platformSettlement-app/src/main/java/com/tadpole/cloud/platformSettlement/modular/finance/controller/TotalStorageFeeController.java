package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TotalStorageFee;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalStorageFeeParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ITotalStorageFeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
* <p>
* 仓储费合计 数据来源 定期生成 前端控制器
* </p>
*
* @author S20190161
* @since 2022-10-14
*/
@RestController
@Api(tags = "仓储费合计 ")
@ApiResource(name = "仓储费合计", path = "/totalStorageFee")
public class TotalStorageFeeController {

    @Autowired
    private ITotalStorageFeeService service;

    @PostResource(name = "仓储费合计列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "仓储费合计列表")
    @BusinessLog(title = "仓储费合计-仓储费合计列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody TotalStorageFeeParam param) {
        var pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    //定时JOB刷新
    @PostResource(name = "重新刷新", path = "/afreshCount",requiredLogin = false)
    @ApiOperation(value = "重新刷新")
    @BusinessLog(title = "仓储费合计-重新刷新",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData afreshCount(@RequestBody TotalStorageFeeParam param) {
        service.afreshCount(param.getStartDur());
        return ResponseData.success();
    }

    @PostResource(name = "导出", path = "/export")
    @ApiOperation(value = "导出")
    @BusinessLog(title = "仓储费合计-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TotalStorageFeeParam param, HttpServletResponse response) throws IOException {
        var list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("仓储费合计.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TotalStorageFee.class).sheet("仓储费合计").doWrite(list);
        return ResponseData.success();
    }


    @PostResource(name = "更新仓储费源报告状态", path = "/updateStorageSrc")
    @ApiOperation(value = "更新仓储费源报告状态")
    @BusinessLog(title = "更新仓储费源报告状态",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateStorageSrc(@RequestBody StationManualAllocationParam param)  {
        service.updateStorageSrc(param);
        return ResponseData.success();
    }

    @PostResource(name = "下推仓储费0", path = "/pushStorageToManualAlloc0")
    @ApiOperation(value = "下推仓储费0")
    @BusinessLog(title = "下推仓储费0-下推手动分摊",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData pushStorageToManualAlloc0(@RequestBody StationManualAllocationParam param) throws ParseException {
        return service.pushStorageToManualAlloc0(param);
    }


    @PostResource(name = "分配分摊行状态", path = "/assignAllocLineStatus")
    @ApiOperation(value = "分配分摊行状态")
    @BusinessLog(title = "分配分摊行状态",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData assignAllocLineStatus(@RequestBody StationManualAllocationParam param) throws ParseException {
        return service.assignAllocLineStatus(param);
    }

    @PostResource(name = "填充分摊行值", path = "/fillAllocLineVales")
    @ApiOperation(value = "填充分摊行值")
    @BusinessLog(title = "填充分摊行值",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData fillAllocLineVales(@RequestBody StationManualAllocationParam param) throws ParseException {
        return service.fillAllocLineVales(param);
    }


    @PostResource(name = "获取仓储费明细", path = "/getSkuStorageDetail")
    @ApiOperation(value = "获取仓储费明细")
    @BusinessLog(title = "获取仓储费明细",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData getSkuStorageDetail(@RequestBody StationManualAllocationParam param) throws ParseException {
        return service.getSkuStorageDetail(param);
    }

    @PostResource(name = "插入超库容费", path = "/insertOverStorage")
    @ApiOperation(value = "插入超库容费")
    @BusinessLog(title = "插入超库容费",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData insertOverStorage(@RequestBody StationManualAllocationParam param) throws ParseException {
        return service.insertOverStorage(param);
    }



    @PostResource(name = "月仓储费和长期仓储未确认源报告数", path = "/noTicked")
    @ApiOperation(value = "月仓储费和长期仓储未确认源报告数")
    @BusinessLog(title = "月仓储费和长期仓储未确认源报告数",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData noTicked(@RequestBody StationManualAllocationParam param) throws ParseException {
        return ResponseData.success(service.noTicked(param));
    }








    @PostResource(name = "下推仓储费Sql", path = "/pushStorageToManualAllocSql")
    @ApiOperation(value = "下推仓储费Sql")
    @BusinessLog(title = "下推仓储费-下推手动分摊Sql",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData pushStorageToManualAllocSql(@RequestBody StationManualAllocationParam param) throws ParseException {
        return service.pushStorageToManualAllocSql(param);
    }



}
