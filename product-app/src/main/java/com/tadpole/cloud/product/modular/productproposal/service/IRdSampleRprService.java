package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleRpr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleRprParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdSampleRprResult;

import java.util.List;

/**
 * <p>
 * 提案-退货款记录 服务类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
public interface IRdSampleRprService extends IService<RdSampleRpr> {

    List<RdSampleRprResult> listSampleRpr(RdSampleRprParam param);

    ResponseData add(List<RdSampleRprParam> params);
}
