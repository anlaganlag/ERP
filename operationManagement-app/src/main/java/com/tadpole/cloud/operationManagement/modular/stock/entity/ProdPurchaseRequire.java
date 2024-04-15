package com.tadpole.cloud.operationManagement.modular.stock.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 新品采购申请
    * </p>
*
* @author gal
* @since 2022-10-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("PROD_PURCHASE_REQUIRE")
@ExcelIgnoreUnannotated
public class ProdPurchaseRequire implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 采购申请编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 物料编码 */
    @TableField("MAT_CODE")
    private String matCode;

    /** 下单需求整理编号 */
    @TableField("PROD_ORDER_DEMAND_ID")
    private String prodOrderDemandId;

    /** 创建部门编号 */
    @TableField("CREATE_DEPT_CODE")
    private String createDeptCode;

    /** 创建部门名称 */
    @TableField("CREATE_DEPT_NAME")
    private String createDeptName;

    /** 创建人编号 */
    @TableField("CREATE_PER_CODE")
    private String createPerCode;

    /** 创建人姓名 */
    @TableField("CREATE_PER_NAME")
    private String createPerName;

    /** 创建日期 */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** 生效日期 */
    @TableField("EFFECTIVE_DATE")
    private Date effectiveDate;

    /** 采购申请单类型 */
    @TableField("PUR_REQUIRE_TYPE")
    private String purRequireType;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team组 */
    @TableField("TEAM")
    private String team;

    /** 申请人编号 */
    @TableField("APPLY_PER_CODE")
    private String applyPerCode;

    /** 申请人姓名 */
    @TableField("APPLY_PER_NAME")
    private String applyPerName;

    /** 下单部门编号 */
    @TableField("MAT_ORDER_DEPT_CODE")
    private String matOrderDeptCode;

    /** 下单部门名称 */
    @TableField("MAT_ORDER_DEPT_NAME")
    private String matOrderDeptName;

    /** 运营申请数量 */
    @TableField("OPR_APPLY_QTY")
    private BigDecimal oprApplyQty;

    /** 运营期望交期 */
    @TableField("OPR_EXPECT_DELIVE_DATE")
    private Date oprExpectDeliveDate;

    /** 申请单状态 */
    @TableField("APPLY_STATUS")
    private String applyStatus;

    /** 建议供应商 */
    @TableField("SUGGEST_SUPPLIER")
    private String suggestSupplier;


    /** 是否含税 */
    @TableField("IS_INCLUDE_TAX")
    private String isIncludeTax;

    /** 下单方式 */
    @TableField("ORDER_METHOD")
    private String orderMethod;

    /** 复核部门编码 */
    @TableField("CHECK_DEPT_CODE")
    private String checkDeptCode;

    /** 复核部门名称 */
    @TableField("CHECK_DEPT_NAME")
    private String checkDeptName;

    /** 复核人编码 */
    @TableField("CHECK_PER_CODE")
    private String checkPerCode;

    /** 复核人姓名 */
    @TableField("CHECK_PER_NAME")
    private String checkPerName;

    /** 复核日期 */
    @TableField("CHECK_DATE")
    private Date checkDate;

    /** 复核数量 */
    @TableField("OPR_APPLY_QTY3")
    private BigDecimal oprApplyQty3;

    /** 采购预计交期 */
    @TableField("PUR_EXPECT_DELIVE_DATE")
    private Date purExpectDeliveDate;

    /** 复核说明 */
    @TableField("CHECK_EXPLAIN")
    private String checkExplain;

    /** PMC审批人编号 */
    @TableField("PMC_APPROVE_PER_CODE")
    private String pmcApprovePerCode;

    /** PMC审批人姓名 */
    @TableField("PMC_APPROVE_PER_NAME")
    private String pmcApprovePerName;

    /** PMC审批日期 */
    @TableField("PMC_APPROVE_DATE")
    private Date pmcApproveDate;

    /** PMC申请说明 */
    @TableField("PMC_APPLY_EXPLAIN")
    private String pmcApplyExplain;

    /** 下单编号 */
    @TableField("ORDER_ID")
    private String orderId;

    /** 建议供应商ID */
    @TableField("SUGGEST_SUPPLIER_ID")
    private String suggestSupplierId;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功,-1待同步) */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步结果 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 同步请求参数 */
    @TableField("SYNC_REQUEST_MSG")
    private String syncRequestMsg;




    /** 前端采购人 */
    @TableField("FRONT_PUR_NAME")
    private String frontPurName;

    /** 前端采购人编号 */
    @TableField("FRONT_PUR_CODE")
    private String frontPurCode;
}