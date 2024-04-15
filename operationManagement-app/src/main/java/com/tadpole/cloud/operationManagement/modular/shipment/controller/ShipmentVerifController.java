package com.tadpole.cloud.operationManagement.modular.shipment.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.DataScope;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.ShipmentTrackingParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.SupplementaryTransferParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.params.VerifParam;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ExportVerifyListResult;
import com.tadpole.cloud.operationManagement.modular.shipment.model.result.ShipmentApplyItemResult;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentApplyItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 发货审核 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2023-02-02
 */
@RestController
@Api(tags = "发货审核")
@ApiResource(name = "发货审核", path = "/shipmentVerif")
public class ShipmentVerifController {



    private final String controllerName = "发货审核";

    @Autowired
    private IShipmentApplyItemService service;


    /**
     * 发货审核List
     *
     * @param applyStauts
     * @return
     */
    @GetResource(name = "发货审核List", path = "/list", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "发货审核List", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"发货审核List",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData verifList(@RequestParam String applyStauts) {
        return service.applyList(applyStauts,"SH");
    }


    @PostResource(name = "发货申请审核", path = "/verifyList",menuFlag = true, materialPermission = true,areaPermission = true,peoplePermission = true)
    @ApiOperation(value = "发货申请审核", response = ShipmentApplyItemResult.class)
    @DataScope(platformAreaAlias="t2",platformField = "PLATFORM",areaField = "AREA")
    @BusinessLog(title = controllerName + "_" +"发货申请审核",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData verifyList(@RequestBody ShipmentTrackingParam trackingParam) {

        return service.verifyList(trackingParam);
    }


    @PostResource(name = "发货审核列表导出", path = "/verifyListExport",materialPermission = true,areaPermission = true,peoplePermission = true)
    @ApiOperation(value = "发货审核列表导出")
    @DataScope(platformAreaAlias="t2",platformField = "PLATFORM",areaField = "AREA")
    @BusinessLog(title = controllerName + "_" +"发货审核列表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody ShipmentTrackingParam trackingParam, HttpServletResponse response) throws IOException {
        ResponseData responseData = service.exportVerifyList(trackingParam);
        if (responseData.getCode() != 200) {
            return ResponseData.error(responseData.getMessage());
        }
        List<ExportVerifyListResult> verifyList = (List<ExportVerifyListResult>) responseData.getData();
        if (ObjectUtil.isNotEmpty(verifyList)) {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("发货审核列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
            EasyExcel.write(response.getOutputStream(), ExportVerifyListResult.class).sheet("发货审核列表").doWrite(verifyList);
            return ResponseData.success();
        }
        return ResponseData.error("No available data!");

    }

    /**
     * 发货审核-通过
     *
     * @param verifParam
     * @return
     */
    @PostResource(name = "发货审核-通过", path = "/verifPass", requiredPermission = false)
    @ApiOperation(value = "发货审核-通过", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"发货审核-通过",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData verifPass(@RequestBody VerifParam verifParam) {
       return service.verif(verifParam);
    }

    /**
     * 发货审核-不通过
     *
     * @param verifParam
     * @return
     */
    @ParamValidator
    @PostResource(name = "发货审核-不通过", path = "/verifNotPass", requiredPermission = false)
    @ApiOperation(value = "发货审核-不通过", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"发货审核-不通过",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData verifNotPass(@RequestBody VerifParam verifParam)  {
        return service.verif(verifParam);
    }


    /**
     * 发货审核-已通过-k3系统已经成功创建调拨单，jcERP 需要补调拨单数据
     *
     * @param supplementaryTransferParam
     * @return
     */
    @PostResource(name = "发货审核-已通过-jcerp补调拨单数据", path = "/supplementaryTransfer", requiredPermission = false)
    @ApiOperation(value = "发货审核-已通过-jcerp补调拨单数据", response = ShipmentApplyItemResult.class)
    @BusinessLog(title = controllerName + "_" +"发货审核-已通过-jcerp补调拨单数据",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData supplementaryTransfer(@Validated @RequestBody SupplementaryTransferParam supplementaryTransferParam) {
        return service.supplementaryTransfer(supplementaryTransferParam);
    }

}
