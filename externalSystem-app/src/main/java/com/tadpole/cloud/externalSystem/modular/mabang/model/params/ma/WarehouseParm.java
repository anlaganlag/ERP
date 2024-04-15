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
public class WarehouseParm {
    /**
     * 状态:1启用;2停用
     */
    @ApiModelProperty("状态:1启用;2停用")
    private Integer status;

    /**
     * 仓库类型：1自建仓2第三方仓库3FBA仓9全部仓库，默认1
     */
    @ApiModelProperty("仓库类型：1自建仓2第三方仓库3FBA仓9全部仓库，默认1")
    private Integer type;
}
