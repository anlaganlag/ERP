package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemoveMain;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.RemoveMainParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.RemoveMainResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * Amazon销毁移除主表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
public interface IRemoveMainService extends IService<RemoveMain> {

  PageResult<RemoveMainResult> findPageBySpec(RemoveMainParam param);

  List<RemoveMainResult> export(RemoveMainParam param);

  String getQuantity(RemoveMainParam param);

  void reject(RemoveMainParam param);

  void rejectBatch(List<RemoveMainParam> params);

  void verify(RemoveMainParam param);

  String getMaterial(RemoveMainParam param);

  void assignMaterial(RemoveMainParam param);

  ResponseData toDisposeRemoveList(RemoveMainParam param);

  List<RemoveMainParam> orgList();

  Boolean orgBatch(List<RemoveMainParam> params);

  Boolean verifyUpdateBatch(RemoveMainParam param);
}
