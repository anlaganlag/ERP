package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.AmazonOrders;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderAbnormalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderDataSourceMonitorParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderAbnormalResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderDataSourceMonitorResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 销量汇总订单数据 Mapper 接口
 * </p>
 *
 * @author ty
 * @since 2022-05-06
 */
public interface AmazonOrdersMapper extends BaseMapper<AmazonOrders> {

    /**
     * 销售订单源报告数据监控列表
     * @param param
     * @return
     */
    Page<OrderDataSourceMonitorResult> queryOrderDataSourceMonitorPage(@Param("page") Page page, @Param("param") OrderDataSourceMonitorParam param);

    /**
     * 订单异常情况汇总列表
     * @param param
     * @return
     */
    Page<OrderAbnormalResult> queryOrderAbnormalPage(@Param("page") Page page, @Param("param") OrderAbnormalParam param);

    /**
     * Amazon订单去重
     * @param days
     */
    void dealAmazonOrderRepeat(@Param("days") Integer days);

    /**
     * Amazon订单明细去重
     * @param days
     */
    void dealAmazonOrderDetailRepeat(@Param("days") Integer days);
}
