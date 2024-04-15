package com.tadpole.cloud.platformSettlement.modular.vat.entity;

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
 * 税金测算VAT明细
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("VAT_DETAIL")
@ExcelIgnoreUnannotated
public class VatDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 汇率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 期间 */
    @TableField("ACTIVITY_PERIOD")
    private String activityPeriod;

    /** 账号 */
    @TableField("EBMS_SHOPS_NAME")
    private String ebmsShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 币种 */
    @TableField("TRANSACTION_CURRENCY_CODE")
    private String transactionCurrencyCode;

    /** 发货国 */
    @TableField("SALE_DEPART_COUNTRY")
    private BigDecimal saleDepartCountry;

    /** 目的国 */
    @TableField("SALE_ARRIVAL_COUNTRY")
    private BigDecimal saleArrivalCountry;

    /** Maketplace */
    @TableField("MAKETPLACE")
    private BigDecimal maketplace;

    /** seller-发货国B2B */
    @TableField("SALE_DEPART_COUNTRY_B")
    private BigDecimal saleDepartCountryB;

    /** 店铺维度生成状态（默认0：未生成，1：已生成） */
    @TableField("GENERATE_STATUS")
    private BigDecimal generateStatus;

    /** 创建人 */
    @TableField("CREATE_PERSON")
    private String createPerson;

    /** 创建时间--默认值：getdate,首次创建 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 最后更新时间--默认值：getdate */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 最后更新人名称--默认值：当前修改人名称 */
    @TableField("UPDATE_PERSON")
    private String updatePerson;

}