package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SpotExchangeRate;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SpotExchangeRateMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SpotExchangeRateResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
* <p>
* ERP即期汇率 服务实现类
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Service
public class SpotExchangeRateServiceImpl extends ServiceImpl<SpotExchangeRateMapper, SpotExchangeRate> implements ISpotExchangeRateService {

    @DataSource(name = "finance")
    @Override
    public PageResult<SpotExchangeRateResult> findPageBySpec(SpotExchangeRateParam param) {

        Page pageContext = getPageContext();
        if(param.getSearchDate()!=null && !param.getSearchDate().equals("")){
            param.setSearchDate(param.getSearchDate()+"-02");
        }

        IPage<SpotExchangeRateResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return new PageResult<>(page);
    }

    @DataSource(name = "erpcloud")
    @Override
    public List<SpotExchangeRate> queryErp() {
        return this.baseMapper.queryErp();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertErpRate(List<SpotExchangeRate> list) {
        //为方便计算，插入即期美元兑美元汇率
        SpotExchangeRate usdSpotExchangeRate = new SpotExchangeRate();
        usdSpotExchangeRate.setDirectRate(BigDecimal.ONE);
        usdSpotExchangeRate.setIndirectRate(BigDecimal.ONE);
        usdSpotExchangeRate.setRateType("即期汇率");
        usdSpotExchangeRate.setOriginalCurrency("USD");
        usdSpotExchangeRate.setTargetCurrency("USD");
        usdSpotExchangeRate.setEffectDate(DateUtil.beginOfYear(DateUtil.date()));
        usdSpotExchangeRate.setIneffectiveDate(DateUtil.beginOfDay(DateUtil.endOfYear(DateUtil.date())));
        usdSpotExchangeRate.setDataStatus("已审核");
        usdSpotExchangeRate.setForbiddenStatus("否");
        usdSpotExchangeRate.setOriginalCurrencyName("美元");
        usdSpotExchangeRate.setTargetCurrencyName("美元");
        list.add(usdSpotExchangeRate);

        this.baseMapper.deleteAll();
        this.saveBatch(list);

    }

    @DataSource(name = "finance")
    @Override
    public List<SpotExchangeRate> originalCurrencyList() {
        return this.baseMapper.originalCurrencyList();
    }

    @DataSource(name = "finance")
    @Override
    public List<SpotExchangeRateResult> exportShopCurrencyList(SpotExchangeRateParam param) {

        Page pageContext = PageFactory.defaultPage();
        pageContext.setSize(Integer.MAX_VALUE);
        IPage<SpotExchangeRateResult> page = this.baseMapper.findPageBySpec(pageContext, param);
        return page.getRecords();
    }

    @DataSource(name = "finance")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpotExchangeRate getRateByDateCurrency(SpotExchangeRateParam param) {
        return this.baseMapper.getRateByDateCurrency(param);
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

}
