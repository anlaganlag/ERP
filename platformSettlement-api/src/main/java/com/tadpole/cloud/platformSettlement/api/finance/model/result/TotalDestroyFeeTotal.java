package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.math.BigDecimal;

/**
 * <Description> <br>
 *
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2022/10/27 <br>
 */
@Data
public class TotalDestroyFeeTotal {

    @TableField("DISPOSAL_FEE")
    private BigDecimal disposalFee;

    @TableField("RETURN_FEE")
    private BigDecimal returnFee;
}
