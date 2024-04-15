package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.OrderAbnormal;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderAbnormalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderAbnormalResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* <p>
*  Mapper 接口
* </p>
*
* @author cyt
* @since 2022-06-16
*/
public interface OrderAbnormalMapper extends BaseMapper<OrderAbnormal> {

    /**
     * 订单异常情况汇总列表
     * @param param
     * @return
     */
    Page<OrderAbnormalResult> queryOrderAbnormalPage(@Param("page") Page page, @Param("param") OrderAbnormalParam param);

    /**
     * 订单异常情况汇总数据生成（刷新）
     * @param fbaCustomerReturns
     * @param fbaShipmentSales
     */
    void refresh(@Param("fbaCustomerReturns") String fbaCustomerReturns, @Param("fbaShipmentSales") String fbaShipmentSales);
}
