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
import com.tadpole.cloud.platformSettlement.api.finance.model.params.*;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DetailResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StatementIncomeResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IDatarangeDtailService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IPaymentConfirmHandlingService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementDetailService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStatementIncomeService;
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
* 收入记录表 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "AZ收入记录", path = "/statementIncome")
@Api(tags = "AZ收入记录")
public class StatementIncomeController {

    @Autowired
    private IStatementIncomeService service;
    @Autowired
    private IDatarangeDtailService datarangeDtailService;
    @Autowired
    private ISettlementDetailService settlementDetailService;
    @Autowired
    private IPaymentConfirmHandlingService handleService;

    @PostResource(name = "AZ收入记录", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "AZ收入记录", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-AZ收入记录列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody StatementIncomeParam param) {
        PageResult<StatementIncomeResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "导出收入记录列表", path = "/exportStatementIncome", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出收入记录列表")
    @BusinessLog(title = "AZ收入记录-导出收入记录列表",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportStatementIncomeList(StatementIncomeParam param, HttpServletResponse response) throws IOException {
        List<StatementIncomeResult> pageBySpec = service.exportStatementIncomeList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出收入记录列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), StatementIncomeResult.class).sheet("导出收入记录列表").doWrite(pageBySpec);

    }

    @GetResource(name = "修改会计期间", path = "/editFiscalPeriod",requiredPermission = false)
    @ApiOperation(value = "修改会计期间", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-修改会计期间",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData editFiscalPeriod(StatementIncomeParam param) {
        service.editFiscalPeriod(param);
        return ResponseData.success();
    }

    @GetResource(name = "凭证同步erp", path = "/syncToErp", requiredPermission = false)
    @ApiOperation(value = "凭证同步erp", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-凭证同步erp",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData syncToErp(StatementIncomeParam param) throws IOException {
        service.syncToErp(param);
        return ResponseData.success();
    }

    @PostResource(name = "凭证批量同步erp", path = "/syncToErpBatch", requiredPermission = false)
    @ApiOperation(value = "凭证批量同步erp", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-凭证批量同步erp",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData syncToErpBatch(@RequestBody  List<StatementIncomeParam> params) throws IOException {
        return service.syncToErpBatch(params);
    }


    @GetResource(name = "收入记录确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "收入记录确认", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-收入记录确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(StatementIncomeParam param) throws Exception {

        if(param.getChargeCard()!=null){
            PaymentConfirmHandlingParam pp = new PaymentConfirmHandlingParam();
            pp.setCollectionAccount(param.getChargeCard());
            BankSubjectCodeMcms res = handleService.getSubjectByBank(pp);
            if(res!=null){
                param.setSubjectCode(res.getSubjectCode());
                param.setSubjectName(res.getSubjectName());
            }else{
                return ResponseData.error("ERP银行科目未创建，请先至erp创建！");
            }
        }
        service.confirm(param);
        return ResponseData.success();
    }

    @PostResource(name = "收入记录批量确认", path = "/confirmBatch", requiredPermission = false)
    @ApiOperation(value = "收入记录批量确认", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-收入记录批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody List<StatementIncomeParam> params) throws Exception {
        service.confirmBatch(params);
        return ResponseData.success();
    }


    @GetResource(name = "刷取金额数据", path = "/syncAmount", requiredPermission = false)
    @ApiOperation(value = "刷取金额数据", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-刷取金额数据",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData syncAmount(StatementIncomeParam param) {
        service.syncAmount(param);
        return ResponseData.success();
    }

    @GetResource(name = "刷全数据", path = "/syncAll", requiredPermission = false)
    @ApiOperation(value = "刷全数据", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-刷全数据",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData syncAll(StatementIncomeParam param) {

        //刷新Datarange财务分类明细
        DatarangeDtailParam param1 = new DatarangeDtailParam();
        datarangeDtailService.RefreshFinancialClass(param1);

        //刷新settlement财务分类明细
        SettlementDetailParam param2 = new SettlementDetailParam();
        settlementDetailService.refreshFinancialClass(param2);

        //刷取金额数据
        service.syncAmount(param);
        return ResponseData.success();
    }

    @GetResource(name = "查看刷财务分类失败的明细", path = "/refreshFailure", requiredPermission = false)
    @ApiOperation(value = "查看刷财务分类失败的明细", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-查看刷财务分类失败的明细",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData refreshFailure(StatementIncomeParam param) {

        List<DetailResult> list = service.refreshFailure(param);

        return ResponseData.success(list);
    }

    @GetResource(name = "导出刷财务分类失败的明细", path = "/exportFailure", requiredPermission = false)
    @ApiOperation(value = "导出刷财务分类失败的明细", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-导出刷财务分类失败的明细",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportFailure(StatementIncomeParam param, HttpServletResponse response) throws IOException {
        List<DetailResult> list = service.refreshFailure(param);

        response.addHeader("Content-Disposition", "attachment;filename=" + new String("刷财务分类失败的明细.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), DetailResult.class).sheet("刷财务分类失败的明细").doWrite(list);
    }

    @GetResource(name = "重新生成结算单原币", path = "/toListing", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "重新生成结算单原币", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-重新生成结算单原币",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData toListing(StatementIncomeParam param) {
        service.toListing(param);
        return ResponseData.success();
    }

    @GetResource(name = "插入应收明细", path = "/toReceive", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "插入应收明细", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-插入应收明细",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData toReceive(StatementIncomeParam param) throws Exception {
        service.toReceive(param);
        return ResponseData.success();
    }

    @GetResource(name = "生成预估冲回", path = "/toChongHui", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "生成预估冲回", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-生成预估冲回",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData toChongHui(StatementIncomeParam param) {
        service.toChongHui(param);
        return ResponseData.success();
    }


    @GetResource(name = "会计期间生成结算单原币", path = "/dateToListing", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "会计期间生成结算单原币", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-会计期间生成结算单原币",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData dateToListing(StatementIncomeParam param) {
        service.dateToListing(param);
        return ResponseData.success();
    }


    @PostResource(name = "店铺站点维度生成分摊表", path = "/shopSiteToList", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "店铺站点维度生成分摊表", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-店铺站点维度生成分摊表",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData shopSiteToAlloc(@RequestBody StatementIncomeParam param) {
        return service.shopSiteToAlloc(param);
    }


    @PostResource(name = "是否生成下游分摊", path = "/shopSiteHave", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "是否生成下游分摊", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-是否生成下游分摊",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData shopSiteHave(@RequestBody StatementIncomeParam param) {
        return service.shopSiteHave(param);
    }







    @PostResource(name = "设置分摊Id", path = "/assignAllocId", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "设置分摊Id", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-设置分摊Id",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData assignAllocId(@RequestBody StationManualAllocationParam param) {
        return service.assignAllocId(param);
    }

}
