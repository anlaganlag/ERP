package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcAutoAnalyzeParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcVerifInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PmcVerifInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockPurchaseApplyResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPmcVerifInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IVerifInfoRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "备货2.0-PMC审批V3")
@ApiResource(name = "备货2.0-PMC审批V3", path = "/pmcVerifV3")
public class PmcVerifV3Controller {

    @Autowired private IPurchaseOrdersService service;
    @Autowired private IVerifInfoRecordService verService;




    @Autowired private IPmcVerifInfoService veriInfoService;
    private final String controllerName = "备货2.0-PMC审批V3";

    @PostResource(name = "备货2.0-PMC审批列表V3", path = "/pmcVerifList", menuFlag = true )
    @ApiOperation(value = "备货2.PMC审批列表V3", response = PmcVerifInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"备货2.0-PMC审批列表V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData planVerifList(@RequestBody PmcVerifInfoParam param) {
        if (ObjectUtil.isNotNull(param)) {
            if (param.getAppQtyLessMoqQty() && param.getAppQtyMoreEqMoqQty()) {
                param.setAppQtyLessMoqQty(Boolean.FALSE);
                param.setAppQtyMoreEqMoqQty(Boolean.FALSE);
            }
        }

        return ResponseData.success(veriInfoService.queryList(param));
    }

    /**
     * 合并--PMC批量通过
     *
     * @author
     */
    @PostResource(name = "合并查询PMC批量通过V3", path = "/batchMergePass",requiredPermission = false)
    @ApiOperation(value = "合并查询PMC批量通过V3", response = StockPurchaseApplyResult.class)
    @BusinessLog(title = controllerName + "_" +"合并查询PMC批量通过V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchMergePass(@RequestBody List<PmcVerifInfoParam> parmList) {
        if(parmList == null || parmList.size() == 0)
            return ResponseData.error("合并查询PMC批量通过未接收到参数！");
        return veriInfoService.batchMergeAction(parmList, StockConstant.VERIFY_SUCESS);
    }


    /**
     * 合并--PMC批量驳回
     *
     * @author
     */
    @PostResource(name = "合并查询PMC批量驳回V3", path = "/batchMergeReject",requiredPermission = false)
    @ApiOperation(value = "合并查询PMC批量驳回V3", response = StockPurchaseApplyResult.class)
    @BusinessLog(title = controllerName + "_" +"合并查询PMC批量驳回V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchMergeReject(@RequestBody List<PmcVerifInfoParam> parmList) {
        if(parmList == null || parmList.size() == 0)
            return ResponseData.error("合并查询PMC批量不通过未接收到参数！");
        return veriInfoService.batchMergeAction(parmList, StockConstant.VERIFY_FAIL);
    }

    /**
     * PMC自动分析
     * @author lsy
     */
    @PostResource(name = "PMC自动分析V3", path = "/autoAnalyze",requiredPermission = false)
    @ApiOperation(value = "PMC自动分析V3", response = StockPurchaseApplyResult.class)
    @BusinessLog(title = controllerName + "_" +"PMC自动分析V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData autoAnalyze(@RequestBody PmcAutoAnalyzeParam param) {
//        return veriInfoService.autoAnalyze(param);
        return veriInfoService.autoAnalyzeByMat(param);
    }


    /**
     * 合并明细审批V3
     *
     * @author
     */
    @PostResource(name = "合并明细审批V3", path = "/mergeDetails",requiredPermission = false)
    @ApiOperation(value = "合并明细审批V3", response = PmcVerifInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"合并明细审批V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData mergeDetails(@RequestBody PmcVerifInfoParam parm) {
        return veriInfoService.mergeDetails(parm);
    }


    /**
     * 合并明细审批-多条提交
     *
     * @author
     */
    @PostResource(name = "合并明细审批V3-多条明细提交", path = "/detailsComit",requiredPermission = false)
    @ApiOperation(value = "合并明细审批V3-多条明细提交", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"合并明细审批V3-多条明细提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData detailsComit(@RequestBody List<PmcVerifInfoParam>  parmList) {
        return veriInfoService.detailsComit (parmList) ;
    }

}
