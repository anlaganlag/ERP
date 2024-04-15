package com.tadpole.cloud.platformSettlement.modular.vat.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
 * 基础信息-税率表
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
@TableName("BASE_TAX_RATE")
public class BaseTaxRateResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 税率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    /** 税率 */
    @ApiModelProperty("TAX_RATE")
    private BigDecimal taxRate;

    /** 生效日期 */
    @ApiModelProperty("EFFECTIVE_DATE")
    private Date effectiveDate;

    /** 失效日期 */
    @ApiModelProperty("EXPIRATION_DATE")
    private Date expirationDate;

    /** 是否删除--默认值：0，【0：启用、1：删除】 */
    @ApiModelProperty("STATUS")
    private BigDecimal status;
}