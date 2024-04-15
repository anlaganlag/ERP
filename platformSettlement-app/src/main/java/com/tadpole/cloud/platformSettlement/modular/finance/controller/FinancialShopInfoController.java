package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComShopParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RestController
@ApiResource(name = "财务店铺信息前端控制器", path = "/financialShopInfo")
@Api(tags = "财务店铺信息前端控制器")
public class FinancialShopInfoController {

    @Autowired
    private com.tadpole.cloud.platformSettlement.modular.finance.consumer.ShopEntityConsumer shopEntityConsumer;


    @PostResource(name = "财务店铺信息--分页查询", path = "/list", menuFlag = true, requiredLogin = false)
    @ApiOperation("财务店铺信息--分页查询")
    @BusinessLog(title = "财务店铺信息--分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComShopParam tbComShopParam) throws Exception {
        return shopEntityConsumer.paginQuery(tbComShopParam);
    }

    @GetResource(name = "财务店铺信息--通过ID查询单条数据", path = "/queryById", requiredLogin = false)
    @ApiOperation("财务店铺信息--通过ID查询单条数据")
    @BusinessLog(title = "财务店铺信息--通过ID查询单条数据", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(@RequestParam String shopName) throws Exception {
        return shopEntityConsumer.queryById(shopName);
    }

    @ApiOperation(value ="财务店铺信息--更新数据",response = TbComShop.class)
    @PostResource(name = "财务店铺信息--更新数据", path = "/edit",  requiredLogin = false)
    @BusinessLog(title = "财务店铺信息--更新数据",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody TbComShop tbComShop){
        return ResponseData.success(shopEntityConsumer.edit(tbComShop));
    }

    @PostResource(name = "财务店铺信息--按查询条件导出数据", path = "/export", requiredLogin = false)
    @ApiOperation("财务店铺信息--按查询条件导出数据")
    @BusinessLog(title = "财务店铺信息--按查询条件导出数据", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData export(@RequestBody TbComShopParam tbComShopParam, HttpServletResponse response) throws Exception {
        List<TbComShopResult> records = shopEntityConsumer.export(tbComShopParam);


        if (Objects.isNull(records) || records.size() == 0) {
            return ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("资源-店铺.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComShopResult.class).sheet("资源-店铺").doWrite(records);
        return ResponseData.success();

    }



}

