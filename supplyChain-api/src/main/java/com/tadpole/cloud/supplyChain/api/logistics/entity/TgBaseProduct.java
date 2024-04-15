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
 *  通关产品基础信息 实体类
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
@TableName("TG_BASE_PRODUCT")
@ExcelIgnoreUnannotated
public class TgBaseProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("物料编码")
    @TableField("MATERIAL_CODE")
    private String materialCode;

    @ApiModelProperty("主物料")
    @TableField("MAIN_MATERIAL_CODE")
    private String mainMaterialCode;

    @ApiModelProperty("转换前物料编码")
    @TableField("PRE_MATERIAL_CODE")
    private String preMaterialCode;

    @ApiModelProperty("物料类型：普通物料，转换物料，组合物料")
    @TableField("MATERIAL_TYPE")
    private String materialType;

    @ApiModelProperty("产品名称")
    @TableField("PRODUCT_NAME")
    private String productName;

    @ApiModelProperty("公司品牌")
    @TableField("COMPANY_BRAND")
    private String companyBrand;

    @ApiModelProperty("开票品名（中文）")
    @TableField("INVOICE_PRO_NAME_CN")
    private String invoiceProNameCn;

    @ApiModelProperty("开票品名（英文）")
    @TableField("INVOICE_PRO_NAME_EN")
    private String invoiceProNameEn;

    @ApiModelProperty("报关材质（中文）")
    @TableField("CLEAR_MATERIAL_CN")
    private String clearMaterialCn;

    @ApiModelProperty("报关材质（英文）")
    @TableField("CLEAR_MATERIAL_EN")
    private String clearMaterialEn;

    @ApiModelProperty("海关编码")
    @TableField("CUSTOMS_CODE")
    private String customsCode;

    @ApiModelProperty("规格型号")
    @TableField("FIT_BRAND")
    private String fitBrand;

    @ApiModelProperty("开票规格型号")
    @TableField("STYLE")
    private String style;

    @ApiModelProperty("是否带电 0：否，1：是")
    @TableField("IS_CHARGED")
    private String isCharged;

    @ApiModelProperty("是否带磁 0：否，1：是")
    @TableField("IS_MAGNET")
    private String isMagnet;

    @ApiModelProperty("要素")
    @TableField("ESSENTIAL_FACTOR")
    private String essentialFactor;

    @ApiModelProperty("不符合要素框架的部分")
    @TableField("ESSENTIAL_FACTOR_TEMP")
    private String essentialFactorTemp;

    @ApiModelProperty("审核状态 0：未审核，1：审核通过，2弃审")
    @TableField("AUDIT_STATUS")
    private String auditStatus;

    @ApiModelProperty("审核人")
    @TableField("AUDIT_USER")
    private String auditUser;

    @ApiModelProperty("审核时间")
    @TableField("AUDIT_TIME")
    private Date auditTime;

    @ApiModelProperty("供应商")
    @TableField("ADVICE_SUPPLIER")
    private String adviceSupplier;

    @ApiModelProperty("采购价")
    @TableField("K3_PRICE")
    private BigDecimal k3Price;

    @ApiModelProperty("是否含税 0：否，1：是")
    @TableField("INCLUDE_TAX")
    private String includeTax;

    @ApiModelProperty("能否开票 0：否，1：能")
    @TableField("IS_MAKE_INVOICE")
    private String isMakeInvoice;

    @ApiModelProperty("退税率")
    @TableField("TAX_REFUND")
    private BigDecimal taxRefund;

    @ApiModelProperty("毛利率")
    @TableField("GROSS_PROFIT_RATE")
    private BigDecimal grossProfitRate;

    @ApiModelProperty("重量")
    @TableField("GROSS_WEIGHT_ORG")
    private BigDecimal grossWeightOrg;

    @ApiModelProperty("重量单位")
    @TableField("WEIGHT_UNIT_ORG")
    private String weightUnitOrg;

    @TableField("DATA_TYPE")
    private String dataType;

    @ApiModelProperty("创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty("创建人")
    @TableField("CREATE_USER")
    private String createUser;

    @ApiModelProperty("更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty("更新人")
    @TableField("UPDATE_USER")
    private String updateUser;

    @ApiModelProperty("备注")
    @TableField("REMARK")
    private String remark;

    /** 明细ID */
    @ApiModelProperty("明细ID")
    @TableField(exist = false)
    private String detailId;

    /** 是否选中 */
    @ApiModelProperty("是否选中 0：未选中，1：选中")
    @TableField(exist = false)
    private String isSelect;

}