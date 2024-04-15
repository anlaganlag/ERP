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
public class LongTermStorageFeeChargesTotal {

    @ApiModelProperty("收取12月长期仓储费的库存数量")
    private Long qtyCharged12Mo;

    @ApiModelProperty("收取6月长期仓储费的库存数量")
    private Long qtyCharged6Mo;

    @ApiModelProperty("长期仓储费（12个月）")
    private BigDecimal mo12LongTermsStorageFee;

    @ApiModelProperty("长期仓储费（6个月）")
    private BigDecimal mo6LongTermsStorageFee;

    @ApiModelProperty("长期仓储费（6个月+12个月）原币")
    private BigDecimal longTermsStorageFee;

    @ApiModelProperty("期间仓存费美金汇总")
    private BigDecimal storageFee;

    @ApiModelProperty("扣费仓储费（美金）")
    private BigDecimal storageDetailFee;

}
