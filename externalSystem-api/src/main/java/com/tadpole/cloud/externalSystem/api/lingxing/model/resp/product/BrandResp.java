package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="领星品牌查询返回实体")
public class BrandResp {

    /**
     * 品牌ID
     */
    private Integer bid;
    /**
     * 品牌名称
     */
    private String title;


}
