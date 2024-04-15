package com.tadpole.cloud.platformSettlement.modular.vat.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 基础信息-税号列表
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("BASE_TAX_LIST")
public class BaseTaxListResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 税号id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 国家缩写--国家简称 */
    @ExcelProperty("国家缩写")
    @ApiModelProperty("COUNTRY")
    private String country;

    /** 账号 */
    @ExcelProperty("账号")
    @ApiModelProperty("ACCOUNT")
    private String account;

    /** 税号 */
    @ExcelProperty("税号")
    @ApiModelProperty("TAX_ID")
    private String taxId;

    /** 是否有税号--默认值：1，【1：是、0：否】 */
    @ApiModelProperty("IS_TAX_ID")
    private BigDecimal isTaxId;

    /** 税号状态 */
    @ExcelProperty("税号状态")
    @ApiModelProperty("TAX_ID_STATUS")
    private String taxIdStatus;

    @ExcelProperty("是否有税号")
    @ApiModelProperty("IS_TAX_ID")
    private String isTaxNum;

    @ExcelProperty("国家英文名")
    @ApiModelProperty("COUNTRY_EN")
    private String countryEn;

    @ExcelProperty("国家中文名")
    @ApiModelProperty("countryCn")
    private String countryCn;

}