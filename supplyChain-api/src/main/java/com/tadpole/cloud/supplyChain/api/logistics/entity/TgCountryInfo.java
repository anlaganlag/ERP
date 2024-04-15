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
 * 国家地区信息 实体类
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
@TableName("TG_COUNTRY_INFO")
@ExcelIgnoreUnannotated
public class TgCountryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 中文全称 */
    @ApiModelProperty("中文全称")
    @TableField("COUNTRY_NAME_CN_FULL")
    private String countryNameCnFull;

    /** 中文简称 */
    @ApiModelProperty("中文简称")
    @TableField("COUNTRY_NAME_CN")
    private String countryNameCn;

    /** 英文全称 */
    @ApiModelProperty("英文全称")
    @TableField("COUNTRY_NAME_EN")
    private String countryNameEn;

    /** 英文简称 */
    @ApiModelProperty("英文简称")
    @TableField("COUNTRY_CODE")
    private String countryCode;

    /** 亚马逊站点 */
    @ApiModelProperty("亚马逊站点")
    @TableField("SITE")
    private String site;

    /** 亚马逊链接 */
    @ApiModelProperty("亚马逊链接")
    @TableField("AMAZON_URL")
    private String amazonUrl;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    @TableField("UPDATE_USER")
    private String updateUser;
}