package com.tadpole.cloud.externalSystem.modular.oa.mapper;

import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmdepartmentResult;
import com.tadpole.cloud.externalSystem.modular.oa.entity.Hrmdepartment;
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
 * @since 2023-05-11
 */
public interface HrmdepartmentMapper extends BaseMapper<Hrmdepartment> {

    List<HrmdepartmentResult> getDepartment();
}
