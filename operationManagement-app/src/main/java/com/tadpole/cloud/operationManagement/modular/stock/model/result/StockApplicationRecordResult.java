package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 备货申请记录
 * </p>
 *
 * @author ly
 * @since 2022-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("STOCK_PURCHASE_ORDERS")
public class StockApplicationRecordResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 申请单编号 */
    @ExcelProperty("申请单编号")
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 申请单类型 */
    @ExcelProperty("申请单类型")
    @ApiModelProperty("BILL_TYPE_Name")
    private String billTypeName;

    /** 事业部审核人 */
    @ExcelProperty("事业部审核人")
    @ApiModelProperty("VERIF_PERSON_NAME")
    private String verifPersonName;

    /** 事业部审核时间 */
    @ExcelProperty("VERIF_DATE_DEPT")
    @ApiModelProperty("VERIF_DATE_DEPT")
    private Date verifDateDept;

    /** 平台 */
    @ExcelProperty("平台")
    @ApiModelProperty("PLATFORM")
    private String platform;

    /*** 事业部 */
    @ExcelProperty("事业部")
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** TEAM */
    @ExcelProperty("TEAM")
    @ApiModelProperty("TEAM")
    private String team;

    /** 物料编码 */
    @ExcelProperty("物料编码")
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** 运营大类 */
    @ExcelProperty("运营大类")
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 产品名称 */
    @ExcelProperty("产品名称")
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 适用品牌 */
    @ExcelProperty("适用品牌")
    @ApiModelProperty("BRAND")
    private String brand;

    /** 型号 */
    @ExcelProperty("型号")
    @ApiModelProperty("MODEL")
    private String model;

    /** 款式 */
    @ExcelProperty("款式")
    @ApiModelProperty("STYLE")
    private String style;



    @ExcelProperty("颜色")
    @ApiModelProperty("color")
    private String color;



    /** 公司品牌 */
    @ExcelProperty("公司品牌")
    @ApiModelProperty("COMPANY_BRAND")
    private String companyBrand;



    /** 二级类目 */
    @ExcelProperty("二级类目")
    @ApiModelProperty("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 拼单起订量 */
    @TableField("SPELL_ORDERNUM")
    @ExcelProperty(value = "采购起订量")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @TableField("SPELL_ORDERNUM_REMARK ")
    @ExcelProperty(value = "拼单起订量备注")
    private String spellOrdernumRemark;


    /** 采购起订量 */
    @ExcelProperty("采购起订量")
    @ApiModelProperty("MINPOQTY")
    private Long minpoqty;

    /** 运营申请数量 */
    @ExcelProperty("运营申请数量")
    @ApiModelProperty("PURCHASE_APPLY_QTY")
    private BigDecimal purchaseApplyQty;

    /*** 申请备货后周转天数 */
    @ExcelProperty("申请备货后周转天数")
    @ApiModelProperty("TURNOVER_DAYS")
    private String turnoverDays;

    /** 运营期望交期 */
    @ExcelProperty("运营期望交期")
    @ApiModelProperty("OPER_EXPECTED_DATE")
    private Date operExpectedDate;

    /** 事业部审核说明 */
    @ExcelProperty("VERIF_REASON_DEPT")
    @ApiModelProperty("sVERIF_REASON")
    private String verifReasonDept;

    /** 计划部审批数量 */
    @ExcelProperty("计划部审批数量")
    @ApiModelProperty("QTY")
    private int qty;

    /** 计划部审批说明 */
    @ExcelProperty("VERIF_REASON_PLAN")
    @ApiModelProperty("VERIF_REASON_PLAN")
    private String verifReasonPlan;

    /** 计划部审批时间 */
    @ExcelProperty("VERIF_DATE_PLAN")
    @ApiModelProperty("VERIF_DATE_PLAN")
    private Date verifDatePlan;

    /** PMC审批时间 */
    @ExcelProperty("VERIF_DATE_PCM")
    @ApiModelProperty("VERIF_DATE_PCM")
    private Date verifDatePcm;

    /** PMC审批说明 */
    @ExcelProperty("VERIF_REASON_PCM")
    @ApiModelProperty("VERIF_REASON_PCM")
    private String verifReasonPCM;

    /** 下单方式：急单，正常单，... ... */
    @ExcelProperty(value = "createOrderType")
    @ApiModelProperty("CREATE_ORDER_TYPE")
    private String createOrderType;

    /**
     * 申请单状态
     * 采购订单状态:值域{"待审核"/"不备货"/"待计划部审批"/"计划未通过"/"待PMC审批"/"PMC未通过"/"已通过"}默认值：待审核
     */
    @ExcelIgnore
    @ApiModelProperty("ORDER_STATUS")
    private int orderStatus;

    @ExcelProperty("ORDER_STATUS_NAME")
    @ApiModelProperty("申请单状态{0:待审核；1:不备货；2:待计划部审批；3:计划未通过；4:待PMC审批；5:PMC未通过；6:已通过}默认值：待审核")
    private String orderStatusName;

    /** 推荐日期 */
    @ExcelIgnore
    @ApiModelProperty("BIZDATE")
    private Date bizdate;

    /** 组合属性(款式	主材料	图案	公司品牌	适用品牌或对象	型号	颜色	尺码	包装数量	版本描述	适用机型	二级标签	MOQ) */
    @ExcelProperty("组合属性")
    @ApiModelProperty("PROPERTY_MERGE")
    private String propertyMerge;

    /** 物控专员 */
    @ExcelProperty("物控专员")
    @ApiModelProperty("物控专员")
    private String matControlPerson;

}
