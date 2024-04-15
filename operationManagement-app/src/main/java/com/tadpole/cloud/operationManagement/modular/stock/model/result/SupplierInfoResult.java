package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
* <p>
    * 审核记录信息
    * </p>
*
* @author cyt
* @since 2022-07-05
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierInfoResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 供应商编码 */
    @ApiModelProperty("供应商编码")
    private String supplierCode ;


    /** 供应商名称 */
    @ApiModelProperty("供应商名称")
    private String supplierName;

    /** 物控专员 */
    @ApiModelProperty("物控专员")
    private String matControlPerson;




}