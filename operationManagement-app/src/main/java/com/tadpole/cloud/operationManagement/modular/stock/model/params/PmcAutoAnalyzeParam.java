package com.tadpole.cloud.operationManagement.modular.stock.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * PMC自动分析请求参数
 *
 * @author lsy
 * @since 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PmcAutoAnalyzeParam implements Serializable, BaseValidatingParam {

    @ApiModelProperty("部门List集合")
    List<String> department;

    @ApiModelProperty("Team list集合")
    List<String> team;

    @ApiModelProperty("物料lsit集合")
    List<String> materialCode;
}
