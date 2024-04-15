package com.tadpole.cloud.platformSettlement.api.finance.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * Amazon广告费用账单
    * </p>
*
* @author S20190161
* @since 2023-07-13
*/
@Data
public class AmazonAdsInvoicesTotal implements Serializable{

    private double amount;
    private double tax;

}
