package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLpDepositPrepayment;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLpDepositPrepaymentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLpDepositPrepaymentResult;

import java.util.List;

/**
 * <p>
 * 物流商押金&预付 服务类
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
public interface ILsLpDepositPrepaymentService extends IService<LsLpDepositPrepayment> {

    /**
     * 分页查询列表
     */
    ResponseData queryPage(LsLpDepositPrepaymentParam param);

    /**
     * 新增
     */
    ResponseData add(LsLpDepositPrepaymentParam param);

    /**
     * 删除
     */
    ResponseData delete(LsLpDepositPrepaymentParam param);

    /**
     * 编辑
     */
    ResponseData edit(LsLpDepositPrepaymentParam param);

    /**
     * 导出
     */
    List<LsLpDepositPrepaymentResult> export(LsLpDepositPrepaymentParam param);

}
