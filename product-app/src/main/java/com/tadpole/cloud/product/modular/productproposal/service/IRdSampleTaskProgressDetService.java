package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleTaskProgressDet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskProgressDetParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskProgressDetResult;

import java.util.List;

/**
 * <p>
 * 提案-开发样任务进度明细 服务类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-29
 */
public interface IRdSampleTaskProgressDetService extends IService<RdSampleTaskProgressDet> {
    List<RdSampleTaskProgressDetResult> listTaskProgressDet(RdSampleTaskProgressDetParam param);

    ResponseData add(RdSampleTaskProgressDetParam param);
}
