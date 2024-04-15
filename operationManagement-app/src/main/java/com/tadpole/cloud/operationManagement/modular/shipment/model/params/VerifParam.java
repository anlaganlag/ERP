package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
* <p>
    * 审核参数
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class VerifParam implements Serializable {

    private static final long serialVersionUID = 1L;


        /** 审核结果：通过1：不通过2 */
        @ApiModelProperty("审核结果：通过1：不通过2")
        private Integer status;

        /** SKU */
        @ApiModelProperty("申请批次编号List")
        private List<String> applyBatchNoList;

        /** 审核备注原因 */
        @ApiModelProperty("审核备注原因")
        private String reson;

}
