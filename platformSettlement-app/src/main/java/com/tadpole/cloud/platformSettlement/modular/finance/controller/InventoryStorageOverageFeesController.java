package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.InventoryStorageOverageFees;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.InventoryStorageOverageFeesParam;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.VerifyExceptionEnum;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IInventoryStorageOverageFeesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* <p>
* 超库容费用
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
@RestController
@Api(tags = "超库容费用")
@ApiResource(name = "超库容费用", path = "/inventoryStorageOverageFees")
public class InventoryStorageOverageFeesController {

    @Autowired
    private IInventoryStorageOverageFeesService service;

    @PostResource(name = "超库容费用列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "超库容费用列表")
    @BusinessLog(title = "超库容费用-超库容费用列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody InventoryStorageOverageFeesParam param) {
        var pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "批量删除", path = "/deleteBatch")
    @ApiOperation(value = "批量删除")
    @BusinessLog(title = "超库容费用-批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody InventoryStorageOverageFeesParam param) {
        if (param.getSysShopsNames()!=null && param.getSysShopsNames().size()>0
                && param.getSysSites()!=null && param.getSysSites().size()>0
                && StringUtils.isNotEmpty(param.getStartDur())
                && StringUtils.isNotEmpty(param.getEndDur())
        ) {
            var result=service.deleteBatch(param);
            return result==0? ResponseData.error(VerifyExceptionEnum.FIN_COM_DEL.getMessage()):ResponseData.success("删除记录数："+result);
        }
        return ResponseData.error(VerifyExceptionEnum.FIN_MSF_DB.getMessage());

    }

    @PostResource(name = "批量确认", path = "/updateBatch")
    @ApiOperation(value = "批量确认")
    @BusinessLog(title = "超库容费用-批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateBatch(@RequestBody InventoryStorageOverageFeesParam param) {
        if (param.getSysShopsNames()!=null && param.getSysShopsNames().size()>0
                && StringUtils.isNotEmpty(param.getStartDur())
                && StringUtils.isNotEmpty(param.getEndDur())
        ) {
            var result= service.updateBatch(param);
            return result==0? ResponseData.error(VerifyExceptionEnum.FIN_COM_UPD.getMessage()):ResponseData.success("确认记录数："+result);
        }
        return ResponseData.error(VerifyExceptionEnum.FIN_MSF_UB.getMessage());
    }

    @PostResource(name = "导出", path = "/export")
    @ApiOperation(value = "导出")
    @BusinessLog(title = "超库容费用-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody InventoryStorageOverageFeesParam param, HttpServletResponse response) throws IOException {
        var list=service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("超库容费用.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), InventoryStorageOverageFees.class).sheet("超库容费用").doWrite(list);
        return ResponseData.success();
    }

    //定时JOB刷新
    @PostResource(name = "Job刷仓储费", path = "/afreshStorageFee",requiredLogin = false)
    @ApiOperation(value = "Job刷仓储费")
    @BusinessLog(title = "超库容费用-Job刷仓储费",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData afreshStorageFee() {
        service.afreshStorageFee();
        return ResponseData.success();
    }
}
