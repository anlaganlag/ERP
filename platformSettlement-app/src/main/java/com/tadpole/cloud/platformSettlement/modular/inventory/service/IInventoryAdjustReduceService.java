package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustReduce;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustReduceDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.InventoryAdjustReduceParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.InventoryAdjustReduceResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * AZ库存调整列表(减少) 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface IInventoryAdjustReduceService extends IService<InventoryAdjustReduce> {

  PageResult<InventoryAdjustReduceResult> findPageBySpec(InventoryAdjustReduceParam param);

  String getQuantity(InventoryAdjustReduceParam param);

  List<InventoryAdjustReduceResult> export(InventoryAdjustReduceParam param);

  void reject(InventoryAdjustReduceParam param);

  void rejectBatch(List<InventoryAdjustReduceParam> params);

  void syncErp(InventoryAdjustReduceParam param) throws IOException;

  List<InventoryAdjustReduceDetail> getSyncList(InventoryAdjustReduceParam param);

  void updateReduceDetailList() throws IOException;

  void updateFileReduceDetailList() throws IOException;

  List<InventoryAdjustReduceResult> emailList();

  void updateSyncStatus();

  void timerSync() throws IOException;

  ResponseData editMat(InventoryAdjustReduceParam param);

  void updateCanSyncNormal();
}
