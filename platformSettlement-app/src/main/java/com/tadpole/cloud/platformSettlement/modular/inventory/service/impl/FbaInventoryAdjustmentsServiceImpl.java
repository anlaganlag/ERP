package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.*;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.FbaInventoryAdjustmentsMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaInventoryAdjustmentsParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.FbaInventoryAdjustmentsResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 * 库存调整报告 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@Service
public class FbaInventoryAdjustmentsServiceImpl extends ServiceImpl<FbaInventoryAdjustmentsMapper, FbaInventoryAdjustments> implements IFbaInventoryAdjustmentsService {

  @Autowired
  private IZZDistributeMcmsService erpService;
  @Autowired
  private IInventoryAdjustAddService addService;
  @Autowired
  private IInventoryAdjustReduceService reduceService;
  @Autowired
  private IInventoryAdjustAddDetailService addDetailService;
  @Autowired
  private IInventoryAdjustReduceDetailService reduceDetailService;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  @Autowired
  private IErpWarehouseCodeService erpWarehouseCodeService;
  /**
   * 生成列表标识
   */
  private static final String TOLIST = "TO_ADJUST_LIST";

  @DataSource(name = "warehouse")
  @Override
  public PageResult<FbaInventoryAdjustments> findPageBySpec(FbaInventoryAdjustmentsParam param) {
    Page pageContext = param.getPageContext();
    IPage<FbaInventoryAdjustments> page = this.baseMapper.customPageList(pageContext, param);
    return new PageResult<>(page);
  }

  @DataSource(name = "warehouse")
  @Override
  public Integer getQuantity(FbaInventoryAdjustmentsParam param) {
    return this.baseMapper.getQuantity(param);
  }

  @DataSource(name = "warehouse")
  @Override
  public List<FbaInventoryAdjustments> export(FbaInventoryAdjustmentsParam param) {
    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    IPage<FbaInventoryAdjustments> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void verify(FbaInventoryAdjustmentsParam param) {
    
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();

    UpdateWrapper<FbaInventoryAdjustments> updateWrapper = new UpdateWrapper<>();
    updateWrapper
        .eq("id", param.getId())
        .set("VERIFY_AT", new Date())
        .set("VERIFY_BY", LoginContext.me().getLoginUser().getAccount())
        .set("UPDATE_BY", LoginContext.me().getLoginUser().getAccount())
        .set("VERIFY_STATUS", 1)

        .set("ORG", param.getInventoryOrganization())
        .set("INVENTORY_ORGANIZATION_CODE", param.getInventoryOrganizationCode())
        .set("WAREHOUSE_NAME", param.getWarehouseName())
        .set("WAREHOUSE_CODE", param.getWarehouseCode());
    this.baseMapper.update(null, updateWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void reject(FbaInventoryAdjustmentsParam param) {
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    UpdateWrapper<FbaInventoryAdjustments> updateWrapper = new UpdateWrapper<>();
    updateWrapper
        .eq("id", param.getId())
        .set("VERIFY_AT", new Date())
        .set("VERIFY_BY", LoginContext.me().getLoginUser().getAccount())
        .set("UPDATE_BY", LoginContext.me().getLoginUser().getAccount())
        .set("VERIFY_STATUS", 2);
    this.baseMapper.update(null, updateWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  public void rejectBatch(List<FbaInventoryAdjustmentsParam> params) {
    FbaInventoryAdjustmentsParam param =  new FbaInventoryAdjustmentsParam();
    param.setVerifyBy(LoginContext.me().getLoginUser().getAccount());
    param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
    List<String> IdList= params.stream().map(i->i.getId().toString()).collect(Collectors.toList());
    List<List<String>> lists = ListUtil.split(IdList, 1000);
    //正常取时分配
    for (List<String> lst : lists) {
      param.setIdList(lst);
      this.baseMapper.rejectBatch(param);
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean verifyUpdateBatch(FbaInventoryAdjustmentsParam param) {
    return this.baseMapper.verifyUpdateBatch(param);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean orgBatch(List<FbaInventoryAdjustmentsParam> params) {
    return this.baseMapper.orgBatch(params);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<FbaInventoryAdjustmentsParam> orgList() {
    return this.baseMapper.orgList();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String getMaterial(FbaInventoryAdjustmentsParam param) {
    return this.baseMapper.getMaterial(param);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assignMaterial(FbaInventoryAdjustmentsParam param) {
    ZZDistributeMcms ent = new ZZDistributeMcms();
    ent.setMaterialCode(param.getMat());
    ent.setShopCode(param.getSalesOrganizationCode());
    erpService.save(ent);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assignBatchMaterial(List<FbaInventoryAdjustmentsParam> params) {
    List<ZZDistributeMcms> ZZDistributeMcmsList = new ArrayList<>();
    ZZDistributeMcms ent = new ZZDistributeMcms();
    for (FbaInventoryAdjustmentsParam param:params){
      ent.setMaterialCode(param.getMat());
      ent.setShopCode(param.getSalesOrganizationCode());
      ZZDistributeMcmsList.add(ent);
    }
    erpService.saveBatch(ZZDistributeMcmsList);

  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData toInventoryAdjustList(FbaInventoryAdjustmentsParam param) {
    BoundValueOperations toList = redisTemplate.boundValueOps(TOLIST);
    //从redis里面取值如果非空则为正在生成!
    if (toList.get() != null && StrUtil.isNotEmpty((String)toList.get())){
      return ResponseData.error("正在生成中!");
    }
    try {
      //为空则设为正在生成!
      toList.set("正在生成中!");

      param.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
      List<FbaInventoryAdjustmentsResult> addHeaderList = this.baseMapper.getAddListHeader(param);
      List<FbaInventoryAdjustmentsResult> reduceHeaderList = this.baseMapper.getReduceListHeader(param);
      if (CollectionUtil.isEmpty(addHeaderList) && CollectionUtil.isEmpty(reduceHeaderList)) {
        return ResponseData.error("无可生成的数据!");
      }
      for (FbaInventoryAdjustmentsResult header : addHeaderList) {
        //生成库存调整头部实体
        InventoryAdjustAdd outAdd = getListAddEntity(header);
        //年月
        outAdd.setShopName(header.getSysShopsName());
        outAdd.setSite(header.getSysSite());
        //更新时间
        outAdd.setUpdateAt(new Date());
        outAdd.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
        //拼接单据编号
        QueryWrapper<InventoryAdjustAdd> wp = new QueryWrapper();
        List<InventoryAdjustAddDetail> detailList = this.baseMapper.getAddDetailList(header);

        String billCodeBase = "QTRKD-" + header.getInventoryOrganizationCode() + "-" +  header.getYear() + header.getMonth() + "000";
        wp.likeRight("BILL_CODE", billCodeBase);
        int billCodeIdx = addService.count(wp) ;
        wp.clear();

        int detailSizeLimit = 500;
          List<List<InventoryAdjustAddDetail>> lists = ListUtil.split(detailList, detailSizeLimit);
          for (List<InventoryAdjustAddDetail> lst: lists) {
            if ( billCodeIdx == 0 ){
              outAdd.setBillCode(billCodeBase);
            } else {outAdd.setBillCode(billCodeBase+'-'+billCodeIdx);}
            addService.save(outAdd);
            lst.stream().forEach(i->{i.setOutId(outAdd.getId());i.setUpdateBy(LoginContext.me().getLoginUser().getAccount());});
            addDetailService.saveBatch(lst);
            billCodeIdx++;
          }
        //改源报告生成状态和写入源报告
        header.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
        header.setBillCode(billCodeBase);
        this.baseMapper.updateAddSrcList(header);

      }
      this.baseMapper.updateAddDetailList();
      this.baseMapper.updateFileAddDetailList();
      for (
          FbaInventoryAdjustmentsResult header : reduceHeaderList) {
        //生成库存调整头部

        InventoryAdjustReduce outReduce = getListReduceEntity(header);
        outReduce.setShopName(header.getSysShopsName());
        outReduce.setSite(header.getSysSite());
        outReduce.setUpdateAt(new Date());
        outReduce.setUpdateBy(LoginContext.me().getLoginUser().getAccount());

        //拼接单据编号
        QueryWrapper<InventoryAdjustReduce> wp = new QueryWrapper();
        List<InventoryAdjustReduceDetail> detailList = this.baseMapper.getReduceDetailList(header);

        String billCodeBase = "QTCKD-" + header.getInventoryOrganizationCode() + "-" +  header.getYear() +  header.getMonth() + "000";
        wp.likeRight("BILL_CODE", billCodeBase);
        int billCodeIdx = reduceService.count(wp) ;
        wp.clear();
        int detailSizeLimit = 500;
          List<List<InventoryAdjustReduceDetail>> lists = ListUtil.split(detailList, detailSizeLimit);
          for (List<InventoryAdjustReduceDetail> lst: lists) {
            if ( billCodeIdx == 0 ){
              outReduce.setBillCode(billCodeBase);
            } else {outReduce.setBillCode(billCodeBase+'-'+billCodeIdx);}
            reduceService.save(outReduce);
            lst.stream().forEach(i->{i.setOutId(outReduce.getId().toString());i.setUpdateBy(LoginContext.me().getLoginUser().getAccount());});
            reduceDetailService.saveBatch(lst);
            billCodeIdx++;
          }
        //改源报告生成状态和写入源报告
        header.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
        header.setBillCode(billCodeBase);
        this.baseMapper.updateReduceSrcList(header);

      }
      //刷新明细数据
      this.baseMapper.updateReduceDetailList();
      this.baseMapper.updateFileReduceDetailList();
      return ResponseData.success();

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseData.error("生成失败!");
    }finally {
      toList.set("");
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData editSites(List<FbaInventoryAdjustmentsParam> params) {
    Map<String, String> orgMap = new ConcurrentHashMap<>();
    Map<String, String> warehouseMap = new ConcurrentHashMap<>();
    List<Map<String,String>> warehouseCodeList = erpWarehouseCodeService.getOrgCodeInfo();
    for (Map<String,String> map : warehouseCodeList) {
      String organizationType = map.get("ORGANIZATION_TYPE");
      String organizationName = map.get("ORGANIZATION_NAME");
      String organizationCode = map.get("ORGANIZATION_CODE");
      //组织机构
      if("organization".equals(organizationType)){
        orgMap.put(organizationName, organizationCode);
      }
      //仓库
      if("warehouse".equals(organizationType)){
        warehouseMap.put(organizationName, organizationCode);
      }
    }

    for (FbaInventoryAdjustmentsParam param : params) {
      //组织名称：平台_账号_站点
      String orgName = "Amazon_" + param.getSysShopsName() + "_" + param.getSysSite();
      String orgCode = orgMap.get(orgName);
      if(StringUtils.isEmpty(orgCode)){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改站点失败，没有找到对应的仓库组织编码信息");
      }

      //仓库名称：平台_账号_站点_仓库
      String wareHouseName = "Amazon_" + param.getSysShopsName() + "_" + param.getSysSite() + "_仓库";
      String wareHouseCode =  warehouseMap.get(wareHouseName);
      if(StringUtils.isEmpty(wareHouseCode)){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改站点失败，没有找到对应的仓库编码信息");
      }

      UpdateWrapper<FbaInventoryAdjustments> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID",param.getId()).isNull("SYS_SITE")
              .set("SYS_SITE",param.getSysSite())
              .set("INVENTORY_ORGANIZATION_CODE", orgCode)
              .set("ORG", orgName)
              .set("WAREHOUSE_CODE", wareHouseCode)
              .set("WAREHOUSE_NAME", wareHouseName)
              .set("UPDATE_BY",LoginContext.me().getLoginUser().getAccount());

      this.update(null,updateWrapper);
    }
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData editShop(List<FbaInventoryAdjustmentsParam> params) {
    Map<String, String> orgMap = new ConcurrentHashMap<>();
    Map<String, String> warehouseMap = new ConcurrentHashMap<>();
    List<Map<String,String>> warehouseCodeList = erpWarehouseCodeService.getOrgCodeInfo();
    for (Map<String,String> map : warehouseCodeList) {
      String organizationType = map.get("ORGANIZATION_TYPE");
      String organizationName = map.get("ORGANIZATION_NAME");
      String organizationCode = map.get("ORGANIZATION_CODE");
      //组织机构
      if("organization".equals(organizationType)){
        orgMap.put(organizationName, organizationCode);
      }
      //仓库
      if("warehouse".equals(organizationType)){
        warehouseMap.put(organizationName, organizationCode);
      }
    }

    for (FbaInventoryAdjustmentsParam param : params) {
      //组织名称：平台_账号_站点
      String orgName = "Amazon_" + param.getSysShopsName() + "_" + param.getSysSite();
      String orgCode = orgMap.get(orgName);
      if(StringUtils.isEmpty(orgCode)){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改账号失败，没有找到对应的仓库组织编码信息");
      }

      //仓库名称：平台_账号_站点_仓库
      String wareHouseName = "Amazon_" + param.getSysShopsName() + "_" + param.getSysSite() + "_仓库";
      String wareHouseCode =  warehouseMap.get(wareHouseName);
      if(StringUtils.isEmpty(wareHouseCode)){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改账号失败，没有找到对应的仓库编码信息");
      }

      UpdateWrapper<FbaInventoryAdjustments> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID",param.getId())
              .set("SYS_SHOPS_NAME",param.getSysShopsName())
              .set("INVENTORY_ORGANIZATION_CODE", orgCode)
              .set("ORG", orgName)
              .set("WAREHOUSE_CODE", wareHouseCode)
              .set("WAREHOUSE_NAME", wareHouseName)
              .set("UPDATE_BY",LoginContext.me().getLoginUser().getAccount());
      

      this.update(null,updateWrapper);
    }
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData addNewOrg(){
    try {
      //刷组织名称（Amazon_账号_站点）和仓库组织名称（Amazon_账号_站点_仓库）
      this.baseMapper.addOrgName();

      //根据组织刷库存组织编码
      this.baseMapper.refreshOrgCode();

      //根据仓库组织名称获取仓库组织编码
      this.baseMapper.refreshWareOrgCode();
      return ResponseData.success();
    } catch (Exception e){
      log.error("FbaInventoryAdjustments刷组织编码失败!", e);
      e.printStackTrace();
      return ResponseData.error(String.valueOf(e));
    }
  }

  private InventoryAdjustAdd getListAddEntity(FbaInventoryAdjustmentsResult param) {
    InventoryAdjustAdd entity = new InventoryAdjustAdd();
    BeanUtil.copyProperties(param, entity);
    return entity;
  }

  private InventoryAdjustReduce getListReduceEntity(FbaInventoryAdjustmentsResult param) {
    InventoryAdjustReduce entity = new InventoryAdjustReduce();
    BeanUtil.copyProperties(param, entity);
    return entity;
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData generateInventoryAdjustments(){
    //从库存事件汇总报告取数
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(DateUtil.date());
    calendar.add(Calendar.MONTH, -1);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date selectDate = calendar.getTime();
    this.baseMapper.generateInventoryAdjustments(selectDate);

    //默认刷组织，先注释，等业务验证数据OK后切换到正式表
//    this.addNewOrg();
    return ResponseData.success();
  }
}
