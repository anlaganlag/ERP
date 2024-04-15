package com.tadpole.cloud.operationManagement.modular.brand.service;


import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandTrademarkRegis;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌商标注册表 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-10-24
 */
public interface TbBrandTrademarkRegisService extends IService<TbBrandTrademarkRegis> {
  void save(TbBrandTrademarkRegisParam param);
  TbBrandTrademarkRegisResult queryById(Long id);

    void addOverClass(TbBrandTrademarkRegisOverParam param);
}

