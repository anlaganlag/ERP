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
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsEssentialFactorParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsEssentialFactorResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsEssentialFactorService;
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
 * 报关要素 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@RestController
@Api(tags = "报关要素")
@ApiResource(name = "报关要素", path = "/tgCustomsEssentialFactor")
public class TgCustomsEssentialFactorController {

    @Autowired
    private ITgCustomsEssentialFactorService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "报关要素", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgCustomsEssentialFactorResult.class)
    @BusinessLog(title = "报关要素-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgCustomsEssentialFactorParam param) {
        return service.queryPage(param);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "报关要素-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TgCustomsEssentialFactorParam param) {
        return service.add(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "报关要素-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgCustomsEssentialFactorParam param) {
        return service.delete(param);
    }

    /**
     * 编辑
     * @param param
     * @return
     */
    @PostResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "报关要素-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody TgCustomsEssentialFactorParam param) {
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
    @BusinessLog(title = "报关要素-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody TgCustomsEssentialFactorParam param, HttpServletResponse response) throws IOException {
        List<TgCustomsEssentialFactorResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("报关要素导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TgCustomsEssentialFactorResult.class).sheet("报关要素导出").doWrite(resultList);
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入", path = "/import", requiredPermission = false)
    @ApiOperation(value = "导入")
    @BusinessLog(title = "报关要素-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importExcel(file);
    }

    /**
     * 报关要素下拉
     * @return
     */
    @GetResource(name = "报关要素下拉", path = "/essentialFactorSelect")
    @ApiOperation(value = "报关要素下拉")
    public ResponseData essentialFactorSelect() {
        return ResponseData.success(service.essentialFactorSelect());
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "导入模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("导入模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/报关要素导入模板.xlsx");
    }
}
