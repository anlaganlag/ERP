package com.tadpole.cloud.platformSettlement.modular.sales.controller;

import cn.hutool.core.collection.CollUtil;
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
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.RpMaterialConsumer;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.InventoryTurnover;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryTurnoverDeptTeamVO;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryTurnoverParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.InventoryTurnoverResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.IInventoryTurnoverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 库存周转 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-06-01
 */
@RestController
@ApiResource(name="Inventory Turnover 库存周转",path = "/inventoryTurnover")
@Api(tags =  "Inventory Turnover 库存周转")
public class InventoryTurnoverController {

    @Autowired
    private IInventoryTurnoverService service;
    @Autowired
    private IInventoryTurnoverService baseSelectService;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;
    @Autowired
    private EbmsBaseConsumer ebmsBaseConsumer;
    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;

    @PostResource(name = "库存周转列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "库存周转列表", response = InventoryTurnoverResult.class)
    @BusinessLog(title = "库存周转-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@Valid @RequestBody InventoryTurnoverParam param) {
        List<String> removeList = transformString(baseSelectConsumer.getTeamByDeptSelect("事业五部"));
        List<InventoryTurnoverResult> list = service.list(param,removeList);
        return ResponseData.success(list);
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "库存周转-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody InventoryTurnoverParam param, HttpServletResponse response) throws IOException {
        List<String> removeList = transformString(baseSelectConsumer.getTeamByDeptSelect("事业五部"));
        List<InventoryTurnoverResult> list = service.list(param,removeList);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("库存周转.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), InventoryTurnoverResult.class).sheet("库存周转").doWrite(list);
        return ResponseData.success();
    }

    @GetResource(name = "库存周转模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("库存周转模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/库存周转模板.xlsx");
    }

    @PostResource(name = "新增库存周转", path = "/add",requiredPermission = false)
    @ApiOperation(value = "新增库存周转",response = InventoryTurnover.class )
    @BusinessLog(title = "库存周转-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Valid InventoryTurnoverParam param) throws ParseException {
        return service.add(param);
    }

    @ParamValidator
    @PostResource(name = "库存周转导入", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "库存周转导入")
    @BusinessLog(title = "库存周转-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@ModelAttribute @Valid InventoryTurnoverParam param,@Valid @RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> platList = baseSelectConsumer.getPlatform();
        List<String> deptList = transformString(baseSelectConsumer.getDepartmentSelect());
        List<String>  teamList= transformString(baseSelectConsumer.getTeamByDeptSelect(""));

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

        List<String> ptList= rpMaterialConsumer.getProductTypeSelect(new RpMaterialParam()).stream().filter(Objects::nonNull).map(i->(String)i.get("productType")).collect(Collectors.toList());
        return service.upload(param,file,platList,teamList,ptList,deptList,brandList);
    }

    @PostResource(name = "删除", path = "/delete",requiredPermission = false)
    @ApiOperation(value="删除", response = InventoryTurnover.class)
    @BusinessLog(title = "库存周转-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody InventoryTurnoverParam param) throws ParseException {
        return service.delete(param);
    }

    @PostResource(name = "修改", path = "/edit", requiredPermission = false)
    @ApiOperation(value = "修改")
    @BusinessLog(title = "库存周转-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Valid InventoryTurnoverParam param) {
        return service.edit(param);
    }

    @PostResource(name = "月份确认", path = "/monthConfirm", requiredPermission = false)
    @ApiOperation(value = "月份确认")
    @BusinessLog(title = "库存周转-月份确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData monthConfirm(@RequestBody InventoryTurnoverParam param) {
        return service.monthConfirm(param);
    }

    @PostResource(name = "当前已确认年月", path = "/confirmedYearMonth", requiredPermission = false)
    @ApiOperation(value = "当前已确认年月")
    @BusinessLog(title = "库存周转-当前已确认年月",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmedYearMonth(@RequestBody InventoryTurnoverParam param) {
        return service.confirmedYearMonth();
    }

    @GetResource(name = "平台下拉", path = "/platformSelect", requiredPermission = false)
    @ApiOperation(value = "平台下拉", response = InventoryTurnoverResult.class)
    public ResponseData getPlatformSelect () {
        return ResponseData.success(service.getPlatformSelect());
    }

    @GetResource(name = "事业部下拉", path = "/departmentSelect", requiredPermission = false)
    @ApiOperation(value = "事业部下拉", response = InventoryTurnoverResult.class)
    public ResponseData getDepartmentSelect () {
        return ResponseData.success(service.getDepartmentSelect());
    }

    @GetResource(name = "Team下拉", path = "/teamSelect", requiredPermission = false)
    @ApiOperation(value = "Team下拉", response = InventoryTurnoverDeptTeamVO.class)
    public ResponseData getTeamSelect () {
        return ResponseData.success(service.getTeamSelect());
    }

    @GetResource(name = "运营大类下拉", path = "/productTypeSelect", requiredPermission = false)
    @ApiOperation(value = "运营大类下拉", response = InventoryTurnoverResult.class)
    public ResponseData getProductTypeSelect () {
        return ResponseData.success(service.getProductTypeSelect());
    }

    @GetResource(name = "销售品牌下拉", path = "/companyBrandSelect", requiredPermission = false)
    @ApiOperation(value = "销售品牌下拉", response = InventoryTurnoverResult.class)
    public ResponseData getCompanyBrandSelect () {
        return ResponseData.success(service.getCompanyBrandSelect());
    }

    @GetResource(name = "年份下拉", path = "/yearSelect", requiredPermission = false)
    @ApiOperation(value = "年份下拉", response = InventoryTurnoverResult.class)
    public ResponseData getYearSelect () {
        return ResponseData.success(service.getYearSelect());
    }

    @GetResource(name = "平台下拉(新增)", path = "/platform", requiredPermission = false)
    @ApiOperation(value = "平台下拉(新增)", response = InventoryTurnoverResult.class)
    public ResponseData getPlatform () {
        return ResponseData.success(baseSelectConsumer.getPlatform());
    }

    @GetResource(name = "事业部下拉(新增)", path = "/department", requiredPermission = false)
    @ApiOperation(value = "事业部下拉(新增)", response = InventoryTurnoverResult.class)
    public ResponseData getDepartment () {
        return ResponseData.success(baseSelectConsumer.getDepartmentSelect());
    }

    @GetResource(name = "Team下拉(新增)", path = "/team", requiredPermission = false)
    @ApiOperation(value = "Team下拉(新增)", response = InventoryTurnoverDeptTeamVO.class)
    public ResponseData getTeam (@RequestParam(value = "department", required = false, defaultValue = "") String department) {
        return ResponseData.success(baseSelectConsumer.getTeamAndDeptSelect(department));
    }

    @GetResource(name = "Team下拉(新增)2", path = "/team2", requiredPermission = false)
    @ApiOperation(value = "Team下拉(新增)2", response = InventoryTurnoverDeptTeamVO.class)
    public ResponseData getTeam2 (@RequestParam(value = "department", required = false, defaultValue = "") String department) {
        return ResponseData.success(baseSelectConsumer.getTeamByDeptSelect(department));
    }

    @GetResource(name = "运营大类下拉(新增)", path = "/productType", requiredPermission = false)
    @ApiOperation(value = "运营大类下拉(新增)", response = InventoryTurnoverResult.class)
    public ResponseData getProductType(@RequestParam(value = "category", required = false, defaultValue = "") String category){
        RpMaterialParam param = new RpMaterialParam();
        param.setCategory(category);
        return ResponseData.success(rpMaterialConsumer.getProductTypeSelect(param));
    }

    @GetResource(name = "销售品牌下拉(新增)", path = "/companyBrand", requiredPermission = false)
    @ApiOperation(value = "销售品牌下拉(新增)", response = InventoryTurnoverResult.class)
    public ResponseData getCompanyBrand () {
        ResponseData responseData = ebmsBaseConsumer.getSaleBrand();
        Object obj = responseData.getData();
        JSONObject jsonObj = (JSONObject) JSONObject.toJSON(obj);
        JSONArray jsonArray = jsonObj.getJSONArray("data");
        List<String> brandList = new ArrayList<>();
        jsonArray.stream().forEach(i ->{
            JSONObject eJsonObj = (JSONObject) JSONObject.toJSON(i);
            brandList.add((String) eJsonObj.get("saleBrandName"));
        });
        return ResponseData.success(brandList);
    }

    public static List<String> transformString(List<Map<String, Object>> list) {
        List<String> resultList = new ArrayList<>();
        if (CollUtil.isEmpty(list)) {
            return resultList;
        }
        for (Map<String, Object> mp : list) {
            if (mp == null) {
                continue;
            }
            Collection values = mp.values();
            for (  Object val : values) {
                resultList.add( val.toString());
            }
        }
        return resultList;
    }
}
