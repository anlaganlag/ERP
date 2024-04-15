package com.tadpole.cloud.platformSettlement.modular.vat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatShopCheck;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatShopCheckParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.VatShopCheckResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 账号检查表 Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
public interface VatShopCheckMapper extends BaseMapper<VatShopCheck> {

    void shopCheckInsert(@Param("shop") TbComShop tbComShop);

    void shopCheckStatus(String period);

    Page<VatShopCheckResult> queryListPage(@Param("page") Page pageContext, @Param("param") VatShopCheckParam param);

    List<String> euShop();
}
