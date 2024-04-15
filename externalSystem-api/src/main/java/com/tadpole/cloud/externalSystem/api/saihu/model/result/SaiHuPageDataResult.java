package com.tadpole.cloud.externalSystem.api.saihu.model.result;

import com.tadpole.cloud.externalSystem.api.saihu.model.params.SaiHuPageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: ty
 * @description: 赛狐分页data响应参数
 * @date: 2024/2/22
 */
@Data
@ApiModel(value="赛狐分页data响应参数")
public class SaiHuPageDataResult extends SaiHuPageParam {

    @ApiModelProperty("总页数")
    private String totalPage;

    @ApiModelProperty("总条数")
    private String totalSize;

    @ApiModelProperty("详细数据")
    private List<Object> rows;

}
