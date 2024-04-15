package com.tadpole.cloud.platformSettlement.modular.sales.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.RpMaterialParam;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.RpMaterialConsumer;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.NewProductBudgetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.NewProductBudgetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.INewProductBudgetService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ITargetBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 新品预算 前端控制器
 * </p>
 *
 * @author gal
 * @since 2022-03-07
 */
@RestController
@ApiResource(name = "New product budget 新品预算", path = "/newProductBudget")
@Api(tags = "NewProductBudget 新品预算")
public class NewProductBudgetController {
    @Autowired
    private INewProductBudgetService service;
    @Autowired
    private ITargetBoardService targetBoardService;
    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;

    @PostResource(name = "新品预算列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "新品预算列表", response = NewProductBudgetResult.class)
    @ParamValidator
    @BusinessLog(title = "新品预算-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@RequestBody NewProductBudgetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空");
        }
        List<NewProductBudgetResult> list = service.list(param);
        return ResponseData.success(list);
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "新品预算-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody NewProductBudgetParam param, HttpServletResponse response) throws IOException {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份及版本不能为空");
        }
        List<NewProductBudgetResult> list = service.list(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("新品预算.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), NewProductBudgetResult.class).sheet("新品预算").doWrite(list);
        return ResponseData.success();
    }

    @PostResource(name = "确认", path = "/confirm", requiredPermission = false)
    @ApiOperation(value = "确认")
    @BusinessLog(title = "新品预算-确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(@RequestBody NewProductBudgetParam param) {
        return service.confirm(param);
    }

    @PostResource(name = "修改", path = "/update", requiredPermission = false)
    @ApiOperation(value = "修改")
    @BusinessLog(title = "新品预算-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData update(@RequestBody NewProductBudgetParam param) {
        return service.edit(param);
    }

    /**
     * 新品预算合计接口
     *
     */
    @PostResource(name = "新品预算合计", path = "/listSum",  requiredPermission = false)
    @ApiOperation(value = "新品预算合计", response = NewProductBudgetResult.class)
    @ParamValidator
    @BusinessLog(title = "新品预算-新品预算合计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSum(@RequestBody  NewProductBudgetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空");
        }
        return ResponseData.success(service.listSum(param));
    }

    @GetResource(name = "运营大类下拉", path = "/productTypeSelect", requiredPermission = false)
    @ApiOperation(value = "运营大类下拉", response = NewProductBudgetResult.class)
    public ResponseData getProductTypeSelect() {
        return ResponseData.success(service.getProductTypeSelect());
    }

    @GetResource(name = "部门下拉", path = "/departmentSelect", requiredPermission = false)
    @ApiOperation(value = "部门下拉", response = NewProductBudgetResult.class)
    public ResponseData getDepartmentSelect() {
        return ResponseData.success(service.getDepartmentSelect());
    }

    @GetResource(name = "二级类目下拉", path = "/secondLabelSelect", requiredPermission = false)
    @ApiOperation(value = "二级类目下拉", response = NewProductBudgetResult.class)
    public ResponseData getSecondLabelSelect() {
        return ResponseData.success(service.getSecondLabelSelect());
    }

    @GetResource(name = "年份下拉", path = "/yearSelect", requiredPermission = false)
    @ApiOperation(value = "年份下拉", response = NewProductBudgetResult.class)
    public ResponseData getYearSelect() {
        return ResponseData.success(service.getYearSelect());
    }

    @GetResource(name = "版本下拉", path = "/versionSelect", requiredPermission = false)
    @ApiOperation(value = "版本下拉", response = NewProductBudgetResult.class)
    public ResponseData getVersionSelect(String year) {
        return ResponseData.success(service.getVersionSelect(year));
    }

    /**
     * 新品预算模板下载
     * @param response
     */
    @GetResource(name = "新品预算模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("新品预算模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/新品预算模板.xlsx");
    }

    /**
     * 新品预算导入
     *
     */
    @ParamValidator
    @PostResource(name = "新品预算导入", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "新品预算导入")
    @BusinessLog(title = "新品预算-新品预算导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@ModelAttribute NewProductBudgetParam param,@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> productTypeList= rpMaterialConsumer.getProductTypeSelect(new RpMaterialParam()).stream().filter(Objects::nonNull).map(i->(String)i.get("productType")).collect(
            Collectors.toList());
        List<String> departmentTeamList= targetBoardService.departmentTeam();
        return service.upload(param,file,productTypeList,departmentTeamList);
    }
}
