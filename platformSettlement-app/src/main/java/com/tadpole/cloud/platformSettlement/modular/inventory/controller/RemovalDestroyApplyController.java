package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.RpMaterialParam;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalDestroyApply;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalDestroyBaseApplyParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalDestroyApplyResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalDestroyBaseApplyResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemovalDestroyApplyService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.RpMaterialConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
*  前端控制器
* </p>
*
* @author cyt
* @since 2022-05-24
*/
@RestController
@ApiResource(name = "库存移除&销毁申请", path = "/removalDestroyApply")
@Api(tags = "库存移除&销毁申请")
public class RemovalDestroyApplyController {

    @Autowired
    private IRemovalDestroyApplyService service;
    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    @PostResource(name = "库存移除&销毁申请列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "库存移除&销毁申请列表", response = RemovalDestroyApply.class)
    @BusinessLog(title = "库存移除&销毁申请列表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody RemovalDestroyApplyParam param) {
        return ResponseData.success(service.findPageBySpec(param));
    }

    @PostResource(name = "库存移除至服务商申请", path = "/toServiceProviderList", menuFlag = true)
    @ApiOperation(value = "库存移除至服务商申请", response = RemovalDestroyApply.class)
    @BusinessLog(title = "库存移除&销毁申请列表-库存移除至服务商申请查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData toServiceProviderList(@RequestBody RemovalDestroyApplyParam param) {
        List<RemovalDestroyApplyResult> pageBySpec=service.getApplyDetail(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "库存销毁申请", path = "/destroyApplyList", menuFlag = true)
    @ApiOperation(value = "库存销毁申请", response = RemovalDestroyApply.class)
    @BusinessLog(title = "库存移除&销毁申请列表-库存销毁申请查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData destroyApplyList(@RequestBody RemovalDestroyApplyParam param) {
        List<RemovalDestroyApplyResult> pageBySpec=service.getApplyDetail(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "查询明细合计", path = "/listSum", requiredPermission = false)
    @ApiOperation(value = "查询明细合计", response = RemovalDestroyApply.class)
    @BusinessLog(title = "库存移除&销毁申请列表-查询明细合计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSum(@RequestBody RemovalDestroyApplyParam param) {
        RemovalDestroyApplyResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "修改", path = "/update",requiredPermission = false)
    @ApiOperation(value="修改",response = RemovalDestroyApply.class)
    @BusinessLog(title = "库存移除&销毁申请列表-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody RemovalDestroyApplyParam param) {
        return service.edit(param);
    }

    @PostResource(name = "基础信息查询", path = "/baseSelect",requiredPermission = false)
    @ApiOperation(value="基础信息查询",response = RemovalDestroyApply.class)
    @BusinessLog(title = "库存移除&销毁申请列表-基础信息查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData baseSelect(@RequestBody RemovalDestroyBaseApplyParam param) {
        return service.baseSelect(param);
    }

    @PostResource(name = "提交", path = "/submit",requiredPermission = false)
    @ApiOperation(value="提交",response = RemovalDestroyApply.class)
    @BusinessLog(title = "库存移除&销毁申请列表-提交",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData submit(@RequestBody RemovalDestroyBaseApplyParam param) {
        return service.submit(param);
    }

    @PostResource(name = "删除", path = "/delete",requiredPermission = false)
    @ApiOperation(value="删除",response = RemovalDestroyApply.class)
    @BusinessLog(title = "库存移除&销毁申请列表-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody RemovalDestroyApplyParam param) {
        return service.delete(param);
    }

    @PostResource(name = "驳回", path = "/reject",requiredPermission = false)
    @ApiOperation(value = "驳回", response = RemovalDestroyBaseApplyResult.class)
    @BusinessLog(title = "库存移除&销毁申请列表-驳回",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData reject(@RequestBody RemovalDestroyBaseApplyParam param) {
        return service.reject(param);
    }

    @ParamValidator
    @PostResource(name = "库存移除导入Excel", path = "/uploadRemoval", requiredPermission = false)
    @ApiOperation(value = "库存移除导入Excel")
    @BusinessLog(title = "库存移除&销毁申请列表-库存移除导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadRemoval(@ModelAttribute RemovalDestroyBaseApplyParam param, @RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importRemovalApply(param,file);
    }

    @ParamValidator
    @PostResource(name = "库存批量移除导入Excel", path = "/uploadBatchRemoval", requiredPermission = false)
    @ApiOperation(value = "库存批量移除导入Excel")
    @BusinessLog(title = "库存移除&销毁申请列表-库存批量移除导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadBatchRemoval(@ModelAttribute RemovalDestroyBaseApplyParam param, @RequestParam(value = "file", required = true) MultipartFile file) {
        return service.uploadBatchRemoval(param,file);
    }

    @PostResource(name = "库存移除导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "库存移除导出")
    @BusinessLog(title = "库存移除&销毁申请列表-库存移除导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportRemoval(@RequestBody RemovalDestroyApplyParam param, HttpServletResponse response) throws IOException {
        List<RemovalDestroyApplyResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("库存移除申请导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), RemovalDestroyApplyResult.class).sheet("库存移除申请导出").doWrite(pageBySpec);
        return ResponseData.success();
    }

    @ParamValidator
    @PostResource(name = "库存销毁导入Excel", path = "/uploadDestroy", requiredPermission = false)
    @ApiOperation(value = "库存销毁导入Excel")
    @BusinessLog(title = "库存移除&销毁申请列表-库存销毁导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadDestroy(@ModelAttribute RemovalDestroyBaseApplyParam param,@RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importDestroyApply(param,file);
    }

    @PostResource(name = "库存销毁导出", path = "/exportDestroy", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "库存销毁导出")
    @BusinessLog(title = "库存移除&销毁申请列表-库存销毁导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportDestroy(@RequestBody RemovalDestroyApplyParam param, HttpServletResponse response) throws IOException {
        List<RemovalDestroyApplyResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("库存销毁申请导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), RemovalDestroyApplyResult.class).sheet("库存销毁申请导出").doWrite(pageBySpec);
        return ResponseData.success();
    }

    @GetResource(name = "刷listing", path = "/refreshListing", requiredPermission = false)
    @ApiOperation(value = "刷listing")
    @BusinessLog(title = "库存移除&销毁申请列表-刷listing",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshListing() throws IOException {
        try {
            service.updateDetailList();
            service.updateFileDetailList();
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseData.success();
    }

    @GetResource(name = "下载库存移除申请模板", path = "/downloadRemoval", requiredPermission = false, requiredLogin = false)
    @ApiOperation("下载库存移除申请模板")
    public void downloadRemoval(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/库存移除申请模板.txt");
    }

    @GetResource(name = "库存批量移除申请模板", path = "/downloadBatchRemoval", requiredPermission = false, requiredLogin = false)
    @ApiOperation("库存批量移除申请模板")
    public void downloadBatchRemoval(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/库存批量移除导入模板.xlsx");
    }

    @GetResource(name = "下载库存销毁申请模板", path = "/downloadDestroy", requiredPermission = false, requiredLogin = false)
    @ApiOperation("下载库存销毁申请模板")
    public void downloadDestroy(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/库存销毁申请模板.txt");
    }

    @GetResource(name = "站点下拉", path = "/siteSelect", requiredPermission = false)
    @ApiOperation(value = "站点下拉", response = RemovalDestroyApply.class)
    public ResponseData siteSelect() {
        return ResponseData.success(baseSelectConsumer.getSiteSelect());
    }

    @GetResource(name = "事业部下拉", path = "/departmentSelect", requiredPermission = false)
    @ApiOperation(value = "事业部下拉", response = RemovalDestroyApply.class)
    public ResponseData departmentSelect() {
        return ResponseData.success(service.getDepartmentSelect());
    }

    @GetResource(name = "Team下拉", path = "/teamSelect", requiredPermission = false)
    @ApiOperation(value = "Team下拉", response = RemovalDestroyApply.class)
    public ResponseData teamSelect(@RequestParam(value = "dept", required = false, defaultValue = "") String dept) {
        return ResponseData.success(baseSelectConsumer.getTeamByDeptSelect(dept));
    }

    @GetResource(name = "运营大类下拉", path = "/productTypeSelect", requiredPermission = false)
    @ApiOperation(value = "运营大类下拉", response = RemovalDestroyApply.class)
    public ResponseData productTypeSelect() {
        RpMaterialParam param = new RpMaterialParam();
        return ResponseData.success(rpMaterialConsumer.getProductTypeSelect(param));
    }
}
