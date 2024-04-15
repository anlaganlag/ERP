package com.tadpole.cloud.platformSettlement.modular.vat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.VatShop;
import com.tadpole.cloud.platformSettlement.modular.vat.mapper.VatShopMapper;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IVatShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 税金测算VAT店铺维度 服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Service
public class VatShopServiceImpl extends ServiceImpl<VatShopMapper, VatShop> implements IVatShopService {

    @Autowired
    private VatShopMapper mapper;

}
