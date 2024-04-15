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
import com.tadpole.cloud.operationManagement.modular.stock.entity.ProductLine2;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProductLine2Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.ProductLineReplaceUpdateParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.ProductLine2Result;
import com.tadpole.cloud.operationManagement.modular.stock.service.IProductLine2Service;
import com.tadpole.cloud.operationManagement.modular.stock.verify.VerifyProductLine2;
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
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 产品线 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2022-06-16
 */
@Slf4j
@RestController
@ApiResource(name = "产品线分配设置New", path = "/productLine2")
@Api(tags = "产品线分配设置New")
public class ProductLine2Controller {

    @Autowired private IProductLine2Service service;
    @Autowired private VerifyProductLine2 verify;

    private final String controllerName = "产品线分配设置New";


    /**
     * 产品线分配-列表查询
     *
     * @param param
     * @return
     */
    @GetResource(name = "产品线分配-列表查询", path = "/list", menuFlag = true)
    @ApiOperation(value = "产品线分配-列表查询", response = ProductLine2.class)
    @BusinessLog(title = controllerName + "_" +"产品线分配-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(ProductLine2Param param) {
        return service.queryListPage(param);
    }

    /**
     * 产品线分配-导出数据"
     *
     * @param response
     * @param param
     */
    @GetResource(name = "/产品线分配-导出数据", path = "/exportExcel", requiredPermission = false )
    @ApiOperation(value = "产品线分配-导出数据",response = ProductLine2.class)
    @BusinessLog(title = controllerName + "_" +"合并明细审批V3",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportExcell(@RequestParam(required = false) String productType ,@RequestParam(required = false) String platform, @RequestParam(required = false) String area,
                                     @RequestParam(required = false) String deptMgrName,@RequestParam(required = false) String teamSuperviseName,@RequestParam(required = false) String operatorName,
                                     @RequestParam(required = false) String department,@RequestParam(required = false) String team ,@RequestParam(required = false) String pageNo ,@RequestParam(required = false) String pageSize,  HttpServletResponse response) throws IOException {
        ProductLine2Param param = new ProductLine2Param();
        param.setProductType(productType);
        param.setPlatform(platform);
        param.setArea(area);
        param.setDeptMgrName(deptMgrName);
        param.setTeamSuperviseName(teamSuperviseName);
        param.setOperatorName(operatorName);
        param.setDepartment(department);
        param.setTeam(team);
        param.setPageNo(new Long(pageNo));
        param.setPageSize(new Long(pageSize));
        List<ProductLine2> records = service.exportExcel(response, param);
        if (Objects.isNull(records) || records.size()==0) {
            return    ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new  String("产品线分配数据.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ProductLine2.class).sheet("产品线分配数据").doWrite(records);
        return ResponseData.success();

    }

    /**
     * 产品线分配-更新
     *
     * @param param
     * @return
     */
    @ApiOperation("产品线分配-更新")
    @PostResource(name = "产品线分配-更新", path = "/update")
    @BusinessLog(title = controllerName + "_" +"产品线分配-更新",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData update(@RequestBody ProductLine2Param param) {
        return service.update(param);
    }

    /**
     * 产品线分配-批量更新
     *
     * @param
     * @return
     */
//    @ApiOperation("产品线分配-批量更新")
//    @PostResource(name = "产品线分配-批量更新", path = "/updateBatch", requiredPermission = false)
//        @BusinessLog(title = controllerName + "_" +"合并明细审批V3",opType = LogAnnotionOpTypeEnum.UPDATE)
//    public ResponseData updateBatch(@RequestBody List<ProductLine2Param> list) {
//        return service.updateBatch(list);
//    }



    @ApiOperation("产品线替换修改-批量更新")
    @PostResource(name = "产品线替换修改-批量更新", path = "/replaceUpdateBatch", requiredPermission = false)
    @BusinessLog(title = controllerName + "_" +"产品线替换修改-批量更新",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData replaceUpdateBatch(@RequestBody ProductLineReplaceUpdateParam obj) {
        return service.replaceUpdateBatch(obj);
    }
    /**
     * 产品线分配-新增
     *
     * @param paramList
     * @return
     */
    @ApiOperation("产品线分配-新增")
    @PostResource(name = "产品线分配-新增", path = "/insertBatch")
    @BusinessLog(title = controllerName + "_" +"产品线分配-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData insertBatch(@RequestBody List<ProductLine2Param> paramList) {
        return service.insertBatch(paramList);
    }

    /**
     * 产品线分配-删除
     *
     * @param
     * @return
     */
    @ApiOperation("产品线分配-删除")
    @PostResource(name = "产品线分配-删除", path = "/delete")
    @BusinessLog(title = controllerName + "_" +"产品线分配-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody ProductLine2Param param) {
        return service.delete(param.getId());
    }

    @ApiOperation("产品线分配-批量删除")
    @PostResource(name = "产品线分配-批量删除", path = "/deleteBatch", requiredPermission = false)
    @BusinessLog(title = controllerName + "_" +"产品线分配-批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody List<Integer> list) throws ParseException {
        return service.deleteBatch(list);
    }

    /**
     * 产品线分配-自动分析 目前不启用。
     * @param param
     * @return
     */
    @GetResource(name = "产品线分配-自动分析", path = "/lineAnalysis", requiredPermission = false)
    @ApiOperation(value = "产品线分配-自动分析", response = ProductLine2Result.class)
    @BusinessLog(title = controllerName + "_" +"产品线分配-自动分析",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData lineAnalysis(ProductLine2Param param) {
        return service.lineAnalysis(param);
    }

    /**
     * 产品线分配-导入Excel
     * @param file
     * @return
     */
    @ParamValidator
    @ApiOperation(value = "产品线分配-导入Excel")
    @PostResource(name = "产品线分配-导入Excel", path = "/upload", requiredPermission = false)
    @BusinessLog(title = controllerName + "_" +"产品线分配-导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        if (file == null) {

            return ResponseData.error("产品线分配-导入Excel 失败，上传文件为空!");
        }

        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<ProductLine2>();
            EasyExcel.read(buffer, ProductLine2.class, listener).sheet().doRead();
            List<ProductLine2> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error("数据为空，无法导入！");
            }
            verify.init();
            return verify.validate(dataList);

           /* //验证
            if (!resData.getSuccess())
                return resData;
            else {
                log.info("产品线分配-导入Excel-开始" + new Date());
                if (service.saveOrUpdateBatch(dataList))
                    resData = ResponseData.success("产品线分配-导入Excel 成功 !");
                else resData =  ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "产品线分配-导入Excel 失败 ，存在异常数据数据!");
                log.info("产品线分配-导入Excel-结束" + new Date());
                return resData;
            }*/
        } catch (Exception ex) {
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "产品线分配-导入Excel 失败 ! /r/n " + ex.getMessage());
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("产品线分配-导入Excel 关闭流异常", e);
                }
            }
        }
    }

    /**
     * 产品线分配-下载模板
     * @param response
     */
    @ApiOperation("产品线分配-下载模板")
    @GetResource(name = "产品线分配-下载模板", path = "/downloadExcel", requiredPermission = false )
    @BusinessLog(title = controllerName + "_" +"产品线分配-下载模板",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void downloadExcel(HttpServletResponse response) {
        String path = "/template/产品线模板New.xlsx";
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.downloadExcel(response, path);
    }
}
