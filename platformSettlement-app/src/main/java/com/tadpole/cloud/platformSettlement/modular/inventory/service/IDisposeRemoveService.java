package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemove;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveDetail;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.DisposeRemoveParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.DisposeRemoveResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 销毁移除列表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface IDisposeRemoveService extends IService<DisposeRemove> {

  PageResult<DisposeRemoveResult> findPageBySpec(DisposeRemoveParam param);

  String getQuantity(DisposeRemoveParam param);

  List<DisposeRemoveResult> export(DisposeRemoveParam param);

  void reject(DisposeRemoveParam param);

  void rejectBatch(List<DisposeRemoveParam> params);

  void syncErp(DisposeRemoveParam param) throws IOException;

  List<DisposeRemoveDetail> getSyncList(DisposeRemoveParam param);

  void updateDetailList() throws IOException;

  void updateFileDetailList() throws IOException;

  List<DisposeRemoveResult> emailList();

  void updateSyncStatus();

  void timerSync() throws IOException;

  ResponseData editMat(DisposeRemoveParam param);

  void updateCanSyncNormal();
}
