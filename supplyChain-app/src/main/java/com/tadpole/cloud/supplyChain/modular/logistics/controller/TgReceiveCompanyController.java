package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgReceiveCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgReceiveCompanyResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgReceiveCompanyService;
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
 * 收货公司 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@RestController
@Api(tags = "收货公司")
@ApiResource(name = "收货公司", path = "/tgReceiveCompany")
public class TgReceiveCompanyController {

    @Autowired
    private ITgReceiveCompanyService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "收货公司", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgReceiveCompanyResult.class)
    @BusinessLog(title = "收货公司-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgReceiveCompanyParam param) {
        return service.queryPage(param);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "收货公司-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TgReceiveCompanyParam param) {
        return service.add(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "收货公司-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgReceiveCompanyParam param) {
        return service.delete(param);
    }

    /**
     * 编辑
     * @param param
     * @return
     */
    @PostResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "收货公司-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody TgReceiveCompanyParam param) {
        return service.edit(param);
    }

    /**
     * 获取收货公司
     * @return
     */
    @PostResource(name = "获取收货公司", path = "/getReceiveCompany")
    @ApiOperation(value = "获取收货公司")
    @BusinessLog(title = "收货公司-获取收货公司",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getReceiveCompany() {
        return service.getReceiveCompany();
    }

    /**
     * 导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "收货公司-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody TgReceiveCompanyParam param, HttpServletResponse response) throws IOException {
        List<TgReceiveCompanyResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("收货公司导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TgReceiveCompanyResult.class).sheet("收货公司导出").doWrite(resultList);
    }

    /**
     * 收货公司名称下拉
     * @return
     */
    @GetResource(name = "收货公司名称下拉", path = "/receiveCompanyNameSelect")
    @ApiOperation(value = "收货公司名称下拉（查询）")
    public ResponseData receiveCompanyNameSelect() {
        return ResponseData.success(service.receiveCompanyNameSelect());
    }

    /**
     * 收货公司下拉
     * @return
     */
    @GetResource(name = "收货公司下拉", path = "/receiveCompanySelect")
    @ApiOperation(value = "收货公司下拉")
    public ResponseData receiveCompanySelect() {
        return ResponseData.success(service.receiveCompanySelect());
    }
}
