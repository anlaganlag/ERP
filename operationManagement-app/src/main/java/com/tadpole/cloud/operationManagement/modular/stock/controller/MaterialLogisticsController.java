package com.tadpole.cloud.operationManagement.modular.stock.controller;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
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
import com.tadpole.cloud.operationManagement.modular.stock.entity.EntMaterialLogistics;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SysMaterialParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SysMaterialResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.MaterialLogisticsService;
import com.tadpole.cloud.operationManagement.modular.stock.verify.MaterialLogisticsVerifyHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * 物料运输方式 前端控制器
 *
 * @author gal
 * @since 2021-07-27
 */
@RestController
@ApiResource(name = "物料运输方式", path = "/MaterialLogistics")
@Api(tags = "物料运输方式")
public class MaterialLogisticsController {

  @Resource
  private MaterialLogisticsService materialLogisticsService;
  @Resource
  private MaterialLogisticsVerifyHandler materialLogisticsVerifyHandler;

  private final String controllerName = "物料运输方式";


  @PostResource(name = "物料运输方式", path = "/list", menuFlag = true, requiredPermission = false)
  @ApiOperation(value = "物料运输方式", response = SysMaterialResult.class)
  @BusinessLog(title = controllerName + "_" +"物料运输方式",opType = LogAnnotionOpTypeEnum.QUERY)
  public ResponseData queryListPage(@RequestBody SysMaterialParam param) {
    PageResult<SysMaterialResult> pageBySpec = materialLogisticsService.findPageBySpec(param);

    return ResponseData.success(pageBySpec);
  }

  @PostResource(name = "批量添加", path = "/addBatch", requiredPermission = false)
  @ApiOperation("批量添加")
  @BusinessLog(title = controllerName + "_" +"批量添加",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData addBatch(@RequestBody List<EntMaterialLogistics> list) throws ParseException {
    materialLogisticsService.insertBatch(list);
    return ResponseData.success();
  }

  @PostResource(name = "批量更新", path = "/updateBatch", requiredPermission = false)
  @ApiOperation("批量更新")
  @BusinessLog(title = controllerName + "_" +"批量更新",opType = LogAnnotionOpTypeEnum.UPDATE)
  public ResponseData updateBatch(@RequestBody List<EntMaterialLogistics> list)
      throws ParseException {
    LoginContext current= SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    list.forEach(e->{
      e.setUpdateUser(currentUser.getAccount());
    });

    materialLogisticsService.updateBatch(list);
    return ResponseData.success();
  }

  @PostResource(name = "批量删除", path = "/deleteBatch",requiredPermission = false)
  @ApiOperation("批量删除")
  @BusinessLog(title = controllerName + "_" +"批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
  public ResponseData deleteBatch (@RequestBody  List<String> list) throws ParseException {
    materialLogisticsService.deleteBatch(list);
    return ResponseData.success();
  }


  @PostResource(name = "批量保存或更新", path = "/saveOrUpdateBatch", requiredPermission = false)
  @ApiOperation("批量保存或更新")
  @BusinessLog(title = controllerName + "_" +"批量保存或更新",opType = LogAnnotionOpTypeEnum.UPDATE)
  public ResponseData saveOrUpdateBatch(@RequestBody List<EntMaterialLogistics> list)
      throws ParseException {
    materialLogisticsService.insertOrUpdateBatchById(list);
    return ResponseData.success();
  }


  @GetResource(name = "自动分析", path = "/analysis", requiredPermission = false)
  @ApiOperation("自动分析")
  @BusinessLog(title = controllerName + "_" +"自动分析",opType = LogAnnotionOpTypeEnum.ADD)
  public ResponseData analysis(SysMaterialParam param){
    materialLogisticsService.analysis(param);
    return ResponseData.success();
  }


  @ParamValidator
  @ApiOperation(value = "导入Excel")
  @PostResource(name = "导入Excel", path = "/upload", requiredPermission = false)
  @BusinessLog(title = controllerName + "_" +"导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
  public ResponseData upload(
          @RequestParam("file") MultipartFile file, HttpServletResponse response) {
    try {

      ImportParams params = new ImportParams(); // 设置验证支持
      params.setVerifyHandler(materialLogisticsVerifyHandler); // 设置一个验证处理器

      ExcelImportResult<EntMaterialLogistics> importResult =
              ExcelUtils.importExcelMore(file, EntMaterialLogistics.class, params);
      // 验证通过的数据
//      List<EntMaterialLogistics> list = importResult.getList();
      List<EntMaterialLogistics> list = new ArrayList<>( new HashSet<>(importResult.getList()));

      // 验证未通过的数据
      List<EntMaterialLogistics> failList = importResult.getFailList();
      materialLogisticsService.insertBatch(list);
      if (failList != null && failList.size() > 0) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName = simpleDateFormat.format(new Date()) + ".xlsx";
        Workbook failWorkbook = importResult.getFailWorkbook();
        FileOutputStream fos = new FileOutputStream(filePath + fileName, true);
        failWorkbook.write(fos);
        return ResponseData.error(fileName);
      }
      return ResponseData.success();
    } catch (Exception e) {
      return ResponseData.error(e + "上传失败");
    }
  }



  /**
   * @program: myutil
   * @description: 从本地项目(本地磁盘上)下载静态文件
   * @author: lsy
   * @create: 2020-08-13 16:58
   */
  @GetResource(
          name = "下载模板",
          path = "/downloadExcel",
          requiredPermission = false,
          requiredLogin = false)
  @ApiOperation("下载模板")
  @BusinessLog(title = controllerName + "_" +"下载模板",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void downloadExcel(HttpServletResponse response) {
    String path = "/template/物料运输方式.xlsx";
    ExcelUtils excelUtils = new ExcelUtils();
    excelUtils.downloadExcel(response, path);
  }

  @PostResource(
          name = "/导出全部数据",
          path = "/exportExcel",
          requiredPermission = false)
  @ApiOperation("导出Excel列表")
  @BusinessLog(title = controllerName + "_" +"导出Excel列表",opType = LogAnnotionOpTypeEnum.EXPORT)
  public void exportExcel(HttpServletResponse response, @RequestBody SysMaterialParam param) throws IOException {
    param.setPageSize(new Long(Integer.MAX_VALUE));

    List<SysMaterialResult> sysMaterialList = materialLogisticsService.findPageBySpec(param).getRows();

    response.addHeader("Content-Disposition", "attachment;filename=" + new String("发货方式设置导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
    EasyExcel.write(response.getOutputStream(), SysMaterialResult.class).sheet("发货方式设置导出").doWrite(sysMaterialList);
//    ExcelUtils.exportExcel(
//        sysMaterialList, "物料运输方式数据", "sheet1", SysMaterialResult.class, "物料运输方式数据", response);
//  }





  }



}
