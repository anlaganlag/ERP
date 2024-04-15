package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.constants.AuditorCodeTwoEnum;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PurchaseOrdersResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PurchaseOrdersVerifyResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IExportOrImportService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApprovaltimeParameterService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.PurchaseOrdersReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.StockRecommendationApplyReqVO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* <p>
    * 采购订单 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@RestController
@ApiResource(name = "日常采购申请单", path = "/purchaseOrders")
@Api(tags = "日常采购申请单")
@Slf4j
public class PurchaseOrdersController {

    @Autowired
    private IPurchaseOrdersService service;

    @Autowired
    private IStockApprovaltimeParameterService parameterService;

    @Autowired
    private IExportOrImportService exportOrImportService;

    private final String controllerName = "日常采购申请单";


    /**
     * 日常采购申请单列表查询
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "日常采购申请单列表", path = "/list", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "日常采购申请单列表", response = PurchaseOrdersResult.class)
    @BusinessLog(title = controllerName + "_" +"日常采购申请单列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OperApplyInfoReqVO reqVO) {
        return ResponseData.success(service.queryPage(reqVO));
    }

    /**
     * 采购申请记录
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "采购申请记录", path = "/applyRecord", requiredPermission = false,menuFlag = true)
    @ApiOperation(value = "采购申请记录", response = PurchaseOrdersVerifyResult.class)
    @BusinessLog(title = controllerName + "_" +"采购申请记录",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData applyRecord(@RequestBody PurchaseOrdersReqVO reqVO) {
        return ResponseData.success(service.applyRecord(reqVO));
    }

    /**
     * 根据采购订单编号查询Team审核记录
     * @param purchaseNo
     * @return
     */
    @GetResource(name = "根据采购订单编号查询Team审核记录", path = "/getByPurchaseNo", requiredPermission = false)
    @ApiOperation(value = "根据采购订单编号查询Team审核记录", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"根据采购订单编号查询Team审核记录",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getByPurchaseNo(@RequestParam String purchaseNo) {
        return service.getByPurchaseNo(purchaseNo);
    }


    @PostResource(name = "采购申请记录", path = "/recordList")
    @ApiOperation(value = "采购申请记录", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"采购申请记录",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData recordList(@RequestBody OperApplyInfoReqVO reqVO) {
        return ResponseData.success(service.recordList(reqVO));
    }


    /**
     * 日常采购申请单物料编码列表
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "日常采购申请单物料编码列表", path = "/materialCodeList", menuFlag = false, requiredPermission = false)
    @ApiOperation(value = "日常采购申请单物料编码列表")
    @BusinessLog(title = controllerName + "_" +"日常采购申请单物料编码列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryMaterialCodeList(@RequestBody OperApplyInfoReqVO reqVO) {
        return ResponseData.success(service.queryMaterialCodeList(reqVO));
    }


    /**
     * 日常采购申请单保存
     *
     * @param ordersParam
     * @return
     */
    @PostResource(name = "日常采购申请单保存", path = "/save", menuFlag = false, requiredPermission = false)
    @ApiOperation(value = "日常采购申请单保存")
    @BusinessLog(title = controllerName + "_" +"日常采购申请单保存",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData save(@RequestBody PurchaseOrdersParam ordersParam) {
        return service.savePurchaseOrders(ordersParam);
    }

    /**
     * 日常采购申请单批量保存
     *
     * @param
     * @return
     */
    @PostResource(name = "日常采购申请单批量保存", path = "/batchSave", menuFlag = false, requiredPermission = false)
    @ApiOperation(value = "日常采购申请单批量保存")
    @BusinessLog(title = controllerName + "_" +"日常采购申请单批量保存",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchSave(@RequestBody List<PurchaseOrdersParam> ordersParamList) {
        return service.batchSave(ordersParamList);
    }


    /**
     * 日常采购申请单提交
     *
     * @param ordersParam
     * @return
     */
    @PostResource(name = "日常采购申请单提交", path = "/submit", menuFlag = false, requiredPermission = false)
    @ApiOperation(value = "日常采购申请单提交")
    @BusinessLog(title = controllerName + "_" +"日常采购申请单提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData submitPurchaseOrders(@RequestBody PurchaseOrdersParam ordersParam) {
        return service.comitPurchaseOrders(ordersParam);
    }


    /**
     * 日常采购申请单批量提交
     *
     * @param idList
     * @return
     */
    @PostResource(name = "日常采购申请单批量提交", path = "/batchSubmit", menuFlag = false, requiredPermission = false)
    @ApiOperation(value = "日常采购申请单批量提交")
    @BusinessLog(title = controllerName + "_" +"日常采购申请单批量提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchSubmit(@RequestBody List<String> idList) {
        return service.batchSubmit(idList);
    }


    /**
     * 日常备货审核列表查询
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "计划部审核NEW", path = "/list1", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "计划部审核NEW", response = PurchaseOrdersResult.class)
    @BusinessLog(title = controllerName + "_" +"计划部审核NEW",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage1(@RequestBody StockRecommendationApplyReqVO reqVO) {

        return ResponseData.success();
    }

    /**
     * 日常备货审核列表查询
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "PMC审核NEW", path = "/list2", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "PMC审核NEW", response = PurchaseOrdersResult.class)
    @BusinessLog(title = controllerName + "_" +"PMC审核NEW",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage2(@RequestBody StockRecommendationApplyReqVO reqVO) {

        return ResponseData.success();
    }

    /**
     * 日常备货审核列表查询
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "特殊备货NEW", path = "/list3", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "特殊备货NEW", response = PurchaseOrdersResult.class)
    @BusinessLog(title = controllerName + "_" +"特殊备货NEW",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage3(@RequestBody StockRecommendationApplyReqVO reqVO) {

        return ResponseData.success();
    }

    /**
     * 日常备货审核列表查询
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "特殊备货审核NEW", path = "/list4", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "特殊备货审核NEW", response = PurchaseOrdersResult.class)
    @BusinessLog(title = controllerName + "_" +"特殊备货审核NEW",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage4(@RequestBody StockRecommendationApplyReqVO reqVO) {

        return ResponseData.success();
    }


    /**
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "/采购申请单导出", path = "/exportExcel1" )
    @ApiOperation("采购申请单导出")
    @BusinessLog(title = controllerName + "_" +"/采购申请单导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response, @RequestBody OperApplyInfoReqVO param) throws Exception {
        List<PurchaseOrdersResult> resultList = service.exportExcel(param);
        if (ObjectUtil.isEmpty(resultList)) {
            throw new Exception("采购申请单导出数据为空!");
        }
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String("采购申请单导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), PurchaseOrdersResult.class).sheet("采购申请单导出")
                .doWrite(resultList);
    }
/**
 * 采购申请记录导出
 */

@PostResource(name = "/采购申请记录导出", path = "/exportRecordExcel" )
@ApiOperation("采购申请记录导出")
@BusinessLog(title = controllerName + "_" +"采购申请记录导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportRecordExcel(HttpServletResponse response, @RequestBody PurchaseOrdersReqVO param) throws Exception {
    List<PurchaseOrdersVerifyResult> resultList = service.exportRecordExcel(param);
    if (ObjectUtil.isEmpty(resultList)) {
        throw new Exception("采购申请记录导出数据为空!");
    }
    response.setContentType("application/vnd.ms-excel");
    response.addHeader("Content-Disposition",
            "attachment;filename=" + new String("采购申请记录导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), PurchaseOrdersVerifyResult.class).sheet("采购申请记录导出")
            .doWrite(resultList);
}

    /**
     * 采购单超时自动审批
     *
     * @return
     */
    @ParamValidator
    @PostResource(name = "采购单超时自动审批", path = "/overTimeAction", requiredPermission = false)
    @ApiOperation(value = "采购单超时自动审批")
    public ResponseData overTimeAction() {
        StockApprovaltimeParameterResult sybjl = parameterService.findByAuditorCode(AuditorCodeTwoEnum.SYBJL.getCode());
        if (ObjectUtil.isNull(sybjl)) {
            return ResponseData.error("事业部审批时自动提交参数未设置");
        }
        return service.overTimeAction(sybjl);
    }


    /**
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "/采购申请单导出", path = "/exportExcel" )
    @ApiOperation("采购申请单导出")
    @BusinessLog(title = controllerName + "_" +"采购申请单导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel2(HttpServletResponse response, @RequestBody OperApplyInfoReqVO param) throws Exception {
        exportOrImportService.purchaseOrdersExport(response,param);
    }

    /**
     * 采购申请单导入
     * @param file
     * @return
     * @throws IOException
     */
    @ParamValidator
    @PostResource(name = "采购申请单导入", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "采购申请单导入")
    @BusinessLog(title = controllerName + "_" +"采购申请单导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam("file") MultipartFile file) throws IOException {
        return exportOrImportService.purchaseOrdersImport(file);
    }

    /**
     * 采购单状态集合
     * @return
     * @throws IOException
     */
    @ParamValidator
    @PostResource(name = "采购单状态下拉", path = "/orderStatusList", requiredPermission = false)
    @ApiOperation(value = "采购单状态下拉")
    public ResponseData orderStatusList() {
        Map<String, Integer> map = new HashMap<>();
        map.put("采购申请单待审核", StockConstant.ORDER_STATUS_WAIT);
        map.put("不备货", StockConstant.ORDER_STATUS_N0_STOCK);
        map.put("待计划部审批", StockConstant.ORDER_STATUS_PLAN_WAIT);
        map.put("计划部未通过", StockConstant.ORDER_STATUS_PLAN_NO);
        map.put("待PMC审批", StockConstant.ORDER_STATUS_PMC_WAIT);
        map.put("PMC未通过", StockConstant.ORDER_STATUS_PMC_NO);
        map.put("已通过(且同步k3成功)", StockConstant.ORDER_STATUS_PMC_YES);
        map.put("已通过(同步k3失败)", StockConstant.ORDER_STATUS_PMC_YES_SYNC_FAIL);
        return ResponseData.success(map);
    }


    @GetResource(name = "上次下单时间", path = "/lastOrderTime", menuFlag = true)
    @ApiOperation(value = "上次下单时间", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"上次下单时间",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData  lastOrderTime (String platform,String team,String materialCode) {
        return  ResponseData.success(service.lastOrderTime(platform,team,materialCode)) ;
    }
}