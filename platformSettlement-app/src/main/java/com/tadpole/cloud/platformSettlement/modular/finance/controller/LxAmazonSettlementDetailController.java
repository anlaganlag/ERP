package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.SettlementFileListReq;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxAmazonSettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementAbnormalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.CwLingxingSettlementResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxAmazonSettlementDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* <p>
*  前端控制器
* </p>
*
* @author cyt
* @since 2022-05-07
*/
@RestController
@ApiResource(name = "AZ结算异常监控", path = "/lingxingSettlement")
@Api(tags = "AZ结算异常监控")
public class LxAmazonSettlementDetailController {

    @Autowired
    private ILxAmazonSettlementDetailService service;

    @PostResource(name = "AZ结算异常监控查询列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "AZ结算异常监控查询列表", response = LxAmazonSettlementDetail.class)
    @BusinessLog(title = "AZ结算异常监控-AZ结算异常监控查询列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody SettlementAbnormalParam param) {
        PageResult<CwLingxingSettlementResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "AZ结算异常监控导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "AZ结算异常监控导出")
    @BusinessLog(title = "AZ结算异常监控-AZ结算异常监控导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody SettlementAbnormalParam param, HttpServletResponse response) throws IOException {
        List<CwLingxingSettlementResult> list = service.list(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("AZ结算异常监控查询列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), CwLingxingSettlementResult.class).sheet("AZ结算异常监控查询列表").doWrite(list);
        return ResponseData.success();
    }

    @PostResource(name = "领星Settlement源文件下载", path = "/generateSettlementSourceFile", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "领星Settlement源文件下载")
    @BusinessLog(title = "AZ结算异常监控-领星Settlement源文件下载",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData generateSettlementSourceFile(@RequestBody SettlementFileListReq param) throws Exception{
        return service.generateSettlementSourceFile(param);
    }

    @PostResource(name = "手动处理领星Settlement源文件下载", path = "/handleGenerateSettlementSourceFile", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "手动处理领星Settlement源文件下载")
    @BusinessLog(title = "AZ结算异常监控-手动处理领星Settlement源文件下载",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData handleGenerateSettlementSourceFile(@Param("synDate") String synDate, @Param("key") String key) throws Exception{
        if(StringUtils.isNotEmpty(key) && "Snfvd@123".equals(key)){
            return service.handleGenerateSettlementSourceFile(synDate);
        }
        return ResponseData.error("手动处理领星Settlement源文件下载异常");
    }

    @PostResource(name = "生成Settlement文件列表接口", path = "/generateSettlementFileID", requiredPermission = false)
    @ApiOperation(value = "生成Settlement文件列表接口", response = LxAmazonSettlementDetail.class)
    @BusinessLog(title = "AZ结算异常监控-生成Settlement文件列表接口",opType = LogAnnotionOpTypeEnum.ADD)
    public Map<Long,String> generateSettlementFileID(@RequestBody SettlementFileListReq param) throws Exception{

        return service.generateSettlementFileID(param);
    }

    @PostResource(name = "AZ结算异常数据生成（刷新）", path = "/refresh", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "AZ结算异常数据生成（刷新）")
    @BusinessLog(title = "AZ结算异常监控-AZ结算异常数据生成（刷新）",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData refresh(){
        return service.refresh();
    }
}
