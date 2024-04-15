package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.DatarangeDtail;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StatementIncome;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.DatarangeDtailMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.DatarangeDtailParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.DatarangeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DatarangeResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IDatarangeDtailService;
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
* datarange源数据 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Slf4j
@Service
public class DatarangeDtailServiceImpl extends ServiceImpl<DatarangeDtailMapper, DatarangeDtail> implements IDatarangeDtailService {

    @Autowired
    private IStatementIncomeService incomeService;

    @DataSource(name = "finance")
    @Override
    public List<DatarangeResult> getDatarangeList(DatarangeParam params) {

        List<DatarangeResult> list = this.baseMapper.getDatarangeList(params);
        return list;
    }

    @DataSource(name = "finance")
    @Override
    public List<DatarangeDtail> getDatarangeDtailList(String settlementId) {

        List<DatarangeDtail> list = this.baseMapper.getDatarangeDtailList(settlementId);
        return list;
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void RefreshFinancialClass(DatarangeDtailParam param) {
        log.info("刷取Date Range财务分类明细开始");
        // 查询主数据状态为已审核
        DatarangeParam params=new DatarangeParam();
        List<DatarangeResult> list=getDatarangeList(params);
        //循环list
        if(list.size()>0) {
            //Datarange刷财务分类明细1
//            this.baseMapper.FillFinancialClass(param);
            for (int i = 0; i < list.size(); i++) {

                param.setSettlementId(list.get(i).getSettlementId());
                //更新difference值
                this.baseMapper.refreshDifference(param);
                //行转列,刷新财务分类2
                param.setBatchNo(list.get(i).getBatchNo());
                InsertDataRangeDetailComfirm(param);
                //更新Datarange状态为行转列：2
                params.setSettlementId(list.get(i).getSettlementId());
                params.setBatchNo(list.get(i).getBatchNo());
                params.setStatus(2);
                this.baseMapper.UpdateStatus(params);
                //Datarange刷特殊财务分类更新3
                this.baseMapper.SpecialFinancialClass(param);
                //Datarange刷财务分类明细1
                this.baseMapper.FillFinancialClass(param);

                //获取刷取财务分类未成功的Datarange数据（查询明细 财务分类字段是不是有空）
                List<DatarangeDtail> alist=getDatarangeDtailList(list.get(i).getSettlementId());
                if(alist.size()>0){
                    //未刷成功的分类写入分类表
                    this.baseMapper.InsertFinancialClass(param);
                    UpdateWrapper<StatementIncome> income = new UpdateWrapper<>();
                    income.eq("SETTLEMENT_ID",list.get(i).getSettlementId()).ne("SYNC_STATUS",4)
                            .set("REFRESH_STATUS",1);

                    incomeService.update(null,income);
                }
                else
                {
                    UpdateWrapper<StatementIncome> income = new UpdateWrapper<>();
                    income.eq("SETTLEMENT_ID",list.get(i).getSettlementId()).ne("SYNC_STATUS",4)
                            .set("REFRESH_STATUS",0);

                    incomeService.update(null,income);
                    //刷新财务分类金额
                    this.baseMapper.RefreshFinancialClass(param);
                    //更新已刷费用状态为3
                    params.setStatus(3);
                    this.baseMapper.UpdateStatus(params);
                }
            }
        }
        log.info("刷取Date Range财务分类明细结束");
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void InsertDataRangeDetailComfirm(DatarangeDtailParam param) {
        this.baseMapper.InsertDataRangeDetailComfirm(param);
    }

    @DataSource(name = "finance")
    @Override
    public void UpdateStatus(DatarangeParam param) {
        this.baseMapper.UpdateStatus(param);
    }

    @DataSource(name = "finance")
    @Override
    public void InsertFinancialClass(DatarangeDtailParam param) {
        this.baseMapper.InsertFinancialClass(param);
    }

}
