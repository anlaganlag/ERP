package com.tadpole.cloud.operationManagement.modular.brand.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.exp.RequestEmptyException;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandTrademarkRegis;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbBrandTrademarkRegisMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandTrademarkRegisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
/**
* <p>
* 品牌商标注册表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-24
*/
@Service
public class TbBrandTrademarkRegisServiceImpl extends ServiceImpl<TbBrandTrademarkRegisMapper, TbBrandTrademarkRegis> implements TbBrandTrademarkRegisService {


  @DataSource(name = "stocking")
  @Override
  public TbBrandTrademarkRegisResult queryById(Long id) {
    TbBrandTrademarkRegis entity = this.baseMapper.selectById(id);
    TbBrandTrademarkRegisResult result = new TbBrandTrademarkRegisResult();
    if (entity != null) {
      BeanUtils.copyProperties(entity, result);
    }
    return result;
  }


  @DataSource(name = "stocking")
  @Override
  public void save(TbBrandTrademarkRegisParam param) {
    TbBrandTrademarkRegis entity = new TbBrandTrademarkRegis();
    BeanUtils.copyProperties(param, entity);
    //edit
    if (entity.getId() != null) {
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
  public void addOverClass(TbBrandTrademarkRegisOverParam param) {
    QueryWrapper<TbBrandTrademarkRegis> query = new QueryWrapper();
    query.eq("bt_id", param.getBtId())
            .eq("register_country", param.getRegisterCountry())
            .eq("trademark_category", param.getTrademarkCategory())
    ;
    if (this.baseMapper.selectCount(query) > 0) {
      throw new RequestEmptyException("【商标名称、商标类型、注册国家、商标大类 已存在】");
    }
    TbBrandTrademarkRegis entity = new TbBrandTrademarkRegis();
    BeanUtils.copyProperties(param, entity);
    entity.setCreateName(LoginContext.me().getLoginUser().getName());
    this.baseMapper.insert(entity);
  }
}
