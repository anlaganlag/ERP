package com.tadpole.cloud.product.modular.product.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.modular.product.entity.RdPssManageVersion;

import java.util.List;

/**
 * <p>
 * 产品同款版本 服务类
 * </p>
 *
 * @author S20210221
 * @since 2024-04-03
 */
public interface IRdPssManageVersionService extends IService<RdPssManageVersion> {

    List<RdPssManageVersion>  getBySysSpu(String sysSpu);

    /**
     * 版本落地（确认）
     * @param sysSpu
     * @return
     */
    ResponseData assertVersion(String sysSpu);
}
