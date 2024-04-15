package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlementRecord;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LogisticsSettlementRecordMapper;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsSettlementRecordParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementRecordResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: ty
 * @description: 物流实际结算记录
 * @date: 2022/11/14
 */
@Service
@Slf4j
public class LogisticsSettlementRecordServiceImpl extends ServiceImpl<LogisticsSettlementRecordMapper, LogisticsSettlementRecord> implements ILogisticsSettlementRecordService {

    @Override
    @DataSource(name = "logistics")
    public PageResult<LogisticsSettlementRecordResult> queryRecordListPage(LogisticsSettlementRecordParam param) {
        return new PageResult<>(this.baseMapper.queryRecordListPage(getPageContext(), param));
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
