package com.tadpole.cloud.operationManagement.modular.shipment.entity;

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
    * 店铺库存销量明细表
    * </p>
*
* @author lsy
* @since 2023-02-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("AMZ_INV_SKU_DAY_AGG")
@ExcelIgnoreUnannotated
public class AmzInvSkuDayAgg implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 日期 */
    @TableField("BIZDATE")
    private Date bizdate;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 区域 */
    @TableField("PRE_AREA")
    private String preArea;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team */
    @TableField("TEAM")
    private String team;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** ASIN */
    @TableField("ASIN")
    private String asin;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** 销售品牌 */
    @TableField("SALES_BRAND")
    private String salesBrand;

    /** SKU近7天销量 */
    @TableField("DAY7QTY")
    private BigDecimal day7qty;

    /** SKU近30天销量 */
    @TableField("DAY30QTY")
    private BigDecimal day30qty;

    /** SKU海外总库存 */
    @TableField("AZ_OVERSEA_TOTAL_ATY")
    private BigDecimal azOverseaTotalAty;

    /** SKU可售库存 */
    @TableField("AZ_AVAIL_QTY")
    private BigDecimal azAvailQty;

    /** 创建人 */
    @TableField("CREATED_BY")
    private String createdBy;

    /** 创建时间 */
    @TableField("CREATED_TIME")
    private Date createdTime;

    /** 更新人 */
    @TableField("UPDATED_BY")
    private String updatedBy;

    /** 更新时间 */
    @TableField("UPDATED_TIME")
    private Date updatedTime;


   /**
    * 站点
    */
   @TableField("SYS_SITE")
   private String sysSite;

}