package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.NoAllocationAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NoAllocationAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NoAllocationAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.text.ParseException;
import java.util.List;

/**
* <p>
* 无需分摊调整表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface NoAllocationAdjustMapper extends BaseMapper<NoAllocationAdjust> {

    Page<NoAllocationAdjustResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") NoAllocationAdjustParam param);

    NoAllocationAdjustResult getQuantity(@Param("paramCondition")NoAllocationAdjustParam param);

    List<SettlementDetailUsdResult> getExitSkuAdjust(@Param("paramCondition") SettlementDetailUsdParam paramCondition);

    void updateToReport(@Param("paramCondition") NoAllocationAdjustParam param);

    void updateAmountOrInsert(@Param("paramCondition") SettlementDetailUsd usd);

    void updateCostBatch() throws ParseException;

    void updateToReportBatch(@Param("paramCondition") NoAllocationAdjustParam param);
}
