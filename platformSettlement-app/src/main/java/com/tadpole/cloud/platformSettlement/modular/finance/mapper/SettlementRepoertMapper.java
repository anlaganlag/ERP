package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReport;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 结算报告 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface SettlementRepoertMapper extends BaseMapper<SettlementReport> {

    Page<SettlementReportResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") SettlementReportParam param);

    SettlementReportResult getQuantity(@Param("paramCondition") SettlementReportParam paramCondition);

    void updateKindleFee(@Param("paramCondition") SettlementReportParam paramCondition);

    void updateOverSeasFee(@Param("paramCondition") SettlementReportParam paramCondition);

    void updateCollecTionFee(@Param("paramCondition") SettlementReportParam paramCondition);

    void updateProfitFee(@Param("paramCondition") SettlementReportParam paramCondition);


    void updateReturnAmount();



}
