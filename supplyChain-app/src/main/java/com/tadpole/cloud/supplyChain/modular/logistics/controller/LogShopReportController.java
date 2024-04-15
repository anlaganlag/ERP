package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopLogisticsReportResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.LogShopReportConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 物流店铺报告 前端控制器
 * </p>
 *
 * @author ty
 * @since 2022-12-22
 */
@RestController
@ApiResource(name = "物流店铺报告", path = "/logShopReport")
@Api(tags = "物流店铺报告")
public class LogShopReportController {

    @Autowired
    private LogShopReportConsumer logShopReportConsumer;

    /**
     * 查询物流店铺报告
     * @return
     */
    @PostResource(name = "物流店铺报告--查询分页数据", path = "/list", menuFlag = true)
    @ApiOperation(value = "物流店铺报告--查询分页数据")
    @BusinessLog(title = "物流店铺报告--查询分页数据", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData logShopReportQuery(@RequestBody TbComShopParam tbComShopParam) throws Exception {
        ResponseData responseData = logShopReportConsumer.logShopReportQuery(tbComShopParam);
        return responseData;
    }

    @PostResource(name = "物流店铺报告--导出分页数据", path = "/export", requiredLogin = false)
    @ApiOperation("物流店铺报告--导出分页数据")
    @BusinessLog(title = "物流店铺报告--导出分页数据", opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbComShopParam tbComShopParam, HttpServletResponse response) throws Exception {
        List<TbComShopLogisticsReportResult> records = logShopReportConsumer.export(tbComShopParam);

        if (Objects.isNull(records) || records.size() == 0) {
            return ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流店铺报告导出.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopLogisticsReportResult.class).sheet("物流店铺报告导出").doWrite(records);
        return ResponseData.success();

    }


}

