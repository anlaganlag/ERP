package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.FixedExchangeRateMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FixedExchangeRateResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFixedExchangeRateService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
* <p>
* ERP固定汇率 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class FixedExchangeRateServiceImpl extends ServiceImpl<FixedExchangeRateMapper, FixedExchangeRate> implements IFixedExchangeRateService {

    @DataSource(name = "finance")
    @Override
    public PageResult<FixedExchangeRateResult> findPageBySpec(FixedExchangeRateParam param) {

        Page pageContext = getPageContext();
        if(param.getSearchDate()!=null && !param.getSearchDate().equals("")){
            param.setSearchDate(param.getSearchDate()+"-02");
        }

        IPage<FixedExchangeRateResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "erpcloud")
    @Override
    public List<FixedExchangeRate> queryErp() {
        return this.baseMapper.queryErp();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertErpRate(List<FixedExchangeRate> list) {
        this.baseMapper.deleteAll();
        for (FixedExchangeRate Rate:list) {
            this.save(Rate);
        }

    }

    @DataSource(name = "finance")
    @Override
    public List<FixedExchangeRate> originalCurrencyList() {
        return this.baseMapper.originalCurrencyList();
    }

    @DataSource(name = "finance")
    @Override
    public FixedExchangeRate getRateByDateCurrency(FixedExchangeRateParam param) {
        return this.baseMapper.getRateByDateCurrency(param);
    }

    @DataSource(name = "finance")
    @Override
    public List<FixedExchangeRate> getFixedExchangeRateList(FixedExchangeRateParam param) {
        return this.baseMapper.getFixedExchangeRateList(param);
    }

    @DataSource(name = "erpcloud")
    @Override
    public FixedExchangeRate getRateByDateCurrencyErp(FixedExchangeRateParam param) {
        return this.baseMapper.getRateByDateCurrencyErp(param);
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
