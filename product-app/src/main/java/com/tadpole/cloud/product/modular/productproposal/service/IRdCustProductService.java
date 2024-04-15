package com.tadpole.cloud.product.modular.productproposal.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.product.api.productproposal.entity.RdCustProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.product.api.productproposal.model.params.RdCustProductParam;
import com.tadpole.cloud.product.api.productproposal.model.result.RdCustProductResult;

import java.util.List;

/**
 * <p>
 * 提案-定品 服务类
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
public interface IRdCustProductService extends IService<RdCustProduct> {
    List<RdCustProductResult> listPage(RdCustProductParam param);

    RdCustProductResult detail(RdCustProductParam param);

    ResponseData checkIsCanCreate(RdCustProductParam param);

    ResponseData addOrEdit(RdCustProductParam param);

    ResponseData custProductAppr(RdCustProductParam param);

    ResponseData custProductAppr2(RdCustProductParam param);
}
