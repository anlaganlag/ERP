package com.tadpole.cloud.product.api.productproposal.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
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
 * 提案-定制申请 入参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_SAMPLE_CA")
public class RdSampleCaParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-费用申请编号 */
    @TableId(value = "SYS_FEE_APP_CODE", type = IdType.ASSIGN_ID)
    private String sysFeeAppCode;

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

    /** 系统信息-申请状态 值域{"待上传合同","待审批","待支付","已归档","已支付"} */
    @ApiModelProperty("系统信息-申请状态 值域{'待上传合同','待审批','待支付','已归档','已支付'}")
    private String sysSaApStatus;

    /** 单据联系-产品线编号 */
    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    /** 单据联系-SPU */
    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;

    /** 单据联系-提案编号 */
    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;

    /** 单据联系-拿样任务编号 */
    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;

    /** 单据联系-定制反馈编号 */
    @ApiModelProperty("单据联系-定制反馈编号")
    private String sysCustFebkCode;

    /** 申请信息-申请说明 */
    @ApiModelProperty("申请信息-申请说明")
    private String sysSaApExplain;

    /** 申请信息-申请时间 */
    @ApiModelProperty("申请信息-申请时间")
    private Date sysSaApDate;

    /** 申请信息-申请员工姓名 */
    @ApiModelProperty("申请信息-申请员工姓名")
    private String sysSaApPerName;

    /** 申请信息-申请员工编号 */
    @ApiModelProperty("申请信息-申请员工编号")
    private String sysSaApPerCode;

    /** 合同信息-合同金额 */
    @ApiModelProperty("合同信息-合同金额")
    private BigDecimal sysSaContractAmount;

    /** 合同信息-合同文件(协议) */
    @ApiModelProperty("合同信息-合同文件(协议)")
    private String sysSaContractDoc;

    /** 合同信息-合同备注(协议) */
    @ApiModelProperty("合同信息-合同备注(协议)")
    private String sysSaContractRemarks;

    /** 合同信息-上传时间(协议) */
    @ApiModelProperty("合同信息-上传时间(协议)")
    private Date sysSaContractUd;

    /** 合同信息-上传员工姓名(协议) */
    @ApiModelProperty("合同信息-上传员工姓名(协议)")
    private String sysSaContractUpn;

    /** 合同信息-上传员工编号(协议) */
    @ApiModelProperty("合同信息-上传员工编号(协议)")
    private String sysSaContractUpc;

    /** 合同审核信息-审核时间 */
    @ApiModelProperty("合同审核信息-审核时间")
    private Date sysSaAuditDate;

    /** 合同审核信息-审核结果 值域{"同意","不同意"} */
    @ApiModelProperty("合同审核信息-审核结果 值域{'同意','不同意'}")
    private String sysSaAuditResult;

    /** 合同审核信息-审核说明 */
    @ApiModelProperty("合同审核信息-审核说明")
    private String sysSaAuditExplain;

    /** 合同审核信息-审核员工姓名 */
    @ApiModelProperty("合同审核信息-审核员工姓名")
    private String sysSaAuditPerName;

    /** 合同审核信息-审核员工编号 */
    @ApiModelProperty("合同审核信息-审核员工编号")
    private String sysSaAuditPerCode;

    /** 审批信息-审批结果 值域{"修订合同","同意","不同意"} */
    @ApiModelProperty("审批信息-审批结果 值域{'修订合同','同意','不同意'}")
    private String sysSaAppResult;

    /** 审批信息-审批备注 */
    @ApiModelProperty("审批信息-审批备注")
    private String sysSaAppRemarks;

    /** 审批信息-审批时间 */
    @ApiModelProperty("审批信息-审批时间")
    private Date sysSaAppDate;

    /** 审批信息-审批员工编号 */
    @ApiModelProperty("审批信息-审批员工编号")
    private String sysSaAppPerName;

    /** 审批信息-审批员工姓名 */
    @ApiModelProperty("审批信息-审批员工姓名")
    private String sysSaAppPerCode;

    /** 支付信息-支付日期 */
    @ApiModelProperty("支付信息-支付日期")
    private Date sysSaPayDate;

    /** 支付信息-实际支付金额 */
    @ApiModelProperty("支付信息-实际支付金额")
    private BigDecimal sysSaPayAmount;

    /** 支付信息-差额说明 */
    @ApiModelProperty("支付信息-差额说明")
    private String sysSaPayVd;

    /** 支付信息-支付宝账户 */
    @ApiModelProperty("支付信息-支付宝账户")
    private String sysSaAlipayAccount;

    /** 支付信息-支付宝账户户名 */
    @ApiModelProperty("支付信息-支付宝账户户名")
    private String sysSaAlipayAn;

    /** 支付信息-支付登记时间 */
    @ApiModelProperty("支付信息-支付登记时间")
    private Date sysSaPayRd;

    /** 支付信息-支付登记员工姓名 */
    @ApiModelProperty("支付信息-支付登记员工姓名")
    private String sysSaPayRpn;

    /** 支付信息-支付登记员工编号 */
    @ApiModelProperty("支付信息-支付登记员工编号")
    private String sysSaPayRpc;

    /** 合同审核信息-是否采用模板协议 值域{"是","否"} */
    @ApiModelProperty("合同审核信息-是否采用模板协议")
    private String sysSaAuditIsUseTemp;

    /** 合同信息-上传时间(协议)集合 */
    @ApiModelProperty("合同信息-上传时间(协议)集合")
    private List<String> sysSaContractUdList;

    /** 合同审核信息-审核时间集合 */
    @ApiModelProperty("合同审核信息-审核时间集合")
    private List<String> sysSaAuditDateList;

    /** 单据联系-提案编号集合 */
    @ApiModelProperty("单据联系-提案编号集合")
    private List<String> sysTaCodeList;

    /** 合同信息-合同文件(协议)集合 */
    @ApiModelProperty("合同信息-合同文件(协议)集合")
    private List<String> sysSaContractDocList;

    @ApiModelProperty("定制反馈信息-采购负责人编号")
    private String sysCfPurPerCode;

    @ApiModelProperty("定制反馈信息-采购负责人姓名")
    private String sysCfPurPerName;

    @ApiModelProperty("定制反馈信息-采购负责人编号集合")
    private List<String> sysCfPurPerCodeList;

    @ApiModelProperty("定制反馈信息-采购负责人姓名集合")
    private List<String> sysCfPurPerNameList;
}
