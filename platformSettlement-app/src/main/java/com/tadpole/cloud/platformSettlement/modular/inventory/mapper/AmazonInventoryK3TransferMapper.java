package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryK3Transfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* K3跨组织直接调拨单 Mapper 接口
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
public interface AmazonInventoryK3TransferMapper extends BaseMapper<AmazonInventoryK3Transfer> {
    List<AmazonInventoryK3Transfer> getK3Transfer(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
