package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleManage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdProposalParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleManageParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdProposalExtentResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleManageResult;

import java.util.List;

/**
 * <p>
 * 提案-开发样管理 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface IRdSampleManageService extends IService<RdSampleManage> {

    List<RdSampleManageResult> listSample(RdSampleManageParam param);

    List<RdSampleManageResult> listTestSample(RdSampleManageParam param);

    ResponseData add(List<RdSampleManageParam> params);

    ResponseData sampleToDisposeOfReturnCheck(List<RdSampleManageParam> params);

    ResponseData sampleToDisposeOf(List<RdSampleManageParam> params);

    PageResult<RdSampleManageResult> listPage(RdSampleManageParam param);

    RdSampleManageResult detail(RdSampleManageParam param);

    List<RdSampleManageResult> listSampleManage(RdSampleManageParam param);

    ResponseData statisticsSample(RdSampleManageParam param);

    ResponseData invSample();

    ResponseData checkInvSample();

    ResponseData startInv(RdSampleManageParam param);
}
