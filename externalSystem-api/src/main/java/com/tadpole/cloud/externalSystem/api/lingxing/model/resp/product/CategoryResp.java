package com.tadpole.cloud.externalSystem.api.lingxing.model.resp.product;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="领星分类查询返回实体")
public class CategoryResp {

    /**
     * 分类ID
     */
    private Integer cid;
    /**
     * 分类名称
     */
    private String title;
    /**
     * 分类父ID
     */
    private Integer parent_cid;

}
