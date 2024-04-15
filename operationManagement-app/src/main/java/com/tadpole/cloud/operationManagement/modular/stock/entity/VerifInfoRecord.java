package com.tadpole.cloud.operationManagement.modular.stock.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * 审核记录信息
 * </p>
 *
 * @author lsy
 * @since 2022-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_VERIF_INFO_RECORD")
@ExcelIgnoreUnannotated
public class VerifInfoRecord implements Serializable {

 private static final long serialVersionUID = 1L;

 /** 审核记录信息id */
 @TableId(value = "ID", type = IdType.ASSIGN_ID)
 private String id;

 /** 采购申请单ID */
 @Excel(name = "单据编号")
 @ExcelProperty("单据编号")
 @TableField("PURCHASE_ORDER_ID")
 private String purchaseOrderId;

 /** 数量---事业部审核：采购申请数量/计划部审核：审批数量 */
 @TableField("QTY")
 private BigDecimal qty;

 /** 审核业务类型：事业部审核10，计划部审核20，PMC审核30  */
 @TableField("VERIF_BIZ_TYPE")
 private int verifBizType;

 /** 值域{"自动"/"人工"} */
 @TableField("VERIF_TYPE")
 private String verifType;

 /** 审批人员工编号 */
 @TableField("VERIF_PERSON_NO")
 private String verifPersonNo;

 /** 审批人姓名 */
 @TableField("VERIF_PERSON_NAME")
 private String verifPersonName;

 /** 部审批日期 */
 @TableField("VERIF_DATE")
 private Date verifDate;

 /** 审核结果：0：未通过，1：通过*/
 @TableField("VERIF_RESULT")
 private String verifResult;

 /** 审核说明,事业部审核会填写 */
 @TableField("VERIF_REASON")
 private String verifReason;

 /** 备注 */
 @Excel(name = "PMC备注")
 @ExcelProperty("PMC备注")
 @TableField("REMARK")
 private String remark;

 /** 采购人员 */
 @Excel(name = "采购员姓名")
 @ExcelProperty("采购员姓名")
 @TableField("PURCHASE_PERSON")
 private String purchasePerson;

 /** 采购人员ID */
 @Excel(name = "采购员工号")
 @ExcelProperty("采购员工号")
 @TableField("PURCHASE_PERSON_ID")
 private String purchasePersonId;

 /** 建议供应商 */
 @Excel(name = "建议供应商")
 @ExcelProperty("建议供应商")
 @TableField("ADVICE_SUPPLIER")
 private String adviceSupplier;

 /** 建议供应商ID */
 @TableField("ADVICE_SUPPLIER_ID")
 private String adviceSupplierId;

 /** 创建时间 */
 @TableField("CREATE_TIME")
 private Date createTime;

 /** 更新时间 */
 @TableField("UPDATE_TIME")
 private Date updateTime;

 /** k3业务单据编号 */
 @TableField("BILL_NO")
 private String billNo;

 //同步时间
 @TableField("SYNC_TIME")
 private Date syncTime;

 //同步状态(0 ：同步失败,1：同步成功)
 @TableField("SYNC_STATUS")
 private String syncStatus;

 //同步请求消息
 @TableField("SYNC_REQUEST_MSG")
 private String syncRequestMsg;

 //同步请求结果
 @TableField("SYNC_RESULT_MSG")
 private String syncResultMsg;

 /** 采购起订量 */
 @TableField("MINPOQTY")
 private Long minpoqty;

 /** 拼单起订量 */
 @TableField("SPELL_ORDERNUM")
 private Long spellOrdernum;



}