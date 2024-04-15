package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdSamplePa;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSamplePaParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleRprParam;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleTaskParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSamplePaResult;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleTaskExtentResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 提案-购样申请 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface IRdSamplePaService extends IService<RdSamplePa> {

    ResponseData addOrEdit(RdSamplePaParam param);

    List<RdSamplePaResult> listSamplePa(RdSamplePaParam param);

    ResponseData checkIsCanCreateSamplePa(RdSamplePaParam param);

    ResponseData revokeSamplePurchaseApp(RdSamplePaParam param);

    List<RdSampleTaskExtentResult> listPageReview(RdSampleTaskParam param);

    List<RdSampleTaskExtentResult> listPageApprove(RdSampleTaskParam param);

    ResponseData paySupply(RdSamplePaParam param);

    ResponseData reviewSamplePurchaseApp(List<RdSamplePaParam> params);

    ResponseData approveSamplePurchaseApp(List<RdSamplePaParam> params);

    RdSamplePaResult detail(RdSamplePaParam param);

    ResponseData paySamplePurchaseApp(RdSamplePaParam param);

    ResponseData refSamplePurchaseAppDetail(RdSamplePaParam param);

    ResponseData refSamplePurchaseApp(RdSampleRprParam param);

    ResponseData returnSamplePurchaseApp(RdSamplePaParam param);

    ResponseData checkReturnSamplePurchaseApp(RdSamplePaParam param);
}
