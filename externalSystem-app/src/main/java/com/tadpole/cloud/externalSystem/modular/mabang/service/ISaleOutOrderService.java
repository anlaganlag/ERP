package com.tadpole.cloud.externalSystem.modular.mabang.service;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrder;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.SaleOutOrderParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ExportSaleOutOrderResult;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 销售出库单 服务类
 * </p>
 *
 * @author cyt
 * @since 2022-08-24
 */
public interface ISaleOutOrderService extends IService<SaleOutOrder> {

    /**
     * 销售出库单查询列表接口
     */
    ResponseData findPageBySpec(SaleOutOrderParam param);

    /**
     * 销售出库单列表导出接口
     */
    List<ExportSaleOutOrderResult> export(SaleOutOrderParam param);

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
     * 获取马帮发货订单生成销售出库单
     * @return
     */
    ResponseData generateSaleOutOrderByMabangOrders();

    /**
     * 同步到K3销售出库单
     * @return
     */
    ResponseData syncSaleOutOrderToErp(String year,String month);

    /**
     * 分配销售组织物料
     * @return
     */
    List<ZZDistributeMcms> getFsaleOrgIdAndMat();

    String getFinanceName(String fNumber);

    String getWarehouseName(String fNumber);

    List<String> getSalOrgName();

    void updateSalOrgName(String ownerId,String ownerName);

    void updateWarehouseName(String ownerId,String ownerName);

}
