package com.tadpole.cloud.operationManagement.modular.stock.controller;


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
import com.tadpole.cloud.operationManagement.modular.stock.constants.AuditorCodeTwoEnum;
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfo;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.OperApplyInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResult;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.OperApplyInfoResultV3;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApprovaltimeParameterResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IExportOrImportService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IOperApplyInfoServiceV3;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApprovaltimeParameterService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
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
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.math.BigDecimal;
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
@ApiResource(name = "日常备货申请V3", path = "/operApplyInfoV3")
@Api(tags = "日常备货申请V3")
public class OperApplyInfoV3Controller {

    @Autowired
    private IOperApplyInfoServiceV3 service;

    @Autowired
    private IExportOrImportService exportOrImportService;

    @Autowired
    private IStockApprovaltimeParameterService parameterService;

    private final String controllerName = "日常备货申请V3";


    /**
     * 日常备货审核列表查询
     * @param reqVO
     * @return
     */
    @PostResource(name = "日常备货申请V3", path = "/list", menuFlag = true, requiredPermission = false,
            materialPermission = false, areaPermission = false)
    @ApiOperation(value = "日常备货申请V3", response = OperApplyInfoResultV3.class)
    @DataScope(platformAreaAlias="a",platformField = "PLATFORM",areaField = "AREA")
    @BusinessLog(title = controllerName + "_" +"日常备货申请V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OperApplyInfoReqV3 reqVO) {
        return ResponseData.success(service.queryList(reqVO));
    }


    /**
     * 合并显示-不备货
     * @param
     * @return
     */
    @PostResource(name = "合并显示-不备货V3", path = "/mergeNoStockBatch",requiredPermission = false )
    @ApiOperation(value = "合并显示-不备货V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"合并显示-不备货V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData mergeNoStockBatch(@RequestBody List<String> idArrayList) {
        return service.batchNoStocking(idArrayList);
    }

    /**
     * 合并显示-使用推荐值
     * @param
     * @return
     */
    @PostResource(name = "合并显示-使用推荐值V3", path = "/mergeUseAdviceBatch",requiredPermission = false )
    @ApiOperation(value = "合并显示-使用推荐值V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"合并显示-使用推荐值V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData mergeUseAdviceBatch(@RequestBody List<String> idArrayList) {
        return service.batchUseAdvice(idArrayList);
    }

    /**
     * 合并显示-批量提交
     * @param idArrayList
     * @return
     */
    @PostResource(name = "合并显示-批量提交V3", path = "/mergeCommitBatch", requiredPermission = false)
    @ApiOperation(value = "合并显示-批量提交V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"合并显示-批量提交V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData mergeCommitBatch(@NotEmpty  @RequestBody List<String> idArrayList) {
//        return service.mergeCommitBatch(idArrayList);
        return service.mergeCommitBatchFast(idArrayList,null);
    }

    /**
     * 合并显示-查看合并的明细数据
     * @param idList
     * @return
     */
    @PostResource(name = "合并显示-查看合并的明细数据V3", path = "/mergeDetail", requiredPermission = false)
    @ApiOperation(value = "合并显示-查看合并的明细数据V3", response = OperApplyInfo.class)
    @BusinessLog(title = controllerName + "_" +"合并显示-查看合并的明细数据V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData mergeDetail(@RequestBody List<BigDecimal> idList) {
        return service.mergeDetail(idList);
    }


    /**
     * 明细数据批量更新
     * @param applyInfoList
     * @return
     */
    @PostResource(name = "运营申请明细数据批量-更新V3", path = "/updateDetailBatch", requiredPermission = false)
    @ApiOperation(value = "运营申请明细数据批量-更新V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"运营申请明细数据批量-更新V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateDetailBatch(@RequestBody List<OperApplyInfo> applyInfoList) {
        return service.updateDetailBatch(applyInfoList);
    }



    /**
     * 运营申请明细数据批量-提交V3
     * @param applyInfoList
     * @return
     */
    @PostResource(name = "运营申请明细数据批量-提交V3", path = "/commitDetailBatch", requiredPermission = false)
    @ApiOperation(value = "运营申请明细数据批量-提交V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"运营申请明细数据批量-提交V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData commitDetailBatch(@RequestBody List<OperApplyInfo> applyInfoList) {
        return service.commitDetailBatch(applyInfoList);
    }



    /**
     * 物料申请数量统计-V3
     * @param materialCode
     * @return
     */
    @GetResource(name = "物料申请数量统计-V3", path = "/applyQtyCountMat", requiredPermission = false)
    @ApiOperation(value = "物料申请数量统计-V3", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"物料申请数量统计-V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData applyQtyCountMat(@NotEmpty String materialCode) {
        return service.applyQtyCountMat(materialCode);
    }


    /**
     * 日常备货申请导入V3
     * @param file
     * @return 返回导入成功的数据
     * @throws IOException
     */
    @ParamValidator
    @PostResource(name = "日常备货申请导入V3", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "日常备货申请导入V3")
    @BusinessLog(title = controllerName + "_" +"日常备货申请导入V3",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam("file") MultipartFile file) throws IOException {
        return exportOrImportService.operImportV3(file);
    }



    /**
     * 运营申请超时自动处理
     * @return
     */
    @PostResource(name = "运营申请超时自动处理V3", path = "/overTimeAction", requiredPermission = false)
    @ApiOperation(value = "运营申请超时自动处理V3")
    public ResponseData overTimeAction() {
        StockApprovaltimeParameterResult yyry = parameterService.findByAuditorCode(AuditorCodeTwoEnum.YYRY.getCode());
        if (ObjectUtil.isNull(yyry)) {
            return ResponseData.error("运营超时自动提交参数未设置");
        }
        return service.overTimeAction(yyry);
    }


//-------------------------------------------------------------------------------------

    @PostResource(name = "按条件查询批量填充销售需求V3", path = "/batchFillSalesDemand", menuFlag = true,requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "按条件查询批量填充销售需求V3", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"按条件查询批量填充销售需求V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchFillSalesDemand(@RequestBody OperApplyInfoReqVO reqVO) {
        return service.batchFillSalesDemand(reqVO);
    }


    @PostResource(name = "日常备货记录V3", path = "/recordList")
    @ApiOperation(value = "日常备货记录V3", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"日常备货记录V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData recordList(@RequestBody OperApplyInfoReqVO reqVO) {

        return ResponseData.success(service.recordList(reqVO));
    }
    /**
     * 日常备货申请修改
     * @param applyInfo
     * @return
     */
    @PostResource(name = "日常备货申请修改V3", path = "/update", requiredPermission = false)
    @ApiOperation(value = "日常备货申请修改V3", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"日常备货申请修改V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody OperApplyInfo applyInfo) {

        return service.update(applyInfo);
    }

    /**
     * 日常备货申请批量修改
     * @param applyInfos
     * @return
     */
    @PostResource(name = "日常备货申请批量修改V3", path = "/updateBatch", requiredPermission = false)
    @ApiOperation(value = "日常备货申请批量修改V3", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"日常备货申请批量修改V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateBatch(@RequestBody List<OperApplyInfo> applyInfos) {

        return service.updateBatch(applyInfos);
    }

    /**
     * 日常备货申请批量提交
     * @param reqParamList
     * @return
     */
    @PostResource(name = "日常备货申请批量提交V3", path = "/commitBatch", requiredPermission = false)
    @ApiOperation(value = "日常备货申请批量提交V3", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"日常备货申请批量提交V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData commitBatch(@RequestBody List<OperApplyReqVO> reqParamList) {

        return service.commitBatch(reqParamList);
    }

    /**
     * 日常备货字段说明Excel模板
     * @param response
     */
    @GetResource(name = "日常备货字段说明Excel模板V3", path = "/downloadDocumentExcel", requiredPermission = false )
    @ApiOperation("日常备货字段说明Excel模板V3")
    @BusinessLog(title = controllerName + "_" +"日常备货字段说明Excel模板V3",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadDocumentExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/字段说明2.0.xlsx");
    }


    /**
     * 日常备货申请导出
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "/日常备货申请导出V3", path = "/exportExcel", requiredPermission = false )
    @ApiOperation("日常备货申请导出V3")
    @BusinessLog(title = controllerName + "_" +"日常备货申请导出V3",opType = LogAnnotionOpTypeEnum.EXPORT)
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
     * 日常备货申请导入提交
     * @param file
     * @return
     * @throws IOException
     */
    @ParamValidator
    @PostResource(name = "日常备货申请导入提交V3", path = "/uploadCommit", requiredPermission = false)
    @ApiOperation(value = "日常备货申请导入提交V3")
    @BusinessLog(title = controllerName + "_" +"日常备货申请导入提交V3",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadCommit(@RequestParam("file") MultipartFile file) throws IOException {
//        return exportOrImportService.operImportComit2(file);
        return exportOrImportService.operImportComit3(file);
    }



    /**
     * 按条件批量填充 salesStockDays  推荐备货天数
     * @param reqVO
     * @return
     */
    @PostResource(name = "按条件批量填充推荐备货天数V3", path = "/fillSalesStockDays", menuFlag = false,requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "按条件批量填充推荐备货天数V3", response = OperApplyInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"按条件批量填充推荐备货天数V3",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData fillSalesStockDays(@RequestBody OperApplyInfoReqVO reqVO) {

        return service.fillSalesStockDays(reqVO);
    }






}
