package com.tadpole.cloud.externalSystem.api.ebms.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 海外仓出库管理明细入参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbmsOverseasOutWarehouseDetailBoxInfoParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 编号 */
    @ApiModelProperty("编号")
    private String packDetID;

    /** 票单号 */
    @ApiModelProperty("票单号")
    private String packCode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private BigDecimal packDetBoxNum;

    /** 箱号上传名称 */
    @ApiModelProperty("箱号上传名称")
    private String packDetBoxNumUpload;

    /** 箱型 */
    @ApiModelProperty("箱型")
    private String packDetBoxType;

    /** 箱长 */
    @ApiModelProperty("箱长")
    private BigDecimal packDetBoxLength ;

    /** 箱宽 */
    @ApiModelProperty("箱宽")
    private BigDecimal packDetBoxWidth;

    /** 箱高 */
    @ApiModelProperty("箱高")
    private BigDecimal packDetBoxHeight;

    /** 重量 */
    @ApiModelProperty("重量")
    private BigDecimal packDetBoxWeight ;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String packDetBoxCode;

    /** 规格单位 */
    @ApiModelProperty("规格单位")
    private String packDetBoxSpeUnit;

    /** 体积单位 */
    @ApiModelProperty("体积单位")
    private String packDetBoxVoluUnit;

    /** 体积 */
    @ApiModelProperty("体积")
    private BigDecimal packDetBoxVolume;

    /** 重量单位 */
    @ApiModelProperty("重量单位")
    private String packDetBoxWeigUnit ;

}