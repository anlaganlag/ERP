package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangAddPurchaseOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3TransferMabangItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAddPurchaseOrder2Result;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAddPurchaseOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangAddPurchaseOrderService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 马帮新增采购订单 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2022-06-15
 *
 * @author ly
 * @modify 2022-08-24
 * @description 列表布局改版，查询结果：主从->合并
 */
@RestController
@Slf4j
@ApiResource(name = "Mabang Add Purchase Order 马帮采购单列表", path = "/mabangAddPurchaseOrder")
@Api(tags = "Mabang Add Purchase Order 马帮采购单列表")
public class MabangAddPurchaseOrderController {

    private final String ControllerName = "平台发展ERP-马帮库存同步-马帮采购单列表";

    @Autowired
    private IMabangAddPurchaseOrderService service;


    @Resource
    IMabangRequstService mabangRequstService;


    @PostResource(name = "马帮采购单列表", path = "/list", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "马帮采购单列表", response = MabangAddPurchaseOrderResult.class)
    public ResponseData list(@RequestBody MabangAddPurchaseOrderParam param) {
        PageResult<MabangAddPurchaseOrderResult> list = service.list(param);
        return ResponseData.success(list);
    }


    @GetResource(name = "查看详情", path = "/display", requiredPermission = false)
    @ApiOperation("查看详情")
    public ResponseData display(@RequestParam String billNo, @RequestParam String groupId) {
        PageResult<K3TransferMabangItemResult> list = this.service.display(billNo, groupId);
        return ResponseData.success(list);
    }

    @PostResource(name = "马帮采购单列表2.0", path = "/mergeList", requiredPermission = false)
    @ApiOperation(value = "马帮采购单列表2.0", response = MabangAddPurchaseOrder2Result.class)
    public ResponseData mergeList(@RequestBody MabangAddPurchaseOrderParam param) {
        PageResult<MabangAddPurchaseOrder2Result> list2 = service.mergeList(param);
        return ResponseData.success(list2);
    }


    @PostResource(name = ControllerName + "导出", path = "/exportExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation(ControllerName + "导出")
    public void exportExcel(HttpServletResponse response,@RequestBody MabangAddPurchaseOrderParam param) throws IOException {
        try {
            List<MabangAddPurchaseOrder2Result> results = service.exportExcel(param);
            exportExcel(response, ControllerName);
            EasyExcel.write(response.getOutputStream(), MabangAddPurchaseOrder2Result.class).sheet(ControllerName).doWrite(results);
        } catch (Exception ex) {
            log.error(ControllerName+"导出错误[{}]", JSON.toJSONString(ex));
            response.sendError(500, ControllerName + "导出错误");
        }
    }

    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }
}
