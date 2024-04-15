package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.FbaCustomerReturnsMapper;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.FbaCustomerReturnsParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.FbaCustomerReturnsResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.*;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
 * 亚马逊物流买家退货 服务实现类
 * </p>
 *
 * @author gal
 * @since 2021-11-24
 */
@Service
@Slf4j
public class FbaCustomerReturnsServiceImpl extends ServiceImpl<FbaCustomerReturnsMapper, FbaCustomerReturns> implements IFbaCustomerReturnsService {

  @Autowired
  private IZZDistributeMcmsService erpService;
  @Autowired
  private ISalesReturnService service;
  @Autowired
  private ISalesReturnDetailService detailService;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  @Autowired
  private IErpWarehouseCodeService erpWarehouseCodeService;
  @Value("${finance_database}")
  private String financeDatabase;
  /**
   * 生成列表标识
   */
  private static final String TOLIST = "TO_RETURN_LIST";

  @DataSource(name = "warehouse")
  @Override
  public PageResult<FbaCustomerReturns> findPageBySpec(FbaCustomerReturnsParam param) {
    List <String> sites = param.getSysSites();
    if (CollUtil.isNotEmpty(sites) && sites.contains("空")){
      param.setSysSite("空");
    }
    return new PageResult<>(this.baseMapper.customPageList(PageFactory.defaultPage(), param));
  }

  @DataSource(name = "warehouse")
  @Override
  public Integer getQuantity(FbaCustomerReturnsParam param) {
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
      BaseExcelListener listener = new BaseExcelListener<FbaCustomerReturns>();
      EasyExcel.read(buffer, FbaCustomerReturns.class, listener).sheet().doRead();
      List<FbaCustomerReturns> dataList = listener.getDataList();

      Map<String, String> orgCodeMap = erpWarehouseCodeService.getOrgCodeMap();
      Boolean isError = this.validationIsError(dataList);
      if (Boolean.TRUE.equals(isError)) {
        String fileName = this.dealImportErrorList(dataList);
        //导入失败
        return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在异常数据数据", fileName);
      }

      for (FbaCustomerReturns data: dataList) {
        String orderId = data.getOrderId();
        String salOrg = data.getSalesOrganization();
        String salOrgCode = orgCodeMap.get(salOrg);
        if (StrUtil.isNotEmpty(orderId) && StrUtil.isNotEmpty(salOrg) && StrUtil.isNotEmpty(salOrgCode)){
          UpdateWrapper<FbaCustomerReturns> updateWrapper = new UpdateWrapper<>();
          updateWrapper
              .eq("ORDER_ID", orderId)
              .eq("VERIFY_STATUS",0)
              .eq("GENERATE_STATUS",0)
              .isNull("SALES_ORGANIZATION_CODE")
              .set("SALES_ORGANIZATION", salOrg)
              .set("SALES_ORGANIZATION_CODE", salOrgCode)
              .set("UPDATE_BY", account)
              .set("UPDATE_TIME", DateUtil.date());
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
  public List<FbaCustomerReturns> export(FbaCustomerReturnsParam param) {
    Page pageContext = PageFactory.defaultPage();
    pageContext.setSize(Integer.MAX_VALUE);
    return this.baseMapper.customPageList(pageContext, param).getRecords();
  }

  @DataSource(name = "warehouse")
  @Override
  public void verify(FbaCustomerReturnsParam param) {
    UpdateWrapper<FbaCustomerReturns> updateWrapper = new UpdateWrapper<>();
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
  public Boolean verifyUpdateBatch(FbaCustomerReturnsParam param) {
    return this.baseMapper.verifyUpdateBatch(param);
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean updateBatch(List<FbaCustomerReturns> updateList) {
    return this.saveOrUpdateBatch(updateList);
  }

  @DataSource(name = "warehouse")
  @Override
  public void reject(FbaCustomerReturnsParam param) {
    UpdateWrapper<FbaCustomerReturns> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("ID", param.getId())
        .eq("VERIFY_STATUS", 0)
        .set("VERIFY_AT", new Date())
        .set("VERIFY_BY", LoginContext.me().getLoginUser().getAccount())
        .set("UPDATE_BY", LoginContext.me().getLoginUser().getAccount())
        .set("VERIFY_STATUS", 2);
    this.baseMapper.update(null, updateWrapper);
  }

  @DataSource(name = "warehouse")
  @Override
  public void rejectBatch(List<FbaCustomerReturnsParam> params) {
    FbaCustomerReturnsParam param =  new FbaCustomerReturnsParam();
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
  public ResponseData toSalesReturnList(FbaCustomerReturnsParam param)  {
    //从redis里面取值如果非空则为正在生成!
    if(redisTemplate.hasKey(TOLIST)){
      return ResponseData.error("正在生成中!");
    }
    try {
      //为空则设为正在生成!
      redisTemplate.boundValueOps(TOLIST).set("正在生成中!", Duration.ofSeconds(600));
      //获取销售退货列表单据头数据
      List<FbaCustomerReturnsResult> headerList = this.baseMapper.getListHeader(param);
      if (CollUtil.isEmpty(headerList)) {
        return ResponseData.error("无可生成的数据!");
      }
      //列表对应明细id分配Erp物料
      List<String>  detailIdList = new ArrayList<>() ;
      for (FbaCustomerReturnsResult header : headerList) {
        //生成销售出库头部
        SalesReturn out = getListEntity(header);
        out.setShopName(header.getSysShopsName());
        out.setSite(header.getSysSite());
        //拼接单据编号
        QueryWrapper<SalesReturn> wp = new QueryWrapper();
        //获取销售退货列表明细数据
        List<SalesReturnDetail> detailList = this.baseMapper.getDetailList(header);
        String billCodeBase = "XSTHD-" + header.getInventoryOrganizationCode() + "-" +
            header.getSalesOrganizationCode() + '-' +
            header.getYear() +
            header.getMonth() + "000";
        wp.likeRight("BILL_CODE", billCodeBase);
        int billCodeIdx = service.count(wp) ;
        wp.clear();
        List<List<SalesReturnDetail>> lists = ListUtil.split(detailList, 500);
        for (List<SalesReturnDetail> lst: lists) {
          if ( billCodeIdx == 0 ){
            out.setBillCode(billCodeBase);
          } else {
            out.setBillCode(billCodeBase+'-'+billCodeIdx);
          }
          service.save(out);
          lst.forEach(i->{i.setOutId(out.getId());i.setUpdateBy(LoginContext.me().getLoginUser().getAccount());});
          detailService.saveBatch(lst);
          detailIdList.addAll(lst.stream().map(i->i.getId().toString()).collect(Collectors.toList()));
          billCodeIdx++;
        }
        //更新销售退货源报告FBA_CUSTOMER_RETURNS的生成状态和单据编码
        header.setUpdateBy(LoginContext.me().getLoginUser().getAccount());
        header.setBillCode(billCodeBase);
        this.baseMapper.updateSrcList(header);
      }
      this.baseMapper.updateDetailList();
      this.baseMapper.updateFileDetailList();
      //获取对应物料和销售组织编码
      List<ZZDistributeMcms> assignList = new ArrayList<>() ;
      List<List<String>> lists = ListUtil.split(detailIdList, 1000);
      //正常取时分配
      for (List<String> lst: lists) {
        //批量获取组织编码和物料编码分配物料
        assignList.addAll(this.assignMaterialList(lst));
      }
      return ResponseData.success(assignList);
    } catch (Exception e) {
      log.error("生成销售退货列表异常，异常信息[{}]", e.getMessage());
      return ResponseData.error("生成失败!");
    }finally {
      redisTemplate.delete(TOLIST);
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public String getMaterial(FbaCustomerReturnsParam param) {
    return this.baseMapper.getMaterial(param);
  }

  @DataSource(name = "erpcloud")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assignMaterial(FbaCustomerReturnsParam param) {
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
    }  catch (Exception e) {
      e.printStackTrace();
      return ResponseData.error("生成列表失败!");
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData editSites(List<FbaCustomerReturnsParam> params) {
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

    for (FbaCustomerReturnsParam param : params) {
      //组织名称：平台_账号_站点
      String orgName = CommonTypeEnum.Amazon.getName()+"_" + param.getSysShopsName() + "_" + param.getSysSite();
      String orgCode = orgMap.get(orgName);
      if(StringUtils.isEmpty(orgCode)){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改站点失败，没有找到对应的仓库组织编码信息");
      }

      //仓库名称：平台_账号_站点_仓库
      String wareHouseName = CommonTypeEnum.Amazon.getName()+"_" + param.getSysShopsName() + "_" + param.getSysSite() + "_仓库";
      String wareHouseCode =  warehouseMap.get(wareHouseName);
      if(StringUtils.isEmpty(wareHouseCode)){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResponseData.error("修改站点失败，没有找到对应的仓库编码信息");
      }

      UpdateWrapper<FbaCustomerReturns> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID",param.getId()).isNull("SYS_SITE")
              .set("SYS_SITE",param.getSysSite())
              .set(CommonTypeEnum.ORG_CODE.getName(), orgCode)
              .set(CommonTypeEnum.ORG.getName(), orgName)
              .set(CommonTypeEnum.WARE_CODE.getName(), wareHouseCode)
              .set(CommonTypeEnum.WARE.getName(), wareHouseName);
      this.update(null,updateWrapper);
    }
    return ResponseData.success();
  }

  @DataSource(name = "warehouse")
  @Override
  public void editPlatform(List<FbaCustomerReturnsParam> params) {
    for (FbaCustomerReturnsParam param:params) {
      UpdateWrapper<FbaCustomerReturns> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID",param.getId())
              .set("SALES_PLATFORM",param.getPlatform());
      this.update(null,updateWrapper);
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData editShop(List<FbaCustomerReturnsParam> params) {
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

    for (FbaCustomerReturnsParam param:params) {
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

      UpdateWrapper<FbaCustomerReturns> updateWrapper = new UpdateWrapper<>();
      updateWrapper.eq("ID",param.getId())
              .set("SYS_SHOPS_NAME",param.getSysShopsName())
              .set(CommonTypeEnum.ORG_CODE.getName(), orgCode)
              .set(CommonTypeEnum.ORG.getName(), orgName)
              .set(CommonTypeEnum.WARE_CODE.getName(), wareHouseCode)
              .set(CommonTypeEnum.WARE.getName(), wareHouseName);
      this.update(null,updateWrapper);
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
  public ResponseData editSalesOrg(List<FbaCustomerReturnsParam> params) {
    try {
      for (FbaCustomerReturnsParam param:params) {
        UpdateWrapper<FbaCustomerReturns> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",param.getId())
                .isNull(CommonTypeEnum.SALES_ORG_CODE.getName())
                .set(CommonTypeEnum.SALES_ORG_CODE.getName(),param.getSalesOrganizationCode())
                .set(CommonTypeEnum.SALES_ORG.getName(),param.getSalesOrganization());
        this.update(null,updateWrapper);
      }
    }catch (Exception e){
      log.error("修改销售组织异常", e);
      return ResponseData.error("修改销售组织异常");
    }
    return ResponseData.success();
  }

  private Boolean validationIsError(List<FbaCustomerReturns> dataList) {
    //用于验证重复全部集合的重复集合
    Set<String> allSet = new HashSet<>();
    //是否有错误数据
    Boolean flag = Boolean.FALSE;
    for (FbaCustomerReturns i : dataList) {
      String amazonOrderId = i.getOrderId();
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

  private String dealImportErrorList(List<FbaCustomerReturns> errorList) {
    String filePath = System.getProperty("user.dir") + "/upload/";
    String fileName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
    OutputStream out = null;
    try {
      out = new FileOutputStream(filePath + fileName, false);
      EasyExcel.write(out, FbaCustomerReturnsVO.class)
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
  public ResponseData addInvWareOrg(){
    try {
      this.baseMapper.addOrgName();
      this.baseMapper.refreshOrgCode();
      this.baseMapper.refreshWareOrgCode();
      return ResponseData.success();
    } catch (Exception e){
      log.error("销售退货刷组织编码失败!", e);
      e.printStackTrace();
      return ResponseData.error(String.valueOf(e));
    }
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

      //2、刷组织名称（Amazon_账号_站点）和仓库组织名称（Amazon_账号_站点_仓库）
      this.baseMapper.addOrgName();

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

      //8、销售组织为空的，拿到FBA_SHIPMENT_SALES的AMAZON_ORDER_ID去匹配AMAZON_ORDERS的MERCHANT_ORDER_ID，再根据AMAZON_ORDER_DETAIL拿到销售平台、账号、站点（只刷平台为Amazon的）
      this.baseMapper.refreshOrderSalOrg(amazonOrders, amazonOrderDetail);

      //9、针对B2B情况特殊处理，FBA_SHIPMENT_SALES存在B2B平台解析出来是Amazon，需要替换销售组织编码位：B2B_MK_ALL
      this.baseMapper.updateB2BSalOrg();

      //10、拿到OrderId去结算报告里匹配销售组织名称（平台_账号_站点，只刷平台为Amazon的）(暂时不启用，先观察能不能刷出销售组织名称 20220701)
      this.baseMapper.refreshSalOrg(settlementDetail);

      //11、根据销售组织名称刷销售组织编码
      this.baseMapper.refreshSalOrgCode();
      return ResponseData.success();
    } catch (Exception e){
      log.error("销售出库刷组织编码失败!", e);
      e.printStackTrace();
      return ResponseData.error(String.valueOf(e));
    }
  }

  @DataSource(name = "warehouse")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<FbaCustomerReturns> remainList() {
    return this.baseMapper.remainList();
  }

  private SalesReturn getListEntity(FbaCustomerReturnsResult param) {
    SalesReturn entity = new SalesReturn();
    BeanUtil.copyProperties(param, entity);
    return entity;
  }
}
