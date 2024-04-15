package com.tadpole.cloud.externalSystem.modular.lingxing.service;


import com.tadpole.cloud.externalSystem.api.lingxing.model.req.product.ProductReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.BrandResp;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.CategoryResp;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product.LinXingCategoryAddResp;

import java.util.List;

/**
 * 领星商品相关接口
 */
public interface LingXingProductService {
    public LinXingCategoryAddResp addCategory(Integer parentCid, String title);

    public LingXingBaseRespData addProduct(ProductReq productReq);

    public List<CategoryResp> getAllCategory();

    public List<BrandResp> getAllBrand();


}
