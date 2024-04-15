package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.hutool.json.JSONUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.objectLog.model.BaseAttributeModel;
import cn.stylefeng.guns.cloud.model.objectLog.model.LogAttributesParam;
import cn.stylefeng.guns.cloud.model.objectLog.model.OperationModel;
import cn.stylefeng.guns.cloud.model.web.response.ErrorResponseData;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.objectLog.client.service.impl.LogClient;
import cn.stylefeng.guns.cloud.system.api.objectLog.client.task.LogObjectTask;
import cn.stylefeng.guns.cloud.system.api.objectLog.entity.Operation;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tadpole.cloud.externalSystem.modular.k3.utils.SyncToErpUtil;
import com.tadpole.cloud.externalSystem.modular.mabang.consumer.ObjectLogClientApiConsumer;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangEmployee;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3PurchaseOrderInStockParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.*;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangStockResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderK3Service;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISyncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@ApiResource(name = "马帮测试controller", path = "/mabang")
@Api(tags = "马帮ERP")

public class MabangTestController {

    @Resource
    IMabangRequstService mabangRequstService;
    @Resource
    ISaleOutOrderK3Service saleOutOrderK3Service;

    @Autowired
    private ISyncService syncService;

    @Resource
    private SyncToErpUtil syncToErpUtil;

    @Resource
    private ObjectLogClientApiConsumer objectLogClientApiConsumer;

    @Resource
    private LogClient logClient;

    @PostMapping("/getOrderList")
    @ApiOperation(value = "订单列表", response = ResponseData.class)
    public ResponseData getOrderList(@RequestBody OrderParm orderParm ){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-order-list", orderParm);
        MabangResult mabangResult = mabangRequstService.getOrderList(mabangHeadParm);
        return ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/getOrderListNew")
    @ApiOperation(value = "订单列表新", response = ResponseData.class)
    public ResponseData getOrderListNew(@RequestBody OrderParm orderParm ){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-order-list-new", orderParm);
        MabangResult mabangResult = mabangRequstService.getOrderListNew(mabangHeadParm);
        return ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }


    @PostMapping("/getShopList")
    @ApiOperation(value = "店铺列表", response = ResponseData.class)
    public ResponseData getShopList(@RequestBody ShopParm shopParm ){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("sys-get-shop-list", shopParm);
        MabangResult mabangResult = mabangRequstService.getShopList(mabangHeadParm);
        return ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/purchaseOrderAdd")
    @ApiOperation(value = "采购订单新增", response = ResponseData.class)
    public ResponseData purchaseOrderAdd(@RequestBody PurchaseAddParm purchaseAddParm ){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-do-add-purchase", purchaseAddParm);
        MabangResult mabangResult = mabangRequstService.purchaseOrderAdd(mabangHeadParm);
        System.out.println(JSONUtil.toJsonStr(mabangResult));
        return ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/purchaseOrderModify")
    @ApiOperation(value = "采购订单修改", response = ResponseData.class)
    public ResponseData purchaseOrderModify(@RequestBody PurchaseModifParm purchaseModifParm ){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-do-change-purchase", purchaseModifParm);
        MabangResult mabangResult = mabangRequstService.purchaseOrderModify(mabangHeadParm);
        return ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/getPurchaseOrderList")
    @ApiOperation(value = "采购订单查询", response = ResponseData.class)
    public ResponseData getPurchaseOrderList(@RequestBody PurchaseGetOrderParm purchaseGetOrderParm ){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("pur-get-purchase-list", purchaseGetOrderParm);
        MabangResult mabangResult = mabangRequstService.getPurchaseOrderList(mabangHeadParm);
        return ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/warehouseList")
    @ApiOperation(value = "仓库列表", response = ResponseData.class)
    public ResponseData warehouseList(@RequestBody WarehouseParm warehouseParm ){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("sys-get-warehouse-list", warehouseParm);
        MabangResult mabangResult = mabangRequstService.warehouseList(mabangHeadParm);
        return ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/createAllocationWarehouse")
    @ApiOperation(value = "创建分仓调拨", response = ResponseData.class)
    public ResponseData createAllocationWarehouse(@RequestBody CreateAllocationWarehouseParm createAllocationWarehouseParm ){
        AllocationWarehouseSku sku = AllocationWarehouseSku.builder()
                .sku("PPS220076")
                .num("10")
                .type("1")
                .build();

        ArrayList<AllocationWarehouseSku> skuList = new ArrayList<>();
        skuList.add(sku);

        createAllocationWarehouseParm.setSku(JSONUtil.toJsonStr(skuList));
        MabangHeadParm mabangHeadParm = new MabangHeadParm("hwc-create-allocation-warehouse", createAllocationWarehouseParm);
        MabangResult mabangResult = mabangRequstService.createAllocationWarehouse(mabangHeadParm);
        return ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/returnOrderList")
    @ApiOperation(value = "退货订单", response = ResponseData.class)
    public ResponseData returnOrderList(@RequestBody ReturnOrderParm returnOrderParm ){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("order-get-return-order-list", returnOrderParm);
        MabangResult mabangResult = mabangRequstService.returnOrderList(mabangHeadParm);
        return ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/stockDoSearchSkuList")
    @ApiOperation(value = "查询商品", response = ResponseData.class)
    public ResponseData stockDoSearchSkuList(@RequestBody MabangGoodsSearchParm mabangGoodsSearchParm){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-do-search-sku-list",mabangGoodsSearchParm);

        MabangResult mabangResult = mabangRequstService.stockDoSearchSkuList(mabangHeadParm);

        List<MabangStockResult> result = (List<MabangStockResult>)(((Map) mabangResult.getData()).get("data"));

        return  ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/stockDoAddStock")
    @ApiOperation(value = "新增商品", response = ResponseData.class)
    public ResponseData stockDoAddStock(@RequestBody MabangGoodsAddParm mabangGoodsAddParm){

        MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-do-add-stock",mabangGoodsAddParm);

        MabangResult mabangResult = mabangRequstService.stockDoSearchSkuList(mabangHeadParm);
        return  ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/stockDoChangeStock")
    @ApiOperation(value = "修改商品", response = ResponseData.class)
    public ResponseData stockDoChangeStock(@RequestBody MabangGoodsModifyParm mabangGoodsModifyParm){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-do-change-stock",mabangGoodsModifyParm);

        MabangResult mabangResult = mabangRequstService.stockDoSearchSkuList(mabangHeadParm);
        return  ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }

    @PostMapping("/stockChangeGrid")
    @ApiOperation(value = "商品修改仓位", response = ResponseData.class)
    public ResponseData stockChangeGrid(@RequestBody MabangGoodsChangeGrid mabangGoodsChangeGrid){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("stock-change-grid",mabangGoodsChangeGrid);

        MabangResult mabangResult = mabangRequstService.stockChangeGrid(mabangHeadParm);
        return  ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }


    @PostMapping("/sysGetEmployeeList")
    @ApiOperation(value = "查询员工列表", response = ResponseData.class)
    public ResponseData sysGetEmployeeList(@RequestParam Integer status){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("sys-get-employee-list",null);

        MabangResult mabangResult = mabangRequstService.stockChangeGrid(mabangHeadParm);
        return  ResponseData.success(mabangResult);
    }


    @GetMapping("/k3test")
    @ApiOperation(value = "K3销售出库异常查询", response = ResponseData.class)
    public ResponseData sysGetEmployeeList(@RequestParam String billNo){

        saleOutOrderK3Service.saleOutOrderExceptionHandle(billNo);
        return ResponseData.success();
    }

    @PostMapping("/orderUpdate")
    @ApiOperation(value = "更新马帮订单信息", response = ResponseData.class)
    public ResponseData orderUpdate(@RequestBody MabangOrderChangeParm mabangOrderChangeParm){
        MabangHeadParm mabangHeadParm = new MabangHeadParm("order-do-change-order",mabangOrderChangeParm);
        MabangResult mabangResult = mabangRequstService.orderUpdate(mabangHeadParm);
        return  ResponseData.success(JSONUtil.toJsonStr(mabangResult));
    }


    @PostResource(name = "K3库存成本价", path = "/k3stockPrice", requiredPermission = false)
    @ApiOperation(value = "K3库存成本价", response = ResponseData.class)
    public ResponseData k3stockPrice(@RequestBody String jsonData){

       return saleOutOrderK3Service.k3stockPrice(jsonData);
    }

    @PostResource(name = "K3库存成本价2", path = "/k3stockPrice2", requiredPermission = false)
    @ApiOperation(value = "K3库存成本价2", response = ResponseData.class)
    public ResponseData k3stockPrice2(@RequestBody String jsonData){

        return saleOutOrderK3Service.k3stockPrice2(jsonData);
    }

    @PostResource(name = "K3采购入库单", path = "/purchaseOrderInStock", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "K3采购入库单", response = ResponseData.class)
    public ResponseData purchaseOrderInStock(@RequestBody K3PurchaseOrderInStockParam param){

        return syncService.purchaseOrderInStock(param);
    }

    @PostResource(name = "K3采购入库单2", path = "/purchaseOrderInStock2", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "K3采购入库单2", response = ResponseData.class)
    public ResponseData purchaseOrderInStock2( @RequestBody String jsonData){

        return syncService.purchaseOrderInStock2(jsonData);
    }


    @GetResource(name = "K3采购入库单提交", path = "/inStockCommit", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "K3采购入库单提交", response = ResponseData.class)
    public ResponseData inStockCommit( String number, Integer fid){
        return syncService.inStockCommit( number, fid);
//        "Id":140672,"Number":"MBCGRK-2023051600001"
    }

    @GetResource(name = "K3采购入库单查询", path = "/inStockQuery", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "K3采购入库单查询", response = ResponseData.class)
    public ResponseData inStockQuery( String number){
        return ResponseData.success(syncService.queryInStockVerifyDate( number));
    }


    @PostResource(name = "生成K3付款凭证调试", path = "/k3PamrTest", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "生成K3付款凭证调试", response = ResponseData.class)
    public ResponseData k3PamrTest( @RequestBody String jsonData){
        JSONObject voucher = syncToErpUtil.voucher(JSON.parseArray(jsonData));
        return ResponseData.success(voucher);
    }

    @GetResource(name = "测试对象属性变化记录", path = "/objectChangeLog", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "测试对象属性变化记录", response = ResponseData.class)
    public ResponseData objectChangeLog( ) throws IllegalAccessException {
        ErrorResponseData r1 = ResponseData.error(500, "5000000");
        ErrorResponseData r2 = ResponseData.error(600, "600000");

        MabangEmployee employee = new MabangEmployee();
        employee.setEmployeeId("13");
        employee.setName("张三");
        employee.setMobile("123456");
        employee.setStatus("1");


        MabangEmployee employee2 = new MabangEmployee();
        employee.setEmployeeId("13");
        employee2.setName("张三2");
        employee2.setMobile("123456789");
        employee2.setStatus("0");

        logClient.logObject("1","2","3","4","33","333333",employee,employee2);
        OperationModel operationModel = logClient.getOperationModel("1", "2", "3", "4", "33", "333333", employee, employee2);
        OperationModel operationModel2 = logClient.getOperationModel("1", "2", "3", "4", "33", "333333", r1, r2);

//        LogObjectParam objectLogParm = new LogObjectParam();
//        objectLogParm.setObjectId("1");
//        objectLogParm.setOperator("2");
//        objectLogParm.setOperationName("3");
//        objectLogParm.setOperationAlias("4");
//        objectLogParm.setExtraWords("5");
//        objectLogParm.setComment("6");
//        objectLogParm.setOldObject(r1);
//        objectLogParm.setNewObjec(r2);


//
        List<BaseAttributeModel> baseActionItemModelList = new ArrayList<>();
        BaseAttributeModel baseActionItemModel = new BaseAttributeModel();
        baseActionItemModel.setAttributeType("NORMAL");
        baseActionItemModel.setAttributeName("status");
        baseActionItemModel.setAttributeAlias("Status");
        baseActionItemModel.setOldValue("TODO");
        baseActionItemModel.setNewValue("DOING");
        baseActionItemModel.setDiffValue(null);
        baseActionItemModelList.add(baseActionItemModel);
        logClient.logAttributes("CleanRoomTask", "ID IS", "Jone", "start", "Start a Task", "Begin to clean room...", "Come on and start cleaning up.",baseActionItemModelList);

//        LogObjectTask logObjectTask = new LogObjectTask();
//
//
//        objectLogParm.setObjectId("111111222");


//
//        ResponseData responseData = objectLogClientApiConsumer.(objectLogParm);

        return ResponseData.success();
    }

    @GetResource(name = "查询对象属性变化记录", path = "/queryObjectChangeLog", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "查询对象属性变化记录", response = ResponseData.class)
    public ResponseData queryObjectChangeLog( ){

        Operation operation = new Operation();
        operation.setObjectId("ID IS");
        objectLogClientApiConsumer.query(operation);
        return ResponseData.success();
    }
}
