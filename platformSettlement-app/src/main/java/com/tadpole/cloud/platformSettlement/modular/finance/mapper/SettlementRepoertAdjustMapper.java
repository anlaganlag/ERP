package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportFinal;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportExportResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportFinalResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 结算报告 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface SettlementRepoertAdjustMapper extends BaseMapper<SettlementReportAdjust> {

    Page<SettlementReportAdjustResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") SettlementReportAdjustParam param);

    Page<SettlementReportExportResult> export(@Param("page") Page page, @Param("paramCondition") SettlementReportAdjustParam param);

    SettlementReportAdjustResult getQuantity(@Param("paramCondition") SettlementReportAdjustParam paramCondition);

    List<SettlementReportFinal> adjustReport(@Param("paramCondition") SettlementReportAdjustParam paramCondition);

    void fillPeopleCost(@Param("paramCondition") SettlementReportAdjustParam paramCondition);

    List<SettlementReportFinalResult> mergeFinalSettleNallocStruct(@Param("paramCondition") SettlementReportAdjustParam paramCondition);

    void updateFinalBatch(@Param("reqParamList") List<SettlementReportFinal> paramCondition);


    List<SettlementReportFinal> fill1();

    void adjustReportByMerge(@Param("paramCondition") SettlementReportAdjustParam paramCondition);

    List<Map<String, String>> adjustRepeatDimension(@Param("paramCondition") SettlementReportAdjustParam paramCondition);

    int updatePeopleCostZero(@Param("paramCondition") SettlementReportAdjustParam paramCondition);

    void fillPeopleCostNew(@Param("paramCondition") SettlementReportAdjustParam paramCondition);
}
