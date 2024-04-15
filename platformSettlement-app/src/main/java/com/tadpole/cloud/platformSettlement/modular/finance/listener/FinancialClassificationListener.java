package com.tadpole.cloud.platformSettlement.modular.finance.listener;

import com.tadpole.cloud.platformSettlement.api.finance.entity.FinancialClassification;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFinancialClassificationService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinancialClassificationListener extends AnalysisEventListener<FinancialClassification> {

  Logger logger = LoggerFactory.getLogger(FinancialClassificationListener.class);

  //每次读取100条数据就进行保存操作
  private static final int BATCH_COUNT = 100;
  //由于每次读都是新new FinancialClassificationDataListener的，所以这个list不会存在线程安全问题

  private IFinancialClassificationService service;

  List<FinancialClassification> list = new ArrayList<>();
  List<FinancialClassification> failList = new ArrayList<>();

  public FinancialClassificationListener(IFinancialClassificationService service) {
    this.service = service;
  }

  @Override
  public void invoke(FinancialClassification data, AnalysisContext analysisContext) {
    logger.info("解析到一条数据:{}", JSON.toJSONString(data));
    data.setClassificationType(data.getFinancialClassification());

    list.add(data);
//    if (list.size() >= BATCH_COUNT) {
//      saveData();
//      // 存储完成清理 list
//      list.clear();
//    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    // 这里也要保存数据，确保最后遗留的数据也存储到数据库
//    saveData();
    this.service.insertBatch((List<FinancialClassification>) list);
    logger.info("所有数据解析完成！");
  }

  private void saveData() {
    logger.info("{}条数据，开始存储数据库！", list.size());
    //保存数据
    //userService.save(list);
    logger.info("存储数据库成功！");
  }

  public List<FinancialClassification> getList() {
    return list;
  }

  public void setList(List<FinancialClassification> list) {
    this.list = list;
  }

  public List<FinancialClassification> getFailList() {
    return failList;
  }

  public void setFailList(
      List<FinancialClassification> failList) {
    this.failList = failList;
  }
}

