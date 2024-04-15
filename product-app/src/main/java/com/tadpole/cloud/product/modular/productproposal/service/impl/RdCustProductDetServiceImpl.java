package com.tadpole.cloud.product.modular.productproposal.service.impl;

import com.tadpole.cloud.product.api.productproposal.entity.RdCustProductDet;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdCustProductDetMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdCustProductDetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提案-定品明细 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
@Service
public class RdCustProductDetServiceImpl extends ServiceImpl<RdCustProductDetMapper, RdCustProductDet> implements IRdCustProductDetService {

    @Resource
    private RdCustProductDetMapper mapper;

}
