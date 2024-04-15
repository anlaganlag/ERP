package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.OrderAbnormal;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OrderAbnormalParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OrderAbnormalResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author: ty
 * @description: 订单异常情况汇总服务类
 * @date: 2022/5/10
 */
public interface IOrderAbnormalService extends IService<OrderAbnormal> {

    /**
     * 订单异常情况汇总列表
     */
    ResponseData queryPage(OrderAbnormalParam param);

    /**
     * 订单异常情况汇总列表导出
     * @param param
     * @return
     */
    List<OrderAbnormalResult> export(OrderAbnormalParam param);

    /**
     * 订单异常情况汇总数据生成（刷新）
     */
    ResponseData refresh();
}
