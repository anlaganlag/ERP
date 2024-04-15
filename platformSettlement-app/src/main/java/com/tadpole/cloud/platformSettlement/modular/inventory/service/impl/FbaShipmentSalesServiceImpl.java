package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.libs.mp.page.PageFactory;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.*;
import com.tadpole.cloud.platformSettlement.modular.inventory.enums.CommonTypeEnum;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.FbaShipmentSalesMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaShipmentSalesParam;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.*;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 * 亚马逊物流买家货件销售 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-23
 */
@Service
@Slf4j
public class FbaShipmentSalesServiceImpl extends ServiceImpl<FbaShipmentSalesMapper, FbaShipmentSales> implements IFbaShipmentSalesService {

  @Autowired
  private IZZDistributeMcmsService erpService;
  @Autowired
  private ISalesStockOutService salesStockOutService;
  @Autowired
  private ISalesStockOutDetailService detailService;
  @Autowired
  private IErpWarehouseCodeService erpWarehouseCodeService;
  @Value("${finance_database}")
  private String financeDatabase;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  /**
   * 生成列表标识
   */
  private static final String TOLIST = "TO_SALES_LIST";

  @DataSource(name = "warehouse")
  @Override
  public PageResult<FbaShipmentSales> findPageBySpec(FbaShipmentSalesParam param) {
    Page pageContext = PageFactory.defaultPage();
    List <String> sites = param.getSysSites();
    if (CollUtil.isNotEmpty(sites) && sites.contains("空")){
      param.setSysSite("空");
    }
    return new PageResult<>(this.baseMapper.customPageList(pageContext, param));
  }

  @DataSource(name = "warehouse")
  @Override
  public Integer getQuantity(FbaShipmentSalesParam param) {
    List <String> sites = param.getSysSites();
    if (CollUtil.isNotEmpty(sites) && sites.contains("空")){
      param.setSysSite("空");
    }
    return this.baseMapper.getQuantity(param);
  }

  /**
   * 导入修改销售组织
   */
  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData upload(MultipartFile file) {
    log.info("导入修改销售组织开始");
    String account = LoginContext.me().getLoginUser().getName();
    BufferedInputStream buffer = null;
    try{
      buffer = new BufferedInputStream(file.getInputStream());
      BaseExcelListener listener = new BaseExcelListener<FbaShipmentSales>();
      EasyExcel.read(buffer, FbaShipmentSales.class, listener).sheet().doRead();
      List<FbaShipmentSales> dataList = listener.getDataList();

      Map<String, String> orgCodeMap = erpWarehouseCodeService.getOrgCodeMap();
      Boolean isError = this.validationIsError(dataList);
      if (Boolean.TRUE.equals(isError)) {
        String fileName = this.dealImportErrorList(dataList);
        //导入失败
        return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在异常数据数据", fileName);
      }

      for (FbaShipmentSales  data: dataList) {
          String amazonOrderId =  data.getAmazonOrderId();
          String salOrg =  data.getSalesOrganization();
          String salOrgCode = orgCodeMap.get(salOrg);
          if (StrUtil.isNotEmpty(amazonOrderId) && StrUtil.isNotEmpty(salOrg) && StrUtil.isNotEmpty(salOrgCode)){
            UpdateWrapper<FbaShipmentSales> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                .eq("amazon_order_id", amazonOrderId).isNull("SALES_ORGANIZATION_CODE")
                .set("SALES_ORGANIZATION", salOrg)
                .set("SALES_ORGANIZATION_CODE", salOrgCode);
            this.baseMapper.update(null, updateWrapper);
            updateWrapper.clear();
          }
        }
      if (CollectionUtil.isEmpty(dataList)) {
        return ResponseData.error("数据为空，无法导入！");
      }
      return ResponseData.success();
    }catch (Exception e) {
      log.error("导入修改销售组织异常，导入失败！", e);
      return ResponseData.error("导入修改销售组织异常，导入失败！");
    } finally {
      if (buffer != null) {
        try {
          buffer.close();
        } catch (IOException e) {
          log.error("导入修改销售组织关闭流异常", e);
        }
      }
    }
  }

  @DataSource(name = "warehouse")
  @Override
  public List<FbaShipmentSales> export(FbaShipmentSalesParam param) {
    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    IPage<FbaShipmentSales> page = this.baseMapper.customPageList(pageContext, param);
    return page.getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String getMaterial(FbaShipmentSalesParam param) {
    return this.baseMapper.getMaterial(param);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assignMaterial(FbaShipmentSalesParam param) {
    ZZDistributeMcms ent = new ZZDistributeMcms();
    ent.setMaterialCode(param.getMat());
    ent.setShopCode(param.getInventoryOrganizationCode());

    erpService.save(ent);

    ZZDistributeMcms entSales = new ZZDistributeMcms();
    entSales.setMaterialCode(param.getMat());
    entSales.setShopCode(param.getSalesOrganizationCode());

    erpService.save(entSales);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData assignBatchMaterial(List<ZZDistributeMcms> assignList) {
    try {
      erpService.saveBatch(assignList, 100000);
      return ResponseData.success("生成列表及分配ERP物料成功!");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseData.error("生成列表失败!");
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void verify(FbaShipmentSalesParam param) {
    UpdateWrapper<FbaShipmentSales> updateWrapper = new UpdateWrapper<>();
    updateWrapper
        .eq("id", param.getId())
        .set("VERIFY_AT", new Date())
        .set("UPDATE_TIME", new Date())
        .set("VERIFY_BY", LoginContext.me().getLoginUser().getAccount())
        .set("UPDATE_BY", LoginContext.me().getLoginUser().getAccount())
        .set("VERIFY_STATUS", 1)
        .set(CommonTypeEnum.SALES_ORG.getName(), param.getSalesOrganization())
        .set(CommonTypeEnum.SALES_ORG_CODE.getName(), param.getSalesOrganizationCode())
        .set(CommonTypeEnum.ORG.getName(), param.getInventoryOrganization())
        .set(CommonTypeEnum.ORG_CODE.getName(), param.getInventoryOrganizationCode())
        .set(CommonTypeEnum.WARE.getName(), param.getWarehouseName())
        .set(CommonTypeEnum.WARE_CODE.getName(), param.getWarehouseCode());
    this.baseMapper.update(null, updateWrapper);
  }


  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateBatch(List<FbaShipmentSales> updateList) {
    return this.saveOrUpdateBatch(updateList);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<FbaShipmentSales> remainList() {
    return this.baseMapper.remainList();
  }

  @DataSource(name = "warehouse")
  @Override
  public void reject(FbaShipmentSalesParam param) {
    UpdateWrapper<FbaShipmentSales> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
        .eq("VERIFY_STATUS", 0)
        .set("VERIFY_AT", new Date())
        .set("UPDATE_TIME", new Date())
        .set("VERIFY_BY", LoginContext.me().getLoginUser().getAccount())
        .set("UPDATE_BY", LoginContext.me().getLoginUser().getAccount())
        .set("VERIFY_STATUS", 2);
    this.baseMapper.update(null, updateWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  public void rejectBatch(List<FbaShipmentSalesParam> params) {
    FbaShipmentSalesParam param =  new FbaShipmentSalesParam();
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
  public Boolean verifyUpdateBatch(FbaShipmentSalesParam param) {
    return this.baseMapper.verifyUpdateBatch(param);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData toSalesStockOutList(FbaShipmentSalesParam param) {
    //redis操作需绑定key
    if(redisTemplate.hasKey(TOLIST)){
      return ResponseData.error("正在生成中!");
    }
    try {
      //设定正在生成!
      redisTemplate.boundValueOps(TOLIST).set("正在生成中", Duration.ofSeconds(600));
      //获取销售出库列表头部数据
      List<SalesStockOut> headerList = this.baseMapper.getListHeader(param);
      if (CollUtil.isEmpty(headerList)) {
        return ResponseData.error("无可生成的数据!");
      }
      List<String> detailIdList = new ArrayList<>();
      for (SalesStockOut header : headerList) {
        //获取销售列表明细数据
        List<SalesStockOutDetail> detailList = this.baseMapper.getDetailList(header);
        //拼接销售出库单据编号
        String billCodeBase = "XSCKD-" + header.getInventoryOrganizationCode() + "-" + header
            .getSalesOrganizationCode() + '-' + header.getYear() + header.getMonth() + "000";

        QueryWrapper<SalesStockOut> wp = new QueryWrapper();
        wp.likeRight("BILL_CODE", billCodeBase);
        int billCodeIdx = salesStockOutService.count(wp);
        wp.clear();
        List<List<SalesStockOutDetail>> lists = ListUtil.split(detailList, 500);
        for (List<SalesStockOutDetail> lst : lists) {
          if (billCodeIdx == 0) {
            header.setBillCode(billCodeBase);
          } else {
            header.setBillCode(billCodeBase + '-' + billCodeIdx);
          }
          salesStockOutService.save(header);
          lst.forEach(i -> {
            i.setOutId(header.getId());
            i.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
          });
          detailService.saveBatch(lst);
          detailIdList.addAll(lst.stream().map(i -> i.getId().toString()).collect(Collectors.toList()));
          billCodeIdx++;
        }
        //更新销售出库fba_shipment_sales源报告生成状态和单据头编码
        header.setWarehouseName(LoginContext.me().getLoginUser().getAccount());
        header.setBillCode(billCodeBase);
        this.baseMapper.updateSrcList(header);
      }
      //销售出库列表明细刷listing
      this.baseMapper.updateDetailList();
      //销售出库列表明细刷存档listing
      this.baseMapper.updateFileDetailList();

      //销售出库列表组织编码及物料编码
      List<ZZDistributeMcms> assignList = new ArrayList<>();
      List<List<String>> lists = ListUtil.split(detailIdList, 1000);
      //正常取时分配
      for (List<String> lst : lists) {
        //获取销售出库列表组织编码及物料编码
        assignList.addAll(this.assignMaterialList(lst));
      }
      return ResponseData.success(assignList);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseData.error("生成失败!:" + e);
    } finally {
      redisTemplate.delete(TOLIST);
    }
  }

  @DataSource(name = "warehouse")
  @Override
  public void syncErpCode(List<ErpOrgCode> codes) {
    for (ErpOrgCode code : codes) {
      this.baseMapper.syncErpCode(code);
    }
  }

  @DataSource(name = "erpcloud")
  @Override
  public List<ErpOrgCode> getErpCode() {
    return this.baseMapper.getErpCode();
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData editSites(List<FbaShipmentSalesParam> params) {
    Map<String, String> orgMap = new ConcurrentHashMap<>();
    Map<String, String> warehouseMap = new ConcurrentHashMap<>();
    List<Map<String, String>> warehouseCodeList = erpWarehouseCodeService.getOrgCodeInfo();
    for (Map<String, String> map : warehouseCodeList) {
      String organizationType = map.get("ORGANIZATION_TYPE");
      String organizationName = map.get("ORGANIZATION_NAME");
      String organizationCode = map.get("ORGANIZATION_CODE");
      //组织机构
      if ("organization".equals(organizationType)) {
        orgMap.put(organizationName, organizationCode);
      }
      //仓库
      if ("warehouse".equals(organizationType)) {
        warehouseMap.put(organizationName, organizationCode);
      }
    }

    for (FbaShipmentSalesParam param : params) {
      //组织名称：平台_账号_站点
      String orgName =
          CommonTypeEnum.Amazon.getName() + "_" + param.getSysShopsName() + "_" + param.getSysSite();
      String orgCode = orgMap.get(orgName);
      if (StringUtils.isEmpty(orgCode)) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改站点失败，没有找到对应的仓库组织编码信息");
      }

      //仓库名称：平台_账号_站点_仓库
      String wareHouseName =
          CommonTypeEnum.Amazon.getName() + "_" + param.getSysShopsName() + "_" + param.getSysSite()
              + "_仓库";
      String wareHouseCode = warehouseMap.get(wareHouseName);
      if (StringUtils.isEmpty(wareHouseCode)) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改站点失败，没有找到对应的仓库编码信息");
      }

      UpdateWrapper<FbaShipmentSales> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID", param.getId()).isNull("SYS_SITE")
          .set("SYS_SITE", param.getSysSite())
          .set(CommonTypeEnum.ORG_CODE.getName(), orgCode)
          .set(CommonTypeEnum.ORG.getName(), orgName)
          .set(CommonTypeEnum.WARE_CODE.getName(), wareHouseCode)
          .set(CommonTypeEnum.WARE.getName(), wareHouseName);

      this.update(null, updateWrapper);
    }
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  public void editPlatform(List<FbaShipmentSalesParam> params) {
    for (FbaShipmentSalesParam param : params) {
      UpdateWrapper<FbaShipmentSales> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID", param.getId())
          .set("PLATFORM", param.getPlatform());
      this.update(null, updateWrapper);
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData editShop(List<FbaShipmentSalesParam> params) {
    Map<String, String> orgMap = new ConcurrentHashMap<>();
    Map<String, String> warehouseMap = new ConcurrentHashMap<>();
    List<Map<String, String>> warehouseCodeList = erpWarehouseCodeService.getOrgCodeInfo();
    for (Map<String, String> map : warehouseCodeList) {
      String organizationType = map.get("ORGANIZATION_TYPE");
      String organizationName = map.get("ORGANIZATION_NAME");
      String organizationCode = map.get("ORGANIZATION_CODE");
      //组织机构
      if ("organization".equals(organizationType)) {
        orgMap.put(organizationName, organizationCode);
      }
      //仓库
      if ("warehouse".equals(organizationType)) {
        warehouseMap.put(organizationName, organizationCode);
      }
    }

    for (FbaShipmentSalesParam param : params) {
      //组织名称：平台_账号_站点
      String orgName = CommonTypeEnum.Amazon.getName() + "_" + param.getSysShopsName() + "_" + param
          .getSysSite();
      String orgCode = orgMap.get(orgName);
      if (StringUtils.isEmpty(orgCode)) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改账号失败，没有找到对应的仓库组织编码信息");
      }

      //仓库名称：平台_账号_站点_仓库
      String wareHouseName =
          CommonTypeEnum.Amazon.getName() + "_" + param.getSysShopsName() + "_" + param.getSysSite()
              + "_仓库";
      String wareHouseCode = warehouseMap.get(wareHouseName);
      if (StringUtils.isEmpty(wareHouseCode)) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改账号失败，没有找到对应的仓库编码信息");
      }

      UpdateWrapper<FbaShipmentSales> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID", param.getId())
          .set("SYS_SHOPS_NAME", param.getSysShopsName())
          .set(CommonTypeEnum.ORG_CODE.getName(), orgCode)
          .set(CommonTypeEnum.ORG.getName(), orgName)
          .set(CommonTypeEnum.WARE_CODE.getName(), wareHouseCode)
          .set(CommonTypeEnum.WARE.getName(), wareHouseName);
      this.update(null, updateWrapper);
    }
    return ResponseData.success();
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<ZZDistributeMcms> assignMaterialList(List<String> detailIdList) {
    return this.baseMapper.assignMaterialList(detailIdList);
  }

  @DataSource(name = "warehouse")
  @Override
  public ResponseData editSalesOrg(List<FbaShipmentSalesParam> params) {
    try {
      for (FbaShipmentSalesParam param : params) {
        UpdateWrapper<FbaShipmentSales> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID", param.getId())
            .isNull(CommonTypeEnum.SALES_ORG_CODE.getName())
            .set(CommonTypeEnum.SALES_ORG.getName(), param.getSalesOrganization())
            .set(CommonTypeEnum.SALES_ORG_CODE.getName(), param.getSalesOrganizationCode());
        this.update(null, updateWrapper);
      }
    } catch (Exception e) {
      log.error("修改销售组织异常", e);
      return ResponseData.error("修改销售组织异常");
    }
    return ResponseData.success();
  }

  private Boolean validationIsError(List<FbaShipmentSales> dataList) {
    //用于验证重复全部集合的重复集合
    Set<String> allSet = new HashSet<>();
    //是否有错误数据
    Boolean flag = Boolean.FALSE;
    for (FbaShipmentSales i : dataList) {
      String amazonOrderId = i.getAmazonOrderId();
      String salOrg =i.getSalesOrganization();
      if (StrUtil.isEmpty(amazonOrderId)  || StrUtil.isEmpty(salOrg)) {
        //不为空校验
        i.setUploadRemark("AmazonOrderId和销售组织不能为空!");
        flag = Boolean.TRUE;
        continue;
      }

      String sb = amazonOrderId + salOrg;
      //全部集合已存在则在重复集合中添加
      if (allSet.contains(sb)) {
        i.setUploadRemark("数据重复!");
        flag = Boolean.TRUE;
        continue;
      }
      allSet.add(sb);

      Map<String, String> orgCodeMap = erpWarehouseCodeService.getOrgCodeMap();
      String salOrgCode = orgCodeMap.get(salOrg);
      if (StrUtil.isEmpty(salOrgCode)) {
        i.setUploadRemark(StrUtil.format("{}无法获取销售组织编码!", salOrg));
        flag = Boolean.TRUE;
      }

    }
    return flag;
  }

  private String dealImportErrorList(List<FbaShipmentSales> errorList) {
    String filePath = System.getProperty("user.dir") + "/upload/";
    String fileName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
    OutputStream out = null;
    try {
      out = new FileOutputStream(filePath + fileName, false);
      EasyExcel.write(out, FbaShipmentSalesVO.class)
          .sheet("导入结果").doWrite(errorList);
    } catch (FileNotFoundException e) {
      log.error("导入Excel异常数据导出异常", e);
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          log.error("导入Excel异常数据导出流关闭异常", e);
        }
      }
    }
    return fileName;
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData addNewOrg(){
    String amazonOrders = financeDatabase + ".AMAZON_ORDERS";
    String amazonOrderDetail = financeDatabase + ".AMAZON_ORDER_DETAIL";
    String settlementDetail = financeDatabase + ".CW_SETTLEMENT_DETAIL";
    try {
      //1、平台为null的先去订单表刷平台
      this.baseMapper.refreshOrderPlatform(amazonOrders, amazonOrderDetail);

      //2-1、刷组织名称（Amazon_账号_站点）
      this.baseMapper.addOrgName();

      //2-2、刷仓库组织名称（Amazon_账号_站点_仓库）
      this.baseMapper.addWarehouseOrgName();

      //3、根据组织名称刷库存组织编码
      this.baseMapper.refreshOrgCode();

      //4、根据仓库组织名称刷仓库组织编码
      this.baseMapper.refreshWareOrgCode();

      //5、匹配订单表刷销售组织名称（平台_账号_站点，只刷平台为Amazon的）
      this.baseMapper.addSalOrg();

      //6、针对eBay刷销售组织名称
      this.baseMapper.addEBaySalOrg();

      //7、针对Rakuten刷销售组织名称
      this.baseMapper.addRakutenSalOrg();

      //其实我分析得出亚马逊给过来的FBA customer shipment sales报告的AMAZON_ORDER_ID其实就是对应亚马逊订单报告的MERCHANT_ORDER_ID
      //8、销售组织为空的，拿到FBA_SHIPMENT_SALES的AMAZON_ORDER_ID去匹配AMAZON_ORDERS的MERCHANT_ORDER_ID，再根据AMAZON_ORDER_DETAIL拿到销售平台、账号、站点（只刷平台为Amazon的）
      this.baseMapper.refreshOrderSalOrg(amazonOrders, amazonOrderDetail);

      //9、针对B2B情况特殊处理，FBA_SHIPMENT_SALES存在B2B平台解析出来是Amazon，需要替换销售组织编码位：B2B_MK_ALL
      this.baseMapper.updateB2BSalOrg();

      //10、针对shopify情况特殊处理，FBA_SHIPMENT_SALES存在B2B平台解析出来是Amazon，需要替换销售组织编码位：shopify_Glucoracy_ALL
      this.baseMapper.addShopifySalOrg();

      //11、拿到OrderId去结算报告里匹配销售组织名称（平台_账号_站点，只刷平台为Amazon的）(暂时不启用，先观察能不能刷出销售组织名称 20220701)
//      this.baseMapper.refreshSalOrg(settlementDetail);

      //12、根据销售组织名称刷销售组织编码
      this.baseMapper.refreshSalOrgCode();
      return ResponseData.success();
    } catch (Exception e){
      log.error("FBA customer shipment sales刷组织编码异常！", e.getMessage());
      return ResponseData.error("FBA customer shipment sales刷组织编码异常！");
    }
  }
}
