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
import com.tadpole.cloud.platformSettlement.modular.sales.entity.NewProductBudget;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.NewProductBudgetMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.mapper.SalesTargetMapper;
import com.tadpole.cloud.platformSettlement.modular.sales.model.params.NewProductBudgetParam;
import com.tadpole.cloud.platformSettlement.modular.sales.model.result.NewProductBudgetResult;
import com.tadpole.cloud.platformSettlement.modular.sales.service.INewProductBudgetService;
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
 * 新品预算 服务实现类
 * </p>
 *
 * @author gal
 * @since 2022-03-07
 */
@Service
@Slf4j

public class NewProductBudgetServiceImpl extends ServiceImpl<NewProductBudgetMapper, NewProductBudget> implements INewProductBudgetService {

  @Resource
  private NewProductBudgetMapper mapper;
  @Autowired
  private ISalesTargetService salesTargetService;
  @Resource
  private SalesTargetMapper salesMapper;

  @DataSource(name = "sales")
  @Override
  public List<NewProductBudgetResult> list(NewProductBudgetParam param) {
    return mapper.list(param);
  }

  @DataSource(name = "sales")
  @Override
  public NewProductBudgetResult listSum(NewProductBudgetParam param) {
    return mapper.listSum(param);
  }

  @DataSource(name = "sales")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData confirm(NewProductBudgetParam param) {
    String account = LoginContext.me().getLoginUser().getAccount();
    if (StrUtil.isEmpty(account)) {
      return ResponseData.error("当前登录用户为空");
    }
    if (StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getVersion())) {
      return ResponseData.error("年份和版本不能为空");
    }
    try {
      UpdateWrapper<NewProductBudget> updateWrapper = new UpdateWrapper<>();
      updateWrapper
          .eq("year", param.getYear())
          .eq("version", param.getVersion())
          .set("CONFIRM_STATUS", 1)
          .set("CONFIRM_DATE", new Date())
          .set("CONFIRM_BY", account);
      this.mapper.update(null, updateWrapper);
      return ResponseData.success();
    } catch (Exception e) {
      log.error("新品预算确认异常，确认失败！", e);
      return ResponseData.error("新品预算确认异常，确认失败!"+e);
    }
  }

  @DataSource(name = "sales")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData edit(NewProductBudgetParam param) {
    try {
      String account = LoginContext.me().getLoginUser().getAccount();
      if (StrUtil.isEmpty(account)) {
        return ResponseData.error("当前登录用户为空");
      }
      if (param.getId() == null || "0".equals(param.getId().toString())) {
        return ResponseData.error("ID不能为空");
      }
      NewProductBudget oneRecord = this.mapper.selectById(param.getId());
      if (oneRecord == null) {
        return ResponseData.error("无对应记录");
      }
      if ("1".equals(oneRecord.getConfirmStatus().toString())) {
        return ResponseData.error("数据已确认,无法修改");
      }
      param.setUpdateAt(new Date());
      param.setUpdateBy(account);
      BigDecimal s1 =param.getSeasonOne() == null? BigDecimal.ZERO:param.getSeasonOne();
      BigDecimal s2 =param.getSeasonTwo() == null? BigDecimal.ZERO:param.getSeasonTwo();
      BigDecimal s3 =param.getSeasonThree() == null? BigDecimal.ZERO:param.getSeasonThree();
      BigDecimal s4 =param.getSeasonFour() == null? BigDecimal.ZERO:param.getSeasonFour();
      BigDecimal line1 =param.getSeasonOneLine() == null? BigDecimal.ZERO:param.getSeasonOneLine();
      BigDecimal line2 =param.getSeasonTwoLine() == null? BigDecimal.ZERO:param.getSeasonTwoLine();
      BigDecimal line3 =param.getSeasonThreeLine() == null? BigDecimal.ZERO:param.getSeasonThreeLine();
      BigDecimal line4 =param.getSeasonFourLine() == null? BigDecimal.ZERO:param.getSeasonFourLine();
      param.setSeasonOne(s1);
      param.setSeasonTwo(s2);
      param.setSeasonThree(s3);
      param.setSeasonFour(s4);
      param.setSeasonOneLine(line1);
      param.setSeasonTwoLine(line2);
      param.setSeasonThreeLine(line3);
      param.setSeasonFourLine(line4);

      param.setLineYearTarget(param.getYearTargetLine());

      if (line1 == null || line1.compareTo(BigDecimal.ZERO) == 0) {
        param.setSeasonOneProportion(BigDecimal.ZERO);
      } else {
        param.setSeasonOneProportion(s1.divide(line1, 4, RoundingMode.HALF_UP));
      }
      if (line2 == null || line2.compareTo(BigDecimal.ZERO) == 0) {
        param.setSeasonTwoProportion(BigDecimal.ZERO);
      } else {
        param.setSeasonTwoProportion(s2.divide(line2, 4, RoundingMode.HALF_UP));
      }
      if (line3 == null || line3.compareTo(BigDecimal.ZERO) == 0) {
        param.setSeasonThreeProportion(BigDecimal.ZERO);
      } else {
        param.setSeasonThreeProportion(s3.divide(line3, 4, RoundingMode.HALF_UP));
      }
      if (line4 == null || line4.compareTo(BigDecimal.ZERO) == 0) {
        param.setSeasonFourProportion(BigDecimal.ZERO);
      } else {
        param.setSeasonFourProportion(s4.divide(line4, 4, RoundingMode.HALF_UP));
      }
      this.mapper.updateById(getEntity(param));
      return ResponseData.success();
    } catch (Exception e) {
      log.error("新品预算更新异常，更新失败！", e);
      return ResponseData.error("新品预算更新异常，更新失败！"+e);
    }
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getProductTypeSelect() {
    QueryWrapper<NewProductBudget> wp = new QueryWrapper<>();
    wp = wp.select("product_type")
        .groupBy("product_type").orderByAsc("product_type");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getDepartmentSelect() {
    QueryWrapper<NewProductBudget> wp = new QueryWrapper<>();
    wp = wp.select("department")
        .groupBy("department").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(TRANSLATE(department,'一二三四五六七八九','123456789'),'[0-9]+',1))");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getSecondLabelSelect() {
    QueryWrapper<NewProductBudget> wp = new QueryWrapper<>();
    wp = wp.select("second_label")
        .groupBy("second_label").orderByAsc("second_label");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getYearSelect() {
    QueryWrapper<NewProductBudget> wp = new QueryWrapper<>();
    wp = wp.select("year").groupBy("year").orderByAsc("year");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  @DataSource(name = "sales")
  @Override
  public List<Map<String, Object>> getVersionSelect(String year) {
    QueryWrapper<NewProductBudget> wp = new QueryWrapper<>();
    wp
        .select("version")
        .eq(year != null && year.length() > 0, "year", year)
        .groupBy("version").orderByAsc("TO_NUMBER(REGEXP_SUBSTR(version,'[0-9]+',1))");
    return transformLowerCase(this.baseMapper.selectMaps(wp));
  }

  /**
   * 新品预算导入
   */
  @DataSource(name = "sales")
  @Override
  @Transactional(rollbackFor = Exception.class)
  public ResponseData upload(NewProductBudgetParam param, MultipartFile file, List<String> productTypeList, List<String> departmentList) {
    if (StrUtil.isEmpty(param.getYear()) || StrUtil.isEmpty(param.getCurrency())) {
      return ResponseData.error("年份及币别不能为空!");
    }
    if (file == null) {
      return ResponseData.error("上传文件为空!");
    }

    if(StringUtil.isEmpty(param.getVersion())) {
      Map<String, String> versionUnConfirmed = salesTargetService.isVersionUnConfirmed("NEW_PRODUCT_BUDGET",param.getYear());
      if (versionUnConfirmed != null && new BigDecimal(BigInteger.ZERO).equals(versionUnConfirmed.get("CONFIRM_STATUS"))) {
        return ResponseData.error(StrUtil.format("新品预算当前年份{}存在未确认版本{},不能导入新版本", param.getYear(), versionUnConfirmed.get("VERSION")));
      }
    }
    log.info("新品预算导入开始");
    String account = LoginContext.me().getLoginUser().getName();
    BufferedInputStream buffer = null;
    try {
      buffer = new BufferedInputStream(file.getInputStream());
      BaseExcelListener listener = new BaseExcelListener<NewProductBudget>();
//      EasyExcel.read(buffer, NewProductBudget.class, listener).sheet().doRead();

      ExcelReader excelReader  = EasyExcel.read(file.getInputStream(), NewProductBudget.class, listener).build();
      excelReader.read(EasyExcel.readSheet("新品预算").build());
      List<NewProductBudget> dataList = listener.getDataList();
      if (CollectionUtil.isEmpty(dataList)) {
        return ResponseData.error("导入数据新品预算数据为空，导入失败！");
      }
      //数据操作
      String version = param.getVersion();
      String year = param.getYear();
      String currency = param.getCurrency();
      //版本为空时获取最大版本再加一
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
      Boolean isError = this.validationIsError(dataList, account, year, version, currency,productTypeList,departmentList);

      if (isError) {
        String fileName = this.dealImportErrorList(dataList);
        //导入失败
        return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在异常数据数据", fileName);
      }
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
      log.error("新品预算导入异常:"+e);
      return ResponseData.error("新品预算导入异常:"+e);
    } finally {
      if (buffer != null) {
        try {
          buffer.close();
        } catch (IOException e) {
          log.error("新品预算导入关闭流异常", e);
        }
      }
    }
  }

  private Boolean validationIsError(List<NewProductBudget> dataList, String account, String year,
      String version, String currency,List<String> productTypeList,List<String>  departmentList) {

    //用于验证重复全部集合的重复集合
    Boolean flag = Boolean.FALSE;
    //不为空校验
    for (NewProductBudget i : dataList) {
      if ("2021".equals(year) && ("事业五部".equals(i.getDepartment()))) {
        continue;
      }
      else {
        if (i.getDepartment() == null
            || i.getProductType() == null

        ) {
          //不为空校验
          i.setUploadRemark("部门、运营大类不能为空!");
          flag = Boolean.TRUE;
          continue;
        }
        //验证平台,事业部,Team信息
        StringBuffer validInfo = new StringBuffer();
        if (!productTypeList.contains(i.getProductType())) {
          validInfo.append("运营大类有误!");
        }
        if (validInfo.length() > 0) {
          i.setUploadRemark(validInfo.toString());
          flag = Boolean.TRUE;
          continue;
        }
        //用于验证数据
        Set<String> allSet = new HashSet<>();
        String department = i.getDepartment() == null ? "" : i.getDepartment();
        String secondLabel = i.getSecondLabel() == null ? "" : i.getSecondLabel();
        String sb = new StringBuffer().append(department).append(i.getProductType()).toString();
        //全部集合已存在则在重复集合中添加
        if (allSet.contains(sb)) {
          i.setUploadRemark("事业部-运营大类重复! ");
          flag = Boolean.TRUE;
          continue;
        }
        allSet.add(sb);
      }
        i.setCreateBy(account);
        i.setCreateAt(new Date());
        i.setUpdateAt(new Date());
        BigDecimal s1 = i.getSeasonOne() == null ? BigDecimal.ZERO : i.getSeasonOne();
        BigDecimal s2 = i.getSeasonTwo() == null ? BigDecimal.ZERO : i.getSeasonTwo();
        BigDecimal s3 = i.getSeasonThree() == null ? BigDecimal.ZERO : i.getSeasonThree();
        BigDecimal s4 = i.getSeasonFour() == null ? BigDecimal.ZERO : i.getSeasonFour();
        BigDecimal line1 = i.getSeasonOneLine() == null ? BigDecimal.ZERO : i.getSeasonOneLine();
        BigDecimal line2 = i.getSeasonTwoLine() == null ? BigDecimal.ZERO : i.getSeasonTwoLine();
        BigDecimal line3 =
            i.getSeasonThreeLine() == null ? BigDecimal.ZERO : i.getSeasonThreeLine();
        BigDecimal line4 = i.getSeasonFourLine() == null ? BigDecimal.ZERO : i.getSeasonFourLine();
        i.setSeasonOne(s1);
        i.setSeasonTwo(s2);
        i.setSeasonThree(s3);
        i.setSeasonFour(s4);
        i.setSeasonOneLine(line1);
        i.setSeasonTwoLine(line2);
        i.setSeasonThreeLine(line3);
        i.setSeasonFourLine(line4);
        if (line1 == null || line1.compareTo(BigDecimal.ZERO) == 0) {
          i.setSeasonOneProportion(BigDecimal.ZERO);
        } else {
          i.setSeasonOneProportion(s1.divide(line1, 4, RoundingMode.HALF_UP));
        }
        if (line2 == null || line2.compareTo(BigDecimal.ZERO) == 0) {
          i.setSeasonTwoProportion(BigDecimal.ZERO);
        } else {
          i.setSeasonTwoProportion(s2.divide(line2, 4, RoundingMode.HALF_UP));
        }
        if (line3 == null || line3.compareTo(BigDecimal.ZERO) == 0) {
          i.setSeasonThreeProportion(BigDecimal.ZERO);
        } else {
          i.setSeasonThreeProportion(s3.divide(line3, 4, RoundingMode.HALF_UP));
        }
        if (line4 == null || line4.compareTo(BigDecimal.ZERO) == 0) {
          i.setSeasonFourProportion(BigDecimal.ZERO);
        } else {
          i.setSeasonFourProportion(s4.divide(line4, 4, RoundingMode.HALF_UP));
        }
      }
    return flag;
  }

  private String dealImportErrorList(List<NewProductBudget> errorList) {
    String filePath = System.getProperty("user.dir") + "/upload/";
    String fileName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
    OutputStream out = null;
    try {
      out = new FileOutputStream(filePath + fileName, false);


      EasyExcel.write(out, NewProductBudget.class).sheet("新品预算").doWrite(errorList);

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

  private NewProductBudget getEntity(NewProductBudgetParam param) {
    NewProductBudget entity = new NewProductBudget();
    BeanUtil.copyProperties(param, entity);
    return entity;
  }
}
