package com.tadpole.cloud.externalSystem.api.lingxing.model.parm;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 物料配置方案批量操作
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SwitchPlanParm {
    /**
     * 需要更换的配置数据ID
     */
    @NotEmpty(message="配置数据idList不能为空")
    private List<String> idList;

    /**
     * 新的方案名称
     */
    @NotEmpty(message="切换的方案名称不能为空")
    private String planName;

}
