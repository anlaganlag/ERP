package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlementRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsSettlementRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementRecordResult;

/**
 * @author: ty
 * @description: 物流实际结算记录
 * @date: 2022/11/14
 */
public interface ILogisticsSettlementRecordService extends IService<LogisticsSettlementRecord> {

    /**
     * 查询物流实际结算记录
     */
    PageResult<LogisticsSettlementRecordResult> queryRecordListPage(LogisticsSettlementRecordParam param);
}
