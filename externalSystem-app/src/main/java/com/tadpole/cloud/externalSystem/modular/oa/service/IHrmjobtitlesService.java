package com.tadpole.cloud.externalSystem.modular.oa.service;

import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmjobtitlesResult;
import com.tadpole.cloud.externalSystem.modular.oa.entity.HrmShiftCalendarNormal;
import com.tadpole.cloud.externalSystem.modular.oa.entity.Hrmjobtitles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-12
 */
public interface IHrmjobtitlesService extends IService<Hrmjobtitles> {

    List<HrmjobtitlesResult> getJobTitle();

    String ssoLogin();

    List<HrmShiftCalendarNormal> hrmCalendar(HrmShiftCalendarNormal param);
}
