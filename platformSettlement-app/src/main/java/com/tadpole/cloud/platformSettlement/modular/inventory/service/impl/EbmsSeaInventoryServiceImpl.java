package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EbmsSeaInventory;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.EbmsSeaInventoryMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EbmsSeaInventoryParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IEbmsSeaInventoryService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * <p>
 * 海外库存 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
@Service
public class EbmsSeaInventoryServiceImpl extends ServiceImpl<EbmsSeaInventoryMapper, EbmsSeaInventory> implements IEbmsSeaInventoryService {

  @DataSource(name = "warehouse")
  @Override
  public PageResult<EbmsSeaInventory> findPageBySpec(EbmsSeaInventoryParam param) {
    return new PageResult<>(this.baseMapper.customPageList(PageFactory.defaultPage(), param));
  }

  @DataSource(name = "warehouse")
  @Override
  public String getQty(EbmsSeaInventoryParam param) {
    return this.baseMapper.getQty(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public List<EbmsSeaInventory> export(EbmsSeaInventoryParam param) {
    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    IPage<EbmsSeaInventory> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  public EbmsSeaInventory checkOne(Wrapper<EbmsSeaInventory> queryWrapper){
    return this.baseMapper.selectOne(queryWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData generateOverseasWarehouse(List<OverseasWarehouseManage> overseasWarehouseList, Map<String, String> orgCodeMap){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtil.date());
    calendar.add(Calendar.MONTH, -1);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date selectDate = calendar.getTime();

    QueryWrapper<EbmsSeaInventory> qw = new QueryWrapper();
    qw.ge("BALANCE_DATE", selectDate);

    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    Date balanceDate = calendar.getTime();
    if(this.count(qw) == 0){
      List<EbmsSeaInventory> seaInventoryList = new ArrayList<>();
      for (OverseasWarehouseManage overseasWarehouse : overseasWarehouseList) {
        EbmsSeaInventory seaInventory = new EbmsSeaInventory();
        seaInventory.setPlatform(overseasWarehouse.getPlatform());
        seaInventory.setShopName(overseasWarehouse.getSysShopsName());
        seaInventory.setSite(overseasWarehouse.getSysSite());
        seaInventory.setWarehouseName(overseasWarehouse.getWarehouseName());
        seaInventory.setSku(overseasWarehouse.getSku());
        seaInventory.setFnsku(overseasWarehouse.getFnSku());
        seaInventory.setMaterialCode(overseasWarehouse.getMaterialCode());
        seaInventory.setAccountNumber(overseasWarehouse.getInventoryQuantity());
        seaInventory.setComingNumber(overseasWarehouse.getComeQuantity());
        seaInventory.setBalanceDate(balanceDate);
        seaInventory.setEtlTime(DateUtil.date());
        seaInventory.setParentId(overseasWarehouse.getId());

        String org = overseasWarehouse.getPlatform() + "_" + overseasWarehouse.getSysShopsName() + "_" + overseasWarehouse.getSysSite();
        seaInventory.setOrg(org);
        seaInventory.setInventoryOrganizationCode(orgCodeMap.get(seaInventory.getOrg()));
        seaInventory.setOrgWarehouseName(overseasWarehouse.getWarehouseName());
        seaInventory.setOrgWarehouseCode(orgCodeMap.get(overseasWarehouse.getWarehouseName()));
        seaInventory.setUniqueOrg(overseasWarehouse.getSysShopsName() + "_" + overseasWarehouse.getSysSite() + "_" + overseasWarehouse.getSku());
        seaInventoryList.add(seaInventory);
      }
      this.saveBatch(seaInventoryList, 100000);
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
      log.error("海外仓库刷组织编码失败!", e);
      e.printStackTrace();
      return ResponseData.error(String.valueOf(e));
    }
  }
}
