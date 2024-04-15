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
* 品牌管理主表
* </p>
*
* @author S20190161
* @since 2023-10-24
*/
@Data
@TableName("TB_BRAND_COMMUNAL")
@ExcelIgnoreUnannotated
public class TbBrandCommunal implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /** 销售品牌名称 */
    @TableField("SALES_BRAND")
    private String salesBrand;

    /** 状态：1.启用;0.禁用;默认启用 */
    @TableField("IS_ENABLE")
    private Integer isEnable;

    /** 售后服务邮箱 */
    @TableField("AFTER_SALES_SERVICE_MAIL")
    private String afterSalesServiceMail;

    /** 绑定电话 */
    @TableField("BINDING_PHONE")
    private String bindingPhone;

    /** 官网 */
    @TableField("OFFICIAL_WEBSITE")
    private String officialWebsite;

    /** facebook */
    @TableField("FACEBOOK")
    private String facebook;

    /** instagram */
    @TableField("INSTAGRAM")
    private String instagram;

    /** 创建时间 */
    @TableField("SYS_CREATE_DATE")
    private Date sysCreateDate;

    /** 创建人编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 创建人姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

}
