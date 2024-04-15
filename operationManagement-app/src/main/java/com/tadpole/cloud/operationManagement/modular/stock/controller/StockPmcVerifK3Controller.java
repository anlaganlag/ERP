package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockPmcVerifK3Param;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockPmcVerifK3Result;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockPmcVerifK3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * PMC审核调用k3记录信息 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2022-09-07
 */

@Slf4j
@RestController
@Api(tags = "备货3.0-PMC审核调用k3")
@ApiResource(name = "备货3.0-PMC审核调用k3", path = "/stockPmcVerifK3")
public class StockPmcVerifK3Controller {

    @Autowired
    private IStockPmcVerifK3Service service;

    private final String controllerName = "备货3.0-PMC审核调用k3";



    /**
     * PMC审核调用k3下单申请记录
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "PMC审核调用k3下单申请记录", path = "/list", menuFlag = true, requiredPermission = false )
    @ApiOperation(value = "PMC审核调用k3下单申请记录", response = StockPmcVerifK3Result.class)
    @BusinessLog(title = controllerName + "_" +"PMC审核调用k3下单申请记录",opType = LogAnnotionOpTypeEnum.QUERY)

    public ResponseData queryPage(@RequestBody StockPmcVerifK3Param reqVO) {
        return ResponseData.success(service.queryPage(reqVO));
    }

    /**
     * PMC审核调用k3下单申请记录
     *
     * @param requestParm
     * @return
     */
    @PostResource(name = "PMC审核调用k3下单申请记录-编辑", path = "/edit", menuFlag = true, requiredPermission = false )
    @ApiOperation(value = "PMC审核调用k3下单申请记录-编辑", response = StockPmcVerifK3Result.class)
    @BusinessLog(title = controllerName + "_" +"PMC审核调用k3下单申请记录-编辑",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody StockPmcVerifK3Param  requestParm) {
        return service.edit(requestParm);
    }


    /**
     * PMC审核调用k3下单申请记录导出
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "下单申请记录-导出", path = "/export",  requiredPermission = false )
    @ApiOperation(value = "下单申请记录-导出", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"下单申请记录-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody StockPmcVerifK3Param reqVO, HttpServletResponse response) throws IOException  {
        List<StockPmcVerifK3Result> list = service.export(reqVO);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("下单申请记录.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), StockPmcVerifK3Result.class).sheet("下单申请记录列表").doWrite(list);
        return ResponseData.success();
    }


}
