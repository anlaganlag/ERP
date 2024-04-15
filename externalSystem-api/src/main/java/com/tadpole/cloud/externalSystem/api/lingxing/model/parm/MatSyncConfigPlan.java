package com.tadpole.cloud.externalSystem.api.lingxing.model.parm;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatSyncConfigPlan {
    String value;
    String lxInterfacePlan;
    String lxProductNameTitle;
    String lxProductNameValue;
    String lxCategoryOneTitle;
    String lxCategoryOneValue;
    String lxCategoryTwoTitle;
    String lxCategoryTwoValue;
    String lxCategoryThreeTitle;
    String lxCategoryThreeValue;
    String lxModelTitle;
    String lxModelValue;
}
