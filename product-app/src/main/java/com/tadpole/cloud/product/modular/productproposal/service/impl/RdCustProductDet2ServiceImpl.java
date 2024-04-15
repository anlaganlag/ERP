package com.tadpole.cloud.product.modular.productproposal.service.impl;

import com.tadpole.cloud.product.api.productproposal.entity.RdCustProductDet2;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdCustProductDet2Mapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdCustProductDet2Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提案-定品明细 服务实现类
 * </p>
 *
 * @author S20210221
 * @since 2024-04-03
 */
@Service
public class RdCustProductDet2ServiceImpl extends ServiceImpl<RdCustProductDet2Mapper, RdCustProductDet2> implements IRdCustProductDet2Service {

    @Resource
    private RdCustProductDet2Mapper mapper;

}
