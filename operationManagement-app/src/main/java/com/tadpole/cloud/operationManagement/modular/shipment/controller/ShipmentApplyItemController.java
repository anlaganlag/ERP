package com.tadpole.cloud.operationManagement.modular.shipment.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.DataScope;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.entity.SysDict;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentApplyItem;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.InvProductGalleryParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ErpTeamAvailableQytResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.OccupyQytResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentApplyItemResult;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentApplyItemService;
import com.tadpole.cloud.operationManagement.modular.shipment.service.ITrackingTransferService;
import com.tadpole.cloud.operationManagement.modular.stock.consumer.DictServiceConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 发货申请明细项 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
@RestController
@Api(tags = "发货申请明细项")
@ApiResource(name = "发货申请明细项", path = "/shipmentApplyItem")
public class ShipmentApplyItemController {


    private final String controllerName = "发货申请明细项";


    @Autowired
    private IShipmentApplyItemService service;

    @Resource
    private DictServiceConsumer dictServiceConsumer;

    @Autowired
    private ITrackingTransferService trackingTransferService;


    /**
     * 发货申请List
     *
     * @param applyStauts
     * @return
     */
    @GetResource(name = "发货申请", path = "/list", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "发货申请", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"发货申请",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData applyList(@RequestParam String applyStauts) {
        return service.applyList(applyStauts,"SQ");
    }


    /**
     * 选择快速创建发货SKU
     *
     * @param param
     * @return
     */
    @PostResource(name = "选择快速创建发货SKU", path = "/selectCreateSku", requiredPermission = false ,
            materialPermission = true, areaPermission = true)
    @ApiOperation(value = "选择快速创建发货SKU", response = ShipmentApplyItemResult.class)
    @DataScope(platformAreaAlias="a",platformField = "PLATFORM",areaField = "AREA")
    @BusinessLog(title = controllerName + "_" +"选择快速创建发货SKU",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData selectCreateSku(@RequestBody InvProductGalleryParam param) {
        return service.selectCreateSku(param);
    }

    /**
     * 导入创建发货SKU
     *
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入创建发货SKU", path = "/importCreateSku", requiredPermission = false)
    @ApiOperation(value = "导入创建发货SKU", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"导入创建发货SKU",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importCreateSku(@RequestParam("file") MultipartFile file) throws IOException {
        //获取发货字典>>交货地点>>parentId
        List<SysDict> shipmentDic = dictServiceConsumer.getDictListByTypeCode("ShipmentDic");
        Long parentId = null;
        try {
            parentId = shipmentDic.stream().filter(dict ->"JHDD".equals(dict.getDictShortName())).collect(Collectors.toList()).get(0).getDictId();
        } catch (Exception e) {
            throw new RuntimeException("请先配置发货字典>>交货地点(JHDD)");
        }
        Long finalParentId = parentId;
        //对应parentId的交货地点Map
        Map<String, String> deliveryMap = shipmentDic.stream().filter(dict -> dict.getParentId().equals(finalParentId)).collect(Collectors.toMap(o -> o.getDictName(), o -> o.getDictCode()));
        return service.importCreateSku(file,deliveryMap);
    }



    /**
     * 发货分析
     *
     * @param applyItemList
     * @return
     */
    @ParamValidator
    @PostResource(name = "发货分析", path = "/shipmentAnalyze", requiredPermission = false)
    @ApiOperation(value = "发货分析", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"发货分析",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData shipmentAnalyze(@RequestBody List<ShipmentApplyItem> applyItemList) {
        return service.shipmentAnalyze(applyItemList,false,false);
    }




    /**
     * 提交申请
     *
     * @param applyBatchNoList
     * @return
     */
    @ParamValidator
    @PostResource(name = "提交申请", path = "/comitByBatchNo", requiredPermission = false)
    @ApiOperation(value = "提交申请", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"提交申请",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData comitByBatchNo(@RequestBody List<String> applyBatchNoList) {

        return service.comitByBatchNo(applyBatchNoList);
    }



    /**
     * 删除申请
     *
     * @param applyBatchNoList
     * @return
     */
    @ParamValidator
    @PostResource(name = "删除申请", path = "/deleteByBatchNo", requiredPermission = false)
    @ApiOperation(value = "删除申请", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"删除申请",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteByBatchNo(@RequestBody List<String> applyBatchNoList) {
        return service.deleteByBatchNo(applyBatchNoList);
    }



    /**
     * 重新分析
     *
     * @param applyBatchNoList
     * @return
     */
    @ParamValidator
    @PostResource(name = "重新分析", path = "/shipmentAgainAnalyze", requiredPermission = false)
    @ApiOperation(value = "重新分析", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"重新分析",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData shipmentAgainAnalyze(@RequestBody List<String> applyBatchNoList) {
        return service.shipmentAgainAnalyze(applyBatchNoList);
    }

    /**
     * 编辑-分析并保存
     *
     * @param applyItemList
     * @return
     */
    @ParamValidator
    @PostResource(name = "编辑-分析并保存", path = "/analyzeSave", requiredPermission = false)
    @ApiOperation(value = "编辑-分析并保存", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"编辑-分析并保存",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData analyzeSave(@RequestBody List<ShipmentApplyItem> applyItemList) {

        return service.analyzeSave(applyItemList);
    }

    /**
     * 自选创建-保存
     *
     * @param itemGroupList
     * @return
     */
    @ParamValidator
    @PostResource(name = "自选创建-保存", path = "/createSave", requiredPermission = false)
    @ApiOperation(value = "自选创建-保存", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"自选创建-保存",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData createSave(@RequestBody List<List<ShipmentApplyItem>> itemGroupList) {
        return service.createSave(itemGroupList);
    }

    /**
     * 自选创建-保存并提交
     *
     * @param itemGroupList
     * @return
     */
    @ParamValidator
    @PostResource(name = "自选创建-保存并提交", path = "/createSaveComit", requiredPermission = false)
    @ApiOperation(value = "自选创建-保存并提交", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"自选创建-保存并提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData createSaveComit(@RequestBody List<List<ShipmentApplyItem>> itemGroupList) {
        return service.createSaveComit(itemGroupList);
    }

    /**
     * 自选创建-导入模板下载
     * @param response
     */
    @ApiOperation("自选创建-导入模板下载")
    @GetResource(name = "自选创建-导入模板下载", path = "/shipmentApplyTemplate", requiredPermission = false )
    @BusinessLog(title = controllerName + "_" +"自选创建-导入模板下载",opType = LogAnnotionOpTypeEnum.IMPORT)
    public void shipmentApplyTemplate(HttpServletResponse response) {
        String path = "/template/shipmentApply.xlsx";
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.downloadExcel(response, path);
    }


    /**
     * 调入仓接口
     *
     */
    @ApiOperation("海外仓下拉接口")
    @GetResource(name = "海外仓下拉接口", path = "/warehouse", requiredPermission = false )
    public ResponseData warehouse(@RequestParam(required = false) String platform, @RequestParam(required = false) String site) {
        return service.warehouse(platform,site);
    }

    /**
     * erp系统team下物料可用调拨数量
     *
     */
    @ApiOperation("erp系统team下物料可用调拨数量")
    @GetResource(name = "erp系统team下物料可用调拨数量", path = "/erpTeamAvailableQty", requiredPermission = false )
    public ResponseData erpTeamAvailableQty(@RequestParam String team, @RequestParam String materialCode,@RequestParam(required = false) String deliverypointNo) {
        if (ObjectUtil.isEmpty(deliverypointNo)) {
            deliverypointNo = "FHD06"; //默认联泰发货
        }
//        return ResponseData.success(service.erpTeamAvailableQty(team,materialCode,deliverypointNo)) ;
        return ResponseData.success(service.erpAvailableQty(team,materialCode,deliverypointNo)) ;
    }


    /**
     * erp系统team下物料可调拨数量+占用库存数量
     *
     */
    @ApiOperation("erp系统team下物料可调拨数量+占用库存数量")
    @GetResource(name = "erp系统team下物料可调拨数量+占用库存数量", path = "/erpAvailableAndOccupyQty", requiredPermission = false )
    public ResponseData erpAvailableAndOccupyQty(@RequestParam(required = false) String applyBatchNo,@RequestParam String team,
                                                 @RequestParam String materialCode,                  @RequestParam(required = false) String deliverypointNo) {

        if (ObjectUtil.isEmpty(deliverypointNo)) {
            deliverypointNo = "FHD06"; //默认联泰发货
        }

        OccupyQytResult occupyQytResult = trackingTransferService.queryOccupyQty(applyBatchNo,team, materialCode,deliverypointNo);

        ErpTeamAvailableQytResult erpTeamAvailableQytResult = service.erpAvailableQty(team, materialCode,deliverypointNo);

        HashMap<String, BigDecimal> resultMap = new HashMap<>();
        resultMap.put("occupyQty", BigDecimal.ZERO);
        resultMap.put("erpCanTransferQty", BigDecimal.ZERO);

        if (ObjectUtil.isNotNull(occupyQytResult)) {
            resultMap.put("occupyQty", occupyQytResult.getQty());
        }

        if (ObjectUtil.isNotNull(erpTeamAvailableQytResult)) {
            resultMap.put("erpCanTransferQty", erpTeamAvailableQytResult.getQty());
        }
        return ResponseData.success(resultMap) ;
    }




}
