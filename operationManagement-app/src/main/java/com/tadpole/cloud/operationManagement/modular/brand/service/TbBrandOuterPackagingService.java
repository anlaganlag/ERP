package com.tadpole.cloud.operationManagement.modular.brand.service;


import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandOuterPackaging;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌外包装表 服务类
 * </p>
 *
 * @author S20190161
 * @since 2023-10-30
 */
public interface TbBrandOuterPackagingService extends IService<TbBrandOuterPackaging> {
    void save(TbBrandOuterPackagingParam param);

    TbBrandOuterPackagingResult queryById(Long id);

    List<TbBrandOuterPackaging> queryByBcId(Long bcId);
}

