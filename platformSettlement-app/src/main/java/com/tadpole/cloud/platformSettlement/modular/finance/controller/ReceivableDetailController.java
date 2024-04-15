package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.ReceivableDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ReceivableDetailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ReceivableDetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IReceivableDetailService;
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
* 应收明细 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "AZ应收明细", path = "/receivableDetail")
@Api(tags = "AZ应收明细")
public class ReceivableDetailController {

    @Autowired
    private IReceivableDetailService service;

    @PostResource(name = "AZ应收明细", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "AZ应收明细", response = ReceivableDetail.class)
    @BusinessLog(title = "AZ应收明细-AZ应收明细列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody ReceivableDetail param) {
        List<ReceivableDetailResult> receivableList = service.queryListPage(param);
        if(receivableList.size()==1){
            receivableList.get(0).setBalance(receivableList.get(0).getEndtermReceivableAmount());
        }
        return ResponseData.success(receivableList);
    }

    @PostResource(name = "应收明细审核详情", path = "/verifyList", requiredPermission = false)
    @ApiOperation(value = "应收明细审核详情", response = ReceivableDetail.class)
    @BusinessLog(title = "AZ应收明细-应收明细审核详情",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData verifyList(@RequestBody ReceivableDetailParam param) {
        List<ReceivableDetail> receivableList = service.verifyList(param);
        return ResponseData.success(receivableList);
    }

    @PostResource(name = "应收明细审核", path = "/verify", requiredPermission = false)
    @ApiOperation(value = "应收明细审核", response = ReceivableDetail.class)
    @BusinessLog(title = "AZ应收明细-应收明细审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData verify(@RequestBody ReceivableDetail param) {
       return service.verify(param);
    }

    @PostResource(name = "应收明细刷取金额", path = "/refresh", requiredPermission = false)
    @ApiOperation(value = "应收明细刷取金额", response = ReceivableDetail.class)
    @BusinessLog(title = "AZ应收明细-应收明细刷取金额",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refresh(@RequestBody ReceivableDetail param) throws Exception {
        service.refresh(param);
        return ResponseData.success();
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "AZ应收明细-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody ReceivableDetailParam param, HttpServletResponse response) throws IOException {
        List<ReceivableDetailResult> list = service.list(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("应收明细列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ReceivableDetailResult.class).sheet("应收明细列表").doWrite(list);
        return ResponseData.success();
    }

}
