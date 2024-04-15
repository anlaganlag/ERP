package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.saihu.entity.SaihuShop;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceSaveParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsClearanceDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsClearanceResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgLogisticsPackingResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsApplyService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsClearanceService;
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
 * 清关单 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@RestController
@Api(tags = "清关单")
@ApiResource(name = "清关单", path = "/tgCustomsClearance")
public class TgCustomsClearanceController {

    @Autowired
    private ITgCustomsClearanceService service;
    @Autowired
    private ITgCustomsApplyService applyService;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "清关单", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgCustomsClearanceResult.class)
    @BusinessLog(title = "清关单-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgCustomsClearanceParam param) {
        return service.queryPage(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "清关单-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgCustomsClearanceParam param) {
        return service.delete(param);
    }

    /**
     * 查询EBMS出货清单信息
     * @param param
     * @return
     */
    @PostResource(name = "查询EBMS出货清单信息", path = "/selectEbmsLogisticsPacking")
    @ApiOperation(value = "查询EBMS出货清单信息")
    @BusinessLog(title = "清关单-查询EBMS出货清单信息",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData selectEbmsLogisticsPacking(@RequestBody TgLogisticsPackingResult param) {
        return service.selectEbmsLogisticsPacking(param);
    }

    /**
     * 出货清单号下拉
     * @return
     */
    @GetResource(name = "出货清单号下拉", path = "/packCodeSelect")
    @ApiOperation(value = "出货清单号下拉")
    public ResponseData packCodeSelect() {
        return applyService.packCodeSelect();
    }

    /**
     * 关联新增保存
     * @param param
     * @return
     */
    @PostResource(name = "关联新增保存", path = "/selectSave")
    @ApiOperation(value = "关联新增保存")
    @BusinessLog(title = "清关单-关联新增保存",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData selectSave(@RequestBody TgCustomsClearanceSaveParam param) {
        return service.selectSave(param);
    }

    /**
     * 关联编辑保存
     * @param param
     * @return
     */
    @PostResource(name = "关联编辑保存", path = "/selectEdit")
    @ApiOperation(value = "关联编辑保存")
    @BusinessLog(title = "清关单-关联编辑保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData selectEdit(@RequestBody TgCustomsClearanceSaveParam param) {
        return service.selectEdit(param);
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入", path = "/import", requiredPermission = false)
    @ApiOperation(value = "导入", response = TgLogisticsPackingResult.class)
    @BusinessLog(title = "清关单-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "file", required = true) MultipartFile file,
                                    @RequestParam("arrivalCountryCode") String arrivalCountryCode,
                                    @RequestParam("arrivalCountryName") String arrivalCountryName) {
        return service.importExcel(file, arrivalCountryCode, arrivalCountryName);
    }

    /**
     * 导入新增保存
     * @param param
     * @return
     */
    @PostResource(name = "导入新增保存", path = "/importSave")
    @ApiOperation(value = "导入新增保存")
    @BusinessLog(title = "清关单-导入新增保存",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData importSave(@RequestBody TgCustomsClearanceSaveParam param) {
        return service.importSave(param);
    }

    /**
     * 导入编辑保存
     * @param param
     * @return
     */
    @PostResource(name = "导入编辑保存", path = "/importEdit")
    @ApiOperation(value = "导入编辑保存")
    @BusinessLog(title = "清关单-导入编辑保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData importEdit(@RequestBody TgCustomsClearanceSaveParam param) {
        return service.importEdit(param);
    }

    /**
     * 生成清关发票（通用）
     * @param param
     * @return
     */
    @PostResource(name = "生成清关发票（通用）", path = "/generateCustomsClearance")
    @ApiOperation(value = "生成清关发票（通用）")
    @BusinessLog(title = "清关单-生成清关发票（通用）",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void generateCustomsClearance(@RequestBody TgCustomsClearanceParam param, HttpServletResponse response) throws IOException {
        service.generateCustomsClearance(param, response);
    }

    /**
     * 生成清关发票（快递）
     * @param param
     * @return
     */
    @PostResource(name = "生成清关发票（快递）", path = "/generateCustomsClearanceKD")
    @ApiOperation(value = "生成清关发票（快递）")
    @BusinessLog(title = "清关单-生成清关发票（快递）",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void generateCustomsClearanceKD(@RequestBody TgCustomsClearanceParam param, HttpServletResponse response) throws IOException {
        service.generateCustomsClearanceKD(param, response);
    }

    /**
     * 销售价编辑
     * @param param
     * @return
     */
    @PostResource(name = "销售价编辑", path = "/salePriceEdit")
    @ApiOperation(value = "销售价编辑")
    @BusinessLog(title = "清关单-销售价编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData salePriceEdit(@RequestBody TgCustomsClearanceDetailParam param) {
        return service.salePriceEdit(param);
    }

    /**
     * 清关合并预览
     * @param param
     * @return
     */
    @PostResource(name = "清关合并预览", path = "/clearMergePreview")
    @ApiOperation(value = "清关合并预览", response = TgCustomsClearanceDetailResult.class)
    @BusinessLog(title = "清关发票-清关合并预览",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData clearMergePreview(@RequestBody TgCustomsClearanceDetailParam param) {
        return service.clearMergePreview(param);
    }

    /**
     * 清关合并拆分
     * @param param
     * @return
     */
    @PostResource(name = "清关合并拆分", path = "/clearMergeSplit")
    @ApiOperation(value = "清关合并拆分")
    @BusinessLog(title = "清关发票-清关合并拆分",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData clearMergeSplit(@RequestBody TgCustomsClearanceDetailParam param) {
        return service.clearMergeSplit(param);
    }

    /**
     * 清关合并
     * @param param
     * @return
     */
    @PostResource(name = "清关合并", path = "/clearMerge")
    @ApiOperation(value = "清关合并")
    @BusinessLog(title = "清关发票-清关合并",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData clearMerge(@RequestBody List<TgCustomsClearanceDetailResult> param) {
        return service.clearMerge(param);
    }

    /**
     * 查看已合并清关产品
     * @param param
     * @return
     */
    @PostResource(name = "查看已合并清关产品", path = "/selectClearMerge")
    @ApiOperation(value = "查看已合并清关产品", response = TgCustomsClearanceDetailResult.class)
    @BusinessLog(title = "清关发票-查看已合并清关产品",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData selectClearMerge(@RequestBody TgCustomsClearanceDetailParam param) {
        return service.selectClearMerge(param);
    }

    /**
     * 特殊清关产品合并列表
     * @param param
     * @return
     */
    @PostResource(name = "特殊清关产品合并列表", path = "/specialClearMergeList")
    @ApiOperation(value = "特殊清关产品合并列表", response = TgCustomsClearanceDetailResult.class)
    @BusinessLog(title = "清关发票-特殊清关产品合并列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData specialClearMergeList(@RequestBody List<TgCustomsClearanceDetailResult> param) {
        return service.specialClearMergeList(param);
    }

    /**
     * 定时获取赛狐在线产品
     * @return
     */
    @PostResource(name = "定时获取赛狐在线产品", path = "/getSaihuProduct", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "定时获取赛狐在线产品")
    @BusinessLog(title = "清关发票-定时获取赛狐在线产品",opType = LogAnnotionOpTypeEnum.OTHER)
    public ResponseData getSaihuProduct(@RequestBody SaihuShop param) {
        return service.getSaihuProduct(param);
    }
}
