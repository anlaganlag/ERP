package com.tadpole.cloud.platformSettlement.modular.finance.listener;

import cn.hutool.core.util.StrUtil;
import com.tadpole.cloud.platformSettlement.api.finance.entity.InitialBalance;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.InitialBalanceResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFinancialSiteService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IInitialBalanceService;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IShopCurrencyService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitialBalanceListener extends AnalysisEventListener<InitialBalanceResult> {

  Logger logger = LoggerFactory.getLogger(InitialBalanceListener.class);

  //每次读取100条数据就进行保存操作
  private static final int BATCH_COUNT = 100;
  //由于每次读都是新new InitialBalanceDataListener的，所以这个list不会存在线程安全问题

  private IInitialBalanceService service;
  private IShopCurrencyService shopService;
  private IFinancialSiteService siteService;
  List<InitialBalanceResult> list = new ArrayList<>();
  List<InitialBalanceResult> failList = new ArrayList<>();
  private HashSet<String> set = new HashSet<String>();

  //这个组件是Spring中的组件，这边推荐两种方法注入这个组件
  //第一种就是提供一个InitialBalanceDataListener的构造方法，这个方法提供一个参数是InitialBalanceDataListener类型
  //另外一种方法就是将 InitialBalanceDataListener 这个类定义成 UserService 实现类的内部类（推荐这种方式）
  //private UserService userService;
  public InitialBalanceListener(IInitialBalanceService service,IShopCurrencyService shopService,IFinancialSiteService siteService) {
    this.service = service;
    this.shopService = shopService;
    this.siteService = siteService;
  }

  @Override
  public void invoke(InitialBalanceResult data, AnalysisContext analysisContext) {

    //校验信息初始化为空
    data.setUploadStatus(null);
    StringBuilder uploadRemark = new StringBuilder();


    //校验判空
    if (data.getPlatformName() == null){
      uploadRemark.append("平台为空,");
    }
    if (data.getShopName() == null){
      uploadRemark.append("账号为空,");
    }
    if (data.getSite() == null){
      uploadRemark.append("站点为空,");
    }

    //校验合法
    if (!"Amazon".equals(data.getPlatformName())  ){
      uploadRemark.append("平台错误,");
    }
    List<String> shopList =  shopService.getShop().stream().map(i->i.getShopName()).collect(Collectors.toList());
    if (!shopList.contains(data.getShopName())  ){
      uploadRemark.append("账号错误,");
    }
    List<String> siteList =  siteService.getSite().stream().map(i->i.getSite()).collect(Collectors.toList());
    if (!siteList.contains(data.getSite()  )){
      uploadRemark.append("站点错误,");
    }
    String regex = "^([1-9]\\d{3}-)(([0]{0,1}[1-9])|([1][0-2]))$";


    if (!Pattern.matches(regex, data.getFiscalPeriod())){
      uploadRemark.append("会计区间有误(yyyy-MM)");
    }
    if ( uploadRemark.length()>0 ) {
    data.setUploadStatus( StrUtil.removeSuffix(uploadRemark.toString(),",")+"!");
      failList.add(data);
      return;
    }
    //校验重复
    String repeatFlag = new StringBuffer().append(data.getPlatformName()).append(data.getShopName()).append(data.getPlatformName()).append(data.getSite()).toString();
    if (set.contains(repeatFlag)) {
      data.setUploadStatus("平台,账号,站点重复!");
      failList.add(data);
      return;
    }

    UpdateWrapper<InitialBalance> wp = new UpdateWrapper<>();
    wp
        .eq("platform_name",data.getPlatformName())
        .eq("shop_name", data.getShopName())
        .eq("site", data.getSite())
        .set("FISCAL_PERIOD",data.getFiscalPeriod())
        .set("INITIAL_BALANCE",data.getInitialBalance());
    if (service.update(wp)) {
      set.add(repeatFlag);
    } else {
      data.setUploadStatus("插入新数据");
      failList.add(data);
      list.add(data);
    }

  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    // 这里也要保存数据，确保最后遗留的数据也存储到数据库
//    saveData();
    logger.info(list.toString());
    List <InitialBalance> insertList = new ArrayList<>();
    for (InitialBalanceResult data :list){
      InitialBalance initialBalance = new InitialBalance();
      initialBalance.setPlatformName( data.getPlatformName());
      initialBalance.setShopName(    data.getShopName());
      initialBalance.setSite(   data.getSite());
      initialBalance.setFiscalPeriod(   data.getFiscalPeriod());
      initialBalance.setInitialBalance(   data.getInitialBalance());
      insertList.add(initialBalance);
    }

    this.service.insertBatch((List<InitialBalance>) insertList);
    logger.info("所有数据解析完成！");
  }

  public List<InitialBalanceResult> getList() {
    return list;
  }

  public void setList(List<InitialBalanceResult> list) {
    this.list = list;
  }

  public List<InitialBalanceResult> getFailList() {
    return failList;
  }

  public void setFailList(List<InitialBalanceResult> failList) {
    this.failList = failList;
  }
}

