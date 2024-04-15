package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.OrderParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.PurOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.SkuStockQtyParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangShopListResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangOrdersService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangShopListService;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    * 马帮订单列表 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-09
*/

@RestController
@ApiResource(name="Mabang Orders 马帮已发货订单列表",path = "/mabangOrders")
@Api(tags =  "Mabang Orders 马帮已发货订单列表")
public class MabangOrdersController {

    @Autowired
    private IMabangOrdersService service;


    @Resource
    IMabangRequstService mabangRequstService;

    @Resource
    IMabangShopListService    shopService;


    @PostResource(name = "已发货订单列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "已发货订单列表", response = MabangShopListResult.class)
    public ResponseData list(@RequestBody MabangOrdersParam param) {
        PageResult<MabangOrdersResult> list = service.list(param);
        return ResponseData.success(list);
    }

    @PostResource(name = "马帮已发货订单导出", path = "/exportExcel", requiredLogin = false)
    @ApiOperation("马帮已发货订单导出")
    public void export(HttpServletResponse response,@RequestBody  MabangOrdersParam param) throws IOException {
        try {
            List<MabangOrdersResult> results = service.exportList(param);
            if (CollectionUtil.isEmpty(results)) {
                exportExcel(response, "导出数据为空");
            }else{
                exportExcel(response, "马帮已发货订单");
            }
            EasyExcel.write(response.getOutputStream(), MabangOrdersResult.class).sheet("马帮已发货订单").doWrite(results);
        } catch (Exception ex) {
            response.sendError(500, "马帮已发货订单导出错误");
        }
    }


    @PostResource(name = "/立即同步已发货订单列表",path="/refreshOrderList")
    @ApiOperation(value = "立即同步已发货订单列表", response = ResponseData.class)
    public ResponseData getOrderList(@RequestBody(required = false) OrderParm orderParm ){
        return  service.getOrderList(orderParm);

    }

    @PostResource(name = "/立即同步采购订单列表",path="/refreshPurList")
    @ApiOperation(value = "立即同步采购订单列表", response = ResponseData.class)
    public ResponseData getPurOrderList(@RequestBody PurOrderParam purOrderParam ){
        return  service.getPurOrderList(purOrderParam);
    }


    @PostResource(name = "/获取SKU库存数量",path="/getSkuStockQty")
    @ApiOperation(value = "获取SKU库存数量", response = ResponseData.class)
    public ResponseData getMatStockQty(@RequestBody SkuStockQtyParam skuStockQtyParam ){
        return  service.getMatStockQty(skuStockQtyParam);
    }


    @PostResource(name = "/刷新全部SKU库存数量",path="/refreshAllSkuStockQty")
    @ApiOperation(value = "刷新全部SKU库存数量", response = ResponseData.class)
    public ResponseData refreshAllMatStockQty() throws InterruptedException {
        return  service.refreshAllMatStockQty();
    }

    @PostResource(name = "/立即同步已完成订单列表",path="/refreshFinishedOrderList")
    @ApiOperation(value = "立即同步已完成订单列表", response = ResponseData.class)
    public ResponseData getFinishedOrderList(@RequestBody OrderParm orderParm ){
        return  service.getFinishedOrderList(orderParm);

    }


    @PostResource(name = "/立即同步历史订单列表",path="/refreshHisOrderList")
    @ApiOperation(value = "立即同步历史订单列表", response = ResponseData.class)
    public ResponseData getHisOrderList(@RequestBody OrderParm orderParm ){
        return  service.getHisOrderList(orderParm);

    }


    @PostResource(name = "/立即同步历史已完成订单列表",path="/refreshHisFinishedOrderList")
    @ApiOperation(value = "立即同步历史已完成订单列表", response = ResponseData.class)
    public ResponseData getHisFinishedOrderList(@RequestBody OrderParm orderParm ){
        return  service.getHisFinishedOrderList(orderParm);

    }


    @GetResource(name = "查看详情", path = "/display")
    @ApiOperation("查看详情")
    public ResponseData display(@RequestParam String orderId) {
        return ResponseData.success(this.service.display(orderId));
    }

    @GetResource(name = "店铺下拉", path = "/shopSelect")
    @ApiOperation(value = "店铺下拉", response = MabangShopListResult.class)
    public ResponseData getShopSelect() {
        return ResponseData.success(shopService.getShopSelect());
    }






    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }







}
