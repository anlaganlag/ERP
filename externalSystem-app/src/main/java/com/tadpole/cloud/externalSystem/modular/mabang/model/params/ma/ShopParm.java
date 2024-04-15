package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopParm {
    /**
     * 状态:1启用;2停用
     */
    private Integer status;

    /**
     * 财务编码
     */
    private String financeCode;
}
