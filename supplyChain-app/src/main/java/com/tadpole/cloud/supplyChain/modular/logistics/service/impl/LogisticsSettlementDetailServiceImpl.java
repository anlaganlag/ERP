package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LogisticsSettlementDetail;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LogisticsSettlementDetailMapper;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsSettlementDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LogisticsSettlementDetailResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 物流实际结算明细
 * @date: 2022/11/14
 */
@Service
@Slf4j
public class LogisticsSettlementDetailServiceImpl extends ServiceImpl<LogisticsSettlementDetailMapper, LogisticsSettlementDetail> implements ILogisticsSettlementDetailService {

    @Override
    @DataSource(name = "logistics")
    public PageResult<LogisticsSettlementDetailResult> queryDetailListPage(LogisticsSettlementDetailParam param) {
        return new PageResult<>(this.baseMapper.queryDetailListPage(getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public void batchUpdateByIds(List<BigDecimal> detailIdList, String dataMonths){
        this.baseMapper.batchUpdateByIds(detailIdList, dataMonths);
    }

    @Override
    @DataSource(name = "logistics")
    public void updateDetailById(LogisticsSettlementDetail detail){
        this.baseMapper.updateDetailById(detail);
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
