package com.tadpole.cloud.externalSystem.modular.mabang.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangEmployee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.BindEmployeeParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma.MabangResult;

/**
 * <p>
 * 马帮员工信息 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-06-09
 */
public interface MabangEmployeeService extends IService<MabangEmployee> {

    ResponseData sync(Integer status);

    MabangResult ssoLogin();
}
