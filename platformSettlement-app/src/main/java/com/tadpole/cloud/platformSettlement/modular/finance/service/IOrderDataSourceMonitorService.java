package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.OrderDatasourceMonitor;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderDataSourceMonitorParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderDataSourceMonitorResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author: ty
 * @description: 销售订单源报告数据监控服务类
 * @date: 2022/5/6
 */
public interface IOrderDataSourceMonitorService extends IService<OrderDatasourceMonitor> {

    /**
     * 销售订单源报告数据监控列表
     */
    ResponseData queryPage(OrderDataSourceMonitorParam param);

    /**
     * 销售订单源报告数据监控列表导出
     * @param param
     * @return
     */
    List<OrderDataSourceMonitorResult> export(OrderDataSourceMonitorParam param);

    /**
     * Amazon订单去重
     */
    ResponseData dealAmazonOrderRepeat(Integer days);

    /**
     * 销售订单源报告数据监控数据生成（刷新）
     */
    ResponseData refresh();
}
