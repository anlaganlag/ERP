package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 供应商-日志 入参类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SUPPLIER_LOG")
public class SupplierLogParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
    @ApiModelProperty("系统编号")
    private long id;

    /** 供应商编号 */
    @ApiModelProperty("供应商编号")
    private String sysSupCode;

    /** 更新种类 值域{"基本信息","财务信息","账户信息","合同模板","管理信息","联系人信息","其它信息"} */
    @ApiModelProperty("更新种类 值域{基本信息,财务信息,账户信息,合同模板,管理信息,联系人信息,其它信息}")
    private String sysUpdateType;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private String sysUpdateDate;

    /** 更新内容 */
    @ApiModelProperty("更新内容")
    private String sysUpdateContent;

    /** 变更人姓名 */
    @ApiModelProperty("变更人姓名")
    private String sysUpdatePerName;

    /** 变更人编号 */
    @ApiModelProperty("变更人编号")
    private String sysUpdatePerCode;

    /** 部门审核时间 */
    @ApiModelProperty("部门审核时间")
    private Date sysDeptExamDate;

    /** 部门审核人姓名 */
    @ApiModelProperty("部门审核人姓名")
    private String sysDeptExamPerName;

    /** 部门审核人编号 */
    @ApiModelProperty("部门审核人编号")
    private String sysDeptExamPerCode;

    /** 资质审核时间 */
    @ApiModelProperty("资质审核时间")
    private Date sysQualExamDate;

    /** 资质审核人姓名 */
    @ApiModelProperty("资质审核人姓名")
    private String sysQualExamPerName;

    /** 资质审核人编号 */
    @ApiModelProperty("资质审核人编号")
    private String sysQualExamPerCode;

    /** 审批时间 */
    @ApiModelProperty("审批时间")
    private Date sysApprDate;

    /** 审批人姓名 */
    @ApiModelProperty("审批人姓名")
    private String sysApprPerName;

    /** 审批人编号 */
    @ApiModelProperty("审批人编号")
    private String sysApprPerCode;

}
