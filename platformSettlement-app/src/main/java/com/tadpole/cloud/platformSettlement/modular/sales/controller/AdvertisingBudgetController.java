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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.platformSettlement.modular.inventory.consumer.EbmsBaseConsumer;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.RpMaterialConsumer;
import com.tadpole.cloud.platformSettlement.modular.sales.enums.SeasonEnum;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.AdvertisingBudgetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.AdvertisingBudgetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.IAdvertisingBudgetService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ISalesTargetService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 广告预算 前端控制器
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
@RestController
@ApiResource(name = "Advertising budget 广告预算", path = "/advertisingBudget")
@Api(tags = "Advertising budget 广告预算")
public class AdvertisingBudgetController {

    @Autowired
    private IAdvertisingBudgetService service;
    @Autowired
    private ISalesTargetService salesTargetservice;
    @Autowired
    private ITargetBoardService targetBoardService;
    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;
    @Autowired
    private EbmsBaseConsumer ebmsBaseConsumer;

    /**
     * 广告预算列表接口
     *
     */
    @PostResource(name = "广告预算列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "广告预算列表", response = AdvertisingBudgetResult.class)
    @ParamValidator
    @BusinessLog(title = "广告预算列表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@RequestBody AdvertisingBudgetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空");
        }
        List<AdvertisingBudgetResult> list = service.list(param);
        return ResponseData.success(list);
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "广告预算列表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody AdvertisingBudgetParam param, HttpServletResponse response) throws IOException {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份及版本不能为空");
        }
        List<AdvertisingBudgetResult> list = service.list(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("广告预算.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), AdvertisingBudgetResult.class).sheet("广告预算").doWrite(list);
        return ResponseData.success();
    }

    @PostResource(name = "确认", path = "/confirm", requiredPermission = false)
    @ApiOperation(value = "确认")
    @BusinessLog(title = "广告预算列表-确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(@RequestBody AdvertisingBudgetParam param) {
        return service.confirm(param);
    }

    @PostResource(name = "修改", path = "/update", requiredPermission = false)
    @ApiOperation(value = "修改")
    @BusinessLog(title = "广告预算列表-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData update(@RequestBody AdvertisingBudgetParam param) {
        return service.edit(param);
    }

    /**
     * 广告预算合计接口
     *
     */

    @PostResource(name = "广告预算合计", path = "/listSum",  requiredPermission = false)
    @ApiOperation(value = "广告预算合计", response = AdvertisingBudgetResult.class)
    @ParamValidator
    @BusinessLog(title = "广告预算列表-广告预算合计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSum(@RequestBody  AdvertisingBudgetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空");
        }
        return ResponseData.success(service.listSum(param));
    }

    @GetResource(name = "平台下拉", path = "/platformSelect", requiredPermission = false)
    @ApiOperation(value = "平台下拉", response = AdvertisingBudgetResult.class)
    public ResponseData getPlatformSelect() {
        return ResponseData.success(service.getPlatformSelect());
    }

    @GetResource(name = "事业部下拉", path = "/departmentSelect", requiredPermission = false)
    @ApiOperation(value = "事业部下拉", response = AdvertisingBudgetResult.class)
    public ResponseData getDepartmentSelect() {
        return ResponseData.success(service.getDepartmentSelect());
    }

    @GetResource(name = "Team下拉", path = "/teamSelect", requiredPermission = false)
    @ApiOperation(value = "Team下拉", response = AdvertisingBudgetResult.class)
    public ResponseData getTeamSelect() {
        return ResponseData.success(service.getTeamSelect());
    }

    @GetResource(name = "运营大类下拉", path = "/productTypeSelect", requiredPermission = false)
    @ApiOperation(value = "运营大类下拉", response = AdvertisingBudgetResult.class)
    public ResponseData getProductTypeSelect() {
        return ResponseData.success(service.getProductTypeSelect());
    }

    @GetResource(name = "销售品牌下拉", path = "/companyBrandSelect", requiredPermission = false)
    @ApiOperation(value = "销售品牌下拉", response = AdvertisingBudgetResult.class)
    public ResponseData getCompanyBrandSelect() {
        return ResponseData.success(service.getCompanyBrandSelect());
    }

    @GetResource(name = "年份下拉", path = "/yearSelect", requiredPermission = false)
    @ApiOperation(value = "年份下拉", response = AdvertisingBudgetResult.class)
    public ResponseData getYearSelect() {
        return ResponseData.success(service.getYearSelect());
    }

    @GetResource(name = "版本下拉", path = "/versionSelect", requiredPermission = false)
    @ApiOperation(value = "版本下拉", response = AdvertisingBudgetResult.class)
    public ResponseData getVersionSelect(String year) {
        return ResponseData.success(service.getVersionSelect(year));
    }

    @GetResource(name = "季度下拉", path = "/seasonSelect", requiredPermission = false)
    @ApiOperation(value = "季度下拉", response = AdvertisingBudgetResult.class)
    public ResponseData getSeasonSelect() {
        List<Map<String,String>>  seasonMap = SeasonEnum.getEnumList();
        List<String> seasonList = seasonMap.stream().map(i->new ArrayList<>(i.keySet()).get(0)).collect(
            Collectors.toList());
        return ResponseData.success(seasonList);

    }

    /**
     * 广告预算模板下载
     * @param response
     */
    @GetResource(name = "广告预算模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("广告预算模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/广告预算模板.xlsx");
    }

    /**
     * 广告预算导入
     */
    @ParamValidator
    @PostResource(name = "广告预算导入", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "广告预算导入")
    @BusinessLog(title = "广告预算列表-广告预算导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@ModelAttribute AdvertisingBudgetParam param,@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> platformList= targetBoardService.platformList();
        List<String> departmentTeamList= targetBoardService.departmentTeam();
        List<String> productTypeList= rpMaterialConsumer.getProductTypeSelect(new RpMaterialParam()).stream().filter(Objects::nonNull).map(i->(String)i.get("productType")).collect(
            Collectors.toList());
        ResponseData responseData = ebmsBaseConsumer.getSaleBrand();
        Object obj = responseData.getData();
        JSONObject jsonObj = (JSONObject) JSONObject.toJSON(obj);
        JSONArray jsonArray = jsonObj.getJSONArray("data");
        List<String> brandList = new ArrayList<>();
        brandList.add("中性");
        jsonArray.stream().forEach(i ->{
            JSONObject eJsonObj = (JSONObject) JSONObject.toJSON(i);
            brandList.add((String) eJsonObj.get("saleBrandName"));
        });

        List<Map<String,String>>  seasonList = SeasonEnum.getEnumList();
        List<String> jpShops = salesTargetservice.selectJpShopNameByEbms();
        return service.upload(param,file,platformList,departmentTeamList,productTypeList,brandList,jpShops);
    }
}
