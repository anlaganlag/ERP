package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.DatarangeDetailComfirm;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementReportCheck;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.DatarangeDetailComfirmMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StatementIncomeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.DetailResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IDatarangeDetailComfirmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* <p>
* datarange明细刷完财务分类后的 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class DatarangeDetailComfirmServiceImpl extends ServiceImpl<DatarangeDetailComfirmMapper, DatarangeDetailComfirm> implements IDatarangeDetailComfirmService {


    @DataSource(name = "finance")
    @Override
    public DatarangeDetailComfirm getRangeMoney(DatarangeDetailComfirm param) {
        return this.baseMapper.getRangeMoney(param);
    }

    @DataSource(name = "finance")
    @Override
    public DatarangeDetailComfirm getNotAmazonFee(DatarangeDetailComfirm param) {
        return this.baseMapper.getNotAmazonFee(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<DatarangeDetailComfirm> getPlatformAmazonFee(DatarangeDetailComfirm param) {
        return this.baseMapper.getPlatformAmazonFee(param);
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
}
