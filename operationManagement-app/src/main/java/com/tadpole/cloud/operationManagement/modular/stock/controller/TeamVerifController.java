package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.constants.AuditorCodeTwoEnum;
import com.tadpole.cloud.operationManagement.modular.stock.entity.TeamVerif;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.TeamVerifResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IExportOrImportService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApprovaltimeParameterService;
import com.tadpole.cloud.operationManagement.modular.stock.service.ITeamVerifService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;
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
    * Team审核 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@RestController
@ApiResource(name = "Team审核", path = "/teamVerif")
@Api(tags = "Team审核")
public class TeamVerifController {

    @Autowired
    private ITeamVerifService service;

    @Autowired
    private IStockApprovaltimeParameterService parameterService;

    @Autowired
    private IExportOrImportService exportOrImportService;

    private final String controllerName = "Team审核";


    /**
     * Team审核列表查询
     * @param reqVO
     * @return
     */
    @PostResource(name = "Team审核", path = "/list", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "Team审核", response = TeamVerifResult.class)
    @BusinessLog(title = controllerName + "_" +"Team审核",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OperApplyInfoReqVO reqVO) {

        return ResponseData.success(service.queryPage(reqVO));
    }


    /**
     * Team审核保存
     * @param reqParamList
     * @return
     */
    @PostResource(name = "Team审核保存", path = "/update", requiredPermission = false)
    @ApiOperation(value = "Team审核保存", response = TeamVerifResult.class)
    @DataSource(name = "stocking")
    @BusinessLog(title = controllerName + "_" +"Team审核保存",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody List<TeamVerif> reqParamList) {

        service.saveOrUpdateBatch(reqParamList);
        return ResponseData.success();

    }


    /**
     * Team审核批量提交
     * @param reqParamList
     * @return
     */
    @PostResource(name = "Team审核批量提交", path = "/commitBatch", requiredPermission = false)
    @ApiOperation(value = "Team审核批量提交", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"Team审核批量提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData commitBatch(@RequestBody List<OperApplyReqVO> reqParamList) {
        return service.commitBatch(reqParamList);
    }




    /**
     * Team审核申请导出
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "/Team审核导出", path = "/exportExcel")
    @ApiOperation("Team审核导出")
    @BusinessLog(title = controllerName + "_" +"Team审核导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(@RequestBody OperApplyInfoReqVO param,HttpServletResponse response) throws Exception {
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
    @GetResource(name = "根据采购订单编号查询Team审核记录", path = "/getByPurchaseNo", requiredPermission = false)
    @ApiOperation(value = "根据采购订单编号查询Team审核记录", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"根据采购订单编号查询Team审核记录",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getByPurchaseNo(@RequestParam String purchaseNo) {
        return service.getByPurchaseNo(purchaseNo);
    }



    /**
     * Team审核超时自动处理
     * @return
     */
    @PostResource(name = "Team审核超时自动处理", path = "/overTimeAction", requiredPermission = false)
    @ApiOperation(value = "Team审核超时自动处理")
    public ResponseData overTimeAction() {

        StockApprovaltimeParameterResult tzg = parameterService.findByAuditorCode(AuditorCodeTwoEnum.TZG.getCode());
        if (ObjectUtil.isNull(tzg)) {
            return ResponseData.error("Team审核超时自动提交参数未设置");
        }

        return service.overTimeAction(tzg);

    }


    /**
     * Team审核导出
     * @param response
     * @throws IOException
     */
    @PostResource(name = "/Team审核导出", path = "/teamVerifyExport")
    @ApiOperation("Team审核导出")
    @BusinessLog(title = controllerName + "_" +"Team审核导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void teamExportExcel(HttpServletResponse response) throws Exception {
       exportOrImportService.teamVerifyExport(response);
    }

    /**
     * Team审核导入
     * @throws IOException
     */
    @PostResource(name = "/Team审核导入", path = "/teamVerifyImport")
    @ApiOperation("Team审核导入")
    @BusinessLog(title = controllerName + "_" +"Team审核导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData teamVerifyImport(MultipartFile file) throws Exception {
      return exportOrImportService.teamVerifyImport(file);
    }


}
