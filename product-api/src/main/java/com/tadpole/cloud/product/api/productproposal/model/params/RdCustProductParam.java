package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
 * <p>
 * 提案-定品 入参类
 * </p>
 *
 * @author S20190096
 * @since 2024-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_CUST_PRODUCT")
public class RdCustProductParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-定品申请编号 */
   @TableId(value = "SYS_CP_CODE", type = IdType.AUTO)
    private String sysCpCode;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;

    /** 系统信息-部门编号 */
    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;

    /** 系统信息-部门名称 */
    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;

    /** 系统信息-员工编号 */
    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;

    /** 系统信息-申请状态 值域{"待提交","待审批","已审批"} */
    @ApiModelProperty("系统信息-申请状态 值域{'待提交','待审批','已审批'}")
    private String sysCpStatus;

    /** 单据联系-提案编号 */
    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;

    /** 申请信息-定品申请名称 */
    @ApiModelProperty("申请信息-定品申请名称")
    private String sysCpName;

    /** 申请信息-提案已用时 */
    @ApiModelProperty("申请信息-提案已用时")
    private BigDecimal sysCpPuTimes;

    /** 申请信息-购样任务次数 */
    @ApiModelProperty("申请信息-购样任务次数")
    private BigDecimal sysCpStTimes;

    /** 申请信息-定制任务次数 */
    @ApiModelProperty("申请信息-定制任务次数")
    private BigDecimal sysCpCtTimes;

    /** 申请信息-任务合计次数 */
    @ApiModelProperty("申请信息-任务合计次数")
    private BigDecimal sysCpTTotalTimes;

    /** 申请信息-购样费用合计 */
    @ApiModelProperty("申请信息-购样费用合计")
    private BigDecimal sysCpStFeeAmount;

    /** 申请信息-定制费用合计 */
    @ApiModelProperty("申请信息-定制费用合计")
    private BigDecimal sysCpCtFeeAmount;

    /** 申请信息-已投入研发费合计 */
    @ApiModelProperty("申请信息-已投入研发费合计")
    private BigDecimal sysCpTotalFeeAmount;

    /** 申请信息-购样数量 */
    @ApiModelProperty("申请信息-购样数量")
    private BigDecimal sysCpStQty;

    /** 申请信息-定制数量 */
    @ApiModelProperty("申请信息-定制数量")
    private BigDecimal sysCpCtQty;

    /** 申请信息-样品数量合计 */
    @ApiModelProperty("申请信息-样品数量合计")
    private BigDecimal sysCpSTotalQty;

    /** 申请信息-无效样品数量合计 */
    @ApiModelProperty("申请信息-无效样品数量合计")
    private BigDecimal sysCpIsQty;

    /** 申请信息-有效样品数量合计 */
    @ApiModelProperty("申请信息-有效样品数量合计")
    private BigDecimal sysCpVsQty;

    /** 申请信息-定品审批通过数量合计 */
    @ApiModelProperty("申请信息-定品审批通过数量合计")
    private BigDecimal sysCpApprQty;

    /** 申请信息-定品申请数量合计 */
    @ApiModelProperty("申请信息-定品申请数量合计")
    private BigDecimal sysCpAppQty;

    /** 申请信息-销售规划文档 */
    @ApiModelProperty("申请信息-销售规划文档")
    private String sysCpSpDoc;

    /** 申请信息-申请时间 */
    @ApiModelProperty("申请信息-申请时间")
    private Date sysCpAppDate;

    /** 申请信息-申请员工姓名 */
    @ApiModelProperty("申请信息-申请员工姓名")
    private String sysCpAppPerName;

    /** 申请信息-申请员工编号 */
    @ApiModelProperty("申请信息-申请员工编号")
    private String sysCpAppPerCode;

    /** 审批信息-审批结果 值域{"继续选品","同意落地","研发撤销"} */
    @ApiModelProperty("审批信息-审批结果 值域{'继续选品','同意落地','研发撤销'}")
    private String sysCpApprResult;

    /** 审批信息-审批说明 */
    @ApiModelProperty("审批信息-审批说明")
    private String sysCpApprExplain;

    /** 审批信息-审批时间 */
    @ApiModelProperty("审批信息-审批时间")
    private Date sysCpApprDate;

    /** 审批信息-审批员工姓名 */
    @ApiModelProperty("审批信息-审批员工姓名")
    private String sysCpApprPerName;

    /** 审批信息-审批员工编号 */
    @ApiModelProperty("审批信息-审批员工编号")
    private String sysCpApprPerCode;

    /** 审批信息-审批结果 值域{"继续选品","同意落地","研发撤销"} */
    @ApiModelProperty("审批结果")
    private String sysCpApprResult2;

    /** 审批信息-审批说明 */
    @ApiModelProperty("审批说明")
    private String sysCpApprExplain2;

    /** 审批信息-审批时间 */
    @ApiModelProperty("审批时间")
    private Date sysCpApprDate2;

    /** 审批信息-审批员工姓名 */
    @ApiModelProperty("审批员工姓名")
    private String sysCpApprPerName2;

    /** 审批信息-审批员工编号 */
    @ApiModelProperty("审批员工编号")
    private String sysCpApprPerCode2;

    @ApiModelProperty("设定信息-拿样方式 值域{'现货拿样','定制拿样'}")
    private String sysTsSampleMethod;

    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;

    @ApiModelProperty("单据联系-拿样任务编号集合")
    private List<String> sysTsTaskCodeList;

    /** 功能操作-保存/提交 */
    @ApiModelProperty("功能操作-保存/提交")
    private String sysFuncOpr;

    /** 界面操作-新增/编辑/审批 */
    @ApiModelProperty("界面操作-新增/编辑/审批")
    private String sysPageOpr;

    @ApiModelProperty("定品明细记录")
    private List<RdCustProductDetParam> rdCustProductDetParams;

    @ApiModelProperty("定品明细记录2")
    private List<RdCustProductDet2Param> rdCustProductDet2Params;
}
