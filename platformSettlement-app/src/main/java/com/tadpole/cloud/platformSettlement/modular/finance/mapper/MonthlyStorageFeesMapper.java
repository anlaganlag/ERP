package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.MonthlyStorageFees;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonthlyStorageFeesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonthlyStorageFeesResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 月储存费用 Mapper 接口
 * </p>
 *
 * @author S20190161
 * @since 2022-10-12
 */
public interface MonthlyStorageFeesMapper extends BaseMapper<MonthlyStorageFees> {
    void afreshStorageFee();

    void fnskuFillMonListing(@Param("param") MonthlyStorageFeesParam param);


    List<MonthlyStorageFeesResult> emailList();
}
