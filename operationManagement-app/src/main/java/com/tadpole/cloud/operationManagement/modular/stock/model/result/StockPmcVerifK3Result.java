package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("STOCK_PMC_VERIF_K3")
public class StockPmcVerifK3Result implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 审核记录信息id */
    @ApiModelProperty("ID")
    private String id;

    /** 平台 */
    @ApiModelProperty("PLATFORM")
    @ExcelProperty("平台")
    private String platform;


    /** 事业部--取值来源：每日备货推荐.事业部 */
    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty("事业部")
    private String department;

    /** Team--取值来源：每日备货推荐.Team */
    @ApiModelProperty("TEAM")
    @ExcelProperty("Team")
    private String team;


    /** 采购申请单ID,多个ID使用‘,’分割 */
    @ApiModelProperty("PURCHASE_ORDER_ID_LIST")
    private String purchaseOrderIdList;

    /** 数量--合并汇总数量 */
    @ApiModelProperty("QTY")
    @ExcelProperty("申请数量")
    private BigDecimal qty;

    /** 值域{"自动"/"人工"} */
    @ApiModelProperty("VERIF_TYPE")
    private String verifType;

    /** 审批人员工编号 */
    @ApiModelProperty("VERIF_PERSON_NO")
    @ExcelProperty("审批人员工编号")
    private String verifPersonNo;

    /** 审批人姓名 */
    @ExcelProperty("审批人姓名")
    @ApiModelProperty("VERIF_PERSON_NAME")
    private String verifPersonName;

    /** PMC审批日期 */
    @ExcelProperty("PMC审批日期")
    @ApiModelProperty("VERIF_DATE")
    private Date verifDate;

    /** k3业务单据号，同STOCK_PMC_VERIF_INFO表的billNo一致，一对多关系 */
    @ExcelProperty("k3业务单据号")
    @ApiModelProperty("BILL_NO")
    private String billNo;

    /** 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货，如有不同类型的则为混合类型 */
//    @ExcelProperty("备货类型")
    @ApiModelProperty("BILL_TYPE")
    private String billType;

    /** 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货，如有不同类型的则为HHBH混合备货类型 */
    @ExcelProperty("备货类型")
    @ApiModelProperty("billTypeName")
    private String billTypeName;



    /** 运营期望交期，汇总取最小值 */
    @ApiModelProperty("OPER_EXPECTED_DATE")
    @ExcelProperty("运营期望交期")
    private Date operExpectedDate;


    /** 物料编码--取值来源：每日备货推荐.物料编码 */
    @ExcelProperty("物料编码")
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;


    /** 运营大类--取值来源：每日备货推荐.运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty("运营大类")
    private String productType;

    /** 产品名称--取值来源：每日备货推荐.产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    @ExcelProperty("产品名称")
    private String productName;

    /** 型号--取值来源：每日备货推荐.型号 */
    @ApiModelProperty("MODEL")
    @ExcelProperty("型号")
    private String model;


    /** MOQ--取值来源：每日备货推荐.MOQ */
    @ApiModelProperty("MINPOQTY")
    @ExcelProperty("MOQ")
    private Long minpoqty;

    /** MOQ:采购起订量备注 */
    @ApiModelProperty("MINPOQTY_NOTES")
    @ExcelProperty("MOQ采购起订量备注")
    private String minpoqtyNotes;

    /** 拼单起订量 */
    @ApiModelProperty("SPELL_ORDERNUM")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
    private String spellOrdernumRemark;

    /** 二级类目--取值来源：每日备货推荐.二级类目 */
    @ApiModelProperty("MATSTYLESECONDLABEL")
    @ExcelProperty("二级类目")
    private String matstylesecondlabel;

    /** 采购人员 */
    @ApiModelProperty("PURCHASE_PERSON")
    @ExcelProperty("采购人员")
    private String purchasePerson;

    /** 采购人员ID */
    @ApiModelProperty("PURCHASE_PERSON_ID")
    @ExcelProperty("采购人员ID")
    private String purchasePersonId;

    /** 建议供应商 */
    @ApiModelProperty("ADVICE_SUPPLIER")
    @ExcelProperty("建议供应商")
    private String adviceSupplier;

    /** 建议供应商ID */
    @ApiModelProperty("ADVICE_SUPPLIER_ID")
    @ExcelProperty("建议供应商编号")
    private String adviceSupplierId;

    /** 审核说明,事业部审核会填写 */
    @ApiModelProperty("VERIF_REASON")
    @ExcelProperty("PMC审核说明")
    private String verifReason;

    /** 备注 */
    @ApiModelProperty("REMARK")
    @ExcelProperty("计划部备注")
    private String remark;

    /** 同步时间 */
    @ApiModelProperty("SYNC_TIME")
    @ExcelProperty("同步K3时间")
    private Date syncTime;

    /** 同步状态(-1:待同步，0 ：同步失败,1：同步成功) */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步状态(-1:待同步，0 ：同步失败,1：同步成功) */
    @ApiModelProperty("SYNC_STATUS")
    @ExcelProperty("同步状态")
    private String syncStatusName;


    /** 同步结果 */
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 同步请求参数 */
    @ApiModelProperty("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 申请人ID */
    @ApiModelProperty("APPLY_PERSON_ID")
    @ExcelProperty("申请人ID")
    private String applyPersonId;

    /** 申请人 */
    @ApiModelProperty("APPLY_PERSON")
    @ExcelProperty("申请人")
    private String applyPerson;

    /** 物料属性合集 */
    @ApiModelProperty("MATERIAL_PROPERTIES")
//    @ExcelProperty("物料属性合集")
    private String materialProperties;

    /** 规格型号 */
    @ExcelProperty("规格型号")
    @ApiModelProperty("规格型号")
    private String matModeSpec;



    /** 是否含税:1含税，0不含税 */
    @ApiModelProperty("INCLUDE_TAX")
    private BigDecimal includeTax;


    /** 是否含税:1含税，0不含税 */
    @ApiModelProperty("INCLUDE_TAX")
    @ExcelProperty("是否含税")
    private String includeTaxName;



    /** 含税的税率：含税时的税率 */
    @ApiModelProperty("TAX_RATE")
    private BigDecimal taxRate;

    /** 下单方式：急单，正常单，... ... */
    @ApiModelProperty("CREATE_ORDER_TYPE")
    @ExcelProperty("下单方式")
    private String createOrderType;

    /** 上次下单时间,最近一次下单时间 */
    @ApiModelProperty("ORDER_LAST_TIME")
    @ExcelProperty("上次下单时间")
    private Date orderLastTime;


    /** 物控专员 */
    @ApiModelProperty("物控专员")
    @ExcelProperty("物控专员")
    private String matControlPerson;

}