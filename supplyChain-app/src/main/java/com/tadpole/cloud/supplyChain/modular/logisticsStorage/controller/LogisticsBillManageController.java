package com.tadpole.cloud.supplyChain.modular.logisticsStorage.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRoute;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsListToHeadRouteDet;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.LogisticsSignParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params.TbLogisticsListToHeadRouteParam;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.LogisticsBillManageService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsListToHeadRouteDetService;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.service.TbLogisticsListToHeadRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Api(tags = "物流单")
@RestController
@ApiResource(name = "物流单", path = "/logisticsBillManage")
public class LogisticsBillManageController {

    public final String baseName = "物流单";
    public final String paginQueryFunName = baseName + "--分页查询物流单信息";
    public final String queryByLhrCode = baseName + "--通过LhrCode发货批次号-获取头程物流单-明细";
    public final String getClearanceData = baseName + "下载通关数据";
    public final String logisticsTransformhouse = baseName + "--物流转仓";
    public final String delLogisticsOrder = baseName + "--删除";
    public final String returnLogisticsOrder = baseName + "--退回";
    public final String updateLogisticsOrder = baseName + "--更新";
    public final String sign = baseName + "--签收";
    public final String exportFunName = baseName + "--导出";
    public final String importExcel = baseName + "--导入费用信息";
    public final String downImportTemplate = baseName + "--下载费用导入模板";

    @Resource
    private TbLogisticsListToHeadRouteService tbLogisticsListToHeadRouteService;

    @Resource
    private TbLogisticsListToHeadRouteDetService tbLogisticsListToHeadRouteDetService;

    @Resource
    private LogisticsBillManageService logisticsBillManageService;

    /**
     * 分页查询
     *
     * @param tbLogisticsListToHeadRouteParm 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = paginQueryFunName, response = TbLogisticsListToHeadRoute.class)
    @PostResource(name = paginQueryFunName, path = "/list", menuFlag = true)
    @BusinessLog(title = paginQueryFunName, opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbLogisticsListToHeadRouteParam tbLogisticsListToHeadRouteParm) {
        //1.分页参数
        Page page = tbLogisticsListToHeadRouteParm.getPageContext();
        long current = page.getCurrent();
        long size = page.getSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        Page<TbLogisticsListToHeadRouteResult> pageResult = tbLogisticsListToHeadRouteService.paginQuery(tbLogisticsListToHeadRouteParm, current, size);
        //3. 分页结果组装
        return ResponseData.success(new PageResult<>(pageResult));
    }

    /**
     * 通过LhrCode发货批次号获取头程物流单明细
     *
     * @param lhrCode 主键
     * @return 实例对象
     */
    @ApiOperation(value = queryByLhrCode, response = TbLogisticsListToHeadRouteDet.class)
    @GetResource(name = queryByLhrCode, path = "/queryByLhrCode")
    public ResponseData queryByLhrCode(String lhrCode) {
        return ResponseData.success(tbLogisticsListToHeadRouteDetService.queryByLhrCode(lhrCode));
    }

    /**
     * 下载通过数据
     *
     * @param lhrOddNumb
     * @return 实例对象
     */
    @ApiOperation(value = getClearanceData, response = TbLogisticsListToHeadRouteDet.class)
    @GetResource(name = getClearanceData, path = "/getClearanceData")
    public ResponseData getClearanceData(String lhrOddNumb, HttpServletResponse response) throws IOException {

        List<TbShipemtListClearancModel> clearancList = logisticsBillManageService.getClearanceData(lhrOddNumb);
        if (ObjectUtil.isEmpty(clearancList)) {
            return ResponseData.error("传入的物流单号" + lhrOddNumb + "未找到相关数据");
        }
        List<TbLogisticsPackingListDet1Result> clearanceBoxInfoData = logisticsBillManageService.getClearanceBoxInfoData(lhrOddNumb);
        String fileName = lhrOddNumb + "_通过数据.xlsx";
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        WriteSheet sheet0 = EasyExcel.writerSheet(0, "通关信息").head(TbShipemtListClearancModel.class).build();
        WriteSheet sheet1 = EasyExcel.writerSheet(1, "重量信息").head(TbLogisticsPackingListDet1Result.class).build();
        excelWriter.write(clearancList, sheet0);
        excelWriter.write(clearanceBoxInfoData, sheet1);
        excelWriter.finish();
        response.flushBuffer();
        return ResponseData.success();
    }


    /**
     * 物流转仓
     *
     * @param param
     * @return
     */
    @ApiOperation(value = logisticsTransformhouse, response = ResponseData.class)
    @PostResource(name = logisticsTransformhouse, path = "/logisticsTransformhouse")
    @BusinessLog(title = logisticsTransformhouse, opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData logisticsTransformhouse(@RequestBody TbLogisticsListToHeadRouteParam param) {
        return tbLogisticsListToHeadRouteDetService.logisticsTransformhouse(param);
    }

    /**
     * 删除
     * <p>
     * 1--推送物流状态至海外仓出库任务
     * 2--推送物流单删除信息至海外仓入库任务
     * 3--推送删除物流实际结算信息
     * 4--删除物流单
     *
     * @param lhrCode
     * @param lhrOddNumb
     * @return
     */
    @ApiOperation(value = delLogisticsOrder, response = ResponseData.class)
    @GetResource(name = delLogisticsOrder, path = "/delLogisticsOrder")
    @BusinessLog(title = delLogisticsOrder, opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delLogisticsOrder(@RequestParam String lhrCode, @RequestParam String lhrOddNumb) {
        return logisticsBillManageService.delLogisticsOrder(lhrCode, lhrOddNumb);
    }

    /**
     * 退回
     * <p>
     * 1--推送物流状态至海外仓出库任务
     * 2--推送物流单删除信息至海外仓入库任务
     * 3--推送删除物流实际结算信息
     * 4--更改物流单状态为 返仓
     *
     * @param lhrCode
     * @param lhrOddNumb
     * @return
     */
    @ApiOperation(value = returnLogisticsOrder, response = ResponseData.class)
    @GetResource(name = returnLogisticsOrder, path = "/returnLogisticsOrder")
    @BusinessLog(title = returnLogisticsOrder, opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData returnLogisticsOrder(@RequestParam String lhrCode, @RequestParam String lhrOddNumb) {
        return logisticsBillManageService.returnLogisticsOrder(lhrCode, lhrOddNumb);
    }


    /**
     * 更新物流单
     *
     * @param request
     * @return
     */
    @ApiOperation(value = updateLogisticsOrder, response = ResponseData.class)
    @PostResource(name = updateLogisticsOrder, path = "/updateLogisticsOrder")
    @BusinessLog(title = updateLogisticsOrder, opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateLogisticsOrder(@RequestBody TbLogisticsListToHeadRouteParam request) {
        return logisticsBillManageService.updateLogisticsOrder(request);
    }

    /**
     * 签收
     *
     * @param signParam
     * @return
     */
    @ApiOperation(value = sign, response = ResponseData.class)
    @PostResource(name = sign, path = "/sign")
    @BusinessLog(title = sign, opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData sign(@RequestBody LogisticsSignParam signParam) {
        return logisticsBillManageService.sign(signParam);
    }


    /**
     * 导出
     *
     * @param tbLogisticsListToHeadRouteParm 筛选条件
     * @return ResponseData
     */
    @PostResource(name = exportFunName, path = "/export")
    @ApiOperation(value = exportFunName, response = TbBscOverseasWayResult.class)
    @BusinessLog(title = exportFunName, opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbLogisticsListToHeadRouteParam tbLogisticsListToHeadRouteParm, HttpServletResponse response) throws IOException {

        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        List<TbLogisticsBillManageExportResult> records = tbLogisticsListToHeadRouteService.billManageExport(tbLogisticsListToHeadRouteParm);

        if (Objects.isNull(records) || records.size() == 0) {
            return ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流单.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsBillManageExportResult.class).sheet("物流单").doWrite(records);
        return ResponseData.success();
    }


    /**
     * 下载费用导入模板
     *
     * @param tbLogisticsListToHeadRouteParm 筛选条件
     * @return ResponseData
     */
    @GetResource(name = downImportTemplate, path = "/downImportTemplate")
    @ApiOperation(value = downImportTemplate, response = TbBscOverseasWayResult.class)
    @BusinessLog(title = downImportTemplate, opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downImportTemplate(@RequestBody TbLogisticsListToHeadRouteParam tbLogisticsListToHeadRouteParm, HttpServletResponse response) throws IOException {

        //1.分页参数
       /* long current = 1L;
        long size = Integer.MAX_VALUE;
        *//*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*//*
        Page<TbLogisticsListToHeadRouteResult> pageResult = tbLogisticsListToHeadRouteService.paginQuery(tbLogisticsListToHeadRouteParm, current, size);


        List<TbLogisticsListToHeadRouteResult> records = pageResult.getRecords();
        if (Objects.isNull(records) || records.size() == 0) {
            return ResponseData.success();
        }
        List<TbLogisticsBillManageImportParam> importParamList = new ArrayList<>();
        for (TbLogisticsListToHeadRouteResult record : records) {
            TbLogisticsBillManageImportParam importParam = new TbLogisticsBillManageImportParam();
            importParam.setLhrOddNumb(record.getLhrOddNumb());
            importParamList.add(importParam);
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流单费用导入.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbLogisticsBillManageImportParam.class).sheet("物流单费用导入").doWrite(importParamList);

        return ResponseData.success();*/
        new ExcelUtils().downloadExcel(response, "/template/物流单费用导入.xlsx");
    }


    /**
     * 导入预估费用
     *
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = importExcel, path = "/import", requiredPermission = false)
    @ApiOperation(value = importExcel)
    @BusinessLog(title = importExcel, opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
        return logisticsBillManageService.importExcel(file);
    }

}
