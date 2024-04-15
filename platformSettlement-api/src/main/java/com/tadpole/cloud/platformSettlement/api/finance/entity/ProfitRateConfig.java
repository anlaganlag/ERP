package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
*
* </p>
*
* @author ty
* @since 2022-05-27
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("PROFIT_RATE_CONFIG")
@ExcelIgnoreUnannotated
public class ProfitRateConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team */
    @TableField("TEAM")
    private String team;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 广告占比 */
    @TableField("AD_RATE")
    private BigDecimal adRate;

    /** 去年公摊占比 */
    @TableField("SHARE_RATE")
    private BigDecimal shareRate;

    /** 平台其他费用占比（除FBA费、佣金、仓储） */
    @TableField("OTHER_RATE")
    private BigDecimal otherRate;

    /** 参数 */
    @TableField("PARAM")
    private BigDecimal param;

    /** 目标利润率 */
    @TableField("TARGET_RATE")
    private BigDecimal targetRate;

    /** 红线利润率(BI) */
    @TableField("WARNING_RATE_BI")
    private BigDecimal warningRateBI;

    /** 红线利润率(预结算) */
    @TableField("WARNING_RATE_PRE_SETTLEMENT")
    private BigDecimal warningRatePreSettlement;

    /** 生效期间 yyyy-mm */
    @TableField("VALID_DATE")
    private String validDate;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_BY")
    private String updateBy;

}