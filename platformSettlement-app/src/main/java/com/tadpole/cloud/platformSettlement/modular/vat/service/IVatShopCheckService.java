package com.tadpole.cloud.platformSettlement.modular.vat.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatShopCheck;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.VatShopCheckParam;
import java.util.List;

/**
 * <p>
 * 账号检查表 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
public interface IVatShopCheckService extends IService<VatShopCheck> {

    ResponseData queryListPage(VatShopCheckParam param);

    ResponseData shopCheck(List<TbComShop> list);

    ResponseData updateRmark(VatShopCheckParam param);

    ResponseData euShop();
}
