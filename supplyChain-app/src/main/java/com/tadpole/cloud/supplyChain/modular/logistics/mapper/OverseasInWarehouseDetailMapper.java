package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasInWarehouseDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  海外仓入库管理详情Mapper 接口
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
public interface OverseasInWarehouseDetailMapper extends BaseMapper<OverseasInWarehouseDetail> {

    /**
     * 根据id批量更新入库明细物流状态
     * @param ids
     * @param operator
     * @return
     */
    Boolean updateBatchLogisticsById(@Param("ids") List<BigDecimal> ids, @Param("operator") String operator);

}
