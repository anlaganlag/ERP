package com.tadpole.cloud.externalSystem.api.lingxing.model.req.advertising;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: cyt
 * @description: 查询广告管理 - 广告活动
 * @date: 2022/5/17
 */
@Data
@ApiModel(value="查询广告管理 - 广告组")
public class OperationLogReq {

    @NotNull
    @ApiModelProperty("店铺ID")
    private int sid;

    @ApiModelProperty("拉取数据偏移量 默认：0")
    private int offset;

    @ApiModelProperty("一页拉取的数据量 默认：1000条")
    private int length;

    @ApiModelProperty("广告类型 ：1:SP,2：SB,3：SD,4：广告设置,5：SBV广告")
    private int type;

}
