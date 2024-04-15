package com.tadpole.cloud.supplyChain.api.logistics.entity;

import java.math.BigDecimal;
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
 * 发货公司 实体类
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
@TableName("TG_POST_COMPANY")
@ExcelIgnoreUnannotated
public class TgPostCompany implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 公司名称（中文） */
    @TableField("COMPANY_NAME_CN")
    private String companyNameCn;

    /** 公司名称（英文） */
    @TableField("COMPANY_NAME_EN")
    private String companyNameEn;

    /** 地址（中文） */
    @TableField("ADDR_CN")
    private String addrCn;

    /** 地址（英文） */
    @TableField("ADDR_EN")
    private String addrEn;

    /** 海关编号 */
    @TableField("CUSTOMS_NUM")
    private String customsNum;

    /** 统一信用代码 */
    @TableField("COMPANY_NUM")
    private String companyNum;

    /** 联系方式 */
    @TableField("CONTACT_INFO")
    private String contactInfo;

    /** 传真 */
    @TableField("FAX")
    private String fax;

    /** 联系人 */
    @TableField("CONTACT_USER")
    private String contactUser;

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

}