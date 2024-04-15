package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonitorGoogdwillParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonitorGoogdwillResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.MonitorGoogdwillTotal;
import com.tadpole.cloud.platformSettlement.api.finance.entity.MonitorGoogdwill;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * goodwill监控表	 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-07-17
 */
public interface IMonitorGoogdwillService extends IService<MonitorGoogdwill> {
    PageTotalResult<MonitorGoogdwillResult, MonitorGoogdwillTotal> findPageBySpec(MonitorGoogdwillParam param);
    List<MonitorGoogdwill> export(MonitorGoogdwillParam param);
    void afreshStorageFee();
}
