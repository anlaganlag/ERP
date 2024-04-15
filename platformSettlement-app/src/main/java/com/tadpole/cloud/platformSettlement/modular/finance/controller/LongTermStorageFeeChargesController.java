package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.hutool.core.collection.CollUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.ApacheEmailAttachments;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LongTermStorageFeeCharges;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LongTermStorageFeeChargesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params. LongTermStorageFeeChargesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result. LongTermStorageFeeChargesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.VerifyExceptionEnum;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILongTermStorageFeeChargesService;
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
* 长期仓储费 前端控制器
* </p>
*
* @author S20190161
* @since 2022-10-12
*/

@RestController
@Api(tags = "长期仓储费")
@ApiResource(name = "长期仓储费", path = "/longTermStorageFee", menuFlag = true)
public class LongTermStorageFeeChargesController {

    @Autowired
    private ILongTermStorageFeeChargesService service;

    @Autowired
    private ApacheEmailConsumer apacheEmailConsumer;


    @PostResource(name = "长期仓储费列表", path = "/queryListPage", requiredLogin = false)
    @ApiOperation(value = "长期仓储费列表", response = SettlementReportResult.class)
    @BusinessLog(title = "长期仓储费-长期仓储费列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody LongTermStorageFeeChargesParam param) {
        var pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "批量删除", path = "/deleteBatch")
    @ApiOperation(value = "批量删除")
    @BusinessLog(title = "长期仓储费-批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody LongTermStorageFeeChargesParam param) {
        if (param.getSysShopsNames()!=null && param.getSysShopsNames().size()>0
                && param.getSysSites()!=null && param.getSysSites().size()>0
                && StringUtils.isNotEmpty(param.getStartDur())
                && StringUtils.isNotEmpty(param.getEndDur())
        ) {
            var result = service.deleteBatch(param);
            return result==0? ResponseData.error(VerifyExceptionEnum.FIN_COM_DEL.getMessage()): ResponseData.success("删除记录数：" + result);
        }
        return ResponseData.error(VerifyExceptionEnum.FIN_MSF_DB.getMessage());
    }

    @PostResource(name = "批量确认", path = "/updateBatch")
    @ApiOperation(value = "批量确认")
    @BusinessLog(title = "长期仓储费-批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateBatch(@RequestBody LongTermStorageFeeChargesParam param) {
        if (param.getSysShopsNames()!=null && param.getSysShopsNames().size()>0
                && StringUtils.isNotEmpty(param.getStartDur())
                && StringUtils.isNotEmpty(param.getEndDur())
        ) {
            var result = service.updateBatch(param);
            if (result == -1) {
                return ResponseData.error("长期仓储费存在事业部为空,请先刷listing");
            }
            return result==0? ResponseData.error(VerifyExceptionEnum.FIN_COM_UPD.getMessage()):ResponseData.success("确认记录数：" + result);
        }
        return ResponseData.error(VerifyExceptionEnum.FIN_MSF_UB.getMessage());
    }

    @PostResource(name = "导出", path = "/export")
    @ApiOperation(value = "导出")
    @BusinessLog(title = "长期仓储费-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody LongTermStorageFeeChargesParam param, HttpServletResponse response) throws IOException {
        var list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("长期仓储费.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LongTermStorageFeeCharges.class).sheet("长期仓储费").doWrite(list);
        return ResponseData.success();
    }

    //定时JOB刷新
    @PostResource(name = "Job刷仓储费", path = "/afreshStorageFee",requiredLogin = false)
    @ApiOperation(value = "Job刷仓储费")
    @BusinessLog(title = "长期仓储费-Job刷仓储费",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData afreshStorageFee() {
        service.afreshStorageFee();
        return ResponseData.success();
    }


    @PostResource(name = "刷fnskuListings", path = "/fnskuFillLongListing",requiredLogin = false)
    @ApiOperation(value = "刷fnskuListings")
    @BusinessLog(title = "长期仓储费用-刷fnskuListings",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData fnskuFillLongListing(@RequestBody LongTermStorageFeeChargesParam param) {
        service.fnskuFillLongListing(param);
        return ResponseData.success();
    }


    @PostResource(name = "发邮件", path = "/sendEmail", requiredPermission = false)
    @ApiOperation(value = "发邮件")
    @BusinessLog(title = "长期仓储费缺失listing-发邮件",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData sendEmail(@RequestBody  LongTermStorageFeeChargesParam param)  {
        try {
            //刷新listing最晚时间
            service.updateLatestDate(param);

            List< LongTermStorageFeeChargesResult> resultList = service.emailList();
            if (CollUtil.isEmpty(resultList)) {
                return ResponseData.error("无缺少sku");
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
            EasyExcel.write(outputStream,  LongTermStorageFeeChargesResult.class).sheet("月仓储明细缺失listings导出")
                    .doWrite(resultList);
            String[] sendToEmailArr =  param.getEmailList().toArray(new String[param.getEmailList().size()]);
            List<ApacheEmailAttachments> attachmentsList = new ArrayList<>();
            ApacheEmailAttachments attachments = new ApacheEmailAttachments();
            String subject = "长期仓储费缺失listing明细缺失listings";
            String msg = "缺失listings最晚于"+param.getUpdateTime()+"前完成listings信息补充，否则长期仓储费缺失listing无法归属到相应的事业部team上，请知悉，谢谢！";
            attachments.setBytes(outputStream.toByteArray());
            attachments.setFileName("长期仓储费缺失listing明细缺失listings导出.xlsx");
            attachmentsList.add(attachments);
            apacheEmailConsumer.sendEmailsWithOnlineAttachments(subject, msg, sendToEmailArr, attachmentsList);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseData.success();
    }

}
