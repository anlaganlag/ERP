package com.tadpole.cloud.externalSystem.modular.oa.mapper;

import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmjobtitlesResult;
import com.tadpole.cloud.externalSystem.modular.oa.entity.HrmShiftCalendarNormal;
import com.tadpole.cloud.externalSystem.modular.oa.entity.Hrmjobtitles;
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
 * @since 2023-05-12
 */
public interface HrmjobtitlesMapper extends BaseMapper<Hrmjobtitles> {

    List<HrmjobtitlesResult> getJobTitle();

    List<HrmShiftCalendarNormal> hrmCalendar(@Param("param") HrmShiftCalendarNormal param);
}
