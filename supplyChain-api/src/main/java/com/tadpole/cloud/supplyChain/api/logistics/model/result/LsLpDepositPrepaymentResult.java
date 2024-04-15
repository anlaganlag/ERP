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
 * 物流商押金&预付 出参类
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_LP_DEPOSIT_PREPAYMENT")
public class LsLpDepositPrepaymentResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ExcelProperty(value ="物流商编码")
    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ExcelProperty(value ="物流商名称")
    @ApiModelProperty("物流商名称")
    private String lpName;

    @ExcelProperty(value ="物流商简称")
    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    @ExcelProperty(value ="通讯地址")
    @ApiModelProperty("通讯地址")
    private String lpAddress;

    @ExcelProperty(value ="统一社会信用代码")
    @ApiModelProperty("统一社会信用代码")
    private String lpUniSocCreCode;

    @ExcelProperty(value ="押金")
    @ApiModelProperty("押金")
    private BigDecimal depositAmt;

    @ExcelProperty(value ="押金币种")
    @ApiModelProperty("押金币种")
    private String depositCurrency;

    @ExcelProperty(value ="预付款")
    @ApiModelProperty("预付款")
    private BigDecimal prepaymentAmt;

    @ExcelProperty(value ="预付款币种")
    @ApiModelProperty("预付款币种")
    private String paymentCurrency;

    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
