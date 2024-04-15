package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.OrderDatasourceMonitor;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderDataSourceMonitorParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderDataSourceMonitorResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* <p>
*  Mapper 接口
* </p>
*
* @author ty
* @since 2022-06-15
*/
public interface OrderDatasourceMonitorMapper extends BaseMapper<OrderDatasourceMonitor> {

    /**
     * 销售订单源报告数据监控列表
     * @param param
     * @return
     */
    Page<OrderDataSourceMonitorResult> queryOrderDataSourceMonitorPage(@Param("page") Page page, @Param("param") OrderDataSourceMonitorParam param);

    /**
     * 刷新（生成销售订单预结算数据）
     * @param fbaRemovalOrderDetail
     */
    void refresh(@Param("fbaRemovalOrderDetail") String fbaRemovalOrderDetail);
}
