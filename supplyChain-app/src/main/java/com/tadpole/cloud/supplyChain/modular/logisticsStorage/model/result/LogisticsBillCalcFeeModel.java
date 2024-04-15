package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@ApiModel(value = "物流单更新时需要从新计算的费用集", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LogisticsBillCalcFeeModel implements Serializable {
    private static final long serialVersionUID = 1L;

    BigDecimal preLogFee = BigDecimal.ZERO; // 预估物流费
    BigDecimal preLogFuelFee = BigDecimal.ZERO; //预估燃油附加费
    BigDecimal preLogSurFeeTotal = BigDecimal.ZERO; // 预估物流附加费合计
    BigDecimal preLogTaxFeeTotal = BigDecimal.ZERO; //预估税费
    BigDecimal preLogFeeTotal = BigDecimal.ZERO; //预估总费用
    BigDecimal preLogBusySeasonAddFee = BigDecimal.ZERO; //预估旺季附加费
    BigDecimal preLogAddAndSundryFee = BigDecimal.ZERO; //预估附加费及杂费
    BigDecimal preLogCustDlearanceFee = BigDecimal.ZERO; //预估报关费
    BigDecimal preLogCustClearanceFee = BigDecimal.ZERO; //预估清关费
    BigDecimal preLogTaxFee = BigDecimal.ZERO; //预估税费
    BigDecimal preLogFeeTotalManual = BigDecimal.ZERO; //预估
    BigDecimal preLogUnitPrice =BigDecimal.ZERO; //预估单价
    BigDecimal logFee = BigDecimal.ZERO; //物流费
    BigDecimal logTaxFee = BigDecimal.ZERO; //税费
    BigDecimal logFeeTotal = BigDecimal.ZERO; //总费用

    String lerLogComfirmBillCurrency = "";
    String lerPreLogUnitPriceType = "";

    //以下正常有报价时的字段需要赋值

    /** 预估计费方式 */
    @ApiModelProperty(value = "预估计费方式")
    private String lerPreChargType ;


    /** 预估总费用 */
    @ApiModelProperty(value = "预估总费用")
    @ExcelProperty(value ="预估总费用")
    private BigDecimal preLogFeeTotalNew ;



}
