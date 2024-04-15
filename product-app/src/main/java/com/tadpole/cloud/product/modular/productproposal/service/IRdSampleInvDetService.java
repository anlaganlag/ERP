package com.tadpole.cloud.product.modular.productproposal.service;

import com.tadpole.cloud.product.api.productproposal.entity.RdSampleInvDet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleInvDetParam;

import java.util.List;

/**
 * <p>
 * 提案-开发样盘点明细 服务类
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
public interface IRdSampleInvDetService extends IService<RdSampleInvDet> {
    List<RdSampleInvDet> listSampleInvDet(RdSampleInvDetParam param);
}
