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
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbLogisticsNoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbLogisticsNoResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbLogisticsNoService;
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
 *  BTB物流单前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
@RestController
@Api(tags = "BTB物流单")
@ApiResource(name = "BTB物流单", path = "/lsBtbLogisticsNo")
public class LsBtbLogisticsNoController {

    @Autowired
    private ILsBtbLogisticsNoService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "BTB物流单", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = LsBtbLogisticsNoResult.class)
    @BusinessLog(title = "BTB物流单-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody LsBtbLogisticsNoParam param) {
        return service.queryPage(param);
    }

    /**
     * 新建物流单
     * @param param
     * @return
     */
    @PostResource(name = "新建物流单", path = "/add")
    @ApiOperation(value = "新建物流单")
    @BusinessLog(title = "BTB物流单-新建物流单",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody LsBtbLogisticsNoParam param) {
        return service.add(param);
    }

    /**
     * 编辑保存
     * @param param
     * @return
     */
    @PostResource(name = "编辑保存", path = "/edit")
    @ApiOperation(value = "编辑保存")
    @BusinessLog(title = "BTB物流单-编辑保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody LsBtbLogisticsNoParam param) {
        return service.edit(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/del")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "BTB物流单-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData del(@RequestBody LsBtbLogisticsNoParam param) {
        return service.del(param);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "导入模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("导入模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/B2B物流单费用导入模板.xlsx");
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入", path = "/import", requiredPermission = false)
    @ApiOperation(value = "导入")
    @BusinessLog(title = "BTB物流单-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importExcel(file);
    }

    /**
     * 导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "BTB物流单-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody LsBtbLogisticsNoParam param, HttpServletResponse response) throws IOException {
        List<LsBtbLogisticsNoResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("BTB物流单导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LsBtbLogisticsNoResult.class).sheet("BTB物流单导出").doWrite(resultList);
    }

    /**
     * 签收
     * @param param
     * @return
     */
    @PostResource(name = "签收", path = "/sign")
    @ApiOperation(value = "签收")
    @BusinessLog(title = "BTB物流单-签收",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData sign(@RequestBody LsBtbLogisticsNoParam param) {
        return service.sign(param);
    }

}
