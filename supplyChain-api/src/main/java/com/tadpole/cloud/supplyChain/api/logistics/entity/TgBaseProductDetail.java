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
 * 通关产品详细信息 实体类
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
@TableName("TG_BASE_PRODUCT_DETAIL")
@ExcelIgnoreUnannotated
public class TgBaseProductDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("id")
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 通关产品基础信息ID */
    @ApiModelProperty("通关产品基础信息ID")
    @TableField("PID")
    private BigDecimal pid;

    @ApiModelProperty("主物料")
    @TableField("MAIN_MATERIAL_CODE")
    private String mainMaterialCode;

    /** 国家编码 */
    @ApiModelProperty("国家编码")
    @TableField("COUNTRY_CODE")
    private String countryCode;

    /** 国家名称 */
    @ApiModelProperty("国家名称")
    @TableField("COUNTRY_NAME")
    private String countryName;

    /** HSCode */
    @ApiModelProperty("HSCode")
    @TableField("HSCODE")
    private String hsCode;

    /** 合并状态 0：未合并，1：已合并 */
    @ApiModelProperty("合并状态 0：未合并，1：已合并")
    @TableField("MERGE_STATUS")
    private String mergeStatus;

    /** 合并ID：合并数据对应合并后的数据ID */
    @ApiModelProperty("合并ID：合并数据对应合并后的数据ID")
    @TableField("MERGE_ID")
    private BigDecimal mergeId;

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