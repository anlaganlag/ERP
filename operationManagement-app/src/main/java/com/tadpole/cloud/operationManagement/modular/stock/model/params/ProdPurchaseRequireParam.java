package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class ProdPurchaseRequireParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 采购申请编号 */
   @TableId(value = "ID", type = IdType.AUTO)
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
    @NotNull
    @ApiModelProperty("OPR_EXPECT_DELIVE_DATE")
    private Date oprExpectDeliveDate;

    /** 申请单状态 */
    @ApiModelProperty("\"待提交\"/\"待采购复核\"/\"待PMC审批\"/\"PMC未通过\"/\"PMC已通过\"/\"已归档")
    private String applyStatus;

    /** 建议供应商 */
    @NotEmpty
    @ApiModelProperty("SUGGEST_SUPPLIER")
    private String suggestSupplier;

    /** 是否含税 */
    @NotEmpty
    @ApiModelProperty("IS_INCLUDE_TAX")
    private String isIncludeTax;

    /** 下单方式 */

    @NotEmpty
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
    @DecimalMin(value="1.00",message="复核数量最小值为1")
    @ApiModelProperty("OPR_APPLY_QTY3")
    private BigDecimal oprApplyQty3;

    /** 采购预计交期 */
    @NotNull
    @ApiModelProperty("PUR_EXPECT_DELIVE_DATE")
    private Date purExpectDeliveDate;

    /** 复核说明 */
    @NotEmpty
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


 /** 提案编号Propose ID */
 @ApiModelProperty("PROPOSE_ID")
 private String proposeId;

 /** 物料运营负责人编号 */
 @ApiModelProperty("MAT_OPR_PER_CODE")
 private String matOprPerCode;


 /** 采购申请编号List */
 @ApiModelProperty("批量编辑时，接收数据id集合")
 private List<String> idList;

 /** 提交类型（0：归档或不通过，1：通过） */
 @ApiModelProperty("提交类型（0：归档或不通过，1：通过）")
 private Integer comitType;

  /** 建议供应商ID */
  @ApiModelProperty("建议供应商ID")
  private String suggestSupplierId;

  /** 同步时间 */
  @ApiModelProperty("同步时间")
  private Date syncTime;

  /** 同步状态(0 ：同步失败,1：同步成功,-1待同步) */
  @ApiModelProperty("同步状态")
  private String syncStatus;

  /** 同步结果 */
  @ApiModelProperty("同步结果")
  private String syncResultMsg;

  /** 同步请求参数 */
  @ApiModelProperty("同步请求参数")
  private String syncRequestMsg;

 /** 物料编码集合 */
 @ApiModelProperty("物料编码集合")
 private List<String> matCodeList;

  /** 物料属性 值域{"自制","外购"}；默认"外购" */
  @ApiModelProperty("物料属性 值域{\"自制\",\"外购\"}；默认\"外购")
  private String matPropertity;

   /** 运营大类 */
   @ApiModelProperty("运营大类")
   private String matOperateCate;

   /** 运营大类集合 */
   @ApiModelProperty("运营大类集合")
   private List<String> matOperateCateList;


   /** 前端采购人 */
   @ApiModelProperty("前端采购人")
   private String frontPurName;

   /** 前端采购人编号 */
   @ApiModelProperty("前端采购人编号")
   private String frontPurCode;

 /** 事业部 */
 @ApiModelProperty("DEPARTMENT")
 private List<String> departmentList;

 /** Team组 */
 @ApiModelProperty("TEAM")
 private List<String> teamList;
}