package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdRefRegist;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdRefRegistParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdRefRegistResult;

import java.util.List;

/**
 * <p>
 * 提案-退款登记 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface IRdRefRegistService extends IService<RdRefRegist> {
    ResponseData add(RdRefRegistParam param);

    List<RdRefRegistResult> listRefRegist(RdRefRegistParam param);

    List<RdRefRegistResult> listPage(RdRefRegistParam param);

    ResponseData statisticsRefRegist(RdRefRegistParam param);

    List<RdRefRegistResult> listPageDetail(RdRefRegistParam param);

    RdRefRegistResult detail(RdRefRegistParam param);

    ResponseData submit(RdRefRegistParam param);

    ResponseData designatedAccount(RdRefRegistParam param);

    ResponseData uploadCredentials(RdRefRegistParam param);

    ResponseData print(RdRefRegistParam param);

    ResponseData capitalVerification(RdRefRegistParam param);

    void determineIfRefundIsPossible();
}
