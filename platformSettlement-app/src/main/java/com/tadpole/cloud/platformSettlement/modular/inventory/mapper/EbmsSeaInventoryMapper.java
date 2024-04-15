package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.EbmsSeaInventory;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.EbmsSeaInventoryParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 海外库存 Mapper 接口
 * </p>
 *
 * @author gal
 * @since 2021-12-09
 */
public interface EbmsSeaInventoryMapper extends BaseMapper<EbmsSeaInventory> {

  /**
   * 海外仓库存列表、导出
   * @param pageContext
   * @param param
   * @return
   */
  IPage<EbmsSeaInventory> customPageList(@Param("page") Page pageContext, @Param("paramCondition") EbmsSeaInventoryParam param);

  /**
   * 获取数量
   * @param paramCondition
   * @return
   */
  String getQty(@Param("paramCondition") EbmsSeaInventoryParam paramCondition);

  /**
   * 根据组织刷库存组织编码
   */
  void refreshOrgCode();
}
