package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
 * <p>
 * 提案-退货款记录 入参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_SAMPLE_RPR")
public class RdSampleRprParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-退款申请编号 */
   @TableId(value = "SYS_REF_CODE", type = IdType.ASSIGN_ID)
    private String sysRefCode;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;

    /** 系统信息-部门编号 */
    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;

    /** 系统信息-部门名称 */
    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;

    /** 系统信息-员工编号 */
    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;

    /** 退货申请信息-退货操作时间 */
    @ApiModelProperty("退货申请信息-退货操作时间")
    private Date sysRefOpDate;

    /** 退货申请信息-退货数量 */
    @ApiModelProperty("退货申请信息-退货数量")
    private BigDecimal sysRefQty;

    /** 退货申请信息-预计退款金额 */
    @ApiModelProperty("退货申请信息-预计退款金额")
    private BigDecimal sysRefPreAmount;

    /** 单据联系-产品线编号 */
    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    /** 单据联系-SPU */
    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;

    /** 单据联系-提案编号 */
    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;

    /** 单据联系-拿样任务编号 */
    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;

    /** 单据联系-费用申请编号 */
    @ApiModelProperty("单据联系-费用申请编号")
    private String sysFeeAppCode;

    /** 单据联系-费用申请来源 值域{"购样申请","定制申请"} */
    @ApiModelProperty("单据联系-费用申请来源 值域{'购样申请','定制申请'}")
    private String sysFeeAppSource;

    /** 退款信息-差额说明 */
    @ApiModelProperty("退款信息-差额说明")
    private String sysRefVd;

    /** 退款确认信息-支付宝账户 */
    @ApiModelProperty("退款确认信息-支付宝账户")
    private String sysRefAlipayA;

    /** 退款确认信息-支付宝账户户名 */
    @ApiModelProperty("退款确认信息-支付宝账户户名")
    private String sysRefAlipayAn;

    /** 退款确认信息-退款日期 */
    @ApiModelProperty("退款确认信息-退款日期")
    private Date sysRefDate;

    /** 退款确认信息-实际退款金额 */
    @ApiModelProperty("退款确认信息-实际退款金额")
    private BigDecimal sysRefRealAmount;

    /** 退款确认信息-退款登记时间 */
    @ApiModelProperty("退款确认信息-退款登记时间")
    private Date sysRefRgDate;

    /** 退款确认信息-退款确认人编号 */
    @ApiModelProperty("退款确认信息-退款确认人编号")
    private String sysRefRgPerCode;

    /** 退款确认信息-退款确认人姓名 */
    @ApiModelProperty("退款确认信息-退款确认人姓名")
    private String sysRefRgPerName;

    /** 单据联系-费用申请编号集合 */
    @ApiModelProperty("单据联系-费用申请编号集合")
    private List<String> sysFeeAppCodeList;

}
