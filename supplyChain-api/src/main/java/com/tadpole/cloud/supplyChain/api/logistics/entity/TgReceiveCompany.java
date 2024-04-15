package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 收货公司 实体类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TG_RECEIVE_COMPANY")
@ExcelIgnoreUnannotated
public class TgReceiveCompany implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 公司名称 */
    @TableField("COMPANY_NAME_CN")
    private String companyNameCn;

    /** 公司地址 */
    @TableField("ADDR_CN")
    private String addrCn;

    /** 联系人 */
    @TableField("CONTACT_USER")
    private String contactUser;

    /** 邮箱 */
    @TableField("EMAIL")
    private String email;

    /** 电话 */
    @TableField("PHONE")
    private String phone;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

    @ApiModelProperty("国家/地区")
    @TableField("COUNTRY_CODE")
    private String countryCode;

    @ApiModelProperty("州/省/郡")
    @TableField("STATE")
    private String state;

    @ApiModelProperty("城市")
    @TableField("CITY")
    private String city;

    @ApiModelProperty("邮政编码")
    @TableField("LOG_REC_ZIP")
    private String logRecZip;

}