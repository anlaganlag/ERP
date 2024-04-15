package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
    * erp查询team可以用物料数量
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
@ExcelIgnoreUnannotated
@TableName("SHIPMENT_APPLY_ITEM")
public class ErpTeamAvailableQytResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("可调拨数量")
    private BigDecimal qty;


    @ApiModelProperty("team")
    private String team;


    @ApiModelProperty("物料编码")
    private String materialCode;


    @ApiModelProperty("合并字段(team+物料)")
    private String mergeField;


    @ApiModelProperty("合并字段(team+物料+交货点编号)")
    private String mergeField3;

    @ApiModelProperty("交货地点编号")
    private String deliverypointNo;


}
