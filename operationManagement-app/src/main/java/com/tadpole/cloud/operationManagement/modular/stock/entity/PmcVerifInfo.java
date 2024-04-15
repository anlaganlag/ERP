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
    * 审核记录信息
    * </p>
*
* @author cyt
* @since 2022-07-05
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_PMC_VERIF_INFO")
@ExcelIgnoreUnannotated
public class PmcVerifInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 审核记录信息id */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 采购申请单ID */
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

    /** k3业务单据号 */
    @TableField("BILL_NO")
    private String billNo;

    /** 申请日期--默认值：getdate */
    @TableField("APPLY_DATE")
    private Date applyDate;

    /** 上次下单时间,最近一次下单时间 */
    @TableField("ORDER_LAST_TIME")
    private Date orderLastTime;

    /** 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货  */
    @TableField("BILL_TYPE")
    private String billType;

    /** 物料编码--取值来源：每日备货推荐.物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 运营大类--取值来源：每日备货推荐.运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 产品名称--取值来源：每日备货推荐.产品名称 */
    @TableField("PRODUCT_NAME")
    private String productName;

    /** 型号--取值来源：每日备货推荐.型号 */
    @TableField("MODEL")
    private String model;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 事业部--取值来源：每日备货推荐.事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team--取值来源：每日备货推荐.Team */
    @TableField("TEAM")
    private String team;

    /** MOQ--取值来源：每日备货推荐.MOQ */
    @TableField("MINPOQTY")
    private Long minpoqty;

    /** MOQ:采购起订量备注 */
    @TableField("MINPOQTY_NOTES")
    private String minpoqtyNotes;

 /** 拼单起订量 */
 @TableField("SPELL_ORDERNUM")
 private Long spellOrdernum;


 /** 拼单起订量备注 */
 @TableField("SPELL_ORDERNUM_REMARK ")
 private String spellOrdernumRemark;

    /** 二级类目--取值来源：每日备货推荐.二级类目 */
    @TableField("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 运营期望交期 */
    @TableField("OPER_EXPECTED_DATE")
    private Date operExpectedDate;

    /** 采购人员 */
    @TableField("PURCHASE_PERSON")
    private String purchasePerson;

    /** 采购人员ID */
    @TableField("PURCHASE_PERSON_ID")
    private String purchasePersonId;

    /** 建议供应商 */
    @TableField("ADVICE_SUPPLIER")
    private String adviceSupplier;

    /** 建议供应商ID */
    @TableField("ADVICE_SUPPLIER_ID")
    private String adviceSupplierId;

    /** 审核结果：0：待审核，1：通过， 2未通过 */
    @TableField("VERIF_RESULT")
    private String verifResult;

    /** 审核说明*/
    @TableField("VERIF_REASON")
    private String verifReason;

    /** 备注，计划的审批说明 */
    @TableField("REMARK")
    private String remark;

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

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 申请人ID */
    @TableField("APPLY_PERSON_ID")
    private String applyPersonId;

    /** 申请人 */
    @TableField("APPLY_PERSON")
    private String applyPerson;

    /** 物料属性合集 */
    @TableField("MATERIAL_PROPERTIES")
    private String materialProperties;

    /** 规格型号 */
    @TableField("MAT_MODE_SPEC")
    private String matModeSpec;

    /** 是否含税:1含税，0不含税 */
    @TableField("INCLUDE_TAX")
    private Integer includeTax;

    /** 含税的税率：含税时的税率 */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /** 下单方式 */
    @TableField("CREATE_ORDER_TYPE")
    private String createOrderType;


    /** 物控专员 */
    @TableField("MAT_CONTROL_PERSON")
    private String matControlPerson;



}