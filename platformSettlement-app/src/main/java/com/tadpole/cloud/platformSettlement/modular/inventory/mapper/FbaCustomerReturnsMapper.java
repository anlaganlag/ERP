package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.FbaCustomerReturns;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.SalesReturnDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaCustomerReturnsParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.FbaCustomerReturnsResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 亚马逊物流买家退货 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface FbaCustomerReturnsMapper extends BaseMapper<FbaCustomerReturns> {

  /**
   * FBA customer return列表、导出
   * @param page
   * @param paramCondition
   * @return
   */
  Page<FbaCustomerReturns> customPageList(@Param("page") Page page, @Param("paramCondition") FbaCustomerReturnsParam paramCondition);

  /**
   * 获取数量合计
   * @param paramCondition
   * @return
   */
  Integer getQuantity(@Param("paramCondition") FbaCustomerReturnsParam paramCondition);

  /**
   * 根据账号、站点、sku获取物料编码
   * @param paramCondition
   * @return
   */
  String getMaterial(@Param("paramCondition") FbaCustomerReturnsParam paramCondition);

  /**
   * 获取销售退货列表单据头数据
   * @param paramCondition
   * @return
   */
  List<FbaCustomerReturnsResult> getListHeader(@Param("paramCondition") FbaCustomerReturnsParam paramCondition);

  /**
   * 获取销售退货列表明细数据
   * @param paramCondition
   * @return
   */
  List<SalesReturnDetail> getDetailList(@Param("paramCondition") FbaCustomerReturnsResult paramCondition);

  /**
   * 更新销售退货源报告FBA_CUSTOMER_RETURNS的生成状态和单据编码
   * @param paramCondition
   */
  void updateSrcList(@Param("paramCondition") FbaCustomerReturnsResult paramCondition);

  /**
   * 销售退货列表明细刷listing
   */
  void updateDetailList();

  /**
   * 销售退货列表明细刷存档listing
   */
  void updateFileDetailList();

  /**
   * 根据组织编码和物料编码批量分配物料
   * @param detailIdList
   * @return
   */
  List<ZZDistributeMcms> assignMaterialList(@Param("detailIdList") List<String> detailIdList);

  /**
   * 批量审核
   * @param paramCondition
   * @return
   */
  Boolean verifyUpdateBatch(@Param("paramCondition") FbaCustomerReturnsParam paramCondition);

  /**
   * 批量作废
   * @param paramCondition
   */
  void rejectBatch(@Param("paramCondition") FbaCustomerReturnsParam paramCondition);

  /**
   * 平台为null的先去订单表刷平台
   */
  void refreshOrderPlatform(@Param("amazonOrders") String amazonOrders, @Param("amazonOrderDetail") String amazonOrderDetail);

  /**
   * 刷组织名称（Amazon_账号_站点）和仓库组织名称（Amazon_账号_站点_仓库）
   */
  void addOrgName();

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
   * 销售组织为空的，拿到FBA_SHIPMENT_SALES的AMAZON_ORDER_ID去匹配AMAZON_ORDERS的MERCHANT_ORDER_ID，再根据AMAZON_ORDER_DETAIL拿到销售平台、账号、站点（只刷平台为Amazon的）
   */
  void refreshOrderSalOrg(@Param("amazonOrders") String amazonOrders, @Param("amazonOrderDetail") String amazonOrderDetail);

  /**
   * 根据销售组织名称刷销售组织编码
   */
  void refreshSalOrgCode();

  /**
   * 拿到OrderId去结算报告里匹配销售组织名称（平台_账号_站点，只刷平台为Amazon的）
   */
  void refreshSalOrg(@Param("settlementDetail") String settlementDetail);

  /**
   * 获取没有刷上销售组织编码和库存组织编码的数据
   * @return
   */
  List<FbaCustomerReturns> remainList();

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
}
