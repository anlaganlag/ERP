package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementVoucher;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementVoucherDetail;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StatementIncomeResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStatementVoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* <p>
* 收入记录表凭证 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "AZ收入记录凭证", path = "/statementVoucher")
@Api(tags = "AZ收入记录凭证")
public class StatementVoucherController {

    @Autowired
    private IStatementVoucherService service;

    @GetResource(name = "AZ收入记录凭证", path = "/queryVoucher", requiredPermission = false)
    @ApiOperation(value = "AZ收入记录凭证", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录凭证-AZ收入记录凭证查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryVoucher(StatementVoucher param) {
        StatementVoucher voucher = service.queryVoucher(param);
        return ResponseData.success(voucher);
    }

    @GetResource(name = "AZ收入记录凭证明细", path = "/queryVoucherDetail",requiredPermission = false)
    @ApiOperation(value = "AZ收入记录凭证明细", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录凭证-AZ收入记录凭证明细查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryVoucherDetail(StatementVoucher param) {
        List<StatementVoucherDetail> voucherDetailList= service.queryVoucherDetail(param);
        return ResponseData.success(voucherDetailList);
    }

    @GetResource(name = "凭证合计", path = "/voucherDetailTotal",requiredPermission = false)
    @ApiOperation(value = "凭证合计", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录凭证-凭证合计查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData voucherDetailTotal(StatementVoucher param) {
        List<StatementVoucherDetail> voucherDetailList= service.voucherDetailTotal(param);
        return ResponseData.success(voucherDetailList);
    }

}
