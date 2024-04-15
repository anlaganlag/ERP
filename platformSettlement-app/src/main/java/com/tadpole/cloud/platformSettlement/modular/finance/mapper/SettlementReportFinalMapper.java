package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportFinal;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportFinalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportFinalResult;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 结算报告 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface SettlementReportFinalMapper extends BaseMapper<SettlementReportFinal> {

    Page<SettlementReportFinalResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") SettlementReportFinalParam param);

    SettlementReportFinalResult getQuantity(@Param("paramCondition") SettlementReportFinalParam paramCondition);

    void updateKindleFee(@Param("paramCondition") SettlementReportFinalParam paramCondition);

    void updateOverSeasFee(@Param("paramCondition") SettlementReportFinalParam paramCondition);

    void updateCollecTionFee(@Param("paramCondition") SettlementReportFinalParam paramCondition);

    void updateProfitFee(@Param("paramCondition") SettlementReportFinalParam paramCondition);


    void updateReturnAmount();



}
