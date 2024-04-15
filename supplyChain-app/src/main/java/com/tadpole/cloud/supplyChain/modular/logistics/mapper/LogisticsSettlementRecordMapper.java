package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlementRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsSettlementRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementRecordResult;
import org.apache.ibatis.annotations.Param;

/**
 * @author: ty
 * @description: 物流实际结算记录
 * @date: 2022/11/14
 */
public interface LogisticsSettlementRecordMapper extends BaseMapper<LogisticsSettlementRecord> {

    /**
     * 查询物流实际结算记录
     * @param param
     * @return
     */
    Page<LogisticsSettlementRecordResult> queryRecordListPage(@Param("page") Page page, @Param("param") LogisticsSettlementRecordParam param);

}
