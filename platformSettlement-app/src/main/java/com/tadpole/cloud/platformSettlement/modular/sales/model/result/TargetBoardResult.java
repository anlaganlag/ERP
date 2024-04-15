package com.tadpole.cloud.platformSettlement.modular.sales.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author gal
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TARGET_BOARD")
public class TargetBoardResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("PLATFORM")
    @ExcelProperty(value ="平台")
    private String platform;

    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value ="事业部")
    private String department;

    @ApiModelProperty("TEAM")
    @ExcelProperty(value ="Team")
    private String team;

    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SALES_TARGET")
    @ExcelProperty(value ="销售目标")
    private BigDecimal salesTarget;

    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SALES_VOLUME_TARGET")
    @ExcelProperty(value ="销售额目标")
    private BigDecimal salesVolumeTarget;

    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("COLLECTION_TARGET")
    @ExcelProperty(value ="回款目标")
    private BigDecimal collectionTarget;

    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("PROFIT_TARGET")
    @ExcelProperty(value ="利润目标")
    private BigDecimal profitTarget;

    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("ADVERTISING_PROPORTION_BUDGET")
    @ExcelProperty(value ="广告占比")
    private BigDecimal advertisingProportionBudget;

    @ApiModelProperty("ad")
    private BigDecimal ad;

    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("PURCHASE_BUDGET")
    @ExcelProperty(value ="采购预算")
    private BigDecimal purchaseBudget;

    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("TARGET_INVENTORY_SALES_RATIO")
    @ExcelProperty(value ="目标库销比")
    private BigDecimal targetInventorySalesRatio;

    public String getD3() {
        return platform+department+team;
    }
}