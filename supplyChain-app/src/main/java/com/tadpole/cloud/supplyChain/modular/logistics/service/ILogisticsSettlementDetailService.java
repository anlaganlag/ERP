package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlementDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsSettlementDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementDetailResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 物流实际结算明细
 * @date: 2022/11/14
 */
public interface ILogisticsSettlementDetailService extends IService<LogisticsSettlementDetail> {

    /**
     * 查询物流实际结算明细
     */
    PageResult<LogisticsSettlementDetailResult> queryDetailListPage(LogisticsSettlementDetailParam param);

    /**
     * 根据ID批量更新明细为空
     * @param detailIdList
     * @param dataMonths
     */
    void batchUpdateByIds(List<BigDecimal> detailIdList, String dataMonths);

    /**
     * 根据ID更新明细
     * @param detail
     */
    void updateDetailById(LogisticsSettlementDetail detail);
}
