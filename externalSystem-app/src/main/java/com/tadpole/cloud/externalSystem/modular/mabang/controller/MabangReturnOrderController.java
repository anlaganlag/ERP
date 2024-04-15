package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.OrderParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangReturnOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangRequstService;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangReturnOrderService;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
    * 马帮退货单列表
    * </p>
*
* @author lsy
* @since 2022-08-23
*/
@RestController
@ApiResource(name = "马帮已退货订单", path = "/returnOrder")
@Api(tags = "马帮已退货订单")
public class MabangReturnOrderController {

    @Autowired
    private IMabangReturnOrderService service;

    @Resource
    IMabangRequstService mabangRequstService;


    /**
     * 马帮已退货订单列表
     * @param
     * @return
     */
    @PostResource(name = "马帮已退货订单列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "马帮已退货订单列表",response = MabangReturnOrderResult.class)
    public ResponseData list(@RequestBody MabangReturnOrderParam param) {
        PageResult<MabangReturnOrderResult> list = service.listBySpec(param);
        return ResponseData.success(list);
    }



    @PostResource(name = "马帮已退货订单列表导出", path = "/export",requiredLogin = false)
    @ApiOperation(value = "马帮已退货订单列表导出")
    public ResponseData export(@RequestBody MabangReturnOrderParam param, HttpServletResponse response) throws IOException {
        List<MabangReturnOrderResult> list = service.exportList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("马帮已退货订单列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), MabangReturnOrderResult.class).sheet("马帮已退货订单列表").doWrite(list);
        return ResponseData.success();
    }


    @PostResource(name = "/立即同步退货订单列表", path = "/refreshReturnOrderList")
    @ApiOperation(value = "立即同步退货订单列表", response = ResponseData.class)
    public ResponseData getReturnOrderList(@RequestBody(required = false) OrderParm orderParm) {
        if (ObjectUtil.isNull(orderParm)) {
            orderParm = new OrderParm();
        }
        return service.getReturnOrderList(orderParm);
    }

    @PostResource(name = "/立即同步历史退货订单列表",path="/refreshHisReturnOrderList")
    @ApiOperation(value = "立即同步历史退货订单列表", response = ResponseData.class)
    public ResponseData getHisReturnOrderList(@RequestBody(required = false) OrderParm orderParm ){
        if (ObjectUtil.isNull(orderParm)) {
            orderParm = new OrderParm();
        }
        return  service.getHisReturnOrderList(orderParm);

    }

    @GetResource(name = "平台名称", path="/queryPlatformNames", requiredPermission = false)
    @ApiOperation(value = "平台名称", response = ResponseData.class)
    public ResponseData queryPlatformNames(){
        return service.queryPlatformNames();
    }


    @GetResource(name = "店铺名称", path="/queryShopName", requiredPermission = false)
    @ApiOperation(value = "店铺名称", response = ResponseData.class)
    public ResponseData queryShopName(){
        return service.queryShopName();
    }


    @GetResource(name = "站点名称", path="/querySite", requiredPermission = false)
    @ApiOperation(value = "站点名称", response = ResponseData.class)
    public ResponseData querySite(){
        return service.querySite();
    }

    @GetResource(name = "退货状态", path="/queryStatus", requiredPermission = false)
    @ApiOperation(value = "退货状态", response = ResponseData.class)
    public ResponseData queryStatus(){
        return service.queryStatus();
    }




}
