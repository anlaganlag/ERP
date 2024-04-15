package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * <Description> <br>
 *
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2022/10/17 <br>
 */
@Data
public class TotalStorageFeeTotal {

    @ApiModelProperty("结算报告 月仓储费")
    private BigDecimal billStorageFee;

    @ApiModelProperty("结算报告 长期仓储费")
    private BigDecimal billStorageLongFee;

    @ApiModelProperty("结算报告 超库容费")
    private BigDecimal billStorageOverageFee;

    @ApiModelProperty("结算报告 合计仓储费")
    private BigDecimal billTotalFee;

    @ApiModelProperty("原报告 超库容费")
    private BigDecimal storageOverageFee;

    @ApiModelProperty("原报告 月仓储费")
    private BigDecimal storageFee;

    @ApiModelProperty("原报告 长期仓储费")
    private BigDecimal storageLongFee;

    @ApiModelProperty("原报告 合计仓储费")
    private BigDecimal totalFee;

    @ApiModelProperty("结算报告-原报告=差异月仓储费")
    private BigDecimal diffStorageFee;

    @ApiModelProperty("结算报告-原报告=差异长期仓储费")
    private BigDecimal diffStoragelongFee;

    @ApiModelProperty("结算报告-原报告=差异 超库容费")
    private BigDecimal diffStorageOverageFee;

    @ApiModelProperty("结算报告-原报告=差异 合计仓储费")
    private BigDecimal diffTotalFee;
}
