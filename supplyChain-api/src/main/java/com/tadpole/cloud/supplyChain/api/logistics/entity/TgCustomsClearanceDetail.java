package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 清关单明细 实体类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TG_CUSTOMS_CLEARANCE_DETAIL")
@ExcelIgnoreUnannotated
public class TgCustomsClearanceDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 主表ID */
    @TableField("PID")
    private BigDecimal pid;

    /** 出货清单号 */
    @TableField("PACK_CODE")
    private String packCode;

    /** 箱号 */
    @TableField("BOX_NO")
    private String boxNo;

    /** 调拨单号 */
    @TableField("PACK_DIRECT_CODE")
    private String packDirectCode;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 清关品名 */
    @TableField("CLEAR_NAME_CN")
    private String clearNameCn;

    /** 开票英文品名 */
    @TableField("INVOICE_PRO_NAME_EN")
    private String invoiceProNameEn;

    /** HSCode */
    @TableField("HSCODE")
    private String hscode;

    /** 单价 */
    @TableField("UNIT_PRICE")
    private BigDecimal unitPrice;

    /** 销售价 */
    @TableField("AMAZON_SALE_PRICE")
    private BigDecimal amazonSalePrice;

    /** 清关单价 */
    @TableField("CUSTOMS_UNIT_PRICE")
    private BigDecimal customsUnitPrice;

    /** 销售链接 */
    @TableField("AMAZON_SALE_LINK")
    private String amazonSaleLink;

    /** 主图链接 */
    @TableField("AMAZON_PICTURE_LINK")
    private String amazonPictureLink;

    /** 带电 */
    @TableField("IS_CHARGED")
    private String isCharged;

    /** 带磁 */
    @TableField("IS_MAGNET")
    private String isMagnet;

    /** 报关英文材质 */
    @TableField("CLEAR_MATERIAL_EN")
    private String clearMaterialEn;

    /** 用途 */
    @TableField("FIT_BRAND")
    private String fitBrand;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** 规格型号 */
    @TableField("TYPE")
    private String type;

    /** 开票规格型号 */
    @TableField("STYLE")
    private String style;

    /** 数量 */
    @TableField("QUANTITY")
    private BigDecimal quantity;

    /** 数量单位 */
    @TableField("UNIT")
    private String unit;

    /** 套装属性 */
    @TableField("ATTRIBUTE")
    private String attribute;

    /** 分单号 */
    @TableField("SPLIT_ORDER")
    private String splitOrder;

    /** 公司品牌 */
    @TableField("COMPANY_BRAND")
    private String companyBrand;

    /** 开票品名 */
    @TableField("INVOICE_PRO_NAME_CN")
    private String invoiceProNameCn;

    /** 箱号上传名称 */
    @TableField("BOX_NO_NAME")
    private String boxNoName;

    /** 箱型 */
    @TableField("BOX_TYPE")
    private String boxType;

    /** 重量 */
    @TableField("WEIGHT")
    private BigDecimal weight;

    /** 长 */
    @TableField("GOODS_LONG")
    private BigDecimal goodsLong;

    /** 宽 */
    @TableField("WIDE")
    private BigDecimal wide;

    /** 高 */
    @TableField("HIGH")
    private BigDecimal high;

    /** 数据类型 0：导入，1：关联 */
    @TableField("DATA_TYPE")
    private String dataType;

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

    /** 是否关联 */
    @ApiModelProperty("是否关联")
    @TableField(exist = false)
    private String isDeal;

    /** 是否选中 */
    @ApiModelProperty("是否选中 true：选中，false：未选中")
    @TableField(exist = false)
    private Boolean isSelect;

    /** 销售价编辑状态 */
    @TableField("EDIT_STATUS")
    private String editStatus;

    /** 特殊清关合并标识 */
    @TableField("SPECIAL_MERGE_SIGN")
    private String specialMergeSign;

    /** 特殊清关合并主物料编码 */
    @TableField("SPECIAL_MATERIAL_CODE")
    private String specialMaterialCode;

    /** 特殊清关合并主物料ID */
    @TableField("SPECIAL_MATERIAL_ID")
    private String specialMaterialId;

}