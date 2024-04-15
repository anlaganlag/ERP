package com.tadpole.cloud.platformSettlement.api.inventory.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class RemovalDestroyApplyParam extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 账号 */
    @ApiModelProperty("账号 SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点SYS_SITE")
    private String sysSite;

    /** 商家SKU */
    @ApiModelProperty("商家SKUMERCHANT_SKU")
    private String merchantSku;

    /** 事业部 */
    @ApiModelProperty("事业部DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("运营大类PRODUCT_TYPE")
    private String productType;

    /** Order ID */
    @ApiModelProperty("ORDER_ID")
    private String orderId;

    /** 摊销期 */
    @ApiModelProperty("摊销期SHARE_NUM")
    private BigDecimal shareNum;

    /** 申请数量 */
    @ApiModelProperty("申请数量APPLY_QUANTITY")
    private int applyQuantity;

    /** 收购单价 */
    @ApiModelProperty("收购单价UNIT_PRICE")
    private BigDecimal unitPrice;

    /** 总价 */
    @ApiModelProperty("总价TOTAL_PRICE")
    private BigDecimal totalPrice;

    /** 备注 */
    @ApiModelProperty("备注REMARK")
    private String remark;

    /** 银行账户 */
    @ApiModelProperty("银行账户BANK_ACCOUNT")
    private String bankAccount;

    /** 银行名称 */
    @ApiModelProperty("银行名称BANK_NAME")
    private String bankName;

    /** 账号所属 */
    @ApiModelProperty("账号所属BANK_USER")
    private String bankUser;

    /** 银行账号 */
    @ApiModelProperty("银行账号BANK_USER_ACCOUNT")
    private String bankUserAccount;

    /** 确认收款金额 */
    @ApiModelProperty("确认收款金额INCOME_AMOUMT")
    private BigDecimal incomeAmoumt;

    /** 币别 */
    @ApiModelProperty("币别CURRENCY")
    private String currency;

    /** 审批节点状态  0：初始导入，1：申请，2：中心最高负责人审批，3：财务审批，4：总经理审批，5：归档 */
    @ApiModelProperty("审批节点状态AUDIT_STATUS")
    private String auditStatus;

    /** 审批人工号 */
    @ApiModelProperty("审批人工号AUDIT_ACCOUNT")
    private String auditAccount;

    /** 审批人姓名 */
    @ApiModelProperty("审批人姓名AUDIT_NAME")
    private String auditName;

    /** 创建时间 */
    @ApiModelProperty("创建时间CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人CREATE_BY")
    private String createBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人UPDATE_BY")
    private String updateBy;

    /** 流程编号 */
    @ApiModelProperty("流程编号APPLY_CODE")
    private String applyCode;

    /** 申请标题 */
    @ApiModelProperty("APPLY_TITLE")
    private String applyTitle;

    /** 申请人姓名 */
    @ApiModelProperty("APPLY_USER_NAME")
    private List applyUserName;

    /** 流程名称 */
    @ApiModelProperty("APPLY_NAME")
    private String applyName;

    /** 申请人工号 */
    @ApiModelProperty("APPLY_USER_ACCOUNT")
    private String applyUserAccount;

    /** 申请部门 */
    @ApiModelProperty("APPLY_DEPARTMENT")
    private String applyDepartment;

    /** 申请时间 */
    @ApiModelProperty("APPLY_DATE")
    private String applyDate;

    /** 申请备注 */
    @ApiModelProperty("INCOME_REMARK")
    private String incomeRemark;

    private String category;
}