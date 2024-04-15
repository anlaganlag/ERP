package com.tadpole.cloud.supplyChain.modular.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbPackBox;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbPackBoxParam;

import java.util.List;

/**
 * <p>
 *  BTB订单发货箱子信息服务类
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
public interface ILsBtbPackBoxService extends IService<LsBtbPackBox> {

    /**
     * BTB订单发货箱子信息
     */
    List<LsBtbPackBox> queryList(LsBtbPackBoxParam param);

}
