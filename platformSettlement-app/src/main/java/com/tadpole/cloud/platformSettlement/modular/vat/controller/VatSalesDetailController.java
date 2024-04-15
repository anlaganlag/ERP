package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.modular.vat.enums.GenerateStatusEnum;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatSalesDetailParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatSalesDetailResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseEuCountriesService;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatSalesDetailService;
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
 * 税金测算Sales明细 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@RestController
@ApiResource(name = "税金测算Sales明细", path = "/salesDetail")
@Api(tags = "税金测算Sales明细")
public class VatSalesDetailController {

    @Autowired
    private IVatSalesDetailService service;
    @Autowired
    private IVatShopCheckService vatShopCheckService;
    @Autowired
    private IBaseEuCountriesService baseEuCountriesService;

    /**
     * 税金测算Sales明细查询列表
     * @param param
     * @return
     */
    @PostResource(name = "税金测算Sales明细查询列表", path = "/queryListPage", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "税金测算Sales明细查询列表",response = VatSalesDetailResult.class)
    @BusinessLog(title = "税金测算Sales明细-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody VatSalesDetailParam param) { return ResponseData.success(service.queryListPage(param)); }

    /**
     * 税金测算Sales查询明细合计
     * @param param
     * @return
     */
    @PostResource(name = "查询明细合计", path = "/listSum", requiredPermission = false)
    @ApiOperation(value = "查询明细合计", response = VatSalesDetailResult.class)
    @BusinessLog(title = "税金测算Sales明细-合计查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSum(@RequestBody VatSalesDetailParam param) { return ResponseData.success(service.getQuantity(param)); }

    /**
     * 税金测算Sales明细导出
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "税金测算Sales明细-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody VatSalesDetailParam param, HttpServletResponse response) throws IOException {
        List<VatSalesDetailResult> list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("税金测算Sales明细列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), VatSalesDetailResult.class).sheet("税金测算Sales明细列表").doWrite(list);
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
     * VAT明细下拉
     * @return
     * @throws Exception
     */
    @GetResource(name = "VAT明细下拉", path = "/statusSelect",requiredPermission = false)
    @ApiOperation(value = "VAT明细下拉")
    public ResponseData statusSelect() throws Exception { return ResponseData.success(GenerateStatusEnum.getEnumList()); }

    /**
     * 生成VAT明细
     * @return
     */
    @PostResource(name = "生成VAT明细", path = "/generateVatDetail", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "生成VAT明细")
    @BusinessLog(title = "税金测算Sales明细-生成VAT明细",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateVatDetail(@RequestBody VatSalesDetailParam param) { return service.generateVatDetail(param); }
}
