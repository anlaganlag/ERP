package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleCfb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCfbParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCfbResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;

import java.util.List;

/**
 * <p>
 * 提案-定制反馈 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface IRdSampleCfbService extends IService<RdSampleCfb> {

    ResponseData addOrEdit(RdSampleCfbParam param);

    List<RdSampleCfbResult> listSampleCfb(RdSampleCfbParam param);

    ResponseData checkIsCanCreateFeedback(RdSampleCfbParam param);

    ResponseData custApplicationFeedback(List<RdSampleCfbParam> params);

    List<RdSampleTaskExtentResult> listPageApprove(RdSampleTaskParam param);

    ResponseData custFeedback(RdSampleCfbParam param);

    RdSampleCfbResult detail(RdSampleCfbParam param);

    ResponseData statisticsRdSampleCa(RdSampleCfbParam param);
}
