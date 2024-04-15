package com.tadpole.cloud.operationManagement.modular.brand.service;


import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandAfterSalesServiceCard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌售后服务卡表 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-10-30
 */
public interface TbBrandAfterSalesServiceCardService extends IService<TbBrandAfterSalesServiceCard> {
  void save(TbBrandAfterSalesServiceCardParam param);
   TbBrandAfterSalesServiceCardResult queryById(Long id);
    List<TbBrandAfterSalesServiceCard> queryByBcId(Long bcId);
}

