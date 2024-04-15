package com.tadpole.cloud.operationManagement.modular.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockPurchaseOrders;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockPurchaseOrdersParam;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 采购订单 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-06-30
 */
public interface IStockPurchaseOrdersService extends IService<StockPurchaseOrders> {

    void exportExcel(HttpServletResponse response, StockPurchaseOrdersParam param);
}
