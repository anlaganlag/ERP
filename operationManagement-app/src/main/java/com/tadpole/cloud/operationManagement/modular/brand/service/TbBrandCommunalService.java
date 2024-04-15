package com.tadpole.cloud.operationManagement.modular.brand.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandCommunal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌管理主表 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-10-30
 */
public interface TbBrandCommunalService extends IService<TbBrandCommunal> {
  void save(TbBrandCommunalParam param);
   TbBrandCommunalResult queryById(Long id);
    Page<TbBrandCommunalResult> getPage(TbBrandCommunalParam param);
}

