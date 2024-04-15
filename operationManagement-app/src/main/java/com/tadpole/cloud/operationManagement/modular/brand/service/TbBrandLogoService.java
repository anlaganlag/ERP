package com.tadpole.cloud.operationManagement.modular.brand.service;


import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandLogo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌logo表 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-10-30
 */
public interface TbBrandLogoService extends IService<TbBrandLogo> {
    void save(TbBrandLogoParam param);

    TbBrandLogoResult queryById(Long id);

    List<TbBrandLogo> queryByBcId(Long bcId);
}

