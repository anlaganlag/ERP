package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.DataScope;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProdPurchaseRequireParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.ProdPurchaseRequireExtentsResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IProdPurchaseRequireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 新品备货-采购复核
 *
 * @author lsy
 * @since 2022-11-7
 */
@Slf4j
@RestController
@Api(tags = "新品备货-采购复核")
@ApiResource(name = "新品备货-采购复核", path = "/newProdPurchase")
public class NewProdPurchaseCheckController {

    @Autowired
    private IProdPurchaseRequireService service;

    private final String controllerName = "新品备货-采购复核";



    /**
     * 新品备货-采购复核-列表
     */
    @PostResource(name = "新品备货菜单", path = "/productNew",  requiredPermission = false , menuFlag = true,peoplePermission = true)
    @ApiOperation(value = "新品备货菜单",response = ProdPurchaseRequireExtentsResult.class)
    @DataScope(peopleAlias="a",peopleField = "FRONT_PUR_CODE")
    @BusinessLog(title = controllerName + "_" +"新品备货菜单",opType = LogAnnotionOpTypeEnum.QUERY)
    public void productNew() {

    }


    /**
     * 新品备货-采购复核-列表
     */
    @PostResource(name = "新品备货-采购复核-列表", path = "/purchaseList", requiredPermission = false , menuFlag = true,peoplePermission = true)
    @ApiOperation(value = "新品备货-采购复核-列表",response = ProdPurchaseRequireExtentsResult.class)
    @DataScope(peopleAlias="a",peopleField = "FRONT_PUR_CODE")
    @BusinessLog(title = controllerName + "_" +"新品备货-采购复核-列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData purchaseCheck(@RequestBody ProdPurchaseRequireParam prodPurchaseRequireParam) {
        try {
            //值域{"待提交"/"待采购复核"/"待PMC审批"/"PMC未通过"/"PMC已通过"/"已归档"}
            if (ObjectUtil.isNull(prodPurchaseRequireParam)) {
             prodPurchaseRequireParam = new ProdPurchaseRequireParam();
                prodPurchaseRequireParam.setApplyStatus("待采购复核");
            }
            return service.purchaseCheck(prodPurchaseRequireParam);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 新品备货-采购复核-列表
     */
    @PostResource(name = "新品备货-PMC审批-列表", path = "/pmcList", requiredPermission = false , menuFlag = true,peoplePermission = true)
    @ApiOperation(value = "新品备货-PMC审批-列表",response = ProdPurchaseRequireExtentsResult.class)
    @BusinessLog(title = controllerName + "_" +"新品备货-PMC审批-列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData purchasePmcCheck(@RequestBody ProdPurchaseRequireParam prodPurchaseRequireParam) {
        try {
            //值域{"待提交"/"待采购复核"/"待PMC审批"/"PMC未通过"/"PMC已通过"/"已归档"}
            if (ObjectUtil.isNull(prodPurchaseRequireParam)) {
                prodPurchaseRequireParam = new ProdPurchaseRequireParam();
                prodPurchaseRequireParam.setApplyStatus("待PMC审批");
            }
            return service.purchasePmcCheck(prodPurchaseRequireParam);
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }


    /**
     * 新品备货-采购复核-批量编辑
     */
    @PostResource(name = "新品备货-采购复核-批量编辑", path = "/purchaseBatchUpdate", requiredPermission = false, requiredLogin = true)
    @ApiOperation(value = "新品备货-采购复核-批量编辑",response = ProdPurchaseRequireExtentsResult.class)
    @BusinessLog(title = controllerName + "_" +"新品备货-采购复核-批量编辑",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchUpdate(@RequestBody ProdPurchaseRequireParam prodPurchaseRequireParam) {
        if (ObjectUtil.isNull(prodPurchaseRequireParam)|| ObjectUtil.isEmpty(prodPurchaseRequireParam.getIdList())) {
            return ResponseData.error("请求参数[idList]不能为空");
        }
        return service.batchUpdate(prodPurchaseRequireParam);

    }


    /**
     * 新品备货-采购复核-提交(通过，归档)
     */
    @PostResource(name = "新品备货-采购复核-提交(通过，归档)", path = "/purchaseBatchComit", requiredPermission = false )
    @ApiOperation(value = "新品备货-采购复核-提交(通过，归档)",response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"新品备货-采购复核-提交(通过，归档)",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData purchaseBatchComit(@RequestBody ProdPurchaseRequireParam param) {

        if (ObjectUtil.isNull(param)|| ObjectUtil.isEmpty(param.getIdList()) || ObjectUtil.isNull(param.getComitType())) {
            return ResponseData.error("请求参数[idList,comitType]都不能为空");
        }
        return service.batchComit(param);

    }


    /**
     * 新品备货-PMC审批-提交(通过，不通过)
     */
    @PostResource(name = "新品备货-PMC审批-提交(通过，不通过)", path = "/pmcBatchComit", requiredPermission = false, requiredLogin = true)
    @ApiOperation(value = "新品备货-PMC审批-提交(通过，不通过)",response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"新品备货-PMC审批-提交(通过，不通过)",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData pmcBatchComit(@RequestBody ProdPurchaseRequireParam param) {

        if (ObjectUtil.isNull(param)|| ObjectUtil.isEmpty(param.getIdList()) || ObjectUtil.isNull(param.getComitType())) {
            return ResponseData.error("请求参数[idList,comitType]都不能为空");
        }
        if ( ! (param.getComitType()>=0 && param.getComitType()<=1 )) {
            return ResponseData.error("PMC审批ComitType传入有误在，值域【0,1】");
        }
        return service.pmcBatchComit(param);
    }


    /**
     * 新品备货-申请记录-再次同步
     */
    @PostResource(name = "新品备货-申请记录-再次同步", path = "/syncK3", requiredPermission = false, requiredLogin = true)
    @ApiOperation(value = "新品备货-申请记录-再次同步",response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"新品备货-申请记录-再次同步",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData syncK3(@RequestBody ProdPurchaseRequireParam param) {

        if (ObjectUtil.isNull(param)|| ObjectUtil.isEmpty(param.getId()) ) {
            return ResponseData.error("请求参数[id]都不能为空");
        }
        return service.syncK3(param);
    }



    /**
     * 新品备货-申请记录-列表
     */
    @PostResource(name = "新品备货-申请记录-列表", path = "/applyListPage", requiredPermission = false , menuFlag = true,peoplePermission = true)
    @ApiOperation(value = "新品备货-申请记录-列表",response = ProdPurchaseRequireExtentsResult.class)
    @DataScope(peopleAlias="a",peopleField = "FRONT_PUR_CODE")
    @BusinessLog(title = controllerName + "_" +"新品备货-申请记录-列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData applyListPage(@RequestBody ProdPurchaseRequireParam param) {
        try {
            //"PMC未通过"/"PMC已通过"/"已归档"
            return ResponseData.success(service.applyListPage(param));
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 新品备货-申请记录-导出
     * @throws IOException
     */
    @PostResource(name = "新品备货-申请记录-导出", path = "/exportExcel", requiredPermission = false ,peoplePermission = true)
    @ApiOperation("新品备货-申请记录-导出")
    @DataScope(peopleAlias="a",peopleField = "FRONT_PUR_CODE")
    @BusinessLog(title = controllerName + "_" +"新品备货-申请记录-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(@RequestBody ProdPurchaseRequireParam param, HttpServletResponse response) throws IOException {
        try {

            List<ProdPurchaseRequireExtentsResult> extentsResults = service.exportExcel(param);
            exportExcel(response, "新品备货申请记录");
            EasyExcel.write(response.getOutputStream(), ProdPurchaseRequireExtentsResult.class).sheet("新品备货申请记录").doWrite(extentsResults);
        } catch (Exception ex) {
            response.sendError(500, "新品备货申请记录导出错误");
        }
    }

    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }

}
