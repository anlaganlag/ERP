package com.tadpole.cloud.operationManagement.modular.brand.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandLogo;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandSlogan;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandSloganParam;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbBrandSloganMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandSloganService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;

import java.util.List;

/**
* <p>
* 品牌sloga表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Service
public class TbBrandSloganServiceImpl extends ServiceImpl<TbBrandSloganMapper, TbBrandSlogan> implements TbBrandSloganService {

  @DataSource(name = "stocking")
  @Override
  public void save(TbBrandSloganParam param){
  TbBrandSlogan entity=new TbBrandSlogan();
  BeanUtils.copyProperties(param,entity);
    //edit
    if (param.getId() != null) {
      entity.setUpdateName(LoginContext.me().getLoginUser().getName());
      entity.setUpdateTime(DateUtil.date());
      this.baseMapper.updateById(entity);
    } else {
      entity.setCreateName(LoginContext.me().getLoginUser().getName());
      entity.setSysPerCode(LoginContext.me().getLoginUser().getAccount());
      this.baseMapper.insert(entity);
    }
  }

  @DataSource(name = "stocking")
  @Override
  public TbBrandSloganResult queryById(Long id) {
    TbBrandSlogan entity = this.baseMapper.selectById(id);
    TbBrandSloganResult result = new TbBrandSloganResult();
    if (entity != null) {
      BeanUtils.copyProperties(entity, result);
    }
    return result;
  }

  @DataSource(name = "stocking")
  @Override
  public List<TbBrandSlogan> queryByBcId(Long bcId) {
    QueryWrapper query = new QueryWrapper();
    query.eq("bc_id", bcId);
    return this.baseMapper.selectList(query);
  }
}
