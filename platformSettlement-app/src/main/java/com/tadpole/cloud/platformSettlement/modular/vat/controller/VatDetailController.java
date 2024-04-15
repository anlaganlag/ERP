package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.modular.vat.enums.GenerateStatusEnum;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatDetailParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatDetailResult;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatShopResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseEuCountriesService;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatDetailService;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatShopCheckService;
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
 * 税金测算VAT明细 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@RestController
@ApiResource(name = "税金测算VAT明细", path = "/vatDetail")
@Api(tags = "税金测算VAT明细")
public class VatDetailController {

    @Autowired
    private IVatDetailService service;
    @Autowired
    private IVatShopCheckService vatShopCheckService;
    @Autowired
    private IBaseEuCountriesService baseEuCountriesService;

    /**
     * 税金测算VAT明细查询列表接口
     * @param param
     * @return
     */
    @PostResource(name = "税金测算VAT明细查询列表", path = "/queryListPage", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "税金测算VAT明细查询列表",response = VatDetailResult.class)
    @BusinessLog(title = "税金测算VAT明细-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody VatDetailParam param) { return service.queryListPage(param); }

    /**
     * 税金测算VAT查询明细合计
     * @param param
     * @return
     */
    @PostResource(name = "查询明细合计", path = "/listSum", requiredPermission = false)
    @ApiOperation(value = "查询明细合计", response = VatDetailResult.class)
    @BusinessLog(title = "税金测算VAT明细-合计查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSum(@RequestBody VatDetailParam param) { return ResponseData.success(service.getQuantity(param)); }

    /**
     * 税金测算VAT明细导出
     *
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "税金测算VAT明细导出", path = "/exportVatDetail", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "税金测算VAT明细导出")
    @BusinessLog(title = "税金测算VAT明细-税金测算VAT明细导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportVatDetail(@RequestBody VatDetailParam param, HttpServletResponse response) throws IOException {
        List<VatDetailResult> list = service.exportVatDetail(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出税金测算VAT明细.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), VatDetailResult.class).sheet("导出税金测算VAT明细").doWrite(list);
        return ResponseData.success();
    }

    /**
     * 税金测算VAT店铺维度导出
     *
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "税金测算VAT店铺维度导出", path = "/exportVatShop", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "税金测算VAT店铺维度导出")
    @BusinessLog(title = "税金测算VAT明细-税金测算VAT店铺维度导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportVatShop(@RequestBody VatDetailParam param, HttpServletResponse response) throws IOException {
        List<VatShopResult> list = service.exportVatShop(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出税金测算VAT店铺维度.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), VatShopResult.class).sheet("导出税金测算VAT店铺维度").doWrite(list);
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
     * 店铺维度下拉
     * @return
     * @throws Exception
     */
    @GetResource(name = "店铺维度下拉", path = "/statusSelect",requiredPermission = false)
    @ApiOperation(value = "店铺维度下拉")
    public ResponseData statusSelect() throws Exception { return ResponseData.success(GenerateStatusEnum.getEnumList()); }

    /**
     * 生成核对表
     * @return
     */
    @PostResource(name = "生成核对表", path = "/generateVatCheck", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "生成核对表")
    public ResponseData generateVatCheck(@RequestBody VatDetailParam param) { return service.generateVatCheck(param); }
}
