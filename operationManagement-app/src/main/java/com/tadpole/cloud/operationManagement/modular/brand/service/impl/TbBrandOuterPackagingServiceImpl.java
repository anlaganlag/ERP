package com.tadpole.cloud.operationManagement.modular.brand.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandAfterSalesServiceCard;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandOuterPackaging;
import com.tadpole.cloud.operationManagement.modular.brand.mapper.TbBrandOuterPackagingMapper;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandOuterPackagingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;

import java.util.List;

/**
* <p>
* 品牌外包装表 服务实现类
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Service
public class TbBrandOuterPackagingServiceImpl extends ServiceImpl<TbBrandOuterPackagingMapper, TbBrandOuterPackaging> implements TbBrandOuterPackagingService {

  @DataSource(name = "stocking")
  @Override
  public void save(TbBrandOuterPackagingParam param) {
    TbBrandOuterPackaging entity = new TbBrandOuterPackaging();
    BeanUtils.copyProperties(param, entity);

    //edit
    if (param.getId() != null) {
      if (StringUtils.isNotEmpty(param.getPackagingPictures())){
        entity.setPackagingCopyDate(DateUtil.date());
        entity.setPackagingCopyAuthorName(LoginContext.me().getLoginUser().getName());
        entity.setPackagingCopyAuthorCode(LoginContext.me().getLoginUser().getAccount());
      }
      this.baseMapper.updateById(entity);
    } else {

      entity.setPackagingPicturesDate(DateUtil.date());
      entity.setPackagingPicturesAuthorCode(LoginContext.me().getLoginUser().getAccount());
      entity.setPackagingPicturesAuthorName(LoginContext.me().getLoginUser().getName());
      this.baseMapper.insert(entity);
    }
  }


  @DataSource(name = "stocking")
  @Override
  public TbBrandOuterPackagingResult queryById(Long id) {
    TbBrandOuterPackaging entity = this.baseMapper.selectById(id);
    TbBrandOuterPackagingResult result = new TbBrandOuterPackagingResult();
    if (entity != null) {
      BeanUtils.copyProperties(entity, result);
    }
    return result;
  }

  @DataSource(name = "stocking")
  @Override
  public List<TbBrandOuterPackaging> queryByBcId(Long bcId) {
    QueryWrapper query = new QueryWrapper();
    query.eq("bc_id", bcId);
    return this.baseMapper.selectList(query);
  }
}
