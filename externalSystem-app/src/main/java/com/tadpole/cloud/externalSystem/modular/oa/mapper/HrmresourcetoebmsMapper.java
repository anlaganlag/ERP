package com.tadpole.cloud.externalSystem.modular.oa.mapper;

import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.externalSystem.modular.oa.entity.Hrmresourcetoebms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper接口
 * </p>
 *
 * @author S20190109
 * @since 2023-05-12
 */
public interface HrmresourcetoebmsMapper extends BaseMapper<Hrmresourcetoebms> {

    List<HrmresourcetoebmsResult> getHrmResource();
}
