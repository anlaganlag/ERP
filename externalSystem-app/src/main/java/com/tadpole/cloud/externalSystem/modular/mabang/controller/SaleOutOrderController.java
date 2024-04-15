package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.externalSystem.modular.mabang.consumer.ZZDistributeMcmsConsumer;
import com.tadpole.cloud.externalSystem.modular.mabang.enums.SyncBaseStatusEnum;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SaleOutOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportSaleOutOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderService;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* <p>
    * 销售出库单
    * </p>
*
* @author lsy
* @since 2022-08-23
*/
@RestController
@Slf4j
@ApiResource(name = "销售出库单", path = "/saleOutOrder")
@Api(tags = "销售出库单")
public class SaleOutOrderController {

    @Autowired
    private ISaleOutOrderService service;

    @Autowired
    private ZZDistributeMcmsConsumer zzDistributeMcmsConsumer;

    /**
     * 马帮销售出库单查询列表
     * @param
     * @return
     */
    @PostResource(name = "销售出库单", path = "/findPageBySpec", menuFlag = true,requiredPermission = false,requiredLogin = true)
    @ApiOperation(value = "销售出库单", response = ExportSaleOutOrderResult.class)
    public ResponseData findPageBySpec(@RequestBody SaleOutOrderParam param) { return service.findPageBySpec(param); }

    /**
     * 马帮销售出库单导出接口
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    public ResponseData export(@RequestBody SaleOutOrderParam param, HttpServletResponse response) throws IOException {

        List<ExportSaleOutOrderResult> list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("销售出库单列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ExportSaleOutOrderResult.class).sheet("销售出库单列表").doWrite(list);
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
     * 获取马帮发货订单生成销售出库单
     * @return
     */
    @PostResource(name = "获取马帮发货订单生成销售出库单", path = "/generateSaleOutOrderByMabangOrders", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "获取马帮发货订单生成销售出库单")
    public ResponseData generateSaleOutOrderByMabangOrders() {

        try{
            //1.获取MabangOrders数据
            service.generateSaleOutOrderByMabangOrders();

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

            //2.分配销售组织物料编码
            List<ZZDistributeMcms> zList = new ArrayList<>();
            zList=service.getFsaleOrgIdAndMat();
            if(CollectionUtil.isNotEmpty(zList)){
                zzDistributeMcmsConsumer.saveMatBatch(zList);
            }
        }catch (Exception e){
            log.error("获取马帮发货订单生成销售出库单，异常信息：{}",e);
        }

        return ResponseData.success();
    }

    /**
     * 同步到K3销售出库单
     *
     * @return
     * @throws Exception
     */
    @GetResource(name = "同步到K3销售出库单", path = "/syncSaleOutOrderToErp", requiredPermission = false)
    @ApiOperation(value = "同步到K3销售出库单")
    public ResponseData syncSaleOutOrderToErp(@RequestParam(required = false) String year, @RequestParam(required = false) String month) throws Exception {
        log.info("同步到K3销售出库单--->start");
        return service.syncSaleOutOrderToErp(year, month);
    }

}
