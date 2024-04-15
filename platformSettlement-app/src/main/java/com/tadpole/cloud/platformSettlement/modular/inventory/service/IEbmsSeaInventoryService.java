package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.EbmsSeaInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EbmsSeaInventoryParam;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasWarehouseManage;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 海外库存 服务类
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
public interface IEbmsSeaInventoryService extends IService<EbmsSeaInventory> {

  /**
   * 海外仓库存列表
   * @param param
   * @return
   */
  PageResult<EbmsSeaInventory> findPageBySpec(EbmsSeaInventoryParam param);

  /**
   * 获取数量
   * @param param
   * @return
   */
  String getQty(EbmsSeaInventoryParam param);

  /**
   * 海外仓库存列表导出
   * @param param
   * @return
   */
  List<EbmsSeaInventory> export(EbmsSeaInventoryParam param);

  /**
   * (月末)海外仓库
   * @param queryWrapper
   * @return
   */
  EbmsSeaInventory checkOne(  Wrapper<EbmsSeaInventory> queryWrapper);

  /**
   * 生成月末海外仓库存
   * @param overseasWarehouseList
   * @param orgCodeMap
   * @return
   */
  ResponseData generateOverseasWarehouse(List<OverseasWarehouseManage> overseasWarehouseList, Map<String, String> orgCodeMap);

  /**
   * 刷组织编码
   * @return
   */
  ResponseData refreshOrgCode();
}
