package com.tadpole.cloud.operationManagement.api.brand.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: Amte Ma
 * @version: 1.0
 * @date: 2023/12/07 <br>
 */
@Data
public class CerticateRenewalResult {
    @ApiModelProperty("延期-商标有效期开始")
    private Date renewalValidityTrademarkStart;
    @ApiModelProperty("延期-商标有效期结束")
    private Date renewalValidityTrademarkEnd;
    @ApiModelProperty("延期-续展文件")
    private String renewalFile;
    @ApiModelProperty("延期-续展费用")
    private BigDecimal renewalAmount;
    @ApiModelProperty("备注说明")
    private String remark;
}
