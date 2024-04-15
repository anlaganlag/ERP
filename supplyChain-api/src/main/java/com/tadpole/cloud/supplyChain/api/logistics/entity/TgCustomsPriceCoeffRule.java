package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 清关价格折算 实体类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TG_CUSTOMS_PRICE_COEFF_RULE")
@ExcelIgnoreUnannotated
public class TgCustomsPriceCoeffRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 国家编码 */
    @TableField("COUNTRY_CODE")
    private String countryCode;

    /** 国家名称 */
    @TableField("COUNTRY_NAME")
    private String countryName;

    /** HSCode */
    @TableField("HSCODE")
    private String hsCode;

    /** 价格类型 */
    @TableField(value = "PRICE_TYPE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
    private String priceType;

    /** 最小关税率 */
    @TableField(value = "MIN_CUSTOMS_RATE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DECIMAL)
    private BigDecimal minCustomsRate;

    /** 是否等于最小关税率 0：否，1：是 */
    @TableField(value = "MIN_EQ", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
    private String minEq;

    /** 最大关税率 */
    @TableField(value = "MAX_CUSTOMS_RATE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DECIMAL)
    private BigDecimal maxCustomsRate;

    /** 是否等于最大关税率 0：否，1：是 */
    @TableField(value = "MAX_EQ", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
    private String maxEq;

    /** 区间类型：区间，非区间 */
    @TableField(value = "INTERVAL_TYPE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
    private String intervalType;

    /** 清关折算率 */
    @TableField(value = "CUSTOMS_COEFF", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DECIMAL)
    private BigDecimal customsCoeff;

    /** 备注 */
    @TableField(value = "REMARK", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
    private String remark;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

}