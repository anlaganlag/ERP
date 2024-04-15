package com.tadpole.cloud.product.modular.productproposal.service;


import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCfbParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleManageParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSamplePaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCfbResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSamplePaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 提案-开模费付款申请 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface ISampleTakeOverTimeDealService {
    List<RdSampleTaskExtentResult> listTimeoutTask(RdSampleTaskParam param);

    List<RdSamplePaResult> listSamplePa(RdSamplePaParam param);

    List<RdSampleCfbResult> listSampleCfb(RdSampleCfbParam param);

    ResponseData addSample(List<RdSampleManageParam> params);
}
