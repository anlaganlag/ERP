package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.hutool.core.collection.CollUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.ApacheEmailAttachments;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailListingParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailListingResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SubsidySummaryResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.FinanceReportTypes;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.SubsidySummaryCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementDetailListingService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.ApacheEmailConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 结算单明细(原币) 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Slf4j
@RestController
@ApiResource(name = "结算单明细(原币)", path = "/settlementDetailListing")
@Api(tags = "结算单明细(原币)")
public class SettlementDetailListingController {

    @Autowired
    private ISettlementDetailListingService service;
    @Autowired
    private ApacheEmailConsumer apacheEmailConsumer;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 原币刷Listing
     */
    private static final String SETTLEMENT_REFRESH_LISTING = "SETTLEMENT_REFRESH_LISTING";

    @PostResource(name = "AZ结算单明细(原币)", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "AZ结算单明细(原币)", response = SettlementDetailListingResult.class)
    @BusinessLog(title = "结算单明细(原币)-AZ结算单明细(原币)列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody SettlementDetailListingParam param) {
        PageResult<SettlementDetailListingResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "结算单明细确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "结算单明细确认", response = SettlementDetailListingResult.class)
    @BusinessLog(title = "结算单明细(原币)-结算单明细确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(SettlementDetailListingParam param) throws Exception {

        //批量确认前需要批量刷listing
        service.afreshListing(param);

        service.confirm(param);
        return ResponseData.success();
    }

    @PostResource(name = "结算单明细批量确认", path = "/confirmBatch", requiredPermission = false)
    @ApiOperation(value = "结算单明细批量确认", response = SettlementDetailListingResult.class)
    @BusinessLog(title = "结算单明细(原币)-结算单明细批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody SettlementDetailListingParam param) throws Exception {
        ResponseData rd =service.confirmBatch(param);
        return rd;
    }

    @PostResource(name = "重刷listing", path = "/afreshListing", requiredPermission = false)
    @ApiOperation(value = "重刷listing", response = SettlementDetailListingResult.class)
    @BusinessLog(title = "结算单明细(原币)-重刷listing",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData afreshListing(@RequestBody SettlementDetailListingParam param) throws Exception {
        service.afreshListing(param);
        return ResponseData.success();
    }

    @PostResource(name = "批量刷listing", path = "/refreshListing", requiredPermission = false)
    @ApiOperation(value = "批量刷listing", response = SettlementDetailListingResult.class)
    @BusinessLog(title = "结算单明细(原币)-批量刷listing",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshListing(@RequestBody SettlementDetailListingParam param) throws Exception {
        if(redisTemplate.hasKey(SETTLEMENT_REFRESH_LISTING)){
            return ResponseData.error("批量刷listing进行中，请稍后再试!");
        }
        try {
            redisTemplate.boundValueOps(SETTLEMENT_REFRESH_LISTING).set("批量刷listing进行中", Duration.ofSeconds(3600));
            service.updateDetailList(param);
            return ResponseData.success();
        }catch (Exception e) {
            log.info("批量刷listing异常，异常信息[{}]", e);
            return ResponseData.error("批量刷listing异常!");
        } finally {
            redisTemplate.delete(SETTLEMENT_REFRESH_LISTING);
        }
    }

    @PostResource(name = "获取汇总金额", path = "/getQuantity", requiredPermission = false)
    @ApiOperation(value = "获取汇总金额", response = SettlementDetailListingResult.class)
    @BusinessLog(title = "结算单明细(原币)-获取汇总金额",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody SettlementDetailListingParam param) {
        SettlementDetailListingResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "导出结算单明细列表", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出结算单明细列表")
    @BusinessLog(title = "结算单明细(原币)-导出结算单明细列表",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportStatementIncomeList(@RequestBody SettlementDetailListingParam param, HttpServletResponse response) throws IOException {
        List<SettlementDetailListingResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出结算单明细列表(原币).xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SettlementDetailListingResult.class).sheet("导出结算单明细列表(原币)").doWrite(pageBySpec);

    }

    @GetResource(name = "下载导入修改sku", path = "/downloadSku", requiredPermission = false, requiredLogin = false)
    @ApiOperation("下载导入修改sku")
    public void download(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/下载导入修改sku.xlsx");
    }
    /**
     * 导入修改sku
     *
     */
    @ParamValidator
    @PostResource(name = "导入修改sku", path = "/uploadSku", requiredPermission = false)
    @ApiOperation(value = "导入修改sku")
    @BusinessLog(title = "结算单明细(原币)-导入修改sku",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData uploadSku(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.uploadSku(file);
    }

    @GetResource(name = "确认状态下拉", path = "/status",requiredPermission = false)
    @ApiOperation(value = "确认状态下拉", response = SubsidySummaryResult.class)
    public ResponseData status() throws Exception {

        return ResponseData.success(SubsidySummaryCheckStatus.getEnumList());
    }

    @GetResource(name = "报告类型下拉", path = "/types",requiredPermission = false)
    @ApiOperation(value = "报告类型下拉", response = SubsidySummaryResult.class)
    public ResponseData types() throws Exception {

        return ResponseData.success(FinanceReportTypes.getEnumList());
    }

    @PostResource(name = "发邮件", path = "/sendEmail", requiredPermission = false)
    @ApiOperation(value = "发邮件")
    @BusinessLog(title = "结算单明细(原币)-发邮件",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData sendEmail(@RequestBody SettlementDetailListingParam param)  {
        try {
            //刷新listing最晚时间
            service.updateLatestDate(param);

            List<SettlementDetailListingResult> resultList = service.emailList();
            if (CollUtil.isEmpty(resultList)) {
                return ResponseData.error("无缺少sku");
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1000);
            EasyExcel.write(outputStream, SettlementDetailListingResult.class).sheet("结算单明细(原币)缺失listings导出")
                    .doWrite(resultList);
            String[] sendToEmailArr =  param.getEmailList().toArray(new String[param.getEmailList().size()]);
            List<ApacheEmailAttachments> attachmentsList = new ArrayList<>();
            ApacheEmailAttachments attachments = new ApacheEmailAttachments();
            String subject = "结算单明细(原币)缺失listings";
            String msg = "缺失listings最晚于"+param.getLatestDate()+"前完成listings信息补充，否则收入或费用无法归属到相应的事业部team上，请知悉，谢谢！";
            attachments.setBytes(outputStream.toByteArray());
            attachments.setFileName("结算单明细(原币)缺失listings导出.xlsx");
            attachmentsList.add(attachments);
            apacheEmailConsumer.sendEmailsWithOnlineAttachments(subject, msg, sendToEmailArr, attachmentsList);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseData.success();
    }

}
