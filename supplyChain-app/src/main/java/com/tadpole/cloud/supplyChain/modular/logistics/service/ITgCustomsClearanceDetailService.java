package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsClearanceDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgCustomsClearanceDetailParam;

/**
 * <p>
 * 清关单明细 服务类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
public interface ITgCustomsClearanceDetailService extends IService<TgCustomsClearanceDetail> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgCustomsClearanceDetailParam param);

    /**
     * 查询列表
     */
    ResponseData queryList(TgCustomsClearanceDetailParam param);
}
