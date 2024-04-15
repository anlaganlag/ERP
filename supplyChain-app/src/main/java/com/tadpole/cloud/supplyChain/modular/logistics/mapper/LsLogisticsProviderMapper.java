package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsProvider;

import java.util.List;

/**
 * <p>
 * 物流商管理 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
public interface LsLogisticsProviderMapper extends BaseMapper<LsLogisticsProvider> {

    /**
     * 物流商信息下拉
     * @return
     */
    List<LsLogisticsProvider> logisticsProviderSelect();
}
