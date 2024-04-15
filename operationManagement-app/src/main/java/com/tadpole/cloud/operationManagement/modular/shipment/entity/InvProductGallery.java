package com.tadpole.cloud.operationManagement.modular.shipment.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * AmazSKU数据来源EBMS--TbAmazSKU
    * </p>
*
* @author lsy
* @since 2023-02-03
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("INV_PRODUCT_GALLERY")
@ExcelIgnoreUnannotated
public class InvProductGallery implements Serializable {

    private static final long serialVersionUID = 1L;


    /** SKU */
    @TableField("SKU")
    private String sku;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 店铺名简称 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 部门 */
    @TableField("DEPARTMENT")
    private String department;

    /** team */
    @TableField("TEAM")
    private String team;

    /** asin */
    @TableField("ASIN")
    private String asin;

    /** FNSKU */
    @TableField("FNSKU")
    private String fnsku;

    @TableField("LABUSESTATE")
    private String labusestate;

    @TableField("SKUCODE")
    private String skucode;

    @TableField("DELIVERY_TYPE")
    private String deliveryType;

    /** 最后一次更新时间 */
    @TableField("LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    /** 销售品牌 */
    @TableField("SALES_BRAND")
    private String salesBrand;

    /** 状态 */
    @TableField("STATUS")
    private BigDecimal status;

    @TableField("ETL_INSERT_TM")
    private Date etlInsertTm;

    /** 创建日期 */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** sku类型 */
    @TableField("SKU_TYPE")
    private String skuType;

    /** asin状态 */
    @TableField("ASINSTATU")
    private String asinstatu;

    /** 区域 */
    @TableField("AREA")
    private String area;

    /** sku标签类型 */
    @TableField("SYS_LABEL_TYPE")
    private String sysLabelType;

    /** 发货模式 */
    @TableField("SYS_LOGI_MODE")
    private String sysLogiMode;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 店铺名称+站点 */
    @TableField("SHOP_NAME")
    private String shopName;

}