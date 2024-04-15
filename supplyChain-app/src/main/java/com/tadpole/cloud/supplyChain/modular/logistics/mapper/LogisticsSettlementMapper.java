package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlement;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsSettlementParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementExportResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementPageTotalResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: ty
 * @description: 物流实际结算
 * @date: 2022/11/14
 */
public interface LogisticsSettlementMapper extends BaseMapper<LogisticsSettlement> {

    /**
     * 物流实际结算查询列表
     * @param param
     * @return
     */
    Page<LogisticsSettlementResult> queryListPage(@Param("page") Page page, @Param("param") LogisticsSettlementParam param);

    /**
     * 物流实际结算查询列表汇总
     * @param param
     * @return
     */
    LogisticsSettlementPageTotalResult queryTotalPage(@Param("param") LogisticsSettlementParam param);


    /**
     * 物流实际结算查询列表导出
     * @param param
     * @return
     */
    List<LogisticsSettlementExportResult> exportList(@Param("param") LogisticsSettlementParam param);

    /**
     * 合约号下拉
     * @return
     */
    List<Map<String, Object>> contractNoSelect();

    /**
     * 货运方式1下拉
     * @return
     */
    List<Map<String, Object>> freightCompanySelect();

    /**
     * 运输方式下拉
     * @return
     */
    List<Map<String, Object>> transportTypeSelect();

    /**
     * 物流渠道下拉
     * @return
     */
    List<Map<String, Object>> logisticsChannelSelect();

    /**
     * 物流单类型下拉
     * @return
     */
    List<Map<String, Object>> orderTypeSelect();

    /**
     * 更新主表为未对账和置空操作类型
     * @param shipmentNumSet
     * @return
     */
    void batchUpdateStatus(@Param("shipmentNumSet") Set<String> shipmentNumSet);


    /**
     * 获取EBMS物流跟踪表的签收日期
     * @param param
     * @return
     */
    List<LogisticsSettlement> getEbmsSignDate(@Param("param") List<LogisticsSettlement> param);

    /**
     * 定时刷物流跟踪表的签收日期
     * @param param
     * @return
     */
    void refreshSignDate(@Param("param") List<LogisticsSettlement> param);

}
