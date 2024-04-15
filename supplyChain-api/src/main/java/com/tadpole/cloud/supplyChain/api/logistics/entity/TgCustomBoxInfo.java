package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 报关自定义外箱表 实体类
 * </p>
 *
 * @author ty
 * @since 2023-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TG_CUSTOM_BOX_INFO")
@ExcelIgnoreUnannotated
public class TgCustomBoxInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 箱型 */
    @TableField("BOX_TYPE")
    private String boxType;

    /** 最小体积 */
    @TableField("MIN_VOLUME")
    private BigDecimal minVolume;

    /** 是否等于最小体积 0：否，1：是 */
    @TableField("MIN_EQ")
    private String minEq;

    /** 最大体积 */
    @TableField("MAX_VOLUME")
    private BigDecimal maxVolume;

    /** 是否等于最大体积 0：否，1：是 */
    @TableField("MAX_EQ")
    private String maxEq;

    /** 箱子重量（KG） */
    @TableField("BOX_WEIGHT")
    private BigDecimal boxWeight;

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