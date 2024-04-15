package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsTaxRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsTaxRateResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsTaxRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
 * 清关税率 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@RestController
@Api(tags = "清关税率")
@ApiResource(name = "清关税率", path = "/tgCustomsTaxRate")
public class TgCustomsTaxRateController {

    @Autowired
    private ITgCustomsTaxRateService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "清关税率", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgCustomsTaxRateResult.class)
    @BusinessLog(title = "清关税率-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgCustomsTaxRateParam param) {
        return service.queryPage(param);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "清关税率-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TgCustomsTaxRateParam param) {
        return service.add(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "清关税率-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgCustomsTaxRateParam param) {
        return service.delete(param);
    }

    /**
     * 编辑
     * @param param
     * @return
     */
    @PostResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "清关税率-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody TgCustomsTaxRateParam param) {
        return service.edit(param);
    }

    /**
     * 导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "清关税率-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody TgCustomsTaxRateParam param, HttpServletResponse response) throws IOException {
        List<TgCustomsTaxRateResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("清关税率导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TgCustomsTaxRateResult.class).sheet("清关税率导出").doWrite(resultList);
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入", path = "/import", requiredPermission = false)
    @ApiOperation(value = "导入")
    @BusinessLog(title = "清关税率-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importExcel(file);
    }

    /**
     * 目的国下拉
     * @return
     */
    @GetResource(name = "目的国下拉", path = "/targetCountrySelect")
    @ApiOperation(value = "目的国下拉")
    public ResponseData targetCountrySelect() {
        return ResponseData.success(service.targetCountrySelect());
    }

    /**
     * 根据国家级联HSCode下拉
     * @return
     */
    @PostResource(name = "根据国家级联HSCode下拉", path = "/hsCodeSelect")
    @ApiOperation(value = "根据国家级联HSCode下拉")
    public ResponseData hsCodeSelect(@RequestBody TgCustomsTaxRateParam param) {
        String countryCode = param.getCountryCode();
        if(StringUtils.isBlank(countryCode)){
            ResponseData.error("国家为空，查询失败！");
        }
        return ResponseData.success(service.hsCodeSelect(countryCode));
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "导入模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("导入模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/清关税率导入模板.xlsx");
    }
}
