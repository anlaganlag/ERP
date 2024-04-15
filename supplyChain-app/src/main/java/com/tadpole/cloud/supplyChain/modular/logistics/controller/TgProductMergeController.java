package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwLingxingShopInfo;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgBaseProductResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgProductMergeResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgProductMergeRuleResult;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgMergeRuleEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgMergeStatusEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgBaseProductService;
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
 * 清关产品合并 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-29
 */
@RestController
@Api(tags = "清关产品合并")
@ApiResource(name = "清关产品合并", path = "/tgProductMerge")
public class TgProductMergeController {

    @Autowired
    private ITgBaseProductService service;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "清关产品合并", path = "/queryMergePage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgProductMergeResult.class)
    @BusinessLog(title = "清关产品合并-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryMergePage(@RequestBody TgBaseProductParam param) {
        return service.queryMergePage(param);
    }

    /**
     * 导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/exportMerge", requiredPermission = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "清关产品合并-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportMerge(@RequestBody TgBaseProductParam param, HttpServletResponse response) throws IOException {
        List<TgProductMergeResult> resultList = service.exportMerge(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("清关产品合并导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TgProductMergeResult.class).sheet("清关产品合并导出").doWrite(resultList);
    }

    /**
     * 编辑保存
     * @return
     */
    @PostResource(name = "编辑保存", path = "/editMerge")
    @ApiOperation(value = "编辑保存")
    @BusinessLog(title = "清关产品合并-编辑保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData editMerge(@RequestBody TgMergeProductEditParam param) {
        return service.editMerge(param);
    }

    /**
     * 已合并产品查询
     * @param param
     * @return
     */
    @PostResource(name = "已合并产品查询", path = "/queryAlrMergePage")
    @ApiOperation(value = "已合并产品查询", response = TgBaseProductResult.class)
    @BusinessLog(title = "清关产品合并-已合并产品查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryAlrMergePage(@RequestBody TgBaseProductParam param) {
        return service.queryAlrMergePage(param);
    }

    /**
     * 报关材质下拉
     * @return
     */
    @GetResource(name = "报关材质下拉", path = "/clearMaterialCnSelect", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "报关材质下拉")
    public ResponseData clearMaterialCnSelect() {
        return ResponseData.success(service.clearMaterialCnSelect());
    }

    /**
     * 报关英文材质下拉
     * @return
     */
    @GetResource(name = "报关英文材质下拉", path = "/clearMaterialEnSelect")
    @ApiOperation(value = "报关英文材质下拉")
    public ResponseData clearMaterialEnSelect() {
        return ResponseData.success(service.clearMaterialEnSelect());
    }

    /**
     * 合并状态下拉
     * @return
     */
    @GetResource(name = "合并状态下拉", path = "/mergeStatusSelect")
    @ApiOperation(value = "合并状态下拉")
    public ResponseData mergeStatusSelect() {
        return ResponseData.success(TgMergeStatusEnum.getMergeStatus());
    }

    /**
     * 合并状态下拉
     * @return
     */
    @GetResource(name = "规则合并下拉", path = "/mergeRuleSelect")
    @ApiOperation(value = "规则合并下拉")
    public ResponseData mergeRuleSelect() {
        return ResponseData.success(TgMergeRuleEnum.getMergeRule());
    }

    /**
     * 规则合并
     * @param params
     * @return
     */
    @PostResource(name = "规则合并", path = "/ruleMerge")
    @ApiOperation(value = "规则合并")
    @BusinessLog(title = "清关产品合并-规则合并",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData ruleMerge(@RequestBody List<TgMergeRuleConfirmParam> params) {
        return service.ruleMerge(params);
    }

    /**
     * 规则合并预览
     * @param param
     * @return
     */
    @PostResource(name = "规则合并预览", path = "/ruleMergePreview")
    @ApiOperation(value = "规则合并预览", response = TgProductMergeRuleResult.class)
    @BusinessLog(title = "清关产品合并-规则合并预览",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData ruleMergePreview(@RequestBody TgProductMergeRuleParam param) {
        return service.ruleMergePreview(param);
    }

    /**
     * 自定义合并
     * @param param
     * @return
     */
    @PostResource(name = "自定义合并", path = "/selectMerge")
    @ApiOperation(value = "自定义合并")
    @BusinessLog(title = "清关产品合并-自定义合并",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData selectMerge(@RequestBody List<TgProductMergeIdsParam> param) {
        return service.selectMerge(param);
    }

    /**
     * 自定义合并预览
     * @param param
     * @return
     */
    @PostResource(name = "自定义合并预览", path = "/selectMergePreview")
    @ApiOperation(value = "自定义合并预览")
    @BusinessLog(title = "清关产品合并-自定义合并预览",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData selectMergePreview(@RequestBody List<TgProductMergeIdsParam> param) {
        return service.selectMergePreview(param);
    }

    /**
     * 合并拆分
     * @param param
     * @return
     */
    @PostResource(name = "合并拆分", path = "/mergeSplit")
    @ApiOperation(value = "合并拆分")
    @BusinessLog(title = "清关产品合并-合并拆分",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData mergeSplit(@RequestBody TgProductMergeIdsParam param) {
        return service.mergeSplit(param);
    }

    /**
     * 定时获取领星亚马逊Listing
     * @return
     */
    @PostResource(name = "定时获取领星亚马逊Listing", path = "/getLxAmazonListing", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "定时获取领星亚马逊Listing")
    @BusinessLog(title = "清关产品合并-定时获取领星亚马逊Listing",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData getLxAmazonListing(@RequestBody CwLingxingShopInfo param) {
        return service.getLxAmazonListing(param);
    }

}
