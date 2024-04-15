package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsNoRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsNoRecordParam;

/**
 * <p>
 * 物流单费用操作记录 服务类
 * </p>
 *
 * @author ty
 * @since 2024-03-19
 */
public interface ILsLogisticsNoRecordService extends IService<LsLogisticsNoRecord> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(LsLogisticsNoRecordParam param);

}
