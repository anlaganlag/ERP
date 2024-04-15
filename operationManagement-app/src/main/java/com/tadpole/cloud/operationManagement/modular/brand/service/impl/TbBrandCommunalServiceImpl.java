package com.tadpole.cloud.operationManagement.modular.brand.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandCommunal;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbBrandCommunalMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandCommunalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
/**
* <p>
* 品牌管理主表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Service
public class TbBrandCommunalServiceImpl extends ServiceImpl<TbBrandCommunalMapper, TbBrandCommunal> implements TbBrandCommunalService {

  @DataSource(name = "stocking")
  @Override
  public Page<TbBrandCommunalResult> getPage(TbBrandCommunalParam param) {
    /*QueryWrapper query = new QueryWrapper();
    if (param.getSalesBrands()!=null && param.getSalesBrands().size()>0) {
      query.in("sales_brand", param.getSalesBrands());
    }
    return this.baseMapper.selectPage(param.getPageContext(), query);*/
    return this.baseMapper.getPage(param.getPageContext(),param);
  }

  @DataSource(name = "stocking")
  @Override
  public void save(TbBrandCommunalParam param) {
    TbBrandCommunal entity = new TbBrandCommunal();
    BeanUtils.copyProperties(param, entity);
    //edit
    if (param.getId() != null) {
      this.baseMapper.updateById(entity);
    } else {
      entity.setSysPerName(LoginContext.me().getLoginUser().getName());
      entity.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
      this.baseMapper.insert(entity);

    }
  }

  @DataSource(name = "stocking")
  @Override
  public TbBrandCommunalResult queryById(Long id) {
    TbBrandCommunal entity = this.baseMapper.selectById(id);
    TbBrandCommunalResult result = new TbBrandCommunalResult();
    if (entity != null) {
      BeanUtils.copyProperties(entity, result);
    }
    return result;
  }
}
