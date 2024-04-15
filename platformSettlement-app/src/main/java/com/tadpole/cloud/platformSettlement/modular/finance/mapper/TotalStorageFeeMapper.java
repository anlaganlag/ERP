package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationManualAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TotalStorageFee;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalStorageFeeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationManualAllocationAllocResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationManualAllocationResult;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 仓储费合计 数据来源 定期生成 Mapper 接口
 * </p>
 *
 * @author S20190161
 * @since 2022-10-14
 */

public interface TotalStorageFeeMapper extends BaseMapper<TotalStorageFee> {
    void afreshCount(@Param("date") String date);

    List<StationManualAllocationAllocResult> selectTotalStorage(@Param("param") TotalStorageFeeParam param);

    List<StationManualAllocationAllocResult> stationSum(@Param("param") StationManualAllocationParam param);

    List<StationManualAllocationAllocResult> updateAllocFee(@Param("param") StationManualAllocationParam param);

    List<StationManualAllocationAllocResult> stationSum2(@Param("param") StationManualAllocationParam param);

    List<StationManualAllocationAllocResult> stationSum3(@Param("param") StationManualAllocationParam param);

    List<StationManualAllocationResult> selectdetailStorage(@Param("param") TotalStorageFeeParam param);

    List<StationManualAllocation> selectSkuStorage(@Param("param") TotalStorageFeeParam param);

    void updateStorageSrc(@Param("param") StationManualAllocationParam param);

    void updateStorageSrc(@Param("param") TotalStorageFeeParam param);

    void assignAllocLineStatus(@Param("param") StationManualAllocationParam param);

    void fillAllocLineVales(@Param("param") StationManualAllocationParam param);

    List<StationManualAllocation> getSkuStorageDetail(@Param("param") StationManualAllocationParam param);

    void insertOverStorage(@Param("param") StationManualAllocationParam param, @Param("directRate") BigDecimal directRate);

    Double toInsertdetailSum(@Param("param") StationManualAllocationParam param);

    Integer noTicked(@Param("param") StationManualAllocationParam param);
}
