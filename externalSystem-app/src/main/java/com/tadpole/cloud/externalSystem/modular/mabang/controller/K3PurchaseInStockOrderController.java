package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3PurchaseInStockOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3PurchaseInStockOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3PurchaseInStockOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * K3采购入库单 前端控制器
 * </p>
 *
 * @author S20190161
 * @since 2023-05-17
 */

@RestController
@Slf4j
@ApiResource(name = "K3采购入库单", path = "/k3PurchaseInStockOrder")
@Api(tags = "K3采购入库单")
public class K3PurchaseInStockOrderController {

    @Autowired
    private IK3PurchaseInStockOrderService service;



    /**
     * K3采购入库单列表
     *
     * @param param
     * @return
     */
    @PostResource(name = "K3采购入库单-列表", path = "/findPageBySpec", menuFlag = true, requiredLogin = false, requiredPermission = false)
    @ApiOperation(value = "K3采购入库单-列表", response = K3PurchaseInStockOrderResult.class)
    public ResponseData findPageBySpec(@RequestBody K3PurchaseInStockOrderParam param) {
        PageResult<K3PurchaseInStockOrderResult> list = service.findPageBySpec(param);
        return ResponseData.success(list);
    }

    /**
     * 生成k3采购入库单
     *
     * @param
     * @return
     */
    @PostResource(name = "k3采购入库单-生成", path = "/createPurchaseInStock", requiredPermission = false)
    @ApiOperation(value = "k3采购入库单-生成", response = ResponseData.class)
    public ResponseData createPurchaseInStock() {

        return service.createPurchaseInStock();
    }


    /**
     * k3采购入库单-同步到K3
     *
     * @param
     * @return
     */
    @PostResource(name = "k3采购入库单-同步到K3", path = "/syncPurchaseInStock", requiredLogin = false, requiredPermission = false)
    @ApiOperation(value = "k3采购入库单-同步到K3", response = ResponseData.class)
    public ResponseData syncPurchaseInStock() {
        return service.syncPurchaseInStock();
    }


    @GetResource(name = "K3采购入库单-更新审核日期", path = "/updateVerifyDate", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "K3采购入库单-更新审核日期", response = ResponseData.class)
    public ResponseData updateVerifyDate( @RequestParam(required = false) String billNo){
        return service.updateVerifyDate( billNo);
    }

    @PostResource(name = "k3采购入库单-更新需求Team", path = "/updateTeam", requiredLogin = false, requiredPermission = false)
    @ApiOperation(value = "K3采购入库单-更新需求Team", response = ResponseData.class)
    public ResponseData updateTeam( @RequestBody K3PurchaseInStockOrderParam param){
        return service.updateTeam( param);
    }

}
