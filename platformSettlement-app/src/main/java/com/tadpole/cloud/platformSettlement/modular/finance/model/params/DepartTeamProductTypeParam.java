package com.tadpole.cloud.platformSettlement.modular.finance.model.params;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;


@ApiModel(value = "DepartTeamProductType",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartTeamProductTypeParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /** department */
    @ApiModelProperty(value = "department")
    private String department ;

    /** Team */
    @ApiModelProperty(value = "Team")
    private String team ;


    /** productType */
    @ApiModelProperty(value = "productType")
    private String productType
            ;



}