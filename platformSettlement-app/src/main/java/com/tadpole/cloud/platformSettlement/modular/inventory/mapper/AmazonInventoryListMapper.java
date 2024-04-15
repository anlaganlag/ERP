package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* <p>
* 庫存列表3.0 Mapper 接口
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
public interface AmazonInventoryListMapper extends BaseMapper<AmazonInventoryList> {
    /**
     * 刷新表数据
     * @author AmteMa
     * @date 2022/12/20
     */
    void afreshCount();
}
