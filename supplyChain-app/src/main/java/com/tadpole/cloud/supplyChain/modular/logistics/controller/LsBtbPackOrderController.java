package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.BtbPackApplyShipmentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackOrderDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackOrderParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbPackOrderService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgImportCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * <p>
 *  BTB订单发货前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
@RestController
@Api(tags = "BTB订单发货")
@ApiResource(name = "BTB订单发货", path = "/lsBtbPackOrder")
public class LsBtbPackOrderController {

    @Autowired
    private ILsBtbPackOrderService service;
    @Autowired
    private ITgImportCompanyService importCompanyService;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "BTB订单发货", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = LsBtbPackOrderResult.class)
    @BusinessLog(title = "BTB订单发货-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody LsBtbPackOrderParam param) {
        return service.queryPage(param);
    }

    /**
     * 明细查询列表
     * @param param
     * @return
     */
    @PostResource(name = "明细查询列表", path = "/queryDetail")
    @ApiOperation(value = "明细查询列表", response = LsBtbPackOrderDetailResult.class)
    @BusinessLog(title = "BTB订单发货-明细查询列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryDetail(@RequestBody LsBtbPackOrderDetailParam param) {
        return service.queryDetail(param);
    }

    /**
     * 申请发货
     * @param param
     * @return
     */
    @PostResource(name = "申请发货", path = "/applyShipment")
    @ApiOperation(value = "申请发货")
    @BusinessLog(title = "BTB订单发货-申请发货",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData applyShipment(@RequestBody BtbPackApplyShipmentParam param) {
        return service.applyShipment(param);
    }

    /**
     * 物流单号是否存在校验
     * @param param
     * @return
     */
    @PostResource(name = "物流单号是否存在校验", path = "/hasLogisticsNo")
    @ApiOperation(value = "物流单号是否存在校验")
    @BusinessLog(title = "BTB订单发货-物流单号是否存在校验",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData hasLogisticsNo(@RequestBody BtbPackApplyShipmentParam param) {
        return service.hasLogisticsNo(param);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "导入模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("导入模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/B2B装箱信息导入模板.xlsx");
    }

    /**
     * 编辑导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "编辑导入", path = "/import", requiredPermission = false)
    @ApiOperation(value = "编辑导入")
    @BusinessLog(title = "BTB订单发货-编辑导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam("id") BigDecimal id, @RequestParam(value = "file", required = true) MultipartFile file) {
        return service.importExcel(id, file);
    }

    /**
     * 编辑保存
     * @param param
     * @return
     */
    @PostResource(name = "编辑保存", path = "/edit")
    @ApiOperation(value = "编辑保存")
    @BusinessLog(title = "BTB订单发货-编辑保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody LsBtbPackOrderResult param) {
        return service.edit(param);
    }

    /**
     * 通关数据导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "通关数据导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "通关数据导出")
    @BusinessLog(title = "BTB订单发货-通关数据导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody LsBtbPackOrderParam param, HttpServletResponse response) throws IOException {
        ExcelWriter excelWriter = null;
        try {
            LsBtbPackOrderResult result = service.export(param);
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("通关数据导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheet0 = EasyExcel.writerSheet(0, "通关信息").head(LsBtbPackBoxDetailResult.class).build();
            excelWriter.write(result.getBoxDetailResultList(), writeSheet0);

            WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "重量信息").head(LsBtbPackBoxResult.class).build();
            excelWriter.write(result.getBoxResultList(), writeSheet1);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(excelWriter != null){
                excelWriter.finish();
            }
        }
    }

    /**
     * 定时同步马帮BTB订单信息
     * @return
     */
    @GetResource(name = "定时同步马帮BTB订单信息", path = "/syncBtbOrderInfo", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "定时同步马帮BTB订单信息")
    @BusinessLog(title = "BTB订单发货-定时同步马帮BTB订单信息",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData syncBtbOrderInfo() {
        return service.syncBtbOrderInfo();
    }

    /**
     * 物流账号下拉
     * @return
     */
    @GetResource(name = "物流账号下拉", path = "/logisticsAccountSelect")
    @ApiOperation(value = "物流账号下拉", response = LsLogisticAccountResult.class)
    public ResponseData logisticsAccountSelect() {
        return ResponseData.success(service.logisticsAccountSelect());
    }

    /**
     * 国家分区下拉
     * @return
     */
    @PostResource(name = "国家分区下拉", path = "/lCountryPartitionSelect")
    @ApiOperation(value = "国家分区下拉", response = LsLogisticAccountResult.class)
    public ResponseData lCountryPartitionSelect(@RequestBody LsLogisticAccountResult param) {
        if(StringUtils.isBlank(param.getLpCode())){
            return ResponseData.error("物流商编码不能为空");
        }
        return ResponseData.success(service.lCountryPartitionSelect(param));
    }

    /**
     * 收货分区下拉
     * @return
     */
    @PostResource(name = "收货分区下拉", path = "/lCountryPartitionAreaSelect")
    @ApiOperation(value = "收货分区下拉", response = LsLogisticAccountResult.class)
    public ResponseData lCountryPartitionAreaSelect(@RequestBody LsLogisticAccountResult param) {
        if(StringUtils.isBlank(param.getLpCode())){
            return ResponseData.error("物流商编码不能为空");
        }
        if(StringUtils.isBlank(param.getLspNum())){
            return ResponseData.error("国家分区号不能为空");
        }
        return ResponseData.success(service.lCountryPartitionAreaSelect(param));
    }

    /**
     * 货运方式1下拉
     * @return
     */
    @GetResource(name = "货运方式1下拉", path = "/freightCompanySelect")
    @ApiOperation(value = "货运方式1下拉", response = BaseSelectResult.class)
    public ResponseData freightCompanySelect() {
        return service.freightCompanySelect();
    }

    /**
     * 运输方式下拉
     * @return
     */
    @GetResource(name = "运输方式下拉", path = "/transportTypeSelect")
    @ApiOperation(value = "运输方式下拉", response = BaseSelectResult.class)
    public ResponseData transportTypeSelect() {
        return service.transportTypeSelect();
    }

    /**
     * 物流渠道下拉
     * @return
     */
    @GetResource(name = "物流渠道下拉", path = "/logisticsChannelSelect")
    @ApiOperation(value = "物流渠道下拉", response = BaseSelectResult.class)
    public ResponseData logisticsChannelSelect() {
        return service.logisticsChannelSelect();
    }

    /**
     * 货物特性下拉
     * @return
     */
    @GetResource(name = "货物特性下拉", path = "/goodsPropertySelect")
    @ApiOperation(value = "货物特性下拉", response = BaseSelectResult.class)
    public ResponseData goodsPropertySelect() {
        return service.goodsPropertySelect();
    }

    /**
     * 报关公司下拉
     * @return
     */
    @GetResource(name = "报关公司下拉", path = "/customsCompanySelect")
    @ApiOperation(value = "报关公司下拉")
    public ResponseData customsCompanySelect() {
        return ResponseData.success(importCompanyService.companySelect("出口公司"));
    }

    /**
     * 物流实际结算平台下拉
     * @return
     */
    @GetResource(name = "物流实际结算平台下拉", path = "/btbPlatformSelect")
    @ApiOperation(value = "物流实际结算平台下拉")
    public ResponseData btbPlatformSelect() {
        return ResponseData.success(service.btbPlatformSelect());
    }

    /**
     * 物流实际结算账号下拉
     * @return
     */
    @GetResource(name = "物流实际结算账号下拉", path = "/btbShopNameSelect")
    @ApiOperation(value = "物流实际结算账号下拉")
    public ResponseData btbShopNameSelect() {
        return ResponseData.success(service.btbShopNameSelect());
    }

    /**
     * 物流实际结算站点下拉
     * @return
     */
    @GetResource(name = "物流实际结算站点下拉", path = "/btbSiteSelect")
    @ApiOperation(value = "物流实际结算站点下拉")
    public ResponseData btbSiteSelect() {
        return ResponseData.success(service.btbSiteSelect());
    }

}
