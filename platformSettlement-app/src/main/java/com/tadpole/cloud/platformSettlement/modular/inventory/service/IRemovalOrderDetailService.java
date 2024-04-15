package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ErpOrgCode;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemovalOrderDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemovalOrderDetailParam;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 移除订单详情报告 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface IRemovalOrderDetailService extends IService<RemovalOrderDetail> {

  PageResult<RemovalOrderDetail> findPageBySpec(RemovalOrderDetailParam param);

  List<RemovalOrderDetail> export(RemovalOrderDetailParam param);

  String getQuantity(RemovalOrderDetailParam param);

  void RemovalOrderDetailSplit(RemovalOrderDetailParam param);

  void reject(RemovalOrderDetailParam param);

  void rejectBatch(List<RemovalOrderDetailParam> params);

  void verify(RemovalOrderDetailParam param);

  void verifyBatch(List<RemovalOrderDetailParam> params);

  void toDisposeRemoveList(RemovalOrderDetailParam param);

  String getMaterial(RemovalOrderDetailParam param);

  void assignMaterial(RemovalOrderDetailParam param);

  List<ErpOrgCode> getErpCode();

  void syncErpCode(List<ErpOrgCode> codes);

  void updateDeptDetailList() throws IOException;

  void updateFileDeptDetailList() throws IOException;

  void editSites(RemovalOrderDetailParam param);

  void editSite(RemovalOrderDetailParam param);

}
