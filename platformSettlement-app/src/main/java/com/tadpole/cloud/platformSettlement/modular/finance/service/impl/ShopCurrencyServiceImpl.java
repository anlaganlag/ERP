package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.finance.entity.ShopCurrency;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.ShopCurrencyMapper;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ShopCurrencyParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ShopCurrencyResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IShopCurrencyService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* <p>
* 店铺报告币别 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class ShopCurrencyServiceImpl extends ServiceImpl<ShopCurrencyMapper, ShopCurrency> implements IShopCurrencyService {

    @DataSource(name = "finance")
    @Override
    public PageResult<ShopCurrencyResult> findPageBySpec(ShopCurrencyParam param) {
        IPage<ShopCurrencyResult> page = this.baseMapper.findPageBySpec(param.getPageContext(), param);
        return new PageResult<>(page);
    }


    @DataSource(name = "finance")
    @Override
    public List<ShopCurrencyResult> exportShopCurrencyList(ShopCurrencyParam param) {
        List<ShopCurrencyResult> page = this.baseMapper.exportShopCurrencyList( param);
        return page;
    }

    @DataSource(name = "stocking")
    @Override
    public List<TbComShop> findShopEbms(TbComShop shop) {
        return this.baseMapper.findShopEbms(shop);
    }

    @DataSource(name = "finance")
    @Override
    public void autoAnalysis(List<TbComShop> list) {
        for (TbComShop tbComShop:list) {
            this.baseMapper.autoAnalysis(tbComShop);
        }
    }

    @DataSource(name = "finance")
    @Override
    public void updateOriCurrency() {
        this.baseMapper.updateOriCurrency();
    }

    @DataSource(name = "finance")
    @Override
    public List<ShopCurrencyResult> getShop() {
        return this.baseMapper.getShop();
    }

    @DataSource(name = "finance")
    @Override
    public void updateCurrency(ShopCurrencyParam param) {
        UpdateWrapper<ShopCurrency> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId())
                .set("PROCEEDS_CURRENCY",param.getProceedsCurrency());

        this.baseMapper.update(null,updateWrapper);
    }

    @DataSource(name = "finance")
    @Override
    public List<ShopCurrencyResult> getCurrency(ShopCurrencyParam param) {
        return this.baseMapper.getCurrency(param);
    }


    private Page getPageContext() {
        return PageFactory.defaultPage();
    }
}
