package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
*
* </p>
*
* @author cyt
* @since 2022-05-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class RemovalDestroyApplyResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 流程编号 */
    @ApiModelProperty("APPLY_CODE")
    @ExcelProperty(value = "流程编号")
    private String applyCode;

    /** 申请标题 */
    @ApiModelProperty("APPLY_TITLE")
    @ExcelProperty(value = "申请标题")
    private String applyTitle;

    /** 申请人姓名 */
    @ApiModelProperty("APPLY_USER_NAME")
    @ExcelProperty(value = "申请人姓名")
    private String applyUserName;

    /** 申请流程名称 */
    @ApiModelProperty("APPLY_NAME")
    @ExcelProperty(value = "申请流程名称")
    private String applyName;

    /** 申请人工号 */
    @ApiModelProperty("APPLY_USER_ACCOUNT")
    @ExcelProperty(value = "申请人工号")
    private String applyUserAccount;

    /** 申请部门 */
    @ApiModelProperty("APPLY_DEPARTMENT")
    @ExcelProperty(value = "申请部门")
    private String applyDepartment;

    /** 申请时间 */
    @ApiModelProperty("APPLY_DATE")
    @ExcelProperty(value = "申请时间")
    private Date applyDate;

    /** 申请备注 */
    @ApiModelProperty("INCOME_REMARK")
    @ExcelProperty(value = "申请备注")
    private String incomeRemark;

    /** 账号 */
    @ApiModelProperty("SYS_SHOPS_NAME")
    @ExcelProperty(value = "账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("SYS_SITE")
    @ExcelProperty(value = "站点")
    private String sysSite;

    /** 商家SKU */
    @ApiModelProperty("MERCHANT_SKU")
    @ExcelProperty(value = "商家SKU")
    private String merchantSku;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value = "事业部")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    @ExcelProperty(value = "Team")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    /** Order ID */
    @ApiModelProperty("ORDER_ID")
    @ExcelProperty(value = "Order ID")
    private String orderId;

    /** 摊销期 */
    @ApiModelProperty("SHARE_NUM")
    @ExcelProperty(value = "摊销期")
    private BigDecimal shareNum;

    /** 申请数量 */
    @ApiModelProperty("APPLY_QUANTITY")
    @ExcelProperty(value = "申请数量")
    private int applyQuantity;

    /** 收购单价 */
    @ApiModelProperty("UNIT_PRICE")
    @ExcelProperty(value = "收购单价")
    private BigDecimal unitPrice;

    /** 总价 */
    @ApiModelProperty("TOTAL_PRICE")
    @ExcelProperty(value = "总价")
    private BigDecimal totalPrice;

    /** 备注 */
    @ApiModelProperty("REMARK")
    @ExcelProperty(value = "备注")
    private String remark;

    /** 银行账户 */
    @ApiModelProperty("BANK_ACCOUNT")
    @ExcelProperty(value = "银行账户")
    private String bankAccount;

    /** 银行名称 */
    @ApiModelProperty("BANK_NAME")
    @ExcelProperty(value = "银行名称")
    private String bankName;

    /** 账号所属 */
    @ApiModelProperty("BANK_USER")
    @ExcelProperty(value = "账号所属")
    private String bankUser;

    /** 银行账号 */
    @ApiModelProperty("BANK_USER_ACCOUNT")
    @ExcelProperty(value = "银行账号")
    private String bankUserAccount;

    /** 确认收款金额 */
    @ApiModelProperty("INCOME_AMOUMT")
    @ExcelProperty(value = "确认收款金额")
    private BigDecimal incomeAmoumt;

    /** 币别 */
    @ApiModelProperty("CURRENCY")
    @ExcelProperty(value = "币别")
    private String currency;

    /** 审批节点状态  1：申请，2：中心最高负责人审批，3：财务审批，4：总经理审批，5：归档 */
    @ApiModelProperty("AUDIT_STATUS")
    private String auditStatus;

    /** 审批人工号 */
    @ApiModelProperty("AUDIT_ACCOUNT")
    @ExcelProperty(value = "审批人工号")
    private String auditAccount;

    /** 审批人姓名 */
    @ApiModelProperty("AUDIT_NAME")
    @ExcelProperty(value = "审批人姓名")
    private String auditName;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    @ExcelProperty(value = "创建人")
    private String createBy;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("UPDATE_BY")
    @ExcelProperty(value = "更新人")
    private String updateBy;
}