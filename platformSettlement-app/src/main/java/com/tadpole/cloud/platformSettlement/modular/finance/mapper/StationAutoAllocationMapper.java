package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.StationAutoAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationAutoAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationAutoAllocationResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 站内自动分摊表 Mapper 接口
* </p>
*
* @author gal
* @since 2021-12-24
*/
public interface StationAutoAllocationMapper extends BaseMapper<StationAutoAllocation> {

    Page<StationAutoAllocationResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") StationAutoAllocationParam param);

    List<SettlementDetailUsdResult> getExitSkuMoney(@Param("paramCondition") SettlementDetailUsdParam paramCondition);

    StationAutoAllocationResult getQuantity(@Param("paramCondition")StationAutoAllocationParam param);

    void updateToReport(@Param("paramCondition") StationAutoAllocationParam param);
}
