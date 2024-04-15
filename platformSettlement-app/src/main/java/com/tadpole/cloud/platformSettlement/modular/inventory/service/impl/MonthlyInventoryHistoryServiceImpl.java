package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import cn.stylefeng.guns.cloud.libs.context.SpringContext;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.*;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.MonthlyInventoryHistoryMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.MonthlyInventoryHistoryParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 * Monthly inventory history报告明细 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@Service
@Slf4j
public class MonthlyInventoryHistoryServiceImpl extends ServiceImpl<MonthlyInventoryHistoryMapper, MonthlyInventoryHistory> implements IMonthlyInventoryHistoryService {

  @Autowired
  private IEndingInventoryService service;
  @Autowired
  private IEndingInventoryDetailService detailService;
  @Autowired
  private IZZDistributeMcmsService erpService;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  @Autowired
  private IErpWarehouseCodeService erpWarehouseCodeService;
  @Autowired
  private IEbmsComingInventoryService ebmsComingInventoryService;
  @Autowired
  private IEbmsSeaInventoryService ebmsSeaInventoryService;
  /**
   * 生成列表标识
   */
  private static final String TOLIST = "TO_MONTH_LIST";

  @DataSource(name = "warehouse")
  @Override
  public PageResult<MonthlyInventoryHistory> findPageBySpec(MonthlyInventoryHistoryParam param) {
    Page pageContext = getPageContext();
    IPage<MonthlyInventoryHistory> page = this.baseMapper.customPageList(pageContext, param);
    return new PageResult<>(page);
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData getQuantity(MonthlyInventoryHistoryParam param) {
    return ResponseData.success(this.baseMapper.getQuantity(param));
  }

  @DataSource(name = "warehouse")
  @Override
  public List<MonthlyInventoryHistory> export(MonthlyInventoryHistoryParam param) {
    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    IPage<MonthlyInventoryHistory> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  public void verify(MonthlyInventoryHistoryParam param) {
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    UpdateWrapper<MonthlyInventoryHistory> updateWrapper = new UpdateWrapper<>();
    updateWrapper
        .eq("id", param.getId())
        .set("VERIFY_AT", new Date())
        .set("VERIFY_BY", currentUser.getAccount())
        .set("UPDATE_BY", currentUser.getAccount())
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
  public Boolean orgBatch(List<MonthlyInventoryHistoryParam> params) {
    return this.baseMapper.orgBatch(params);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<MonthlyInventoryHistoryParam> orgList() {
    return this.baseMapper.orgList();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String getMaterial(MonthlyInventoryHistoryParam param) {
    return this.baseMapper.getMaterial(param);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assignMaterial(MonthlyInventoryHistoryParam param) {
    ZZDistributeMcms ent = new ZZDistributeMcms();
    ent.setMaterialCode(param.getMat());
    ent.setShopCode(param.getSalesOrganizationCode());
    erpService.save(ent);
  }

  @DataSource(name = "warehouse")
  @Override
  public void reject(MonthlyInventoryHistoryParam param) {
    LoginContext current = SpringContext.getBean(LoginContext.class);
    LoginUser currentUser = current.getLoginUser();
    UpdateWrapper<MonthlyInventoryHistory> updateWrapper = new UpdateWrapper<>();
    updateWrapper
        .eq("id", param.getId())
        .set("VERIFY_AT", new Date())
        .set("VERIFY_BY", currentUser.getAccount())
        .set("UPDATE_BY", currentUser.getAccount())
        .set("VERIFY_STATUS", 2);
    this.baseMapper.update(null, updateWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  public void rejectBatch(List<MonthlyInventoryHistoryParam> params) {
    MonthlyInventoryHistoryParam param =  new MonthlyInventoryHistoryParam();
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
  public ResponseData toEndingInventoryList(MonthlyInventoryHistoryParam param) {
    if(StringUtils.isBlank(param.getDateMonthly())){
      return ResponseData.error("请输入月份");
    }
    String dateMonthly = param.getDateMonthly();

    //根据月份查询月末在途库存是否生成
    QueryWrapper<EbmsComingInventory> comingQW = new QueryWrapper();
    comingQW.eq("TO_CHAR(STATICS_DATE,'yyyymm')", dateMonthly);
    if (ebmsComingInventoryService.count(comingQW) == 0){
      log.error(param.getDateMonthly() + "没有在途库存数据");
      return ResponseData.error(param.getDateMonthly() + "没有在途库存数据");
    }

    //根据月份查询月末海外仓库存是否生成
    QueryWrapper<EbmsSeaInventory> seaQW = new QueryWrapper();
    seaQW.eq("TO_CHAR(BALANCE_DATE,'yyyymm')", dateMonthly);
    if (ebmsSeaInventoryService.count(seaQW) == 0){
      log.error(param.getDateMonthly() + "没有海外仓库存数据");
      return ResponseData.error(param.getDateMonthly() + "没有海外仓库存数据");
    }
    String userAccount = LoginContext.me().getLoginUser().getAccount();

    //期末库存明细表集合
    List<EndingInventoryDetail> updateInvQtyList = new ArrayList<>();

    //生成期末库存列表单据头和明细
    //获取指定月份Monthly Inventory History汇总数据（单据头）
    List<EndingInventory> headerList = this.baseMapper.getListHeader(param);
    if (CollectionUtil.isNotEmpty(headerList)) {
      for (EndingInventory header : headerList) {
        //获取指定月份Monthly Inventory_History明细数据
        List<EndingInventoryDetail> detailList = this.baseMapper.getDetailList(header);
        //拼接单据编号
        QueryWrapper<EndingInventory> wp = new QueryWrapper();
        String billCodeBase = "PDFA01_SYS-" + header.getInventoryOrganizationCode() + "-" + header.getYear() + header.getMonth() + "000";
        wp.likeRight("BILL_CODE", billCodeBase);
        int billCodeIdx = service.count(wp);
        List<List<EndingInventoryDetail>> lists = ListUtil.split(detailList, 500);
        for (List<EndingInventoryDetail> subList: lists) {
          if (billCodeIdx == 0){
            header.setBillCode(billCodeBase);
          } else {
            header.setBillCode(billCodeBase + "-" + billCodeIdx);
          }
          //入库期末库存主表
          header.setCreateUser(userAccount);
          service.save(header);
          subList.stream().forEach(i -> {
            i.setDataMonth(dateMonthly);
            i.setOutId(header.getId());//汇总表ID
            i.setCreateUser(userAccount);
          });
          //入库期末库存明细表
          detailService.saveBatch(subList);
          billCodeIdx++;
        }
        updateInvQtyList.addAll(detailList);

        //更新Monthly Inventory History源报告生成状态和单据头
        header.setBillCode(billCodeBase);
        this.baseMapper.updateSrcList(header);
      }

      //+在途库存更新物流代发数和在途数
      this.baseMapper.updateComingInventory(dateMonthly);

      //更新在途库存使用状态
      this.baseMapper.updateComingUse(dateMonthly);

      //+海外仓库存更新在库数和在途数
      this.baseMapper.updateSeaInventory(dateMonthly);

      //更新海外仓库存使用状态
      this.baseMapper.updateSeaUse(dateMonthly);
    }

    //判断Monthly Inventory History源报告全部数据是否已审核且已经生成期末库存列表，===》全部已审核且已经生成，未使用的途库存和海外仓库存生成期末库存列表表头和明细
    if(this.baseMapper.getNotVerifyCounts(param) == 0){
      List<EndingInventory> comingAndSeaHeadList = this.baseMapper.getHeaderByComingAndSea(dateMonthly, userAccount);
      if(CollectionUtil.isNotEmpty(comingAndSeaHeadList )){
        for (EndingInventory header : comingAndSeaHeadList) {
          //获取指定月份Monthly Inventory_History明细数据
          header.setDateMonthly(dateMonthly);
          List<EndingInventoryDetail> detailList = this.baseMapper.getDetailByComingAndSea(header);
          //拼接单据编号
          QueryWrapper<EndingInventory> wp = new QueryWrapper();
          String billCodeBase = "PDFA01_SYS-" + header.getInventoryOrganizationCode() + "-" + header.getYear() + header.getMonth() + "000";
          wp.likeRight("BILL_CODE", billCodeBase);
          int billCodeIdx = service.count(wp);
          List<List<EndingInventoryDetail>> lists = ListUtil.split(detailList, 500);
          for (List<EndingInventoryDetail> subList: lists) {
            if (billCodeIdx == 0){
              header.setBillCode(billCodeBase);
            } else {
              header.setBillCode(billCodeBase + "-" + billCodeIdx);
            }
            //入库期末库存主表
            service.save(header);
            subList.stream().forEach(i -> {
              i.setOutId(header.getId());//汇总表ID
              i.setCreateUser(userAccount);
            });
            //入库期末库存明细表
            detailService.saveBatch(subList);
            billCodeIdx++;
          }
          updateInvQtyList.addAll(detailList);
        }

        //批量更新在途库存使用状态
        UpdateWrapper<EbmsComingInventory> updateComingWrapper = new UpdateWrapper<>();
        updateComingWrapper.eq("TO_CHAR(STATICS_DATE,'yyyymm')", dateMonthly)
                .isNotNull("INVENTORY_ORGANIZATION_CODE")
                .eq("IS_USE", "0")
                .set("IS_USE", "1");
        ebmsComingInventoryService.update(null, updateComingWrapper);

        //批量更新海外仓库存使用状态
        UpdateWrapper<EbmsSeaInventory> updateSeaWrapper = new UpdateWrapper<>();
        updateSeaWrapper.eq("TO_CHAR(BALANCE_DATE,'yyyymm')", dateMonthly)
                .isNotNull("INVENTORY_ORGANIZATION_CODE")
                .eq("IS_USE", "0")
                .set("IS_USE", "1");
        ebmsSeaInventoryService.update(null, updateSeaWrapper);
      }
    }

    //期末库存列表明细刷listings
    try {
      this.baseMapper.updateDetailList();
      this.baseMapper.updateFileDetailList();
    } catch (Exception e) {
      log.error("期末库存列表明细刷listings异常", e);
    }
    List <Long> updateInvIdList = updateInvQtyList.stream().map(i->i.getId()).collect(Collectors.toList());
      return ResponseData.success(updateInvIdList);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData editSites(List<MonthlyInventoryHistoryParam> params) {
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

    for (MonthlyInventoryHistoryParam param : params) {
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

      UpdateWrapper<MonthlyInventoryHistory> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID", param.getId()).isNull("SYS_SITE")
          .set("SYS_SITE", param.getSysSite())
          .set("INVENTORY_ORGANIZATION_CODE", orgCode)
          .set("ORG", orgName)
          .set("WAREHOUSE_CODE", wareHouseCode)
          .set("WAREHOUSE_NAME", wareHouseName)
          .set("UPDATE_BY",LoginContext.me().getLoginUser().getAccount());

      this.update(null, updateWrapper);
    }
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData editShop(List<MonthlyInventoryHistoryParam> params) {
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

    for (MonthlyInventoryHistoryParam param:params) {
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

      UpdateWrapper<MonthlyInventoryHistory> updateWrapper = new UpdateWrapper<>();
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
  public Boolean verifyUpdateBatch(MonthlyInventoryHistoryParam param) {
    return this.baseMapper.verifyUpdateBatch(param);
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

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<ZZDistributeMcms> assignMaterialList(List<String> detailIdList) {
    return this.baseMapper.assignMaterialList(detailIdList);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData assignBatchMaterial(List<ZZDistributeMcms> assignList) {
    try {
      erpService.saveBatch(assignList);
      return ResponseData.success("期末库存列表同步ERP成功!");
    } catch (Exception e) {
      log.error("期末库存列表同步ERP失败，[{}]", e.getMessage());
      return ResponseData.error("期末库存列表同步ERP失败!");
    }
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData generateMonthlyInventoryHistory(){
      //从库存事件汇总报告取数
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(DateUtil.date());
      calendar.add(Calendar.MONTH, -1);
      Date selectDate = calendar.getTime();
      String dataDate = DateUtil.format(selectDate, "MM/yyyy");
      this.baseMapper.generateMonthlyInventoryHistory(dataDate);

      //默认刷组织，先注释，等业务验证数据OK后切换到正式表
//      this.addNewOrg();
      return ResponseData.success();
  }

  private Page getPageContext() {
    return PageFactory.defaultPage();
  }
}
