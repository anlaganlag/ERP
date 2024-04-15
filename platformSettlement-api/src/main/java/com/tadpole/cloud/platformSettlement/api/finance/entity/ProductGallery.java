package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* sku信息表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RP_PRODUCT_GALLERY")
public class ProductGallery implements Serializable {

    private static final long serialVersionUID = 1L;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 账号简称 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** 销售团队 */
    @TableField("TEAM")
    private String team;

    /** ASIN */
    @TableField("ASIN")
    private String asin;

    /** FNSKU */
    @TableField("FNSKU")
    private String fnsku;

    /** SKU发货标签状态 */
    @TableField("LABUSESTATE")
    private String labusestate;

    /** SKU编码 */
    @TableField("SKUCODE")
    private String skucode;

    /** 派送方式 */
    @TableField("DELIVERY_TYPE")
    private String deliveryType;

    /** 最后更新时间 */
    @TableField("LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    /** 销售品牌 */
    @TableField("SALES_BRAND")
    private String salesBrand;

    /** SKU状态 */
    @TableField("STATUS")
    private Integer status;

    /** ETL抽数时间 */
    @TableField("ETL_INSERT_TM")
    private LocalDateTime etlInsertTm;

    /** 创建时间 */
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    /** asin状态(包含已归档和未归档,file归档) */
    @TableField("ASINSTATU")
    private String asinstatu;

}