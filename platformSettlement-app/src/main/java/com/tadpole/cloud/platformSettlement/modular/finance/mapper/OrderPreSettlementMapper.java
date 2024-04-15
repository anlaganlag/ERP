package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.OrderPreSettlement;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderPreSettlementParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderPreSettlementResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* <p>
*  Mapper 接口
* </p>
*
* @author cyt
* @since 2022-06-08
*/
public interface OrderPreSettlementMapper extends BaseMapper<OrderPreSettlement> {

    /**
     * 销售订单预结算列表
     * @param param
     * @return
     */
    Page<OrderPreSettlementResult> queryOrderPreSettlementPage(@Param("page") Page page, @Param("param") OrderPreSettlementParam param);

    /**
     * 获取汇总数
     * @param param
     * @return
     */
    OrderPreSettlementResult getTotalQuantity(@Param("param") OrderPreSettlementParam param);

    /**
     * 刷新（生成销售订单预结算数据）
     */
    void refresh();
}
