package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportCheck;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementIncome;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StatementIncomeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DetailResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementDetail;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SettlementDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementDetailService;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailParam;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStatementIncomeService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
* <p>
* settlement明细数据 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Slf4j
@Service
public class SettlementDetailServiceImpl extends ServiceImpl<SettlementDetailMapper, SettlementDetail> implements ISettlementDetailService {


    @Autowired
    private IStatementIncomeService incomeService;

    @DataSource(name = "finance")
    @Override
    public List<SettlementResult> getSettlementList(SettlementParam params) {

        List<SettlementResult> list = this.baseMapper.getSettlementList(params);
        return list;
    }

    @DataSource(name = "finance")
    @Override
    public List<SettlementDetail> getSettlementDetailList(String settlementId) {
        List<SettlementDetail> list = this.baseMapper.getSettlementDetailList(settlementId);
        return list;
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshFinancialClass(SettlementDetailParam param) {
        log.info("刷取settlement财务分类明细开始");
        // 查询主数据状态为已审核
        SettlementParam params=new SettlementParam();
        List<SettlementResult> list=getSettlementList(params);
        //循环list
        if(list.size()>0) {
            //重新刷新增的财务分类到结算明细
            this.baseMapper.FillFinancialClass(param);
            for (int i = 0; i < list.size(); i++) {
                //根据settlementId获取未刷上财务分类的settlement明细数据
                List<SettlementDetail> alist = getSettlementDetailList(list.get(i).getSettlementId());
                param.setSettlementId(list.get(i).getSettlementId());
                if(alist.size()>0){
                    //未刷成功的财务分类写入财务分类表
                    this.baseMapper.InsertFinancialClass(param);
                    UpdateWrapper<StatementIncome> income = new UpdateWrapper<>();
                    income.eq("SETTLEMENT_ID",list.get(i).getSettlementId())
                            .ne("SYNC_STATUS",4)
                            .set("REFRESH_STATUS",1);
                    incomeService.update(null,income);
                } else {
                    UpdateWrapper<StatementIncome> income = new UpdateWrapper<>();
                    income.eq("SETTLEMENT_ID",list.get(i).getSettlementId())
                            .ne("SYNC_STATUS",4)
                            .set("REFRESH_STATUS",0);
                    incomeService.update(null,income);
                    //根据settlementId刷新财务分类
                    this.baseMapper.refreshFinancialClass(param);
                    //根据settlementId更新settlement主表状态为2：已刷财务分类
                    this.baseMapper.updateStatus(param);
                }
            }
        }
        log.info("刷取settlement财务分类明细结束");
    }

    @DataSource(name = "finance")
    @Override
    public void updateStatus(SettlementDetailParam param) {
        this.baseMapper.updateStatus(param);
    }

    @DataSource(name = "finance")
    @Override
    public void InsertFinancialClass(SettlementDetailParam param) {
        this.baseMapper.InsertFinancialClass(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<DetailResult> refreshFailure(StatementIncomeParam param) {
        return this.baseMapper.refreshFailure(param);
    }

    @DataSource(name = "finance")
    @Override
    public void updateSite(SettlementReportCheck param) {
        this.baseMapper.updateSite(param);
    }


    @DataSource(name = "finance")
    @Override
    public SettlementDetail getSettlementMoney(SettlementDetail param) {
        return this.baseMapper.getSettlementMoney(param);
    }

    @DataSource(name = "finance")
    @Override
    public SettlementDetail getNotAmazonFee(SettlementDetail param) {
        return this.baseMapper.getNotAmazonFee(param);
    }



    @DataSource(name = "finance")
    @Override
    public List<SettlementDetail> getPlatformAmazonFee(SettlementDetail param) {
        return this.baseMapper.getPlatformAmazonFee(param);
    }
}
