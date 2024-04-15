package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.NoStationAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NoStationAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NoStationAllocationResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.text.ParseException;
import java.util.List;

/**
* <p>
* 无需分摊站内费用表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface NoStationAllocationMapper extends BaseMapper<NoStationAllocation> {

    Page<NoStationAllocationResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") NoStationAllocationParam param);

    NoStationAllocationResult getQuantity(@Param("paramCondition") NoStationAllocationParam param);

    List<SettlementDetailUsdResult> getExitSkuStationAllocation(@Param("paramCondition") SettlementDetailUsdParam paramCondition);

    void updateToReport(@Param("paramCondition") NoStationAllocationParam param);

    void updateAmountOrInsert(@Param("paramCondition") SettlementDetailUsd usd);

    void updateCostBatch() throws ParseException;

    void updateToReportBatch(@Param("paramCondition") NoStationAllocationParam param);
}
