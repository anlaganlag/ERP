package com.tadpole.cloud.platformSettlement.modular.sales.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
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
import com.tadpole.cloud.platformSettlement.modular.sales.entity.AdvertisingBudget;
import com.tadpole.cloud.platformSettlement.modular.sales.entity.SalesVolumeTarget;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.AdvertisingBudgetMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.SalesVolumeTargetMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.AdvertisingBudgetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.AdvertisingBudgetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.IAdvertisingBudgetService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ISalesTargetService;
import com.tadpole.cloud.platformSettlement.modular.sales.service.ITargetBoardService;
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
 * 广告预算 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
@Service
@Slf4j
public class AdvertisingBudgetServiceImpl extends ServiceImpl<AdvertisingBudgetMapper, AdvertisingBudget> implements IAdvertisingBudgetService {

  @Resource
  private AdvertisingBudgetMapper mapper;
  @Autowired
  private ISalesTargetService salesTargetService;
  @Resource
  private SalesVolumeTargetMapper salesMapper;

  @DataSource(name = "sales")
  @Override
  public List<AdvertisingBudgetResult> list(AdvertisingBudgetParam param) {
    return mapper.list(param);
  }

  @DataSource(name = "sales")
  @Override
  public AdvertisingBudgetResult listSum(AdvertisingBudgetParam param) {
    return mapper.listSum(param);
  }

  @DataSource(name = "sales")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData confirm(AdvertisingBudgetParam param) {
    String account = LoginContext.me().getLoginUser().getAccount();
    if (StrUtil.isEmpty(account)) {
      return ResponseData.error("当前登录用户为空");
    }
    if (StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())) {
      return ResponseData.error("年份和版本不能为空");
    }
    UpdateWrapper<AdvertisingBudget> updateWrapper = new UpdateWrapper<>();
    updateWrapper
        .eq("year", param.getYear())
        .eq("version", param.getVersion())
        .set("CONFIRM_STATUS", 1)
        .set("CONFIRM_DATE", new Date())
        .set("CONFIRM_BY", account);
    this.mapper.update(null, updateWrapper);
    return ResponseData.success();
  }

  @DataSource(name = "sales")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData edit(AdvertisingBudgetParam param) {
    String account = LoginContext.me().getLoginUser().getAccount();
    if (StrUtil.isEmpty(account)) {
      return ResponseData.error("当前登录用户为空");
    }
    if (param.getId() == null || "0".equals(param.getId().toString())) {
      return ResponseData.error("ID不能为空");
    }
    AdvertisingBudget oneRecord = this.mapper.selectById(param.getId());
    if (oneRecord == null) {
      return ResponseData.error("无对应记录");
    }
    if ("1".equals(oneRecord.getConfirmStatus().toString())) {
      return ResponseData.error("数据已确认,无法修改");
    }

    QueryWrapper<SalesVolumeTarget> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("PLATFORM", param.getPlatform())
            .eq("DEPARTMENT", param.getDepartment())
            .eq("TEAM", param.getTeam())
            .eq(StrUtil.isNotEmpty(param.getShopName()),"SHOP_NAME", param.getShopName())
            .eq(StrUtil.isNotEmpty(param.getProductType()),"PRODUCT_TYPE", param.getProductType())
            .eq(StrUtil.isNotEmpty(param.getCompanyBrand()),"COMPANY_BRAND", param.getCompanyBrand())
            .eq("NEWOLD_PRODUCT", param.getNewoldProduct())
            .eq("YEAR", param.getYear())
            .eq("VERSION", param.getVersion())
            .last("AND ROWNUM = 1");

    SalesVolumeTarget salesVol = salesMapper.selectOne(queryWrapper);
    if (salesVol == null || salesVol.getSeasonOne() == null || salesVol.getSeasonTwo() == null
        || salesVol.getSeasonThree() == null || salesVol.getSeasonFour() == null) {
      return ResponseData.error(StrUtil.format("未匹配到{}{}当前维度销售额", param.getYear(), param.getVersion()));
    }
    BigDecimal s1 = salesVol.getSeasonOne();
    BigDecimal s2 = salesVol.getSeasonTwo();
    BigDecimal s3 = salesVol.getSeasonThree();
    BigDecimal s4 = salesVol.getSeasonFour();
    BigDecimal total = s1.add(s2).add(s3).add(s4);
    BigDecimal prop1 = param.getSeasonOneProportion();
    BigDecimal prop2 = param.getSeasonTwoProportion();
    BigDecimal prop3 = param.getSeasonThreeProportion();
    BigDecimal prop4 = param.getSeasonFourProportion();
    BigDecimal prop = param.getAdvertisingProportion();
    BigDecimal sumFirstTotal = total.multiply(prop);
    BigDecimal propFirstTotal = s1.multiply(prop1).add(s2.multiply(prop2)).add(s3.multiply(prop3)).add(s4.multiply(prop4));


    if (sumFirstTotal.divide(propFirstTotal,4,RoundingMode.HALF_UP).subtract(BigDecimal.ONE).abs().compareTo(
        new BigDecimal("0.001")) > 0){
      BigDecimal calProp = propFirstTotal.divide(total,4,RoundingMode.HALF_UP);
      return ResponseData.error(StrUtil
          .format("总金额{}和季度汇总{}不等记录，不能修改！ Q1到Q4销售额 {},{},{},{},计算正确广告占比为{}", sumFirstTotal, propFirstTotal,
              s1, s2, s3, s4,calProp));
    }
    param.setSeasonOneMoney(s1.multiply(prop1).setScale(2,RoundingMode.HALF_UP));
    param.setSeasonTwoMoney(s2.multiply(prop2).setScale(2,RoundingMode.HALF_UP));
    param.setSeasonThreeMoney(s3.multiply(prop3).setScale(2,RoundingMode.HALF_UP));
    param.setSeasonFourMoney(s4.multiply(prop4).setScale(2,RoundingMode.HALF_UP));
    param.setUpdateAt(new Date());
    param.setUpdateBy(account);
    this.mapper.updateById(getEntity(param));
    return ResponseData.success();
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getPlatformSelect() {
    QueryWrapper<AdvertisingBudget> wp = new QueryWrapper<>();
    wp = wp.select("platform").groupBy("platform").orderByAsc("platform");

    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getDepartmentSelect() {
    QueryWrapper<AdvertisingBudget> wp = new QueryWrapper<>();
    wp = wp.select("department").groupBy("department").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(TRANSLATE(department,'一二三四五六七八九','123456789'),'[0-9]+'))");
    return transformLowerCase(this.baseMapper.selectMaps(wp));

  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getTeamSelect() {
    QueryWrapper<AdvertisingBudget> wp = new QueryWrapper<>();
    wp = wp.select("team").groupBy("team").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(team,'[0-9]+'))");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getCompanyBrandSelect() {
    QueryWrapper<AdvertisingBudget> wp = new QueryWrapper<>();
    wp = wp.select("company_brand")
        .groupBy("company_brand").orderByAsc("company_brand");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getProductTypeSelect() {
    QueryWrapper<AdvertisingBudget> wp = new QueryWrapper<>();
    wp = wp.select("product_type")
        .groupBy("product_type").orderByAsc("product_type");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getYearSelect() {
    QueryWrapper<AdvertisingBudget> wp = new QueryWrapper<>();
    wp = wp.select("year").groupBy("year").orderByAsc("year");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getVersionSelect(String year) {
    QueryWrapper<AdvertisingBudget> wp = new QueryWrapper<>();
    wp.select("version")
        .eq(year != null && year.length() > 0, "year", year)
        .groupBy("version").orderByAsc("version");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  /**
   * 广告预算导入
   */
  @DataSource(name = "sales")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData upload(AdvertisingBudgetParam param, MultipartFile file, List<String> platformList,List<String> departmentTeamList,List<String> productTypeList,List<String> brandList,List<String> jpShops) {
    if (StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getCurrency())) {
      return ResponseData.error("年份及币别不能为空!");
    }
    if (file == null) {
      return ResponseData.error("上传文件为空!");
    }

    if(StringUtil.isEmpty(param.getVersion())) {
      Map<String, String> versionUnConfirmed = salesTargetService.isVersionUnConfirmed("ADVERTISING_BUDGET",param.getYear());
      if (versionUnConfirmed != null && new BigDecimal(BigInteger.ZERO).equals(versionUnConfirmed.get("CONFIRM_STATUS"))) {
        return ResponseData.error(StrUtil.format("广告预算当前年份{}存在未确认版本{},不能导入新版本", param.getYear(), versionUnConfirmed.get("VERSION")));
      }
    }
    log.info("广告预算导入开始");
    String account = LoginContext.me().getLoginUser().getName();
    BufferedInputStream buffer = null;
    try {
      buffer = new BufferedInputStream(file.getInputStream());
      BaseExcelListener listener = new BaseExcelListener<AdvertisingBudget>();
//      EasyExcel.read(buffer, AdvertisingBudget.class, listener).sheet().doRead();

      ExcelReader excelReader  = EasyExcel.read(file.getInputStream(), AdvertisingBudget.class, listener).build();
      excelReader.read(EasyExcel.readSheet("广告预算").build());

      List<AdvertisingBudget> dataList = listener.getDataList();
      if (CollectionUtil.isEmpty(dataList)) {
        return ResponseData.error("广告预算数据为空，无法导入！");
      }
      String version = param.getVersion();
      String year = param.getYear();
      String currency = param.getCurrency();
      //版本为空时获取最大版本
      if (StrUtil.isEmpty(version)) {
        String maxVersion = mapper.selectMaxVersionByYear(year);
        version = maxVersion == null ? "V1" : "V" + (
            Integer.parseInt(StrUtil.strip(maxVersion, "V")) + 1);
      }
      //版本状态是否已确认
      String status = mapper.selectOneStatusByYearVersion(year, version);
      if (StrUtil.isNotEmpty(status) && "1".equals(status)) {
        return ResponseData.error("不能导入！当前年度版本数据已确认!");
      }
      //异常数据集合
      Boolean isError = this.validationIsError(dataList, account, year, version, currency,platformList,departmentTeamList, productTypeList, brandList,jpShops);
      if (Boolean.TRUE.equals(isError)) {
        String fileName = this.dealImportErrorList(dataList);
        //导入失败
        return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在异常数据数据", fileName);
      }
      //删除未确认的数据
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
      log.error("广告预算导入异常:"+ e);
      return ResponseData.error("广告预算导入异常:"+e);
    } finally {
      if (buffer != null) {
        try {
          buffer.close();
        } catch (IOException e) {
          log.error("广告预算导入关闭流异常", e);
        }
      }
    }
  }

  private Boolean validationIsError(List<AdvertisingBudget> dataList, String account, String year,
      String version, String currency,List<String> platformList,List<String>departmentTeamList,List<String> productTypeList,List<String> brandList,List<String> jpShops) {
    //用于验证重复全部集合的重复集合
    Set<String> allSet = new HashSet<>();
    //是否有错误数据
    Boolean flag = Boolean.FALSE;
    for (AdvertisingBudget i : dataList) {
      if ("事业五部".equals(i.getDepartment())) {
        if(i.getShopName()!=null && !jpShops.contains(i.getShopName())){
          i.setUploadRemark(StrUtil.format("事业五部账号可选:[{}]",String.join( "、", jpShops)));
          flag = Boolean.TRUE;
          continue;
        }
      }
      SalesVolumeTarget salesVol = null;
      BigDecimal s1,s2,s3,s4,prop1,prop2,prop3,prop4;
      if ("2021".equals(year) && ("事业五部".equals(i.getDepartment()))) {
        if (i.getAdvertisingProportion() == null
            || i.getSeasonOneProportion() == null
            || i.getSeasonTwoProportion() == null
            || i.getSeasonThreeProportion() == null
            || i.getSeasonFourProportion() == null
        ) {
          //不为空校验
          i.setUploadRemark("广告占比不能为空!");
          flag = Boolean.TRUE;
          continue;
        }
        QueryWrapper<SalesVolumeTarget> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("PLATFORM", "DEPARTMENT","SUM(SEASON_ONE) SEASON_ONE","SUM(SEASON_TWO) SEASON_TWO","SUM(SEASON_THREE) SEASON_THREE","SUM(SEASON_FOUR) SEASON_FOUR")
            .eq("PLATFORM", i.getPlatform())
            .eq("DEPARTMENT", i.getDepartment())
            .eq(StrUtil.isNotEmpty(i.getTeam()),"TEAM", i.getTeam())
            .eq("YEAR", year)
            .eq("VERSION", version)
            .eq("CURRENCY", currency)
            .groupBy("PLATFORM", "DEPARTMENT");
        salesVol = salesMapper.selectOne(queryWrapper);

        if (salesVol == null || salesVol.getSeasonOne() == null || salesVol.getSeasonTwo() == null  || salesVol.getSeasonThree() == null || salesVol.getSeasonFour() == null) {
          i.setUploadRemark(StrUtil.format("未匹配到{}{}当前维度销售收入", year, version));
          flag = Boolean.TRUE;
          continue;
        }
        s1 = salesVol.getSeasonOne();
        s2 = salesVol.getSeasonTwo();
        s3 = salesVol.getSeasonThree();
        s4 = salesVol.getSeasonFour();
        BigDecimal total = s1.add(s2).add(s3).add(s4);
        prop1 = i.getSeasonOneProportion();
        prop2 = i.getSeasonTwoProportion();
        prop3 = i.getSeasonThreeProportion();
        prop4 = i.getSeasonFourProportion();
        BigDecimal prop = i.getAdvertisingProportion();
        BigDecimal sumFirstTotal = total.multiply(prop);
        BigDecimal propFirstTotal = s1.multiply(prop1).add(s2.multiply(prop2)).add(s3.multiply(prop3))
            .add(s4.multiply(prop4));
        if  (propFirstTotal.compareTo(BigDecimal.ZERO) == 0 ){
          continue;
        } else {
          if (sumFirstTotal.divide(propFirstTotal, 4, RoundingMode.HALF_UP).subtract(BigDecimal.ONE)
              .abs().compareTo(
                  new BigDecimal("0.001")) > 0) {
            BigDecimal calProp = propFirstTotal.divide(total, 4, RoundingMode.HALF_UP);
            i.setUploadRemark(StrUtil
                .format("广告占比金额{}和分季度占比求和金额{}不一致!,计算正确广告占比为{}", sumFirstTotal, propFirstTotal,
                    calProp));
            //导出时展示销售额相关数据
            i.setSalVolSeasonOne(s1);
            i.setSalVolSeasonTwo(s2);
            i.setSalVolSeasonThree(s3);
            i.setSalVolSeasonFour(s4);
            flag = Boolean.TRUE;
            continue;
          }
        }
      }
    else {
        if (i.getAdvertisingProportion() == null
            || i.getSeasonOneProportion() == null
            || i.getSeasonTwoProportion() == null
            || i.getSeasonThreeProportion() == null
            || i.getSeasonFourProportion() == null
            || i.getPlatform() == null
            || i.getDepartment() == null
            || i.getTeam() == null
        ) {
          //不为空校验
          i.setUploadRemark("平台、事业部、team及各季节占比均不能为空!");
          flag = Boolean.TRUE;
          continue;
        }
        if ("Amazon".equals(i.getPlatform()) && (i.getProductType() == null
            || i.getCompanyBrand() == null)) {
          i.setUploadRemark("Amazon平台运营大类、销售品牌不能为空!");
          flag = Boolean.TRUE;
          continue;
        }
        //验证平台,事业部,Team信息
        StringBuilder validInfo = new StringBuilder();
        String Platform = i.getPlatform().toUpperCase();
        if (!Platform.equals("B2B") && !platformList.contains(i.getPlatform())) {
          validInfo.append("平台有误!");
        }
        if (!departmentTeamList.contains(i.getDepartment())) {
          validInfo.append("事业有误!");
        }
        if (Platform.trim().equals("AMAZON") && !departmentTeamList.contains(i.getTeam())) {
          validInfo.append("Team有误!");
        }

        if (Platform.trim().equals("AMAZON") && !productTypeList.contains(i.getProductType())) {
          validInfo.append("运营大类有误!");
        }
        if (Platform.trim().equals("AMAZON") && !brandList.contains(i.getCompanyBrand())) {
          validInfo.append("销售品牌有误!");
        }
        if (validInfo.length() > 0) {
          i.setUploadRemark(validInfo.toString());
          flag = Boolean.TRUE;
          continue;
        }
        String sb = i.getPlatform() + i.getDepartment()
            + i.getTeam() +i.getShopName()+ i.getProductType() + i.getCompanyBrand()+i.getNewoldProduct();



        //全部集合已存在则在重复集合中添加
        if (allSet.contains(sb)) {
          i.setUploadRemark("平台-事业部-Team-账号-运营大类-销售品牌-收缩线-新旧品数据重复!");
          flag = Boolean.TRUE;
          continue;
        }
        allSet.add(sb);

        QueryWrapper<SalesVolumeTarget> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PLATFORM", i.getPlatform())
                .eq("DEPARTMENT", i.getDepartment())
                .eq("TEAM", i.getTeam())
                .eq(StrUtil.isNotEmpty(i.getShopName()),"SHOP_NAME", i.getShopName())
                .eq(StrUtil.isNotEmpty(i.getProductType()),"PRODUCT_TYPE", i.getProductType())
                .eq(StrUtil.isNotEmpty(i.getCompanyBrand()),"COMPANY_BRAND", i.getCompanyBrand())
                .eq("NEWOLD_PRODUCT", i.getNewoldProduct())
                .eq("YEAR", year)
                .eq("VERSION", version)
                .eq("CURRENCY", currency).last("AND ROWNUM = 1");
        salesVol = salesMapper.selectOne(queryWrapper);

        if (salesVol == null || salesVol.getSeasonOne() == null || salesVol.getSeasonTwo() == null
            || salesVol.getSeasonThree() == null || salesVol.getSeasonFour() == null) {
          i.setUploadRemark(StrUtil.format("未匹配到{}{}当前维度销售收入", year, version));
          flag = Boolean.TRUE;
          continue;
        }
        s1 = salesVol.getSeasonOne();
        s2 = salesVol.getSeasonTwo();
        s3 = salesVol.getSeasonThree();
        s4 = salesVol.getSeasonFour();
        BigDecimal total = s1.add(s2).add(s3).add(s4);
        prop1 = i.getSeasonOneProportion();
        prop2 = i.getSeasonTwoProportion();
        prop3 = i.getSeasonThreeProportion();
        prop4 = i.getSeasonFourProportion();
        BigDecimal prop = i.getAdvertisingProportion();
        BigDecimal sumFirstTotal = total.multiply(prop);
        BigDecimal propFirstTotal = s1.multiply(prop1).add(s2.multiply(prop2))
            .add(s3.multiply(prop3))
            .add(s4.multiply(prop4));
        if  (propFirstTotal.compareTo(BigDecimal.ZERO) == 0 ){
          continue;
        } else {
          if (sumFirstTotal.divide(propFirstTotal, 4, RoundingMode.HALF_UP).subtract(BigDecimal.ONE)
              .abs().compareTo(
                  new BigDecimal("0.001")) > 0) {
            BigDecimal calProp = propFirstTotal.divide(total, 4, RoundingMode.HALF_UP);
            i.setUploadRemark(StrUtil
                .format("广告占比金额{}和分季度占比求和金额{}不一致{}!,计算正确广告占比为{}", sumFirstTotal, propFirstTotal,
                    sumFirstTotal.divide(propFirstTotal, 4, RoundingMode.HALF_UP)
                        .subtract(BigDecimal.ONE).abs(),
                    calProp));
            //导出时展示销售额相关数据
            i.setSalVolSeasonOne(s1);
            i.setSalVolSeasonTwo(s2);
            i.setSalVolSeasonThree(s3);
            i.setSalVolSeasonFour(s4);
            flag = Boolean.TRUE;
            continue;
          }
        }
      }
        i.setCreateBy(account);
        i.setCreateAt(new Date());
        i.setUpdateAt(new Date());
        i.setSeasonOneMoney(s1.multiply(prop1).setScale(2, RoundingMode.HALF_UP));
        i.setSeasonTwoMoney(s2.multiply(prop2).setScale(2, RoundingMode.HALF_UP));
        i.setSeasonThreeMoney(s3.multiply(prop3).setScale(2, RoundingMode.HALF_UP));
        i.setSeasonFourMoney(s4.multiply(prop4).setScale(2, RoundingMode.HALF_UP));

    }
    return flag;
  }

  private String dealImportErrorList(List<AdvertisingBudget> errorList) {
    String filePath = System.getProperty("user.dir") + "/upload/";
    String fileName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
    OutputStream out = null;
    try {
      out = new FileOutputStream(filePath + fileName, false);
      EasyExcel.write(out, AdvertisingBudget.class)
          .sheet("广告预算").doWrite(errorList);
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

  private AdvertisingBudget getEntity(AdvertisingBudgetParam param) {
    AdvertisingBudget entity = new AdvertisingBudget();
    BeanUtil.copyProperties(param, entity);
    return entity;
  }
}
