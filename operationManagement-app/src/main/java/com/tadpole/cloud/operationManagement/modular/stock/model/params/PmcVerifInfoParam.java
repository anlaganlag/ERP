package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelIgnore;
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
import java.util.List;

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
public class PmcVerifInfoParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 审核记录信息id */
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 采购申请单ID */
    //@Excel(name = "单据编号")
    @ExcelProperty("单据编号")//导入Excel 注解
    @ApiModelProperty("采购申请单ID")
    private String purchaseOrderId;

    /** 数量---事业部审核：采购申请数量/计划部审核：审批数量 */
    //@Excel(name = "申请数量")
    @ExcelProperty("申请数量")
    @ApiModelProperty("数量")
    private BigDecimal qty;

    /** 审核业务类型：事业部审核10，计划部审核20，PMC审核30  */
    @ApiModelProperty("审核业务类型：事业部审核10，计划部审核20，PMC审核30")
    private int verifBizType;

    /** 值域{"自动"/"人工"} */
    @ApiModelProperty("值域{\"自动\"/\"人工\"}")
    private String verifType;

    /** 审批人员工编号 */
    @ApiModelProperty("审批人员工编号")
    private String verifPersonNo;

    /** 审批人姓名 */
    @ApiModelProperty("审批人姓名")
    private String verifPersonName;

    /** 部审批日期 */
    @ApiModelProperty("部审批日期")
    //@DateTimeFormat(pattern="YYYY-MM-DD")
    //@JSONField(format="YYYY-MM-DD")
    private Date verifDate;

    /** k3业务单据号 */
    @ApiModelProperty("k3业务单据号")
    private String billNo;

    /** 申请日期--默认值：getdate */
    //@Excel(name = "申请日期")
    @ExcelProperty("申请日期")
    @ApiModelProperty("申请日期")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//输入是 Date 限制
    private Date applyDate;

    /** 上次下单时间,最近一次下单时间 */
    //@Excel(name = "上次下单时间")
    @ExcelProperty("上次下单时间")
    @ApiModelProperty("上次下单时间,最近一次下单时间")
    private Date orderLastTime;

    /** 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货  */
    @ApiModelProperty("备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货")
    //@Excel(name = "备货类型")
    @ExcelProperty("备货类型")
    private String billType;

    /** 物料编码--取值来源：每日备货推荐.物料编码 */
    @ApiModelProperty("物料编码--取值来源：每日备货推荐.物料编码")
    //@Excel(name = "物料编码")
    @ExcelProperty("物料编码")
    private String materialCode;


    /** 物料编码--取值来源：每日备货推荐.物料编码 */
    @ApiModelProperty("物料编码List--前端查询传入多个物料编码")
    //@Excel(name = "物料编码")
//    @ExcelProperty("物料编码List")
    private List<String> materialCodeList;

    /** 运营大类--取值来源：每日备货推荐.运营大类 */
    //@Excel(name = "运营大类")
    @ExcelProperty("运营大类")
    @ApiModelProperty("运营大类--取值来源：每日备货推荐.运营大类")
    private String productType;

    /** 产品名称--取值来源：每日备货推荐.产品名称 */
    //@Excel(name = "物料名称")
    @ExcelProperty("物料名称")
    @ApiModelProperty("产品名称--取值来源：每日备货推荐.产品名称")
    private String productName;

    /** 型号--取值来源：每日备货推荐.型号 */
    //@Excel(name = "规格型号")
    @ExcelProperty("规格型号")
    @ApiModelProperty("型号--取值来源：每日备货推荐.型号")
    private String model;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 事业部--取值来源：每日备货推荐.事业部 */
    //@Excel(name = "事业部")
    @ExcelProperty("事业部")
    @ApiModelProperty("事业部--取值来源：每日备货推荐.事业部")
    private String department;

    /** Team--取值来源：每日备货推荐.Team */
    //@Excel(name = "Team")
    @ExcelProperty("Team")
    @ApiModelProperty("Team--取值来源：每日备货推荐.Team")
    private String team;

    /** MOQ--取值来源：每日备货推荐.MOQ */
    //@Excel(name = "MOQ")
    @ExcelProperty("MOQ")
    @ApiModelProperty("MOQ--取值来源：每日备货推荐.MOQ")
    private Long minpoqty;

    /** MOQ:采购起订量备注 */
    //@Excel(name = "MOQ备注")
    @ExcelProperty("MOQ备注")
    @ApiModelProperty("MOQ:采购起订量备注")
    private String minpoqtyNotes;

    /** 拼单起订量 */
    @ApiModelProperty("SPELL_ORDERNUM")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
    private String spellOrdernumRemark;

    /** 二级类目--取值来源：每日备货推荐.二级类目 */
    //@Excel(name = "款式二级标签")
    @ExcelProperty("款式二级标签")
    @ApiModelProperty("二级类目--取值来源：每日备货推荐.二级类目")
    private String matstylesecondlabel;

    /** 运营期望交期 */
    //@Excel(name = "期望交期")
    @ExcelProperty("期望交期")
    @ApiModelProperty("运营期望交期")
    private Date operExpectedDate;

    /** 采购人员 */
    //@Excel(name = "采购员姓名")
    @ExcelProperty("采购员姓名")
    @ApiModelProperty("采购人员")
    private String purchasePerson;

    /** 采购人员ID */
    //@Excel(name = "采购员工号")
    @ExcelProperty("采购员工号")
    @ApiModelProperty("采购人员ID")
    private String purchasePersonId;

    /** 建议供应商 */
    //@Excel(name = "建议供应商")
    @ExcelProperty("建议供应商")
    @ApiModelProperty("建议供应商")
    private String adviceSupplier;

    /** 建议供应商ID */
    @ApiModelProperty("建议供应商ID")
    private String adviceSupplierId;

    /** 审核结果：0：未通过，1：通过 ，2驳回*/
    @ApiModelProperty("审核结果：0：未通过，1：通过 ，2驳回")
    private String verifResult;

    /** 审核说明,事业部审核会填写 */
    //@Excel(name = "PMC备注")
    @ExcelProperty("PMC备注")
    @ApiModelProperty("审核说明")
    private String verifReason;

    /** 备注 */
    //@Excel(name = "计划部备注")
    @ExcelProperty("计划部备注")
    @ApiModelProperty("P备注")
    private String remark;

    /** 同步时间 */
    @ApiModelProperty("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ApiModelProperty("SYNC_STATUS")
    private String syncStatus;

    /** 同步结果 */
    @ApiModelProperty("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 同步请求参数 */
    @ApiModelProperty("SYNC_REQUEST_MSG")
    private String syncRequestMsg;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 申请人ID */
    @ApiModelProperty("APPLY_PERSON_ID")
    private String applyPersonId;

    /** 申请人 */
    @ApiModelProperty("APPLY_PERSON")
    private String applyPerson;

    /** 物料属性合集 */
    @ApiModelProperty("MATERIAL_PROPERTIES")
    private String materialProperties;

    /** 规格型号 */
    @ApiModelProperty("MAT_MODE_SPEC")
    private String matModeSpec;

    @ApiModelProperty("款式二级标签")
    private String styleSecondLabel;

    /** 是否含税:1含税，0不含税 */
    @ApiModelProperty("是否含税:1含税，0不含税")
    private Integer includeTax;

    /** 含税的税率：含税时的税率 */
    @ApiModelProperty("含税的税率：含税时的税率 ")
    private BigDecimal taxRate;

    /** 合并记录条数 */
    @ApiModelProperty("合并记录条数")
    private Integer mergeCount;

    /** 申请数量小于MOQ */
    @ApiModelProperty("申请数量小于MOQ")
    private Boolean appQtyLessMoqQty;

    /** 申请数量大于等于MOQ */
    @ApiModelProperty("申请数量大于等于MOQ")
    private Boolean appQtyMoreEqMoqQty;

    /** 分组字段合并 */
    @ExcelIgnore
    @ApiModelProperty("分组字段合并GROUP_FIELD")
    private String  mergeGroupField;

    /** 下单方式 */
    @ApiModelProperty("下单方式")
    private String createOrderType;


    /** 申请日期-开始 */
    @ExcelIgnore
    @ApiModelProperty("PMC审批记录创建日期-开始")
    private Date createTimeStart;

    /** 申请日期-结束 */
    @ExcelIgnore
    @ApiModelProperty("PMC审批记录创建日期-结束")
    private Date createTimeEnd;


    /** 物控专员 */
    @ApiModelProperty("物控专员")
    private String matControlPerson;

}