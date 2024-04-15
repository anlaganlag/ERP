package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
    * 查询team占用物料数量
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
public class OccupyQytResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("team占用物料数量")
    private BigDecimal qty;


    @ApiModelProperty("team")
    private String team;


    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("交货地点编号")
    private String deliverypointNo;


    @ApiModelProperty("合并字段")
    private String mergeField;


    @ApiModelProperty("合并字段(team+物料编码+交货地点编号)")
    private String mergeField3;



}
