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
 * 物流商管理 实体类
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_LOGISTICS_PROVIDER")
@ExcelIgnoreUnannotated
public class LsLogisticsProvider implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物流商编码 */
    @TableField("LP_CODE")
    private String lpCode;

    /** 物流商名称 */
    @TableField("LP_NAME")
    private String lpName;

    /** 物流商简称 */
    @TableField("LP_SIMPLE_NAME")
    private String lpSimpleName;

    /** 通讯地址 */
    @TableField("LP_ADDRESS")
    private String lpAddress;

    /** 统一社会信用代码 */
    @TableField("LP_UNI_SOC_CRE_CODE")
    private String lpUniSocCreCode;

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