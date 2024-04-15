package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3PurchaseInStockOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3PurchaseInStockOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3PurchaseInStockOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3PurchaseInStockOrderResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangOrdersResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * K3采购入库单 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-05-17
 */
public interface IK3PurchaseInStockOrderService extends IService<K3PurchaseInStockOrder> {

    PageResult<K3PurchaseInStockOrderResult> findPageBySpec(K3PurchaseInStockOrderParam param);

    /**
     * k3采购入库单-生成
     * @return
     */
    ResponseData createPurchaseInStock();

    /**
     * 根据马帮的销售订单生成 k3的采购入库单数据 并保存在mc系统
     * @param ordersResultList
     * @return
     */
    boolean createPurchaseInStock(List<MabangOrdersResult> ordersResultList);

    /**
     * 同步采购入库单
     * @return
     */
    ResponseData syncPurchaseInStock();


    /**
     * 同步采购入库单到k3
     * @param stockOrder
     * @param itemList
     * @return
     */
    ResponseData syncPurchaseInStockToErp(K3PurchaseInStockOrder stockOrder, List<K3PurchaseInStockOrderItem> itemList);

    /**
     * k3采购入库单查询更新
     * @param billNo
     * @return
     */
    ResponseData updateVerifyDate(String billNo);

    /**
     * 更新需求Team
     * @param param
     * @return
     */
    ResponseData updateTeam(K3PurchaseInStockOrderParam param);

}
