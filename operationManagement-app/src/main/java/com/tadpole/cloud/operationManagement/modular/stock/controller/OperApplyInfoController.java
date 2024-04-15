package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.constants.AuditorCodeTwoEnum;
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IExportOrImportService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IOperApplyInfoService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApprovaltimeParameterService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqVO;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyReqVO;
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
import java.util.List;

/**
* <p>
    * 备货申请信息 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@Slf4j
@RestController
@ApiResource(name = "日常备货申请", path = "/operApplyInfo")
@Api(tags = "日常备货申请")
public class OperApplyInfoController {

    @Autowired
    private IOperApplyInfoService service;

    @Autowired
    private IExportOrImportService exportOrImportService;

    @Autowired
    private IStockApprovaltimeParameterService parameterService;

    private final String controllerName = "日常备货申请";


    /**
     * 日常备货审核列表查询
     * @param reqVO
     * @return
     */
    @PostResource(name = "日常备货申请", path = "/list", menuFlag = true,requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "日常备货申请", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"日常备货申请",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OperApplyInfoReqVO reqVO) {

        return ResponseData.success(service.queryPage(reqVO));
    }

    @PostResource(name = "按条件查询批量填充销售需求", path = "/batchFillSalesDemand", menuFlag = true,requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "按条件查询批量填充销售需求", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"按条件查询批量填充销售需求",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchFillSalesDemand(@RequestBody OperApplyInfoReqVO reqVO) {
        return service.batchFillSalesDemand(reqVO);
    }

    @PostResource(name = "日常备货记录", path = "/recordList")
    @ApiOperation(value = "日常备货记录", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"日常备货记录",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData recordList(@RequestBody OperApplyInfoReqVO reqVO) {

        return ResponseData.success(service.recordList(reqVO));
    }
    /**
     * 日常备货申请修改
     * @param applyInfo
     * @return
     */
    @PostResource(name = "日常备货申请修改", path = "/update", requiredPermission = false)
    @ApiOperation(value = "日常备货申请修改", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"日常备货申请修改",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody OperApplyInfo applyInfo) {
        return service.update(applyInfo);
    }

    /**
     * 日常备货申请批量修改
     * @param applyInfos
     * @return
     */
    @PostResource(name = "日常备货申请批量修改", path = "/updateBatch", requiredPermission = false)
    @ApiOperation(value = "日常备货申请批量修改", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"日常备货申请批量修改",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateBatch(@RequestBody List<OperApplyInfo> applyInfos) {

        return service.updateBatch(applyInfos);
    }

    /**
     * 日常备货申请批量提交
     * @param reqParamList
     * @return
     */
    @PostResource(name = "日常备货申请批量提交", path = "/commitBatch", requiredPermission = false)
    @ApiOperation(value = "日常备货申请批量提交", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"日常备货申请批量提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData commitBatch(@RequestBody List<OperApplyReqVO> reqParamList) {

        return service.commitBatch(reqParamList);
    }

    /**
     * 日常备货字段说明Excel模板
     * @param response
     */
    @GetResource(name = "日常备货字段说明Excel模板", path = "/downloadDocumentExcel", requiredPermission = false )
    @ApiOperation("日常备货字段说明Excel模板")
    @BusinessLog(title = controllerName + "_" +"日常备货字段说明Excel模板",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadDocumentExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/字段说明2.0.xlsx");
    }


    /**
     * 日常备货申请导出
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "/日常备货申请导出", path = "/exportExcel", requiredPermission = false )
    @ApiOperation("日常备货申请导出")
    @BusinessLog(title = controllerName + "_" +"日常备货申请导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(@RequestBody OperApplyInfoParam param, HttpServletResponse response) throws IOException {

        try {
//            exportOrImportService.operExport(response);
//            exportOrImportService.operExportFast(response);
            exportOrImportService.operExportFast2(response);
        } catch (Exception e) {
            log.error("日常备货申请导出异常：异常信息：{}",e);
//            e.printStackTrace();
            return;
        }
    }

    /**
     * 日常备货申请导入
     * @param file
     * @return
     * @throws IOException
     */
    @ParamValidator
    @PostResource(name = "日常备货申请导入", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "日常备货申请导入")
    @BusinessLog(title = controllerName + "_" +"日常备货申请导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam("file") MultipartFile file) throws IOException {
        return exportOrImportService.operImport(file);
    }

    /**
     * 日常备货申请导入提交
     * @param file
     * @return
     * @throws IOException
     */
    @ParamValidator
    @PostResource(name = "日常备货申请导入提交", path = "/uploadCommit", requiredPermission = false)
    @ApiOperation(value = "日常备货申请导入提交")
    @BusinessLog(title = controllerName + "_" +"日常备货申请导入提交",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadCommit(@RequestParam("file") MultipartFile file) throws IOException {
//        return exportOrImportService.operImportComit2(file);
        return exportOrImportService.operImportComit3(file);
    }

    /**
     * 运营申请超时自动处理
     * @return
     */
    @PostResource(name = "运营申请超时自动处理", path = "/overTimeAction", requiredPermission = false)
    @ApiOperation(value = "运营申请超时自动处理")
    public ResponseData overTimeAction() {
        StockApprovaltimeParameterResult yyry = parameterService.findByAuditorCode(AuditorCodeTwoEnum.YYRY.getCode());
        if (ObjectUtil.isNull(yyry)) {
            return ResponseData.error("运营超时自动提交参数未设置");
        }
        return service.overTimeAction(yyry);
    }


    /**
     * 按条件批量填充 salesStockDays  推荐备货天数
     * @param reqVO
     * @return
     */
    @PostResource(name = "按条件批量填充推荐备货天数", path = "/fillSalesStockDays", menuFlag = false,requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "按条件批量填充推荐备货天数", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"按条件批量填充推荐备货天数",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData fillSalesStockDays(@RequestBody OperApplyInfoReqVO reqVO) {

        return service.fillSalesStockDays(reqVO);
    }

}
