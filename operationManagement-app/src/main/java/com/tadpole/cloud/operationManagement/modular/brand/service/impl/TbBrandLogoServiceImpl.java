package com.tadpole.cloud.operationManagement.modular.brand.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandAuthorization;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandLogo;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbBrandLogoMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandLogoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;

import java.util.List;

/**
* <p>
* 品牌logo表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Service
public class TbBrandLogoServiceImpl extends ServiceImpl<TbBrandLogoMapper, TbBrandLogo> implements TbBrandLogoService {

  @DataSource(name = "stocking")
  @Override
  public void save(TbBrandLogoParam param) {
    TbBrandLogo entity = new TbBrandLogo();
    BeanUtils.copyProperties(param, entity);
    //edit
    if (param.getId() != null) {
      entity.setSysLastUpdDate(DateUtil.date());
      this.baseMapper.updateById(entity);
    } else {
      entity.setCreateName(LoginContext.me().getLoginUser().getName());
      this.baseMapper.insert(entity);
    }
  }

  @DataSource(name = "stocking")
  @Override
  public TbBrandLogoResult queryById(Long id) {
    TbBrandLogo entity = this.baseMapper.selectById(id);
    TbBrandLogoResult result = new TbBrandLogoResult();
    if (entity != null) {
      BeanUtils.copyProperties(entity, result);
    }
    return result;
  }

  @DataSource(name = "stocking")
  @Override
  public List<TbBrandLogo> queryByBcId(Long bcId) {
    QueryWrapper query = new QueryWrapper();
    query.eq("bc_id", bcId);
    return this.baseMapper.selectList(query);
  }
}
