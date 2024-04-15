package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * <Description> <br>
 *
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2022/10/13 <br>
 */
@Data
public class InventoryStorageOverageFeesTotal {

    @ApiModelProperty("超库容费（原币）")
    private BigDecimal chargedFeeAmount;

    @ApiModelProperty("仓存费美金汇总")
    private BigDecimal storageFee;

    @ApiModelProperty("扣费仓储费（美金")
    private BigDecimal storageDetailFee;
}
