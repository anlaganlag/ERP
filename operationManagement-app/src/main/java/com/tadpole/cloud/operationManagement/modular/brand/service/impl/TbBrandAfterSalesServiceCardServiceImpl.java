package com.tadpole.cloud.operationManagement.modular.brand.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandSlogan;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandAfterSalesServiceCard;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbBrandAfterSalesServiceCardMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandAfterSalesServiceCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;

import java.util.List;

/**
* <p>
* 品牌售后服务卡表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Service
public class TbBrandAfterSalesServiceCardServiceImpl extends ServiceImpl<TbBrandAfterSalesServiceCardMapper, TbBrandAfterSalesServiceCard> implements TbBrandAfterSalesServiceCardService {

  @DataSource(name = "stocking")
  @Override
  public void save(TbBrandAfterSalesServiceCardParam param) {
    TbBrandAfterSalesServiceCard entity = new TbBrandAfterSalesServiceCard();
    BeanUtils.copyProperties(param, entity);
    entity.setServiceCardCopyDate(DateUtil.date());

    if (StringUtils.isNotEmpty(param.getServiceCardCopy())) {
      entity.setServiceCardCopyAuthorName(LoginContext.me().getLoginUser().getName());
      entity.setServiceCardCopyAuthorCode(LoginContext.me().getLoginUser().getAccount());
      entity.setServiceCardPicturesDate(DateUtil.date());
    }

    if (StringUtils.isNotEmpty(param.getServiceCardPictures())) {
      entity.setServiceCardPicturesDate(DateUtil.date());
      entity.setServiceCardPicturesAuthorName(LoginContext.me().getLoginUser().getName());
      entity.setServiceCardPicturesAuthorCode(LoginContext.me().getLoginUser().getAccount());
    }
    //edit
    if (param.getId() != null) {
      this.baseMapper.updateById(entity);
    } else {

      this.baseMapper.insert(entity);
    }
  }

  @DataSource(name = "stocking")
  @Override
  public TbBrandAfterSalesServiceCardResult queryById(Long id) {
    TbBrandAfterSalesServiceCard entity = this.baseMapper.selectById(id);
    TbBrandAfterSalesServiceCardResult result = new TbBrandAfterSalesServiceCardResult();
    if (entity != null) {
      BeanUtils.copyProperties(entity, result);
    }
    return result;
  }

  @DataSource(name = "stocking")
  @Override
  public List<TbBrandAfterSalesServiceCard> queryByBcId(Long bcId) {
    QueryWrapper query = new QueryWrapper();
    query.eq("bc_id", bcId);
    return this.baseMapper.selectList(query);
  }
}
