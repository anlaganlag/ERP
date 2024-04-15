package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.BankSubjectCodeMcms;
import com.tadpole.cloud.platformSettlement.api.finance.entity.PaymentConfirmHandling;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.PaymentConfirmHandlingParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.PaymentConfirmHandlingResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IPaymentConfirmHandlingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* <p>
* 回款确认办理 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "AZ回款确认办理", path = "/paymentConfirmHandling")
@Api(tags = "AZ回款确认办理")
public class PaymentConfirmHandlingController {

    @Autowired
    private IPaymentConfirmHandlingService service;

    @PostResource(name = "AZ回款确认办理", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "AZ回款确认办理", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-AZ回款确认办理列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody PaymentConfirmHandlingParam param) {
        PageResult<PaymentConfirmHandlingResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "出纳确认", path = "/confirm", requiredPermission = false)
    @ApiOperation(value = "出纳确认", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-出纳确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(PaymentConfirmHandlingParam param) throws Exception {
        //去前后空格
        String collectionAccount =param.getCollectionAccount();
        collectionAccount = collectionAccount.trim();
        while (collectionAccount.startsWith("　")) {//这里判断是不是全角空格
            collectionAccount = collectionAccount.substring(1, collectionAccount.length()).trim();
        }
        while (collectionAccount.endsWith("　")) {
            collectionAccount = collectionAccount.substring(0, collectionAccount.length() - 1).trim();
        }
         param.setCollectionAccount(collectionAccount);
         BankSubjectCodeMcms res = service.getSubjectByBank(param);
         if(res!=null){
             param.setSubjectCode(res.getSubjectCode());
             param.setSubjectName(res.getSubjectName());
         }else{
             return ResponseData.error("ERP银行科目未创建，请先至erp创建！");
         }
         service.confirm(param);
        return ResponseData.success();
    }

    @GetResource(name = "会计审核", path = "/verify", requiredPermission = false)
    @ApiOperation(value = "会计审核", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-会计审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData verify(PaymentConfirmHandlingParam param) throws Exception {
        service.verify(param);
        return ResponseData.success();
    }

    @GetResource(name = "反审核", path = "/returnVerify", requiredPermission = false)
    @ApiOperation(value = "反审核", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-反审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData returnVerify(PaymentConfirmHandlingParam param) throws Exception {
        return service.returnVerify(param);
    }

    @GetResource(name = "同步erp", path = "/syncToErp", requiredPermission = false)
    @ApiOperation(value = "同步erp", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-同步erp",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData syncToErp(PaymentConfirmHandlingParam param) throws IOException {
        return service.syncToErp(param);
    }

    @PostResource(name = "出纳批量确认", path = "/confirmBatch", requiredPermission = false)
    @ApiOperation(value = "出纳批量确认", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-出纳批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody List<PaymentConfirmHandlingParam> params) throws Exception {
        List<PaymentConfirmHandlingParam> okParams =new ArrayList<>();
        String message ="";
        for (PaymentConfirmHandlingParam param : params) {
            //去前后空格
            String collectionAccount =param.getCollectionAccount();
            collectionAccount = collectionAccount.trim();
            while (collectionAccount.startsWith("　")) {//这里判断是不是全角空格
                collectionAccount = collectionAccount.substring(1, collectionAccount.length()).trim();
            }
            while (collectionAccount.endsWith("　")) {
                collectionAccount = collectionAccount.substring(0, collectionAccount.length() - 1).trim();
            }
            param.setCollectionAccount(collectionAccount);
            BankSubjectCodeMcms res = service.getSubjectByBank(param);
            if(res!=null){
                param.setSubjectCode(res.getSubjectCode());
                param.setSubjectName(res.getSubjectName());
                okParams.add(param);
            }else{
                message = "部分ERP银行科目未创建，请先至erp创建！";
            }
        }
        if(okParams.size()>0){
            service.confirmBatch(okParams);
        }
        return ResponseData.success(ResponseData.DEFAULT_SUCCESS_CODE,message,null);
    }

    @PostResource(name = "会计批量审核", path = "/verifyBatch", requiredPermission = false)
    @ApiOperation(value = "会计批量审核", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-会计批量审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData verifyBatch(@RequestBody List<PaymentConfirmHandlingParam> params) throws Exception {
        service.verifyBatch(params);
        return ResponseData.success();
    }

    @GetResource(name = "审核不通过", path = "/reject", requiredPermission = false)
    @ApiOperation(value = "审核不通过", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-审核不通过",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData reject(PaymentConfirmHandlingParam param) {
        service.reject(param);
        return ResponseData.success();
    }

    @PostResource(name = "审核批量不通过", path = "/rejectBatch", requiredPermission = false)
    @ApiOperation(value = "审核批量不通过", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-审核批量不通过",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData rejectBatch(@RequestBody  List<PaymentConfirmHandlingParam> params) throws IOException {
        service.rejectBatch(params);
        return ResponseData.success();
    }

    @PostResource(name = "批量同步erp", path = "/syncToErpBatch", requiredPermission = false)
    @ApiOperation(value = "批量同步erp", response = PaymentConfirmHandlingResult.class)
    @BusinessLog(title = "AZ回款确认办理-批量同步erp",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData syncToErpBatch(@RequestBody List<PaymentConfirmHandlingParam> params) throws IOException {
        return service.syncToErpBatch(params);
    }

    @GetResource(name = "回款银行", path = "/getBank", requiredPermission = false)
    @ApiOperation(value = "回款银行", response = PaymentConfirmHandlingResult.class)
    public ResponseData getBank(PaymentConfirmHandlingParam param) {
       List<PaymentConfirmHandlingResult> res =  service.getBank(param);
        return ResponseData.success(res);
    }

    @GetResource(name = "导出回款确认办理列表", path = "/exportPaymentConfirmHandling", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出回款确认办理列表")
    @BusinessLog(title = "AZ回款确认办理-导出回款确认办理列表",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportPaymentConfirmHandlingList(PaymentConfirmHandlingParam param, HttpServletResponse response) throws IOException {
        List<PaymentConfirmHandlingResult> pageBySpec = service.exportPaymentConfirmHandlingList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出回款确认办理列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), PaymentConfirmHandlingResult.class).sheet("导出回款确认办理列表").doWrite(pageBySpec);
    }

    @GetResource(name = "获取erp凭证编号", path = "/syncErpVoucherNo", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "获取erp凭证编号")
    @BusinessLog(title = "AZ回款确认办理-获取erp凭证编号",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData syncErpVoucherNo(PaymentConfirmHandlingParam param, HttpServletResponse response) throws IOException {
        List<String> voucher = service.getErpVoucherNo(param);
        String voucherNo = "";
        for (String string : voucher) {
            voucherNo += voucherNo+",";
        }
        PaymentConfirmHandling h = new PaymentConfirmHandling();

        if(!voucherNo.equals("")){
            h.setId(param.getId());
            h.setSyncStatus(2);
            h.setVoucherNo(voucherNo.substring(0,voucherNo.length()-1));
            service.updateVoucherNo(h);
            return ResponseData.success();
        }else {
            return ResponseData.error("请先到erp手动创建凭证！");
        }
    }

    @GetResource(name = "生成应收明细", path = "/toReceivableDetail", requiredPermission = false)
    @ApiOperation(value = "生成应收明细", response = PaymentConfirmHandlingResult.class)
    public ResponseData toReceivableDetail(PaymentConfirmHandlingParam param) throws Exception {
        return service.toReceivableDetail(param);
    }
}
