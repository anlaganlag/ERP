package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustAdd;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustAddDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InventoryAdjustAddParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.InventoryAdjustAddResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * AZ库存调整列表(增加) 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface IInventoryAdjustAddService extends IService<InventoryAdjustAdd> {

  PageResult<InventoryAdjustAddResult> findPageBySpec(InventoryAdjustAddParam param);

  String getQuantity(InventoryAdjustAddParam param);

  List<InventoryAdjustAddResult> export(InventoryAdjustAddParam param);

  void reject(InventoryAdjustAddParam param);

  void rejectBatch(List<InventoryAdjustAddParam> params);

  void syncErp(InventoryAdjustAddParam param) throws IOException;

  List<InventoryAdjustAddDetail> getSyncList(InventoryAdjustAddParam param);

  void updateAddDetailList() throws IOException;

  void updateFileAddDetailList() throws IOException;

  List<InventoryAdjustAddResult> emailList();

  void updateSyncStatus();

  void timerSync() throws IOException;

  ResponseData editMat(InventoryAdjustAddParam param);

  void updateCanSyncNormal();
}
