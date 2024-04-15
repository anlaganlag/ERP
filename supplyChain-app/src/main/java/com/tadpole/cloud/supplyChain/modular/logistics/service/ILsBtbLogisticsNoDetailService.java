package com.tadpole.cloud.supplyChain.modular.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsBtbLogisticsNoDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbLogisticsNoDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbLogisticsNoDetailResult;

import java.util.List;

/**
 * <p>
 *  BTB物流单明细服务类
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
public interface ILsBtbLogisticsNoDetailService extends IService<LsBtbLogisticsNoDetail> {

    /**
     * 列表查询
     */
    List<LsBtbLogisticsNoDetailResult> queryList(LsBtbLogisticsNoDetailParam param);

}
