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
 * 报关单明细 实体类
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
@TableName("TG_CUSTOMS_APPLY_DETAIL")
@ExcelIgnoreUnannotated
public class TgCustomsApplyDetail implements Serializable {

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

    /** 海关编码 */
    @TableField("CUSTOMS_NUM")
    private String customsNum;

    /** 要素 */
    @TableField("ESSENTIAL_FACTOR")
    private String essentialFactor;

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

    /** 币制 */
    @TableField("CURRENCY")
    private String currency;

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

    /** 箱子对应数据的数据条数，用于判断箱子的数据是否全部选中 */
    @ApiModelProperty("箱子对应数据的数据条数")
    @TableField(exist = false)
    private Integer BoxNumAccount;

    /** 报关导入箱号相同的对应的箱子个数 */
    @TableField(exist = false)
    private BigDecimal boxNum;

    /** MC是否存在此箱型 0：否，1：是 */
    @TableField(exist = false)
    private Boolean hasBoxType;

}