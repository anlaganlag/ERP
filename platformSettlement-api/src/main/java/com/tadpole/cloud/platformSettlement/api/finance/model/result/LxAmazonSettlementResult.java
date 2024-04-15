package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 领星Settlement源文件汇总
* </p>
*
* @author cyt
* @since 2022-05-13
*/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
@ExcelIgnoreUnannotated
public class LxAmazonSettlementResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @ExcelProperty(value ="")
    private BigDecimal id;

    /** 结算单号 */
    @ApiModelProperty("SETTLEMENT_ID")
    @ExcelProperty(value ="结算单号")
    private String settlementId;

    /** 结算开始日期 */
    @ApiModelProperty("SETTLEMENT_START_DATE")
    @ExcelProperty(value ="结算开始日期")
    private Date settlementStartDate;

    /** 结算截止日期 */
    @ApiModelProperty("SETTLEMENT_END_DATE")
    @ExcelProperty(value ="结算截止日期")
    private Date settlementEndDate;

    /** 银行汇款日期 */
    @ApiModelProperty("DEPOSIT_DATE")
    @ExcelProperty(value ="银行汇款日期")
    private Date depositDate;

    /** 总额 */
    @ApiModelProperty("TOTAL_AMOUNT")
    @ExcelProperty(value ="总额")
    private BigDecimal totalAmount;

    /** 货币 */
    @ApiModelProperty("CURRENCY")
    @ExcelProperty(value ="货币")
    private String currency;

    /** 站点 */
    @ApiModelProperty("SITE")
    @ExcelProperty(value ="站点")
    private String site;

    /** 账号简称 */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value ="账号简称")
    private String shopName;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty(value ="创建时间")
    private Date createTime;

}