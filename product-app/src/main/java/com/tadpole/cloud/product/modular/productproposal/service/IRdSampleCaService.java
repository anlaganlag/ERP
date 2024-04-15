package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleCa;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdRefRegistParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleCaParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleCaResult;

import java.util.List;

/**
 * <p>
 * 提案-定制申请 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface IRdSampleCaService extends IService<RdSampleCa> {
    ResponseData add(RdSampleCaParam param);

    ResponseData checkIsCanCreateFeedback(RdSampleCaParam param);

    ResponseData uploadContract(RdSampleCaParam param);

    List<RdSampleCaResult> listSampleCa(RdSampleCaParam param);

    PageResult<RdSampleCaResult> listPage(RdSampleCaParam param);

    ResponseData reviewContract(RdSampleCaParam param);

    RdSampleCaResult detail(RdSampleCaParam param);

    ResponseData approveSampleCa(RdSampleCaParam param);

    ResponseData payRdSampleCa(RdSampleCaParam param);

    ResponseData refRdSampleCaDetail(RdSampleCaParam param);

    ResponseData refRdSampleCa(RdRefRegistParam param);
}
