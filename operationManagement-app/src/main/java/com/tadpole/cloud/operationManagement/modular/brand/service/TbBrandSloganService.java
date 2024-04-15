package com.tadpole.cloud.operationManagement.modular.brand.service;


import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandSlogan;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandSloganParam;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌sloga表 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-10-30
 */
public interface TbBrandSloganService extends IService<TbBrandSlogan> {
  void save(TbBrandSloganParam param);
   TbBrandSloganResult queryById(Long id);
    List<TbBrandSlogan> queryByBcId(Long bcId);
}

