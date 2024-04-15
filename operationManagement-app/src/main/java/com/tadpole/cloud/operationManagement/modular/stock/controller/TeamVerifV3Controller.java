package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.DataScope;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.constants.AuditorCodeTwoEnum;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.TeamVerifParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResultV3;
import com.tadpole.cloud.operationManagement.modular.stock.service.IExportOrImportService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApprovaltimeParameterService;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITeamVerifV3Service;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
* <p>
    * Team审核 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@RestController
@ApiResource(name = "Team审核V3", path = "/teamVerifV3")
@Api(tags = "Team审核V3")
public class TeamVerifV3Controller {

    @Autowired
    private ITeamVerifV3Service service;

    @Autowired
    private IStockApprovaltimeParameterService parameterService;

    @Autowired
    private IExportOrImportService exportOrImportService;

    private final String controllerName = "Team审核V3";


    /**
     * Team审核列表查询
     * @param reqVO
     * @return
     */
    @PostResource(name = "Team审核列表V3", path = "/list", menuFlag = true,requiredPermission = false,materialPermission = false,areaPermission = false)
    @ApiOperation(value = "Team审核列表V3", response = TeamVerifResultV3.class)
    @DataScope(platformAreaAlias="a",platformField = "PLATFORM",areaField = "AREA")
    @BusinessLog(title = controllerName + "_" +"Team审核列表V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OperApplyInfoReqV3 reqVO) {
        return ResponseData.success(service.queryList(reqVO));
    }


    /**
     * team合并显示-不备货
     * @param
     * @return
     */
    @PostResource(name = "Team合并显示-不备货V3", path = "/mergeNoStockBatch",requiredPermission = false )
    @ApiOperation(value = "Team合并显示-不备货V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"Team合并显示-不备货V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData mergeNoStockBatch(@RequestBody List<String> idArrayList) {
        return service.batchNoStocking(idArrayList);
    }


    /**
     * Team合并显示-批量提交
     * @param idArrayList
     * @return
     */
    @PostResource(name = "Team合并显示-批量提交V3", path = "/mergeCommitBatch", requiredPermission = false)
    @ApiOperation(value = "Team合并显示-批量提交V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"Team合并显示-批量提交V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData mergeCommitBatch(@NotEmpty @RequestBody List<String> idArrayList) {
        return service.mergeCommitBatchFast(idArrayList,null);
    }

    /**
     * Team合并显示-查看合并的明细数据
     * @param idList
     * @return
     */
    @PostResource(name = "Team合并显示-查看合并的明细数据V3", path = "/mergeDetail", requiredPermission = false)
    @ApiOperation(value = "Team合并显示-查看合并的明细数据V3", response = TeamVerif.class)
    @BusinessLog(title = controllerName + "_" +"Team合并显示-查看合并的明细数据V3",opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData mergeDetail(@RequestBody List<BigDecimal> idList) {
        return service.mergeDetail(idList);
    }



    /**
     * Team审核明细数据批量更新
     * @param teamVerifList
     * @return
     */
    @PostResource(name = "Team审核明细数据批量-更新V3", path = "/updateDetailBatch", requiredPermission = false)
    @ApiOperation(value = "Team审核明细数据批量-更新V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"Team审核明细数据批量-更新V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateDetailBatch(@RequestBody List<TeamVerif> teamVerifList) {
        return service.updateDetailBatch(teamVerifList);
    }

    /**
     * Team审核明细数据批量-提交V3
     * @param teamVerifList
     * @return
     */
    @PostResource(name = "Team审核明细数据批量-提交V3", path = "/commitDetailBatch", requiredPermission = false)
    @ApiOperation(value = "Team审核明细数据批量-提交V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"Team审核明细数据批量-提交V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData commitDetailBatch(@RequestBody List<TeamVerif> teamVerifList) {
        return service.commitDetailBatch(teamVerifList);
    }


    /**
     * 物料申请数量统计Team维度-V3
     * @param param
     * @return
     */
    @PostResource(name = "Team节点物料申请数量统计-V3", path = "/applyQtyCountMat", requiredPermission = false)
    @ApiOperation(value = "Team节点物料申请数量统计-V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"Team节点物料申请数量统计-V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData applyQtyCountMat( @RequestBody TeamVerifParam param) {
        if (ObjectUtil.isNull(param.getBizdate())
                ||  ObjectUtil.isEmpty(param.getMaterialCode())
                ||ObjectUtil.isEmpty(param.getDepartment())) {
            return ResponseData.error("department,materialCode,bizdate三个参数不能为空");
        }

        return service.applyQtyCountMat(param.getDepartment(),param.getMaterialCode(),param.getBizdate());
    }


    /**
     * 根据TEAM审核信息ID查找对应的ASIN合并提交数据-V3
     * @param teamNoList
     * @return
     */
    @PostResource(name = "根据Team编号查找asin提交明细-V3", path = "/queryAsinListByTeamNo", requiredPermission = false)
    @ApiOperation(value = "根据Team编号查找asin提交明细-V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"根据Team编号查找asin提交明细-V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryAsinListByTeamNo(@RequestBody List<String> teamNoList) {
        return service.queryAsinListByTeamNo(teamNoList);
    }


    /**
     * Team审核超时自动处理
     * @return
     */
    @PostResource(name = "Team审核超时自动处理V3", path = "/overTimeAction", requiredPermission = false)
    @ApiOperation(value = "Team审核超时自动处理V3")
    public ResponseData overTimeAction() {

        StockApprovaltimeParameterResult tzg = parameterService.findByAuditorCode(AuditorCodeTwoEnum.TZG.getCode());
        if (ObjectUtil.isNull(tzg)) {
            return ResponseData.error("Team审核超时自动提交参数未设置");
        }

        return service.overTimeAction(tzg);

    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Team审核保存
     * @param reqParamList
     * @return
     */
    @PostResource(name = "Team审核保存V3", path = "/update", requiredPermission = false)
    @ApiOperation(value = "Team审核保存V3", response = TeamVerifResult.class)
    @DataSource(name = "stocking")
    @BusinessLog(title = controllerName + "_" +"特殊备货审核保存",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody List<TeamVerif> reqParamList) {

        service.saveOrUpdateBatch(reqParamList);
        return ResponseData.success();

    }


    /**
     * Team审核批量提交
     * @param reqParamList
     * @return
     */
    @PostResource(name = "Team审核批量提交V3", path = "/commitBatch", requiredPermission = false)
    @ApiOperation(value = "Team审核批量提交V3", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"特殊备货审核保存",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData commitBatch(@RequestBody List<OperApplyReqVO> reqParamList) {
        return service.commitBatch(reqParamList);
    }




    /**
     * Team审核申请导出
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "/Team审核导出V3", path = "/exportExcel")
    @ApiOperation("Team审核导出V3")
    @BusinessLog(title = controllerName + "_" +"Team审核导出V3",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(@RequestBody OperApplyInfoReqV3 param,HttpServletResponse response) throws Exception {
        List<TeamVerifResult> resultList = service.exportExcel(param);
        if (ObjectUtil.isEmpty(resultList)) {
            throw new Exception("数据为空!");
        }
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String("Team审核导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TeamVerifResult.class).sheet("Team审核导出")
                .doWrite(resultList);


    }


    /**
     * 根据采购订单编号查询Team审核记录
     * @param purchaseNo
     * @return
     */
    @GetResource(name = "根据采购订单编号查询Team审核记录V3", path = "/getByPurchaseNo", requiredPermission = false)
    @ApiOperation(value = "根据采购订单编号查询Team审核记录V3", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"根据采购订单编号查询Team审核记录V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getByPurchaseNo(@RequestParam String purchaseNo) {
        return service.getByPurchaseNo(purchaseNo);
    }






    /**
     * Team审核导出
     * @param response
     * @throws IOException
     */
    @PostResource(name = "/Team审核导出V3", path = "/teamVerifyExport")
    @ApiOperation("Team审核导出V3")
    @BusinessLog(title = controllerName + "_" +"Team审核导出V3",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void teamExportExcel(HttpServletResponse response) throws Exception {
       exportOrImportService.teamVerifyExport(response);
    }

    /**
     * Team审核导入
     * @throws IOException
     */
    @PostResource(name = "/Team审核导入V3", path = "/upload")
    @ApiOperation("Team审核导入V3")
    @BusinessLog(title = controllerName + "_" +"Team审核导入V3",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData teamVerifyImport(MultipartFile file) throws Exception {
      return exportOrImportService.teamVerifyImport(file);
    }


}
