package com.tadpole.cloud.operationManagement.modular.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SysBizConfig;

/**
 * <p>
 * 系统业务配置表 服务类
 * </p>
 *
 * @author lsy
 * @since 2022-08-31
 */
public interface ISysBizConfigService extends IService<SysBizConfig> {

    boolean updateActionResult(Integer id,String actionResult );
}
