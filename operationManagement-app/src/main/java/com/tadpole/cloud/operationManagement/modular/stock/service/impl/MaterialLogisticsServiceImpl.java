package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.EntMaterialLogistics;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.MaterialLogisticsMapper;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SysMaterialParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SysMaterialResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.MaterialLogisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 物料运输方式 服务实现类
 *
 * @author gal
 * @since 2021-07-27
 */
@Service
public class MaterialLogisticsServiceImpl extends ServiceImpl<MaterialLogisticsMapper, EntMaterialLogistics> implements MaterialLogisticsService {

  @DataSource(name = "stocking")
  @Override
  public PageResult<SysMaterialResult> findPageBySpec(SysMaterialParam param) {

    LoginContext current= SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    param.setOperator(currentUser.getAccount());

    Page pageContext = param.getPageContext();
    IPage<SysMaterialResult> page = this.baseMapper.customPageList(pageContext,param);
    return new PageResult<>(page);
  }

  @DataSource(name = "stocking")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void insertBatch(List<EntMaterialLogistics> materialLogisticsList) {

    LoginContext current= SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    for (EntMaterialLogistics item:materialLogisticsList){
      item.setCreateUser(currentUser.getAccount());
      item.setStatus("1");


      Date now = new Date();
      SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式

      item.setCreateDate(dateFormat.format(now));




    }
    this.saveBatch(materialLogisticsList);
  }

  @DataSource(name = "stocking")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateBatch(List<EntMaterialLogistics> materialLogisticsList) {
    this.updateBatchById(materialLogisticsList);
  }

  @DataSource(name = "stocking")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteBatch(List<String> list) {
    for (List<String> ids : CollUtil.split(list,900)) {
      this.baseMapper.deleteBatch(ids);
    }
  }


  // 根据主键id插入或者更新
  @DataSource(name = "stocking")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void insertOrUpdateBatchById(List<EntMaterialLogistics> MaterialLogisticsList) {
    this.saveOrUpdateBatch(MaterialLogisticsList);
  }

  // 根据主键id插入或者更新
  @DataSource(name = "stocking")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void analysis(SysMaterialParam param) {
    LoginContext current= SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    param.setOperator(currentUser.getAccount());
    this.baseMapper.analysis(param);
  }

  // 根据主键id插入或者更新
  //  @DataSource(name = "replenish")
  //  @Override
  //  @Transactional(rollbackFor = Exception.class)
  //  public void insertOrUpdateBatchByCustom(List<SysMaterialParam> MaterialLogisticsList) {
  //    this.baseMapper.insertOrUpdateBatchByCustom(MaterialLogisticsList);
  //  }

  private Page getPageContext() {
    return PageFactory.defaultPage();
  }
}
