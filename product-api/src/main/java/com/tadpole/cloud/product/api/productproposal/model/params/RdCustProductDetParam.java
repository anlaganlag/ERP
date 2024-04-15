package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 提案-定品明细 入参类
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
public class RdCustProductDetParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 系统信息-定品申请编号 */
    @ApiModelProperty("系统信息-定品申请编号")
    private String sysCpCode;

    /** 申请信息-开发样编号 */
    @ApiModelProperty("申请信息-开发样编号")
    private String sysKfyCode;

    /** 申请信息-产品分类 */
    @ApiModelProperty("申请信息-产品分类")
    private String sysCpProductClass;

    /** 申请信息-定品分析 */
    @ApiModelProperty("申请信息-定品分析")
    private String sysCpAnalysis;

    /** 申请信息-建议销售价 */
    @ApiModelProperty("申请信息-建议销售价")
    private BigDecimal sysCpSuggSalePrice;

    /** 申请信息-预估毛利率 */
    @ApiModelProperty("申请信息-预估毛利率")
    private BigDecimal sysCpEstGrossProMargin;

    /** 申请信息-是否选品 */
    @ApiModelProperty("申请信息-是否选品")
    private String sysCpIsSelect;

    /** 审批信息-是否定品 */
    @ApiModelProperty("审批信息-是否定品")
    private String sysCpIsCustProduct;

}
