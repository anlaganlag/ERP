package com.tadpole.cloud.product.modular.productproposal.Controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.model.params.RdExpenseReimburseParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdMoldOpenPfaParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.productproposal.service.IRdExpenseReimburseService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 提案-研发费报销 前端控制器
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
@RestController
@Api(tags = "提案-研发费报销")
@ApiResource(name = "提案-研发费报销", path = "/rdExpenseReimburse")
public class RdExpenseReimburseController {

    @Autowired
    private IRdExpenseReimburseService rdExpenseReimburseService;

    @PostResource(name = "研发费报销-归档列表查询", path = "/listPage")
    @ApiOperation(value = "研发费报销-归档列表查询")
    @BusinessLog(title = "研发费报销-归档列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdExpenseReimburseParam param) {

        return ResponseData.success(this.rdExpenseReimburseService.listPage(param));
    }

    @PostResource(name = "研发费报销-列表查询", path = "/listExpenseReimburse")
    @ApiOperation(value = "研发费报销-列表查询")
    @BusinessLog(title = "研发费报销-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listExpenseReimburse(@RequestBody RdExpenseReimburseParam param) {

        return ResponseData.success(this.rdExpenseReimburseService.listExpenseReimburse(param));
    }

    @PostResource(name = "研发费报销-报销列表统计", path = "/statisticsExpenseReimburse")
    @ApiOperation(value = "研发费报销-报销列表统计")
    @BusinessLog(title = "研发费报销-报销列表统计", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData statisticsExpenseReimburse(@RequestBody RdExpenseReimburseParam param) {
        return this.rdExpenseReimburseService.statisticsExpenseReimburse(param);
    }

    @PostResource(name = "研发费报销-使用上次申请收款对象", path = "/useLastRecipientAccount")
    @ApiOperation(value = "研发费报销-使用上次申请收款对象")
    @BusinessLog(title = "研发费报销-使用上次申请收款对象",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData useLastRecipientAccount(@RequestBody RdExpenseReimburseParam param) {

        return ResponseData.success(this.rdExpenseReimburseService.useLastRecipientAccount(param));
    }

    @PostResource(name = "研发费报销-报销明细", path = "/detail")
    @ApiOperation(value = "研发费报销-报销明细")
    @BusinessLog(title = "研发费报销-报销明细",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestBody RdExpenseReimburseParam param) {

        return ResponseData.success(this.rdExpenseReimburseService.detail(param));
    }

    @PostResource(name = "研发费报销-刷新报销明细数据", path = "/refreshExpenseReimburseDet")
    @ApiOperation(value = "研发费报销-刷新报销明细数据")
    @BusinessLog(title = "研发费报销-刷新报销明细数据",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData refreshExpenseReimburseDet(@RequestBody RdExpenseReimburseParam param) {

        return this.rdExpenseReimburseService.refreshExpenseReimburseDet(param);
    }

    @PostResource(name = "研发费报销-自动分析", path = "/autoAnalysis")
    @ApiOperation(value = "研发费报销-自动分析")
    @BusinessLog(title = "研发费报销-自动分析",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData autoAnalysis(@RequestBody RdExpenseReimburseParam param) {

        return this.rdExpenseReimburseService.autoAnalysis(param);
    }

    @PostResource(name = "研发费报销-报销申请", path = "/addOrEdit")
    @ApiOperation(value = "研发费报销-报销申请")
    @BusinessLog(title = "研发费报销-报销申请",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addOrEdit(@RequestBody RdExpenseReimburseParam param) {

        return this.rdExpenseReimburseService.addOrEdit(param);
    }

    @PostResource(name = "研发费报销-报销审核", path = "/reviewExpenseReimburse")
    @ApiOperation(value = "研发费报销-报销审核")
    @BusinessLog(title = "研发费报销-报销审核",opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData reviewExpenseReimburse(@RequestBody RdExpenseReimburseParam param) {

        return this.rdExpenseReimburseService.reviewExpenseReimburse(param);
    }

    @PostResource(name = "研发费报销-报销打印", path = "/printExpenseReimburse")
    @ApiOperation(value = "研发费报销-报销打印")
    @BusinessLog(title = "研发费报销-报销打印",opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData printExpenseReimburse(@RequestBody RdExpenseReimburseParam param) {

        return this.rdExpenseReimburseService.printExpenseReimburse(param);
    }

    @PostResource(name = "研发费报销-报销付款", path = "/payExpenseReimburse")
    @ApiOperation(value = "研发费报销-报销付款")
    @BusinessLog(title = "研发费报销-报销付款",opType = LogAnnotionOpTypeEnum.CHANGE_STATUS)
    public ResponseData payExpenseReimburse(@RequestBody RdExpenseReimburseParam param) {

        return this.rdExpenseReimburseService.payExpenseReimburse(param);
    }

}
