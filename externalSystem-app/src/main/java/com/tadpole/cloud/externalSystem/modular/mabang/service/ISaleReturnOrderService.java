package com.tadpole.cloud.externalSystem.modular.mabang.service;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleReturnOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3CrossTransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SaleReturnOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportSaleReturnOrderResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 销售退货单 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-24
 */
public interface ISaleReturnOrderService extends IService<SaleReturnOrder> {

    /**
     * 销售退货单查询列表接口
     */
    ResponseData findPageBySpec(SaleReturnOrderParam param);

    /**
     * 销售退货单列表导出接口
     */
    List<ExportSaleReturnOrderResult> export(SaleReturnOrderParam param);

    /**
     * 平台名称下拉
     * @return
     */
    List<Map<String, Object>> platformSelect();

    /**
     * 年份下拉
     * @return
     */
    List<Map<String, Object>> yearSelect();

    /**
     * 月份下拉
     * @return
     */
    List<Map<String, Object>> monthSelect();

    /**
     * 店铺名称下拉
     * @return
     */
    List<Map<String, Object>> shopSelect(List<String> platformNames);

    /**
     * 站点下拉
     * @return
     */
    List<Map<String, Object>> siteSelect();

    /**
     * 获取马帮退货订单生成跨组织调拨单
     */
    ResponseData generateSaleReturnOrderByMabangReturnOrders(K3CrossTransferItemParam param) throws Exception;

    String getFinanceName(String fNumber);

    String getWarehouseName(String fNumber);

    List<String> getSalOrgName();

    void updateSalOrgName(String ownerId,String ownerName);

    void updateWarehouseName(String ownerId,String ownerName);

    ResponseData generateOtherInOrder(K3CrossTransferItemParam param);

    ResponseData syncOtherInOrder(K3CrossTransferItemParam param);

    public ResponseData syncOtherInOrder2Erp(SaleReturnOrder order);
}
