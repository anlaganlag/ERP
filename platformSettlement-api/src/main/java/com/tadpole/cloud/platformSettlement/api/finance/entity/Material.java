package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 物料信息表
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
@TableName("RP_MATERIAL")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    @TableField("CATEGORY")
    private String category;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 产品名称 */
    @TableField("PRODUCT_NAME")
    private String productName;

    /** 款式 */
    @TableField("STYLE")
    private String style;

    /** 主材料 */
    @TableField("MAIN_MATERIAL")
    private String mainMaterial;

    /** 图案 */
    @TableField("DESIGN")
    private String design;

    /** 公司品牌 */
    @TableField("COMPANY_BRAND")
    private String companyBrand;

    /** 适用品牌或对象 */
    @TableField("FIT_BRAND")
    private String fitBrand;

    /** 型号 */
    @TableField("MODEL")
    private String model;

    /** 颜色 */
    @TableField("COLOR")
    private String color;

    /** 尺码 */
    @TableField("SIZES")
    private String sizes;

    /** 包装数量 */
    @TableField("PACKING")
    private String packing;

    /** 版本描述 */
    @TableField("VERSION")
    private String version;

    /** 适用机型 */
    @TableField("TYPE")
    private String type;

    /** 物料创建日期 */
    @TableField("CREATE_DATE")
    private Date createDate;


    /** 版本描述 */
    @TableField("VERSION_DES")
    private String versionDes;

    /** 二级类目 */
    @TableField("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 生产周期 */
    @TableField("MATPROCYCLE")
    private Integer matprocycle;

    /** 采购起订量 */
    @TableField("MINPOQTY")
    private Long minpoqty;

    /** 采购起订量备注 */
    @TableField("MINPOQTY_NOTES")
    private String minpoqtyNotes;

    /** SPU */
    @TableField("SPU")
    private String spu;

    /** NBDU */
    @TableField("NBDU")
    private String nbdu;

    /** 节日标签 */
    @TableField("FESTIVAL_LABEL")
    private String festivalLabel;

    /** 季节标签 */
    @TableField("SEASON_LABEL")
    private String seasonLabel;

    /** 建议物流方式 */
    @TableField("DELIVERY_TYPE")
    private String deliveryType;

    /** 是否可售：值  ：null,是，否 */
    @TableField("MAT_IS_AVAILABLE_SALE")
    private String matIsAvailableSale;

    /** 物料状态：禁用，启用，淘汰 */
    @TableField("STATUS")
    private String status;
}