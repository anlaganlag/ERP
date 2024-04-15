package com.tadpole.cloud.platformSettlement.modular.sales.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.sales.entity.StockMonitor;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.InventoryDemand;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.SalesTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.InventoryDemandMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.SalesTargetMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.InventoryDemandParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.SalesTargetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.InventoryDemandResult;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.SalesTargetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.IInventoryDemandService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ISalesTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

/**
 * <p>
 * 存货需求 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-03-03
 */
@Service
@Slf4j
public class InventoryDemandServiceImpl extends ServiceImpl<InventoryDemandMapper, InventoryDemand> implements IInventoryDemandService {

  @Resource
  private InventoryDemandMapper mapper;
  @Autowired
  private ISalesTargetService salesTargetService;
  @Resource
  private SalesTargetMapper salesMapper;

  @DataSource(name = "sales")
  @Override
  public List<InventoryDemandResult> list(InventoryDemandParam param) {
    return mapper.list(param);
  }


  @DataSource(name = "sales")
  @Override
  public List<StockMonitor> stockMonitorHead(String department) {
    long quarterLeftDays = getQuarterLeftDays();
    return mapper.stockMonitorHead(department);
  }

  private long getQuarterLeftDays() {
    Date now = DateUtil.date();
    int curYear = DateUtil.year(DateUtil.date());
    int QuarterLastMon = DateUtil.quarter(DateUtil.date())*3;
    String QuarterLastMonDateStr = curYear+"-"+QuarterLastMon+"-01";

    Date quarterLastMonDate = DateUtil.parse(QuarterLastMonDateStr);
    Date quarterEndDate = DateUtil.endOfMonth(quarterLastMonDate);
    return DateUtil.between(quarterEndDate,now, DateUnit.DAY);
  }



  @DataSource(name = "sales")
  @Override
  public InventoryDemandResult listSum(InventoryDemandParam param) {
    InventoryDemandResult invResult = mapper.listSum(param);
    if (invResult == null) {
      return null;
    }
    List<String> distinctDeptList = mapper.distinctDept(param.getYear(),param.getVersion());
    SalesTargetParam salesTargetParam = new SalesTargetParam();
    BeanUtil.copyProperties(param, salesTargetParam);
    //全量查询自动过滤小平台只保留Amazon和事业五部的乐天
    if (CollUtil.isEmpty(param.getPlatforms()) && CollUtil.isEmpty(param.getDepartments()) && CollUtil.isEmpty(param.getTeams()) && CollUtil.isEmpty(param.getProductTypes())
              && CollUtil.isEmpty(param.getCompanyBrands()) && CollUtil.isNotEmpty(distinctDeptList) ) {
      salesTargetParam.setDepartments(distinctDeptList);
    }
    SalesTargetResult salResult= salesMapper.getQuantity(salesTargetParam);
    if (salResult == null) {
      return null;
    }
    BigDecimal targetInventory = invResult.getTargetInventoryTotal();
    BigDecimal yearTarget = salResult.getYearTarget();

    if (targetInventory!= null && yearTarget != null && yearTarget.compareTo(BigDecimal.ZERO) > 0){
      invResult.setTargetInventorySalesRatio(targetInventory.divide(yearTarget,4 ,RoundingMode.HALF_UP));
    }else {
      return null;
    }
    return invResult;
  }


  @DataSource(name = "sales")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData confirm(InventoryDemandParam param) {
    String account = LoginContext.me().getLoginUser().getAccount();
    if (StrUtil.isEmpty(account)) {
      return ResponseData.error("当前登录用户为空");
    }
    if (StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())) {
      return ResponseData.error("年份和版本不能为空");
    }
    try {
      UpdateWrapper<InventoryDemand> updateWrapper = new UpdateWrapper<>();
      updateWrapper
          .eq("year", param.getYear())
          .eq("version", param.getVersion())
          .set("CONFIRM_STATUS", 1)
          .set("CONFIRM_DATE", new Date())
          .set("CONFIRM_BY", account);
      this.mapper.update(null, updateWrapper);
      return ResponseData.success();
    }catch (Exception e) {
      log.error("存货需求确认异常，确认失败！", e);
      return ResponseData.error("存货需求确认异常，确认失败!");    }
  }


  @DataSource(name = "sales")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData edit(InventoryDemandParam param) {
    try {
      String account = LoginContext.me().getLoginUser().getAccount();
      if (StrUtil.isEmpty(account)) {
        return ResponseData.error("当前登录用户为空");
      }
      if (param.getId() == null || "0".equals(param.getId().toString())) {
        return ResponseData.error("ID不能为空");
      }
      InventoryDemand oneRecord = this.mapper.selectById(param.getId());
      if (oneRecord == null) {
        return ResponseData.error("无对应记录");
      }
      if ("1".equals(oneRecord.getConfirmStatus().toString())) {
        return ResponseData.error("数据已确认,无法修改");
      }
      if (param.getTargetInventorySalesRatio() == null) {
        return ResponseData.error("目标库销比不能为空!");
      }
      QueryWrapper<SalesTarget> queryWrapper = new QueryWrapper<>();
      String year = param.getYear();
      String version = param.getVersion();
      String currency = param.getCurrency();



      //1.事业五部和BTB组   DEPARTMENT+ Team 2.平台发展组 平台发展部+非BTB组  3.Amazon和Walmart  PLATFORM+DEPARTMENT+TEAM+PRODUCT_TYPE
      if (param.getDepartment().equals("事业五部") ||  param.getTeam().equals("BTB组")) {
        queryWrapper
                .select("DEPARTMENT,TEAM,YEAR,VERSION,SUM(SEASON_ONE) SeasonOne,SUM(SEASON_TWO) SeasonTwo,SUM(SEASON_THREE) SeasonThree,SUM(SEASON_FOUR) SeasonFour")
                .eq("YEAR", year)
                .eq("VERSION", version)
                .eq("CURRENCY", currency)
                .eq("DEPARTMENT", param.getDepartment())
                .eq("TEAM", param.getTeam())
                .groupBy("DEPARTMENT", "TEAM", "YEAR", "VERSION");
      } else if ( param.getTeam().equals("平台发展组")) {
        queryWrapper
                .select("DEPARTMENT,YEAR,VERSION,SUM(SEASON_ONE) SeasonOne,SUM(SEASON_TWO) SeasonTwo,SUM(SEASON_THREE) SeasonThree,SUM(SEASON_FOUR) SeasonFour")
                .eq("YEAR", year)
                .eq("VERSION", version)
                .eq("CURRENCY", currency)
                .eq("DEPARTMENT", param.getDepartment())
                .notLike("TEAM","BTB组" )
                .groupBy("DEPARTMENT", "YEAR", "VERSION");
        // 1.Az非5部和沃尔玛
      }else if(param.getPlatform().equals("Amazon") || param.getPlatform().equals("Walmart")){
        queryWrapper
                .select("PLATFORM,DEPARTMENT,TEAM,PRODUCT_TYPE,YEAR,VERSION,SUM(SEASON_ONE) SeasonOne,SUM(SEASON_TWO) SeasonTwo,SUM(SEASON_THREE) SeasonThree,SUM(SEASON_FOUR) SeasonFour")
                .eq("YEAR", year)
                .eq("VERSION", version)
                .eq("CURRENCY", currency)
                .eq("PLATFORM", param.getPlatform())
                .eq("DEPARTMENT", param.getDepartment())
                .eq("TEAM", param.getTeam())
                .eq(StrUtil.isNotEmpty(param.getProductType()),"PRODUCT_TYPE", param.getProductType())
                .groupBy("PLATFORM","DEPARTMENT","TEAM","PRODUCT_TYPE","YEAR","VERSION");
      }
      SalesTarget salesAmt = salesMapper.selectOne(queryWrapper);
      if (salesAmt == null || salesAmt.getSeasonOne() == null || salesAmt.getSeasonTwo() == null
          || salesAmt.getSeasonThree() == null || salesAmt.getSeasonFour() == null ) {
        return ResponseData.error(StrUtil.format("未匹配到{}{}当前维度销售数量", year, param.getVersion()));
      }
      BigDecimal calTargetInventory = param.getTargetInventorySalesRatio().multiply(
          salesAmt.getSeasonOne().add(salesAmt.getSeasonTwo()).add(salesAmt.getSeasonThree())
              .add(salesAmt.getSeasonFour()).setScale(0,
              RoundingMode.HALF_UP));
      param.setTargetInventory(calTargetInventory);
      param.setUpdateAt(new Date());
      param.setUpdateBy(account);
      this.mapper.updateById(getEntity(param));
      return ResponseData.success();
    } catch (Exception e) {
      log.error("库存需求修改异常，修改失败！", e);
      return ResponseData.error("库存需求修改异常，修改失败！");
    }
  }


  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getPlatformSelect() {
    QueryWrapper<InventoryDemand> wp = new QueryWrapper<>();
    wp = wp.select("platform").groupBy("platform").orderByAsc("platform");

    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getDepartmentSelect() {
    QueryWrapper<InventoryDemand> wp = new QueryWrapper<>();
    wp = wp.select("department").groupBy("department").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(TRANSLATE(department,'一二三四五六七八九','123456789'),'[0-9]+',1))");
    return transformLowerCase(this.baseMapper.selectMaps(wp));

  }


  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getTeamSelect() {
    QueryWrapper<InventoryDemand> wp = new QueryWrapper<>();
    wp =
        wp.select("team").groupBy("team").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(team,'[0-9]+'))");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }


  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getCompanyBrandSelect() {
    QueryWrapper<InventoryDemand> wp = new QueryWrapper<>();
    wp = wp.select("company_brand")
        .groupBy("company_brand").orderByAsc("company_brand");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getProductTypeSelect() {
    QueryWrapper<InventoryDemand> wp = new QueryWrapper<>();
    wp = wp.select("product_type")
        .groupBy("product_type").orderByAsc("product_type");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getYearSelect() {
    QueryWrapper<InventoryDemand> wp = new QueryWrapper<>();
    wp = wp.select("year").groupBy("year").orderByAsc("year");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getVersionSelect(String year) {
    QueryWrapper<InventoryDemand> wp = new QueryWrapper<>();
    wp
        .select("version")
        .eq(year != null && year.length() > 0, "year", year)
        .groupBy("version").orderByAsc("version");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }


  /**
   * 库存需求导入
   */
  @DataSource(name = "sales")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData upload(InventoryDemandParam param, MultipartFile file,  List<String> platformList,List<String> departmentTeamList,List<String> productTypeList,List<String> brandList) {
    if (StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getCurrency())) {
      return ResponseData.error("年份及币别不能为空!");
    }
    if (file == null) {
      return ResponseData.error("上传文件为空!");
    }

    if(StringUtil.isEmpty(param.getVersion())) {
      Map<String, String> versionUnConfirmed = salesTargetService.isVersionUnConfirmed("INVENTORY_DEMAND",param.getYear());
      if (versionUnConfirmed != null && new BigDecimal(BigInteger.ZERO).equals(versionUnConfirmed.get("CONFIRM_STATUS"))) {
        return ResponseData.error(StrUtil.format("存货需求当前年份{}存在未确认版本{},不能导入新版本", param.getYear(), versionUnConfirmed.get("VERSION")));
      }
    }
    log.info("库存需求导入开始");
    String account = LoginContext.me().getLoginUser().getName();
    BufferedInputStream buffer = null;
    try {
      buffer = new BufferedInputStream(file.getInputStream());
      BaseExcelListener listener = new BaseExcelListener<InventoryDemand>();
//      EasyExcel.read(buffer, InventoryDemand.class, listener).sheet().doRead();

      ExcelReader excelReader  = EasyExcel.read(file.getInputStream(), InventoryDemand.class, listener).build();
      excelReader.read(EasyExcel.readSheet("存货").build());

      List<InventoryDemand> dataList = listener.getDataList();
      if (CollectionUtil.isEmpty(dataList)) {
        return ResponseData.error("导入数据存货数据为空，导入失败！");
      }
      //数据操作
      String version = param.getVersion();
      String year = param.getYear();
      String currency = param.getCurrency();
      //版本为空时获取最大版本
      if (StrUtil.isEmpty(version)) {
        String maxVersion = mapper.selectMaxVersionByYear(year);
        version = maxVersion == null ? "V1" : "V" + (
            Integer.parseInt(StrUtil.strip(maxVersion, "V")) + 1);
      }
      String status = mapper.selectOneStatusByYearVersion(year, version);
      if (StrUtil.isNotEmpty(status) && "1".equals(status)) {
        return ResponseData.error("更新失败，当前年度版本数据已确认");
      }
      //异常数据集合
      Boolean isError = this.validationIsError(dataList, account, year, version, currency,platformList,departmentTeamList, productTypeList, brandList);

      if (isError) {
        String fileName = this.dealImportErrorList(dataList);
        //导入失败
        return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在异常数据数据", fileName);
      }
      //版本为空时,根据年度查出数据库中存在最大版本 Vn，版本设置为Vn+1;
      if (StrUtil.isNotEmpty(status) && "0".equals(status)) {
        mapper.deleteByByYearVersion(year, version);
      }
      String finalYear = year;
      String finalVersion = version;
      dataList.stream().forEach(i -> {
        i.setVersion(finalVersion);
        i.setYear(finalYear);
        i.setCurrency(currency);
      });
      this.saveBatch(dataList);
      return ResponseData.success("导入成功！");
    } catch (Exception e) {
      log.error("库存需求导入异常:"+e);
      return ResponseData.error("库存需求导入异常:"+e);
    } finally {
      if (buffer != null) {
        try {
          buffer.close();
        } catch (IOException e) {
          log.error("库存需求导入关闭流异常", e);
        }
      }
    }
  }


  @DataSource(name = "sales")
  @Override
  public StockMonitor stockMonitorHeadDept(String department) {

    long quarterLeftDays = getQuarterLeftDays();
    return mapper.stockMonitorHeadDept(department);

  }

  private Boolean validationIsError(List<InventoryDemand> dataList, String account, String year,
      String version, String currency,List<String> platformList,List<String>departmentTeamList,List<String> productTypeList,List<String> brandList) {
    Boolean flag = Boolean.FALSE;
    for (InventoryDemand i : dataList) {
      SalesTarget salesAmt = null;
      String department = i.getDepartment();
      //事业五部单校验,无Team、运营大类、销售品牌
        //不为空校验
        if (!department.equals("事业五部") && !department.equals("平台发展部") && (i.getSeasonOne() == null
            || i.getSeasonTwo() == null
            || i.getSeasonThree() == null
            || i.getSeasonFour() == null
            || i.getPlatform() == null
            || i.getDepartment() == null
            || i.getTeam() == null
        )) {
          i.setUploadRemark("平台、事业部、team及各季节均不能为空!");
          flag = Boolean.TRUE;
          continue;
        }
        if ("Amazon".equalsIgnoreCase(i.getPlatform()) && !"事业五部".equals(i.getDepartment()) &&  i.getProductType() == null ) {
          i.setUploadRemark("运营大类不能为空!");
          flag = Boolean.TRUE;
          continue;
        }

        //验证平台,事业部,Team信息
        StringBuffer validInfo = new StringBuffer();
        String platform = i.getPlatform();
        if (!department.equals("事业五部") && !department.equals("平台发展部")  && !platformList.contains(i.getPlatform())) {
          validInfo.append("平台有误!");
        }
        if (!departmentTeamList.contains(i.getDepartment())) {
          validInfo.append("事业部有误!");
        }
        if (ObjectUtil.isNotEmpty(platform) && platform.trim().equals("Amazon") && !departmentTeamList.contains(i.getTeam())) {
          validInfo.append("Team有误!");
        }

        if (!department.equals("事业五部") && !department.equals("平台发展部") && !productTypeList.contains(i.getProductType())) {
          validInfo.append("运营大类有误!");
        }


        if (validInfo.length() > 0) {
          i.setUploadRemark(validInfo.toString());
          flag = Boolean.TRUE;
          continue;
        }
        //用于验证数据
        Set<String> allSet = new HashSet<>();
        String sb = new StringBuffer().append(i.getPlatform()).append(i.getDepartment())
            .append(i.getTeam()).append(i.getProductType()).append(i.getCompanyBrand()).toString();
        //全部集合已存在则在重复集合中添加
        if (allSet.contains(sb)) {
          i.setUploadRemark("平台-事业部-Team-运营大类-销售品牌数据重复! ");
          flag = Boolean.TRUE;
          continue;
        }
        allSet.add(sb);
        QueryWrapper<SalesTarget> queryWrapper = new QueryWrapper<>();


        //1.事业五部和BTB组   DEPARTMENT+ Team 2.平台发展组 平台发展部+非BTB组  3.Amazon和Walmart  PLATFORM+DEPARTMENT+TEAM+PRODUCT_TYPE
        if (i.getDepartment().equals("事业五部") || (i.getDepartment().equals("平台发展部") && i.getTeam().equals("BTB组"))) {
          queryWrapper
                  .select("DEPARTMENT,TEAM,YEAR,VERSION,SUM(SEASON_ONE) SeasonOne,SUM(SEASON_TWO) SeasonTwo,SUM(SEASON_THREE) SeasonThree,SUM(SEASON_FOUR) SeasonFour")
                  .eq("YEAR", year)
                  .eq("VERSION", version)
                  .eq("CURRENCY", currency)
                  .eq("DEPARTMENT", i.getDepartment())
                  .eq("TEAM", i.getTeam())
                  .groupBy("DEPARTMENT", "TEAM", "YEAR", "VERSION");
        } else if (i.getDepartment().equals("平台发展部") && i.getTeam().equals("平台发展组")) {
              queryWrapper
                      .select("DEPARTMENT,YEAR,VERSION,SUM(SEASON_ONE) SeasonOne,SUM(SEASON_TWO) SeasonTwo,SUM(SEASON_THREE) SeasonThree,SUM(SEASON_FOUR) SeasonFour")
                      .eq("YEAR", year)
                      .eq("VERSION", version)
                      .eq("CURRENCY", currency)
                      .eq("DEPARTMENT", i.getDepartment())
                      .notLike("TEAM","BTB组" )
                      .groupBy("DEPARTMENT", "YEAR", "VERSION");
         // 1.Az非5部和沃尔玛
        }else if(i.getPlatform().equals("Amazon") || i.getPlatform().equals("Walmart")){
          queryWrapper
                  .select("PLATFORM,DEPARTMENT,TEAM,PRODUCT_TYPE,YEAR,VERSION,SUM(SEASON_ONE) SeasonOne,SUM(SEASON_TWO) SeasonTwo,SUM(SEASON_THREE) SeasonThree,SUM(SEASON_FOUR) SeasonFour")
                  .eq("YEAR", year)
                  .eq("VERSION", version)
                  .eq("CURRENCY", currency)
                  .eq("PLATFORM", i.getPlatform())
                  .eq("DEPARTMENT", i.getDepartment())
                  .eq("TEAM", i.getTeam())
                  .eq(StrUtil.isNotEmpty(i.getProductType()),"PRODUCT_TYPE", i.getProductType())
                  .groupBy("PLATFORM","DEPARTMENT","TEAM","PRODUCT_TYPE","YEAR","VERSION");
        }
      List<SalesTarget> salesTargets = salesMapper.selectList(queryWrapper);
      if (salesTargets.size() <1) {
        i.setUploadRemark(StrUtil.format("未匹配到{}{}当前维度销售数量", year, version));
        flag = Boolean.TRUE;
        continue;
      }
      if (salesTargets.size() >1) {
        i.setUploadRemark(StrUtil.format("匹配到{}{}多条当前维度销售数量", year, version));
        flag = Boolean.TRUE;
        continue;
      }
       salesAmt = salesTargets.get(0);
      if (salesAmt == null || salesAmt.getSeasonOne() == null || salesAmt.getSeasonTwo() == null
            || salesAmt.getSeasonThree() == null || salesAmt.getSeasonFour() == null) {
          i.setUploadRemark(StrUtil.format("未匹配到{}{}当前维度销售数量", year, version));
          flag = Boolean.TRUE;
          continue;
        }
        //正常数据
      i.setCreateBy(account);
      i.setCreateAt(new Date());
      i.setUpdateAt(new Date());
      i.setTargetInventory(i.getTargetInventorySalesRatio().multiply(
          salesAmt.getSeasonOne().add(salesAmt.getSeasonTwo()).add(salesAmt.getSeasonThree())
              .add(salesAmt.getSeasonFour()).setScale(0,
              RoundingMode.HALF_UP)));
    }
    return flag;
  }

  private String dealImportErrorList(List<InventoryDemand> errorList) {
    String filePath = System.getProperty("user.dir") + "/upload/";
    String fileName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
    OutputStream out = null;
    try {
      out = new FileOutputStream(filePath + fileName, false);
      EasyExcel.write(out, InventoryDemand.class)
          .sheet("存货").doWrite(errorList);
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


  public static List<Map<String, Object>> transformLowerCase(List<Map<String, Object>> list) {
    List<Map<String, Object>> resultList = new ArrayList<>();
    if (CollUtil.isEmpty(list)) {
      return resultList;
    }
    for (Map<String, Object> mp : list) {
      if (mp == null) {
        continue;
      }
      Map<String, Object> resultMap = new HashMap<>(1024);
      Set<String> keySet = mp.keySet();
      for (String key : keySet) {
        String newKey = key.toLowerCase();
        resultMap.put(newKey, mp.get(key));
      }
      resultList.add(resultMap);
    }
    return resultList;
  }

  private InventoryDemand getEntity(InventoryDemandParam param) {
    InventoryDemand entity = new InventoryDemand();
    BeanUtil.copyProperties(param, entity);
    return entity;
  }


}
