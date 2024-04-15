package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatCheckParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatCheckResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseEuCountriesService;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseExchangeRateService;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatCheckService;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatShopCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * VAT核对表 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@RestController
@ApiResource(name = "VAT核对表", path = "/vatCheck")
@Api(tags = "VAT核对表")
public class VatCheckController {

    @Autowired
    private IVatCheckService service;
    @Autowired
    private IVatShopCheckService vatShopCheckService;
    @Autowired
    private IBaseEuCountriesService baseEuCountriesService;
    @Autowired
    private IBaseExchangeRateService baseExchangeRateService;

    /**
     * VAT核对表查询列表接口
     * @return
     */
    @PostResource(name = "VAT核对表查询列表", path = "/queryListPage", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "VAT核对表查询列表",response = VatCheckResult.class)
    @BusinessLog(title = "VAT核对表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody VatCheckParam param) {return service.queryListPage(param); }

    /**
     * VAT核对表查询明细合计接口
     * @param param
     * @return
     */
    @PostResource(name = "查询明细合计", path = "/listSum", requiredPermission = false)
    @ApiOperation(value = "查询明细合计", response = VatCheckResult.class)
    @BusinessLog(title = "VAT核对表-合计查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSum(@RequestBody VatCheckParam param) { return ResponseData.success(service.getQuantity(param)); }

    /**
     * VAT核对表明细编辑
     * @param param
     * @return
     */
    @PostResource(name = "VAT核对表明细编辑",path = "/edit")
    @ApiOperation(value = "VAT核对表明细编辑")
    @BusinessLog(title = "VAT核对表-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody VatCheckParam param) {return service.edit(param);}

    /**
     * VAT核对表明细导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "VAT核对表明细导入", path = "/importExcel",requiredPermission = false)
    @ApiOperation(value = "VAT核对表明细导入", response = VatCheckResult.class)
    @BusinessLog(title = "VAT核对表-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file) { return service.importExcel(file); }

    /**
     * VAT核对表明细导出
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "VAT核对表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody VatCheckParam param, HttpServletResponse response) throws IOException {
        List<VatCheckResult> list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("VAT核对表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), VatCheckResult.class).sheet("VAT核对表").doWrite(list);
        return ResponseData.success();
    }

    /**
     * 账号下拉
     * @return
     */
    @GetResource(name = "账号下拉", path = "/shopsNameSelect")
    @ApiOperation(value = "账号下拉")
    public ResponseData shopsNameSelect() { return vatShopCheckService.euShop(); }

    /**
     * 站点下拉
     * @return
     *
     */
    @GetResource(name = "站点下拉", path = "/siteSelect")
    @ApiOperation(value = "站点下拉")
    public ResponseData siteSelect() { return baseEuCountriesService.euCountry(); }

    /**
     * 币种下拉
     * @return
     *
     */
    @GetResource(name = "币种下拉", path = "/currencySelect")
    @ApiOperation(value = "币种下拉")
    public ResponseData currencySelect() { return baseExchangeRateService.queryOriginalCurrency(); }
}
