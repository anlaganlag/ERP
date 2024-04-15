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
 * 清关税率 实体类
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
@TableName("TG_CUSTOMS_TAX_RATE")
@ExcelIgnoreUnannotated
public class TgCustomsTaxRate implements Serializable {

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

    /** 流转税率（欧洲VAT/加拿大GST/日本消费税） */
    @TableField(value = "CHANGE_TAX_RATE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DECIMAL)
    private BigDecimal changeTaxRate;

    /** 关税率 */
    @TableField(value = "TAX_RATE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DECIMAL)
    private BigDecimal taxRate;

    /** 附加税 */
    @TableField(value = "ADD_TAX_RATE", updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.DECIMAL)
    private BigDecimal addTaxRate;

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