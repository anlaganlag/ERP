package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* selltement主数据
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_SETTLEMENT")
public class Settlement implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId("ID")
    private BigDecimal id;

    /** 结算单号 */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** 结算开始日期 */
    @TableField("SETTLEMENT_START_DATE")
    private LocalDateTime settlementStartDate;

    /** 结算截止日期 */
    @TableField("SETTLEMENT_END_DATE")
    private LocalDateTime settlementEndDate;

    /** 银行汇款日期 */
    @TableField("DEPOSIT_DATE")
    private LocalDateTime depositDate;

    /** 总额 */
    @TableField("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /** 币别 */
    @TableField("CURRENCY")
    private String currency;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 账号简称 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 创建日期 */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /** 报告任务指定下载日期 */
    @TableField("UPLOAD_DATE")
    private LocalDateTime uploadDate;

    /** 数据状态 */
    @TableField("STATUS")
    private BigDecimal status;

}