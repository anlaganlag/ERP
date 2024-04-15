package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 供应商-联系人信息 入参类
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
@TableName("SUPPLIER_CONTACT_INFO")
public class SupplierContactInfoParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
    @ApiModelProperty("系统编号")
    private long id;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;

    /** 系统信息-创建部门编号 */
    @ApiModelProperty("系统信息-创建部门编号")
    private String sysDeptCode;

    /** 系统信息-创建部门名称 */
    @ApiModelProperty("系统信息-创建部门名称")
    private String sysDeptName;

    /** 系统信息-创建员工编码 */
    @ApiModelProperty("系统信息-创建员工编码")
    private String sysPerCode;

    /** 系统信息-创建员工姓名 */
    @ApiModelProperty("系统信息-创建员工姓名")
    private String sysPerName;

    /** 系统信息-供应商编码 */
    @ApiModelProperty("系统信息-供应商编码")
    private String sysSupCode;

    /** 基本信息-姓名 */
    @ApiModelProperty("基本信息-姓名")
    private String sysName;

    /** 基本信息-性别 */
    @ApiModelProperty("基本信息-性别")
    private String sysGender;

    /** 基本信息-职务 */
    @ApiModelProperty("基本信息-职务")
    private String sysJobTitle;

    /** 基本信息-手机 */
    @ApiModelProperty("基本信息-手机")
    private String sysMobile;

    /** 基本信息-电子邮箱 */
    @ApiModelProperty("基本信息-电子邮箱")
    private String sysEmail;

    /** 基本信息-联系人状态 值域{"正常","作废"} */
    @ApiModelProperty("基本信息-联系人状态 值域{正常,作废}")
    private String sysContactStatus;

    /** 基本信息-默认联系人 值域{"是","否"} */
    @ApiModelProperty("基本信息-默认联系人 值域{是,否}")
    private String sysDefaultContact;

    /** 基本信息-备注 */
    @ApiModelProperty("基本信息-备注")
    private String sysRemarks;

}
