package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 物流费付款 入参类
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
@TableName("LS_LOGISTICS_FEE_PAYMENT")
public class LsLogisticsFeePaymentParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物流费编号 */
    @ApiModelProperty("物流费编号")
    private String logisticsFeeNo;

    /** 物流商编码 */
    @ApiModelProperty("物流商编码")
    private String lpCode;

    /** 物流商名称 */
    @ApiModelProperty("物流商名称")
    private String lpName;

    /** 物流商简称 */
    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    /** 物流费 */
    @ApiModelProperty("物流费")
    private BigDecimal totalLogisticsFee;

    /** 税费 */
    @ApiModelProperty("税费")
    private BigDecimal totalTaxFee;

    /** 付款费用 */
    @ApiModelProperty("付款费用")
    private BigDecimal totalPaymentFee;

    /** 付款申请状态：未申请，申请成功，申请失败 */
    @ApiModelProperty("付款申请状态：未申请，申请成功，申请失败")
    private String paymentApplyStatus;

    /** ERP申请日期 */
    @ApiModelProperty("ERP申请日期")
    private Date erpApplyDate;

    /** 付款申请编号 */
    @ApiModelProperty("付款申请编号")
    private String paymentApplyNo;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

    /** 物流费编号集合 */
    @ApiModelProperty("物流费编号集合")
    private List<String> logisticsFeeNoList;

    /** ERP申请日期开始时间 */
    @ApiModelProperty("ERP申请日期开始时间")
    private String erpApplyStartDate;

    /** ERP申请日期结束时间 */
    @ApiModelProperty("ERP申请日期结束时间")
    private String erpApplyEndDate;

    /** 付款申请编号集合 */
    @ApiModelProperty("付款申请编号集合")
    private List<String> paymentApplyNoList;

    /** 付款申请状态集合：未申请，申请成功，申请失败 */
    @ApiModelProperty("付款申请状态集合：未申请，申请成功，申请失败")
    private List<String> paymentApplyStatusList;

    /** 发货批次号集合 */
    @ApiModelProperty("发货批次号集合")
    private List<String> shipmentNumList;

    /** 物流对账单号集合 */
    @ApiModelProperty("物流对账单号集合")
    private List<String> logisticsCheckOrderList;

}
