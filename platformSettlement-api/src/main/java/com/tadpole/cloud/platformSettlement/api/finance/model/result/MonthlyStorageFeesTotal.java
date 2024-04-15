package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 汇总
 *
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2022/10/13 <br>
 */
@Data
public class MonthlyStorageFeesTotal {
    /** 原币汇总 */
    @ApiModelProperty("原币汇总")
    private BigDecimal estimatedMonthlyStorageFee;

    /** 仓存费美金汇总 */
    @ApiModelProperty("仓储费汇总")
    private BigDecimal storageFee;

    @ApiModelProperty(value= "仓储费扣费明细（原币）")
    private BigDecimal estimatedMonthlyStorageDetailFee;

    @ApiModelProperty(value= "仓储费扣费明细（美金）")
    private BigDecimal storageDetailFee;

}
