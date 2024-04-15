package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 供应商-联系人信息 出参类
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
@TableName("SUPPLIER_CONTACT_INFO")
public class SupplierContactInfoResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("系统信息-系统编号")
    private long id;


    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-创建部门编号")
    private String sysDeptCode;


    @ApiModelProperty("系统信息-创建部门名称")
    private String sysDeptName;


    @ApiModelProperty("系统信息-创建员工编码")
    private String sysPerCode;


    @ApiModelProperty("系统信息-创建员工姓名")
    private String sysPerName;


    @ApiModelProperty("系统信息-供应商编码")
    private String sysSupCode;


    @ApiModelProperty("基本信息-姓名")
    private String sysName;


    @ApiModelProperty("基本信息-性别")
    private String sysGender;


    @ApiModelProperty("基本信息-职务")
    private String sysJobTitle;


    @ApiModelProperty("基本信息-手机")
    private String sysMobile;


    @ApiModelProperty("基本信息-电子邮箱")
    private String sysEmail;


    @ApiModelProperty("基本信息-联系人状态 值域{正常,作废}")
    private String sysContactStatus;


    @ApiModelProperty("基本信息-默认联系人 值域{是,否}")
    private String sysDefaultContact;


    @ApiModelProperty("基本信息-备注")
    private String sysRemarks;

}
