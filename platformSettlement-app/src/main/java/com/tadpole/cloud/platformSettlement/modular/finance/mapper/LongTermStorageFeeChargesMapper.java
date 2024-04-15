package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.LongTermStorageFeeCharges;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LongTermStorageFeeChargesParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LongTermStorageFeeChargesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonthlyStorageFeesResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 长期仓储费 Mapper 接口
 * </p>
 *
 * @author S20190161
 * @since 2022-10-12
 */
public interface LongTermStorageFeeChargesMapper extends BaseMapper<LongTermStorageFeeCharges> {
    void afreshStorageFee();


    void fnskuFillLongListing(@Param("param") LongTermStorageFeeChargesParam param);

    List<LongTermStorageFeeChargesResult> emailList();
}
