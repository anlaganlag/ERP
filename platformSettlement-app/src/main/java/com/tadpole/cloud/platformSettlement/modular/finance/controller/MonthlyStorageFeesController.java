package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.ApacheEmailAttachments;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.tadpole.cloud.platformSettlement.api.finance.entity.MonthlyStorageFees;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonthlyStorageFeesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonthlyStorageFeesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonthlyStorageFeesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationManualAllocationResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.VerifyExceptionEnum;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.MonthlyStorageFeesMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IMonthlyStorageFeesService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.ApacheEmailConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 月储存费用 前端控制器
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
@RestController
@ApiResource(name = "月仓储费用", path = "/monthlyStorageFees")
@Api(tags = "月仓储费用")
public class MonthlyStorageFeesController {

    @Autowired
    private IMonthlyStorageFeesService service;


    @Autowired
    private ApacheEmailConsumer apacheEmailConsumer;

    @PostResource(name = "月仓储费用列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "月仓储费用列表")
    @BusinessLog(title = "月仓储费用-月仓储费用列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody MonthlyStorageFeesParam param) {
        var pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "批量删除", path = "/deleteBatch")
    @ApiOperation(value = "批量删除")
    @BusinessLog(title = "月仓储费用-批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody MonthlyStorageFeesParam param) {
        if (param.getSysShopsNames()!=null && param.getSysShopsNames().size()>0
                && param.getSysSites()!=null && param.getSysSites().size()>0
                && StringUtils.isNotEmpty(param.getStartDur())
                && StringUtils.isNotEmpty(param.getEndDur())
        ) {
            var result=service.deleteBatch(param);
            return result==0? ResponseData.error(VerifyExceptionEnum.FIN_COM_DEL.getMessage()):ResponseData.success("删除记录数："+result);
        }
        return ResponseData.error(VerifyExceptionEnum.FIN_MSF_DB.getMessage());

    }

    @PostResource(name = "批量确认", path = "/updateBatch")
    @ApiOperation(value = "批量确认")
    @BusinessLog(title = "月仓储费用-批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateBatch(@RequestBody MonthlyStorageFeesParam param) {
        if (param.getSysShopsNames()!=null && param.getSysShopsNames().size()>0
                && StringUtils.isNotEmpty(param.getStartDur())
                && StringUtils.isNotEmpty(param.getEndDur())
        ) {
            var result= service.updateBatch(param);
            if (result == -1) {
                return ResponseData.error("月仓储费存在事业部为空,请先刷listing");
            }
            return result==0? ResponseData.error(VerifyExceptionEnum.FIN_COM_UPD.getMessage()):ResponseData.success("确认记录数："+result);
        }
        return ResponseData.error(VerifyExceptionEnum.FIN_MSF_UB.getMessage());
    }

    @PostResource(name = "导出", path = "/export")
    @ApiOperation(value = "导出")
    @BusinessLog(title = "月仓储费用-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody MonthlyStorageFeesParam param, HttpServletResponse response) throws IOException {
        var list=service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("月仓储费用.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), MonthlyStorageFees.class).sheet("月仓储费用").doWrite(list);
        return ResponseData.success();
    }

    //定时JOB刷新
    @PostResource(name = "Job刷仓储费", path = "/afreshStorageFee",requiredLogin = false)
    @ApiOperation(value = "Job刷仓储费")
    @BusinessLog(title = "月仓储费用-Job刷仓储费",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData afreshStorageFee() {
        service.afreshStorageFee();
        return ResponseData.success();
    }

    @PostResource(name = "刷fnskuListings", path = "/fnskuFillMonListing",requiredPermission = false)
    @ApiOperation(value = "刷fnskuListings", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-刷fnskuListings",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData fnskuFillMonListing(@RequestBody MonthlyStorageFeesParam param)  {
        service.fnskuFillMonListing(param);
        return ResponseData.success();
    }

    @PostResource(name = "发邮件", path = "/sendEmail", requiredPermission = false)
    @ApiOperation(value = "发邮件")
    @BusinessLog(title = "月仓储费-发邮件",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData sendEmail(@RequestBody MonthlyStorageFeesParam param)  {
        try {
            //刷新listing最晚时间
            service.updateLatestDate(param);

            List<MonthlyStorageFeesResult> resultList = service.emailList();
            if (CollUtil.isEmpty(resultList)) {
                return ResponseData.error("无缺少sku");
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
            EasyExcel.write(outputStream, MonthlyStorageFeesResult.class).sheet("月仓储明细缺失listings导出")
                    .doWrite(resultList);
            String[] sendToEmailArr =  param.getEmailList().toArray(new String[param.getEmailList().size()]);
            List<ApacheEmailAttachments> attachmentsList = new ArrayList<>();
            ApacheEmailAttachments attachments = new ApacheEmailAttachments();
            String subject = "月仓储费明细缺失listings";
            String msg = "缺失listings最晚于"+param.getUpdateTime()+"前完成listings信息补充，否则月仓储费无法归属到相应的事业部team上，请知悉，谢谢！";
            attachments.setBytes(outputStream.toByteArray());
            attachments.setFileName("月仓储费明细缺失listings导出.xlsx");
            attachmentsList.add(attachments);
            apacheEmailConsumer.sendEmailsWithOnlineAttachments(subject, msg, sendToEmailArr, attachmentsList);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseData.success();
    }


}
