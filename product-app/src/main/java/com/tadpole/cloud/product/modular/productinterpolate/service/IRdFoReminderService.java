package com.tadpole.cloud.product.modular.productinterpolate.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productinterpolate.entity.RdFoReminder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productinterpolate.model.params.RdFoReminderParam;

/**
 * <p>
 * 产品线管理 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
public interface IRdFoReminderService extends IService<RdFoReminder> {
    ResponseData add(RdFoReminderParam param);
}
