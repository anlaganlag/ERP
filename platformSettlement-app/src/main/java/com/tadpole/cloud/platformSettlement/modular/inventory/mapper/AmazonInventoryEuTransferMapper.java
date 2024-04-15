package com.tadpole.cloud.platformSettlement.modular.inventory.mapper;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryEuTransfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* <p>
* EBMS转仓清单（欧洲站点调拨) Mapper 接口
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
public interface AmazonInventoryEuTransferMapper extends BaseMapper<AmazonInventoryEuTransfer> {
    List<AmazonInventoryEuTransfer> getEbmsEuTransfer(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
