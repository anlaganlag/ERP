package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.constants.StockConstant;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PmcVerifInfoParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.PurchaseOrdersParam;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "备货2.0-PMC审批")
@ApiResource(name = "备货2.0-PMC审批", path = "/pmcVerif")
public class PmcVerifController {

    @Autowired private IPurchaseOrdersService service;
    @Autowired private IVerifInfoRecordService verService;


    private final String controllerName = "备货2.0-PMC审批";


    @Autowired private IPmcVerifInfoService veriInfoService;

    @PostResource(name = "备货2.0-PMC审批列表", path = "/pmcVerifList", menuFlag = true)
    @ApiOperation(value = "备货2.PMC审批列表", response = PmcVerifInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"备货2.0-PMC审批列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData planVerifList(@RequestBody PmcVerifInfoParam param) {
        return ResponseData.success(veriInfoService.querypmcVerifList(param));
    }

    @GetResource(name = "供应商获取物控专员", path = "/getMatControlPersonBySupplier")
    @ApiOperation(value = "供应商获取物控专员", response = PmcVerifInfoResult.class)
    @BusinessLog(title = controllerName + "_" +"供应商获取物控专员",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getMatControlPersonBySupplier(@RequestParam(value = "supplier", required = false) String supplier,@RequestParam(value = "supplierCode", required = false) String supplierCode) {
        return ResponseData.success(veriInfoService.getMatControlPersonBySupplier(supplier,supplierCode));
    }





    /**
     * PMC审批数据导出
     * @throws IOException
     */
    @PostResource(name = "备货2.0-PMC审批导出", path = "/exportExcel", requiredPermission = false )
    @ApiOperation("备货2.0-PMC审批导出")
    @BusinessLog(title = controllerName + "_" +"备货2.0-PMC审批导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response,@RequestBody PmcVerifInfoParam info) throws IOException {
        veriInfoService.exportExcel(response,info);
    }

    /**
     * PMC审批模板下载
     * @param response
     */
    @GetResource(name = "下载模板", path = "/downloadExcel", requiredPermission = false )
    @ApiOperation("下载模板")
    @BusinessLog(title = controllerName + "_" +"下载模板",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/PMC审批模板.xlsx");
    }

    /**
     * 导入PMC审批Excel
     * @param file
     * @param response
     * @return
     */
    @ParamValidator
    @ApiOperation(value = "导入PMC审批Excel")
    @PostResource(name = "导入PMC审批Excel", path = "/upload", requiredPermission = false)
    @BusinessLog(title = controllerName + "_" +"导入PMC审批Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        if (file == null) {
            return ResponseData.error("导入PMC审批Excel 失败，上传文件为空!");
        }
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<PmcVerifInfoParam>();
            EasyExcel.read(buffer, PmcVerifInfoParam.class, listener).sheet().doRead();
            List<PmcVerifInfoParam> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {

                return ResponseData.error("导入PMC审批Excel 失败，Excel数据为空！");
            }

            if (veriInfoService.saveOrUpdateBatch(dataList, veriInfoService.getAllSupplier())) {

                return ResponseData.success("导入PMC审批Excel 成功 !");
            } else {
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入PMC审批Excel 失败 ，存在异常数据数据!");
            }
        } catch (Exception ex) {
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "导入PMC审批Excel 失败 ! /r/n " + ex.getMessage());
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入PMC审批Excel 关闭流异常", e);
                }
            }
        }
    }

    /**
     * PMC编辑
     * @param param
     * @return
     */
    @PostResource(name = "PMC编辑", path = "/update",requiredPermission = false)
    @ApiOperation(value = "PMC编辑", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"PMC编辑",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit( @RequestBody PmcVerifInfoParam param) {
       return veriInfoService.updateInfo(param);
    }

    /**
     * PMC自动通过
     *
     * @author gal @Date 2021-6-02
     */
    @GetResource(name = "PMC自动通过", path = "/auditAll",requiredPermission = false)
    @ApiOperation(value = "PMC自动通过", response = StockPurchaseApplyResult.class)
    public ResponseData auditAll() {

        return veriInfoService.auditAll();
    }

    /**
     * PMC批量通过
     *
     * @author gal @Date 2021-6-02
     */
    @PostResource(name = "PMC批量通过", path = "/batchPass",requiredPermission = false)
    @ApiOperation(value = "PMC批量通过", response = StockPurchaseApplyResult.class)
    @BusinessLog(title = controllerName + "_" +"PMC批量通过",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData batchPass(@RequestBody List<PmcVerifInfoParam> parmList) {
        if (parmList == null || parmList.size() == 0) {

            return ResponseData.error("未获取到待审核的数据！");
        }
        return veriInfoService.batchVerif(parmList, StockConstant.VERIFY_SUCESS);
    }


    /**
     * 审批通过
     * @param param
     * @return
     */
    @PostResource(name = "PMC审批", path = "/audit", menuFlag = true)
    @ApiOperation(value = "PMC审批", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"PMC审批",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData  audit(@RequestBody PmcVerifInfoParam param) {
        return veriInfoService.pmcVerif(param);
    }

    /**
     * PMC驳回
     *
     * @author gal @Date 2021-6-02
     */
    @PostResource(name = "PMC驳回", path = "/reject",requiredPermission = false)
    @ApiOperation(value = "PMC驳回", response = StockPurchaseApplyResult.class)
    @BusinessLog(title = controllerName + "_" +"PMC驳回",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData reject(@RequestBody PmcVerifInfoParam param) {
        return veriInfoService.pmcVerif(param);
    }

    /**
     * PMC批量驳回
     *
     * @author gal @Date 2021-6-02
     */
    @PostResource(name = "PMC批量驳回", path = "/rejectList",requiredPermission = false)
    @ApiOperation(value = "PMC批量驳回", response = StockPurchaseApplyResult.class)
    @BusinessLog(title = controllerName + "_" +"PMC批量驳回",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData rejectList(@RequestBody List<PmcVerifInfoParam> parmList) {
        if (parmList == null || parmList.size() == 0) {

            return ResponseData.error("未获取到待审核的数据！");
        }
        return veriInfoService.batchVerif(parmList, StockConstant.VERIFY_FAIL);
    }

    /**
     * 查询供应商信息
     *
     * @author gal @Date 2021-6-02
     */
    @PostResource(name = "查询供应商信息", path = "/supplier",requiredPermission = false)
    @ApiOperation(value = "查询供应商信息", response = StockPurchaseApplyResult.class)
    @BusinessLog(title = controllerName + "_" +"查询供应商信息",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData supplier(@RequestBody PmcVerifInfoParam param) {
        HashMap<String, String> allSupplier = veriInfoService.getAllSupplier();//key 供应商名称  value 供应商id
        return  veriInfoService.getSupplierByName(param,allSupplier);
    }

    @GetResource(name = "详情", path = "/detail", menuFlag = true)
    @ApiOperation(value = "详情", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"详情",opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData  detail (PurchaseOrdersParam param) {
        return ResponseData.success(service.detail(param));
    }

    /**
     * SELECT TOP
     * 	1 f.FID,
     * 	f.FTAXRATE AS 税率,
     * 	a.FBILLNO AS k3单据编号,
     * 	b.FNUMBER AS 供应编号,
     * 	c.FNAME AS 供应商,
     * 	d.FNUMBER AS 采购员工号,
     * 	e.FNAME AS 采购员,
     * 	f.FNOTE 备注,
     * 	a.FCREATEDATE AS 创建时间
     * FROM
     * 	(--找出最近下单的单号
     * 	SELECT
     * 		a.FID ,
     * 		a.FNOTE,
     * 		aa.FTAXRATE
     * 	FROM
     * 		t_PUR_POOrderEntry a
     * 		LEFT JOIN t_PUR_POOrderEntry_F aa ON a.FID= aa.FID
     * 	WHERE
     * 		a.FMATERIALID= ( SELECT FMATERIALID FROM T_BD_MATERIAL WHERE FNUMBER = 'PPT210078' AND FUSEORGID = 100269 )
     * 		AND a.F_PAEZ_BASE= ( SELECT FDEPTID FROM T_BD_DEPARTMENT_L WHERE FNAME = 'Team6' ) -- ORDER BY FENTRYID DESC
     *
     * 	) f
     * 	LEFT JOIN t_PUR_POOrder a ON a.FID = f.FID
     * 	LEFT JOIN T_BD_SUPPLIER b ON b.FSUPPLIERID= a.FSUPPLIERID
     * 	LEFT JOIN T_BD_SUPPLIER_L c ON c.FSUPPLIERID= a.FSUPPLIERID
     * 	LEFT JOIN V_BD_BUYER d ON d.FID= a.FPURCHASERID
     * 	LEFT JOIN T_BD_STAFF_L e ON e.FSTAFFID= d.FSTAFFID
     * ORDER BY
     * 	a.FCREATEDATE DESC
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * 	SELECT  TOP 1
     * 	f.FID,
     * 	f.MATERIAL_CODE,
     * 	f.team,
     * 	f.FTAXRATE AS 税率,
     * 	a.FBILLNO AS k3单据编号,
     * 	b.FNUMBER AS 供应编号,
     * 	c.FNAME AS 供应商,
     * 	d.FNUMBER AS 采购员工号,
     * 	e.FNAME AS 采购员,
     * 	f.FNOTE 备注,
     * 	a.FCREATEDATE AS 创建时间
     * FROM
     * 	(--找出最近下单的单号
     * 	SELECT
     * 		ab.FNUMBER MATERIAL_CODE,
     * 		ac.FNAME as team,
     * 		a.FID ,
     * 		a.FNOTE,
     * 		aa.FTAXRATE
     * 	FROM
     * 		t_PUR_POOrderEntry a
     * 		LEFT JOIN t_PUR_POOrderEntry_F aa ON a.FID= aa.FID
     * 		LEFT JOIN T_BD_MATERIAL ab on a.FMATERIALID =ab.FMATERIALID
     * 		LEFT JOIN T_BD_DEPARTMENT_L ac on a.F_PAEZ_BASE=ac.FDEPTID
     * 	WHERE
     * 		ab.FUSEORGID = 100269
     * 		-- ORDER BY FENTRYID DESC
     *
     * 	) f
     * 	LEFT JOIN t_PUR_POOrder a ON a.FID = f.FID
     * 	LEFT JOIN T_BD_SUPPLIER b ON b.FSUPPLIERID= a.FSUPPLIERID
     * 	LEFT JOIN T_BD_SUPPLIER_L c ON c.FSUPPLIERID= a.FSUPPLIERID
     * 	LEFT JOIN V_BD_BUYER d ON d.FID= a.FPURCHASERID
     * 	LEFT JOIN T_BD_STAFF_L e ON e.FSTAFFID= d.FSTAFFID
     *
     * 	WHERE
     * 	f.MATERIAL_CODE='PPT210078'
     * 	AND f.team='Team6'
     *
     * ORDER BY
     * 	a.FCREATEDATE DESC
     *
     *
     *
     */
}
