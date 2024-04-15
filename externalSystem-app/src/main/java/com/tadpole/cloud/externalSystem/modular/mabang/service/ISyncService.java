package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3PurchaseOrderInStockParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3AvailableResult;

import java.util.Date;
import java.util.List;

public interface ISyncService {
    public ResponseData syncToManbang();

    ResponseData anitAudit(String billNo);

    /**
     * k3指定仓库所有物料可用数量，销售出库到马帮erp
     * @param warehouseList 待同步的仓库名称集合
     * @return
     */
    ResponseData syncAllMatAvailableQty(List<String> warehouseList);

    /**
     * 查出k3系统指定仓库下的所有物料可以用数量>0的
     * @param warehouseList
     * @return
     */
    List<K3AvailableResult> queryK3AvailableQty(List<String> warehouseList);

    /**
     * 物料库存价格更新
     * @param fallbackMonths 当前月份回退 多少个月 取价格更新
     * @return
     */
    ResponseData updateMatStockPrice(Integer fallbackMonths);

    /**
     * k3采购入库单
     * @param param
     * @return
     */
    ResponseData purchaseOrderInStock(K3PurchaseOrderInStockParam param);




    ResponseData purchaseOrderInStock2(String jsonData);
    /**
     * 采购入库单保存
     * @param jsonData
     * @return
     */
    ResponseData saveInStockOrder(String jsonData);

    /**
     * 采购入库单提交
     * @param number
     * @param fid
     * @return
     */
    ResponseData inStockCommit(String number, Integer fid);

    /**
     * 采购入库单查询
     * @param number
     * @param fid
     * @return
     */
    Date queryInStockVerifyDate(String billNo);
}
