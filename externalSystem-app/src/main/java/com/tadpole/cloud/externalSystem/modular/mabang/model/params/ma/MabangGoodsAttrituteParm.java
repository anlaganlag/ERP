package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MabangGoodsAttrituteParm {

    @ApiModelProperty("蓝属性名,必填")
    private String name;//蓝属性名,必填

    @ApiModelProperty("属性值")
    private String value;//属性值

}
