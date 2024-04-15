package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.EbmsComingInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EbmsComingInventoryParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * EBMS在途库存 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
public interface EbmsComingInventoryMapper extends BaseMapper<EbmsComingInventory> {

  /**
   * 在途库存列表
   * @param pageContext
   * @param param
   * @return
   */
  IPage<EbmsComingInventory> customPageList(@Param("page") Page pageContext, @Param("paramCondition") EbmsComingInventoryParam param);

  /**
   * 获取数量
   * @param paramCondition
   * @return
   */
  String getQty(@Param("paramCondition") EbmsComingInventoryParam paramCondition);

  /**
   * Monthly Inventory History生成期末库存列表获取BI在途库存数据
   * @return
   */
  List<EbmsComingInventory> getComingInventoryList();

  /**
   * 根据组织刷库存组织编码
   */
  void refreshOrgCode();
}