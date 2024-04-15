package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
public class PmcVerifInfoResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 审核记录信息id */
    @ExcelIgnore
    @ApiModelProperty("审核记录信息id")
    private String id;


    /** 采购申请单ID */
    @ExcelProperty("单据编号")//导出Excel 注解
    @ApiModelProperty("采购申请单ID")
    private String purchaseOrderId;


    /** 采购申请单ID */
    @ExcelProperty("采购申请单号")//导出Excel 注解
    @ApiModelProperty("采购申请单号")
    private String billNo;

    /** 申请日期--默认值：getdate */
    @ExcelProperty("申请日期")
    @ApiModelProperty("申请日期")
    @DateTimeFormat("yyyy-MM-dd")
    //@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date applyDate;

    /** 上次下单时间,最近一次下单时间 */
    @ExcelProperty("上次下单时间")
    @ApiModelProperty("上次下单时间,最近一次下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date orderLastTime;

    /** 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货  */
    @ExcelProperty("备货类型")
    @ApiModelProperty("备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货")
    private String billType;

    /**
     * 备货类型名称--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货
     */
    @ApiModelProperty("备货类型名称")
    private String billTypeName;


    /** 物料编码--取值来源：每日备货推荐.物料编码 */
    @ExcelProperty("物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 运营大类--取值来源：每日备货推荐.运营大类 */
    @ExcelProperty("运营大类")
    @ApiModelProperty("运营大类")
    private String productType;

    /** 产品名称--取值来源：每日备货推荐.产品名称 */
    @ExcelProperty("物料名称")
    @ApiModelProperty("产品名称-")
    private String productName;

    /** 物料属性合集 */
    @ExcelIgnore
    @ApiModelProperty("物料属性合集")
    private String materialProperties;

    /** 规格型号 */
    @ExcelProperty("规格型号")
    @ApiModelProperty("规格型号")
    private String matModeSpec;

    /** 型号--取值来源：每日备货推荐.型号 */
    @ExcelIgnore
    @ApiModelProperty("型号")
    private String model;

    /** 二级类目--取值来源：每日备货推荐.二级类目 */
    @ExcelProperty("款式二级标签")
    @ApiModelProperty("二级类目")
    private String matstylesecondlabel;

    /** 平台 */
    @ExcelProperty("平台")
    @ApiModelProperty("平台")
    private String platform;

    /** 事业部--取值来源：每日备货推荐.事业部 */
    @ExcelProperty("事业部")
    @ApiModelProperty("事业部")
    private String department;

    /** Team--取值来源：每日备货推荐.Team */
    @ExcelProperty("Team")
    @ApiModelProperty("Team")
    private String team;

    /** 申请人 */
    @ExcelProperty("申请人")
    @ApiModelProperty("申请人")
    private String applyPerson;


    /** 数量---事业部审核：采购申请数量/计划部审核：审批数量 */
    @ExcelProperty("申请数量")
    @ApiModelProperty("数量")
    private BigDecimal qty;

    /** MOQ--取值来源：每日备货推荐.MOQ */
    @ExcelProperty("MOQ")
    @ApiModelProperty("MOQ")
    private Long minpoqty;

    /** MOQ:采购起订量备注 */
    @ExcelProperty("MOQ备注")
    @ApiModelProperty("MOQ:采购起订量备注")
    private String minpoqtyNotes;

    /** 拼单起订量 */
    @ApiModelProperty("SPELL_ORDERNUM")
    @ExcelProperty("拼单起订量")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
    @ExcelProperty("拼单起订量备注")
    private String spellOrdernumRemark;



    /** 备注 */
    @ExcelProperty("计划部备注")
    @ApiModelProperty("备注，计划部审核备注信息")
    private String remark;

    /** 备注 */
    @ExcelProperty("计划部备注合并显示")
    @ApiModelProperty("计划部备注-多条明细合并显示")
    private String remarkMerge;

    /** 运营期望交期 */
    @ExcelProperty("期望交期")
    @ApiModelProperty("运营期望交期")
    @DateTimeFormat("yyyy-MM-dd")
    //@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date operExpectedDate;

    /** 采购人员 */
    @ExcelProperty("采购员姓名")
    @ApiModelProperty("采购人员")
    private String purchasePerson;

    /** 采购人员ID */
    @ExcelProperty("采购员工号")
    @ApiModelProperty("采购人员ID")
    private String purchasePersonId;

    /** 建议供应商 */
    @ExcelProperty("建议供应商")
    @ApiModelProperty("建议供应商")
    private String adviceSupplier;

    /** 建议供应商ID */
    @ExcelIgnore
    @ApiModelProperty("建议供应商ID")
    private String adviceSupplierId;

    /** 审核说明,事业部审核会填写 */
    @ExcelProperty("PMC备注")
    @ApiModelProperty("审核说明")
    private String verifReason;

    /** 审核业务类型：事业部审核10，计划部审核20，PMC审核30  */
    @ExcelIgnore
    @ApiModelProperty("审核业务类型：事业部审核10，计划部审核20，PMC审核30")
    private int verifBizType;

    /** 值域{"自动"/"人工"} */
    @ExcelIgnore
    @ApiModelProperty("自动\"/\"人工")
    private String verifType;

    /** 审批人员工编号 */
    @ExcelIgnore
    @ApiModelProperty("审批人员工编号")
    private String verifPersonNo;

    /** 审批人姓名 */
    @ExcelIgnore
    @ApiModelProperty("审批人姓名")
    private String verifPersonName;

    /** 部审批日期 */
    @ExcelIgnore
    @ApiModelProperty("部审批日期")
    @JSONField(format="YYYY-MM-DD")
    private Date verifDate;


    /** 审核结果：0：未通过，1：通过,2驳回 */
    @ExcelIgnore
    @ApiModelProperty("审核结果：0：未通过，1：通过,2驳回")
    private String verifResult;

    /** 同步时间 */
    @ExcelIgnore
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ExcelIgnore
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步结果 */
    @ExcelIgnore
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 同步请求参数 */
    @ExcelIgnore
    @ApiModelProperty("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

    /** 创建时间 */
    @ExcelIgnore
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ExcelIgnore
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 申请人ID */
    @ExcelIgnore
    @ApiModelProperty("申请人ID")
    private String applyPersonId;

    /** 是否含税:1含税，0不含税 */
    @ApiModelProperty("INCLUDE_TAX")
    @ExcelProperty("是否含税:1含税，0不含税")
    private Integer includeTax;

    @ExcelIgnore
    @ApiModelProperty("includeTax->1-HS,0-BHS")
    private String includeTaxName;

    /** 含税的税率：含税时的税率 */
    @ApiModelProperty("TAX_RATE")
    @ExcelProperty("税率")
    private BigDecimal taxRate;

    /** 合并记录条数 */
    @ExcelIgnore
    @ApiModelProperty("合并记录条数")
    @TableField("mergeCount")
    private Integer mergeCount;

    /** 分组字段合并 */
    @ExcelIgnore
    @ApiModelProperty("分组字段合并GROUP_FIELD")
    private String  mergeGroupField;

    /** 下单方式 */
    @TableField("下单方式")
    private String createOrderType;


    /** 事业部申请日期  */
    @ExcelIgnore
    @ApiModelProperty("事业部申请日期--日常备货：事业部审核通过日期，特殊备货：V3.0特殊备货提交日期")
    private Date deptApplyDate;

    /** 运营审核人  */
    @ExcelIgnore
    @ApiModelProperty("运营审核人--日常备货：事业部审核通过操作人，特殊备货：V3.0特殊备货提交申请的人")
    private String operVerifPerson;

    /** 运营审核说明  */
    @ExcelIgnore
    @ApiModelProperty("运营审核说明--日常备货：事业部审核通过审批原因，特殊备货：V3.0特殊备货提交申请的原因")
    private String operVerifReason;

    @ExcelIgnore
    @ApiModelProperty("包含紧急备货的待审批的记录条数")
    private Integer countJjbh;


    /** 物控专员  */
    @ApiModelProperty("物控专员")
    private String matControlPerson;



}