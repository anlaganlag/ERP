package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgSaihuProduct;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgSaihuProductMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgSaihuProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 赛狐在线产品 服务实现类
 * </p>
 *
 * @author ty
 * @since 2024-02-23
 */
@Service
public class TgSaihuProductServiceImpl extends ServiceImpl<TgSaihuProductMapper, TgSaihuProduct> implements ITgSaihuProductService {

    @Resource
    private TgSaihuProductMapper mapper;

}
