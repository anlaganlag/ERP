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
import com.tadpole.cloud.platformSettlement.api.finance.model.params.InitialBalanceParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.InitialBalanceResult;
import com.tadpole.cloud.platformSettlement.modular.finance.listener.InitialBalanceListener;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFinancialSiteService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IInitialBalanceService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IShopCurrencyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* <p>
* 设置期初余额 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "设置期初余额", path = "/initialBalance")
@Api(tags = "设置期初余额")
public class InitialBalanceController {

    @Autowired
    private IInitialBalanceService service;
    @Autowired
    private IShopCurrencyService shopService;
    @Autowired
    private IFinancialSiteService siteService;

    @PostResource(name = "设置期初余额", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "设置期初余额", response = InitialBalanceResult.class)
    @BusinessLog(title = "期初余额-期初余额查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody InitialBalanceParam param) {
        PageResult<InitialBalanceResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "下载模板", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("下载模板")
    public void downloadExcel(HttpServletResponse response) {
        String path = "/template/期初余额导入模板.xlsx";
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.downloadExcel(response, path);
    }

    @GetResource(name = "导出期初余额列表", path = "/exportInitialBalanceList", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出期初余额列表")
    @BusinessLog(title = "期初余额-导出期初余额列表",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportInitialBalanceList(InitialBalanceParam param, HttpServletResponse response) throws IOException {
        List<InitialBalanceResult> pageBySpec = service.exportInitialBalanceList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出期初余额列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), InitialBalanceResult.class).sheet("导出期初余额列表").doWrite(pageBySpec);
    }

    @GetResource(name = "设置期初余修改", path = "/updateBalance", requiredPermission = false)
    @ApiOperation(value = "设置期初余修改", response = InitialBalanceResult.class)
    @BusinessLog(title = "期初余额-设置期初余修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateBalance(InitialBalanceParam param) {
        service.updateBalance(param);
        return ResponseData.success();
    }

    @ParamValidator
    @PostResource(name = "导入Excel", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "导入Excel")
    @BusinessLog(title = "期初余额-导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        final InitialBalanceListener excelListener = new InitialBalanceListener(service,shopService,siteService);
        EasyExcel.read(file.getInputStream(), InitialBalanceResult.class, excelListener).sheet().doRead();
        if(excelListener.getFailList().size()>0){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String filePath = System.getProperty("user.dir") + "/upload/";
            String fileName = simpleDateFormat.format(new Date()) + ".xlsx";
            OutputStream out = new FileOutputStream(filePath +fileName,true);
            EasyExcel.write(out, InitialBalanceResult.class).sheet("设置期初余额导入结果").doWrite(excelListener.getFailList());
            return ResponseData.error(fileName);
        }else{
            return ResponseData.success();
        }
    }
}
