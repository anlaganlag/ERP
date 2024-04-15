package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* <p>
* 店铺报告币别
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class ShopCurrencyResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    @ExcelProperty(value= "平台")
    private String platformName;

    /** 站点 */
    @ApiModelProperty("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value= "账号")
    private String shopName;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    @ExcelProperty(value= "原币")
    private String originalCurrency;

    /** 收款币 */
    @ApiModelProperty("PROCEEDS_CURRENCY")
    @ExcelProperty(value= "收款币")
    private String proceedsCurrency;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private LocalDateTime createAt;

    /** 修改时间 */
    @ApiModelProperty("UPDATE_AT")
    private LocalDateTime updateAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

}