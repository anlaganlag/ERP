package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 物流单费用
 * @date: 2024/3/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsNoFeeParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 发货批次号 */
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    /** 计费类型 */
    @ApiModelProperty("计费类型")
    private String confirmFeeType;

    /** 计费量 */
    @ApiModelProperty("计费量")
    private BigDecimal confirmCountFee;

    /** 物流费币制 */
    @ApiModelProperty("物流费币制")
    private String logisticsCurrency;

    /** 单价 */
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("物流费")
    private BigDecimal logisticsFee;

    /** 燃油附加费 */
    @ApiModelProperty("燃油附加费")
    private BigDecimal oilFee;

    /** 旺季附加费 */
    @ApiModelProperty("旺季附加费")
    private BigDecimal busySeasonFee;

    /** 附加费及杂费 */
    @ApiModelProperty("附加费及杂费")
    private BigDecimal othersFee;

    /** 附加费及杂费备注 */
    @ApiModelProperty("附加费及杂费备注")
    private String othersFeeRemark;

    /** 报关费 */
    @ApiModelProperty("报关费")
    private BigDecimal customsFee;

    /** 清关费 */
    @ApiModelProperty("清关费")
    private BigDecimal clearCustomsFee;

    /** 税费 */
    @ExcelProperty(value ="税费")
    @ApiModelProperty("税费")
    private BigDecimal taxFee;

    /** 备注 */
    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;

    /** 总费用（人工维护） */
    @ApiModelProperty("总费用（人工维护）")
    private BigDecimal totalFee;

}
