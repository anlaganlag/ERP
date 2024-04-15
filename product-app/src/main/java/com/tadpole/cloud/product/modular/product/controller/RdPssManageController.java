package com.tadpole.cloud.product.modular.product.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.product.modular.product.model.params.RdManageUpRecordParam;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.params.RdPssManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPlManageResult;
import com.tadpole.cloud.product.modular.product.model.result.RdPssManageResult;
import com.tadpole.cloud.product.modular.product.service.IRdManageUpRecordService;
import com.tadpole.cloud.product.modular.product.service.IRdPlManageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.product.service.IRdPssManageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品同款管理 前端控制器
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
@RestController
@Api(tags = "SPU管理")
@ApiResource(name = "SPU管理", path = "/rdPssManage")
public class RdPssManageController {

    @Autowired
    private IRdPssManageService service;

    @Autowired
    private IRdPlManageService plManageService;

    @Autowired
    private IRdManageUpRecordService manageUpRecordService;

    @PostResource(name = "SPU管理-列表", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "SPU管理-列表")
    @BusinessLog(title = "SPU管理-列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdPlManageParam param) {

        List<RdPlManageResult> removeList= new ArrayList<>();

        List<RdPlManageResult> plManageResults= plManageService.list(param);
        for (RdPlManageResult plManage:plManageResults ) {
            param.setSysPlCode(plManage.getSysPlCode());
            List<RdPssManageResult> list = service.listPage(param);
            plManage.setRdPssManageList(list);
            if(list.size()==0){
                removeList.add(plManage);
            }
        }
        for (RdPlManageResult plManage:removeList ) {
            plManageResults.remove(plManage);
        }

        return ResponseData.success(plManageResults);
    }

    @PostResource(name = "SPU管理-编辑", path = "/edit")
    @ApiOperation(value = "SPU管理-编辑")
    @BusinessLog(title = "SPU管理-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody RdPssManageParam param) throws IllegalAccessException {
        return service.edit(param);
    }


    @PostResource(name = "SPU管理-关闭", path = "/disable")
    @ApiOperation(value = "SPU管理-关闭")
    @BusinessLog(title = "SPU管理-关闭",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData disable(@RequestBody RdPlManageParam param) {
        service.disable(param);
        return ResponseData.success();
    }

    @PostResource(name = "SPU管理-开启", path = "/enable")
    @ApiOperation(value = "SPU管理-开启")
    @BusinessLog(title = "SPU管理-开启",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData enable(@RequestBody RdPlManageParam param) {
        service.enable(param);
        return ResponseData.success();
    }

    @PostResource(name = "SPU管理-日志", path = "/log")
    @ApiOperation(value = "SPU管理-日志")
    @BusinessLog(title = "SPU管理-日志",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData log(@RequestBody RdManageUpRecordParam param) {
        param.setSysType("SPU_RECORD");
        return ResponseData.success(manageUpRecordService.getList(param));
    }


    @PostResource(name = "SPU管理-导出", path = "/export",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "SPU管理-导出", response = ResponseData.class)
    @BusinessLog(title = "SPU管理-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody RdPlManageParam param, HttpServletResponse response) throws IOException {
        List<RdPssManageResult> list = service.listPage(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("产品同款管理.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), RdPssManageResult.class).sheet("产品同款管理").doWrite(list);
        return ResponseData.success();
    }

    @PostResource(name = "SPU管理-过滤查询", path = "/listSpu",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "SPU管理-过滤查询")
    @BusinessLog(title = "SPU管理-过滤查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSpu(@RequestBody RdPlManageParam param) {
        List<RdPssManageResult> list = service.listPage(param);
        return ResponseData.success(list);
    }

    @PostResource(name = "SPU管理-产品名称", path = "/listProName",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "SPU管理-产品名称")
    @BusinessLog(title = "SPU管理-产品名称",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listProName() {
        List<RdPssManageResult> list = service.listProName();
        return ResponseData.success(list);
    }
    @PostResource(name = "SPU管理-款式", path = "/listStyle",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "SPU管理-款式")
    @BusinessLog(title = "SPU管理-款式",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listStyle() {
        List<RdPssManageResult> list = service.listStyle();
        return ResponseData.success(list);
    }
    @PostResource(name = "SPU管理-主材料", path = "/listMainMaterial",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "SPU管理-主材料")
    @BusinessLog(title = "SPU管理-主材料",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listMainMaterial() {
        List<RdPssManageResult> list = service.listMainMaterial();
        return ResponseData.success(list);
    }
    @PostResource(name = "SPU管理-适用品牌或对象", path = "/listBrand",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "SPU管理-适用品牌或对象")
    @BusinessLog(title = "SPU管理-适用品牌或对象",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listBrand() {
        List<RdPssManageResult> list = service.listBrand();
        return ResponseData.success(list);
    }
    @PostResource(name = "SPU管理-型号", path = "/listModel",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "SPU管理-型号")
    @BusinessLog(title = "SPU管理-型号",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listModel() {
        List<RdPssManageResult> list = service.listModel();
        return ResponseData.success(list);
    }
}
