package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.authority.column.annotation.DatalimitColumn;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBaseProductEditParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBaseProductParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgBaseProductApplyResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgBaseProductClearResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgBaseProductResult;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgAuditStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgIncludeTaxEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgBaseProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * 通关产品基本信息 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@RestController
@Api(tags = "通关产品基本信息")
@ApiResource(name = "通关产品基本信息", path = "/tgBaseProduct")
@Slf4j
public class TgBaseProductController {

    @Autowired
    private ITgBaseProductService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "通关产品基本信息", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgBaseProductResult.class)
    @BusinessLog(title = "通关产品基本信息-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    @DatalimitColumn(clazz = List.class, name = "数据列过滤")
    public ResponseData queryPage(@RequestBody TgBaseProductParam param) {
        return service.queryPage(param);
    }

    /**
     * 导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "通关产品基本信息-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody TgBaseProductParam param, HttpServletResponse response) throws IOException {
        List<TgBaseProductResult> resultList = service.export(param);
        resultList = JSON.parseArray(JSONUtil.toJsonStr(resultList), TgBaseProductResult.class);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("产品基本信息导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TgBaseProductResult.class).sheet("产品基本信息导出").doWrite(resultList);
    }

    /**
     * 报关产品信息导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "报关产品信息导出", path = "/exportCustomsApply", requiredPermission = false)
    @ApiOperation(value = "报关产品信息导出")
    @BusinessLog(title = "通关产品基本信息-报关产品信息导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportCustomsApply(@RequestBody TgBaseProductParam param, HttpServletResponse response) throws IOException {
        List<TgBaseProductApplyResult> resultList = service.exportCustomsApply(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("报关产品信息导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TgBaseProductApplyResult.class).sheet("报关产品信息导出").doWrite(resultList);
    }

    /**
     * 清关产品信息导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "清关产品信息导出", path = "/exportCustomsClear", requiredPermission = false)
    @ApiOperation(value = "清关产品信息导出")
    @BusinessLog(title = "通关产品基本信息-清关产品信息导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportCustomsClear(@RequestBody TgBaseProductParam param, HttpServletResponse response) throws IOException {
        List<TgBaseProductClearResult> resultList = service.exportCustomsClear(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("清关产品信息导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TgBaseProductClearResult.class).sheet("清关产品信息导出").doWrite(resultList);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "导入模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("导入模板下载")
    public void downloadExcel(@RequestParam("modelType") String modelType, HttpServletResponse response) {
        if("0".equals(modelType)){
            new ExcelUtils().downloadExcel(response, "/template/产品报关信息导入模板.xlsx");
        }
        if("1".equals(modelType)){
            new ExcelUtils().downloadExcel(response, "/template/产品清关信息导入模板.xlsx");
        }
    }

    /**
     * 导入报关信息
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入报关信息", path = "/import", requiredPermission = false)
    @ApiOperation(value = "导入报关信息")
    @BusinessLog(title = "通关产品基本信息-导入报关信息",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importExcel(file);
    }

    /**
     * 导入清关信息
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入清关信息", path = "/importDetail", requiredPermission = false)
    @ApiOperation(value = "导入清关信息")
    @BusinessLog(title = "通关产品基本信息-导入清关信息",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importDetail(@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importDetail(file);
    }

    /**
     * 批量审核通过
     * @param param
     * @return
     */
    @PostResource(name = "批量审核通过", path = "/batchAuditPass")
    @ApiOperation(value = "批量审核通过")
    @BusinessLog(title = "通关产品基本信息-批量审核通过",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchAuditPass(@RequestBody TgBaseProductParam param) {
        return service.batchAuditPass(param);
    }

    /**
     * 批量反审
     * @param param
     * @return
     */
    @PostResource(name = "批量反审", path = "/batchAuditReset")
    @ApiOperation(value = "批量反审")
    @BusinessLog(title = "通关产品基本信息-批量反审",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchAuditReset(@RequestBody TgBaseProductParam param) {
        return service.batchAuditReset(param);
    }

    /**
     * 更新产品信息
     * @return
     */
    @PostResource(name = "更新产品信息", path = "/updateProductInfo", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "更新产品信息")
    @BusinessLog(title = "通关产品基本信息-更新产品信息",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateProductInfo(@RequestParam(value = "day", required = true) Integer day) {
        //正常物料
        List<TgBaseProductResult> resultList = null;
        try {
            resultList = service.getUpdateProductInfo(day);
        } catch (Exception e) {
            log.error("更新普通物料产品信息异常", e);
        }

        //转换物料
        List<TgBaseProductResult> changeResultList = null;
        try {
            changeResultList = service.getUpdateChangeProductInfo();
        } catch (Exception e) {
            log.error("更新转换物料产品信息异常", e);
        }

        //组合物料
        List<TgBaseProductResult> groupResultList = null;
        List<TgBaseProductResult> groupMatList = null;
        try {
            groupResultList = service.getUpdateGroupProductInfo();
            //获取物料编码对应的组合物料
            groupMatList = service.getGroupMat();
        } catch (Exception e) {
            log.error("更新组合物料产品信息异常", e);
        }

        return service.updateProductInfo(day, resultList, changeResultList, groupResultList, groupMatList);
    }

    /**
     * 编辑保存
     * @return
     */
    @PostResource(name = "编辑保存", path = "/edit")
    @ApiOperation(value = "编辑保存")
    @BusinessLog(title = "通关产品基本信息-编辑保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody TgBaseProductEditParam param) {
        return service.edit(param);
    }

    /**
     * 报关材质下拉
     * @return
     */
    @GetResource(name = "报关材质下拉", path = "/clearMaterialCnSelect", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "报关材质下拉")
    public ResponseData clearMaterialCnSelect() {
        return ResponseData.success(service.clearMaterialCnSelect());
    }

    /**
     * 报关英文材质下拉
     * @return
     */
    @GetResource(name = "报关英文材质下拉", path = "/clearMaterialEnSelect")
    @ApiOperation(value = "报关英文材质下拉")
    public ResponseData clearMaterialEnSelect() {
        return ResponseData.success(service.clearMaterialEnSelect());
    }

    /**
     * 审核状态下拉
     * @return
     */
    @GetResource(name = "审核状态下拉", path = "/auditStatusSelect")
    @ApiOperation(value = "审核状态下拉")
    public ResponseData checkReasonSelect() {
        return ResponseData.success(TgAuditStatusEnum.getTgAuditStatus());
    }

    /**
     * 含税状态下拉
     * @return
     */
    @GetResource(name = "含税状态下拉", path = "/includeTaxSelect")
    @ApiOperation(value = "含税状态下拉")
    public ResponseData includeTaxSelect() {
        return ResponseData.success(TgIncludeTaxEnum.getIncludeTax());
    }
}
