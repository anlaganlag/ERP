package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationManualAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationManualAllocationResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 站内手工分摊表 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-12-24
 */
public interface StationManualAllocationMapper extends BaseMapper<StationManualAllocation> {

    Page<StationManualAllocationResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") StationManualAllocationParam param);

    StationManualAllocationResult getQuantity(@Param("paramCondition") StationManualAllocationParam param);

    void updateToReport(@Param("paramCondition") StationManualAllocationParam param);

    void pullStorageFee();


    List<StationManualAllocation> refreshListing(@Param("paramCondition") StationManualAllocationParam param);

    void fillListing(@Param("param") StationManualAllocationParam param);

    void fnskuFillDestroyListing(@Param("param") StationManualAllocationParam param);

    void fillSalesBrand(@Param("param") StationManualAllocationParam param);
}
