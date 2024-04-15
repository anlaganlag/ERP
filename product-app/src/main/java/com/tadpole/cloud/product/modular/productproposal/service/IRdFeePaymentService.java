package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.result.RdFeePayResult;

import java.util.List;

/**
 * <p>
 * 提案-开模费付款申请 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface IRdFeePaymentService {

    List<RdFeePayResult> listRdFeePay();

    ResponseData listAccountMgtPersonal() throws Exception;
}
