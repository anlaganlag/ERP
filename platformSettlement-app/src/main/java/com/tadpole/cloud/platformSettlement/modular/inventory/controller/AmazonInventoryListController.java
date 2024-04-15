package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryList;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.AmazonInventoryListParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IAmazonInventoryEuTransferService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IAmazonInventoryK3TransferService;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IAmazonInventoryListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* <p>
* 库存列表3.0 前端控制器
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
@RestController
@Api(tags = "库存列表3.0")
@ApiResource(name = "库存列表3.0", path = "/amazonInventory")
public class AmazonInventoryListController {

    @Autowired
    private IAmazonInventoryListService service;
    @Autowired
    IAmazonInventoryK3TransferService k3TransferService;
    @Autowired
    IAmazonInventoryEuTransferService euTransferService;

    @PostResource(name = "库存列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "库存列表")
    @BusinessLog(title = "库存列表3.0-库存列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody AmazonInventoryListParam param) {
        var pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "导出", path = "/export")
    @ApiOperation(value = "导出")
    @BusinessLog(title = "库存列表3.0-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody AmazonInventoryListParam param, HttpServletResponse response) throws IOException {
        var list = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("库存列表3.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), AmazonInventoryList.class).sheet("库存列表3").doWrite(list);
        return ResponseData.success();
    }

    //定时JOB刷新上月列表数据
    @PostResource(name = "刷新数据", path = "/afreshCount", requiredLogin = false)
    @ApiOperation(value = "刷新数据")
    @BusinessLog(title = "库存列表3.0-刷新数据",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData afreshCount() {
        var k3List= k3TransferService.getErpEuTransfer();
        k3List.parallelStream().forEach(a->{
            a.setShopName(a.getWarehouseName().split("_")[1]);
        });
        k3TransferService.save(k3List);
        var euList=euTransferService.getEbmsEuTransfer();
        euTransferService.save(euList);
        service.afreshCount();
        return ResponseData.success();
    }
}
