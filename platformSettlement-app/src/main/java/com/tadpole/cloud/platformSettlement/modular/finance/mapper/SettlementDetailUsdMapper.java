package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetail;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 结算单明细(美金) Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface SettlementDetailUsdMapper extends BaseMapper<SettlementDetailUsd> {
    Page<SettlementDetailUsdResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") SettlementDetailUsdParam param);

    List<SettlementDetail> getSettlementMoney(@Param("paramCondition") SettlementDetail param);

    void afreshExchangeRate(@Param("paramCondition") SettlementDetailUsdParam param);

    void refreshExchangeRate() throws Exception;

    SettlementDetailUsdResult autoAmountSummary(@Param("paramCondition") SettlementDetailUsd param);

    SettlementDetailUsdResult getQuantity(@Param("paramCondition") SettlementDetailUsdParam paramCondition);


    /**
     * 将结算单明细（美金）批量拆分到站内费用手工分摊表
     * @param param
     * @return
     */
    void insertOrUpdateManualAllocation(@Param("paramCondition") SettlementDetailUsdParam param);

    /**
     * 将结算单明细（美金）批量拆分到站内费用自动分摊表汇总
     * @param param
     * @return
     */
    void insertOrUpdateAutoAllocationTotal(@Param("paramCondition") SettlementDetailUsdParam param);

    /**
     * 将结算单明细（美金）批量拆分到无需分摊站内费用表
     * @param param
     * @return
     */
    void insertOrUpdateNoStationAllocation(@Param("paramCondition") SettlementDetailUsdParam param);

    /**
     * 将结算单明细（美金）批量拆分到无需分摊调整表
     * @param param
     * @return
     */
    void insertOrUpdateNoAllocationAdjust(@Param("paramCondition") SettlementDetailUsdParam param);

    /**
     * 将结算单明细（美金）批量拆分到手动分摊调整表
     * @param param
     * @return
     */
    void insertOrUpdateManualAllocationAdjust(@Param("paramCondition") SettlementDetailUsdParam param);

    /**
     * 批量更新结算单明细（美金）确认信息
     * @param param
     * @return
     */
    void updateConfirmDetailUsd(@Param("paramCondition") SettlementDetailUsdParam param);
}
