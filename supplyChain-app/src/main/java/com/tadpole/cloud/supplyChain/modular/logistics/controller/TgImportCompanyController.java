package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgImportCompanyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgImportCompanyResult;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgIncludeTaxEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgImportCompanyService;
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
 * 进口商 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@RestController
@Api(tags = "进口商")
@ApiResource(name = "进口商", path = "/tgImportCompany")
public class TgImportCompanyController {

    @Autowired
    private ITgImportCompanyService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "进口商", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgImportCompanyResult.class)
    @BusinessLog(title = "进口商-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgImportCompanyParam param) {
        return service.queryPage(param);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    @PostResource(name = "新增", path = "/add")
    @ApiOperation(value = "新增")
    @BusinessLog(title = "进口商-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody TgImportCompanyParam param) {
        return service.add(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "进口商-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgImportCompanyParam param) {
        return service.delete(param);
    }

    /**
     * 编辑
     * @param param
     * @return
     */
    @PostResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑")
    @BusinessLog(title = "进口商-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody TgImportCompanyParam param) {
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
    @BusinessLog(title = "进口商-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody TgImportCompanyParam param, HttpServletResponse response) throws IOException {
        List<TgImportCompanyResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("进口商导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TgImportCompanyResult.class).sheet("进口商导出").doWrite(resultList);
    }

    /**
     * 是否包税下拉
     * @return
     */
    @GetResource(name = "是否包税下拉", path = "/includeTaxSelect")
    @ApiOperation(value = "是否包税下拉")
    public ResponseData includeTaxSelect() {
        return ResponseData.success(TgIncludeTaxEnum.getIncludeTax());
    }

    /**
     * 进口商名称下拉
     * @return
     */
    @GetResource(name = "进口商名称下拉", path = "/inCompanyNameSelect")
    @ApiOperation(value = "进口商名称下拉（查询）")
    public ResponseData inCompanyNameSelect() {
        return ResponseData.success(service.inCompanyNameSelect());
    }

    /**
     * EBMS实体公司下拉
     * @return
     */
    @GetResource(name = "EBMS实体公司下拉", path = "/companySelect")
    @ApiOperation(value = "EBMS实体公司下拉（新增）")
    public ResponseData companySelect() {
        return ResponseData.success(service.companySelect(null));
    }

    /**
     * 进口商公司下拉
     * @return
     */
    @GetResource(name = "进口商公司下拉", path = "/importCompanySelect")
    @ApiOperation(value = "进口商公司下拉")
    public ResponseData importCompanySelect() {
        return ResponseData.success(service.importCompanySelect());
    }

    /**
     * 进口商公司运抵国下拉
     * @return
     */
    @PostResource(name = "进口商公司运抵国下拉", path = "/importCompanyCountrySelect")
    @ApiOperation(value = "进口商公司运抵国下拉")
    public ResponseData importCompanyCountrySelect(@RequestBody TgImportCompanyParam param) {
        return ResponseData.success(service.importCompanyCountrySelect(param));
    }

}
