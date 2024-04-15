package com.tadpole.cloud.operationManagement.modular.stock.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * PMC审核调用k3记录信息
    * </p>
*
* @author lsy
* @since 2022-09-07
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_PMC_VERIF_K3")
@ExcelIgnoreUnannotated
public class StockPmcVerifK3 implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 审核记录信息id */
    @TableField("ID")
    private String id;

    /** 采购申请单ID,多个ID使用‘,’分割 */
    @TableField("PURCHASE_ORDER_ID_LIST")
    private String purchaseOrderIdList;

    /** 数量--合并汇总数量 */
    @TableField("QTY")
    private BigDecimal qty;

    /** 值域{"自动"/"人工"} */
    @TableField("VERIF_TYPE")
    private String verifType;

    /** 审批人员工编号 */
    @TableField("VERIF_PERSON_NO")
    private String verifPersonNo;

    /** 审批人姓名 */
    @TableField("VERIF_PERSON_NAME")
    private String verifPersonName;

    /** PMC审批日期 */
    @TableField("VERIF_DATE")
    private Date verifDate;

    /** k3业务单据号，同STOCK_PMC_VERIF_INFO表的billNo一致，一对多关系 */
    @TableField("BILL_NO")
    private String billNo;

    /** 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货，如有不同类型的则为混合类型 */
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

    /** 运营期望交期，汇总取最小值 */
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

    /** 审核说明,事业部审核会填写 */
    @TableField("VERIF_REASON")
    private String verifReason;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(-1:待同步，0 ：同步失败,1：同步成功) */
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

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 是否含税:1含税，0不含税 */
    @TableField("INCLUDE_TAX")
    private BigDecimal includeTax;

    /** 含税的税率：含税时的税率 */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /** 下单方式：急单，正常单，... ... */
    @TableField("CREATE_ORDER_TYPE")
    private String createOrderType;

    /** 上次下单时间,最近一次下单时间 */
    @TableField("ORDER_LAST_TIME")
    private Date orderLastTime;


    /** 物控专员 */
    @TableField("MAT_CONTROL_PERSON")
    private String matControlPerson;

}