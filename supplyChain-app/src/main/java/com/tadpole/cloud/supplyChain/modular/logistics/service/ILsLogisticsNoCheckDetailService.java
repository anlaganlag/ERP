package com.tadpole.cloud.supplyChain.modular.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsNoCheckDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoCheckDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckDetailResult;

import java.util.List;

/**
 * <p>
 * 物流单对账明细 服务类
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
public interface ILsLogisticsNoCheckDetailService extends IService<LsLogisticsNoCheckDetail> {

    /**
     * 列表查询
     */
    List<LsLogisticsNoCheckDetailResult> queryList(LsLogisticsNoCheckDetailParam param);

}
