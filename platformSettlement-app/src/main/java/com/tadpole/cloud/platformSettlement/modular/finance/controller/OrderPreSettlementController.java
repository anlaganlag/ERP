package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderPreSettlementParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderPreSettlementResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IOrderPreSettlementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: ty
 * @description: 销售订单预结算
 * @date: 2022/5/30
 */
@RestController
@ApiResource(name = "销售订单预结算", path = "/orderPreSettlement")
@Api(tags = "销售订单预结算")
public class OrderPreSettlementController {

    @Autowired
    private IOrderPreSettlementService service;

    /**
     * AZ销售订单预结算
     * @param param
     * @return
     */
    @PostResource(name = "AZ销售订单预结算", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "AZ销售订单预结算", response = OrderPreSettlementResult.class)
    @BusinessLog(title = "销售订单预结算-AZ销售订单预结算列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OrderPreSettlementParam param) {
        return service.queryPage(param);
    }

    /**
     * AZ销售订单预结算导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "AZ销售订单预结算导出", path = "/export")
    @ApiOperation(value = "AZ销售订单预结算导出")
    @BusinessLog(title = "销售订单预结算-AZ销售订单预结算导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody OrderPreSettlementParam param, HttpServletResponse response) throws IOException {
        List<OrderPreSettlementResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("AZ销售订单预结算导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OrderPreSettlementResult.class).sheet("AZ销售订单预结算导出").doWrite(resultList);
    }

    @PostResource(name = "获取汇总数", path = "/getTotalQuantity")
    @ApiOperation(value = "获取汇总数")
    @BusinessLog(title = "销售订单预结算-获取汇总数",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getTotalQuantity(@RequestBody OrderPreSettlementParam param){
        return service.getTotalQuantity(param);
    }

    @PostResource(name = "刷新", path = "/refresh", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "刷新")
    @BusinessLog(title = "销售订单预结算-刷新",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refresh(){
        return service.refresh();
    }

    @PostResource(name = "获取领星亚马逊源报表-退货订单", path = "/getLxRefundOrders", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "获取领星亚马逊源报表-退货订单")
    @BusinessLog(title = "销售订单预结算-获取领星亚马逊源报表-退货订单",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData getLXRefundOrders(@Param("synDate") String synDate, @Param("key") String key){
        return service.getLxRefundOrders(synDate, key);
    }
}
