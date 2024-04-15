package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackOrderDetail;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsBtbPackOrderDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbPackOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * BTB订单发货明细信息 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-12-19
 */
@Service
public class LsBtbPackOrderDetailServiceImpl extends ServiceImpl<LsBtbPackOrderDetailMapper, LsBtbPackOrderDetail> implements ILsBtbPackOrderDetailService {

    @Resource
    private LsBtbPackOrderDetailMapper mapper;

}
