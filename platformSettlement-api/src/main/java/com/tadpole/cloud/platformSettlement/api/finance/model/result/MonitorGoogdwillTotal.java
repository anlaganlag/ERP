package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <Description> <br>
 *
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2023/07/17 <br>
 */
@Data
public class MonitorGoogdwillTotal {

    @ApiModelProperty("")
    private BigDecimal amount;


    @ApiModelProperty("订单发货数量")
    private Long shipNum;


    @ApiModelProperty("退货入库数量")
    private Long storageNum;

}
