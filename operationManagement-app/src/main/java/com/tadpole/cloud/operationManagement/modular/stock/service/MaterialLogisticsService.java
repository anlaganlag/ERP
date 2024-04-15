package com.tadpole.cloud.operationManagement.modular.stock.service;


import cn.stylefeng.guns.cloud.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.EntMaterialLogistics;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.SysMaterialParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.SysMaterialResult;

import java.text.ParseException;
import java.util.List;

/**
 * 物料运输方式 服务类
 *
 * @author gal
 * @since 2021-07-27
 */
public interface MaterialLogisticsService extends IService<EntMaterialLogistics> {

  void insertBatch(List<EntMaterialLogistics> materialLogisticsList) throws ParseException;

  void updateBatch(List<EntMaterialLogistics> materialLogisticsList) throws ParseException;

  void insertOrUpdateBatchById(List<EntMaterialLogistics> materialLogisticsList)
      throws ParseException;

  //  void insertOrUpdateBatchByCustom(List<EntMaterialLogistics> materialLogisticsList)
  //      throws ParseException;
  void analysis(SysMaterialParam param);
  PageResult<SysMaterialResult> findPageBySpec(SysMaterialParam param);




  void deleteBatch(List<String> list) throws ParseException;
}
