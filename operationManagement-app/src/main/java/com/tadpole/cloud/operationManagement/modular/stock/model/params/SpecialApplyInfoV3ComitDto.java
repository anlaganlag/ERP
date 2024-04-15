package com.tadpole.cloud.operationManagement.modular.stock.model.params;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialApplyInfoV3ComitDto {

    @NotEmpty
    @ApiModelProperty("数据id集合")
    List<Integer> idList = new ArrayList<>();


    @ApiModelProperty("提交时是否忽略提示：1忽略，继续提交，0不忽略，取消提交，默认0")
    Integer ignoreWarn;
}
