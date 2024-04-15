package com.tadpole.cloud.externalSystem.api.lingxing.model.req.product;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="领星添加/编辑本地产品请求参数实体")
public class ProductReq {


    /**
     * sku
     */
    private String sku;

    /**
     * 品名
     */
    private String product_name;

    /**
     * 分类(传最小分类 3级)
     */
    private String category;

    /**
     * 分类ID(传最小分类 3级)
     */
    private Integer category_id;

    /**
     * 型号
     */
    private String model;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 品牌id
     */
    private Integer brand_id;

    /**
     * 状态：0-停售 1-在售 2-开发中 3-清仓
     */
    private Integer status;



}
