package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
public interface ISubjectService extends IService<ZZDistributeMcms> {

  void add(ZZDistributeMcms param);
}
