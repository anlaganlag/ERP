package com.tadpole.cloud.platformSettlement.modular.vat.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 税金测算VAT店铺维度
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
@TableName("VAT_SHOP")
@ExcelIgnoreUnannotated
public class VatShop implements Serializable {

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

    /** 卖家自行缴纳VAT */
    @TableField("SELLER_VAT")
    private BigDecimal sellerVat;

    /** 亚马逊代扣代缴VAT */
    @TableField("AMAZON_VAT")
    private BigDecimal amazonVat;

    /** 跨境B2B免税 */
    @TableField("FREE_VAT")
    private BigDecimal freeVat;

    /** 核对表生成状态（默认0：未生成，1：已生成） */
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