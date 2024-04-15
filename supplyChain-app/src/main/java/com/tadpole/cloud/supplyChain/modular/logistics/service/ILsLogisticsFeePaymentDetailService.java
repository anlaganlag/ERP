package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsFeePaymentDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsFeePaymentDetailParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentDetailResult;

import java.util.List;

/**
 * <p>
 * 物流费付款明细 服务类
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
public interface ILsLogisticsFeePaymentDetailService extends IService<LsLogisticsFeePaymentDetail> {

    /**
     * 列表查询
     */
    List<LsLogisticsFeePaymentDetailResult> queryList(LsLogisticsFeePaymentDetailParam param);

    /**
     * 列表合计
     * @param param
     * @return
     */
    ResponseData queryListTotal(LsLogisticsFeePaymentDetailParam param);

}
