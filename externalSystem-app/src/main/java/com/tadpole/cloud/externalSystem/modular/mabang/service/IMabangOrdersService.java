package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangOrders;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangOrdersParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.OrderParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.PurOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.SkuStockQtyParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangOrderItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangOrdersResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;

import java.util.List;


/**
 * <p>
 * 马帮订单列表 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-09
 */
public interface IMabangOrdersService extends IService<MabangOrders> {


    PageResult<MabangOrdersResult> list(MabangOrdersParam param);
    List<MabangOrdersResult> exportList(MabangOrdersParam param);



    void add(MabangResult param);

    void addNew(MabangResult param);

    ResponseData getOrderList(OrderParm param);
    ResponseData getPurOrderList(PurOrderParam param);
    ResponseData getMatStockQty(SkuStockQtyParam param);

    ResponseData refreshAllMatStockQty() throws InterruptedException;







    ResponseData getFinishedOrderList(OrderParm param);


    ResponseData getHisOrderList(OrderParm param);
    ResponseData getHisFinishedOrderList(OrderParm param);



    PageResult<MabangOrderItemResult> display(String OrderId);



    Integer getByPlatformOrderId(String id);
    void deleteByPlatformOrderId(String id);


    /***
     * 获取马帮订单待创建K3采购入库单的 订单
     * @param param
     * @return
     */
    List<MabangOrdersResult> getWaitCreatePurchaseOrder(MabangOrdersParam param);

    /**
     * 根据马帮订单号获取订单信息
     * @param orderId
     * @return
     */
    ResponseData getOrderListByOrderId(String orderId);


    MabangOrders getMabangOrderByPlatformOrderId(String platOrdId);
}
