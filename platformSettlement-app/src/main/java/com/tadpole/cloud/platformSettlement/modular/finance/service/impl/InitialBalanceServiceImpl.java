package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.InitialBalance;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.InitialBalanceMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.InitialBalanceParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.InitialBalanceResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IInitialBalanceService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* <p>
* 设置期初余额 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class InitialBalanceServiceImpl extends ServiceImpl<InitialBalanceMapper, InitialBalance> implements IInitialBalanceService {

    @DataSource(name = "finance")
    @Override
    public PageResult<InitialBalanceResult> findPageBySpec(InitialBalanceParam param) {
        Page pageContext = param.getPageContext();

        IPage<InitialBalanceResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "finance")
    @Override
    public List<InitialBalanceResult> exportInitialBalanceList(
        InitialBalanceParam param) {
        List<InitialBalanceResult> page = this.baseMapper.exportInitialBalanceList( param);
        return page;
    }

    @Override
    @DataSource(name = "finance")
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Wrapper<InitialBalance> updateWrapper) {
        return this.update(null,updateWrapper);
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<  InitialBalance>   InitialBalanceList) {
        this.saveBatch(InitialBalanceList);
    }

    @DataSource(name = "finance")
    @Override
    public void updateBalance(InitialBalanceParam param) {
        UpdateWrapper<InitialBalance> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId())
                .set("FISCAL_PERIOD",param.getFiscalPeriod())
                .set("INITIAL_BALANCE",param.getInitialBalance());

        this.baseMapper.update(null,updateWrapper);
    }

}
