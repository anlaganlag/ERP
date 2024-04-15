package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.Account;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwSysDict;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SysDictDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FinancialClassificationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialClassificationResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFinancialClassificationService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
* 财务分类表 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "财务分类", path = "/financialClassification")
@Api(tags = "财务分类")
public class FinancialClassificationController {

    private static  String SETTLEMENT_FINANCE_CLASS="SCWFL";//settlement报告-财务分类
    private static  String SETTLEMENT_FEE_CLASS="SFYZWMC";//settlement报告-费用中文名称
    private static  String SETTLEMENT_SUBJECT_CLASS="SKMFL";//settlement科目分类字典
    private static  String DATARANGE_FINANCE_CLASS="DCWFL";//DataRange报告-财务分类
    private static  String DATARANGE_FEE_CLASS="DFYZWMC";//DataRange报告-费用中文名称
    private static  String DATARANGE_SUBJECT_CLASS="DKMFL";//settlement科目分类字典
    @Autowired
    private IFinancialClassificationService service;
    @Autowired
    private ISysDictService dictService;

    @PostResource(name = "settlement财务分类列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "settlement财务分类列表", response = FinancialClassificationResult.class)
    @BusinessLog(title = "财务分类-settlement财务分类列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody FinancialClassificationParam param) {
        param.setClassificationType("Settlement");
        PageResult<FinancialClassificationResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "导出settlement财务分类列表", path = "/exportFinancialClassificationList", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出settlement财务分类列表")
    @BusinessLog(title = "财务分类-导出settlement财务分类列表",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportFinancialClassificationList(@RequestBody FinancialClassificationParam param, HttpServletResponse response) throws IOException {
        param.setClassificationType("Settlement");
        List<FinancialClassificationResult> pageBySpec = service.exportFinancialClassificationList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出settlement财务分类列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), FinancialClassificationResult.class).sheet("导出settlement财务分类列表").doWrite(pageBySpec);

    }

    @PostResource(name = "dataRange财务分类列表", path = "/dataRangePage", menuFlag = true)
    @ApiOperation(value = "dataRange财务分类列表", response = FinancialClassificationResult.class)
    @BusinessLog(title = "财务分类-dataRange财务分类列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData dataRangePage(@RequestBody FinancialClassificationParam param) {
        param.setClassificationType("DataRange");
        PageResult<FinancialClassificationResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "导出dataRange列表", path = "/exportDataRangeList", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出dataRange列表")
    @BusinessLog(title = "财务分类-导出dataRange列表",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportDataRangeList(@RequestBody FinancialClassificationParam param, HttpServletResponse response) throws IOException {
        param.setClassificationType("DataRange");
        List<FinancialClassificationResult> pageBySpec = service.exportFinancialClassificationList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出dataRange列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), FinancialClassificationResult.class).sheet("导出dataRange列表").doWrite(pageBySpec);

    }

    @GetResource(name = "下载模板", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("下载模板")
    public void downloadExcel(HttpServletResponse response) {
        String path = "/template/财务分类导入模板.xlsx";
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.downloadExcel(response, path);
    }

    @ParamValidator
    @PostResource(name = "导入Excel", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "导入Excel")
    @BusinessLog(title = "财务分类-导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        List<Account> accountList = service.queryAccountList();
        return service.importExcel(file,accountList);
    }

    @GetResource(name = "科目下拉选择", path = "/queryAccount", requiredPermission = false)
    @ApiOperation(value = "科目下拉选择", response = Account.class)
    public ResponseData queryAccount(Account param) {
        List<Account> pageBySpec = service.queryAccount(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "Settlement财务分类下拉-字典", path = "/getSettlementFinace", requiredPermission = false)
    @ApiOperation(value = "Settlement财务分类下拉-字典", response = Account.class)
    public ResponseData getSettlementFinace() {
        CwSysDict param = new CwSysDict();
        param.setDictCode(this.SETTLEMENT_FINANCE_CLASS);
        List<SysDictDetail> pageBySpec = dictService.getByDictCode(param);
        return ResponseData.success(pageBySpec);
    }


    @GetResource(name = "DataRange财务分类下拉-字典", path = "/DataRangeFinace", requiredPermission = false)
    @ApiOperation(value = "DataRange财务分类下拉-字典", response = Account.class)
    public ResponseData getDataRangeFinace() {
        CwSysDict param = new CwSysDict();
        param.setDictCode(this.DATARANGE_FINANCE_CLASS);
        List<SysDictDetail> pageBySpec = dictService.getByDictCode(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "Settlement科目分类-字典", path = "/getSettlementSubjectCode", requiredPermission = false)
    @ApiOperation(value = "Settlement科目分类-字典", response = Account.class)
    public ResponseData getSettlementSubjectCode() {
        CwSysDict param = new CwSysDict();
        param.setDictCode(this.SETTLEMENT_SUBJECT_CLASS);
        List<SysDictDetail> pageBySpec = dictService.getByDictCode(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "DataRange科目分类-字典", path = "/getDataRangeSubjectCode", requiredPermission = false)
    @ApiOperation(value = "DataRange科目分类-字典", response = Account.class)
    public ResponseData getDataRangeSubjectCode() {
        CwSysDict param = new CwSysDict();
        param.setDictCode(this.DATARANGE_SUBJECT_CLASS);
        List<SysDictDetail> pageBySpec = dictService.getByDictCode(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "Settlement费用名称下拉", path = "/getSettlementFee", requiredPermission = false)
    @ApiOperation(value = "Settlement费用名称下拉", response = Account.class)
    public ResponseData getSettlementFee() {
        CwSysDict param = new CwSysDict();
        param.setDictCode(this.SETTLEMENT_FEE_CLASS);
        List<SysDictDetail> pageBySpec = dictService.getByDictCode(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "DataRange费用名称下拉", path = "/getDataRangeFee", requiredPermission = false)
    @ApiOperation(value = "DataRange费用名称下拉", response = Account.class)
    public ResponseData getDataRangeFee() {
        CwSysDict param = new CwSysDict();
        param.setDictCode(this.DATARANGE_FEE_CLASS);
        List<SysDictDetail> pageBySpec = dictService.getByDictCode(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "Settlement财务分类新增", path = "/add", requiredPermission = false)
    @ApiOperation(value = "Settlement财务分类新增", response = FinancialClassificationResult.class)
    @BusinessLog(title = "财务分类-Settlement财务分类新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody FinancialClassificationParam param) {
        param.setClassificationType("Settlement");
        service.add(param);
        return ResponseData.success();
    }

    @PostResource(name = "DataRange财务分类新增", path = "/addDataRange", requiredPermission = false)
    @ApiOperation(value = "DataRange财务分类新增", response = FinancialClassificationResult.class)
    @BusinessLog(title = "财务分类-DataRange财务分类新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addDataRange(@RequestBody FinancialClassificationParam param) {
        param.setClassificationType("DataRange");
        service.addDataRange(param);
        return ResponseData.success();
    }

    @PostResource(name = "财务分类修改", path = "/update", requiredPermission = false)
    @ApiOperation(value = "财务分类修改", response = Account.class)
    @BusinessLog(title = "财务分类-财务分类修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData update(@RequestBody FinancialClassificationParam param) {
        service.update(param);
        return ResponseData.success();
    }

    @PostResource(name = "财务分类禁用", path = "/changeStatus", requiredPermission = false)
    @ApiOperation(value = "财务分类修改禁用", response = FinancialClassificationResult.class)
    @BusinessLog(title = "财务分类-财务分类修改禁用",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData changeStatus(@RequestBody FinancialClassificationParam param) {
        service.changeStatus(param);
        return ResponseData.success();
    }

    @PostResource(name = "财务分类审核", path = "/verifySettlement", requiredPermission = false)
    @ApiOperation(value = "财务分类审核", response = FinancialClassificationResult.class)
    @BusinessLog(title = "财务分类-财务分类审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData verifySettlement(@RequestBody FinancialClassificationParam param) {
        service.verifySettlement(param);
        return ResponseData.success();
    }
}
