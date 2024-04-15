package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsNoCheckDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoCheckDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 物流单对账明细 Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
public interface LsLogisticsNoCheckDetailMapper extends BaseMapper<LsLogisticsNoCheckDetail> {

    /**
     * 列表查询
     * @param param
     * @return
     */
    List<LsLogisticsNoCheckDetailResult> queryList(@Param("param") LsLogisticsNoCheckDetailParam param);

}
