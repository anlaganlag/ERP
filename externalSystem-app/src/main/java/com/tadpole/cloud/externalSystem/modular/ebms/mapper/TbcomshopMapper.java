package com.tadpole.cloud.externalSystem.modular.ebms.mapper;

import com.tadpole.cloud.externalSystem.api.ebms.model.param.TbcomshopParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.result.TbcomshopResult;
import com.tadpole.cloud.externalSystem.modular.ebms.entity.Tbcomshop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper接口
 * </p>
 *
 * @author S20190109
 * @since 2023-05-15
 */
public interface TbcomshopMapper extends BaseMapper<Tbcomshop> {

    List<TbcomshopResult> getShopList(@Param("paramCondition") TbcomshopParam param);
}
