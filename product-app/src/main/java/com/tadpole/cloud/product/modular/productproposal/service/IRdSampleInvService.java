package com.tadpole.cloud.product.modular.productproposal.service;

import com.tadpole.cloud.product.api.productproposal.entity.RdSampleInv;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 提案-开发样盘点 服务类
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
public interface IRdSampleInvService extends IService<RdSampleInv> {
    List<RdSampleInv> listSampleInv();
}
