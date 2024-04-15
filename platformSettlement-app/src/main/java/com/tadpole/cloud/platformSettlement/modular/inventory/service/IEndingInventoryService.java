package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EndingInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EndingInventoryParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.EndingInventoryResult;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 期末库存列表 服务类
 * </p>
 *
 * @author gal
 * @since 2021-11-22
 */
public interface IEndingInventoryService extends IService<EndingInventory> {

  /**
   * 期末库存列表
   * @param param
   * @return
   */
  PageResult<EndingInventoryResult> findPageBySpec(EndingInventoryParam param);

  /**
   * 期末库存列表导出
   * @param param
   * @return
   */
  List<EndingInventoryResult> export(EndingInventoryParam param);

  /**
   * 获取数量
   * @param param
   * @return
   */
  String getQuantity(EndingInventoryParam param);

  /**
   * 同步erp
   * @param param
   * @return
   * @throws ParseException
   */
  ResponseData syncErp(EndingInventoryParam param);

  /**
   * 作废
   * @param param
   */
  void reject(EndingInventoryParam param);

  /**
   * 批量作废
   * @param params
   */
  void rejectBatch(List<EndingInventoryParam> params);

  /**
   * 刷listing
   */
  void updateDetailList();

  /**
   * 刷存档listing
   */
  void updateFileDetailList();

  /**
   * 发邮件
   * @return
   */
  List<EndingInventoryResult> emailList();

  void updateSyncStatus();

  void timerSync() throws IOException, ParseException;

  /**
   * 修改物料
   * @param param
   * @return
   */
  ResponseData editMat(EndingInventoryParam param);
}

