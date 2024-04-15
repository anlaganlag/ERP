package com.tadpole.cloud.product.api.productproposal.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 提案-定品 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_CUST_PRODUCT")
public class RdCustProductResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "SYS_CP_CODE", type = IdType.AUTO)
    private String sysCpCode;


    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;


    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;


    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;


    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;


    @ApiModelProperty("系统信息-申请状态 值域{'待提交','待审批','已审批'}")
    private String sysCpStatus;


    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;


    @ApiModelProperty("申请信息-定品申请名称")
    private String sysCpName;


    @ApiModelProperty("申请信息-提案已用时")
    private BigDecimal sysCpPuTimes;


    @ApiModelProperty("申请信息-购样任务次数")
    private BigDecimal sysCpStTimes;


    @ApiModelProperty("申请信息-定制任务次数")
    private BigDecimal sysCpCtTimes;


    @ApiModelProperty("申请信息-任务合计次数")
    private BigDecimal sysCpTTotalTimes;


    @ApiModelProperty("申请信息-购样费用合计")
    private BigDecimal sysCpStFeeAmount;


    @ApiModelProperty("申请信息-定制费用合计")
    private BigDecimal sysCpCtFeeAmount;


    @ApiModelProperty("申请信息-已投入研发费合计")
    private BigDecimal sysCpTotalFeeAmount;


    @ApiModelProperty("申请信息-购样数量")
    private BigDecimal sysCpStQty;


    @ApiModelProperty("申请信息-定制数量")
    private BigDecimal sysCpCtQty;


    @ApiModelProperty("申请信息-样品数量合计")
    private BigDecimal sysCpSTotalQty;


    @ApiModelProperty("申请信息-无效样品数量合计")
    private BigDecimal sysCpIsQty;


    @ApiModelProperty("申请信息-有效样品数量合计")
    private BigDecimal sysCpVsQty;


    @ApiModelProperty("申请信息-定品审批通过数量合计")
    private BigDecimal sysCpApprQty;


    @ApiModelProperty("申请信息-定品申请数量合计")
    private BigDecimal sysCpAppQty;


    @ApiModelProperty("申请信息-销售规划文档")
    private String sysCpSpDoc;


    @ApiModelProperty("申请信息-申请时间")
    private Date sysCpAppDate;


    @ApiModelProperty("申请信息-申请员工姓名")
    private String sysCpAppPerName;


    @ApiModelProperty("申请信息-申请员工编号")
    private String sysCpAppPerCode;


    @ApiModelProperty("审批信息-审批结果 值域{'继续选品','同意落地','研发撤销'}")
    private String sysCpApprResult;


    @ApiModelProperty("审批信息-审批说明")
    private String sysCpApprExplain;


    @ApiModelProperty("审批信息-审批时间")
    private Date sysCpApprDate;


    @ApiModelProperty("审批信息-审批员工姓名")
    private String sysCpApprPerName;


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

    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;

    /** 产品线设定-产品线名称 */
    @ApiModelProperty("产品线名称")
    private String sysPlName;

    /** 预案公共-产品名称 */
    @ApiModelProperty("预案公共-产品名称")
    private String sysProName;

    /** 预案公共-款式 */
    @ApiModelProperty("预案公共-款式")
    private String sysStyle;

    /** 预案公共-适用品牌或对象 */
    @ApiModelProperty("预案公共-适用品牌或对象")
    private String sysBrand;

    /** 预案公共-主材料 */
    @ApiModelProperty("预案公共-主材料")
    private String sysMainMaterial;

    /** 预案公共-型号 */
    @ApiModelProperty("预案公共-型号")
    private String sysModel;

    /** 同款设定-当前迭代版本 */
    @ApiModelProperty("同款设定-当前迭代版本")
    private String sysCurIteVersion;

    @ApiModelProperty("立项信息-开发方式")
    private String sysDevMethond;

    @ApiModelProperty("立项信息-上架时间要求")
    private String sysTaLtrDate;

    @ApiModelProperty("立项信息-提案等级 值域{'S','A','B','C','D'}")
    private String sysTaLevel;

    @ApiModelProperty("立项信息-提案提案立项日期")
    private Date sysTaPaDate;

    @ApiModelProperty("立项信息-产品经理编号")
    private String sysPmPerCode;

    @ApiModelProperty("立项信息-产品经理姓名")
    private String sysPmPerName;

    @ApiModelProperty("定品明细")
    private List<RdCustProductDetResult> rdCustProductDetResultList;

    @ApiModelProperty("定品明细2")
    private List<RdCustProductDet2Result> rdCustProductDetResult2List;
}
