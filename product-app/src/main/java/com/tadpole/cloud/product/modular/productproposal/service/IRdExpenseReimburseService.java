package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdExpenseReimburse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdExpenseReimburseParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdExpenseReimburseResult;

import java.util.List;

/**
 * <p>
 * 提案-研发费报销 服务类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
public interface IRdExpenseReimburseService extends IService<RdExpenseReimburse> {
    PageResult<RdExpenseReimburseResult> listPage(RdExpenseReimburseParam param);

    List<RdExpenseReimburseResult> listExpenseReimburse(RdExpenseReimburseParam param);

    ResponseData statisticsExpenseReimburse(RdExpenseReimburseParam param);

    RdExpenseReimburseResult detail(RdExpenseReimburseParam param);

    RdExpenseReimburseResult useLastRecipientAccount(RdExpenseReimburseParam param);

    ResponseData refreshExpenseReimburseDet(RdExpenseReimburseParam param);

    ResponseData autoAnalysis(RdExpenseReimburseParam param);

    ResponseData addOrEdit(RdExpenseReimburseParam param);

    ResponseData reviewExpenseReimburse(RdExpenseReimburseParam param);

    ResponseData printExpenseReimburse(RdExpenseReimburseParam param);

    ResponseData payExpenseReimburse(RdExpenseReimburseParam param);
}
