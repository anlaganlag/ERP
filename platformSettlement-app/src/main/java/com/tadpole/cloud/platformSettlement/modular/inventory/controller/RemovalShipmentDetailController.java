package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveTrackDTO;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalShipmentDetailParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalShipmentDetailResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IErpWarehouseCodeService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemovalShipmentDetailService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
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
* 移除货件详情报告 前端控制器
* </p>
*
* @author gal
* @since 2022-04-15
*/
@RestController
@ApiResource(name = "Removal Shipment Detail", path = "/RemovalShipmentDetail")
@Api(tags = "Removal Shipment Detail")
public class RemovalShipmentDetailController {

    @Autowired
    private IRemovalShipmentDetailService service;
    @Autowired
    private IErpWarehouseCodeService wareService;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    @PostResource(name = "Removal Shipment Detail", path = "/list", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "Removal Shipment Detail", response = RemovalShipmentDetailResult.class)
    @BusinessLog(title = "Removal Shipment Detail-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody RemovalShipmentDetailParam param) {
        return ResponseData.success(service.findPageBySpec(param));
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "Removal Shipment Detail-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody RemovalShipmentDetailParam param, HttpServletResponse response) throws IOException {
        List<RemovalShipmentDetailResult> list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("RemovalShipmentDetail列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), RemovalShipmentDetailResult.class).sheet("RemovalShipmentDetail列表").doWrite(list);
        return ResponseData.success();
    }

    @PostResource(name = "获取数量", path = "/getQuantity", requiredPermission = false)
    @ApiOperation(value = "获取数量")
    @BusinessLog(title = "Removal Shipment Detail-获取汇总数量",opType = LogAnnotionOpTypeEnum.QUERY)
    public String getQuantity(@RequestBody RemovalShipmentDetailParam param) {
        return service.getQuantity(param);
    }

    @PostResource(name = "审核", path = "/verify", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "审核")
    @BusinessLog(title = "Removal Shipment Detail-审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData verify(@RequestBody RemovalShipmentDetailParam param) {
        String invOrg = "Amazon" + "_" + param.getSysShopsName() + "_" + param.getSysSite();
        String invOrgCode = wareService.getOrganizationCode(invOrg);
        String wareCode = wareService.getOrganizationCode(invOrg + "_仓库");
        param.setOrg(invOrg);
        param.setInventoryOrganizationCode(invOrgCode);
        param.setWarehouseName(invOrg + "_仓库");
        param.setWarehouseCode(wareCode);
        //获取及设置物料编码以便ERP分配物料
        String material = service.getMaterial(param);
        param.setMat(material);
        if (param.getInventoryOrganizationCode() == null){
            return ResponseData.error("无法获取库存组织编码");
        }

        if (param.getWarehouseCode() == null){
            return ResponseData.error("无法获取仓库组织编码");
        }
        //生成销毁移除成本月分摊
        service.generateRemovalShipmentMonShare(param);

        //通过审核
        service.verify(param);

        //分配物料即插入物料及组织
        service.assignMaterial(param);
        return ResponseData.success();
    }

    @PostResource(name = "批量审核", path = "/verifyBatchBySpec", requiredPermission = false)
    @ApiOperation(value = "批量审核")
    @BusinessLog(title = "Removal Shipment Detail-批量审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData verifyBatchBySpec(@RequestBody RemovalShipmentDetailParam param) {
        param.setVerifyBy(LoginContext.me().getLoginUser().getName());
        param.setUpdateBy(LoginContext.me().getLoginUser().getName());

        //生成销毁移除成本月分摊
        service.generateRemovalShipmentMonShare(param);

        //批量更新状态
        service.verifyUpdateBatch(param);
        return ResponseData.success();
    }

    @PostResource(name = "作废", path = "/reject", requiredPermission = false)
    @ApiOperation(value = "作废")
    @BusinessLog(title = "Removal Shipment Detail-作废",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData reject(@RequestBody RemovalShipmentDetailParam param) {
        service.reject(param);
        return ResponseData.success();
    }

    @PostResource(name = "批量作废", path = "/rejectBatch", requiredPermission = false)
    @ApiOperation(value = "批量作废")
    @BusinessLog(title = "Removal Shipment Detail-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData rejectBatch(@RequestBody List<RemovalShipmentDetailParam> params) {
        service.rejectBatch(params);
        return ResponseData.success();
    }

    @PostResource(name = "生成销毁移除详情列表", path = "/toDisposeRemoveList", requiredPermission = false)
    @ApiOperation(value = "生成销毁移除详情列表")
    @BusinessLog(title = "生成销毁移除详情列表",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData toDisposeRemoveList(@RequestBody RemovalShipmentDetailParam param) {
        ResponseData rd =  service.toDisposeRemoveList(param);
        if (rd.getSuccess()){
            return service.assignBatchMaterial((List<ZZDistributeMcms>) rd.getData());
        }
        return rd;
    }

    @PostResource(name = "新添加组织", path = "/addOrg", requiredPermission = false)
    @ApiOperation(value = "新添加组织")
    @BusinessLog(title = "Removal Shipment Detail-新添加组织",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData addOrg() {
        return ResponseData.success(service.addNewOrg());
    }

    @PostResource(name = "生成销毁移除跟踪", path = "/refreshTrack", requiredPermission = false)
    @ApiOperation(value = "生成销毁移除跟踪",response = DisposeRemoveTrackDTO.class)
    @BusinessLog(title = "生成销毁移除跟踪",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateTrack() {
        return service.generateTrack();
    }

    @PostResource(name = "销毁移除跟踪列表", path = "/trackList", menuFlag = true)
    @ApiOperation(value = "销毁移除跟踪列表", response = DisposeRemoveTrackDTO.class)
    @BusinessLog(title = "销毁移除跟踪列表-查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData trackList(@RequestBody RemovalShipmentDetailParam param) {
        return ResponseData.success(service.trackList(param));
    }

    @PostResource(name = "导出销毁移除跟踪列表", path = "/trackExport", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出销毁移除跟踪列表")
    @BusinessLog(title = "销毁移除跟踪列表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData trackExport(@RequestBody RemovalShipmentDetailParam param, HttpServletResponse response) throws IOException {
        List<DisposeRemoveTrackDTO> list = service.trackExport(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("销毁移除跟踪列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), DisposeRemoveTrackDTO.class).sheet("销毁移除跟踪列表").doWrite(list);
        return ResponseData.success();
    }

    /**
     * 销毁移除跟踪表获取汇总数量
     * @param param
     * @return
     */
    @PostResource(name = "获取汇总数量", path = "/getTrackQuantity", requiredPermission = false)
    @ApiOperation(value = "获取汇总数量")
    @BusinessLog(title = "销毁移除跟踪表-获取汇总数量",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getTrackQuantity(@RequestBody RemovalShipmentDetailParam param) {
        return service.getTrackQuantity(param);
    }

    @PostResource(name = "销毁移除跟踪列表刷listing", path = "/refreshListing", requiredPermission = false)
    @ApiOperation(value = "销毁移除跟踪列表刷listing")
    @BusinessLog(title = "销毁移除跟踪列表-刷listing",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshListing() {
        return service.refreshListing();
    }

    @GetResource(name = "销毁移除跟踪列表-事业部和Team级联下拉", path = "/selectDeptAndTeam")
    @ApiOperation(value = "销毁移除跟踪列表-事业部和Team级联下拉")
    public ResponseData selectDeptAndTeam() {
        return ResponseData.success(baseSelectConsumer.selectDeptAndTeam());
    }

    @GetResource(name = "销毁移除跟踪列表-订单类型下拉", path = "/orderTypeSelect", requiredPermission = false)
    @ApiOperation(value = "销毁移除跟踪列表-订单类型下拉")
    public ResponseData orderTypeSelect() {
        return service.orderTypeSelect();
    }

    @GetResource(name = "销毁移除跟踪列表-订单状态下拉", path = "/orderStatusSelect", requiredPermission = false)
    @ApiOperation(value = "销毁移除跟踪列表-订单状态下拉")
    public ResponseData orderStatusSelect() {
        return service.orderStatusSelect();
    }

    @GetResource(name = "刷新站点", path = "/refreshSite", requiredPermission = false)
    @ApiOperation(value = "刷新站点",response = DisposeRemoveTrackDTO.class)
    @BusinessLog(title = "Removal Shipment Detail-刷新站点",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshSite() {
        return service.refreshSite();
    }

    /**
     * 每天定时获取Removal Shipment Detail数据
     * @return
     */
    @PostResource(name = "每天定时获取Removal Shipment Detail数据", path = "/generateRemovalShipmentDetail", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "每天定时获取Removal Shipment Detail数据")
    public ResponseData generateRemovalShipmentDetail() {
        return service.generateRemovalShipmentDetail();
    }
}
