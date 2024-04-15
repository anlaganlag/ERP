package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LX_AMAZON_SETTLEMENT")
@ExcelIgnoreUnannotated
public class LxAmazonSettlement implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ID")
    @TableId(value = "ID", type = IdType.AUTO)
    @ExcelProperty(value ="")
    private BigDecimal id;

    /** 结算单号 */
    @TableField("SETTLEMENT_ID")
    @ExcelProperty(value ="结算单号")
    private String settlementId;

    /** 结算开始日期 */
    @TableField("SETTLEMENT_START_DATE")
    @ExcelProperty(value ="结算开始日期")
    private Date settlementStartDate;

    /** 结算截止日期 */
    @TableField("SETTLEMENT_END_DATE")
    @ExcelProperty(value ="结算截止日期")
    private Date settlementEndDate;

    /** 银行汇款日期 */
    @TableField("DEPOSIT_DATE")
    @ExcelProperty(value ="银行汇款日期")
    private Date depositDate;

    /** 总额 */
    @TableField("TOTAL_AMOUNT")
    @ExcelProperty(value ="总额")
    private BigDecimal totalAmount;

    /** 货币 */
    @TableField("CURRENCY")
    @ExcelProperty(value ="货币")
    private String currency;

    /** 站点 */
    @TableField("SITE")
    @ExcelProperty(value ="站点")
    private String site;

    /** 账号简称 */
    @TableField("SHOP_NAME")
    @ExcelProperty(value ="账号简称")
    private String shopName;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    @ExcelProperty(value ="创建时间")
    private Date createTime;

}