package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLpDepositPrepaymentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLpDepositPrepaymentResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLpDepositPrepaymentService;
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
 * 物流商押金&预付 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
@RestController
@Api(tags = "物流商押金&预付")
@ApiResource(name = "物流商押金&预付", path = "/lsLpDepositPrepayment")
public class LsLpDepositPrepaymentController {

    @Autowired
    private ILsLpDepositPrepaymentService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "物流商押金&预付", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = LsLpDepositPrepaymentResult.class)
    @BusinessLog(title = "物流商押金&预付-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody LsLpDepositPrepaymentParam param) {
        return service.queryPage(param);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "物流商押金&预付-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody LsLpDepositPrepaymentParam param) {
        return service.add(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "物流商押金&预付-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody LsLpDepositPrepaymentParam param) {
        return service.delete(param);
    }

    /**
     * 编辑
     * @param param
     * @return
     */
    @PostResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "物流商押金&预付-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody LsLpDepositPrepaymentParam param) {
        return service.edit(param);
    }

    /**
     * 导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "物流商押金&预付-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody LsLpDepositPrepaymentParam param, HttpServletResponse response) throws IOException {
        List<LsLpDepositPrepaymentResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流商押金&预付.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LsLpDepositPrepaymentResult.class).sheet("物流商押金&预付导出").doWrite(resultList);
    }

}
