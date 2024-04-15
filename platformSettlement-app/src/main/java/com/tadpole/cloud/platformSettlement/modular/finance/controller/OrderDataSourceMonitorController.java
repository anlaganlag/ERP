package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderDataSourceMonitorParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderDataSourceMonitorResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IOrderDataSourceMonitorService;
import io.lettuce.core.dynamic.annotation.Param;
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
 * @description: 销售订单源报告数据监控控制器
 * @date: 2022/5/6
 */
@RestController
@ApiResource(name = "销售订单源报告数据监控控制器", path = "/orderDataSourceMonitor")
@Api(tags = "销售订单源报告数据监控控制器")
public class OrderDataSourceMonitorController {

    @Autowired
    private IOrderDataSourceMonitorService orderDataSourceMonitorService;

    @GetResource(name = "销售订单源报告数据监控列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "销售订单源报告数据监控列表", response = OrderDataSourceMonitorResult.class)
    @BusinessLog(title = "销售订单源报告数据监控-销售订单源报告数据监控列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(OrderDataSourceMonitorParam param) {
        return orderDataSourceMonitorService.queryPage(param);
    }

    /**
     * 销售订单源报告数据监控列表导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "销售订单源报告数据监控列表导出", path = "/export", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "销售订单源报告数据监控列表导出")
    @BusinessLog(title = "销售订单源报告数据监控-销售订单源报告数据监控列表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody OrderDataSourceMonitorParam param, HttpServletResponse response) throws IOException {
        List<OrderDataSourceMonitorResult> resultList = orderDataSourceMonitorService.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("AZ销售订单源报告数据监控.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OrderDataSourceMonitorResult.class).sheet("AZ销售订单源报告数据监控").doWrite(resultList);
    }

    @PostResource(name = "Amazon订单去重", path = "/dealAmazonOrderRepeat", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "Amazon订单去重")
    public ResponseData dealAmazonOrderRepeat(@Param("days") Integer days){
        return orderDataSourceMonitorService.dealAmazonOrderRepeat(days);
    }

    @PostResource(name = "销售订单源报告数据监控数据生成（刷新）", path = "/refresh", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "销售订单源报告数据监控数据生成（刷新）")
    @BusinessLog(title = "销售订单源报告数据监控-销售订单源报告数据监控数据生成（刷新）",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData refresh(){
        return orderDataSourceMonitorService.refresh();
    }
}
