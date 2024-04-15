package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseAgeParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseAgeResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseAgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  海外仓库龄报表前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-02-03
 */
@RestController
@ApiResource(name = "海外仓库龄报表", path = "/overseasWarehouseAge")
@Api(tags = "海外仓库龄报表")
public class OverseasWarehouseAgeController {

    @Autowired
    private IOverseasWarehouseAgeService service;

    /**
     * 海外仓库龄报表分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "海外仓库龄报表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "海外仓库龄报表分页查询列表", response = OverseasWarehouseAgeResult.class)
    @BusinessLog(title = "海外仓库龄报表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OverseasWarehouseAgeParam param) {
        return service.queryPage(param);
    }

    /**
     * 海外仓库龄报表导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "海外仓库龄报表导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "海外仓库龄报表导出")
    @BusinessLog(title = "海外仓库龄报表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody OverseasWarehouseAgeParam param, HttpServletResponse response) throws IOException {
        List<OverseasWarehouseAgeResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓库龄报表导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OverseasWarehouseAgeResult.class).sheet("海外仓库龄报表导出").doWrite(resultList);
    }
}
