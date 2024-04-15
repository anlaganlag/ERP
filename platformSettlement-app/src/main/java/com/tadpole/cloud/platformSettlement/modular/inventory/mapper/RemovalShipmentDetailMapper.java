package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.RemovalShipmentCostMonthlyShare;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveTrackDTO;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalShipmentDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalShipmentDetailParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveTrackResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveTrackTotalResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.OverseasInWarehouseFBAResult;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalShipmentDetailResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* <p>
* 移除货件详情报告 Mapper 接口
* </p>
*
* @author gal
* @since 2022-04-15
*/
public interface RemovalShipmentDetailMapper extends BaseMapper<RemovalShipmentDetail> {

  /**
   * Removal Shipment Detail查询列表
   * @param page
   * @param paramCondition
   * @return
   */
  Page<RemovalShipmentDetailResult> customPageList(@Param("page") Page page, @Param("paramCondition") RemovalShipmentDetailParam paramCondition);

  /**
   * 销毁移除跟踪列表、导出
   * @param page
   * @param paramCondition
   * @return
   */
  Page<DisposeRemoveTrackDTO> trackPageList(@Param("page") Page page, @Param("paramCondition") RemovalShipmentDetailParam paramCondition);

  /**
   * Removal Shipment Detail获取数量
   * @param paramCondition
   * @return
   */
  String getQuantity(@Param("paramCondition") RemovalShipmentDetailParam paramCondition);


  String getMaterial(
      @Param("paramCondition") RemovalShipmentDetailParam paramCondition);

  Boolean verifyUpdateBatch(@Param("paramCondition") RemovalShipmentDetailParam paramCondition);


  void rejectBatch(@Param("paramCondition") RemovalShipmentDetailParam paramCondition);

  /**
   * 获取销毁移除列表单据头数据
   * @param paramCondition
   * @return
   */
  List<RemovalShipmentDetailResult> getListHeader(@Param("paramCondition") RemovalShipmentDetailParam paramCondition);

  /**
   * 获取销毁移除列表明细数据
   * @param paramCondition
   * @return
   */
  List<DisposeRemoveDetail> getDetailList(@Param("paramCondition") RemovalShipmentDetailResult paramCondition);

  /**
   * 更新Removal Shipment Detail生成状态
   * @param paramCondition
   */
  void updateSrcDetailList(@Param("paramCondition") RemovalShipmentDetailResult paramCondition);

  /**
   * 刷listings
   */
  void updateDetailList();

  /**
   * 刷存档listings
   */
  void updateFileDetailList();


  List<RemovalShipmentDetailParam> orgList();

  Boolean orgBatch(@Param("verifyList") List<RemovalShipmentDetailParam> verifyList);

  List<ZZDistributeMcms> assignMaterialList(@Param("detailIdList") List<String> detailIdList);

  /**
   * 销毁移除跟踪表刷移除货件表数据
   * @param startDate
   * @param endDate
   */
  void updateRemovalShipmentToTrace(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

  /**
   * 销毁移除跟踪表刷每月移除订单表数据
   * @param startDate
   * @param endDate
   */
  void updateRemovalDetailToTrace(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

  /**
   * 销毁移除跟踪表反向推导每月移除订单表数据，删除无效数据
   */
  void deleteDisposeRemoveTrack();

  /**
   * 销毁移除跟踪表刷listing
   */
  void updateTrackList();

  /**
   * 销毁移除跟踪表刷listing(存档)
   */
  void updateFileTrackList();

  /**
   * 更新移除订单表生成销毁移除跟踪表状态
   * @param startDate
   * @param endDate
   */
  void updateRemovalDetailStatus(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

  /**
   * 刷新RemovalShipmentDetail站点
   */
  void refreshSite();

  /**
   * 销毁移除跟踪表获取汇总数量
   * @param paramCondition
   * @return
   */
  DisposeRemoveTrackTotalResult getTrackQuantity(@Param("paramCondition") RemovalShipmentDetailParam paramCondition);

  /**
   * 订单类型下拉
   * @return
   */
  List<Map<String, Object>> orderTypeSelect();

  /**
   * 订单状态下拉
   * @return
   */
  List<Map<String, Object>> orderStatusSelect();

  /**
   * 生成销毁移除成本月分摊
   * @param paramCondition
   * @param rpNewAveragePrice
   * @param rpSpotExchangeRate
   * @param userName
   * @return
   */
  List<RemovalShipmentCostMonthlyShare> generateRemovalShipmentMonShare(@Param("paramCondition") RemovalShipmentDetailParam paramCondition,
                                                                        @Param("rpNewAveragePrice") String rpNewAveragePrice,
                                                                        @Param("rpSpotExchangeRate") String rpSpotExchangeRate,
                                                                        @Param("userName") String userName);

  /**
   * 刷组织名称（Amazon_账号_站点）和仓库组织名称（Amazon_账号_站点_仓库）
   */
  void addOrgName();

  /**
   * 根据组织刷库存组织编码
   */
  void refreshOrgCode();

  /**
   * 根据仓库组织名称获取仓库组织编码
   */
  void refreshWareOrgCode();

  /**
   * 获取FBA退海外仓数据
   */
  List<OverseasInWarehouseFBAResult> generateInWarehouseByFBA();

  /**
   * 更新入库海外仓状态
   */
  void updateGenerateHwc(List<BigDecimal> params);

  /**
   * 每天定时获取Removal Shipment Detail数据
   */
  void generateRemovalShipmentDetail();

  /**
   * 把remove shipment detail解析出来的站点赋值给LAST_SYS_SITE
   */
  void updateShipmentLastSysSite();

  /**
   * 取RemovalOrderDetail更新RemovalShipmentDetail的站点
   * @param startDate
   * @param endDate
   */
  void updateShipmentDetailSite(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

  /**
   * 获取销毁移除跟踪表刷移除货件表多站点数据
   * @return
   */
  List<DisposeRemoveTrackResult> getMoreSiteTrace();
}
