package com.tadpole.cloud.product.api.productproposal.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 提案-定品明细 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_CUST_PRODUCT_DET2")
public class RdCustProductDet2Result implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private String id;


    @ApiModelProperty("系统信息-定品申请编号")
    private String sysCpCode;


    @ApiModelProperty("申请信息-产品分类")
    private String sysCpProductClass;


    @ApiModelProperty("系统信息-开发样编号")
    private String sysKfyCode;


    @ApiModelProperty("申请信息-颜色")
    private String sysCpColor;


    @ApiModelProperty("申请信息-公司品牌")
    private String sysCpComBrand;

    @ApiModelProperty("系统信息-开发样编号2")
    private String sysKfyCodeOrg;

}
