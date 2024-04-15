package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.*;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaShipmentSalesParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 亚马逊物流买家货件销售 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-23
 */
public interface FbaShipmentSalesMapper extends BaseMapper<FbaShipmentSales> {

  /**
   * 分页查询列表、导出
   * @param page
   * @param paramCondition
   * @return
   */
  Page<FbaShipmentSales> customPageList(@Param("page") Page page, @Param("paramCondition") FbaShipmentSalesParam paramCondition);

  /**
   * 获取没有刷上销售组织编码和库存组织编码的数据
   * @return
   */
  List<FbaShipmentSales> remainList();

  /**
   * Quantity合计
   * @param paramCondition
   * @return
   */
  Integer getQuantity(@Param("paramCondition") FbaShipmentSalesParam paramCondition);

  /**
   * 获取销售出库列表头部数据
   * @param paramCondition
   * @return
   */
  List<SalesStockOut> getListHeader(@Param("paramCondition") FbaShipmentSalesParam paramCondition);

  /**
   * 根据账号、站点、sku获取物料编码
   * @param paramCondition
   * @return
   */
  String getMaterial(@Param("paramCondition") FbaShipmentSalesParam paramCondition);

  /**
   * 获取销售列表明细数据
   * @param paramCondition
   * @return
   */
  List<SalesStockOutDetail> getDetailList(@Param("paramCondition") SalesStockOut paramCondition);

  /**
   * 更新销售出库fba_shipment_sales源报告生成状态和单据头编码
   * @param paramCondition
   */
  void updateSrcList(@Param("paramCondition") SalesStockOut paramCondition);

  /**
   * 销售出库列表明细刷listing
   */
  void updateDetailList();

  /**
   * 销售出库列表明细刷存档listing
   */
  void updateFileDetailList();

  /**
   * 同步ERP仓库组织编码
   * @param code
   */
  void syncErpCode(@Param("paramCondition") ErpOrgCode code);

  /**
   * 同步ERP仓库组织编码
   * @return
   */
  List<ErpOrgCode> getErpCode();

  /**
   * 批量审核
   * @param paramCondition
   * @return
   */
  Boolean verifyUpdateBatch(@Param("paramCondition") FbaShipmentSalesParam paramCondition);

  /**
   * 批量作废
   * @param paramCondition
   */
  void rejectBatch(@Param("paramCondition") FbaShipmentSalesParam paramCondition);

  /**
   * 获取销售出库列表组织编码及物料编码
   * @param detailIdList
   * @return
   */
  List<ZZDistributeMcms> assignMaterialList(@Param("detailIdList") List<String> detailIdList);

  /**
   * 刷组织名称（Amazon_账号_站点）
   */
  void addOrgName();

  /**
   * 刷仓库组织名称（Amazon_账号_站点_仓库）
   */
  void addWarehouseOrgName();

  /**
   * 根据组织名称刷库存组织编码
   */
  void refreshOrgCode();

  /**
   * 根据仓库组织名称刷仓库组织编码
   */
  void refreshWareOrgCode();

  /**
   * 匹配订单表刷销售组织名称（平台_账号_站点，只刷平台为Amazon的）
   */
  void addSalOrg();

  /**
   * 平台为null的先去订单表刷平台
   */
  void refreshOrderPlatform(@Param("amazonOrders") String amazonOrders, @Param("amazonOrderDetail") String amazonOrderDetail);

  /**
   * 销售组织为空的，拿到FBA_SHIPMENT_SALES的AMAZON_ORDER_ID去匹配AMAZON_ORDERS的MERCHANT_ORDER_ID，再根据AMAZON_ORDER_DETAIL拿到销售平台、账号、站点（只刷平台为Amazon的）
   */
  void refreshOrderSalOrg(@Param("amazonOrders") String amazonOrders, @Param("amazonOrderDetail") String amazonOrderDetail);

  /**
   * 拿到OrderId去结算报告里匹配销售组织名称（平台_账号_站点，只刷平台为Amazon的）
   */
  void refreshSalOrg(@Param("settlementDetail") String settlementDetail);

  /**
   * 根据销售组织名称刷销售组织编码
   */
  void refreshSalOrgCode();

  /**
   * 针对B2B情况特殊处理，FBA_SHIPMENT_SALES存在B2B平台解析出来是Amazon，需要替换销售组织编码位：B2B_MK_ALL
   */
  void updateB2BSalOrg();

  /**
   * 针对eBay刷销售组织名称
   */
  void addEBaySalOrg();

  /**
   * 针对Rakuten刷销售组织名称
   */
  void addRakutenSalOrg();

  /**
   * 针对shopify情况特殊处理，FBA_SHIPMENT_SALES存在B2B平台解析出来是Amazon，需要替换销售组织编码位：shopify_Glucoracy_ALL
   */
  void addShopifySalOrg();
}
