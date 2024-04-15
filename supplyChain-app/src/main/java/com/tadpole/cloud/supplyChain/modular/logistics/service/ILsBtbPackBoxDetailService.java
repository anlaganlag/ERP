package com.tadpole.cloud.supplyChain.modular.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackBoxDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackBoxDetailParam;

import java.util.List;

/**
 * <p>
 *  BTB订单发货箱子明细信息服务类
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
public interface ILsBtbPackBoxDetailService extends IService<LsBtbPackBoxDetail> {

    /**
     * BTB订单发货箱子明细信息
     */
    List<LsBtbPackBoxDetail> queryList(LsBtbPackBoxDetailParam param);

}
