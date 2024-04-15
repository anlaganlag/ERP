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
 * 物流费付款 出参类
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LS_LOGISTICS_FEE_PAYMENT")
public class LsLogisticsFeePaymentResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ExcelProperty(value ="物流费编号")
    @ApiModelProperty("物流费编号")
    private String logisticsFeeNo;

    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ExcelProperty(value ="物流商名称")
    @ApiModelProperty("物流商名称")
    private String lpName;

    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    @ExcelProperty(value ="物流费合计")
    @ApiModelProperty("物流费")
    private BigDecimal totalLogisticsFee;

    @ExcelProperty(value ="税费合计")
    @ApiModelProperty("税费")
    private BigDecimal totalTaxFee;

    @ExcelProperty(value ="申请付款金额")
    @ApiModelProperty("付款费用")
    private BigDecimal totalPaymentFee;

    @ExcelProperty(value ="付款申请状态")
    @ApiModelProperty("付款申请状态：未申请，申请成功，申请失败")
    private String paymentApplyStatus;

    @ExcelProperty(value ="申请日期")
    @ApiModelProperty("ERP申请日期")
    private Date erpApplyDate;

    @ExcelProperty(value ="付款申请单编号")
    @ApiModelProperty("付款申请编号")
    private String paymentApplyNo;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
