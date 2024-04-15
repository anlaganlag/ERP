package com.tadpole.cloud.product.modular.productproposal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.entity.RdTlSetting;
import com.tadpole.cloud.product.api.productproposal.model.params.RdTlSettingParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleApproveResult;

import java.util.List;

/**
 * <p>
 * 提案-设置-提案等级审批设置 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
public interface IRdFeeReimburseReviewService extends IService<RdSampleApproveResult> {
    List<RdSampleApproveResult> listPage();
}
