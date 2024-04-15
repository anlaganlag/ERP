package com.tadpole.cloud.operationManagement.modular.shipment.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.DataScope;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentBoardParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.TrackParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.TrackingTransferParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentApplyItemResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentTrackingResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.TrackingFlatVO;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentTrackingService;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsDayParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.ILogisticsDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* <p>
    * 发货追踪汇总表 前端控制器
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@RestController
@Api(tags = "发货追踪汇总表")
@ApiResource(name = "发货追踪汇总表", path = "/shipmentTracking")
public class ShipmentTrackingController {


    private final String controllerName = "发货追踪汇总表";


    @Autowired
    private IShipmentTrackingService service;


    @Resource
    private ILogisticsDayService logDayService;

    /**
     * 发货追踪
     *
     * @param param
     * @return
     */



    @PostResource(name = "发货申请跟踪", path = "/trackFlatList",menuFlag = true, requiredPermission = true,materialPermission = true,areaPermission = true,peoplePermission = true)
    @ApiOperation(value = "发货申请跟踪", response = ShipmentTrackingResult.class)
    @DataScope(platformAreaAlias="t1",platformField = "PLATFORM",areaField = "AREA")
    @BusinessLog(title = controllerName + "_" +"发货申请跟踪",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData trackFlatList(@RequestBody TrackParam param) {
        PageResult<TrackingFlatVO> pageBySpec = service.trackFlatList(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "追踪发货导出", path = "/export", requiredPermission = true,materialPermission = true,areaPermission = true,peoplePermission = true)
    @ApiOperation(value = "追踪发货导出", response = ShipmentTrackingResult.class)
    @DataScope(platformAreaAlias="t1",platformField = "PLATFORM",areaField = "AREA")
    @BusinessLog(title = controllerName + "_" +"追踪发货导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TrackParam param, HttpServletResponse response)  throws IOException {
        param.setPageSize((long) Integer.MAX_VALUE);
        PageResult<TrackingFlatVO> records = service.trackFlatList(param);

        List<TrackingFlatVO> rows = records.getRows();
        if (Objects.isNull(rows) || rows.size() ==0 ) {
            return  ResponseData.error("No available data!");
//            return  ResponseData.success("数据为空!");
        }
        for (TrackingFlatVO row : rows) {
            String materialInfo = row.getMaterialInfo();
            JSONObject entries = JSONUtil.parseObj(materialInfo);
            row.setCategory((String)entries.get("category"));
            row.setStyle((String)entries.get("style"));
            row.setMainMaterial((String)entries.get("mainMaterial"));
            row.setDesign((String)entries.get("design"));
            row.setCompanyBrand((String)entries.get("companyBrand"));
            row.setBrand((String)entries.get("fitBrand"));
            row.setModel((String)entries.get("model"));
            row.setColor((String)entries.get("color"));
            row.setSizes((String)entries.get("sizes"));
            row.setPacking((String)entries.get("packing"));
            row.setVersionDes((String)entries.get("versionDes"));
            row.setType((String)entries.get("type"));
            row.setProductType((String)entries.get("productType"));
            row.setProductName((String)entries.get("productName"));

        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("追踪发货导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TrackingFlatVO.class).sheet("追踪发货导出").doWrite(rows);
        return ResponseData.success();
    }


    @GetResource(name = "发货追踪", path = "/track",menuFlag = true)
    @ApiOperation(value = "发货追踪", response = ShipmentApplyItemResult.class)
    public ResponseData track(@RequestParam(required = false) String batchNo) {
        return service.track(batchNo);
    }


    @GetResource(name = "发货调拨单撤销", path = "/transferRevoke")
    @ApiOperation(value = "发货调拨单撤销", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"发货调拨单撤销",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData transferRevoke( @RequestParam String billNo,@RequestParam String revokeReason) {
        return service.transferRevoke(billNo,revokeReason);
    }


    @PostResource(name = "发货调拨单列表撤销", path = "/transferListRevoke")
    @ApiOperation(value = "发货调拨单列表撤销", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"发货调拨单列表撤销",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData transferListRevoke(@RequestBody List<TrackingTransferParam> transferList) {
        return service.transferListRevoke(transferList);
    }






    @PostResource(name = "追踪发货批次列表", path = "/trackBatchList")
    @ApiOperation(value = "追踪发货批次列表", response = ShipmentTrackingResult.class)
    @BusinessLog(title = controllerName + "_" +"追踪发货批次列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData trackBatchList(@RequestBody TrackParam param) {
        PageResult<ShipmentTrackingResult> pageBySpec = service.trackBatchList(param);
        return ResponseData.success(pageBySpec);
    }


    @GetResource(name = "追踪调拨单明细列表", path = "/trackTransferList")
    @ApiOperation(value = "追踪调拨单明细列表", response = ShipmentTrackingResult.class)
    @BusinessLog(title = controllerName + "_" +"追踪调拨单明细列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData trackTransferList(@RequestParam(required = true) String applyBatchNo) {
        return service.trackTransferList(applyBatchNo);
    }


    @GetResource(name = "发货追踪物流明细", path = "/trackLogisticsList")
    @ApiOperation(value = "发货追踪物流明细", response = ShipmentTrackingResult.class)
    @BusinessLog(title = controllerName + "_" +"发货追踪物流明细",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData trackLogisticsList(@RequestParam(required = true) String billNo) {
        return service.trackLogisticsList(billNo);
    }








    /**
     * 删除调拨单
     *
     * @param billNo
     * @return
     */
    @GetResource(name = "删除调拨单", path = "/delTranferByBillNo", requiredPermission = false)
    @ApiOperation(value = "删除调拨单", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"删除调拨单",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delTranferByBillNo(@RequestParam(required = false) String billNo) {
       return service .delTranferByBillNo(billNo);
    }

    @PostResource(name = "发货看板", path = "/queryShipmentBoard",menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "发货看板",response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"发货看板",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryShipmentBoard(@RequestBody ShipmentBoardParam param) {
        return service.queryShipmentBoard(param);
    }


    @GetResource(name = "物流方式下拉(区域)", path = "/transportByAreaList" )
    @ApiOperation(value = "物流方式下拉(区域)", response = ShipmentTrackingResult.class)
    public ResponseData transportByAreaList(@RequestParam(required = false) String area) {
        //对应物流方式列表
        List<String> logisticsModeList = logDayService.findPageBySpec(LogisticsDayParam.builder().area(area).build()).getRows().stream().filter(Objects::isNull)
                .map(item->item.getLogisticsMode()).collect(Collectors.toList());
        return ResponseData.success(logisticsModeList);
    }



}
