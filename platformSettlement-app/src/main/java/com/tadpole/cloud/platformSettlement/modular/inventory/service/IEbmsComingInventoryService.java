package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EbmsComingInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EbmsComingInventoryParam;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * EBMS在途库存 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
public interface IEbmsComingInventoryService extends IService<EbmsComingInventory> {

  /**
   * 在途库存列表
   * @param param
   * @return
   */
  PageResult<EbmsComingInventory> findPageBySpec(EbmsComingInventoryParam param);

  /**
   * 获取数量
   * @param param
   * @return
   */
  String getQty(EbmsComingInventoryParam param);

  /**
   * 在途库存列表导出
   * @param param
   * @return
   */
  List<EbmsComingInventory> export(EbmsComingInventoryParam param);

  /**
   * (月末)在途库存
   * @param queryWrapper
   * @return
   */
  EbmsComingInventory checkOne(  Wrapper<EbmsComingInventory> queryWrapper);

  /**
   * Monthly Inventory History生成期末库存列表获取BI在途库存数据
   */
  List<EbmsComingInventory> getComingInventoryList();

  /**
   * Monthly Inventory History生成期末库存列表生成在途库存数据
   * @param comingInventoryList
   * @param orgCodeMap
   * @return
   */
  ResponseData generateComingInventory(List<EbmsComingInventory> comingInventoryList, Map<String, String> orgCodeMap);

  /**
   * 刷组织编码
   * @return
   */
  ResponseData refreshOrgCode();
}
