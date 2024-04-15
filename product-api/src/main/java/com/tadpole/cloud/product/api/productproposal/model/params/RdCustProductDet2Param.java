package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

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
 * @author S20210221
 * @since 2024-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_CUST_PRODUCT_DET2")
public class RdCustProductDet2Param implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 系统信息-定品申请编号 */
    @ApiModelProperty("系统信息-定品申请编号")
    private String sysCpCode;

    /** 申请信息-产品分类 */
    @ApiModelProperty("申请信息-产品分类")
    private String sysCpProductClass;

    /** 系统信息-开发样编号 */
    @ApiModelProperty("系统信息-开发样编号")
    private String sysKfyCode;

    /** 申请信息-颜色 */
    @ApiModelProperty("申请信息-颜色")
    private String sysCpColor;

    /** 申请信息-公司品牌 */
    @ApiModelProperty("申请信息-公司品牌")
    private String sysCpComBrand;

    @ApiModelProperty("系统信息-开发样编号2")
    private String sysKfyCodeOrg;

}
