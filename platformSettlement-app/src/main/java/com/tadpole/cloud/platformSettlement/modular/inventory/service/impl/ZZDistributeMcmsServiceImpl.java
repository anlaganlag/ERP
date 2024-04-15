package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ZZDistributeMcms;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.ZZDistributeMcmsMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IZZDistributeMcmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
@Service
public class ZZDistributeMcmsServiceImpl extends ServiceImpl<ZZDistributeMcmsMapper, ZZDistributeMcms> implements IZZDistributeMcmsService {

  @Autowired
  private ZZDistributeMcmsMapper mapper;

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void add(ZZDistributeMcms param) {
    this.save(param);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void saveMatBatch(List<ZZDistributeMcms> param) {
    if(CollectionUtil.isNotEmpty(param)){
      this.saveBatch(param);
    }
  }
}
