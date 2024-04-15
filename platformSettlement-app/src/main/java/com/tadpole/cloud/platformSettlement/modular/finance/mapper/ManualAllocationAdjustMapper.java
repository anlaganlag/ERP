package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.ManualAllocationAdjust;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetailUsd;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ManualAllocationAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ManualAllocationAdjustResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 手动分摊调整表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface ManualAllocationAdjustMapper extends BaseMapper<ManualAllocationAdjust> {

    Page<ManualAllocationAdjustResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") ManualAllocationAdjustParam param);

    List<ManualAllocationAdjustResult> queryList(@Param("paramCondition") ManualAllocationAdjustParam param);

    void updateToReport(@Param("paramCondition") ManualAllocationAdjustParam ss);

    ManualAllocationAdjustResult getQuantity(@Param("paramCondition") ManualAllocationAdjustParam param);

    List<ManualAllocationAdjustParam> queryConfirm(@Param("paramCondition") ManualAllocationAdjustParam param);

    List<SettlementDetailUsdResult> getManualAllocationAdjust(@Param("paramCondition") SettlementDetailUsdParam param);

    void updateAmountOrInsert(@Param ("paramCondition") SettlementDetailUsd usd);
}
