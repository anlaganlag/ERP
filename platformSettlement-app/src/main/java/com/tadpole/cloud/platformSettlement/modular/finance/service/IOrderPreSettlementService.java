package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.OrderPreSettlement;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderPreSettlementParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderPreSettlementResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author: ty
 * @description: 销售订单预结算服务类
 * @date: 2022/5/30
 */
public interface IOrderPreSettlementService extends IService<OrderPreSettlement> {

    /**
     * AZ销售订单预结算
     */
    ResponseData queryPage(OrderPreSettlementParam param);

    /**
     * AZ销售订单预结算导出
     * @param param
     * @return
     */
    List<OrderPreSettlementResult> export(OrderPreSettlementParam param);

    /**
     * 获取汇总数
     */
    ResponseData getTotalQuantity(OrderPreSettlementParam param);

    /**
     * 刷新（生成销售订单预结算数据）
     */
    ResponseData refresh();

    /**
     * 获取领星亚马逊源报表-退货订单
     * @param synDate
     * @param key
     * @return
     */
    ResponseData getLxRefundOrders(String synDate, String key);
}
