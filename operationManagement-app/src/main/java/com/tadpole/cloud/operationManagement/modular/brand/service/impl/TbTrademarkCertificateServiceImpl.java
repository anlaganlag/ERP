package com.tadpole.cloud.operationManagement.modular.brand.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbTrademarkCertificate;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbTrademarkCertificateMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbTrademarkCertificateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;

import java.util.List;

import static cn.hutool.json.XMLTokener.entity;

/**
* <p>
* 商标证书管理表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-27
*/
@Service
public class TbTrademarkCertificateServiceImpl extends ServiceImpl<TbTrademarkCertificateMapper, TbTrademarkCertificate> implements TbTrademarkCertificateService {

  @DataSource(name = "stocking")
  @Override
  public void save(TbTrademarkCertificateParam param) {
    TbTrademarkCertificate entity = new TbTrademarkCertificate();
    BeanUtils.copyProperties(param, entity);
    //edit
    if(param.getId()!=null){
      entity.setUpdateName(LoginContext.me().getLoginUser().getName());
      entity.setUpdateTime(DateUtil.date());
      if (param.getHandoverStatus() !=null && param.getHandoverStatus()==2){
        entity.setHandoverDate(DateUtil.date());

      }
      this.baseMapper.updateById(entity);
    }else{
      entity.setCreateName(LoginContext.me().getLoginUser().getName());
      entity.setCreateTime(DateUtil.date());
      this.baseMapper.insert(entity);
    }

  }



  @DataSource(name = "stocking")
  @Override
  public TbTrademarkCertificateResult queryById(Long id) {
    TbTrademarkCertificate entity = this.baseMapper.selectById(id);
    TbTrademarkCertificateResult result = new TbTrademarkCertificateResult();
    if (entity != null) {
      BeanUtils.copyProperties(entity, result);
    }
    return result;
  }

  @DataSource(name = "stocking")
  @Override
  public List<TbTrademarkCertificate> queryByBrand(String salesBrand) {
  /*  QueryWrapper query=new QueryWrapper();
    query.eq("trade_name",salesBrand);*/
    return this.baseMapper.queryByBrand(salesBrand);


  }
  @DataSource(name = "stocking")
  @Override
  public Page<TbTrademarkCertificateResult> getPage(TbTrademarkCertificatePageParam param) {
    QueryWrapper<TbTrademarkCertificate> query = new QueryWrapper<>();
    if (param.getTradeNames() != null && param.getTradeNames().size() > 0) {
      query.in("trade_name", param.getTradeNames());
    }
    if (StringUtils.isNotEmpty(param.getRegisterNumber())) {
      query.eq("register_number", param.getRegisterNumber());
    }
    if (StringUtils.isNotEmpty(param.getRegisterCountry())) {
      query.eq("register_country", param.getRegisterCountry());
    }
    return this.baseMapper.selectPage(param.getPageContext(), query);

  }
}
