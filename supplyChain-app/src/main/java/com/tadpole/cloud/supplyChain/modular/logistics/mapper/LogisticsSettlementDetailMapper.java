package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlementDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsSettlementDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementDetailResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 物流实际结算明细
 * @date: 2022/11/14
 */
public interface LogisticsSettlementDetailMapper extends BaseMapper<LogisticsSettlementDetail> {

    /**
     * 查询物流实际结算明细
     * @param param
     * @return
     */
    Page<LogisticsSettlementDetailResult> queryDetailListPage(@Param("page") Page page, @Param("param") LogisticsSettlementDetailParam param);

    /**
     * 根据ID批量更新明细为空
     * @param detailIdList
     * @param dataMonths
     */
    void batchUpdateByIds(@Param("detailIdList") List<BigDecimal> detailIdList, @Param("dataMonths") String dataMonths);

    /**
     * 根据ID更新明细
     * @param detail
     */
    void updateDetailById(@Param("detail") LogisticsSettlementDetail detail);
}
