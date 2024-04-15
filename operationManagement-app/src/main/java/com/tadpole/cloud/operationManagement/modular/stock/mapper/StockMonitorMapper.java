package com.tadpole.cloud.operationManagement.modular.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockMonitorParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockMonitorResult;
import com.tadpole.cloud.platformSettlement.api.sales.entity.StockMonitor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 备货监控 Mapper 接口
    * </p>
*
* @author cyt
* @since 2022-07-21
*/
public interface StockMonitorMapper extends BaseMapper<StockMonitor> {


    List<StockMonitorResult> customPageList(@Param("paramCondition") StockMonitorParam paramCondition);



    void fillPurchase( @Param("quarterLeftDays") long quarterLeftDays );

    void fillTurnOverDay();

    void fillTurnOverDayDept(@Param("department") String department);



    void deleteByDept(@Param("department") String department);

    List<StockMonitor> getErpData (@Param("department") String department);

    List<StockMonitor> getCurPurQty(@Param("department") String department);



}
