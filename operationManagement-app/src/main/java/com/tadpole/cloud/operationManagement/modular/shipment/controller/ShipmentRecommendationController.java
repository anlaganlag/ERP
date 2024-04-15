package com.tadpole.cloud.operationManagement.modular.shipment.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.DataScope;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentApplyItem;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentRecommendation;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentRecomD6Param;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentRecomD6PreAreaParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentRecommendationParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.SkuCheckParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentRecommendationResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentRecommendationShopSkuResult;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentApplyItemService;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentRecommendationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
    * 发货推荐 前端控制器
    * </p>
*
* @author lsy
* @since 2023-02-02
*/


@RestController
@Api(tags = "发货推荐")
@ApiResource(name = "发货推荐", path = "/shipmentRecommendation")
@Slf4j
public class ShipmentRecommendationController {



    private final String controllerName = "发货推荐";


    @Resource
    private IShipmentRecommendationService service;

    @Resource
    private IShipmentApplyItemService shipmentApplyItemService;




    /**
     * 发货推荐
     *
     * @param param
     * @return
     */
    @PostResource(name = "每日发货推荐", path = "/shipmentRecommendationList", menuFlag = true ,
            materialPermission = true, areaPermission = true)
    @ApiOperation(value = "每日发货推荐", response = ShipmentRecommendationResult.class)
    @DataScope(platformAreaAlias="a",platformField = "PLATFORM",areaField = "PRE_AREA")
    @BusinessLog(title = controllerName + "_" +"每日发货推荐",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData shipmentRecommendationList(@RequestBody ShipmentRecommendationParam param) {
        //看Team维度数据
//        LoginContext current = SpringContext.getBean(LoginContext.class);
//        LoginUser currentUser = current.getLoginUser();
//        String team = currentUser.getTeam();
//        //运营列表
//        List<String> teams = departmentSelectService.getTeamSelect().stream().map(i ->(String) i.get("team")).collect(Collectors.toList());
//        if (team != null && teams.contains(team)) {
//            param.setTeam(team);
//        }
//        return ResponseData.success(service.shipmentRecommendationList(param));
        return ResponseData.success(service.shipmentRecommendationListDatalimit(param));
    }


    /**
     * 推荐申请发货保存
     *
     * @param applyItemList
     * @return
     */

    @PostResource(name = "推荐申请发货保存", path = "/recommendationApplyShipmentSave")
    @ApiOperation(value = "推荐申请发货保存")
    @BusinessLog(title = controllerName + "_" +"推荐申请发货保存",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData recommendationApplyShipmentSave(@RequestBody List<ShipmentApplyItem> applyItemList) {
        return service.recommendationApplyShipmentSave(applyItemList);
    }


    /**
     * 推荐申请发货提交
     *
     * @param applyItemList
     * @return
     */

    @PostResource(name = "推荐申请发货提交", path = "/recommendationApplyShipmentSubmit")
    @ApiOperation(value = "推荐申请发货提交")
    @BusinessLog(title = controllerName + "_" +"推荐申请发货提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData recommendationApplyShipmentSubmit(@RequestBody List<ShipmentApplyItem> applyItemList) {
        return service.recommendationApplyShipmentSubmit(applyItemList);
    }

    /**
     * 发货推荐
     *
     * @param pKList 主键为平台+区域+事业部+Team+物料编码+ASIN 'Amazon'||PRE_AREA || department || team || material_code || asin
     * @return
     */
    @PostResource(name = "获取发货推荐列表", path = "/getListByPK" )
    @ApiOperation(value = "获取发货推荐列表")
    @BusinessLog(title = controllerName + "_" +"获取发货推荐列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getListByPK(@RequestBody List<String> pKList) {
        if (pKList.size() == 0) {
            return ResponseData.error("发货推荐列表参数不能为空");
        }
        List<ShipmentRecommendation> results = service.getListByPK(pKList);
        if (ObjectUtil.isNotEmpty(results)) {
            return ResponseData.success(results);
        }
        return ResponseData.error("未找到发货推荐列表");
    }


    @PostResource(name = "获取店铺SKU销售库存明细", path = "/getShopSkuListD6" )
    @ApiOperation(value = "获取店铺SKU销售库存明细")
    @BusinessLog(title = controllerName + "_" +"获取店铺SKU销售库存明细",opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData getShopSkuListD6(@RequestBody @Validated ShipmentRecomD6PreAreaParam param) {
        List<ShipmentRecommendationShopSkuResult> results = service.getShopSkuListByPK(param.getPk());
        if (ObjectUtil.isNotEmpty(results)) {
            return ResponseData.success(results);
        }
        return ResponseData.success();
//        return ResponseData.error("获取店铺SKU销售库存明细>>getShopSkuListD6>>未找到发货推荐店铺SKU销售库存明细列表");
    }

    @PostResource(name = "获取BI推荐数据D6维度查询", path = "/getRecommendationD6" )
    @ApiOperation(value = "获取BI推荐数据D6维度查询",response = ShipmentRecommendationResult.class)
    @BusinessLog(title = controllerName + "_" +"获取BI推荐数据D6维度查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getRecommendationD6(@Validated @RequestBody ShipmentRecomD6Param d6Param) {
        ShipmentRecommendation result = service.getByApplyItem(d6Param);
        if (ObjectUtil.isNotNull(result)) {
            return ResponseData.success(result);
        }
        return ResponseData.error("未找到发货推荐列表");
    }


    @PostResource(name = "/导出组合SKU推荐", path = "/exportRecommendation" )
    @ApiOperation("导出组合SKU推荐")
    @BusinessLog(title = controllerName + "_" +"导出组合SKU推荐",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportRecommendation(@RequestBody ShipmentRecommendationParam param, HttpServletResponse response) throws Exception {
        log.info("导出组合SKU推荐>>exportTemplate 导出组合SKU推荐入参数: [{}]", JSONUtil.toJsonStr(param));
        service.exportTemplateSku(response,param);
    }

    @ApiOperation("导出源模板")
    @PostResource(name = "导出源模板", path = "/exportTemplate" )
    @BusinessLog(title = controllerName + "_" +"导出源模板",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportTemplate(@RequestBody ShipmentRecommendationParam param, HttpServletResponse response) throws Exception {
        log.info("导出源模板>>exportTemplate 导出源模板入参: [{}]", JSONUtil.toJsonStr(param));
        service.exportTemplate(response, param);
    }


    @ApiOperation(value = "SKU可用性校验")
    @PostResource(name = "SKU可用性校验", path = "/skuCheck" )
    public ResponseData skuCheck(@RequestBody SkuCheckParam param) {
        return service.skuCheck(param);
    }

    @ApiOperation("信息项说明下载")
    @GetResource(name = "信息项说明下载", path = "/downloadExcel", requiredPermission = false )
    @BusinessLog(title = controllerName + "_" +"信息项说明下载",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/发货推荐数据-信息项目说明.xlsx");
    }






    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }
}
