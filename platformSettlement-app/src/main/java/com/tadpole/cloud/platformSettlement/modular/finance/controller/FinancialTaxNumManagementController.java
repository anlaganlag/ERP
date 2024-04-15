package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComTaxNum;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxAuditParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.param.TbComTaxNumParam;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComTaxNumResult;
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
@ApiResource(name = "财务税号管理前端控制器", path = "/financialTaxNum")
@Api(tags = "财务税号管理前端控制器")
public class FinancialTaxNumManagementController {

    @Autowired
    private com.tadpole.cloud.platformSettlement.modular.finance.consumer.TaxNumConsumer taxNumConsumer;


    @PostResource(name = "财务税号管理--分页查询", path = "/list", menuFlag = true, requiredLogin = false)
    @ApiOperation("财务税号管理--分页查询")
    @BusinessLog(title = "财务税号管理--分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData paginQuery(@RequestBody TbComTaxNumParam tbComTaxNumParam) throws Exception {
        return taxNumConsumer.paginQuery(tbComTaxNumParam);
    }

    @GetResource(name = "财务税号管理--通过ID查询单条数据", path = "/queryById", requiredLogin = false)
    @ApiOperation("财务税号管理--通过ID查询单条数据")
    @BusinessLog(title = "财务税号管理--通过ID查询单条数据", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryById(@RequestParam String id) throws Exception {
        return taxNumConsumer.queryById(id);
    }

    @ApiOperation(value ="财务税号管理--根据店铺名更新数据",response = TbComTaxNum.class)
    @PostResource(name = "财务税号管理--根据店铺名更新数据", path = "/updateByShopName",  requiredLogin = false)
    @BusinessLog(title = "财务税号管理--根据店铺名更新数据",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateByShopName(@RequestBody TbComTaxNum tbComTaxNum) throws Exception {
        return taxNumConsumer.updateByShopName(tbComTaxNum);
    }

    @PostResource(name = "财务税号管理--按查询条件导出数据", path = "/export", requiredLogin = false)
    @ApiOperation("财务税号管理--按查询条件导出数据")
    @BusinessLog(title = "财务税号管理--按查询条件导出数据", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData export(@RequestBody TbComTaxNumParam TbComTaxNumParam, HttpServletResponse response) throws Exception {
        List<TbComTaxNumResult> records = taxNumConsumer.export(TbComTaxNumParam);


        if (Objects.isNull(records) || records.size() == 0) {
            return ResponseData.success();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("财务税号管理.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), TbComTaxNumResult.class).sheet("财务税号管理").doWrite(records);
        return ResponseData.success();

    }


    @ApiOperation(value ="财务税号管理--注销税号",response = TbComTaxNum.class)
    @PostResource(name = "财务税号管理--注销税号", path = "/cancelTaxCode",  requiredLogin = false)
    @BusinessLog(title = "财务税号管理--注销税号",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData cancelTaxCode(@RequestBody TbComTaxNum tbComTaxNum) throws Exception {
        return ResponseData.success(taxNumConsumer.cancelTaxCode(tbComTaxNum));
    }

    @ApiOperation(value ="财务税号管理--查账",response = TbComTaxNum.class)
    @PostResource(name = "财务税号管理--查账", path = "/addTaxAudit",  requiredLogin = false)
    @BusinessLog(title = "财务税号管理--查账",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData addTaxAudit(@RequestBody TbComTaxAuditParam param) throws Exception {
        return taxNumConsumer.addTaxAudit(param);
    }

    @ApiOperation(value ="财务税号管理--更改税号状态",response = TbComTaxNum.class)
    @GetResource(name = "财务税号管理--更改税号状态", path = "/updateTaxNumState",  requiredLogin = false)
    @BusinessLog(title = "财务税号管理--更改税号状态",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData updateTaxNumState(String shopName,String taxnState) {
        return  taxNumConsumer.updateTaxNumState(shopName, taxnState);
    };
}

