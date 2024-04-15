package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.ShopCurrency;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ShopCurrencyParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ShopCurrencyResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 店铺报告币别 服务类
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
public interface IShopCurrencyService extends IService<ShopCurrency> {

    PageResult<ShopCurrencyResult> findPageBySpec(ShopCurrencyParam param);

    List<ShopCurrencyResult> exportShopCurrencyList(ShopCurrencyParam param);


    List<TbComShop> findShopEbms(TbComShop shop);

    void autoAnalysis(List<TbComShop> list);

    void updateOriCurrency();

    List<ShopCurrencyResult> getShop();

    void updateCurrency(ShopCurrencyParam param);


    List<ShopCurrencyResult> getCurrency(ShopCurrencyParam param);

}
