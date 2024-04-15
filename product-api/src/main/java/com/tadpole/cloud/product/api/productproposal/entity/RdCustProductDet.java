package com.tadpole.cloud.product.api.productproposal.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 提案-定品明细 实体类
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_CUST_PRODUCT_DET")
@ExcelIgnoreUnannotated
public class RdCustProductDet implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统信息-定品申请编号 */
    @TableField("SYS_CP_CODE")
    private String sysCpCode;

    /** 申请信息-开发样编号 */
    @TableField("SYS_KFY_CODE")
    private String sysKfyCode;

    /** 申请信息-产品分类 */
    @TableField("SYS_CP_PRODUCT_CLASS")
    private String sysCpProductClass;

    /** 申请信息-定品分析 */
    @TableField("SYS_CP_ANALYSIS")
    private String sysCpAnalysis;

    /** 申请信息-建议销售价 */
    @TableField("SYS_CP_SUGG_SALE_PRICE")
    private BigDecimal sysCpSuggSalePrice;

    /** 申请信息-预估毛利率 */
    @TableField("SYS_CP_EST_GROSS_PRO_MARGIN")
    private BigDecimal sysCpEstGrossProMargin;

    /** 申请信息-是否选品 */
    @TableField("SYS_CP_IS_SELECT")
    private String sysCpIsSelect;

    /** 审批信息-是否定品 */
    @TableField("SYS_CP_IS_CUST_PRODUCT")
    private String sysCpIsCustProduct;

    /** 申请信息-开发样编号 */
    @TableField("SYS_KFY_CODE_ORG")
    private String sysKfyCodeOrg;

}