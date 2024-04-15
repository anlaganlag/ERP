package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 供应商-联系人信息 实体类
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
@ExcelIgnoreUnannotated
public class SupplierContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
    @TableId(value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private long id;

    /** 系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 系统信息-创建部门编号 */
    @TableField("SYS_DEPT_CODE")
    private String sysDeptCode;

    /** 系统信息-创建部门名称 */
    @TableField("SYS_DEPT_NAME")
    private String sysDeptName;

    /** 系统信息-创建员工编码 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 系统信息-创建员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 系统信息-供应商编码 */
    @TableField("SYS_SUP_CODE")
    private String sysSupCode;

    /** 基本信息-姓名 */
    @TableField("SYS_NAME")
    private String sysName;

    /** 基本信息-性别 */
    @TableField("SYS_GENDER")
    private String sysGender;

    /** 基本信息-职务 */
    @TableField("SYS_JOB_TITLE")
    private String sysJobTitle;

    /** 基本信息-手机 */
    @TableField("SYS_MOBILE")
    private String sysMobile;

    /** 基本信息-电子邮箱 */
    @TableField("SYS_EMAIL")
    private String sysEmail;

    /** 基本信息-联系人状态 值域{"正常","作废"} */
    @TableField("SYS_CONTACT_STATUS")
    private String sysContactStatus;

    /** 基本信息-默认联系人 值域{"是","否"} */
    @TableField("SYS_DEFAULT_CONTACT")
    private String sysDefaultContact;

    /** 基本信息-备注 */
    @TableField("SYS_REMARKS")
    private String sysRemarks;

}