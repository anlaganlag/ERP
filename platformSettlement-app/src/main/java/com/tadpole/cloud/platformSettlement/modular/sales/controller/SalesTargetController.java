package com.tadpole.cloud.platformSettlement.modular.sales.controller;

import cn.hutool.core.collection.CollUtil;
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
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IShopCurrencyService;
import com.tadpole.cloud.platformSettlement.modular.inventory.consumer.EbmsBaseConsumer;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.RpMaterialConsumer;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.SalesTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.AdvertisingBudgetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryDemandParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.NewProductBudgetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.SalesTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.SalesTargetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 销量目标 前端控制器
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
@RestController
@ApiResource(name = "销量目标列表", path = "/salesTarget")
@Api(tags = "销量目标列表")
public class SalesTargetController {

    @Autowired
    private ISalesTargetService service;
    @Autowired
    private ISalesVolumeTargetService service2;
    @Autowired
    private ICollectionTargetService service3;
    @Autowired
    private IProfitTargetService service4;
    @Autowired
    private IAdvertisingBudgetService service5;
    @Autowired
    private IInventoryDemandService service6;
    @Autowired
    private INewProductBudgetService service7;
    @Autowired
    private IInventoryTurnoverService service8;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;
    @Autowired
    private IShopCurrencyService shopService;
    @Autowired
    private EbmsBaseConsumer ebmsBaseConsumer;
    @Autowired
    private ITargetBoardService targetBoardService;
    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;

    @PostResource(name = "销量目标列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "销量目标列表", response = SalesTargetResult.class)
    @BusinessLog(title = "销量目标列表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@RequestBody SalesTargetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空！");
        }
        List<SalesTargetResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "销量目标合计", path = "/listSum", requiredPermission = false)
    @ApiOperation(value = "销量目标合计", response = SalesTargetResult.class)
    @BusinessLog(title = "销量目标列表-销量目标合计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSum(@RequestBody SalesTargetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空！");
        }
        SalesTargetResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "平台下拉", path = "/platformSelect", requiredPermission = false)
    @ApiOperation(value = "平台下拉", response = SalesTargetResult.class)
    public ResponseData platformSelect() {
        return ResponseData.success(service.getPlatformSelect());
    }

    @GetResource(name = "事业部下拉", path = "/departmentSelect", requiredPermission = false)
    @ApiOperation(value = "事业部下拉", response = SalesTargetResult.class)
    public ResponseData departmentSelect() {
        return ResponseData.success(service.getDepartmentSelect());
    }

    @GetResource(name = "Team下拉", path = "/teamSelect", requiredPermission = false)
    @ApiOperation(value = "Team下拉", response = SalesTargetResult.class)
    public ResponseData teamSelect() {
        return ResponseData.success(service.getTeamSelect());
    }

    @GetResource(name = "运营大类下拉", path = "/productTypeSelect", requiredPermission = false)
    @ApiOperation(value = "运营大类下拉", response = SalesTargetResult.class)
    public ResponseData productTypeSelect() {
        return ResponseData.success(service.getProductTypeSelect());
    }

    @GetResource(name = "销售品牌下拉", path = "/companyBrandSelect", requiredPermission = false)
    @ApiOperation(value = "销售品牌下拉", response = SalesTargetResult.class)
    public ResponseData companyBrandSelect() {
        return ResponseData.success(service.getCompanyBrandSelect());
    }

    @GetResource(name = "年份下拉", path = "/yearSelect", requiredPermission = false)
    @ApiOperation(value = "年份下拉", response = SalesTargetResult.class)
    public ResponseData yearSelect() {
        return ResponseData.success(service.getYearSelect());
    }

    @GetResource(name = "版本下拉", path = "/versionSelect", requiredPermission = false)
    @ApiOperation(value = "版本下拉", response = SalesTargetResult.class)
    public ResponseData versionSelect(SalesTargetParam param) {
        return ResponseData.success(service.getVersionSelect(param));
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "销量目标模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("销量目标模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/销售目标导入模板.xlsx");
    }

    @GetResource(name = "全表模板下载", path = "/downloadAllExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("全表模板下载")
    public void downloadAllExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/全表导入模板.xlsx");
    }

    @ParamValidator
    @PostResource(name = "全表导入", path = "/allImport", requiredPermission = false)
    @ApiOperation(value = "全表导入")
    @BusinessLog(title = "销量目标列表-全表导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData allImport(@RequestParam("year") String year,@RequestParam(value = "version",required = false) String version,
                               @RequestParam("currency") String currency,@RequestParam(value = "file", required = true) MultipartFile file) throws Exception {
        List<String> platformList= targetBoardService.platformList();
        List<String> departmentTeamList= targetBoardService.departmentTeam();
        List<String> productTypeList= rpMaterialConsumer.getProductTypeSelect(new RpMaterialParam()).stream().filter(Objects::nonNull).map(i->(String)i.get("productType")).collect(Collectors.toList());

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

        List<String> deptList = transformString(baseSelectConsumer.getDepartmentSelect());
        List<String>  teamList= transformString(baseSelectConsumer.getTeamByDeptSelect(""));
        List<String> jpShops = service.selectJpShopNameByEbms();

        try {
            //无版本取该年最大版本,有版本校验该版本是否已确认;
            if(StringUtil.isEmpty(version)) {
                Map<String, String> versionUnConfirmed = service.isVersionUnConfirmed("SALES_TARGET",year);
                if (versionUnConfirmed != null && new BigDecimal(BigInteger.ZERO).equals(versionUnConfirmed.get("CONFIRM_STATUS"))) {
                    return ResponseData.error(StrUtil.format("当前年份{}存在未确认版本{},不能导入新版本",year,versionUnConfirmed.get("VERSION")));
                }
                version= service.getMaxVersion(year);
            }else {
                if (Objects.equals(service.isVersionConfirmed(year, version), "1")){
                    return ResponseData.error(StrUtil.format("当前年份{}版本{}已确认",year,version));
                }
            }
            String diffDimension = service.isDiffDimension(year, version, currency, file);
            if( diffDimension.length() > 1) {
                return ResponseData.error(StrUtil.format("<pre>{}<pre>",diffDimension));
            }


            ResponseData responseData1 = service.importExcel(year, version, currency, file, platformList, departmentTeamList, productTypeList, brandList, jpShops);
            if (responseData1.getCode() == 500) {
                service.deleCurrent(year,version);
                return responseData1;
            }
            ResponseData responseData2 = service2.importExcel(year, version, currency, file, platformList, departmentTeamList, productTypeList, brandList, jpShops);
            if (responseData2.getCode() == 500) {
                service.deleCurrent(year,version);
                return responseData2;
            }

            ResponseData responseData3 = service3.importExcel(year, version, currency, file, platformList, departmentTeamList, productTypeList, brandList, jpShops);
            if (responseData3.getCode() == 500) {
                service.deleCurrent(year,version);
                return responseData3;
            }

            ResponseData responseData4 = service4.importExcel(year, version, currency, file, platformList, departmentTeamList, productTypeList, brandList, jpShops);
            if (responseData4.getCode() == 500) {
                service.deleCurrent(year,version);
                return responseData4;
            }
            AdvertisingBudgetParam advertisingBudgetParam = AdvertisingBudgetParam.builder().year(year).version(version).currency(currency).build();
            ResponseData responseData5 = service5.upload(advertisingBudgetParam, file, platformList, departmentTeamList, productTypeList, brandList, jpShops);
            if (responseData5.getCode() == 500) {
                service.deleCurrent(year,version);
                return responseData5;
            }

            InventoryDemandParam inventoryDemandParam = InventoryDemandParam.builder().year(year).version(version).currency(currency).build();
            ResponseData responseData6 = service6.upload(inventoryDemandParam, file, platformList, departmentTeamList, productTypeList, brandList);
            if (responseData6.getCode() == 500) {
                service.deleCurrent(year,version);
                return responseData6;
            }
            NewProductBudgetParam newProductBudgetParam = NewProductBudgetParam.builder().year(year).version(version).currency(currency).build();
            ResponseData responseData7 = service7.upload(newProductBudgetParam, file, productTypeList, departmentTeamList);
            if (responseData7.getCode() == 500) {
                service.deleCurrent(year,version);
                return responseData7;
            }
        }catch (Exception e) {
            return ResponseData.error(StrUtil.format("销量目标导入失败: ", e));
        }
        return ResponseData.success();
    }

    @ParamValidator
    @PostResource(name = "导入Excel", path = "/upload")
    @ApiOperation(value = "导入Excel")
    @BusinessLog(title = "销量目标列表-导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam("year") String year,@RequestParam(value = "version",required = false) String version,
                               @RequestParam("currency") String currency,@RequestParam(value = "file") MultipartFile file) throws Exception {
        List<String> platformList= targetBoardService.platformList();
        List<String> departmentTeamList= targetBoardService.departmentTeam();
        List<String> productTypeList= rpMaterialConsumer.getProductTypeSelect(new RpMaterialParam()).stream().filter(Objects::nonNull).map(i->(String)i.get("productType")).collect(Collectors.toList());

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
        List<String> jpShops = service.selectJpShopNameByEbms();
        try  {
            return service.importExcel(year,version,currency,file,platformList,departmentTeamList,productTypeList,brandList,jpShops);

        }catch (Exception e) {
            throw new Exception("销量目标导入失败:"+e);
        }
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "销量目标列表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody SalesTargetParam param, HttpServletResponse response) throws IOException {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空！");
        }
        List<SalesTargetResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("销售数量.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SalesTargetResult.class).sheet("销售数量").doWrite(pageBySpec);
        return ResponseData.success();
    }

    @PostResource(name = "全表确认", path = "/confirm", requiredPermission = false)
    @ApiOperation(value = "全表确认", response = SalesTargetResult.class)
    @BusinessLog(title = "销量目标列表-销量目标合计",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(@RequestBody SalesTargetParam param) throws Exception {
        return service.confirm(param);
    }

    @PostResource(name = "修改", path = "/edit",requiredPermission = false)
    @ApiOperation(value = "修改", response = SalesTargetResult.class)
    @BusinessLog(title = "销量目标列表-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody SalesTarget param) {
        return service.edit(param);
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
