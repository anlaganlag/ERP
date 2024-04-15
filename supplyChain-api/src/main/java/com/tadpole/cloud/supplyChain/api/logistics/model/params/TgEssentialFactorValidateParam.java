package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description: 要素组成
 * @date: 2023/6/8
 */
@Data
public class TgEssentialFactorValidateParam extends BaseRequest {

    /** 要素序号 */
    @ApiModelProperty("要素序号")
    private Integer orderNo;

    /** 要素名称 */
    @ApiModelProperty("要素名称")
    private String name;

    /** 要素值 */
    @ApiModelProperty("要素值")
    private String value;

}
