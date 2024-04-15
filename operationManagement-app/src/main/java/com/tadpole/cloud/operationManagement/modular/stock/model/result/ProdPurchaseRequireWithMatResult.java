package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
//@TableName("PROD_PURCHASE_REQUIRE")
public class ProdPurchaseRequireWithMatResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 采购申请编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 物料编码 */
    @ApiModelProperty("MAT_CODE")
    private String matCode;

    /** 下单需求整理编号 */
    @ApiModelProperty("PROD_ORDER_DEMAND_ID")
    private String prodOrderDemandId;

    /** 创建部门编号 */
    @ApiModelProperty("CREATE_DEPT_CODE")
    private String createDeptCode;

    /** 创建部门名称 */
    @ApiModelProperty("CREATE_DEPT_NAME")
    private String createDeptName;

    /** 创建人编号 */
    @ApiModelProperty("CREATE_PER_CODE")
    private String createPerCode;

    /** 创建人姓名 */
    @ApiModelProperty("CREATE_PER_NAME")
    private String createPerName;

    /** 创建日期 */
    @ApiModelProperty("CREATE_DATE")
    private Date createDate;

    /** 生效日期 */
    @ApiModelProperty("EFFECTIVE_DATE")
    private Date effectiveDate;

    /** 采购申请单类型 */
    @ApiModelProperty("PUR_REQUIRE_TYPE")
    private String purRequireType;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team组 */
    @ApiModelProperty("TEAM")
    private String team;

    /** 申请人编号 */
    @ApiModelProperty("APPLY_PER_CODE")
    private String applyPerCode;

    /** 申请人姓名 */
    @ApiModelProperty("APPLY_PER_NAME")
    private String applyPerName;

    /** 下单部门编号 */
    @ApiModelProperty("MAT_ORDER_DEPT_CODE")
    private String matOrderDeptCode;

    /** 下单部门名称 */
    @ApiModelProperty("MAT_ORDER_DEPT_NAME")
    private String matOrderDeptName;

    /** 运营申请数量 */
    @ApiModelProperty("OPR_APPLY_QTY")
    private BigDecimal oprApplyQty;

    /** 运营期望交期 */
    @ApiModelProperty("OPR_EXPECT_DELIVE_DATE")
    private Date oprExpectDeliveDate;

    /** 申请单状态 */
    @ApiModelProperty("APPLY_STATUS")
    private String applyStatus;

    /** 建议供应商 */
    @ApiModelProperty("SUGGEST_SUPPLIER")
    private String suggestSupplier;

    /** 是否含税 */
    @ApiModelProperty("IS_INCLUDE_TAX")
    private String isIncludeTax;

    /** 下单方式 */
    @ApiModelProperty("ORDER_METHOD")
    private String orderMethod;

    /** 复核部门编码 */
    @ApiModelProperty("CHECK_DEPT_CODE")
    private String checkDeptCode;

    /** 复核部门名称 */
    @ApiModelProperty("CHECK_DEPT_NAME")
    private String checkDeptName;

    /** 复核人编码 */
    @ApiModelProperty("CHECK_PER_CODE")
    private String checkPerCode;

    /** 复核人姓名 */
    @ApiModelProperty("CHECK_PER_NAME")
    private String checkPerName;

    /** 复核日期 */
    @ApiModelProperty("CHECK_DATE")
    private Date checkDate;

    /** 复核数量 */
    @ApiModelProperty("OPR_APPLY_QTY3")
    private BigDecimal oprApplyQty3;

    /** 采购预计交期 */
    @ApiModelProperty("PUR_EXPECT_DELIVE_DATE")
    private Date purExpectDeliveDate;

    /** 复核说明 */
    @ApiModelProperty("CHECK_EXPLAIN")
    private String checkExplain;

    /** PMC审批人编号 */
    @ApiModelProperty("PMC_APPROVE_PER_CODE")
    private String pmcApprovePerCode;

    /** PMC审批人姓名 */
    @ApiModelProperty("PMC_APPROVE_PER_NAME")
    private String pmcApprovePerName;

    /** PMC审批日期 */
    @ApiModelProperty("PMC_APPROVE_DATE")
    private Date pmcApproveDate;

    /** PMC申请说明 */
    @ApiModelProperty("PMC_APPLY_EXPLAIN")
    private String pmcApplyExplain;

    /** 下单编号 */
    @ApiModelProperty("ORDER_ID")
    private String orderId;




   @ApiModelProperty("MAT_PROPERTITY")
   private String matPropertity;

   @ApiModelProperty("MAT_CATE")
   private String matCate;

   @ApiModelProperty("MAT_OPERATE_CATE")
   private String matOperateCate;

   @ApiModelProperty("MAT_CATE_CODE")
   private String matCateCode;

   @ApiModelProperty("MAT_OPERATE_CATE_CODE")
   private String matOperateCateCode;

   @ApiModelProperty("MAT_PRO_NAME")
   private String matProName;

  @ApiModelProperty("MAT_COLOR")
  private String matColor;





    private ProdPreMaterielResult prodPreMaterielResult;
    //private ProdMaterielResult prodMaterielResult;

}