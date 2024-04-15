package com.tadpole.cloud.externalSystem.modular.mabang.controller;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.SyncBaseStatusEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SaleReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportSaleReturnOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3CrossTransferItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleReturnOrderService;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
    * 销售退货单 前端控制器
    * </p>
*
* @author cyt
* @since 2022-08-24
*/
@RestController
@Slf4j
@ApiResource(name = "马帮销售退货单列表", path = "/saleReturnOrder")
@Api(tags = "马帮销售退货单列表")
@RequestMapping("/saleReturnOrder")
public class SaleReturnOrderController {

    @Autowired
    private ISaleReturnOrderService service;

    /**
     * 马帮销售退货单查询列表
     * @param
     * @return
     */
    @PostResource(name = "马帮销售退货单列表", path = "/findPageBySpec", menuFlag = true,requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "马帮销售退货单列表", response = ExportSaleReturnOrderResult.class)
    public ResponseData findPageBySpec(@RequestBody SaleReturnOrderParam param) { return service.findPageBySpec(param); }

    /**
     * 马帮销售退货单导出接口
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    public ResponseData export(@RequestBody SaleReturnOrderParam param, HttpServletResponse response) throws IOException {

        List<ExportSaleReturnOrderResult> list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("销售退货单列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ExportSaleReturnOrderResult.class).sheet("销售退货单列表").doWrite(list);
        return ResponseData.success();
    }

    /**
     * 平台名称下拉
     * @return
     */
    @GetResource(name = "平台名称下拉", path = "/platformSelect")
    @ApiOperation(value = "平台名称下拉")
    public ResponseData platformSelect() { return ResponseData.success(service.platformSelect()); }

    /**
     * 年份下拉
     * @return
     */
    @GetResource(name = "年份下拉", path = "/yearSelect")
    @ApiOperation(value = "年份下拉")
    public ResponseData yearSelect() { return ResponseData.success(service.yearSelect()); }

    /**
     * 月份下拉
     * @return
     */
    @GetResource(name = "月份下拉", path = "/monthSelect")
    @ApiOperation(value = "月份下拉")
    public ResponseData monthSelect() { return ResponseData.success(service.monthSelect()); }

    /**
     * 店铺名称下拉
     * @return
     */
    @PostResource(name = "店铺名称下拉", path = "/shopSelect")
    @ApiOperation(value = "店铺名称下拉")
    public ResponseData shopSelect(@RequestBody List<String> platformNames) { return ResponseData.success(service.shopSelect(platformNames)); }

    /**
     * 站点下拉
     * @return
     */
    @GetResource(name = "站点下拉", path = "/siteSelect")
    @ApiOperation(value = "站点下拉")
    public ResponseData siteSelect() { return ResponseData.success(service.siteSelect()); }

    /**
     * 推送状态下拉
     * @return
     * @throws Exception
     */
    @GetResource(name = "推送状态下拉", path = "/syncBaseStatusSelect")
    @ApiOperation(value = "推送状态下拉")
    public ResponseData syncBaseStatusSelect() throws Exception {
        return ResponseData.success(SyncBaseStatusEnum.getEnumList());
    }

    /**
     * 获取马帮退货订单生成跨组织调拨单
     * @return
     */
    @PostResource(name = "获取马帮退货订单生成跨组织调拨单", path = "/generateSaleReturnOrderByMabangReturnOrders", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "获取马帮退货订单生成跨组织调拨单", response = K3CrossTransferItemResult.class)
    public ResponseData generateSaleReturnOrderByMabangReturnOrders(@RequestBody K3CrossTransferItemParam param) throws Exception{

        try{
        service.generateSaleReturnOrderByMabangReturnOrders(param);

        List<String> ownerIdList = service.getSalOrgName();
        for (String ownerId : ownerIdList) {
            String ownerName = service.getFinanceName(ownerId);
            String warehouseName = service.getWarehouseName(ownerId);
            if (StrUtil.isNotEmpty(ownerName)) {
                //1-1.销售组织名称->客户名称
                service.updateSalOrgName(ownerId, ownerName);
                //1-2.仓库编码名称->仓库名称
                service.updateWarehouseName(ownerId,warehouseName);
            }
        }
        }catch (Exception e){
            log.error("获取马帮退货订单生成跨组织调拨单，异常信息：{}",e);
        }
        return ResponseData.success();
    }

    /**
     * 获取马帮退货订单生成K3其他入库单
     * @return
     */
    @GetResource(name = "获取马帮退货订单生成K3其他入库单", path = "/generateOtherInOrder", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "获取马帮退货订单生成K3其他入库单", response = K3CrossTransferItemResult.class)
    public ResponseData generateOtherInOrder() throws Exception{

        try{
            ResponseData  responseData=   service.generateOtherInOrder(null);

            List<String> ownerIdList = service.getSalOrgName();
            for (String ownerId : ownerIdList) {
                String ownerName = service.getFinanceName(ownerId);
                String warehouseName = service.getWarehouseName(ownerId);
                if (StrUtil.isNotEmpty(ownerName)) {
                    //1-1.销售组织名称->客户名称
                    service.updateSalOrgName(ownerId, ownerName);
                    //1-2.仓库编码名称->仓库名称
                    service.updateWarehouseName(ownerId,warehouseName);
                }
            }
        }catch (Exception e){
            log.error("获取马帮退货订单生成跨组织调拨单，异常信息：{}",e);
        }
        return ResponseData.success();
    }

    /**
     * 同步K3其他入库单
     * @return
     */
    @GetResource(name = "同步K3其他入库单", path = "/syncOtherInOrder", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "同步K3其他入库单", response = K3CrossTransferItemResult.class)
    public ResponseData syncOtherInOrder() throws Exception{

            return service.syncOtherInOrder(null);
    }
}
