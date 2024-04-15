package com.tadpole.cloud.operationManagement.modular.brand.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandTrademarkRegis;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkPageParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkRegisParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandTrademarkReportPageParam;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandTrademarkReportResult;
import com.tadpole.cloud.operationManagement.api.brand.model.result.TbBrandTrademarkResult;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandTrademarkRegisService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LongTermStorageFeeCharges;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LongTermStorageFeeChargesParam;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandTrademarkService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
* <p>
* 品牌商标表 前端控制器
* </p>
*
* @author S20190161
* @since 2023-10-19
*/
@RestController
@Api(tags = "品牌商标表")
@ApiResource(name = "品牌商标表", path = "/brand/brandTrademark")
public class TbBrandTrademarkController {

    @Autowired
    private TbBrandTrademarkService service;
    @Autowired
    private TbBrandTrademarkRegisService regisService;

    @ApiOperation(value = "分页查询", response = TbBrandTrademarkResult.class)
    @PostResource(name = "品牌商标-分页查询", path = "/getPage", menuFlag = true)
    @BusinessLog(title = "分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getPage(@RequestBody TbBrandTrademarkPageParam param) {

        return ResponseData.success(service.getPage(param));
    }

    @ApiOperation(value = "获取商标名称")
    @GetResource(path = "/getTradeName")
    @BusinessLog(title = "获取商标名称", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getTradeName() {
        return ResponseData.success(service.getTradeName());
    }

    @ApiOperation(value = "获取EBMS字典")
    @GetResource(path = "/getDictionary")
    @BusinessLog(title = "获取EBMS字典", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getDictionary(String code){
        return ResponseData.success(service.getDictionarys(code));
    }


    @ApiOperation(value = "保存商标提案")
    @PostResource(name = "保存商标提案",path = "/save")
    @BusinessLog(title = "保存商标提案", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody @Valid TbBrandTrademarkParam param) {
        service.save(param);
        return ResponseData.success();
    }


    @ApiOperation(value = "商标追踪报表查询", response = TbBrandTrademarkReportResult.class)
    @PostResource(name = "品牌商标-商标追踪报表查询", path = "/getTradeReportPage", menuFlag = true)
    @BusinessLog(title = "商标追踪报表查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getTradeReportPage(@RequestBody TbBrandTrademarkReportPageParam param) {

        return ResponseData.success(service.getTradeReportPage(param));
    }
    @PostResource(name = "导出商标追踪报表", path = "/export")
    @ApiOperation(value = "导出商标追踪报表")
    @BusinessLog(title = "商标追踪报表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody TbBrandTrademarkReportPageParam param, HttpServletResponse response) throws IOException {
        var list = service.getTradeReport(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("商标追踪报表.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbBrandTrademarkReportResult.class).sheet("商标追踪报表").doWrite(list);
        return ResponseData.success();
    }


}
