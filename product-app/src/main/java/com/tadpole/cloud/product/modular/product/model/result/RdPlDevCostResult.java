package com.tadpole.cloud.product.modular.product.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 产品线管理 出参类
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
//@TableName("RD_PL_MANAGE")
public class RdPlDevCostResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("系统信息-产品线编号")
    private String sysPlCode;

    @ApiModelProperty("购样费")
    private BigDecimal sysAppPaFee;

    @ApiModelProperty("打样费")
    private BigDecimal sysAppCaFee;

    @ApiModelProperty("开模费")
    private BigDecimal sysAppCaMoFee;

    @ApiModelProperty("网版费")
    private BigDecimal sysAppWbFee;

    @ApiModelProperty("专利费")
    private BigDecimal sysAppZlFee;

    @ApiModelProperty("版权费")
    private BigDecimal sysAppBqFee;

    @ApiModelProperty("检测费")
    private BigDecimal sysAppJcFee;

    @ApiModelProperty("耗材费")
    private BigDecimal sysAppHcFee;

}
