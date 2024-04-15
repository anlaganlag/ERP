package com.tadpole.cloud.operationManagement.modular.brand.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandAuthorization;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbBrandAuthorizationMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandAuthorizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;

import java.time.LocalDate;
import java.util.List;

/**
* <p>
* 品牌授权表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Service
public class TbBrandAuthorizationServiceImpl extends ServiceImpl<TbBrandAuthorizationMapper, TbBrandAuthorization> implements TbBrandAuthorizationService {

  @DataSource(name = "stocking")
  @Override
  public void save(TbBrandAuthorizationParam param) {
    TbBrandAuthorization entity = new TbBrandAuthorization();
    BeanUtils.copyProperties(param, entity);
    //edit
    if (param.getId() != null) {
      entity.setUpdateName(LoginContext.me().getLoginUser().getName());
      entity.setUpdateTime(DateUtil.date());
      this.baseMapper.updateById(entity);
    } else {
      entity.setCreateName(LoginContext.me().getLoginUser().getName());
      this.baseMapper.insert(entity);
    }

  }

  @DataSource(name = "stocking")
  @Override
  public TbBrandAuthorizationResult queryById(Long id) {
    TbBrandAuthorization entity = this.baseMapper.selectById(id);
    TbBrandAuthorizationResult result = new TbBrandAuthorizationResult();
    if (entity != null) {
      BeanUtils.copyProperties(entity, result);
    }
    return result;
  }

  @DataSource(name = "stocking")
  @Override
  public List<TbBrandAuthorization> queryByBcId(Long bcId) {
    QueryWrapper query = new QueryWrapper();
    query.eq("bc_id", bcId);
    return this.baseMapper.selectList(query);
  }
  @DataSource(name = "stocking")
  @Override
  public void delete(Long id) {
    this.baseMapper.deleteById(id);
  }
}
