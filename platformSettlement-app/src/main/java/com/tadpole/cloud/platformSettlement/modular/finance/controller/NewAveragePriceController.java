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
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NewAveragePriceParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NewAveragePriceResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.NewAveragePriceCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.INewAveragePriceService;
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
* 新核算库存平均单价表 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "源新核算库存平均单价", path = "/newAveragePrice")
@Api(tags = "源新核算库存平均单价")
public class NewAveragePriceController {

    @Autowired
    private INewAveragePriceService service;

    @PostResource(name = "源新核算库存平均单价列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "源新核算库存平均单价列表", response = NewAveragePriceResult.class)
    @BusinessLog(title = "源新核算库存平均单价-源新核算库存平均单价列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody NewAveragePriceParam param) {
        PageResult<NewAveragePriceResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "源新核算库存平均单价-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody NewAveragePriceParam param, HttpServletResponse response) throws IOException {
        List<NewAveragePriceResult> pageBySpec = service.queryList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("核算库存平均单价表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), NewAveragePriceResult.class).sheet("核算库存平均单价总表").doWrite(pageBySpec);
    }

    @ParamValidator
    @PostResource(name = "导入Excel", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "导入Excel")
    @BusinessLog(title = "源新核算库存平均单价-导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importExcel(file);
    }

    @GetResource(name = "修改", path = "/edit",requiredPermission = false)
    @ApiOperation(value = "修改", response = NewAveragePriceResult.class)
    @BusinessLog(title = "源新核算库存平均单价-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(NewAveragePriceParam param) {
        service.edit(param);
        return ResponseData.success();
    }

    @GetResource(name = "确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "确认", response = NewAveragePriceResult.class)
    @BusinessLog(title = "源新核算库存平均单价-确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(NewAveragePriceParam param) throws Exception {
        service.confirm(param);
        return ResponseData.success();
    }

    @GetResource(name = "删除", path = "/delete",requiredPermission = false)
    @ApiOperation(value = "删除", response = NewAveragePriceResult.class)
    @BusinessLog(title = "源新核算库存平均单价-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(NewAveragePriceParam param) throws Exception {
        service.delete(param);
        return ResponseData.success();
    }

    @PostResource(name = "批量确认", path = "/confirmBatch",requiredPermission = false)
    @ApiOperation(value = "批量确认", response = NewAveragePriceResult.class)
    @BusinessLog(title = "源新核算库存平均单价-批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody NewAveragePriceParam param) throws Exception {
        service.confirmBatch(param);
        return ResponseData.success();
    }

    @PostResource(name = "批量删除", path = "/deleteBatch",requiredPermission = false)
    @ApiOperation(value = "批量删除", response = NewAveragePriceResult.class)
    @BusinessLog(title = "源新核算库存平均单价-批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody NewAveragePriceParam param) throws Exception {
        service.deleteBatch(param);
        return ResponseData.success();
    }

    @GetResource(name = "确认状态下拉", path = "/status",requiredPermission = false)
    @ApiOperation(value = "确认状态下拉", response = NewAveragePriceResult.class)
    public ResponseData status() throws Exception {
        return ResponseData.success(NewAveragePriceCheckStatus.getEnumList());
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/源新核算库存平均单价导入模板.xlsx");
    }
}
