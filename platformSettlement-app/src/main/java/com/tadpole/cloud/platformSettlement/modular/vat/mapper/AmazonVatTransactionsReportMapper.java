package com.tadpole.cloud.platformSettlement.modular.vat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.AmazonVatTransactionsReport;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.AmazonVatTransactionsReportParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.AmazonVatTransactionsReportResult;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseExchangeRateResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 源报告表 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
public interface AmazonVatTransactionsReportMapper extends BaseMapper<AmazonVatTransactionsReport> {

    Page<AmazonVatTransactionsReportResult>  queryListPage(@Param("Page") Page pageContext, @Param("param") AmazonVatTransactionsReportParam param);

    AmazonVatTransactionsReportResult querySum(@Param("param") AmazonVatTransactionsReportParam param);

    void refreshSalesTotalFromDataRange(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    AmazonVatTransactionsReportResult getEditData(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void generateMainData(@Param("paramCondition")  AmazonVatTransactionsReportParam param);

    void refreshSellerFhg(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void refreshSellerMdg(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void refreshMarket(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void refreshBtbSellerFhg(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void refreshDiffrence(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void autoVerify(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void refreshExchangeRate(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    List<BaseExchangeRateResult> notExchangeRate(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void refreshRateOne(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void updateStatus(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void generateMainDataMdg(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void refreshRateOneMdg(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void refreshExchangeRateMdg(@Param("paramCondition") AmazonVatTransactionsReportParam param);

    void autoFei(@Param("paramCondition") AmazonVatTransactionsReportParam param);
}
