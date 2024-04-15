package com.tadpole.cloud.operationManagement.api.brand.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
* <p>
* 品牌授权表
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@Data
@TableName("TB_BRAND_AUTHORIZATION")
@ExcelIgnoreUnannotated
public class TbBrandAuthorization implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** 品牌主表主键 */
    @TableField("BC_ID")
    private Long bcId;

    /** 注册号 */
    @TableField("REGISTRATION_NO")
    private String registrationNo;

    /** 注册国家 */
    @TableField("REGISTERED_COUNTRY")
    private String registeredCountry;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** br注册店铺 */
    @TableField("REGISTERED_STORE")
    private String registeredStore;

    /** br注册邮箱 */
    @TableField("REGISTERED_MAIL")
    private String registeredMail;

    /** br授权店铺 */
    @TableField("AUTHORIZED_STORE")
    private String authorizedStore;

    /** br授权邮箱 */
    @TableField("AUTHORIZED_MAIL")
    private String authorizedMail;

    /** br授权角色 */
    @TableField("AUTHORIZED_ROLE")
    private String authorizedRole;

    /** 创建人编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人姓名 */
    @TableField("CREATE_NAME")
    private String createName;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_NAME")
    private String updateName;

}
