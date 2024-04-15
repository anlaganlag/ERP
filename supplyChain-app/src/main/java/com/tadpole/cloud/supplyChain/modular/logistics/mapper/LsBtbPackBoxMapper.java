package com.tadpole.cloud.supplyChain.modular.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackBox;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackBoxParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  BTB订单发货箱子信息Mapper接口
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
public interface LsBtbPackBoxMapper extends BaseMapper<LsBtbPackBox> {

    /**
     * BTB订单发货箱子信息
     * @param param
     * @return
     */
    List<LsBtbPackBox> queryList(@Param("param") LsBtbPackBoxParam param);

}
