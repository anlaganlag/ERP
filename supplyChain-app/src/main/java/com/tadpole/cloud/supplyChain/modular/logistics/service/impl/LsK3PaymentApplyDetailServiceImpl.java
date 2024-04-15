package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import com.tadpole.cloud.supplyChain.api.logistics.entity.LsK3PaymentApplyDetail;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsK3PaymentApplyDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsK3PaymentApplyDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 物流费K3付款申请单明细 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-12-05
 */
@Service
public class LsK3PaymentApplyDetailServiceImpl extends ServiceImpl<LsK3PaymentApplyDetailMapper, LsK3PaymentApplyDetail> implements ILsK3PaymentApplyDetailService {

    @Resource
    private LsK3PaymentApplyDetailMapper mapper;

}
