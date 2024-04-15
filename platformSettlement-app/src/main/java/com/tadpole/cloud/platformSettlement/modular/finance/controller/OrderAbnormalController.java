package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderAbnormalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderAbnormalResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IOrderAbnormalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: ty
 * @description: 订单异常情况汇总控制器
 * @date: 2022/5/10
 */
@RestController
@ApiResource(name = "订单异常情况汇总控制器", path = "/orderAbnormal")
@Api(tags = "订单异常情况汇总控制器")
public class OrderAbnormalController {

    @Autowired
    private IOrderAbnormalService orderAbnormalService;

    @PostResource(name = "订单异常情况汇总列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "订单异常情况汇总列表", response = OrderAbnormalResult.class)
    @BusinessLog(title = "订单异常情况汇总-订单异常情况汇总列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OrderAbnormalParam param) {
        return orderAbnormalService.queryPage(param);
    }

    /**
     * 订单异常情况汇总列表导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "订单异常情况汇总列表导出", path = "/export", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "订单异常情况汇总列表导出")
    @BusinessLog(title = "订单异常情况汇总-订单异常情况汇总列表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody OrderAbnormalParam param, HttpServletResponse response) throws IOException {
        List<OrderAbnormalResult> resultList = orderAbnormalService.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("订单异常情况汇总.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OrderAbnormalResult.class).sheet("订单异常情况汇总").doWrite(resultList);
    }

    @PostResource(name = "订单异常情况汇总数据生成（刷新）", path = "/refresh", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "订单异常情况汇总数据生成（刷新）")
    @BusinessLog(title = "订单异常情况汇总-订单异常情况汇总数据生成（刷新）",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData refresh(){
        return orderAbnormalService.refresh();
    }
}
