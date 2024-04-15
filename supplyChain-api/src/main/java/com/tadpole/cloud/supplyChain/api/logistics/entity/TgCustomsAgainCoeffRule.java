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
 * 清关二次折算 实体类
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
@TableName("TG_CUSTOMS_AGAIN_COEFF_RULE")
@ExcelIgnoreUnannotated
public class TgCustomsAgainCoeffRule implements Serializable {

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

    /** 最小清关价格 */
    @TableField(value = "MIN_CUSTOMS_PRICE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DECIMAL)
    private BigDecimal minCustomsPrice;

    /** 是否等于最小清关价格 */
    @TableField(value = "MIN_EQ", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
    private String minEq;

    /** 最大清关价格 */
    @TableField(value = "MAX_CUSTOMS_PRICE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DECIMAL)
    private BigDecimal maxCustomsPrice;

    /** 是否等于最大清关价格 */
    @TableField(value = "MAX_EQ", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
    private String maxEq;

    /** 区间类型：区间，非区间 */
    @TableField(value = "INTERVAL_TYPE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
    private String intervalType;

    /** 清关币种 */
    @TableField(value = "CUSTOMS_CURRENCY", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.VARCHAR)
    private String customsCurrency;

    /** 二次折算率 */
    @TableField(value = "AGAIN_COEFF", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DECIMAL)
    private BigDecimal againCoeff;

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