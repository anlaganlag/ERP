package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.ShopCurrency;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ShopCurrencyParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ShopCurrencyResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* 店铺报告币别 Mapper 接口
* </p>
*
* @author gal
* @since 2021-10-25
*/
public interface ShopCurrencyMapper extends BaseMapper<ShopCurrency> {

    Page<ShopCurrencyResult> findPageBySpec(
            @Param("page") Page page, @Param("paramCondition") ShopCurrencyParam paramCondition);


    List<ShopCurrencyResult> exportShopCurrencyList( @Param("paramCondition") ShopCurrencyParam paramCondition);


    List<TbComShop> findShopEbms(@Param("paramCondition") TbComShop shop);

    void autoAnalysis(@Param("paramCondition") TbComShop paramCondition);

    void updateOriCurrency();

    List<ShopCurrencyResult> getShop();

    List<ShopCurrencyResult> getCurrency(@Param("paramCondition") ShopCurrencyParam param);
}
