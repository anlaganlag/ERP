package com.tadpole.cloud.platformSettlement.modular.vat.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.AmazonVatTransactionsReport;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.AmazonVatTransactionsReportParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.AmazonVatTransactionsReportResult;
import java.util.List;

/**
 * <p>
 * 源报告表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
public interface IAmazonVatTransactionsReportService extends IService<AmazonVatTransactionsReport> {

    ResponseData queryListPage(AmazonVatTransactionsReportParam param);

    ResponseData getEditData(AmazonVatTransactionsReportParam param);

    ResponseData edit(AmazonVatTransactionsReportParam param);

    ResponseData generateSalesDetail(AmazonVatTransactionsReportParam param);

    List<AmazonVatTransactionsReportResult> export(AmazonVatTransactionsReportParam param);

    ResponseData cancelBatch(AmazonVatTransactionsReportParam param);

    ResponseData querySum(AmazonVatTransactionsReportParam param);

    ResponseData verifyBatch(AmazonVatTransactionsReportParam param);

    ResponseData autoVerify(AmazonVatTransactionsReportParam param);
}
