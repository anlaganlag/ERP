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
import com.tadpole.cloud.platformSettlement.modular.sales.entity.CollectionTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.CollectionTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.CollectionTargetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ICollectionTargetService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ISalesTargetService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ITargetBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 回款目标 前端控制器
 * </p>
 *
 * @author gal
 * @since 2022-03-04
 */
@RestController
@ApiResource(name = "回款目标列表", path = "/collectionTarget")
@Api(tags = "回款目标列表")
public class CollectionTargetController {

    @Autowired
    private ISalesTargetService salesTargetservice;
    @Autowired
    private ICollectionTargetService service;
    @Autowired
    private ITargetBoardService targetBoardService;
    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;
    @Autowired
    private EbmsBaseConsumer ebmsBaseConsumer;

    @PostResource(name = "回款目标列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "回款目标列表", response = CollectionTargetResult.class)
    @BusinessLog(title = "回款目标列表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(@RequestBody CollectionTargetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空！");
        }
        List<CollectionTargetResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "回款目标合计", path = "/listSum", requiredPermission = false)
    @ApiOperation(value = "回款目标合计", response = CollectionTargetResult.class)
    @BusinessLog(title = "回款目标列表-回款目标合计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSum(@RequestBody CollectionTargetParam param) {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空！");
        }
        CollectionTargetResult pageBySpec= service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "平台下拉", path = "/platformSelect", requiredPermission = false)
    @ApiOperation(value = "平台下拉", response = CollectionTargetResult.class)
    public ResponseData platformSelect() {
        return ResponseData.success(service.getPlatformSelect());
    }

    @GetResource(name = "事业部下拉", path = "/departmentSelect", requiredPermission = false)
    @ApiOperation(value = "事业部下拉", response = CollectionTargetResult.class)
    public ResponseData departmentSelect() {
        return ResponseData.success(service.getDepartmentSelect());
    }

    @GetResource(name = "Team下拉", path = "/teamSelect", requiredPermission = false)
    @ApiOperation(value = "Team下拉", response = CollectionTargetResult.class)
    public ResponseData teamSelect() {
        return ResponseData.success(service.getTeamSelect());
    }

    @GetResource(name = "运营大类下拉", path = "/productTypeSelect", requiredPermission = false)
    @ApiOperation(value = "运营大类下拉", response = CollectionTargetResult.class)
    public ResponseData productTypeSelect() {
        return ResponseData.success(service.getProductTypeSelect());
    }

    @GetResource(name = "销售品牌下拉", path = "/companyBrandSelect", requiredPermission = false)
    @ApiOperation(value = "销售品牌下拉", response = CollectionTargetResult.class)
    public ResponseData companyBrandSelect() {
        return ResponseData.success(service.getCompanyBrandSelect());
    }

    @GetResource(name = "年份下拉", path = "/yearSelect", requiredPermission = false)
    @ApiOperation(value = "年份下拉", response = CollectionTargetResult.class)
    public ResponseData yearSelect() {
        return ResponseData.success(service.getYearSelect());
    }

    @GetResource(name = "版本下拉", path = "/versionSelect", requiredPermission = false)
    @ApiOperation(value = "版本下拉", response = CollectionTargetResult.class)
    public ResponseData versionSelect(CollectionTargetParam param) {
        return ResponseData.success(service.getVersionSelect(param));
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "回款目标模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("回款目标模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/回款目标导入模板.xlsx");
    }

    @ParamValidator
    @PostResource(name = "导入Excel", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "导入Excel")
    @BusinessLog(title = "回款目标列表-导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam("year") String year, @RequestParam(value = "version",required = false) String version,
                               @RequestParam("currency") String currency, @RequestParam(value = "file", required = true) MultipartFile file) {
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

        List<String> jpShops = salesTargetservice.selectJpShopNameByEbms();
        return service.importExcel(year,version,currency,file,platformList,departmentTeamList,productTypeList,brandList,jpShops);
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "回款目标列表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody CollectionTargetParam param, HttpServletResponse response) throws IOException {
        if  ( StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())){
            return ResponseData.error("年份和版本不能为空！");
        }
        List<CollectionTargetResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("回款.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), CollectionTargetResult.class).sheet("回款").doWrite(pageBySpec);
        return ResponseData.success();
    }
    @PostResource(name = "回款目标导入确认", path = "/confirm", requiredPermission = false)
    @ApiOperation(value = "回款目标导入确认", response = CollectionTargetResult.class)
    @BusinessLog(title = "回款目标列表-回款目标导入确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(@RequestBody CollectionTargetParam param) throws Exception {
        return service.confirm(param);
    }

    @PostResource(name = "修改", path = "/edit",requiredPermission = false)
    @ApiOperation(value = "修改", response = CollectionTargetResult.class)
    @BusinessLog(title = "回款目标列表-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody CollectionTarget param) {
        return service.edit(param);
    }

}
