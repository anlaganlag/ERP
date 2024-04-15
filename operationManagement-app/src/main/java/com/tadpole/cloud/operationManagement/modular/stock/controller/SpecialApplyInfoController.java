package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PurchaseOrdersResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SpecialApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IExportOrImportService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISpecialApplyInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.SpecialApplyReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
    * 特殊备货申请信息 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-24
*/
@RestController
@ApiResource(name = "特殊备货申请", path = "/specialApplyInfo")
@Api(tags = "特殊备货申请")
public class SpecialApplyInfoController {

    @Autowired
    private ISpecialApplyInfoService service;


    @Autowired
    private IExportOrImportService exportOrImportService;


    @Autowired
    private IPurchaseOrdersService purchaseOrdersOriService;


    private final String controllerName = "特殊备货申请";



    /**
     * 特殊备货审核列表查询
     * @param reqVO
     * @return
     */
    @PostResource(name = "特殊备货申请", path = "/list", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "特殊备货申请", response = SpecialApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"特殊备货申请",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody SpecialApplyInfoReqVO reqVO) {
        return ResponseData.success(service.queryPage(reqVO));
    }




    /**
     * 特殊备货审核列表
     * @param reqVO
     * @return
     */
    @PostResource(name = "特殊备货审核列表", path = "/queryVerifyList",requiredPermission = false)
    @ApiOperation(value = "特殊备货审核列表", response = PurchaseOrdersResult.class)
    @BusinessLog(title = controllerName + "_" +"特殊备货审核列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData querySpecialVerifyPage(@RequestBody OperApplyInfoReqVO reqVO) {
        OperApplyInfoReqVO.Eform eform = reqVO.getEform();
        reqVO.setEform(eform);
        return ResponseData.success(purchaseOrdersOriService.queryPage(reqVO));
    }


    /**
     * 特殊备货审核提交
     * @param ordersParam
     * @return
     */
    @PostResource(name = "特殊备货审核提交", path = "/submitVerifyList",requiredPermission = false)
    @ApiOperation(value = "特殊备货审核提交")
    @BusinessLog(title = controllerName + "_" +"特殊备货审核提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData submitVerifyList(@RequestBody PurchaseOrdersParam ordersParam) {
        return purchaseOrdersOriService.comitPurchaseOrders(ordersParam);
    }


    /**
     * 特殊备货审核批量提交
     * @param ordersParam
     * @return
     */
    @PostResource(name = "特殊备货审核批量提交", path = "/batchSubmit",requiredPermission = false)
    @ApiOperation(value = "特殊备货审核批量提交")
    @BusinessLog(title = controllerName + "_" +"特殊备货审核批量提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchSubmit(@RequestBody PurchaseOrdersParam ordersParam) {
        return purchaseOrdersOriService.comitPurchaseOrders(ordersParam);
    }

    /**
     * 特殊备货申请修改
     * @param applyInfo
     * @return
     */
    @PostResource(name = "特殊备货申请修改", path = "/update", requiredPermission = false)
    @ApiOperation(value = "特殊备货申请修改", response = SpecialApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"特殊备货申请修改",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody SpecialApplyInfo applyInfo) {

        return service.update(applyInfo);
    }


    /**
     * 特殊备货申请批量提交
     * @param reqParamList
     * @return
     */
    @PostResource(name = "特殊备货申请批量提交", path = "/commitBatch", requiredPermission = false)
    @ApiOperation(value = "特殊备货申请批量提交", response = SpecialApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"特殊备货申请批量提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData commitBatch(@RequestBody List<SpecialApplyReqVO> reqParamList) {

        return service.commitBatch(reqParamList);
    }


    /**
     *  特殊备货审核提交
     * @param ordersParam
     * @return
     */
    @PostResource(name = "特殊备货审核提交", path = "/specialVerifySubmit", requiredPermission = false)
    @ApiOperation(value = "特殊备货审核提交")
    @BusinessLog(title = controllerName + "_" +"特殊备货审核提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData specialVerifySubmit(@RequestBody PurchaseOrdersParam ordersParam) {
        return service.specialVerifySubmit(ordersParam);
    }

    @PostResource(name = "特殊备货审核保存", path = "/specialVerifySave", requiredPermission = false)
    @ApiOperation(value = "特殊备货审核保存")
    @BusinessLog(title = controllerName + "_" +"特殊备货审核保存",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData specialVerifySave(@RequestBody PurchaseOrdersParam ordersParam) {
        return service.specialVerifySave(ordersParam);
    }



    /**
     * 特殊备货申请导出
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "/特殊备货申请导出", path = "/exportExcel", requiredPermission = false )
    @ApiOperation("特殊备货申请导出")
    @BusinessLog(title = controllerName + "_" +"特殊备货申请导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(@RequestBody SpecialApplyInfoReqVO param, HttpServletResponse response) throws IOException {

        try {
            exportOrImportService.specialExport(response,param);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 特殊备货申请导入
     * @param file
     * @return
     * @throws IOException
     */
    @ParamValidator
    @PostResource(name = "特殊备货申请导入", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "特殊备货申请导入")
    @BusinessLog(title = controllerName + "_" +"特殊备货申请导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam("file") MultipartFile file) throws IOException {
        return exportOrImportService.specialImport(file);
    }

    /**
     * 特殊备货申请导入提交
     * @param file
     * @return
     * @throws IOException
     */
    @ParamValidator
    @PostResource(name = "特殊备货申请导入提交", path = "/uploadCommit", requiredPermission = false)
    @ApiOperation(value = "特殊备货申请导入提交")
    @BusinessLog(title = controllerName + "_" +"特殊备货申请导入提交",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadCommit(@RequestParam("file") MultipartFile file) throws IOException {
        return exportOrImportService.specialImportComit(file);
    }



    /**
     *
     * @param purchaseId
     * @return
     */
    @GetResource(name = "采购ID获取ASIN明细", path = "/getByPurchaseId", requiredPermission = false)
    @ApiOperation(value = "采购ID获取ASIN明细", response = SpecialApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"采购ID获取ASIN明细",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getByPurchaseId( @RequestParam String purchaseId) {
        return  service.getByPurchaseId(purchaseId);
    }




}
