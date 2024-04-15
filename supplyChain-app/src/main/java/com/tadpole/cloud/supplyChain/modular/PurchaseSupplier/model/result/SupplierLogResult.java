package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result;

import cn.stylefeng.guns.cloud.model.objectLog.model.AttributeModel;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 供应商-日志 出参类
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
@ExcelIgnoreUnannotated
@TableName("SUPPLIER_LOG")
public class SupplierLogResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("系统编号")
    private long id;


    @ApiModelProperty("供应商编号")
    private String sysSupCode;


    @ApiModelProperty("更新种类 值域{基本信息,财务信息,账户信息,合同模板,管理信息,联系人信息,其它信息}")
    private String sysUpdateType;


    @ApiModelProperty("更新时间")
    private String sysUpdateDate;


    @ApiModelProperty("更新内容")
    private String sysUpdateContent;


    @ApiModelProperty("变更人姓名")
    private String sysUpdatePerName;


    @ApiModelProperty("变更人编号")
    private String sysUpdatePerCode;


    @ApiModelProperty("部门审核时间")
    private Date sysDeptExamDate;


    @ApiModelProperty("部门审核人姓名")
    private String sysDeptExamPerName;


    @ApiModelProperty("部门审核人编号")
    private String sysDeptExamPerCode;


    @ApiModelProperty("资质审核时间")
    private Date sysQualExamDate;


    @ApiModelProperty("资质审核人姓名")
    private String sysQualExamPerName;


    @ApiModelProperty("资质审核人编号")
    private String sysQualExamPerCode;


    @ApiModelProperty("审批时间")
    private Date sysApprDate;


    @ApiModelProperty("审批人姓名")
    private String sysApprPerName;


    @ApiModelProperty("审批人编号")
    private String sysApprPerCode;

    @ApiModelProperty("更新内容")
    private List<AttributeModel> attributeModels;

}
