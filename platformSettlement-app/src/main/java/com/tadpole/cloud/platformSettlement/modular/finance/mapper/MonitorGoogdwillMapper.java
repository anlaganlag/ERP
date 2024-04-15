package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.MonitorGoogdwill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* <p>
    * goodwill监控表	 Mapper接口
    * </p>
*
* @author S20190161
* @since 2023-07-17
*/
    public interface MonitorGoogdwillMapper extends BaseMapper<MonitorGoogdwill> {
    void afreshStorageFee();
    }
