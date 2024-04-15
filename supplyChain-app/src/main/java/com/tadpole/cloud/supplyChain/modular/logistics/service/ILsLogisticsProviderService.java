package com.tadpole.cloud.supplyChain.modular.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsProvider;

import java.util.List;

/**
 * <p>
 * 物流商管理 服务类
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
public interface ILsLogisticsProviderService extends IService<LsLogisticsProvider> {

    /**
     * 物流商信息下拉
     */
    List<LsLogisticsProvider> logisticsProviderSelect();

    /**
     * 物流商编码下拉
     */
    List<LsLogisticsProvider> lpCodeSelect();

    /**
     * 物流商名称下拉
     */
    List<LsLogisticsProvider> lpNameSelect();

    /**
     * 物流商简称下拉
     */
    List<LsLogisticsProvider> lpSimpleNameSelect();

    /**
     * 统一社会信用代码下拉
     */
    List<LsLogisticsProvider> lpUniSocCreCodeSelect();

}
