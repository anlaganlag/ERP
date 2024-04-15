package com.tadpole.cloud.externalSystem.api.saihu.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description: 赛狐分页请求参数
 * @date: 2024/2/21
 */
@Data
@ApiModel(value="赛狐分页请求参数")
public class SaiHuPageParam {

    @ApiModelProperty("第几页")
    private String pageNo;

    @ApiModelProperty("每页大小")
    private String pageSize;
}
