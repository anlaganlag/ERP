package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveTrackDTO;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalShipmentDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalShipmentDetailParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalShipmentDetailResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.OverseasInWarehouseFBAResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 移除货件详情报告 服务类
 * </p>
 *
 * @author gal
 * @since 2022-04-15
 */
public interface IRemovalShipmentDetailService extends IService<RemovalShipmentDetail> {

  /**
   * Removal Shipment Detail查询列表
   * @param param
   * @return
   */
  PageResult<RemovalShipmentDetailResult> findPageBySpec(RemovalShipmentDetailParam param);

  /**
   * Removal Shipment Detail导出
   * @param param
   * @return
   */
  List<RemovalShipmentDetailResult> export(RemovalShipmentDetailParam param);

  /**
   * Removal Shipment Detail获取数量
   * @param param
   * @return
   */
  String getQuantity(RemovalShipmentDetailParam param);

  void reject(RemovalShipmentDetailParam param);

  String getMaterial(RemovalShipmentDetailParam param);

  void assignMaterial(RemovalShipmentDetailParam param);

  ResponseData assignBatchMaterial(List<ZZDistributeMcms> assignList);

  List<RemovalShipmentDetailParam> orgList();

  Boolean orgBatch(List<RemovalShipmentDetailParam> params);

  void verify(RemovalShipmentDetailParam param);

  Boolean verifyUpdateBatch(RemovalShipmentDetailParam param);

  void rejectBatch(List<RemovalShipmentDetailParam> params);

  ResponseData toDisposeRemoveList(RemovalShipmentDetailParam param);

  /**
   * 生成销毁移除跟踪
   * @return
   */
  ResponseData generateTrack();

  /**
   * 销毁移除跟踪列表
   * @param param
   * @return
   */
  PageResult<DisposeRemoveTrackDTO> trackList(RemovalShipmentDetailParam param);

  /**
   * 导出销毁移除跟踪列表
   * @param param
   * @return
   */
  List<DisposeRemoveTrackDTO> trackExport(RemovalShipmentDetailParam param);

  /**
   * 销毁移除跟踪表获取汇总数量
   * @param param
   * @return
   */
  ResponseData getTrackQuantity(RemovalShipmentDetailParam param);

  /**
   * 销毁移除跟踪列表刷listing
   * @return
   */
  ResponseData refreshListing();

  /**
   * 订单类型下拉
   * @return
   */
  ResponseData orderTypeSelect();

  /**
   * 订单状态下拉
   * @return
   */
  ResponseData orderStatusSelect();

  /**
   * 刷新RemovalShipmentDetail站点
   * @return
   */
  ResponseData refreshSite();

  /**
   * 生成销毁移除成本月分摊
   * @param param
   */
  void generateRemovalShipmentMonShare(RemovalShipmentDetailParam param);

  /**
   * 刷组织
   * @return
   */
  ResponseData addNewOrg();

  /**
   * 获取FBA退海外仓数据
   * @return
   */
  List<OverseasInWarehouseFBAResult> generateInWarehouseByFBA();

  /**
   * 更新入库海外仓状态
   * @return
   */
  void updateGenerateHwc(List<BigDecimal> params);

  /**
   * 每天定时获取Removal Shipment Detail数据
   * @return
   */
  ResponseData generateRemovalShipmentDetail();
}
