package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgBaseProductDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.TgBaseProductDetailParam;

/**
 * <p>
 * 通关产品详细信息 服务类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
public interface ITgBaseProductDetailService extends IService<TgBaseProductDetail> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(TgBaseProductDetailParam param);

    /**
     * 删除
     */
    ResponseData delete(TgBaseProductDetailParam param);

}
