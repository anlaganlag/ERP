package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EbmsComingInventory;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.EbmsComingInventoryMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EbmsComingInventoryParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IEbmsComingInventoryService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * <p>
 * EBMS在途库存 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
@Service
public class EbmsComingInventoryServiceImpl extends ServiceImpl<EbmsComingInventoryMapper, EbmsComingInventory> implements IEbmsComingInventoryService {

  @DataSource(name = "warehouse")
  @Override
  public PageResult<EbmsComingInventory> findPageBySpec(EbmsComingInventoryParam param) {
    return new PageResult<>(this.baseMapper.customPageList(PageFactory.defaultPage(), param));
  }

  @DataSource(name = "warehouse")
  @Override
  public String getQty(EbmsComingInventoryParam param) {
    return this.baseMapper.getQty(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public List<EbmsComingInventory> export(EbmsComingInventoryParam param) {
    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    IPage<EbmsComingInventory> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  public EbmsComingInventory checkOne(Wrapper<EbmsComingInventory> queryWrapper){
    return this.baseMapper.selectOne(queryWrapper);
  }

  @DataSource(name = "EBMS")
  @Override
  public List<EbmsComingInventory> getComingInventoryList() {
    return this.baseMapper.getComingInventoryList();
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData generateComingInventory(List<EbmsComingInventory> comingInventoryList, Map<String, String> orgCodeMap) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtil.date());
    calendar.add(Calendar.MONTH, -1);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date selectDate = calendar.getTime();

    QueryWrapper<EbmsComingInventory> qw = new QueryWrapper();
    qw.ge("STATICS_DATE", selectDate);

    if(this.count(qw) == 0){
      for (EbmsComingInventory comingInventory : comingInventoryList) {
        String org = "Amazon" + "_" + comingInventory.getShopName() + "_" + comingInventory.getSite();
        comingInventory.setOrg(org);
        comingInventory.setInventoryOrganizationCode(orgCodeMap.get(comingInventory.getOrg()));
        comingInventory.setOrgWarehouseName(org + "_仓库");
        comingInventory.setOrgWarehouseCode(orgCodeMap.get(comingInventory.getOrgWarehouseName()));
        comingInventory.setUniqueOrg(comingInventory.getShopName() + "_" + comingInventory.getSite() + "_" + comingInventory.getSku());
      }
      this.saveBatch(comingInventoryList, 100000);
    }
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData refreshOrgCode(){
    try {
      this.baseMapper.refreshOrgCode();
      return ResponseData.success();
    } catch (Exception e){
      log.error("在途库存刷组织编码失败!", e);
      e.printStackTrace();
      return ResponseData.error(String.valueOf(e));
    }
  }
}
