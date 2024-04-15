package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbLogisticsNoDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbLogisticsNoDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbLogisticsNoDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  BTB物流单明细Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
public interface LsBtbLogisticsNoDetailMapper extends BaseMapper<LsBtbLogisticsNoDetail> {

    /**
     * 列表查询
     * @param param
     * @return
     */
    List<LsBtbLogisticsNoDetailResult> queryList(@Param("param") LsBtbLogisticsNoDetailParam param);

}
