package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

public class SupplementaryTransferParam implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 审核结果：通过1：不通过2
     */
    @NotNull
    @ApiModelProperty("审核结果：通过1：不通过2")
    private Integer status;

    /**
     * SKU
     */
    @NotEmpty
    @ApiModelProperty("申请批次编号List")
    private List<String> applyBatchNoList;

    /**
     * 补调拨单信息的原因
     */
    @NotEmpty
    @ApiModelProperty("补调拨单信息的原因")
    private String reson;


    /**
     * 补调拨单信息的原因
     */
    @NotEmpty
    @ApiModelProperty("用户名")
    private String name;


    /**
     * 补调拨单信息的原因
     */
    @NotEmpty
    @ApiModelProperty("用户账号")
    private String account;


}
