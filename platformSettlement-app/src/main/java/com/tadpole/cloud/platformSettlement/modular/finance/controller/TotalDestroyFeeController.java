package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.hutool.core.collection.CollUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.ApacheEmailAttachments;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TotalDestroyFee;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonthlyStorageFeesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalDestroyFeeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonthlyStorageFeesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.TotalDestroyFeeResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.VerifyExceptionEnum;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.StationManualAllocationMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStationManualAllocationService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ITotalDestroyFeeService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.ApacheEmailConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 销毁移除费用统计 前端控制器
* </p>
*
* @author S20190161
* @since 2022-10-18
*/
@RestController
@Api(tags = "销毁移除费用统计")
@ApiResource(name = "销毁移除费用统计", path = "/totalDestroyFee")
public class TotalDestroyFeeController {

    @Autowired
    private ITotalDestroyFeeService service;

    @Autowired
    private IStationManualAllocationService stationManualAllocationService;

    @Autowired
    private ApacheEmailConsumer apacheEmailConsumer;

    @PostResource(name = "销毁移除费用合计列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "销毁移除费用合计列表")
    @BusinessLog(title = "销毁移除费用统计-销毁移除费用合计列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody TotalDestroyFeeParam param) {
        var pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "导出", path = "/export")
    @ApiOperation(value = "导出")
    @BusinessLog(title = "销毁移除费用统计-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TotalDestroyFeeParam param, HttpServletResponse response) throws IOException {
        var list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("销毁移除费用统计.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TotalDestroyFee.class).sheet("销毁移除费用统计").doWrite(list);
        return ResponseData.success();
    }

    //定时JOB刷新
    @PostResource(name = "刷新数据", path = "/afreshCount",requiredLogin = false)
    @ApiOperation(value = "刷新数据")
    @BusinessLog(title = "销毁移除费用统计-刷新数据",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData afreshCount(@RequestBody TotalDestroyFeeParam param) {
        service.afreshCount(param.getStartDur());
        return ResponseData.success();
    }

    @PostResource(name = "手动确认", path = "/confirm")
    @ApiOperation(value = "手动确认")
    @BusinessLog(title = "销毁移除费用统计-手动确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(@RequestBody TotalDestroyFeeParam param) {
        service.confirm(param);
        return ResponseData.success();
    }

    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "销毁移除费用统计-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TotalDestroyFeeParam param) {
        var resule=  service.add(param);
        switch (resule){
            case -1 :return ResponseData.error(VerifyExceptionEnum.FIN_TDF_ADD.getMessage());
            case -2:return ResponseData.error(VerifyExceptionEnum.FIN_TDF_ADR.getMessage());
        }
        return ResponseData.success();
    }

    @PostResource(name = "获取结算Id汇总明细差异费用", path = "/getFifferenceFee")
    @ApiOperation(value = "获取结算Id汇总明细差异费用")
    @BusinessLog(title = "销毁移除费用统计-获取结算Id汇总明细差异费用",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getFifferenceFee(@RequestBody TotalDestroyFeeParam param) {
        return ResponseData.success(service.getFifferenceFee(param));
    }

    @PostResource(name = "获取SKU产品信息", path = "/getProductBySku")
    @ApiOperation(value = "获取SKU产品信息")
    @BusinessLog(title = "销毁移除费用统计-获取SKU产品信息",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getProductBySku(@RequestBody TotalDestroyFeeParam param) {
        return ResponseData.success(service.getProductBySku(param));
    }


    @PostResource(name = "刷销毁费和移除费汇总行", path = "/freshDisposeReturnFee")
    @ApiOperation(value = "刷销毁费和移除费汇总行")
    @BusinessLog(title = "刷销毁费和移除费汇总行",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData freshDisposeReturnFee(@RequestBody TotalDestroyFeeParam param) {
        service.freshDisposeReturnFee(param);
        return ResponseData.success();
    }

    @PostResource(name = "删除", path = "/deleteById")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "销毁移除费用统计-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteById(@RequestBody TotalDestroyFeeParam param) {
        service.deleteById(param);
        return ResponseData.success();
    }

    @PostResource(name = "编辑", path = "/updateById")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "销毁移除费用统计-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateById(@RequestBody TotalDestroyFeeParam param) {
        service.updateById(param);
        return ResponseData.success();
    }

    @PostResource(name = "销毁移除费-下推手动分摊v1", path = "/pushDestroyManualAllocV1")
    @ApiOperation(value = "销毁移除费-下推手动分摊v1")
    @BusinessLog(title = "销毁移除费-下推手动分摊v1",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData pushDestroyManualAlloc(@RequestBody TotalDestroyFeeParam param) throws ParseException {
        return service.pushDestroyManualAllocSql(param);
    }

    @PostResource(name = "销毁移除费-下推手动分摊", path = "/pushDestroyManualAlloc")
    @ApiOperation(value = "销毁移除费-下推手动分摊")
    @BusinessLog(title = "销毁移除费-下推手动分摊",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData pushDestroyManualAllocSql(@RequestBody TotalDestroyFeeParam param) throws ParseException {
        return service.pushDestroyManualAllocSql(param);
    }


    @PostResource(name = "销毁移除费-刷fnskuListing", path = "/fnskuFillDestroyListing")
    @ApiOperation(value = "销毁移除费-刷fnskuListing")
    @BusinessLog(title = "销毁移除费-刷fnskuListing",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData fnskuFillDestroyListingSql(@RequestBody StationManualAllocationParam param) throws ParseException {
         stationManualAllocationService.fnskuFillDestroyListing(param);
        return ResponseData.success();
    }



    @PostResource(name = "发邮件", path = "/sendEmail", requiredPermission = false)
    @ApiOperation(value = "发邮件")
    @BusinessLog(title = "销毁移除费-发邮件",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData sendEmail(@RequestBody TotalDestroyFeeParam param)  {
        try {
            //刷新listing最晚时间
            service.updateLatestDate(param);

            List<TotalDestroyFeeResult> resultList = service.emailList();
            if (CollUtil.isEmpty(resultList)) {
                return ResponseData.error("无缺少sku");
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
            EasyExcel.write(outputStream, TotalDestroyFeeResult.class).sheet("销毁移除明细缺失listings导出")
                    .doWrite(resultList);
            String[] sendToEmailArr =  param.getEmailList().toArray(new String[param.getEmailList().size()]);
            List<ApacheEmailAttachments> attachmentsList = new ArrayList<>();
            ApacheEmailAttachments attachments = new ApacheEmailAttachments();
            String subject = "销毁移除费明细缺失listings";
            String msg = "缺失listings最晚于"+param.getUpdateTime()+"前完成listings信息补充，否则销毁移除费无法归属到相应的事业部team上，请知悉，谢谢！";
            attachments.setBytes(outputStream.toByteArray());
            attachments.setFileName("销毁移除费明细缺失listings导出.xlsx");
            attachmentsList.add(attachments);
            apacheEmailConsumer.sendEmailsWithOnlineAttachments(subject, msg, sendToEmailArr, attachmentsList);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseData.success();
    }


}
