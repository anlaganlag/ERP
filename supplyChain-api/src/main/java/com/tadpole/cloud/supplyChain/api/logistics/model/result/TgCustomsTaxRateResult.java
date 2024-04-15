package com.tadpole.cloud.supplyChain.api.logistics.model.result;

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
import java.util.Date;

/**
 * <p>
 * 清关税率 出参类
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
@ExcelIgnoreUnannotated
@TableName("TG_CUSTOMS_TAX_RATE")
public class TgCustomsTaxRateResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("国家编码")
    private String countryCode;

    @ExcelProperty(value ="国家")
    @ApiModelProperty("国家名称")
    private String countryName;

    @ExcelProperty(value ="HSCode")
    @ApiModelProperty("HSCode")
    private String hsCode;

    @ExcelProperty(value ="流转税率（欧洲VAT/加拿大GST/日本消费税）")
    @ApiModelProperty("流转税率（欧洲VAT/加拿大GST/日本消费税）")
    private BigDecimal changeTaxRate;

    @ExcelProperty(value ="关税率")
    @ApiModelProperty("关税率")
    private BigDecimal taxRate;

    @ExcelProperty(value ="附加税")
    @ApiModelProperty("附加税")
    private BigDecimal addTaxRate;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
