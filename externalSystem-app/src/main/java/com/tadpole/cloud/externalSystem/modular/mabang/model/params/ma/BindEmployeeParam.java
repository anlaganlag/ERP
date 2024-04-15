package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BindEmployeeParam {
    /**
     * 马帮员工编号
     */
    private String mbEmployeeId;
    /**
     *
     * 外部标识(第三方系统中的唯一标识和马帮ERP中的唯一标识建立绑定关系)
     */
    private String outerId;

}
