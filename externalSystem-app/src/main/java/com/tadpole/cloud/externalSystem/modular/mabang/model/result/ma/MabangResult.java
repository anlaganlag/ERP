package com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MabangResult {
    private String code;
    private String message;
    private Object data;
}
