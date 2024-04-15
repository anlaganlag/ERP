package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLpDepositPrepaymentRecord;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLpDepositPrepaymentRecordParam;

/**
 * <p>
 * 物流商押金&预付操作记录 服务类
 * </p>
 *
 * @author ty
 * @since 2023-12-21
 */
public interface ILsLpDepositPrepaymentRecordService extends IService<LsLpDepositPrepaymentRecord> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(LsLpDepositPrepaymentRecordParam param);

}
