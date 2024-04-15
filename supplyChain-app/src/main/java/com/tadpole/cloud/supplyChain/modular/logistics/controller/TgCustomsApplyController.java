package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBoxInfo;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsApplyDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsApplySaveParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgCustomsApplyResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.TgLogisticsPackingResult;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgCustomsApplyEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.enums.TgTradingTypeEnum;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgBoxInfoService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomBoxInfoService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCustomsApplyService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgImportCompanyService;
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
 * 报关单 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@RestController
@Api(tags = "报关单")
@ApiResource(name = "报关单", path = "/tgCustomsApply")
public class TgCustomsApplyController {

    @Autowired
    private ITgCustomsApplyService service;
    @Autowired
    private ITgImportCompanyService importCompanyService;
    @Autowired
    private ITgBoxInfoService tgBoxInfoService;
    @Autowired
    private ITgCustomBoxInfoService tgCustomBoxInfoService;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "报关单", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = TgCustomsApplyResult.class)
    @BusinessLog(title = "报关单-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody TgCustomsApplyParam param) {
        return service.queryPage(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "报关单-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody TgCustomsApplyParam param) {
        return service.delete(param);
    }

    /**
     * 查询EBMS出货清单信息
     * @param param
     * @return
     */
    @PostResource(name = "查询EBMS出货清单信息", path = "/selectEbmsLogisticsPacking")
    @ApiOperation(value = "查询EBMS出货清单信息")
    @BusinessLog(title = "报关单-查询EBMS出货清单信息",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData selectEbmsLogisticsPacking(@RequestBody TgLogisticsPackingResult param) {
        //获取MC报关外箱信息
        List<TgBoxInfo> tgBoxInfoList = tgBoxInfoService.listTgBoxInfo();
        //获取MC自定义报关外箱信息
        List<BaseSelectResult> boxTypeSelect = tgCustomBoxInfoService.boxTypeSelect();
        if(CollectionUtil.isNotEmpty(boxTypeSelect)){
            TgBoxInfo tgBoxInfo = new TgBoxInfo();
            tgBoxInfo.setBoxType(boxTypeSelect.get(0).getCode());
            tgBoxInfoList.add(tgBoxInfo);
        }
        return service.selectEbmsLogisticsPacking(param, tgBoxInfoList);
    }

    /**
     * 出货清单号下拉
     * @return
     */
    @GetResource(name = "出货清单号下拉", path = "/packCodeSelect")
    @ApiOperation(value = "出货清单号下拉")
    public ResponseData packCodeSelect() {
        return service.packCodeSelect();
    }

    /**
     * 关联新增保存
     * @param param
     * @return
     */
    @PostResource(name = "关联新增保存", path = "/selectSave")
    @ApiOperation(value = "关联新增保存")
    @BusinessLog(title = "报关单-关联新增保存",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData selectSave(@RequestBody TgCustomsApplySaveParam param) {
        return service.selectSave(param);
    }

    /**
     * 关联编辑保存
     * @param param
     * @return
     */
    @PostResource(name = "关联编辑保存", path = "/selectEdit")
    @ApiOperation(value = "关联编辑保存")
    @BusinessLog(title = "报关单-关联编辑保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData selectEdit(@RequestBody TgCustomsApplySaveParam param) {
        return service.selectEdit(param);
    }

    /**
     * 导入
     * @param files
     * @return
     */
    @ParamValidator
    @PostResource(name = "导入", path = "/import", requiredPermission = false)
    @ApiOperation(value = "导入", response = TgCustomsApplyDetail.class)
    @BusinessLog(title = "报关单-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData importExcel(@RequestParam(value = "files", required = true) MultipartFile[] files) {
        return service.importExcel(files);
    }

    /**
     * 导入新增保存
     * @param param
     * @return
     */
    @PostResource(name = "导入新增保存", path = "/importSave")
    @ApiOperation(value = "导入新增保存")
    @BusinessLog(title = "报关单-导入新增保存",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData importSave(@RequestBody TgCustomsApplySaveParam param) {
        return service.importSave(param);
    }

    /**
     * 导入编辑保存
     * @param param
     * @return
     */
    @PostResource(name = "导入编辑保存", path = "/importEdit")
    @ApiOperation(value = "导入编辑保存")
    @BusinessLog(title = "报关单-导入编辑保存",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData importEdit(@RequestBody TgCustomsApplySaveParam param) {
        return service.importEdit(param);
    }

    /**
     * 生成报关单
     * @param param
     * @return
     */
    @PostResource(name = "生成报关单", path = "/generateCustomsApply")
    @ApiOperation(value = "生成报关单")
    @BusinessLog(title = "报关单-生成报关单",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void generateCustomsApply(@RequestBody TgCustomsApplyParam param, HttpServletResponse response) throws IOException {
        service.generateCustomsApply(param, response);
    }

    /**
     * 成交方式下拉
     * @return
     */
    @GetResource(name = "成交方式下拉", path = "/tradingTypeSelect")
    @ApiOperation(value = "成交方式下拉")
    public ResponseData tradingTypeSelect() {
        return ResponseData.success(TgTradingTypeEnum.getTgTradingType());
    }

    /**
     * 是否正式报关下拉
     * @return
     */
    @GetResource(name = "是否正式报关下拉", path = "/customsApplySelect")
    @ApiOperation(value = "是否正式报关下拉")
    public ResponseData customsApplySelect() {
        return ResponseData.success(TgCustomsApplyEnum.getTgCustomsApply());
    }

    /**
     * 境外收货公司下拉
     * @return
     */
    @GetResource(name = "境外收货公司下拉", path = "/abroadCompanySelect")
    @ApiOperation(value = "境外收货公司下拉")
    public ResponseData abroadCompanySelect() {
        return ResponseData.success(importCompanyService.companySelect("香港公司"));
    }
}
