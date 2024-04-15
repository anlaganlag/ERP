package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ErpOrgCode;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalOrderDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalOrderDetailParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemovalOrderDetailResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 移除订单详情报告 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface RemovalOrderDetailMapper extends BaseMapper<RemovalOrderDetail> {

  Page<RemovalOrderDetail> customPageList(@Param("page") Page page, @Param("paramCondition") RemovalOrderDetailParam paramCondition);

  String getQuantity(@Param("paramCondition") RemovalOrderDetailParam paramCondition);

  void InsertRemoveMain(@Param("paramCondition") RemovalOrderDetailParam paramCondition);

  void InsertRemoveMainDetial(@Param("paramCondition") RemovalOrderDetailParam paramCondition);

  List<RemovalOrderDetailResult> getListHeader(@Param("paramCondition") RemovalOrderDetailParam paramCondition);

  List<DisposeRemoveDetail> getDetailList(@Param("paramCondition") RemovalOrderDetailResult paramCondition);

  void updateSrcList(@Param("paramCondition") RemovalOrderDetailResult paramCondition);

  void updateDetailList(@Param("paramCondition") RemovalOrderDetailResult paramCondition);

  String getMaterial(@Param("paramCondition") RemovalOrderDetailParam paramCondition);

  void syncErpCode(@Param("paramCondition") ErpOrgCode code);

  List<ErpOrgCode> getErpCode();

  void updateDeptDetailList();

  void updateFileDeptDetailList();

  void editSites(@Param("paramCondition") RemovalOrderDetailParam paramCondition);
}
