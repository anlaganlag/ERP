package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.RpMaterialParam;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.RemovalShipmentCostMonthlyShare;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.RemovalShipmentCostMonthlyShareParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.RemovalShipmentCostMonthlyShareResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.RemovalShipmentTotalCost;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.RemovalShipmenShareNum;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.RemovalShipmentConfirmStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.RemovalShipmentOrderType;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IRemovalShipmentCostMonthlyShareService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.RpMaterialConsumer;
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
* 销毁移除成本月分摊表 前端控制器
* </p>
*
* @author ty
* @since 2022-05-19
*/
@RestController
@ApiResource(name = "销毁移除成本月分摊", path = "/removalShipmentCostMonthlyShare")
@Api(tags = "销毁移除成本月分摊")
public class RemovalShipmentCostMonthlyShareController {

    @Autowired
    private IRemovalShipmentCostMonthlyShareService shipmentCostMonthlyShareService;
    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    /**
     * 销毁移除成本月分摊表
     * @param param
     * @return
     */
    @PostResource(name = "销毁移除成本月分摊表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "销毁移除成本月分摊表列表", response = RemovalShipmentCostMonthlyShareResult.class)
    @BusinessLog(title = "销毁移除成本月分摊-销毁移除成本月分摊表列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody RemovalShipmentCostMonthlyShareParam param) {
        return shipmentCostMonthlyShareService.queryPage(param);
    }

    /**
     * 销毁移除成本月分摊表列表导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "销毁移除成本月分摊表列表导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "销毁移除成本月分摊表列表导出")
    @BusinessLog(title = "销毁移除成本月分摊-销毁移除成本月分摊表列表导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody RemovalShipmentCostMonthlyShareParam param, HttpServletResponse response) throws IOException {
        List<RemovalShipmentCostMonthlyShareResult> resultList = shipmentCostMonthlyShareService.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("销毁移除成本月分摊表列表导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), RemovalShipmentCostMonthlyShareResult.class).sheet("销毁移除成本月分摊表列表导出").doWrite(resultList);
    }

    /**
     * 销毁移除成本月分摊汇总统计
     * @param param
     * @return
     */
    @PostResource(name = "销毁移除成本月分摊汇总统计", path = "/totalCost")
    @ApiOperation(value = "销毁移除成本月分摊汇总统计", response = RemovalShipmentTotalCost.class)
    @BusinessLog(title = "销毁移除成本月分摊-销毁移除成本月分摊汇总统计",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData totalCost(@RequestBody RemovalShipmentCostMonthlyShareParam param) {
        return shipmentCostMonthlyShareService.totalCost(param);
    }

    /**
     * 销毁移除成本月分摊表列表导入
     * @param file
     * @return
     */
    @ParamValidator
    @PostResource(name = "销毁移除成本月分摊表列表导入", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "销毁移除成本月分摊表列表导入")
    @BusinessLog(title = "销毁移除成本月分摊-销毁移除成本月分摊表列表导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> departmentList = baseSelectConsumer.getDepartment();
        List<String> teamList = baseSelectConsumer.getTeam();
        return shipmentCostMonthlyShareService.importExcel(file, departmentList, teamList);
    }

    /**
     * 销毁移除批量确认
     * @return
     */
    @PostResource(name = "销毁移除批量确认", path = "/batchConfirm")
    @ApiOperation(value = "销毁移除批量确认")
    @BusinessLog(title = "销毁移除成本月分摊-销毁移除批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData batchConfirm(@RequestBody List<Long> param) {
        return shipmentCostMonthlyShareService.batchConfirm(param);
    }

    /**
     * 生成站外费用分摊汇总销毁成本
     * @return
     */
    @GetResource(name = "生成站外费用分摊汇总销毁成本", path = "/generateRemovalShipment")
    @ApiOperation(value = "生成站外费用分摊汇总销毁成本")
    @BusinessLog(title = "销毁移除成本月分摊-生成站外费用分摊汇总销毁成本",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateRemovalShipment(@RequestParam(value = "fiscalPeriod", required = true) String fiscalPeriod) {
        return shipmentCostMonthlyShareService.generateRemovalShipment(fiscalPeriod);
    }

    /**
     * 销毁移除批量作废
     * @return
     */
    @PostResource(name = "销毁移除批量作废", path = "/destroyRemovalShipment")
    @ApiOperation(value = "销毁移除批量作废")
    @BusinessLog(title = "销毁移除成本月分摊-销毁移除批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData destroyRemovalShipment(@RequestBody List<Long> param) {
        return shipmentCostMonthlyShareService.destroyRemovalShipment(param);
    }

    /**
     * 销毁移除编辑接口
     * @return
     */
    @PostResource(name = "销毁移除编辑接口", path = "/updateRemovalShipment")
    @ApiOperation(value = "销毁移除编辑接口")
    @BusinessLog(title = "销毁移除成本月分摊-销毁移除编辑接口",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateRemovalShipment(@RequestBody RemovalShipmentCostMonthlyShare param) {
        return shipmentCostMonthlyShareService.updateRemovalShipment(param);
    }

    /**
     * 销毁移除订单类型下拉
     * @return
     */
    @GetResource(name = "销毁移除订单类型下拉", path = "/removalShipmentOrderType")
    @ApiOperation(value = "销毁移除订单类型下拉")
    public ResponseData removalShipmentOrderType() {
        return ResponseData.success(RemovalShipmentOrderType.getEnumValueList());
    }

    /**
     * 销毁移除确认状态下拉
     * @return
     */
    @GetResource(name = "销毁移除确认状态下拉", path = "/removalShipmentConfirmStatus")
    @ApiOperation(value = "销毁移除确认状态下拉")
    public ResponseData removalShipmentConfirmStatus() {
        return ResponseData.success(RemovalShipmentConfirmStatus.getEnumList());
    }

    /**
     * 销毁移除款式二级标签下拉
     * @return
     */
    @GetResource(name = "销毁移除款式二级标签下拉", path = "/styleSecondLabel")
    @ApiOperation(value = "销毁移除款式二级标签下拉")
    public ResponseData styleSecondLabel() {
        return ResponseData.success(rpMaterialConsumer.styleSecondLabel());
    }

    /**
     * 销毁移除款式运营大类下拉
     * @return
     */
    @GetResource(name = "销毁移除款式运营大类下拉", path = "/productTypeSelect")
    @ApiOperation(value = "销毁移除款式运营大类下拉")
    public ResponseData productTypeSelect() {
        return ResponseData.success(rpMaterialConsumer.getProductTypeSelect(new RpMaterialParam()));
    }

    /**
     * 销毁移除摊销期下拉
     * @return
     */
    @GetResource(name = "销毁移除摊销期下拉", path = "/removalShipmenShareNumSelect")
    @ApiOperation(value = "销毁移除摊销期下拉")
    public ResponseData removalShipmenShareNumSelect() {
        return ResponseData.success(RemovalShipmenShareNum.getEnumList());
    }
}
