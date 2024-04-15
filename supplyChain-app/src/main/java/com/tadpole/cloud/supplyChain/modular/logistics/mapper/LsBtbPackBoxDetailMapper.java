package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackBoxDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackBoxDetailParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  BTB订单发货箱子明细信息Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
public interface LsBtbPackBoxDetailMapper extends BaseMapper<LsBtbPackBoxDetail> {

    /**
     * BTB订单发货箱子明细信息
     * @param param
     * @return
     */
    List<LsBtbPackBoxDetail> queryList(@Param("param") LsBtbPackBoxDetailParam param);

}
